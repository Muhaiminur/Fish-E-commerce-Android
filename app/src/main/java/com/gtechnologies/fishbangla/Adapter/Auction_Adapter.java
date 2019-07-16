package com.gtechnologies.fishbangla.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gtechnologies.fishbangla.Fragment.Fragment_Auction_Page;
import com.gtechnologies.fishbangla.Model.Auction_List;
import com.gtechnologies.fishbangla.R;

import java.util.List;

public class Auction_Adapter extends FragmentPagerAdapter {

    List<Fragment>auction_lists;
    public Auction_Adapter(FragmentManager fm, List<Fragment> auctionLists) {
        super(fm);
        auction_lists=auctionLists;
    }

    @Override
    public Fragment getItem(int i) {
        return auction_lists.get(i);
    }

    @Override
    public int getCount() {
        return auction_lists.size();
    }
}
