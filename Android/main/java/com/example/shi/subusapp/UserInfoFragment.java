package com.example.shi.subusapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.Activity;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.Date;
import java.util.HashMap;

import java.util.HashMap;

/**
 * Created by Tommy on 2/18/15.
 */
public class UserInfoFragment extends Fragment {
    private HashMap<String,?> movie;
    private static final String ARG_MOVIE = "movie";
    private static final int REQUEST_DATE=0;
    public static final String DATE_ARGS1 = "index";
    private int total=0;
    //private MenuItemCompat menuItemCompat;
    private TextView result;
    private ShareActionProvider shareActionProvider;


    public static UserInfoFragment newInstance(HashMap<String,?>movie){
        UserInfoFragment fragment=new UserInfoFragment();
        Bundle args=new Bundle();
        args.putSerializable(ARG_MOVIE,movie);
        fragment.setArguments(args);
        return fragment;

    }

    public UserInfoFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstateState){
        super.onCreate(savedInstateState);
        //setHasOptionsMenu(true);
        if(getArguments()!=null){
            movie = (HashMap<String,?>) getArguments().getSerializable(ARG_MOVIE);
        }
        if(savedInstateState!=null){
            total=savedInstateState.getInt("Total");
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("Total",total );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.userinfo_fragment,container,false);
        setHasOptionsMenu(true);
        if(MainActivity.BackColor != 0){
            rootView.setBackgroundColor(MainActivity.BackColor);
        }
        final TextView name =(TextView) rootView.findViewById(R.id.textView7);
        final TextView director =(TextView) rootView.findViewById(R.id.textView11);
        final TextView cast =(TextView) rootView.findViewById(R.id.textView15);
        final TextView rating =(TextView) rootView.findViewById(R.id.textView13);
        result=rating;
        final TextView description =(TextView) rootView.findViewById(R.id.textView17);
        //final TextView click=(TextView) rootView.findViewById(R.id.textView3);
        final RatingBar ratingbar =(RatingBar) rootView.findViewById(R.id.ratingBar);
        //final TextView year=(TextView)rootView.findViewById(R.id.textView9);
        final ImageView image =(ImageView) rootView.findViewById(R.id.imageView9);
        name.setText((String)movie.get("userid"));
        director.setText((String)movie.get("BriefDP"));
        cast.setText((String)movie.get("Location"));
        rating.setText((String)movie.get("rating").toString());
        description.setText((String)movie.get("Description"));
        //year.setText((String)movie.get("year"));
        //getActivity().getResources().getIdentifier((String)movie.get(""), "drawable", getActivity().getPackageName())
        ratingbar.setRating(Float.parseFloat(movie.get("rating").toString())/2);
        image.setImageResource((Integer)movie.get("Image"));
        //click.setText("Wanna leave feeback");
       /* click.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                *//*getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.container_frontPage, CommentPage.newInstance(movie))
                        .addToBackStack(null)
                        .commit();*//*
                Intent intent=new Intent(getActivity(),CommentActivity.class);
                intent.putExtra(DATE_ARGS1,movie);
                startActivityForResult(intent, REQUEST_DATE);
            }
        });*/
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.userinfo_modify,menu);
        /*MenuItem shareItem=menu.findItem(R.id.action_share);
        shareActionProvider=(ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent intentshare=new Intent(Intent.ACTION_SEND);
        intentshare.setType("text/plain");
        intentshare.putExtra(Intent.EXTRA_TEXT,(String)movie.get("name"));
        if(shareActionProvider!=null)
            shareActionProvider.setShareIntent(intentshare);*/
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item != null && item.getItemId() == R.id.edit_profile) {
            if(MainActivity.CurrentUser.matches(""))
                Toast.makeText(getActivity(),"Please Login first",Toast.LENGTH_SHORT).show();
            else {
                Intent intent=new Intent(getActivity(),EditActivity.class);
                intent.putExtra(MyRecyclerView.DATE_ARGS1,MainActivity.CurrentUserInf);
                startActivityForResult(intent, REQUEST_DATE);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(data==null) return;
        Uri contactUri = data.getData();

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            /*String input = (String) data.getSerializableExtra(MyDialogFragment.TEXT_ARGS);
            String input2 = (String) data.getSerializableExtra(MyDialogFragment.TEXT_ARGS1);
            Double afterpros=(Double)MainActivity.CurrentUserInf.get("rating")*(Integer)movie.get("ratingcount")+Double.parseDouble(input2);
            System.out.println(afterpros);
            Double result1=afterpros/((Integer)movie.get("ratingcount")+1);
            result1=Math.round(result1*100.0)/100.0;
            result.setText(result1.toString());
            Toast.makeText(getActivity(),input,Toast.LENGTH_SHORT).show();*/
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_frontPage, UserInfoFragment.newInstance(MainActivity.CurrentUserInf))
                    .commit();
        }
    }
}
