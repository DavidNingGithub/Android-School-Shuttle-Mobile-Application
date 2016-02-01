package com.example.shi.subusapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Roxie on 3/31/2015.
 */
public class Fragment_Contact_us extends Fragment {
    public Fragment_Contact_us() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contect_us, container, false);
        if(MainActivity.BackColor != 0){
            rootView.setBackgroundColor(MainActivity.BackColor);
        }
        if(MainActivity.grayDrawable) {
            Resources resources = getActivity().getResources();
            Drawable btnDrawable = resources.getDrawable(R.drawable.gray_gradient);
            rootView.setBackground(btnDrawable);
        }
        TextView send = (TextView) rootView.findViewById(R.id.send);
        final EditText suggestion = (EditText) rootView.findViewById(R.id.suggestion);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "roxie@syr.edu, shishi@syr.edu, ningtao@syr.edu" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions of SU Bus");
                String text = suggestion.getText().toString();
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, ""));
            }
        });
        return rootView;
    }
}
