package com.rm.appstoreandroid.model.interfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.model.utils.Callbacks;
import com.rm.appstoreandroid.persistence.database_helper.DatabaseHelper;

import java.util.List;

/**
 * Created by oscargallon on 5/5/16.
 */
public interface ICategory {

    List<CategoryDTO> createCategoriesFromCursor(Cursor cursor);

    void getCategoriesFromResourcesArray(Context context,
                                         Callbacks.GetCategoiesFromArrayResourcesCallback
                                                 getCategoiesFromArrayResourcesCallback);

    int[] saveCategoriesIntoDatabase(SQLiteDatabase sqLiteDatabase, List<CategoryDTO> categoryDTOList);

    List<ContentValues> getContentValuesFromCategoriesDTOList(List<CategoryDTO> categoryDTOList);
}
