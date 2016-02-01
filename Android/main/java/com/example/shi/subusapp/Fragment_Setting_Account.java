package com.example.shi.subusapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Roxie on 4/2/2015.
 */
public class Fragment_Setting_Account extends Fragment {

    public Fragment_Setting_Account() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.information, container, false);
        return rootView;
    }
}
