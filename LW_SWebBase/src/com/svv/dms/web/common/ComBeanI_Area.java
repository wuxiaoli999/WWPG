package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.entity.I_Area;
import com.svv.dms.web.util.HIUtil;

public class ComBeanI_Area {
	protected static TreeMap<Object, I_Area> map = null;

    public static List<LabelValueBean> getList(boolean hasAll, int minLevel, int maxLevel) {
        return getList(-1, hasAll, "", 1, minLevel, maxLevel);
    }

    public static List<LabelValueBean> getList(Integer myAreaID, boolean hasAll, String defText) {
        return getList(myAreaID, hasAll, defText, 1, ParamClass.VALUE_LEVEL_ONE, ParamClass.VALUE_LEVEL_FOUR);
    }
    public static List<LabelValueBean> getChildList(Integer myAreaID, boolean hasAll, String defText) {
    	if(map==null) load();
        I_Area myArea = null;
        if(myAreaID > 0) myArea = map.get(myAreaID);
        if(myArea == null){
            return getList(myAreaID, hasAll, defText, 0, ParamClass.VALUE_LEVEL_ONE, ParamClass.VALUE_LEVEL_ONE);
        }
        return getList(myAreaID, hasAll, defText, 0, myArea.getAreaLevel()+1, myArea.getAreaLevel()+1);
    }

	public static List<LabelValueBean> getList(boolean hasAll, String defText, int endLevel) {
        return getList(-1, hasAll, defText, 1, ParamClass.VALUE_LEVEL_ONE, endLevel);
    }

	public static String getText(Object key) {
    	if(map==null) load();
        return map.get(key)==null? "":map.get(key).getAreaName();
    }

    public static String getText(String keys) {
    	StringBuilder rtn = new StringBuilder("");
        if (map == null) load();
        String[] ss = keys.split(",");
        for(String s: ss){
        	rtn.append(HIUtil.toString(map.get(Integer.parseInt(s)).getAreaName())).append(",");
        }
        if(rtn.length()>0) return rtn.substring(0,rtn.length()-1);
        return rtn.toString();
    }

    @SuppressWarnings("unchecked")
    public static void load() {
        map = new TreeMap<Object, I_Area>();

        List<I_Area> result = HIUtil.getList(I_Area.class, HIUtil.dbQuery("SP_I_AreaQuery", null));
        if (result != null && result.size() > 0) {
            for (I_Area o : result) {
                map.put(o.getAreaID(), o);
            }
        }
    }

    private static List<LabelValueBean> getList(Integer myAreaID, boolean hasAll, String defText, int valueIndex, int minLevel, int maxLevel) {
        if (map == null) load();
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if (hasAll) rtn.add(new LabelValueBean(defText, ""));
        I_Area myArea = null;
        if(myAreaID > 0) myArea = map.get(myAreaID);
        if (map != null && map.size() > 0) {
            Collection<I_Area> values = map.values();
            for(I_Area o: values){
            	if(myArea!=null){
            		if(myArea.getAreaLevel() == o.getAreaLevel() && o.getAreaID() != myAreaID) continue;
            		if(myArea.getAreaLevel() > o.getAreaLevel()) continue;
            	}
            	if(o.getAreaLevel()>=minLevel && o.getAreaLevel()<=maxLevel)
            	    rtn.add(new LabelValueBean( valueIndex==0 ? o.getAreaName() : 
            		    HIUtil.lPad(o.getAreaName(), "　", (o.getAreaLevel()-ParamClass.VALUE_LEVEL_ONE)*2), o.getAreaID()+""));
            }
        }        
        return Collections.unmodifiableList(rtn);
    }
    public static I_Area[] getList(Integer myAreaID){
        return getList(myAreaID, 1, ParamClass.VALUE_LEVEL_ONE, ParamClass.VALUE_LEVEL_THREE);
    }
    private static I_Area[] getList(Integer myAreaID, int valueIndex, int minLevel, int maxLevel) {
        if (map == null) load();
        List<I_Area> rtn = new ArrayList<I_Area>();
        I_Area myArea = null;
        if(myAreaID > 0) myArea = map.get(myAreaID);
        if (map != null && map.size() > 0) {
            Collection<I_Area> values = map.values();
            for(I_Area o: values){
                if(myArea!=null){
                    if(myArea.getAreaLevel() == o.getAreaLevel() && o.getAreaID() != myAreaID) continue;
                    if(myArea.getAreaLevel() < o.getAreaLevel()) continue;
                }
                if(o.getAreaLevel()>=minLevel && o.getAreaLevel()<=maxLevel)
                    rtn.add(o);
            }
        }
        return rtn.toArray(new I_Area[0]);
    }
    
}
