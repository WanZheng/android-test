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

import java.util.ArrayList;

public class DragController {
    private final static String TAG = "drag";

    private View mHostedView;
    private View mDragItem;
    private HostView mHost;
    private DragInfo mDragInfo;
    private ArrayList<DropTarget> mDropTargets = new ArrayList<DropTarget>();
    private DropTarget mCurrentDropTarget;
    private DragSource mDragSource;

    /* Public */
    public DragController (Context context) {
	mHost = new HostView(context);
    }

    public void startDrag(DragSource source, Rect rect, DragInfo dragInfo) throws Exception {
	if (mDragSource != null) {
	    throw new Exception("Is draging already, mDragSource="+mDragSource);
	}

	mDragSource = source;

	mDragInfo = dragInfo;
	mCurrentDropTarget = null; // TODO: find a drop target

	// TODO: create item according to dragInfo
	mDragItem = new View(mHost.getContext());
	mDragItem.setBackgroundColor(Color.argb(0x80, 0xff, 0xff, 0x00));
	mDragItem.setLayoutParams(new AbsoluteLayout.LayoutParams(50, 50, -50, -50));
	mHost.addView(mDragItem);

	mHost.setDisallowInterceptTouchEvent(false);

	Log.d(TAG, "startDrag(source="+mDragSource+")");
    }

    public void cancelDrag() {
	// TODO
    }

    public void setHostedView(View hostedView) throws Exception{
	if (mHostedView != null) {
	    throw new Exception("has a hosted view already");
	}
	mHostedView = hostedView;

	mHost.addView(hostedView);
	ViewGroup.LayoutParams layoutParams = hostedView.getLayoutParams();
	layoutParams.width = ViewGroup.LayoutParams.FILL_PARENT;
	layoutParams.height = ViewGroup.LayoutParams.FILL_PARENT;
	hostedView.setLayoutParams(layoutParams);
    }

    public void addDropTarget(DropTarget target) {
	mDropTargets.add(target);
    }

    public ViewGroup getHostView() {
	return mHost;
    }

    /* Private */
    private class HostView extends AbsoluteLayout {
	private boolean mDisallowInterceptTouchEvent = true;

	public HostView(Context context) {
	    super(context);
	}

	public void setDisallowInterceptTouchEvent(boolean disallowIntercept) {
	    mDisallowInterceptTouchEvent = disallowIntercept;
	}

	@Override public boolean onInterceptTouchEvent (MotionEvent event){
	    // Log.d(TAG, "ViewGroup::onInterceptTouchEvent("+event+")");
	    if (mDisallowInterceptTouchEvent) {
		return false;
	    }else{
		onTouchEvent(event); /* XXX: this event will be ignored if don't call onTouchEvent() */
		return true;
	    }
	}

	@Override public boolean onTouchEvent(MotionEvent event) {
	    // Log.d(TAG, "ViewGroup::onTouchEvent("+event+")");
	    if (! mDisallowInterceptTouchEvent) {
		int [] dropCoordinates = new int[2];
		int x = (int)event.getX();
		int y = (int)event.getY();

		DropTarget target = findDropTarget(x, y, dropCoordinates);
		Rect rect = new Rect(dropCoordinates[0], dropCoordinates[1], 50+dropCoordinates[0], 25+dropCoordinates[1]);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		    break;
		case MotionEvent.ACTION_MOVE:
		    if (target != null && mCurrentDropTarget != target) {
			enterDropTarget(target, rect);
		    }

		    mDragItem.setLayoutParams(new AbsoluteLayout.LayoutParams(50, 50, (int)event.getX(), (int)event.getY()));
		    if (target != null) {
			target.onDragOver(rect, mDragInfo);
		    }
		    break;
		case MotionEvent.ACTION_UP:
		    endDrag(rect.left, rect.top, target);
		    break;
		case MotionEvent.ACTION_CANCEL:
		    Log.d(TAG, "cancel");
		    break;
		}
		return true;
	    }else{
		return false;
	    }
	}
    }

    private void enterDropTarget(DropTarget target, Rect rect) {
	if (mCurrentDropTarget != null) {
	    mCurrentDropTarget.onDragExit(mDragInfo);
	}

	target.onDragEnter(rect, mDragInfo);
	mCurrentDropTarget = target;
    }

    private void endDrag(int x, int y, DropTarget target) {
	if (mDragSource == null){
	    Log.e(TAG, "is not draging");
	}else{
	    Rect rect = new Rect(x, y, x+25, y+50);

	    if (target != null) {
		target.onDragExit(mDragInfo);
		if (target.acceptDrop(rect, mDragInfo)) {
		    target.onDrop(rect, mDragInfo);
		    mDragSource.onDropCompleted(true);
		}else{
		    mDragSource.onDropCompleted(false);
		}
	    }

	    mHost.removeView(mDragItem);

	    mHost.setDisallowInterceptTouchEvent(true);
	    mDragSource = null;
	    Log.d(TAG, "set source to " + mDragSource);
	}
    }

    private DropTarget findDropTarget(int x, int y, int[] dropCoordinates) {
	int count = mDropTargets.size();
	for (int i=count-1; i>=0; i--){
	    DropTarget target = mDropTargets.get(i);
	    Rect r = new Rect();
	    target.getHitRect(r);
	    target.getLocationOnScreen(dropCoordinates);
	    Log.d(TAG, "["+i+"] x,y="+x+","+y+" rect="+r+", cor=("+dropCoordinates[0]+","+dropCoordinates[1]+")");

	    // r.offset(dropCoordinates[0] - target.getLeft(), dropCoordinates[1] - target.getTop());
	    if (r.contains(x, y)) {
		/*
		dropCoordinates[0] = x - dropCoordinates[0];
		dropCoordinates[1] = y - dropCoordinates[1];
		*/
		dropCoordinates[0] = x - r.left;
		dropCoordinates[1] = y - r.top;

		Log.d(TAG, "find target "+i+", cor on target="+dropCoordinates[0]+","+dropCoordinates[1]);
		return target;
	    }
	}
	return null;
    }
}