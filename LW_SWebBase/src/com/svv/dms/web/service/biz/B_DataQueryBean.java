package com.svv.dms.web.service.biz;

import java.util.List;
import java.util.TreeMap;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanI_DataParamType;
import com.svv.dms.web.common.ComBeanI_DataTable;
import com.svv.dms.web.common.ComBeanI_DataTableType;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.entity.B_Data;
import com.svv.dms.web.entity.I_DataTable;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.entity.I_DataTableType;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.FileDES;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.PoiUtil;
import com.svv.dms.web.util.TColumn;

public class B_DataQueryBean extends B_DataBaseBean {

    public String out(){
    	List<Object> list = loadDataList();
    	if(list==null || list.size()==0){
    		this.setScript("parent.setError('无数据！');");
        	return BConstants.POI;
    	}
    	Object[] objs = getResult_List(list);
    	String title = ""; //ao.getTableMemo() + " " + HIUtil.getCurrentDate();
    	this.setResultPoi("meledata_"+HIUtil.getCurrentDate("yyyyMMddHHmmss"), new PoiUtil().createXssf(title, objs));
    	return BConstants.POI;
	}
	
	public String topDataTypes(){
        StringBuilder s = new StringBuilder("");
		List<I_DataTableType> list = ComBeanI_DataTableType.getList(-1, ParamClass.VALUE_LEVEL_ONE, ParamClass.VALUE_LEVEL_ONE);
    	if(list!=null && list.size()>0){    		
    		s.append("<table width=100% cellSpacing=1 cellPadding=30 border=0>");
			s.append("<tr>");
			
    		int i = 0;
    		for(I_DataTableType o: list){
    			i++;
    			s.append("<td width=120 class=dtbig_txt><span onclick=DM_Select('"+o.getTableTypeID()+"','"+o.getTableTypeName()+"') title=\"").append(o.getTableTypeName()).append("\">");
    			s.append("<img src='../doc/images/datatabletype/").append(o.getTableTypeID()).append(".png' class=dtbig_pic><br>");
    			s.append(ComBeanI_DataTableType.getSimpText(o.getTableTypeID()));
    			s.append("</span></td>");
    			if(i%4==0) s.append("</tr><tr>");
    		}
    		s.append("</table>");
    	}
		
		this.setMessage(true, s.toString());
    	//System.out.println(s.toString()); //####################################################
    	return BConstants.MESSAGE;		
	}
	
