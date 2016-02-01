package com.example.shi.subusapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class FrontPageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new FindScheduleFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_front_page, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private BusInfoDBHelper busDBHelper;
        private SQLiteDatabase busDB;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_front_page, container, false);
            final DBManager dbManager = new DBManager(getActivity(),DBManager.DB_NAME, null, DBManager.VERSION);
            //dbManager.createTable("bus_James");
            //dbManager.initBusJames();
            final EditText inputTxt = (EditText) rootView.findViewById(R.id.input_txt);
            final TextView outputTxt = (TextView) rootView.findViewById(R.id.output_txt);
            Button submitBut = (Button) rootView.findViewById(R.id.submit);
            submitBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String stop = "EGenUni";
                    String time = "'06:31:00'";
                    String newTime = "'06:31:30'";
                    String busName = "bus_James";
                    dbManager.dropTable(busName);
                    dbManager.createTable(busName);
                    dbManager.initBusJames();
                    dbManager.updateTable(busName, stop, time, newTime);
                    String text = dbManager.queryDB(busName);
                    /*String[] schedule = dbManager.getBusInfoByStopAndTime(stop,time);
                    if(schedule == null) {
                        outputTxt.setText("Fail to find schedule about " + stop);
                        return;
                    }
                    String text = "";
                    for(int i = 0; i < schedule.length; i++)
                        text += schedule[i] + '\n';*/
                    //Toast.makeText(getActivity(),"Text = " + text + ", num = "+schedule.length,Toast.LENGTH_LONG).show();
                    outputTxt.setText(text);
                    //dbManager.dropTable("bus_James");


                }
            });
            return rootView;
        }

    }
}
