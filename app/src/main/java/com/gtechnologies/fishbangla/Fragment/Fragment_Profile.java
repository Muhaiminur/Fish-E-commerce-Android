package com.gtechnologies.fishbangla.Fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.GET_ADDRESS;
import com.gtechnologies.fishbangla.API_GET.GET_LOGIN;
import com.gtechnologies.fishbangla.API_GET.GET_USER_PROFILE;
import com.gtechnologies.fishbangla.API_GET.Get_Division;
import com.gtechnologies.fishbangla.API_GET.Get_Fish_List;
import com.gtechnologies.fishbangla.API_GET.Get_Upozilla;
import com.gtechnologies.fishbangla.API_GET.Get_Zilla;
import com.gtechnologies.fishbangla.API_SEND.Send_Delivery_address;
import com.gtechnologies.fishbangla.API_SEND.Send_Update_Profile;
import com.gtechnologies.fishbangla.API_SEND.Send_Upozilla;
import com.gtechnologies.fishbangla.API_SEND.Send_UserID;
import com.gtechnologies.fishbangla.API_SEND.Send_Zilla;
import com.gtechnologies.fishbangla.Activity.Profile_Product_List;
import com.gtechnologies.fishbangla.Adapter.Address_Adapter;
import com.gtechnologies.fishbangla.Adapter.Fish_List_Adapter_Rv;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Address_List;
import com.gtechnologies.fishbangla.R;
import com.karan.churi.PermissionManager.PermissionManager;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fr.ganfra.materialspinner.MaterialSpinner;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Profile extends Fragment {


    @BindView(R.id.user_profile_image)
    CircularImageView userProfileImage;
    @BindView(R.id.profile_image_edit)
    Button profileImageEdit;
    @BindView(R.id.profile_name)
    TextView profileName;
    @BindView(R.id.profile_email)
    TextView profileEmail;
    @BindView(R.id.profile_mobile)
    TextView profileMobile;
    @BindView(R.id.profile_dob)
    TextView profileDob;
    @BindView(R.id.profile_sex)
    TextView profileSex;
    @BindView(R.id.profile_details_edit)
    Button profileDetailsEdit;
    @BindView(R.id.profile_cardview)
    CardView profileCardview;
    @BindView(R.id.profile_recyclerview)
    RecyclerView profileRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.profile_more_product)
    Button profileMoreProduct;
    @BindView(R.id.profile_add_address)
    Button profileAddAddress;
    @BindView(R.id.profile_address_recyclerview)
    RecyclerView profileAddressRecyclerview;
    @BindView(R.id.product_card)
    CardView productCard;

    public Fragment_Profile() {
        // Required empty public constructor
    }


    //
    MaterialSpinner cartAddressDivision;
    MaterialSpinner cartAddressZilla;
    MaterialSpinner cartAddressUpozilla;
    View custom_view;
    View update_view;
    AutoCompleteTextView cartAddressName;
    AutoCompleteTextView cartAddressMbl;
    AutoCompleteTextView cartAddressAddress;
    AutoCompleteTextView cartAddressVillage;
    AutoCompleteTextView cartAddressLandmark;
    Button cartAddressAdd;
    Button cartAddressCancel;
    Dialog dialog;
    String receiversName = "";
    String receiversContact = "";
    String address = "";
    String village = "";
    String nearbyLandmark = "";
    String profession = "";
    String latitude = "";
    String longitude = "";
    String status = "";
    String user = "";
    String division_add = "";
    String district_add = "";
    String upazilla_add = "";

    List<Get_Division> division = new ArrayList<>();
    List<Get_Zilla> zilla = new ArrayList<>();
    List<Get_Upozilla> upozilla = new ArrayList<>();

    //


    View view;
    Utility utility;
    ApiInterface apiService;
    Context context;
    GET_LOGIN getLogin;
    PermissionManager permissionManager;
    private List<Get_Fish_List> productList = new ArrayList<>();
    private Fish_List_Adapter_Rv product_adapter;
    private List<GET_ADDRESS> addressList = new ArrayList<>();
    private Address_Adapter address_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment__profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            context = getActivity();
            utility = new Utility(context);
            getLogin = utility.islogged();
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            permissionManager = new PermissionManager() {
            };
            product_adapter = new Fish_List_Adapter_Rv(productList, context);
            address_adapter = new Address_Adapter(addressList, context);
            profileRecyclerview.setHasFixedSize(true);
            profileAddressRecyclerview.setHasFixedSize(true);
            //LinearLayout mLayoutManager = new GridLayoutManager(context, 2);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            profileRecyclerview.setLayoutManager(mLayoutManager);
            profileAddressRecyclerview.setLayoutManager(mLayoutManager2);
            profileRecyclerview.setAdapter(product_adapter);
            profileAddressRecyclerview.setAdapter(address_adapter);
            //Prepare_fish_data();
            if (getLogin != null) {
                Seller_Fish_List(String.valueOf(getLogin.getId()));
            }
            //Prepare_address_data();
            spinner_work();
            update_work();
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
                            profileImageEdit,
                            profileName,
                            profileEmail,
                            profileMobile,
                            profileDob,
                            profileSex,
                            profileDetailsEdit,
                            profileMoreProduct,
                            profileAddAddress
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

    /*public void Prepare_address_data() {
        for (int c = 0; c < 6; c++) {
            Address_List p = new Address_List("Name " + c, "Number " + c, "Road " + c, "zilla " + c);
            addressList.add(p);
        }
        address_adapter.notifyDataSetChanged();
    }*/

    @OnClick({R.id.profile_image_edit, R.id.profile_details_edit, R.id.profile_more_product, R.id.profile_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_image_edit:
                try {
                    if (checkPermission()) {
                        EasyImage.openChooserWithGallery(Fragment_Profile.this, "CHOOSE IMAGE", 0);
                    } else {
                        permissionManager.checkAndRequestPermissions(getActivity());
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
                break;
            case R.id.profile_details_edit:
                showupdateDialog(context);
                break;
            case R.id.profile_more_product:
                startActivity(new Intent(context, Profile_Product_List.class));
                break;
            case R.id.profile_add_address:
                //address_input_alert();
                showCustomDialog(context);
                break;
        }
    }

    public void showCustomDialog(final Context context) {
        try {
            if (dialog != null) {
                utility.logger("not null");
                if (custom_view != null) {
                    ViewGroup parent = (ViewGroup) custom_view.getParent();
                    if (parent != null) {
                        parent.removeAllViews();
                    }
                }
            }
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.setContentView(custom_view);
            final Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.app_blue);
            window.setGravity(Gravity.CENTER);
            ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
            InsetDrawable inset = new InsetDrawable(back, 20);
            dialog.getWindow().setBackgroundDrawable(inset);
            dialog.show();
            Fish_Division_Get();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void spinner_work() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        custom_view = inflater.inflate(R.layout.custom_address_input, null, false);
        cartAddressName = custom_view.findViewById(R.id.cart_address_name);
        cartAddressMbl = custom_view.findViewById(R.id.cart_address_mbl);
        cartAddressAddress = custom_view.findViewById(R.id.cart_address_address);
        cartAddressVillage = custom_view.findViewById(R.id.cart_address_village);
        cartAddressLandmark = custom_view.findViewById(R.id.cart_address_landmark);
        cartAddressAdd = custom_view.findViewById(R.id.cart_address_add);
        cartAddressCancel = custom_view.findViewById(R.id.cart_address_cancel);
        cartAddressDivision = custom_view.findViewById(R.id.cart_address_division);
        cartAddressZilla = custom_view.findViewById(R.id.cart_address_zilla);
        cartAddressUpozilla = custom_view.findViewById(R.id.cart_address_upozilla);
        cartAddressAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getLogin != null && !TextUtils.isEmpty(division_add) && !TextUtils.isEmpty(district_add) && !TextUtils.isEmpty(upazilla_add)) {
                    if (!TextUtils.isEmpty(cartAddressName.getText().toString())) {
                        receiversName = cartAddressName.getText().toString();
                    }
                    if (!TextUtils.isEmpty(cartAddressMbl.getText().toString())) {
                        receiversContact = cartAddressMbl.getText().toString();
                    }
                    if (!TextUtils.isEmpty(cartAddressAddress.getText().toString())) {
                        address = cartAddressAddress.getText().toString();
                    }
                    if (!TextUtils.isEmpty(cartAddressVillage.getText().toString())) {
                        village = cartAddressVillage.getText().toString();
                    }
                    if (!TextUtils.isEmpty(cartAddressLandmark.getText().toString())) {
                        nearbyLandmark = cartAddressLandmark.getText().toString();
                    }
                    status = getLogin.getStatus();
                    user = String.valueOf(getLogin.getId());
                    Send_Delivery_address deliveryAddress = new Send_Delivery_address(receiversName, receiversContact, address, village, nearbyLandmark, profession, latitude, longitude, status, user, division_add, district_add, upazilla_add);
                    Add_address(deliveryAddress);
                } else {
                    utility.showToast(context.getResources().getString(R.string.validation_string));
                }
            }
        });
        cartAddressCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }
        });
        String[] ITEMS = {};
        ArrayAdapter<String> spiner_adapter = new ArrayAdapter<String>(context, R.layout.custom_item_spinner_selected, ITEMS);
        spiner_adapter.setDropDownViewResource(R.layout.custom_item_spinner);
        cartAddressDivision.setAdapter(spiner_adapter);
        cartAddressZilla.setAdapter(spiner_adapter);
        cartAddressUpozilla.setAdapter(spiner_adapter);
        cartAddressDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        cartAddressZilla.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        cartAddressUpozilla.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("upoZilla", (String) parent.getItemAtPosition(position));
                for (Get_Upozilla zil : upozilla) {
                    if (zil.getUpazillaBn().equals(parent.getItemAtPosition(position))) {
                        Log.v("UpoZilla", zil.getId() + "");
                        upazilla_add = String.valueOf(zil.getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void Seller_Fish_List(String f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(f)) {
                utility.showProgress(false);
                Send_UserID userID = new Send_UserID(f);
                Log.d("Getting", "Seller Product List" + userID.toString());
                Call<JsonElement> call = apiService.FishBangla_Seller_Fish_List(userID);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Seller Product List", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<Get_Fish_List>>() {
                                }.getType();
                                List<Get_Fish_List> fish_list_Response = gson.fromJson(response.body(), listType);
                                Log.d("Seller Product List", fish_list_Response.size() + "");
                                if (fish_list_Response.size() > 0) {
                                    productList.clear();
                                    productList.addAll(fish_list_Response);
                                    product_adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.no_data_string), Toast.LENGTH_LONG).show();
                                    productCard.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                            utility.hideProgress();
                        }
                        Seller_Profile(String.valueOf(getLogin.getId()));
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
                                    cartAddressDivision.setAdapter(division_adapter);
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
                                    cartAddressZilla.setAdapter(zilla_adapter);
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
                                    cartAddressUpozilla.setAdapter(zilla_adapter);
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

    public void Add_address(Send_Delivery_address address) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (true) {
                utility.showProgress(false);
                Log.d("Address", address.toString());
                Call<JsonElement> call = apiService.FishBangla_Delivery_address_add(address);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Address", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Log.d("Json", response.body().toString());
                                JSONObject jObj = new JSONObject(response.body().toString());
                                String id = jObj.getString("id");
                                if (id != null) {
                                    utility.showToast(getResources().getString(R.string.cart_address_success_string));
                                    if (dialog.isShowing()) {
                                        dialog.cancel();
                                        dialog.dismiss();
                                    }
                                    Profile_Address(String.valueOf(getLogin.getId()));
                                } else {
                                    utility.showToast(getResources().getString(R.string.try_again_string));
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

    public void Seller_Profile(String f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(f)) {
                utility.showProgress(false);
                Send_UserID userID = new Send_UserID(f);
                Log.d("Getting", "Seller Profile" + userID.toString());
                Call<JsonElement> call = apiService.FishBangla_Seller_Profile(userID);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Seller Profile", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                GET_USER_PROFILE profile = gson.fromJson(response.body(), GET_USER_PROFILE.class);
                                Log.d("Seller Profile", profile.toString());
                                if (profile != null) {
                                    profileName.setText(profile.getName());
                                    profileEmail.setText(profile.getEmail());
                                    profileMobile.setText(profile.getContact());
                                    profileDob.setText(profile.getUpdatedAt());
                                    profileSex.setText("Male");
                                    RequestOptions requestOptions = new RequestOptions();
                                    requestOptions.placeholder(R.drawable.loader);
                                    requestOptions.error(R.drawable.ic_fish);
                                    Glide.with(context).load(context.getResources().getString(R.string.image_base_url) + profile.getPicture()).thumbnail(0.1f).apply(requestOptions).into(userProfileImage);
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
                        Profile_Address(String.valueOf(getLogin.getId()));
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
                utility.hideProgress();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            utility.hideProgress();
        }
    }

    public void Profile_Address(String f) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (!TextUtils.isEmpty(f)) {
                utility.showProgress(false);
                Send_UserID userID = new Send_UserID(f);
                Log.d("Getting", "Address List" + userID.toString());
                Call<JsonElement> call = apiService.FishBangla_Address_List(userID);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Address List", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<GET_ADDRESS>>() {
                                }.getType();
                                List<GET_ADDRESS> fish_list_Response = gson.fromJson(response.body(), listType);
                                Log.d("Address List", fish_list_Response.size() + "");
                                if (fish_list_Response.size() > 0) {
                                    addressList.clear();
                                    addressList.addAll(fish_list_Response);
                                    address_adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, getResources().getString(R.string.no_data_string), Toast.LENGTH_LONG).show();
                                    //productCard.setVisibility(View.GONE);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        permissionManager.checkResult(requestCode, permissions, grantResults);
        if (checkPermission()) {
            EasyImage.openChooserWithGallery(Fragment_Profile.this, "CHOOSE IMAGE", 0);
        } else {
            permissionManager.checkAndRequestPermissions(getActivity());
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "requestCode " + requestCode + ", resultCode " + resultCode);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                if (imageFile != null && getLogin.getId() != null) {
                    Image_Upload(String.valueOf(getLogin.getId()), imageFile);
                } else {
                    utility.logger("Image Null");
                }
            }
        });
    }

    public void Image_Upload(String i, /*String url*/final File url) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (i != null && url != null) {
                utility.showProgress(false);
                Log.d("Profile", "Profile Image" + url.toString());
                //File m = new File(url);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), url);
                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part image_body = MultipartBody.Part.createFormData("userPhoto", url.getName(), requestFile);
                Call<JsonElement> call = apiService.FishBangla_Profile_image_upload(i, image_body);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Profile Image", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Log.d("Json", response.body().toString());
                                JSONObject jObj = new JSONObject(response.body().toString());
                                String id = jObj.getString("code");
                                if (id.equals("200")) {
                                    utility.showToast(getResources().getString(R.string.profile_image_upload_string));
                                    RequestOptions requestOptions = new RequestOptions();
                                    requestOptions.placeholder(R.drawable.loader);
                                    requestOptions.error(R.drawable.ic_default_background_banner);
                                    Glide.with(context).load(url).thumbnail(0.1f).fitCenter().apply(requestOptions).into(userProfileImage);

                                } else {
                                    utility.showToast(getResources().getString(R.string.try_again_string));
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                            Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error Profile Image", t.toString());
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


    public void Profile_Update(Send_Update_Profile i) {
        try {
            if (!utility.isNetworkAvailable()) {
                Toast.makeText(context, getResources().getString(R.string.no_internet_string), Toast.LENGTH_LONG).show();
            } else if (i != null) {
                utility.showProgress(false);
                Log.d("Profile", "Profile Update" + i.toString());
                Call<JsonElement> call = apiService.FishBangla_Profile_Update(i);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.d("Profile Update", response.toString() + "");
                        utility.hideProgress();
                        try {
                            if (response.code() == 200) {
                                Log.d("Json", response.body().toString());
                                JSONObject jObj = new JSONObject(response.body().toString());
                                String id = jObj.getString("id");
                                if (id != null) {
                                    utility.showToast(getResources().getString(R.string.profile_image_upload_string));
                                    if (dialog.isShowing()) {
                                        dialog.cancel();
                                        dialog.dismiss();
                                    }
                                    Seller_Profile(String.valueOf(getLogin.getId()));
                                } else {
                                    utility.showToast(getResources().getString(R.string.try_again_string));
                                }
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                            Toast.makeText(context, getResources().getString(R.string.try_again_string), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("Error Profile Update", t.toString());
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

    public void showupdateDialog(final Context context) {
        try {
            if (dialog != null) {
                utility.logger("not null");
                if (update_view != null) {
                    ViewGroup parent = (ViewGroup) update_view.getParent();
                    if (parent != null) {
                        parent.removeAllViews();
                    }
                }
            }
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            dialog.setContentView(update_view);
            final Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.app_blue);
            window.setGravity(Gravity.CENTER);
            ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
            InsetDrawable inset = new InsetDrawable(back, 20);
            dialog.getWindow().setBackgroundDrawable(inset);
            dialog.show();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void update_work() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        update_view = inflater.inflate(R.layout.custom_profile_update, null, false);
        final AutoCompleteTextView update_name = update_view.findViewById(R.id.profile_update_name);
        final AutoCompleteTextView update_email = update_view.findViewById(R.id.profile_update_email);
        final AutoCompleteTextView update_dob = update_view.findViewById(R.id.profile_update_bday);
        //RadioGroup update_gender = update_view.findViewById(R.id.update_gender_grp);
        Button update_add = update_view.findViewById(R.id.profile_update_add);
        Button update_cancel = update_view.findViewById(R.id.profile_update_cancel);
        if (getLogin != null) {
            update_name.setText(getLogin.getName());
            update_email.setText(getLogin.getEmail());
            update_dob.setText(getLogin.getCreatedAt());
            //update_name.setText(getLogin.getName());
        }
        update_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getLogin != null) {
                    Send_Update_Profile profile = new Send_Update_Profile();
                    if (!TextUtils.isEmpty(update_name.getEditableText().toString())) {
                        profile.setName(update_name.getEditableText().toString());
                    } else {
                        profile.setName("");
                    }
                    if (!TextUtils.isEmpty(update_email.getEditableText().toString())) {
                        profile.setEmail(update_email.getEditableText().toString());
                    } else {
                        profile.setEmail("");
                    }
                    /*if (!TextUtils.isEmpty(update_dob.getEditableText().toString())) {
                        profile.se(update_dob.getEditableText().toString());
                    }else {
                        profile.setEmail("");
                    }*/
                    profile.setUser_id(String.valueOf(getLogin.getId()));
                    profile.setRefer_code("");
                    profile.setContact("");
                    profile.setReference_code("");
                    profile.setBrefere_code("");
                    utility.logger(profile.toString());
                    Profile_Update(profile);
                } else {
                    utility.showToast(context.getResources().getString(R.string.validation_string));
                }
            }
        });
        update_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }
        });
    }
}
