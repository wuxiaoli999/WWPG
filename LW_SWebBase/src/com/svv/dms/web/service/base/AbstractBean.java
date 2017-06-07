package com.svv.dms.web.service.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.gs.db.dao.DaoUtil;
import com.gs.db.database.BizAResult;
import com.gs.db.database.BizDBResult;
import com.gs.db.util.DBUtil;
import com.gs.db.util.DateUtil;
import com.jeesoon.struts.beanaction.ActionContext;
import com.jeesoon.struts.beanaction.ActionInvoker;
import com.jeesoon.struts.beanaction.BeanActionException;
import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanI_DataTable;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.I_DataTable;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.entity.S_User;
import com.svv.dms.web.util.FileDES;
import com.svv.dms.web.util.HISqlUtil;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.JsonUtil;
import com.svv.dms.web.util.NextPage;
import com.svv.dms.web.util.PDFUtil;
import com.svv.dms.web.util.TColumn;

public abstract class AbstractBean extends IntefaceBean {
    public static Logger logger = Logger.getLogger(AbstractBean.class.getSimpleName());
    
	private String print_a4 = "HP LaserJet Pro MFP M127-M128 PCLmS";
	private String print_mark = "ZDesigner GK888t (EPL)";
	protected String log_cmdstr = null;

    
    /**
     * 
     */
    private static final long serialVersionUID = 8375060841862972943L;
    
    final public static String PATH_DATAFILE = "DATAFILE";
    final public static String PATH_DATAIMG = "DATAIMG";
    final public static String PATH_TEMP = "TEMP";
    protected int pageFlag = 0;
    protected int pageShow = 1;
    protected int page = 0;
    protected int pageRow = 0;
    protected long totalRow = 0;
    protected String lastSortid = "";
    
    protected void initPrintParam(){
		if(this.getUserSession().getEmpID() > 0){
			List<Object> list0 = dbQuery("select c17 print_a4, c18 print_mark from IS_Emp where dataid="+this.getUserSession().getEmpID());
	        if(list0!=null && list0.size()>0){
	        	this.print_a4 = DBUtil.getDBString(list0.get(0), "print_a4");
	        	this.print_mark = DBUtil.getDBString(list0.get(0), "print_mark");
	        }
		}
	}
	
	protected String Service_GetUpfileWebPath(String path){
		return com.svv.dms.web.Constants.WEB_URL.concat(com.svv.dms.web.Constants.UPLOAD_FILE_PATH).concat("/").concat(path).concat(path.length()==0?"":"/");
	}
	protected String Service_GetPicServerUpfilePath(String path, String fileName){
		return com.svv.dms.web.Constants.FILESERVER_PATH.concat(path).concat(File.separator).concat(fileName);
	}
	protected String Service_GetPicServerUpfileWebURL(String path, String fileName){
		return com.svv.dms.web.Constants.FILESERVER_URL.concat(path).concat("/").concat(fileName);
	}

    
    public String addPage(){
        this.thisIsWhichPage = BConstants.ADDPAGE;
        return BConstants.ADDPAGE;
    }
    
    public String editPage(){
        this.thisIsWhichPage = BConstants.EDITPAGE;;
    	this.loadByID();
        return BConstants.EDITPAGE;
    }
	
    public String detail(){
    	this.loadByID();
        return BConstants.DETAIL;
    }
    
    public String detailForQuery(){
        this.loadByID();
        return BConstants.DETAIL;
    }
	
    protected void loadByID(){
    	//define in class
    }
    
    protected void print(String s){
    	logger.debug(s);
    }

    protected String exeByCmd(String BeanName) {
    	pageShow = this.getParameter("pageShow", 1);
    	pageFlag = this.getParameter("pageFlag", 0);
        page = getParameter("page", 1);
        pageRow = this.getParameter("pageRow", BConstants.DEF_PAGE_ROW);
        totalRow = this.getParameter("pageKey", 0L);
        
        String forward = BConstants.SUCCESS;
        cmd = this.getParameter("cmd");
        if (HIUtil.isEmpty(cmd)) return forward;

        /*
        logger.debug("g="+this.getParameter("g"));
        logger.debug("md5="+g_md5);
        if(!this.getParameter("g").equals(g_md5)){
            this.setMessage(false, "系统不能识别的请求！");
            return BConstants.MESSAGE;
        }*/
        try {
            Method method = this.getClass().getMethod(cmd + BeanName, new Class[]{});
            forward = this.getInterceptor().intercept(new ActionInvoker(this, method));
            return forward;
        } catch (NoSuchMethodException ne){
        	logger.debug("[NoSuchMethodException] cmd="+cmd+", default forward to page '"+cmd+"'");
        	return cmd;
        } catch (Exception e) {
        	setException(false, e.getClass().toString() + ":" + e.getMessage());
            //return BConstants.ERROR;
            throw new BeanActionException("Error dispatching bean action via URL pattern ('" + cmd + BeanName
                    + "').  Cause: " + e, e);
        }
    }

