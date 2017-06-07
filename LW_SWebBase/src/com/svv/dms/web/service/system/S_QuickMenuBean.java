package com.svv.dms.web.service.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.entity.S_Module;
import com.svv.dms.web.entity.S_QuickMenu;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.TColumn;

public class S_QuickMenuBean extends AbstractBean {


    public String S_QuickMenu(){
        return this.exeByCmd("");
    }
    @SuppressWarnings("unchecked")
    public String query(){
        this.addOptionList(BConstants.option_del_string);
        setResult_List(getList(S_QuickMenu.class, dbQuery("SP_S_QuickMenuQuery", new Object[]{this.getUserSession().getUserID()})));
        logger(ComBeanLogType.TYPE_QUERY, "查询快捷菜单");
        return BConstants.LIST;
    }
    public String add(){
        qm.setUserID(this.getUserSession().getUserID());
        Object[] params = new Object[] {"add", -1, qm.getUserID(), qm.getModuleID(), qm.getSeq()};
        if(dbExe_p("SP_S_QuickMenuManager", params)) logger(ComBeanLogType.TYPE_ADD, "添加快捷菜单", params);
        return BConstants.SUCCESS;
    }
    
    public String edit(){
        qm.setUserID(this.getUserSession().getUserID());
        Object[] params = new Object[] {"edit", qm.getId(), qm.getUserID(), qm.getModuleID(), qm.getSeq()};
        if(dbExe_p("SP_S_QuickMenuManager", params)) logger(ComBeanLogType.TYPE_EDIT, "编辑快捷菜单", params);        
        return BConstants.SUCCESS;
    }
    
    public String del(){
        Object[] params = new Object[] {"del", qm.getId(), qm.getModuleName()};
        if(dbExe_p("SP_S_QuickMenuManager", params)) logger(ComBeanLogType.TYPE_DEL, "删除快捷菜单", params);        
        return BConstants.MESSAGE;
    }
    
    public void setResult_List(List<S_QuickMenu> list) {
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("序号"),
                                     new TColumn("菜单名称"),
                                     new TColumn("更新时间"),
                                     new TColumn("操作")};
            for(S_QuickMenu o: list){
                objs[i++] = new Object[]{"",
                                         o.getSeq(),
                                         o.getModuleName(),
                                         o.getUptDate(),
                                         this.getOptionHtmlString(o.getId(), o.getModuleName(), "")};
            }
            
        }
        this.setResultList(objs);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private S_QuickMenu qm = new S_QuickMenu();
    
    public List<LabelValueBean> getModuleList(){
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        List<S_Module> list = this.getUserSession().getMyMenuModules();
        for(S_Module o: list){
            if (!HIUtil.isEmpty(o.getUrl())) rtn.add(new LabelValueBean(o.getModuleName(), o.getModuleID()));
        }
        return rtn;
    }
    public S_QuickMenu getQm() {
        return qm;
    }
    public void setQm(S_QuickMenu qm) {
        this.qm = qm;
    }
    

}
