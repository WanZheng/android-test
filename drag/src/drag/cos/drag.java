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
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.net.Uri;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.content.Context;
import android.util.Log;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation;
import android.view.MotionEvent;

public class drag extends Activity
{
    private String TAG = "drag";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	DragController dragController = new DragController(this);
        LinearLayout layout = new LinearLayout(this);
	layout.setOrientation(LinearLayout.VERTICAL);

        RectView view = new RectView(this, Color.BLUE);
        layout.addView(view, 300, 200);

	view = new RectView(this, Color.RED);
	layout.addView(view, 200, 300);

	try {
	    dragController.setHostedView(layout);
	} catch (Exception e){
	    Log.d(TAG, "" + e);
	}

	setContentView(dragController.getHostView());

	try {
	    dragController.startDrag(null, null, null); // rect, dragInfo);
	} catch (Exception e) {
	    Log.d(TAG, "failed to startDrag()");
	}
    }
}
