package com.example.shi.subusapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shi on 4/14/2015.
 */
public class StopAndTimeFragment extends Fragment {
    private String busName = null;
    private String stopName = null;
    private String selTime = null;
    public static String BUSNAME= "BUSNAME";
    public static String STOPNAME= "STOPNAME";
    public static String TIME= "TIME";
    private BusDataJson busData;
    String reporter = null;
    ViewGroup myContainer;
    public StopAndTimeFragment() {}
    public static StopAndTimeFragment newInstance(String bus, String stop, String time) {
        StopAndTimeFragment fragment = new StopAndTimeFragment();
        Bundle args = new Bundle();
        args.putString(BUSNAME,bus);
        args.putString(STOPNAME,stop);
        args.putString(TIME,time);
        fragment.setArguments(args);
        Log.w(bus,stop);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        busData = new BusDataJson();
        if(getArguments() != null) {
            busName = getArguments().getString(BUSNAME);
            stopName = getArguments().getString(STOPNAME);
            selTime = getArguments().getString(TIME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stop_time_column,container,false);
        setRetainInstance(true);
        myContainer = container;
        if(MainActivity.BackColor != 0){
            rootView.setBackgroundColor(MainActivity.BackColor);
        }
        if(MainActivity.grayDrawable) {
            Resources resources = getActivity().getResources();
            Drawable btnDrawable = resources.getDrawable(R.drawable.gray_gradient);
            rootView.setBackground(btnDrawable);
        }
        TextView stopTxt = (TextView) rootView.findViewById(R.id.stop_title);
        stopTxt.setText(MainActivity.parser.get(stopName));
        ListView timeList = (ListView) rootView.findViewById(R.id.time_list);
        final TimeListAdapter adapter = new TimeListAdapter(null, getActivity(),busName,stopName,selTime);
        timeList.setAdapter(adapter);
        Rotate3dAnimation animation = new Rotate3dAnimation(0, 360, 200, 200, 0, true);
        animation.setDuration(1000);
        LayoutAnimationController controller = new LayoutAnimationController(animation, 0.1f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        timeList.setLayoutAnimation(controller);
        if(busName != null && stopName != null) {
            DownloadStopTimeAsyncTask downloader = new DownloadStopTimeAsyncTask(adapter);
            downloader.execute(new String[]{busName,stopName});
        }
        //ImageView refreshBut = (ImageView) rootView.findViewById(R.id.refresh_stops);
        /*refreshBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (busName != null && stopName != null) {
                    DownloadStopTimeAsyncTask refreshDownloader = new DownloadStopTimeAsyncTask(adapter);
                    refreshDownloader.execute(new String[]{busName, stopName});
                }
            }
        });*/
        final RefreshableView refreshableView = (RefreshableView) rootView.findViewById(R.id.refreshable_view);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        },0);
        adapter.SetOnClickeListener( new TimeListAdapter.OnClickListener() {
            @Override
            public void onReportClick(View v, View itemView, String rep) {
                if(MainActivity.CurrentUserInf == null || MainActivity.CurrentUserInf.size() < 1) {
                    Toast.makeText(getActivity(),"Please Login to use this function",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!MainActivity.CurrentPermission) {
                    Toast.makeText(getActivity(),"Sorry, your rating is too low to use this function",Toast.LENGTH_LONG).show();
                    return;
                }
                final String user = MainActivity.CurrentUserInf.get("userid").toString();
                final String reporter = rep;
                PopupMenu popupMenu = new PopupMenu(getActivity(),v);
                TextView txt = (TextView) itemView.findViewById(R.id.time);
                final String oldtime = txt.getText().toString();
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                /*reprtertxt =(TextView) v.findViewById(R.id.reporter);
                if(reprtertxt == null)
                    Log.w("reprtertxt", "null reprtertxt");
                if(reprtertxt != null && reprtertxt.getText() != null)
                    reporter = reprtertxt.getText().toString();*/
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        String curtime = formatter.format(curDate);
                        switch (item.getItemId()) {
                            case R.id.bus_gone:
                                if(busName != null && stopName != null && oldtime != null) {
                                    UpdateBusScheduleAsyncTask downloader = new UpdateBusScheduleAsyncTask();
                                    downloader.execute(new String[]{busName, stopName,curtime, oldtime,user});
                                    DownloadStopTimeAsyncTask refresher = new DownloadStopTimeAsyncTask(adapter);
                                    refresher.execute(new String[]{busName,stopName});
                                }
                                return true;
                            case R.id.bus_not_gone:
                                if(busName != null && stopName != null && oldtime != null) {
                                    char[] tempc = curtime.toCharArray();
                                    tempc[6] = '5';
                                    Log.w("1 curtime = ",curtime);
                                    curtime = new String(tempc);
                                    Log.w("2 curtime = ",curtime);
                                    UpdateBusScheduleAsyncTask downloader = new UpdateBusScheduleAsyncTask();
                                    downloader.execute(new String[]{busName, stopName,curtime, oldtime,user});
                                    DownloadStopTimeAsyncTask refresher = new DownloadStopTimeAsyncTask(adapter);
                                    refresher.execute(new String[]{busName,stopName});
                                }
                                return true;
                            case R.id.wrong_info:
                               if(reporter == null) {
                                   Log.w("userid: ","null reporter");
                                   break;
                               }
                                String[] userId = reporter.split(" ");
                                String url=MovieDataJson.FILE_SERVER+"Users/id/"+userId[userId.length-1];
                                Log.w("userid: ",userId[userId.length-1]);
                                MydownloadJsonAsynTask2 downloadJson= new MydownloadJsonAsynTask2();
                                downloadJson.execute(url);
                                return true;
                        }
                        return false;
                    }
                });
                menuInflater.inflate(R.menu.report,popupMenu.getMenu());
                popupMenu.show();
            }
        });
        return rootView;
    }

    private class DownloadStopTimeAsyncTask extends AsyncTask<String, Void, List<String[]>> {
        private final WeakReference<TimeListAdapter> adapterReference;

        public DownloadStopTimeAsyncTask(TimeListAdapter a) {
            //adapterReference = new WeakReference<BusSpinnerAdapter>(adapter);
            adapterReference = new WeakReference<TimeListAdapter>(a);
        }

        @Override
        protected void onPostExecute(List<String[]> strings) {
            if(adapterReference != null) {
                final TimeListAdapter adapter = adapterReference.get();
                if (adapter != null) {
                    adapter.setData(strings);
                }
            }
            super.onPostExecute(strings);
        }

        @Override
        protected List<String[]> doInBackground(String... params) {
            List<String> timeList = null;
            List<String[]> result = new LinkedList<String[]>();
            HashMap<String, String> update = null;
            try {
                busData.downloadBuses();
                timeList = busData.downloadStopTime(params[0],params[1]);
                update = busData.getRealTimeBusInfo(params[0],params[1]);
                for(String time : timeList) {
                    if(update != null && update.containsKey(time))
                        result.add(new String[]{time, update.get(time)});
                    else
                        result.add(new String[]{time, null});
                }
            } catch (Exception e) {
                Log.d("MyDebugMsg", "IOException in DownloadStopsAsyncTask");
                e.printStackTrace();
            }
            return result;
        }
    }
    private class UpdateBusScheduleAsyncTask extends AsyncTask<String, Void, Void> {
        public UpdateBusScheduleAsyncTask() {}
        @Override
        protected Void doInBackground(String... params) {
            List<List<String>> result = null;
            try {
                new BusDataJson().reportGoneBus(params[0],params[1],params[2],params[3],params[4]);
            } catch (Exception e) {
                Log.d("MyDebugMsg", "IOException in DownloadBusScheduleAsyncTask()");
                e.printStackTrace();
            }
            return null;
        }
    }
    private class MydownloadJsonAsynTask2 extends AsyncTask<String,Void,MovieDataJson2> {
        //private final WeakReference<Button> buttonWeakReference;

        public MydownloadJsonAsynTask2(){
            //buttonWeakReference=new WeakReference<Button>();


        }
        @Override
        protected MovieDataJson2 doInBackground(String... urls){
            MovieDataJson2 threadMovieData=new MovieDataJson2(getActivity());
            for(String url:urls){
                threadMovieData.UserInfoByName(url);
            }
            return threadMovieData;
        }
        @Override
        protected void onPostExecute (MovieDataJson2 threadMovieData){
            /*getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_frontPage, UserInfoFragment.newInstance(threadMovieData.TempUser))
                    .addToBackStack(null)
                    .commit(); */
            Intent intent=new Intent(getActivity(),ViewPageActivity.class);
            intent.putExtra(FrontPage.DATE_ARGS,threadMovieData.TempUser);
            startActivity(intent);

        }
    }

}
