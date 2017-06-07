package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.svv.dms.web.entity.I_DataTable;
import com.svv.dms.web.util.HIUtil;

public class ComBeanI_DataTable {
	protected static TreeMap<Integer, I_DataTable> map = null;
	protected static TreeMap<Integer, List<I_DataTable>> map_childs = null;
	protected static String ZSQL_FORMAT = "insert into Z%s select SEQ_Z_DATAHIS_IID.nextval, to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')||' %s', t0.* from %s t0 where %s";

	public static String sqlToZ(String tableName, String memo, String where){
		return String.format(ZSQL_FORMAT, new Object[]{tableName, memo, tableName, where});
	}
	
	public static String getMemo(int key) {
    	if(map==null) load();
    	I_DataTable o = map.get(key);
    	if(o==null){
    		load();
    		o = map.get(key);
    	}
        return o==null? "":o.getTableMemo();
    }
	public static I_DataTable get(Object key) {
    	if(map==null) load();
    	I_DataTable o = map.get(key);
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
        	rtn.append(HIUtil.toString(map.get(Integer.parseInt(s)).getTableMemo())).append(",");
        }
        if(rtn.length()>0) return rtn.substring(0,rtn.length()-1);
        return rtn.toString();
    }

    @SuppressWarnings("unchecked")
	public static void load() {
        map = new TreeMap<Integer, I_DataTable>();
        map_childs = new TreeMap<Integer, List<I_DataTable>>();

        List<I_DataTable> result = HIUtil.getList(I_DataTable.class, HIUtil.dbQuery("SP_I_DataTableQuery", null));
        //List<I_DataTable> result = JSUtil.getList(I_DataTable.class, JSUtil.dbQuery("SP_I_DataTableQueryByParentTableID", new Object[]{-1}));
        if (result != null && result.size() > 0) {
            for (I_DataTable o : result) {
                map.put(o.getTableID(), o);
                
            	List<I_DataTable> tmp = map_childs.get(o.getTableTypeID());
            	if(tmp == null) tmp = new ArrayList<I_DataTable>();
            	if(Integer.parseInt(o.getParentTableID())==-1) tmp.add(o);
            	map_childs.put(o.getTableTypeID(), tmp);
            }
        }
    }

    public static List<I_DataTable> getList(int parentID) {
        if (map == null) load();
        List<I_DataTable> rtn = new ArrayList<I_DataTable>();
        if (map_childs != null && map_childs.size() > 0) {
        	List<I_DataTable> tmp = map_childs.get(parentID);
        	if(tmp!=null && tmp.size()>0) rtn.addAll(tmp);
        }
        return rtn;
    }
    
}
