package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.util.HIUtil;

public class ComBeanBase {
	
    @SuppressWarnings("rawtypes")
    protected static List<LabelValueBean> toList(Map map, boolean hasAll) {
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if (hasAll) rtn.add(new LabelValueBean("", ""));
        rtn.addAll(HIUtil.getLabelList(map));
        return Collections.unmodifiableList(rtn);
    }

    @SuppressWarnings("rawtypes")
    protected static List<LabelValueBean> toList(Map map, long mykey, boolean hasAll) {
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if(mykey>0) {
            rtn.add(new LabelValueBean(map.get(mykey).toString(), mykey+""));
        }else{
            if (hasAll) rtn.add(new LabelValueBean("", ""));
            rtn.addAll(HIUtil.getLabelList(map));
        }
        return Collections.unmodifiableList(rtn);
    }

	@SuppressWarnings("rawtypes")
    protected static String getText(Map map, Object key) {
        return HIUtil.toString(map.get(key));
    }

}
