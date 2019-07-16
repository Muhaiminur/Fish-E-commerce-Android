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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.Default_Message;
import com.gtechnologies.fishbangla.API_SEND.Generate_Pin;
import com.gtechnologies.fishbangla.API_SEND.Send_Registration;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registraion extends AppCompatActivity {
    @BindView(R.id.reg_name)
    EditText regName;
    @BindView(R.id.reg_email)
    EditText regEmail;
    @BindView(R.id.registration)
    Button registration;
    @BindView(R.id.reg_login)
    TextView regLogin;
    @BindView(R.id.reg_head)
    TextView regHead;
    @BindView(R.id.reg_mobile_title)
    TextView regMobileTitle;
    @BindView(R.id.reg_category_title)
    TextView regCategoryTitle;
    @BindView(R.id.reg_buyer)
    RadioButton regBuyer;
    @BindView(R.id.reg_seller)
    RadioButton regSeller;
    @BindView(R.id.reg_both)
    RadioButton regBoth;
    @BindView(R.id.reg_cat_grp)
    RadioGroup regCatGrp;
    @BindView(R.id.reg_buyer_refer)
    EditText regBuyerRefer;
    @BindView(R.id.reg_seller_refer)
    EditText regSellerRefer;
    String mobile_number;

    Utility utility;
    Context context;
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraion);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        try {
            context = Registraion.this;
            utility = new Utility(context);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            register();
            category_group();
            if (Stash.getString("Reg_mobile_number") != null) {
                mobile_number = Stash.getString("Reg_mobile_number");
                regMobileTitle.setText(mobile_number);
            } else {
                finish();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @OnClick({R.id.registration})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.registration:
                String tag = "buyer";
                if (regCatGrp.getCheckedRadioButtonId() == regSeller.getId()) {
                    tag = "seller";
                } else if (regCatGrp.getCheckedRadioButtonId() == regBuyer.getId()) {
                    tag = "buyer";
                } else if (regCatGrp.getCheckedRadioButtonId() == regBoth.getId()) {
                    tag = "both";
                }
                String b_r = "";
                if (!TextUtils.isEmpty(regBuyerRefer.getText())) {
                    b_r = regBuyerRefer.getText().toString();
                }
                String s_r = "";
                if (!TextUtils.isEmpty(regSellerRefer.getText())) {
                    s_r = regSellerRefer.getText().toString();
                }
                Registration_Sent(mobile_number, tag, regName.getText().toString(), regEmail.getText().toString(), b_r, s_r);
                break;
        }
    }

    public void register() {
        try {
            String register_string = getResources().getString(R.string.registration_login_button_string);
            int i1 = register_string.indexOf("?") + 2;
            int i2 = register_string.length();
            regLogin.setMovementMethod(LinkMovementMethod.getInstance());
            regLogin.setText(register_string, TextView.BufferType.SPANNABLE);
            Spannable mySpannable = (Spannable) regLogin.getText();
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
                            regHead,
                            regName,
                            regEmail,
                            registration,
                            regLogin,
                            regBuyer,
                            regSeller,
                            regBoth

                    });
            utility.setFonts_normal(
                    new View[]{
                            regCategoryTitle,
                            regMobileTitle,
                            regBuyerRefer,
                            regSellerRefer
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void category_group() {
        regCatGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.reg_buyer) {
                    regBuyerRefer.setVisibility(View.GONE);
                    regSellerRefer.setVisibility(View.GONE);
                } else if (checkedId == R.id.reg_seller) {
                    regBuyerRefer.setVisibility(View.GONE);
                    regSellerRefer.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.reg_both) {
                    regBuyerRefer.setVisibility(View.GONE);
                    regSellerRefer.setVisibility(View.VISIBLE);
                }
            }

        });
    }

    public void Registration_Sent(String mobl, String tag, String name, String email, String b_r, String s_r) {
        try {
            if (!isConnected()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(mobl) && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)) {
                utility.showProgress(false);
                Send_Registration sendRegistration = new Send_Registration(mobl, tag, s_r, b_r, name, email);
                Log.d("Registration", sendRegistration.toString());
                Call<JsonElement> call = apiService.FishBangla_Registration(sendRegistration);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Registration", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Default_Message m = gson.fromJson(response.body(), Default_Message.class);
                                Log.d("Registration", m.toString() + "");
                                Toast.makeText(context, /*getResources().getString(R.string.registration_pin_success_string)*/m.getData(), Toast.LENGTH_LONG).show();
                                //startActivity(new Intent(context, Registraion.class));
                                if (m.getCode() == 200) {
                                    startActivity(new Intent(context, Login.class));
                                    finish();
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
            } else if (TextUtils.isEmpty(mobl) || TextUtils.isEmpty(tag) || TextUtils.isEmpty(name) || TextUtils.isEmpty(email)) {
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
