package com.gtechnologies.fishbangla.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gtechnologies.fishbangla.API_GET.Get_Fish_List;
import com.gtechnologies.fishbangla.Activity.Fish_Details;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Fish_List;
import com.gtechnologies.fishbangla.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FISHLIST_Adapter extends RecyclerView.Adapter<FISHLIST_Adapter.MyViewHolder> {
    Context context;
    List<Get_Fish_List> fish_list;
    Utility utility;


    public FISHLIST_Adapter(List<Get_Fish_List> f, Context c) {
        fish_list = f;
        context = c;
        utility = new Utility(context);
    }

    @OnClick({R.id.fish_row_add, R.id.product})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fish_row_add:
                break;
            case R.id.product:
                break;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fish_row_image)
        ImageView fishRowImage;
        @BindView(R.id.fish_row_add)
        Button fishRowAdd;
        @BindView(R.id.fish_row_price)
        TextView fishRowPrice;
        @BindView(R.id.fish_row_location)
        TextView fishRowLocation;
        @BindView(R.id.fish_row_category)
        TextView fishRowCategory;
        @BindView(R.id.product)
        CardView product;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fish_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Get_Fish_List bodyResponse = fish_list.get(position);
        holder.fishRowAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context, "Added", Toast.LENGTH_LONG).show();
            }
        });
        holder.fishRowPrice.setText(bodyResponse.getNameBn() + " - " + utility.convertToBangle(bodyResponse.getPrice()) + context.getResources().getString(R.string.unit_string));
        holder.fishRowLocation.setText(bodyResponse.getUserName());
        holder.fishRowCategory.setText(bodyResponse.getUpazilla() + ", " + bodyResponse.getDistrict() + ", " + bodyResponse.getDivision());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loader);
        requestOptions.error(R.drawable.ic_default_background_banner);

        Glide.with(context).load(context.getResources().getString(R.string.image_base_url) + bodyResponse.getImage()).thumbnail(0.1f).apply(requestOptions).into(holder.fishRowImage);
        /*holder.productName.setText(bodyResponse.getName());
        holder.productPrice.setText(bodyResponse.getPrice());
        holder.productRating.setRating(Float.parseFloat(bodyResponse.getRating()));
        holder.productReview.setText(bodyResponse.getReview());*/
        holder.product.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    /*Fragment frag = new Fragment_Fish_Details();
                    //Bundle args = new Bundle();
                    //args.putSerializable("compare_info", informationRequest);
                    //args.putSerializable("compare_result", bodyResponse);
                    //frag.setArguments(args);
                    if (frag != null) {
                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.home_area, frag);
                        ft.addToBackStack(null);
                        ft.commit();
                    }*/
                    context.startActivity(new Intent(context, Fish_Details.class).putExtra("fish_details", bodyResponse));
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }
        });
        utility.setFonts_normal(new View[]{holder.fishRowAdd, holder.fishRowCategory, holder.fishRowLocation, holder.fishRowPrice}, Fonts.MEDIUM);
    }

    @Override
    public int getItemCount() {
        return fish_list.size();
    }
}