package io.github.adibalwani03.wat;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Aditya on 10/11/2014.
 */
public class MainActivity extends ListActivity {
	private CameraPreview cameraPreview;
	private Typeface font;
	private final String TAG = "MainActivity";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cameraPreview = new CameraPreview(this, WAT.mCamera);
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.camera_preview1);
		frameLayout.addView(cameraPreview);
		//font = Typeface.createFromAsset(getAssets(), "fonts/OpenSans.ttf");
	}

	@Override
	protected void onResume() {
		super.onResume();

		Cursor c = getContentResolver().query(WAT.INBOX_URI, null, null, null, null);
		startManagingCursor(c);

		String[] from = {"address", "date", "body"};
		int[] to = {R.id.textView1, R.id.textView2, R.id.textView3};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				this,
				R.layout.adapter_inbox,
				c,
				from,
				to);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(this, ReadActivity.class);
		intent.putExtra("id", String.valueOf(id));
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flashcompose, menu);

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
		else if (id == R.id.compose) {
			newMessage();
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
	public void newMessage() {
		Intent intent = new Intent(this,ComposeActivity.class );
		startActivity(intent);
	}
}

