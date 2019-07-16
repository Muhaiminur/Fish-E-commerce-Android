package com.gtechnologies.fishbangla.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gtechnologies.fishbangla.Fragment.Fragment_List;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Tag_List;
import com.gtechnologies.fishbangla.R;

import java.util.List;

public class Tag_List_Adapter extends BaseAdapter {

    Context context;
    List<Tag_List> cat_list;
    LayoutInflater inflter;
    String saletype;
    Utility utility;

    public Tag_List_Adapter(Context c, List<Tag_List> cat_list, String s) {
        this.context = c;
        this.cat_list = cat_list;
        inflter = (LayoutInflater.from(c));
        saletype = s;
        utility = new Utility(context);
    }

    @Override
    public int getCount() {
        return cat_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.tag_row, null);
        TextView tagRowText = view.findViewById(R.id.tag_row_text);
        tagRowText.setText(cat_list.get(i).getNameBn());
        ImageButton imageButton = view.findViewById(R.id.tag_row_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Gridview", "Found2");
                try {
                    Bundle send_id = new Bundle();
                    if (cat_list.size() > 0) {
                        send_id.putInt("tagid", cat_list.get(i).getId());
                        send_id.putString("sale_type", saletype);
                    }
                    Fragment home = new Fragment_List();
                    home.setArguments(send_id);
                    Replace_Fragment(home);
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }
        });
        utility.setFonts_normal(new View[]{tagRowText,}, Fonts.MEDIUM);
        return view;
    }

    public void Replace_Fragment(Fragment frag) {
        try {
            if (frag != null) {
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.home_area, frag);
                ft.addToBackStack(null);
                ft.commit();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}
