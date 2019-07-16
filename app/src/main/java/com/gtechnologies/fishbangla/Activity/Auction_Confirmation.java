package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fxn.stash.Stash;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Auction_List;
import com.gtechnologies.fishbangla.Model.Cart_List;
import com.gtechnologies.fishbangla.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Auction_Confirmation extends AppCompatActivity {

    Utility utility;
    Context context;
    @BindView(R.id.auction_apply_again)
    Button auctionApplyAgain;
    Auction_List auction_list;
    int position;
    @BindView(R.id.auction_cart)
    ImageView auctionCart;
    @BindView(R.id.auction_cart_textview)
    TextView auctionCartTextview;

    List<Cart_List> cartLists = new ArrayList<>();
    @BindView(R.id.auction_quantity)
    TextView auctionQuantity;
    @BindView(R.id.rl_cart)
    RelativeLayout rlCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction__confirmation);
        position = getIntent().getExtras().getInt("position");
        auction_list = (Auction_List) getIntent().getSerializableExtra("auction");
        ButterKnife.bind(this);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            context = Auction_Confirmation.this;
            utility = new Utility(context);
            font_setup();
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
                            auctionApplyAgain,
                            auctionQuantity
                    }, Fonts.BIG);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent("com.refresh.auction");
        intent.putExtra("position", position);
        intent.putExtra("auction", auction_list);
        sendBroadcast(intent);
        finish();
        return true;
    }
    /*@OnClick(R.id.auction_apply_again)
    public void onViewClicked() {
        Intent intent = new Intent("com.refresh.auction");
        intent.putExtra("position", position);
        intent.putExtra("auction", auction_list);
        sendBroadcast(intent);
        finish();
    }

    @OnClick(R.id.auction_cart)
    public void onViewClicked() {
    }*/

    @OnClick({R.id.auction_apply_again,R.id.rl_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.auction_apply_again:
                Intent intent = new Intent("com.refresh.auction");
                intent.putExtra("position", position);
                intent.putExtra("auction", auction_list);
                sendBroadcast(intent);
                finish();
                break;
            case R.id.rl_cart:
                cartLists.clear();
                cartLists = Stash.getArrayList("cart_list", Cart_List.class);
                if (cartLists.size() > 0) {
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
            auctionCartTextview.setText(String.valueOf(utility.cart_number()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (utility.islogged() != null) {
            auctionCartTextview.setText(String.valueOf(utility.cart_number()));
        }
    }
}
