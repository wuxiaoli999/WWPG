package com.svv.dms.web.common;

import java.util.List;
import java.util.TreeMap;

import com.svv.dms.web.entity.I_DataModule;
import com.svv.dms.web.util.HIUtil;

public class ComBeanI_DataModule {
	protected static TreeMap<String, I_DataModule> map = null;
		
	public static String getMemo(int key) {
    	if(map==null) load();
    	I_DataModule o = map.get(key);
    	if(o==null){
    		load();
    		o = map.get(key);
    	}
        return o==null? "":o.getModuleName();
    }
	public static I_DataModule get(Object key) {
    	if(map==null) load();
    	I_DataModule o = map.get(key);
    	if(o==null){
    		load();
    		o = map.get(key);
    	}
        return o;
    }

    public static String getMemos(String keys) {
    	StringBuilder rtn = new StringBuilder("");
        if (map == null) load();
        String[] ss = keys.split(",");
        for(String s: ss){
        	rtn.append(HIUtil.toString(map.get(Integer.parseInt(s)).getModuleName())).append(",");
        }
        if(rtn.length()>0) return rtn.substring(0,rtn.length()-1);
        return rtn.toString();
    }

    @SuppressWarnings("unchecked")
	public static void load() {
        map = new TreeMap<String, I_DataModule>();

        List<I_DataModule> result = HIUtil.getList(I_DataModule.class, HIUtil.dbQuery("SP_I_DataModuleQuery", null));
        if (result != null && result.size() > 0) {
            for (I_DataModule o : result) {
                map.put(o.getModuleID(), o);
            }
        }
    }
}
