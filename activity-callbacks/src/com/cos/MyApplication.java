package com.cos;

import android.app.Application;
import android.content.res.Configuration;

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        ActivityCallbacks.log("Application:onCreate()");
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onTerminate() {
        ActivityCallbacks.log("Application:onTerminate()");
        super.onTerminate();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onLowMemory() {
        ActivityCallbacks.log("Application:onLowMemory()");
        super.onLowMemory();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        ActivityCallbacks.log("Application:onConfigurationChanged()");
        super.onConfigurationChanged(newConfig);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
