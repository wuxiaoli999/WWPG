package com.svv.dms.web.entity;



public class B_Data extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175409144853160630L;

    protected long dataid        = -1;
    protected String parentDataid  = "";
    protected int tableID        = -1;
    protected long extid         = -1;
    protected String inputPerson = "";
    protected String checkPerson = "";
    protected String cmtPerson = "";
    protected String power = "";
    protected int dataSctLevel;
    protected int dataStatus = -1;
    protected String istDate = "";
    protected String uptDate = "";
 
    private I_DataTable table = new I_DataTable();
    protected String tableName = "";
    protected String tableMemo = "";
    
    private I_DataModule module = new I_DataModule();
    protected String moduleID = "";
    protected String moduleName = "";
    protected String viewName = "";
    
    public static String tableID_desc             = "数据类别";
    public static String dataSctLevel_desc        = "密级";
    public static String dataStatus_desc          = "数据状态";
    public static String inputPerson_desc         = "录入人员";
    public static String checkPerson_desc         = "校验人员";
    public static String cmtPerson_desc           = "审核人员";
    
    public B_Data(){}
	public B_Data(Object rs){
		loadFromRs(this, rs);
    }
	public I_DataObject getObject(){
		if(this.tableID>0){
			table.setTable(true);
			table.setObjectID(String.valueOf(tableID));
			table.setObjectName(tableName);
			return table;
		}
		else if(this.moduleID.length()>0){
			module.setModule(true);
			module.setObjectID(moduleID);
			module.setObjectName(moduleName);
			return module;
		}
		return null;
	}
	public long getDataid() {
		return dataid;
	}

	public void setDataid(long dataid) {
		this.dataid = dataid;
	}

	public int getTableID() {
		return tableID;
	}

	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	public long getExtid() {
		return extid;
	}

	public void setExtid(long extid) {
		this.extid = extid;
	}

	public String getInputPerson() {
		return inputPerson;
	}

	public void setInputPerson(String inputPerson) {
		this.inputPerson = inputPerson;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public String getCmtPerson() {
		return cmtPerson;
	}

	public void setCmtPerson(String cmtPerson) {
		this.cmtPerson = cmtPerson;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public int getDataSctLevel() {
		return dataSctLevel;
	}

	public void setDataSctLevel(int dataSctLevel) {
		this.dataSctLevel = dataSctLevel;
	}

	public int getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(int dataStatus) {
		this.dataStatus = dataStatus;
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

	public String getTableID_desc() {
		return tableID_desc;
	}

	public String getDataSctLevel_desc() {
		return dataSctLevel_desc;
	}

	public String getDataStatus_desc() {
		return dataStatus_desc;
	}

	public String getInputPerson_desc() {
		return inputPerson_desc;
	}

	public String getCheckPerson_desc() {
		return checkPerson_desc;
	}

	public String getCmtPerson_desc() {
		return cmtPerson_desc;
	}

	public String getTableMemo() {
		return tableMemo;
	}

	public void setTableMemo(String tableMemo) {
		this.tableMemo = tableMemo;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public I_DataTable getTable() {
		return table;
	}
	public void setTable(I_DataTable table) {
		this.table = table;
	}
	public String getParentDataid() {
		return parentDataid;
	}
	public void setParentDataid(String parentDataid) {
		this.parentDataid = parentDataid;
	}
	public I_DataModule getModule() {
		return module;
	}
	public void setModule(I_DataModule module) {
		this.module = module;
	}
	public String getModuleID() {
		return moduleID;
	}
	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

}
