package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

public class ComBeanLogType {

    final public static long TYPE_QUERY = 1;
    final public static long TYPE_LOGIN = 2;
    final public static long TYPE_LOGOUT = 2;
    final public static long TYPE_EDIT = 3;
    final public static long TYPE_ADD = 4;
    final public static long TYPE_DEL = 5;    
	
    private static List<LabelValueBean> master = null;

    public static List<LabelValueBean> getList(boolean hasAll) {
    	if(master == null) load();
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if(hasAll) rtn.add(new LabelValueBean("", ""));
        rtn.addAll(master);
        return rtn;
    }
    
    public String getText(Long key){
    	if(master == null) load();
        switch (key.intValue()){
        case 1: return "查询";
        case 2: return "登录";
        case 3: return "编辑";
        case 4: return "添加";
        case 5: return "删除";
        }
        return "";
    }

    private static void load() {
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(new LabelValueBean("查询", "1"));
        list.add(new LabelValueBean("登录", "2"));
        list.add(new LabelValueBean("编辑", "3"));
        list.add(new LabelValueBean("添加", "4"));
        list.add(new LabelValueBean("删除", "5"));
        master = Collections.unmodifiableList(list);
    }
}
