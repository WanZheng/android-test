package animation.cos.com;

import android.os.SystemClock;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.Context;
import android.widget.FrameLayout;
import android.view.animation.TranslateAnimation;
// import android.view.animation.Animation;
import android.util.Log;
import android.view.Gravity;

public class Animation extends Activity
{
    final static String TAG="Animation";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	View item = new MyView(this);
	item.setBackgroundColor(Color.RED);
	FrameLayout layout = (FrameLayout) findViewById(R.id.layout);
	layout.addView(item);
	FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) item.getLayoutParams();
	layoutParams.width = 100;
	layoutParams.height = 200;
	layoutParams.gravity = Gravity.CENTER;
	item.setLayoutParams(layoutParams);

	TranslateAnimation a = new TranslateAnimation(0.0f, 0.0f, 0.0f, 100.0f);
	a.setDuration(1000);
	if (false) {
	    a.setRepeatCount(android.view.animation.Animation.INFINITE);
	    a.setRepeatMode(android.view.animation.Animation.REVERSE);
	}
	item.startAnimation(a);
    }

    class MyView extends View
    {
	long mStartTime;
	int mCount = 0;

	MyView(Context context) {
	    super(context);
	}

	@Override protected void onDraw(Canvas canvas) {
	    //Log.d(TAG, "onDraw()");
	    mCount ++;
	    super.onDraw(canvas);
	    try{
		// Thread.sleep(100);
	    }catch(Exception e){
	    }
	    //Log.d(TAG, "leave onDraw()");
	}

	@Override protected void onAnimationStart(){
	    super.onAnimationStart();
	    Log.d(TAG, "Animation start at: " + mStartTime);
	    mStartTime = SystemClock.uptimeMillis();
	    mCount = 0;
	}

	@Override protected void onAnimationEnd(){
	    super.onAnimationEnd();

	    long endTime = SystemClock.uptimeMillis();
	    Log.d(TAG, "Animation duration: " + (endTime - mStartTime) + ",fps = " + mCount * 1000 / (endTime - mStartTime) + ", count=" + mCount);
	}
    }
}
