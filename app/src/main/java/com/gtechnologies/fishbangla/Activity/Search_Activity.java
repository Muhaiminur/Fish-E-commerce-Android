package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.Adapter.ProductListAdapter;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_Activity extends AppCompatActivity {

    @BindView(R.id.spinner)
    Spinner spinner;

    View view;
    Utility utility;
    Context context;
    ApiInterface apiService;
    ProductListAdapter productListAdapter;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        ButterKnife.bind(this);
        try {
            context = Search_Activity.this;
            utility = new Utility(context);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.homepage_search_string));
            font_setup();
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            getProductList();
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    if (jsonObject.optInt("id") != 0) {
                        //utility.showToast(jsonObject.optString("nameBn"));
                        Intent intent = new Intent(context, SearchActivity.class);
                        intent.putExtra("value", jsonObject.toString());
                        context.startActivity(intent);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
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

    private void getProductList() {
        utility.showProgress(true);
        Call<ResponseBody> call = apiService.getProductList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                utility.hideProgress();
                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        jsonArray = new JSONArray();
                        JSONObject object = new JSONObject();
                        object.put("id", 0);
                        object.put("nameBn", "এখানে অনুসন্ধান করুন");
                        jsonArray.put(object);
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray array = jsonObject.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            jsonArray.put(array.optJSONObject(i));
                        }
                        productListAdapter = new ProductListAdapter(context, jsonArray);
                        spinner.setAdapter(productListAdapter);
                    } catch (Exception ex) {
                        //do nothing
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                utility.hideProgress();
            }
        });
    }
}
