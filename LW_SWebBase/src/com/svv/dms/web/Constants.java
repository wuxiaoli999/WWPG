package com.svv.dms.web;

import javax.servlet.http.HttpServletRequest;

import com.svv.dms.web.common.ComBeanI_DataParamType;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanIS_Organ;
import com.svv.dms.web.common.SystemParam;

public class Constants {
//	public static UGID UGID = new UGID(100);

	public static boolean DATA_PRINT_FLAG = false; //系统参数：是否打印数据查询结果
	public static String FILESERVER_PATH = null; //业务参数：文件服务器硬盘地址
	public static String FILESERVER_URL = null; //业务参数：文件服务器web地址
	public static String WEBSERVER_URL = null; //业务参数：WEB SERVER地址
	public static boolean FILE_TOTAL_URLPATH_FLAG = true; //数据库存储文件路径是否为URL全路径

	public static String MODULE_WEB_NAME = "";
    public static String DEF_AREA_ID = "";
    public static int DEF_NEXT_PAGE_AUTO_TIME = 0;
    public static int DEF_CLOSE_AUTO_TIME = 0;
    public static String DEF_USER_PASSWORD = "";
    public static String DBUSER = "";
    public static String WEB_URL = "";
    public static String WEB_DOMAIN = "";
    public static String WEB_ORGANID = "";

    public static String MK_BIQ = "_BIQ";
    public static String MK_BIZ = "_BIZ";
    public static String MK_ADMIN = "<Tomcat启动时自动生成>";
    
    public static String HSQL_DBNAME = "mydb";
    public static int HSQL_DBPORT = 13901;
    public static String HSQL_USER = "sa";
    public static String HSQL_PASS = "";
    
    final public static int USER_STATE_LOCK = 0;
    final public static int USER_STATE_VALID = 1;
    final public static int USER_STATE_STOP = 2;
    final public static int USER_STATE_DEL = 3;
    final public static int USER_STATE_STARTING = 4;
    
    public static final String WEB_EXTEND = ".shtml";
    public static String UPLOAD_FILE_PATH = "upfile";
        
    public static final String NULL = "";
    public static String DEF_SELECT_TEXT = "==== 全部 ====";
    
    /*db default value*/
    public static String DEF_PARAMETET_STRING = "null";
    public static long DEF_PARAMETET_LONG = -1L;
    
    /*Split flag*/
    public static String MAP_CALLNO_SPLITER = "$";
    public static String SPLITER = "`"; //不能用“,”
    public static String SPLITER_SP = "^"; //不能用“ ` ,”
    public static String SPLITER_DATA = "#"; //不能用“ ` , ^”
    public static String SPLITER2S = "``";
    public static String PARAMETET_PRIMARY_SPLITER = ";";
    public static String PARAMETET_EXTENDS_SPLITER = ",";
    public static String PARAMETET_SQLPARAM_PRIMARY_SPLITER = ";";
    public static String PARAMETET_SQLPARAM_EXTENDS_SPLITER = ",";

    public static long LOG_LIMIZ_TYPE = ComBeanLogType.TYPE_QUERY;

    /* statement type */
    public static final String STATEMENT_SQLTYPE_SELECT = "select";
    public static final String STATEMENT_SQLTYPE_INSERT = "insert";
    public static final String STATEMENT_SQLTYPE_UPDATE = "update";
    public static final String STATEMENT_SQLTYPE_DELETE = "delete";
    
    public static final String SESSION_ATTRIBUTE_SUSER = "seesion.suser";    
    public static final String SESSION_ATTRIBUTE_CHART = "seesion.chart";
    public static final String SESSION_ATTRIBUTE_DATA = "seesion.data";
    public static final String SESSION_ATTRIBUTE_SID = "seesion.sid";
    public static final String SESSION_ATTRIBUTE_SIDCONTAINER = "seesion.sidcontainer";
    public static final String SESSION_ATTRIBUTE_PAGEMANAGER = "session.pagemanager";
    
    public static final String REQUEST_ATTRIBUTE_LIST = "request.list";
    public static final String REQUEST_ATTRIBUTE_CHARZ_URL = "request.chart.url";
    public static final String REQUEST_ATTRIBUTE_POI = "request.poi";
    public static final String REQUEST_ATTRIBUTE_POI_NAME = "request.poi.name";
    public static final String REQUEST_ATTRIBUTE_LOCATIONRESULT = "location.Result";
    public static final String REQUEST_ATTRIBUTE_RESULT = "request.data";
    public static final String REQUEST_ATTRIBUTE_MESSAGE = "request.message";
    public static final String REQUEST_ATTRIBUTE_RESULT_HTML = "request.resulthtml";
    public static final String REQUEST_ATTRIBUTE_EXCEPTION = "request.exception";
    public static final String REQUEST_ATTRIBUTE_PAGECONTROL = "request.pages";
    public static final String REQUEST_ATTRIBUTE_OPTIONBAR = "request.optionbar";
    public static final String REQUEST_ATTRIBUTE_NEXTPAGE = "request.nextpage";
    public static final String REQUEST_ATTRIBUTE_CLOSE = "request.close";
    public static final String REQUEST_ATTRIBUTE_SCRIPT = "request.script";
    public static final String REQUEST_ATTRIBUTE_METHODPATH = "request.methodPath";
    public static final String REQUEST_ATTRIBUTE_METHODNAME = "request.methodName";
    public static final String REQUEST_ATTRIBUTE_FORMHTML = "request.formhtml";
    public static final String REQUEST_ATTRIBUTE_FORMCHECKJS = "request.formCheckJS";
    public static final String REQUEST_ATTRIBUTE_INITJS = "request.initJS";
    public static final String REQUEST_ATTRIBUTE_QUERYCONDITION = "request.queryCondition";
    
    

    static{
    	loadSystemParam();
    	ComBeanI_DataParamType.load();
    }
    public static void loadSystemParam() {
        MK_ADMIN = Math.random()+"";
        MODULE_WEB_NAME = SystemParam.getValue("SYSTEM_NAME");
        DEF_AREA_ID = SystemParam.getValue("DEF_AREA_ID");
        DEF_NEXT_PAGE_AUTO_TIME = Integer.parseInt(SystemParam.getValue("DEF_NEXT_PAGE_AUTO_TIME"));
        DEF_CLOSE_AUTO_TIME = Integer.parseInt(SystemParam.getValue("DEF_CLOSE_AUTO_TIME"));
        
        DEF_USER_PASSWORD = SystemParam.getValue("DEF_USER_PASSWORD");
        DBUSER = SystemParam.getValue("DBUSER");        

        DATA_PRINT_FLAG = ComBeanI_SystemParam.getText(900, "900001").equals("1");
        FILESERVER_URL = ComBeanI_SystemParam.getText(902, "902001");
        FILESERVER_PATH = ComBeanI_SystemParam.getText(903, "903001");
        WEBSERVER_URL = ComBeanI_SystemParam.getText(904, "904001");
        
        ComBeanIS_Organ.load();
    }
    
    public static void checkDomain(HttpServletRequest req){
    	if(req.getServerName().indexOf(".")>0){
	    	Constants.WEB_DOMAIN = req.getServerName().substring(0, req.getServerName().indexOf("."));
	    	Integer sysOrganID = ComBeanIS_Organ.getIDByDomain(Constants.WEB_DOMAIN);
	  	    if(sysOrganID==null) WEB_DOMAIN = "";
    	}
    }
}
