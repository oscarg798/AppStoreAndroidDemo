package com.rm.appstoreandroid.persistence.database_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rm.appstoreandroid.persistence.contracts.DatabaseContract;

/**
 * Created by oscargallon on 5/5/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        runCreateStatements(db);
    }

    private void runCreateStatements(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.CategoryTable.CREATE_TABLE);
        db.execSQL(DatabaseContract.ImageTABLE.CREATE_TABLE);
        db.execSQL(DatabaseContract.AppTable.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.CategoryTable.DELETE_TABLE);
        db.execSQL(DatabaseContract.ImageTABLE.DELETE_TABLE);
        db.execSQL(DatabaseContract.AppTable.DELETE_TABLE);
        runCreateStatements(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.CategoryTable.DELETE_TABLE);
        db.execSQL(DatabaseContract.ImageTABLE.DELETE_TABLE);
        db.execSQL(DatabaseContract.AppTable.DELETE_TABLE);
        runCreateStatements(db);

    }
}
