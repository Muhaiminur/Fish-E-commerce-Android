package com.gtechnologies.fishbangla.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.Adapter.Auction_Adapter;
import com.gtechnologies.fishbangla.Fragment.Fragment_Auction_Page;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Auction_List;
import com.gtechnologies.fishbangla.Model.Cart_List;
import com.gtechnologies.fishbangla.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Wholesale_Auction extends AppCompatActivity {
    @BindView(R.id.auction_pager)
    ViewPager auctionPager;

    Utility utility;
    Context context;
    List<Fragment> auction_list;
    Auction_Adapter auction_adapter;

    ApiInterface apiService;
    @BindView(R.id.rl_cart)
    RelativeLayout wholesaleaucCart;
    @BindView(R.id.wholesaleauc_textview)
    TextView wholesaleaucTextview;

    List<Cart_List> cartLists_cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesale__auction);
        apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
        ButterKnife.bind(this);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("বর্তমান নিলাম");
            context = Wholesale_Auction.this;
            utility = new Utility(context);
            auction_list = new ArrayList<>();
            auction_adapter = new Auction_Adapter(getSupportFragmentManager(), auction_list);
            auctionPager.setAdapter(auction_adapter);
            font_setup();
            //Prepare_history_data();
            getAuctionList();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*Auction_List auction = (Auction_List) intent.getSerializableExtra("auction");
            int position = intent.getExtras().getInt("position");
            auction_list.remove(position);
            auction_list.add(position, Fragment_Auction_Page.newInstance(auction, position));
            auction_adapter.notifyDataSetChanged();*/
            finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter("com.refresh.auction"));
        if (utility.islogged() != null) {
            wholesaleaucTextview.setText(String.valueOf(utility.cart_number()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void getAuctionList() {
        try {
            if (!utility.isNetworkAvailable()) {
                utility.showToast(getString(R.string.no_internet_string));
            } else {
                utility.showProgress(true);
                Call<ResponseBody> call = apiService.getAuctionList();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        utility.hideProgress();
                        try {
                            if (response.isSuccessful() && response.code() == 200) {
                                JSONArray jsonArray = new JSONArray(response.body().string());
                                for (int c = 0; c < jsonArray.length(); c++) {
                                    JSONObject jsonObject = jsonArray.optJSONObject(c);
                                    Auction_List p = new Auction_List(
                                            jsonObject.optString("image"),
                                            jsonObject.optString("createdAt"),
                                            jsonObject.optInt("amount"),
                                            jsonObject.optString("expireTime"),
                                            jsonObject.optString("name"),
                                            jsonObject.optString("auctioneerContact"),
                                            jsonObject.optInt("id"),
                                            jsonObject.optInt("auctioneerId"),
                                            jsonObject.optString("auctioneerName"),
                                            jsonObject.optString("auctioneerPicture")
                                    );
                                    auction_list.add(Fragment_Auction_Page.newInstance(p, c));
                                }
                                auction_adapter.notifyDataSetChanged();
                                /*Gson gson = new Gson();
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
                                }*/
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                        }
                        //Seller_Fish_List(seller_id);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error Fish List", t.toString());
                        Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
                        utility.hideProgress();
                    }
                });
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
                            wholesaleaucTextview
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
            wholesaleaucTextview.setText(String.valueOf(utility.cart_number()));
        }
    }
/*    public void Prepare_history_data() {
        for (int c = 0; c < 6; c++) {
            Auction_List p = new Auction_List("url","Fish Description " + c, "Price " + c, "Time " + c, c + " Kg");
            auction_list.add(Fragment_Auction_Page.newInstance(p));
        }
        auction_adapter.notifyDataSetChanged();
    }*/
}
