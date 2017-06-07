package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.util.HIUtil;

public class ComBeanYear {
    
    public static List<LabelValueBean> getList(boolean hasAll, String defText, int start, int end) {
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if(hasAll) rtn.add(new LabelValueBean(defText, ""));
        for (int i=end; i>=start; i--){
            rtn.add(new LabelValueBean(""+i, ""+i));
        }
        return Collections.unmodifiableList(rtn);
    }
    
    public static String[] getList(int start, int end) {
        String[] rtn = new String[end-start+1];
        for (int i=end; i>=start; i--){
            rtn[i-start] = ""+i;
        }
        return rtn;
    }
    
    public static String[] getList() {
        return getList(Integer.parseInt(HIUtil.getCurrentDate("yyyy"))-5, Integer.parseInt(HIUtil.getCurrentDate("yyyy"))+5);
    }
    
    public static List<LabelValueBean> getList(boolean hasAll, String defText) {
        return getList(hasAll, defText, Integer.parseInt(HIUtil.getCurrentDate("yyyy"))-5, Integer.parseInt(HIUtil.getCurrentDate("yyyy"))+5);
    }

}
