package com.example.shi.subusapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Shi on 4/14/2015.
 */
public class BusViewPagerFragment extends Fragment{
    public static String BUSNAME= "BUSNAME";
    private BusDataJson busData;
    private String busName = null;

    public BusViewPagerFragment() {}

    public static BusViewPagerFragment newInstance(String bus) {
        BusViewPagerFragment fragment = new BusViewPagerFragment();
        Bundle args = new Bundle();
        args.putString(BUSNAME,bus);
        fragment.setArguments(args);
        Log.w("bus = ", bus);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w("BusViewPagerFragment: ", "onCreateView1");
        setHasOptionsMenu(true);
        setRetainInstance(true);
        if(getArguments() != null) {
            busName = getArguments().getString(BUSNAME);
            Log.w("busName = ", busName);
            busData = new BusDataJson();
        }
        View rootView = inflater.inflate(R.layout.stop_time_pager,container,false);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.bus_pager);
        BusViewPagerAdapter adapter = new BusViewPagerAdapter(getFragmentManager(),busName, null);
        pager.setAdapter(adapter);
        Log.w("BusViewPagerFragment: ", "onCreateView2");
        if(busName != null) {
            DownloadstopsAsyncTask busesDownloader = new DownloadstopsAsyncTask(adapter);
            busesDownloader.execute(new String[]{busName});
        }
        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                final  float normalized_position = Math.abs(Math.abs(position) - 1);
                view.setScaleX(normalized_position/2 + 0.5f);
                view.setScaleY(normalized_position/2 + 0.5f);
            }
        });
        //TextView title = (TextView) getActivity().findViewById(R.id.title);
        //title.setText(MainActivity.parser.get(busName));
        Log.w("BusViewPagerFragment: ", "onCreateView3");
        return rootView;
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //final String station = getArguments().getString(busName);

        inflater.inflate(R.menu.actionbar_schedule, menu);
        MenuItem Item = menu.findItem(R.id.action_schedule);
        Item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getFragmentManager().beginTransaction()
                        // .setCustomAnimations(R.animator.fad_in,R.animator.fade_out)
                        .replace(R.id.container_frontPage, Fragment_schedule_detail_pic.newInstance(busName))
                        .commit();
                //TextView title = (TextView) getActivity().findViewById(R.id.title);
                //title.setText("James Street");
                return false;
            }
        });
        MenuItem switchItem = menu.findItem(R.id.action_switch);
        switchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String name;
                if(busName.equals("cptojameslodi_weekend"))
                    name = "jamesloditocp_weekend";
                else if(busName.equals("jamesloditocp_weekend"))
                    name = "cptojameslodi_weekend";
                else name = busName;
                getFragmentManager().beginTransaction()
                        // .setCustomAnimations(R.animator.fad_in,R.animator.fade_out)
                        .replace(R.id.container_frontPage, BusViewPagerFragment.newInstance(name))
                        .commit();
                return false;
            }
        });
    }
    private class DownloadstopsAsyncTask extends AsyncTask<String, Void, List<String>> {
        private final WeakReference<BusViewPagerAdapter> adapterReference;

        public DownloadstopsAsyncTask(BusViewPagerAdapter a) {
            //adapterReference = new WeakReference<BusSpinnerAdapter>(adapter);
            adapterReference = new WeakReference<BusViewPagerAdapter>(a);
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            if(strings == null || strings.size() < 1) {
                new DialogFragment_NoConnection().show(getFragmentManager(), "NoConnection");
            }
            else if(adapterReference != null) {
                final BusViewPagerAdapter adapter = adapterReference.get();
                if (adapter != null) {
                    adapter.setData(strings);
                    Log.w("BusViewPager onPostExecute stops", Integer.toString(strings.size()));
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
                Log.w("BusViewPager download stops", Integer.toString(result.size()));
            } catch (Exception e) {
                Log.d("MyDebugMsg", "IOException in DownloadStopsAsyncTask");
                e.printStackTrace();
            }
            return result;
        }
    }
}
