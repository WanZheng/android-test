package me.cos.update;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.util.Log;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Color;

public class MyRelativeLayout extends RelativeLayout {
    public MyRelativeLayout(Context context, AttributeSet attrs) {
	super(context, attrs);
	Log.d(Config.TAG, "MyRelativeLayout.construct()");
    }

    @Override protected void onDraw(Canvas canvas) {
	Log.d(Config.TAG, "MyRelativeLayout.onDraw: id="+getId()+", clipBounds="+canvas.getClipBounds() + ", isOpaque="+isOpaque());
	canvas.drawColor(Color.argb(0xff, RandomRectView.RND.nextInt() & 0xff, RandomRectView.RND.nextInt() & 0xff, RandomRectView.RND.nextInt() & 0xff), PorterDuff.Mode.SRC);
    }
}