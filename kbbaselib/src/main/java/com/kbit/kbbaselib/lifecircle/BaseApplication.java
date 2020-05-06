package com.kbit.kbbaselib.lifecircle;

import android.app.Application;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityManager.registerLifeCicleCallback();
    }


}
