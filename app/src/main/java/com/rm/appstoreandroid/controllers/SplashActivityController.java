package com.rm.appstoreandroid.controllers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.androidesentials.model.utils.CoupleParams;
import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.model.Category;
import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.model.utils.Callbacks;
import com.rm.appstoreandroid.model.utils.DatabaseOperationEnum;
import com.rm.appstoreandroid.persistence.contracts.DatabaseContract;
import com.rm.appstoreandroid.persistence.database_helper.DatabaseHelper;
import com.rm.appstoreandroid.persistence.utils.DatabaseOperationAsyncTask;
import com.rm.appstoreandroid.presentation.activities.DashBoardActivity;
import com.rm.appstoreandroid.presentation.activities.SplashActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by oscargallon on 5/9/16.
 */
public class SplashActivityController extends AbstractController
        implements Callbacks.GetCategoiesFromArrayResourcesCallback,
        Callbacks.DatabaseLoadOperationCallback {

    private SQLiteDatabase sqLiteDatabase;

    private List<CategoryDTO> categoryDTOs;

    private Date requestDate = null;


    private int SPLASH_TIME_OUT = 5000;

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
        sqLiteDatabase = new DatabaseHelper(getActivity().getApplicationContext()).getWritableDatabase();

        this.categoryDTOs = categoryDTOs;

        Category.getInstance().saveCategoriesIntoDatabase(sqLiteDatabase,
                categoryDTOs, this);

    }

    @Override
    public void onGetCategoriesError(Exception e) {
        e.printStackTrace();

    }

    @Override
    public void onDatabaseOperationSucess(Object objects) {

        int[] insertResult = (int[]) objects;
        Log.i("Insert", " EXITOSOS: " + Integer.toString(insertResult[0]));

        Log.i("Insert", " FALLIDOS: " + Integer.toString(insertResult[1]));


        List<CoupleParams> coupleParamsList =
                new ArrayList<>();

        coupleParamsList.add(new CoupleParams.CoupleParamBuilder(getActivity()
                .getApplicationContext().getString(R.string.categories_key))
                .nestedObject((Serializable) categoryDTOs)
                .createCoupleParam());

        final long timePassed = new Date().getTime() - requestDate.getTime();
        if (timePassed > SPLASH_TIME_OUT) {
            changeActivity(DashBoardActivity.class, coupleParamsList);
        } else {
            final List<CoupleParams> coupleParamsList1 = coupleParamsList;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    changeActivity(DashBoardActivity.class, coupleParamsList1);
                }
            }, SPLASH_TIME_OUT - timePassed);
        }


    }

    @Override
    public void onDatabaseOperationFailiure(Exception e) {
        e.printStackTrace();

    }
}
