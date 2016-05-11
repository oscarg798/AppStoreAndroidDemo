package com.rm.appstoreandroid.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.model.dto.ImageDTO;
import com.rm.appstoreandroid.model.interfaces.IImage;
import com.rm.appstoreandroid.persistence.contracts.DatabaseContract;
import com.rm.appstoreandroid.persistence.utils.DatabaseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oscargallon on 5/10/16.
 */
public class Image implements IImage {

    private static Image ourInstance = new Image();

    public static Image getInstance() {
        return ourInstance;
    }

    private Image() {
    }

    @Override
    public List<ImageDTO> getImageFromJsonArray(JSONArray jsonArray, Context context) {
        JSONObject jsonObject = null;
        ImageDTO imageDTO = null;
        List<ImageDTO> imageDTOList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                imageDTO = getImageFromJsonObject(jsonArray.getJSONObject(i), context);
                if (imageDTO != null) {
                    imageDTOList.add(imageDTO);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                imageDTO = null;
                imageDTOList = null;
                break;
            }

        }

        return imageDTOList;
    }

    @Override
    public ImageDTO getImageFromJsonObject(JSONObject jsonObject, Context context) {
        ImageDTO imageDTO = null;

        try {
            imageDTO = new ImageDTO.ImageDTOBuilder()
                    .withALink(jsonObject.getString(context.getString(R.string.label_key)))
                    .withAnAttributes(jsonObject.getJSONObject(context.getString(R.string.attributes_key))
                            .getString(context.getString(R.string.height_key)))
                    .createImageDTO();
        } catch (JSONException e) {
            e.printStackTrace();
            imageDTO = null;
        }


        return imageDTO;
    }

    @Override
    public int[] saveImageIntoDatabase(SQLiteDatabase db, List<ImageDTO> imageDTOList) {

        int[] result = new int[2];



        List<ContentValues> contentValuesList = getContentValuesFromImageDTOList(imageDTOList);

        for (ContentValues contentValues : contentValuesList) {
            if (DatabaseUtils.getCount(db, DatabaseContract.ImageTABLE.COUNT_WHERE,
                    contentValues.getAsString(DatabaseContract.ImageTABLE.COLUMN_LINK)) == 0) {
                long res = db.insert(DatabaseContract.ImageTABLE.TABLE_NAME, null, contentValues);
                if (res != -1) {
                    result[0] = result[0] + 1;
                } else {
                    result[1] = result[1] + 1;
                }

            } else {
                result[1] = result[1] + 1;
            }

        }


        return result;
    }

    @Override
    public List<ContentValues> getContentValuesFromImageDTOList(List<ImageDTO> imageDTOs) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        ContentValues contentValues = null;
        for (ImageDTO imageDTO : imageDTOs) {
            contentValues = new ContentValues();
            contentValues.put(DatabaseContract.ImageTABLE.COLUMN_LINK, imageDTO.getLink());
            contentValues.put(DatabaseContract.ImageTABLE.COLUMN_ATTRIBUTES, imageDTO.getAttributes());
            contentValues.put(DatabaseContract.ImageTABLE.COLUMN_APP, imageDTO.getApp());
            contentValuesList.add(contentValues);
        }
        return contentValuesList;
    }

    @Override
    public List<ImageDTO> getImagesByApp(String app, SQLiteDatabase db) {
        List<ImageDTO> imageDTOList = null;
        Cursor cursor = db.rawQuery(DatabaseContract.ImageTABLE.SELECT_QUERY + " '" +
                app + "'", null);
        if (cursor.moveToFirst()) {
            imageDTOList = new ArrayList<>();

            do {
                ImageDTO imageDTO = new ImageDTO.ImageDTOBuilder()
                        .withAnAttributes(cursor.getString(cursor.getColumnIndex(DatabaseContract.ImageTABLE.COLUMN_ATTRIBUTES)))
                        .withALink(cursor.getString(cursor.getColumnIndex(DatabaseContract.ImageTABLE.COLUMN_LINK)))
                        .withAnApp(cursor.getString(cursor.getColumnIndex(DatabaseContract.ImageTABLE.COLUMN_APP)))
                        .createImageDTO();

                imageDTOList.add(imageDTO);
            } while (cursor.moveToNext());
        }


        return imageDTOList;
    }
}
