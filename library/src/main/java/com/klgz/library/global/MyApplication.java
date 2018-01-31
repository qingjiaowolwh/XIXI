package com.klgz.library.global;

import android.app.Application;


/**
 * Created by lwh on 2016/3/30.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

}
