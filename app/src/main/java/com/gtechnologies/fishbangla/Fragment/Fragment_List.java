package com.gtechnologies.fishbangla.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.Get_Fish_List;
import com.gtechnologies.fishbangla.API_SEND.Send_List;
import com.gtechnologies.fishbangla.Adapter.FISHLIST_Adapter;
import com.gtechnologies.fishbangla.Adapter.Fish_List_Adapter;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Fish_List;
import com.gtechnologies.fishbangla.Model.Tag_List;
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
public class Fragment_List extends Fragment {


    @BindView(R.id.fish_list_search)
    EditText fishListSearch;
    @BindView(R.id.fish_list_recyclerview)
    RecyclerView fishListRecyclerview;
    Unbinder unbinder;

    public Fragment_List() {
        // Required empty public constructor
    }


    View view;
    Utility utility;
    Context context;
    String tag_id;
    String sale_type;
    ApiInterface apiService;
    private List<Get_Fish_List> productList = new ArrayList<>();
    private FISHLIST_Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment__list, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            context = getActivity();
            utility = new Utility(context);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            Bundle bundle = getArguments();
            if (bundle != null) {
                tag_id = bundle.getInt("tagid") + "";
                sale_type = bundle.getString("sale_type");
                Log.d("Product List", tag_id + " = " + sale_type);
            } else {
                utility.logger("no bundle");
            }
            font_setup();

            mAdapter = new FISHLIST_Adapter(productList, getActivity());
            fishListRecyclerview.setHasFixedSize(true);
            GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            fishListRecyclerview.setLayoutManager(mLayoutManager);
            fishListRecyclerview.setAdapter(mAdapter);
            //Prepare_fish_data();
            Fish_List_Get("all", sale_type/*"wholesale"*/, tag_id);
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
                            fishListSearch
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

    /*public void Prepare_fish_data() {
        for (int c = 0; c < 10; c++) {
            Fish_List p = new Fish_List("url" + c, "0" + c, "Location" + c, "Category" + c);
            productList.add(p);
        }
        mAdapter.notifyDataSetChanged();
    }*/

    public void Fish_List_Get(String f, String s, String t) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(f) && !TextUtils.isEmpty(s) && !TextUtils.isEmpty(t)) {
                utility.showProgress(false);
                Send_List send_list = new Send_List(f, s, t);
                Log.d("Getting", "Fish List" + send_list.toString());
                Call<JsonElement> call = apiService.FishBangla_Fish_List(send_list);
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
}
