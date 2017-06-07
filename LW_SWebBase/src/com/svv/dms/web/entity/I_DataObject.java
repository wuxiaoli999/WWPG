package com.svv.dms.web.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.svv.dms.web.common.ParamClass;

public abstract class I_DataObject extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175409144853160630L;

    protected String otherButtons = "";
    protected int state      = 1;
    protected String istDate = "";
    protected String uptDate = "";

    private boolean isTable = false;
    private boolean isModule = false;
    private String objectID;
    private String objectName;
    
    private HashMap<Long, I_DataTableColumn> columns_map = null;
    private HashMap<Integer, I_DataTableColumn> keyTable_columns_map = null;
    private List<I_DataTableColumn> columns = null;
    private List<I_DataTableColumn> columns_keyword = null;
    private List<I_DataTableColumn> columns_keyname = null;
    private List<I_DataTableColumn> columns_querycond = null;
    private List<I_DataTableColumn> columns_relation = null;

    public I_DataObject(){}
    public I_DataObject(Object rs){
    	loadFromRs(this, rs);
    }
    
	public String getObjectID() {
		return objectID;
	}
	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public boolean isTable() {
		return isTable;
	}
	public void setTable(boolean isTable) {
		this.isTable = isTable;
	}
	public boolean isModule() {
		return isModule;
	}
	public void setModule(boolean isModule) {
		this.isModule = isModule;
	}
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getOtherButtons() {
		return otherButtons;
	}
	public void setOtherButtons(String otherButtons) {
		this.otherButtons = otherButtons;
	}
	public String getIstDate() {
		return istDate;
	}

	public void setIstDate(String istDate) {
		this.istDate = istDate;
	}

	public String getUptDate() {
		return uptDate;
	}

	public void setUptDate(String uptDate) {
		this.uptDate = uptDate;
	}

	/**********************************************************************/
	public abstract List<I_DataTableColumn> queryColumnList();
	
    public void setColumns(){
        List<I_DataTableColumn> collist = queryColumnList();
        if(collist!=null){
            this.columns_map = new HashMap<Long, I_DataTableColumn>();
            this.keyTable_columns_map = new HashMap<Integer, I_DataTableColumn>();
            this.columns = new ArrayList<I_DataTableColumn>();
            this.columns_keyword = new ArrayList<I_DataTableColumn>();
            this.columns_keyname = new ArrayList<I_DataTableColumn>();
            this.columns_querycond = new ArrayList<I_DataTableColumn>();
            this.columns_relation = new ArrayList<I_DataTableColumn>();
            for(I_DataTableColumn o: collist){
                this.columns_map.put(o.getColid(), o);
                columns.add(o);
                if(o.getExtKeywordFlag()==1) this.columns_keyword.add(o);
                if(o.getExtKeyNameFlag()==1) this.columns_keyname.add(o);
                if(o.getExtQueryByFlag()==1) this.columns_querycond.add(o);
                if(o.getExtValueScopeTypeID()>0 && o.getExtValueScopeTypeID()==ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE){
                    columns_relation.add(o);
                    keyTable_columns_map.put(Integer.parseInt(o.getExtValueScopeTypeParam()), o);
                }
            }
        }
    }
    public HashMap<Long, I_DataTableColumn> getColumnsMap() {
        if(columns_map==null) setColumns();
        return columns_map;
    }
    public List<I_DataTableColumn> getColumns() {
        if(columns==null) setColumns();
        return columns;
    }
    public List<I_DataTableColumn> getColumns_keyword() {
        if(columns_keyword==null) setColumns();
        return columns_keyword;
    }
    public List<I_DataTableColumn> getColumns_keyname() {
        if(columns_keyname==null) setColumns();
        return columns_keyname;
    }
    public List<I_DataTableColumn> getColumns_querycond() {
        if(columns_querycond==null) setColumns();
        return columns_querycond;
    }
    public List<I_DataTableColumn> getColumns_relation() {
        if(columns_relation==null) setColumns();
        return columns_relation;
    }
    public I_DataTableColumn getKeyTableColumn(Integer keyTableID){
        if(keyTable_columns_map==null) setColumns();
        return keyTable_columns_map.get(keyTableID);
    }

}
