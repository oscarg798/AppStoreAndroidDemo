package com.rm.appstoreandroid.persistence.contracts;

/**
 * Created by oscargallon on 5/5/16.
 */
public class DatabaseContract {

    public static final String DATABASE_NAME = "appstore";

    public static final int DATABASE_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

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
        public static final String COLUMN_IMAGE = "image";


        public static final String[] COLUMN_NAMES =
                new String[]{COLUMN_ID, COLUMN_TERM, COLUMN_LABEL, COLUMN_SCHEME};

        public static final String CREATE_TABLE = "CREATE TABLE  IF NOT EXISTS " +
                TABLE_NAME + " (" +
                COLUMN_ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                COLUMN_TERM + TEXT_TYPE + COMMA_SEP +
                COLUMN_LABEL + TEXT_TYPE + COMMA_SEP +
                COLUMN_SCHEME + TEXT_TYPE + COMMA_SEP +
                COLUMN_IMAGE + INTEGER_TYPE + ")";

        public static final String COUNT_WHERE =
                "select count(*) from " + TABLE_NAME + " where " + COLUMN_ID + " = ?";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static abstract class ImageTABLE {
        public static final String TABLE_NAME = "image";
        public static final String COLUMN_LINK = "link";
        public static final String COLUMN_ATTRIBUTES = "attributes";
        public static final String COLUMN_APP = "app";

        public static final String[] COLUMN_NAMES =
                new String[]{COLUMN_LINK, COLUMN_ATTRIBUTES};

        public static final String CREATE_TABLE = "CREATE TABLE  IF NOT EXISTS " +
                TABLE_NAME + "(" +
                COLUMN_LINK + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                COLUMN_ATTRIBUTES + TEXT_TYPE + COMMA_SEP +
                COLUMN_APP + TEXT_TYPE + ")";

        public static final String COUNT_WHERE =
                "select count(*) from " + TABLE_NAME + " where " + COLUMN_LINK + " = ?";

        public static final String SELECT_QUERY =
                "select * from " + TABLE_NAME + " where " + COLUMN_APP + "=";


        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class AppTable {
        public static final String TABLE_NAME = "app";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SUMARY = "sumary";
        public static final String COLUMN_PRICE_CURRENCY = "price_currency";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_RIGHTS = "rights";
        public static final String COLUMN_APP_LINK = "app_link";
        public static final String COLUMN_BUNDLE_ID = "bundle_id";
        public static final String COLUMN_ARTIST = "artist";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + "(" +
                COLUMN_BUNDLE_ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_SUMARY + TEXT_TYPE + COMMA_SEP +
                COLUMN_PRICE_CURRENCY + TEXT_TYPE + COMMA_SEP +
                COLUMN_PRICE + TEXT_TYPE + COMMA_SEP +
                COLUMN_RIGHTS + TEXT_TYPE + COMMA_SEP +
                COLUMN_APP_LINK + TEXT_TYPE + COMMA_SEP +
                COLUMN_ARTIST + TEXT_TYPE + COMMA_SEP +
                COLUMN_CATEGORY + TEXT_TYPE + COMMA_SEP +
                COLUMN_RELEASE_DATE + TEXT_TYPE + ")";


        public static final String[] COLUMN_NAMES =
                new String[]{COLUMN_NAME, COLUMN_SUMARY, COLUMN_PRICE_CURRENCY,
                        COLUMN_PRICE, COLUMN_RIGHTS, COLUMN_APP_LINK, COLUMN_BUNDLE_ID, COLUMN_ARTIST,
                        COLUMN_CATEGORY, COLUMN_RELEASE_DATE};

        public static final String COUNT_WHERE =
                "select count(*) from " + TABLE_NAME + " where " + COLUMN_BUNDLE_ID + " = ?";

        public static final String COUNT =
                "select count(*) from " + TABLE_NAME;

        public static final String UPDATE_WHERE_BUNDLE_ID = COLUMN_BUNDLE_ID + " =?";

        public static final String SELECT_WHERE_CATEGORY =
                COLUMN_CATEGORY + " =?";


        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


}
