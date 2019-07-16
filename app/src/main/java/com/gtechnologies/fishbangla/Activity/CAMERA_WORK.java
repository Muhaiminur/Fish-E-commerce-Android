package com.gtechnologies.fishbangla.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;
import com.karan.churi.PermissionManager.PermissionManager;
/*import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Mode;
import com.otaliastudios.cameraview.VideoResult;*/

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CAMERA_WORK extends AppCompatActivity {

    /*@BindView(R.id.camera_view)
    CameraView cameraView;*/

    PermissionManager permissionManager;
    Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera__work);
        ButterKnife.bind(this);
        utility = new Utility(CAMERA_WORK.this);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.step3_video_string));
            permissionManager = new PermissionManager() {
            };
            permissionManager.checkAndRequestPermissions(this);

            /*cameraView.setLifecycleOwner(this);
            cameraView.addCameraListener(new CameraListener() {
                @Override
                public void onVideoTaken(VideoResult result) {
                    // Video was taken!
                    // Use result.getFile() to access a file holding
                    // the recorded video.
                    Log.d("Check 3", result.getFile().getAbsolutePath());
                    utility.showToast("Recording Ended");
                    finish();
                }
            });
            start_capturing();*/
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    /*public void start_capturing() {
        try {
            Log.d("Recording", "start");
            if (cameraView.getMode() == Mode.PICTURE) {
                utility.showToast("Can't record HQ videos while in PICTURE mode.");
                return;
            }
            if (cameraView.isTakingPicture() || cameraView.isTakingVideo()) return;
            utility.showToast("Recording for 10 seconds");
            cameraView.takeVideo(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "video.mp4"), 10000);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        permissionManager.checkResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }*/
}
