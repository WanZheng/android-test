package drag.cos;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.widget.LinearLayout;
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

class DragHost extends ViewGroup {
    private View mHostedView;
    private View mDragItem;

    public DragHost(Context context) {
	super(context);
    }

    public void setHostedView(View hostedView) throws Exception{
	if (mHostedView != null) {
	    throw new Exception("has a hosted view already");
	}
	mHostedView = hostedView;
	addView(hostedView);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	measureChildren(widthMeasureSpec, heightMeasureSpec);
	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
	if (mHostedView != null) {
	    Log.d("cos", "hostedView.layout("+l+","+t+","+r+","+b+")");
	    mHostedView.layout(0, 0, r-l, b-t);
	    Log.d("cos", "done");
	}else{
	    Log.d("cos", "hostedView == null");
	}
    }

    /*
    @Override protected void onDraw(Canvas canvas) {
	Log.d("cos", "DragHost.onDraw()");
	//super.draw(canvas);
	//mHostedView.draw(canvas);

	Paint paint = new Paint();
	paint.setColor(Color.RED);
	canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    @Override public void draw(Canvas canvas) {
	Log.d("cos", "DragHost.draw()");
	// super.draw(canvas);
    }
    */

    @Override protected void dispatchDraw(Canvas canvas) {
	Log.d("cos", "dispatchDraw()");
	super.dispatchDraw(canvas);

	Paint paint = new Paint();
	paint.setColor(Color.GREEN);
	canvas.drawRect(0, 0, 50, 100, paint);
    }
}

public class drag extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	DragHost dragHost = new DragHost(this);
        LinearLayout layout = new LinearLayout(this);
	layout.setOrientation(LinearLayout.VERTICAL);

        RectView view = new RectView(this, Color.BLUE);
        layout.addView(view, 300, 200);

	view = new RectView(this, Color.RED);
	layout.addView(view, 200, 300);

	try {
	    dragHost.setHostedView(layout);
	} catch (Exception e){
	    Log.d("cos", "" + e);
	}

	setContentView(dragHost);
    }

    class RectView extends View {
        private Paint mPaint;
        private int mCount = 0;

        RectView(Context context, int color) {
            super(context);
	    mPaint = new Paint();
	    mPaint.setColor(color);
	    mPaint.setAntiAlias(true);
        }

        @Override protected void onDraw(Canvas canvas) {
            canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
            mCount++;
        }

        public int getCount() { return mCount; }
        public void resetCount() { mCount = 0; }
    }
}
