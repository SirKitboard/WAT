package io.github.adibalwani03.wat;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Aditya on 10/11/2014.
 */
public class MainActivity extends ListActivity {
	private Camera mCamera;
	private CameraPreview cameraPreview;
	private Typeface font;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mCamera = getCameraInstance();
		cameraPreview = new CameraPreview(this, mCamera);
		//FrameLayout frameLayout = (FrameLayout) findViewById(R.id.camera_preview1);
		//frameLayout.addView(cameraPreview);
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
	public static Camera getCameraInstance(){
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return c; // returns null if camera is unavailable
	}
}

