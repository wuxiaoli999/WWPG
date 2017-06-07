package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.entity.I_SystemParam;
import com.svv.dms.web.util.HIUtil;

public class ComBeanI_SystemParam {

    private static TreeMap<Integer, TreeMap<Integer, String>> maps = null;
    private static TreeMap<Integer, List<I_SystemParam>> map_childs = null;

    public static List<LabelValueBean> getList(Integer parentParamClass, Integer paramClass, boolean hasAll, String defText) {
        if (maps == null) load();
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if (hasAll) rtn.add(new LabelValueBean(defText, ""));
        List<I_SystemParam> result = map_childs.get(parentParamClass);
        if (result != null && result.size() > 0) {
            for (I_SystemParam o : result) {
                if(Integer.parseInt(o.getParamClass())==paramClass)
                    rtn.add(new LabelValueBean(o.getName(), o.getValue()));
            }
        }
        return rtn;
    }
    public static List<LabelValueBean> getList(Integer ParamClass, boolean hasAll, String defText) {
        if (maps == null) load();
        TreeMap<Integer, String> newmap  = new TreeMap<Integer, String>();
        if(ParamClass > 0 && maps!=null) newmap = maps.get(ParamClass);
        
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if (hasAll) rtn.add(new LabelValueBean(defText, ""));
        rtn.addAll(HIUtil.getLabelList(newmap));
        return Collections.unmodifiableList(rtn);
    }
    public static List<LabelValueBean> getList(Integer ParamClass, String myValue) {
        if (maps == null) load();
        TreeMap<Integer, String> newmap  = new TreeMap<Integer, String>();
        if(ParamClass > 0 && maps!=null) newmap = maps.get(ParamClass);        
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        rtn.add(new LabelValueBean(newmap.get(myValue), myValue));
        return Collections.unmodifiableList(rtn);
    }

    public static List<LabelValueBean> getNameList(Integer ParamClass, boolean hasAll, String defText) {
        if (maps == null) load();
        TreeMap<Integer, String> newmap  = new TreeMap<Integer, String>();
        if(ParamClass > 0 && maps!=null) newmap = maps.get(ParamClass);
        
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if (hasAll) rtn.add(new LabelValueBean(defText, ""));
        Set<Integer> keySet = newmap.keySet();
        for(Integer key: keySet){
            rtn.add(new LabelValueBean(newmap.get(key), newmap.get(key)));
        }
        return Collections.unmodifiableList(rtn);
    }
    public static String[] getNames(Integer ParamClass){
        if (maps == null) load();
        TreeMap<Integer, String> newmap  = new TreeMap<Integer, String>();
        if(ParamClass > 0 && maps!=null) newmap = maps.get(ParamClass);
        return newmap.values().toArray(new String[0]);
    }
    public static List<String[]> getList(Integer parentParamClass, Integer paramClass) {
        if (maps == null) load();
        List<String[]> rtn = null;
        List<I_SystemParam> result = map_childs.get(parentParamClass);
        if (result != null && result.size() > 0) {
            rtn = new ArrayList<String[]>();
            for (I_SystemParam o : result) {
                if(Integer.parseInt(o.getParamClass())==paramClass){
                    rtn.add(new String[]{o.getValue(), o.getName()});
                }
            }
        }
        return rtn;
    }

    public static String getText(Integer ParamClass, String key) {
    	if(HIUtil.isEmpty(key)) return "";
        return getText(ParamClass, Integer.parseInt(key));
    }

    public static String getText(Integer ParamClass, Integer key) {
        if (maps == null) load();
        if(maps.get(ParamClass)==null) return ParamClass+" NULL";
        return HIUtil.toString(maps.get(ParamClass).get(key));
    }

    public static String getText(Integer ParamClass, String keys, String split) {
        if(HIUtil.isEmpty(keys)) return "";
        if (maps == null) load();
        StringBuilder rtn = new StringBuilder("");
        String[] ss = keys.split(",");
        for(String s: ss){
            rtn.append(maps.get(ParamClass).get(s)).append(split);
        }
        if(rtn.length()>0) return rtn.substring(0,rtn.length()-1);
        return rtn.toString();
    }

    public static TreeMap<Integer, String> getList(Integer paramClass) {
        if (maps == null) load();
        return maps.get(paramClass);
    }
    public static LabelValueBean[] getArrayList(Integer paramClass) {
        if (maps == null) load();
        TreeMap<Integer, String> mylist = maps.get(paramClass);
        if (mylist == null || mylist.size() == 0) return null;
        LabelValueBean[] rtn = new LabelValueBean[mylist.size()];
        if (mylist != null && mylist.size() > 0) {
            Set<Integer> keySet = mylist.keySet();
            int i = 0;
            for(Integer key: keySet){
                rtn[i++] = new LabelValueBean(mylist.get(key), key+"");
            }
        }        
        return rtn;
    }

    public static TreeMap<String, String> getNameToIDList(Integer paramClass) {
        if (maps == null) load();
        TreeMap<Integer, String> mymap  = maps.get(paramClass);
        TreeMap<String, String> newmap  = new TreeMap<String, String>();
        Set<Integer> keySet = mymap.keySet();
        for(Integer key: keySet){
        	newmap.put(mymap.get(key), key+"");
        }
        return newmap;
    }

    @SuppressWarnings("unchecked")
    public static void load() {
        maps = new TreeMap<Integer, TreeMap<Integer, String>>();
        map_childs = new TreeMap<Integer, List<I_SystemParam>>();

        List<I_SystemParam> result = HIUtil.getList(I_SystemParam.class, HIUtil.dbQuery("SP_I_SystemParamQueryByState", new Object[]{1}));
        if (result != null && result.size() > 0) {
        	int tmpParamClass = -1;
        	TreeMap<Integer, String> tmp = null;
            for (I_SystemParam o : result) {
            	if(tmpParamClass != Integer.parseInt(o.getParamClass())){
            		if(tmp!=null) maps.put(tmpParamClass, tmp);
            		tmp = new TreeMap<Integer, String>();
            		tmpParamClass = Integer.parseInt(o.getParamClass());
            	}
            	tmp.put(Integer.parseInt(o.getValue()), o.getName());            	

            	if(!HIUtil.isEmpty(o.getParentClass())){
                    List<I_SystemParam> tmp2 = map_childs.get(Integer.parseInt(o.getParentClass()));
                    if(tmp2 == null) tmp2 = new ArrayList<I_SystemParam>();
                    tmp2.add(o);
                    map_childs.put(Integer.parseInt(o.getParentClass()), tmp2);
            	}
            }
            if(tmp!=null) maps.put(tmpParamClass, tmp);
        }
    }
}
