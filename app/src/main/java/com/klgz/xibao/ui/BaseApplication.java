package com.klgz.xibao.ui;

import android.app.Application;

import com.klgz.xibao.utils.CrashHandler;

/**
 * Created by lwh on 2018/2/5.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        CrashHandler.getInstance().init();
        super.onCreate();

    }
}
