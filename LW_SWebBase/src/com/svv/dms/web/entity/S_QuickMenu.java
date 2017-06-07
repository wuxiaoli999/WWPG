package com.svv.dms.web.entity;


public class S_QuickMenu extends AbstractEntity {
    private static final long serialVersionUID = -4893276615970577879L;
    
    protected long id;
    protected long userID;
    protected String moduleID = "";
    protected String moduleName = "";
    protected String moduleUrl = "";
    protected String moduleImg = "";
    protected int seq;
    protected String istDate = "";
    protected String uptDate = "";
  
    public S_QuickMenu(){}
    public S_QuickMenu(Object rs){
        loadFromRs(this, rs);
    }
    
    public long getId() {
        return id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
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

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
	public String getModuleImg() {
		return moduleImg;
	}
	public void setModuleImg(String moduleImg) {
		this.moduleImg = moduleImg;
	}

}
