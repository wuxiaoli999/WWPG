package com.svv.dms.web.common;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.svv.dms.web.entity.IB_Formation;
import com.svv.dms.web.entity.I_DataTableType;
import com.svv.dms.web.util.HIUtil;

public class ComBeanB_Formation {
	protected static TreeMap<Object, TreeMap<Object, IB_Formation>> map = new TreeMap<Object, TreeMap<Object, IB_Formation>>();
	protected static TreeMap<Object, TreeMap<Object, List<IB_Formation>>> map_childs = new TreeMap<Object, TreeMap<Object, List<IB_Formation>>>();

	public static String getText(Object type, Object key){
        if (map.get(type)==null) load(type);
        return map.get(type).get(key).getFormationName();
	}
    @SuppressWarnings("unchecked")
	public static void load(Object type) {
    	TreeMap<Object, IB_Formation> tmp = new TreeMap<Object, IB_Formation>();
    	TreeMap<Object, List<IB_Formation>> tmp_childs = new TreeMap<Object, List<IB_Formation>>();

        List<IB_Formation> result = HIUtil.getList(IB_Formation.class, HIUtil.dbQuery("SP_B_FormationQuery", new Object[]{type}));
        if (result != null && result.size() > 0) {
            for (IB_Formation o : result) {
            	tmp.put(o.getFormationID(), o);
            	
            	List<IB_Formation> f = tmp_childs.get(o.getParentID());
            	if(f == null) f = new ArrayList<IB_Formation>();
            	f.add(o);
            	tmp_childs.put(o.getParentID(), f);
            }
        }
        map.put(type, tmp);
        map_childs.put(type, tmp_childs);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
	public static List<IB_Formation> getList(int type, int parentID, int parentNodeKB) {
    	List list = new ArrayList();
    	List tmp = null;
    	if(parentNodeKB == -1){
            if (map_childs.get(type) == null) load(type);
    		tmp = map_childs.get(type).get(parentID);
    		if(tmp!=null && tmp.size()>0) list.addAll(tmp);
    	}else if(parentID==-1){
    	    
    	}else if(IB_Formation.NODEKB_TABLETYPE == parentNodeKB){
    		tmp = ComBeanI_DataTableType.getList(parentID, ParamClass.VALUE_LEVEL_ONE, ParamClass.VALUE_LEVEL_FOUR);
    		if(tmp!=null && tmp.size()>0) list.addAll(tmp);

            if (map_childs.get(type) == null) load(type);
    		tmp = map_childs.get(type).get(parentID);
    		if(tmp!=null && tmp.size()>0) list.addAll(tmp);
    	}

        List<IB_Formation> rtn = new ArrayList<IB_Formation>();
        if(list!=null && list.size()>0){
            for(Object o: list){
            	IB_Formation b = new IB_Formation();
            	if(I_DataTableType.class.isInstance(o)){
            		b.setFormationID(((I_DataTableType)o).getTableTypeID());
            		b.setFormationName(((I_DataTableType)o).getTableTypeName());
            		b.setChildNum(((I_DataTableType)o).getChildNum());
            		b.setNodeKB(IB_Formation.NODEKB_TABLETYPE);
            	}else if(IB_Formation.class.isInstance(o)){
            		b.setFormationID(((IB_Formation)o).getFormationID());
            		b.setFormationName(((IB_Formation)o).getFormationName());
            		b.setChildNum(((IB_Formation)o).getChildNum());
            		if(type==ParamClass.VALUE_FORMATION_TYPE_EMP) b.setNodeKB(1);
            		else b.setNodeKB(IB_Formation.NODEKB_DATA);
            	}
        		b.setParentID(parentID);
            	rtn.add(b);
            }
        }
        return rtn;
    }
}
