package com.svv.dms.web.service.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanIS_Emp;
import com.svv.dms.web.common.ComBeanIS_Organ;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.IB_Process;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.entity.I_Process;
import com.svv.dms.web.entity.I_ProcessDesc;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.TColumn;

public class IB_ProcessBean extends B_DataBean {

    public String IB_Process(){
        return this.exeByCmd("");
    }
    public String IB_ProcessNew(){
        return this.exeByCmd("");
    }
    public String IB_ProcessWait(){
        return this.exeByCmd("");
    }
    
    @SuppressWarnings("rawtypes")
    public String detail(){
        List list = getList(IB_Process.class, dbQuery("SP_IB_ProcessQueryByID", new Object[]{bo.getProcessID()}));
        if(list!=null && list.size()>0){
            bo = (IB_Process)list.get(0);
            ao.setDataid(bo.getDataid());
            loadByID();
            if(row!=null){
                setFormHtml(row, false);
            }
            if(!HIUtil.isEmpty(ao.getTable().getChildTableIDs())){
                this.childTableID = Integer.parseInt(ao.getTable().getChildTableIDs().split(",")[0]);
                ao.setTableID(childTableID);
                ao.setParentDataid(ao.getDataid()+"");
            }
            return BConstants.DETAIL;
        }
        this.setMessage(false, "流程不存在，请联系系统管理员！");
        return BConstants.MESSAGE_PAGE;
    }
    
    public String control(){
        detail();
        control = 1;
        return BConstants.DETAIL;
    }
    
