package com.svv.dms.web.service.system;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.S_Log;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.TColumn;


public class S_LogBean extends AbstractBean {
    private static final int QUERY_CMD_List_S = 102;
    private static final int QUERY_CMD_List_B = 103;
    private static final int QUERY_CMD_List_M = 104;
    private static final int QUERY_CMD_List_T = 105;
    private static final int QUERY_CMD_List_FORQUERY = 106;
    private static final int QUERY_CMD_List_FORUSER = 107;
    private static final int QUERY_CMD_List_FORORGAN = 108;
    
    public String S_Log(){
        return this.exeByCmd("");
    }
    public String S_LogQuery(){
        return this.exeByCmd("ForQuery");
    }
    public String M_LogQuery(){
        return this.exeByCmd("M");
    }   
    
    public String S_LogForUser(){
        return this.exeByCmd("ForUser");
    }   
    
    public String S_LogForOrgan(){
        return this.exeByCmd("ForOrgan");
    }
    public String query(){
        setResult_List(getQueryList(QUERY_CMD_List));        
        return BConstants.LIST;
    }

    public String queryForQuery(){
        setResult_List(getQueryList(QUERY_CMD_List_FORQUERY));        
        return BConstants.LIST;
    }
    
    public String queryForUser(){
        setResult_List(getQueryList(QUERY_CMD_List_FORUSER));        
        return BConstants.LIST;
    }

    public String queryForOrgan(){
        setResult_List(getQueryList(QUERY_CMD_List_FORORGAN));        
        return BConstants.LIST;
    }

    public String queryS(){
        setResult_List(getQueryList(QUERY_CMD_List_S));        
        return BConstants.LIST;
    }
    public String queryB(){
        setResult_List(getQueryList(QUERY_CMD_List_B));        
        return BConstants.LIST;
    }
    public String queryM(){
        setResult_List(getQueryList(QUERY_CMD_List_M));        
        return BConstants.LIST;
    }
    public String queryT(){
        setResult_List(getQueryList(QUERY_CMD_List_T));        
        return BConstants.LIST;
    }

    //为了实现在同一张表中显示不同的字段
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected List queryListByCmd(int cmd) {
    	List<S_Log> list = null;
        if(cmd == QUERY_CMD_List_S){
            c_startTime = this.getParameterStartDate("c_startTime");
            c_endTime = this.getParameterEndDate("c_endTime");
            c_keyword = this.getParameter("c_keyword");
            c_logType = this.getParameter("c_logType");
            list = getList(S_Log.class, dbQuery(SQL.SP_LogQueryByC("T_SLog", c_startTime,c_endTime,c_keyword,c_logType)));
        }else if(cmd == QUERY_CMD_List_B){
            c_startTime = this.getParameterStartDate("c_startTime");
            c_endTime = this.getParameterEndDate("c_endTime");
            c_keyword = this.getParameter("c_keyword");
            c_logType = this.getParameter("c_logType");
            list = getList(S_Log.class, dbQuery(SQL.SP_LogQueryByC("T_DataLog", c_startTime,c_endTime,c_keyword,c_logType)));
            
        }else if(cmd == QUERY_CMD_List_M){
            c_startTime = this.getParameterStartDate("c_startTime");
            c_endTime = this.getParameterEndDate("c_endTime");
            c_keyword = this.getParameter("c_keyword");
            c_logType = this.getParameter("c_logType");
            list = getList(S_Log.class, dbQuery(SQL.SP_LogQueryByC("T_MLog", c_startTime,c_endTime,c_keyword,c_logType)));
            
        }else if(cmd == QUERY_CMD_List_T){
            c_startTime = this.getParameterStartDate("c_startTime");
            c_endTime = this.getParameterEndDate("c_endTime");
            c_keyword = this.getParameter("c_keyword");
            c_logType = this.getParameter("c_logType");
            list = getList(S_Log.class, dbQuery(SQL.SP_LogQueryByC("T_Log", c_startTime,c_endTime,c_keyword,c_logType)));
            
        }else if(cmd == QUERY_CMD_List_FORUSER){
            list = getList(S_Log.class, dbQuery(SQL.SP_LogQueryByUserC("T_SLog", c_startTime,c_endTime,c_keyword,this.getUserSession().getUserID())));
            
        }else if(cmd == QUERY_CMD_List_FORORGAN){
            c_startTime = this.getParameterStartDate("c_startTime");
            c_endTime = this.getParameterEndDate("c_endTime");
            c_keyword = this.getParameter("c_keyword");
            list = getList(S_Log.class, dbQuery(SQL.SP_LogQueryByOrganC("T_SLog", c_startTime,c_endTime,c_keyword,this.getUserSession().getOrganID())));

        }else if(cmd == QUERY_CMD_List_FORQUERY){
            c_startTime = this.getParameterStartDate("c_startTime");
            c_endTime = this.getParameterEndDate("c_endTime");
            c_keyword = this.getParameter("c_keyword");
            list = getList(S_Log.class, dbQuery(SQL.SP_LogQueryByC("T_SLog", c_startTime,c_endTime,c_keyword)));
        }
        
        return list;
    }

