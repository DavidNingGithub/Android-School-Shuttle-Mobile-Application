package com.example.shi.subusapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.Activity;


import java.util.HashMap;



public class MasterView extends ActionBarActivity
        implements RecyclerViewFragment.OnListItemSelectedListener{

    private boolean mTwoPane=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);
        //

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_list,RecyclerViewFragment.newInstance(0))
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

            /*Intent intent=new Intent();
            String input=movie.get("title").toString();
            //intent.putExtra(MyDialogFragment.DATE_ARGS,mDate);
            intent.putExtra(MyDialogFragment.TEXT_ARGS,input);
            this.setResult(Activity.RESULT_OK,intent);
            this.finish();*/
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
