package com.gtechnologies.fishbangla.Library;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.gtechnologies.fishbangla.API_GET.GET_LOGIN;
import com.gtechnologies.fishbangla.Model.Cart_List;
import com.gtechnologies.fishbangla.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Utility {
    Context context;
    ProgressDialog mProgressDialog;

    public Utility(Context c) {
        context = c;
    }

    public void setFonts_normal(View[] views, int fontSize) {
        Typeface tf = null;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi.ttf");
        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            if (view instanceof RadioButton) {
                RadioButton rb = (RadioButton) view;
                rb.setTypeface(tf);
                rb.setTextSize(fontSize);
            } else if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                checkBox.setTypeface(tf);
                checkBox.setTextSize(fontSize);
            } else if (view instanceof EditText) {
                EditText et = (EditText) view;
                et.setTypeface(tf);
                et.setTextSize(fontSize);
            } else if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setTypeface(tf);
                tv.setTextSize(fontSize);
            } else if (view instanceof Button) {
                Button btn = (Button) view;
                btn.setTypeface(tf);
                btn.setTextSize(fontSize);
            } else if (view instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) view;
                SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
                mNewTitle.setSpan(tf, 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                menuItem.setTitle(mNewTitle);
            }
        }
    }

    public void setFonts_Bold(View[] views) {
        Typeface tf = null;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi.ttf");
        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            if (view instanceof RadioButton) {
                RadioButton rb = (RadioButton) view;
                rb.setTypeface(tf);
                rb.setTextSize(20);
            } else if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                checkBox.setTypeface(tf);
                checkBox.setTextSize(20);
            } else if (view instanceof EditText) {
                EditText et = (EditText) view;
                et.setTypeface(tf);
                et.setTextSize(20);
            } else if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setTypeface(tf);
                tv.setTextSize(20);
            } else if (view instanceof Button) {
                Button btn = (Button) view;
                btn.setTypeface(tf);
                btn.setTextSize(20);
            } else if (view instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) view;
                SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
                mNewTitle.setSpan(tf, 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                menuItem.setTitle(mNewTitle);
            }
        }
    }

    public void showProgress(boolean isCancelable) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(isCancelable);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
    }

    /*
    ================ Hide Progress Dialog ===============
    */
    public void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /*
    ================ JSON from Folder ===============
    */

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("Json/" + filename + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    /*
    ================ show Toast ===============
    */
    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /*
        ================ Get Screen Width ===============
        */
    /*public HashMap<String, Integer> getScreenRes() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        map.put(KeyWord.SCREEN_WIDTH, width);
        map.put(KeyWord.SCREEN_HEIGHT, height);
        return map;
    }*/

    public void logger(String message) {
        Log.d(context.getString(R.string.app_name), message);
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        String date = sdf.format(new Date());
        //writeToFile(date+" -> "+message);
    }

    public void clearText(View[] view) {
        for (View v : view) {
            if (v instanceof EditText) {
                ((EditText) v).setText("");
            } else if (v instanceof Button) {
                ((Button) v).setText("");
            } else if (v instanceof TextView) {
                ((TextView) v).setText("");
            }
        }
    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public void put_string(String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String get_string(String key) {
        String result = "";
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        result = sharedPref.getString(key, "");
        return result;
    }
    public void put_Object(String key, Object value) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(key, json);
        editor.apply();
    }

    public Object get_Object(String key) {
        String result = "";
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        result = sharedPref.getString(key, "");
        Gson gson = new Gson();
        Object obj = gson.fromJson(result,Object.class);
        return obj;
    }

    public GET_LOGIN islogged(){
        GET_LOGIN result;
        GET_LOGIN login = (GET_LOGIN) Stash.getObject("user", GET_LOGIN.class);
        if (login != null) {
            result=login;
        } else {
            result=null;
        }
        return result;
    }

    public int cart_number(){
        int result=0;
        ArrayList<Cart_List>cart_lists=Stash.getArrayList("cart_list", Cart_List.class);
        if (cart_lists.size()>0){
            result=cart_lists.size();
        }
        return result;
    }


    /*
   ================ Bangla Number Converter ============
   */
    public String convertToBangle(String numbers) {
        String banglaNumber = "";
        for (int i = 0; i < numbers.length(); i++) {
            switch (numbers.charAt(i)) {
                case '1':
                    banglaNumber += context.getString(R.string.one);
                    break;
                case '2':
                    banglaNumber += context.getString(R.string.two);
                    break;
                case '3':
                    banglaNumber += context.getString(R.string.three);
                    break;
                case '4':
                    banglaNumber += context.getString(R.string.four);
                    break;
                case '5':
                    banglaNumber += context.getString(R.string.five);
                    break;
                case '6':
                    banglaNumber += context.getString(R.string.six);
                    break;
                case '7':
                    banglaNumber += context.getString(R.string.seven);
                    break;
                case '8':
                    banglaNumber += context.getString(R.string.eight);
                    break;
                case '9':
                    banglaNumber += context.getString(R.string.nine);
                    break;
                case '0':
                    banglaNumber += context.getString(R.string.zero);
                    break;
                default:
                    banglaNumber += numbers.charAt(i);
                    break;
            }
        }
        return banglaNumber;
    }
    public String humanReadableDateTime(long times){
        long minutes = times/60000;
        String hours =  String.valueOf(minutes/60);
        if(hours.length()==1){
            hours = "0"+hours;
        }
        String mins = String.valueOf(minutes%60);
        if(mins.length()==1){
            mins = ")"+mins;
        }
        String secs = String.valueOf((times/1000)%60);
        if(secs.length()==1){
            secs = "0"+secs;
        }
        String value = hours+":"+mins+":"+secs;
        return value;
    }


    public HashMap<String, Integer> getScreenRes() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        map.put("width", width);
        map.put("height", height);
        //map.put(KeyWord.SCREEN_DENSITY, (int)metrics.density);
        return map;
    }
}
