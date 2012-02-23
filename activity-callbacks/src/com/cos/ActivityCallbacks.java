package com.cos;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

public class ActivityCallbacks extends Activity
{
    private static final String TAG = "ActivityCallbacks";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        log("onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onDestroy() {
        log("onDestroy");
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onPause() {
        log("onPause");
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onResume() {
        log("onResume");
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public static void log(String s) {
        Log.d(TAG, "" + Process.myPid() + "," + Process.myTid() + "," + Process.myUid() + ": " + s);
    }
}
