package com.rm.appstoreandroid.controllers;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.androidesentials.model.utils.CoupleParams;
import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.Utils.ExecutorAsyncTask;
import com.rm.appstoreandroid.Utils.interfaces.IExecutatorAsynTask;
import com.rm.appstoreandroid.model.App;
import com.rm.appstoreandroid.model.Category;
import com.rm.appstoreandroid.model.dto.AppDTO;
import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.model.utils.Callbacks;
import com.rm.appstoreandroid.model.utils.DatabaseOperationEnum;
import com.rm.appstoreandroid.persistence.contracts.DatabaseContract;
import com.rm.appstoreandroid.persistence.database_helper.DatabaseHelper;
import com.rm.appstoreandroid.persistence.utils.DatabaseOperationAsynTaskBuilder;
import com.rm.appstoreandroid.persistence.utils.DatabaseOperationAsyncTask;
import com.rm.appstoreandroid.presentation.activities.AppActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oscargallon on 5/5/16.
 */
public class DashBoardActivityController extends AbstractController {

    /**
     * Instancia de la base de datos
     */
    private SQLiteDatabase sqLiteDatabase;

    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public DashBoardActivityController(Activity activity) {
        super(activity);
        //loadInitData();
    }

    /**
     * Metodo que trata de obtener las aplicaciones de un categoria
     *
     * @param term  nombre de la categoria
     * @param label label de la categoria, es el que el usuario ve
     */
    public void getAppsDTOFromCategory(final String term, final String label) {
        /**
         * Creamos un hilo para evitar que se bloquee la pantalla
         */
        ExecutorAsyncTask executorAsyncTask
                = new ExecutorAsyncTask(new IExecutatorAsynTask() {
            /**
             * Tratamos de obtener las aplicaciones por categoria
             * @return null si error, lista de aplicaciones por
             * categorias
             */
            @Override
            public Object execute() {
                sqLiteDatabase = new DatabaseHelper(getActivity().getApplicationContext()).getReadableDatabase();

                return App.getInstance()
                        .getAppsDTOByCategoryTerm(sqLiteDatabase, term);

            }

            /**
             * Verifica el resultado de la ejecucion
             * @param object lista de aplicaciones
             */
            @Override
            public void onExecuteComplete(Object object) {

                if (object != null) {
                    try {
                        List<AppDTO> appDTOList = (List<AppDTO>) object;
                        /**
                         * Si no hay aplicaciones para esta categoria mostramos un mensaje al usuario
                         */
                        if(appDTOList.size()==0){
                            showAlertDialog(getActivity().getApplicationContext()
                                    .getString(R.string.alert_label), getActivity().getApplicationContext()
                                    .getString(R.string.category_has_no_apps));
                            return;
                        }

                        /**
                         * Cambios de actividad enviando las aplicacion a estas,
                         * y el label de la categoria
                         */
                        List<CoupleParams> coupleParamsList
                                = new ArrayList<>();
                        coupleParamsList.add(
                                new CoupleParams.CoupleParamBuilder(getActivity()
                                        .getApplicationContext().getString(R.string.apps_key))
                                        .nestedObject(appDTOList).createCoupleParam());

                        coupleParamsList.add(
                                new CoupleParams.CoupleParamBuilder(getActivity()
                                        .getApplicationContext().getString(R.string.category_key))
                                        .nestedParam(label)
                                        .createCoupleParam());

                        changeActivity(AppActivity.class, coupleParamsList);

                    } catch (ClassCastException e) {
                        onExecuteFaliure(e);
                    }

                } else {
                    /**
                     * Si no  se obtuvieron las categorias por un error mostramos un mensaje al usuario
                     */
                    showAlertDialog(getActivity().getApplicationContext()
                            .getString(R.string.error_title), getActivity().getApplicationContext()
                            .getString(R.string.can_not_get_app_from_category));
                }

                tryToCloseDB();

            }

            /**
             * Si hubo error tratando de obtener las categorias de BD
             * mostramos el mensaje
             * @param e
             */
            @Override
            public void onExecuteFaliure(Exception e) {
                showAlertDialog(getActivity().getApplicationContext()
                        .getString(R.string.error_title), getActivity().getApplicationContext()
                        .getString(R.string.can_not_get_app_from_category) + e.getMessage());
                tryToCloseDB();
            }
        });

        executorAsyncTask.execute();
    }

    /**
     * Metodo que intenta cerrar la conexion a BD
     */
    private void tryToCloseDB() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }


}
