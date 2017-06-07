package com.svv.dms.web.service.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanS_User;
import com.svv.dms.web.common.ComError;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.S_Role;
import com.svv.dms.web.entity.S_User;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.MD5;
import com.svv.dms.web.util.TColumn;

public class S_UserBean extends AbstractBean{

	public String S_User(){
        return this.exeByCmd("");
    }
    @SuppressWarnings("unchecked")
	public String query(){
        if(isHasEditPower()) this.addOptionList(BConstants.option_edit_string);
        if(isHasDelPower()) this.addOptionList(BConstants.option_del_string);
		
        setResult_List(getList(S_User.class, dbQuery(SQL.SP_S_UserQueryByC(this.getUserSession().getRoleID(), c_organID))));
        logger(ComBeanLogType.TYPE_QUERY, "查询帐户信息");
		return BConstants.LIST;
	}
	public String S_UserStart(){
	    if(HIUtil.isEmpty(this.getParameter("cmd"))){
	        this.loadByID();
	        user.setState(Constants.USER_STATE_VALID);
	        return BConstants.SUCCESS;
	    }
        return this.exeByCmd("");
	}
    public String S_UserStop(){
        if(this.dbExeBySQL("SP_S_UserUpdateState", new Object[]{Constants.USER_STATE_STOP, user.getUserID()})){
            this.setMessage(true, "帐户已停用.");
        }
        return BConstants.MESSAGE;
    }
    public String S_UserStartApply(){
        if(this.dbExeBySQL("SP_S_UserUpdateState", new Object[]{Constants.USER_STATE_STARTING, user.getUserID()})){
            this.setMessage(true, "帐户启用申请已提交，请等待管理员处理.");
        }
        return BConstants.MESSAGE;
    }
	
	public String add(){
//        user.setPassword(Constants.DEF_USER_PASSWORD);
//        user.setValidatePassword(Constants.DEF_USER_PASSWORD);
	    Object[] params = new Object[] {
                "add",
                -1,
                user.getLoginName(),
                user.getUserName(),
                MD5.encode(user.getPassword().toUpperCase()), //MD5加密
                MD5.encode(user.getValidatePassword().toUpperCase()), //MD5加密
                user.getRoleID(),
                HIUtil.isEmpty(user.getCssStyle(), "default"),
                user.getState(),
                user.getAreaID(),
                user.getEmpID(),
                };
		if(dbExe_p("SP_S_UserManager", params)){
		    logger(ComBeanLogType.TYPE_ADD, "添加帐户信息", params);
            this.setNextPage("帐户列表", "system/S_User.y");
            return BConstants.MESSAGE_PAGE;
		}
		return BConstants.SUCCESS;
	}
	
