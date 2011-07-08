package icon_loader.com;

import java.util.Random;
import java.lang.Runnable;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.AbsoluteLayout;
import java.util.List;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

public class icon_loader extends Activity
{
    final static private String TAG = "icon_loader";
    final static private boolean DEBUG_LOADERS = true;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	Thread r = new Thread() {
		@Override public void run() {
		    try{
			Thread.sleep(2 * 1000);
		    }catch(Exception e){
		    }

		    Log.d(TAG, "now="+SystemClock.uptimeMillis());

		    final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		    final PackageManager packageManager = getPackageManager();
		    List<ResolveInfo> apps = null;

		    long t0 = SystemClock.uptimeMillis();
		    apps = packageManager.queryIntentActivities(mainIntent, 0);
		    long t1;
		    t1 = SystemClock.uptimeMillis(); Log.d(TAG, "queryIntentActivities took " + (t1 - t0) + "ms");  t0 = t1;

		    Log.d(TAG, "# of apps = " + apps.size());
		    for (int i=0; i<apps.size(); i++) {
			ResolveInfo info = apps.get(i);
			Log.d(TAG, "["+i+"] "+info.activityInfo.applicationInfo.packageName+" "+info.activityInfo.name);
		    }
		    t1 = SystemClock.uptimeMillis(); Log.d(TAG, "get name took " + (t1 - t0) + "ms");  t0 = t1;

		    AbsoluteLayout grid = (AbsoluteLayout) findViewById(R.id.grid);
		    Random r = new Random(128);

		    /*
		    for (int i=0; i<apps.size(); i++) {
			ImageView image = new ImageView();
			image.setLayoutParams(new AbsoluteLayout.LayoutParams(90 + r.nextInt(20), 90 + r.nextInt(20), r.nextInt(100), r.nextInt(100)));
			grid.addView(image);
		    }
		    */


		    /*
		    for (int i=0; i<apps.size(); i++) {
			ResolveInfo info = apps.get(i);
			Drawable icon = info.activityInfo.loadIcon(packageManager);

			ImageView image = new ImageView(icon_loader.this);
			image.setImageDrawable(icon);

			image.setLayoutParams(new AbsoluteLayout.LayoutParams(90 + r.nextInt(20), 90 + r.nextInt(20), r.nextInt(100), r.nextInt(100)));
			grid.addView(image);

			try{
			    Thread.sleep(2 * 1000);
			}catch (Exception e){
			}
		    }
		    t1 = SystemClock.uptimeMillis(); Log.d(TAG, "get icon took " + (t1 - t0) + "ms");  t0 = t1;
		    */
		}
	    };
	/*
	Handler handler = new Handler();
	handler.postAtTime(r, SystemClock.uptimeMillis() + 3 * 1000);
	*/
	r.start();
    }
}