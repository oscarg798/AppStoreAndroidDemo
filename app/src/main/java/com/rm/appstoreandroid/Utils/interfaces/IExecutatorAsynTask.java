package com.rm.appstoreandroid.Utils.interfaces;

/**
 * Created by oscargallon on 5/9/16.
 */
public interface IExecutatorAsynTask {

    Object execute();

    void onExecuteComplete(Object object);

    void onExecuteFaliure(Exception e);

}
