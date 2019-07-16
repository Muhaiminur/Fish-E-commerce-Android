package com.gtechnologies.fishbangla.Fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_SEND.SEND_AUCTION_AMOUNT;
import com.gtechnologies.fishbangla.API_SEND.SEND_TAG;
import com.gtechnologies.fishbangla.Activity.Auction_Confirmation;
import com.gtechnologies.fishbangla.Activity.Cart_Activity;
import com.gtechnologies.fishbangla.Activity.SearchActivity;
import com.gtechnologies.fishbangla.Adapter.ProductListAdapter;
import com.gtechnologies.fishbangla.Adapter.Tag_List_Adapter;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Tag_List;
import com.gtechnologies.fishbangla.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class Fragment_Tags extends Fragment {


    /*@BindView(R.id.tag_list_search)
    EditText tagListSearch;*/
    @BindView(R.id.tag_list_grid)
    GridView tagListGrid;
    @BindView(R.id.spinner)
    Spinner spinner;
    Unbinder unbinder;

    public Fragment_Tags() {
        // Required empty public constructor
    }

    View view;
    Utility utility;
    Context context;
    ApiInterface apiService;
    List<Tag_List> tag_lists = new ArrayList<>();
    Tag_List_Adapter tag_adapter;
    ProductListAdapter productListAdapter;
    JSONArray jsonArray;

    String sale_type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment__tags, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            context = getActivity();
            utility = new Utility(context);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            Bundle bundle = getArguments();
            if (bundle != null) {
                sale_type = bundle.getString("sale_type");
                Log.d("Fragment Tag", sale_type);
            } else {
                utility.logger("no bundle");
            }
            font_setup();
            Load_Tag_list_Internet();
            Tag_List_Get();
            /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    /*public void Load_Tag_list() {
        Gson gson = new Gson();
        Tag_List[] list = gson.fromJson(utility.loadJSONFromAsset("tag"), Tag_List[].class);
        Log.d("Tag_list", list.length + "");
        tag_lists = new ArrayList<>(Arrays.asList(list));
        // Create an object of CustomAdapter and set Adapter to GirdView
        Tag_List_Adapter customAdapter = new Tag_List_Adapter(getActivity(), tag_lists);
        tagListGrid.setAdapter(customAdapter);
        tagListGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Log.d("Gridview", "Found notok" + tag_lists.get(position).getId());
                try {
                    Bundle args = new Bundle();
                    if (tag_lists.size() > 0) {
                        args.putString("tagid", tag_lists.get(position).getId() + "");
                    }
                    Fragment home = new Fragment_List();
                    home.setArguments(args);
                    Replace_Fragment(home);
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }
        });
    }*/

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
                ft.commit();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    /*@OnClick(R.id.tags_cart)
    public void onViewClicked() {
        Log.d("Check", "CHeck");
        startActivity(new Intent(context, Cart_Activity.class));
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public boolean isConnected() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null;
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return false;
    }

    public void Load_Tag_list_Internet() {
        tag_adapter = new Tag_List_Adapter(getActivity(), tag_lists, sale_type);
        tagListGrid.setAdapter(tag_adapter);
        tagListGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Gridview", "Found");
                try {
                    Bundle send_id = new Bundle();
                    if (tag_lists.size() > 0) {
                        send_id.putInt("tagid", tag_lists.get(position).getId());
                        send_id.putString("sale_type", sale_type);
                        utility.logger("bundle" + tag_lists.get(position).getId());
                    }
                    Fragment home = new Fragment_List();
                    home.setArguments(send_id);
                    Replace_Fragment(home);
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }
        });
    }

    /*public void Tag_List_Get() {
        try {
            if (!isConnected()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (true) {
                utility.showProgress(false);
                Log.d("Getting", "Tag List");
                Call<JsonElement> call = apiService.FishBangla_Tag_List();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Tag List", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Tag_List>>() {
                                }.getType();
                                List<Tag_List> tag_list_Response = gson.fromJson(response.body(), listType);
                                Log.d("Tag List respose", tag_list_Response.size() + "");
                                if (tag_list_Response.size() > 0) {
                                    tag_lists.clear();
                                    tag_lists.addAll(tag_list_Response);
                                    tag_adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
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
                        Log.e("Error Tag List", t.toString());
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
    }*/
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

    public void Tag_List_Get() {
        try {
            if (!isConnected()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (true) {
                utility.showProgress(true);
                Log.d("Getting", "Tag List");
                Call<JsonElement> call = apiService.FishBangla_Tag_List();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Tag List", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Tag_List>>() {
                                }.getType();
                                List<Tag_List> tag_list_Response = gson.fromJson(response.body(), listType);
                                Log.d("Tag List respose", tag_list_Response.size() + "");
                                if (tag_list_Response.size() > 0) {
                                    tag_lists.clear();
                                    tag_lists.addAll(tag_list_Response);
                                    tag_adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                        }
                        //getProductList();
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error Tag List", t.toString());
                        Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
                        utility.hideProgress();
                        //getProductList();
                    }
                });
            } else {
                Toast.makeText(context, getResources().getString(R.string.something_went_string), Toast.LENGTH_LONG).show();
                //getProductList();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            utility.hideProgress();
            //getProductList();
        }
    }
}
