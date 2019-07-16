package com.gtechnologies.fishbangla.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProductListAdapter extends BaseAdapter {

    Context context;
    Utility utility;
    JSONArray jsonArray;

    public ProductListAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        utility = new Utility(this.context);
        this.jsonArray = jsonArray;
    }

    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return jsonArray.optJSONObject(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_list, null);
        }
        JSONObject jsonObject = jsonArray.optJSONObject(i);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        tvName.setText(jsonObject.optString("nameBn"));
        utility.setFonts_normal(new View[]{tvName}, Fonts.MEDIUM);
        return view;
    }
}
