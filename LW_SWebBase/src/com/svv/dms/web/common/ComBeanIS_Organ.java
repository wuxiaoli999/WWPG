package com.svv.dms.web.common;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.util.HIUtil;

public class ComBeanIS_Organ extends ComBeanBase {
    public final static int TABLEID = 1;
    protected static TreeMap<Integer, String> map = null;
    protected static HashMap<String, Integer> map_domain = null;
    protected static HashMap<String, String> map_amid = null;

    public static List<LabelValueBean> getList(boolean hasAll, int myorgan) {
        if (map == null) load();
        TreeMap<Integer, String> temp; 
        if(myorgan <= 0) temp = map;
        else{
            temp = new TreeMap<Integer, String>();
            temp.put(myorgan, map.get(myorgan));
        }
        return toList(temp, hasAll);
    }

    public static String getText(Integer key) {
        if(map==null) load();
        return map.get(key)==null? "":map.get(key);
    }
    public static Integer getIDByDomain(String key) {
        if(map_domain==null) load();
        return map_domain.get(key);
    }
    public static String getAMIDByDomain(String key) {
        if(map_amid==null) load();
        return map_amid.get(key);
    }
    public static void load() {
//        I_DataTable tmptable = ComBeanI_DataTable.get(TABLEID);
//        map = tmptable.getDataKeyNameMap(null, null, null, null, null, null);
    	map = new TreeMap<Integer, String>();
        map_domain = new HashMap<String, Integer>();
        map_amid = new HashMap<String, String>();

        List<Object> result = HIUtil.dbQuery("select dataid, c1 organName, c6 domain, c8 amid from IS_Organ where c7=130001");
        if (result != null && result.size() > 0) {
            for (Object o : result) {
                map.put(DBUtil.getDBInt(o, "dataid"), DBUtil.getDBString(o, "organName"));
                map_domain.put(DBUtil.getDBString(o, "domain"), DBUtil.getDBInt(o, "dataid"));
                map_amid.put(DBUtil.getDBString(o, "domain"), DBUtil.getDBString(o, "amid"));
            }
        }
    }
}
