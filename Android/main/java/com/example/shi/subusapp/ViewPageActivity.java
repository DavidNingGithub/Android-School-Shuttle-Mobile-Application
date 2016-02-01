package com.example.shi.subusapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class ViewPageActivity extends ActionBarActivity{

    MyFragmentPagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    HashMap<String,?> UserInfo;
    //MovieData movieData;
    //ViewPageActivity() {}
   /* ViewPageActivity newInstance(HashMap data) {
        ViewPageActivity fragment = new ViewPageActivity();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FrontPage.DATE_ARGS, data);
        fragment.setArguments(bundle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_viewpager);
        View rootView = inflater.inflate(R.layout.activity_viewpager,container,false);
        //Intent intent=getIntent();
        HashMap<String,?> UserInfo1=(HashMap<String,?>)getArguments().getSerializable(FrontPage.DATE_ARGS);
        String check=(String)UserInfo1.get("userid");
        UserInfo=UserInfo1;
        Log.d("MydebugMess : ----", check);
        //movieData=new MovieData();
        mPagerAdapter=new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(),2);
        mViewPager=(ViewPager) rootView.findViewById(R.id.pager);
        customizeViewPager();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        Intent intent=getIntent();
        HashMap<String,?> UserInfo1=(HashMap<String,?>)intent.getSerializableExtra(FrontPage.DATE_ARGS);
        String check=(String)UserInfo1.get("userid");
        UserInfo=UserInfo1;
        Log.d("MydebugMess : ----", check);
        //movieData=new MovieData();
        mPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),2);
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
            count=2;

        }

        @Override
        public Fragment getItem(int position){
            if(position==0)
                return Fragment_DetailView.newInstance(UserInfo);
            else
                return MyRecyclerView2.newInstance(0,(String)UserInfo.get("userid"));
        }

        @Override
        public int getCount(){
            return 2;};

        @Override
        public CharSequence getPageTitle(int postion){
            Locale l=Locale.getDefault();
            String name;
            if(postion==0)
                name="UserInfo";
            else
                name="Comments";
            return name.toUpperCase(l);
        }
    }
}
