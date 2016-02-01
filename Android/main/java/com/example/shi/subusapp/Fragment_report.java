package com.example.shi.subusapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Roxie on 3/31/2015.
 */
public class Fragment_report extends Fragment {

    public Fragment_report() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.report, container, false);
        if(MainActivity.BackColor != 0){
            rootView.setBackgroundColor(MainActivity.BackColor);
        }
        Spinner name = (Spinner) rootView.findViewById(R.id.name);
        String[] Bus = new String[]{"Nob Hill", "East Campus", "James Street"};
        ArrayAdapter<String> BusStopsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Bus);
        name.setAdapter(BusStopsAdapter);

        Spinner location = (Spinner) rootView.findViewById(R.id.location);
        String[] stations = new String[]{"Westcott", "University Pl", "College Pl"};
        ArrayAdapter<String> stationsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stations);
        location.setAdapter(stationsAdapter);

        return rootView;
    }
}
