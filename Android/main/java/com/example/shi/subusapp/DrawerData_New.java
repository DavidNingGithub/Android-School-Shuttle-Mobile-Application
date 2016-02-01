package com.example.shi.subusapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tommy on 3/1/15.
 */
public class DrawerData_New {
    public static final int TYPE1=1;
    public static final int TYPE2=2;
    public static final int TYPE3=3;
    List<Map<String,?>> drawerList;
    public List<Map<String,?>> getDrawerList(){return drawerList;}
    public Map<String,?> getItem(int position){return drawerList.get(position);}
    public int getSize(){return drawerList.size();}
    public DrawerData_New(){
        HashMap item;
        drawerList=new ArrayList<Map<String, ?>>();
        item=new HashMap();
        item.put("type",TYPE1);item.put("icon",R.drawable.home1);item.put("title","Home");
        drawerList.add(item);
        item=new HashMap();
        item.put("type",TYPE1);item.put("icon",R.drawable.list);item.put("title","ListView");
        drawerList.add(item);
        item=new HashMap();
        item.put("type",TYPE1);item.put("icon",R.drawable.gridview);item.put("title","GridView");
        drawerList.add(item);
        item=new HashMap();
        item.put("type",TYPE2);item.put("icon",R.drawable.simple_line);
        drawerList.add(item);
        item=new HashMap();
        item.put("type",TYPE3);item.put("title","Lab7");
        drawerList.add(item);
        item=new HashMap();
        item.put("type",TYPE3);item.put("title","Caricature");
        drawerList.add(item);
        item=new HashMap();
        item.put("type",TYPE3);item.put("title","Simple");
        drawerList.add(item);
    }
}

