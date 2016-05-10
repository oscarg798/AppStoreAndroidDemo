package com.rm.appstoreandroid.Utils;

import android.os.AsyncTask;

import com.rm.appstoreandroid.Utils.interfaces.IExecutatorAsynTask;

/**
 * Created by oscargallon on 5/9/16.
 */
public class ExecutorAsyncTask extends AsyncTask<String, Boolean, Boolean> {

    private final IExecutatorAsynTask iExecutatorAsynTask;

    private Object returnedObject = null;

    private Exception returnedException = null;

    public ExecutorAsyncTask(IExecutatorAsynTask iExecutatorAsynTask) {
        this.iExecutatorAsynTask = iExecutatorAsynTask;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {

            returnedObject = iExecutatorAsynTask.execute();

        } catch (Exception e) {
            returnedException = e;
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            iExecutatorAsynTask.onExecuteComplete(returnedObject);
        } else {
            iExecutatorAsynTask.onExecuteFaliure(returnedException);
        }
    }
}
