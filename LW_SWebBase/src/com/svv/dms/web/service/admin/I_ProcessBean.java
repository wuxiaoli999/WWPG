package com.svv.dms.web.service.admin;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanS_User;
import com.svv.dms.web.common.ComBeanState;
import com.svv.dms.web.common.ComBeanIS_Organ;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.I_Process;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.TColumn;

public class I_ProcessBean extends AbstractBean {

    public String I_Process(){
        return this.exeByCmd("");
    }

    public String formWord(){
        return "formWord";
    }
    public String ppic(){
        ao = (I_Process)getList(I_Process.class, dbQuery("SP_I_ProcessQueryByID", new Object[]{ ao.getPsid() })).get(0);
        return "ppic";
    }
    
    @SuppressWarnings("unchecked")
    public String query(){
        this.addOptionList("<input type=button class=button onclick=\"detail('%s','%s')\" value='流程设计'>");
        //this.addOptionList("<input type=button class=button onclick=\"ppic('%s','%s')\" value='流程图'>");
        //this.addOptionList("<a onclick=formWord('%s','%s','%s')>编辑模板</a>");
        this.addOptionList(BConstants.option_edit_string);
        this.addOptionList(BConstants.option_copy_string);
        this.addOptionList(BConstants.option_del_string);
        setResult_List(getList(I_Process.class, dbQuery(SQL.SP_I_ProcessQueryByC(c_organID, c_processType))));
        logger(ComBeanLogType.TYPE_QUERY, "查询流程");
        return BConstants.LIST;
    }

    public String add(){
        ao.setCreator(this.getUserSession().getUserID());
        Object[] params = new Object[]{"add", ao.getPsid(), ao.getOrganID(), ao.getProcessName(), ao.getProcessType(), ao.getTableID(), ao.getCreator(), ao.getState(), ao.getRemark()};
        if(dbExe_p("SP_I_ProcessManager", params)){
            logger(ComBeanLogType.TYPE_ADD, "添加流程", params);
        }
        return BConstants.SUCCESS;
    }
    
    public String edit(){
        Object[] params = new Object[]{"edit", ao.getPsid(), ao.getOrganID(), ao.getProcessName(), ao.getProcessType(), ao.getTableID(), ao.getCreator(), ao.getState(), ao.getRemark()};
        if(dbExe_p("SP_I_ProcessManager", params)){
            logger(ComBeanLogType.TYPE_EDIT, "编辑流程", params);
        }
        return BConstants.SUCCESS;
    }

    public String copy(){
        Object[] params = new Object[]{"copy", ao.getPsid(), ao.getProcessName()};
        if(dbExe_p("SP_I_ProcessManager", params)){
            logger(ComBeanLogType.TYPE_EDIT, "复制流程", params);
        }
        return BConstants.MESSAGE;
    }
    
    public String del(){
        Object[] params = new Object[]{"del", ao.getPsid(), ao.getProcessName()};
        if(dbExe_p("SP_I_ProcessManager", params)){
            logger(ComBeanLogType.TYPE_DEL, "删除流程", params);
        }
        return BConstants.MESSAGE;
    }
    
    private void setResult_List(List<I_Process> list) {        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn(I_Process.psid_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_Process.organID_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_Process.processName_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_Process.processType_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_Process.creator_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_Process.state_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_Process.uptDate_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn("操作", null, TColumn.ALIGN_LEFT)};
            for(I_Process o: list){
                objs[i++] = new Object[]{"doFocus('"+o.getPsid()+"','"+o.getOrganID()+"','"+o.getProcessName()+"','"+o.getProcessType()+"','"+o.getTableID()+"','"+o.getCreator()+"','"+HIUtil.toHtmlStr(o.getRemark())+"','"+o.getState()+"');",
                                         o.getPsid(),
                                         ComBeanIS_Organ.getText(o.getOrganID()),
                                         o.getProcessName(),
                                         ComBeanI_SystemParam.getText(ParamClass.CLASS_PROCESS_TYPE, o.getProcessType()),
                                         ComBeanS_User.getText(o.getCreator()),
                                         ComBeanState.getText(o.getState()),
                                         o.getUptDate(),
                                         this.getOptionHtmlString(o.getPsid(), o.getProcessName(), "")};
            }            
        }
        this.setResultList(objs);
    }


    /**
     * 
     */
    private static final long serialVersionUID = 3940692024165352924L;

    private String c_organID = "";
    private String c_processType = "";

    private I_Process ao = new I_Process();

    public List<LabelValueBean> getOrganList() {
        return ComBeanIS_Organ.getList(false, this.getUserSession().getOrganID());
    }

    public List<LabelValueBean> getC_processTypeList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_PROCESS_TYPE, true, "");
    }
    public List<LabelValueBean> getProcessTypeList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_PROCESS_TYPE, false, "");
    }

    public List<LabelValueBean> getStateList() {
        return ComBeanState.getList();
    }
    
    public I_Process getAo() {
        return ao;
    }

    public void setAo(I_Process ao) {
        this.ao = ao;
    }
    
    public String getC_organID() {
        return c_organID;
    }

    public void setC_organID(String cOrganID) {
        c_organID = cOrganID;
    }

    public String getC_processType() {
        return c_processType;
    }

    public void setC_processType(String cProcessType) {
        c_processType = cProcessType;
    }
    
}
