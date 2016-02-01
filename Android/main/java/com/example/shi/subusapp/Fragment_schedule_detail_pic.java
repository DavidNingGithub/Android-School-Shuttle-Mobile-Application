package com.example.shi.subusapp;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Roxie on 4/13/2015.
 */
public class Fragment_schedule_detail_pic extends Fragment {
    public Fragment_schedule_detail_pic() {
    }

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Fragment_schedule_detail_pic newInstance(String sectionNumber) {
        Fragment_schedule_detail_pic fragment = new Fragment_schedule_detail_pic();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String option = getArguments().getString(ARG_SECTION_NUMBER);
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_table_detail_pic, container, false);
        ImageView bus_table = (ImageView) rootView.findViewById(R.id.bus_table);
        TextView title = (TextView) getActivity().findViewById(R.id.title);
        //title.setText(MainActivity.parser.get(option));
        switch (option){
            case "NobHill":
                bus_table.setImageResource(R.drawable.nob_hill);
                title.setText(option);
                Log.w("Nobhill", option);
                break;
            case "EastCampus":
                bus_table.setImageResource(R.drawable.east_campus);
                title.setText(option);
                break;
            default:
                bus_table.setImageResource(R.drawable.james_street);
                title.setText(option);
                break;
        }

        return rootView;
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.

        inflater.inflate(R.menu.back, menu);
        MenuItem Item = menu.findItem(R.id.action_back);
        Item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getFragmentManager().beginTransaction()
                        // .setCustomAnimations(R.animator.fad_in,R.animator.fade_out)
                        .replace(R.id.container_frontPage,new Fragment_schedule())
                        .addToBackStack(null)
                        .commit();
                TextView title = (TextView) getActivity().findViewById(R.id.title);
                title.setText("Schedule");
                return false;
            }
        });
    }

}
