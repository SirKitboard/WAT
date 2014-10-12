package io.github.adibalwani03.wat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Aditya on 10/11/2014.
 */
public class ComposeActivity extends Activity {

	private static final String ACTION_SENT = "com.appsrox.smsxp.SENT";
	private static final int DIALOG_SENDTO = 1;

	private EditText et1;
	private ImageButton ib3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.compose);
		setTitle("New Message");

		//et1 = (EditText) findViewById(R.id.editText1);

	}

	/*public void onClick(View v) {
		switch (v.getId()) {
			case R.id.imageButton3:
				try {
					Intent sendIntent = new Intent(Intent.ACTION_VIEW);
					sendIntent.putExtra("sms_body", et1.getText().toString());
					sendIntent.setType("vnd.android-dir/mms-sms");
					startActivity(sendIntent);

				} catch (ActivityNotFoundException e) {
					showDialog(DIALOG_SENDTO);
				}
				break;
		}
	}*/

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG_SENDTO:
				final EditText et = new EditText(this);
				et.setInputType(EditorInfo.TYPE_CLASS_PHONE);
				return new AlertDialog.Builder(this)
						.setTitle("To")
						.setView(et)
						.setCancelable(true)
						.setPositiveButton("Send", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								String to = et.getText().toString().trim();
								sendSMS(to);
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						})
						.create();
		}
		return super.onCreateDialog(id);
	}

	private void sendSMS(String to) {
		SmsManager manager = SmsManager.getDefault() ;
		manager.sendTextMessage(to, null, et1.getText().toString(), null, null);
	}

}
