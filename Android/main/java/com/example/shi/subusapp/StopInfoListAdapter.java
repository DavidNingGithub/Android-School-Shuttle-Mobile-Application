package com.example.shi.subusapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shi on 4/12/2015.
 */
public class StopInfoListAdapter extends BaseAdapter {
    List<List<String>> data;
    Context context;
    OnClickListener myListItemListener = null;
    public StopInfoListAdapter(List<List<String>> stops, Context c) {
        context = c;
    }
    @Override
    public int getCount() {
        if(data != null) {
            Log.w("data.size = ", Integer.toString(data.size()));
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(data != null)
            return data.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public interface OnClickListener {
        public void onItemClick(View v);
    }
    public void SetOnClickeListener(OnClickListener listener) {
        myListItemListener = listener;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("MyDebug","StopInfoListAdatper getView");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.stop_info,parent, false);
        TextView txt = (TextView) itemView.findViewById(R.id.stop_info_txt);
        if(txt != null) {
            List<String> list = data.get(position);
            String text = new String();
            text += MainActivity.parser.get(list.get(0)) + "\n";
            for (int i = 1; i < list.size(); i++) {
                text += list.get(i) +",";
            }
            text = text.substring(0,text.length()-1);
            text += "\n";
            txt.setText(text);
            Log.w("text = ", text);
        }
        itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListItemListener.onItemClick(v);
            }
        });
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.list_anim);
        animation.setFillEnabled(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                itemView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return itemView;
    }

    public void setData(List<List<String>> buses) {
        //data.clear();
        //data.addAll(buses);
        data = buses;
        notifyDataSetChanged();
    }
}
