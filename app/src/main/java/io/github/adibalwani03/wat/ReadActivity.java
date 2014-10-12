package io.github.adibalwani03.wat;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Aditya on 10/11/2014.
 */
public class ReadActivity extends Activity {

	private TextView tv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read);

		tv = (TextView) findViewById(R.id.textView1);

		String id = getIntent().getStringExtra("id");
		String[] projection = {"_id", "address", "date", "body"};
		String selection = "_id = ?";
		String[] selectionArgs = {id};
		Cursor c = getContentResolver().query(WAT.INBOX_URI, projection,
				selection, selectionArgs, null);
		if (c.moveToFirst()) {
			setTitle(c.getString(c.getColumnIndex("address")));
			tv.setText(c.getString(c.getColumnIndex("body")));
		}
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
		if(body.trim().equalsIgnoreCase("")) {
			manager.sendTextMessage(to, null, body, null, null);
			Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT);
		}
		else {
			Toast.makeText(getApplicationContext(), "Invalid text", Toast.LENGTH_SHORT);
		}
		finish();
	}

}