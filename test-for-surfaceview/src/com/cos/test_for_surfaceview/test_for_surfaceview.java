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
import android.content.Context;
import android.util.Log;

public class test_for_surfaceview extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	FrameLayout layout = new FrameLayout(this);
	View view = new RectView(this);

	layout.addView(view);
	FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
	Log.d("COS", "layoutParams="+layoutParams.width+"x"+layoutParams.height);
	layoutParams.width = 100;
	layoutParams.height = 200;
	layoutParams.gravity = Gravity.CENTER;
	view.setLayoutParams(layoutParams);

        setContentView(layout);
    }

    class RectView extends View {
	private Paint paint = new Paint();
	{ paint.setColor(Color.RED); }

	RectView(Context context) {
	    super(context);
	}

	@Override protected void onDraw(Canvas canvas) {
	    canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
	}
    }
}
