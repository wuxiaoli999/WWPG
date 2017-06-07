package com.svv.dms.web.service.biz;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts.upload.FormFile;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.Constants;
import com.svv.dms.web.UGID;
import com.svv.dms.web.common.ComBeanI_DataParamType;
import com.svv.dms.web.common.ComBeanI_DataTable;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComError;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.I_DataModule;
import com.svv.dms.web.entity.I_DataTable;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.DES;
import com.svv.dms.web.util.FileDES;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.Pinyin4jUtil;

public class B_DataBean extends B_DataQueryBean {

	public String B_Data(){
        return this.exeByCmd("");
    }
    public String B_Data_add(){
        return this.exeByCmd("");
    }
    public String B_Data_edit(){
        return this.exeByCmd("");
    }
    public String B_DataPublic_add(){
        return this.exeByCmd("");
    }
    public String B_DataPublic_edit(){
        return this.exeByCmd("");
    }
    public String B_DataAdds_add(){
        return this.exeByCmd("");
    }
    public String B_DataAdds_edit(){
        return this.exeByCmd("");
    }
    public String B_DataPublicChild_add(){
        return this.exeByCmd("");
    }
    public String B_DataPublicChild_edit(){
        return this.exeByCmd("");
    }
    public String B_ProcessChild_add(){
        return this.exeByCmd("");
    }
    public String B_ProcessChild_edit(){
        return this.exeByCmd("");
    }
	public String B_Formation_add(){
        return this.exeByCmd("");
	}
	public String B_Formation_edit(){
        return this.exeByCmd("");
	}
	public String B_FormationShow(){
        return this.exeByCmd("");
	}

	public String XMLSelectData(){
        return this.exeByCmd("ForSelect");
	}
	
