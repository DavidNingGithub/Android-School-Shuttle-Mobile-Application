package com.example.shi.subusapp;

import java.util.HashMap;

/**
 * Created by Shi on 4/23/2015.
 */
public class NameParser {
    HashMap<String, String> names;
    public NameParser(){
        names = new HashMap<String, String>();
        names.put("cptojameslodi_weekend","College Place to James Street by James Street");
        names.put("jamesloditocp_weekend","James Street to College Place by James Street");
        names.put("JamesLodi","James and Lodi");
        names.put("EGenIrv","E.Genesee and Irving");
        names.put("EGenUni","E.Genesee and Univ. Ave.");
        names.put("EGenWes","E.Genesee and Westcott");
        names.put("WesEuc","Westcott and Euclid");
        names.put("CP","College Place");
    }
    public String get(String str) {
        if(names != null && str != null)
            return names.get(str);
        return "";
    }
}
