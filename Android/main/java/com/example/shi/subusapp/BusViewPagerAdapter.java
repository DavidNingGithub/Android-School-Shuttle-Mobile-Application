package com.example.shi.subusapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Shi on 4/14/2015.
 */
public class BusViewPagerAdapter extends FragmentStatePagerAdapter {
    List<String> stops = null;
    String busName = null;
    public BusViewPagerAdapter(FragmentManager fm, String bus, List<String> l) {
        super(fm);
        stops = l;
        busName = bus;
    }
    @Override
    public Fragment getItem(int i) {
        Log.w("BusViewPagerAdapter: ", Integer.toString(i));
        if(stops != null && busName != null) {
            Log.w("busName = ",busName);
            return StopAndTimeFragment.newInstance(busName, stops.get(i), null);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(stops != null )
            return stops.size();
        return 0;
    }
    public void setData(List<String> l) {
        stops = l;
        notifyDataSetChanged();
    }
}
