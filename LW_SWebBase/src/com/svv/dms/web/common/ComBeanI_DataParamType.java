package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.I_DataParamType;
import com.svv.dms.web.util.HIUtil;

public class ComBeanI_DataParamType {
	static{
		load();
	}

    final public static int CLASS_PUB_ACTOR = 135;   //职位

	protected static TreeMap<Integer, I_DataParamType> map = null;
	protected static HashMap<Integer, HashMap<String, I_DataParamType>> map_memo = null;
	protected static TreeMap<Integer, List<I_DataParamType>> map_childs = null;

    public static List<LabelValueBean> getList(int minLevel, int maxLevel) {
        return getList(true, "", 1, minLevel, maxLevel);
    }

    public static List<LabelValueBean> getList(boolean hasAll, String defText) {
        return getList(hasAll, defText, 1, ParamClass.VALUE_LEVEL_ONE, ParamClass.VALUE_LEVEL_FOUR);
    }
    public static List<LabelValueBean> getList(int parentID, boolean hasAll, String defText) {
        List<I_DataParamType> list = getList(parentID, false);
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if (hasAll) rtn.add(new LabelValueBean(defText, ""));
        if (list != null && list.size() > 0) {
            for(I_DataParamType o: list){
              rtn.add(new LabelValueBean(o.getClassName(), o.getParamClassID()+""));
            }
        }        
        return Collections.unmodifiableList(rtn);
    }

    public static String getText(Object key) {
        if(map==null) load();
        if(key instanceof String){
        	return getTexts((String)key, ",");
        }
        return map.get(key)==null? "":map.get(key).getClassName();
    }
    public static String getTexts(String keys, String spliter) {
        if(map==null) load();
        String rtn = "";
        if(keys.length()>0){
            String[] ss = HIUtil.split(keys, spliter);
            for(String s: ss){
            	if(s.startsWith("!")){
            		s = s.substring(1);
                    if(map.get(Integer.parseInt(s))!=null) rtn += "<非>"+map.get(Integer.parseInt(s)).getClassName() + spliter;
            	}else{
                    if(map.get(Integer.parseInt(s))!=null) rtn += map.get(Integer.parseInt(s)).getClassName() + spliter;
            	}
            }
            if(rtn.length()>0) rtn = rtn.substring(0, rtn.length()-spliter.length());
        }
        return rtn;
    }
    public static String getText(int parentID, String key) {
        if(map==null) load();
        return map.get(key)==null? "":map.get(key).getClassName();
    }
    public static String getMemoByName(int parentID, String name) {
        if(map_memo==null) load();
        HashMap<String , I_DataParamType> tmp = map_memo.get(parentID);
        if(tmp==null) return "";
        return tmp.get(name)==null? "":tmp.get(name).getMemo();
    }
    public static I_DataParamType getByName(int parentID, String name) {
        if(map_memo==null) load();
        HashMap<String , I_DataParamType> tmp = map_memo.get(parentID);
        if(tmp==null) return null;
        return tmp.get(name);
    }
    
    public static int getLevel(Object key) {
        if(map==null) load();
        return map.get(key)==null? 0:map.get(key).getTypeLevel();
    }
	
	public static I_DataParamType get(Integer key){
    	if(map==null) load();
		return map.get(key);
	}

    @SuppressWarnings("unchecked")
	public static void load() {
        map = new TreeMap<Integer, I_DataParamType>();
        map_memo = new HashMap<Integer, HashMap<String , I_DataParamType>>();
        map_childs = new TreeMap<Integer, List<I_DataParamType>>();

        List<I_DataParamType> result = HIUtil.getList(I_DataParamType.class, HIUtil.dbQuery(SQL.SP_I_DataParamTypeQueryByC("","")));
        if (result != null && result.size() > 0) {
            for (I_DataParamType o : result) {
                map.put(o.getParamClassID(), o);

            	List<I_DataParamType> tmp = map_childs.get(o.getParentID());
            	if(tmp == null) tmp = new ArrayList<I_DataParamType>();
            	tmp.add(o);
            	map_childs.put(o.getParentID(), tmp);

            	HashMap<String , I_DataParamType> tmp_2 = map_memo.get(o.getParentID());
            	if(tmp_2 == null) tmp_2 = new HashMap<String , I_DataParamType>();
            	tmp_2.put(o.getClassName(), o);
            	map_memo.put(o.getParentID(), tmp_2);
            }
        }
    }

