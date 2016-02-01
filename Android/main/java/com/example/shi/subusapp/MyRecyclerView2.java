package com.example.shi.subusapp;

import android.app.Activity;
import android.os.AsyncTask;
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

import java.lang.ref.WeakReference;
import java.util.HashMap;


public class MyRecyclerView2 extends Fragment{
    private static final String ARG_OPTION = "option";
    private static final String ARG_OPTION2 = "option2";
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter2 mRecyclerViewAdapter2;
    private RecyclerView.LayoutManager mLayoutManager;
    private MovieDataJson2 commentData;
    //public OnListItemSelectedListener mListener;

    public static MyRecyclerView2 newInstance(int option,String id){
        MyRecyclerView2 fragment=new MyRecyclerView2();
        Bundle args = new Bundle();
        args.putInt(ARG_OPTION,option);
        args.putSerializable(ARG_OPTION2,id);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        setRetainInstance(true);
        commentData = new MovieDataJson2(getActivity());///待解决
    }

    public  MyRecyclerView2(){
        //movieData=new MovieData();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

    }

    /*public interface OnListItemSelectedListener{
        public void onItemClick1(int position,HashMap<String,?>movie);
    }*/

    /*@Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mListener=(OnListItemSelectedListener)activity;

        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    +"must implement OnItemSelectedListener");

        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setRetainInstance(true);
        final View rootView = inflater.inflate(R.layout.recyclerview, container, false);
        mRecyclerView=(RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        int option=getArguments().getInt(ARG_OPTION);
        String id=(String)getArguments().getSerializable(ARG_OPTION2);
        switch (option){
            case 0:
                mLayoutManager=new LinearLayoutManager(getActivity());
                mRecyclerViewAdapter2=new MyRecyclerViewAdapter2(getActivity(),commentData.getComment(),0);//待解决
                break;
            case 1:
                mLayoutManager=new GridLayoutManager(getActivity(),4);
                mRecyclerViewAdapter2=new MyRecyclerViewAdapter2(getActivity(),commentData.getComment(),1);
                break;
            default:
                mLayoutManager=new LinearLayoutManager(getActivity());
                mRecyclerViewAdapter2=new MyRecyclerViewAdapter2(getActivity(),commentData.getComment(),0);
                break;
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter2);
        //mRecyclerViewAdapter2.SetOnListItemSelectedListener(mListener);
       /* mRecyclerViewAdapter2.SetOnItemClickListener(new MyRecyclerViewAdapter1.OnItemClickListener() {
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
                                mRecyclerViewAdapter1.duplicate(position);
                                mRecyclerViewAdapter1.notifyItemInserted(position+1);
                                return true;
                            case R.id.item_delete:
                                userData.removeItem(position);
                                mRecyclerViewAdapter1.notifyItemRemoved(position);
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                inflater.inflate(R.menu.contextual_or_popup_menu,popup.getMenu());
                popup.show();
            }
        });*/
        MydownloadJsonAsynTask downloadJson= new MydownloadJsonAsynTask(mRecyclerViewAdapter2);
        String url=MovieDataJson2.FILE_SERVER+"/Comment/id/"+id;//rating/"+query;//待解决
        downloadJson.execute(url);
        return rootView;
    }
    private class MydownloadJsonAsynTask extends AsyncTask<String,Void,MovieDataJson2> {
        private final WeakReference<MyRecyclerViewAdapter2> adapterWeakReference2;
        public MydownloadJsonAsynTask(MyRecyclerViewAdapter2 adapter){
            adapterWeakReference2=new WeakReference<MyRecyclerViewAdapter2>(adapter);

        }
        @Override
        protected MovieDataJson2 doInBackground(String... urls){
            MovieDataJson2 threadMovieData=new MovieDataJson2(getActivity());
            for(String url:urls){
                threadMovieData.downloadDataJson1(url);
            }
            return threadMovieData;
        }
        @Override
        protected void onPostExecute (MovieDataJson2 threadMovieData){
            commentData.commentlist.clear();
            for(int i=0;i<threadMovieData.getSize2();i++){
                commentData.commentlist.add(threadMovieData.commentlist.get(i));
            }
            if(adapterWeakReference2!=null){
                final MyRecyclerViewAdapter2 adapter1=adapterWeakReference2.get();
                if(adapter1!=null){
                    adapter1.notifyDataSetChanged();
                }
            }

        }
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
                    commentData.removeItem(position);
                    mRecyclerViewAdapter2.notifyItemRemoved(position);
                    mode.finish();
                    break;
                case R.id.item_duplicate:
                    mRecyclerViewAdapter2.duplicate(position);
                    mRecyclerViewAdapter2.notifyItemInserted(position+1);
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
            HashMap hm=(HashMap) commentData.getItem2(position);
            mode.setTitle((String) hm.get("name"));
            return false;
        }
    }
}
