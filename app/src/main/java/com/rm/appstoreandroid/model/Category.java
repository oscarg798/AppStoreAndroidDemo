package com.rm.appstoreandroid.model;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;

import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.model.interfaces.ICategory;
import com.rm.appstoreandroid.persistence.contracts.DatabaseContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oscargallon on 5/5/16.
 */
public class Category implements ICategory {

    public static final Category instance = new Category();

    private Category() {
    }

    @Override
    public List<CategoryDTO> createCategoriesFromCursor(Cursor cursor){
        List<CategoryDTO> categoryDTOList = null;
        CategoryDTO categoryDTO = null;
        if (cursor == null) {
            return categoryDTOList;
        }

        if (cursor.moveToFirst()) {
            categoryDTOList = new ArrayList<>();
            do {
                categoryDTO = new CategoryDTO.CategoryDTOBuilder()
                        .withAnId(cursor.getString(cursor.getColumnIndex(DatabaseContract.CategoryTable.COLUMN_ID)))
                        .withALabel(cursor.getString(cursor.getColumnIndex(DatabaseContract.CategoryTable.COLUMN_LABEL)))
                        .withATerm(cursor.getString(cursor.getColumnIndex(DatabaseContract.CategoryTable.COLUMN_TERM)))
                        .withAScheme(cursor.getString(cursor.getColumnIndex(DatabaseContract.CategoryTable.COLUMN_SCHEME)))
                        .createCategoryDTO();
                categoryDTOList.add(categoryDTO);

            } while (cursor.moveToNext());
        }

        return categoryDTOList;
    }

    public static Category getInstance() {
        return instance;
    }
}
