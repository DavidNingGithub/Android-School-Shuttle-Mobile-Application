package com.example.shi.subusapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Shi on 4/12/2015.
 */
public class FindScheduleFragment extends Fragment{
    private BusDataJson busData;
    String busName;
    String stopName;
    public static int TIME_REQUEST = 0;
    EditText timeTxt;
    public void showDialog() {
        String tag = "DatePicker Dialog";
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setTargetFragment(FindScheduleFragment.this, TIME_REQUEST);
        timePickerFragment.show(getActivity().getSupportFragmentManager(), tag);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;
        String time;
        if(requestCode == TIME_REQUEST) {
            time = data.getStringExtra(TimePickerFragment.TIME_ARGS);
            timeTxt.setText(time);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        busData = new BusDataJson();
        View rootView = inflater.inflate(R.layout.scheduleandresult, container, false);
        if(MainActivity.BackColor != 0){
            rootView.setBackgroundColor(MainActivity.BackColor);
        }
        if(MainActivity.grayDrawable) {
            Resources resources = getActivity().getResources();
            Drawable btnDrawable = resources.getDrawable(R.drawable.gray_gradient);
            rootView.setBackground(btnDrawable);
        }
        //get bus names
        Spinner busspinner = (Spinner) rootView.findViewById(R.id.bus_name_spinner);
        BusSpinnerAdapter busesAdapter = new BusSpinnerAdapter(busData.busesList,getActivity());
        busspinner.setAdapter(busesAdapter);
        DownloadBusesAsyncTask busesDownloader = new DownloadBusesAsyncTask(busesAdapter);
        busesDownloader.execute(new String[]{});
        //get stop names
        Spinner stopspinner = (Spinner) rootView.findViewById(R.id.stop_name_spinner);
        final BusSpinnerAdapter stopsAdapter = new BusSpinnerAdapter(null,getActivity());
        stopspinner.setAdapter(stopsAdapter);
        //get stop info
        ListView stopsInfoListView = (ListView) rootView.findViewById(R.id.stop_info_listview);
        final StopInfoListAdapter stopsInfoAdapter = new StopInfoListAdapter(null,getActivity());
        stopsInfoListView.setAdapter(stopsInfoAdapter);
        busspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                busName = busData.busesList.get(position);
                DownloadstopsAsyncTask busesDownloader = new DownloadstopsAsyncTask(stopsAdapter);
                busesDownloader.execute(new String[]{busName});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stopspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stopName = busData.getStopsList().get(position);
                String time = timeTxt.getText().toString();
                if(!time.contains(":"))
                    time = null;
                DownloadBusScheduleAsyncTask downloader = new DownloadBusScheduleAsyncTask(stopsInfoAdapter);
                downloader.execute(new String[]{busName, stopName, time});

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        timeTxt = (EditText) rootView.findViewById(R.id.time_selector);
        timeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        timeTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String time = timeTxt.getText().toString();
                if(!time.contains(":"))
                    time = null;
                DownloadBusScheduleAsyncTask downloader = new DownloadBusScheduleAsyncTask(stopsInfoAdapter);
                downloader.execute(new String[]{busName, stopName, time});
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        stopsInfoAdapter.SetOnClickeListener(new StopInfoListAdapter.OnClickListener() {
            @Override
            public void onItemClick(View v) {
                String time = timeTxt.getText().toString();
                if(!time.contains(":"))
                    time = null;
                getActivity().getSupportFragmentManager().beginTransaction()
                        // .setCustomAnimations(R.animator.fad_in,R.animator.fade_out)
                        .replace(R.id.container_frontPage, StopAndTimeFragment.newInstance(busName,stopName,time))
                        .addToBackStack(null)
                        .commit();
            }
        });
        return rootView;
    }
    private class DownloadBusesAsyncTask extends AsyncTask<String, Void, List<String>> {
        private final WeakReference<BusSpinnerAdapter> adapterReference;
        public DownloadBusesAsyncTask(BusSpinnerAdapter a) {
            //adapterReference = new WeakReference<BusSpinnerAdapter>(adapter);
            adapterReference = new WeakReference<BusSpinnerAdapter>(a);
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            if(strings == null) {
                new DialogFragment_NoConnection().show(getFragmentManager(), "NoConnection");
            }
            if(adapterReference != null && strings != null) {
                final BusSpinnerAdapter adapter = adapterReference.get();
                if (adapter != null) {
                    adapter.setData(strings);
                }
            }
            super.onPostExecute(strings);
        }

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> result = null;
            try {
                busData.downloadBuses();
                result = busData.getBusesList();
            } catch (Exception e) {
                Log.d("MyDebugMsg", "IOException in DownloadBusesAsyncTask");
                e.printStackTrace();
            }
            return result;
        }
    }
    private class DownloadstopsAsyncTask extends AsyncTask<String, Void, List<String>> {
        private final WeakReference<BusSpinnerAdapter> adapterReference;

        public DownloadstopsAsyncTask(BusSpinnerAdapter a) {
            //adapterReference = new WeakReference<BusSpinnerAdapter>(adapter);
            adapterReference = new WeakReference<BusSpinnerAdapter>(a);
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            if(adapterReference != null && strings != null) {
                final BusSpinnerAdapter adapter = adapterReference.get();
                if (adapter != null) {
                    adapter.setData(strings);
                }
            }
            super.onPostExecute(strings);
        }

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> result = null;
            try {
                busData.downloadBuses();
                result = busData.downloadStops(params[0]);
            } catch (Exception e) {
                Log.d("MyDebugMsg", "IOException in DownloadStopsAsyncTask");
                e.printStackTrace();
            }
            return result;
        }
    }
    private class DownloadBusScheduleAsyncTask extends AsyncTask<String, Void, List<List<String>>> {
        private final WeakReference<StopInfoListAdapter> adapterReference;
        public DownloadBusScheduleAsyncTask(StopInfoListAdapter txt) {
            adapterReference = new WeakReference<StopInfoListAdapter>(txt);
        }
        @Override
        protected void onPostExecute(List<List<String>> stringLists) {
            final StopInfoListAdapter adapter = adapterReference.get();
            if(adapter != null && stringLists != null) {
                adapter.setData(stringLists);
            }
            super.onPostExecute(stringLists);
        }

        @Override
        protected List<List<String>> doInBackground(String... params) {
            List<List<String>> result = null;
            try {
                busData.downloadBuses();
                result = busData.downloadBusSchedule(params[0],params[1],params[2]);
            } catch (Exception e) {
                Log.d("MyDebugMsg", "IOException in DownloadBusScheduleAsyncTask()");
                e.printStackTrace();
            }
            return result;
        }
    }
}
