package com.example.shi.subusapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Roxie on 4/14/2015.
 */
public class Fragment_schedule_detail_station extends Fragment{
    public Fragment_schedule_detail_station() {
    }

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Fragment_schedule_detail_station newInstance(int sectionNumber) {
        Fragment_schedule_detail_station fragment = new Fragment_schedule_detail_station();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule_list, container, false);
        TextView station = (TextView) rootView.findViewById(R.id.station);

        final int option = getArguments().getInt(ARG_SECTION_NUMBER);
        switch (option){
            case 0:
                station.setText("station 1");
                break;
            default:
                station.setText("station 2");
                break;

        }


        return rootView;
    }
}
