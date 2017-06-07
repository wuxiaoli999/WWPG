package com.svv.dms.web.entity;



public class I_SystemTable extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175409144853160630L;
	
    protected int tableID;
    protected String tableName = "";
    protected String tableMemo = "";
    protected String creator = "";
    protected int tableTypeID;
	protected int colNum = 0;
	protected long dataNum = 0;
    protected int sctLevel   = -1;
    protected int scopeFlag  = 0;
    protected int attachFileFlag  = 0;
    protected int dataStatusFlag  = 0;
    protected int sctLevelFlag  = 0;
    protected String otherButtons = "";
    protected String parentTableID  = "";
	protected int childTableNum = 0;
    protected String childTableIDs  = "";
    protected int state      = 1;
    protected String istDate = "";
    protected String uptDate = "";

    public static String tableID_desc         = "数据表ID";
    public static String tableName_desc       = "英文名称";
    public static String tableMemo_desc       = "数据表中文名称";
    public static String creator_desc         = "创建者";
    public static String tableTypeID_desc     = "数据表类别";
    public static String colNum_desc          = "属性数";
    public static String dataNum_desc         = "数据数";
    public static String sctLevel_desc        = "密级";
    public static String scopeFlag_desc       = "是否值域数据表";
    public static String attachFileFlag_desc  = "多媒体信息开关";
    public static String dataStatusFlag_desc  = "数据状态开关";
    public static String sctLevelFlag_desc    = "密级开关";
    public static String otherButtons_desc    = "其他功能菜单";
    public static String parentTableID_desc   = "所属父表";
    public static String childTableNum_desc   = "子表数";
    public static String state_desc           = "有效状态";
    public static String istDate_desc         = "创建日期";

    public I_SystemTable(){}
    public I_SystemTable(Object rs){
    	loadFromRs(this, rs);
    }
    
    public int getTableID() {
		return tableID;
	}

	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableMemo() {
		return tableMemo;
	}

	public void setTableMemo(String tableMemo) {
		this.tableMemo = tableMemo;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	public String getTableID_desc() {
		return tableID_desc;
	}

	public String getTableName_desc() {
		return tableName_desc;
	}

	public String getTableMemo_desc() {
		return tableMemo_desc;
	}

	public String getCreator_desc() {
		return creator_desc;
	}

	public String getSctLevel_desc() {
		return sctLevel_desc;
	}

	public String getState_desc() {
		return state_desc;
	}

	public String getIstDate_desc() {
		return istDate_desc;
	}

    public int getTableTypeID() {
        return tableTypeID;
    }

    public void setTableTypeID(int tableTypeID) {
        this.tableTypeID = tableTypeID;
    }

    public String getTableTypeID_desc() {
        return tableTypeID_desc;
    }

	public long getDataNum() {
		return dataNum;
	}

	public void setDataNum(long dataNum) {
		this.dataNum = dataNum;
	}

	public String getDataNum_desc() {
		return dataNum_desc;
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
	public int getScopeFlag() {
		return scopeFlag;
	}
	public void setScopeFlag(int scopeFlag) {
		this.scopeFlag = scopeFlag;
	}
	public String getScopeFlag_desc() {
		return scopeFlag_desc;
	}
	public String getParentTableID() {
		return parentTableID;
	}
	public void setParentTableID(String parentTableID) {
		this.parentTableID = parentTableID;
	}
	public String getParentTableID_desc() {
		return parentTableID_desc;
	}
	public int getChildTableNum() {
		return childTableNum;
	}
	public void setChildTableNum(int childTableNum) {
		this.childTableNum = childTableNum;
	}
	public String getChildTableNum_desc() {
		return childTableNum_desc;
	}
	public String getChildTableIDs() {
		return childTableIDs;
	}
	public void setChildTableIDs(String childTableIDs) {
		this.childTableIDs = childTableIDs;
	}
    public String getAttachFileFlag_desc() {
        return attachFileFlag_desc;
    }
    public int getAttachFileFlag() {
        return attachFileFlag;
    }
    public void setAttachFileFlag(int attachFileFlag) {
        this.attachFileFlag = attachFileFlag;
    }
    public int getDataStatusFlag() {
        return dataStatusFlag;
    }
    public void setDataStatusFlag(int dataStatusFlag) {
        this.dataStatusFlag = dataStatusFlag;
    }
    public String getDataStatusFlag_desc() {
        return dataStatusFlag_desc;
    }
    public int getSctLevelFlag() {
        return sctLevelFlag;
    }
    public void setSctLevelFlag(int sctLevelFlag) {
        this.sctLevelFlag = sctLevelFlag;
    }
    public String getSctLevelFlag_desc() {
        return sctLevelFlag_desc;
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

}
