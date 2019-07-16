package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class User_Category extends AppCompatActivity {

    @BindView(R.id.user_buyer)
    LinearLayout userBuyer;
    @BindView(R.id.user_seller)
    LinearLayout userSeller;
    @BindView(R.id.category_buyer)
    Button categoryBuyer;
    @BindView(R.id.category_seller)
    Button categorySeller;
    @BindView(R.id.category_head)
    TextView categoryHead;
    @BindView(R.id.category_question)
    TextView categoryQuestion;

    Utility utility;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__category);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        try {
            context = User_Category.this;
            utility = new Utility(context);
            font_setup();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @OnClick({R.id.category_buyer, R.id.category_seller})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.category_buyer:
                startActivity(new Intent(User_Category.this, Home_page.class));
                finish();
                break;
            case R.id.category_seller:
                startActivity(new Intent(User_Category.this, Login.class));
                finish();
                break;
        }
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                            categoryBuyer,
                            categorySeller,
                            categoryHead,
                            categoryQuestion
                    });
            utility.setFonts_normal(
                    new View[]{
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}
