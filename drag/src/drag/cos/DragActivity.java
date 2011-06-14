package drag.cos;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.widget.TextView;
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
import java.util.Random;

public class DragActivity extends Activity
{
    private final static String TAG = "drag";

    private ContainerView mDesktop;
    private ContainerView mToolbar;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	View view;
	ViewGroup.LayoutParams layoutParams;
	TextView background;

        super.onCreate(savedInstanceState);

	DragController dragController = new DragController(this);

	LinearLayout layout = new LinearLayout(this);
	layout.setOrientation(LinearLayout.VERTICAL);

	/*
	 * top bar
	 */
	TextView top = new TextView(this);
	top.setText(R.string.top);
	top.setGravity(Gravity.CENTER);
	layout.addView(top);
	layoutParams = top.getLayoutParams();
	layoutParams.height = 50;
	top.setLayoutParams(layoutParams);

	/*
	 * desktop
	 */
	mDesktop = new ContainerView(this);
	layout.addView(mDesktop);
	layoutParams = mDesktop.getLayoutParams();
	layoutParams.width = ViewGroup.LayoutParams.FILL_PARENT;
	layoutParams.height = 300;
	mDesktop.setLayoutParams(layoutParams);

	/* background */
	background = new TextView(this);
	mDesktop.addView(background);
	layoutParams = background.getLayoutParams();
	layoutParams.width = ViewGroup.LayoutParams.FILL_PARENT;
	layoutParams.height = 300;
	background.setLayoutParams(layoutParams);
	background.setText(R.string.desktop);
	background.setGravity(Gravity.CENTER);
	background.setBackgroundColor(Color.WHITE);

	/* item 1 */
	view = new View(this);
	view.setBackgroundColor(Color.BLUE);
	view.setLayoutParams(new AbsoluteLayout.LayoutParams(100, 200, 10, 20));
	view.setOnTouchListener(mDesktop.getOnTouchListener());
        mDesktop.addView(view);

	/* item 2 */
	view = new View(this);
	view.setBackgroundColor(Color.RED);
	view.setLayoutParams(new AbsoluteLayout.LayoutParams(50, 100, 20, 250));
	view.setOnTouchListener(mDesktop.getOnTouchListener());
	mDesktop.addView(view);

	/* drag related things */
	mDesktop.installDragController(dragController);

	/*
	 * Toolbar
	 */
	mToolbar = new ContainerView(this);
	layout.addView(mToolbar);
	layoutParams = mToolbar.getLayoutParams();
	layoutParams.height = 100;
	mToolbar.setLayoutParams(layoutParams);

	/* background */
	background = new TextView(this);
	mToolbar.addView(background);
	layoutParams = background.getLayoutParams();
	layoutParams.width = ViewGroup.LayoutParams.FILL_PARENT;
	layoutParams.height = 100;
	background.setLayoutParams(layoutParams);
	background.setText(R.string.toolbar);
	background.setGravity(Gravity.CENTER);
	background.setBackgroundColor(Color.GRAY);

	/* item 1 */
	view = new View(this);
	view.setBackgroundColor(Color.BLUE);
	view.setLayoutParams(new AbsoluteLayout.LayoutParams(5, 10, 10, 20));
	view.setOnTouchListener(mToolbar.getOnTouchListener());
        mToolbar.addView(view);

	/* item 2 */
	view = new View(this);
	view.setBackgroundColor(Color.RED);
	view.setLayoutParams(new AbsoluteLayout.LayoutParams(50, 100, 50, 30));
	view.setOnTouchListener(mToolbar.getOnTouchListener());
	mToolbar.addView(view);

	/* drag related things */
	mToolbar.installDragController(dragController);

	try {
	    dragController.setHostedView(layout);
	} catch (Exception e){
	    Log.d(TAG, "" + e);
	}
	setContentView(dragController.getHostView());
    }
}

class ContainerView extends AbsoluteLayout {
    private final static String TAG = "drag";
    private View.OnTouchListener mOnTouchListener;
    private DragSource mDragSource;
    private DropTarget mDropTarget;
    private DragController mDragController;
    private static Random s_rand = new Random(108);
    private int mColor = Color.rgb(s_rand.nextInt(256), s_rand.nextInt(256), s_rand.nextInt(256));

    public ContainerView(Context context) {
	super(context);
    }

    void setDragController(DragController controller) {
	mDragController = controller;
    }

    public View.OnTouchListener getOnTouchListener() {
	if (mOnTouchListener == null) {
	    mOnTouchListener = new View.OnTouchListener() {
		    @Override public boolean onTouch(View v, MotionEvent event) {
			Log.d(TAG, "desktopView.onTouch("+event.getAction()+")");

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
			    try {
				mDragController.startDrag(getDragSource(), null, null);
				removeView(v);
			    } catch (Exception e) {
				Log.d(TAG, "failed to startDrag() "+e);
			    }
			}
			return true;
		    }
		};
	}
	return mOnTouchListener;
    }

    DragSource getDragSource() {
	if (mDragSource == null) {
	    mDragSource = new DragSource() {
		    @Override public void onDropCompleted(boolean success) {
			Log.d(TAG, "onDropCompleted("+success+")");
		    }
		};
	}
	return mDragSource;
    }

    DropTarget getDropTarget() {
	if (mDropTarget == null) {
	    mDropTarget = new DropTarget() {
		    private View mDragItem;

		    @Override public void onDragEnter(Rect rect, DragInfo info) {
			TextView item = new TextView(getContext());
			item.setText(R.string.item_on_drag_source);

			mDragItem = item;
			mDragItem.setLayoutParams(new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, rect.left, rect.top));
			mDragItem.setBackgroundColor(mColor);
			addView(mDragItem);
		    }

		    @Override public void onDragOver(Rect rect, DragInfo info) {
			mDragItem.setLayoutParams(new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, rect.left, rect.top));
		    }

		    @Override public void onDragExit(DragInfo info) {
			removeView(mDragItem);
			mDragItem = null;
		    }

		    @Override public boolean acceptDrop(Rect rect, DragInfo info) {
			return true;
		    }

		    @Override public void onDrop(Rect rect, DragInfo info) {
			View view = new View(getContext());
			view.setBackgroundColor(mColor);
			view.setLayoutParams(new AbsoluteLayout.LayoutParams(rect.width(), rect.height(), rect.left, rect.top));
			view.setOnTouchListener(getOnTouchListener());
			addView(view);
		    }

		    @Override public void getLocationOnScreen(int[] loc) {
			ContainerView.this.getLocationOnScreen(loc);
		    }

		    @Override public void getHitRect(Rect outRect) {
			ContainerView.this.getHitRect(outRect);
		    }

		    @Override public int getLeft(){
			return ContainerView.this.getLeft();
		    }

		    @Override public int getTop(){
			return ContainerView.this.getTop();
		    }
		};
	}
	return mDropTarget;
    }

    void installDragController(DragController controller) {
	setDragController(controller);
	controller.addDropTarget(getDropTarget());
    }
}