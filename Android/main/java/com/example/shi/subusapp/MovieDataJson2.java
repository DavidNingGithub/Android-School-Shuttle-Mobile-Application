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

public class MovieDataJson2 {
    Context mContext;
    List<Map<String,?>> userList;
    List<Map<String,?>> commentlist;
    public HashMap TempUser;
    //boolean loginresult;
    //boolean userresult;
    public List<Map<String, ?>> getUserList() {
        return userList;
    }
    public List<Map<String, ?>> getComment() {
        return commentlist;
    }
    public static final String FILE_SERVER="http://www.subusapp.com/";
    public int getSize(){
        return userList.size();
    }
    public int getSize2(){
        return commentlist.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < userList.size()){
            return (HashMap) userList.get(i);
        } else return null;
    }
    public HashMap getItem2(int i){
        if (i >=0 && i < commentlist.size()){
            return (HashMap) commentlist.get(i);
        } else return null;
    }
    public MovieDataJson2(Context context)  {
        mContext=context;
        userList=new ArrayList<Map<String, ?>>();
        commentlist=new ArrayList<Map<String, ?>>();
        TempUser=new HashMap();
    }
    public void UserInfoByName(String json_url){
        //Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"111111111");
        String userArray=MyUtility.downloadJSON(json_url);
        Log.d("MyDebugMsg",userArray);
        if(userArray==null){
            Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"111111111");
            return ;
        }
        try{
            JSONArray userJsonArray=new JSONArray(userArray);

            JSONObject userJsonObject=(JSONObject) userJsonArray.get(0);
            if(userJsonObject!=null){
                TempUser.clear();
                String name=(String) userJsonObject.get("UserID");
                name=name.replace("+"," ");
                double rating=Double.parseDouble(userJsonObject.get("Rating").toString());
                String Pw=(String) userJsonObject.get("UserPW");
                String Des=(String) userJsonObject.get("Description");
                Des=Des.replace("+"," ");
                String BDes=(String)userJsonObject.get("BrifeDP");
                BDes=BDes.replace("+"," ");
                Integer ratingcount=Integer.parseInt(userJsonObject.get("RatingCount").toString());
                String Loc=(String)userJsonObject.get("Location");
                Loc=Loc.replace("+"," ");
                String UIcon=(String)userJsonObject.get("Icon");
                int resID = mContext.getResources().getIdentifier(UIcon, "drawable", mContext.getPackageName());
                TempUser=createUser(name,Pw,rating,ratingcount,BDes,Des,Loc,UIcon,resID);
                /*FrontPage.CurrentUser=name;
                if(rating>6)
                    FrontPage.CurrentPermission=true;
                else
                    FrontPage.CurrentPermission=false;*//*
                Log.d("Mydebugmessage123123",FrontPage.CurrentUser);
                System.out.println((Integer)FrontPage.CurrentUserInf.get("Image"));
                Log.d("Mydebugmessage123123",(String)FrontPage.CurrentUserInf.get("passwd"));*/

            }
            Log.d("MydebugMessage","dsfasdfsadfsadfsdafsadfsdafdsafsd");
            System.out.println(userList.size());
        }catch(JSONException ex){
            Log.d("MyDebugMsg","JASONException in DownloadMovieDataJason");
            ex.printStackTrace();
        }

    }

    public void downloadDataJson(String json_url){
        //Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"111111111");
        String userArray=MyUtility.downloadJSON(json_url);
        Log.d("URL:  ",json_url);
        Log.d("MyDebugMsg","HELLLLLOO"+userArray);
        if(userArray==null){
            Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"111111111");
            return ;
        }
        try{
            JSONArray userJsonArray=new JSONArray(userArray);
            for(int i=0;i<userJsonArray.length();i++){
                JSONObject userJsonObject=(JSONObject) userJsonArray.get(i);
                if(userJsonObject!=null){
                    String name=(String) userJsonObject.get("UserID");
                    name=name.replace("+"," ");
                    double rating=Double.parseDouble(userJsonObject.get("Rating").toString());
                    String Pw=(String) userJsonObject.get("UserPW");
                    String Des=(String) userJsonObject.get("Description");
                    Des=Des.replace("+"," ");
                    String BDes=(String)userJsonObject.get("BrifeDP");
                    BDes=BDes.replace("+"," ");
                    Integer ratingcount=Integer.parseInt(userJsonObject.get("RatingCount").toString());
                    String Loc=(String)userJsonObject.get("Location");
                    Loc=Loc.replace("+"," ");
                    String UIcon=(String)userJsonObject.get("Icon");
                    int resID = mContext.getResources().getIdentifier(UIcon, "drawable", mContext.getPackageName());
                    userList.add(createUser(name,Pw,rating,ratingcount,BDes,Des,Loc,UIcon,resID));
                }

            }
            Log.d("MydebugMessage","dsfasdfsadfsadfsdafsadfsdafdsafsd");
            System.out.println(userList.size());
        }catch(JSONException ex){
            Log.d("MyDebugMsg","JASONException in DownloadMovieDataJason");
            ex.printStackTrace();
        }

    }
    public void downloadDataJson1(String json_url){
        //Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"111111111");
        String commentArray=MyUtility.downloadJSON(json_url);
        Log.d("MyDebugMsg",commentArray);
        if(commentArray==null){
            Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"111111111");
            return ;
        }
        try{
            JSONArray commentJsonArray=new JSONArray(commentArray);
            for(int i=0;i<commentJsonArray.length();i++){
                JSONObject commentJsonObject=(JSONObject) commentJsonArray.get(i);
                if(commentJsonObject!=null){
                    String name=(String) commentJsonObject.get("FPersonID");
                    name=name.replace("+"," ");
                    //double rating=Double.parseDouble(commentJsonObject.get("Rating").toString());

                    String Des=(String) commentJsonObject.get("Description");
                    Des=Des.replace("+"," ");
                    String CDate=(String)commentJsonObject.get("CDate");
                    String UIcon=(String)commentJsonObject.get("Icon");
                    int resID = mContext.getResources().getIdentifier(UIcon, "drawable", mContext.getPackageName());
                    commentlist.add(createComment(name,Des,CDate,resID));
                }

            }
            Log.d("MydebugMessage","dsfasdfsadfsadfsdafsadfsdafdsafsd");
            System.out.println(userList.size());
        }catch(JSONException ex){
            Log.d("MyDebugMsg","JASONException in DownloadMovieDataJason");
            ex.printStackTrace();
        }

    }
    //}
    public void SignUp(String json_url){
        Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"2222");
        String result=MyUtility.downloadJSON(json_url);
        if(result==null){
            Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"333333");
            return ;
        }
        Log.d("MyDebugMsg",result);

    }
    public void UserInfo(String json_url){
        //Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"111111111");
        String userArray=MyUtility.downloadJSON(json_url);
        Log.d("MyDebugMsg",userArray);
        if(userArray==null){
            Log.d("MyDebugMsg","Having Trouble loading URL: "+json_url+"111111111");
            return ;
        }
        try{
            JSONArray userJsonArray=new JSONArray(userArray);

            JSONObject userJsonObject=(JSONObject) userJsonArray.get(0);
                if(userJsonObject!=null){
                    String name=(String) userJsonObject.get("UserID");
                    name=name.replace("+"," ");
                    double rating=Double.parseDouble(userJsonObject.get("Rating").toString());
                    String Pw=(String) userJsonObject.get("UserPW");
                    String Des=(String) userJsonObject.get("Description");
                    Des=Des.replace("+"," ");
                    String BDes=(String)userJsonObject.get("BrifeDP");
                    BDes=BDes.replace("+"," ");
                    Integer ratingcount=Integer.parseInt(userJsonObject.get("RatingCount").toString());
                    String Loc=(String)userJsonObject.get("Location");
                    Loc=Loc.replace("+"," ");
                    String UIcon=(String)userJsonObject.get("Icon");
                    int resID = mContext.getResources().getIdentifier(UIcon, "drawable", mContext.getPackageName());
                    MainActivity.CurrentUserInf=createUser(name,Pw,rating,ratingcount,BDes,Des,Loc,UIcon,resID);
                    MainActivity.CurrentUser=name;
                    if(rating>6)
                        MainActivity.CurrentPermission=true;
                    else
                        MainActivity.CurrentPermission=false;
                    Log.d("Mydebugmessage123123",MainActivity.CurrentUser);
                    System.out.println((Integer)MainActivity.CurrentUserInf.get("Image"));
                    Log.d("Mydebugmessage123123",(String)MainActivity.CurrentUserInf.get("passwd"));

            }
            Log.d("MydebugMessage","dsfasdfsadfsadfsdafsadfsdafdsafsd");
            System.out.println(userList.size());
        }catch(JSONException ex){
            Log.d("MyDebugMsg","JASONException in DownloadMovieDataJason");
            ex.printStackTrace();
        }

    }
    private HashMap createUser(String userid, String Pw,double rating,Integer ratingcount, String BriefDP, String description,String Location, String Icon,int Image) {
        HashMap movie = new HashMap();
        movie.put("userid", userid);
        movie.put("passwd",Pw);
        movie.put("rating", rating);
        movie.put("ratingcount",ratingcount);
        movie.put("BriefDP",BriefDP);
        movie.put("Description",description);
        movie.put("Icon",Icon);
        movie.put("Location",Location);
        movie.put("Image",Image);
        return movie;
    }
    private HashMap createComment(String userid, String description,String CDate,int Image) {
        HashMap movie = new HashMap();
        movie.put("userid", userid);


        movie.put("Description",description);
        movie.put("Date",CDate);
        movie.put("Image",Image);
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
        for(int i=0;i<userList.size();i++)
        {
            String Str=(String)userList.get(i).get("name");
            if(Str.matches(query+".*")==true)
            {
                return i;
            }
        }
        return -1;
    }

    public void removeItem(int position) {userList.remove(position);
    }
}

