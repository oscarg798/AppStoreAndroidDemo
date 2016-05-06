package com.rm.appstoreandroid.persistence.utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.rm.appstoreandroid.model.utils.Callbacks;
import com.rm.appstoreandroid.model.utils.DatabaseOperationEnum;

/**
 * Created by oscargallon on 5/5/16.
 */
public class DatabaseOperationAsynTaskBuilder {

    private Callbacks.DatabaseLoadOperationCallback databaseLoadOperationCallback;

    private ContentValues contentValues;

    private SQLiteDatabase db;

    private String TABLE_NAME;

    private DatabaseOperationEnum databaseOperationEnum;

    private String WHERE_CLAUSE;

    private String[] WHERE_ARGUMENTS;

    private String[] COLUMN_NAMES;


    public DatabaseOperationAsynTaskBuilder withCOLUMN_NAMES(String[] COLUMN_NAMES) {
        this.COLUMN_NAMES = COLUMN_NAMES;
        return this;
    }

    public DatabaseOperationAsynTaskBuilder whitWHERE_ARGUMENTS(String[] WHERE_ARGUMENTS) {
        this.WHERE_ARGUMENTS = WHERE_ARGUMENTS;
        return this;
    }

    public DatabaseOperationAsynTaskBuilder withAWHERE_CLAUSE(String WHERE_CLAUSE) {
        this.WHERE_CLAUSE = WHERE_CLAUSE;
        return this;
    }

    public DatabaseOperationAsynTaskBuilder swithADatabaseOperationEnum(DatabaseOperationEnum databaseOperationEnum) {
        this.databaseOperationEnum = databaseOperationEnum;
        return this;
    }

    public DatabaseOperationAsynTaskBuilder withATABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
        return this;
    }

    public DatabaseOperationAsynTaskBuilder withADb(SQLiteDatabase db) {
        this.db = db;
        return this;
    }

    public DatabaseOperationAsynTaskBuilder whitContentValues(ContentValues contentValues) {
        this.contentValues = contentValues;
        return this;
    }

    public DatabaseOperationAsynTaskBuilder whitADatabaseLoadOperationCallback(Callbacks.DatabaseLoadOperationCallback databaseLoadOperationCallback) {
        this.databaseLoadOperationCallback = databaseLoadOperationCallback;
        return this;
    }

    public DatabaseOperationAsyncTask createDatabaseOperationAsyncTask() {
        return new DatabaseOperationAsyncTask(databaseLoadOperationCallback, contentValues, db,
                TABLE_NAME, databaseOperationEnum, WHERE_CLAUSE, WHERE_ARGUMENTS, COLUMN_NAMES);
    }

}
