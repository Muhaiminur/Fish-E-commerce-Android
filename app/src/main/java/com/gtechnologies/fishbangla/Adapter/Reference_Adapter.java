package com.gtechnologies.fishbangla.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gtechnologies.fishbangla.API_GET.GET_REFERLIST;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Reference_List;
import com.gtechnologies.fishbangla.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Reference_Adapter extends RecyclerView.Adapter<Reference_Adapter.MyViewHolder> {
    Context context;
    List<GET_REFERLIST> refer_list;
    Utility utility;

    public Reference_Adapter(List<GET_REFERLIST> f, Context c) {
        refer_list = f;
        context = c;
        utility = new Utility(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.refer_profile_image)
        CircularImageView referProfileImage;
        @BindView(R.id.referel_name)
        TextView referelName;
        @BindView(R.id.referel_date)
        TextView referelDate;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fish_reference_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GET_REFERLIST bodyResponse = refer_list.get(position);
        holder.referelName.setText(bodyResponse.getName());
        if (bodyResponse.getEmail() != null) {
            holder.referelDate.setText(bodyResponse.getContact() + ", " + bodyResponse.getEmail());
        } else {
            holder.referelDate.setText(bodyResponse.getContact());
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loader);
        requestOptions.error(R.drawable.ic_default_background_banner);

        Glide.with(context).load(context.getResources().getString(R.string.image_base_url) + bodyResponse.getPicture()).thumbnail(0.1f).apply(requestOptions).into(holder.referProfileImage);

        utility.setFonts_normal(new View[]{holder.referelDate, holder.referelName}, Fonts.MEDIUM);
    }

    @Override
    public int getItemCount() {
        return refer_list.size();
    }
}