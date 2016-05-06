package com.rm.appstoreandroid.persistence.contracts;

/**
 * Created by oscargallon on 5/5/16.
 */
public class DatabaseContract {

    public static final String DATABASE_NAME = "appstore.db";

    public static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    private static final String PRIMARY_KEY = " PRIMARY KEY";

    private DatabaseContract() {
    }

    public static abstract class CategoryTable {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TERM = "term";
        public static final String COLUMN_LABEL = "label";
        public static final String COLUMN_SCHEME = "scheme";
        public static final String[] COLUMN_NAMES =
                new String[]{COLUMN_ID, COLUMN_TERM, COLUMN_LABEL, COLUMN_SCHEME};

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                COLUMN_TERM + TEXT_TYPE + COMMA_SEP +
                COLUMN_LABEL + TEXT_TYPE + COMMA_SEP +
                COLUMN_SCHEME + TEXT_TYPE + ")";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    }


}
