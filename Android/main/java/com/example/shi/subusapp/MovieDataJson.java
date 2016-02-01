package com.example.shi.subusapp;

import android.content.Context;
import android.util.Log;

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

public class MovieDataJson {

    List<Map<String,?>> moviesList;
    boolean loginresult;
    boolean userresult;
    public List<Map<String, ?>> getMoviesList() {
        return moviesList;
    }
    public static final String FILE_SERVER="http://www.subusapp.com/";
    public int getSize(){
        return moviesList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < moviesList.size()){
            return (HashMap) moviesList.get(i);
        } else return null;
    }

    public MovieDataJson()  {moviesList=new ArrayList<Map<String, ?>>();}

    public void downloadMovieDataJson(String json_url){
        Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"111111111");
        String moviesArray=MyUtility.downloadJSON(json_url);
        if(moviesArray==null){
            Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"111111111");
            return ;
        }
        try{
            JSONArray movieJsonArray=new JSONArray(moviesArray);
            for(int i=0;i<movieJsonArray.length();i++){
                JSONObject movieJsonObject=(JSONObject) movieJsonArray.get(i);
                if(movieJsonObject!=null){
                    String name=(String) movieJsonObject.get("name");
                    String url=(String) movieJsonObject.get("url");
                    double rating=Double.parseDouble(movieJsonObject.get("rating").toString());
                    String description=(String)movieJsonObject.get("description");

                    String id=(String)movieJsonObject.get("id");
                    String detailinfo=MyUtility.downloadJSON(FILE_SERVER+"movies/id/"+id);
                    String year=null;
                    String stars=null;
                    String director=null;
                    String length=null;
                    if(detailinfo==null){
                        Log.d("MyDebugMsg","Having Trouble loading URL: "+FILE_SERVER+id+".json");
                        return ;
                    }
                    try{
                        JSONObject movieDetail=new JSONObject(detailinfo);
                        if(movieDetail!=null){
                            year=(String)movieDetail.get("year");
                            stars=(String)movieDetail.get("stars");
                            director=(String)movieDetail.get("director");
                            length=(String)movieDetail.get("length");
                        }

                    }catch (JSONException ix){
                        Log.d("MyDebugMsg","JASONException in DownloadMovieDataJason");
                        ix.printStackTrace();
                    }
                    moviesList.add(createMovie(name,description,rating,url,id,director,stars,year,length));
                }
            }
        }catch(JSONException ex){
            Log.d("MyDebugMsg","JASONException in DownloadMovieDataJason");
            ex.printStackTrace();
        }

    }
    public void Logincheck(String json_url){
        Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"2222222");
        String result=MyUtility.downloadJSON(json_url);
        if(result==null){
            Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"333333");
            return ;
        }
        Log.d("MyDebugMsg",result);
        Integer numresult;
        try{
            JSONArray checkresult1=new JSONArray(result);
            if(checkresult1!=null){
                JSONObject temp=(JSONObject)checkresult1.get(0);
                numresult=(Integer)temp.get("Result");///jlkdsajflksad;fsadjfkasdjk;fljsad;kfjasdk;lfj
                //numresult=Double.parseDouble(temp.get("Rating").toString());
                Log.d("MyDebugMsg",numresult.toString()+"hfuihxchuvihds");
                if(numresult==1)
                    loginresult=true;
                else
                    loginresult=false;
            }

        }catch (JSONException ix){
            Log.d("MyDebugMsg","JASONException in DownloadMovieDataJason");
            ix.printStackTrace();
        }
    }
    public void UserInfo(String json_url){
        Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"2222222");
        String result=MyUtility.downloadJSON(json_url);
        if(result==null){
            Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"333333");
            return ;
        }
        Log.d("MyDebugMsg",result);
        Double numresult;
        try{
            JSONArray checkresult1=new JSONArray(result);
            if(checkresult1!=null){
                JSONObject temp=(JSONObject)checkresult1.get(0);
                //numresult=(Integer)temp.get("Result");///jlkdsajflksad;fsadjfkasdjk;fljsad;kfjasdk;lfj
                numresult=Double.parseDouble(temp.get("Rating").toString());
                Log.d("MyDebugMsg",numresult.toString()+"hfuihxchuvihds");
                if(numresult==1)
                    loginresult=true;
                else
                    loginresult=false;
            }

        }catch (JSONException ix){
            Log.d("MyDebugMsg","JASONException in DownloadMovieDataJason");
            ix.printStackTrace();
        }
    }
    public void UserIDcheck(String json_url){
        Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"2222222");
        String result=MyUtility.downloadJSON(json_url);
        if(result==null){
            Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"333333");
            return ;
        }
        Log.d("MyDebugMsg",result);
        Integer numresult;
        try{
            JSONArray checkresult1=new JSONArray(result);
            if(checkresult1!=null){
                JSONObject temp=(JSONObject)checkresult1.get(0);
                numresult=(Integer)temp.get("Result");
                Log.d("MyDebugMsg",numresult.toString()+"hfuihxchuvihds");
                if(numresult==1)
                    userresult=false;
                else
                    userresult=true;
            }

        }catch (JSONException ix){
            Log.d("MyDebugMsg","JASONException in DownloadMovieDataJason");
            ix.printStackTrace();
        }
        /*try{
            JSONArray movieJsonArray=new JSONArray(moviesArray);
            for(int i=0;i<movieJsonArray.length();i++){
                JSONObject movieJsonObject=(JSONObject) movieJsonArray.get(i);
                if(movieJsonObject!=null){
                    String name=(String) movieJsonObject.get("name");
                    String url=(String) movieJsonObject.get("url");
                    double rating=Double.parseDouble(movieJsonObject.get("rating").toString());
                    String description=(String)movieJsonObject.get("description");

                    String id=(String)movieJsonObject.get("id");
                    String detailinfo=MyUtility.downloadJSON(FILE_SERVER+"movies/id/"+id);
                    String year=null;
                    String stars=null;
                    String director=null;
                    String length=null;
                    if(detailinfo==null){
                        Log.d("MyDebugMsg","Having Trouble loading URL: "+FILE_SERVER+id+".json");
                        return ;
                    }
                    try{
                        JSONObject movieDetail=new JSONObject(detailinfo);
                        if(movieDetail!=null){
                            year=(String)movieDetail.get("year");
                            stars=(String)movieDetail.get("stars");
                            director=(String)movieDetail.get("director");
                            length=(String)movieDetail.get("length");
                        }

                    }catch (JSONException ix){
                        Log.d("MyDebugMsg","JASONException in DownloadMovieDataJason");
                        ix.printStackTrace();
                    }
                    moviesList.add(createMovie(name,description,rating,url,id,director,stars,year,length));
                }
            }
        }catch(JSONException ex){
            Log.d("MyDebugMsg","JASONException in DownloadMovieDataJason");
            ex.printStackTrace();
        }*/

    }
    public void SignUp(String json_url){
        Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"2222");
        String result=MyUtility.downloadJSON(json_url);
        if(result==null){
            Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"333333");
            return ;
        }
        Log.d("MyDebugMsg",result);

    }

