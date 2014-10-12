package io.github.adibalwani03.wat;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

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
	}

}