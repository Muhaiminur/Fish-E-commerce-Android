package com.gtechnologies.fishbangla.Activity;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.Get_Fish_List;
import com.gtechnologies.fishbangla.API_SEND.Send_List;
import com.gtechnologies.fishbangla.API_SEND.Send_Product_List;
import com.gtechnologies.fishbangla.Adapter.FISHLIST_Adapter;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    Utility utility = new Utility(this);
    JSONObject jsonObject;
    TextView tvTitle;
    ImageView ivBack;
    RecyclerView fishListRecyclerview;

    ApiInterface apiService;
    private List<Get_Fish_List> productList = new ArrayList<>();
    private FISHLIST_Adapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        String value = getIntent().getExtras().getString("value");
        try {
            jsonObject = new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
        font_setup();
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        fishListRecyclerview = (RecyclerView) findViewById(R.id.fish_list_recyclerview);
        tvTitle.setText(jsonObject.optString("nameBn"));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAdapter = new FISHLIST_Adapter(productList, this);
        fishListRecyclerview.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        fishListRecyclerview.setLayoutManager(mLayoutManager);
        fishListRecyclerview.setAdapter(mAdapter);
        Fish_List_Get(jsonObject.optInt("id"));
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                    });
            utility.setFonts_normal(
                    new View[]{
                            tvTitle
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void Fish_List_Get(int id) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(this, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else {
                utility.showProgress(false);
                Send_Product_List send_product_list = new Send_Product_List(id);
                Call<JsonElement> call = apiService.getSearchList(send_product_list);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Fish List", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Get_Fish_List>>() {
                                }.getType();
                                List<Get_Fish_List> fish_list_Response = gson.fromJson(response.body(), listType);
                                Log.d("Fish List respose", fish_list_Response.size() + "");
                                if (fish_list_Response.size() > 0) {
                                    productList.clear();
                                    productList.addAll(fish_list_Response);
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(SearchActivity.this, getResources().getString(R.string.no_data_string), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(SearchActivity.this, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error Fish List", t.toString());
                        Toast.makeText(SearchActivity.this, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
                        utility.hideProgress();
                    }
                });
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            utility.hideProgress();
        }
    }
}
