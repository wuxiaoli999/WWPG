package com.svv.dms.web.entity;

import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.util.TColumn;

public class I_DataTableColumn extends I_DataTableColumnExtInfo {
	
	final public static String COL_NAME_SPLITER = "-";

	final public static int LISTKB_NONE = 0;
	final public static int LISTKB_SHOW = 1;
	final public static int LISTKB_FROZEN = 2;
	final public static int EDITKB_TEXT = 1;
	final public static int EDITKB_CHECKBOX = 2;
	final public static int EDITKB_SELECT = 3;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175409144853160630L;

    protected long id     = -1;
    protected long colid     = -1;
    protected int tableID    = -1;
    protected String moduleID = "";
    protected String colName = "";
    protected String colName_as = "";
    protected String dataTypeStr = "";
    protected int dataType    = ParamClass.VALUE_DATA_TYPE_NUMBER;
    protected int dataLen     = 6;
    protected int dataDotLen  = 0;
    protected String colstr  = "";
    protected String colMemo  = "";
    protected String nullFlag = "1";
    protected String def      = "";
    protected int dataCol1Num = 0;
    protected int dataCol2Num = 0;
    protected int dataCol3Num = 0;
    protected String creator  = "";
    protected int sctLevel    = ParamClass.VALUE_SCT_LEVEL_PUBLIC;
    protected int seq         = 0;
    protected int groupFunc   = -1;
    protected String listShowFlag   = "1";
    protected int showWidth   = 0;
    protected String formShowFlag   = "1";
    protected int state       = 1;
    protected String istDate  = "";
    protected String uptDate  = "";
    
    private int listKB         = 0;     //列表显示标记
    private boolean primaryKey = false; //是否主键
    private boolean sortKB     = false; //是否排序
    private int editKB         = 0;     //编辑
    private String align       = "";    //位置

    public static String colid_desc           = "ID";
    public static String tableID_desc         = "主表ID";
    public static String colstr_desc          = "列说明";
    public static String colMemo_desc         = "名称";
    public static String colName_desc         = "字段";
    public static String dataTypeStr_desc     = "数据类型";
    public static String dataType_desc        = "数据类型";
    public static String dataLen_desc         = "数据总长度";
    public static String dataDotLen_desc      = "小数位数";
    public static String nullFlag_desc        = "允许为空";
    public static String def_desc             = "默认值";
    public static String creator_desc         = "创建者";
    public static String sctLevel_desc        = "密级";
    public static String seq_desc             = "排序";
    public static String groupFunc_desc       = "分组";
    public static String listShowFlag_desc    = "列表中是否显示";
    public static String showWidth_desc       = "列表显示宽度";
    public static String formShowFlag_desc    = "表单中是否显示";
    public static String state_desc           = "有效状态";
    public static String istDate_desc         = "创建日期";

    public I_DataTableColumn(){}
    public I_DataTableColumn(Object rs){
    	loadFromRs(this, rs);
    }
    
    public I_DataTableColumn(String colName, String colMemo){
    	init(colName, colMemo, 0, 0, false, 0, TColumn.ALIGN_LEFT, false);
    }
    public I_DataTableColumn(String colName, String colMemo, int listKB, int editFlag){
    	init(colName, colMemo, listKB, editFlag, false, 0, TColumn.ALIGN_LEFT, false);
    }
    public I_DataTableColumn(String colName, String colMemo, int listKB, int editFlag, int wid){
    	init(colName, colMemo, listKB, editFlag, false, wid, TColumn.ALIGN_LEFT, false);
    }
    public I_DataTableColumn(String colName, String colMemo, int listKB, int editFlag, boolean primaryKeyFlag){
    	init(colName, colMemo, listKB, editFlag, primaryKeyFlag, 0, TColumn.ALIGN_LEFT, false);
    }
    public I_DataTableColumn(String colName, String colMemo, int listKB, int editFlag, int dataLen, String align, boolean sortFlag){
    	init(colName, colMemo, listKB, editFlag, false, dataLen, align, sortFlag);
    }
    public void init(String colName, String colMemo, int listKB, int editFlag, boolean primaryKeyFlag, int dataLen, String align, boolean sortFlag){
    	this.colName = colName;
    	this.colMemo = colMemo;
    	this.listKB = listKB;
    	this.editKB = editFlag;
    	this.primaryKey = primaryKeyFlag;
    	this.sortKB = sortFlag;
    	this.dataLen = dataLen;
    	this.align = align;
    }

