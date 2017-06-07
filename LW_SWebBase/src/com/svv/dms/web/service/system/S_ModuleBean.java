package com.svv.dms.web.service.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanState;
import com.svv.dms.web.entity.S_Module;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.TColumn;

public class S_ModuleBean extends AbstractBean{
    public final static int BIZTYPE_BCQR_CTRL = 1;  //业务流程控制
    public final static int BIZTYPE_BCQR_QUERY = 2; //业务信息查看
    public final static int BIZTYPE_PROJECT_QUERY = 3; //项目信息查看

    public final static int ISMENU_MENU = 1;
    public final static int ISMENU_NOT_MENU = 0;
    public final static int ISMENU_BUTTON = 5;
    
	public String S_Module(){
        return this.exeByCmd("");
    }
    
    @SuppressWarnings("unchecked")
    public String query(){
    	if(isSuperAdmin()){
    		this.addOptionList(BConstants.option_edit_string);
        	this.addOptionList(BConstants.option_copy_string);
        	this.addOptionList(BConstants.option_del_string);
    	}
        
        setResult_List(getList(S_Module.class, dbQuery("SP_S_ModuleQuery", null)));
        logger(ComBeanLogType.TYPE_QUERY, "查询模块信息");
    	System.out.println("S_Module.getUserSession()==null 5? " + (this.getUserSession()==null?true:false));
        return BConstants.LIST;
    }

	/*
    public String query(){
        logger(ComBeanLogType.TYPE_QUERY, "查询模块信息");
        return this.getListHead("模块信息", SQL.SP_S_ModuleQuery);
    }
    protected List<I_DataTableColumn> getColumnList(String pageTab){
    	List<I_DataTableColumn> collist = new ArrayList<I_DataTableColumn>();
    	collist.add(new I_DataTableColumn("moduleID", S_Module.moduleID_desc, I_DataTableColumn.LISTKB_FROZEN, 0, true));
    	collist.add(new I_DataTableColumn("moduleName", S_Module.moduleName_desc, I_DataTableColumn.LISTKB_FROZEN, 1, 15));
    	collist.add(new I_DataTableColumn("state", S_Module.state_desc, I_DataTableColumn.LISTKB_SHOW, 1));
    	collist.add(new I_DataTableColumn("isMenu", S_Module.isMenu_desc, I_DataTableColumn.LISTKB_SHOW, 1));
    	collist.add(new I_DataTableColumn("parent", S_Module.parent_desc, I_DataTableColumn.LISTKB_SHOW, 1));
    	collist.add(new I_DataTableColumn("hasChild", S_Module.hasChild_desc, I_DataTableColumn.LISTKB_SHOW, 1));
    	collist.add(new I_DataTableColumn("url", S_Module.url_desc, I_DataTableColumn.LISTKB_SHOW, 1, 20));
    	collist.add(new I_DataTableColumn("img", S_Module.img_desc, I_DataTableColumn.LISTKB_SHOW, 1));
    	collist.add(new I_DataTableColumn("power", S_Module.power_desc, I_DataTableColumn.LISTKB_SHOW, 1));
    	collist.add(new I_DataTableColumn("remark", S_Module.remark_desc, I_DataTableColumn.LISTKB_SHOW, 1));
    	return collist;
    }
    */
	
	public String add(){
		if(ao.getUrl().indexOf("B_DataModule")>=0){
			ao.setPower("");
		}
        Object[] params = new Object[] { "add", ao.getS_moduleID(), ao.getModuleID(), ao.getModuleName(), ao.getState(), ao.getIsMenu(), ao.getParent(), ao.getHasChild(), ao.getUrl(), ao.getImg(), ao.getPower()};
        if(dbExe_p("SP_S_ModuleManager", params)) logger(ComBeanLogType.TYPE_ADD, "添加模块信息", params);
		return BConstants.SUCCESS;
	}
	
