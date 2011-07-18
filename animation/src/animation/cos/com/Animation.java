package animation.cos.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.Context;
import android.widget.AbsoluteLayout;
import android.view.animation.TranslateAnimation;
// import android.view.animation.Animation;
import android.util.Log;

public class Animation extends Activity
{
    final static String TAG="Animation";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	View item = new MyView(this);
	item.setBackgroundColor(Color.RED);
	item.setLayoutParams(new AbsoluteLayout.LayoutParams(100, 100, 100, 120));
	AbsoluteLayout layout = (AbsoluteLayout) findViewById(R.id.layout);
	layout.addView(item);

	TranslateAnimation a = new TranslateAnimation(0.0f, 0.0f, 100.0f, 100.0f);
	a.setDuration(20*1000);
	// a.setRepeatCount(Animation.INFINITE);
	item.startAnimation(a);
    }

    class MyView extends View
    {
	MyView(Context context) {
	    super(context);
	}

	@Override protected void onDraw(Canvas canvas) {
	    Log.d(TAG, "onDraw()");
	    super.onDraw(canvas);
	    /*
	      try{                                                                                              
	      Thread.sleep(1 * 1000);                                                                            
	      }catch(Exception e){                                                                              
	      } 
	    */    
	}
    }
}

