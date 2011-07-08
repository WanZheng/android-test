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
import android.graphics.Color;
import java.util.List;
import java.util.ArrayList;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Message;

public class icon_loader extends Activity
{
    final static private String TAG = "icon_loader";
    final static private boolean DEBUG_LOADERS = true;
    final static private int CMD_NUMBER_PACKAGES = 1;
    final static private int CMD_NEW_ICON = 2;
    ArrayList<Drawable> mPendingIcons = new ArrayList();
    ArrayList<ImageView> mIcons = new ArrayList();
    int mIndex = 0;
    Handler mHandler;

    /** Called when the activity is first created. */
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mHandler = new Handler() {
            @Override public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CMD_NUMBER_PACKAGES:
                        Log.d(TAG, "get message CMD_NUMBER_PACKAGS("+msg.arg1+")");
                        AbsoluteLayout grid = (AbsoluteLayout) findViewById(R.id.grid);
                        Random r = new Random(128);

                        final int W = 5;
                        for (int i=0; i<msg.arg1; i++) {
                            ImageView image = new ImageView(icon_loader.this);
                            mIcons.add(image);

                            image.setLayoutParams(new AbsoluteLayout.LayoutParams(20, 20, 22 * (i % W), 22 * (i / W)));
                            grid.addView(image);
                            image.setBackgroundColor(Color.RED);
                        }
                        break;
                    case CMD_NEW_ICON:
                        Log.d(TAG, "receive CMD_NEW_ICON");
                        ArrayList<Drawable> copy;
                        synchronized(mPendingIcons) {
                            copy = new ArrayList(mPendingIcons);
                            mPendingIcons.clear();
                        }

                        Log.d(TAG, "# pending icons = " + copy.size());
                        for (int i=0; i<copy.size(); i++) {
                            ImageView image = mIcons.get(mIndex++);
                            image.setImageDrawable(copy.get(i));
                        }
                        copy.clear();

                        /*
                           try{
                           Thread.sleep(2 * 100);
                           }catch(Exception e){
                           }
                           */

                        break;
                    default:
                        break;
                }
            }
        };

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

                Message msg = mHandler.obtainMessage(CMD_NUMBER_PACKAGES, apps.size(), -1);
                mHandler.sendMessage(msg);
                Log.d(TAG, "send message CMD_NUMBER_PACKAGS");

                /*
                   for (int i=0; i<apps.size(); i++) {
                   ResolveInfo info = apps.get(i);
                   Log.d(TAG, "["+i+"] "+info.activityInfo.applicationInfo.packageName+" "+info.activityInfo.name);
                   }
                   t1 = SystemClock.uptimeMillis(); Log.d(TAG, "get name took " + (t1 - t0) + "ms");  t0 = t1;
                   */

                try{
                    Thread.sleep(2 * 1000);
                }catch(Exception e){
                }
                t0 = SystemClock.uptimeMillis();

                for (int i=0; i<apps.size(); i++) {
                    ResolveInfo info = apps.get(i);
                    Drawable icon = info.activityInfo.loadIcon(packageManager);

                    synchronized(mPendingIcons) {
                        mPendingIcons.add(icon);
                    }
                    msg = mHandler.obtainMessage(CMD_NEW_ICON);
                    mHandler.sendMessage(msg);
                    Log.d(TAG, "send CMD_NEW_ICON "+i);

                    /*
                       try{
                       Thread.sleep(2 * 1000);
                       }catch (Exception e){
                       }
                       */
                }
                t1 = SystemClock.uptimeMillis(); Log.d(TAG, "get icon took " + (t1 - t0) + "ms");  t0 = t1;
            }
        };

        r.start();
    }
}
