package com.svv.dms.web.common;

import java.util.List;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.entity.S_Role;
import com.svv.dms.web.util.HIUtil;

public class ComBeanS_Role extends ComBeanBase {
    
    private static TreeMap<Integer, String> map = null;

    public static List<LabelValueBean> getList(boolean hasAll) {
        if (map == null) load();
        return toList(map, hasAll);
    }

    public static String getText(int key) {
        if (map == null) load();
        return getText(map, key);
    }

    public static String getText(String keys, int lineNum, String split) {
        StringBuilder rtn = new StringBuilder("");
        if (map == null) load();
        String[] ss = keys.split(",");
        int i = 0;
        for(String s: ss){
            i++;
            String t = map.get(Integer.parseInt(s));
            if(t!=null){
                rtn.append(t);
                if(lineNum==0){
                    rtn.append(split);
                }else{
                    if(i%lineNum==0) rtn.append(split);
                    else rtn.append(HIUtil.lPad("", "   ", t.length()>12 ? 1 : 12-t.length()));
                }
            }
        }
        if(rtn.length()>0) return rtn.substring(0,rtn.length()-1);
        return rtn.toString();
    }

    @SuppressWarnings("unchecked")
    public static void load() {
        map = new TreeMap<Integer, String>();

        List<S_Role> result = HIUtil.getList(S_Role.class, HIUtil.dbQuery("SP_S_RoleQuery", null));
        if (result != null && result.size() > 0) {
            for (S_Role o : result) {
                map.put(o.getRoleID(), o.getRoleName());
            }
        }
    }
}
