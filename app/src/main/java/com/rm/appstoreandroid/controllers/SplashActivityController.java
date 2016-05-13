package com.rm.appstoreandroid.controllers;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.androidesentials.model.utils.CoupleParams;
import com.rm.androidesentials.utils.Utils;
import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.Utils.ExecutorAsyncTask;
import com.rm.appstoreandroid.Utils.interfaces.IExecutatorAsynTask;
import com.rm.appstoreandroid.model.App;
import com.rm.appstoreandroid.model.Category;
import com.rm.appstoreandroid.model.dto.AppDTO;
import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.model.utils.Callbacks;
import com.rm.appstoreandroid.persistence.contracts.DatabaseContract;
import com.rm.appstoreandroid.persistence.database_helper.DatabaseHelper;
import com.rm.appstoreandroid.persistence.utils.DatabaseUtils;
import com.rm.appstoreandroid.presentation.activities.DashBoardActivity;
import com.rm.appstoreandroid.presentation.activities.SplashActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by oscargallon on 5/9/16.
 */
public class SplashActivityController extends AbstractController
        implements Callbacks.GetCategoiesFromArrayResourcesCallback,
        Callbacks.GetAppDTOFromBackEndCallback {

    /**
     * Instancia de la base de datos
     */
    private SQLiteDatabase sqLiteDatabase;

    /**
     * Lista de categorias
     */
    private List<CategoryDTO> categoryDTOs;

    /**
     * Lista de aplicacion obtenidas del servicio
     */
    private List<AppDTO> appDTOList;

    /**
     * Objeto para almacenar la hora de la peticion al servicio
     */
    private Date requestDate = null;

    /**
     * Objeto para encapsular un error
     */
    private Exception returnedException = null;

    /**
     * Tiempo de espera minimo de la actividad
     */
    private final int SPLASH_TIME_OUT = 3000;

    /**
     * Lista de parametros a enviar a la otra actividad
     */
    private List<CoupleParams> coupleParamsList;

    /**
     * Bandera para controlar si es la primera, o n vez de tratar de obtener conexion a internet
     */
    private boolean isCheckingAgain;

    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public SplashActivityController(Activity activity) {
        super(activity);
    }

    /**
     * Este metodo invoca el llamado para obtener
     * las categorias desde el array defino
     */
    public void getCategoriesFromResources() {
        Category.getInstance().getCategoriesFromResourcesArray(getActivity().getApplicationContext(),
                this);
        requestDate = new Date();
    }

    /**
     * Callback de exito para cuando se han obtenido las categorias desde los recursos
     *
     * @param categoryDTOs lista de categorias obtenidas
     */
    @Override
    public void onCategegoriesGot(List<CategoryDTO> categoryDTOs) {
        if (!isCheckingAgain) {
            sqLiteDatabase = new DatabaseHelper(getActivity().getApplicationContext()).getWritableDatabase();
            this.categoryDTOs = categoryDTOs;
        }

        /**
         * Debemos checkear si tenemos conexion a internet para
         * consumir el servicio
         */
        isInternetAvailable();

    }

    /**
     * Callback de Error para cuando se tratan de obtener las categorias
     *
     * @param e
     */
    @Override
    public void onGetCategoriesError(Exception e) {
        e.printStackTrace();
    }

    /**
     * Metodo que guarda las categorias en la base de datos y las
     * aplicaciones una vez estas han sido consultados
     */
    public void executeOperations() {
        ExecutorAsyncTask executorAsyncTask = new ExecutorAsyncTask(new IExecutatorAsynTask() {
            @Override
            public Object execute() {

                try {
                    /**
                     * Intentamos guardar las categorias
                     */
                    Category.getInstance().saveCategoriesIntoDatabase(sqLiteDatabase,
                            categoryDTOs);

                    /**
                     * Guardamos o actualizamos las aplicaciones
                     */
                    App.getInstance().saveOrUpdateIntoDatabase(sqLiteDatabase, appDTOList);

                } catch (Exception e) {
                    returnedException = e;
                    return false;
                }


                return true;
            }

            /**
             * Verificamos si todo fue exitoso
             * @param object respuesta de la ejecucion del hilo, true si es correcto,
             *               false de lo contrario
             */
            @Override
            public void onExecuteComplete(Object object) {
                boolean result = (boolean) object;
                if (result) {
                    /**
                     * Verificamos si paso el tiempo minimo de espera para
                     * cambiar la actividad
                     */
                    final long timePassed = new Date().getTime() - requestDate.getTime();
                    if (timePassed > SPLASH_TIME_OUT) {
                        goToDashBoard();
                    } else {
                        checkSplashTimer();
                    }
                } else {
                    onExecuteFaliure(returnedException);
                }

            }

            /**
             * Metodo que se encarga de mostrar un mensaje al usuario
             * sobre el error
             * @param e exception
             */
            @Override
            public void onExecuteFaliure(Exception e) {
                returnedException = e;
                if (e != null) {
                    e.printStackTrace();
                    showAlertDialog(getActivity().getApplicationContext()
                                    .getString(R.string.error_title),
                            e.getMessage());
                } else {
                    showAlertDialog(getActivity().getApplicationContext()
                                    .getString(R.string.error_title),
                            getActivity().getApplicationContext()
                                    .getString(R.string.default_error_message));
                }
            }
        });

        executorAsyncTask.execute();
    }

    /**
     * Callback de exito para el metodo que se encarga de obtener las aplicaciones
     * desde el servicio
     *
     * @param appsDTO string con el json retornado por el servicio
     */
    @Override
    public void onAppsDTOGot(String appsDTO) {
        try {
            /**
             * Obtenemos el objeto JsonArray con las aplicaciones
             */
            JSONObject jsonObject = new JSONObject(appsDTO);
            jsonObject = jsonObject.getJSONObject(getActivity()
                    .getApplicationContext().getString(R.string.feed_key));

            final JSONArray jsonArray = jsonObject.getJSONArray(getActivity()
                    .getApplicationContext().getString(R.string.entry_key));

            /**
             * Creamos un hilo para obtener las aplicaciones
             * desde el Json array
             */
            ExecutorAsyncTask executorAsyncTask = new ExecutorAsyncTask(new IExecutatorAsynTask() {
                @Override
                public Object execute() {
                    try {
                        appDTOList = App.getInstance().getAppFromJsonArray(jsonArray,
                                getActivity().getApplicationContext());
                    } catch (Exception e) {
                        returnedException = e;
                    }

                    return true;
                }

                /**
                 * Metodo que verifica si la repsuesta del servicio fue correcta
                 * @param object true si se obtuvieron las aplicacioens, false de lo contrario
                 */
                @Override
                public void onExecuteComplete(Object object) {
                    if ((boolean) object) {
                        executeOperations();

                    } else {
                        onExecuteFaliure(returnedException);
                    }
                }

                /**
                 * Metodo que muestra el error
                 * @param e exepcion
                 */
                @Override
                public void onExecuteFaliure(Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                        showAlertDialog(getActivity().getApplicationContext()
                                        .getString(R.string.error_title),
                                e.getMessage());
                    } else {
                        showAlertDialog(getActivity().getApplicationContext()
                                        .getString(R.string.error_title),
                                getActivity().getApplicationContext()
                                        .getString(R.string.default_error_message));
                    }


                }
            });
            executorAsyncTask.execute();
        } catch (JSONException e) {
            e.printStackTrace();
            showAlertDialog(getActivity().getApplicationContext()
                            .getString(R.string.error_title),
                    e.getMessage());
        }


    }

    /**
     * Metodo que invoca el llamado para cambiar de actividad
     */
    public void goToDashBoard() {
        coupleParamsList =
                new ArrayList<>();
        coupleParamsList.add(new CoupleParams.CoupleParamBuilder(getActivity()
                .getApplicationContext().getString(R.string.categories_key))
                .nestedObject((Serializable) categoryDTOs)
                .createCoupleParam());
        changeActivity(DashBoardActivity.class, coupleParamsList);
    }

    /**
     * Callback de eror para cuando se tratan de obtener las aplicaciones del servicio
     *
     * @param message mensaje de error
     */
    @Override
    public void onGetAppsDTOError(String message) {
        /**
         * TODO: should show a message
         */
        if (message != null) {
            showAlertDialog(getActivity().getApplicationContext()
                            .getString(R.string.error_title),
                    message);
            Log.i(getActivity().getApplicationContext()
                    .getString(R.string.error_label), message);

        } else {
            showAlertDialog(getActivity().getApplicationContext()
                            .getString(R.string.error_title),
                    getActivity().getApplicationContext()
                            .getString(R.string.default_error_message));
        }

    }

    /**
     * Este metodo muestra el mensaje de volver a intentar consultar las aplicaciones
     * del servicio, pero no se tiene conexion a internet
     *
     * @param message mensaje a mostrar
     */
    private void showRetryMessage(int message) {
        showAlertDialog(getActivity().getString(R.string.alert_label), getActivity().getString(message),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isCheckingAgain = true;
                        onCategegoriesGot(null);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }

                }, getActivity().getString(R.string.acept_label),
                getActivity().getString(R.string.cancel_label));
    }

    /**
     * Metodo que espera por el tiempo que falta para el splash,
     *  antes de cambiar de activity
     */
    private void checkSplashTimer() {
        final long timePassed = new Date().getTime() - requestDate.getTime();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToDashBoard();
            }
        }, SPLASH_TIME_OUT - timePassed);
    }

    /**
     * Este metodo trata de cerrar la conexion a la base de datos si esta abierta
     */
    public void tryToCloseDB() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    /**
     * Callback de exito para la verificacion de internet,
     * si si hay tratamos de consultar las aplicaciones
     */
    private void onInternetConnection() {
        App.getInstance().getAppsDTOFromBackend(this, getActivity()
                .getApplicationContext().getString(R.string.backend_url));
        isCheckingAgain = false;
    }

    /**
     * Callback de error para cuando no hay conexion a la base de datos
     */
    private void onNoInternetConnection() {
        /**
         * Intentamos obtener las aplicaciones de base de datos, para ver si podemos ingresar
         * si no mostramos mensaje
         */
        if (DatabaseUtils.getCount(sqLiteDatabase, DatabaseContract.AppTable.COUNT, null) > 0) {
            checkSplashTimer();
        } else {
            if (!isCheckingAgain) {
                showRetryMessage(R.string.no_internet_connection_message);
            } else {
                showRetryMessage(R.string.no_internet_after_try_again_message);
            }

        }

    }

    /**
     * Metodo que verifica la conexion a internet del usuario
     */
    public void isInternetAvailable() {

        ExecutorAsyncTask executorAsyncTask = new ExecutorAsyncTask(new IExecutatorAsynTask() {
            @Override
            public Object execute() {

                /**
                 * Tratamos de conectarnos a google y obtener su ip, si hay conexion
                 * por que el provedor de red puede estar habilitado pero puede
                 * que no tenga conexion a internet
                 */
                InetAddress ipAddr = null;
                try {
                    ipAddr = InetAddress.getByName("google.com");
                    if (ipAddr.equals("")) {
                        return false;
                    } else {
                        return true;
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            /**
             * Metodo que verifica la respuesta de la conexion a internet
             * @param object true si hay conexion, false si no
             */
            @Override
            public void onExecuteComplete(Object object) {
                boolean res = (boolean) object;
                if (res) {
                    onInternetConnection();
                } else {
                    onNoInternetConnection();
                }
            }

            /**
             * informamos que no hay internet
             * @param e error
             */
            @Override
            public void onExecuteFaliure(Exception e) {
                if (e != null) {
                    e.printStackTrace();
                } else {
                    onNoInternetConnection();
                }

            }
        });

        executorAsyncTask.execute();


    }
}
