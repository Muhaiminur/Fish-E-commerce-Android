package com.gtechnologies.fishbangla.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.main_home)
    Button mainHome;
    @BindView(R.id.main_head)
    TextView mainHead;
    @BindView(R.id.main_logIn)
    TextView mainLogIn;
    @BindView(R.id.main_search)
    EditText mainSearch;
    @BindView(R.id.homepage_retail)
    ImageButton homepageRetail;
    @BindView(R.id.main_retail)
    TextView mainRetail;
    @BindView(R.id.homepage_wholesale)
    ImageButton homepageWholesale;
    @BindView(R.id.main_wholesale)
    TextView mainWholesale;
    @BindView(R.id.homepage_raw_one)
    LinearLayout homepageRawOne;

    Utility utility;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        try {
            context = MainActivity.this;
            utility = new Utility(context);
            font_setup();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        // Example of a call to a native method
        /*TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());*/
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    /*public native String stringFromJNI();*/
    @OnClick({R.id.main_home, R.id.main_logIn, R.id.homepage_retail, R.id.homepage_wholesale})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_home:
                break;
            case R.id.main_logIn:
                break;
            case R.id.homepage_retail:
                break;
            case R.id.homepage_wholesale:
                break;
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
                            mainLogIn,
                            mainSearch,
                            mainRetail,
                            mainWholesale

                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}
