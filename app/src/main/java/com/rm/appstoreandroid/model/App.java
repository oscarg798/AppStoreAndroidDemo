package com.rm.appstoreandroid.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rm.androidesentials.services.http.HTTPServices;
import com.rm.androidesentials.services.interfaces.IHTTPServices;
import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.model.dto.AppDTO;
import com.rm.appstoreandroid.model.dto.ImageDTO;
import com.rm.appstoreandroid.model.interfaces.IApp;
import com.rm.appstoreandroid.model.utils.Callbacks;
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
public class App implements IApp, IHTTPServices {
    private static App ourInstance = new App();

    private Context context;

    private Callbacks.GetAppDTOFromBackEndCallback getAppDTOFromBackEndCallback;

    public static App getInstance() {
        return ourInstance;
    }

    private App() {
    }

    public void getAppsDTOFromBackend(Callbacks.GetAppDTOFromBackEndCallback getAppDTOFromBackEndCallback,
                                      String url) {
        this.getAppDTOFromBackEndCallback = getAppDTOFromBackEndCallback;
        HTTPServices httpServices = new HTTPServices(this, null, "GET", true);
        httpServices.execute(url);
    }


    @Override
    public int[] saveOrUpdateIntoDatabase(SQLiteDatabase sqLiteDatabase, List<AppDTO> appDTOList) {
        int[] results = new int[2];

        List<ContentValues> contentValuesList = new ArrayList<>();
        ContentValues contentValues = null;
        for (AppDTO appDTO : appDTOList) {
            Image.getInstance().saveImageIntoDatabase(sqLiteDatabase, appDTO.getImages());
            contentValues = new ContentValues();
            contentValues.put(DatabaseContract.AppTable.COLUMN_BUNDLE_ID, appDTO.getBundleId());
            contentValues.put(DatabaseContract.AppTable.COLUMN_APP_LINK, appDTO.getAppLink());
            contentValues.put(DatabaseContract.AppTable.COLUMN_ARTIST, appDTO.getArtist());
            contentValues.put(DatabaseContract.AppTable.COLUMN_CATEGORY, appDTO.getCategory());
            contentValues.put(DatabaseContract.AppTable.COLUMN_PRICE, appDTO.getPrice());
            contentValues.put(DatabaseContract.AppTable.COLUMN_PRICE_CURRENCY, appDTO.getPriceCurrency());
            contentValues.put(DatabaseContract.AppTable.COLUMN_RELEASE_DATE, appDTO.getReleaseDate());
            contentValues.put(DatabaseContract.AppTable.COLUMN_RIGHTS, appDTO.getRights());
            contentValues.put(DatabaseContract.AppTable.COLUMN_SUMARY, appDTO.getSumary());
            contentValues.put(DatabaseContract.AppTable.COLUMN_NAME, appDTO.getName());
            contentValuesList.add(contentValues);
        }


        long result = 0;
        for (ContentValues contentValues1 : contentValuesList) {
            if (DatabaseUtils.getCount(sqLiteDatabase,
                    DatabaseContract.AppTable.COUNT_WHERE,
                    contentValues1.getAsString(DatabaseContract.AppTable.COLUMN_BUNDLE_ID)) == 0) {

                result = sqLiteDatabase.insert(DatabaseContract.AppTable.TABLE_NAME, null,
                        contentValues1);

            } else {
                contentValues1.remove(DatabaseContract.AppTable.COLUMN_BUNDLE_ID);
                result = sqLiteDatabase.update(DatabaseContract.AppTable.TABLE_NAME, contentValues1,
                        DatabaseContract.AppTable.UPDATE_WHERE_BUNDLE_ID,
                        new String[]{contentValues.getAsString(DatabaseContract.AppTable.COLUMN_BUNDLE_ID)});
            }

            if (result != -1) {
                results[0]++;
            } else {
                results[1]++;
            }
        }


        return new int[0];
    }

