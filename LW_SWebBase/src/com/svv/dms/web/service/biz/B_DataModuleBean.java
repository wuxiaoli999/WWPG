package com.svv.dms.web.service.biz;

import java.util.List;
import java.util.TreeMap;

import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanI_DataModule;
import com.svv.dms.web.common.ComBeanI_DataParamType;
import com.svv.dms.web.common.ComBeanI_DataTable;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.dao.SQL_0;
import com.svv.dms.web.entity.I_DataModule;
import com.svv.dms.web.entity.I_DataTable;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;

public class B_DataModuleBean extends B_DataBean {

    public String B_DataModule(){
        if("".equals(cmd) && ao.getModuleID().length() > 0){
            if(!this.checkUserPower("B_DataModule_".concat(ao.getModuleID()))){
                this.setMessage(false, "您没有访问权限，请联系系统管理员！");
                return BConstants.MESSAGE;
            }
        }
        return this.exeByCmd("");
    }

    protected String saveQueryCondition(){
        StringBuilder con_sql = new StringBuilder();
        StringBuilder con_url = new StringBuilder();
        List<I_DataTableColumn> collist = ao.getModule().getColumns_querycond();
        if(collist!=null && collist.size()>0){
            for(I_DataTableColumn o: collist){
                String tmp_id = this.getParameter(o.getColName());
                if(o.getExtValueScopeTypeID()>0){
                    int paramTypeID = o.getExtValueScopeTypeID();
                    //数据应用字典
                    if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_SYS_DATAPARAM){
                        //if(o.getNullFlag().equals("0") || tmp_id.length()>0){
                        if(tmp_id.length()>0){
                        	if(o.getExtValueScopeTypeParam().length()>3)
                                con_sql.append(SQL_0.getWhere_like(o.getColName_as(), tmp_id));
                        	else
                                con_sql.append(SQL_0.getWhere_number(o.getColName_as(), tmp_id));
                        }

                    //其他业务数据
                    }else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE){
                        //if(o.getNullFlag().equals("0") || tmp_id.length()>0){
                        if(tmp_id.length()>0){
                            con_sql.append(SQL_0.getWhere_number(o.getColName_as(), tmp_id));
                        }
                    }
                }else if(tmp_id.startsWith("!")){
                	if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_NUMBER)
                		con_sql.append(SQL_0.getWhere_not(o.getColName_as(), tmp_id.substring(1)));
                	else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_VARCHAR2)
                		con_sql.append(SQL_0.getWhere_notlike(o.getColName_as(), tmp_id.substring(1)));
                }else if(tmp_id.startsWith(">=") || tmp_id.startsWith("<=")){
                    con_sql.append(SQL_0.getWhere_number(o.getColName_as(), tmp_id.substring(2), tmp_id.substring(0,2)));
                }else if(tmp_id.startsWith(">") || tmp_id.startsWith("<")){
                    con_sql.append(SQL_0.getWhere_number(o.getColName_as(), tmp_id.substring(1), tmp_id.substring(0,1)));

                }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_NUMBER || o.getDataType()==ParamClass.VALUE_DATA_TYPE_CHAR){
                    con_sql.append(SQL_0.getWhere_number(o.getColName_as(), tmp_id));
                }else{
                    con_sql.append(SQL_0.getWhere_like(o.getColName_as(), tmp_id));
                }
                con_url.append("&").append(o.getColName_as()).append("=").append(tmp_id);
            }
        }
        String sql = SQL.SP_B_DataQueryByC(ao.getModule().getViewSql(), "", "", getKeywordColNames(), c_keyword, con_sql.toString());
        this.setAttribute("QUERY_CONDITION_STR", con_url);
        this.setAttribute("QUERY_CONDITION_SQL", dES.encrypt(sql));
        //return SQL.SP_B_DataQueryByC(ao.getTableID(), ao.getTableName(), ao.getParentDataid()+"", ParamClass.VALUE_DATA_STATUS_BEGIN+"", this.getUserSession().getSctLevel()+"", getKeywordColNames(), c_keyword, con_sql);
        return sql;
    }
    
    public String exeByCmd(String pre){
        if("".equals(cmd) && ao.getModuleID().length() > 0){
            I_DataModule module = ComBeanI_DataModule.get(ao.getModuleID());
            if(module == null){
                this.setMessage(false, "此模块正在开发中，或联系系统管理员！");
                return BConstants.MESSAGE;
            }
            
            ao.setModule(module);
            initQueryCondition();
        }
        return super.exeByCmd("");
    }
    
    protected void initQueryCondition(){
        StringBuilder s = new StringBuilder("");
        StringBuilder ins = new StringBuilder("");
        List<I_DataTableColumn> collist = ao.getModule().getColumns_querycond();
        if(collist!=null && collist.size()>0){
            String con_keyword = null;
            int i = 0;
            for(I_DataTableColumn o: collist){
                /***********************************/
                String relation_fun  = getRelationFun(ao.getModule(), o, null);
                /***********************************/
                
                if(con_keyword==null && o.getExtKeywordFlag()==1){
                    con_keyword = "<span title=\"关键字仅查询列名带*号的字段\">关键字: <input type=text class=ipt_text name=\"c_keyword\" size=\"20\"  onkeypress=\"if(event.keyCode==13) {btn_query.click();return false;}\"/></span>";
                }else{
                	if(i>0 && i%4==0) s.append("<br>");
                	i++;
                	
                	if(o.getExtValueScopeTypeID()>0){
	                    int paramTypeID = o.getExtValueScopeTypeID();
	                    String tmp_id = "";
	                    //数据应用字典
	                    if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_SYS_DATAPARAM){
	                        int param = Integer.parseInt(o.getExtValueScopeTypeParam());
	                        
	                        s.append(o.getColMemo()).append(": ");
	                        if(param<1000){ //三位数的数据字典以select框显示
	                            s.append(HIUtil.genHtmlSelect(true, o.getColName(), ComBeanI_DataParamType.getNameMap(isSuperAdmin(), Integer.parseInt(""+o.getExtValueScopeTypeParam())), tmp_id, true, relation_fun)); //o.getNullFlag().equals("1")
	                            s.append("&nbsp;&nbsp;");
	                        }else{
	                            s.append("<input type=hidden name=\"").append(o.getColName()).append("\">");
	                            s.append("<input type=text class=ipt_text name=\"TMP_").append(o.getColName()).append("\" size=20 onchange=\"if(this.value=='') d('"+o.getColName()+"').value='';\"");
	                            s.append(" onclick=\"CommonFocus();selectFromDataParamType(d('"+o.getColName()+"'))\"");
	                            s.append(">&nbsp;");
	                            s.append("<input type=hidden name=\"PID_").append(o.getColName()).append("\" value="+o.getExtValueScopeTypeParam()+">");
	                        }
	                    //其他业务数据
	                    }else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE){
	                        int param = Integer.parseInt(o.getExtValueScopeTypeParam());
	                        I_DataTable tmptable = ComBeanI_DataTable.get(param);
	                        //系统标识表
	                        if(tmptable.getSidFlag()==1){
	                            if(getSession("SSID_"+tmptable.getTableID()) != null) tmp_id = String.valueOf(getSession("SSID_"+tmptable.getTableID()));
	                            relation_fun += "SetCurrentSID('SSID_"+tmptable.getTableID()+"','"+o.getColName()+"');page_redirect();";
	                        }
	                        if(o.getListShowFlag().equals("0")){
	                        	s.append("<input type=hidden name=\"").append(o.getColName()).append("\" value=\"").append(tmp_id).append("\">");
	                        }else{
		                        TreeMap<Long, String> tmpMap = tmptable.getDataKeyNameMap(null, null, null, null, null, null);
		                        s.append(o.getColMemo()).append(": ");
		                        //select框选择
		                        s.append(HIUtil.genHtmlSelect(true, o.getColName(), tmpMap, tmp_id, o.getNullFlag().equals("1"), "CommonController(this);"+relation_fun));
		                        s.append("&nbsp;&nbsp;");
	                        }
	                        /*//弹出窗口选择
	                        s.append("<input type=hidden name=\"").append(o.getColName()).append("\" value=\"").append(tmp_id).append("\">");
	                        s.append("<input type=text class=ipt_text readonly=true name=\"TMP_").append(o.getColName()).append("\" value=\"").append(tmp_name).append("\" size=20");
	                        s.append(" onclick=\"CommonFocus();selectFromDataTable(d('"+o.getColName()+"'),"+o.getExtValueScopeTypeID()+",'"+tmptable.getTableMemo()+"')\"");
	                        s.append(">&nbsp;");
	                        */
	                    }
	                }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_VARCHAR2){
	                    s.append(o.getColMemo()).append(": ");
	                    s.append("<input type=text class=ipt_text name=\"").append(o.getColName()).append("\"").append(" maxlength=").append(o.getDataLen()+3).append(" size=40>&nbsp;&nbsp;");
	                    
	                }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_NUMBER || o.getDataType()==ParamClass.VALUE_DATA_TYPE_CHAR){
	                    s.append(o.getColMemo()).append(": ");
	                    s.append("<input type=text class=ipt_text name=\"").append(o.getColName()).append("\" maxlength=").append(o.getDataLen()+3).append(" size=15>&nbsp;&nbsp;");
	
	                }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_DATE){
	
	                }
                }

                ins.append(relation_fun);
            }
            if(!HIUtil.isEmpty(con_keyword)){
            	if(i>0 && i%4==0) s.append("<br>");
            	s.append(con_keyword);
            }
            if(s.length()>0){
                s.append("&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"button\" class=\"button\" id=\"btn_query\" value=\" 查 询 \" onclick=\"page_redirect();\">");
            }
        }
        this.setAttribute(Constants.REQUEST_ATTRIBUTE_INITJS, ins.toString());
        this.setAttribute(Constants.REQUEST_ATTRIBUTE_QUERYCONDITION, s.toString());
    }

    /**
     * 
     */
    private static final long serialVersionUID = -8012749868344483471L;

}
