package com.svv.dms.web.service.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanS_Role;
import com.svv.dms.web.common.ComBeanState;
import com.svv.dms.web.common.ComBeanIS_Organ;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.entity.S_Module;
import com.svv.dms.web.entity.S_Role;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.TColumn;

public class S_RoleBean extends AbstractBean{

    public String S_Role(){
        return this.exeByCmd("");
    }

    @SuppressWarnings("unchecked")
	public String query(){
        this.addOptionHtmlSting(BConstants.optionbar_add_string);
        this.addOptionHtmlSting(BConstants.optionbar_edit_string);
        this.addOptionHtmlSting(BConstants.optionbar_copy_string);
        this.addOptionHtmlSting(BConstants.optionbar_del_string);
        this.addOptionHtmlSting(BConstants.optionbar_detail_string);
		setResult_List(getList(S_Role.class, dbQuery("SP_S_RoleQueryByRole", new Object[]{getUserSession().getRoleID()+""})));		
		logger(ComBeanLogType.TYPE_QUERY, "查询角色信息");		
		return BConstants.LIST;
	}
	
	public String add(){
	    this.role.setOrganType(this.getParameter("role.organType", -1));
        Object[] params = new Object[]{"add", Constants.DEF_PARAMETET_LONG, role.getRoleName(), role.getRoleSeq(), role.getOrganType(), role.getSctLevel(), role.getState(), role.getPower(), role.getRemark()};
		if(dbExe_p("SP_S_RoleManager", params)){
		    logger(ComBeanLogType.TYPE_ADD, "添加角色信息", params);
		    this.setNextPage("角色列表", "system/S_Role.y");
		    return BConstants.MESSAGE_PAGE;
		}
		return BConstants.SUCCESS;
	}
	public String edit(){
        this.role.setOrganType(this.getParameter("role.organType", -1));
        Object[] params = new Object[]{"edit", role.getRoleID(), role.getRoleName(), role.getRoleSeq(), role.getOrganType(), role.getSctLevel(), role.getState(), role.getPower(), role.getRemark()};
		if(dbExe_p("SP_S_RoleManager", params)){
		    logger(ComBeanLogType.TYPE_EDIT, "编辑角色信息", params);
		}
		return BConstants.SUCCESS;
	}
    public String copy(){
        Object[] params = new Object[]{"copy", role.getRoleID(), role.getRoleName(), role.getRoleSeq()};
        if(dbExe_p("SP_S_RoleManager", params)){
            logger(ComBeanLogType.TYPE_ADD, "复制添加角色信息", params);
        }
        return BConstants.MESSAGE;
    }
    public String del(){
    	if(role.getRoleID()==200){
    		this.setMessage(false, "您无权删除该角色！sys=200");
            return BConstants.MESSAGE;
    	}
        this.role.setOrganType(this.getParameter("role.organType", -1));
        Object[] params = new Object[]{"del", role.getRoleID(), role.getRoleName(), role.getRoleSeq()};
        if(dbExe_p("SP_S_RoleManager", params)){
            logger(ComBeanLogType.TYPE_DEL, "删除角色信息", params);
            this.setNextPage("角色列表", "system/S_Role.y");
            return BConstants.MESSAGE_PAGE;
        }
        return BConstants.SUCCESS;
    }

    @SuppressWarnings("unchecked")
    public String init(){
        role.setRoleName(ComBeanS_Role.getText(role.getRoleID()));
        List<S_Module> list = getList(S_Module.class, dbQuery("SP_S_ModuleQueryByRole", 
                new Object[] { role.getRoleID(), Constants.DEF_PARAMETET_LONG }));
        if(list!=null && list.size()>0){
            myModules = new String[list.size()];
            myRolePowers = new String[list.size()];
            for(int i=0; i<list.size(); i++){
                myModules[i] = list.get(i).getModuleID();
                myRolePowers[i] = list.get(i).getRolePower();
            }
        }   
        
        logger(ComBeanLogType.TYPE_QUERY, "查询角色权限");
        return "set";
    }

    @SuppressWarnings("unchecked")
    public String getModules(){
        initMyModules();
        List<S_Module> list = null;
        if(this.getUserSession().isSuperAdmin()){
        	list = getList(S_Module.class, dbQuery("SP_S_ModuleQueryByState", null));
        }else{
        	list = getList(S_Module.class, dbQuery("SP_S_ModuleQueryByRole", 
                    new Object[] { this.getUserSession().getRoleID() }));
        }
        setResult_RoleModuleList(list);
        
        logger(ComBeanLogType.TYPE_QUERY, "查询角色权限");        
        return BConstants.LIST;
    }
    
