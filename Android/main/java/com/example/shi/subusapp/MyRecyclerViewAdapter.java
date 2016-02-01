package com.example.shi.subusapp;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by TaoNing on 2015/2/9.
 */



public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Map<String,?>> mDataset;
    private Context mContext;
    RecyclerViewFragment.OnListItemSelectedListener mListener;
    LayoutInflater inflater;
    OnItemClickListener mItemClickListener;
    int mOption;

    public MyRecyclerViewAdapter(Context myContext, List<Map<String,?>> myDataset,int option){
        mContext=myContext;
        mDataset=myDataset;
        mOption=option;
    }

    public interface OnItemClickListener{
        //public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
        public void onOverflowMenuClicked(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener= mItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public ImageView vMenu;
        private RatingBar vRatingBar;
        public ViewHolder(View v){
            super(v);
            vIcon=(ImageView)v.findViewById(R.id.imageView1);
            vTitle=(TextView)v.findViewById(R.id.textTitle);
           // vDescription=(TextView)v.findViewById(R.id.textDesp);
            vMenu=(ImageView)v.findViewById(R.id.overflow_button);
            //vRatingBar=(RatingBar)v.findViewById(R.id.ratingBar);

            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(mListener!=null){
                        HashMap<String,?>movie=(HashMap<String,?>)mDataset.get(getPosition());
                        mListener.onItemClick(getPosition(),movie);
                    }
                }


            });
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemLongClick(v,getPosition());
                    }
                    return true;
                }
            });
            if(vMenu!=null){
                vMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mItemClickListener!=null){
                            mItemClickListener.onOverflowMenuClicked(v,getPosition());
                        }
                    }
                });
            }


        }
        public void bindMovieData(Map<String,?> movie, int position){
            if(vTitle!=null)
                vTitle.setText((String)movie.get("name"));
            /*if(vDescription!=null)
                vDescription.setText((String)movie.get("description"));*/
            if(vIcon!=null)
                vIcon.setImageResource((Integer)movie.get("image"));
            //vCheckbox.setChecked((Boolean)movie.get("selection"));
            /*if(vRatingBar!=null)
                vRatingBar.setRating(Float.parseFloat(movie.get("rating").toString())/2);*/
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v;
        switch (mOption){
            case 0:
                v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview4,parent,false);
                break;
            case 1:
                v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview2,parent,false);
                break;
            default:
                v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview4,parent,false);
                break;
        }
        //  v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        RecyclerView.ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        Map<String,?> movie = mDataset.get(position);
        ViewHolder lowViewHolder = (ViewHolder) holder;
        lowViewHolder.bindMovieData(movie, position);
    }

    @Override
    public int getItemViewType(int position) {
        Map<String,?>item = mDataset.get(position);
        double rating = (Double)item.get("rating");
        if(rating>8.0)
            return 0;
        else
            return 1;
    }

    @Override
    public int getItemCount(){return mDataset.size();}

    public void duplicate(int position) {
        HashMap<String, ?> itemMap=(HashMap<String, ?>) mDataset.get(position);
        HashMap<String, ?> newitemMap=(HashMap<String, ?>) itemMap.clone();
        mDataset.add(position+1,newitemMap);
    }

    public void SetOnListItemSelectedListener(final RecyclerViewFragment.OnListItemSelectedListener mListItemSelectedListener){
        this.mListener= mListItemSelectedListener;
    }

}
