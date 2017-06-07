package com.svv.dms.web.common;

import java.util.List;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.entity.I_DataTable;

public class ComBeanIS_JobPosition extends ComBeanBase {
    public final static int TABLEID = 4;
    public final static String ORGAN_COLNAME = "c2";
    
    protected static TreeMap<Long, String> map = null;

    public static List<LabelValueBean> getList(String organID, boolean hasAll) {
        I_DataTable tmptable = ComBeanI_DataTable.get(TABLEID);
        TreeMap<Long, String> mymap = tmptable.getDataKeyNameMap(ORGAN_COLNAME, organID, null, null, null, null);
        return toList(mymap, hasAll);
    }

    public static String getText(Object key) {
        if(map==null) load();
        return map.get(key)==null? "":map.get(key);
    }
    public static void load() {
        I_DataTable tmptable = ComBeanI_DataTable.get(TABLEID);
        map = tmptable.getDataKeyNameMap(null, null, null, null, null, null);
    }
}
