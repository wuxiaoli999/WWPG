package com.svv.dms.web.entity;



public class I_Process extends AbstractEntity {

    private static final long serialVersionUID = -7073179232937445514L;

    protected int psid;
    protected int organID;
    protected String processName = "";
    protected int processType;
    protected int tableID;
    protected long creator;
    protected int state;
    protected String remark = "";
    protected String istDate = "";
    protected String uptDate = "";

    public static String psid_desc            = "ID";
    public static String organID_desc         = "公司名称";
    public static String processName_desc     = "流程名称";
    public static String processType_desc     = "流程分类 ";
    public static String tableID_desc         = "对应数据表";
    public static String creator_desc         = "创建者";
    public static String state_desc           = "有效状态";
    public static String remark_desc          = "备注";
    public static String istDate_desc         = "创建日期";
    public static String uptDate_desc         = "更新日期";
    
    public I_Process(){}
    public I_Process(Object rs){
    	loadFromRs(this, rs);
    }
    public int getPsid() {
        return psid;
    }
    public void setPsid(int psid) {
        this.psid = psid;
    }
    public String getProcessName() {
        return processName;
    }
    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public int getTableID() {
        return tableID;
    }
    public void setTableID(int tableID) {
        this.tableID = tableID;
    }
    public long getCreator() {
        return creator;
    }
    public void setCreator(long creator) {
        this.creator = creator;
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
    public String getPsid_desc() {
        return psid_desc;
    }
    public String getProcessName_desc() {
        return processName_desc;
    }
    public String getTableID_desc() {
        return tableID_desc;
    }
    public String getCreator_desc() {
        return creator_desc;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRemark_desc() {
        return remark_desc;
    }
    public int getOrganID() {
        return organID;
    }
    public void setOrganID(int organID) {
        this.organID = organID;
    }
    public String getOrganID_desc() {
        return organID_desc;
    }
    public int getProcessType() {
        return processType;
    }
    public void setProcessType(int processType) {
        this.processType = processType;
    }
    public String getProcessType_desc() {
        return processType_desc;
    }

}
