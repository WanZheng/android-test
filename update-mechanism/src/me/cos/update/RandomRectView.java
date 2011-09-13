package me.cos.update;

import java.util.Random;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Color;

public class RandomRectView extends View {
    private static Random RND = new Random();

    public RandomRectView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    @Override protected void onDraw(Canvas canvas) {
	canvas.drawColor(Color.argb(0xff, RND.nextInt() & 0xff, RND.nextInt() & 0xff, RND.nextInt() & 0xff), PorterDuff.Mode.SRC);
    }
}