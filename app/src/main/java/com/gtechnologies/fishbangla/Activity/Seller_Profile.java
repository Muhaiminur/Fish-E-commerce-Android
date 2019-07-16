package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.GET_LOGIN;
import com.gtechnologies.fishbangla.API_GET.GET_SELLER_PROFILE;
import com.gtechnologies.fishbangla.API_GET.GET_USER_PROFILE;
import com.gtechnologies.fishbangla.API_GET.Get_Fish_List;
import com.gtechnologies.fishbangla.API_SEND.Send_Ratting;
import com.gtechnologies.fishbangla.API_SEND.Send_Seller;
import com.gtechnologies.fishbangla.API_SEND.Send_UserID;
import com.gtechnologies.fishbangla.Adapter.FISHLIST_Adapter;
import com.gtechnologies.fishbangla.Adapter.Fish_List_Adapter_Rv;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Cart_List;
import com.gtechnologies.fishbangla.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Seller_Profile extends AppCompatActivity {
    @BindView(R.id.seller_profile_image)
    CircularImageView sellerProfileImage;
    @BindView(R.id.seller_profile_name)
    TextView sellerProfileName;
    @BindView(R.id.seller_profile_mobile)
    TextView sellerProfileMobile;
    @BindView(R.id.seller_profile_location)
    TextView sellerProfileLocation;
    @BindView(R.id.seller_profile_ratting)
    RatingBar sellerProfileRatting;
    @BindView(R.id.seller_profile_give_ratting)
    Button sellerProfileGiveRatting;
    @BindView(R.id.seller_profile_recycler)
    RecyclerView sellerProfileRecycler;
    String seller_id;
    GET_LOGIN getLogin;
    String r = "";

    Utility utility;
    Context context;
    ApiInterface apiService;
    @BindView(R.id.rl_cart)
    RelativeLayout sellerproCart;
    @BindView(R.id.sellerpro_cart_textview)
    TextView sellerproCartTextview;
    private List<Get_Fish_List> productList = new ArrayList<>();
    private Fish_List_Adapter_Rv mAdapter;

    List<Cart_List> cartLists_cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__profile);
        ButterKnife.bind(this);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            context = Seller_Profile.this;
            utility = new Utility(context);
            seller_id = getIntent().getStringExtra("seller_id");
            getLogin = utility.islogged();
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            mAdapter = new Fish_List_Adapter_Rv(productList, context);
            sellerProfileRecycler.setHasFixedSize(true);
            //LinearLayout mLayoutManager = new GridLayoutManager(context, 2);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            sellerProfileRecycler.setLayoutManager(mLayoutManager);
            sellerProfileRecycler.setAdapter(mAdapter);
            //Prepare_fish_data();
            Seller_Fish_rate(seller_id);
            //Seller_Fish_List(seller_id);
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
                            sellerProfileName,
                            sellerProfileMobile,
                            sellerProfileLocation,
                            sellerProfileGiveRatting
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    /*public void Prepare_fish_data() {
        for (int c = 0; c < 6; c++) {
            Fish_List p = new Fish_List("url" + c, "0" + c, "Location" + c, "Category" + c);
            productList.add(p);
        }
        mAdapter.notifyDataSetChanged();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    /*@OnClick(R.id.seller_profile_give_ratting)
    public void onViewClicked() {
        task_edit_alert();
    }*/

    public void task_edit_alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setTitle(getString(R.string.seller_profile_rate_string));
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.custom_ratting_seller_input, null);
        final RatingBar input_rating = viewInflated.findViewById(R.id.seller_custom_ratting);
        final AutoCompleteTextView input_review = viewInflated.findViewById(R.id.seller_review_input);
        if (!TextUtils.isEmpty(input_review.getEditableText().toString())) {
            r = input_review.getEditableText().toString();
            utility.logger(r);
        } else {
            utility.logger("Empty Review");
        }
        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getLogin != null && seller_id != null) {
                    Send_Ratting sendRatting = new Send_Ratting(seller_id, String.valueOf(getLogin.getId()), Math.round(input_rating.getRating()), input_review.getEditableText().toString(), "active");
                    Seller_Send_Ratting(sendRatting);
                    dialog.dismiss();
                    utility.logger(sendRatting.toString());
                }
                //m_Text = input.getText().toString();
                //UpdateData(s,collection,new TASK(input.getText().toString(),hint.getUpdate()));
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void Seller_Fish_rate(String f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(f)) {
                utility.showProgress(false);
                Send_Seller send_list = new Send_Seller(f);
                Log.d("Getting", "Seller Profile" + send_list.toString());
                Call<JsonElement> call = apiService.FishBangla_Seller_ratting_List(send_list);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Seller Profile", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<GET_SELLER_PROFILE>>() {
                                }.getType();
                                List<GET_SELLER_PROFILE> fish_rating_Response = gson.fromJson(response.body(), listType);
                                Log.d("Seller Profile", fish_rating_Response.size() + "");
                                if (fish_rating_Response.size() > 0) {
                                    productList.clear();
                                    float rating = 0;
                                    for (GET_SELLER_PROFILE profile : fish_rating_Response) {
                                        rating = rating + profile.getRating();
                                    }
                                    rating = rating / fish_rating_Response.size();
                                    sellerProfileRatting.setRating(rating);
                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.no_data_string), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                        }
                        Seller_Fish_List(seller_id);
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

    public void Seller_Send_Ratting(Send_Ratting f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (f != null) {
                utility.showProgress(false);
                Log.d("Sending", "Seller rating" + f.toString());
                Call<JsonElement> call = apiService.FishBangla_Seller_Rating(f);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Seller rating", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Log.d("Json", response.body().toString());
                                JSONObject jObj = new JSONObject(response.body().toString());
                                String id = jObj.getString("id");
                                if (id != null) {
                                    utility.showToast(getResources().getString(R.string.seller_rating_success_string));
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

    public void Seller_Fish_List(String f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(f)) {
                utility.showProgress(false);
                Send_UserID userID = new Send_UserID(f);
                Log.d("Getting", "Seller Product List" + userID.toString());
                Call<JsonElement> call = apiService.FishBangla_Seller_Fish_List(userID);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Seller Product List", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Get_Fish_List>>() {
                                }.getType();
                                List<Get_Fish_List> fish_list_Response = gson.fromJson(response.body(), listType);
                                Log.d("Seller Product List", fish_list_Response.size() + "");
                                if (fish_list_Response.size() > 0) {
                                    productList.clear();
                                    productList.addAll(fish_list_Response);
                                    mAdapter.notifyDataSetChanged();
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
                        Seller_Profile(seller_id);
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

    public void Seller_Profile(String f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(f)) {
                utility.showProgress(false);
                Send_UserID userID = new Send_UserID(f);
                Log.d("Getting", "Seller Profile" + userID.toString());
                Call<JsonElement> call = apiService.FishBangla_Seller_Profile(userID);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Seller Profile", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                GET_USER_PROFILE profile = gson.fromJson(response.body(), GET_USER_PROFILE.class);
                                Log.d("Seller Profile", profile.toString());
                                if (profile != null) {
                                    sellerProfileName.setText(profile.getName());
                                    sellerProfileMobile.setText(profile.getContact());
                                    sellerProfileLocation.setText(profile.getEmail());
                                    RequestOptions requestOptions = new RequestOptions();
                                    requestOptions.placeholder(R.drawable.loader);
                                    requestOptions.error(R.drawable.ic_fish);

                                    Glide.with(context).load(profile.getPicture()).thumbnail(0.1f).apply(requestOptions).into(sellerProfileImage);
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

    @OnClick({R.id.seller_profile_give_ratting, R.id.rl_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.seller_profile_give_ratting:
                task_edit_alert();
                break;
            case R.id.rl_cart:
                cartLists_cart.clear();
                cartLists_cart = Stash.getArrayList("cart_list", Cart_List.class);
                if (cartLists_cart.size() > 0) {
                    startActivity(new Intent(context, Cart_Activity.class));
                } else {
                    utility.showToast(getResources().getString(R.string.cart_empty_string));
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (utility.islogged() != null) {
            sellerproCartTextview.setText(String.valueOf(utility.cart_number()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (utility.islogged() != null) {
            sellerproCartTextview.setText(String.valueOf(utility.cart_number()));
        }
    }
}