    @Override
    public List<AppDTO> getAppFromJsonArray(JSONArray jsonArray, Context context) {
        JSONObject jsonObject = null;
        List<AppDTO> appList = new ArrayList<>();
        AppDTO appDTO = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                appDTO = getAppFromJsonObject(jsonArray.getJSONObject(i), context);
                if (appDTO != null) {
                    appList.add(appDTO);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                appDTO = null;
                appList = null;
                break;
            }
        }
        return appList;
    }

    @Override
    public AppDTO getAppFromJsonObject(JSONObject jsonObject, Context context) {

        AppDTO appDTO = null;
        List<ImageDTO> imageDTOList = null;

        try {
            appDTO = new AppDTO.AppDTOBuilder()
                    .withAnAppLink(jsonObject.getJSONObject(context.getString(R.string.link_key))
                            .getJSONObject(context.getString(R.string.attributes_key))
                            .getString(context.getString(R.string.href_key)))
                    .withAName(jsonObject.getJSONObject(context.getString(R.string.app_name_key))
                            .getString(context.getString(R.string.label_key)))
                    .withABundleId(jsonObject.getJSONObject(context.getString(R.string.id_key))
                            .getJSONObject(context.getString(R.string.attributes_key))
                            .getString(context.getString(R.string.bundle_id_key)))
                    .withACategory(jsonObject.getJSONObject(context.getString(R.string.category_key))
                            .getJSONObject(context.getString(R.string.attributes_key))
                            .getString(context.getString(R.string.term_key)))
                    .withAnArtist(jsonObject.getJSONObject(context.getString(R.string.artist_key))
                            .getString(context.getString(R.string.label_key)))
                    .withAPrice(jsonObject.getJSONObject(context.getString(R.string.price_key))
                            .getJSONObject(context.getString(R.string.attributes_key))
                            .getString(context.getString(R.string.amount_key)))
                    .withAPriceCurrency(jsonObject.getJSONObject(context.getString(R.string.price_key))
                            .getJSONObject(context.getString(R.string.attributes_key))
                            .getString(context.getString(R.string.currency_key)))
                    .withAReleaseDate(jsonObject.getJSONObject(context.getString(R.string.release_date_key))
                            .getString(context.getString(R.string.label_key)))
                    .withASumary(jsonObject.getJSONObject(context.getString(R.string.summary_key))
                            .getString(context.getString(R.string.label_key)))
                    .withRights(jsonObject.getJSONObject(context.getString(R.string.rights_key))
                            .getString(context.getString(R.string.label_key)))
                    .withAnImages(Image.getInstance().getImageFromJsonArray(jsonObject
                            .getJSONArray(context.getString(R.string.image_key)), context))
                    .createAppDTO();

        } catch (JSONException e) {
            e.printStackTrace();
            appDTO = null;
        }


        return appDTO;
    }

    @Override
    public List<AppDTO> getAppsDTOByCategoryTerm(SQLiteDatabase db, String term) {

        //db.rawQuery(DatabaseContract.AppTable.SELECT_WHERE_CATEGORY +  " '" + term  +  "'" , null);

        Cursor cursor = db.query(DatabaseContract.AppTable.TABLE_NAME, DatabaseContract.AppTable.COLUMN_NAMES,
                DatabaseContract.AppTable.SELECT_WHERE_CATEGORY,
                new String[]{term}, null, null, null, null);
        List<AppDTO> appDTOList = getAPPDTOFromCursor(cursor, db);

        db.close();

        return appDTOList;
    }

    @Override
    public List<AppDTO> getAPPDTOFromCursor(Cursor cursor, SQLiteDatabase db) {
        List<AppDTO> appDTOList = null;
        if (cursor == null) {
            return appDTOList;
        }

        if (cursor.moveToFirst()) {
            appDTOList = new ArrayList<>();
            do {
                AppDTO appDTO = new AppDTO.AppDTOBuilder()
                        .withAnArtist(cursor.getString(cursor.getColumnIndex(DatabaseContract.AppTable.COLUMN_ARTIST)))
                        .withAPrice(cursor.getString(cursor.getColumnIndex(DatabaseContract.AppTable.COLUMN_PRICE)))
                        .withACategory(cursor.getString(cursor.getColumnIndex(DatabaseContract.AppTable.COLUMN_CATEGORY)))
                        .withAReleaseDate(cursor.getString(cursor.getColumnIndex(DatabaseContract.AppTable.COLUMN_RELEASE_DATE)))
                        .withRights(cursor.getString(cursor.getColumnIndex(DatabaseContract.AppTable.COLUMN_RIGHTS)))
                        .withABundleId(cursor.getString(cursor.getColumnIndex(DatabaseContract.AppTable.COLUMN_BUNDLE_ID)))
                        .withASumary(cursor.getString(cursor.getColumnIndex(DatabaseContract.AppTable.COLUMN_SUMARY)))
                        .withAnAppLink(cursor.getString(cursor.getColumnIndex(DatabaseContract.AppTable.COLUMN_APP_LINK)))
                        .withAName(cursor.getString(cursor.getColumnIndex(DatabaseContract.AppTable.COLUMN_NAME)))
                        .withAPriceCurrency(cursor.getString(cursor.getColumnIndex(DatabaseContract.AppTable.COLUMN_PRICE_CURRENCY)))
                        .createAppDTO();

                List<ImageDTO> imageDTOList = Image.getInstance()
                        .getImagesByApp(appDTO.getBundleId(), db);

                appDTO.setImages(imageDTOList);
                appDTOList.add(appDTO);
            } while (cursor.moveToNext());

        }
        return appDTOList;
    }

    @Override
    public void successFullResponse(String response) {
        getAppDTOFromBackEndCallback.onAppsDTOGot(response);
    }

    @Override
    public void errorResponse(String message, JSONObject jsonObject) {
        getAppDTOFromBackEndCallback.onGetAppsDTOError(message);
    }


}
