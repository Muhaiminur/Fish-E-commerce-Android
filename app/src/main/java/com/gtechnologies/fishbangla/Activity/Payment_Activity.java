package com.gtechnologies.fishbangla.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.GET_DISCOUNT;
import com.gtechnologies.fishbangla.API_GET.GET_LOGIN;
import com.gtechnologies.fishbangla.API_SEND.SEND_DISCOUNT;
import com.gtechnologies.fishbangla.API_SEND.Send_Cart;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment_Activity extends AppCompatActivity {

    Utility utility;
    Context context;
    ApiInterface apiService;
    Send_Cart send_cart;
    GET_LOGIN login;
    @BindView(R.id.payment_cod)
    RadioButton paymentCod;
    @BindView(R.id.payment_online)
    RadioButton paymentOnline;
    @BindView(R.id.payment_bkash)
    RadioButton paymentBkash;
    @BindView(R.id.payment_grp)
    RadioGroup paymentGrp;
    @BindView(R.id.payment_voucher_input)
    EditText paymentVoucherInput;
    @BindView(R.id.payment_voucher)
    Button paymentVoucher;
    @BindView(R.id.payment_sevice)
    TextView paymentSevice;
    @BindView(R.id.payment_vat)
    TextView paymentVat;
    @BindView(R.id.payment_delivery)
    TextView paymentDelivery;
    @BindView(R.id.payment_minus_promotion)
    TextView paymentMinusPromotion;
    @BindView(R.id.payment_sum)
    TextView paymentSum;
    @BindView(R.id.payment_confirm)
    TextView paymentConfirm;
    @BindView(R.id.payment_sum_without)
    TextView paymentSumWithout;

    int discount_id = 0;
    String dis_amount = "";
    String amount_after_dis = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_);
        ButterKnife.bind(this);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            context = Payment_Activity.this;
            utility = new Utility(context);
            send_cart = (Send_Cart) getIntent().getSerializableExtra("cart_data");
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            login = (GET_LOGIN) Stash.getObject("user", GET_LOGIN.class);
            if (login.getId() != null) {
                //Profile_Address(String.valueOf(login.getId()));
            }
            if (send_cart != null) {
                paymentSevice.setText(utility.convertToBangle(send_cart.getServiceCharge()) + getString(R.string.bdt_sign));
                paymentVat.setText(utility.convertToBangle(send_cart.getVat()) + getString(R.string.bdt_sign));
                paymentDelivery.setText(utility.convertToBangle(send_cart.getDeliveryCharge()) + getString(R.string.bdt_sign));
                paymentSum.setText(utility.convertToBangle(send_cart.getTotal()) + getString(R.string.bdt_sign));
                paymentSumWithout.setText(utility.convertToBangle(send_cart.getSubtotal()) + getString(R.string.bdt_sign));
            }
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
                            paymentCod,
                            paymentOnline,
                            paymentBkash,
                            paymentVoucherInput,
                            paymentVoucher,
                            paymentSevice,
                            paymentVat,
                            paymentDelivery,
                            paymentMinusPromotion,
                            paymentSum,
                            paymentConfirm,
                            paymentSumWithout
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    @OnClick({R.id.payment_online, R.id.payment_bkash, R.id.payment_grp, R.id.payment_confirm, R.id.payment_voucher})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.payment_online:
                utility.showToast(getResources().getString(R.string.payment_no_pay_string));
                break;
            case R.id.payment_bkash:
                utility.showToast(getResources().getString(R.string.payment_no_pay_string));
                break;
            case R.id.payment_grp:
                break;
            case R.id.payment_confirm:
                if (send_cart != null && paymentGrp.getCheckedRadioButtonId() == R.id.payment_cod) {
                    send_cart.setPointUsed(0);
                    send_cart.setDiscountId(discount_id);
                    send_cart.setPaymentType("Check");
                    send_cart.setPaymentStatus("Done");
                    send_cart.setMessage("Received");
                    send_cart.setDiscountAmount(dis_amount);
                    send_cart.setTotalAfterDiscount(amount_after_dis);
                    utility.logger(send_cart.toString());
                    Product_add(send_cart);
                } else {
                    utility.showToast(getResources().getString(R.string.payment_no_pay_string));
                }
                break;
            case R.id.payment_voucher:
                //utility.showToast(paymentVoucherInput.getText().toString());
                utility.hideKeyboard(view);
                if (!TextUtils.isEmpty(paymentVoucherInput.getText().toString())) {
                    Product_voucher(new SEND_DISCOUNT(paymentVoucherInput.getText().toString()));
                }
                break;
        }
    }

    public void Product_add(Send_Cart f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (f != null) {
                utility.showProgress(false);
                Log.d("Sending", "Send Cart" + f.toString());
                Call<JsonElement> call = apiService.FishBangla_Order(f);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Send Cart", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Log.d("Json", response.body().toString());
                                JSONObject jObj = new JSONObject(response.body().toString());
                                String id = jObj.getString("id");
                                if (id != null) {
                                    showDialog(getResources().getString(R.string.payment_succes_string));
                                    /*utility.showToast(getResources().getString(R.string.payment_succes_string));
                                    //
                                    Stash.clear("cart_list");
                                    finishAffinity();
                                    Intent intent = new Intent(context, Home_page.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();*/
                                } else {
                                    utility.showToast(getResources().getString(R.string.try_again_string));
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                            Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error Fish List", t.toString());
                        Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
                        utility.hideProgress();
                    }
                });
            } else {
                Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            utility.hideProgress();
        }
    }

    public void Product_voucher(final SEND_DISCOUNT discount) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (discount != null) {
                utility.showProgress(false);
                Log.d("Sending", "Discount" + discount.toString());
                Call<JsonElement> call = apiService.FishBangla_Discount_Code(discount);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Send Discount", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Log.d("Json", response.body().toString());
                                Gson gson = new Gson();
                                GET_DISCOUNT get_discount = gson.fromJson(response.body(), GET_DISCOUNT.class);
                                if (get_discount.getId() != null) {
                                    utility.logger(get_discount.toString());
                                    if (get_discount.getType().equals("amount")) {
                                        discount_id = get_discount.getId();
                                        if (send_cart != null) {
                                            int sum_amount = (int) Double.parseDouble(send_cart.getTotal()) - get_discount.getAmount();
                                            if (sum_amount < 0) {
                                                sum_amount = 0;
                                            }
                                            //send_cart.setTotal(String.valueOf(sum_amount));
                                            amount_after_dis = String.valueOf(sum_amount);
                                            dis_amount = String.valueOf(get_discount.getAmount());
                                            paymentSum.setText(utility.convertToBangle(String.valueOf(amount_after_dis) + getString(R.string.bdt_sign)));
                                            paymentMinusPromotion.setText(utility.convertToBangle(dis_amount) + getString(R.string.bdt_sign));
                                        }
                                    } else if (get_discount.getType().equals("percentage")) {
                                        discount_id = get_discount.getId();
                                        if (send_cart != null) {
                                            utility.logger(send_cart.getTotal());
                                            utility.logger(get_discount.getAmount() + "");
                                            double percent = get_discount.getAmount() / 100.0;
                                            utility.logger(percent + "");
                                            double d = Double.parseDouble(send_cart.getTotal()) * percent;
                                            utility.logger(d + "");
                                            if (d > Double.parseDouble(get_discount.getMaxAmount() + "")) {
                                                d = get_discount.getMaxAmount();
                                            }
                                            int sum_amount = (int) Double.parseDouble(send_cart.getTotal()) - (int) d;
                                            //send_cart.setTotal(String.valueOf(sum_amount));
                                            amount_after_dis = String.valueOf(sum_amount);
                                            dis_amount = String.valueOf(d);
                                            paymentSum.setText(utility.convertToBangle(String.valueOf(amount_after_dis) + getString(R.string.bdt_sign)));
                                            paymentMinusPromotion.setText(utility.convertToBangle(dis_amount) + getString(R.string.bdt_sign));
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.discount_invalid_string), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                            Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error Discount", t.toString());
                        Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
                        utility.hideProgress();
                    }
                });
            } else {
                Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            utility.hideProgress();
        }
    }

    public void showDialog(String message) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        HashMap<String, Integer> screen = utility.getScreenRes();
        int width = screen.get("width");
        int height = screen.get("height");
        int mywidth = (width / 10) * 7;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_toast);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_message);
        Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        utility.setFonts_normal(new View[]{tvMessage, btnOk}, Fonts.BIG);
        tvMessage.setText(message);
        LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.dialog_layout_size);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.width = mywidth;
        ll.setLayoutParams(params);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Stash.clear("cart_list");
                finishAffinity();
                Intent intent = new Intent(context, Home_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}
