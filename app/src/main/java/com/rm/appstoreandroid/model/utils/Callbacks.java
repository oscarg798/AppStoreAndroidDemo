package com.rm.appstoreandroid.model.utils;

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


}
