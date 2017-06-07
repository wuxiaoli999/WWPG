package com.svv.dms.web.entity;



public class I_Area extends AbstractEntity {

    private static final long serialVersionUID = -7073179232937445514L;

    protected int id;
    protected String areaName = "";
    protected int areaID;
    protected int state;
    protected int areaLevel;
    protected int upAreaID;
    protected String remark = "";
    protected String istDate = "";
    protected String uptDate = "";

    public I_Area(){}
    public I_Area(Object rs){
    	loadFromRs(this, rs);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(int level) {
		this.areaLevel = level;
	}

	public int getUpAreaID() {
		return upAreaID;
	}

	public void setUpAreaID(int upAreaID) {
		this.upAreaID = upAreaID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
