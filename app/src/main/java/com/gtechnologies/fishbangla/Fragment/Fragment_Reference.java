package com.gtechnologies.fishbangla.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.GET_LOGIN;
import com.gtechnologies.fishbangla.API_GET.GET_REFERLIST;
import com.gtechnologies.fishbangla.API_GET.Get_Fish_List;
import com.gtechnologies.fishbangla.API_SEND.Send_UserID;
import com.gtechnologies.fishbangla.Adapter.Buy_History_Adapter;
import com.gtechnologies.fishbangla.Adapter.Reference_Adapter;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Buy_History_List;
import com.gtechnologies.fishbangla.Model.Reference_List;
import com.gtechnologies.fishbangla.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Reference extends Fragment {


    @BindView(R.id.referel_recyclerview)
    RecyclerView referelRecyclerview;
    Unbinder unbinder;

    public Fragment_Reference() {
        // Required empty public constructor
    }


    View view;
    Utility utility;
    ApiInterface apiService;
    Context context;
    GET_LOGIN getLogin;
    private List<GET_REFERLIST> refer_list = new ArrayList<>();
    private Reference_Adapter referAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment__reference, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            context = getActivity();
            utility = new Utility(context);
            getLogin = utility.islogged();
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            referAdapter = new Reference_Adapter(refer_list, context);
            referelRecyclerview.setHasFixedSize(true);
            //LinearLayout mLayoutManager = new GridLayoutManager(context, 2);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            referelRecyclerview.setLayoutManager(mLayoutManager);
            referelRecyclerview.setAdapter(referAdapter);
            //Prepare_history_data();
            if (getLogin != null) {
                Seller_Reffer_List(String.valueOf(getLogin.getId()));
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                    });
            utility.setFonts_normal(
                    new View[]{
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

    /*public void Prepare_history_data() {
        for (int c = 0; c < 6; c++) {
            Reference_List p = new Reference_List("url","Name " + c, "Date " + c);
            refer_list.add(p);
        }
        referAdapter.notifyDataSetChanged();
    }*/

    public void Seller_Reffer_List(String f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(f)) {
                utility.showProgress(false);
                Send_UserID userID = new Send_UserID(f);
                Log.d("Getting", "Seller Reffer List" + userID.toString());
                Call<JsonElement> call = apiService.FishBangla_Refer_List(userID);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Seller Reffer List", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<GET_REFERLIST>>() {
                                }.getType();
                                List<GET_REFERLIST> fish_list_Response = gson.fromJson(response.body(), listType);
                                Log.d("Seller Reffer List", fish_list_Response.size() + "");
                                if (fish_list_Response.size() > 0) {
                                    refer_list.clear();
                                    refer_list.addAll(fish_list_Response);
                                    referAdapter.notifyDataSetChanged();
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
                        Log.e("Error Reffer List", t.toString());
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
}
