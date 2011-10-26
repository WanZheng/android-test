package me.cos.update;

import java.util.Random;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Color;
import android.util.Log;

public class RandomRectView extends View {
    public static Random RND = new Random();

    public RandomRectView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    @Override protected void onDraw(Canvas canvas) {
	Log.d(Config.TAG, "RandomRectView.onDraw: id="+getId()+", clipBounds="+canvas.getClipBounds() + ", isOpaque="+isOpaque());
	canvas.drawColor(Color.argb(0xff, RND.nextInt() & 0xff, RND.nextInt() & 0xff, RND.nextInt() & 0xff), PorterDuff.Mode.SRC);
    }
}