	@SuppressWarnings("rawtypes")
	public String getRowsHead(){
        loggerB(ComBeanLogType.TYPE_QUERY, "查询数据："+ao.getObject().getObjectName(), ao.getObject().getObjectID()+"", null, null);         

        StringBuilder s = new StringBuilder("");
        try{
	        if(ao.getTableID()>0){
	        	this.setAttribute("ao.tableID", ao.getTableID()+"");
	            if(!HIUtil.isEmpty(ao.getParentDataid())) this.setAttribute("ao.parentDataid", ao.getParentDataid()+"");
	        }else if(ao.getModuleID().length()>0){
	        	this.setAttribute("ao.moduleID", ao.getModuleID());
	        }
	        if(ao.getDataSctLevel()>0) this.setAttribute("ao.dataSctLevel", ao.getDataSctLevel()+"");
	
	        List collist = getColumnList(null);
	        if(collist!=null && collist.size() > 0){
	        	String sql = saveQueryCondition(); //*************************************************
	            int editBarFlag = this.getParameter("editFlag", 0);
	            
	            String moduleName = getModuleName();
	            if(checkModulePath("B_DataPublicQuery")) moduleName = "B_DataPubQ_" + ao.getTableID();
	            else if(checkModulePath("B_DataPublic")) moduleName = "B_DataPub_" + ao.getTableID();
	            else if(checkModulePath("B_DataPublicCmt")) moduleName = "B_DataPubCmt_" + ao.getTableID();
	            else if(checkModulePath("B_DataPublicChild")) moduleName = "B_DataPubChild_" + ao.getTable().getParentTableID() +"_"+ ao.getTableID();
	            else if(checkModulePath("B_DataModule")) moduleName = "B_DataModule_" + ao.getModuleID();
	
	            StringBuilder tmp_frozen = new StringBuilder("");
	            //if(editBarFlag==1) tmp_frozen.append("{field:'ck',checkbox:true},");
	        	if(checkModulePath("_his")){
	        		tmp_frozen.append("{title:'备注',field:'imemo',width:300,align:'center',formatter:function(value){return '<span style=\"color:black\">'+value+'</span>';}},\n");
	        	}
	        	tmp_frozen.append("{title:'数据ID',field:'dataid',sortable:true,width:60,align:'center',formatter:function(value){return '<span style=\"color:black\">'+value+'</span>';}},\n");
	        	if(ao.getObject().isTable()){
		        	if(ao.getTable().getSctLevelFlag()==1) tmp_frozen.append("{title:'密级',field:'dataSctLevel',width:40,align:'center',formatter:function(value){return '<span style=\"color:black\">'+value+'</span>';}},\n");
		        	if(ao.getTable().getDataStatusFlag()==1) tmp_frozen.append("{title:'数据状态',field:'dataStatus',width:110,formatter:function(value){return '<span style=\"color:black\">'+value+'</span>';}},\n");
		            if(ao.getTable().getAttachFileFlag()==1) tmp_frozen.append("{title:'多媒体信息',field:'attachFile',width:100,align:'center'},\n");
		            //tmp_frozen.append("{title:'数据来源',field:'resource',width:90,align:'center'},\n");
	        	}
	            
	            int mySctLevel = this.getUserSession().getSctLevel();
	            StringBuilder tmp_col = new StringBuilder("");
	        	for(Object o_: collist){
	        		I_DataTableColumn o = (I_DataTableColumn)o_;
	        	    if(o.getListShowFlag().equals("1") && mySctLevel>=o.getSctLevel()){
	            		StringBuilder tmp = new StringBuilder("");
	            		String[] ss = o.getColMemo().split(I_DataTableColumn.COL_NAME_SPLITER);
	            		int wid = 0;
	            		for(String i: ss){
	            			if(i.getBytes().length-5>wid) wid = i.getBytes().length-5;
	            		}
	            		if((o.getDataType()==ParamClass.VALUE_DATA_TYPE_VARCHAR2) && o.getShowWidth()-5 > wid) wid = o.getShowWidth()-5;
	            		else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_TEXT && o.getShowWidth()-5 > wid) wid = o.getShowWidth() > 100 ? 100 : o.getShowWidth()-5;
	            		else if(o.getShowWidth() > wid) wid = o.getShowWidth();
	            		
	            		tmp.append("{field:'").append(o.getColName()).append("',sortable:true,title:'").append(o.getColMemo().replaceAll(I_DataTableColumn.COL_NAME_SPLITER, "<br>")).append(o.getExtKeywordFlag()==1?"<b>*</b>":"").append("',width:").append(wid*10).append(",editor:'text'},\n");
	            		if(o.getExtKeyNameFlag()==1){
	            			tmp_frozen.append(tmp);
	            		}else{
	              		  tmp_col.append(tmp);
	            		}
	        	    }
	        	}
	        	
	            if(ao.getObject().isTable() && ao.getTable().getChildTableNum() > 0){
	                String[] ss = ao.getTable().getChildTableIDs().split(",");
	                I_DataTable childTable;
	                for(String si: ss){
	                	childTable = ComBeanI_DataTable.get(Integer.parseInt(si));
	                    int wid = childTable.getTableMemo().getBytes().length+2;
	                    if(childTable.getScopeFlag()==1 && checkUserPower("B_DataPubChild_"+ao.getTableID()+"_" + si) && mySctLevel>=childTable.getSctLevel())
	                    	tmp_frozen.append("{title:'"+childTable.getTableMemo()+"',field:'childTable_"+si+"',width:"+(wid*10)+",align:'center'},\n");
	                }
	            }
	        	
	        	tmp_col.append("{title:'添加时间',field:'istDate',width:130,align:'center',formatter:function(value){return '<span style=\"color:black\">'+value+'</span>';}},\n");
	        	tmp_col.append("{title:'更新时间',field:'uptDate',width:130,align:'center',formatter:function(value){return '<span style=\"color:black\">'+value+'</span>';}},\n");
	        	if(tmp_col.length()>0) tmp_col.setLength(tmp_col.length()-2);
	        	if(tmp_frozen.length()>0) tmp_frozen.setLength(tmp_frozen.length()-2);
	
	            String otherButtons = ao.getObject().getOtherButtons();
	            StringBuilder A_buttons = new StringBuilder("");
	            StringBuilder B_buttons = new StringBuilder("");
	            if(!HIUtil.isEmpty(otherButtons)){
	                String[] bs = otherButtons.split(",");
	                for(int i=0; i<bs.length; i++){
	                    String[] ss = bs[i].split("\\|");
	                    if(checkUserPower(moduleName+"_"+ss[1])){
	                        if(ss[1].startsWith("A"))
	                            A_buttons.append("<input type=button class=buttonLine onclick=\""+ss[1]+"()\" title=\""+ss[0]+"\" value=\""+ss[0]+"\">&nbsp;&nbsp;");
	                        else if(ss[1].startsWith("B")) 
	                            B_buttons.append("{id:'btnother"+i+"', text:'"+ss[0]+"', iconCls:'icon-btn', handler:function(){ "+ss[1]+"() }},\n");
	                    }
	                }
	            }            
	                  
	            StringBuilder actionMenuStr = new StringBuilder("");
	            
	            int btnWidth = 0;
	            //boolean sub_importFlag = checkUserPower(moduleName+"_import") || checkUserPower(moduleName+"Import");
	            boolean sub_importFlag = this.getParameter("outFlag", 0)==1 && (checkUserPower(moduleName+"_import") || checkUserPower(moduleName+"Import"));
	        	if(sub_importFlag){
	        		btnWidth += 90;
	        		actionMenuStr.append("&nbsp;&nbsp;<img src=\"../doc/script/jquery-easyui-1.2.3/icons/daoru.png\" onclick=\"imt()\" title=\"导入数据\">");
	        	}
	            boolean sub_exportFlag = this.getParameter("outFlag", 0)==1 && (checkUserPower(moduleName+"_export") || checkUserPower(moduleName+"Export"));
	        	if(sub_exportFlag){
	        		btnWidth += 90;
	        		actionMenuStr.append("&nbsp;&nbsp;<img src=\"../doc/script/jquery-easyui-1.2.3/icons/daochu.png\" onclick=\"out()\" title=\"导出数据\">");
	        	}
	
	            String url = "../" + getModulePath() + ".y?cmd=getRowsData&ks="+dES.encrypt(sql)+"&ao.tableID="+ao.getTableID()+"&ao.moduleID="+ao.getModuleID()+"&formationID="+this.getParameter("formationID")+"&editFlag="+editBarFlag;
	            
	        	s.append("title:'<div class=dt_header><table width=100% cellspacing=0 cellpadding=0><tr><td>【").append(ao.getTableID()>0?ao.getTable().getTableMemo(): ao.getModule().getModuleName()).append("】</td><td align=right>").append(A_buttons).append("</td><td align=right width=").append(btnWidth).append(">").append(actionMenuStr).append("</td><td width=10></td></tr></table></div>',\n");
	        	s.append("width:'100%', height:'"+this.getParameter("h", "530")+"', nowrap: false, singleSelect: true, striped: true, rownumbers: false, animate:true, closable:false, collapsible:false, pagination:"+(this.getParameter("pageFlag", 1)==1)+",\n");
	        	s.append("url:'").append(url).append("',\n");
	        	s.append("sortName:'rowid', sortName:'asc', remoteSort: false, \n");
	        	s.append("idField:'rowid', treeField:'rowid',\n");
	        	
	        	s.append("frozenColumns:[[\n").append(tmp_frozen).append("]],\n");        	
	        	s.append("columns:[[").append(tmp_col).append("]],\n");        	
	
	            if(editBarFlag==1){
	            	actionMenuStr = new StringBuilder("");
	
	                boolean sub_addFlag = checkUserPower(moduleName+"_add");
	                boolean sub_copyFlag = checkUserPower(moduleName+"_copy");
	                boolean sub_editFlag = checkUserPower(moduleName+"_edit");
	                boolean sub_delFlag = checkUserPower(moduleName+"_del");
	                boolean sub_his = checkUserPower(moduleName+"_his");
	                boolean sub_submitFlag = checkUserPower(moduleName+"_submit");
	                boolean sub_checkFlag = checkUserPower(moduleName+"_check");
	                boolean sub_cmtFlag = checkUserPower(moduleName+"_cmt");
	                boolean sub_rebackFlag = checkUserPower(moduleName+"_reback");
	                boolean sub_publishFlag = checkUserPower(moduleName+"_publish");
	            	
	        	    if(sub_addFlag) actionMenuStr.append("{id:'btnadd', text:'新增', iconCls:'icon-add', handler:function(){ add() }},\n");
	        		if(sub_copyFlag) actionMenuStr.append("{id:'btncopy', text:'复制', iconCls:'icon-copy', handler:function(){ copy() }},\n");
	        		if(sub_editFlag) actionMenuStr.append("{id:'btnedit', text:'编辑', iconCls:'icon-edit', handler:function(){ edit() }},\n");
	            	if(sub_delFlag) actionMenuStr.append("{id:'btndel', text:'删除', iconCls:'icon-remove', handler:function(){ remove() }},\n");
	            	if(sub_his) actionMenuStr.append("{id:'btndel', text:'历史', iconCls:'icon-his', handler:function(){ his() }},\n");
	            	if(sub_submitFlag) actionMenuStr.append("{id:'btnok', text:'提交', iconCls:'icon-ok', handler:function(){ action(0) }},\n");
	                if(sub_checkFlag) actionMenuStr.append("{id:'btnok', text:'校对', iconCls:'icon-ok', handler:function(){ action(1) }},\n");
	                if(sub_cmtFlag) actionMenuStr.append("{id:'btnok', text:'审核通过', iconCls:'icon-ok', handler:function(){ action(2) }},\n");
		            if(sub_rebackFlag) actionMenuStr.append("{id:'btnback', text:'退回', iconCls:'icon-back', handler:function(){ action(3) }},\n");
	            	if(sub_publishFlag) actionMenuStr.append("{id:'btnok', text:'发布', iconCls:'icon-ok', handler:function(){ action(4) }},\n");
	                actionMenuStr.append(B_buttons);
	
	                if(ao.getTable().getChildTableNum() > 0){
	                    String[] ss = ao.getTable().getChildTableIDs().split(",");
	                    I_DataTable childTable;
	                    for(String si: ss){
	                    	childTable = ComBeanI_DataTable.get(Integer.parseInt(si));
	                        if(childTable.getScopeFlag()==0 && checkUserPower("B_DataPubChild_"+ao.getTableID()+"_" + si)) actionMenuStr.append("{id:'btndetail"+si+"', text:'"+childTable.getTableMemo()+"', iconCls:'icon-detail', handler:function(){ showChildTables(null,"+si+",'"+childTable.getTableMemo()+"',"+editBarFlag+") }},\n");
	                    }
	                }
	                
		        	if(actionMenuStr.length()>0){
		        		actionMenuStr.setLength(actionMenuStr.length()-2);
			        	s.append("toolbar:[\n").append(actionMenuStr).append("],\n");
		        	}
		        	
	            }
	        	
	        	if(editBarFlag==2){
	        		s.append("onClickRow: function(row){ if(edit_node && edit_node.rowid != row.rowid){ saveNode(edit_node);  } },\n");
	        		s.append("onDblClickRow: function(row){ if(edit_node && edit_node.rowid != row.rowid){ saveNode(edit_node); } editNode(row); },\n");
	            }
	            if(this.getParameter("pageFlag", 1)==1){
	                url += "&pageFlag=1&page='+options.pageNumber+'&pageRow='+options.pageSize+'";
	        	    s.append("onBeforeLoad: function(row){ var options = $('#div_tree').datagrid('options'); options.url = '").append(url).append("';},\n");
	            }
	        	
	        	s.append("onContextMenu: function(e,row){ e.preventDefault(); $(this).treegrid('unselectAll'); $(this).treegrid('select', row.rowid); }\n");
	        	s.append("});\n");
	        	
	        	/*
	        	s.append("onBeforeLoad:function(row,param){\n");
	        	s.append("  if (row){$(this).datagrid('options').url = '").append(url).append("&parentID='+row.rowid;}\n");
	        	s.append("  else {$(this).datagrid('options').url = '").append(url).append("';}\n");
	        	s.append("},\n");*/
	        	
	        }
	    	this.setResultHtml(true, s.toString());
	    	//System.out.println(s.toString()); //####################################################
        }catch(Exception e){
        	e.printStackTrace();
        }
    	return "XMLDataList";
	}	
	public String getRowsData(){
		String s = getTreeData();
    	this.setMessage(true, s);
    	//System.out.println(s.toString()); //####################################################
    	return BConstants.MESSAGE;
	}

	protected String getTreeData(){
        StringBuilder s = new StringBuilder("");
		try{
	    	List<Object> list = loadDataList();
	    	
	        if(list!=null && list.size() > 0){
	
	        	boolean manageFlag = checkModulePath("B_Data") && this.checkUserPower("B_Data") || (checkModulePath("B_DataPublic") && this.checkUserPower("B_DataPublic")) || (checkModulePath("B_DataChild") && this.checkUserPower("B_DataChild"));
	        	boolean addFlag = checkModulePath("B_DataAdds") && this.checkUserPower("B_DataAddsadd");
	            boolean editBarFlag = this.getParameter("editFlag", 0)==1 && (manageFlag || addFlag);
	        	
	            s.append("{\"total\":").append(D_TotalNum).append(",\"rows\":[\n");
	            List<I_DataTableColumn> collist = getColumnList(null);
	            int seq = -1;
	            int k = 0;
	            int dataid = -1;
	            for(Object row: list){
	            	seq = k+1;
	            	dataid = DBUtil.getDBInt(row, "dataid");
	            	s.append("{\"rowid\":").append(101+k).append(",\"seq\":").append(seq)
	            	 .append(",\"dataid\":\"").append(dataid).append("\"")
	            	 .append(",\"imemo\":\"").append(DBUtil.getDBString(row, "imemo")).append("\"")
	            	 .append(",\"istDate\":\"").append(DBUtil.getDBDateStr(row, "istDate")).append("\"")
	            	 .append(",\"uptDate\":\"").append(DBUtil.getDBDateStr(row, "uptDate")).append("\"")
	            	 .append(",\"dataStatus\":\"").append(ComBeanI_SystemParam.getText(ParamClass.CLASS_DATA_STATUS, DBUtil.getDBInt(row, "dataStatus"))).append("\"")
	            	 .append(",\"dataSctLevel\":\"").append(ComBeanI_SystemParam.getText(ParamClass.CLASS_SCT_LEVEL, DBUtil.getDBInt(row, "dataSctLevel"))).append("\"");
	            	
	            	int attachFileNum = DBUtil.getDBInt(row, "attachFileNum");
	            	if(editBarFlag && attachFileNum == 0){
	                	s.append(",\"attachFile\":\"<a href=# onclick='showAttachFiles(").append(dataid).append(",").append(editBarFlag ? 1:0).append(")'>").append( "<上传文件>" ).append("</a>\""); //多媒体信息
	            	}else{
	                	s.append(",\"attachFile\":\"已上传 <a href=# onclick='showAttachFiles(").append(dataid).append(",").append(editBarFlag ? 1:0).append(")'>").append( attachFileNum ).append("</a> 个\""); //多媒体信息
	            	}
	                if(ao.getTable().getChildTableNum() > 0){
	                    String[] ss = ao.getTable().getChildTableIDs().split(",");
	                    String name;
	                    for(String si: ss){
	                        name = ComBeanI_DataTable.getMemo(Integer.parseInt(si));
	                        s.append(",\"childTable_"+si+"\":\"<a href=# onclick='showChildTables(").append(dataid).append(",").append(si).append(",").append((manageFlag || addFlag) ? 1:0).append(")'>").append("<"+name+"></a>\"");
	                    }
	                }
	                
	            	s.append(",\"text\":\"").append(DBUtil.getDBString(row, collist.get(0).getColName())).append("\"");
	                int mySctLevel = this.getUserSession().getSctLevel();
	                for(I_DataTableColumn o: collist){
	                    if(o.getListShowFlag().equals("1") && mySctLevel>=o.getSctLevel()){
	                    	String tmp_id = DBUtil.getDBString(row, o.getColName());
	                    	tmp_id = tmp_id.replaceAll("\\\\n", "");
	                    	tmp_id = tmp_id.replaceAll("\\n", "");
	                    	tmp_id = tmp_id.replaceAll("\\\\n", "\\\\r\\\\n");
	                    	tmp_id = HIUtil.htmlEncode(tmp_id); // HTML编码
	                    	
	                    	if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_DATETIME) tmp_id = DBUtil.getDBDateStr(row, o.getColName());
	                        int paramTypeID = o.getExtValueScopeTypeID();       
	
	                        if(paramTypeID > 0){
	                            if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_DEF_USERINFO){
	                                paramTypeID = -2;
	                            }else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_DEF_CURDATE){
	                                paramTypeID = -2;
	                            }else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_DEF_DEFINE){
	                                paramTypeID = -2;
	                            }
	                        }
	                    	
	        	        	if(!HIUtil.isEmpty(tmp_id) && paramTypeID>0){
	        	        		//数据应用字典
	        	        		if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_SYS_DATAPARAM){
	                        	    s.append(",\"").append(o.getColName()).append("\":\"").append(tmp_id).append("-").append(ComBeanI_DataParamType.getText(tmp_id)).append("\"");
	                        	    
	        		        	//其他业务数据
	                            }else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE){
	                                int param = Integer.parseInt(o.getExtValueScopeTypeParam());
	                                I_DataTable tmptable = ComBeanI_DataTable.get(param);
	                                TreeMap<Long, String> tmpMap = tmptable.getDataKeyNameMap(null, null, null, null, null, null);
	                                /****临时 s*******/
	                                String[] ss = tmp_id.split(Constants.SPLITER_DATA);
	                                tmp_id = ss[0];
	                                /****临时 e*******/
	                                s.append(",\"").append(o.getColName()).append("\":\"").append(tmp_id).append("-").append( HIUtil.isEmpty(tmpMap.get(Long.parseLong(tmp_id)), "") ).append("\"");
	                                
	        	        		}
	        	        	}else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_FILE){
	        	        		if(!HIUtil.isEmpty(tmp_id)){
		        	        		int isDesFile = 0;
		        	        		if(tmp_id.startsWith("##")){
		        	        			isDesFile = 1;
		        	        			tmp_id = tmp_id.substring(2);
		        	        		}
		        	        	    if(tmp_id.startsWith("$$")) tmp_id = dES.decrypt(tmp_id.substring(2));
		        	        	    s.append(",\"").append(o.getColName()).append("\":\"<a href=# onclick=showDataFile('").append(tmp_id).append("',").append(isDesFile).append(")>").append(tmp_id.indexOf("`")>0?tmp_id.substring(tmp_id.indexOf("`")+1):tmp_id).append("</a>\"");
	        	        		}else{
	                    	        s.append(",\"").append(o.getColName()).append("\":\"").append(tmp_id).append("\"");
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
			        	        		    	fullName = FileDES.decrypt(fullName, getServerPath("temp/" + tmp_id));
			        	        	        } catch (Exception e) {
			        	        	            e.printStackTrace();
			        	        	        }
			        	        	    }
	        	        			}
		        	        	    s.append(",\"").append(o.getColName()).append("\":\"<img src='"+fullName+"' height=60 onclick=showPic('"+fullName+"')>\"");
	        	        		}else{
	                    	        s.append(",\"").append(o.getColName()).append("\":\"").append(tmp_id).append("\"");
	        	        		}
	        	        	    
	        	        	}else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_IMGLINK){
	        	        		if(!HIUtil.isEmpty(tmp_id)){
	                                s.append(",\"").append(o.getColName()).append("\":\"<img src='"+tmp_id+"' height=60 onclick=showPic('"+tmp_id+"')>\"");
	        	        		}else{
	                    	        s.append(",\"").append(o.getColName()).append("\":\"").append(tmp_id).append("\"");
	        	        		}
	        	        	}else if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_NUMBER){
	                    	    s.append(",\"").append(o.getColName()).append("\":\"").append(HIUtil.NumFormat(tmp_id, o.getDataDotLen())).append("\"");
	        	        		
	        	        	}else if(o.getDataLen()>200 && tmp_id.length()>100){
	                            s.append(",\"").append(o.getColName()).append("\":\"").append(tmp_id.substring(0, 100)).append("...\"");
	                            
	                        }else{
	                    	    s.append(",\"").append(o.getColName()).append("\":\"").append(tmp_id).append("\"");
	        	        	}
	                    }
	                }
	                s.append("},\n");
	                k++;
	            }
	            s.setLength(s.length()-2);
	            s.append("]}\n");
	        }else{
	            s.append("{\"total\":0,\"rows\":[]}");
	        }
	        if(Constants.DATA_PRINT_FLAG) System.out.println(s.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
        return s.toString();
    }
    
	protected String saveQueryCondition(){
		return null;
	}
	protected List<I_DataTableColumn> getColumnList(String pageTab){
		List<I_DataTableColumn> collist = ao.getObject().getColumns();
		return collist;
	}
	protected String getKeywordColNames(){
        List<I_DataTableColumn> collist = ao.getObject().getColumns_keyword();
	    //////StringBuffer s = new StringBuffer("dataid");
	    StringBuffer s = new StringBuffer("");
	    if(collist!=null && collist.size()>0){
		    for(I_DataTableColumn o: collist){
		    	s.append(o.getColName_as()).append("||");
		    }
		    s.deleteCharAt(s.length()-1);
		    s.deleteCharAt(s.length()-1);
	    }
	    return s.toString();
	}
	
	private List<Object> loadDataList(){
    	String sql = this.getParameter("ks","");
    	if(!HIUtil.isEmpty(sql)) sql = dES.decrypt(sql);
    	else sql = saveQueryCondition();
    	return dbQuery(sql);
	}
	private Object[] getResult_List(List<Object> list) {        
        Object[] objs = null;        
        if(list!=null && list.size() > 0){
            List<I_DataTableColumn> collist = this.getColumnList(null);
        	int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            
            int j = 0;
            Object[] tmp = new Object[collist.size()+2];
            //tmp[j++] = new TColumn("序号", null, TColumn.ALIGN_CENTER);
            tmp[j++] = new TColumn("数据ID", null, TColumn.ALIGN_CENTER);
            for(I_DataTableColumn o: collist){
                tmp[j++] = new TColumn(o.getColMemo(), null, TColumn.ALIGN_CENTER);
            }
            tmp[j++] = new TColumn(B_Data.dataSctLevel_desc, null, TColumn.ALIGN_CENTER);
            objs[i++] = tmp;
            
            for(int k=0; k<list.size(); k++){
            	tmp = new Object[collist.size()+3];
            	j = 0;
            	tmp[j++] = "";
            	//tmp[j++] = (k+1);
            	tmp[j++] = DBUtil.getDBString(list.get(k), "dataid");
                for(I_DataTableColumn o: collist){
                	String tmp_id = DBUtil.getDBString(list.get(k), o.getColName());
    	        	if(!HIUtil.isEmpty(tmp_id) && o.getExtValueScopeTypeID()>0){
    	        		int paramTypeID = o.getExtValueScopeTypeID();
    	        		//数据应用字典
    	        		if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_SYS_DATAPARAM){
                            int param = Integer.parseInt(o.getExtValueScopeTypeParam());
                            //---------修补
                            if(tmp_id.length()<3) tmp_id = ""+(param*1000 + Integer.parseInt(tmp_id));
                            //-----end
    	        			tmp[j++] = ComBeanI_DataParamType.getText(Integer.parseInt(tmp_id));
    		        	//其他数据
    	        		}else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE){
    	        			String[] ss = tmp_id.split(Constants.SPLITER_DATA);
    	        			tmp[j++] = ss.length>1 ? ss[1] : tmp_id;
    	        		}
    	        		
    	        	}else{
    	        		tmp[j++] = tmp_id;
    	        	}
                }
                tmp[j++] = ComBeanI_SystemParam.getText(ParamClass.CLASS_SCT_LEVEL, DBUtil.getDBString(list.get(k), "dataSctLevel"));

                objs[i++] = tmp;
            }
        }
        return objs;
    }
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 8478063805436645281L;

	protected String c_keyword = "";
	
	protected int rowNum = 10; //批量添加生成表格行数

	public String getC_keyword() {
		return c_keyword;
	}

	public void setC_keyword(String c_keyword) {
		this.c_keyword = c_keyword;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
}
