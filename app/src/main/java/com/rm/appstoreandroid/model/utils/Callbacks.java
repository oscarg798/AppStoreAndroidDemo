package com.rm.appstoreandroid.model.utils;

import com.rm.appstoreandroid.model.dto.CategoryDTO;

import java.util.List;
import java.util.Objects;

/**
 * Created by oscargallon on 5/5/16.
 */
public class Callbacks {

    public interface DatabaseLoadOperationCallback {

        void onDatabaseOperationSucess(Object objects);

        void onDatabaseOperationFailiure(Exception e);
    }

    public interface DatabaseOperation{
        void execute();
    }

    public interface GetCategoiesFromArrayResourcesCallback {

        void onCategegoriesGot(List<CategoryDTO> categoryDTOs);

        void onGetCategoriesError(Exception e);
    }

}
