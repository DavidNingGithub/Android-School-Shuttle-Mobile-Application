package com.example.shi.subusapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tommy on 3/1/15.
 */
public class MyDrawerRecyclerViewAdapter extends RecyclerView.Adapter<MyDrawerRecyclerViewAdapter.ViewHolder> {
    private List<Map<String,?>> mDataset;
            private Context mContext;
            OnItemClickListener mItemClickListener;
            private int mCurrentposition;

            public MyDrawerRecyclerViewAdapter(Context myContext, List<Map<String,?>> myDataSet,int curr){
                mContext=myContext;
                mDataset=myDataSet;
                mCurrentposition=curr;
            }

        public void SetOnItemClickListener(final OnItemClickListener mItemClickListener){
            this.mItemClickListener= mItemClickListener;
        }

        public interface OnItemClickListener{
            public void onItemClick(View view, int position);

        }


        public class ViewHolder extends RecyclerView.ViewHolder{
            private ImageView vIcon=null;
            private TextView vTitle=null;
            private ImageView vLine=null;
            private int vViewType;
            private View vView=null;

            public ViewHolder(View v,int viewType){
                super(v);
                vView=v;
                vViewType=viewType;
                vIcon=(ImageView)v.findViewById(R.id.icon);
                vTitle=(TextView)v.findViewById(R.id.title);
                vLine=(ImageView)v.findViewById(R.id.line);

                v.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if(mItemClickListener!=null){
                            mItemClickListener.onItemClick(v,getPosition());
                        }
                        mCurrentposition=getPosition();
                        notifyDataSetChanged();
                    }
                });

            }



            public void bindData(Map<String,?> item, int position){
//            if(position==mCurrentposition)
//                vView.setBackgroundColor(0xff888888);
//            else
//                vView.setBackgroundColor(0x00000000);
                switch (vViewType){
                    case DrawerData_New.TYPE1:
                        if(vTitle!=null)
                            vTitle.setText((String)item.get("title"));
                        if(position==mCurrentposition)
                            vTitle.setBackgroundColor(0xff888888);
                        else
                            vTitle.setBackgroundColor(0x00000000);
                        if(vIcon!=null)
                            vIcon.setImageResource((Integer)item.get("icon"));
                        break;
                    case DrawerData_New.TYPE2:
                        if(vLine!=null)
                            vLine.setImageResource((Integer) item.get("icon"));
                        break;
                    case DrawerData_New.TYPE3:
                        if(vTitle!=null)
                            vTitle.setText((String)item.get("title"));
                        if(position==mCurrentposition)
                            vTitle.setBackgroundColor(0xff888888);
                        else
                            vTitle.setBackgroundColor(0x00000000);
                        break;
                    default:
                        break;
                }
        }
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Map<String,?> item=mDataset.get(position);
        holder.bindData(item,position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v;
        switch (viewType){
            case DrawerData_New.TYPE1:
                v= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.drawer_list_item_type1,parent,false);
                break;
            case DrawerData_New.TYPE2:
                v=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.drawer_list_item_type2,parent,false);
                break;
            case DrawerData_New.TYPE3:
                v=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.drawer_list_item_type3,parent,false);
                break;
            default:
                v=v= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.drawer_list_item_type1,parent,false);
                break;

        }
        ViewHolder vh=new ViewHolder(v,viewType);
        return vh;
    }

    @Override
    public int getItemViewType(int postion){
        Map<String,?> item=mDataset.get(postion);
        return (Integer) item.get("type");
    }

    @Override
    public int getItemCount(){return mDataset.size();}

}
