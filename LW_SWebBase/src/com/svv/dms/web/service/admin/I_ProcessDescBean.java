package com.svv.dms.web.service.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.common.ComBeanI_DataTable;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanState;
import com.svv.dms.web.common.ComBeanIS_Department;
import com.svv.dms.web.common.ComBeanIS_Emp;
import com.svv.dms.web.common.ComBeanIS_JobPosition;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.entity.I_Process;
import com.svv.dms.web.entity.I_ProcessDesc;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.TColumn;

public class I_ProcessDescBean extends AbstractBean {

    public String I_ProcessDesc(){
        if(this.getParameter("cmd").equals("")){
            I_Process o = (I_Process)getList(I_Process.class, dbQuery("SP_I_ProcessQueryByID", new Object[]{ po.getPsid() })).get(0);
            po.setProcessName(o.getProcessName());
            po.setOrganID(o.getOrganID());
            po.setTableID(o.getTableID());
            ao.setPsid(po.getPsid());
        }
        return this.exeByCmd("");
    }

    public String conditions(){
        return "conditions";
    }

    public String cols(){
        return "cols";
    }
    @SuppressWarnings("unchecked")
    public String queryCols(){
        List<I_DataTableColumn> list = (List<I_DataTableColumn>)getList(I_DataTableColumn.class, dbQuery(SQL.SP_I_DataTableColumnQueryByC(po.getTableID()+"")));

        HashMap<Long, Integer> colsmap = new HashMap<Long, Integer>();
        if(ao.getProcessCols().length()>0){
            String[] cols = ao.getProcessCols().substring(1).split(",");            
            for(String c: cols){
                String[] cc = c.split(":");
                colsmap.put(Long.parseLong(cc[0]), Integer.parseInt(cc[1]));
            }
        }
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("序号", "LastCCID", TColumn.ALIGN_LEFT),
                                     new TColumn("列", null, TColumn.ALIGN_LEFT),
                                     new TColumn("操作", null, TColumn.ALIGN_LEFT)};
            for(I_DataTableColumn o: list){
                int cf = colsmap.containsKey(o.getColid()) ? colsmap.get(o.getColid()) : 0;
                String radio = "<input type=radio name=radio"+o.getColid()+" value=0 "+(cf==0?"checked":"")+" onclick=changeFlag("+o.getColid()+",0) id=r0"+o.getColid()+"><label for=r0"+o.getColid()+">隐藏</label>  "+
                               "<input type=radio name=radio"+o.getColid()+" value=1 "+(cf==1?"checked":"")+" onclick=changeFlag("+o.getColid()+",1) id=r1"+o.getColid()+"><label for=r1"+o.getColid()+">仅显示</label>  "+
                               "<input type=radio name=radio"+o.getColid()+" value=2 "+(cf==2?"checked":"")+" onclick=changeFlag("+o.getColid()+",2) id=r2"+o.getColid()+"><label for=r2"+o.getColid()+">可编辑</label>";
                
                objs[i++] = new Object[]{"",
                                         ""+(i-2),
                                         o.getColMemo(),
                                         radio};
            }
            
        }
        this.setResultList(objs);
        return BConstants.LIST;
    }
    
    @SuppressWarnings("unchecked")
    public String query(){
        //this.addOptionList("<a onclick=conditions('%s','%s','%s')>条件设置</a>");
        this.addOptionList(BConstants.option_edit_string);
        this.addOptionList(BConstants.option_del_string);
        setResult_List(getList(I_ProcessDesc.class, dbQuery("SP_I_ProcessDescQuery", new Object[]{ao.getPsid()})));
        logger(ComBeanLogType.TYPE_QUERY, "查询流程明细");
        return BConstants.LIST;
    }

    public String add(){
        Object[] params = params("add");
        if(dbExe_p("SP_I_ProcessDescManager", params)){
            logger(ComBeanLogType.TYPE_ADD, "添加过程", params);
        }
        return BConstants.SUCCESS;
    }
    
    public String edit(){
        Object[] params = params("edit");
        if(dbExe_p("SP_I_ProcessDescManager", params)){
            logger(ComBeanLogType.TYPE_EDIT, "编辑过程", params);
        }
        return BConstants.SUCCESS;
    }
    
    private Object[] params(String mark){
        if(actors!=null) ao.setProcessActor(StringUtils.join(actors, ","));
        if(actorsDepartment!=null) ao.setProcessActorDepartment(StringUtils.join(actorsDepartment, ","));
        if(actorsRole!=null) ao.setProcessActorRole(StringUtils.join(actorsRole, ","));
        if(attachFilePower!=null){
            int tmp = 0;
            for(String s: attachFilePower) tmp += Integer.parseInt(s);
            ao.setAttachFilePower(tmp+"");
        }
        return new Object[]{mark, ao.getCcid(), ao.getPsid(), ao.getSeq(), ao.getProcessTitle(), ao.getProcessDesc(), ao.getNextSeq(), ao.getConditions(), ao.getProcessCols(), ao.getProcessActorMode(), ao.getProcessActor(), ao.getProcessActorDepartment(), ao.getProcessActorRole(), ao.getActorSelectRule(), ao.getActorAutoSelectRule(), ao.getActorAutoSelectRuleMemo(), ao.getAttachFilePower(), ao.getAttachFileEditFlag(), ao.getState()};
    }

    public String copy(){
        Object[] params = new Object[]{"copy", ao.getCcid(), ao.getProcessTitle()};
        if(dbExe_p("SP_I_ProcessDescManager", params)){
            logger(ComBeanLogType.TYPE_DEL, "复制过程", params);
        }
        return BConstants.MESSAGE;
    }
    
    public String del(){
        Object[] params = new Object[]{"del", ao.getCcid(), ao.getProcessTitle()};
        if(dbExe_p("SP_I_ProcessDescManager", params)){
            logger(ComBeanLogType.TYPE_DEL, "删除流程", params);
        }
        return BConstants.MESSAGE;
    }
    
    private void setResult_List(List<I_ProcessDesc> list) {
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn(I_ProcessDesc.seq_desc, "LastCCID", TColumn.ALIGN_LEFT),
                                     new TColumn(I_ProcessDesc.processTitle_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_ProcessDesc.nextSeq_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_ProcessDesc.state_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn("操作", null, TColumn.ALIGN_LEFT)};
            for(I_ProcessDesc o: list){
                objs[i++] = new Object[]{"doFocus('"+o.getCcid()+"','"+o.getPsid()+"','"+o.getSeq()+"','"+o.getProcessTitle()+"','"+o.getProcessDesc()+"','"+o.getNextSeq()+"','"+o.getConditions()+"','"+o.getProcessCols()+"','"+o.getProcessActorMode()+"','"+o.getProcessActor()+"','"+o.getProcessActorDepartment()+"','"+o.getProcessActorRole()+"','"+o.getActorSelectRule()+"','"+o.getActorAutoSelectRule()+"','"+o.getAttachFilePower()+"','"+o.getAttachFileEditFlag()+"','"+o.getState()+"');",
                                         o.getSeq(),
                                         o.getProcessTitle(),
                                         o.getNextSeq().replace("-1", "结束"),
                                         ComBeanState.getText(o.getState()),
                                         this.getOptionHtmlString(o.getCcid(), o.getProcessTitle(), "")};
            }
            
        }
        this.setResultList(objs);
    }


    /**
     * 
     */
    private static final long serialVersionUID = 3940692024165352924L;

    private String[] actors;
    private String[] actorsDepartment;
    private String[] actorsRole;
    private String[] attachFilePower;
    private String condition_whc1 = "";
    private String condition_whc2 = "";
    private String condition_whc3 = "";

    private I_Process po = new I_Process();
    private I_ProcessDesc ao = new I_ProcessDesc();

    public List<LabelValueBean> getStateList() {
        return ComBeanState.getList();
    }
    public List<LabelValueBean> getTableColList() {
        List<I_DataTableColumn> columns = ComBeanI_DataTable.get(this.po.getTableID()).getColumns();
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        for(I_DataTableColumn o: columns){
            list.add(new LabelValueBean(o.getColMemo(), o.getColid()+""));
        }
        return Collections.unmodifiableList(list);
    }
    public List<LabelValueBean> getProcessActorList() {
        return ComBeanIS_Emp.getList(po.getOrganID()+"", null, false);
    }
    public List<LabelValueBean> getProcessActorDepartmentList() {
        return ComBeanIS_Department.getList(po.getOrganID()+"", false);
    }
    public List<LabelValueBean> getProcessActorRoleList() {
        return ComBeanIS_JobPosition.getList(po.getOrganID()+"", false);
    }
    public List<LabelValueBean> getProcessActorModeList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_PROCESS_ACTOR_MODE, false, "");
    }
    public List<LabelValueBean> getActorSelectRuleList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_PROCESS_ACTOR_SELECT_RULE, false, "");
    }
    public List<LabelValueBean> getActorAutoSelectRuleList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_PROCESS_ACTOR_AUTOSELECT_RULE, false, "");
    }
    public List<LabelValueBean> getAttachFilePowerList(){
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(new LabelValueBean("新建", "10000"));
        list.add(new LabelValueBean("编辑", "1000"));
        list.add(new LabelValueBean("删除", "100"));
        list.add(new LabelValueBean("下载", "10"));
        list.add(new LabelValueBean("打印", "1"));
        return Collections.unmodifiableList(list);
    }
    public List<LabelValueBean> getAttachFileEditFlagList(){
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(new LabelValueBean("是", "1"));
        list.add(new LabelValueBean("否", "0"));
        return Collections.unmodifiableList(list);
    }
    public I_Process getPo() {
        return po;
    }

    public void setPo(I_Process po) {
        this.po = po;
    }

    public I_ProcessDesc getAo() {
        return ao;
    }

    public void setAo(I_ProcessDesc ao) {
        this.ao = ao;
    }
    
    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public String[] getActorsDepartment() {
        return actorsDepartment;
    }

    public void setActorsDepartment(String[] actorsDepartment) {
        this.actorsDepartment = actorsDepartment;
    }

    public String[] getActorsRole() {
        return actorsRole;
    }

    public void setActorsRole(String[] actorsRole) {
        this.actorsRole = actorsRole;
    }

    public String[] getAttachFilePower() {
        return attachFilePower;
    }

    public void setAttachFilePower(String[] attachFilePower) {
        this.attachFilePower = attachFilePower;
    }

    public String getCondition_whc1() {
        return condition_whc1;
    }

    public void setCondition_whc1(String condition_whc1) {
        this.condition_whc1 = condition_whc1;
    }

    public String getCondition_whc2() {
        return condition_whc2;
    }

    public void setCondition_whc2(String condition_whc2) {
        this.condition_whc2 = condition_whc2;
    }

    public String getCondition_whc3() {
        return condition_whc3;
    }

    public void setCondition_whc3(String condition_whc3) {
        this.condition_whc3 = condition_whc3;
    }
        
}
