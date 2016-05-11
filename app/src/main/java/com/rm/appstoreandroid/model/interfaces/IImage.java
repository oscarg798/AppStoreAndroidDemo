package com.rm.appstoreandroid.model.interfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rm.appstoreandroid.model.dto.ImageDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by oscargallon on 5/10/16.
 */
public interface IImage {

    List<ImageDTO> getImageFromJsonArray(JSONArray jsonArray, Context context);

    ImageDTO getImageFromJsonObject(JSONObject jsonObject, Context context);

    int[] saveImageIntoDatabase(SQLiteDatabase db, List<ImageDTO> imageDTOList);

    List<ContentValues> getContentValuesFromImageDTOList(List<ImageDTO> imageDTOs);

    List<ImageDTO> getImagesByApp(String app, SQLiteDatabase db);



}
