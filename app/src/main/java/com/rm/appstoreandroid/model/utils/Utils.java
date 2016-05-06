package com.rm.appstoreandroid.model.utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.rm.appstoreandroid.persistence.database_helper.DatabaseHelper;

/**
 * Created by oscargallon on 5/5/16.
 */
public class Utils {

    public interface ExecuteDataBaseOperation {

        Object databaseOperation(SQLiteDatabase db, ContentValues contentValues,
                              DatabaseOperationEnum databaseOperationEnum);


    }


}
