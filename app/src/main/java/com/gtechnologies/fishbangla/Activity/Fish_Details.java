package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.gtechnologies.fishbangla.API_GET.Get_Fish_List;
import com.gtechnologies.fishbangla.API_SEND.Send_Suggestion;
import com.gtechnologies.fishbangla.Adapter.FISHLIST_Adapter;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Cart_List;
import com.gtechnologies.fishbangla.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fish_Details extends AppCompatActivity {
    @BindView(R.id.fish_details_image)
    ImageView fishDetailsImage;
    @BindView(R.id.fish_details_add)
    ImageView fishDetailsAdd;
    @BindView(R.id.fish_details_price)
    TextView fishDetailsPrice;
    @BindView(R.id.fish_details_location)
    TextView fishDetailsLocation;
    @BindView(R.id.fish_details_category)
    TextView fishDetailsCategory;
    @BindView(R.id.fish_details_recyclerview)
    RecyclerView fishDetailsRecyclerview;
    @BindView(R.id.fish_details_profile)
    LinearLayout fishDetailsProfile;

    Utility utility;
    Context context;
    ApiInterface apiService;
    @BindView(R.id.fishdetails_cart)
    ImageView fishdetailsCart;
    @BindView(R.id.fishdetails_cart_textview)
    TextView fishdetailsCartTextview;
    @BindView(R.id.rl_cart)
    RelativeLayout rlCart;
    private List<Get_Fish_List> productList = new ArrayList<>();
    private List<Cart_List> cartLists = new ArrayList<>();
    private FISHLIST_Adapter mAdapter;
    Get_Fish_List getFishList;

    List<Cart_List> cartLists_cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish__details);
        ButterKnife.bind(this);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            context = Fish_Details.this;
            utility = new Utility(context);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            Intent i = getIntent();
            getFishList = (Get_Fish_List) i.getSerializableExtra("fish_details");
            if (getFishList != null) {
                utility.logger(getFishList.toString());
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.loader);
                requestOptions.error(R.drawable.ic_default_background_banner);
                Glide.with(context).load(context.getResources().getString(R.string.image_base_url) + getFishList.getImage()).thumbnail(0.1f).apply(requestOptions).into(fishDetailsImage);
                fishDetailsPrice.setText(getFishList.getUserName());
                fishDetailsLocation.setText(getFishList.getNameBn() + " - " + utility.convertToBangle(getFishList.getPrice()) + context.getResources().getString(R.string.unit_string));
                fishDetailsCategory.setText(getFishList.getUpazilla() + ", " + getFishList.getDistrict() + ", " + getFishList.getDivision());
                getSupportActionBar().setTitle(getFishList.getNameBn() + " - " + utility.convertToBangle(getFishList.getPrice()) + context.getResources().getString(R.string.unit_string));
            }
            mAdapter = new FISHLIST_Adapter(productList, context);
            fishDetailsRecyclerview.setHasFixedSize(true);
            GridLayoutManager mLayoutManager = new GridLayoutManager(context, 2);
            fishDetailsRecyclerview.setLayoutManager(mLayoutManager);
            fishDetailsRecyclerview.setAdapter(mAdapter);
            /*Prepare_fish_data();*/
            Seller_Fish_List(String.valueOf(getFishList.getId()));
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
                            fishDetailsPrice,
                            fishDetailsLocation,
                            fishDetailsCategory,
                    }, Fonts.BIG);
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

    @OnClick({R.id.fish_details_add, R.id.fish_details_profile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fish_details_add:
                if (getFishList != null) {
                    cartLists.clear();
                    cartLists = Stash.getArrayList("cart_list", Cart_List.class);
                    Cart_List p = new Cart_List(String.valueOf(getFishList.getId()), getFishList.getImage(), getFishList.getNameBn(), getFishList.getPrice(), "1");
                    String up = "no";
                    for (Cart_List cartList : cartLists) {
                        if (cartList.getD_id().equals(p.getD_id())) {
                            int i = Integer.parseInt(cartList.getFish_quantity()) + 1;
                            cartList.setFish_quantity(String.valueOf(i));
                            up = "yes";
                            break;
                        }
                    }
                    if (up.equals("no")) {
                        cartLists.add(p);
                    }
                    Stash.put("cart_list", cartLists);
                    utility.showToast(getResources().getString(R.string.product_succes_string));

                    /*if (cartLists.size() > 0) {
                        for (Cart_List c : cartLists) {
                            add_custom_view(c);
                            utility.logger(c.getFish_price());
                            sum = sum + (Integer.parseInt(c.getFish_price()) * Integer.parseInt(c.getFish_quantity()));
                            Item item=new Item(Integer.parseInt(c.getD_id()),"Good",c.getFish_quantity(),c.getFish_price(),"active");
                            itemArrayList.add(item);
                        }
                    }*/
                    fishdetailsCartTextview.setText(String.valueOf(utility.cart_number()));
                }
                break;
            case R.id.fish_details_profile:
                try {
                    if (getFishList != null) {
                        startActivity(new Intent(context, Seller_Profile.class).putExtra("seller_id", String.valueOf(getFishList.getUserId())));
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
                break;
        }
    }

    public void Seller_Fish_List(String f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(f)) {
                utility.showProgress(false);
                Send_Suggestion send_suggestion = new Send_Suggestion(f, "all");
                Log.d("Getting", "Fish List" + send_suggestion.toString());
                Call<JsonElement> call = apiService.FishBangla_Seller_Suggestion(send_suggestion);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Fish List", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Get_Fish_List>>() {
                                }.getType();
                                List<Get_Fish_List> fish_list_Response = gson.fromJson(response.body(), listType);
                                Log.d("Fish List respose", fish_list_Response.size() + "");
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

    @OnClick(R.id.rl_cart)
    public void onViewClicked() {
        cartLists_cart.clear();
        cartLists_cart = Stash.getArrayList("cart_list", Cart_List.class);
        if (cartLists_cart.size() > 0) {
            startActivity(new Intent(context, Cart_Activity.class));
        } else {
            utility.showToast(getResources().getString(R.string.cart_empty_string));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (utility.islogged() != null) {
            fishdetailsCartTextview.setText(String.valueOf(utility.cart_number()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (utility.islogged() != null) {
            fishdetailsCartTextview.setText(String.valueOf(utility.cart_number()));
        }
    }
}
