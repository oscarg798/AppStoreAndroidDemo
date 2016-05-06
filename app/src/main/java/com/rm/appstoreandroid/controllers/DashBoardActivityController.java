package com.rm.appstoreandroid.controllers;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.appstoreandroid.model.Category;
import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.model.utils.Callbacks;
import com.rm.appstoreandroid.model.utils.DatabaseOperationEnum;
import com.rm.appstoreandroid.persistence.contracts.DatabaseContract;
import com.rm.appstoreandroid.persistence.database_helper.DatabaseHelper;
import com.rm.appstoreandroid.persistence.utils.DatabaseOperationAsynTaskBuilder;
import com.rm.appstoreandroid.persistence.utils.DatabaseOperationAsyncTask;

import java.util.List;

/**
 * Created by oscargallon on 5/5/16.
 */
public class DashBoardActivityController extends AbstractController
        implements Callbacks.DatabaseLoadOperationCallback {

    private SQLiteDatabase sqLiteDatabase;

    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public DashBoardActivityController(Activity activity) {
        super(activity);
        loadInitData();
    }


    private void loadInitData() {
        sqLiteDatabase =new DatabaseHelper(getActivity().getApplicationContext()).getReadableDatabase();

        DatabaseOperationAsyncTask databaseOperationAsyncTask = new
                DatabaseOperationAsynTaskBuilder()
                .withADb(sqLiteDatabase)
                .withATABLE_NAME(DatabaseContract.CategoryTable.TABLE_NAME)
                .whitADatabaseLoadOperationCallback(this)
                .swithADatabaseOperationEnum(DatabaseOperationEnum.LOAD)
                .withCOLUMN_NAMES(DatabaseContract.CategoryTable.COLUMN_NAMES)
                .createDatabaseOperationAsyncTask();

        databaseOperationAsyncTask.execute();
    }

    @Override
    public void onDatabaseOperationSucess(Object objects) {
        if (objects instanceof Cursor) {
            List<CategoryDTO> categoryDTOList =
                    Category.getInstance().createCategoriesFromCursor((Cursor) objects);
            if (categoryDTOList != null && categoryDTOList.size() > 0) {
                showAlertDialog("ALERTA", "TENEMOS MAS DE UNO");
            } else {
                onDatabaseOperationSucess(null);
            }
        }

        tryToCloseDatabase();


    }

    @Override
    public void onDatabaseOperationFailiure(Exception e) {
        if (e != null) {
            showAlertDialog("ERROR", e.getMessage());

        } else {
            showAlertDialog("ERROR", "ERROR DESCONOCIDO");

        }
        tryToCloseDatabase();



    }

    private void tryToCloseDatabase(){
        if(sqLiteDatabase != null && sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
            sqLiteDatabase= null;
        }
    }
}
