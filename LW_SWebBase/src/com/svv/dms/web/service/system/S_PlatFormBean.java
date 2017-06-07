package com.svv.dms.web.service.system;

import java.util.List;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.entity.S_PlatForm;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.TColumn;

public class S_PlatFormBean extends AbstractBean {

    public String S_PlatForm(){
        return this.exeByCmd("");
    }
    
    public String S_PlatFormMonitor(){
        return this.exeByCmd("");
    }

    @SuppressWarnings("unchecked")
    public String query(){
        setResult_List(getList(S_PlatForm.class, dbQuery("SP_S_PlatFormQuery", null)));
        logger(ComBeanLogType.TYPE_QUERY, "查询平台状态");
        return BConstants.LIST;
    }
    
    public String queryS_PublishLog(){        
        setResult_S_PublishLogList(this.dbQuery("select * from S_PublishLog"));
        return BConstants.LIST;
    }
    
    public String add(){
        Object[] params = new Object[] {
                "add",
                this.platFormID,
                this.platFormName,
                this.status
                };
        
        if(dbExe("SP_S_PlatFormManager", params)) logger(ComBeanLogType.TYPE_ADD, "添加平台信息", params);
        
        return BConstants.SUCCESS;
    }
    
    public String edit(){
        Object[] params = new Object[] {
                "edit",
                this.platFormID,
                this.platFormName,
                new Long(this.status)
                };
        
        if(dbExe("SP_S_PlatFormManager", params)) logger(ComBeanLogType.TYPE_EDIT, "编辑平台信息", params);
        
        return BConstants.SUCCESS;
    }
    
    public String del(){
        Object[] params = new Object[] {
                "del",
                this.platFormID,
                this.platFormName,
                new Long(this.status)
                };
        
        if(dbExe("SP_S_PlatFormManager", params)) logger(ComBeanLogType.TYPE_DEL, "删除平台信息", params);
        
        return BConstants.SUCCESS;
    }
    
    public void setResult_List(List<S_PlatForm> list) {
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("ID"),
                                     new TColumn("接口名称"),
                                     new TColumn("状态"),
                                     new TColumn("更新间隔"),
                                     new TColumn("最后更新时间")};
            for(S_PlatForm o: list){
                String color = o.getStatus()==-1L ? "red" : (o.getStatus()==1L ? "yellow" : "black");
                objs[i++] = new Object[]{"doFocus('"+o.getPlatFormID()+"','"+o.getPlatFormName()+"','"+o.getStatus()+"');",
                                         o.getPlatFormID(),
                                         o.getPlatFormName(),                                         
                                         "<font color="+color+">"+o.getStatus()+"</font>", 
                                         o.getInterval(),
                                         o.getUptDate()};
            }
            
        }
        this.setResultList(objs);
    }
    
    public void setResult_S_PublishLogList(List<Object> list) {
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("版本号"),
                                     new TColumn("发布日期"),
                                     new TColumn("更新时间")};
            for(Object o: list){
                objs[i++] = new Object[]{"",
                                         DBUtil.getDBString(o, "publishNo"),
                                         DBUtil.getDBDateStr(o, "publishDate"),
                                         DBUtil.getDBDateStr(o, "istDate")};
            }
            
        }
        this.setResultList(objs);
    }
    

    /**
     * 
     */
    private static final long serialVersionUID = 6468922956685257278L;
    
    private String platFormID = "";
    private String platFormName = "";
    private String status = "0";
    
    public String getPlatFormID() {
        return platFormID;
    }

    public void setPlatFormID(String platFormID) {
        this.platFormID = platFormID;
    }

    public String getPlatFormName() {
        return platFormName;
    }

    public void setPlatFormName(String platFormName) {
        this.platFormName = platFormName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
