package io.github.adibalwani03.wat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Aditya on 10/11/2014.
 */
public class ComposeActivity extends Activity {

	private static final String ACTION_SENT = "com.appsrox.smsxp.SENT";
	private static final int DIALOG_SENDTO = 1;

	private EditText num;
	private ImageButton ib3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		setTitle("New Message");
		num = (EditText) findViewById(R.id.number);
		num.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		//et1 = (EditText) findViewById(R.id.editText1);
		CameraPreview cameraPreview = new CameraPreview(this, WAT.mCamera);
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.camera_preview3);
		frameLayout.addView(cameraPreview);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void send(View v) {
		EditText et1 = (EditText)findViewById(R.id.messagebody);
		SmsManager manager = SmsManager.getDefault();
		String body = et1.getText().toString();
		if(!body.trim().equalsIgnoreCase("")) {
			try {
				manager.sendTextMessage(num.getText().toString(), null, body, null, null);
				Toast.makeText(getApplicationContext(), "Sending Message", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Invalid text", Toast.LENGTH_SHORT).show();
			}
		}
		else {

		}
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash, menu);

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

}
