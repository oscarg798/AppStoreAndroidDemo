package com.rm.appstoreandroid.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.Utils.ExecutorAsyncTask;
import com.rm.appstoreandroid.Utils.Utils;
import com.rm.appstoreandroid.Utils.interfaces.IExecutatorAsynTask;
import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.model.dto.ImageDTO;
import com.rm.appstoreandroid.model.interfaces.ICategory;
import com.rm.appstoreandroid.model.utils.Callbacks;
import com.rm.appstoreandroid.persistence.contracts.DatabaseContract;
import com.rm.appstoreandroid.persistence.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oscargallon on 5/5/16.
 */
public class Category implements ICategory {

    public static final Category instance = new Category();


    private Context context;

    private final int arrayName = R.array.categories;


    private Category() {

    }

    @Override
    public List<CategoryDTO> createCategoriesFromCursor(Cursor cursor) {
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


    @Override
    public void getCategoriesFromResourcesArray(final Context context,
                                                final Callbacks.GetCategoiesFromArrayResourcesCallback
                                                        getCategoiesFromArrayResourcesCallback) {
        this.context = context;

        ExecutorAsyncTask executorAsyncTask = new ExecutorAsyncTask(new IExecutatorAsynTask() {
            @Override
            public Object execute() {
                TypedArray typedArray = context.getResources().obtainTypedArray(arrayName);
                List<CategoryDTO> categoriesList = new ArrayList<>();
                String[] auxStringArray = null;
                CategoryDTO categoryDTO = null;
                for (int i = 0; i < typedArray.length(); i++) {
                    auxStringArray = context.getResources().getStringArray(typedArray.getResourceId(i, 0));
                    categoryDTO = new CategoryDTO.CategoryDTOBuilder()
                            .withAnId(Integer.toBinaryString(i))
                            .withALabel(auxStringArray[1])
                            .withATerm(auxStringArray[0])
                            .withAnImage(Utils.getCategoryDrawable(auxStringArray[0], context))
                            .createCategoryDTO();
                    categoriesList.add(categoryDTO);
                }

                typedArray.recycle();
                categoryDTO = null;
                auxStringArray = null;

                return categoriesList;
            }

            @Override
            public void onExecuteComplete(Object object) {
                getCategoiesFromArrayResourcesCallback.onCategegoriesGot((List<CategoryDTO>) object);

            }

            @Override
            public void onExecuteFaliure(Exception e) {
                getCategoiesFromArrayResourcesCallback.onGetCategoriesError(e);

            }
        });

        executorAsyncTask.execute();

    }

    @Override
    public int[] saveCategoriesIntoDatabase(final SQLiteDatabase db,
                                            final List<CategoryDTO> categoryDTOList) {


        int[] insertCount = new int[2];

        List<ContentValues> contentValuesList = getContentValuesFromCategoriesDTOList(categoryDTOList);

        for (ContentValues contentValue : contentValuesList) {
            if (DatabaseUtils.getCount(db, DatabaseContract.CategoryTable.COUNT_WHERE,
                    contentValue.getAsString(DatabaseContract.CategoryTable.COLUMN_ID)) == 0) {
                long result = db.insert(DatabaseContract.CategoryTable.TABLE_NAME, null, contentValue);
                if (result != -1) {
                    insertCount[0] = insertCount[0] + 1;
                } else {
                    insertCount[1] = insertCount[1] + 1;
                }
            } else {
                insertCount[1] = insertCount[1] + 1;
            }
        }

        return insertCount;
    }

    @Override
    public List<ContentValues> getContentValuesFromCategoriesDTOList(List<CategoryDTO> categoryDTOList) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        ContentValues contentValues = null;
        for (CategoryDTO categoryDTO : categoryDTOList) {
            contentValues = new ContentValues();
            contentValues.put(DatabaseContract.CategoryTable.COLUMN_ID, categoryDTO.getId());
            contentValues.put(DatabaseContract.CategoryTable.COLUMN_LABEL, categoryDTO.getLabel());
            contentValues.put(DatabaseContract.CategoryTable.COLUMN_TERM, categoryDTO.getTerm());
            contentValues.put(DatabaseContract.CategoryTable.COLUMN_IMAGE, categoryDTO.getImage());
            if (categoryDTO.getScheme() != null) {
                contentValues.put(DatabaseContract.CategoryTable.COLUMN_SCHEME, categoryDTO.getScheme());
            }
            contentValuesList.add(contentValues);
        }

        return contentValuesList;
    }


    public static Category getInstance() {
        return instance;
    }


}
