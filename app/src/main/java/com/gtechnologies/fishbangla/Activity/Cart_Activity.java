package com.gtechnologies.fishbangla.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fxn.stash.Stash;
import com.gtechnologies.fishbangla.API_GET.GET_LOGIN;
import com.gtechnologies.fishbangla.API_SEND.Item;
import com.gtechnologies.fishbangla.API_SEND.Send_Cart;
import com.gtechnologies.fishbangla.Adapter.Cart_Adapter;
import com.gtechnologies.fishbangla.Adapter.Reference_Adapter;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Cart_List;
import com.gtechnologies.fishbangla.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Cart_Activity extends AppCompatActivity {
    @BindView(R.id.cart_custom_add)
    LinearLayout cartCustomAdd;
    @BindView(R.id.cart_delivery)
    TextView cartDelivery;
    @BindView(R.id.cart_service_charge)
    TextView cartServiceCharge;
    @BindView(R.id.cart_vat)
    TextView cartVat;
    @BindView(R.id.cart_delivery_charge)
    TextView cartDeliveryCharge;
    @BindView(R.id.cart_sum)
    TextView cartSum;
    @BindView(R.id.cart_without_sum)
    TextView cartWithoutSum;

    @BindView(R.id.cart_image_icon)
    TextView cartImageIcon;
    @BindView(R.id.cart_fish_name)
    TextView cartFishName;
    @BindView(R.id.cart_fish_quantity)
    TextView cartFishQuantity;
    @BindView(R.id.cart_fish_price)
    TextView cartFishPrice;
    @BindView(R.id.cart_recyclerview)
    RecyclerView cartRecyclerview;

    Utility utility;
    Context context;
    GET_LOGIN login;
    private List<Cart_List> cartLists = new ArrayList<>();
    List<Cart_List> cart_array = new ArrayList<>();
    List<Cart_List> cart_delete = new ArrayList<>();
    List<Item> itemArrayList = new ArrayList<>();
    Send_Cart sendCart = new Send_Cart();
    Cart_Adapter cartAdapter;
    DecimalFormat df = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);
        ButterKnife.bind(this);
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("যোগকৃত পণ্যের তালিকা");
            context = Cart_Activity.this;
            utility = new Utility(context);
            login = (GET_LOGIN) Stash.getObject("user", GET_LOGIN.class);
            font_setup();

            cartAdapter = new Cart_Adapter(cart_array, context, new Cart_Adapter.DetailsAdapterListener() {
                @Override
                public void plus_click(View v, int position) {
                    int quan = Integer.parseInt(cart_array.get(position).getFish_quantity());
                    if (quan > 0) {
                        List<Cart_List> cart_plus = new ArrayList<>();
                        quan += 1;
                        cart_plus = Stash.getArrayList("cart_list", Cart_List.class);
                        if (cart_plus.size() > 0) {
                            cart_plus.get(position).setFish_quantity(String.valueOf(quan));
                            utility.logger("cart" + cart_plus.get(position).toString());
                            Stash.put("cart_list", cart_plus);
                            Cart_fish_data();
                        }
                    }
                }

                @Override
                public void minus_Click(View v, int position) {
                    int quan = Integer.parseInt(cart_array.get(position).getFish_quantity());
                    if (quan >= 2) {
                        List<Cart_List> cart_plus = new ArrayList<>();
                        quan -= 1;
                        cart_plus = Stash.getArrayList("cart_list", Cart_List.class);
                        if (cart_plus.size() > 0) {
                            cart_plus.get(position).setFish_quantity(String.valueOf(quan));
                            utility.logger("cart" + cart_plus.get(position).toString());
                            Stash.put("cart_list", cart_plus);
                            Cart_fish_data();
                        }
                    }
                }

                @Override
                public void delete_click(View v, int position) {
                    /*int quan = Integer.parseInt(cart_array.get(position).getFish_quantity());
                    if (quan >= 2) {
                        List<Cart_List> cart_plus = new ArrayList<>();
                        quan -= 1;
                        cart_plus = Stash.getArrayList("cart_list", Cart_List.class);
                        if (cart_plus.size() > 0) {
                            cart_plus.get(position).setFish_quantity(String.valueOf(quan));
                            utility.logger("cart" + cart_plus.get(position).toString());
                            Stash.put("cart_list", cart_plus);
                            Cart_fish_data();
                        }
                    }*/
                    showDialog(/*getResources().getString(R.string.confirm_question_string)*/"আপনি কি " + cartLists.get(position).getFish_name() + " বাতিল করতে চান?", cart_array.get(position).getD_id());
                }
            });
            cartRecyclerview.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            cartRecyclerview.setLayoutManager(mLayoutManager);
            cartRecyclerview.setAdapter(cartAdapter);


            Cart_fish_data();
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
                            cartDelivery,
                            cartServiceCharge,
                            cartVat,
                            cartDeliveryCharge,
                            cartSum,
                            cartWithoutSum,
                            cartImageIcon,
                            cartFishName,
                            cartFishQuantity,
                            cartFishPrice
                    }, Fonts.MEDIUM);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void Cart_fish_data() {
        /*for (int c = 0; c < 6; c++) {
            Cart_List p = new Cart_List("custom_cart" + c, "url" + c, "name" + c,c+"", "" + c);
            cartLists.add(p);
            //add_custom_view(p);
        }
        Stash.put("cart_list",cartLists);*/
        //Stash.clear("cart_list");
        double sum = 0;
        double sub_sum = 0;
        double vat = 0;
        double service_charge = 0;
        double delivery = 60;
        delivery = Double.parseDouble(Stash.getString("DELIVERY_CHARGE"));
        double vat_server = Integer.parseInt(Stash.getString("VAT")) / 100.0;
        ;
        double service_server = Integer.parseInt(Stash.getString("SERVICE_CHARGE")) / 100.0;
        cartLists.clear();
        cartLists = Stash.getArrayList("cart_list", Cart_List.class);
        if (cartLists.size() >= 0) {
            for (Cart_List c : cartLists) {
                //add_custom_view(c);
                utility.logger(c.getFish_price());
                sum = sum + (Integer.parseInt(c.getFish_price()) * Integer.parseInt(c.getFish_quantity()));
                Item item = new Item(Integer.parseInt(c.getD_id()), "Good", c.getFish_quantity(), c.getFish_price(), "active");
                itemArrayList.add(item);
            }
            utility.logger("Size" + cartLists.size() + "");
            cart_array.clear();
            cart_array.addAll(cartLists);
            cartAdapter.notifyDataSetChanged();
        }
        utility.logger("Before" + sum + "");
        sub_sum = sum;
        cartWithoutSum.setText(utility.convertToBangle(String.valueOf(df.format(sum))) + getString(R.string.bdt_sign));
        service_charge = sum * service_server;
        vat = sum * vat_server;
        //service_charge=sum-service_charge;
        cartServiceCharge.setText(utility.convertToBangle(String.valueOf(df.format(service_charge))) + getString(R.string.bdt_sign));
        cartVat.setText(utility.convertToBangle(String.valueOf(df.format(vat))) + getString(R.string.bdt_sign));
        cartDeliveryCharge.setText(utility.convertToBangle(String.valueOf(df.format(delivery))) + getString(R.string.bdt_sign));
        sum = service_charge + vat + delivery + sum;
        cartSum.setText(utility.convertToBangle(String.valueOf(df.format(sum))) + getString(R.string.bdt_sign));
        utility.logger(sum + "");

        if (/*login.getId() != null*/true) {
            sendCart.setTotal(String.valueOf(sum));
            sendCart.setServiceCharge(String.valueOf(service_charge));
            sendCart.setVat(String.valueOf(vat));
            sendCart.setDeliveryCharge(String.valueOf(delivery));
            sendCart.setSubtotal(String.valueOf(sub_sum));
            sendCart.setPointUsed(0);
            //sendCart.setUserId(login.getId());
            sendCart.setTransactionId(String.valueOf(System.currentTimeMillis()));
            sendCart.setInvoice("no");
            sendCart.setStatus("pending");
            sendCart.setItems(itemArrayList);
        }
    }

    /*public void add_custom_view(Cart_List c) {
        LinearLayout.LayoutParams match_width = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //match_width.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams image_layout = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        //image_layout.gravity = Gravity.CENTER|Gravity.START;
        image_layout.weight = 1;

        LinearLayout.LayoutParams name_layout = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        //name_layout.gravity = Gravity.CENTER | Gravity.START;
        name_layout.weight = 2;

        LinearLayout.LayoutParams quantity_layout = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        //quantity_layout.gravity = Gravity.CENTER;
        quantity_layout.weight = 1;

        LinearLayout.LayoutParams price_layout = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        //price_layout.gravity = Gravity.CENTER | Gravity.END;
        price_layout.weight = 2;


        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(match_width);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(6);
        linearLayout.setPadding(5, 10, 5, 10);


        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(image_layout);
        imageView.setMaxHeight(50);
        imageView.setMaxHeight(50);
        imageView.setBackgroundColor(getResources().getColor(R.color.app_blue));
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_user));

        TextView name = new TextView(context);
        name.setLayoutParams(name_layout);
        name.setPadding(5, 5, 5, 5);
        name.setText(c.getFish_name());
        name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        name.setTextColor(getResources().getColor(R.color.app_black));
        name.setGravity(Gravity.CENTER | Gravity.START);

        TextView price = new TextView(context);
        price.setLayoutParams(price_layout);
        price.setPadding(5, 5, 5, 5);
        price.setText(String.valueOf(Integer.parseInt(c.getFish_quantity()) * Integer.parseInt(c.getFish_price())));
        price.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        price.setTextColor(getResources().getColor(R.color.app_black));
        price.setGravity(Gravity.CENTER | Gravity.END);

        TextView quantity = new TextView(context);
        quantity.setLayoutParams(quantity_layout);
        quantity.setPadding(5, 5, 5, 5);
        quantity.setText(c.getFish_quantity());
        quantity.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        quantity.setTextColor(getResources().getColor(R.color.app_black));
        quantity.setGravity(Gravity.CENTER | Gravity.START);


        linearLayout.addView(imageView);
        linearLayout.addView(name);
        linearLayout.addView(quantity);
        linearLayout.addView(price);

        cartCustomAdd.addView(linearLayout);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.cart_delivery)
    public void onViewClicked() {
        if (cartLists.size() > 0) {
            if (login.getId() != null) {
                startActivity(new Intent(context, Buyer_Delivery_Address.class).putExtra("cart_data", sendCart));
                utility.logger(sendCart.toString());
            } else {
                startActivity(new Intent(context, Login.class));
            }
        } else {
            utility.showToast(getResources().getString(R.string.cart_empty_string));
        }
    }

    public void showDialog(String message, final String id) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        HashMap<String, Integer> screen = utility.getScreenRes();
        int width = screen.get("width");
        int height = screen.get("height");
        int mywidth = (width / 10) * 7;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_toast_yes_no);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_message);
        Button btnOk = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnNo = (Button) dialog.findViewById(R.id.btn_no);
        utility.setFonts_normal(new View[]{tvMessage, btnOk, btnNo}, Fonts.MEDIUM);
        tvMessage.setText(message);
        LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.dialog_layout_size);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.width = mywidth;
        ll.setLayoutParams(params);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_delete.clear();
                cart_delete = Stash.getArrayList("cart_list", Cart_List.class);
                if (cart_delete.size() > 0) {
                    /*for (Cart_List c : cart_delete) {
                        if (id.equals(c.getD_id())) {
                            utility.logger("before delete"+cart_delete.size());
                            cart_delete.remove(c);
                            utility.logger("after delete"+cart_delete.size());
                            Stash.put("cart_list", cart_delete);
                        }
                    }*/

                    for (Iterator<Cart_List> iterator = cart_delete.iterator(); iterator.hasNext(); ) {
                        Cart_List value = iterator.next();
                        if (id.equals(value.getD_id())) {
                            utility.logger("before delete" + cart_delete.size());
                            //cart_delete.remove(c);
                            iterator.remove();
                            utility.logger("after delete" + cart_delete.size());
                            Stash.put("cart_list", cart_delete);
                        }
                    }
                }
                cart_delete.clear();
                Cart_fish_data();
                dialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}
