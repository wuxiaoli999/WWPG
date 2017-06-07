package com.svv.dms.web.entity;


public class I_SystemParam extends AbstractEntity {

	private static final long serialVersionUID = -3549602586609366389L;

	protected String id = "";	
	protected String paramClass = "";	
	protected String className = "";
	protected String value = "";
	protected String name = "";
	protected String parentClass = "";
	protected String state = "";
	protected String istDate = "";
	protected String uptDate = "";

	public static String id_desc = "ID";
	public static String paramClass_desc = "类别编号";
	public static String className_desc = "类别名称";
	public static String value_desc = "值";
	public static String name_desc = "值描述";
	public static String parentClass_desc = "上级类别";
	public static String state_desc = "状态";
	public static String istDate_desc = "插入日期";
	public static String uptDate_desc = "更新日期";
	
	public I_SystemParam(){}
    public I_SystemParam(Object rs){
    	loadFromRs(this, rs);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParamClass() {
		return paramClass;
	}

	public void setParamClass(String paramClass) {
		this.paramClass = paramClass;
	}

	public String getParentClass() {
		return parentClass;
	}

	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
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

	public String getId_desc() {
		return id_desc;
	}

	public String getParamClass_desc() {
		return paramClass_desc;
	}

	public String getClassName_desc() {
		return className_desc;
	}

	public String getValue_desc() {
		return value_desc;
	}

	public String getParentClass_desc() {
		return parentClass_desc;
	}

	public String getState_desc() {
		return state_desc;
	}

	public String getIstDate_desc() {
		return istDate_desc;
	}

	public String getUptDate_desc() {
		return uptDate_desc;
	}

	public String getName_desc() {
		return name_desc;
	}
	

}
