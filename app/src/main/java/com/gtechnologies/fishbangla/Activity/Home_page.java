package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fxn.stash.Stash;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.Fragment.Fragment_Buy_History;
import com.gtechnologies.fishbangla.Fragment.Fragment_Dash_Boared;
import com.gtechnologies.fishbangla.Fragment.Fragment_Profile;
import com.gtechnologies.fishbangla.Fragment.Fragment_Reference;
import com.gtechnologies.fishbangla.Fragment.Fragment_Sell_History;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Cart_List;
import com.gtechnologies.fishbangla.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    @BindView(R.id.main_home)
    Button mainHome;
    @BindView(R.id.main_head)
    TextView mainHead;
    @BindView(R.id.main_logIn)
    Button mainLogIn;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.rl_cart)
    RelativeLayout homepageCart;
    @BindView(R.id.homepage_cart_textview)
    TextView homepageCartTextview;


    Utility utility;
    Context context;
    ApiInterface apiService;

    List<Cart_List> cartLists_cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        //toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        try {
            context = Home_page.this;
            utility = new Utility(context);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            if (utility.islogged() != null) {
                utility.logger(utility.islogged().getContact());
                mainLogIn.setVisibility(View.GONE);
                homepageCartTextview.setText(String.valueOf(utility.cart_number()));
            } else {
                mainLogIn.setVisibility(View.VISIBLE);
            }
            Fragment home = new Fragment_Dash_Boared();
            Replace_Fragment(home);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (utility.islogged() != null) {
            getMenuInflater().inflate(R.menu.home_page, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_sell) {
            startActivity(new Intent(Home_page.this, Fish_Upload_Product.class));
        } else if (id == R.id.menu_profile) {
            try {
                Fragment home = new Fragment_Profile();
                Replace_Fragment(home);
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        } else if (id == R.id.menu_order) {
            return true;
        } else if (id == R.id.menu_refere) {
            try {
                Fragment home = new Fragment_Reference();
                Replace_Fragment(home);
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        } else if (id == R.id.menu_buy_history) {
            try {
                Fragment home = new Fragment_Buy_History();
                Replace_Fragment(home);
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        } else if (id == R.id.menu_sell_history) {
            try {
                Fragment home = new Fragment_Sell_History();
                Replace_Fragment(home);
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        } else if (id == R.id.menu_logout) {
            Stash.clearAll();
            restart();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sell) {
            // Handle the camera action
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_order) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Replace_Fragment(Fragment frag) {
        try {
            if (frag != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.home_area, frag);
                ft.commit();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @OnClick({R.id.main_home, R.id.main_head, R.id.main_logIn, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_home:
                clear_fragment();
                Fragment home = new Fragment_Dash_Boared();
                Replace_Fragment(home);
                break;
            case R.id.main_head:
                break;
            case R.id.main_logIn:
                startActivity(new Intent(Home_page.this, Login.class));
                break;
            case R.id.iv_search:
                startActivity(new Intent(context,Search_Activity.class));
                //utility.showToast("Search Start");
                break;
        }
    }

    public void clear_fragment() {
        try {
            FragmentManager fm = getSupportFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
                Log.d("Fragment", "remove");
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (utility.islogged() != null) {
            utility.logger(utility.islogged().getContact());
            mainLogIn.setVisibility(View.GONE);
        } else {
            mainLogIn.setVisibility(View.VISIBLE);
        }
        invalidateOptionsMenu();
        if (utility.islogged() != null) {
            mainLogIn.setVisibility(View.GONE);
            homepageCartTextview.setText(String.valueOf(utility.cart_number()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (utility.islogged() != null) {
            mainLogIn.setVisibility(View.GONE);
            homepageCartTextview.setText(String.valueOf(utility.cart_number()));
        }
    }

    public void restart() {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
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

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                    });
            utility.setFonts_normal(
                    new View[]{
                            mainHome,
                            mainHead,
                            mainLogIn
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}
