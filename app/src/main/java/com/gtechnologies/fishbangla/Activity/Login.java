package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.Default_Message;
import com.gtechnologies.fishbangla.API_GET.GET_EMPTY;
import com.gtechnologies.fishbangla.API_GET.GET_LOGIN;
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

public class Login extends AppCompatActivity {
    @BindView(R.id.login_mobile)
    EditText loginMobile;
    @BindView(R.id.login_password)
    OtpView loginPassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.login_forgot)
    TextView loginForgot;
    @BindView(R.id.login_registration)
    TextView loginRegistration;
    @BindView(R.id.login_head)
    TextView loginHead;
    @BindView(R.id.login_title)
    TextView loginTitle;
    @BindView(R.id.login_number_title)
    TextView loginNumberTitle;

    Utility utility;
    Context context;
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        try {
            context = Login.this;
            utility = new Utility(context);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            register();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void register() {
        try {
            String register_string = getResources().getString(R.string.login_registration_string);
            int i1 = register_string.indexOf("?") + 2;
            int i2 = register_string.length();
            loginRegistration.setMovementMethod(LinkMovementMethod.getInstance());
            loginRegistration.setText(register_string, TextView.BufferType.SPANNABLE);
            Spannable mySpannable = (Spannable) loginRegistration.getText();
            ClickableSpan myClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    startActivity(new Intent(context, Phone_Verification.class));
                }
            };
            mySpannable.setSpan(myClickableSpan, i1, i2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @OnClick({R.id.login_forgot, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_forgot:
                startActivity(new Intent(context, Forget_Activity.class));
                break;
            case R.id.login:
                Login_Check(loginMobile.getText().toString(), loginPassword.getText().toString());
                break;
        }
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                            loginHead,
                            loginForgot,
                            loginRegistration,
                            login
                    });
            utility.setFonts_normal(
                    new View[]{
                            loginTitle,
                            loginNumberTitle
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void Login_Check(final String s, final String c) {
        try {
            if (!utility.isNetworkAvailable()) {
                utility.showToast(getResources().getString(R.string.no_internet_string));
            } else if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(c)) {
                utility.showProgress(false);
                Verify_Pin verifyPin = new Verify_Pin(s, c);
                Log.d("Login", s + "code " + c);
                Call<JsonElement> call = apiService.FishBangla_Login(verifyPin);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Login", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                GET_LOGIN getLogin = gson.fromJson(response.body(), GET_LOGIN.class);
                                if (getLogin.getId() != null) {
                                    Log.d("Login", getLogin.toString() + "");
                                    Stash.put("user", getLogin);
                                    startActivity(new Intent(context, Home_page.class));
                                    finish();
                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.login_invalid_string), Toast.LENGTH_LONG).show();
                                }
                                try {

                                } catch (Exception e) {
                                    Log.d("Error Line Number", Log.getStackTraceString(e));
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
}