    private static List<LabelValueBean> getList(boolean hasAll, String defText, int valueIndex, int minLevel, int maxLevel) {
        if (map == null) load();
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        if (hasAll) rtn.add(new LabelValueBean(defText, ""));
        if (map != null && map.size() > 0) {
            Collection<I_DataParamType> values = map.values();
            for(I_DataParamType o: values){
            	if(o.getTypeLevel()>=minLevel && o.getTypeLevel()<=maxLevel)
            	    rtn.add(new LabelValueBean( valueIndex==0 ? o.getClassName() : 
            		    HIUtil.lPad(o.getClassName(), "　", (o.getTypeLevel()-ParamClass.VALUE_LEVEL_ONE)*2), o.getParamClassID()+""));
            }
        }        
        return Collections.unmodifiableList(rtn);
    }
    @SuppressWarnings("unchecked")
    public static List<I_DataParamType> getList(int parentID, boolean sortByName) {
        List<I_DataParamType> rtn;
        if(parentID == -1) rtn = HIUtil.getList(I_DataParamType.class, HIUtil.dbQuery("SP_I_DataParamTypeQueryByParent", new Object[]{1, parentID}));
        else if(sortByName)  rtn = HIUtil.getList(I_DataParamType.class, HIUtil.dbQuery("SP_I_DataParamTypeQueryByParentOrderByName", new Object[]{parentID}));
        else rtn = HIUtil.getList(I_DataParamType.class, HIUtil.dbQuery("SP_I_DataParamTypeQueryByParentOrderByID", new Object[]{parentID}));
        return rtn;
    }
    @SuppressWarnings("unchecked")
    public static List<I_DataParamType> getList(int parentID) {
        List<I_DataParamType> rtn;
        if(parentID == -1) rtn = HIUtil.getList(I_DataParamType.class, HIUtil.dbQuery("SP_I_DataParamTypeQueryByParent", new Object[]{1, parentID}));
        else rtn = HIUtil.getList(I_DataParamType.class, HIUtil.dbQuery("SP_I_DataParamTypeQueryByParentOrderByID", new Object[]{parentID}));
        return rtn;
    }
    @SuppressWarnings("unchecked")
    public static TreeMap<Integer, String> getNameMap(boolean superAdmin, int parentID) {
        TreeMap<Integer, String> rtn = new TreeMap<Integer, String>();
        List<I_DataParamType> list = HIUtil.getList(I_DataParamType.class, HIUtil.dbQuery("SP_I_DataParamTypeQueryByParent", new Object[]{superAdmin?"state":"1", parentID}));
        if (list != null && list.size() > 0) {
            for (I_DataParamType o : list) {
            	rtn.put(o.getParamValue(), o.getClassName());
            }
        }
        return rtn;
    }
    @SuppressWarnings("unchecked")
    public static TreeMap<Integer, I_DataParamType> getMap(int parentID) {
        TreeMap<Integer, I_DataParamType> rtn = new TreeMap<Integer, I_DataParamType>();
        List<I_DataParamType> list = HIUtil.getList(I_DataParamType.class, HIUtil.dbQuery("SP_I_DataParamTypeQueryByParent", new Object[]{1, parentID}));
        if (list != null && list.size() > 0) {
            for (I_DataParamType o : list) {
            	rtn.put(o.getParamValue(), o);
            }
        }
        return rtn;
    }
    
}
