package com.example.shi.subusapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.Activity;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.HashMap;
import android.view.View;

import java.util.HashMap;


public class Activity_GridView extends ActionBarActivity
        implements RecyclerViewFragment.OnListItemSelectedListener{

    private boolean mTwoPane=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);
        //

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_list,RecyclerViewFragment.newInstance(1))
                    .commit();
        }
        if(findViewById(R.id.item_detail_container)!=null){
            mTwoPane=true;
        }
    }

    @Override
    public void onItemClick(int position,HashMap<String,?>movie){
        if(mTwoPane==true){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container,Fragment_DetailView.newInstance(movie))
                    .addToBackStack(null)
                    .commit();
        }else{

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_list,Fragment_DetailView.newInstance(movie))
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__master_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
