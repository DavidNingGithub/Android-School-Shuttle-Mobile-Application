package com.example.shi.subusapp;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
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
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.SearchView;
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

import org.json.JSONException;

import java.util.HashMap;


public class RecyclerViewFragment extends Fragment{
    private static final String ARG_OPTION = "option";
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MovieDataJson1 movieData;
    public OnListItemSelectedListener mListener;

    public static RecyclerViewFragment newInstance(int option){
        RecyclerViewFragment fragment=new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_OPTION,option);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        try{
            movieData = new MovieDataJson1(getActivity());
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        /*catch (JSONException e){
            e.printStackTrace();
        }*/
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.fragment_menu,menu);
        android.support.v7.widget.SearchView search= (android.support.v7.widget.SearchView)menu.findItem(R.id.action_search).getActionView();
        if(search!=null){
            search.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener(){
                @Override
                public boolean onQueryTextSubmit(String query){
                    //System.out.println(4);
                    int position=movieData.findFirst(query);
                    mRecyclerView.scrollToPosition(position);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String query){
                    int position=movieData.findFirst(query);
                    mRecyclerView.scrollToPosition(position);
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu,inflater);
    }*/

    public  RecyclerViewFragment(){
        //movieData=new MovieData();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

    }

    public interface OnListItemSelectedListener{
        public void onItemClick(int position,HashMap<String,?>movie);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mListener=(OnListItemSelectedListener)activity;

        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    +"must implement OnItemSelectedListener");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setRetainInstance(true);
        final View rootView = inflater.inflate(R.layout.recyclerview, container, false);
        if(MainActivity.BackColor != 0){
            rootView.setBackgroundColor(MainActivity.BackColor);
        }
        mRecyclerView=(RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        int option=getArguments().getInt(ARG_OPTION);
        switch (option){
            case 0:
                mLayoutManager=new LinearLayoutManager(getActivity());
                mRecyclerViewAdapter=new MyRecyclerViewAdapter(getActivity(),movieData.getMoviesList(),0);
                break;
            case 1:
                mLayoutManager=new GridLayoutManager(getActivity(),4);
                mRecyclerViewAdapter=new MyRecyclerViewAdapter(getActivity(),movieData.getMoviesList(),1);
                break;
            default:
                mLayoutManager=new LinearLayoutManager(getActivity());
                mRecyclerViewAdapter=new MyRecyclerViewAdapter(getActivity(),movieData.getMoviesList(),0);
                break;
        }
        //mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerViewAdapter=new MyRecyclerViewAdapter(getActivity(),movieData.getMoviesList());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.SetOnListItemSelectedListener(mListener);
        mRecyclerViewAdapter.SetOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                getActivity().startActionMode(new ActionBarCallBack(position));
            }
            @Override
            public void onOverflowMenuClicked(View v, final int position){
                PopupMenu popup=new PopupMenu(getActivity(),v);
                MenuInflater inflater=popup.getMenuInflater();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_duplicate:
                                mRecyclerViewAdapter.duplicate(position);
                                mRecyclerViewAdapter.notifyItemInserted(position+1);
                                return true;
                            case R.id.item_delete:
                                movieData.removeItem(position);
                                mRecyclerViewAdapter.notifyItemRemoved(position);
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                inflater.inflate(R.menu.contextual_or_popup_menu,popup.getMenu());
                popup.show();
            }
        });
        return rootView;
    }


    public class ActionBarCallBack implements ActionMode.Callback {
        int position;
        public ActionBarCallBack(int position) {
            this.position=position;
        }
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item){
            int id= item.getItemId();
            switch(id)
            {
                case R.id.item_delete:
                    movieData.removeItem(position);
                    mRecyclerViewAdapter.notifyItemRemoved(position);
                    mode.finish();
                    break;
                case R.id.item_duplicate:
                    mRecyclerViewAdapter.duplicate(position);
                    mRecyclerViewAdapter.notifyItemInserted(position+1);
                    mode.finish();
                    break;
                default:
                    break;

            }
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu){
            mode.getMenuInflater().inflate(R.menu.contextual_or_popup_menu, menu);
            return true;
        }


        @Override
        public void onDestroyActionMode(ActionMode mode){

        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode,Menu menu){
            HashMap hm=(HashMap) movieData.getItem(position);
            mode.setTitle((String) hm.get("name"));
            return false;
        }
    }
}
