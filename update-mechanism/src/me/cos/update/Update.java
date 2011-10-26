package me.cos.update;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class Update extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
	Log.d(Config.TAG, "onKeyDown, keyCode="+keyCode+" event="+event);
	return true;
    }
}