    public String edit(){
        Object[] params = new Object[] {
                "edit",
                user.getUserID(),
                user.getLoginName(),
                user.getUserName(),
                user.getPassword().length()==32 ? user.getPassword() : MD5.encode(user.getPassword().toUpperCase()),
                user.getValidatePassword().length()==32 ? user.getValidatePassword() : MD5.encode(user.getValidatePassword().toUpperCase()),
                user.getRoleID(),
                user.getCssStyle(),
                user.getState(),
                user.getAreaID(),
                user.getEmpID(),
                };
        if(dbExe_p("SP_S_UserManager", params)){
            logger(ComBeanLogType.TYPE_EDIT, "编辑帐户信息", params);
            this.setNextPage("帐户列表", "system/S_User.y");
            return BConstants.MESSAGE_PAGE;
        }
        return BConstants.SUCCESS;
    }
    public String set(){
        S_User newUser = (S_User)getList(S_User.class, dbQuery("SP_S_UserQueryByUserID", new Object[]{ user.getUserID() })).get(0);
        newUser.setPassword(HIUtil.getRandomString(6).toUpperCase()); //生成随机密码
        newUser.setValidatePassword(HIUtil.getRandomString(6).toUpperCase()); //生成随机密码
        newUser.setRoleID(user.getRoleID());
        newUser.setState(user.getState());
        
        Object[] params = new Object[] {
                "edit",
                newUser.getUserID(),
                newUser.getLoginName(),
                newUser.getUserName(),
                MD5.encode(newUser.getPassword()), //MD5加密
                MD5.encode(newUser.getValidatePassword()), //MD5加密
                newUser.getRoleID(),
                newUser.getCssStyle(),
                newUser.getState(),
                newUser.getAreaID(),
                newUser.getEmpID(),
                };
        if(dbExe_p("SP_S_UserManager", params)){
            logger(ComBeanLogType.TYPE_EDIT, "设置帐户状态", params);
            
            String tmp = "机构名称："+newUser.getOrganName()+"<br>帐户名称："+newUser.getUserName()+"<br>角色名称："+newUser.getRoleName()+"<br>登录名称："+newUser.getLoginName()+"<br>登录密码："+newUser.getPassword()+"<br>验证密码："+newUser.getValidatePassword();
            if(newUser.getOperator()>0){
                this.msg(tmp, newUser.getOperator()+"");
            }else if(this.isSuperAdmin()){
                this.msg(tmp, this.getUserSession().getUserID()+"");
            }
            this.setMessage(true, "<span id=info_aaa style=\"font-size:12px;\">"+tmp+"</span><br><br><a href=# onclick=\"clipboardData.setData('text',info_aaa.innerText)\"><复制以上信息></a>");
            //this.setNextPage(0, "帐户列表", "system/S_User.y");
            this.setScript("parent.page_redirect();");
            return BConstants.MESSAGE_PAGE;
        }
        return BConstants.SUCCESS;
    }
    
	public String del(){
        Object[] params = new Object[] {
                "del",
                user.getUserID(),
                user.getLoginName(),
                user.getUserName(),
                user.getPassword(),
                Constants.DEF_PARAMETET_LONG, //new Long(roleID),
                Constants.DEF_PARAMETET_LONG, //new Long(state),
                Constants.DEF_PARAMETET_LONG, //this.getParameter("areaID", Constants.DEF_PARAMETET_LONG)
                };
        if(dbExe("SP_S_UserManager", params)){
            logger(ComBeanLogType.TYPE_DEL, "删除帐户信息", params);
            ComBeanS_User.load();
        }
		return BConstants.SUCCESS;
	}
	
    public String S_Mpsd(){
        return this.exeByCmd("ForMyInfo");
    }
    
