package com.gtechnologies.fishbangla.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.Datum;
import com.gtechnologies.fishbangla.API_GET.GET_FISH_NAME;
import com.gtechnologies.fishbangla.API_GET.GET_LOGIN;
import com.gtechnologies.fishbangla.API_GET.Get_Division;
import com.gtechnologies.fishbangla.API_GET.Get_Upozilla;
import com.gtechnologies.fishbangla.API_GET.Get_Zilla;
import com.gtechnologies.fishbangla.API_SEND.Send_Product;
import com.gtechnologies.fishbangla.API_SEND.Send_Upozilla;
import com.gtechnologies.fishbangla.API_SEND.Send_Zilla;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Product_Model;
import com.gtechnologies.fishbangla.Model.Tag_List;
import com.gtechnologies.fishbangla.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Upload_Step1 extends Fragment {


    @BindView(R.id.upload_next1)
    Button uploadNext1;
    Unbinder unbinder;
    @BindView(R.id.select_fish)
    MaterialSpinner selectFish;
    @BindView(R.id.fish_description)
    EditText fishDescription;
    @BindView(R.id.fish_category)
    MaterialSpinner fishCategory;
    @BindView(R.id.fish_price)
    EditText fishPrice;
    @BindView(R.id.fish_quantity)
    EditText fishQuantity;
    @BindView(R.id.fish_minimum_quantity)
    EditText fishMinimumQuantity;
    @BindView(R.id.fish_upozilla)
    MaterialSpinner fishUpozilla;
    @BindView(R.id.fish_sell_type)
    TextView fishSellType;
    @BindView(R.id.fish_sell_retail)
    RadioButton fishSellRetail;
    @BindView(R.id.fish_sell_wholesell)
    RadioButton fishSellWholesell;
    @BindView(R.id.fish_sell_type_cat)
    RadioGroup fishSellTypeCat;
    @BindView(R.id.fish_frozen_title)
    TextView fishFrozenTitle;
    @BindView(R.id.fish_frozen_yes)
    RadioButton fishFrozenYes;
    @BindView(R.id.fish_frozen_no)
    RadioButton fishFrozenNo;
    @BindView(R.id.fish_frozen_grp)
    RadioGroup fishFrozenGrp;
    @BindView(R.id.fish_division)
    MaterialSpinner fishDivision;
    @BindView(R.id.fish_zilla)
    MaterialSpinner fishZilla;

    public Fragment_Upload_Step1() {
        // Required empty public constructor
    }

    View view;
    Utility utility;
    Context context;
    ApiInterface apiService;
    GET_LOGIN login;
    ViewPager pager;

    List<Get_Division> division = new ArrayList<>();
    List<Get_Zilla> zilla = new ArrayList<>();
    List<Get_Upozilla> upozilla = new ArrayList<>();
    List<Datum> name_list = new ArrayList<>();
    List<Tag_List> tag_lists = new ArrayList<>();
    String division_add = "";
    String district_add = "";
    String upazilla_add = "";
    String product_id = "";
    String tagid = "";

    String product_name = "";
    String tag_name = "";
    String upozilla_name="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment__upload__step1, container, false);
        unbinder = ButterKnife.bind(this, view);
        pager = (ViewPager) getActivity().findViewById(R.id.upload_viewPager);
        try {
            context = getActivity();
            utility = new Utility(context);
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            login = (GET_LOGIN) Stash.getObject("user", GET_LOGIN.class);
            fishselect_spinner();
            //Fish_Division_Get();
            Fish_List_Get();
            Stash.clear("product");
            Stash.clear("product_hint");
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

    @OnClick(R.id.upload_next1)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(tagid) && !TextUtils.isEmpty(product_id) && !TextUtils.isEmpty(upazilla_add)) {
            String froz = "no";
            String froz_full=getResources().getString(R.string.no_string);
            if (fishFrozenGrp.getCheckedRadioButtonId() == R.id.fish_frozen_yes) {
                froz = "yes";
                froz_full=getResources().getString(R.string.yes_string);
            }
            String type = "retail";
            String type_full=getResources().getString(R.string.homepage_retail_string);
            if (fishSellTypeCat.getCheckedRadioButtonId() == R.id.fish_sell_wholesell) {
                type = "wholesale";
                type_full=getResources().getString(R.string.homepage_wholesale_string);
            }
            Send_Product send_product = new Send_Product(product_id, fishDescription.getText().toString(), fishQuantity.getText().toString(), fishMinimumQuantity.getText().toString(), fishPrice.getText().toString(), "yes", froz, "no", type, tagid, String.valueOf(login.getId()), upazilla_add);
            utility.logger(send_product.toString());

            Product_Model productModel = new Product_Model(product_name,tag_name, upozilla_name,type_full,froz_full);
            Stash.put("product", send_product);
            Stash.put("product_hint", productModel);
            pager.setCurrentItem(1);
        } else {
            utility.logger(getResources().getString(R.string.validation_string));
        }
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                    });
            utility.setFonts_normal(
                    new View[]{
                            uploadNext1,
                            fishDescription,
                            fishPrice,
                            fishQuantity,
                            fishMinimumQuantity,
                            fishSellType,
                            fishSellRetail,
                            fishSellWholesell,
                            fishFrozenTitle,
                            fishFrozenYes,
                            fishFrozenNo
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

    public void fishselect_spinner() {
        String[] ITEMS = {};
        ArrayAdapter<String> spiner_adapter = new ArrayAdapter<String>(context, R.layout.custom_item_spinner_selected, ITEMS);
        spiner_adapter.setDropDownViewResource(R.layout.custom_item_spinner);
        fishDivision.setAdapter(spiner_adapter);
        fishZilla.setAdapter(spiner_adapter);
        fishUpozilla.setAdapter(spiner_adapter);
        selectFish.setAdapter(spiner_adapter);
        fishCategory.setAdapter(spiner_adapter);
        fishDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("Division", (String) parent.getItemAtPosition(position));
                for (Get_Division div : division) {
                    if (div.getDivisionBn().equals(parent.getItemAtPosition(position))) {
                        Log.v("Division", div.getId() + "");
                        division_add = String.valueOf(div.getId());
                        Fish_zilla_Get(String.valueOf(div.getId()));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fishZilla.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("Zilla", (String) parent.getItemAtPosition(position));
                for (Get_Zilla zil : zilla) {
                    if (zil.getDistrictBn().equals(parent.getItemAtPosition(position))) {
                        Log.v("Zilla", zil.getId() + "");
                        district_add = String.valueOf(zil.getId());
                        Fish_UPOzilla_Get(String.valueOf(zil.getId()));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fishUpozilla.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("upoZilla", (String) parent.getItemAtPosition(position));
                for (Get_Upozilla zil : upozilla) {
                    if (zil.getUpazillaBn().equals(parent.getItemAtPosition(position))) {
                        Log.v("UpoZilla", zil.getId() + "");
                        upazilla_add = String.valueOf(zil.getId());
                        upozilla_name=String.valueOf(zil.getUpazillaBn());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectFish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("fish", (String) parent.getItemAtPosition(position));
                for (Datum zil : name_list) {
                    if (zil.getNameBn().equals(parent.getItemAtPosition(position))) {
                        Log.v("fish", zil.getId() + "");
                        product_id = String.valueOf(zil.getId());
                        product_name = String.valueOf(zil.getNameBn());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fishCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("Tag id", (String) parent.getItemAtPosition(position));
                for (Tag_List zil : tag_lists) {
                    if (zil.getNameBn().equals(parent.getItemAtPosition(position))) {
                        Log.v("Tag ID", zil.getId() + "");
                        tagid = String.valueOf(zil.getId());
                        tag_name = String.valueOf(zil.getNameBn());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void Fish_List_Get() {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (true) {
                utility.showProgress(false);
                Log.d("Fish Name", " Name");
                Call<JsonElement> call = apiService.FishBangla_Fish_list();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Fish Name", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                GET_FISH_NAME get_fish_name = gson.fromJson(response.body(), GET_FISH_NAME.class);
                                Log.d("Fish Name response", get_fish_name.getData().size() + "");
                                if (get_fish_name.getData().size() > 0) {
                                    name_list.clear();
                                    name_list.addAll(get_fish_name.getData());
                                    String[] div = new String[name_list.size()];
                                    int index = 0;
                                    for (Datum value : name_list) {
                                        div[index] = (String) value.getNameBn();
                                        index++;
                                    }
                                    ArrayAdapter<String> division_adapter = new ArrayAdapter<String>(context, R.layout.custom_item_spinner_selected, div);
                                    division_adapter.setDropDownViewResource(R.layout.custom_item_spinner);
                                    selectFish.setAdapter(division_adapter);
                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.no_data_string), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                        }
                        Tag_List_Get();
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error Division List", t.toString());
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

    public void Tag_List_Get() {
        try {
            if (!utility.isNetworkAvailable()) {
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
                                    String[] div = new String[tag_lists.size()];
                                    int index = 0;
                                    for (Tag_List value : tag_lists) {
                                        div[index] = (String) value.getNameBn();
                                        index++;
                                    }
                                    ArrayAdapter<String> division_adapter = new ArrayAdapter<String>(context, R.layout.custom_item_spinner_selected, div);
                                    division_adapter.setDropDownViewResource(R.layout.custom_item_spinner);
                                    fishCategory.setAdapter(division_adapter);
                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                        }
                        Fish_Division_Get();
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
    }

    public void Fish_Division_Get() {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (true) {
                utility.showProgress(false);
                Log.d("Division", "Division List");
                Call<JsonElement> call = apiService.FishBangla_Division_List();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Division List", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Get_Division>>() {
                                }.getType();
                                List<Get_Division> fish_list_Response = gson.fromJson(response.body(), listType);
                                Log.d("Division List response", fish_list_Response.size() + "");
                                if (fish_list_Response.size() > 0) {
                                    division.clear();
                                    division.addAll(fish_list_Response);
                                    String[] div = new String[division.size()];
                                    int index = 0;
                                    for (Get_Division value : division) {
                                        div[index] = (String) value.getDivisionBn();
                                        index++;
                                    }
                                    ArrayAdapter<String> division_adapter = new ArrayAdapter<String>(context, R.layout.custom_item_spinner_selected, div);
                                    division_adapter.setDropDownViewResource(R.layout.custom_item_spinner);
                                    fishDivision.setAdapter(division_adapter);
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
                        Log.e("Error Division List", t.toString());
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

    public void Fish_zilla_Get(String id) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(id)) {
                utility.showProgress(false);
                Send_Zilla send_zilla = new Send_Zilla(id);
                Log.d("Zilla", "ID " + send_zilla.getDivision_id());
                Call<JsonElement> call = apiService.FishBangla_zilla_List(send_zilla);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Zilla List", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                //Log.d("Json",response.body().toString());
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Get_Zilla>>() {
                                }.getType();
                                List<Get_Zilla> zilla_response = gson.fromJson(response.body(), listType);
                                Log.d("Zilla List response", zilla_response.size() + "");
                                if (zilla_response.size() > 0) {
                                    zilla.clear();
                                    zilla.addAll(zilla_response);
                                    String[] zil = new String[zilla_response.size()];
                                    int index = 0;
                                    for (Get_Zilla value : zilla_response) {
                                        zil[index] = (String) value.getDistrictBn();
                                        index++;
                                    }
                                    ArrayAdapter<String> zilla_adapter = new ArrayAdapter<String>(context, R.layout.custom_item_spinner_selected, zil);
                                    zilla_adapter.setDropDownViewResource(R.layout.custom_item_spinner);
                                    fishZilla.setAdapter(zilla_adapter);
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
                        Log.e("Error Division List", t.toString());
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

    public void Fish_UPOzilla_Get(String id) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(id)) {
                utility.showProgress(false);
                Send_Upozilla sendUpozilla = new Send_Upozilla(id);
                Log.d("sendUpozilla", "ID " + sendUpozilla.getDistrict_id());
                Call<JsonElement> call = apiService.FishBangla_Upozilla_List(sendUpozilla);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("sendUpozilla List", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                //Log.d("Json",response.body().toString());
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Get_Upozilla>>() {
                                }.getType();
                                List<Get_Upozilla> upozilla_response = gson.fromJson(response.body(), listType);
                                Log.d("Upozilla List response", upozilla_response.size() + "");
                                if (upozilla_response.size() > 0) {
                                    upozilla.clear();
                                    upozilla.addAll(upozilla_response);
                                    String[] zil = new String[upozilla_response.size()];
                                    int index = 0;
                                    for (Get_Upozilla value : upozilla_response) {
                                        zil[index] = (String) value.getUpazillaBn();
                                        index++;
                                    }
                                    ArrayAdapter<String> zilla_adapter = new ArrayAdapter<String>(context, R.layout.custom_item_spinner_selected, zil);
                                    zilla_adapter.setDropDownViewResource(R.layout.custom_item_spinner);
                                    fishUpozilla.setAdapter(zilla_adapter);
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
                        Log.e("Error Division List", t.toString());
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