    public String set(){
        
        if(myModules==null || myModules.length==0){
            this.setMessage(false, "请选择权限!");
            return "set";
        }
        String myModules_ = StringUtils.join(myModules, Constants.SPLITER)+Constants.SPLITER;
        String myRolePowers_ = StringUtils.join(myRolePowers, Constants.SPLITER)+Constants.SPLITER;
        
        Object[] params = new Object[]{role.getRoleID(), myModules_, myRolePowers_, Constants.SPLITER, new Long(myModules.length)};
        if (dbExe("SP_S_RoleSetModules", params))
            logger(ComBeanLogType.TYPE_DEL, "设置角色权限", params);
        
        return "set";
    }
    
    public void setResult_List(List<S_Role> list){
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("角色名称"),
                                     new TColumn("适用机构"),
                                     new TColumn("角色状态"),
                                     new TColumn("秘密级别"),
                                     new TColumn("排序"),
                                     new TColumn("设置权限"),
                                   };
            for(S_Role o: list){
                objs[i++] = new Object[]{"doFocus('"+o.getRoleID()+"','"+o.getRoleSeq()+"','"+o.getRoleName()+"','"+o.getOrganType()+"','"+o.getState()+"','"+o.getPower()+"');",
                                         "<span title=\"ID:"+o.getRoleID()+"\">"+o.getRoleName()+"</span>",
                                         o.getOrganType()==-1 ? "<不限>" : o.getOrganType()+"-"+(o.getOrganType()>ParamClass.CLASS_ORGAN_TYPE*1000? ComBeanI_SystemParam.getText(ParamClass.CLASS_ORGAN_TYPE, o.getOrganType()) : ComBeanIS_Organ.getText(o.getOrganType())),
                                         ComBeanState.getText(o.getState()),
                                         ComBeanI_SystemParam.getText(ParamClass.CLASS_SCT_LEVEL, o.getSctLevel()),
                                         this.isSuperAdmin()?o.getRoleSeq():(i-2),
                                         this.getUserSession().getRoleID()==o.getRoleID() ? "" : "<input type=button class=buttonLine value=设置权限 onclick=\"setModules("+o.getRoleID()+", '"+o.getRoleName()+"')\">",
                                         };
            }
            
        }
        this.setResultList(objs);       
        
    }
    
    private void setResult_RoleModuleList(List<S_Module> list){
        
        Object[] objs = null;
        if(list!=null && list.size()>0){

            List<Object[]> tmp_list = new ArrayList<Object[]>();
            String checked = "";
            String rolePower = "";
            String b = "";
            String memu = "";
            String tmp = null;
            String display = "";
            for(S_Module o: list){
                checked = "";
                rolePower = "";
                if(myModulesMap!=null && myModulesMap.containsKey(o.getModuleID())){
                    checked =  " checked ";
                    rolePower = myModulesMap.get(o.getModuleID());
                }

                if(o.getBizType()==0){
                    if(tmp != null){
                        tmp_list.add(new Object[]{"",
                                tmp,
                                display                                               
                                });
                        tmp = null;
                    }
                    display = "";
                    display += "<input type=hidden name=myRolePowers id=\"id_pw_"+o.getModuleID()+"\" value=\""+rolePower+"\" "+(checked.length()>0?"" : "disabled=disabled")+">";
                }else if(o.getBizType()==1){
                    display += "<input type=checkbox name=myModules id=\"id_"+o.getParent()+"\" value=\""+o.getModuleID()+"\" "+checked+" onclick=\"onSelector(this)\"> " + o.getModuleName();
                    continue;
                }else if(o.getBizType()==2){
                    display += "<input type=hidden name=myRolePowers id=\"id_pw_"+o.getModuleID()+"\" value=\""+o.getPower()+"\" "+(checked.length()>0?"" : "disabled=disabled")+">";
                    display += "<input type=checkbox name=myModules id=\"id_"+o.getParent()+"\" value=\""+o.getModuleID()+"\" "+checked+" style='display:none'>";
                    continue;
                }
                
                b = o.getParent().length()==0 ? "<b>" : "";
                memu = o.getIsMenu()!=1 ? " <font color=blue>< 非菜单 ></font> " : "";
                tmp = b + o.getModuleID() + StringUtils.repeat("&nbsp;", o.getModuleID().length()*3) + "<input type=checkbox class=checkbox name=myModules id=\"id_"+o.getParent()+"\" value=\""+o.getModuleID()+"\" "+checked+" onclick=\"onSelector(this)\"> " + o.getModuleName() + memu;
                
                if(o.getRolePower().length()>0){
                    for(int j=0;j<o.getRolePower().length();j++){
                        if(o.getRolePower().charAt(j)=='1'){
                            checked = rolePower.length()>j && rolePower.charAt(j)=='1' ? " checked " : "";
                            display += "<input type=checkbox name=chk_"+o.getModuleID()+" id=\"id_"+o.getModuleID()+"\" value=\""+j+"\" "+checked+" onclick=\"power(this,'"+o.getModuleID()+"', "+j+")\"> " + S_Module.POWER_ZH[j];
                        }
                    }
                }
            }
            if(tmp != null){                        
                tmp_list.add(new Object[]{"",
                        tmp,
                        display                                             
                        });
                tmp = null;
            }
            
            int i = 0;
            objs = new Object[tmp_list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("模块", 400),
                                     new TColumn("配置子功能")};
            for(Object[] o: tmp_list){
                objs[i++] = o;
            }
            
        }
        this.setResultList(objs);        
    }

    @SuppressWarnings("unchecked")
    private void initMyModules(){
        List<S_Module> list = getList(S_Module.class, dbQuery("SP_S_ModuleQueryByRole", 
                new Object[] { this.role.getRoleID(), Constants.DEF_PARAMETET_LONG }));
        if(list!=null && list.size()>0){
            myModulesMap = new HashMap<String, String>();
            myModules = new String[list.size()];
            myRolePowers = new String[list.size()];
            for(int i=0; i<list.size(); i++){
                myModules[i] = list.get(i).getModuleID();
                myRolePowers[i] = list.get(i).getRolePower();

                myModulesMap.put(myModules[i], myRolePowers[i]);
            }
        }   
    }
    
    private static final long serialVersionUID = -6242234627978844763L;

    private S_Role role = new S_Role();
    
    public List<LabelValueBean> getStateList() {
        return ComBeanState.getList();
    }
    public List<LabelValueBean> getOrganTypeList() {
    	List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
    	rtn.addAll(ComBeanI_SystemParam.getList(ParamClass.CLASS_ORGAN_TYPE, true, ""));
    	rtn.addAll(ComBeanIS_Organ.getList(false, this.getUserSession().getOrganID()));
        return rtn;
    }
    public List<LabelValueBean> getSctLevelList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_SCT_LEVEL, false, "");
    }
    
    public List<LabelValueBean> getPowerList() {
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(new LabelValueBean("无", S_Module.POWER_NONE+""));
        list.add(new LabelValueBean("本人", S_Module.POWER_OWN+""));
        list.add(new LabelValueBean("本地区", S_Module.POWER_AREA+""));
        list.add(new LabelValueBean("所有", S_Module.POWER_ALL+""));
        return Collections.unmodifiableList(list);
    }
    
    public TreeMap<Integer, String> getPowerMap() {
        TreeMap<Integer, String> map = new TreeMap<Integer, String>();
        map.put(S_Module.POWER_NONE, "无");
        map.put(S_Module.POWER_OWN, "本人");
        map.put(S_Module.POWER_AREA, "本地区");
        map.put(S_Module.POWER_ALL, "所有");
        return map;
    }
    
    public List<LabelValueBean> getAdminList() {
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(new LabelValueBean("否", "0"));
        list.add(new LabelValueBean("是", "1"));
        list.add(new LabelValueBean("超级管理员", "2"));
        return Collections.unmodifiableList(list);
    }
    
    public String getAdminText(int key) {
        switch(key){
            case 0: return "否";
            case 1: return "是";
            case 2: return "超级管理员";
        }
        return "";
    }

    private HashMap<String, String> myModulesMap = null;
    private String[] myModules;
    private String[] myRolePowers;
    
    public String[] getMyModules() {
        return myModules;
    }

    public void setMyModules(String[] modules) {
        myModules = modules;
    }
        

    public String[] getMyRolePowers() {
        return myRolePowers;
    }

    public void setMyRolePowers(String[] myRolePowers) {
        this.myRolePowers = myRolePowers;
    }

    @SuppressWarnings("unchecked")
    public List<S_Module> getModuleList() {    	
        return getList(S_Module.class, dbQuery("SP_S_ModuleQueryByRole",  
                new Object[] { this.getUserSession().getRoleID(), Constants.DEF_PARAMETET_LONG }));
    }

    public S_Role getRole() {
        return role;
    }

    public void setRole(S_Role role) {
        this.role = role;
    }
}
