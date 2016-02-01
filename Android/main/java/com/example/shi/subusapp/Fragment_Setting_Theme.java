package com.example.shi.subusapp;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Roxie on 4/19/2015.
 */
public class Fragment_Setting_Theme extends android.support.v4.app.Fragment {

    Bitmap backgroundBitmap;
    Canvas backgroundCanvas;

    public Fragment_Setting_Theme() {
    }
    //private OnBackColorClickedListener cListener;

    public void onBackColorClicked(int color){
            MainActivity.BackColor = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_setting_theme, container, false);
        if(MainActivity.BackColor != 0){
            rootView.setBackgroundColor(MainActivity.BackColor);
        }
        final ImageView pink = (ImageView) rootView.findViewById(R.id.pink);
        final ImageView defaultBack = (ImageView) rootView.findViewById(R.id.defaultBack);
        final ImageView gray = (ImageView) rootView.findViewById(R.id.gray);
        final ImageView yellow = (ImageView) rootView.findViewById(R.id.yellow);
        final ImageView blue = (ImageView) rootView.findViewById(R.id.blue);
        final ImageView green = (ImageView) rootView.findViewById(R.id.green);


        backgroundBitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888);
        backgroundCanvas = new Canvas(backgroundBitmap);
        gray.setImageBitmap(backgroundBitmap);
        drawShadedCircle(backgroundCanvas,0);
        gray.invalidate();

        defaultBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackColorClicked(0);
                MainActivity.mToolbar.setBackgroundColor(0xFFFFa500);
                getActivity().findViewById(R.id.drawer).setBackgroundColor(0xFFFFFFFF);
                rootView.setBackgroundColor(0);
                MainActivity.grayDrawable = false;
            }
        });

        pink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackColorClicked(0xFFFFB6C1);
                MainActivity.mToolbar.setBackgroundColor(0xFFC71585);
                getActivity().findViewById(R.id.drawer).setBackgroundColor(0xFFFF83FF);
                rootView.setBackgroundColor(0xFFFFB6C1);
                MainActivity.grayDrawable = false;
            }
        });

        yellow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackColorClicked(0xFFFFF68F);
                MainActivity.mToolbar.setBackgroundColor(0xFFFFD700);
                getActivity().findViewById(R.id.drawer).setBackgroundColor(0xFFE3CF57);
                rootView.setBackgroundColor(0xFFFFF68F);
                MainActivity.grayDrawable = false;
            }
        });

        blue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackColorClicked(0xFFBFEFFF);
                MainActivity.mToolbar.setBackgroundColor(0xFF87CEEB);
                getActivity().findViewById(R.id.drawer).setBackgroundColor(0xFF98F5FF);
                rootView.setBackgroundColor(0xFFBFEFFF);
                MainActivity.grayDrawable = false;
            }
        });

        green.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackColorClicked(0xFFC1FFC1);
                MainActivity.mToolbar.setBackgroundColor(0xFF7CCD7C);
                getActivity().findViewById(R.id.drawer).setBackgroundColor(0xFF00EE76);
                rootView.setBackgroundColor(0xFFC1FFC1);
                MainActivity.grayDrawable = false;
            }
        });

        gray.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackColorClicked(0xFFCFCFCF);
                MainActivity.grayDrawable = true;
                MainActivity.mToolbar.setBackgroundColor(0xFF9C9C9C);
                getActivity().findViewById(R.id.drawer).setBackgroundColor(0xFFA8A8A8);
                //rootView.setBackgroundColor(0xFFCFCFCF);
                Resources resources = getActivity().getResources();
                Drawable btnDrawable = resources.getDrawable(R.drawable.gray_gradient);
                rootView.setBackground(btnDrawable);
            }
        });
        return rootView;

    }


    void drawShadedCircle(Canvas canvas, int color){
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        if (color == 0) {
            paint.setShader(new LinearGradient(0.40f, 0.0f, 0.60f, 1.0f,
                    Color.rgb(0xf0, 0xf5, 0xf0),
                    Color.rgb(0x30, 0x31, 0x30),
                    Shader.TileMode.CLAMP));
        }

        int scale = backgroundCanvas.getWidth();
        backgroundCanvas.scale(scale, scale);
        canvas.drawCircle(0.5f, 0.5f, 0.35f,paint);

    }

}
