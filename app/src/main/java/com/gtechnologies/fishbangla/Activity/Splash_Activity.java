package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.Config_Data;
import com.gtechnologies.fishbangla.API_GET.GET_CONFIG;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash_Activity extends AppCompatActivity {

    Utility utility;
    Context context;
    ApiInterface apiService;
    @BindView(R.id.iv_water_drops)
    ImageView ivWaterDrops;
    @BindView(R.id.splash_head)
    TextView splashHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);
        ButterKnife.bind(this);
        //to remove "information bar" above the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/

        /*new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash_Activity.this, MainActivity.class));
            }
        }, 3000);*/
        try {
            context = Splash_Activity.this;
            utility = new Utility(context);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            FISH_CONFIG();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        /*new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                startActivity(new Intent(Splash_Activity.this, User_Category.class));
                finish();
            }
        }.start();*/
    }

    public void FISH_CONFIG() {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (true) {
                //utility.showProgress(false);
                Log.d("Fish CONFIG", " Name");
                Call<JsonElement> call = apiService.FishBangla_Config();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Fish CONFIG", response.toString() + "");
                        //utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                GET_CONFIG get_fish_config = gson.fromJson(response.body(), GET_CONFIG.class);
                                Log.d("Fish CONFIG response", get_fish_config.getData().size() + "");
                                if (get_fish_config.getData().size() >= 0) {
                                    for (Config_Data config_data:get_fish_config.getData()){
                                        if (config_data.getCode().equalsIgnoreCase("VAT")){
                                            Stash.put("VAT",config_data.getValue());
                                        }
                                        if (config_data.getCode().equalsIgnoreCase("SERVICE_CHARGE")){
                                            Stash.put("SERVICE_CHARGE",config_data.getValue());
                                        }
                                        if (config_data.getCode().equalsIgnoreCase("DELIVERY_CHARGE")){
                                            Stash.put("DELIVERY_CHARGE",config_data.getValue());
                                        }
                                    }
                                }
                                startActivity(new Intent(Splash_Activity.this, User_Category.class));
                                finish();
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error CONFIG List", t.toString());
                        Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
                        //utility.hideProgress();
                    }
                });
            } else {
                Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            //utility.hideProgress();
        }
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                            splashHead
                    });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}
