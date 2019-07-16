package com.gtechnologies.fishbangla.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gtechnologies.fishbangla.Adapter.Fish_List_Adapter;
import com.gtechnologies.fishbangla.Library.Fonts;
import com.gtechnologies.fishbangla.Library.Utility;
import com.gtechnologies.fishbangla.Model.Fish_List;
import com.gtechnologies.fishbangla.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Fish_Details extends Fragment {


    @BindView(R.id.fish_details_image)
    ImageView fishDetailsImage;
    @BindView(R.id.fish_details_add)
    Button fishDetailsAdd;
    @BindView(R.id.fish_details_price)
    TextView fishDetailsPrice;
    @BindView(R.id.fish_details_location)
    TextView fishDetailsLocation;
    @BindView(R.id.fish_details_category)
    TextView fishDetailsCategory;
    @BindView(R.id.fish_details_recyclerview)
    RecyclerView fishDetailsRecyclerview;
    Unbinder unbinder;

    public Fragment_Fish_Details() {
        // Required empty public constructor
    }

    View view;
    Utility utility;
    Context context;

    private List<Fish_List> productList = new ArrayList<>();
    private Fish_List_Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment__fish__details, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            context = getActivity();
            utility = new Utility(context);
            font_setup();

            mAdapter = new Fish_List_Adapter(productList, getActivity());
            fishDetailsRecyclerview.setHasFixedSize(true);
            GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            fishDetailsRecyclerview.setLayoutManager(mLayoutManager);
            fishDetailsRecyclerview.setAdapter(mAdapter);
            Prepare_fish_data();
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
                            fishDetailsAdd,
                            fishDetailsPrice,
                            fishDetailsLocation,
                            fishDetailsCategory
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

    public void Prepare_fish_data() {
        for (int c = 0; c < 6; c++) {
            Fish_List p = new Fish_List("url" + c, "0" + c, "Location" + c, "Category" + c);
            productList.add(p);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fish_details_add)
    public void onViewClicked() {
    }
}
