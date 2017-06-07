package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

public class ComBeanState {

    final static public int VALID = 1;
    final static public int UNVALID = 0;
    
    private static List<LabelValueBean> master = null;

    public static List<LabelValueBean> getList() {
        return getList(false);
    }
    
    public static List<LabelValueBean> getList(boolean hasAll) {
        if(master == null) load();
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if(hasAll) rtn.add(new LabelValueBean("", ""));
        rtn.addAll(master);
        return rtn;
    }
    
    public static String getText(int key){
        if(master == null) load();
        if(key==VALID) return "有效";
        if(key==UNVALID) return "<font color=#ff0000>无效</font>";
        return "";
    }

    private static void load() {
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(new LabelValueBean("有效", VALID+""));
        list.add(new LabelValueBean("无效", UNVALID+""));
        master = Collections.unmodifiableList(list);
    }
}
