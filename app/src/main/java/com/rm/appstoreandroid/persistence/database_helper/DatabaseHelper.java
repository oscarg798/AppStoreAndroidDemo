package com.rm.appstoreandroid.persistence.database_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rm.appstoreandroid.persistence.contracts.DatabaseContract;

/**
 * Created by oscargallon on 5/5/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private final String CREATE_CATEGORY_STATEMENT =
            "insert into "
                    + DatabaseContract.CategoryTable.TABLE_NAME +
                    "(id, term, label, scheme) values (601,'Games'," +
                    "'culito', 'GAMES')";

    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        runCreateStatements(db);


    }

    private void runCreateStatements(SQLiteDatabase db){
        db.execSQL(DatabaseContract.CategoryTable.CREATE_TABLE);
        db.execSQL(CREATE_CATEGORY_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.CategoryTable.DELETE_TABLE);
        runCreateStatements(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.CategoryTable.DELETE_TABLE);
        runCreateStatements(db);

    }
}
