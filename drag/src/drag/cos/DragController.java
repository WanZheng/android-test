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
    private AbsoluteLayout mHost;
    private DragInfo mDragInfo;
    private ArrayList<DropTarget> mDropTargets = new ArrayList<DropTarget>();
    private DropTarget mCurrentDropTarget;
    private DragSource mDragSource;

    private OnTouchListener mOnTouchListener = new OnTouchListener();

    /* Public */
    public DragController (Context context) {
	mHost = new AbsoluteLayout(context);
    }

    public void startDrag(DragSource source, Rect rect, DragInfo dragInfo) throws Exception {
	if (mDragSource != null) {
	    throw new Exception("Is draging already, mDragSource="+mDragSource);
	}

	mDragSource = source;

	mDragInfo = dragInfo;
	mCurrentDropTarget = null; // TODO: find a drop target

	// TODO: create item according to dragInfo
	mDragItem = new RectView(mHost.getContext(), Color.argb(0x80, 0xff, 0xff, 0x00));
	mDragItem.setLayoutParams(new AbsoluteLayout.LayoutParams(20, 30, 50, 100));
	mHost.addView(mDragItem);

	mHost.setOnTouchListener(mOnTouchListener);
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
    }

    public void addDropTarget(DropTarget target) {
	mDropTargets.add(target);
    }

    public ViewGroup getHostView() {
	return mHost;
    }

    /* Private */
    private class OnTouchListener implements View.OnTouchListener {
	@Override public boolean onTouch(View v, MotionEvent event) {
	    int x = (int)event.getX();
	    int y = (int)event.getY();

	    DropTarget target = findDropTarget(x, y);
	    Rect rect = new Rect(x, y, 50+x, 25+y);

	    switch (event.getAction()) {
	    case MotionEvent.ACTION_DOWN:
		Log.d(TAG, "down");
		break;
	    case MotionEvent.ACTION_MOVE:
		if (mCurrentDropTarget != target) {
		    enterDropTarget(target, rect);
		}

		mDragItem.setLayoutParams(new AbsoluteLayout.LayoutParams(20, 30, (int)event.getX(), (int)event.getY()));
		target.onDragOver(rect, mDragInfo);
		break;
	    case MotionEvent.ACTION_UP:
		Log.d(TAG, "up");
		endDrop(x, y);
		break;
	    case MotionEvent.ACTION_CANCEL:
		Log.d(TAG, "cancel");
		break;
	    }
	    return true;
	}
    }

    private void enterDropTarget(DropTarget target, Rect rect) {
	if (mCurrentDropTarget != null) {
	    mCurrentDropTarget.onDragExit(mDragInfo);
	}

	target.onDragEnter(rect, mDragInfo);
	mCurrentDropTarget = target;
    }

    private void endDrop(int x, int y) {
	if (mDragSource == null){
	    Log.e(TAG, "is not draging");
	}else{
	    Rect rect = new Rect(x, y, x+25, y+50);

	    DropTarget target = findDropTarget(x, y);
	    if (target != null) {
		target.onDragExit(mDragInfo);
		if (target.acceptDrop(rect, mDragInfo)) {
		    target.onDrop(rect, mDragInfo);
		    mDragSource.onDropCompleted(true);
		}else{
		    mDragSource.onDropCompleted(false);
		}
	    }

	    mHost.setOnTouchListener(null);
	    mDragSource = null;
	    Log.d(TAG, "set source to " + mDragSource);
	}
    }

    private DropTarget findDropTarget(int x, int y) {
	// TODO
	return mDropTargets.get(0);
    }
}