    public long getColid() {
		return colid;
	}

	public void setColid(long colid) {
		this.colid = colid;
	}

	public int getTableID() {
		return tableID;
	}

	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public int getDataLen() {
		return dataLen;
	}

	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}

	public int getDataDotLen() {
		return dataDotLen;
	}

	public void setDataDotLen(int dataDotLen) {
		this.dataDotLen = dataDotLen;
	}

	public String getColMemo() {
		return colMemo;
	}

	public void setColMemo(String colMemo) {
		this.colMemo = colMemo;
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

	public String getColid_desc() {
		return colid_desc;
	}

	public String getTableID_desc() {
		return tableID_desc;
	}

	public String getColName_desc() {
		return colName_desc;
	}

	public String getDataType_desc() {
		return dataType_desc;
	}

	public String getDataLen_desc() {
		return dataLen_desc;
	}

	public String getDataDotLen_desc() {
		return dataDotLen_desc;
	}

	public String getColMemo_desc() {
		return colMemo_desc;
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

	public String getDataTypeStr_desc() {
		return dataTypeStr_desc;
	}

	public String getDataTypeStr() {
		return dataTypeStr;
	}

	public void setDataTypeStr(String dataTypeStr) {
		this.dataTypeStr = dataTypeStr;
	}

	public int getListKB() {
		return listKB;
	}

	public void setListKB(int listKB) {
		this.listKB = listKB;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public boolean isSortKB() {
		return sortKB;
	}

	public void setSortKB(boolean sortKB) {
		this.sortKB = sortKB;
	}

	public int getEditKB() {
		return editKB;
	}

	public void setEditKB(int editKB) {
		this.editKB = editKB;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	public int getDataCol1Num() {
		return dataCol1Num;
	}
	public void setDataCol1Num(int dataCol1Num) {
		this.dataCol1Num = dataCol1Num;
	}
	public int getDataCol2Num() {
		return dataCol2Num;
	}
	public void setDataCol2Num(int dataCol2Num) {
		this.dataCol2Num = dataCol2Num;
	}
	public int getDataCol3Num() {
		return dataCol3Num;
	}
	public void setDataCol3Num(int dataCol3Num) {
		this.dataCol3Num = dataCol3Num;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSeq_desc() {
		return seq_desc;
	}
    public String getNullFlag() {
        return nullFlag;
    }
    public void setNullFlag(String nullFlag) {
        this.nullFlag = nullFlag;
    }
    public String getNullFlag_desc() {
        return nullFlag_desc;
    }
    public String getListShowFlag() {
        return listShowFlag;
    }
    public void setListShowFlag(String listShowFlag) {
        this.listShowFlag = listShowFlag;
    }
    public int getShowWidth() {
        return showWidth;
    }
    public void setShowWidth(int showWidth) {
        this.showWidth = showWidth;
    }
    public String getFormShowFlag() {
        return formShowFlag;
    }
    public void setFormShowFlag(String formShowFlag) {
        this.formShowFlag = formShowFlag;
    }
    public String getListShowFlag_desc() {
        return listShowFlag_desc;
    }
    public String getShowWidth_desc() {
        return showWidth_desc;
    }
    public String getFormShowFlag_desc() {
        return formShowFlag_desc;
    }
    
	public int getGroupFunc() {
		return groupFunc;
	}
	public void setGroupFunc(int groupFunc) {
		this.groupFunc = groupFunc;
	}
	public String getDef() {
		return def;
	}
	public void setDef(String def) {
		this.def = def;
	}
	public String getDef_desc() {
		return def_desc;
	}
	public String getColstr() {
		return colstr;
	}
	public void setColstr(String colstr) {
		this.colstr = colstr;
	}
	public String getColstr_desc() {
		return colstr_desc;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getModuleID() {
		return moduleID;
	}
	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}
	public String getColName_as() {
		return colName_as;
	}
	public void setColName_as(String colName_as) {
		this.colName_as = colName_as;
	}
	public String getGroupFunc_desc() {
		return groupFunc_desc;
	}

}
