package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

public class ComBeanMonth {
    
    public static List<LabelValueBean> getList() {
    	String[] month = new String[]{"一","二","三","四","五","六","七","八","九","十","十一","十二"};
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        for (int i=0; i<month.length; i++){
        	rtn.add(new LabelValueBean(month[i], ""+(i+1)));
        }
        return Collections.unmodifiableList(rtn);
    }
}
