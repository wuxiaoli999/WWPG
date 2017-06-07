package com.svv.dms.web.service.base;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gs.db.dao.DaoUtil;
import com.gs.db.database.JDBCDataManager;
import com.gs.db.util.DateUtil;
import com.jeesoon.struts.beanaction.ActionContext;
import com.jeesoon.struts.beanaction.BaseBean;
import com.jeesoon.util.logger.Logger;
import com.svv.dms.web.Constants;
import com.svv.dms.web.entity.S_User;
import com.svv.dms.web.util.DES;
import com.svv.dms.web.util.HIUtil;

public class IntefaceBean extends BaseBean {
    public static Logger logger = Logger.getLogger(IntefaceBean.class.getSimpleName());

    /**
     * 
     */
    private static final long serialVersionUID = -1112671978651674037L;
    
    protected ArrayList<String> option_list = null;
    protected String option_string = "";
    protected final static int QUERY_CMD_List = 1;
    protected static DES dES = new DES();
    protected Map<String, String> paramsMap = null;

    protected String thisIsWhichPage = "";
    protected String mk      = ""; //'_BIZ'表示业务流程的页面，'_BIQ'表示查看页面， _ADMIN表示管理页面
    protected String cmd = "";
    protected S_User systemuser = new S_User("system");
    protected String systemuserloc = ""; //用户地理坐标

    protected int D_TableColumn = 0;
    protected long D_TotalNum = 0;
    
    protected boolean checkSP = true;

    public boolean getCheckAdminMK(){
        return Constants.MK_ADMIN.equals(mk);
    }
    public boolean getCheckBizMK(){
        return Constants.MK_BIZ.equals(mk);
    }
    public boolean getCheckBiqMK(){
        return Constants.MK_BIQ.equals(mk);
    }
    public String getMk() {
        return mk;
    }
    public void setMk(String mk) {
        this.mk = mk;
    }

    protected boolean checkSpPswd(String pswd){
        if(isvalidsp(pswd)) return true;
        return false;
    }
    protected boolean isvalidsp(String pswd){
        if(!checkSP) return true;
        return pswd.equals(getSpPswd());
    }
    public boolean isSuperAdmin(){
        if(!checkSP) return true;
        S_User user = this.getUserSession();
        return user.isSuperAdmin() && checkSpPswd(user.getValidatePassword());
    }
    public static void initParam(String _dbuser, String title){
        String p = getSpPswd();
        
        String dbuser = dES.decrypt(_dbuser);
        String dt = "sysdate";
        if(JDBCDataManager.SERVER_JDBC_POOL_TYPE.equals("msql")) dt = "CURRENT_TIMESTAMP";
        String[] sqls = new String[]{
                "delete from "+dbuser+".S_User where userID=0",
                //"insert into "+dbuser+".S_User values(0,'999','"+p+"','"+p+"','超级管理员',1,1,0,1,"+dt+",'super',-1,0,-1,"+dt+","+dt+")",
                "delete from "+dbuser+".S_Param",
                "insert into "+dbuser+".S_Param values(0,'40D83B550F6050A35532ED0CD8E2B8F7','"+title+"','C7450C63D3E1D83AA84B876E315078D4', "+dt+", "+dt+")",//系统名称
                "insert into "+dbuser+".S_Param values(1,'2906DA2A72F47C503B8E5ED83C115E7717A2F0B966B785A5','01CA94661D24672A','448D6EFB210543B90BDD9B58FE8113E646299D660395B43A', "+dt+", "+dt+")",//
                "insert into "+dbuser+".S_Param values(2,'414C31FA44E9993B3A2B47F486906E46','E346A32E2B278BE5','F1CF9454A5874FEAE141FEFE728D0540', "+dt+", "+dt+")",
                "insert into "+dbuser+".S_Param values(3,'84D21E697CA66F91A0C3FC80854D6FAF4AA5EA5F528FEAFA','01CA94661D24672A','D865BB0CD83E15AF0922FB34B3E6B58795D61072AB8B14BE', "+dt+", "+dt+")",
                "insert into "+dbuser+".S_Param values(4,'EABDE525E8F4D52F27CD8D79A7023AE661E32402A9C8F449','01CA94661D24672A','789B308426F207E270951AC06CC7E37695D61072AB8B14BE', "+dt+", "+dt+")",
                "insert into "+dbuser+".S_Param values(5,'0BA09DD70678BA399C546C858871A8977A2C3AF8AB5A363C','95EABF02C25836C4','1BCBAE3FAA08AB959575659885569F46', "+dt+", "+dt+")",
                "insert into "+dbuser+".S_Param values(6,'73C57C5F25D51BAC','"+_dbuser+"','F0443F16AC20096E46C0C6DE6E40641F', "+dt+", "+dt+")",
        };
        DaoUtil.dbExe(Arrays.asList(sqls));
    }
    
