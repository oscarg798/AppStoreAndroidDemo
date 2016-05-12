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

    private SQLiteDatabase sqLiteDatabase;

    private List<CategoryDTO> categoryDTOs;

    private List<AppDTO> appDTOList;

    private Date requestDate = null;

    private Exception returnedException = null;

    private int SPLASH_TIME_OUT = 3000;

    private List<CoupleParams> coupleParamsList;

    private boolean isCheckingAgain;


    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public SplashActivityController(Activity activity) {
        super(activity);
    }


    public void getCategoriesFromResources() {
        Category.getInstance().getCategoriesFromResourcesArray(getActivity().getApplicationContext(),
                this);
        requestDate = new Date();
    }

    @Override
    public void onCategegoriesGot(List<CategoryDTO> categoryDTOs) {
        if (!isCheckingAgain) {
            sqLiteDatabase = new DatabaseHelper(getActivity().getApplicationContext()).getWritableDatabase();
            this.categoryDTOs = categoryDTOs;
        }

        isInternetAvailable();

    }

    @Override
    public void onGetCategoriesError(Exception e) {
        e.printStackTrace();
    }

    public void executeOperations() {
        ExecutorAsyncTask executorAsyncTask = new ExecutorAsyncTask(new IExecutatorAsynTask() {
            @Override
            public Object execute() {

                try {

                    Category.getInstance().saveCategoriesIntoDatabase(sqLiteDatabase,
                            categoryDTOs);

                    App.getInstance().saveOrUpdateIntoDatabase(sqLiteDatabase, appDTOList);
                    tryToCloseDB();

                } catch (Exception e) {
                    tryToCloseDB();
                    returnedException = e;
                    return false;
                }


                return true;
            }

            @Override
            public void onExecuteComplete(Object object) {
                boolean result = (boolean) object;
                if (result) {
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


    @Override
    public void onAppsDTOGot(String appsDTO) {
        try {
            JSONObject jsonObject = new JSONObject(appsDTO);
            jsonObject = jsonObject.getJSONObject(getActivity()
                    .getApplicationContext().getString(R.string.feed_key));

            final JSONArray jsonArray = jsonObject.getJSONArray(getActivity()
                    .getApplicationContext().getString(R.string.entry_key));

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

                @Override
                public void onExecuteComplete(Object object) {
                    if ((boolean) object) {
                        executeOperations();

                    } else {
                        onExecuteFaliure(returnedException);
                    }
                }

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

    public void goToDashBoard() {
        coupleParamsList =
                new ArrayList<>();
        coupleParamsList.add(new CoupleParams.CoupleParamBuilder(getActivity()
                .getApplicationContext().getString(R.string.categories_key))
                .nestedObject((Serializable) categoryDTOs)
                .createCoupleParam());
        changeActivity(DashBoardActivity.class, coupleParamsList);
    }

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

    private void checkSplashTimer() {
        final long timePassed = new Date().getTime() - requestDate.getTime();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToDashBoard();
            }
        }, SPLASH_TIME_OUT - timePassed);
    }

    private void tryToCloseDB() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public void onInternetConnection() {
        App.getInstance().getAppsDTOFromBackend(this, getActivity()
                .getApplicationContext().getString(R.string.backend_url));
        isCheckingAgain = false;
    }

    public void onNoInternetConnection() {
        if (DatabaseUtils.getCount(sqLiteDatabase, DatabaseContract.AppTable.COUNT, null) > 0) {
            checkSplashTimer();
            tryToCloseDB();
        } else {
            if (!isCheckingAgain) {
                showRetryMessage(R.string.no_internet_connection_message);
            } else {
                showRetryMessage(R.string.no_internet_after_try_again_message);
            }

        }

    }

    public void isInternetAvailable() {

        ExecutorAsyncTask executorAsyncTask = new ExecutorAsyncTask(new IExecutatorAsynTask() {
            @Override
            public Object execute() {
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

            @Override
            public void onExecuteComplete(Object object) {
                boolean res = (boolean) object;
                if (res) {
                    onInternetConnection();
                } else {
                    onNoInternetConnection();
                }
            }

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
