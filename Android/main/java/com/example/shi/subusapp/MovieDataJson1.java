package com.example.shi.subusapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDataJson1 {

    List<Map<String,?>> moviesList;
    Context mContext;
    public List<Map<String, ?>> getMoviesList() {
        return moviesList;
    }

    public int getSize(){
        return moviesList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < moviesList.size()){
            return (HashMap) moviesList.get(i);
        } else return null;
    }

    public MovieDataJson1(Context context) throws JSONException {
        String description = null;
        String length = null;
        String year = null;
        double rating = 0.0;
        String director = null;
        String stars = null;
        String url = null;
        String name = null;
        String drawablename = null;
        int resID = 0;
        JSONArray moviesJsonArray = null;
        JSONObject movieJsonObj = null;
        moviesList = new ArrayList<Map<String,?>>();
        String moviesArray = loadMovieJSONFromAsset(context);
        moviesJsonArray = new JSONArray(moviesArray);
        for(int i = 0; i <moviesJsonArray.length();i++){
            movieJsonObj = (JSONObject) moviesJsonArray.get(i);
            if(movieJsonObj != null) {
                name = (String) movieJsonObj.get("name");
                year = (String) movieJsonObj.get("year");
                length = (String) movieJsonObj.get("length");
                rating = Double.parseDouble(movieJsonObj.get("rating").toString());
                director = (String) movieJsonObj.get("director");
                stars = (String) movieJsonObj.get("stars");
                url = (String) movieJsonObj.get("url");
                description = (String) movieJsonObj.get("description");
                drawablename = (String) movieJsonObj.get("image");
                resID = context.getResources().getIdentifier(drawablename, "drawable", context.getPackageName());
            }
            moviesList.add(createMovie(name,drawablename, resID, description, year, length, rating, director, stars, url));

        }
    }


    private HashMap createMovie(String name,String imagename, int image, String description, String year,
                                String length, double rating, String director, String stars, String url) {
        HashMap movie = new HashMap();
        movie.put("image",image);
        movie.put("title",imagename);
        movie.put("name", name);
        movie.put("description", description);
        movie.put("year", year);
        movie.put("length",length);
        movie.put("rating",rating);
        movie.put("director",director);
        movie.put("stars",stars);
        movie.put("url",url);
        movie.put("selection",false);
        return movie;
    }

    public String loadMovieJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("movie.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public int findFirst(String query) {
        for(int i=0;i<moviesList.size();i++)
        {
            String Str=(String)moviesList.get(i).get("name");
            if(Str.matches(query+".*")==true)
            {
                return i;
            }
        }
        return -1;
    }

    public void removeItem(int position) {moviesList.remove(position);
    }
}
