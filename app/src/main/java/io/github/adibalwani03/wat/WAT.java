package io.github.adibalwani03.wat;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

/**
 * Created by Aditya on 10/11/2014.
 */
public class WAT extends Application {
	public static SharedPreferences sp;
	public static final Uri INBOX_URI = Uri.parse("content://sms/inbox");

	@Override
	public void onCreate() {
		super.onCreate();

		PreferenceManager.setDefaultValues(this, R.xml.settings, false);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
	}
}
