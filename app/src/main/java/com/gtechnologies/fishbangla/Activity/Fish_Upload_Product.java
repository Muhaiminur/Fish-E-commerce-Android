package com.gtechnologies.fishbangla.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.fxn.stash.Stash;
import com.gtechnologies.fishbangla.Fragment.Fragment_Upload_Step1;
import com.gtechnologies.fishbangla.Fragment.Fragment_Upload_Step2;
import com.gtechnologies.fishbangla.Fragment.Fragment_Upload_Step3;
import com.gtechnologies.fishbangla.Fragment.Fragment_Upload_Step4;
import com.gtechnologies.fishbangla.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fish_Upload_Product extends AppCompatActivity {

    @BindView(R.id.upload_tablayout)
    TabLayout uploadTablayout;
    @BindView(R.id.upload_viewPager)
    ViewPager uploadViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish__upload__product);
        ButterKnife.bind(this);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.menu_sell));
            setupViewPager(uploadViewPager);
            uploadTablayout.setupWithViewPager(uploadViewPager);
            /*LinearLayout tabStrip = ((LinearLayout) uploadTablayout.getChildAt(0));
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
            uploadViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });*/
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Upload_Step1(), getResources().getString(R.string.step1_string));
        adapter.addFragment(new Fragment_Upload_Step2(), getResources().getString(R.string.step2_string));
        adapter.addFragment(new Fragment_Upload_Step3(), getResources().getString(R.string.step3_string));
        adapter.addFragment(new Fragment_Upload_Step4(), getResources().getString(R.string.step4_string));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("canel","video");
        Stash.clear("video_id");
        Stash.clear("image_id");
    }
}
