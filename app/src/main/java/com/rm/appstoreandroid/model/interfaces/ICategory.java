package com.rm.appstoreandroid.model.interfaces;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import com.rm.appstoreandroid.model.dto.CategoryDTO;

import java.util.List;

/**
 * Created by oscargallon on 5/5/16.
 */
public interface ICategory {

    List<CategoryDTO> createCategoriesFromCursor(Cursor cursor);
}
