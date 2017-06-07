package com.svv.dms.web.entity;

import com.svv.dms.web.common.ComBeanIS_Emp;

public class IB_Process extends AbstractEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 4352418983487213032L;
    protected long processID    = -1;
    protected String processTitle    = "";
    protected long organID    = 0;
    protected int psid    = 0;
    protected int tableID    = 0;
    protected long dataid    = -1;
    protected long creator    = 0;
    protected int curSeq    = 0;
    protected String curActor    = "";
    protected String curStatus    = "";
    protected int dealFlag    = 0;
    protected int state    = 1;
    protected String remark    = "";
    protected String istDate    = "";
    protected String uptDate    = "";

    private int processSeq = 0;
    private long processActor = 0;
    private String processStatus = "";
    private int processActResult = 0;
    private String processActMemo = "";

    public static String processID_desc    = "流程ID";
    public static String processTitle_desc    = "流程标题";
    public static String organID_desc    = "公司名称";
    public static String psid_desc    = "psid";
    public static String tableID_desc    = "主表ID";
    public static String dataid_desc    = "主表数据ID";
    public static String creator_desc    = "申请人";
    public static String curSeq_desc    = "当前步骤";
    public static String curActor_desc    = "当前经手人";
    public static String curStatus_desc    = "当前状态";
    public static String dealFlag_desc    = "处理标记";
    public static String state_desc    = "有效状态";
    public static String remark_desc    = "备注";
    public static String istDate_desc    = "创建日期";
    public static String uptDate_desc    = "操作时间";   

    public static String processSeq_desc    = "步骤序号";
    public static String processActor_desc    = "操作人员";
    public static String processStatus_desc    = "步骤";
    public static String processActResult_desc    = "经手人意见";
    public static String processActMemo_desc    = "批注意见";

    public IB_Process(){}
    public IB_Process(Object rs){
        loadFromRs(this, rs);
    }
    public long getProcessID(){
        return this.processID;
    }

    public void setProcessID(long processID){
        this.processID = processID;
    }

    public String getProcessTitle(){
        return this.processTitle;
    }

    public void setProcessTitle(String processTitle){
        this.processTitle = processTitle;
    }

    public long getOrganID(){
        return this.organID;
    }

    public void setOrganID(long organID){
        this.organID = organID;
    }

    public int getPsid(){
        return this.psid;
    }

    public void setPsid(int psid){
        this.psid = psid;
    }

    public int getTableID(){
        return this.tableID;
    }

    public void setTableID(int tableID){
        this.tableID = tableID;
    }

    public long getDataid(){
        return this.dataid;
    }

    public void setDataid(long dataid){
        this.dataid = dataid;
    }

    public long getCreator(){
        return this.creator;
    }
    public String getCreatorName(){
        return ComBeanIS_Emp.getText(creator);
    }

    public void setCreator(long creator){
        this.creator = creator;
    }

    public String getCurActor(){
        return this.curActor;
    }
    public String getCurActorName(){
        return ComBeanIS_Emp.getText(Long.parseLong(curActor));
    }

    public void setCurActor(String curActor){
        this.curActor = curActor;
    }

    public String getCurStatus(){
        return this.curStatus;
    }

    public void setCurStatus(String curStatus){
        this.curStatus = curStatus;
    }

    public int getState(){
        return this.state;
    }

    public void setState(int state){
        this.state = state;
    }

    public String getRemark(){
        return this.remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getIstDate(){
        return this.istDate;
    }

    public void setIstDate(String istDate){
        this.istDate = istDate;
    }

    public String getUptDate(){
        return this.uptDate;
    }

    public void setUptDate(String uptDate){
        this.uptDate = uptDate;
    }

    public String getProcessID_desc(){
        return processID_desc;
    }

    public String getProcessTitle_desc(){
        return processTitle_desc;
    }

    public String getOrganID_desc(){
        return organID_desc;
    }

    public String getPsid_desc(){
        return psid_desc;
    }

    public String getTableID_desc(){
        return tableID_desc;
    }

    public String getDataid_desc(){
        return dataid_desc;
    }

    public String getCreator_desc(){
        return creator_desc;
    }

    public String getCurActor_desc(){
        return curActor_desc;
    }

    public String getCurStatus_desc(){
        return curStatus_desc;
    }

    public String getState_desc(){
        return state_desc;
    }

    public String getRemark_desc(){
        return remark_desc;
    }

    public String getIstDate_desc(){
        return istDate_desc;
    }

    public String getUptDate_desc(){
        return uptDate_desc;
    }
    public int getCurSeq() {
        return curSeq;
    }
    public void setCurSeq(int curSeq) {
        this.curSeq = curSeq;
    }
    public int getDealFlag() {
        return dealFlag;
    }
    public void setDealFlag(int dealFlag) {
        this.dealFlag = dealFlag;
    }
    public String getCurSeq_desc() {
        return curSeq_desc;
    }
    public String getDealFlag_desc() {
        return dealFlag_desc;
    }
    public int getProcessActResult() {
        return processActResult;
    }
    public void setProcessActResult(int processActResult) {
        this.processActResult = processActResult;
    }
    public String getProcessActMemo() {
        return processActMemo;
    }
    public void setProcessActMemo(String processActMemo) {
        this.processActMemo = processActMemo;
    }
    public String getProcessActResult_desc() {
        return processActResult_desc;
    }
    public String getProcessActMemo_desc() {
        return processActMemo_desc;
    }
    public String getProcessStatus() {
        return processStatus;
    }
    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }
    public String getProcessStatus_desc() {
        return processStatus_desc;
    }
    public int getProcessSeq() {
        return processSeq;
    }
    public void setProcessSeq(int processSeq) {
        this.processSeq = processSeq;
    }
    public String getProcessSeq_desc() {
        return processSeq_desc;
    }
    public long getProcessActor() {
        return processActor;
    }
    public void setProcessActor(long processActor) {
        this.processActor = processActor;
    }
    public String getProcessActor_desc() {
        return processActor_desc;
    }

}