    public String mpsdForMyInfo(){
        S_User my = this.getUserSession();
        if(my==null){
            return "index";
        }
        String password = this.getParameter("password").toUpperCase();
        String vpassword = this.getParameter("vpassword").toUpperCase();
        if(!HIUtil.isEmpty(password)){
            if(!my.getPassword().equals(this.getParameter("oldpd").toUpperCase()) && !my.getPassword().equals(MD5.encode(this.getParameter("oldpd").toUpperCase()))){
                this.setMessage(false, "原登录密码有误，请重新输入！");
                return BConstants.SUCCESS;
            }
            password = MD5.encode(password);
        }
        if(!HIUtil.isEmpty(vpassword)){
            if(!my.getValidatePassword().equals(this.getParameter("oldvpd").toUpperCase()) && !my.getValidatePassword().equals(MD5.encode(this.getParameter("oldvpd").toUpperCase()))){
                this.setMessage(false, "原验证密码有误，请重新输入！");
                return BConstants.SUCCESS;
            }
            vpassword = MD5.encode(vpassword);
        }

        if(dbExe("SP_S_UserMpsd", new Object[] {my.getUserID(), password, vpassword})){
            logger(ComBeanLogType.TYPE_EDIT, "修改登录密码与二级验证密码");
            this.setMessage("您修改了密码，请重新登录！");
            return "index";
        }
        return BConstants.SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
	protected void loadByID(){
        List<S_User> list = getList(S_User.class, dbQuery("SP_S_UserQueryByUserID", new Object[]{ user.getUserID() }));
        if (list == null ){
            this.setMessage(false, ComError.err_000001);
        }else{
            user = list.get(0);
        }
    }
	
	private void setResult_List(List<S_User> list) {
		
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
        	objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("ID", "userID"),
                                   new TColumn("登录名", "loginName"),
                                   new TColumn("帐户姓名", "userName"),
                                   new TColumn("所属机构", "organName", 180),
                                   new TColumn("角色", "roleName"),
                                   new TColumn("登录次数", "loginNum"),
                                   new TColumn("最后登录日期", "lastLoginDate"),                          
                                   new TColumn("状态", "state"),
                                   new TColumn("创建日期"),
                                   new TColumn("管理", null, TColumn.ALIGN_LEFT)};
            for(S_User o: list){
                String tmp = "";
                if(o.getState()==0) tmp = "<input type=button class=button value=启用 onclick=\"valid(this,"+o.getUserID()+")\"> ";
                else if(o.getState()==1) tmp = "<input type=button class=button value=停用 onclick=\"stop(this,"+o.getUserID()+")\"> ";
                else if(o.getState()==2) tmp = "<input type=button class=button value=启用 onclick=\"valid(this,"+o.getUserID()+")\"> ";
                objs[i++] = new Object[]{"",
                						 o.getUserID(),
                						 o.getLoginName(),
                                         o.getUserName(),
                                         o.getOrganName(),
                                         o.getRoleName(),
                                         o.getLoginNum(),
                                         o.getLastLoginDate(),
                                         "<span id=si_state_"+o.getUserID()+">"+ (o.getState()!=1? "<font color=red>"+getStateText(o.getState())+"</font>" : getStateText(o.getState()))+"</span>",
                                         o.getIstDate(),
                                         tmp + this.getOptionHtmlString(o.getUserID()+"", o.getUserName(), "")};
            }
        }
        this.setResultList(objs);
    }
	

	private static final long serialVersionUID = -6263722023956496664L;

    private String c_organID = "";
    
	private S_User user = new S_User();
		
    @SuppressWarnings("unchecked")
	public List<LabelValueBean> getRoleList() {
        List<S_Role> list = null;
        if(user.getOrganID()>0){
            list = getList(S_Role.class, dbQuery("SP_S_RoleQueryByOrganType", new Object[] { user.getOrganID(), user.getOrganType(), this.getUserSession().getRoleID() }));
        }else{
            list = getList(S_Role.class, dbQuery("SP_S_RoleQueryByRole", new Object[] { this.getUserSession().getRoleID() }));
        }
                
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        rtn.add(new LabelValueBean("", ""));
        if(list!=null && list.size()>0){
            for(S_Role o: list){
                rtn.add(new LabelValueBean(o.getRoleName(), o.getRoleID()+""));
            }
        }
        return Collections.unmodifiableList(rtn);
    }
    
    public List<LabelValueBean> getStateList(){
        List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
        rtn.add(new LabelValueBean("锁定", Constants.USER_STATE_LOCK+""));
        rtn.add(new LabelValueBean("有效", Constants.USER_STATE_VALID+""));
        rtn.add(new LabelValueBean("停用", Constants.USER_STATE_STOP+""));
        return Collections.unmodifiableList(rtn);
    }
    public static String getStateText(int key){
        if(key==Constants.USER_STATE_LOCK) return "锁定";
        if(key==Constants.USER_STATE_VALID) return "有效";
        if(key==Constants.USER_STATE_STOP) return "停用";
        if(key==Constants.USER_STATE_DEL) return "已删除";
        return "";
    }
	public boolean isHasAddPower(){
    	return checkUserPower("S_User_add");
    }
    public boolean isHasEditPower(){
    	return this.isSuperAdmin() || checkUserPower("S_User_edit");
    }
    public boolean isHasDelPower(){
    	return this.isSuperAdmin() || checkUserPower("S_User_del");
    }
    public S_User getUser() {
        return user;
    }

    public void setUser(S_User user) {
        this.user = user;
    }

    public String getC_organID() {
        return c_organID;
    }

    public void setC_organID(String c_organid) {
        c_organID = c_organid;
    }
}
