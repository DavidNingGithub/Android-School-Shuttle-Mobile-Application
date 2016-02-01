package com.example.shi.subusapp;


import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;


public class Activity_ToolBar extends ActionBarActivity {

    Toolbar mToolbar;
    public static final String ARG_MOVIE_INDEX = "index";
    int index;
    MovieDataJson1 movieData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_layout);

        try{
            movieData = new MovieDataJson1(getApplicationContext());
        }
        catch ( JSONException e){
            e.printStackTrace();
        }

       /* catch ( JSONException e){
            e.printStackTrace();
        }*/
        index=getIntent().getIntExtra(Activity_ToolBar.ARG_MOVIE_INDEX,0);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,Fragment_DetailView.newInstance((HashMap<String,?>)movieData.getItem(index)))
                    .commit();
        }
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        //mToolbar.inflateMenu(R.menu.menu_activity__tool_bar);
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                int id = menuItem.getItemId();
//                switch (id) {
//                    case R.id.tools:
//                        Toast.makeText(getApplicationContext(), "Clicked Tools", Toast.LENGTH_LONG).show();
//                        return true;
//                    case R.id.tools1:
//                        Toast.makeText(getApplicationContext(), "Clicked Cars", Toast.LENGTH_LONG).show();
//                        return true;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.tools:
                Toast.makeText(getApplicationContext(), "Clicked Tools", Toast.LENGTH_LONG).show();
                return true;
            case R.id.tools1:
                Toast.makeText(getApplicationContext(), "Clicked Cars", Toast.LENGTH_LONG).show();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
