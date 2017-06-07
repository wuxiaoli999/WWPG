package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.entity.I_DataTable;
import com.svv.dms.web.util.HIUtil;

public class ComBeanIS_Emp extends ComBeanBase {
    public final static int TABLEID = 2;
    public final static String ORGAN_COLNAME = "c2";
    public final static String DEPARTMENT_COLNAME = "c3";
    public final static String JOB_COLNAME = "c4";
    
    protected static TreeMap<Long, String> map = null;
    protected static HashMap<Integer, HashMap<Integer, List<Object>>> pmap = new HashMap<Integer, HashMap<Integer, List<Object>>>();

    public static List<LabelValueBean> getList(String organID, String departmentID, boolean hasAll) {
        I_DataTable tmptable = ComBeanI_DataTable.get(TABLEID);
        TreeMap<Long, String> mymap = tmptable.getDataKeyNameMap(ORGAN_COLNAME, organID, DEPARTMENT_COLNAME, departmentID, null, null);
        return toList(mymap, hasAll);
    }
    public static TreeMap<Long, String> getList(String organID, String departmentID) {
        I_DataTable tmptable = ComBeanI_DataTable.get(TABLEID);
        return tmptable.getDataKeyNameMap(ORGAN_COLNAME, organID, DEPARTMENT_COLNAME, departmentID, null, null);
    }
    public static TreeMap<Long, String> getList(String organID, int jobID) {
        I_DataTable tmptable = ComBeanI_DataTable.get(TABLEID);
        return tmptable.getDataKeyNameMap(ORGAN_COLNAME, organID, JOB_COLNAME, jobID+"", null, null);
    }
    public static TreeMap<Long, String> getList(String organID) {
        I_DataTable tmptable = ComBeanI_DataTable.get(TABLEID);
        return tmptable.getDataKeyNameMap(ORGAN_COLNAME, organID, null, null, null, null);
    }
    public static String getText(long key) {
        if(map==null) load();
        return map.get(key)==null? "":map.get(key);
    }
    public static String getText(String keys) {
        if(map==null) load();
        String[] ss = keys.split(",");
        String rtn = "";
        if(ss.length > 0){
            for(String s: ss){
                rtn += getText(Long.parseLong(s))+" ";
            }
        }
        return rtn;
    }
    public static void load() {
        I_DataTable tmptable = ComBeanI_DataTable.get(TABLEID);
        map = tmptable.getDataKeyNameMap(null, null, null, null, null, null);
    }
    
    public static List<Object> getList(int organID, int parentID){
        if(pmap.get(organID)==null) loadTree(organID);
        if(pmap.get(organID)!=null) return pmap.get(organID).get(parentID);
        return null;
    }
    public static void loadTree(int organID){
        List<Object> list = HIUtil.dbQuery("select a.dataid,a.c1, 0 s1 from IS_Emp a where c2="+organID+" and c3 is null union select b.dataid+1000 dataid,b.c1, 0 s1 from Y_Department b where c2="+organID+" union select c.dataid,c.c1, NVL(c3+1000,0) s1 from Y_Emp c where c2="+organID+" and c3 is not null order by s1, dataid");

        HashMap<Integer, List<Object>> tempMap = null;
        if(list!=null && list.size()>0){
            tempMap = new HashMap<Integer, List<Object>>();
            int parentID = -1;
            List<Object> tmpList = new ArrayList<Object>();
            for(Object o: list){
                if(parentID != DBUtil.getDBInt(o, "s1")){
                    if(parentID>=0) tempMap.put(parentID, tmpList);
                    tmpList = new ArrayList<Object>();
                    parentID = DBUtil.getDBInt(o, "s1");
                }
                tmpList.add(o);
            }
            if(parentID>=0) tempMap.put(parentID, tmpList);
        }
        pmap.put(organID, tempMap);
    }

}
