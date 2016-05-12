package com.rm.appstoreandroid.model.interfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rm.appstoreandroid.model.App;
import com.rm.appstoreandroid.model.dto.AppDTO;
import com.rm.appstoreandroid.model.dto.ImageDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by oscargallon on 5/10/16.
 */
public interface IApp {

    int[] saveOrUpdateIntoDatabase(SQLiteDatabase sqLiteDatabase, List<AppDTO> categoryDTOList);

    List<AppDTO> getAppFromJsonArray(JSONArray jsonArray, Context context);

    AppDTO getAppFromJsonObject(JSONObject jsonObject, Context context);

    List<AppDTO> getAppsDTOByCategoryTerm(SQLiteDatabase db, String term);

    List<AppDTO> getAPPDTOFromCursor(Cursor cursor, SQLiteDatabase db);




}
