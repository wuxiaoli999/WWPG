package com.svv.dms.web.entity;

import com.svv.dms.web.util.DES;


public class S_Param extends AbstractEntity {

    private static final long serialVersionUID = -3549602586609366389L;

    protected long id;
    protected String paramName = "";
    protected String paramValue = "";
    protected String remark = "";

    public S_Param(){}
    public S_Param(Object rs){
    	loadFromRs(this, rs);
    	DES des = new DES();
    	this.paramName = des.decrypt(paramName);
    	this.paramValue = des.decrypt(paramValue);
    	this.remark = des.decrypt(remark);
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
