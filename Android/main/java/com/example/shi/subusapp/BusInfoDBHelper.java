package com.example.shi.subusapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Shi on 3/12/2015.
 */
public class BusInfoDBHelper extends SQLiteOpenHelper{
    private  static final String TAG = "TestSQLite";
    public BusInfoDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //输出创建数据库的日志信息
        Log.i(TAG, "create Database------------->");
        //execSQL函数用于执行SQL语句
        //db.execSQL("create table if not exists bus_20(JamesLodi time primary key,EGenIrv time,EGenUni time,EGenWes time,WesEuc time,CP time)");
        //db.execSQL("insert into bus_20 values('5:45:00','5:50:00','5:51:00','5:55:00','6:00:00','6:05:00')");
        //db.execSQL("create table if not exists bus_James(JamesLodi time primary key,EGenIrv time,EGenUni time,EGenWes time,WesEuc time,CP time)");
        db.execSQL("create table if not exists bus_James(JamesLodi varchar primary key,EGenIrv varchar,EGenUni varchar,EGenWes varchar,WesEuc varchar,CP varchar)");
        //db.openOrCreateDatabase(DB_NAME, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "update Database------------->");
    }
}
