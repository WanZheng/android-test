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
    private String TAG = "drag";

    private View mHostedView;
    private View mDragItem;
    private AbsoluteLayout mHost;
    private DragInfo mDragInfo;
    private ArrayList<DropTarget> mDropTargets = new ArrayList<DropTarget>();
    private DragSource mDragSource;

    private OnTouchListener mOnTouchListener = new OnTouchListener();

    /* Public */
    public DragController (Context context) {
	mHost = new AbsoluteLayout(context);
    }

    public void startDrag(DragSource source, Rect rect, DragInfo dragInfo) throws Exception {
	if (mDragSource != null) {
	    throw new Exception("Is draging already");
	}

	mDragInfo = dragInfo;

	// TODO: create item according to dragInfo
	mDragItem = new RectView(mHost.getContext(), Color.YELLOW);
	mDragItem.setLayoutParams(new AbsoluteLayout.LayoutParams(20, 30, 50, 100));
	mHost.addView(mDragItem);

	mHost.setOnTouchListener(mOnTouchListener);
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
	    switch (event.getAction()) {
	    case MotionEvent.ACTION_DOWN:
		Log.d(TAG, "down");
		break;
	    case MotionEvent.ACTION_MOVE:
		Log.d(TAG, "move");
		mDragItem.setLayoutParams(new AbsoluteLayout.LayoutParams(20, 30, (int)event.getX(), (int)event.getY()));
		break;
	    case MotionEvent.ACTION_UP:
		Log.d(TAG, "up");
		break;
	    case MotionEvent.ACTION_CANCEL:
		Log.d(TAG, "cancel");
		break;
	    }
	    return true;
	}
    }

    private boolean drop(int x, int y) {
	Rect rect = null; // TODO

	DropTarget target = findDropTarget(x, y);
	if (target != null) {
	    target.onDragExit(mDragInfo);
	    if (target.acceptDrop(rect, mDragInfo)) {
		target.onDrop(rect, mDragInfo);
		mDragSource.onDropCompleted(true);
	    }else{
		mDragSource.onDropCompleted(false);
	    }
	    return true;
	}else{
	    return false;
	}
    }

    private void endDrop() throws Exception {
	if (mDragSource == null){
	    throw new Exception("Not draging right now");
	}
	mHost.setOnTouchListener(null);
	mDragSource = null;
    }

    private DropTarget findDropTarget(int x, int y) {
	// TODO
	return null;
    }
}