package com.rm.appstoreandroid.persistence.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rm.appstoreandroid.persistence.contracts.DatabaseContract;

/**
 * Created by oscargallon on 5/10/16.
 */
public class DatabaseUtils {

    private DatabaseUtils() {
    }

    public static int getCount(SQLiteDatabase db, String query, String key) {
        Cursor c = null;
        c = db.rawQuery(query, new String[]{key});
        if (c.moveToFirst()) {
            return c.getInt(0);
        }
        return 0;
    }
}
