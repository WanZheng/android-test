package drag.cos;

import android.content.Context;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Canvas;

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