package me.cos.update;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

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
	switch (keyCode) {
	case KeyEvent.KEYCODE_A:
	    ((TextView)findViewById(R.id.text)).setText("text_" + (RandomRectView.RND.nextInt()&0xff));
	    return true;
	case KeyEvent.KEYCODE_B:
	    ((View)findViewById(R.id.r2)).invalidate(0, 0, 100, 100);
	    return true;
	}
	return super.onKeyDown(keyCode, event);
    }
}
