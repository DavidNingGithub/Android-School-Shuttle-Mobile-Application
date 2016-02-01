package com.example.shi.subusapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Roxie on 4/2/2015.
 */
public class Fragment_Setting_Home extends Fragment {
    public Fragment_Setting_Home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting_home, container, false);

        Spinner NobHill = (Spinner) rootView.findViewById(R.id.NobHill);
        String[] BusNobHill = new String[]{"Nob Hill", "station 1", "station2"};
        ArrayAdapter<String> NobHillAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, BusNobHill);
        NobHill.setAdapter(NobHillAdapter);

        Spinner EastCampus = (Spinner) rootView.findViewById(R.id.EastCampus);
        String[] BusEastCampus = new String[]{ "East Campus","station 1", "station 2"};
        ArrayAdapter<String> EastCampusAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, BusEastCampus);
        EastCampus.setAdapter(EastCampusAdapter);

        Spinner JamesStreet = (Spinner) rootView.findViewById(R.id.JamesStreet);
        String[] BusJamesStreet = new String[]{"James Street", "Station 1", "Station 2"};
        ArrayAdapter<String> JamesStreetAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, BusJamesStreet);
        JamesStreet.setAdapter(JamesStreetAdapter);

        return rootView;
    }
}