    public String dels(){
        c_startTime = this.getParameterStartDate("c_startTime");
        c_endTime = this.getParameterEndDate("c_endTime");
        c_keyword = this.getParameter("c_keyword");        
        dbExe(SQL.SP_LogDelByC("S_Log", c_startTime, c_endTime, c_keyword));                
        return BConstants.MESSAGE;
    }
    public String delsM(){
        c_startTime = this.getParameterStartDate("c_startTime");
        c_endTime = this.getParameterEndDate("c_endTime");
        c_keyword = this.getParameter("c_keyword");        
        dbExe(SQL.SP_LogDelByC("M_Log", c_startTime, c_endTime, c_keyword));                
        return BConstants.MESSAGE;
    }
    public String delsB(){
        c_startTime = this.getParameterStartDate("c_startTime");
        c_endTime = this.getParameterEndDate("c_endTime");
        c_keyword = this.getParameter("c_keyword");        
        dbExe(SQL.SP_LogDelByC("B_DataLog", c_startTime, c_endTime, c_keyword));                
        return BConstants.MESSAGE;
    }
    public String delsT(){
        c_startTime = this.getParameterStartDate("c_startTime");
        c_endTime = this.getParameterEndDate("c_endTime");
        c_keyword = this.getParameter("c_keyword");        
        dbExe(SQL.SP_LogDelByC("T_Log", c_startTime, c_endTime, c_keyword));                
        return BConstants.MESSAGE;
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void setResult_List(List list){
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{//new TColumn("<input type=checkbox class=checkbox name=\"chb_all\" onclick=\"selectAll(this.checked);\">选择", TColumn.ALIGN_LEFT),                                   
                                   new TColumn("操作员", "userName"),
                                   new TColumn("内容", "title"),
                                   new TColumn("日期", TColumn.ALIGN_LEFT, 130),
                                   new TColumn("IP地址", "ipAddress")};
            for(S_Log o: (List<S_Log>)list){
                objs[i++] = new Object[]{"",
                                         //"<input type=checkbox class=checkbox name=listradio value=\""+o.getID()+"\" onclick=\"selectControler(this);\">",
                                         o.getUserName(),                                       
                                         o.getContext().length()>50 ? "" : "<div title=\""+o.getContext()+"\" style=\"width:300px;\">"+o.getContext()+"</span>",
                                         o.getIstDate(),
                                         o.getIpAddress()
                                         };
                if(o.getContext().length()>50){
                	objs = (Object[])HIUtil.resizeArray(objs, objs.length+1);
                	objs[i++] = new Object[]{"", HIUtil.htmlEncode(o.getContext())};
                }
            }
            
        }
        this.setResultList(objs);
    }
    
//    @SuppressWarnings({ "unchecked", "unused" })
//    private void setDataResult_List(List list){
//        Object[] objs = null;
//        if(list!=null && list.size()>0){
//            int i = 0;
//            objs = new Object[list.size()+2];
//            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
//            objs[i++] = new Object[]{//new TColumn("<input type=checkbox class=checkbox name=\"chb_all\" onclick=\"selectAll(this.checked);\">选择", TColumn.ALIGN_CENTER),                                   
//                                   new TColumn("操作员", "userName", TColumn.ALIGN_CENTER),
//                                   new TColumn("内容", "title", TColumn.ALIGN_CENTER),
//                                   new TColumn("登入时间", "ipAddress", TColumn.ALIGN_CENTER);
//            for(S_Log o: (List<S_Log>)list){
//                objs[i++] = new Object[]{"",
//                                         //"<input type=checkbox class=checkbox name=listradio value=\""+o.getID()+"\" onclick=\"selectControler(this);\">",
//                                         o.getUserName(),                                       
//                                         "<span title=\""+o.getContext()+"\">"+o.getTitle()+"</span>",
//                                         o.getIpAddress(),
//                                         o.getIstDate()};
//            }
//            
//        }
//        this.setResultList(objs);
//    }
        
    private static final long serialVersionUID = -2503008236559105766L;
    
    private String id = "";
    private String c_oid = "";
    private String c_startTime = HIUtil.getCurrentDate();
    private String c_endTime = HIUtil.getCurrentDate();
    private String c_keyword = "";
    
    private String c_logType = "";
    private String c_UserName="";
    
    public List<LabelValueBean> getLogTypeList() {
        return ComBeanLogType.getList(true);
    }
    
    public String getC_logType() {
        return c_logType;
    }

    public void setC_logType(String type) {
        c_logType = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getC_startTime() {
        return c_startTime;
    }

    public void setC_startTime(String c_startTime) {
        this.c_startTime = c_startTime;
    }
    
    public String getC_endTime() {
        return c_endTime;
    }

    public void setC_endTime(String c_endTime) {
        this.c_endTime = c_endTime;
    }

    public String getC_keyword() {
        return c_keyword;
    }

    public void setC_keyword(String c_keyword) {
        this.c_keyword = c_keyword;
    }

    public String getC_oid() {
        return c_oid;
    }

    public void setC_oid(String c_oid) {
        this.c_oid = c_oid;
    }

	public String getC_UserName() {
		return c_UserName;
	}

	public void setC_UserName(String userName) {
		this.c_UserName = userName;
	}
}
