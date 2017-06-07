package com.svv.dms.web.entity;



public class I_SystemTableColumnExtInfo extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175409144853160630L;

    protected long id        = -1;
    protected long colid     = -1;
    protected int tableID    = -1;
    protected String extCode = "2000";
    protected String extNameZh = "";
    protected String extNameZhs = "";
    protected String extNameEn = "";
    protected String extNameEns = "";
    protected String extDefine = "";
    protected String extShow = "";
    protected int extLength = 0;
    protected int extPrecision = 0;
    protected String extUnit = "";
    protected String extValueScope = "";
    protected int extValueScopeTypeID = -1;
    protected String extValueScopeTypeParam = "";
    protected int extKeyNameFlag      = 0;
    protected int extKeywordFlag      = 0;
    protected int extQueryByFlag      = 0;
    protected String extSynName = "";
    protected String extRelateEnvironment = "火星探索数据工程";
    protected String extVersion = "V1.0";
    protected String extMemo = "";
    protected int extState      = 1;
    protected String extIstDate = "";
    protected String extUptDate = "";

    public static String colid_desc           = "列ID";
    public static String tableID_desc         = "主表ID";
    public static String extCode_desc = "标识符";
    public static String extNameZh_desc = "中文名称";
    public static String extNameZhs_desc = "中文名称缩写";
    public static String extNameEn_desc = "英文名称";
    public static String extNameEns_desc = "英文名称缩写";
    public static String extDefine_desc = "定义";
    public static String extShow_desc = "表示";
    public static String extLength_desc = "表示";
    public static String extPrecision_desc = "精度";
    public static String extUnit_desc = "单位";
    public static String extValueScope_desc = "值域";
    public static String extKeyNameFlag_desc = "是否主键名称";
    public static String extKeywordFlag_desc = "是否关键字";
    public static String extQueryByFlag_desc = "是否查询条件";
    public static String extSynName_desc = "同义名称";
    public static String extRelateEnvironment_desc = "相关环境";
    public static String extVersion_desc = "版本号";
    public static String extMemo_desc = "备注";
    public static String state_desc           = "有效状态";
    public static String istDate_desc         = "创建日期";
    
    public I_SystemTableColumnExtInfo(){}
    public I_SystemTableColumnExtInfo(Object rs){
    	loadFromRs(this, rs);
    }
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTableID() {
		return tableID;
	}

	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	public long getColid() {
		return colid;
	}

	public void setColid(long colid) {
		this.colid = colid;
	}

	public String getExtCode() {
		return extCode;
	}

	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}

	public String getExtNameZh() {
		return extNameZh;
	}

	public void setExtNameZh(String extNameZh) {
		this.extNameZh = extNameZh;
	}

	public String getExtNameZhs() {
		return extNameZhs;
	}

	public void setExtNameZhs(String extNameZhs) {
		this.extNameZhs = extNameZhs;
	}

	public String getExtNameEn() {
		return extNameEn;
	}

	public void setExtNameEn(String extNameEn) {
		this.extNameEn = extNameEn;
	}

	public String getExtNameEns() {
		return extNameEns;
	}

	public void setExtNameEns(String extNameEns) {
		this.extNameEns = extNameEns;
	}

	public String getExtDefine() {
		return extDefine;
	}

	public void setExtDefine(String extDefine) {
		this.extDefine = extDefine;
	}

	public String getExtShow() {
		return extShow;
	}

	public void setExtShow(String extShow) {
		this.extShow = extShow;
	}

	public int getExtPrecision() {
		return extPrecision;
	}

	public void setExtPrecision(int extPrecision) {
		this.extPrecision = extPrecision;
	}

	public String getExtUnit() {
		return extUnit;
	}

	public void setExtUnit(String extUnit) {
		this.extUnit = extUnit;
	}

	public String getExtValueScope() {
		return extValueScope;
	}

	public void setExtValueScope(String extValueScope) {
		this.extValueScope = extValueScope;
	}

	public String getExtSynName() {
		return extSynName;
	}

	public void setExtSynName(String extSynName) {
		this.extSynName = extSynName;
	}

	public String getExtRelateEnvironment() {
		return extRelateEnvironment;
	}

	public void setExtRelateEnvironment(String extRelateEnvironment) {
		this.extRelateEnvironment = extRelateEnvironment;
	}

	public String getExtVersion() {
		return extVersion;
	}

	public void setExtVersion(String extVersion) {
		this.extVersion = extVersion;
	}

	public String getExtMemo() {
		return extMemo;
	}

	public void setExtMemo(String extMemo) {
		this.extMemo = extMemo;
	}

	public int getExtState() {
		return extState;
	}
	public void setExtState(int extState) {
		this.extState = extState;
	}
	public String getExtIstDate() {
		return extIstDate;
	}
	public void setExtIstDate(String extIstDate) {
		this.extIstDate = extIstDate;
	}
	public String getExtUptDate() {
		return extUptDate;
	}
	public void setExtUptDate(String extUptDate) {
		this.extUptDate = extUptDate;
	}
	public String getColid_desc() {
		return colid_desc;
	}

	public String getTableID_desc() {
		return tableID_desc;
	}

	public String getExtCode_desc() {
		return extCode_desc;
	}

	public String getExtNameZh_desc() {
		return extNameZh_desc;
	}

	public String getExtNameZhs_desc() {
		return extNameZhs_desc;
	}

	public String getExtNameEn_desc() {
		return extNameEn_desc;
	}

	public String getExtNameEns_desc() {
		return extNameEns_desc;
	}

	public String getExtDefine_desc() {
		return extDefine_desc;
	}

	public String getExtShow_desc() {
		return extShow_desc;
	}

	public String getExtPrecision_desc() {
		return extPrecision_desc;
	}

	public String getExtUnit_desc() {
		return extUnit_desc;
	}

	public String getExtValueScope_desc() {
		return extValueScope_desc;
	}

	public String getExtSynName_desc() {
		return extSynName_desc;
	}

	public String getExtRelateEnvironment_desc() {
		return extRelateEnvironment_desc;
	}

	public String getExtVersion_desc() {
		return extVersion_desc;
	}

	public String getExtMemo_desc() {
		return extMemo_desc;
	}

	public String getState_desc() {
		return state_desc;
	}

	public String getIstDate_desc() {
		return istDate_desc;
	}

	public int getExtLength() {
		return extLength;
	}

	public void setExtLength(int extLength) {
		this.extLength = extLength;
	}

	public String getExtLength_desc() {
		return extLength_desc;
	}
	public int getExtValueScopeTypeID() {
		return extValueScopeTypeID;
	}
	public void setExtValueScopeTypeID(int extValueScopeTypeID) {
		this.extValueScopeTypeID = extValueScopeTypeID;
	}
	public String getExtValueScopeTypeParam() {
		return extValueScopeTypeParam;
	}
	public void setExtValueScopeTypeParam(String extValueScopeTypeParam) {
		this.extValueScopeTypeParam = extValueScopeTypeParam;
	}
	public int getExtKeyNameFlag() {
		return extKeyNameFlag;
	}
	public void setExtKeyNameFlag(int extKeyNameFlag) {
		this.extKeyNameFlag = extKeyNameFlag;
	}
	public int getExtKeywordFlag() {
		return extKeywordFlag;
	}
	public void setExtKeywordFlag(int extKeywordFlag) {
		this.extKeywordFlag = extKeywordFlag;
	}
	public String getExtKeyNameFlag_desc() {
		return extKeyNameFlag_desc;
	}
	public String getExtKeywordFlag_desc() {
		return extKeywordFlag_desc;
	}
    public int getExtQueryByFlag() {
        return extQueryByFlag;
    }
    public void setExtQueryByFlag(int extQueryByFlag) {
        this.extQueryByFlag = extQueryByFlag;
    }
    public String getExtQueryByFlag_desc() {
        return extQueryByFlag_desc;
    }

}
