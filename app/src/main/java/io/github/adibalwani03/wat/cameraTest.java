package io.github.adibalwani03.wat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;


public class cameraTest extends Activity {
	CameraPreview cameraPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
		cameraPreview = new CameraPreview(this, WAT.mCamera);
	    FrameLayout frameLayout = (FrameLayout) findViewById(R.id.camera_preview);
	    frameLayout.addView(cameraPreview);
    }

}
