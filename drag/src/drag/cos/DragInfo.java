package drag.cos;

import android.os.Bundle;
import android.graphics.Bitmap;

public class DragInfo {
    Bundle mBundle = new Bundle();
    Bitmap mBitmap;

    Bitmap getBitmap() {
	return mBitmap;
    }

    void putBitmap(Bitmap bitmap) {
	mBitmap = bitmap;
    }

    String getString(String key) {
	return mBundle.getString(key);
    }

    void putString(String key, String value) {
	mBundle.putString(key, value);
    }
}