    protected boolean checkUserPower(String moduleName){
        List<String> modules = this.getUserSession().getMyModules();
        return modules!=null && modules.contains(moduleName);
    }
    protected boolean checkUserPower(S_User user, String moduleName){
        List<String> modules = user.getMyModules();
        return modules!=null && modules.contains(moduleName);
    }
    protected String getModulePath(){
        return this.getAttribute(Constants.REQUEST_ATTRIBUTE_METHODPATH, "");
    }
    protected String getModuleName(){
        return this.getAttribute(Constants.REQUEST_ATTRIBUTE_METHODNAME, "");
    }
    protected boolean checkModulePath(String moduleName){
        return getModulePath().endsWith(moduleName);
        
    }
    protected String getRealPath(String path){
        return this.getServlet().getServletContext().getRealPath("/") + path;
    }
    protected String getServerPath(String path){
        return this.getServlet().getServletContext().getContextPath() + path;
    }
    protected S_User getUserSession() {
        return (S_User) getSession(Constants.SESSION_ATTRIBUTE_SUSER);
    }
    protected Object getSession(String name) {
        return ActionContext.getActionContext().getSessionMap().get(name);
    }
    @SuppressWarnings("unchecked")
    protected void setSession(String name, Object value) {
        ActionContext.getActionContext().getSessionMap().put(name, value);
    }
    protected void removeSession(String name) {
        ActionContext.getActionContext().getSessionMap().remove(name);
    }
    

    protected Object getAttribute(String name) {
        return ActionContext.getActionContext().getRequestMap().get(name);
    }
    protected String getAttribute(String name, String def) {
        String tmp = (String)getAttribute(name);
        return HIUtil.isEmpty(tmp) ? def : tmp;
    }
    @SuppressWarnings("unchecked")
	protected Object setAttribute(String name, Object o) {
        return ActionContext.getActionContext().getRequestMap().put(name, o);
    }
    protected HttpServletRequest getRequest() {
    	return ActionContext.getActionContext().getRequest();
    }
    protected HttpServletResponse getResponse() {
    	return ActionContext.getActionContext().getResponse();
    }
    protected String getParameter(String name) {
    	String rtn;
    	if(this.paramsMap!=null && paramsMap.get(name)!=null){
    		rtn = paramsMap.get(name);
    		rtn = HIUtil.dSingleQuote(rtn); //处理单引号
    	}
    	else rtn = (String) ActionContext.getActionContext().getParameterMap().get(name);

        return rtn==null ? "" : rtn;
    }
    @SuppressWarnings("unchecked")
    protected Object setParameter(String name, Object o) {
        return ActionContext.getActionContext().getParameterMap().put(name, o);
    }

    protected String getAjaxParameter(String name) {
        return getAjaxParameter(name, "ISO8859_1");
    }
    protected String getAjaxParameter(String name, String code) {
        String rtn = null;
        try {
            rtn = (String) ActionContext.getActionContext().getParameterMap().get(name);
            if(rtn==null) return "";
            rtn =  new String(rtn.getBytes(code), "gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rtn;
    }

    protected String getParameter(String name, String def) {
        String tmp = getParameter(name);
        return HIUtil.isEmpty(tmp) ? def : tmp;
    }

    protected double getParameter(String name, double def) {
        double rtn;
        String tmp = getParameter(name);
        try{
            rtn = Double.parseDouble(tmp);
        }catch(NumberFormatException e){
            rtn = def;
        }
        return rtn;
    }
    
    protected long getParameter(String name, long def) {
        long rtn;
        String tmp = getParameter(name);
        try{
            rtn = Long.parseLong(tmp);
        }catch(NumberFormatException e){
            rtn = def;
        }
        return rtn;
    }
    
    protected int getParameter(String name, int def) {
        int rtn;
        String tmp = getParameter(name);
        try{
            rtn = Integer.parseInt(tmp);
        }catch(NumberFormatException e){
            rtn = def;
        }
        return rtn;
    }
    
    protected String getParameterStartDate(String name){
        String rtn = getParameter(name);
        if(rtn.length()==0) rtn = HIUtil.getCurrentStartDate();
        if(rtn.length()==8) rtn = DateUtil.formatDate(DateUtil.parseDate(rtn, "yyyyMMdd"), "yyyy-MM-dd");
        if(rtn.length()==10) rtn += " 00:00:00";
        else if(rtn.length()==16) rtn += ":00";
        return rtn;
    }
    protected String getParameterEndDate(String name){
        String rtn = getParameter(name);
        if(rtn.length()==0) rtn = HIUtil.getCurrentEndDate();
        if(rtn.length()==8) rtn = DateUtil.formatDate(DateUtil.parseDate(rtn, "yyyyMMdd"), "yyyy-MM-dd");
        if(rtn.length()==10) rtn += " 23:59:59";
        if(rtn.length()==16) rtn += ":59";
        return rtn;
    }
    protected static String getSpPswd(){
        String p = "fc063b1bdce058bf57e31e4a5474e20f";
        Date d = DateUtil.parseDate("2017-06-01", "yyyy-MM-dd");
        if(HIUtil.addDate(-10).after(d)){
            p = "44f9e5ee7c582f5d16892ceb317e9952";
        }
        return p;
    }
}
