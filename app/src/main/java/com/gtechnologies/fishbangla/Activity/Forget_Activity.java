package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.Default_Message;
import com.gtechnologies.fishbangla.API_SEND.Generate_Pin;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forget_Activity extends AppCompatActivity {

    @BindView(R.id.forget_mobile)
    EditText forgetMobile;
    @BindView(R.id.forget)
    Button forget;
    @BindView(R.id.forget_title)
    TextView forgetTitle;
    @BindView(R.id.forget_head)
    TextView forgetHead;


    Utility utility;
    Context context;
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        try {
            context = Forget_Activity.this;
            utility = new Utility(context);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @OnClick(R.id.forget)
    public void onViewClicked() {
        forget_password(forgetMobile.getText().toString());
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                    });
            utility.setFonts_normal(
                    new View[]{
                            forgetTitle,
                            forgetMobile,
                            forget,
                            forgetHead
                    }, Fonts.BIG);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void forget_password(final String s) {
        try {
            if (!isConnected()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(s)) {
                utility.showProgress(false);
                Generate_Pin generate_pin = new Generate_Pin(s);
                Log.d("Forget pin", s);
                Call<JsonElement> call = apiService.FishBangla_Forget_Pin(generate_pin);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Forget Pin", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Default_Message m = gson.fromJson(response.body(), Default_Message.class);
                                Log.d("Forget Pin", m.toString() + "");
                                Toast.makeText(context, /*getResources().getString(R.string.registration_pin_success_string)*/m.getData(), Toast.LENGTH_LONG).show();
                                //startActivity(new Intent(context, Registraion.class));
                                if (m.getCode() == 200) {
                                    startActivity(new Intent(context, Forget_Wait_Activity.class).putExtra("forget_mobile", s));
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
                        Log.e("Error Forget pin", t.toString());
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
