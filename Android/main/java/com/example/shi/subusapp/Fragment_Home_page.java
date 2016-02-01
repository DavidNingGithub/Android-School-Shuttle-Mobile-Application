package com.example.shi.subusapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Roxie on 3/31/2015.
 */
public class Fragment_Home_page extends Fragment {

    public Fragment_Home_page() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        if(MainActivity.BackColor != 0){
            rootView.setBackgroundColor(MainActivity.BackColor);
        }
        if(MainActivity.grayDrawable) {
            Resources resources = getActivity().getResources();
            Drawable btnDrawable = resources.getDrawable(R.drawable.gray_gradient);
            rootView.setBackground(btnDrawable);
        }
        ImageView bus = (ImageView) rootView.findViewById(R.id.front_bus);
        bus.setImageResource(R.drawable.front_bus);
        TranslateAnimation animation = new TranslateAnimation(-700.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(1800);
        bus.startAnimation(animation);
        bus.animate().setStartDelay(2500).setDuration(1800).x(900);
        ImageView logo = (ImageView) rootView.findViewById(R.id.front_logo);
        logo.setImageResource(R.drawable.front_logo);

        final Button login = (Button) rootView.findViewById(R.id.home_page_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date=new Date(System.currentTimeMillis());
                Intent intent=new Intent(getActivity(),SecondActivity.class);
                intent.putExtra(MyDialogFragment.DATE_ARGS,date);
                startActivityForResult(intent, FirstPage.REQUEST_DATE);
            }
        });

        final ImageView find = (ImageView) rootView.findViewById(R.id.home_find_bus);
        find.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_frontPage, new FindScheduleFragment())
                        .commit();
            }
        });
        return rootView;
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(data == null) return;
        Uri contactUri = data.getData();

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == FirstPage.REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(MyDialogFragment.DATE_ARGS);
            String name = (String) data.getSerializableExtra(MyDialogFragment.TEXT_ARGS);
            //textView_result.setText(input);
            Toast.makeText(getActivity(), name + " login successfully", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_frontPage, UserInfoFragment.newInstance(MainActivity.CurrentUserInf)
                    )
                    .commit();
        }
    }
}

