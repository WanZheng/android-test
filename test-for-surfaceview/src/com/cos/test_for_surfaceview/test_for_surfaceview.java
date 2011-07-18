package com.cos.test_for_surfaceview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.net.Uri;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PaintFlagsDrawFilter;
import android.content.Context;
import android.util.Log;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation;
import android.view.MotionEvent;

public class test_for_surfaceview extends Activity
{
    private Runnable mTimerTask;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	FrameLayout layout = new FrameLayout(this);
	final RectView view = new RectView(this);
	layout.addView(view);
	TranslateAnimation a = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
	a.setDuration(2*1000);
	a.setRepeatCount(Animation.INFINITE);
	// layout.startAnimation(a);

	FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
	layoutParams.width = 100;
	layoutParams.height = 200;
	layoutParams.gravity = Gravity.CENTER;
	view.setLayoutParams(layoutParams);

	// RotateAnimation animation = new RotateAnimation(-10.0f, 10.0f, layoutParams.width/2, layoutParams.height/2);
	TranslateAnimation animation = new TranslateAnimation(-50.0f, -20.0f, 50.0f, 20.0f);

	animation.setDuration(2 * 1000);
	animation.setRepeatCount(Animation.INFINITE);
	animation.setRepeatMode(Animation.REVERSE);
	view.startAnimation(animation);

        setContentView(layout);
	Log.d("COS", "cached? = " + layout.isAnimationCacheEnabled());

	layout.setOnTouchListener(new View.OnTouchListener() {
		@Override public boolean onTouch(View v, MotionEvent event){
		    final Handler handler = v.getHandler();
		    if (mTimerTask == null) {
			mTimerTask = new Runnable() {
				@Override public void run() {
				    Log.d("COS", "now="+SystemClock.uptimeMillis()+" count="+view.getCount());
				    view.resetCount();
				    handler.postAtTime(this, SystemClock.uptimeMillis() + 1*1000);
				}
			    };
			handler.postAtTime(mTimerTask, SystemClock.uptimeMillis());
		    }
		    return true;
		}
	    });
    }

    /*
    class ProxyView extends View {
	private View mRealView;
	private int mCount = 0;
	ProxyView(Context context, View realView) {
	    super(context);
	    mRealView = realView;
	}
	@Override protected void onDraw(Canvas canvas) {
	    realView.onDraw(canvas);
	    mCount++;
	}
	public int getCount() {
	    return mCount;
	}
    }
    */

    class RectView extends View {
	private Paint mPaint = new Paint();
	private int mCount = 0;
	{
	    mPaint.setColor(Color.RED);
	    mPaint.setAntiAlias(true);
	}

	RectView(Context context) {
	    super(context);
	}

	@Override protected void onDraw(Canvas canvas) {
	    mPaint.setColor(mCount % 2 == 0 ? Color.RED : Color.BLUE);
	    canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
	    mCount++;
	}

	public int getCount() { return mCount; }
	public void resetCount() { mCount = 0; }
    }
}
