package com.example.shi.subusapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shi on 4/12/2015.
 */
public class BusSpinnerAdapter extends BaseAdapter {
    List<String> data;
    Context context;
    public BusSpinnerAdapter(List<String> buses, Context con) {
        data = buses;
        context = con;
    }
    @Override
    public int getCount() {
        if(data != null)
            return data.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        if(data != null)
            return data.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.spinner_row, parent, false);
        TextView textView = (TextView) itemView.findViewById(R.id.name);
        if(data != null && textView != null)
            textView.setText(MainActivity.parser.get(data.get(position)));
        return itemView;
    }

    public void setData(List<String> buses) {
        //data.clear();
        //data.addAll(buses);
        data = buses;
        notifyDataSetChanged();
    }
}
