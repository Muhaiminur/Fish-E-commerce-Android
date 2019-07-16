package com.gtechnologies.fishbangla.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.gtechnologies.fishbangla.API.ApiInterface;
import com.gtechnologies.fishbangla.API.ServiceGenerator;
import com.gtechnologies.fishbangla.API_GET.GET_ADDRESS;
import com.gtechnologies.fishbangla.API_GET.GET_LOGIN;
import com.gtechnologies.fishbangla.API_GET.Get_Division;
import com.gtechnologies.fishbangla.API_GET.Get_Fish_List;
import com.gtechnologies.fishbangla.API_GET.Get_Upozilla;
import com.gtechnologies.fishbangla.API_GET.Get_Zilla;
import com.gtechnologies.fishbangla.API_SEND.Send_Cart;
import com.gtechnologies.fishbangla.API_SEND.Send_Delivery_address;
import com.gtechnologies.fishbangla.API_SEND.Send_Upozilla;
import com.gtechnologies.fishbangla.API_SEND.Send_UserID;
import com.gtechnologies.fishbangla.API_SEND.Send_Zilla;
import com.gtechnologies.fishbangla.Adapter.Cart_Address_Adapter;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.RecyclerTouchListener;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Address_List;
import com.gtechnologies.fishbangla.R;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Buyer_Delivery_Address extends AppCompatActivity {
    @BindView(R.id.delivery_add_address)
    Button deliveryAddAddress;
    @BindView(R.id.delivery_address_recyclerview)
    RecyclerView deliveryAddressRecyclerview;
    @BindView(R.id.buyer_delivery_payment)
    TextView buyerDeliveryPayment;
    MaterialSpinner cartAddressDivision;
    MaterialSpinner cartAddressZilla;
    MaterialSpinner cartAddressUpozilla;


    View custom_view;
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


    Utility utility;
    Context context;
    ApiInterface apiService;
    Send_Cart send_cart;
    GET_ADDRESS get_address;
    private List<GET_ADDRESS> addressList = new ArrayList<>();
    private Cart_Address_Adapter address_adapter;
    List<Get_Division> division = new ArrayList<>();
    List<Get_Zilla> zilla = new ArrayList<>();
    List<Get_Upozilla> upozilla = new ArrayList<>();
    GET_LOGIN login;
    int address_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__delivery__address);
        ButterKnife.bind(this);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_address_string));
            context = Buyer_Delivery_Address.this;
            utility = new Utility(context);
            send_cart = (Send_Cart) getIntent().getSerializableExtra("cart_data");
            apiService = ServiceGenerator.createService(ApiInterface.class, "admin", "fishbangla@gtech");
            font_setup();
            login = (GET_LOGIN) Stash.getObject("user", GET_LOGIN.class);
            address_adapter = new Cart_Address_Adapter(addressList, context);
            deliveryAddressRecyclerview.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            deliveryAddressRecyclerview.setLayoutManager(mLayoutManager);
            deliveryAddressRecyclerview.setAdapter(address_adapter);
            //Prepare_address_data();
            //Fish_Division_Get();
            if (login.getId() != null) {
                Profile_Address(String.valueOf(login.getId()));
            }
            spinner_work();
            deliveryAddressRecyclerview.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), deliveryAddressRecyclerview, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    get_address = addressList.get(position);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void font_setup() {
        try {
            utility.setFonts_Bold(
                    new View[]{
                    });
            utility.setFonts_normal(
                    new View[]{
                            deliveryAddAddress,
                            buyerDeliveryPayment
                    }, Fonts.SMALL);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    /*public void Prepare_address_data() {
        for (int c = 0; c < 6; c++) {
            Address_List p = new Address_List("Name " + c, "Number " + c, "Road " + c, "zilla " + c);
            addressList.add(p);
        }
        address_adapter.notifyDataSetChanged();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
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

    @OnClick({R.id.delivery_add_address, R.id.buyer_delivery_payment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delivery_add_address:
                showCustomDialog(context);
                break;
            case R.id.buyer_delivery_payment:
                if (addressList.size() > 0) {
                    if (get_address == null) {
                        get_address = addressList.get(0);
                    }
                    send_cart.setAddressId(get_address.getId());
                    send_cart.setUserId(login.getId());
                    utility.logger(get_address.getId() + send_cart.toString());
                    startActivity(new Intent(context, Payment_Activity.class).putExtra("cart_data", send_cart));
                }
                break;
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
                if (login != null && !TextUtils.isEmpty(division_add) && !TextUtils.isEmpty(district_add) && !TextUtils.isEmpty(upazilla_add)) {
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
                    status = login.getStatus();
                    user = String.valueOf(login.getId());
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
                                    if (login.getId() != null) {
                                        Profile_Address(String.valueOf(login.getId()));
                                    }
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
}
