package com.example.shi.subusapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shi on 4/11/2015.
 */
public class SUBusesFragment extends Fragment {
    private BusDataJson busData;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        busData = new BusDataJson();
        View rootView = inflater.inflate(R.layout.fragment_front_page, container, false);
        Button but = (Button) rootView.findViewById(R.id.submit);
        final TextView txt = (TextView) rootView.findViewById(R.id.output_txt);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadBusesAsyncTask downloader = new DownloadBusesAsyncTask(txt);
                downloader.execute(new String[]{});
            }
        });
        return rootView;
    }
    private class DownloadBusesAsyncTask extends AsyncTask<String, Void, List<String>> {
        private final WeakReference<TextView> txtReference;
        public DownloadBusesAsyncTask(TextView txt) {
            txtReference = new WeakReference<TextView>(txt);
        }
        @Override
        protected void onPostExecute(List<String> strings) {
            final TextView txt = txtReference.get();
            txt.setText(strings.toString());
            super.onPostExecute(strings);
        }

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> result = null;
            try {
                busData.downloadBuses();
                result = busData.getBusesList();
            } catch (Exception e) {
                Log.d("MyDebugMsg", "IOException in loadJSONFromAsset()");
                e.printStackTrace();
            }
            return result;
        }
    }


}
