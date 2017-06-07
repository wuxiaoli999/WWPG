package com.svv.dms.web.entity;


public class S_PlatForm extends AbstractEntity {
    private static final long serialVersionUID = -4893276615970577879L;

    protected long platFormID;
    protected String platFormName = "";
    protected long status;
    protected long interval;
    protected String istDate = "";
    protected String uptDate = "";
    
    public S_PlatForm(){}
    public S_PlatForm(Object rs){
    	loadFromRs(this, rs);
    }
    
    public long getPlatFormID() {
        return platFormID;
    }

    public void setPlatFormID(long platFormID) {
        this.platFormID = platFormID;
    }

    public String getPlatFormName() {
        return platFormName;
    }

    public void setPlatFormName(String platFormName) {
        this.platFormName = platFormName;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
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

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

}
