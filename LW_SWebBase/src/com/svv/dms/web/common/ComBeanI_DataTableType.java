package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.entity.I_DataTableType;
import com.svv.dms.web.util.HIUtil;

public class ComBeanI_DataTableType {
	protected static TreeMap<Object, I_DataTableType> map = null;
	protected static TreeMap<Object, List<I_DataTableType>> map_childs = null;

    public static List<LabelValueBean> getList(int parentID) {
        return getList(parentID, true, "", 1, ParamClass.VALUE_LEVEL_ONE, ParamClass.VALUE_LEVEL_FOUR);
    }
    public static List<LabelValueBean> getTopList(int parentID) {
        return getList(parentID, true, "", 1, ParamClass.VALUE_LEVEL_ONE, ParamClass.VALUE_LEVEL_ONE);
    }

    public static List<LabelValueBean> getList(int parentID, boolean hasAll, String defText) {
        return getList(parentID, hasAll, defText, 1, ParamClass.VALUE_LEVEL_ONE, ParamClass.VALUE_LEVEL_FOUR);
    }
    public static I_DataTableType get(Object key){
    	if(map==null) load();
        return map.get(key)==null? null:map.get(key);
    }
	public static String getText(Object key) {
    	if(map==null) load();
        return map.get(key)==null? "":map.get(key).getTableTypeName();
    }
	public static String getSimpText(int key) {
        if(key==100000) return "装备保障";
        if(key==110000) return "后勤保障";
        if(key==120000) return "行政政工";
        return "";
    }

    @SuppressWarnings("unchecked")
	public static void load() {
        map = new TreeMap<Object, I_DataTableType>();
        map_childs = new TreeMap<Object, List<I_DataTableType>>();

        List<I_DataTableType> result = HIUtil.getList(I_DataTableType.class, HIUtil.dbQuery("SP_I_DataTableTypeQuery", null));
        if (result != null && result.size() > 0) {
            for (I_DataTableType o : result) {
                map.put(o.getTableTypeID(), o);
                
            	List<I_DataTableType> tmp = map_childs.get(o.getParentID());
            	if(tmp == null) tmp = new ArrayList<I_DataTableType>();
            	tmp.add(o);
            	map_childs.put(o.getParentID(), tmp);
            }
        }
    }

    private static List<LabelValueBean> getList(int parentID, boolean hasAll, String defText, int valueIndex, int minLevel, int maxLevel) {
    	if (map == null) load();
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if (hasAll) rtn.add(new LabelValueBean(defText, ""));
        if (map != null && map.size() > 0) {
            Collection<I_DataTableType> values = map.values();
            for(I_DataTableType o: values){
            	if(parentID==-1 || parentID>0 && (o.getTableTypeID()+"").startsWith((""+parentID).substring(0,2)))
	            	if(o.getTypeLevel()>=minLevel && o.getTypeLevel()<=maxLevel)
	            	    rtn.add(new LabelValueBean( valueIndex==0 ? o.getTableTypeName() : 
	            		    HIUtil.lPad(o.getTableTypeName(), "　", (o.getTypeLevel()-ParamClass.VALUE_LEVEL_ONE)*2), o.getTableTypeID()+""));
            }
        }
        return Collections.unmodifiableList(rtn);
    }
    public static List<I_DataTableType> getList(int parentID, int minLevel, int maxLevel) {
        if (map == null) load();
        List<I_DataTableType> rtn = new ArrayList<I_DataTableType>();
        if (map != null && map.size() > 0) {
        	List<I_DataTableType> tmp = map_childs.get(parentID);
        	if (tmp != null && tmp.size() > 0) {
        	    for(I_DataTableType o: tmp){
        	    	if((minLevel==-1 || o.getTypeLevel() >= minLevel)
        	    			&& (maxLevel==-1 || o.getTypeLevel() <= maxLevel)) rtn.add(o);
        	    }
        	}
        }
        return rtn;
    }
    
}
