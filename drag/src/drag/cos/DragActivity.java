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

public class DragActivity extends Activity
{
    private final static String TAG = "drag";

    private DesktopView mDesktopView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	final DragController dragController = new DragController(this);

	mDesktopView = new DesktopView(this);

        RectView view = new RectView(this, Color.BLUE);
	view.setLayoutParams(new AbsoluteLayout.LayoutParams(100, 200, 10, 20));
        mDesktopView.addView(view);

	view = new RectView(this, Color.RED);
	view.setLayoutParams(new AbsoluteLayout.LayoutParams(50, 100, 20, 250));
	mDesktopView.addView(view);

	try {
	    dragController.setHostedView(mDesktopView);
	} catch (Exception e){
	    Log.d(TAG, "" + e);
	}

	setContentView(dragController.getHostView());

	dragController.addDropTarget(mDesktopView.getDropTarget());

	mDesktopView.setOnTouchListener(
					new View.OnTouchListener() {
					    @Override public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
						    try {
							dragController.startDrag(mDesktopView.getDragSource(), null, null);
						    } catch (Exception e) {
							Log.d(TAG, "failed to startDrag() "+e);
						    }
						}
						Log.d(TAG, "desktopView.onTouch()");
						return true;
					    }
					}
					);
    }
}

class DesktopView extends AbsoluteLayout {
    private final static String TAG = "drag";

    public DesktopView(Context context) {
	super(context);
    }

    DragSource getDragSource() {
	return new InnerDragSource();
    }

    DropTarget getDropTarget() {
	return new InnerDropTarget();
    }

    class InnerDragSource implements DragSource {
	@Override public void onDropCompleted(boolean success) {
	    Log.d(TAG, "onDropCompleted("+success+")");
	}
    }

    class InnerDropTarget implements DropTarget {
	private RectView mDragItem;

	@Override public void onDragEnter(Rect rect, DragInfo info) {
	    mDragItem = new RectView(getContext(), Color.RED);
	    mDragItem.setLayoutParams(new AbsoluteLayout.LayoutParams(rect.width(), rect.height(), rect.left, rect.top));
	    addView(mDragItem);
	}

	@Override public void onDragOver(Rect rect, DragInfo info) {
	    mDragItem.setLayoutParams(new AbsoluteLayout.LayoutParams(rect.width(), rect.height(), rect.left, rect.top));
	}

	@Override public void onDragExit(DragInfo info) {
	    removeView(mDragItem);
	    mDragItem = null;
	}

	@Override public boolean acceptDrop(Rect rect, DragInfo info) {
	    return true;
	}

	@Override public void onDrop(Rect rect, DragInfo info) {
	    RectView view = new RectView(getContext(), Color.GREEN);
	    view.setLayoutParams(new AbsoluteLayout.LayoutParams(rect.width(), rect.height(), rect.left, rect.top));
	    addView(view);
	}

	@Override public void getLocationOnScreen(int[] loc) {
	    DesktopView.this.getLocationOnScreen(loc);
	}
    }
}