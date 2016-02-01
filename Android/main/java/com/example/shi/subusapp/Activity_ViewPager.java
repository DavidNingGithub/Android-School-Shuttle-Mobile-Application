package com.example.shi.subusapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Locale;


public class Activity_ViewPager extends ActionBarActivity
        implements RecyclerViewFragment.OnListItemSelectedListener{

    MyFragmentPagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    MovieDataJson1 movieData;

    @Override
    public void onItemClick(int position,HashMap<String,?>movie){
        Intent intent=new Intent(this,Activity_ToolBar.class);
        intent.putExtra(Activity_ToolBar.ARG_MOVIE_INDEX,position);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        try{
            movieData = new MovieDataJson1(getApplicationContext());
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        /*}
        catch (JSONException e){
            e.printStackTrace();
        }*/
        mPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),movieData.getSize());
        mViewPager=(ViewPager) findViewById(R.id.pager);
        customizeViewPager();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);
    }
    private void customizeViewPager(){
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer(){
            @Override
            public void transformPage(View page,float position){
                final float normalized_position=Math.abs(Math.abs(position)-1);
                //2.
                /*page.setScaleX(normalized_position/2+0.5f);
                page.setScaleY(normalized_position / 2 + 0.5f);*/
                page.setAlpha(normalized_position);
                //1.page.setRotationY(position * -30);

            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int i,float v, int i2){

            }
            @Override
            public void onPageSelected(int i){

            }
            @Override
            public void onPageScrollStateChanged(int i){

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity__view_pager, menu);
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



    public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
        int count;
        public MyFragmentPagerAdapter(android.support.v4.app.FragmentManager fm, int size){
            super(fm);
            count=size;

        }

        @Override
        public Fragment getItem(int position){
            return RecyclerViewFragment.newInstance(position);
        }

        @Override
        public int getCount(){
            return 2;};

        @Override
        public CharSequence getPageTitle(int position){
            Locale l=Locale.getDefault();
            String name;
            switch(position)
            {
                case 0:
                    name="Top Selling Movies";
                    break;
                case 1:
                    name="New Movies Release";
                    break;
                default:
                    name="Top Selling Movies";
                    break;
            }
            /*HashMap<String,?>movie=(HashMap<String,?>)movieData.getItem(postion);
            String name=(String) movie.get("name");*/
            return name.toUpperCase(l);
        }
    }
}
