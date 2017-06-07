package com.svv.dms.web.entity;



public class S_Log extends AbstractEntity {

    private static final long serialVersionUID = 1852051112181283334L;

    protected long id;
    protected long logType;
    protected String title = "";
    protected String context = "";    
    protected String oid;        
    protected long userID;    
    protected String userName = "";    
    protected String ipAddress = "";    
    protected String istDate = null;
    
    public S_Log(){}
    public S_Log(Object rs){
    	loadFromRs(this, rs);
    }
    
    public long getId() {
        return id;
    }

    public String getOid() {
        return oid;
    }
    public void setOid(String oid) {
        this.oid = oid;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getLogType() {
        return logType;
    }

    public void setLogType(long logType) {
        this.logType = logType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIstDate() {
        return istDate;
    }

    public void setIstDate(String istDate) {
        this.istDate = istDate;
    }
}
