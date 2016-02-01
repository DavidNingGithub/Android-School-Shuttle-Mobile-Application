package com.example.shi.subusapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shi on 4/14/2015.
 */
public class TimeListAdapter extends BaseAdapter {
    List<String[]> data = null;
    Context context;
    String busName = null;
    String stopName = null;
    String selTime = null;
    OnClickListener myReportListener;
    public TimeListAdapter(List<String[]> l, Context con, String bus, String stop, String seltime) {
        data = l;
        context = con;
        busName = bus;
        stopName = stop;
        selTime = seltime;
    }
    @Override
    public int getCount() {
        if(data != null)
            return data.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(data != null)
            return data.get(position);
        return null;
    }
    public interface OnClickListener {
        public void onReportClick(View v,View itemView, String reporter);
    }
    public void SetOnClickeListener(OnClickListener listener) {
        myReportListener = listener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.time_row,parent,false);
        TextView timeTxt = (TextView) itemView.findViewById(R.id.time);
        final TextView reporterTxt = (TextView) itemView.findViewById(R.id.reporter);
        if(timeTxt != null && data != null && reporterTxt != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String curtime = formatter.format(curDate);
            String[] t = data.get(position);
            timeTxt.setText(t[0]);
            if(t[1] != null)
                reporterTxt.setText("reported by "+t[1]);
            //if(selTime != null)
            //       curtime = selTime;
            if(t[0].compareTo(curtime) <= 0) {
                Log.w("TimeListAdapter: getView curtime = ", curtime);
                Log.w("TimeListAdapter: getView time = ", t[0]);
                itemView.setBackgroundColor(0xFFFFFFF4);
            }
        }
        final String user = "shishi";
        TextView reportBut = (TextView) itemView.findViewById(R.id.report_gone);
        reportBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myReportListener.onReportClick(v,itemView, reporterTxt.getText().toString());
            }
        });
        return itemView;
    }
    public void setData(List<String[]> l ) {
        data = l;
        notifyDataSetChanged();
    }

}
