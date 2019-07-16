package com.gtechnologies.fishbangla.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gtechnologies.fishbangla.API_GET.GET_BUYHISTORY;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Buy_History_List;
import com.gtechnologies.fishbangla.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Buy_History_Adapter extends RecyclerView.Adapter<Buy_History_Adapter.MyViewHolder> {
    Context context;
    List<GET_BUYHISTORY> buy_history;
    Utility utility;


    public Buy_History_Adapter(List<GET_BUYHISTORY> f, Context c) {
        buy_history = f;
        context = c;
        utility = new Utility(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.history_name)
        TextView historyName;
        @BindView(R.id.history_price)
        TextView historyPrice;
        @BindView(R.id.history_unit)
        TextView historyUnit;
        @BindView(R.id.history_time)
        TextView historyTime;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fish_history_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GET_BUYHISTORY bodyResponse = buy_history.get(position);
        holder.historyName.setText(bodyResponse.getName());
        holder.historyPrice.setText(utility.convertToBangle(bodyResponse.getSubtotal()) + context.getResources().getString(R.string.unit_string));
        holder.historyUnit.setText(utility.convertToBangle(bodyResponse.getTotal()));
        holder.historyTime.setText(bodyResponse.getCreatedAt());
        utility.setFonts_normal(new View[]{holder.historyName, holder.historyPrice, holder.historyUnit, holder.historyTime}, Fonts.SMALL);
    }

    @Override
    public int getItemCount() {
        return buy_history.size();
    }
}