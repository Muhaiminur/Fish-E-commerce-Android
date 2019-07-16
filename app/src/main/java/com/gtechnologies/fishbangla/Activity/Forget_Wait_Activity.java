package com.gtechnologies.fishbangla.Activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.Default_Message;
import com.gtechnologies.fishbangla.API_SEND.Generate_Pin;
import com.gtechnologies.fishbangla.API_SEND.Verify_Pin;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;
import com.mukesh.OtpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forget_Wait_Activity extends AppCompatActivity {

    @BindView(R.id.forget_code)
    OtpView forgetCode;
    @BindView(R.id.forget_wait)
    Button forgetWait;
    @BindView(R.id.forget_progress)
    ProgressBar forgetProgress;
    @BindView(R.id.forget_wait_head)
    TextView forgetWaitHead;
    @BindView(R.id.forget_wait_title)
    TextView forgetWaitTitle;
    String forget_mobile;

    Utility utility;
    Context context;
    ApiInterface apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__wait_);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        set_timer();
        try {
            context = Forget_Wait_Activity.this;
            utility = new Utility(context);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            forget_mobile = getIntent().getStringExtra("forget_mobile");
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @OnClick(R.id.forget_wait)
    public void onViewClicked() {
        verifycodesent(forget_mobile, forgetCode.getText().toString());
    }

    public void set_timer() {
        /*// timer for seekbar
        final int oneMin = 1 * 60 * 1000; // 1 minute in milli seconds

        *//** CountDownTimer starts with 1 minutes and every onTick is 1 second *//*
        new CountDownTimer(oneMin, 1000) {
            public void onTick(long millisUntilFinished) {

                //forward progress
                long finishedSeconds = oneMin - millisUntilFinished;
                int total = (int) (((float) finishedSeconds / (float) oneMin) * 100.0);
                forgetProgress.setProgress(total);

//                //backward progress
//                int total = (int) (((float) millisUntilFinished / (float) oneMin) * 100.0);
//                progressBar.setProgress(total);

            }

            public void onFinish() {
                // DO something when 1 minute is up
                forgetProgress.setVisibility(View.GONE);
            }
        }.start();*/
        ObjectAnimator animation = ObjectAnimator.ofInt(forgetProgress, "progress", 0, 500); // see this max value coming back here, we animate towards that value
        animation.setDuration(60000); // in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        new CountDownTimer(60 * 1 * 1000, 500) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                forgetProgress.setProgress((int) seconds);

            }

            @Override
            public void onFinish() {
                forgetProgress.setProgress(60 * 1);
                forgetProgress.clearAnimation();

            }
        }.start();
    }

    public void font_setup() {
        try {
            utility.setFonts_normal(
                    new View[]{
                            forgetWait,
                            forgetWaitHead,
                            forgetWaitTitle
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void verifycodesent(final String s, final String c) {
        try {
            if (!isConnected()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(c)) {
                utility.showProgress(false);
                Verify_Pin verifyPin = new Verify_Pin(s, c);
                Log.d("verify send", s + "code " + c);
                Call<JsonElement> call = apiService.FishBangla_Verify_PIN(verifyPin);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Verify Pin", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Default_Message m = gson.fromJson(response.body(), Default_Message.class);
                                Log.d("verify Pin", m.toString() + "");
                                Toast.makeText(context, /*getResources().getString(R.string.registration_pin_success_string)*/m.getData(), Toast.LENGTH_LONG).show();
                                if (m.getCode() == 200) {
                                    startActivity(new Intent(context, Login.class));
                                    finish();
                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                                }
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
                        Log.e("Error verify pin", t.toString());
                        Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
                        utility.hideProgress();
                    }
                });
            } else if (TextUtils.isEmpty(s)) {
                Toast.makeText(context, getResources().getString(R.string.validation_string), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            utility.hideProgress();
        }

    }

    public boolean isConnected() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null;
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return false;
    }
}
