package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.Constants;
import com.svv.dms.web.entity.S_User;
import com.svv.dms.web.util.HIUtil;

public class ComBeanS_User {

    private static TreeMap<Long, String> map = null;
    private static TreeMap<Long, S_User> mapEmp = null;

    public static List<LabelValueBean> getList(boolean hasAll) {
        if (map == null) load();
        
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if (hasAll) rtn.add(new LabelValueBean(Constants.DEF_SELECT_TEXT, ""));
        rtn.addAll(HIUtil.getLabelList(map));
        return Collections.unmodifiableList(rtn);
    }

    public static String getText(long userID) {
        if (map == null) load();
        return HIUtil.toString(map.get(userID));
    }
    public static S_User getEmp(long empID) {
        if (mapEmp == null) load();
        return mapEmp.get(empID);
    }

    @SuppressWarnings("unchecked")
	public static void load() {
        map = new TreeMap<Long, String>();
        mapEmp = new TreeMap<Long, S_User>();

        List<S_User> result = HIUtil.getList(S_User.class, HIUtil.dbQuery("SP_S_UserQuery",
                new Object[]{Constants.DEF_PARAMETET_LONG}));
        if (result != null && result.size() > 0) {
            for (S_User o : result) {
                map.put(o.getUserID(), o.getUserName());
                mapEmp.put(o.getEmpID(), o);
            }
        }
    }

}