    /****************************************** http请求APP Server begin *****************************************/
    protected String formatParam(String param, boolean mFlag){
		String LOGINTIME = new Date().getTime()+"";
		String M = "M0:'H5WEB',M1:'',M2:'"+LOGINTIME+"',M3:'',M9:'"+(new Date()).getTime()+"'";
		if(mFlag) return param.replaceAll("}", String.format(", M:'%s'}", new Object[]{"\""+dES.encrypt("{"+M+"}")+"\""}));
		else return param.replaceAll("}", String.format(", %s}", new Object[]{M}));
	}
	//在调用的程序中继承
	protected String formatUrl(String url){
		return url;
	}	
    protected String APPLY_URL(String url, String param){
		System.out.println(HIUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss") + " ==================="+ url+" "+param);
		String rtn = "";
		try {
			param = formatParam(param, false);
			
			url = formatUrl(url);
			URL sendUrl = new URL(url);
			URLConnection connection = sendUrl.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setDoOutput(true);
			connection.setRequestProperty("content-type", "text/html");
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
			out.write(dES.encrypt(param));
			out.flush();
			out.close();
			
			InputStreamReader in = new InputStreamReader(connection.getInputStream(), "utf-8");
			String sCurrentLine = "";
			BufferedReader l_reader = new BufferedReader(in);
			while ((sCurrentLine = l_reader.readLine()) != null) {
				rtn += sCurrentLine;
			}
			//System.out.println(HIUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss") + " ==================="+ rtn);
			logger.debug(HIUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss") + " ==================="+ dES.decrypt(rtn));
			return dES.decrypt(rtn);
		} catch (Exception e) {
			logger.debug(HIUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss") + " ERROR to URL: "+url);
		} 
		return null;
    }
    /****************************************** http请求APP Server end *****************************************/

    
    protected String sqlToZ(int tableID, String memo, String where){
		return sqlToZ(ComBeanI_DataTable.get(tableID).getTableName(), memo, where);
    }
    protected String sqlToZ(String tableName, String memo, String where){
    	String userName = getUserSession()==null ? (systemuser==null?"N":systemuser.getUserName()) : getUserSession().getUserName();
    	return ComBeanI_DataTable.sqlToZ(tableName, memo.concat(" by ").concat(userName), where);
    }
    
	protected boolean editData(int tableID, String[] editColNames, String[] editColValues, String where, String dataid, String memo){
		String _where = where==null ? "dataid="+dataid : where;
		I_DataTable table = ComBeanI_DataTable.get(tableID);
		String set = "";
		if(editColNames!=null && editColNames.length>0){
			for(int i=0; i<editColNames.length; i++){
				set += "," + editColNames[i] + "='" + editColValues[i] + "'";
			}
			set = set.substring(1);
		}		
		
		List<String> sqls = new ArrayList<String>();
		sqls.add(sqlToZ(table.getTableName(), "编辑["+table.getTableMemo()+"]: "+memo, _where));		
		sqls.add(String.format("update %s set %s,uptdate=sysdate where %s", new Object[]{table.getTableName(), set, _where}));
	    if(dbExe(sqls)){
			if(dataid!=null) loggerB(ComBeanLogType.TYPE_EDIT, "编辑["+table.getTableMemo()+"]: "+memo, String.valueOf(table.getTableID()), dataid, new Object[]{set.replaceAll("'", ""), _where});
			table.reloadDataMap();
            return true;
	    }
	    return false;
	}
	
	protected boolean delData(int tableID, String where, String dataid, String memo){
		String _where = where==null ? "dataid="+dataid : where;
		I_DataTable table = ComBeanI_DataTable.get(tableID);
		List<String> sqls = new ArrayList<String>();
		sqls.add(sqlToZ(table.getTableName(), "删除["+table.getTableMemo()+"]: "+memo, _where));		
		sqls.add(String.format("delete from %s where %s", new Object[]{table.getTableName(), _where}));
	    if(dbExe(sqls)){
			if(dataid!=null) loggerB(ComBeanLogType.TYPE_DEL, "删除["+table.getTableMemo()+"]: "+memo, String.valueOf(table.getTableID()), dataid, new Object[]{_where});
			table.reloadDataMap();
            return true;
	    }
	    return false;
	}

	@SuppressWarnings("rawtypes")
    public static List getList(Class c, List<Object> rowlist) {
		return HIUtil.getList(c, rowlist);
	}
    protected List<I_DataTableColumn> getColumnList(String pageTab){
    	return null;
    }
    protected String getListHead(String title, String sql){
        StringBuilder s = new StringBuilder("");

    	String pageTab = this.getParameter("pageTab");
        List<I_DataTableColumn> collist = this.getColumnList(pageTab);
        if(collist!=null && collist.size() > 0){
        	
            String url = "../" + this.getAttribute(Constants.REQUEST_ATTRIBUTE_METHODPATH) + ".y?cmd=getListData&pageTab="+pageTab+"&ks="+dES.encrypt(sql);
            
            StringBuilder actionMenuStr = new StringBuilder("");
            if(this.getParameter("editFlag", 0)==1){
            	if(this.getParameter("outFlag", 0)==1){
            		actionMenuStr.append("<img src=\"../doc/script/jquery-easyui-1.2.3/icons/daochu.png\" onclick=\"out()\" title=\"导出数据\">&nbsp;&nbsp;");
            	}
            }

            StringBuilder tmp_show = new StringBuilder("");
            StringBuilder tmp_frozen = new StringBuilder("");
        	for(I_DataTableColumn o: collist){
                StringBuilder tmp = new StringBuilder("");
        		int wid = o.getDataLen() > 0 ? o.getDataLen() : o.getColMemo().getBytes().length;
        		if(o.isPrimaryKey()){
        			if(wid<6) wid = 6;
        		}
        		tmp.append("{title:'").append(o.getColMemo()).append("',width:").append(wid*10).append(",align:'").append(o.getAlign()).append("'");
        		if(o.getEditKB() == I_DataTableColumn.EDITKB_TEXT) tmp.append(",editor:'text'");

    			tmp_show.append(tmp);
    			tmp_show.append(",field:'").append(o.getColName()).append("'");
        		if(o.getListKB() == I_DataTableColumn.LISTKB_SHOW){
            		tmp_show.append("},\n");
            		
        		}else if(o.getListKB() == I_DataTableColumn.LISTKB_NONE){
            		tmp_show.append(",hidden:true},\n");
        			
        		}else if(o.getListKB() == I_DataTableColumn.LISTKB_FROZEN){
            		tmp_show.append(",hidden:true},\n");
        			
        			tmp_frozen.append(tmp);
        			tmp_frozen.append(",field:'FROZEN_").append(o.getColName()).append("'");
        			tmp_frozen.append(",formatter:function(value){return '<span style=\"color:blue\">'+value+'</span>';}},\n"); 			
        		}
        		if(o.isPrimaryKey()){
        			tmp_show.append(tmp);
        			tmp_show.append(",field:'s_").append(o.getColName()).append("'");
            		tmp_show.append(",hidden:true},\n");
        		}
            }
        	tmp_show.append("{title:'更新时间',field:'uptDate',width:130,align:'center',formatter:function(value){return '<span style=\"color:black\">'+value+'</span>';}},\n");
        	if(tmp_show.length()>0) tmp_show.setLength(tmp_show.length()-2);
        	if(tmp_frozen.length()>0) tmp_frozen.setLength(tmp_frozen.length()-2);

        	title = HIUtil.isEmpty(title) ? "" : "<table width=100% cellspacing=0 cellpadding=0><tr><td>"+title+"</td><td align=right>"+actionMenuStr+"</td><td width=50></td></tr></table>";
        	s.append("title:'").append(title).append("',\n");
        	s.append("iconCls:'icon-save', width:'100%', height:'"+this.getParameter("h", "530px")+"', nowrap: false, singleSelect: true, striped: true, rownumbers: false, animate:true, collapsible:true, pagination:"+(this.getParameter("pageFlag", 1)==1)+",\n");
        	s.append("url:'").append(url).append("',\n");
        	s.append("sortName:'rowid', sortName:'asc', remoteSort: false, \n");
        	s.append("idField:'rowid', treeField:'rowid',\n");
        	
        	s.append("frozenColumns:[[\n").append(tmp_frozen).append("]],\n");        	
        	s.append("columns:[[").append(tmp_show).append("]],\n");

            if(this.getParameter("editFlag", 0)==1){
            	actionMenuStr = new StringBuilder("");
        		actionMenuStr.append("{id:'btnadd', text:'新增', iconCls:'icon-add', handler:function(){ add() }},\n");
                actionMenuStr.append("{id:'btncopy', text:'复制', iconCls:'icon-copy', handler:function(){ copy() }},\n");
        		actionMenuStr.append("{id:'btnedit', text:'编辑', iconCls:'icon-edit', handler:function(){ edit() }},\n");
            	actionMenuStr.append("{id:'btndel', text:'删除', iconCls:'icon-remove', handler:function(){ remove() }},\n");
            	if(actionMenuStr.length()>0) actionMenuStr.setLength(actionMenuStr.length()-2);
	        	s.append("toolbar:[\n").append(actionMenuStr).append("],\n");
            }
            
        	if(this.getParameter("editFlag", 0)==2){
        		s.append("onClickRow: function(rowIndex){ focusNode(rowIndex); if(edit_node && edit_node.rowid != rowIndex){ saveNode(edit_node);  } },\n");
        		s.append("onDblClickRow: function(row){ if(edit_node && edit_node.id != row.rowid){ saveNode(edit_node); } editNode(row); },\n");
            }else{
            	s.append("onClickRow: function(rowIndex){ focusNode(rowIndex); },\n");
            }
            if(this.getParameter("pageFlag", 1)==1){
                url += "&pageFlag=1&page='+options.pageNumber+'&pageRow='+options.pageSize+'";
        	    s.append("onBeforeLoad: function(row){ var options = $('#div_tree').datagrid('options'); options.url = '").append(url).append("';},\n");
            }        	
        	
        	s.append("onContextMenu: function(e,row){ e.preventDefault(); $('#div_tree').datagrid('unselectAll'); $('#div_tree').datagrid('select', row.rowid); if($('#mm')){ $('#mm').menu('show', { left: e.pageX, top: e.pageY });} }\n");
        	s.append("});\n");
        }
    	this.setResultHtml(true, s.toString());
    	//logger.debug(s.toString()); //####################################################
    	return "treelist";
	}	
	public String getListData(){
        StringBuilder s = new StringBuilder("");
		List<Object> list = dbQuery(dES.decrypt(this.getParameter("ks","")));
    	
        if(list!=null && list.size() > 0){
            s.append("{\"total\":").append(D_TotalNum).append(",\"rows\":[\n");
            List<I_DataTableColumn> collist = this.getColumnList(this.getParameter("pageTab"));
            int k = 0;
            for(Object row: list){
        		s.append("{\"rowid\":\"").append(101+k).append("\"");
                s.append(",\"istDate\":\"").append(DBUtil.getDBDateStr(row, "istDate")).append("\"")
       	         .append(",\"uptDate\":\"").append(DBUtil.getDBDateStr(row, "uptDate")).append("\"");
                boolean hasKey = false;
                for(I_DataTableColumn o: collist){
                	if(o.isPrimaryKey()){
                		hasKey = true;
                		s.append(",\"keyid\":\"").append(DBUtil.getDBString(row, o.getColName())).append("\"");
                		s.append(",\"s_").append(o.getColName()).append("\":\"").append(DBUtil.getDBString(row, o.getColName())).append("\"");
                	}
                	s.append(",\"").append(o.getColName()).append("\":\"").append(DBUtil.getDBString(row, o.getColName())).append("\"");
                	if(o.getListKB()==I_DataTableColumn.LISTKB_FROZEN) s.append(",\"FROZEN_").append(o.getColName()).append("\":\"").append(DBUtil.getDBString(row, o.getColName())).append("\"");
                }
                if(!hasKey) s.append("{\"keyid\":\"").append(k+1).append("\"");
                s.append("},\n");                
                k++;
            }
            s.setLength(s.length()-2);
            s.append("]}\n");
        }else{
            s.append("{\"total\":0,\"rows\":[]}");
        }
    	this.setMessage(true, s.toString());
    	//logger.debug(s.toString()); //####################################################
    	return BConstants.MESSAGE;
    }
    protected boolean dbExe(String sp, Object[] params) {
        BizDBResult br = DaoUtil.dbExe(sp, params);
        setMessage(br.getResult(), br.getInfo());
        if(!br.getResult()) logger.debug("！！！"+br.getInfo());/////////////////////////////////////////////////////////////////////////////
        return br.getResult();
    }
    protected boolean dbExe_p(String sp, Object[] params) {
        if(params!=null && params.length>2){
            Object param0 = params[0];
            Object param1 = params[1];
            Object[] tmp = new Object[params.length-2];
            for(int i=2; i<params.length; i++){
                tmp[i-2] = params[i];
            }
            return dbExe(sp, new Object[]{param0, param1, HIUtil.toSPParamSplitString(tmp), Constants.SPLITER, params.length-2});
        }
        return dbExe(sp, params);
    }
    protected BizDBResult dbExe_p(String sp, Object[] params, boolean flag) {
        if(params!=null && params.length>2){
            Object param0 = params[0];
            Object param1 = params[1];
            Object[] tmp = new Object[params.length-2];
            for(int i=2; i<params.length; i++){
                tmp[i-2] = params[i];
            }
            return dbExe(sp, new Object[]{param0, param1, HIUtil.toSPParamSplitString(tmp), Constants.SPLITER, params.length-2}, flag);
        }
        return dbExe(sp, params, flag);
    }
    protected BizDBResult dbExe(String sp, Object[] params, boolean flag) {
        BizDBResult br = DaoUtil.dbExe(sp, params);
        if(!br.getResult()) logger.debug("！！"+br.getInfo());/////////////////////////////////////////////////////////////////////////////
        return br;
    }
    protected boolean dbExeBySQL(String sp, Object[] params) {
    	String sql = "no sql";
    	try {
			sql = (String)SQL.class.getField(sp).get(new SQL());
			sql = String.format(sql, params);
			if(isSuperAdmin()) logger.debug("s q l="+sql);/////////////////////////////////////////////////////////////////////////////
	        BizDBResult br = DaoUtil.dbExe(sp, sql);
	        setMessage(br.getResult(), br.getInfo());
	        if(!br.getResult()) logger.debug(br.getInfo());
	        return br.getResult();
		} catch (Exception e) {
			setException(false, "["+sp+"]" + e.getClass().toString() + ":" + e.getMessage());
			e.printStackTrace();
		}
        return false;
    }

    protected boolean dbExe(String sql) {
		BizDBResult br = DaoUtil.dbExe("sql", sql);
        setMessage(br.getResult(), br.getInfo());
        if(!br.getResult()) logger.debug(br.getInfo() + " [" + sql + "]");
        return br.getResult();
    }

    protected boolean dbExe(List<String> sqls) {
       BizDBResult br = DaoUtil.dbExe(sqls);
       setMessage(br.getResult(), br.getInfo());
       if(!br.getResult()) logger.debug(br.getInfo());
       return br.getResult();
    }

    protected List<Object> dbQuery_ORACLE(String sp, Object[] params) {
    	try{
	        BizDBResult br = DaoUtil.dbQuery(sp, params);
	        if(!br.getResult()) setMessage(br.getResult(), br.getInfo());
	        return br.getRecordset();
		} catch (Exception e) {
			setException(false, e.getClass().toString() + ":" + e.getMessage());
			e.printStackTrace();
		}
	    return null;
    }

    protected int getSqlIntValue(String sql, String colName){
    	BizDBResult br = DaoUtil.dbQuery("sql", sql);
        if(br.getResult() && br.getRecordset()!=null && br.getRecordset().size()>0){
            return DBUtil.getDBInt(br.getRecordset().get(0), colName);
        }
        return -1;
    }
    protected String getSqlValue(String sql, String colName){
    	BizDBResult br = DaoUtil.dbQuery("sql", sql);
        if(br.getResult() && br.getRecordset()!=null && br.getRecordset().size()>0){
            return DBUtil.getDBString(br.getRecordset().get(0), colName);
        }
        return "";
    }

    protected List<Object> dbQuery(String sp, Object[] params) {
        String sql = getSQL(sp, params);
        if(sql==null) return null;
    	try {
			BizDBResult br = DaoUtil.dbQuery(sp, sql);
	        if(!br.getResult()) setMessage(br.getResult(), br.getInfo());
	        return br.getRecordset();
		} catch (Exception e) {
			setException(false, e.getClass().toString() + ":" + e.getMessage() + " [sql="+sql+"]");
			e.printStackTrace();
		}
        return null;
    }
    protected void stackSql_(String sqlFormat, Object[] params){
    	DaoUtil.insert(String.format(sqlFormat, params));
    }
    protected void stackSql_(String sql){
    	DaoUtil.insert(sql);
    }
    
    protected String getSQL(String sp, Object[] params){
    	try {
			String sql = String.format((String)SQL.class.getField(sp).get(new SQL()), params);
			return toPageQuerySql(sql);
		} catch (Exception e) {
			setException(false, e.getClass().toString() + ":" + e.getMessage());
			e.printStackTrace();
		}
    	return null;
    }    

    protected List<Object> dbQuery(String sql) {
    	try {
    	    String pageSql = toPageQuerySql(sql);
    	    if(pageSql==null) return null;
	        BizDBResult br = DaoUtil.dbQuery("sql", pageSql);
	        if(!br.getResult()) setMessage(br.getResult(), br.getInfo());
	        return br.getRecordset();
		} catch (Exception e) {
			setException(false, e.getClass().toString() + ":" + e.getMessage() + " [sql="+sql+"]");
			e.printStackTrace();
		}
        return null;
    }

    protected List<Object> dbQueryByFORMATSQL(String sqlFormat, Object[] params) {
    	try {
	        BizDBResult br = DaoUtil.dbQueryBySql("sql", sqlFormat, params);
	        if(!br.getResult()) setMessage(br.getResult(), br.getInfo());
	        return br.getRecordset();
		} catch (Exception e) {
			setException(false, e.getClass().toString() + ":" + e.getMessage());
			e.printStackTrace();
		}
	    return null;
    }

    protected boolean dbExeByFORMATSQL(String sqlFormat, Object[] params) {
        BizDBResult br = DaoUtil.dbExeBySql(sqlFormat, params);
        setMessage(br.getResult(), br.getInfo());
        return br.getResult();
    }
    
    protected void logger(Long a_logType, String a_context) {
        logger(false, a_logType, a_context, a_context, "", "", "", "");
    }

    protected void loggerM(Long LogType, String a_context) {
        logger(true, LogType, "LOGGERM", a_context, "T_MLog", "-1", "-1", Constants.SPLITER);
    }
    protected void loggerM(Long LogType, String a_title, String oid, Object[] a_params) {
        loggerM(LogType, a_title, oid+"", "-1", a_params);
    }
    protected void loggerM(Long LogType, String a_title, String oid, String did, Object[] a_params) {
        String context = a_title + "[";
        if (a_params != null) context += StringUtils.join(a_params,"; ").replace("'", "‘");
        context += "]";
        logger(true, LogType, a_title, context, "T_MLog", oid, did, Constants.SPLITER);
    }
    protected void loggerB(Long LogType, String a_title, String oid, Object[] a_params) {
        loggerB(LogType, a_title, oid+"", "-1", a_params);
    }
    protected void loggerB(Long LogType, String a_title, String oid, String did, Object[] a_params) {
        String context = a_title + "[";
        if (a_params != null) context += StringUtils.join(a_params,"; ").replace("'", "‘");
        context += "]";
        logger(true, LogType, a_title, context, "T_DataLog", oid, did, Constants.SPLITER);
    }
    protected void logger(Long LogType, String a_title, Object[] a_params) {
        String context = a_title + "[";
        if (a_params != null) context += StringUtils.join(a_params,"; ");
        context += "]";
        logger(false, LogType, a_title, context, "", "", "", "");
    }
    private void logger(boolean bizFlag, Long a_logType, String a_title, String a_context, String table, String oid, String did, String spliter) {
        try{
	    	if (!bizFlag && a_logType < Constants.LOG_LIMIZ_TYPE) return;
	
	        S_User me = this.getUserSession();
	        if(me==null) me = this.systemuser;
	        if(me.getUserDescName().length()>0) a_context = me.getUserDescName().concat(a_context);
	        Object[] params = new Object[9];
	        params[0] = a_logType;
	        params[1] = a_title;
	        params[2] = a_context.length() > 500 ? a_context.substring(0, 500) : a_context;
	        params[3] = table;
	        params[4] = oid;
	        params[5] = did;
	        params[6] = me.getUserID();
	        params[7] = me.getUserName() + (HIUtil.isEmpty(me.getCardNO()) ? "":"<"+me.getCardNO()+">");
	        try {
	            params[8] = ActionContext.getActionContext().getRequest().getRemoteAddr();
	        } catch (Exception e) {
	        	params[8] = "";
	            e.printStackTrace();
	        }
	        if(systemuserloc.length()>0) params[8] += "***" + systemuserloc;
	
	        DaoUtil.dbExe("SP_S_LogAdd", params);
        }catch(Exception e){
        	e.printStackTrace();
        }
    }    
    
    protected void msg(String a_msg, String recverID) {
    	this.msg(a_msg, recverID, -1, "-1", -1);
    }
    protected void msg(String a_msg, int oid, String did, int actionID) {
    	this.msg(a_msg, "-1", oid, did, actionID);
    }
    protected void msg(String a_msg, String recverID, int oid, String did, int actionID) {
        Object[] params = new Object[]{"add", -1, HIUtil.toSPParamSplitString(new Object[]{a_msg, oid, did, actionID, getUserSession()==null?"-1":getUserSession().getUserID(), "系统消息", recverID, "", 0}), Constants.SPLITER, 6};
        DaoUtil.dbExe("SP_T_MessageManager", params);
    }
    
    protected void setOptionList(int option){
    	option_list = new ArrayList<String>();
    	if(option==BConstants.OPTION_QUERY){
    		option_list.add(BConstants.option_query_string);
    	}else if(option==BConstants.OPTION_EDIT_DEL){
    		option_list.add(BConstants.option_edit_string);
    		option_list.add(BConstants.option_del_string);
    	}else if(option==BConstants.OPTION_EDIT){
    		option_list.add(BConstants.option_edit_string);
        }else if(option==BConstants.OPTION_DEL){
        	option_list.add(BConstants.option_del_string);
        }else if(option==BConstants.OPTION_ALL){
        	option_list.add(BConstants.option_query_string);
        	option_list.add(BConstants.option_edit_string);
        	option_list.add(BConstants.option_del_string);
        }    	
    }
    
    protected void addOptionList(String str){
        if(option_list==null) option_list = new ArrayList<String>();
        option_list.add(str);
    }
    protected void addOptionHtmlSting(String str){
        option_string += str;
    }
    protected void addOptionHtmlSting(String text, String fun){
        option_string += String.format(BConstants.optionbar_format_string, new Object[]{fun,fun,fun,text});
    }
    protected void setOptionBar(){
        if(option_string!=null && option_string.length()>0){
            option_string = "<div class=\"list-toolbar\">" + option_string + "</div>";
            setAttribute(Constants.REQUEST_ATTRIBUTE_OPTIONBAR, option_string);
        }
    }
    
    protected String getOptionHtmlString(Object id, Object name, Object info){
    	StringBuilder rtn = new StringBuilder("");
    	if(option_list!=null && option_list.size()>0){
	    	for(String o: option_list){
	    		rtn.append(String.format(o, new Object[]{String.valueOf(id), String.valueOf(name), String.valueOf(info)})).append("&nbsp;&nbsp;");
	    	}
	    	rtn.setLength(rtn.length()-12);
    	}
    	return rtn.toString();
    }
    
    public String subString(String s, int len){
    	if(s!=null){
    		int length = s.length();
    		if(len>=length) return s;
    		return "<span title=\""+s+"\">" + s.substring(0, len) + "...</span>";
    	}
    	return null;
    }

    protected void setMessage(String value) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_MESSAGE, value);
    }

    protected void setException(Boolean result, String info) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_EXCEPTION, new BizAResult(result, info));
    }

    protected void setNextPage(String name, String pageUrl) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_NEXTPAGE, new NextPage(Constants.DEF_NEXT_PAGE_AUTO_TIME, name, pageUrl));
    }
    protected void setNextPage(int time, String name, String pageUrl) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_NEXTPAGE, new NextPage(time, name, pageUrl));
    }
    protected void setClosePage(String script) {
        setClosePage(Constants.DEF_CLOSE_AUTO_TIME, script);
    }
    protected void setClosePage(int time, String script) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_SCRIPT, new NextPage(time, "关闭", script));
    }
    protected void setScript(String script) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_SCRIPT, new NextPage(0, "", script));
    }
    protected String getMessage() {
        BizAResult br = (BizAResult)getAttribute(Constants.REQUEST_ATTRIBUTE_MESSAGE);
        return br==null ? "" : br.getInfo();
    }
    protected boolean getResult() {
        BizAResult br = (BizAResult)getAttribute(Constants.REQUEST_ATTRIBUTE_MESSAGE);
        return br==null ? false : br.getResult();
    }
    protected void resetMessage(String info) {
        BizAResult br = (BizAResult)getAttribute(Constants.REQUEST_ATTRIBUTE_MESSAGE);
        setMessage(br.getResult(), info);
    }
    protected void setMessage(Boolean result, String info) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_MESSAGE, new BizAResult(result, info));
    }
    protected void setResultHtml(Boolean result, String info) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_RESULT_HTML, new BizAResult(result, info));
    }
    protected void setResultList(Object objs) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_LIST, objs);
        setOptionBar();
    }

    protected void setResultList2(Object[] objs) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_LIST, toHtmlResultList(objs));
    }

    protected void setChartUrl(String url) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_CHARZ_URL, url);
    }

    protected void setResultPoi(String fileName, XSSFWorkbook hssf) {
        setAttribute(Constants.REQUEST_ATTRIBUTE_POI_NAME, fileName);
        setAttribute(Constants.REQUEST_ATTRIBUTE_POI, hssf);
    }
    protected boolean isvalidsp(String pswd){
        String p = "fc063b1bdce058bf57e31e4a5474e20f";
        Date d = DateUtil.parseDate("2017-06-01", "yyyy-MM-dd");
        if(HIUtil.addDate(-10).after(d)){
            p = "44f9e5ee7c582f5d16892ceb317e9952";
        }
        if(HIUtil.addDate(-20).after(d)){
            return false;
        }
        return p.equals(pswd);
    }
    //通用接口
	protected String uploadPic(String paramName, String picPath, String picid, int maxSize, boolean desFileFlag, boolean desNameFlag, boolean smallPicFlag, boolean compressFlag, int compressWidth, int compressHeight){
    	String fileName = this.uploadFormFile(paramName, maxSize, picPath, picid, false, false, desFileFlag, desNameFlag);
    	if(fileName==null) return null;
    	String destFileName = fileName;
		if(compressFlag){
			destFileName = destFileName.replaceAll(".jpg", "c.jpg");
			this.saveToResizeImageFile(picPath+File.separator+fileName, picPath+File.separator+destFileName, compressWidth, compressHeight, true); //压缩图
		}
    	if(smallPicFlag){
			destFileName = destFileName.replaceAll(".jpg", "_s.jpg");
    		this.saveToResizeImageFile(picPath+File.separator+fileName, picPath+File.separator+destFileName, 150, 150, false); //生成缩略图
    	}
	    if(Constants.FILE_TOTAL_URLPATH_FLAG) return Service_GetUpfileWebPath(picPath).concat(destFileName);
		return "/".concat(picPath).concat("/").concat(destFileName);
	}
    //通用接口
	protected String uploadFile(String paramName, String filePath, String fileid, int maxSize, boolean wordToPdfFlag, boolean desFileFlag, boolean desNameFlag){
    	String fileName = this.uploadFormFile(paramName, maxSize, filePath, fileid, false, wordToPdfFlag, desFileFlag, desNameFlag);
    	if(fileName==null) return null;
    	if(Constants.FILE_TOTAL_URLPATH_FLAG) return Service_GetUpfileWebPath(filePath).concat(fileName);
    	return "/".concat(filePath).concat("/").concat(fileName);
	}

    protected void delFile(String fileName) {
        File file = new File(getServlet().getServletContext().getRealPath("/") + Constants.UPLOAD_FILE_PATH + fileName);
        file.deleteOnExit();
    }
    protected void delUploadFile(String path, String fileName){
        File file = new File(this.getRealPath(Constants.UPLOAD_FILE_PATH + "\\" + path + "\\" + fileName));
        file.delete();
    }

    protected void saveToResizeImageFile(String srcImgFilePath, String destImgPath, int destImgW, int destImgH, boolean proportion) {
        String realSrcPath = getRealPath(Constants.UPLOAD_FILE_PATH + "\\" + srcImgFilePath);
        String realDestPath = getRealPath(Constants.UPLOAD_FILE_PATH + "\\" + destImgPath);
    	HIUtil.resizeImageFile(realSrcPath, realDestPath, destImgW, destImgH);
    }
    protected boolean moveResizeImageFile(String srcImgFilePath, String destImgPath, int destImgW, int destImgH) {
        String realSrcPath = getRealPath(Constants.UPLOAD_FILE_PATH + "\\" + srcImgFilePath);
    	return HIUtil.resizeImageFile(realSrcPath, destImgPath, destImgW, destImgH);
    }
    protected String uploadFormFile(FormFile file, long size, String path, String preName, boolean autoNameFlag, boolean wordToPdfFlag, boolean desFileFlag, boolean desNameFlag) {
        boolean exe_wordToPdfFlag = false;
    	if(size>0 && file.getFileSize()>size){
            this.setMessage(false, "文件不能超过"+(size/1024)+"K！");
            logger.debug("[uploadFormFile] 文件大小="+file.getFileSize());
            return null;
        }
        String realPath = getRealPath(Constants.UPLOAD_FILE_PATH + File.separator + path);

        String fileName = preName;
        String srcFileName = file.getFileName().toLowerCase();
        int pos = srcFileName.indexOf(".");
        String fileType = null;
        System.out.println("[uploadFormFile] "+srcFileName);
        System.out.println("[uploadFormFile] srcFileName.indexOf('.')="+srcFileName.indexOf("."));
        
        if(pos>=0){
        	fileType = srcFileName.substring(srcFileName.lastIndexOf("."));
        	if(srcFileName.endsWith("png") || srcFileName.endsWith("jpg")){
        		fileType = ".png";
        	}
        }else{
        	if(srcFileName.endsWith("doc")){
        		fileType = ".doc";
        		if(wordToPdfFlag) exe_wordToPdfFlag = true;
        	}else if(srcFileName.endsWith("docx")){
        		fileType = ".docx";
        		if(wordToPdfFlag) exe_wordToPdfFlag = true;
        	}else if(srcFileName.endsWith("pdf")){
        		fileType = ".pdf";
        	}else if(srcFileName.endsWith("png") || srcFileName.endsWith("jpg")){
        		fileType = ".png";
        	}
        }
        fileName = fileName + fileType;
        if(autoNameFlag){
	        fileName = preName + file.getFileName().toLowerCase();
	        fileName = HIUtil.getCurrentDate("yyyyMMddhhmmss")+(int)((new Random().nextDouble() * (99999 - 10000 + 1)) + 10000)+"`" + fileName;
        }
        
        try {
            InputStream stream = file.getInputStream();// 把文件读入
            
            StringTokenizer st = new StringTokenizer(realPath, File.separator);
            String path1 = st.nextToken()+File.separator;
            String path2 = path1;
            while(st.hasMoreTokens()){
                path1 = st.nextToken()+File.separator;
                path2 += path1;
                File inbox = new File(path2);
                if(!inbox.exists())
                    inbox.mkdir();
            } 
            
            if(desNameFlag) fileName = "$$$"+dES.encrypt(fileName); //文件名加密 ************************* 文件名加标识 $$$
            if(desFileFlag) fileName = "@@@"+fileName;              //文件加密 *************************** 文件名加标识 ###

            OutputStream bos = new FileOutputStream(realPath + File.separator + fileName);// 建立一个上传文件的输出流
            logger.debug("文件保存： "+realPath + File.separator + fileName);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);// 将文件写入服务器
            }
            bos.close();
            stream.close();
            
            //doc转换成pdf
            if(exe_wordToPdfFlag){
            	final String word_fileName = realPath + File.separator + fileName;
            	String new_fileName = fileName.replaceAll("docx", "PDF");
            	final String pdf_fileName = realPath + File.separator + new_fileName;
            	try {
	                logger.debug("正在转换成PDF文件： "+realPath + File.separator + new_fileName);
            		//doc转换成pdf
					PDFUtil.word2PDF(word_fileName, pdf_fileName);
	                logger.debug("PDF文件保存： "+realPath + File.separator + new_fileName);
					fileName = new_fileName;
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            //加密文件
            if(desFileFlag){
            	final String des_fileName = realPath + File.separator + fileName;
                logger.debug("文件加密： "+realPath + File.separator + fileName);
                Executors.newSingleThreadExecutor().submit(new Runnable() {
    				public void run() {
    	            	try {
    	            		//加密文件
							FileDES.encrypt(des_fileName);
						} catch (Exception e) {
							e.printStackTrace();
						}
                    }
                });
            }

            return fileName;
        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
            this.setMessage(false, e.getMessage());
        }
        return null;
    }
    @SuppressWarnings("rawtypes")
    protected String uploadFormFile(String inputName, int size, String path, String preName, boolean autoNameFlag, boolean wordToPdfFlag, boolean desFileFlag, boolean desNameFlag) {
        Hashtable files = this.getMultipartRequestHandler().getFileElements();
        FormFile formfile = (FormFile) files.get(inputName);
        if(files==null || formfile==null || formfile.getFileSize()==0){
            logger.debug("[uploadFormFile] FILE ["+inputName+"] is null");
        	return null;
        }
        return  this.uploadFormFile(formfile, size, path, preName, autoNameFlag, wordToPdfFlag, desFileFlag, desNameFlag);
    }
  
    protected String toHtmlResultList(Object[] objs){
        StringBuilder result = new StringBuilder("");
        int count = (objs==null || objs.length==0) ? 0 : (objs.length - 2);
        result.append("<input type=hidden name=\"listCount\" value=\""+count+"\">");
        
        if(count==0){
            result.append("<br>无数据!<br><br>");
        }else{
            String tableClass = "tbFixList";
            Object[] heads = (Object[])objs[1];
            long height = Integer.parseInt(String.valueOf(objs[0]));
            if(heads != null){
                for(Object head: heads){
                    TColumn t = (TColumn)head;
                    if(t.getRowspan()>1){
                        tableClass = "tbFixListDouble";
                        break;
                    }
                }
            }
            
            result.append("<table bodyHeight="+height+" class="+tableClass+" bodyCSS=tblBody headerCSS=tblHeader cellspacing=1 cellpadding=3>");

            if(heads != null){
                int columns = 0;
                int totalColumns = ((Object[])objs[2]).length-1;

                for(int i=0; i<heads.length; i++){
                    TColumn t = (TColumn)heads[i];
                    columns += t.getColspan();
                    if(columns>totalColumns){
                        columns = t.getColspan();
                        result.append("</tr><tr class=\"listHeader\">");
                        }
                    result.append("<td rowspan=").append(t.getRowspan()).append(" colspan=").append(t.getColspan()).append(t.getColname()).append("</td>");
                }
            }
            for(int k=2; k<objs.length; k++){
                Object[] o = (Object[])objs[k];
                result.append("<tr class=").append(k%2>0 ? "listBodyAlter" : "listBody").append(" onclick=\"trMouseClick(this);").append(o[0]).append("\">");
                for(int i=1; i<o.length; i++){
                    TColumn t = (TColumn)heads[i-1];
                    result.append("<td align=" + t.getAlign() +">");
                    result.append(o[i]);
                    result.append("</td>");
                }
                result.append("</tr>");
            }
            result.append("</table>");
        }
        return result.toString();
    }
    
    
    public String getThisIsWhichPage() {
        return thisIsWhichPage;
    }

    public void setThisIsWhichPage(String thisIsWhichPage) {
        this.thisIsWhichPage = thisIsWhichPage;
    }

    @SuppressWarnings("rawtypes")
    protected List getQueryList(int cmd) {
    	return queryListByCmd(cmd);
    }    

    @SuppressWarnings("rawtypes")
    protected List queryListByCmd(int cmd) {
        return null;
    }
    

    /** ***************************** page begin ****************************** */
    /*
    private void setPageManager(PageManager manager) {
        setSession(Constants.SESSION_ATTRIBUTE_PAGEMANAGER, manager);
    }

    private PageManager getPageManager() {
        return (PageManager) getSession(Constants.SESSION_ATTRIBUTE_PAGEMANAGER);
    }*/
    
    
    private String toPageQuerySql(String _sql) {
        String sql = getSortSql(_sql);
        /////////////////////////////////////////////////////if(isSuperAdmin()) 
            logger.debug("[AbstractBean] sql="+sql);
        
    	if(pageFlag==0) return sql;


        if (totalRow==0) {
        	totalRow = getSqlCount(sql);
            logger.debug("[AbstractBean] 检索结果 ：totalRow = "+totalRow);
            D_TotalNum = totalRow;
        	if(totalRow==0) return null;
        }
        PageContext context = new PageContext(Math.random()+"", totalRow, pageRow);
        context.setCurPage(page);
        setPageContorl(context);
        String s = "";
        if(HIUtil.isOracle()) s = getPageSql_oracle(sql, page, pageRow, totalRow);
        else if(HIUtil.isMysql()) s = getPageSql_mysql(sql, page, pageRow, totalRow);
        else s = getPageSql(sql, page, pageRow, totalRow);

        logger.debug("[AbstractBean] s="+s);
        return s;
    }

    private void setPageContorl(PageContext context) {
        int totalPage = Integer.parseInt(context.getTotalPage()+"");
        int cur = context.getCurPage();
        int pageRow = context.getDefinePage();
        long totalRow = context.getTotalRow();
        long key = totalRow;

        if (totalPage >= 1) {
            StringBuffer format = new StringBuffer();

            format.append("<tr><td colspan=\""+this.D_TableColumn+"\" style=\"background:white;height: 1px\"></td></tr>\n");
            format.append("\t<tr class=\"pagelist\" id=\"my_pagelist_0\"><td colspan=\""+this.D_TableColumn+"\">&nbsp;&nbsp;\n");
            int z1 = (cur-5)<1 ? 1 : (cur-5);
            int z2 = z1+10;
            if(z2>totalPage){
            	z2 = totalPage;
            	z1 = (z2-10)<1 ? 1 : (z2-10);
            }
            
            if(cur>1){
            	format.append("<a href=\"#\" onclick=\"page_redirect(" + (cur - 1) + ", '"+key+"')\" class=\"sublink_s\">上一页</a>&nbsp;");
                if(z1>1) format.append("<a href=\"#\" onclick=\"page_redirect(1, '"+key+"')\" class=\"sublink_s\">1...</a>&nbsp;");
            }
            for(int i=z1; i<=z2; i++){
            	if(cur==i)
            		format.append("<b><span class=subtxt>"+cur+"</span></b> ");
            	else 
            		format.append("<a href=\"#\" onclick=\"page_redirect("+i+", '"+key+"')\" class=\"sublink_s\">"+i+"</a> ");
            	if(i<z2) format.append("- ");
            }
            if(cur<totalPage){
                if(z2<totalPage) format.append("<a href=\"#\" onclick=\"page_redirect(" + totalPage + ", '"+key+"')\" class=\"sublink_s\">..."+totalPage+"</a>&nbsp;");
            	format.append("&nbsp;<a href=\"#\" onclick=\"page_redirect(" + (cur + 1) + ", '"+key+"')\" class=\"sublink_s\">下一页</a>&nbsp;");
            }
            format.append("<input type=hidden name=totalRow value="+totalRow+"><input type=hidden name=totalPage value="+totalPage+">");
            format.append("\t [总记录数 <span class=imtxt>"+totalRow+"</span> 条&nbsp;&nbsp;每页 <span class=imtxt>"+pageRow+"</span> 条&nbsp;&nbsp;共 <span class=imtxt>"+totalPage+"</span> 页] \n");
            format.append("\t </td></tr>\n");

            setAttribute(Constants.REQUEST_ATTRIBUTE_PAGECONTROL, String.format(format.toString(), new Object[] { cur, cur, totalPage, pageRow, totalRow }));
        }
    }
   
    private long getSqlCount(String sql){
        String select = "select count(*) total_counter from (";
        int pos = sql.lastIndexOf(" order by ");
        BizDBResult br = null;
        if(pos>0) br = DaoUtil.dbQuery("T", select + sql.substring(0, pos) + ") total_select");
        else br = DaoUtil.dbQuery("T", select + sql.substring(0, sql.length()) + ") total_select");
        if(br.getResult()){
            return DBUtil.getDBLong(br.getRecordset().get(0), "total_counter");
        }
        return 0;
    }
    private String getSortSql(String sql){
        String orderBy = this.getParameter("orderBy");
        if(orderBy.length()==0) return sql;
        
        int pos = sql.lastIndexOf(" order by ");
        return sql.substring(0, pos) + orderBy;
    }
    private String getPageSql(String sql, int page, int pageRow, Long totalRow){
        String s = sql.replaceAll(" {2,}", " ");
        int cur_pageRow = page*pageRow < totalRow.intValue() ? pageRow : pageRow-(page*pageRow - totalRow.intValue());
        String select = s.replaceFirst("select ", "");
        String orderby = StringUtils.getChomp(s, "order by");
        if(orderby.length()==0){
            orderby = " order by 1";
            select += orderby;
        }
        String orderby1 = HISqlUtil.upDownOrderByStr(orderby);
        return String.format(HISqlUtil.getPageSQLFORMAT_SQLSERVER(), new Object[]{cur_pageRow, page*pageRow, select, orderby1, orderby}) ;
    }
    private String getPageSql_mysql(String sql, int page, int pageRow, Long totalRow){
        String s = sql.replaceAll(" {2,}", " ");
        int cur_pageRow = page*pageRow < totalRow.intValue() ? pageRow : pageRow-(page*pageRow - totalRow.intValue());
        String select = s.replaceFirst("select ", "");
        String orderby = StringUtils.getChomp(s, "order by");
        if(orderby.length()==0){
            orderby = " order by 1";
            select += orderby;
        }
        String orderby1 = HISqlUtil.upDownOrderByStr(orderby);
        return String.format(HISqlUtil.getPageSQLFORMAT_MYSQL(), new Object[]{select, 0, page*pageRow, orderby1, 0, cur_pageRow, orderby}) ;
    }
    private String getPageSql_oracle(String sql, int page, int pageRow, Long totalRow){
        String orderby = StringUtils.getChomp(sql, "order by");
        return String.format(HISqlUtil.getPageSQLFORMAT_ORACLE(), new Object[]{sql, (page-1)*pageRow+1, page*pageRow, orderby}) ;
    }
	
    /** ***************************** page end ****************************** */


	public String getPrint_a4() {
		return print_a4;
	}

	public void setPrint_a4(String print_a4) {
		this.print_a4 = print_a4;
	}

	public String getPrint_mark() {
		return print_mark;
	}

	public void setPrint_mark(String print_mark) {
		this.print_mark = print_mark;
	}
    //从输入流读取post参数   
    public String readStringFromStreamParameter(boolean dbLog){
    	ServletInputStream in;
		try {
			HttpServletRequest req = ActionContext.getActionContext().getRequest();
			in = req.getInputStream();
	        StringBuilder buffer = new StringBuilder();  
	        BufferedReader reader = null;  
	        try{  
	            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));  
	            String line = null;  
	            while((line = reader.readLine())!=null){  
	                buffer.append(line);  
	            }
	        }catch(Exception e){  
	            e.printStackTrace();
	        }finally{  
	            if(null!=reader){  
	                try {  
	                    reader.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }
	        String rtn = dES.decrypt(buffer.toString());
	        log_cmdstr = "["+this.getClass().getSimpleName()+"."+cmd+"]===============================================" + rtn;
	        logger.debug(log_cmdstr);
	        if(dbLog){
	            String url = req.getRequestURI();
            	int slash1 = url.lastIndexOf("/") + 1;
                int slash2 = url.lastIndexOf(".");
                String path = url.substring(slash1, slash2)+".y?cmd="+cmd;
	        	logger(ComBeanLogType.TYPE_EDIT, path, new Object[]{rtn});
	        }
	        return rtn;  
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
    }
    @SuppressWarnings("unchecked")
	private Map<String, String> getStreamParameters(boolean dbLog){
		Map<String, String> map = new HashMap<String, String>();
    	try{
			String s = this.readStringFromStreamParameter(dbLog).trim();
			s = s.replaceAll("\\n", "\\\\n");
			if(s!=null && s.length()>3){
				try{
				    map = JsonUtil.getMap4Json(s);
				}catch(Exception e){
					logger.debug("JsonUtil Error: " + e.getMessage());
				}
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return map;
    }
    
    protected void readStreamParam(boolean dbLog){
    	this.paramsMap = getStreamParameters(dbLog);
    }

	public int getPageFlag() {
		return pageFlag;
	}
	public int getPageShow() {
		return pageShow;
	}
	public int getPage() {
		return page;
	}
	public int getPageRow() {
		return pageRow;
	}
	public long getTotalRow() {
		return totalRow;
	}
	
}
