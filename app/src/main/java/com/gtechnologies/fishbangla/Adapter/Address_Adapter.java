package com.gtechnologies.fishbangla.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gtechnologies.fishbangla.API_GET.GET_ADDRESS;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Address_List;
import com.gtechnologies.fishbangla.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Address_Adapter extends RecyclerView.Adapter<Address_Adapter.MyViewHolder> {
    Context context;
    List<GET_ADDRESS> address_list;
    Utility utility;

    public Address_Adapter(List<GET_ADDRESS> f, Context c) {
        address_list = f;
        context = c;
        utility = new Utility(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.address_name)
        TextView addressName;
        @BindView(R.id.address_number)
        TextView addressNumber;
        @BindView(R.id.address_add)
        TextView addressAdd;
        @BindView(R.id.address_zilla)
        TextView addressZilla;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fish_address_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GET_ADDRESS bodyResponse = address_list.get(position);
        holder.addressName.setText(bodyResponse.getReceiversName());
        holder.addressNumber.setText(bodyResponse.getReceiversContact());
        String temp1 = "";
        if (bodyResponse.getAddress() != null) {
            temp1 = bodyResponse.getAddress();
        }
        if (bodyResponse.getNearbyLandmark() != null) {
            temp1 = temp1 + ", " + bodyResponse.getNearbyLandmark();
        }
        holder.addressAdd.setText(temp1);
        String temp2 = "";
        if (bodyResponse.getVillage() != null) {
            temp2 = bodyResponse.getVillage();
        }
        if (bodyResponse.getNearbyLandmark() != null) {
            temp2 = temp2 + ", " + bodyResponse.getUpazilla();
        }
        if (bodyResponse.getAddress() != null) {
            temp2 = temp2 + ", " + bodyResponse.getDistrict();
        }
        if (bodyResponse.getNearbyLandmark() != null) {
            temp2 = temp2 + ", " + bodyResponse.getDivision();
        }
        holder.addressZilla.setText(temp2);
        utility.setFonts_normal(new View[]{holder.addressName, holder.addressNumber, holder.addressAdd, holder.addressZilla}, Fonts.SMALL);
    }

    @Override
    public int getItemCount() {
        return address_list.size();
    }
}