//    public MovieDataJson(Context context) throws JSONException {
//        String description = null;
//		String length = null;
//		String year = null;
//		double rating = 0.0;
//		String director = null;
//		String stars = null;
//		String url = null;
//        String name = null;
//        String drawablename = null;
//        int resID = 0;
//        JSONArray moviesJsonArray = null;
//        JSONObject movieJsonObj = null;
//        moviesList = new ArrayList<Map<String,?>>();
//        String moviesArray = loadMovieJSONFromAsset(context);
//        moviesJsonArray = new JSONArray(moviesArray);
//        for(int i = 0; i <moviesJsonArray.length();i++){
//            movieJsonObj = (JSONObject) moviesJsonArray.get(i);
//            if(movieJsonObj != null) {
//                name = (String) movieJsonObj.get("name");
//                year = (String) movieJsonObj.get("year");
//                length = (String) movieJsonObj.get("length");
//                rating = Double.parseDouble(movieJsonObj.get("rating").toString());
//                director = (String) movieJsonObj.get("director");
//                stars = (String) movieJsonObj.get("stars");
//                url = (String) movieJsonObj.get("url");
//                description = (String) movieJsonObj.get("description");
//                drawablename = (String) movieJsonObj.get("image");
//                resID = context.getResources().getIdentifier(drawablename, "drawable", context.getPackageName());
//            }
//            moviesList.add(createMovie(name, resID, description, year, length, rating, director, stars, url));
//
//        }
//    }


    private HashMap createMovie(String name, String description, double rating, String url, String id,String director, String stars,String year,String length) {
        HashMap movie = new HashMap();
        movie.put("name", name);
        movie.put("description", description);
        movie.put("rating",rating);
        movie.put("url",url);
        movie.put("id",id);
        movie.put("director",director);
        movie.put("stars",stars);
        movie.put("year",year);
        movie.put("length",length);
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




//package com.example.apple.lab5;
//
//import android.content.Context;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MovieDataJson {
//
//    List<Map<String,?>> moviesList;
//
//    public List<Map<String, ?>> getMoviesList() {
//        return moviesList;
//    }
//
//    public int getSize(){
//        return moviesList.size();
//    }
//
//    public HashMap getItem(int i){
//        if (i >=0 && i < moviesList.size()){
//            return (HashMap) moviesList.get(i);
//        } else return null;
//    }
//
//    public MovieDataJson(Context context) throws JSONException {
//        String description = null;
//		String length = null;
//		String year = null;
//		double rating = 0.0;
//		String director = null;
//		String stars = null;
//		String url = null;
//        String name = null;
//        String drawablename = null;
//        int resID = 0;
//        JSONArray moviesJsonArray = null;
//        JSONObject movieJsonObj = null;
//        moviesList = new ArrayList<Map<String,?>>();
//        String moviesArray = loadMovieJSONFromAsset(context);
//        moviesJsonArray = new JSONArray(moviesArray);
//        for(int i = 0; i <moviesJsonArray.length();i++){
//            movieJsonObj = (JSONObject) moviesJsonArray.get(i);
//            if(movieJsonObj != null) {
//                name = (String) movieJsonObj.get("name");
//                year = (String) movieJsonObj.get("year");
//                length = (String) movieJsonObj.get("length");
//                rating = Double.parseDouble(movieJsonObj.get("rating").toString());
//                director = (String) movieJsonObj.get("director");
//                stars = (String) movieJsonObj.get("stars");
//                url = (String) movieJsonObj.get("url");
//                description = (String) movieJsonObj.get("description");
//                drawablename = (String) movieJsonObj.get("image");
//                resID = context.getResources().getIdentifier(drawablename, "drawable", context.getPackageName());
//            }
//            moviesList.add(createMovie(name, resID, description, year, length, rating, director, stars, url));
//
//        }
//    }
//
//
//    private HashMap createMovie(String name, int image, String description, String year,
//                                String length, double rating, String director, String stars, String url) {
//        HashMap movie = new HashMap();
//        movie.put("image",image);
//        movie.put("name", name);
//        movie.put("description", description);
//		movie.put("year", year);
//		movie.put("length",length);
//		movie.put("rating",rating);
//		movie.put("director",director);
//		movie.put("stars",stars);
//		movie.put("url",url);
//        movie.put("selection",false);
//        return movie;
//    }
//
//    public String loadMovieJSONFromAsset(Context context) {
//        String json = null;
//        try {
//            InputStream is = context.getAssets().open("movie.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
//
//    public int findFirst(String query) {
//        for(int i=0;i<moviesList.size();i++)
//        {
//            String Str=(String)moviesList.get(i).get("name");
//            if(Str.matches(query+".*")==true)
//            {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    public void removeItem(int position) {moviesList.remove(position);
//    }
//}
