package com.gtechnologies.fishbangla.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fxn.stash.Stash;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;
import com.karan.churi.PermissionManager.PermissionManager;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Upload_Step2 extends Fragment {


    @BindView(R.id.upload_next2)
    Button uploadNext2;
    @BindView(R.id.upload_image_2)
    ImageView uploadImage2;
    @BindView(R.id.upload_image)
    Button uploadImage;
    Unbinder unbinder;

    //main
    PermissionManager permissionManager;

    public Fragment_Upload_Step2() {
        // Required empty public constructor
    }


    File final_image;
    View view;
    Utility utility;
    Context context;
    ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment__upload__step2, container, false);
        unbinder = ButterKnife.bind(this, view);
        pager = (ViewPager) getActivity().findViewById(R.id.upload_viewPager);
        try {
            context = getActivity();
            utility = new Utility(context);
            font_setup();
            permissionManager = new PermissionManager() {
            };
            permissionManager.checkAndRequestPermissions(getActivity());
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
                            uploadNext2,
                            uploadImage
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        permissionManager.checkResult(requestCode, permissions, grantResults);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "requestCode " + requestCode + ", resultCode " + resultCode);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                if (imageFile != null) {
                    final_image = imageFile;
                    if (final_image != null) {
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(R.drawable.loader);
                        requestOptions.error(R.drawable.ic_default_background_banner);

                        Glide.with(context).load(imageFile.getPath()).thumbnail(0.1f).apply(requestOptions).into(uploadImage2);
                        Stash.put("image_id", imageFile.getPath());
                    } else {
                        utility.logger("Image Null");
                    }
                }
            }
        });
    }

    @OnClick({R.id.upload_image, R.id.upload_next2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.upload_image:
                try {
                    if (checkPermission()) {
                        EasyImage.openChooserWithGallery(Fragment_Upload_Step2.this, "CHOOSE IMAGE", 0);
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
                break;
            case R.id.upload_next2:
                pager.setCurrentItem(2);
                break;
        }
    }
}
