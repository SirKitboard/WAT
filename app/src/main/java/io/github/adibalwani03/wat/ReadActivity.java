package io.github.adibalwani03.wat;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Aditya on 10/11/2014.
 */
public class ReadActivity extends Activity {
	private final String TAG = "ReadActivity";
	private TextView tv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read);

		tv = (TextView) findViewById(R.id.textView4);

		String id = getIntent().getStringExtra("id");
		String[] projection = {"_id", "address", "date", "body"};
		String selection = "_id = ?";
		String[] selectionArgs = {id};
		Cursor c = getContentResolver().query(WAT.INBOX_URI, projection,
				selection, selectionArgs, null);
		if (c.moveToFirst()) {
			String name = getContactName(this, c.getString(c.getColumnIndex("address")));
			if(!name.equals(""))
				setTitle(name);
			else {
				setTitle(c.getString(c.getColumnIndex("address")));
			}
			tv.setText(c.getString(c.getColumnIndex("body")));
		}
		CameraPreview cameraPreview = new CameraPreview(this, WAT.mCamera);
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.camera_preview2);
		frameLayout.addView(cameraPreview);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void reply(View v) {
		EditText et1 = (EditText)findViewById(R.id.replybody);
		try {
			Intent sendIntent = new Intent(Intent.ACTION_VIEW);
			sendIntent.putExtra("sms_body", et1.getText().toString());
			sendIntent.setType("vnd.android-dir/mms-sms");
			sendIntent.putExtra("address", getTitle().toString() + ";");
			startActivity(sendIntent);
		} catch (ActivityNotFoundException e) {
			sendSMS(getTitle().toString());
		}

	}
	private void sendSMS(String to) {
		EditText et1 = (EditText)findViewById(R.id.replybody);
		SmsManager manager = SmsManager.getDefault();
		String body = et1.getText().toString();
		if(!body.trim().equalsIgnoreCase("")) {
			manager.sendTextMessage(to, null, body, null, null);
			Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(getApplicationContext(), "Invalid text", Toast.LENGTH_SHORT).show();
		}
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash2, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == R.id.flashOn) {
			turnFlash();
		}
		return super.onOptionsItemSelected(item);
	}
	public void turnFlash() {
		Camera.Parameters p = WAT.mCamera.getParameters();
		if(p.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH)) {
			p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			WAT.mCamera.setParameters(p);
		}
		else  {
			p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			WAT.mCamera.setParameters(p);
		}
	}
	private String getContactName(Context context, String number) {

		String name = "";

		// define the columns I want the query to return
		String[] projection = new String[] {
				ContactsContract.PhoneLookup.DISPLAY_NAME,
				ContactsContract.PhoneLookup._ID};

		// encode the phone number and build the filter URI
		Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

		// query time
		Cursor cursor = context.getContentResolver().query(contactUri, projection, null, null, null);

		if(cursor != null) {
			if (cursor.moveToFirst()) {
				name =      cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
				Log.v(TAG, "Started uploadcontactphoto: Contact Found @ " + number);
				Log.v(TAG, "Started uploadcontactphoto: Contact name  = " + name);
			} else {
				Log.v(TAG, "Contact Not Found @ " + number);
			}
			cursor.close();
		}
		return name;
	}


}