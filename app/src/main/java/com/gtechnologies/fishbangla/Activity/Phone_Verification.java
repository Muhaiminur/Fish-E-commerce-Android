package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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

public class Phone_Verification extends AppCompatActivity {

    @BindView(R.id.verify_head)
    TextView verifyHead;
    @BindView(R.id.verify_title)
    TextView verifyTitle;
    @BindView(R.id.verify_mobile)
    EditText verifyMobile;
    @BindView(R.id.verify_send)
    Button verifySend;
    @BindView(R.id.verify_input_view)
    CardView verifyInputView;
    @BindView(R.id.verify_code_title)
    TextView verifyCodeTitle;
    @BindView(R.id.verify_code)
    OtpView verifyCode;
    @BindView(R.id.verify)
    Button verify;
    @BindView(R.id.verify_view)
    CardView verifyView;
    @BindView(R.id.verify_login)
    TextView verifyLogin;

    Utility utility;
    Context context;
    ApiInterface apiService;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone__verification);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        try {
            context = Phone_Verification.this;
            utility = new Utility(context);
            //api work
            //apiService = ApiClient.getClient().create(ApiInterface.class);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            register();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @OnClick({R.id.verify_send, R.id.verify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.verify_send:
                pincodesent(verifyMobile.getText().toString());
                break;
            case R.id.verify:
                verifycodesent(verifyMobile.getText().toString(), verifyCode.getText().toString());
                break;
        }
    }

    public void register() {
        try {
            String register_string = getResources().getString(R.string.registration_login_button_string);
            int i1 = register_string.indexOf("?") + 2;
            int i2 = register_string.length();
            verifyLogin.setMovementMethod(LinkMovementMethod.getInstance());
            verifyLogin.setText(register_string, TextView.BufferType.SPANNABLE);
            Spannable mySpannable = (Spannable) verifyLogin.getText();
            ClickableSpan myClickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    startActivity(new Intent(context, Login.class));
                }
            };
            mySpannable.setSpan(myClickableSpan, i1, i2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                            verifyHead,
                            verifyTitle,
                            verifySend,
                            verifyCodeTitle,
                            verify,
                            verifyMobile,
                            verifyLogin
                    });
            utility.setFonts_normal(
                    new View[]{
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void pincodesent(String s) {
        try {
            if (!isConnected()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(s)) {
                utility.showProgress(false);
                Generate_Pin generate_pin = new Generate_Pin(s);
                Log.d("pin send", s);
                Call<JsonElement> call = apiService.FishBangla_Send_PIN(generate_pin);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Generate Pin", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Default_Message m = gson.fromJson(response.body(), Default_Message.class);
                                Log.d("Generate Pin", m.toString() + "");
                                Toast.makeText(context, /*getResources().getString(R.string.registration_pin_success_string)*/m.getData(), Toast.LENGTH_LONG).show();
                                //startActivity(new Intent(context, Registraion.class));
                                if (m.getCode() == 200) {
                                    verifyInputView.setVisibility(View.GONE);
                                    verifyView.setVisibility(View.VISIBLE);
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
                        Log.e("Error Generate pin", t.toString());
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
                                    Stash.put("Reg_mobile_number", s);
                                    startActivity(new Intent(context, Registraion.class));
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
