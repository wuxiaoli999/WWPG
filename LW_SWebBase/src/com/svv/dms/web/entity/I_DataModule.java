package com.svv.dms.web.entity;

import java.util.List;

import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.util.HIUtil;

public class I_DataModule extends I_DataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175409144853160630L;

    protected String moduleID;
    protected String moduleName;
    protected String viewName;
    protected String viewSql;
    protected String tableIDs;
	protected int colNum = 0;
	protected long dataNum = 0;
    protected int sctLevel   = -1;
    protected String otherCondition = "";
    protected String otherButtons = "";

    public static String moduleID_desc         = "模块ID";
    public static String moduleName_desc       = "模块名称";
    public static String viewName_desc         = "视图名称";
    public static String viewSql_desc          = "视图sql";
    public static String tableIDs_desc         = "数据表";
    public static String colNum_desc           = "属性数";
    public static String otherCondition_desc   = "其他SQL条件";
    public static String otherButtons_desc     = "其他功能菜单";
    public static String state_desc            = "有效状态";
    public static String istDate_desc          = "创建日期";


    public I_DataModule(){}
    public I_DataModule(Object rs){
    	loadFromRs(this, rs);
    }
    
	public String getViewSql() {
		return viewSql;
	}
	public void setViewSql(String viewSql) {
		this.viewSql = viewSql;
	}
	public String getTableIDs() {
		return tableIDs;
	}
	public void setTableIDs(String tableIDs) {
		this.tableIDs = tableIDs;
	}
	public int getSctLevel() {
		return sctLevel;
	}

	public void setSctLevel(int sctLevel) {
		this.sctLevel = sctLevel;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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

	public String getIstDate_desc() {
		return istDate_desc;
	}

	public long getDataNum() {
		return dataNum;
	}

	public void setDataNum(long dataNum) {
		this.dataNum = dataNum;
	}
	public int getColNum() {
		return colNum;
	}
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
	public String getColNum_desc() {
		return colNum_desc;
	}
	
    public String getOtherCondition() {
		return otherCondition;
	}
	public void setOtherCondition(String otherCondition) {
		this.otherCondition = otherCondition;
	}
	public String getOtherCondition_desc() {
		return otherCondition_desc;
	}
	public String getOtherButtons() {
        return otherButtons;
    }
    public void setOtherButtons(String otherButtons) {
        this.otherButtons = otherButtons;
    }
    public String getOtherButtons_desc() {
        return otherButtons_desc;
    }
	
	public String getModuleID() {
		return moduleID;
	}
	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getState_desc() {
		return state_desc;
	}
	public String getModuleID_desc() {
		return moduleID_desc;
	}
	public String getModuleName_desc() {
		return moduleName_desc;
	}
	public String getViewName_desc() {
		return viewName_desc;
	}	
	public String getViewSql_desc() {
		return viewSql_desc;
	}
	public String getTableIDs_desc() {
		return tableIDs_desc;
	}

    @SuppressWarnings("unchecked")
	public List<I_DataTableColumn> queryColumnList(){
        return HIUtil.getList(I_DataTableColumn.class, HIUtil.dbQuery(SQL.SP_I_DataModuleColumnQueryByC(this.getModuleID(), "", "", true)));
    }
}
