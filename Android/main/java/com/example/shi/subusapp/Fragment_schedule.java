package com.example.shi.subusapp;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Roxie on 3/31/2015.
 */
public class Fragment_schedule extends Fragment {

    public Fragment_schedule() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_schedule_table, container, false);

        final TextView NobHill = (TextView) rootView.findViewById(R.id.nobhill);
        final TextView EastCampus = (TextView) rootView.findViewById(R.id.eastcampus);
        final TextView JamesStreet = (TextView) rootView.findViewById(R.id.jamsstreet);

        NobHill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NobHill.animate().setDuration(400).x(300).y(480).scaleX(7).scaleY(7);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.list_anim,R.anim.fade_in)
                        .replace(R.id.container_frontPage, BusViewPagerFragment.newInstance("NobHill"))
                        .commit();
                //mListener.onViewClickListener("NobHill");
            }
        });

        EastCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EastCampus.animate().setDuration(400).x(300).y(480).scaleX(7).scaleY(7);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.list_anim,R.anim.fade_in)
                        .replace(R.id.container_frontPage, BusViewPagerFragment.newInstance("EastCampus"))
                        .addToBackStack(null)
                        .commit();
            }
        });

        JamesStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JamesStreet.animate().setDuration(400).x(300).y(480).scaleX(7).scaleY(7);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.list_anim,R.anim.fade_in)
                        .replace(R.id.container_frontPage, BusViewPagerFragment.newInstance("jamesloditocp_weekend"))
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }
}
