package com.example.shi.subusapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.os.Build;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class FrontPage extends ActionBarActivity
                            implements FirstPage.OnItemSelectedListener,RecyclerViewFragment.OnListItemSelectedListener,MyRecyclerView.OnListItemSelectedListener{
                            //implements FrontPage.PlaceholderFragment.OnItemSelectedListener{
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawer;
    private static final int REQUEST_DATE=0;
    private RecyclerView mDrawerList;
    private FirstPage firstPage;
    private MyDrawerRecyclerViewAdapter mDrawerRecyclerViewAdapter;
    private int currentpos=0;
    public static final String DATE_ARGS = "index1";
    public static HashMap CurrentUserInf;
    public static String CurrentUser="";
    public static boolean CurrentPermission=false;
    @Override
    public void onItemSelected(){
        getSupportFragmentManager().beginTransaction()
                           .replace(R.id.container, new FrontPageFragment())
                           .addToBackStack(null)
                           .commit();
//        Date date=new Date(System.currentTimeMillis());
//        MyDialogFragment dialogFragment=MyDialogFragment.newInstance(date);
////        dialogFragment.setTargetFragment(;
//        dialogFragment.setTargetFragment(FirstPage.this,REQUEST_DATE);
//        dialogFragment.show(getFragmentManager(),"MyDialoge1");
    }
    @Override
    public void onItemSelected1(){
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new AboutMeFragment())
                .addToBackStack(null)
                .commit();*/
    }
    @Override
    public void onItemClick(int position,HashMap<String,?>movie){


    }
    @Override
    public void onItemClick1(int position,HashMap<String,?>movie){
        Intent intent=new Intent(this,ViewPageActivity.class);
        intent.putExtra(DATE_ARGS,movie);
        startActivity(intent);
    }
    @Override
    public void onItemSelected2(){
        Intent intent=new Intent(this,Activity_ViewPager.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected3(){
        Intent intent=new Intent(this,Activity_GridView.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_front_page);
        CurrentUserInf=new HashMap();
        Toolbar mToolbar;
        setContentView(R.layout.drawerlayout);

        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //mToolbar=getSupportActionBar();
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new FirstPage())
                    .commit();
        }

        mDrawer=(RelativeLayout)findViewById(R.id.left_drawer);
        mDrawerList=(RecyclerView)findViewById(R.id.drawer_list);
        mDrawerList.setLayoutManager(new LinearLayoutManager(this));
        mDrawerRecyclerViewAdapter=new MyDrawerRecyclerViewAdapter(this,(new DrawerData_New()).getDrawerList(),currentpos);
        mDrawerRecyclerViewAdapter.SetOnItemClickListener(new MyDrawerRecyclerViewAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int position) {
                        selectItem(position);
            }
        });
        mDrawerList.setAdapter(mDrawerRecyclerViewAdapter);

    }


    private void selectItem(int position){
        Intent intent;
        switch(position) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new FirstPage())
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, RecyclerViewFragment.newInstance(0))
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, RecyclerViewFragment.newInstance(1))
                        .addToBackStack(null)
                        .commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new FrontPageFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 5:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new TextFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 6:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SampleFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;



        }
        mDrawerLayout.closeDrawer(mDrawer);
    }


    @Override
    protected void onPostCreate(Bundle savedINstanceState){
        mDrawerToggle.syncState();
        super.onPostCreate(savedINstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.pen:
                Toast.makeText(getApplicationContext(), "Clicked Pen1", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Clicked Setting", Toast.LENGTH_LONG).show();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
