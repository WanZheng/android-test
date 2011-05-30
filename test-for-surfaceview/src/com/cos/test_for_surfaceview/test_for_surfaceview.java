package com.cos.test_for_surfaceview;

import android.app.Activity;
import android.os.Bundle;
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
import android.view.animation.Animation;
import android.view.MotionEvent;

public class test_for_surfaceview extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	FrameLayout layout = new FrameLayout(this);
	RectView view = new RectView(this);
	layout.addView(view);

	FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
	layoutParams.width = 100;
	layoutParams.height = 200;
	layoutParams.gravity = Gravity.CENTER;
	view.setLayoutParams(layoutParams);

	RotateAnimation animation = new RotateAnimation(-10.0f, 10.0f, layoutParams.width/2, layoutParams.height/2);
	animation.setDuration(2 * 1000);
	animation.setRepeatCount(Animation.INFINITE);
	animation.setRepeatMode(Animation.REVERSE);
	view.startAnimation(animation);

        setContentView(layout);
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
	    /*
	    PaintFlagsDrawFilter filter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG);
	    canvas.setDrawFilter( filter );
	    */
	    canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
	    mCount++;
	}

	@Override public boolean onTouchEvent(MotionEvent event) {
	    Log.d("COS", "count="+getCount());
	    resetCount();
	    return super.onTouchEvent(event);
	}

	public int getCount() { return mCount; }
	public void resetCount() { mCount = 0; }
    }
}
