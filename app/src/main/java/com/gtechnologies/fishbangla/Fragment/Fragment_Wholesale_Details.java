package com.gtechnologies.fishbangla.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.Activity.SearchActivity;
import com.gtechnologies.fishbangla.Activity.Wholesale_Auction;
import com.gtechnologies.fishbangla.Adapter.ProductListAdapter;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Wholesale_Details extends Fragment {


    @BindView(R.id.wholesale_auction)
    ImageButton wholesaleAuction;
    @BindView(R.id.actionbar_notifcation_textview)
    TextView actionbarNotifcationTextview;
    @BindView(R.id.spinner)
    Spinner spinner;
    Unbinder unbinder;
    @BindView(R.id.wholesale_fish)
    ImageButton wholesaleFish;

    public Fragment_Wholesale_Details() {
        // Required empty public constructor
    }


    View view;
    Utility utility;
    Context context;
    ApiInterface apiService;
    ProductListAdapter productListAdapter;
    JSONArray jsonArray;

    String sale_type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment__wholesale__details, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            context = getActivity();
            utility = new Utility(context);
            font_setup();
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            Bundle bundle = getArguments();
            if (bundle != null) {
                sale_type = bundle.getString("sale_type");
                Log.d("wholesale details", sale_type);
            } else {
                utility.logger("no bundle");
            }
            /*getProductList();
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    if (jsonObject.optInt("id") != 0) {
                        //utility.showToast(jsonObject.optString("nameBn"));
                        Intent intent = new Intent(context, SearchActivity.class);
                        intent.putExtra("value", jsonObject.toString());
                        context.startActivity(intent);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });*/
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return view;
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                    });
            utility.setFonts_normal(
                    new View[]{
                            actionbarNotifcationTextview
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void Replace_Fragment(Fragment frag) {
        try {
            if (frag != null) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.home_area, frag);
                ft.addToBackStack(null);
                ft.commit();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getProductList() {
        utility.showProgress(true);
        Call<ResponseBody> call = apiService.getProductList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                utility.hideProgress();
                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        jsonArray = new JSONArray();
                        JSONObject object = new JSONObject();
                        object.put("id", 0);
                        object.put("nameBn", "এখানে অনুসন্ধান করুন");
                        jsonArray.put(object);
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray array = jsonObject.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            jsonArray.put(array.optJSONObject(i));
                        }
                        productListAdapter = new ProductListAdapter(context, jsonArray);
                        spinner.setAdapter(productListAdapter);
                    } catch (Exception ex) {
                        //do nothing
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                utility.hideProgress();
            }
        });
    }

    @OnClick({R.id.wholesale_auction, R.id.wholesale_fish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wholesale_auction:
                startActivity(new Intent(context, Wholesale_Auction.class));
                break;
            case R.id.wholesale_fish:
                try {
                    Bundle send_sale = new Bundle();
                    send_sale.putString("sale_type", sale_type);
                    Fragment home = new Fragment_Tags();
                    home.setArguments(send_sale);
                    Replace_Fragment(home);
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
                break;
        }
    }
}