    @SuppressWarnings({"rawtypes"})
    public String queryDetail(){
        List list = dbQuery("SP_IB_ProcessDetailQuery", new Object[]{bo.getProcessID()});
        logger(ComBeanLogType.TYPE_QUERY, "查询流程处理明细");

        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("序号", null, TColumn.ALIGN_LEFT),
                                     new TColumn(IB_Process.processStatus_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(IB_Process.processActor_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(IB_Process.processActResult_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(IB_Process.processActMemo_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(IB_Process.uptDate_desc, null, TColumn.ALIGN_LEFT)};
            for(Object o: list){
                objs[i++] = new Object[]{"",
                                         (i-2),
                                         DBUtil.getDBString(o, "status"),
                                         ComBeanIS_Emp.getText(DBUtil.getDBLong(o, "actor")),
                                         getProcessActResultStr(DBUtil.getDBInt(o, "actResult")),
                                         DBUtil.getDBString(o, "actMemo"),
                                         DBUtil.getDBDateStr(o, "uptDate")};
            }
        }
        this.setResultList(objs);
        return BConstants.LIST;
    }
    
    @SuppressWarnings("unchecked")
    public String queryWait(){
        this.addOptionList("<input type=button class=button value=处理 onclick=deal(this,'%s','%s','%s')>");
        setResult_List(getList(IB_Process.class, dbQuery(SQL.SP_IB_ProcessQueryByC(c_organID, this.getUserSession().getEmpID()+"", "", "2"))));
        logger(ComBeanLogType.TYPE_QUERY, "查询待办事项");
        return BConstants.LIST;
    }

    @SuppressWarnings("unchecked")
    public String queryMy(){
        this.addOptionList("<input type=button value=查看 onclick=detail(this,'%s','%s','%s')>");
        setResult_List(getList(IB_Process.class, dbQuery(SQL.SP_IB_ProcessQueryByC(c_organID, "", this.getUserSession().getEmpID()+"", ""))));
        logger(ComBeanLogType.TYPE_QUERY, "查询我提交的事项");
        return BConstants.LIST;
    }

    @SuppressWarnings("unchecked")
    public String queryPS(){
        List<I_Process> list = getList(I_Process.class, dbQuery(SQL.SP_I_ProcessQueryByC(this.getUserSession().getOrganID()<=0?"":this.getUserSession().getOrganID()+"", c_processType)));
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[(int)(Math.ceil((list.size()+1)/2))+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("<b>请选择工作流程</b>", null, TColumn.ALIGN_CENTER),
                                     new TColumn("", null, TColumn.ALIGN_CENTER)
                                     };
            for(int j=0;j<list.size();j=j+2){
                String o1 = "";
                String o2 = "";
                I_Process o = list.get(j);
                o1 = "<div style=\"height:20px;padding-top:8px;\"><a href=# onclick=\"doFocus('"+o.getPsid()+"','"+o.getOrganID()+"','"+o.getProcessName()+"','"+o.getProcessType()+"','"+o.getTableID()+"');\">"+o.getProcessName()+"</a></div>";
                if(j+1 < list.size()){
                    o = list.get(j+1);
                    o2 = "<div style=\"height:20px;padding-top:8px;\"><a href=# onclick=\"doFocus('"+o.getPsid()+"','"+o.getOrganID()+"','"+o.getProcessName()+"','"+o.getProcessType()+"','"+o.getTableID()+"');\">"+o.getProcessName()+"</a></div>";
                }
                objs[i++] = new Object[]{"",
                                         o1,
                                         o2,
                                         };
            }
            
        }
        this.setResultList(objs);
        logger(ComBeanLogType.TYPE_QUERY, "查询流程列表");
        return BConstants.LIST;
    }

    public String addPage(){
        return htmlPage(1, "addPage");
    }

    @SuppressWarnings("rawtypes")
    public String editPage(){
        List list = getList(IB_Process.class, dbQuery("SP_IB_ProcessQueryByID", new Object[]{bo.getProcessID()}));
        if(list!=null && list.size()>0){
            bo = (IB_Process)list.get(0);
            po.setPsid(bo.getPsid());
            ao.setDataid(bo.getDataid());
            
            if(!HIUtil.isEmpty(ao.getTable().getChildTableIDs())){
                this.childTableID = Integer.parseInt(ao.getTable().getChildTableIDs().split(",")[0]);
            }
            loadByID();
            return htmlPage(bo.getCurSeq(), "editPage");
        }
        this.setMessage(false, "流程不存在，请联系系统管理员！");
        return BConstants.MESSAGE_PAGE;
    }
    
    @SuppressWarnings("unchecked")
    public String query(){
        this.addOptionList("<input type=button class=button value=管理 onclick=control(this,'%s','%s','%s')>");
        this.addOptionList("<input type=button value=查看 onclick=detail(this,'%s','%s','%s')>");
        this.addOptionList(BConstants.option_del_string);
        setResult_List(getList(IB_Process.class, dbQuery("SP_IB_ProcessQuery", new Object[]{c_organID})));
        logger(ComBeanLogType.TYPE_QUERY, "查询工作流程");
        return BConstants.LIST;
    }
    
    public String add(){
        if(nextFlag==1) closePageFlag = false;
        addData();
        if(this.getResult()){
            bo.setProcessTitle(this.getUserSession().getUserName()+":"+po.getProcessName());
            bo.setOrganID(po.getOrganID());
            bo.setPsid(po.getPsid());
            bo.setTableID(ao.getTableID());
            bo.setDataid(ao.getDataid());
            bo.setCreator(this.getUserSession().getEmpID());
            bo.setDealFlag(nextFlag==1 ? -1 : 2);
            bo.setProcessActor(this.getUserSession().getEmpID());
            bo.setProcessActResult(-1);
            bo.setProcessActMemo("");
            int curSeq = nextFlag==1 ? bo.getProcessSeq() : bo.getCurSeq();
            String curActor = nextFlag==1 ? bo.getProcessActor()+"" : bo.getCurActor();
            String curStatus = nextFlag==1 ? bo.getProcessStatus() : bo.getCurStatus();
            
            Object[] params = new Object[]{"add", bo.getProcessID(), bo.getProcessTitle(), bo.getOrganID(), bo.getPsid(), bo.getTableID(), bo.getDataid(), bo.getCreator(), curSeq, curActor, curStatus, bo.getDealFlag(), bo.getState(), bo.getRemark(), bo.getProcessSeq(), bo.getProcessActor(), bo.getProcessStatus(), bo.getProcessActResult(), bo.getProcessActMemo()};
            if(dbExe_p("SP_IB_ProcessManager", params)){

                String message = getMessage();
                resetMessage("已提交。");
                String id = "";
                if(bo.getProcessID() ==-1 && message.indexOf("id=") > 0) id = message.substring(message.indexOf("id=")+3);
                loggerB(ComBeanLogType.TYPE_ADD, "添加工作流程："+bo.getProcessTitle()+"(id="+id+")", bo.getPsid()+"", id, params);
                
                if(nextFlag==1){
                    bo.setProcessID(Long.parseLong(id));
                    detail();
                    return "addNext";
                }
            }
        }
        return BConstants.MESSAGE_PAGE;
    }
    public String addCommit(){
        if(dbExe("update IB_Process set dealFlag=2,curSeq="+bo.getCurSeq()+",curActor='"+bo.getCurActor()+"',curStatus='"+bo.getCurStatus()+"' where processID="+bo.getProcessID())){
            if(closePageFlag) this.setScript("if(parent.setReturn){parent.setReturn('已提交.');window.close();}"); 
            loggerB(ComBeanLogType.TYPE_EDIT, "添加工作流程明细："+bo.getProcessTitle()+"(id="+bo.getProcessID()+")", bo.getPsid()+"", bo.getProcessID()+"", null);
        }
        return BConstants.MESSAGE_PAGE;
    }
    public String controlCommit(){
        if(dbExe("update IB_Process set dealFlag=2,curSeq="+bo.getCurSeq()+",curActor='"+bo.getCurActor()+"',curStatus='"+bo.getCurStatus()+"' where processID="+bo.getProcessID())){
            if(closePageFlag) this.setScript("if(parent.setReturn){parent.setReturn('提交成功.');window.close();}"); 
            loggerB(ComBeanLogType.TYPE_EDIT, "控制工作流程："+bo.getProcessTitle()+"(id="+bo.getProcessID()+")", bo.getPsid()+"", bo.getProcessID()+"", null);
        }
        return BConstants.MESSAGE_PAGE;
    }
    public String edit(){
        if(nextFlag==1) closePageFlag = false;
        addData();
        if(this.getResult()){
            bo.setDealFlag(2);
            bo.setProcessActor(this.getUserSession().getEmpID());
            if(bo.getProcessSeq()==1) bo.setProcessActResult(0);
            
            Object[] params = new Object[]{"edit", bo.getProcessID(), bo.getProcessTitle(), bo.getOrganID(), bo.getPsid(), bo.getTableID(), bo.getDataid(), bo.getCreator(), bo.getCurSeq(), bo.getCurActor(), bo.getCurStatus(), bo.getDealFlag(), bo.getState(), bo.getRemark(), bo.getProcessSeq(), bo.getProcessActor(), bo.getProcessStatus(), bo.getProcessActResult(), bo.getProcessActMemo()};
            if(dbExe_p("SP_IB_ProcessManager", params)){
                resetMessage("已提交.");
                String id = bo.getProcessID()+"";
                loggerB(ComBeanLogType.TYPE_EDIT, "处理工作流程："+bo.getProcessTitle()+"(id="+id+")", bo.getPsid()+"", id, params);
            }
        }
        return BConstants.MESSAGE_PAGE;
    }
    //退回
    public String back(){
        bo.setDealFlag(-1);
        bo.setCurActor(bo.getCreator()+"");
        bo.setCurSeq(1);
        bo.setCurStatus("退回编辑");
        bo.setProcessActor(this.getUserSession().getEmpID());
        
        Object[] params = new Object[]{"edit", bo.getProcessID(), bo.getProcessTitle(), bo.getOrganID(), bo.getPsid(), bo.getTableID(), bo.getDataid(), bo.getCreator(), bo.getCurSeq(), bo.getCurActor(), bo.getCurStatus(), bo.getDealFlag(), bo.getState(), bo.getRemark(), bo.getProcessSeq(), bo.getProcessActor(), bo.getProcessStatus(), bo.getProcessActResult(), bo.getProcessActMemo()};
        if(dbExe_p("SP_IB_ProcessManager", params)){
            if(closePageFlag) this.setScript("if(parent.setReturn){parent.setReturn('退回工作流程成功.');window.close();}"); 
            resetMessage("已退回.");
            String id = bo.getProcessID()+"";
            loggerB(ComBeanLogType.TYPE_EDIT, "退回工作流程："+bo.getProcessTitle()+"(id="+id+")", bo.getPsid()+"", id, params);
        }
        return BConstants.MESSAGE_PAGE;
    }
    
    public String del(){
        Object[] params = new Object[]{"del", bo.getProcessID(), bo.getProcessTitle()};
        if(dbExe_p("SP_IB_ProcessManager", params)){
            logger(ComBeanLogType.TYPE_DEL, "删除工作流程", params);
        }
        return BConstants.MESSAGE;
    }
    
    @SuppressWarnings("rawtypes")
    private String htmlPage(int curSeq, String toPage){
        if(curSeq==1 && ao.getTable().getChildTableNum()>0) this.nextFlag = 1;
        po = (I_Process)getList(I_Process.class, dbQuery("SP_I_ProcessQueryByID", new Object[]{ po.getPsid() })).get(0);
        I_ProcessDesc co = null;
        I_ProcessDesc co_2 = null;
        List pdlist = getList(I_ProcessDesc.class, dbQuery("SP_I_ProcessDescQueryBySeq", new Object[]{ po.getPsid(), curSeq }));
        if(pdlist!=null && pdlist.size()>0){
            co = (I_ProcessDesc)pdlist.get(0);
            bo.setProcessSeq(co.getSeq());
            bo.setProcessStatus(co.getProcessTitle());
            
            //判断下一步骤
            if(co.getNextSeq().split(",").length>1){
                bo.setCurSeq(Integer.parseInt(co.getNextSeq().split(",")[0]));
                ///////////////////////////////////////////
                ////////////////////////////
                
                //////////////
                
            }else{
                bo.setCurSeq(Integer.parseInt(co.getNextSeq()));
            }
            if(bo.getCurSeq() == -1){
                
            }else{
                pdlist = getList(I_ProcessDesc.class, dbQuery("SP_I_ProcessDescQueryBySeq", new Object[]{ po.getPsid(), bo.getCurSeq()}));
                if(pdlist!=null && pdlist.size()>0){
                    co_2 = (I_ProcessDesc)pdlist.get(0);
                }else{
                    this.setMessage(false, "该流程没有配置步骤，请联系系统管理员！");
                    return BConstants.MESSAGE_PAGE;
                }
            }
            bo.setCurStatus(co_2==null?"结束":"等待"+co_2.getProcessTitle());
        }else{
            this.setMessage(false, "该流程没有配置步骤，请联系系统管理员！");
            return BConstants.MESSAGE_PAGE;
        }
        List<I_DataTableColumn> collist = getColumnList(null);
        
        HashMap<Long, Integer> colsmap = new HashMap<Long, Integer>();
        if(co!=null && co.getProcessCols().length()>0){
            String[] cols = co.getProcessCols().substring(1).split(",");            
            for(String c: cols){
                String[] cc = c.split(":");
                colsmap.put(Long.parseLong(cc[0]), Integer.parseInt(cc[1]));
            }
        }
        
        StringBuilder sa = new StringBuilder("");
        //经手人
        if(co_2!=null){
            List list1 = null;
            TreeMap<Long, String> actor_map = new TreeMap<Long, String>();            

            if(co_2.getActorAutoSelectRule() == 103001){//不进行自动选择
                
                switch(co_2.getActorSelectRule()){
                    case 102001://允许选择全部指定的经办人
                        if(co_2.getProcessActor().length()>0){
                            for(String s: co_2.getProcessActor().split(",")){
                                long empid = Long.parseLong(s);
                                actor_map.put(empid, ComBeanIS_Emp.getText(empid));
                            }
                        }else if(co_2.getProcessActorDepartment().length()>0){
                            for(String s: co_2.getProcessActorDepartment().split(",")){
                                actor_map.putAll(ComBeanIS_Emp.getList(po.getOrganID()+"", s));
                            }
                        }else if(co_2.getProcessActorRole().length()>0){
                            for(String s: co_2.getProcessActorRole().split(",")){
                                actor_map.putAll(ComBeanIS_Emp.getList(po.getOrganID()+"", Integer.parseInt(s)));
                            }
                        }else{
                            actor_map = ComBeanIS_Emp.getList(po.getOrganID()+"");
                        }
                        break;
                    case 102002://只允许选择本部门经办人
                        actor_map = ComBeanIS_Emp.getList(po.getOrganID()+"", this.getUserSession().getDepartmentID()+"");
                        break;
                    case 102003://只允许选择上级部门经办人
                        list1 = dbQuery("select c7 jobID from IS_JobPosition where dataid="+this.getUserSession().getJobID());
                        if(list1!=null && list1.size()>0){
                            actor_map = ComBeanIS_Emp.getList(po.getOrganID()+"", DBUtil.getDBInt(list1.get(0), "jobID"));
                        }                    
                        break;
                    case 102004://只允许选择下级部门经办人
                        list1 = dbQuery("select dataid from IS_Department where c6="+this.getUserSession().getJobID());
                        if(list1!=null && list1.size()>0){
                            for(Object o: list1){
                                actor_map.putAll(ComBeanIS_Emp.getList(po.getOrganID()+"", DBUtil.getDBString(o, "dataid")));
                            }
                        }                    
                        break;
                    case 102005://只允许选择本角色经办人
                        actor_map = ComBeanIS_Emp.getList(po.getOrganID()+"", this.getUserSession().getJobID());
                        break;
                }
                
            }else{    
                switch(co_2.getActorAutoSelectRule()){
                    case 103001://不进行自动选择
                        break;
                    case 103002://自动选择流程发起人
                        actor_map.put(bo.getCreator(), ComBeanIS_Emp.getText(bo.getCreator()));
                        break;
                    case 103003://自动选择本部门主管
                        list1 = dbQuery("select c7 jobID from IS_JobPosition where dataid="+this.getUserSession().getJobID());
                        if(list1!=null && list1.size()>0 && DBUtil.getDBString(list1.get(0), "jobID").length()>0){
                            actor_map = ComBeanIS_Emp.getList(po.getOrganID()+"", DBUtil.getDBInt(list1.get(0), "jobID"));
                        }
                        break;
                    case 103004://自动选择上级主管领导
                        if(this.getUserSession().getDepartmentID()>0){
                            list1 = dbQuery("select c6 jobID from IS_Department where dataid="+this.getUserSession().getDepartmentID());
                        }else{
                            list1 = dbQuery("select c7 jobID from IS_JobPosition where dataid="+this.getUserSession().getJobID());
                        }
                        if(list1!=null && list1.size()>0){
                            actor_map = ComBeanIS_Emp.getList(po.getOrganID()+"", DBUtil.getDBInt(list1.get(0), "jobID"));
                        }
                        break;
                    case 103005://自动选择董事长（一级部门主管）
                        list1 = dbQuery("select dataid jobID from IS_JobPosition where c2="+po.getOrganID()+" and c8=1");
                        if(list1!=null && list1.size()>0){
                            actor_map = ComBeanIS_Emp.getList(po.getOrganID()+"", DBUtil.getDBInt(list1.get(0), "jobID"));
                        }
                        break;
                    case 103006://自动选择总经理（二级部门主管）
                        list1 = dbQuery("select dataid jobID from IS_JobPosition where c2="+po.getOrganID()+" and c8=2");
                        if(list1!=null && list1.size()>0){
                            actor_map = ComBeanIS_Emp.getList(po.getOrganID()+"", DBUtil.getDBInt(list1.get(0), "jobID"));
                        }
                        break;
                    case 103007://自动选择指定步骤经办人
                        if(co_2.getProcessActor().length()>0){
                            long empid = Long.parseLong(co_2.getProcessActor().split(",")[0]);
                            actor_map.put(empid, ComBeanIS_Emp.getText(empid));
                        }
                        break;
                }
            }
            if(actor_map==null || actor_map.size()==0){
                list1 = dbQuery("select dataid jobID,c8 from IS_JobPosition where c2="+po.getOrganID()+" and c8=1 union select dataid jobID,c8 from Y_JobPosition where c2="+po.getOrganID()+" and c8=2 order by c8 desc");
                if(list1!=null && list1.size()>0){
                    actor_map = ComBeanIS_Emp.getList(po.getOrganID()+"", DBUtil.getDBInt(list1.get(0), "jobID"));
                }
            }
            
            if(actor_map.size()>0){
                sa.append("<tr").append(actor_map.size()>-999999991?"":" style=\"display:none;\"").append("><td class=tdHeaderR>下一步【").append(co_2.getProcessTitle()).append("】，选择经手人</td><td class=tdBody>");
                
                switch(co_2.getProcessActorMode()){
                    case 101001://一人名单一人处理
                        sa.append("<select name=bo.curActor style=\"width:130px;\">");
                        break;
                    case 101002://多人名单一人处理
                        sa.append("<select name=bo.curActor multiple=multiple style=\"width:130px;\">");
                        break;
                    case 101003://多人名单多人并行处理
                        sa.append("<select name=bo.curActor multiple=multiple style=\"width:130px;\">");
                        break;
                }
                for(Long key: actor_map.keySet()){
                    sa.append("<option value=").append(key).append(">").append(actor_map.get(key)).append("</option>");
                }
                sa.append("</select></td></tr>");
            }
        }
        
        StringBuilder s = new StringBuilder("");
        StringBuilder js = new StringBuilder("");
        StringBuilder ins = new StringBuilder("");
        if(collist!=null && collist.size()>0){
            for(I_DataTableColumn o: collist){
                
                int cf = colsmap.containsKey(o.getColid()) ? colsmap.get(o.getColid()) : 0;
                if(cf==0){
                    s.append("<tr style=\"display:none\">");                    
                }else{
                    s.append("<tr>");
                }
                s.append("<td class=tdHeaderR>");
                s.append(o.getColMemo());
                s.append("</td>");
                s.append("<td class=tdBody width=66%>");
                String colValue = "";
                if(row!=null) colValue = DBUtil.getDBString(row, o.getColName());
                String[] ss = initInputHTML(ao.getTable(), o, colValue, cf!=1, null);
                s.append(ss[0]);
                js.append(ss[1]);
                ins.append(ss[2]);
                s.append("</td>");
                s.append("</tr>");
            }
        }
        
        this.setAttribute(Constants.REQUEST_ATTRIBUTE_FORMHTML, s.toString()+sa.toString());
        this.setAttribute(Constants.REQUEST_ATTRIBUTE_FORMCHECKJS, js.toString());
        this.setAttribute(Constants.REQUEST_ATTRIBUTE_INITJS, ins.toString());
        return toPage;
    }

    private void setResult_List(List<IB_Process> list) {
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn(IB_Process.processID_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(IB_Process.creator_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(IB_Process.processTitle_desc, null, TColumn.ALIGN_LEFT),
                                     //new TColumn(IB_Process.curSeq_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(IB_Process.curActor_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(IB_Process.curStatus_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(IB_Process.istDate_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(IB_Process.uptDate_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn("操作", null, TColumn.ALIGN_LEFT),
                                     new TColumn("", null, TColumn.ALIGN_LEFT)};
            String btnStr = "";
            for(IB_Process o: list){
                if(o.getCurSeq()==1 && o.getCreator()==this.getUserSession().getEmpID()){
                    btnStr = "<input type=button class=button value=编辑 onclick=\"deal(this,'"+o.getProcessID()+"','"+o.getProcessTitle()+"','"+o.getTableID()+"');\">";
                }else{
                    btnStr = "<a href=# onclick=\"remind(this,'"+o.getProcessID()+"','"+o.getProcessTitle()+"','"+o.getTableID()+"');\">提醒</a>";
                }
                objs[i++] = new Object[]{"doFocus('"+o.getProcessID()+"','"+o.getProcessTitle()+"','"+o.getOrganID()+"','"+o.getPsid()+"','"+o.getTableID()+"','"+o.getDataid()+"','"+o.getCreator()+"','"+o.getCurActor()+"','"+o.getCurStatus()+"','"+o.getState()+"','"+o.getRemark()+"');",
                                         o.getProcessID(),
                                         ComBeanIS_Emp.getText(o.getCreator()),
                                         o.getProcessTitle(),
                                         //o.getCurSeq(),
                                         ComBeanIS_Emp.getText(o.getCurActor()),
                                         o.getCurStatus(),
                                         o.getIstDate(),
                                         o.getUptDate(),
                                         btnStr,
                                         this.getOptionHtmlString(o.getProcessID(),o.getProcessTitle(),o.getTableID())};
            }
        }
        this.setResultList(objs);
    }
    
    private String getProcessActResultStr(int actResult){
        switch(actResult){
            case 1:
                return "同意";
            case 2:
                return "拒绝";
            case 4:
                return "退回";
        }
        return "";
    }

    /**
     * 
     */
    private static final long serialVersionUID = -1408030293909923949L;

    private IB_Process bo = new IB_Process();
    private I_Process po = new I_Process();
    private String c_organID = "";
    private String c_processType = "";
    private int nextFlag = 0;
    private int childTableID = 0;
    private int control = 0;
    
    public List<LabelValueBean> getOrganList() {
        return ComBeanIS_Organ.getList(false, this.getUserSession().getOrganID());
    }

    public List<LabelValueBean> getC_processTypeList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_PROCESS_TYPE, true, "");
    }
    
    @SuppressWarnings("unchecked")
    public List<LabelValueBean> getControl_seqList(){
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        List<I_ProcessDesc> list = getList(I_ProcessDesc.class, dbQuery("SP_I_ProcessDescQuery", new Object[]{bo.getPsid()}));
        if(list!=null && list.size()>0){
            for(I_ProcessDesc o: list){
                rtn.add(new LabelValueBean(o.getProcessTitle(), o.getSeq()+""));
            }
        }
        return Collections.unmodifiableList(rtn);
    }
    public List<LabelValueBean> getProcessActorList() {
        return ComBeanIS_Emp.getList(bo.getOrganID()+"", null, false);
    }

    public String getC_organID() {
        return c_organID;
    }
    public void setC_organID(String cOrganID) {
        c_organID = cOrganID;
    }
    public IB_Process getBo() {
        return bo;
    }
    public void setBo(IB_Process bo) {
        this.bo = bo;
    }    
    public I_Process getPo() {
        return po;
    }

    public void setPo(I_Process po) {
        this.po = po;
    }

    public String getC_processType() {
        return c_processType;
    }

    public void setC_processType(String cProcessType) {
        c_processType = cProcessType;
    }
    public int getNextFlag() {
        return nextFlag;
    }
    public void setNextFlag(int nextFlag) {
        this.nextFlag = nextFlag;
    }
    public int getChildTableID() {
        return childTableID;
    }
    public void setChildTableID(int childTableID) {
        this.childTableID = childTableID;
    }
    public int getControl() {
        return control;
    }
    public void setControl(int control) {
        this.control = control;
    }
    
}
