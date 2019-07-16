package com.gtechnologies.fishbangla.Fragment;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.fxn.stash.Stash;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;
import com.jmolsmobile.landscapevideocapture.VideoCaptureActivity;
import com.jmolsmobile.landscapevideocapture.configuration.CaptureConfiguration;
import com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Upload_Step3 extends Fragment {


    @BindView(R.id.upload_next3)
    Button uploadNext3;
    Unbinder unbinder;
    @BindView(R.id.video_upload)
    Button videoUpload;
    @BindView(R.id.video_preview)
    ImageView videoPreview;

    String filename;
    public Fragment_Upload_Step3() {
        // Required empty public constructor
    }


    View view;
    Utility utility;
    Context context;
    ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment__upload__step3, container, false);
        unbinder = ButterKnife.bind(this, view);
        pager = (ViewPager) getActivity().findViewById(R.id.upload_viewPager);
        try {
            context = getActivity();
            utility = new Utility(context);
            font_setup();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return view;
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                    });
            utility.setFonts_normal(
                    new View[]{
                            uploadNext3
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void Replace_Fragment(Fragment frag) {
        try {
            if (frag != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.home_area, frag);
                ft.addToBackStack(null);
                ft.commit();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.video_upload, R.id.upload_next3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.video_upload:
                //startActivity(new Intent(context, CAMERA_WORK.class));

                final CaptureConfiguration config = createCaptureConfiguration();
                final String filename = "FishBangla.mp4";
                final Intent intent = new Intent(context, VideoCaptureActivity.class);
                intent.putExtra(VideoCaptureActivity.EXTRA_CAPTURE_CONFIGURATION, config);
                intent.putExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME, filename);
                //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);
                //intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,200000);
                //intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,10000);
                startActivityForResult(intent, 101);
                break;
            case R.id.upload_next3:
                pager.setCurrentItem(3);
                break;
        }
    }

    private CaptureConfiguration createCaptureConfiguration() {
        final PredefinedCaptureConfigurations.CaptureResolution resolution = PredefinedCaptureConfigurations.CaptureResolution.RES_480P;
        final PredefinedCaptureConfigurations.CaptureQuality quality = PredefinedCaptureConfigurations.CaptureQuality.LOW;

        CaptureConfiguration.Builder builder = new CaptureConfiguration.Builder(resolution, quality);

        try {
            int maxDuration = Integer.valueOf("10");
            builder.maxDuration(maxDuration);
        } catch (final Exception e) {
            //NOP
        }
        try {
            int maxFileSize = Integer.valueOf("2");
            builder.maxFileSize(maxFileSize);
        } catch (final Exception e) {
            //NOP
        }
        /*try {
            int fps = Integer.valueOf(fpsEt.getEditableText().toString());
            builder.frameRate(fps);
        } catch (final Exception e) {
            //NOP
        }*/
        if (/*showTimerCb.isChecked()*/true) {
            builder.showRecordingTime();
        }
        if (/*!allowFrontCameraCb.isChecked()*/false) {
            builder.noCameraToggle();
        }

        return builder.build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String statusMessage;
        if (resultCode == Activity.RESULT_OK) {
            filename = data.getStringExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME);
            Log.d("File Name", filename);
            if (!TextUtils.isEmpty(filename)){
                Stash.put("video_id", filename);
                statusMessage = String.format("Abir Success", filename);
                final Bitmap thumbnail = getThumbnail();

                if (thumbnail != null) {
                    videoPreview.setImageBitmap(thumbnail);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            filename = null;
            statusMessage = "ABir Cancel";
        } else if (resultCode == VideoCaptureActivity.RESULT_ERROR) {
            filename = null;
            statusMessage = "ABir Fail";
        }
        //updateStatusAndThumbnail();

        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.VideoPreviewPlayButton)
    public void onViewClicked() {
        playVideo();
    }
    private Bitmap getThumbnail() {
        if (filename == null) return null;
        return ThumbnailUtils.createVideoThumbnail(filename, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }
    public void playVideo() {
        if (filename == null) return;

        final Intent videoIntent = new Intent(Intent.ACTION_VIEW);
        videoIntent.setDataAndType(Uri.parse(filename), "video/*");
        try {
            startActivity(videoIntent);
        } catch (ActivityNotFoundException e) {
            // NOP
        }
    }
}
