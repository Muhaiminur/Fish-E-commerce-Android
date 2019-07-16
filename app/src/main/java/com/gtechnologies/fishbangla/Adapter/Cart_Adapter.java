package com.gtechnologies.fishbangla.Adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Cart_List;
import com.gtechnologies.fishbangla.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.MyViewHolder> {
    Context context;
    List<Cart_List> cart_list;
    DetailsAdapterListener onClickListener;
    Utility utility;


    public Cart_Adapter(List<Cart_List> f, Context c, DetailsAdapterListener listener) {
        cart_list = f;
        context = c;
        utility = new Utility(context);
        onClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cart_image_icon)
        ImageView cartImageIcon;
        @BindView(R.id.cart_fish_name)
        TextView cartFishName;
        @BindView(R.id.card_plus)
        ImageView cardPlus;
        @BindView(R.id.cart_fish_quantity)
        TextView cartFishQuantity;
        @BindView(R.id.card_minus)
        ImageView cardMinus;
        @BindView(R.id.cart_fish_price)
        TextView cartFishPrice;
        @BindView(R.id.cart_item)
        LinearLayout cartItem;
        @BindView(R.id.card_delete)
        Button cartdelete;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            cardPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.plus_click(v, getAdapterPosition());
                }
            });
            cardMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.minus_Click(v, getAdapterPosition());
                }
            });
            cartdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.delete_click(v, getAdapterPosition());
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fish_cart_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Cart_List bodyResponse = cart_list.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loader);
        requestOptions.error(R.drawable.ic_default_background_banner);

        Glide.with(context).load(context.getResources().getString(R.string.image_base_url) + bodyResponse.getImage_url()).thumbnail(0.1f).apply(requestOptions).into(holder.cartImageIcon);
        holder.cartFishName.setText(bodyResponse.getFish_name());
        holder.cartFishQuantity.setText(utility.convertToBangle(bodyResponse.getFish_quantity()));
        holder.cartFishPrice.setText(utility.convertToBangle(String.valueOf(Integer.parseInt(bodyResponse.getFish_quantity()) * Integer.parseInt(bodyResponse.getFish_price()))) + context.getString(R.string.bdt_sign));
        utility.setFonts_normal(new View[]{holder.cartFishQuantity, holder.cartFishPrice, holder.cartFishName}, Fonts.MEDIUM);
    }

    @Override
    public int getItemCount() {
        return cart_list.size();
    }

    public interface DetailsAdapterListener {

        void plus_click(View v, int position);

        void minus_Click(View v, int position);

        void delete_click(View v, int position);
    }
}