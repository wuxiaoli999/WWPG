package com.svv.dms.web.entity;


public class S_Role extends AbstractEntity {

    private static final long serialVersionUID = 5625344067885269935L;

    protected int roleID;
    protected int roleSeq = 100;
    protected String roleName = "";
    protected int organType = -1;
    protected int sctLevel = -1;
    protected int state = 1;
    protected int power = 0;
    protected String remark = "";
    protected String istDate = "";
    protected String uptDate = "";
    
    public S_Role(){}
    public S_Role(Object rs){
    	loadFromRs(this, rs);
    }
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setState(int roleState) {
        this.state = roleState;
    }

    public int getState() {
        return state;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
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

    public int getOrganType() {
        return organType;
    }

    public void setOrganType(int organType) {
        this.organType = organType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRoleSeq() {
        return roleSeq;
    }

    public void setRoleSeq(int roleSeq) {
        this.roleSeq = roleSeq;
    }

	public int getSctLevel() {
		return sctLevel;
	}

	public void setSctLevel(int sctLevel) {
		this.sctLevel = sctLevel;
	}

}