package com.example.shi.subusapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity
        implements MyRecyclerView.OnListItemSelectedListener{

    public static HashMap CurrentUserInf;
    public static String CurrentUser="";
    public static boolean CurrentPermission=false;
    public static int BackColor = 0;
    public static Toolbar mToolbar;
    public static DrawerLayout mDrawerLayout;
    public static boolean grayDrawable = false;
    public static NameParser parser;
    ActionBarDrawerToggle mDrawerToggle;
    //public Person user = null;
    @Override
    public void onItemClick1(int position,HashMap<String,?>movie){
        Log.w("Main Activiey", Integer.toString(position));
        Log.d("jkljklsaklfdsjaklfjdslkj:      ",(String)movie.get("userid"));
        Intent intent=new Intent(this,ViewPageActivity.class);
        intent.putExtra(FrontPage.DATE_ARGS,movie);
        startActivity(intent);
       /* getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_frontPage, UserInfoFragment.newInstance(movie))
                .addToBackStack(null)
                .commit();*/
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CurrentUserInf=new HashMap();
        parser = new NameParser();
        setContentView(R.layout.fragment_schedule_drawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,R.string.open_drawer,R.string.close_drawer);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_frontPage, new Fragment_Home_page())
                    .commit();

        ImageView drawer = (ImageView)findViewById(R.id.expend);
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(findViewById(R.id.drawer));
            }
        });

        final Button home = (Button)findViewById(R.id.drawer_home);
        final Button schedule = (Button)findViewById(R.id.drawer_schedule);
        final Button report = (Button)findViewById(R.id.drawer_report);
        final Button contact = (Button)findViewById(R.id.drawer_contact);
        final Button search = (Button)findViewById(R.id.drawer_search);
        final Button setting = (Button)findViewById(R.id.drawer_setting);
        final Button logout = (Button)findViewById(R.id.drawer_logout);
        final TextView title = (TextView)findViewById(R.id.title);

        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(CurrentUserInf == null || CurrentUserInf.size() < 1) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_frontPage, new Fragment_Home_page())
                            .commit();
                }
                else {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_frontPage, UserInfoFragment.newInstance(MainActivity.CurrentUserInf))
                            .commit();
                }

                schedule.setBackgroundColor(Color.TRANSPARENT);
                report.setBackgroundColor(Color.TRANSPARENT);
                contact.setBackgroundColor(Color.TRANSPARENT);
                home.setBackgroundColor(BackColor);
                search.setBackgroundColor(Color.TRANSPARENT);
                setting.setBackgroundColor(Color.TRANSPARENT);
                logout.setBackgroundColor(Color.TRANSPARENT);
                title.setText("SU Bus");
                mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
            }
        });

        schedule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_frontPage, new Fragment_schedule())
                        .addToBackStack(null)
                        .commit();
                schedule.setBackgroundColor(BackColor);
                report.setBackgroundColor(Color.TRANSPARENT);
                contact.setBackgroundColor(Color.TRANSPARENT);
                home.setBackgroundColor(Color.TRANSPARENT);
                search.setBackgroundColor(Color.TRANSPARENT);
                setting.setBackgroundColor(Color.TRANSPARENT);
                logout.setBackgroundColor(Color.TRANSPARENT);
                title.setText("Schedule");
                mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
            }
        });

        report.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_frontPage, MyRecyclerView.newInstance(0))
                        .commit();
                schedule.setBackgroundColor(Color.TRANSPARENT);
                report.setBackgroundColor(BackColor);
                contact.setBackgroundColor(Color.TRANSPARENT);
                home.setBackgroundColor(Color.TRANSPARENT);
                search.setBackgroundColor(Color.TRANSPARENT);
                setting.setBackgroundColor(Color.TRANSPARENT);
                logout.setBackgroundColor(Color.TRANSPARENT);
                title.setText("User List");
                mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
            }
        });

        contact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_frontPage, new Fragment_Contact_us())
                        .commit();
                schedule.setBackgroundColor(Color.TRANSPARENT);
                report.setBackgroundColor(Color.TRANSPARENT);
                contact.setBackgroundColor(BackColor);
                home.setBackgroundColor(Color.TRANSPARENT);
                search.setBackgroundColor(Color.TRANSPARENT);
                setting.setBackgroundColor(Color.TRANSPARENT);
                logout.setBackgroundColor(Color.TRANSPARENT);
                title.setText("Contact us");
                mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
            }
        });

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_frontPage, new FindScheduleFragment())
                        .commit();
                schedule.setBackgroundColor(Color.TRANSPARENT);
                report.setBackgroundColor(Color.TRANSPARENT);
                contact.setBackgroundColor(Color.TRANSPARENT);
                home.setBackgroundColor(Color.TRANSPARENT);
                search.setBackgroundColor(BackColor);
                setting.setBackgroundColor(Color.TRANSPARENT);
                logout.setBackgroundColor(Color.TRANSPARENT);
                title.setText("Search");
                mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
            }
        });

        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_frontPage, new Fragment_Setting_Theme())
                        .commit();
                schedule.setBackgroundColor(Color.TRANSPARENT);
                report.setBackgroundColor(Color.TRANSPARENT);
                contact.setBackgroundColor(Color.TRANSPARENT);
                home.setBackgroundColor(Color.TRANSPARENT);
                search.setBackgroundColor(Color.TRANSPARENT);
                setting.setBackgroundColor(BackColor);
                logout.setBackgroundColor(Color.TRANSPARENT);
                title.setText("Settings");
                mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
            }
        });
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CurrentUserInf.clear();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_frontPage, new Fragment_Home_page())
                        .commit();
                schedule.setBackgroundColor(Color.TRANSPARENT);
                report.setBackgroundColor(Color.TRANSPARENT);
                contact.setBackgroundColor(Color.TRANSPARENT);
                home.setBackgroundColor(Color.TRANSPARENT);
                search.setBackgroundColor(Color.TRANSPARENT);
                setting.setBackgroundColor(Color.TRANSPARENT);
                logout.setBackgroundColor(BackColor);
                title.setText("Settings");
                mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.expend) {
            mDrawerLayout.openDrawer(findViewById(R.id.drawer));
            return true;
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        CurrentUserInf.clear();
        super.onDestroy();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

    }
}
