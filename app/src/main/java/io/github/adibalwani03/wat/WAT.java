package io.github.adibalwani03.wat;

import android.app.Application;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.net.Uri;
import android.preference.PreferenceManager;

/**
 * Created by Aditya on 10/11/2014.
 */
public class WAT extends Application {
	public static SharedPreferences sp;
	public static final Uri INBOX_URI = Uri.parse("content://sms/inbox");
	public static Camera mCamera;

	@Override
	public void onCreate() {
		super.onCreate();
		mCamera = getCameraInstance();
		PreferenceManager.setDefaultValues(this, R.xml.settings, false);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
	}

	public static Camera getCameraInstance(){
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
		}
		catch (Exception e){
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}
}