	public String edit(){
		if(ao.getUrl().indexOf("B_DataModule")>=0){
			ao.setPower("");
		}
        Object[] params = new Object[] { "edit", ao.getS_moduleID(), ao.getModuleID(), ao.getModuleName(), ao.getState(), ao.getIsMenu(), ao.getParent(), ao.getHasChild(), ao.getUrl(), ao.getImg(), ao.getPower()};
		if(dbExe_p("SP_S_ModuleManager", params)) logger(ComBeanLogType.TYPE_EDIT, "编辑模块信息", params);
		return BConstants.SUCCESS;
	}
    
    public String del(){
        Object[] params = new Object[] { "del", ao.getS_moduleID(), ao.getModuleID(), ao.getModuleName()};
        if(dbExe_p("SP_S_ModuleManager", params))   logger(ComBeanLogType.TYPE_DEL, "删除模块信息", params);
        return BConstants.SUCCESS;
    }
    
    public String copy(){
        Object[] params = new Object[] { "copy", ao.getS_moduleID(), ao.getModuleID(), ao.getModuleName()};
        if(dbExe_p("SP_S_ModuleManager", params))   logger(ComBeanLogType.TYPE_DEL, "复制模块信息", params);
        return BConstants.SUCCESS;
    }

    private void setResult_List(List<S_Module> list){
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn(S_Module.moduleID_desc, 80),
                                     new TColumn(S_Module.moduleName_desc, 140),
                                     new TColumn(S_Module.state_desc),
                                     new TColumn(S_Module.isMenu_desc),
                                     new TColumn(S_Module.hasChild_desc),
                                     new TColumn(S_Module.url_desc, 200),
                                     new TColumn(S_Module.img_desc),
                                     new TColumn(S_Module.power_desc),
                                     new TColumn("操作")};
            String otherBtn;
            for(S_Module o: list){
            	if(isSuperAdmin() && o.getUrl().indexOf("B_DataModule_")>0){
            		otherBtn = "<a href=# class=button onclick=\"dataModule(this,'"+o.getModuleID()+"','"+o.getModuleName()+"','')\">[配置表]</a>";
            	}else{
            		otherBtn = "";
            	}
                objs[i++] = new Object[]{"doFocus('"+o.getModuleID()+"','"+o.getModuleName()+"','"+o.getState()+"','"+o.getIsMenu()+"','"+o.getParent()+"','"+o.getHasChild()+"','"+o.getUrl()+"','"+o.getImg()+"','"+o.getPower()+"');",
                                         o.getModuleID(),
                                         ((o.getParent().length()==0 || o.getHasChild()==1)?"<b>":"") + o.getModuleName(),
                                         o.getState()==1?"有效":"",
                                         o.getIsMenu()==1?"是":"",
                                         o.getHasChild()==1?"有":"",
                                         o.getUrl(),
                                         o.getImg(),
                                         getModulePowerStr(o.getPower()),
                                         this.getOptionHtmlString(o.getModuleID(), o.getModuleName(), "") + otherBtn};
            }
            
        }
        this.setResultList(objs);
        
    }
    
    public static String getModulePowerStr(String power){
        String rtn = "";
        for(int i=0;i<power.length();i++){
            if(power.charAt(i)=='1') rtn += S_Module.POWER_ZH[i] + " ";
        }
        return rtn.trim();
    }

    private static final long serialVersionUID = 4842404997994632511L;
	
    private S_Module ao = new S_Module();
    
	public List<LabelValueBean> getStateList(){
    	return ComBeanState.getList();
    }
    
    public List<LabelValueBean> getIsmenuList(){
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(new LabelValueBean("是", "1"));
        list.add(new LabelValueBean("否", "0"));
        return Collections.unmodifiableList(list);
    }
	
    public List<LabelValueBean> getHaschildList(){
    	List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(new LabelValueBean("有", "1"));
        list.add(new LabelValueBean("无", "0"));
        list.add(new LabelValueBean("其他", "2"));
        return Collections.unmodifiableList(list);
    }

    public S_Module getAo() {
		return ao;
	}

	public void setAo(S_Module ao) {
		this.ao = ao;
	}

}
