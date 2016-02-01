package com.example.shi.subusapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;

/**
 * Created by Shi on 3/13/2015.
 */
public class DBManager {
    private  static final String TAG = "TestSQLite";
    public static int VERSION = 1;
    public static String DB_PATH = "/data/data/com.example.shi.subusapp/databases/";
    public static String DB_NAME = "test2.db";
    private BusInfoDBHelper busDBHelper;
    private SQLiteDatabase busDB;
    //constructor
    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        busDBHelper = new BusInfoDBHelper(context,name, factory, version);
        busDB = busDBHelper.getReadableDatabase();
    }
    //put data about bus line 20 into database
    public  void initBusJames() {
        busDB.execSQL("insert into bus_James values('05:45:00','05:50:00','05:51:00','05:55:00','06:00:00','06:05:00')");
        busDB.execSQL("insert into bus_James values('06:25:00','06:30:00','06:31:00','06:35:00','06:40:00','06:55:00')");
        busDB.execSQL("insert into bus_James values('07:10:00','07:20:00','07:21:00','07:25:00','07:30:00','07:35:00')");
        busDB.execSQL("insert into bus_James values('07:50:00','07:55:00','07:56:00','08:00:00','08:05:00','08:10:00')");
        busDB.execSQL("insert into bus_James values('08:25:00','08:30:00','08:31:00','08:35:00','08:40:00','08:45:00')");
    }
    public void writeDB(String busName, String constraint) {
        if(constraint == null) return;
        constraint = "insert into bus_table values('c','c','c','c','c','c')";
        busDB.execSQL(constraint);
    }
    public String queryDB(String busName) {
        String constraint = "select * from bus_James";
        Cursor cursor = busDB.rawQuery(constraint, null);
        String result = "";
        if(cursor == null)
            return result;
        while(cursor.moveToNext()){
            result += cursor.getString(cursor.getColumnIndex("JamesLodi"));
            result += ";\t" + cursor.getString(cursor.getColumnIndex("EGenIrv"));
            result += ";\t" + cursor.getString(cursor.getColumnIndex("EGenUni"));
            result += ";\t" + cursor.getString(cursor.getColumnIndex("EGenWes"));
            result += ";\t" + cursor.getString(cursor.getColumnIndex("WesEuc"));
            result += ";\t" + cursor.getString(cursor.getColumnIndex("CP"));
            result += "\n";
        }
        return result;
    }
    public String[] getBusInfoByStop(String stop) {
        String sql = "select "+stop+" from bus_James";
        Cursor cursor = busDB.rawQuery(sql,null);
        if(cursor == null) return null;
        int num = cursor.getCount();
        int i = 0;
        String[] result = new String[num];
        while (cursor.moveToNext()) {
            result[i++] = cursor.getString(cursor.getColumnIndex(stop));
        }
        return result;
    }
    public String[] getBusInfoByStopAndTime(String stop, String time) {
        // strict time format is required
        String sql = "select " + stop + " from bus_James where strftime(\"%H:%M:%S\", " + stop + ") >= strftime(\"%H:%M:%S\"," + time + ")";
        Log.w("sql=",sql);
        //String sql = "select EGenIrv from bus_James where strftime(\"%H:%M:%S\", EGenIrv) >= strftime(\"%H:%M:%S\", '05:51:00')";
        Cursor cursor = busDB.rawQuery(sql,null);
        if(cursor == null) return null;
        int num = cursor.getCount();
        int i = 0;
        String[] result = new String[num];
        while (cursor.moveToNext()) {
            result[i++] = cursor.getString(cursor.getColumnIndex(stop));
        }
        return result;
    }
    public void updateTable(String tableName, String stopName, String oldData, String newData) {
        /*ContentValues values = new ContentValues();
        Time time = (Time) newData;
        values.put(stopName, newData);
        String[] args = {"strftime(\"%H:%M:%S\"," + oldData + ")"};
        int result = busDB.update(tableName, values, "strftime(\"%H:%M:%S\", " + stopName + ") = strftime(\"%H:%M:%S\"," + oldData + ")", null);*/
        String sql = "update bus_James set " + stopName + " = "+ newData + "where strftime(\"%H:%M:%S\", " + stopName + ") = strftime(\"%H:%M:%S\"," + oldData + ")";
        //Log.w("update lines ",Integer.toString(result));
        busDB.execSQL(sql);

    }
    public void createTable(String name) {
        busDB.execSQL("create table if not exists bus_James(JamesLodi time primary key,EGenIrv time,EGenUni time,EGenWes time,WesEuc time,CP time)");
    }
    public void dropTable(String name) {
        busDB.execSQL("drop table bus_James");
    }
}
