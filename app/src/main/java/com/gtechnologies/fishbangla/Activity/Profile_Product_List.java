package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.gtechnologies.fishbangla.Adapter.Fish_List_Adapter;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Fish_List;
import com.gtechnologies.fishbangla.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Profile_Product_List extends AppCompatActivity {

    @BindView(R.id.profile_product_list_recyclerview)
    RecyclerView profileProductListRecyclerview;
    Utility utility;
    Context context;
    private List<Fish_List> productList = new ArrayList<>();
    private Fish_List_Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__product__list);
        ButterKnife.bind(this);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            context = Profile_Product_List.this;
            utility = new Utility(context);
            font_setup();
            mAdapter = new Fish_List_Adapter(productList, context);
            profileProductListRecyclerview.setHasFixedSize(true);
            GridLayoutManager mLayoutManager = new GridLayoutManager(context, 2);
            //LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            profileProductListRecyclerview.setLayoutManager(mLayoutManager);
            profileProductListRecyclerview.setAdapter(mAdapter);
            Prepare_fish_data();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                    });
            utility.setFonts_normal(
                    new View[]{
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void Prepare_fish_data() {
        for (int c = 0; c < 6; c++) {
            Fish_List p = new Fish_List("url" + c, "0" + c, "Location" + c, "Category" + c);
            productList.add(p);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
