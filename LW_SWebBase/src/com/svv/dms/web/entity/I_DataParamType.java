package com.svv.dms.web.entity;


public class I_DataParamType extends AbstractEntity {

	private static final long serialVersionUID = -3549602586609366389L;

	protected int paramClassID = -1;
	protected String className = "";
	protected String classNameEn = "";
	protected String classNameEn2 = "";
	protected String classNameEn3 = "";
	protected String classNameEn4 = "";
	protected String classNameEn5 = "";
	protected int typeLevel = -1;
	protected int parentID = -1;
	protected long childNum = 1;
    protected String memo = "";
	protected int state = 1;
	protected String istDate = "";
	protected String uptDate = "";
		
	public static String paramClassID_desc  = "ID";
	public static String className_desc     = "类别中文名称";
	public static String classNameEn_desc   = "英文名称";
	public static String classNameEn2_desc   = "外文名称";
	public static String classNameEn3_desc   = "外文名称";
	public static String classNameEn4_desc   = "外文名称";
	public static String classNameEn5_desc   = "外文名称";
	public static String typeLevel_desc     = "级别";
    public static String parentID_desc      = "上一级";
    public static String memo_desc          = "描述";
	public static String state_desc         = "有效状态";

	public I_DataParamType(){}
    public I_DataParamType(Object rs){
    	loadFromRs(this, rs);
    }

	public int getParamClassID() {
		return paramClassID;
	}
	public int getParamValue(){
	    //if((""+paramClassID).length()<3) return paramClassID;
	    //return Integer.parseInt((""+paramClassID).substring((""+paramClassID).length()-3));
	    return paramClassID;
	}

	public void setParamClassID(int paramClassID) {
		this.paramClassID = paramClassID;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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

	public String getParamClassID_desc() {
		return paramClassID_desc;
	}

	public String getClassName_desc() {
		return className_desc;
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

	public String getClassNameEn() {
		return classNameEn;
	}
	public String getClassNameEn(int index) {
		if(index<2) return classNameEn;
		if(index==2) return classNameEn2;
		if(index==3) return classNameEn3;
		if(index==4) return classNameEn4;
		if(index==5) return classNameEn5;
		return "";
	}

	public void setClassNameEn(String classNameEn) {
		this.classNameEn = classNameEn;
	}

	public String getClassNameEn_desc() {
		return classNameEn_desc;
	}
	public String getClassNameEn2() {
		return classNameEn2;
	}
	public void setClassNameEn2(String classNameEn2) {
		this.classNameEn2 = classNameEn2;
	}
	public String getClassNameEn3() {
		return classNameEn3;
	}
	public void setClassNameEn3(String classNameEn3) {
		this.classNameEn3 = classNameEn3;
	}
	public String getClassNameEn4() {
		return classNameEn4;
	}
	public void setClassNameEn4(String classNameEn4) {
		this.classNameEn4 = classNameEn4;
	}
	public String getClassNameEn5() {
		return classNameEn5;
	}
	public void setClassNameEn5(String classNameEn5) {
		this.classNameEn5 = classNameEn5;
	}
	public String getClassNameEn2_desc() {
		return classNameEn2_desc;
	}
	public String getClassNameEn3_desc() {
		return classNameEn3_desc;
	}
	public String getClassNameEn4_desc() {
		return classNameEn4_desc;
	}
	public String getClassNameEn5_desc() {
		return classNameEn5_desc;
	}

}
