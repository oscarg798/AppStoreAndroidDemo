package com.rm.appstoreandroid.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rm.appstoreandroid.R;
import com.rm.appstoreandroid.Utils.ExecutorAsyncTask;
import com.rm.appstoreandroid.Utils.interfaces.IExecutatorAsynTask;
import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.model.interfaces.ICategory;
import com.rm.appstoreandroid.model.utils.Callbacks;
import com.rm.appstoreandroid.persistence.contracts.DatabaseContract;

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
                            .withAnImage(getCategoryDrawable(auxStringArray[0]))
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
    public void saveCategoriesIntoDatabase(final SQLiteDatabase db,
                                           final List<CategoryDTO> categoryDTOList,
                                           final Callbacks.DatabaseLoadOperationCallback
                                                   databaseLoadOperationCallback) {

        ExecutorAsyncTask executorAsyncTask = new ExecutorAsyncTask(new IExecutatorAsynTask() {
            @Override
            public Object execute() {
                int[] insertCount = new int[2];
                db.beginTransactionNonExclusive();

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

                for (ContentValues contentValue : contentValuesList) {
                    if (getCount(db, contentValue.getAsString(DatabaseContract.CategoryTable.COLUMN_ID)) == 0) {
                        long result = db.insert(DatabaseContract.CategoryTable.TABLE_NAME, null, contentValue);
                        if (result != -1) {
                            insertCount[0] = insertCount[0] + 1;
                        } else {
                            insertCount[1] = insertCount[1] + 1;

                        }
                    }
                }
                return insertCount;
            }

            @Override
            public void onExecuteComplete(Object object) {
                databaseLoadOperationCallback.onDatabaseOperationSucess(object);
            }

            @Override
            public void onExecuteFaliure(Exception e) {
                databaseLoadOperationCallback.onDatabaseOperationFailiure(e);
            }
        });

        executorAsyncTask.execute();
    }

    private int getCount(SQLiteDatabase db, String key) {
        Cursor c = null;
        c = db.rawQuery(DatabaseContract.CategoryTable.COUNT_WHERE, new String[]{key});
        if (c.moveToFirst()) {
            return c.getInt(0);
        }
        return 0;
    }


    public static Category getInstance() {
        return instance;
    }


    private int getCategoryDrawable(String term) {
        int drawable = 0;
        if (term.equals(this.context.getString(R.string.books_key))) {
            drawable = R.drawable.ic_book_black_24dp;
        } else if (term.equals(this.context.getString(R.string.business_key))) {
            drawable = R.drawable.ic_business_black_24dp;

        } else if (term.equals(this.context.getString(R.string.education_key))) {
            drawable = R.drawable.ic_education_black_24dp;

        } else if (term.equals(this.context.getString(R.string.catalogs_key))) {
            drawable = R.drawable.ic_catalog_black_24dp;

        } else if (term.equals(this.context.getString(R.string.entertaiment_key))) {
            drawable = R.drawable.ic_entertaiment_black_24dp;

        } else if (term.equals(this.context.getString(R.string.finance_key))) {
            drawable = R.drawable.ic_financial_black_24dp;

        } else if (term.equals(this.context.getString(R.string.food_and_drink_key))) {
            drawable = R.drawable.ic_food_and_drink_black_24dp;

        } else if (term.equals(this.context.getString(R.string.games_key))) {
            drawable = R.drawable.ic_games_black_24dp;

        } else if (term.equals(this.context.getString(R.string.health_and_fitness_key))) {
            drawable = R.drawable.ic_health_black_24dp;

        } else if (term.equals(this.context.getString(R.string.lifestyle_key))) {
            drawable = R.drawable.ic_life_style_black_24dp;

        } else if (term.equals(this.context.getString(R.string.medical_key))) {
            drawable = R.drawable.ic_medical_black_24dp;

        } else if (term.equals(this.context.getString(R.string.music_key))) {
            drawable = R.drawable.ic_music_black_24dp;

        } else if (term.equals(this.context.getString(R.string.navigation_key))) {
            drawable = R.drawable.ic_navigation_24dp;

        } else if (term.equals(this.context.getString(R.string.news_key))) {
            drawable = R.drawable.ic_news_24dp;

        } else if (term.equals(this.context.getString(R.string.photo_and_video_key))) {
            drawable = R.drawable.ic_photo_and_video_24dp;

        } else if (term.equals(this.context.getString(R.string.productivity_key))) {
            drawable = R.drawable.ic_productivity_24dp;

        } else if (term.equals(this.context.getString(R.string.reference_key))) {
            drawable = R.drawable.ic_reference_24dp;

        } else if (term.equals(this.context.getString(R.string.social_networking_key))) {
            drawable = R.drawable.ic_social_networking_24dp;

        } else if (term.equals(this.context.getString(R.string.sports_key))) {
            drawable = R.drawable.ic_sports_black_24dp;

        } else if (term.equals(this.context.getString(R.string.travel_key))) {
            drawable = R.drawable.ic_travel_black_24dp;

        } else if (term.equals(this.context.getString(R.string.utilities_key))) {
            drawable = R.drawable.ic_utilities_24dp;
        } else if (term.equals(this.context.getString(R.string.weather_key))) {
            drawable = R.drawable.ic_weather_black_24dp;
        } else {
            drawable = R.drawable.ic_utilities_24dp;


        }


        return drawable;
    }


}
