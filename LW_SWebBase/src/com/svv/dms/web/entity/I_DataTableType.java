package com.svv.dms.web.entity;


public class I_DataTableType extends AbstractEntity {

	private static final long serialVersionUID = -3549602586609366389L;

	protected int tableTypeID = -1;
	protected String tableTypeName = "";
	protected int typeLevel = -1;
	protected int parentID = -1;
	protected long childNum = 1;
    protected String memo = "";
	protected int state = 1;
	protected String istDate = "";
	protected String uptDate = "";
	
	public static String tableTypeID_desc   = "ID";
	public static String tableTypeName_desc = "类别名称";
	public static String typeLevel_desc     = "级别";
    public static String parentID_desc      = "上一级";
    public static String memo_desc          = "描述";
	public static String state_desc         = "有效状态";

	public I_DataTableType(){}
	public I_DataTableType(Object rs){
		loadFromRs(this, rs);
    }
	
	public int getTableTypeID() {
		return tableTypeID;
	}

	public void setTableTypeID(int tableTypeID) {
		this.tableTypeID = tableTypeID;
	}

	public String getTableTypeName() {
		return tableTypeName;
	}

	public void setTableTypeName(String tableTypeName) {
		this.tableTypeName = tableTypeName;
	}

	public int getTypeLevel() {
		return typeLevel;
	}

	public void setTypeLevel(int typeLevel) {
		this.typeLevel = typeLevel;
	}

	public int getParentID() {
		return parentID;
	}

	public void setParentID(int parentID) {
		this.parentID = parentID;
	}

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

	public String getTableTypeID_desc() {
		return tableTypeID_desc;
	}

	public String getTableTypeName_desc() {
		return tableTypeName_desc;
	}

	public String getTypeLevel_desc() {
		return typeLevel_desc;
	}

	public String getParentID_desc() {
		return parentID_desc;
	}

	public String getState_desc() {
		return state_desc;
	}

    public String getMemo_desc() {
        return memo_desc;
    }

	public long getChildNum() {
		return childNum;
	}

	public void setChildNum(long childNum) {
		this.childNum = childNum;
	}

}
