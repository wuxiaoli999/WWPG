package com.svv.dms.web.entity;



public class I_ProcessDesc extends AbstractEntity {

    private static final long serialVersionUID = -7073179232937445514L;

    protected int ccid;
    protected int psid;
    protected int seq;
    protected String processTitle = "";
    protected String processDesc = "";
    protected String nextSeq = "";
    protected String conditions = "";
    protected String processCols = "";
    protected int processActorMode;
    protected String processActor;
    protected String processActorDepartment;
    protected String processActorRole;
    protected int actorSelectRule;
    protected int actorAutoSelectRule;
    protected String actorAutoSelectRuleMemo;
    protected String attachFilePower;
    protected int attachFileEditFlag = 0;
    protected int state;
    protected String istDate = "";
    protected String uptDate = "";

    public static String ccid_desc            = "ID";
    public static String psid_desc            = "流程ID";
    public static String seq_desc             = "序号";
    public static String processTitle_desc    = "过程标题";
    public static String processDesc_desc     = "过程说明";
    public static String nextSeq_desc         = "下一步骤";
    public static String conditions_desc      = "条件设置";
    public static String processCols_desc     = "表格字段";
    public static String processActorMode_desc       = "经手模式";
    public static String processActor_desc           = "经手人（人员）";
    public static String processActorDepartment_desc = "经手人（部门）";
    public static String processActorRole_desc       = "经手人（角色）";
    public static String actorSelectRule_desc        = "选人过滤规则";
    public static String actorAutoSelectRule_desc    = "自动选人规则";
    public static String attachFilePower_desc         = "附件操作权限";
    public static String attachFileEditFlag_desc      = "是否允许本步骤经办人编辑附件";
    public static String state_desc           = "有效状态";
    public static String istDate_desc         = "创建日期";
    public static String uptDate_desc         = "更新日期";
    
    public I_ProcessDesc(){}
    public I_ProcessDesc(Object rs){
    	loadFromRs(this, rs);
    }
    public int getCcid() {
        return ccid;
    }
    public void setCcid(int ccid) {
        this.ccid = ccid;
    }
    public int getPsid() {
        return psid;
    }
    public void setPsid(int psid) {
        this.psid = psid;
    }
    public int getSeq() {
        return seq;
    }
    public void setSeq(int seq) {
        this.seq = seq;
    }
    public String getProcessTitle() {
        return processTitle;
    }
    public void setProcessTitle(String processTitle) {
        this.processTitle = processTitle;
    }
    public String getProcessDesc() {
        return processDesc;
    }
    public void setProcessDesc(String processDesc) {
        this.processDesc = processDesc;
    }
    public String getNextSeq() {
        return nextSeq;
    }
    public void setNextSeq(String nextSeq) {
        this.nextSeq = nextSeq;
    }
    public String getProcessCols() {
        return processCols;
    }
    public void setProcessCols(String processCols) {
        this.processCols = processCols;
    }
    public int getProcessActorMode() {
        return processActorMode;
    }
    public void setProcessActorMode(int processActorMode) {
        this.processActorMode = processActorMode;
    }
    public String getProcessActor() {
        return processActor;
    }
    public void setProcessActor(String processActor) {
        this.processActor = processActor;
    }
    public String getProcessActorDepartment() {
        return processActorDepartment;
    }
    public void setProcessActorDepartment(String processActorDepartment) {
        this.processActorDepartment = processActorDepartment;
    }
    public String getProcessActorRole() {
        return processActorRole;
    }
    public void setProcessActorRole(String processActorRole) {
        this.processActorRole = processActorRole;
    }
    public int getActorSelectRule() {
        return actorSelectRule;
    }
    public void setActorSelectRule(int actorSelectRule) {
        this.actorSelectRule = actorSelectRule;
    }
    public int getActorAutoSelectRule() {
        return actorAutoSelectRule;
    }
    public void setActorAutoSelectRule(int actorAutoSelectRule) {
        this.actorAutoSelectRule = actorAutoSelectRule;
    }
    public String getAttachFilePower() {
        return attachFilePower;
    }
    public void setAttachFilePower(String attachFilePower) {
        this.attachFilePower = attachFilePower;
    }
    public int getAttachFileEditFlag() {
        return attachFileEditFlag;
    }
    public void setAttachFileEditFlag(int attachFileEditFlag) {
        this.attachFileEditFlag = attachFileEditFlag;
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
    public String getCcid_desc() {
        return ccid_desc;
    }
    public String getPsid_desc() {
        return psid_desc;
    }
    public String getSeq_desc() {
        return seq_desc;
    }
    public String getProcessTitle_desc() {
        return processTitle_desc;
    }
    public String getProcessDesc_desc() {
        return processDesc_desc;
    }
    public String getNextSeq_desc() {
        return nextSeq_desc;
    }
    public String getProcessCols_desc() {
        return processCols_desc;
    }
    public String getProcessActorMode_desc() {
        return processActorMode_desc;
    }
    public String getProcessActor_desc() {
        return processActor_desc;
    }
    public String getProcessActorDepartment_desc() {
        return processActorDepartment_desc;
    }
    public String getProcessActorRole_desc() {
        return processActorRole_desc;
    }
    public String getActorSelectRule_desc() {
        return actorSelectRule_desc;
    }
    public String getActorAutoSelectRule_desc() {
        return actorAutoSelectRule_desc;
    }
    public String getAttachFilePower_desc() {
        return attachFilePower_desc;
    }
    public String getAttachFileEditFlag_desc() {
        return attachFileEditFlag_desc;
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
    public String getConditions() {
        return conditions;
    }
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
    public String getConditions_desc() {
        return conditions_desc;
    }
    public String getActorAutoSelectRuleMemo() {
        return actorAutoSelectRuleMemo;
    }
    public void setActorAutoSelectRuleMemo(String actorAutoSelectRuleMemo) {
        this.actorAutoSelectRuleMemo = actorAutoSelectRuleMemo;
    }
    
}