	public String addPage(){
        if(row==null) ao.setDataStatus(ParamClass.VALUE_DATA_STATUS_BEGIN);
		setFormHtml(row, true);
		return BConstants.ADDPAGE;
	}	
	public String editPage(){
		loadByID();
		
		if(row!=null){
			ao.setDataStatus(DBUtil.getDBInt(row, "dataStatus"));
			ao.setDataSctLevel(DBUtil.getDBInt(row, "dataSctLevel"));
			ao.setParentDataid(DBUtil.getDBString(row, "parentDataid"));
			setFormHtml(row, true);
		}
		
		return BConstants.EDITPAGE;
	}
    public String detail(){
        loadByID();
        if(row!=null){
            setFormHtml(row, false);
        }
        return BConstants.DETAIL;
    }
	public String addData(){
	    if(ao.getDataStatus()==-1) ao.setDataStatus(ParamClass.VALUE_DATA_STATUS_BEGIN);
	    
        List<I_DataTableColumn> collist = getColumnList(null);
        int fixColNum = (HIUtil.isEmpty(ao.getParentDataid()) ? 2 : 3);
    	Object[] tmpParams = new Object[collist.size() + fixColNum];
    	int k = 0;
    	tmpParams[k++] = ao.getDataSctLevel();
    	tmpParams[k++] = ao.getDataStatus();

    	StringBuilder keyword = new StringBuilder();
    	for(I_DataTableColumn o: collist){
    	    String paramValue = "";
    	    if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_MD5){
    	    	paramValue = this.getParameter(o.getColName());
    	    	if(paramValue.length()!=32){
    	    		paramValue = DES.md5(paramValue);
    	    	}
    	    	
    	    }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_FILE){
                paramValue = this.uploadFormFile("file_"+o.getColName(), 2*1024*1024, PATH_DATAFILE, UGID.createUGID(), false, false, o.getExtValueScopeTypeID()==ParamClass.VALUE_VALUE_SCOPE_TYPE_DES_FILE, o.getExtValueScopeTypeID()==ParamClass.VALUE_VALUE_SCOPE_TYPE_DES_FILENAME);
                if(!HIUtil.isEmpty(paramValue) && !HIUtil.isEmpty(this.getParameter(o.getColName()))){
                    delUploadFile(PATH_DATAFILE, this.getParameter(o.getColName()));
                }
                paramValue = HIUtil.isEmpty(paramValue, this.getParameter(o.getColName()));
    	    }else{
        	    if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_IMGFILE){
                    paramValue = uploadPic("file_"+o.getColName(), PATH_DATAIMG, UGID.createUGID(), 2*1024*1024, o.getExtValueScopeTypeID()==ParamClass.VALUE_VALUE_SCOPE_TYPE_DES_FILE, o.getExtValueScopeTypeID()==ParamClass.VALUE_VALUE_SCOPE_TYPE_DES_FILENAME, true, false, -1, -1);
                    if(!HIUtil.isEmpty(paramValue) && !HIUtil.isEmpty(this.getParameter(o.getColName()))){
                        delUploadFile(PATH_DATAIMG, this.getParameter(o.getColName()));
                    }
                    paramValue = HIUtil.isEmpty(paramValue, this.getParameter(o.getColName()));
	    	    }else{
	                paramValue = this.getParameter(o.getColName()).replaceAll("\\r", "").replaceAll("\\n", "\\\\n");
	    	    }
    	    }
            System.out.println(o.getColName() + " = " + paramValue);
    		if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_DATETIME && !HIUtil.isEmpty(paramValue)) tmpParams[k++] = "to_date('"+HIUtil.toDBStr(paramValue)+"','yyyy-mm-dd hh24:mi:ss')";
    		else tmpParams[k++] = HIUtil.toDBStr(paramValue);
    		if(o.getExtKeyNameFlag()==1) keyword.append(paramValue).append(" ").append(Pinyin4jUtil.getPinYin(paramValue)).append(" ").append(Pinyin4jUtil.getPinYinHeadChar(paramValue)).append(" ");
    	}
    	if(!HIUtil.isEmpty(ao.getParentDataid())) tmpParams[k++] = ao.getParentDataid();

        System.out.println("keyword = "+keyword);
        Object[] params = new Object[]{
        		"add",
        		ao.getDataid(),
        		ao.getTableID(),
        		HIUtil.toDBStr(keyword.toString()),
        		HIUtil.toSPParamSplitString(tmpParams),
        		Constants.SPLITER,
        		collist.size() + fixColNum,
        		this.getUserSession().getUserName()
        		};
        if(dbExe("SP_B_DataManager", params)){
        	String message = getMessage();
        	if(message.indexOf("dataid=") > 0) resetMessage(message.substring(0, message.indexOf("dataid=")-1));
        	String dataid = ao.getDataid()+"";
        	if(ao.getDataid() ==-1 && message.indexOf("dataid=") > 0) dataid = message.substring(message.indexOf("dataid=")+7);
        	loggerB(ComBeanLogType.TYPE_ADD, (ao.getDataid()==-1 ? "添加":"编辑") + "数据："+ao.getTableMemo()+"(dataid="+dataid+")", ao.getTableID()+"", dataid, params);        	
        	if(closePageFlag) this.setScript("if(parent.document.getElementById('rtnMsg')){parent.document.getElementById('rtnMsg').value='"+message+"';parent.closeBox();}");
        	//if(parent.setReturn){parent.setReturn('"+message+"');window.close();}
        	ao.setDataid(Long.parseLong(dataid));
            ao.getTable().reloadDataMap();
            
            return BConstants.MESSAGE_PAGE;
        }
	    return ao.getDataid()==-1 ? addPage() : editPage();
	}

	public String delData(){
		ao.setTableID(this.getParameter("ao.tableID", -1));
		String dataids = this.getParameter("dataids");
		String [] dataid_list = (dataids.indexOf(",") > 0) ? dataids.split(",") : new String[]{dataids};
		for(String dataid: dataid_list){
	        Object[] params = new Object[]{
	        		"del", 
	        		dataid,
	        		ao.getTableID(),
	        		"",
	        		"",
	        		"",
	        		0,
	        		this.getUserSession().getUserName()
	        		};
	        if(dbExe("SP_B_DataManager", params)){
	        	loggerB(ComBeanLogType.TYPE_EDIT, "删除数据："+ao.getTableMemo()+"(dataid="+dataid+")", ao.getTableID()+"", dataid, params);
	            ao.getTable().reloadDataMap();
	        }
		}
	    return BConstants.MESSAGE;
	}

	public String copyData(){
		ao.setTableID(this.getParameter("ao.tableID", -1));
        Object[] params = new Object[]{
        		"copy", 
        		ao.getDataid(),
        		ao.getTableID(),
        		"",
        		"",
        		"",
        		0,
        		this.getUserSession().getUserName()
        		};
        if(dbExe("SP_B_DataManager", params)){
        	loggerB(ComBeanLogType.TYPE_ADD, "复制添加数据："+ao.getTableMemo()+"(dataid="+ao.getDataid()+")", ao.getTableID()+"", ao.getDataid()+"", params);
        }
	    return BConstants.MESSAGE;
	}

	protected void setFormHtml(Object tmprow, boolean editFlag){
        List<I_DataTableColumn> collist = getColumnList(null);
        StringBuilder s = new StringBuilder("");
        StringBuilder js = new StringBuilder("");
        StringBuilder ins = new StringBuilder("");
        if(collist!=null && collist.size()>0){
            int mySctLevel = this.getUserSession().getSctLevel();
        	String[] ss0 = new String[]{"","",""};
        	String formShow = null;
	        for(I_DataTableColumn o: collist){
	        	if(o.getFormShowFlag().equals("1") && mySctLevel>=o.getSctLevel()){
	        		formShow = "";
	        	} else if(this.isSuperAdmin() && !o.getColMemo().equals("（未使用）")){
	        	    formShow = " style=\"background:#e0e0e0;\"";
	        	} else {
	        	    formShow = " style=\"display:none;\"";
	        	}
	        	s.append("<tr").append(formShow).append(">");
	        	if(o.getDataCol1Num() > 1){
	        		String[] ss = o.getColMemo().split(I_DataTableColumn.COL_NAME_SPLITER);
	        		if(!ss[0].equals(ss0[0])){
			        	s.append("<td class=tdHeader width=180 rowspan="+o.getDataCol1Num()+">");
			        	s.append(ss[0]);
			        	s.append("</td>");
			        	ss0[0] = ss[0];
	        		}
	        		if(o.getDataCol2Num() > 1){
	                    if(!ss[1].equals(ss0[1])){
				        	s.append("<td class=tdHeader rowspan="+o.getDataCol2Num()+">");
				        	s.append(ss[1]);
				        	s.append("</td>");
				        	ss0[1] = ss[1];
		        		}
		        		if(o.getDataCol3Num() > 1){
		                    if(!ss[2].equals(ss0[2])){
					        	s.append("<td class=tdHeader rowspan="+o.getDataCol3Num()+">");
					        	s.append(ss[2]);
					        	s.append("</td>");
					        	ss0[2] = ss[2];
			        		}
				        	s.append("<td class=tdHeaderR colspan=2>");
				        	s.append(ss[3]);
		        		}else{
				        	s.append("<td class=tdHeaderR colspan=3>");
				        	s.append(ss[2]);
		        		}
	        		}else{
			        	s.append("<td class=tdHeaderR colspan=4>");
			        	s.append(ss[1]);
	        		}
	        	}else{
		        	s.append("<td class=tdHeaderR colspan=5>");
		        	s.append(o.getColMemo());
                    if(o.getNullFlag().equals("0")) s.append(" <font color=red><b>*</b></font>");
	        	}	        	
	        	s.append("</td>");
	        	s.append("<td class=tdBody width=66%>");

                String colValue = "";
                if(tmprow!=null){
                	colValue = DBUtil.getDBString(tmprow, o.getColName());
                	////////////colValue = HIUtil.htmlEncode(colValue); // HTML编码
                	if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_DATETIME) colValue = DBUtil.getDBDateStr(tmprow, o.getColName());
                }
	            /***********************************/
	            String[] ss = initInputHTML(ao.getTable(), o, colValue, editFlag, tmprow);
                s.append(ss[0]);
                js.append(ss[1]);
                ins.append(ss[2]);
                /***********************************/
	        	
	        	s.append("</td>");
	        	s.append("</tr>");
	        }
        }
        this.setAttribute(Constants.REQUEST_ATTRIBUTE_INITJS, ins.toString());
        this.setAttribute(Constants.REQUEST_ATTRIBUTE_FORMCHECKJS, js.toString());
        this.setAttribute(Constants.REQUEST_ATTRIBUTE_FORMHTML, s.toString());
	}
    
    @SuppressWarnings("rawtypes")
	protected String[] initInputHTML(I_DataTable table, I_DataTableColumn o, String colValue, boolean editFlag, Object row){
        StringBuilder s = new StringBuilder("");
        StringBuilder js = new StringBuilder("");
        
        String tmp_id = "";
        String tmp_name = "";
        String value = "";
        if(!HIUtil.isEmpty(colValue)){
            if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_DATETIME && colValue.indexOf(".")>0){
                colValue = colValue.substring(0, colValue.indexOf(".")-1);
            }
            tmp_id = colValue;
            value = " value=\""+ colValue +"\"";
        }
        if(o.getExtQueryByFlag()==1 && this.getParameter(o.getColName()).length()>0){
            tmp_id = this.getParameter(o.getColName());
        }

        String relation_fun  = getRelationFun(ao.getTable(), o, row);
        int paramTypeID = o.getExtValueScopeTypeID();       

        String def = "";
        if(paramTypeID > 0){
            String param = o.getExtValueScopeTypeParam();
            if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_DEF_USERINFO){
                if(param.equals("user.id")){
                    def = this.getUserSession().getEmpID()+"";
                }else if(param.equals("user.name")){
                    def = this.getUserSession().getEmpName();
                }else if(param.equals("user.organ")){
                    def = this.getUserSession().getOrganName();
                }else if(param.equals("user.department")){
                    def = this.getUserSession().getDepartmentName();
                }else if(param.equals("user.job")){
                    def = this.getUserSession().getJobName();
                }else if(param.equals("user.role")){
                    def = this.getUserSession().getRoleName();
                }
                paramTypeID = -2;
            }else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_DEF_CURDATE){
            	if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_NUMBER){
            		if(o.getDataLen()==8){
                        def = HIUtil.getCurrentDate("yyyyMMdd");
            		}
            	}else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_VARCHAR2){
            		def = HIUtil.getCurrentDate("yyyy-MM-dd");
            	}
                paramTypeID = -2;
            }else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_DEF_NEXTDATE){
            	if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_NUMBER){
            		if(o.getDataLen()==8){
                        def = HIUtil.addDate(1, "yyyyMMdd");
            		}else if(o.getDataLen()==12){
                        def = HIUtil.addDate(1, "yyyyMMdd")+"0900";
            		}
            	}
                paramTypeID = -2;
            }else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_DEF_DEFINE){
                def = o.getExtValueScopeTypeParam();
                paramTypeID = -2;
            }
        }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_UGID){
        	def = UGID.createUGID();
        }else if(o.getNullFlag().equals("0") && o.getDef().length()>0){
        	def = o.getDef();
        }
        if(!HIUtil.isEmpty(def) && HIUtil.isEmpty(value)) value = " value=\""+ def +"\"";

        if(paramTypeID > 0){
            //数据应用字典
            if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_SYS_DATAPARAM){
                if(editFlag){
                    int param = Integer.parseInt(o.getExtValueScopeTypeParam());
                	if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_RADIO){//单选框
                		TreeMap map = ComBeanI_DataParamType.getNameMap(isSuperAdmin(), param);
                		Set<Integer> keys = map.keySet();
                        s.append("<input type=text name=\"").append(o.getColName()).append("\" value=\"").append(tmp_id).append("\" size=40><br>");
                        int r = 0;
                		for(Integer key: keys){
                			s.append("<input type=radio class=radio name=\"TMP_").append(o.getColName()).append("\" value=\"").append(key).append("\" ").append(tmp_id.indexOf(key+"")>=0 ? "checked" :"");
                			s.append(" onclick=\"if(this.checked){d.").append(o.getColName()).append(".value=this.value;}\"");
                			s.append(">").append(map.get(key));
                			r++;
                			if(r%4==0) s.append("<br>");
                		}
                		s.append("</table>");
                		
                	}else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_CHECKBOX){//多选框
                		TreeMap map = ComBeanI_DataParamType.getNameMap(isSuperAdmin(), param);
                		Set<Integer> keys = map.keySet();
                        s.append("<input type=text name=\"").append(o.getColName()).append("\" value=\"").append(tmp_id).append("\" size=40><br>");
                        int r = 0;
                		for(Integer key: keys){
                			s.append("<input type=checkbox class=checkbox name=\"TMP_").append(o.getColName()).append("\" value=\"").append(key).append("\" ").append(tmp_id.indexOf(key+"")>=0 ? "checked" :"");
                			s.append(" onclick=\"var v=d.").append(o.getColName()).append(".value;if(v.length>0){v=','+v;}v=v.replace(',").append(key).append("','');if(this.checked){v=v+',").append(key).append("';}d.").append(o.getColName()).append(".value=v.substring(1);\"");
                			s.append(">").append(map.get(key));
                			r++;
                			if(r%4==0) s.append("<br>");
                		}
                		
                    }else{
                        try{
                            if(tmp_id.length() > 0){
                                tmp_name = ComBeanI_DataParamType.getText(Integer.parseInt(tmp_id));
                            }
                        }catch(Exception e){}

	                    if(param<1000){ //三位数的数据字典以select框显示
	                        s.append(HIUtil.genHtmlSelect(true, o.getColName(), ComBeanI_DataParamType.getNameMap(isSuperAdmin(), param), tmp_id, o.getNullFlag().equals("1"), relation_fun));
	                    }else{
	                        s.append("<input type=hidden name=\"").append(o.getColName()).append("\" value=\"").append(tmp_id).append("\">");
	                        s.append("<input type=text class=ipt_text readonly=true name=\"TMP_").append(o.getColName()).append("\" value=\"").append(tmp_name).append("\" maxlength=").append(o.getDataLen()).append(" size=40");
	                        s.append(" onclick=\"CommonFocus();selectFromDataParamType(d('"+o.getColName()+"'))\"");
	                        s.append(">&nbsp;");
	                        s.append("<input type=hidden name=\"PID_").append(o.getColName()).append("\" value="+o.getExtValueScopeTypeParam()+">");
	                    }
                    }
                    s.append("<span title=\""+o.getExtValueScope()+"\">&nbsp;&nbsp;</span>");
                }else{
                    s.append(tmp_name);
                    s.append("<input type=hidden name=\"").append(o.getColName()).append("\" value=\"").append(tmp_id).append("\">");
                }
            //业务数据
            }else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE){
                int param = Integer.parseInt(o.getExtValueScopeTypeParam());
                I_DataTable tmptable = ComBeanI_DataTable.get(param);
                TreeMap<Long, String> tmpMap;
                if(tmptable.getParentTableID().equals(ao.getTable().getParentTableID()))
                	tmpMap = tmptable.getDataKeyNameMap("parentDataid", ao.getParentDataid(), null, null, null, null);
                else tmpMap = tmptable.getDataKeyNameMap(null, null, null, null, null, null);
                //系统标识表
                if(tmptable.getSidFlag()==1){
                    if(getSession("SSID_"+tmptable.getTableID()) != null) tmp_id = String.valueOf(getSession("SSID_"+tmptable.getTableID()));
                }
                if(!HIUtil.isEmpty(tmp_id)){
                    tmp_name = HIUtil.isEmpty(tmpMap.get(Long.parseLong(tmp_id)), "");
                }
                if(editFlag){
                	if(tmpMap.size()<=30){
	                    //select框选择
	                    s.append(HIUtil.genHtmlSelect(true, o.getColName(), tmpMap, tmp_id, o.getNullFlag().equals("99999999991"), "CommonController();"+relation_fun));
                	}else{
	                    //弹出窗口选择
	                    s.append("<input type=hidden name=\"").append(o.getColName()).append("\" value=\"").append(tmp_id).append("\">");
	                    s.append("<input type=text class=ipt_text readonly=true name=\"TMP_").append(o.getColName()).append("\" value=\"").append(tmp_name).append("\" maxlength=").append(o.getDataLen()).append(" size=40");
	                    s.append(" onclick=\"CommonFocus();selectFromDataTable(d('"+o.getColName()+"'),"+param+",'"+tmptable.getTableMemo()+"')\"");
	                    s.append(">&nbsp;");
                	}
                    s.append("<span title=\""+o.getExtValueScope()+"\">&nbsp;&nbsp;</span>");
                }else{
                    s.append(tmp_name);
                    s.append("<input type=hidden name=\"").append(o.getColName()).append("\" value=\"").append(tmp_id).append("\">");
                }
            }
        
        }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_VARCHAR2 || o.getDataType()==ParamClass.VALUE_DATA_TYPE_UGID || o.getDataType()==ParamClass.VALUE_DATA_TYPE_MD5){
            if(editFlag){
                if(o.getDataLen() > 100){
                    s.append("<textarea class=ipt_text name=\"").append(o.getColName()).append("\" maxlength=").append(o.getDataLen()).append(" cols=80 rows=").append(HIUtil.min(o.getDataLen()/40+"", "20")).append(" onclick=\"CommonFocus();\">").append(StringEscapeUtils.escapeHtml(colValue.replaceAll("\\\\n", "\\\r\\\n"))).append("</textarea>");
                }else{
                    s.append("<input type=text class=ipt_text name=\"").append(o.getColName()).append("\"").append("").append(" maxlength=").append(o.getDataLen()).append(" size=40 onclick=\"CommonFocus();\">");
                    s.append("<script>document.all('").append(o.getColName()).append("').value='").append(colValue.replaceAll("\\\\n", "\\\r\\\n")).append("';</script>");
                }
                if(o.getNullFlag().equals("0")) js.append(" && checkStrInput(f[\"").append(o.getColName()).append("\"], 1000, true, '").append(o.getColMemo()).append("')");
            }else{
                s.append(HIUtil.isEmpty(colValue, def));
                s.append("<input type=hidden name=\"").append(o.getColName()).append("\"").append(value).append(">");
            }

        }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_TEXT){
            if(editFlag){
                s.append("<textarea class=ipt_text name=\"").append(o.getColName()).append("\" maxlength=").append(o.getDataLen()).append(" cols=70 rows=20 onclick=\"CommonFocus();\">").append(StringEscapeUtils.escapeHtml(colValue.replaceAll("\\\\n", "\\\r\\\n"))).append("</textarea>");
            }else{
                s.append(HIUtil.isEmpty(colValue, def));
                s.append("<input type=hidden name=\"").append(o.getColName()).append("\"").append(value).append(">");
            }
            
        }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_NUMBER || o.getDataType()==ParamClass.VALUE_DATA_TYPE_CHAR){
            if(editFlag){
                if(HIUtil.isEmpty(o.getExtValueScope())){
                    s.append("<input type=text class=ipt_text name=\"").append(o.getColName()).append("\"").append(value).append(" maxlength=").append(o.getDataLen()).append(" size=15 onclick=\"CommonFocus(d('"+o.getColName()+"'));\" onblur=\"doubleInput(this,").append(o.getDataDotLen()).append(")\">");
                    s.append("<input type=hidden name=\"TMP_").append(o.getColName()).append("\">");
                    s.append("<input type=hidden name=\"COLID_").append(o.getColName()).append("\" value="+o.getColid()+">");
                    s.append("<span id=\"div_").append(o.getColName()).append("\">").append(tmp_name).append("</span>");
                }else{
                    String[] ss = o.getExtValueScope().split("~");
                    if(ss.length > 1){
                        s.append("<input type=text class=ipt_text name=\"").append(o.getColName()).append("\"").append(value).append(" maxlength=").append(o.getDataLen()).append(" size=15 onclick=\"CommonFocus();\" onblur=\"if(this.value<"+ss[0]+") this.value="+ss[0]+";if(this.value>"+ss[1]+") this.value="+ss[1]+";doubleInput(this,").append(o.getDataDotLen()).append(");\"> 范围："+o.getExtValueScope());
                    }else{
                        tmp_id = HIUtil.isEmpty(tmp_id, def);
                        s.append("<input type=text class=ipt_text name=\"").append(o.getColName()).append("\"").append(value).append(" maxlength=").append(o.getDataLen()).append(" size=15 onclick=\"CommonFocus();\" onblur=\"doubleInput(this,").append(o.getDataDotLen()).append(")\">");
                    }
                }
                if(o.getNullFlag().equals("0")) js.append(" && checkStrInput(f[\"").append(o.getColName()).append("\"], 1000, true, '").append(o.getColMemo()).append("')");
            }else{
                s.append(HIUtil.isEmpty(colValue, def));
                s.append("<input type=hidden name=\"").append(o.getColName()).append("\"").append(value).append("\">");
            }
            
        }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_DATE){
            if(editFlag){
                s.append("<input type=text class=\"ipt_text easyui-datebox\" name=\"").append(o.getColName()).append("\" id=\"id_").append(o.getColName()).append("\"").append(value).append(" maxlength=").append(o.getDataLen()).append(" size=15>");
                if(o.getNullFlag().equals("0")) js.append(" && checkStrInput(f[\"").append(o.getColName()).append("\"], 1000, true, '").append(o.getColMemo()).append("')");
            }else{
                s.append(HIUtil.isEmpty(colValue, def));
                s.append("<input type=hidden name=\"").append(o.getColName()).append("\"").append(value).append(">");
            }
            
        }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_DATETIME){
            if(editFlag){
                s.append("<input type=text class=\"ipt_text easyui-datetimebox\" name=\"").append(o.getColName()).append("\" ").append(value).append(" data-options=\"formatter:ww4,parser:w4\" style=\"width:200px;\">");
                if(o.getNullFlag().equals("0")) js.append(" && checkStrInput(f[\"").append(o.getColName()).append("\"], 1000, true, '").append(o.getColMemo()).append("')");
            }else{
                s.append(HIUtil.isEmpty(colValue, def));
                s.append("<input type=hidden name=\"").append(o.getColName()).append("\"").append(value).append(">");
            }
            
        }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_FILE){
    		int isDesFile = 0;
    		if(tmp_id.startsWith("##")){
    			isDesFile = 1;
    			tmp_id = tmp_id.substring(2);
    		}
    	    if(tmp_id.startsWith("$$")) tmp_id = dES.decrypt(tmp_id.substring(2));
            if(!HIUtil.isEmpty(tmp_id)) s.append("<a href=# onclick=\"showDataFile('"+tmp_id+"',").append(isDesFile).append(")\">"+(tmp_id.indexOf("`")>0?tmp_id.substring(tmp_id.indexOf("`")+1):tmp_id)+"</a><br>");
            if(editFlag){
                s.append("<input type=file class=\"ipt_text\" name=\"file_").append(o.getColName()).append("\" id=\"id_").append(o.getColName()).append("\"").append(value).append(" size=50>");
                if(o.getNullFlag().equals("0")) js.append(" && checkStrInput(f[\"").append("file_"+o.getColName()).append("\"], 1000, true, '").append(o.getColMemo()).append("')");
                s.append("<input type=hidden name=\"").append(o.getColName()).append("\"").append(value).append(">");
            }else{
                s.append("<input type=hidden name=\"").append(o.getColName()).append("\"").append(value).append(">");
            }
            
        }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_IMGFILE){
        	if(!HIUtil.isEmpty(tmp_id)){
        		String fullName;
        		if(tmp_id.startsWith("http")){
        			fullName = tmp_id;
        		}else{
		    		int isDesFile = 0;
		    		if(tmp_id.startsWith("##")){
		    			isDesFile = 1;
		    			//tmp_id = tmp_id.substring(2);
		    		}
		    	    //if(tmp_id.startsWith("$$")) tmp_id = DES.decrypt(tmp_id.substring(2));
	    		    fullName = getRealPath(Constants.UPLOAD_FILE_PATH + "/" + PATH_DATAIMG + "/" + tmp_id);
	    		    if(ao.getTable().getServerPath().length()>0) fullName = ao.getTable().getServerPath() + Constants.UPLOAD_FILE_PATH + "/" + PATH_DATAIMG + "/" + tmp_id;
		    	    if(isDesFile == 1){    	        		    
		    		    try {
		    		    	fullName = FileDES.decrypt(fullName, getRealPath("temp/" + tmp_id));
		    	        } catch (Exception e) {
		    	            e.printStackTrace();
		    	        }
		    	    }
        		}
	    	    s.append("<img src='"+fullName+"' height=60>");
        	}

            if(editFlag && !HIUtil.isEmpty(tmp_id)){
            	s.append("<input type=text name=\"").append(o.getColName()).append("\"").append(value).append(" size=60>");
            }else{
                s.append("<input type=").append(this.isSuperAdmin()?"text":"hidden").append(" name=\"").append(o.getColName()).append("\"").append(value).append(" size=60>");
            }
            if(editFlag){
                s.append("<input type=file class=\"ipt_text\" name=\"file_").append(o.getColName()).append("\" id=\"id_").append(o.getColName()).append("\"").append(value).append(" size=50><br>");
                if(o.getNullFlag().equals("0")) js.append(" && checkStrInput(f[\"").append("file_"+o.getColName()).append("\"], 1000, true, '").append(o.getColMemo()).append("')");
            }
            
        }else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_IMGLINK){
            if(!HIUtil.isEmpty(tmp_id)) s.append("<img src=\""+tmp_id+"\" height=50><br>");
            if(editFlag){
                if(o.getDataLen() > 100){
                    s.append("<textarea class=ipt_text name=\"").append(o.getColName()).append("\"").append(value).append(" maxlength=").append(o.getDataLen()).append(" cols=80 rows=").append(HIUtil.min(o.getDataLen()/40+"", "20")).append(" onclick=\"CommonFocus();\">").append(colValue).append("</textarea>");
                }else{
                    s.append("<input type=text class=ipt_text name=\"").append(o.getColName()).append("\"").append(value).append(" maxlength=").append(o.getDataLen()).append(" size=40 onclick=\"CommonFocus();\">");
                }
                if(o.getNullFlag().equals("0")) js.append(" && checkStrInput(f[\"").append(o.getColName()).append("\"], 1000, true, '").append(o.getColMemo()).append("')");
            }else{
                s.append(HIUtil.isEmpty(colValue, def));
                s.append("<input type=hidden name=\"").append(o.getColName()).append("\"").append(value).append(">");
            }

        }
        return new String[]{s.toString(), js.toString(), relation_fun};
    }
    
    protected String getRelationFun(I_DataTable table, I_DataTableColumn scol, Object row){
        String relation_fun  = "";
        if(scol.getExtRelationSubCols().length()>0){
            String[] ss = scol.getExtRelationSubCols().split(",");
            for(String id: ss){
            	System.out.println("B_DataBean.getRelationFun.table.getColumnsMap() table="+table.getTableID()+"  id="+id+"  relationSubCols="+scol.getExtRelationSubCols()+"  scol="+scol.getColid());
                I_DataTableColumn col = table.getColumnsMap().get(Long.parseLong(id));
                if(col.getColid()!=scol.getColid()){
                    int synFlag = col.getExtValueScopeTypeParam().equals(scol.getExtValueScopeTypeParam())?1:0;
                    String value = "";
                    if(row!=null) value = DBUtil.getDBString(row, col.getColName());
                    I_DataTable t = ComBeanI_DataTable.get(Integer.parseInt(col.getExtValueScopeTypeParam()));
                    relation_fun += "XMLGetSelectList("+t.getTableID()+", '"+t.getKeyTableColumn(Integer.parseInt(scol.getExtValueScopeTypeParam())).getColName()+"', '"+col.getColName()+"', '"+value+"', "+col.getNullFlag()+",'"+scol.getColName()+"',"+synFlag+");";
                }
            }
        }
        return relation_fun;
    }
    protected String getRelationFun(I_DataModule module, I_DataTableColumn scol, Object row){
        String relation_fun  = "";
        if(scol.getExtRelationSubCols().length()>0){
            String[] ss = scol.getExtRelationSubCols().split(",");
            for(String id: ss){
            	System.out.println("B_DataBean.getRelationFun.module.getColumnsMap()");
            	I_DataTableColumn col = module.getColumnsMap().get(Long.parseLong(id));
                if(col.getColid()!=scol.getColid()){
                    int synFlag = col.getExtValueScopeTypeParam().equals(scol.getExtValueScopeTypeParam())?1:0;
                    String value = "";
                    if(row!=null) value = DBUtil.getDBString(row, col.getColName());
                    I_DataTable t = ComBeanI_DataTable.get(Integer.parseInt(col.getExtValueScopeTypeParam()));
                    relation_fun += "XMLGetSelectList("+t.getTableID()+", '"+t.getKeyTableColumn(Integer.parseInt(scol.getExtValueScopeTypeParam())).getColName()+"', '"+col.getColName()+"', '"+value+"', "+col.getNullFlag()+",'"+scol.getColName()+"',"+synFlag+");";
                }
            }
        }
        return relation_fun;
    }

	protected void loadByID(){
		List<Object> list = this.dbQuery(SQL.SP_B_DataQueryByID(ao.getTableID(), ao.getTableName(), ao.getDataid()));
		if(list==null || list.size()==0){
			this.setMessage(false, ComError.err_000001);
		}else{
			this.row = list.get(0);
		}
	}
	
	protected String saveQueryCondition(){
		I_DataTable table = ComBeanI_DataTable.get(ao.getTableID());
        if(table!=null){
		    return SQL.SP_B_DataQueryByC(ao.getTableID(), table.getTableName(), ao.getParentDataid(), "", this.getUserSession().getSctLevel()+"", getKeywordColNames(), c_keyword);
        }
        return null;
	}

    /**
	 * 
	 */
	private static final long serialVersionUID = 7558246240700607904L;

	protected boolean closePageFlag = true;
	protected FormFile dataFile;
	protected String dataFileName = "";
    
	protected Object row = null;	

    public FormFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(FormFile dataFile) {
        this.dataFile = dataFile;
    }

    public String getDataFileName() {
        return dataFileName;
    }

    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
    }
	
	public Object getRow() {
		return row;
	}

	public void setRow(Object row) {
		this.row = row;
	}

}
