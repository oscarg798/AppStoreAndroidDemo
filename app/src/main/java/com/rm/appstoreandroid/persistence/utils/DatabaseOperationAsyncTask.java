package com.rm.appstoreandroid.persistence.utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import com.rm.appstoreandroid.model.utils.Callbacks;
import com.rm.appstoreandroid.model.utils.DatabaseOperationEnum;

/**
 * Created by oscargallon on 5/5/16.
 */
public class DatabaseOperationAsyncTask extends AsyncTask<String, Boolean, Boolean> {

    private final Callbacks.DatabaseLoadOperationCallback databaseLoadOperationCallback;

    private final ContentValues contentValues;

    private final SQLiteDatabase db;

    private final String TABLE_NAME;


    private final DatabaseOperationEnum databaseOperationEnum;

    private final String WHERE_CLAUSE;

    private final String[] WHERE_ARGUMENTS;

    private final String[] COLUMN_NAMES;

    private Exception exception;

    private Object returnedObject = null;

    public DatabaseOperationAsyncTask(Callbacks.DatabaseLoadOperationCallback databaseLoadOperationCallback,
                                      ContentValues contentValues, SQLiteDatabase db,
                                      String TABLE_NAME, DatabaseOperationEnum databaseOperationEnum,
                                      String WHERE_CLAUSE, String[] WHERE_ARGUMENTS, String[] COLUMN_NAMES) {
        this.databaseLoadOperationCallback = databaseLoadOperationCallback;
        this.contentValues = contentValues;
        this.db = db;
        this.TABLE_NAME = TABLE_NAME;
        this.databaseOperationEnum = databaseOperationEnum;
        this.WHERE_CLAUSE = WHERE_CLAUSE;
        this.WHERE_ARGUMENTS = WHERE_ARGUMENTS;
        this.COLUMN_NAMES = COLUMN_NAMES;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        switch (databaseOperationEnum) {
            case INSERT: {
                try {
                    insertIntoDatabase();
                } catch (SQLiteException e) {
                    exception = e;
                }
                break;
            }

            case UPDATE: {
                try {
                    loadFromDatabase();
                } catch (SQLiteException e) {
                    exception = e;
                }
                updateIntoDatabase();
                break;
            }

            case LOAD: {
                try {
                    loadFromDatabase();
                } catch (SQLiteException e) {
                    exception = e;
                }
                break;
            }

            case DELETE: {
                try {
                    deleteFromDatabase();
                } catch (SQLiteException e) {
                    exception = e;
                }

                break;
            }
        }

        return (returnedObject != null && !(returnedObject instanceof Exception));
    }

    /**
     * -1 if an error
     */
    private void insertIntoDatabase() throws SQLiteException {
        db.beginTransaction();
        returnedObject = db.insert(TABLE_NAME, null, contentValues);


    }

    /**
     * 0 if no row was affected
     */
    private void deleteFromDatabase() throws SQLiteException {
        db.beginTransaction();
        returnedObject = db.delete(TABLE_NAME, WHERE_CLAUSE, WHERE_ARGUMENTS);
    }

    /**
     * Number of rows Affected
     */
    private void updateIntoDatabase() throws SQLiteException {
        db.beginTransaction();
        returnedObject = db.update(TABLE_NAME, contentValues, WHERE_CLAUSE, WHERE_ARGUMENTS);
    }


    private void loadFromDatabase() throws SQLiteException {
        db.beginTransaction();
        returnedObject = db.query(TABLE_NAME, COLUMN_NAMES, WHERE_CLAUSE, WHERE_ARGUMENTS, null, null, null);
        db.endTransaction();


    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            databaseLoadOperationCallback.onDatabaseOperationSucess(returnedObject);
        } else {
            databaseLoadOperationCallback.onDatabaseOperationFailiure(exception);
        }
    }


}
