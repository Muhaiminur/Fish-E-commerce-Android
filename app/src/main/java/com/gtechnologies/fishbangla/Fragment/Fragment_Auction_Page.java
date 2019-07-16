package com.gtechnologies.fishbangla.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.GET_LOGIN;
import com.gtechnologies.fishbangla.API_GET.GET_MAX_AUCTION;
import com.gtechnologies.fishbangla.API_GET.GET_USER_PROFILE;
import com.gtechnologies.fishbangla.API_SEND.SEND_AUCTION_AMOUNT;
import com.gtechnologies.fishbangla.API_SEND.SEND_AUCTION_ID;
import com.gtechnologies.fishbangla.API_SEND.Send_UserID;
import com.gtechnologies.fishbangla.Activity.Auction_Confirmation;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Auction_List;
import com.gtechnologies.fishbangla.R;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Auction_Page extends Fragment {
    @BindView(R.id.auction_description)
    TextView auctionDescription;
    @BindView(R.id.auction_quantity)
    TextView auctionQuantity;
    @BindView(R.id.auction_fish_image)
    ImageView auctionFishImage;
    @BindView(R.id.auction_apply)
    Button auctionApply;
    @BindView(R.id.auction_time)
    TextView auctionTime;
    @BindView(R.id.auction_price)
    TextView auctionPrice;
    Unbinder unbinder;
    View view;
    @BindView(R.id.auction_input)
    EditText auctionInput;
    @BindView(R.id.auction_submit)
    Button auctionSubmit;
    @BindView(R.id.auction_input_view)
    LinearLayout auctionInputView;

    Utility utility;

    CountDownTimer countDownTimer;
    Auction_List auction_list;
    int position;
    ApiInterface apiService;
    GET_LOGIN get_login;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.custom_auction_pager, container, false);
        unbinder = ButterKnife.bind(this, view);
        utility = new Utility(getContext());
        context = getActivity();
        font_setup();
        get_login = (GET_LOGIN) Stash.getObject("user", GET_LOGIN.class);
        apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
        String jsonMyObject = getArguments().getString("auction");
        position = getArguments().getInt("position");
        auction_list = new Gson().fromJson(jsonMyObject, Auction_List.class);
        auctionDescription.setText(auction_list.getName());
        auctionQuantity.setText("পরিমান - " + utility.convertToBangle(String.valueOf(auction_list.getAmount())) + " কেজি");
        //auctionQuantity.setVisibility(View.GONE);
        //auctionQuantity.setText(utility.convertToBangle(String.valueOf(auction_list.getAmount())));
        auctionTime.setText(utility.convertToBangle(auction_list.getExpireTime()));
        //auctionPrice.setText(utility.convertToBangle(String.valueOf(auction_list.getAmount())) + getContext().getString(R.string.bdt_sign));
        Glide.with(this).load(getContext().getString(R.string.image_base_url) + auction_list.getImage()).into(auctionFishImage);
        final long minutes = Long.parseLong(auction_list.getExpireTime()) - System.currentTimeMillis();
        countDownTimer = new CountDownTimer(minutes, 1000) {

            @Override
            public void onTick(long l) {
                String value = utility.humanReadableDateTime(l);
                if (auctionTime != null) {
                    auctionTime.setText(utility.convertToBangle(value));
                }
            }

            @Override
            public void onFinish() {
                if (auctionTime != null) {
                    auctionTime.setText(utility.convertToBangle("00:00:00"));
                }
            }
        };

        Auction_Max(new SEND_AUCTION_ID(String.valueOf(auction_list.getId())));
        return view;
    }

    public static Fragment_Auction_Page newInstance(Auction_List auction, int position) {

        Fragment_Auction_Page f = new Fragment_Auction_Page();
        Bundle b = new Bundle();
        b.putString("auction", new Gson().toJson(auction));
        b.putInt("position", position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onResume() {
        super.onResume();
        countDownTimer.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        countDownTimer.cancel();
    }

    @OnClick({R.id.auction_apply, R.id.auction_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.auction_apply:
                auctionInputView.setVisibility(View.VISIBLE);
                auctionApply.setVisibility(View.GONE);
                break;
            case R.id.auction_submit:
                utility.hideKeyboard(view);
                if (get_login != null) {
                    String textInput = auctionInput.getText().toString();
                    if (textInput.length() > 0) {
                        /*if (Integer.parseInt(textInput) <= auction_list.getAmount()) {
                            utility.showToast("Please enter greater amount than current amount");
                        } else {
                            //startActivity(new Intent(getActivity(), Auction_Confirmation.class));
                            *//*try {
                                JSONObject json = new JSONObject();
                                json.put("name", auction_list.getName());
                                json.put("amount", textInput);
                                json.put("image", auction_list.getImage());
                                json.put("status", "active");
                                json.put("userId", get_login.getId());
                                json.put("expire_time", *//**//*auction_list.getExpireTime()*//**//*"2019-05-26 23:59:59");
                                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
                                addTopAuctionAmount(requestBody);
                            } catch (Exception ex) {
                                utility.showToast(ex.toString());
                            }*//*
                            try{
                                Auction_Amount(new SEND_AUCTION_AMOUNT(String.valueOf(auction_list.getId()),String.valueOf(get_login.getId()),textInput));
                            }catch(Exception e){
                              Log.d("Error Line Number",Log.getStackTraceString(e));
                            }
                        }*/

                        try {
                            Auction_Amount(new SEND_AUCTION_AMOUNT(String.valueOf(auction_list.getId()), String.valueOf(get_login.getId()), textInput));
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                        }
                    } else {
                        utility.showToast("Please enter amount");
                    }
                } else {
                    utility.showToast("Please login first");
                }
                //startActivity(new Intent(getActivity(), Auction_Confirmation.class));
                //getActivity().finish();
                break;
        }
    }

    private void addTopAuctionAmount(RequestBody requestBody) {
        utility.showProgress(false);
        Call<ResponseBody> call = apiService.addTopAuctionAmount(String.valueOf(auction_list.getId()), requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                utility.hideProgress();
                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        auction_list.setAmount(jsonObject.optJSONObject("data").optInt("amount"));
                        auctionPrice.setText(utility.convertToBangle(String.valueOf(auction_list.getAmount())) + getContext().getString(R.string.bdt_sign));
                        Intent intent = new Intent(getActivity(), Auction_Confirmation.class);
                        intent.putExtra("auction", auction_list);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    } catch (Exception ex) {
                        utility.showToast(ex.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                utility.hideProgress();
            }
        });
    }


    public void Auction_Max(SEND_AUCTION_ID f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (f != null) {
                utility.showProgress(false);
                Call<JsonElement> call = apiService.FishBangla_Max_Auction(f);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Seller Profile", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                GET_MAX_AUCTION profile = gson.fromJson(response.body(), GET_MAX_AUCTION.class);
                                Log.d("Seller Profile", profile.toString());
                                if (profile != null) {
                                    auctionPrice.setText(utility.convertToBangle(String.valueOf(profile.getAuctionAmount() == null ? 0 : profile.getAuctionAmount())) + getContext().getString(R.string.bdt_sign));
                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.no_data_string), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                            utility.hideProgress();
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

    public void Auction_Amount(SEND_AUCTION_AMOUNT f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (f != null) {
                utility.showProgress(false);
                Call<JsonElement> call = apiService.FishBangla_Amount_Auction(f);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Seller Profile", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Log.d("Json", response.body().toString());
                                JSONObject jObj = new JSONObject(response.body().toString());
                                String id = jObj.getString("id");
                                if (id != null) {
                                    Intent intent = new Intent(getActivity(), Auction_Confirmation.class);
                                    intent.putExtra("auction", auction_list);
                                    intent.putExtra("position", position);
                                    startActivity(intent);
                                } else {
                                    utility.showToast(getResources().getString(R.string.try_again_string));
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                            utility.hideProgress();
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

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                    });
            utility.setFonts_normal(
                    new View[]{
                            auctionDescription,
                            auctionQuantity,
                            auctionApply,
                            auctionTime,
                            auctionPrice,
                            auctionInput,
                            auctionSubmit
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}
