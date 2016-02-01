package com.example.shi.subusapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by Shi on 4/11/2015.
 */
public class BusDataJson {
    List<String> busesList = null;
    List<String> stopsList = null;
    HashMap<String,String> updateHashMap = null;
    MyUtility downloader;
    public BusDataJson() {
        busesList = new LinkedList<String>();
        downloader = new MyUtility();
    }

    public List<String> getBusesList() {
        return busesList;
    }

    public List<String> getStopsList() {
        return stopsList;
    }

    public int downloadBuses() throws JSONException {
        if(busesList != null)
            busesList.clear();
        String url = "http://www.subusapp.com/buses/";
        String busesArray = downloader.downloadJSON(url);
        if(busesArray == null) return -1;
        JSONArray busesJsonArray = new JSONArray(busesArray);
        JSONObject movieJsonObj = null;
        for(int i = 0; i < busesJsonArray.length(); i++) {
            movieJsonObj = (JSONObject) busesJsonArray.get(i);
            if(movieJsonObj.get("Tables_in_subusdb").toString().contains("_temp") || !movieJsonObj.get("Tables_in_subusdb").toString().contains("weekend") )
                continue;
            busesList.add(movieJsonObj.get("Tables_in_subusdb").toString());
        }
        return 0;
    }

    /*public HashMap downloadBusSchedule(String bus, String stop, String time) throws JSONException {
        HashMap<String, List<String>> columns = new HashMap<String, List<String>>();
        String url = "http://www.subusapp.com/buses/bus/"+bus+"_temp/stop/"+stop+"/time/"+time;
        Log.d("MyDebugMsg url = ", url);
        String temp;
        String busesArray = downloader.downloadJSON(url);
        if(busesArray == null) {
            Log.d("Fail to download url = ", url);
            return null;
        }
        JSONArray busesJsonArray = new JSONArray(busesArray);
        if(busesJsonArray.length() < 1) return null;
        JSONObject movieJsonObj;
        Iterator<String> keys;
        for(int i = 0; i < busesJsonArray.length(); i++) {
            movieJsonObj = (JSONObject) busesJsonArray.get(i);
            keys = movieJsonObj.keys();
            while(keys.hasNext()) {
                temp = keys.next();
                if(!columns.containsKey(temp)) {
                    List<String> list = new LinkedList<String>();
                    columns.put(temp,list);
                }
                columns.get(temp).add(movieJsonObj.get(temp).toString());
            }
        }
        return columns;
    }*/
    public List<String> downloadStopTime(String bus, String stop)  throws JSONException {
        List<String> timeList = new LinkedList<String>();
        String url = "http://www.subusapp.com/buses/bus/"+bus+"/stop/"+stop;
        String busesArray = downloader.downloadJSON(url);
        if(busesArray == null) {
            Log.d("Fail to download url = ", url);
            return null;
        }
        JSONArray busesJsonArray = new JSONArray(busesArray);
        JSONObject busJsonObj;
        for(int i = 0; i < busesJsonArray.length(); i++) {
            busJsonObj = (JSONObject) busesJsonArray.get(i);
            timeList.add(busJsonObj.get(stop).toString());
        }
        return timeList;
    }
    public List<List<String>> downloadBusSchedule(String bus, String stopName, String time) throws JSONException {
        //List<HashMap<String, List<String>>> schedule = new LinkedList<HashMap<String, List<String>>>();
        List<List<String>> schedules = new LinkedList<List<String>>();
        List<String> stops = new LinkedList<String>();
        stops.addAll(downloadStops(bus));
        String url, busesArray,temp;
        JSONArray busesJsonArray;
        JSONObject busJsonObj;
        Iterator<String> keys;
        //remove useless stops
        for (int i = 0; stopName != null && i < stops.size(); i++) {
             temp = stops.get(i);
             if (temp.equalsIgnoreCase(stopName)) break;
             stops.remove(i);
             i--;
        }
        for(String stop : stops) {
            if(time != null)
                url = "http://www.subusapp.com/buses/bus/"+bus+"/stop/"+stop+"/time/"+time;
            else
                url = "http://www.subusapp.com/buses/bus/"+bus+"/stop/"+stop;
            Log.d("MyDebugMsg url = ", url);
            busesArray = downloader.downloadJSON(url);
            if(busesArray == null) {
                Log.d("Fail to download url = ", url);
                return null;
            }
            List<String> list = new LinkedList<String>();
            list.add(stop);
            busesJsonArray = new JSONArray(busesArray);
            for(int i = 0; i < busesJsonArray.length(); i++) {
                busJsonObj = (JSONObject) busesJsonArray.get(i);
                list.add(busJsonObj.get(stop).toString());
            }
            schedules.add(list);
        }
        return schedules;
    }
    public List<String> downloadStops(String bus) throws JSONException {
        String url = "http://www.subusapp.com/buses/bus/"+bus;
        Log.d("MyDebugMsg downloadStops:url = ", url);
        String stopsArray = downloader.downloadJSON(url);
        if(stopsList == null)
            stopsList = new LinkedList<String>();
        else stopsList.clear();
        if(stopsArray == null) return null;
        JSONArray stopsJsonArray = new JSONArray(stopsArray);
        JSONObject stopsJsonObj = null;
        for(int i = 0; i < stopsJsonArray.length(); i++) {
            stopsJsonObj = (JSONObject) stopsJsonArray.get(i);
            stopsList.add(stopsJsonObj.get("COLUMN_NAME").toString());
        }
        return stopsList;
    }
    public HashMap<String, String> getRealTimeBusInfo(String busName, String stopName) throws JSONException {
        if(updateHashMap == null)
            updateHashMap = new HashMap<String, String>();
        else updateHashMap.clear();
        String url= "http://www.subusapp.com/update/getinfo/bus/" + busName + "/stop/" + stopName;
        String busesArray = downloader.downloadJSON(url);
        if(busesArray == null) return null;
        JSONArray busesJsonArray = new JSONArray(busesArray);
        if(busesJsonArray.length() < 1) return null;
        JSONObject movieJsonObj;
        String time, reporter;
        for(int i = 0; i < busesJsonArray.length(); i++) {
            movieJsonObj = (JSONObject) busesJsonArray.get(i);
            time = movieJsonObj.get("gonetime").toString();
            reporter = movieJsonObj.get("reporter").toString();
            updateHashMap.put(time,reporter);
        }
        return updateHashMap;
    }
    public int reportGoneBus(String bus, String stop, String time, String oldtime,String reporter){
        String url = "http://www.subusapp.com/update/bus/"+bus+"/stop/"+stop+"/time/"+time + "/oldtime/" + oldtime +"/reporter/"+reporter;
        Log.d("MyDebugMsg reportGoneBus:url = ", url);
        String busesArray = downloader.downloadJSON(url);
        if(busesArray != null)
            return -1;
        return 0;
    }

}
