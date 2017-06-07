package com.svv.dms.web.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanI_DataTable;
import com.svv.dms.web.common.ComBeanI_DataTableType;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanState;
import com.svv.dms.web.common.ComError;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.I_DataTable;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.entity.I_DataTableColumnExtInfo;
import com.svv.dms.web.entity.I_DataTableType;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.Pinyin4jUtil;
import com.svv.dms.web.util.TColumn;

public class I_DataTableBean extends AbstractBean {

    public String I_DataTable(){
        return this.exeByCmd("");
    }
    
    public String child(){
		I_DataTable parent = ComBeanI_DataTable.get(Integer.parseInt(ao.getParentTableID()));
		ao.setTableTypeID(parent.getTableTypeID());
		this.parentTableMemo = ComBeanI_DataTableType.getText(parent.getTableTypeID()) + " - " + parent.getTableMemo();
    	return "child";
    }
    
    public String XMLDataTableTree(){
        return this.exeByCmd("ForXMLTree");
    }
    public String getTreeDataForXMLTree(){
    	int parentID = this.getParameter("parentID", -1);
    	int relationID = this.getParameter("relationID", -1);
    	int level = parentID==-1 && relationID==-1 ? ParamClass.VALUE_LEVEL_ONE : -1;
		String s = getTreeData(parentID, relationID, level);
    	this.setMessage(true, s.toString());
    	//System.out.println(s.toString()); //####################################################
    	return BConstants.MESSAGE;
    }

	private String getTreeData(int parentID, int relationID, int level){
        StringBuilder s = new StringBuilder("");
    	try{
        	StringBuilder tmp = new StringBuilder("");
            if(parentID!=-1){
	            List<I_DataTable> list = ComBeanI_DataTable.getList(parentID);
	            if(list!=null && list.size()>0){
	                for(I_DataTable o: list){
	                	tmp.append("{\"rowid\":\"1_").append(o.getTableID()).append("\",\"text\":\"").append(o.getTableMemo()).append("\",\"iconCls\":\"icon-ok\"");
	                	tmp.append("},");
	                }
	            }
            }else if(relationID!=-1){
                List<Object> list = new ArrayList<Object>();
                List<Object> tmplist;        
                //父表
                tmplist = dbQuery(String.format("select tableID, 'P['||tableID||']' tableID_, tableMemo from I_DataTable t where exists (select m.tableid from I_DataTable m where m.parentTableID=t.tableID and m.tableid=%s)", new Object[]{relationID}));
	            if(tmplist!=null) list.addAll(tmplist);
                //子表
                tmplist = dbQuery(String.format("select tableID, 'C['||tableID||']' tableID_, tableMemo from I_DataTable t where t.parentTableID=%s", new Object[]{relationID}));
	            if(tmplist!=null) list.addAll(tmplist);
                //相同父表
	            tmplist = dbQuery(String.format("select tableID, 'B['||tableID||']' tableID_, tableMemo from I_DataTable t where exists (select m.tableid from I_DataTable m where m.parentTableID=t.parentTableID and t.parentTableID>0 and m.tableID=%s)", new Object[]{relationID}));
	            if(tmplist!=null) list.addAll(tmplist);
                //关联表
	            tmplist = dbQuery(String.format("select tableID, 'R['||tableID||']' tableID_, tableMemo from I_DataTable t where exists (select m.tableid from I_DataTableColumnExtInfo m where m.extValueScopeTypeID=%s and m.extValueScopeTypeParam=t.tableID and m.tableID=%s)", new Object[]{ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE, relationID}));
	            if(tmplist!=null) list.addAll(tmplist);
                //被关联表
	            tmplist = dbQuery(String.format("select tableID, 'T['||tableID||']' tableID_, tableMemo from I_DataTable t where exists (select m.tableid from I_DataTableColumnExtInfo m where m.tableID=t.tableID and m.extValueScopeTypeID=%s and m.extValueScopeTypeParam=%s)", new Object[]{ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE, relationID}));
	            if(tmplist!=null) list.addAll(tmplist);
                //间接关联表
	            tmplist = dbQuery(String.format("select tableID, 'F['||tableID||']' tableID_, tableMemo from I_DataTable t where exists (select m.tableid from I_DataTableColumnExtInfo m, I_DataTableColumnExtInfo n where n.tableID=t.tableID and m.extValueScopeTypeID=n.extValueScopeTypeID and m.extValueScopeTypeParam=n.extValueScopeTypeParam and m.extValueScopeTypeID=%s and m.tableID=%s)", new Object[]{ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE, relationID}));
	            if(tmplist!=null) list.addAll(tmplist);
	            
	            if(list!=null && list.size()>0){
	            	List<Integer> tables = new ArrayList<Integer>();
	                for(Object o: list){
	                	if(!tables.contains(DBUtil.getDBInt(o, "tableID"))){
	                		tables.add(DBUtil.getDBInt(o, "tableID"));
		                	tmp.append("{\"rowid\":\"1_").append(DBUtil.getDBString(o, "tableID_")).append("\",\"text\":\"").append(DBUtil.getDBString(o, "tableMemo")).append("\",\"iconCls\":\"icon-ok\"");
		                	tmp.append("},");
	                	}
	                }
	            }
            }

            if(level>0 && level <= ParamClass.VALUE_LEVEL_THREE){
	            List<I_DataTableType> list = ComBeanI_DataTableType.getList(parentID, ParamClass.VALUE_LEVEL_ONE, ParamClass.VALUE_LEVEL_FOUR);
	            //System.out.println("[getTreeData getTreeData getTreeData] parentID="+parentID+"] "+(list==null?0:list.size())); //####################################################
	            if(list!=null && list.size()>0){
	                for(I_DataTableType o: list){
	                	tmp.append("{\"rowid\":\"0_").append(o.getTableTypeID()).append("\",\"text\":\"").append(o.getTableTypeName()).append("\"");
	                    if(o.getChildNum()>0) tmp.append(",\"treeState\":\"closed\"");
	                    else tmp.append(",\"iconCls\":\"icon-file\"");
	                    //////if(o.getChildNum()>0) tmp.append(getTreeData(o.getTableTypeID(), level+1, false));
	                    tmp.append("},");
	                }
	            }
            }
            s.append("[");
            if(tmp.length() > 0){
	            s.append(tmp);
	            s.setLength(s.length()-1);
            }
            s.append("]");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return s.toString();
    }
    
    @SuppressWarnings("rawtypes")
    public String query(){
    	this.addOptionList(BConstants.option_query_string);
        if(isPowerEditTable()) this.addOptionList(BConstants.option_edit_string);
        if(isPowerDelTable()) this.addOptionList(BConstants.option_del_string);
        if(isPowerAddTable()) this.addOptionList(BConstants.option_copy_string);
        if(isPowerSQLOut()) this.addOptionList(BConstants.option_sql_string);
        if(isPowerSQLOut()) this.addOptionList("<a href=# class=button onclick=\"childTable(this,'%s','%s','%s')\">[子表]</a>");
        List list = getList(I_DataTable.class, dbQuery(SQL.SP_I_DataTableQueryByC(c_tableTypeID, c_keyword, ao.getParentTableID())));
        logger(ComBeanLogType.TYPE_QUERY, "查询数据表");
        setResult_List(list);
        return BConstants.LIST;
    }

    public String showDDL(){
    	if(isPowerSQLOut()){
	    	initParam();
	    	this.setMessage(true, toDDL_DOC(false, Integer.parseInt(ao.getParentTableID()), null, false, false) + toDDL_DOC(true, Integer.parseInt(ao.getParentTableID()), null, false, false));
    	}
    	return BConstants.MESSAGE;
    }
	@SuppressWarnings("unchecked")
	public String showDDL2(){
    	if(isPowerSQLOut()){
	    	this.ao = ComBeanI_DataTable.get(ao.getTableID());
	    	List<I_DataTableColumn> collist = getList(I_DataTableColumn.class, dbQuery(SQL.SP_I_DataTableColumnQueryByC(ao.getTableID()+"", "", "", false)));
	    	this.setMessage(true, toDDL_DOC(false, Integer.parseInt(ao.getParentTableID()), collist, false, false) + toDDL_DOC(true, Integer.parseInt(ao.getParentTableID()), collist, false, false));
    	}
    	return BConstants.MESSAGE;
    }

    public String add(){
    	if(isPowerAddTable()){
	        Object[] params = getSqlParams("add");
	        if(params!=null) if(dbExe("SP_I_DataTableManager", params)){
	        	logger(ComBeanLogType.TYPE_ADD, "添加数据表", params);
	        	I_DataTableTypeBean.Reflesh();
	        }
    	}
        return BConstants.ADDPAGE;
    }
    
    public String edit(){
    	if(isPowerEditTable()){
	        Object[] params = getSqlParams("edit");
	        if(params!=null) if(dbExe("SP_I_DataTableManager", params)){
	        	logger(ComBeanLogType.TYPE_EDIT, "修改数据表", params);
                I_DataTableTypeBean.Reflesh();
                synS_Module();
	        }
    	}
        return BConstants.EDITPAGE;
    }
    
    private void synS_Module(){
        List<String> sqls = new ArrayList<String>();
        
        String parentModuleID = "40.0"; //默认都放在【基本信息管理】
        String moduleID = parentModuleID + "0" + HIUtil.getCurrentDate("ss");
        String mainUrl = "biz/B_DataPub_" + ao.getTableID();
        List<Object> mainMenu = dbQuery("select moduleid from S_Module where url='"+mainUrl+"'");
        if(mainMenu==null || mainMenu.size()==0){
        	////////////////////////insert into S_Module(moduleID,moduleName,state,ismenu,parent,haschild,url,img,power) values('90.13','APP模块',1,1,'90.0',0,'biz/B_DataPub_23','','11111111');
        	sqls.add(String.format("insert into S_Module(moduleID,moduleName,state,ismenu,parent,haschild,url,img,power) values('%s','%s',1,0,'%s',0,'%s','','11111111')", new Object[]{moduleID, ao.getTableMemo(), parentModuleID, mainUrl}));

        	parentModuleID = moduleID;
        	moduleID = parentModuleID + ".0" + HIUtil.getCurrentDate("sss");
        }else{
        	parentModuleID = DBUtil.getDBString(mainMenu.get(0), "moduleid");
        	moduleID = parentModuleID + ".0" + HIUtil.getCurrentDate("sss");
        }
    	
    	String otherButtons = ao.getOtherButtons();
        if(!HIUtil.isEmpty(otherButtons)){
            String[] bs = otherButtons.split(",");
            for(int i=0; i<bs.length; i++){
                String[] ss = bs[i].split("\\|");
                String suburl = mainUrl + "_" + ss[1];
                
                List list = dbQuery("select moduleid from S_Module where url='"+suburl+"'");
                if(list==null || list.size()==0){                
	                String subModuleID = "";
	                List lastmenu = dbQuery("select max(moduleid) moduleid from S_Module where url like '"+mainUrl+"_%'");
	                if(lastmenu==null || lastmenu.size()==0){
	                	subModuleID = moduleID;
	                }else{
	                	String[] sss = DBUtil.getDBString(lastmenu.get(0), "moduleid").split("\\.");
	                	subModuleID = sss[0]+"."+sss[1]+"."+HIUtil.lPad((Integer.parseInt(sss[1])+1)+"", "00");
	                }
                
	            	////////////////////////insert into S_Module(moduleID,moduleName,state,ismenu,parent,haschild,url,img,power) values('90.13.02','复制模块及明细',1,0,'90.13',0,'biz/B_DataPub_23_BAppModule_CopyDetail','','')
                	sqls.add(String.format("insert into pcace.S_Module(moduleID,moduleName,state,ismenu,parent,haschild,url,img,power) values('%s','%s',1,0,'%s',0,'%s','','')", new Object[]{subModuleID, ss[0], parentModuleID, suburl}));
                }
            }
        }            

        if(sqls.size()>0 && dbExe(sqls)){
        	logger(ComBeanLogType.TYPE_EDIT, "同步S_Module表"+sqls.size()+"个", new Object[]{});
        }
    }
    
    public String copy(){
    	if(isPowerDelTable()){
	        Object[] params = new Object[]{"copy", ao.getTableID(), ao.getTableName(), "", "", "", "", -1, -1, -1};
	        if(dbExe("SP_I_DataTableManager", params)){
	        	logger(ComBeanLogType.TYPE_DEL, "复制数据表", params);
                I_DataTableTypeBean.Reflesh();
	        }
    	}
        return BConstants.MESSAGE;
    }
    public String del(){
    	if(isPowerDelTable()){
	        Object[] params = new Object[]{"del", ao.getTableID(), ao.getTableName(), "", "", "", "", -1, -1, -1};
	        if(dbExe("SP_I_DataTableManager", params)){
	        	logger(ComBeanLogType.TYPE_DEL, "删除数据表", params);
                I_DataTableTypeBean.Reflesh();
	        }
    	}
        return BConstants.MESSAGE;
    }
    public String delCol(){
        Object[] params = new Object[]{"delCol", dc.getColid(), dc.getColName(), "", "", "", "", -1, -1, -1};
        if(dbExe("SP_I_DataTableManager", params)){
            logger(ComBeanLogType.TYPE_DEL, "删除列", params);
            I_DataTableTypeBean.Reflesh();
        }
        return BConstants.MESSAGE;
    }
    public String updateColSeq(){
        Object[] params = new Object[]{"updateColSeq", dc.getColid(), dc.getSeq(), dc.getColName(), "", "", "", -1, -1, -1};
        if(dbExe("SP_I_DataTableManager", params)){
            logger(ComBeanLogType.TYPE_EDIT, "更改排序", params);
            I_DataTableTypeBean.Reflesh();
        }
        return BConstants.MESSAGE;
    }
    private Object[] getSqlParams(String type){
        if(colid==null || colid.length==0){
        	this.setMessage(false, "您没有输入参数，请检查！");
        	return null;
        }
    	initParam();
    	
    	ao.setParentTableID(this.getParameter("ao.parentTableID", "-1"));
    	Object[] tmpParams = new Object[]{ao.getTableName(), ao.getTableMemo(), ao.getServerPath(), ao.getTableTypeID(), ao.getSctLevel(), ao.getSequenceFlag(), ao.getPkFlag(), ao.getSidFlag(), ao.getScopeFlag(), ao.getAttachFileFlag(), ao.getDataStatusFlag(), ao.getSctLevelFlag(), ao.getOtherButtons(), ao.getParentTableID(), ao.getState()};
    	Object[] tmpStrs = new Object[]{
    			StringUtils.join(this.colid, Constants.SPLITER_SP),
    			StringUtils.join(this.colName, Constants.SPLITER_SP),
    			StringUtils.join(this.colMemo, Constants.SPLITER_SP),
    			StringUtils.join(this.dataTypeStr, Constants.SPLITER_SP),
    			StringUtils.join(this.dataType, Constants.SPLITER_SP),
    			StringUtils.join(this.dataLen, Constants.SPLITER_SP),
                StringUtils.join(this.dataDotLen, Constants.SPLITER_SP),
                StringUtils.join(this.nullFlag, Constants.SPLITER_SP),
                StringUtils.join(this.def, Constants.SPLITER_SP),
    			StringUtils.join(this.sctLevel, Constants.SPLITER_SP),
                StringUtils.join(this.seq, Constants.SPLITER_SP),
                StringUtils.join(this.listShowFlag, Constants.SPLITER_SP),
                StringUtils.join(this.showWidth, Constants.SPLITER_SP),
                StringUtils.join(this.formShowFlag, Constants.SPLITER_SP),
    			StringUtils.join(this.state, Constants.SPLITER_SP)
    			};
    	
        Object[] params = new Object[]{
        		type,
        		ao.getTableID(),
        		HIUtil.toSPParamSplitString(tmpParams),
        		HIUtil.toSPParamSplitString(tmpStrs),
        		toDDL_DB(Integer.parseInt(ao.getParentTableID())),
        		Constants.SPLITER,
        		Constants.SPLITER_SP,
        		tmpParams.length,
        		tmpStrs.length,
        		colid.length
        		};
        return params;
    }
    
	public String toDDLFile(){
    	this.setMessage(true, getDDLs(false, false));
    	return BConstants.MESSAGE;
    }
    
    @SuppressWarnings("unchecked")
	public String toUpgradeFile(){
    	StringBuilder s = new StringBuilder("");
		List<I_DataTable> list = getList(I_DataTable.class, dbQuery(SQL.SP_I_DataTableQueryByC(c_tableTypeID, c_keyword, "")));
        if(list!=null && list.size()>0){
        	for(I_DataTable o: list){
        	    if(o.getPkFlag()==1)
        	    	s.append(String.format("alter table %s drop constraint %s_PK;<br>", new Object[]{o.getTableName(), o.getTableName()}));
        		s.append(String.format("ALTER TABLE %s RENAME TO %s;<br>", new Object[]{o.getTableName(), "Z44_"+o.getTableName()}));
        		s.append(String.format("ALTER TABLE %s RENAME TO %s;<br>", new Object[]{"Z"+o.getTableName(), "Z44_Z"+o.getTableName()}));
        	}
        	
        	s.append(getDDLs(true, true));
        	
        	StringBuilder aa;
        	for(I_DataTable o: list){
        		aa = new StringBuilder("");
        		aa.append("insert into %s select dataid,").append(Integer.parseInt(o.getParentTableID())>0 ? "parentDataid,":"")
        		.append("dataSctLevel,dataStatus,'' keyword,istdate,uptdate");
            	List<I_DataTableColumn> collist = getList(I_DataTableColumn.class, dbQuery(SQL.SP_I_DataTableColumnQueryByC(o.getTableID()+"", "", "", false)));
                for(I_DataTableColumn col: collist){
                	aa.append(",").append(col.getColName());
                }
        		aa.append(" from %s order by dataid;<br>");
        		s.append(String.format(aa.toString(), new Object[]{o.getTableName(), "Z44_"+o.getTableName()}));

        		aa = new StringBuilder("");
        		aa.append("insert into %s select iid,imemo,dataid,").append(Integer.parseInt(o.getParentTableID())>0 ? "parentDataid,":"")
        		.append("dataSctLevel,dataStatus,'' keyword,istdate,uptdate");
            	for(I_DataTableColumn col: collist){
                	aa.append(",").append(col.getColName());
                }
        		aa.append(" from %s order by dataid;<br>");
        		s.append(String.format(aa.toString(), new Object[]{"Z"+o.getTableName(), "Z44_Z"+o.getTableName()}));
        	}
        }
        this.setMessage(true, s.toString());
    	return BConstants.MESSAGE;
    }
    
    @SuppressWarnings("unchecked")
	public String resetKeyword(){
    	StringBuilder s = new StringBuilder();
		List<I_DataTable> list = getList(I_DataTable.class, dbQuery(SQL.SP_I_DataTableQueryByC(c_tableTypeID, c_keyword, "")));
        if(list!=null && list.size()>0){
        	StringBuilder keyCols = new StringBuilder();
        	StringBuilder keyword = new StringBuilder();
        	String paramValue;
        	List<Object> datalist;
        	List<String> sqls = new ArrayList<String>();
        	for(I_DataTable t: list){
            	List<I_DataTableColumn> collist = getList(I_DataTableColumn.class, dbQuery(SQL.SP_I_DataTableColumnQueryByC(t.getTableID()+"", "", "", false)));
            	keyCols = new StringBuilder();
            	int k = 0;
            	for(I_DataTableColumn col: collist){
            		if(col.getExtKeyNameFlag()==1)
            			keyCols.append(", ").append(col.getColName()).append(" k"+(k++));
                }
            	if(k>0){
	            	datalist = dbQuery(String.format("select dataid %s from %s", new Object[]{keyCols, t.getTableName()}));
	        		for(Object o: datalist){
	        			keyword = new StringBuilder();
	        			paramValue = DBUtil.getDBString(o, "k0");
	        			if(paramValue.length()>0)
	            			keyword.append(paramValue).append(" ").append(Pinyin4jUtil.getPinYin(paramValue)).append(" ").append(Pinyin4jUtil.getPinYinHeadChar(paramValue)).append(" ");
	        			paramValue = DBUtil.getDBString(o, "k1");
	        			if(paramValue.length()>0)
	            			keyword.append(paramValue).append(" ").append(Pinyin4jUtil.getPinYin(paramValue)).append(" ").append(Pinyin4jUtil.getPinYinHeadChar(paramValue)).append(" ");
	        			paramValue = DBUtil.getDBString(o, "k2");
	        			if(paramValue.length()>0)
	            			keyword.append(paramValue).append(" ").append(Pinyin4jUtil.getPinYin(paramValue)).append(" ").append(Pinyin4jUtil.getPinYinHeadChar(paramValue)).append(" ");
	        		
	        			sqls.add(String.format("update %s set keyword='%s' where dataid=%s", new Object[]{t.getTableName(), keyword.toString(), DBUtil.getDBString(o, "dataid")}));
	        			if(sqls.size()>=100){
	        				if(dbExe(sqls)){
	        					sqls.clear();
	        				}else{
	        					s.append(sqls.toString());
	        				}
	        			}
	        		}
            	}
    			if(sqls.size()>0){
    				if(dbExe(sqls)){
    					sqls.clear();
    				}else{
    					s.append(sqls.toString());
    				}
    			}
        	}
        }
        if(s.length()>0){
            this.setMessage(false, "全部表的关键字设置完毕." + s.toString());        	
        }else{
            this.setMessage(true, "全部表的关键字设置完毕.");
        }
    	return BConstants.MESSAGE;
    }
    
    @SuppressWarnings("unchecked")
    private String getDDLs(boolean noSeq, boolean noDrop){
    	StringBuilder s = new StringBuilder("");
    	if(isPowerSQLOut()){
    		int z = this.getParameter("Z", 0); //z=1仅取历史表
    		List<I_DataTable> list = getList(I_DataTable.class, dbQuery(SQL.SP_I_DataTableQueryByC(c_tableTypeID, c_keyword, "")));
	        if(list!=null && list.size()>0){
	        	for(I_DataTable o: list){
	        		this.ao = o;
	            	List<I_DataTableColumn> collist = getList(I_DataTableColumn.class, dbQuery(SQL.SP_I_DataTableColumnQueryByC(o.getTableID()+"", "", "", false)));
	        		if(z==0) s.append(this.toDDL_DOC(false, Integer.parseInt(o.getParentTableID()), collist, noSeq, noDrop));
	        		s.append(this.toDDL_DOC(true, Integer.parseInt(o.getParentTableID()), collist, noSeq, noDrop));
	        	}
	        }
    	}
    	return s.toString();
    }

    //生成create语句
    private String toDDL_DOC(boolean isHis, int parentTableID, List<I_DataTableColumn> collist, boolean noSeq, boolean noDrop){
    	StringBuilder s = new StringBuilder("");
        String his = isHis ? "Z" : "";

		s.append("<span style=\"font-family:Trebuchet MS, Tahoma, Verdana, Arial;\"><br>");
		if(!isHis){
			if(ao.getSequenceFlag()==1){
				if(!noSeq){
			        s.append("DROP SEQUENCE ").append(Constants.DBUSER).append(".seq").append(ao.getTableName()).append(";<br>");
			        s.append("CREATE SEQUENCE ").append(Constants.DBUSER).append(".seq").append(ao.getTableName()).append(" MINVALUE 1 MAXVALUE 99999999999999 START WITH 1 INCREMENT BY 1 CACHE 10;<br>");
			    }
			}
			if(!noDrop){
			    s.append("DROP TABLE ").append(Constants.DBUSER).append(".").append(ao.getTableName()).append(";<br>");
			    s.append("DROP TABLE ").append(Constants.DBUSER).append(".Z").append(ao.getTableName()).append(";<br>");
			}
		}
		if(HIUtil.isOracle()){
    		s.append("prompt<br>");
        	s.append("prompt Creating table ").append(Constants.DBUSER).append(".").append(his).append(ao.getTableName()).append(" ").append(ao.getTableMemo()).append("<br>");
    		s.append("prompt =============================<br>");
    		s.append("prompt<br>");
		}
		s.append(toDDL(isHis, parentTableID, collist, "<br>"));
		s.append("<br></span>");
    	return s.toString().replaceAll("#", "&nbsp;");
    }

    //生成create语句(数据库执行)
    private String toDDL_DB(int parentTableID){
        return ""; //################################################
        /*
        StringBuilder s = new StringBuilder("");
        s.append(toDDL(false, parentTableID, null, ""));
        return s.toString().replaceAll("#", " ");*/
    }
    
    private String toDDL(boolean isHis, int parentTableID, List<I_DataTableColumn> collist, String br_str){
        String his = isHis ? "Z" : "";
        String tableSpace_mk = "";
        if(ao.getTableName().endsWith("Log")) tableSpace_mk = "LOG";
        else if(isHis || ao.getTableName().startsWith("T_")) tableSpace_mk = "HIS";
    	
    	StringBuilder s = new StringBuilder("");
        s.append("CREATE TABLE ").append(Constants.DBUSER).append(".").append(his).append(ao.getTableName()).append("(").append(br_str);
        if(isHis){
        	s.append(toFormatSQLLine("iid", getDataTypeStr(ParamClass.VALUE_DATA_TYPE_NUMBER, 14, 0, null, 0)+" " +(HIUtil.isMysql()?" AUTO_INCREMENT":""), "", br_str));
        	s.append(toFormatSQLLine("imemo", getDataTypeStr(ParamClass.VALUE_DATA_TYPE_VARCHAR2, 500, 0, null, 1), "", br_str));
        }
        s.append(toFormatSQLLine("dataid", getDataTypeStr(ParamClass.VALUE_DATA_TYPE_NUMBER, 11, 0, null, isHis?1:0), "数据ID", br_str));
        if(parentTableID > 0) s.append(toFormatSQLLine("parentDataid", getDataTypeStr(ParamClass.VALUE_DATA_TYPE_NUMBER, 9, 0, null, 0)+" ", "父表数据ID", br_str));
        s.append(toFormatSQLLine("dataSctLevel", getDataTypeStr(ParamClass.VALUE_DATA_TYPE_NUMBER, 6, 0, isHis?null:"-1", isHis?1:0), "数据秘密等级", br_str));
        s.append(toFormatSQLLine("dataStatus", getDataTypeStr(ParamClass.VALUE_DATA_TYPE_NUMBER, 6, 0, isHis?null:"-1", isHis?1:0), "数据状态", br_str));
    	s.append(toFormatSQLLine("keyword", getDataTypeStr(ParamClass.VALUE_DATA_TYPE_VARCHAR2, 200, 0, null, 1), "搜索关键字", br_str));
        s.append(toFormatSQLLine("istdate", getDataTypeStr(ParamClass.VALUE_DATA_TYPE_DATETIME, 0, 0, "", 0), "插入日期", br_str));
        s.append(toFormatSQLLine("uptdate", getDataTypeStr(ParamClass.VALUE_DATA_TYPE_DATETIME, 0, 0, null, 0), "更新日期", br_str));
        if(collist==null){ //编辑页面中查看
            for(int i=0;i<colid.length;i++) s.append(toFormatSQLLine(colName[i], dataTypeStr[i], colMemo[i], br_str));
        }else{ //主列表中查看
            for(I_DataTableColumn o: collist) s.append(toFormatSQLLine(o.getColName(), o.getDataTypeStr(), o.getColMemo(), br_str));
        }
        if(isHis||ao.getPkFlag()==0) s.replace(s.lastIndexOf(","), s.lastIndexOf(",")+1, "");
        if(HIUtil.isOracle()){
        	if(ao.getPkFlag()==1 && !isHis) s.append(toFormatSQLLine("CONSTRAINT "+(his+ao.getTableName())+"_PK PRIMARY KEY ("+(isHis?"iid":"dataid")+")", "", "", br_str));
            s.append(") tablespace TableSpace_").append(Constants.DBUSER).append(tableSpace_mk).append(" pctfree 10 initrans 1 maxtrans 255 storage ( initial 16K minextents 1 maxextents unlimited );").append(br_str);
        }else if(HIUtil.isMysql()){
        	//if(ao.getPkFlag()==1){
            //	s.append(toFormatSQLLine("PRIMARY KEY (`"+(isHis?"iid":"dataid")+"`),", "", "", br_str));
            //    s.append(toFormatSQLLine("UNIQUE KEY `"+(isHis?"iid":"dataid")+"`(`"+(isHis?"iid":"dataid")+"`)", "", "", br_str));
        	//}
            s.append(") ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8;").append(br_str);
        }
        return s.toString();
    }
    
    private StringBuilder toFormatSQLLine(String name, String type, String memo, String br_str){
    	return toFormatSQLLine(name, type, memo, br_str, false);
    }
    private StringBuilder toFormatSQLLine(String name, String type, String memo, String br_str, boolean end){
    	StringBuilder rtn = new StringBuilder(HIUtil.lPad("", "#", 4)).append(name);
    	if(!HIUtil.isEmpty(type)) rtn.append(HIUtil.lPad("", "#", 35-rtn.length())).append(type).append(end?"":",");
    	if(!HIUtil.isEmpty(memo)) rtn.append(HIUtil.lPad("", "#", 65-rtn.length())).append(" --").append(memo);
    	rtn.append(br_str);
		return rtn;
    }
    private void initParam(){
        if(colid!=null && colid.length>0){
	    	dataTypeStr = new String[colid.length];
	    	for(int i=0;i<colid.length;i++){
	    	    dataTypeStr[i] = getDataTypeStr(Integer.parseInt(dataType[i]), Integer.parseInt(dataLen[i]), Integer.parseInt(dataDotLen[i]), def[i], Integer.parseInt(nullFlag[i]));
	    	}
        }
    }

    private String getDataTypeStr(int dataType, int dataLen, int dotLen, String def, int nullFlag){
        String rtn = "";
        String format_def = "'%s'";
        if(HIUtil.isOracle()){
            if(dataType == ParamClass.VALUE_DATA_TYPE_NUMBER){
                rtn = "NUMBER("+dataLen+","+dotLen+")";
                format_def = "%s";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_VARCHAR2){
                rtn = "VARCHAR2("+dataLen+")";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_MD5){
                rtn = "VARCHAR2("+dataLen+")";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_DATE){ //日期
                rtn = "VARCHAR2("+dataLen+")";
                format_def = "%s";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_CHAR){
                rtn = "CHAR("+dataLen+")";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_TEXT){ //长文本
                rtn = "CLOB";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_DATETIME){ //日期时间
                rtn = "DATE";
                if(nullFlag==0) rtn = rtn + " DEFAULT sysdate";
                nullFlag = 1;
                def = null;
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_FILE || dataType == ParamClass.VALUE_DATA_TYPE_IMGFILE){ //附件
                rtn = "VARCHAR2("+dataLen+")";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_IMGLINK){ //图片链接
                rtn = "VARCHAR2("+dataLen+")";
            }else{
                rtn = "VARCHAR2("+dataLen+")";
            }
        }else if(HIUtil.isMysql()){
            if(dataType == ParamClass.VALUE_DATA_TYPE_NUMBER){
                if(dotLen==0) rtn = "INTEGER("+dataLen+")";
                else rtn = "DOUBLE("+dataLen+","+dotLen+")";
                format_def = "%s";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_VARCHAR2){
                rtn = "VARCHAR("+dataLen+")";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_MD5){
                rtn = "VARCHAR("+dataLen+")";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_DATE){ //日期
                rtn = "VARCHAR("+dataLen+")";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_CHAR){
                rtn = "INTEGER("+dataLen+")";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_TEXT){ //长文本
                rtn = "TEXT";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_DATETIME){ //日期时间
                rtn = "TIMESTAMP";
                if(def==null) rtn = rtn + " DEFAULT CURRENT_TIMESTAMP";
                else if(def.equals("")) rtn = rtn + " DEFAULT '2000-01-01 00:00:00'";
                nullFlag = 1;
                def = null;
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_FILE || dataType == ParamClass.VALUE_DATA_TYPE_IMGFILE){ //附件
                rtn = "VARCHAR("+dataLen+")";
            }else if(dataType == ParamClass.VALUE_DATA_TYPE_IMGLINK){ //图片链接
                rtn = "VARCHAR("+dataLen+")";
            }else{
                rtn = "VARCHAR("+dataLen+")";
            }
        }
        if(nullFlag==0 && (def==null || def.length()==0)) rtn = rtn + " NOT NULL";
        if(def!=null && def.length()>0) rtn = rtn + " DEFAULT " + String.format(format_def, def);
        return rtn;
    }
    
    @SuppressWarnings("unchecked")
	protected void loadByID(){
        List<I_DataTable> list = getList(I_DataTable.class, dbQuery("SP_I_DataTableQueryByID", new Object[]{ ao.getTableID() }));
        if (list == null ){
            this.setMessage(false, ComError.err_000001);
        }else{
            ao = list.get(0);
        }
    }

	public String queryRows(){
    	if(isSuperAdmin()) this.addOptionList("<a href=\"javascript:editExt(this,'%s','%s','%s')\"><元数据></a>");
    	if(isSuperAdmin()) this.addOptionList("<a href=\"javascript:updateColSeq(this,'%s','%s','%s')\"><更改排序></a>");
    	if(isPowerDelTable()) this.addOptionList(BConstants.option_del_string);
    	
    	this.rowNum = 0;
        List<I_DataTableColumn> collist = ao.getColumns();
        if(collist!=null && rowNum < collist.size()) rowNum = collist.size();
        
        Object[] objs = null;
        int i = 0;
        objs = new Object[rowNum+2];
        objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
        objs[i++] = new Object[]{new TColumn(I_DataTableColumn.seq_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.colMemo_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.colName_desc+"[标注]", null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.dataType_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumnExtInfo.extValueScope_desc),
                                 new TColumn(I_DataTableColumn.sctLevel_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn("操作", null, TColumn.ALIGN_LEFT)};
        for(int k=0; k<rowNum; k++){
        	I_DataTableColumn o = (collist!=null && k<collist.size()) ? collist.get(k) : new I_DataTableColumn();
            objs[i++] = new Object[]{"",
                                     "<b>B"+o.getSeq()+"</b>",
                                     "<span title="+o.getColid()+">"+o.getColMemo()+"</span>" + (o.getExtKeyNameFlag()==1 ? " <font color=red><b>*</b></font>":"") + (o.getExtKeywordFlag()==1 ? " <font color=yellow><b>*</b></font>":"") + (o.getExtQueryByFlag()==1 ? " <font color=green><b>*</b></font>":""),
                                     o.getColName()+(HIUtil.isEmpty(o.getExtNameEn())?"":"&nbsp;&nbsp;&nbsp;&nbsp;["+o.getExtNameEn()+"]"),
                                     o.getDataTypeStr(),
                                     o.getExtValueScope(),
                                     ComBeanI_SystemParam.getText(ParamClass.CLASS_SCT_LEVEL, o.getSctLevel()),
                                     this.getOptionHtmlString(o.getColid()+"", o.getColName(), o.getColMemo()),
                                     };
        }
        
        this.setResultList(objs);        
    	return BConstants.LIST;
    }
    
    public String queryXMLCols() {
        String[] ss = this.getParameter("s", "").split(",");
        int relationFlag = this.getParameter("relationFlag", 0);
        
        List<I_DataTableColumn> collist = relationFlag==0? ao.getColumns() : ao.getColumns_relation();
        Object[] objs = null;
        if(collist!=null){
	        int i = 0;
	        objs = new Object[collist.size()+2];
	        objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
	        objs[i++] = new Object[]{new TColumn(I_DataTableColumn.seq_desc, null, TColumn.ALIGN_LEFT),
	                                 new TColumn(I_DataTableColumn.colMemo_desc, null, TColumn.ALIGN_LEFT),
	                                 new TColumn("选择", null, TColumn.ALIGN_LEFT)};
	        for(I_DataTableColumn o: collist){
	            String checked = "";
	            if(ss.length>0){
		            for(String s: ss){
		                if(s.length()>0 && Integer.parseInt(s)==o.getColid()){
		                    checked = " checked";
		                    break;
		                }
		            }
	            }
	            objs[i++] = new Object[]{"",
	                                     "<b>B"+o.getSeq()+"</b>",
	                                     o.getColMemo() + (o.getExtKeyNameFlag()==1 ? " <font color=red><b>*</b></font>":""),
	                                     "<input type=checkbox class=checkbox id=sel_colid value="+o.getColid()+" "+checked+">"+o.getColMemo()+"|"+o.getColName()+"",
	                                     };
	        }
        }
        this.setResultList(objs);        
        return BConstants.LIST;
    }

	public String addRows(){
    	this.rowNum = this.getParameter("rowNum", 0);
        List<I_DataTableColumn> collist = ao.getColumns();
        if(collist!=null && rowNum < collist.size()) rowNum = collist.size();
        
        Object[] objs = null;
        int i = 0;
        objs = new Object[rowNum+2];
        objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
        objs[i++] = new Object[]{new TColumn(I_DataTableColumn.seq_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.colMemo_desc, TColumn.ALIGN_LEFT, 280),
                                 new TColumn(I_DataTableColumn.colName_desc, TColumn.ALIGN_LEFT, 200),
                                 new TColumn(I_DataTableColumn.dataType_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.dataLen_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.dataDotLen_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.nullFlag_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.def_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.listShowFlag_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.showWidth_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.formShowFlag_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.sctLevel_desc, null, TColumn.ALIGN_LEFT)};
        for(int k=0; k<rowNum; k++){
        	I_DataTableColumn o = collist!=null && k<collist.size() ? collist.get(k) : new I_DataTableColumn();
        	if(o.getSeq()<k+1) o.setSeq(k+1);
            objs[i++] = new Object[]{"doFocus("+(k+1)+")",
            		                 "<b>B</b><input type=text name=\"seq\" id=\"seq_"+(k+1)+"\" value=\""+o.getSeq()+"\" class=\"ipt_read\" maxlength=3 size=2 onblur=\"intInput(this);\" onafterpaste=\"intInput(this)\">" + "<input type=hidden name=\"colid\" id=\"colid_"+(k+1)+"\" value=\""+o.getColid()+"\"><input type=hidden name=\"state\" id=\"state_"+(k+1)+"\" value=\""+o.getState()+"\">",
                                     "<input type=text name=\"colMemo\" id=\"colMemo_"+(k+1)+"\" value=\""+o.getColMemo()+"\" class=\"tableInput\" maxlength=100 size=50>",
                                     "<input type=text name=\"colName\" id=\"colName_"+(k+1)+"\" value=\""+HIUtil.isEmpty(o.getColName(),"c"+(k+1))+"\" class=\"ReadOnly\" maxlength=50 readOnly=true>",
                                     "<span id=\"dataType_"+(k+1)+"\"><select class=sel_text name=\"dataType\" value=\""+o.getDataType()+"\"></select><input type=hidden name=\"dataType_\" value=\""+o.getDataType()+"\"></span>",
                                     "<input type=text name=\"dataLen\" id=\"dataLen_"+(k+1)+"\" value=\""+o.getDataLen()+"\" class=\"tableInput\" maxlength=6 size=6 onblur=\"intInput(this);\" onafterpaste=\"intInput(this)\">",
                                     "<input type=text name=\"dataDotLen\" id=\"dataDotLen_"+(k+1)+"\" value=\""+o.getDataDotLen()+"\" class=\"tableInput\" maxlength=4 size=4 onblur=\"intInput(this);\" onafterpaste=\"intInput(this)\">",
                                     "<input type=hidden name=nullFlag id=\"nullFlag_"+(k+1)+"\" value=\""+HIUtil.isEmpty(o.getNullFlag(),"1")+"\"><input type=checkbox class=checkbox "+(HIUtil.isEmpty(o.getNullFlag(),"1").equals("1") ? "checked" : "")+" onblur=\"d('nullFlag_"+(k+1)+"').value=this.checked?1:0\">",
                                     "<input type=text name=\"def\" id=\"def_"+(k+1)+"\" value=\""+o.getDef()+"\" class=\"tableInput\" maxlength=100 size=10>",
                                     "<input type=hidden name=listShowFlag id=\"listShowFlag_"+(k+1)+"\" value=\""+HIUtil.isEmpty(o.getListShowFlag(),"1")+"\"><input type=checkbox class=checkbox "+(HIUtil.isEmpty(o.getListShowFlag(),"1").equals("1") ? "checked" : "")+" onblur=\"d('listShowFlag_"+(k+1)+"').value=this.checked?1:0\">",
                                     "<input type=text name=\"showWidth\" id=\"showWidth_"+(k+1)+"\" value=\""+o.getShowWidth()+"\" class=\"tableInput\" maxlength=6 size=6 onblur=\"intInput(this);\" onafterpaste=\"intInput(this)\">",
                                     "<input type=hidden name=formShowFlag id=\"formShowFlag_"+(k+1)+"\" value=\""+HIUtil.isEmpty(o.getFormShowFlag(),"1")+"\"><input type=checkbox class=checkbox "+(HIUtil.isEmpty(o.getFormShowFlag(),"1").equals("1") ? "checked" : "")+" onblur=\"d('formShowFlag_"+(k+1)+"').value=this.checked?1:0\">",
                                     "<span id=\"sctLevel_"+(k+1)+"\"><select class=sel_text name=\"sctLevel\" value=\""+o.getSctLevel()+"\"></select><input type=hidden name=\"sctLevel_\" value=\""+o.getSctLevel()+"\"></span>",};
        }
        
        this.setResultList(objs);        
    	return BConstants.LIST;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
	private void setResult_List(List list) {        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            
            int c = 0;
            int cols = this.isSuperAdmin() ? 10 : 6;
            Object[] tmp = new Object[cols];
            tmp[c++] = new TColumn("序号", null, TColumn.ALIGN_LEFT);
            //tmp[c++] = new TColumn(I_DataTable.tableID_desc, "tableID", TColumn.ALIGN_LEFT);
            tmp[c++] = new TColumn(I_DataTable.tableTypeID_desc, "tableTypeID", TColumn.ALIGN_LEFT);
            tmp[c++] = new TColumn(I_DataTable.tableName_desc, "tableName", TColumn.ALIGN_LEFT);
            tmp[c++] = new TColumn(I_DataTable.tableMemo_desc, "tableMemo", TColumn.ALIGN_LEFT);
            if(this.isSuperAdmin()){
                tmp[c++] = new TColumn(I_DataTable.serverPath_desc, "serverPath", TColumn.ALIGN_LEFT);
            	tmp[c++] = new TColumn(I_DataTable.colNum_desc, "colNum", TColumn.ALIGN_LEFT);
            	tmp[c++] = new TColumn(I_DataTable.dataNum_desc, "dataNum", TColumn.ALIGN_LEFT);
            }
            //tmp[c++] = new TColumn(I_DataTable.creator_desc, "creator", TColumn.ALIGN_LEFT);
            tmp[c++] = new TColumn(I_DataTable.state_desc, "state", TColumn.ALIGN_LEFT);
            if(this.isSuperAdmin()) tmp[c++] = new TColumn(I_DataTable.istDate_desc, "istDate", TColumn.ALIGN_LEFT);
            tmp[c++] = new TColumn("操作", null, TColumn.ALIGN_LEFT);
            objs[i++] = tmp;
            
            for(I_DataTable o: (List<I_DataTable>)list){
            	c = 0;
            	tmp = new Object[cols+1];
            	tmp[c++] = "";
            	tmp[c++] = (i-1);
            	//tmp[c++] = o.getTableID();
            	tmp[c++] = Integer.parseInt(o.getParentTableID())>0 ? (ComBeanI_DataTable.getMemo(Integer.parseInt(o.getParentTableID()))+" - 子表") : (o.getTableTypeID() + " - " + ComBeanI_DataTableType.getText(o.getTableTypeID()));
            	tmp[c++] = o.getTableName();
            	tmp[c++] = "<span title="+o.getTableID()+">"+o.getTableMemo()+"</span>";
            	if(this.isSuperAdmin()){
                	tmp[c++] = o.getServerPath();
            		tmp[c++] = o.getColNum();
            		tmp[c++] = o.getDataNum();
            	}
            	//tmp[c++] = o.getCreator();
            	tmp[c++] = ComBeanState.getText(o.getState());
            	if(this.isSuperAdmin()) tmp[c++] = o.getIstDate();
            	tmp[c++] = this.getOptionHtmlString(o.getTableID()+"", o.getTableName(), o.getTableMemo());
                objs[i++] = tmp;
            }
            
        }
        this.setResultList(objs);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -8422475366273959921L;

    protected String c_tableTypeID = "";
    protected String c_keyword = "";

    protected I_DataTable ao = new I_DataTable();
    protected I_DataTableColumn dc = new I_DataTableColumn();

    protected String parentTableMemo = "";
    protected String parentTableTypeName = "";
    
    protected int rowNum = 15;
    protected String[] colid = null;
    protected String[] colName = null;
    protected String[] colMemo = null;
    protected String[] dataTypeStr = null;
    protected String[] dataType = null;
    protected String[] dataLen = null;
    protected String[] dataDotLen = null;
    protected String[] nullFlag = null;
    protected String[] def = null;
    protected String[] sctLevel = null;
    protected String[] seq = null;
    protected String[] listShowFlag = null;
    protected String[] showWidth = null;
    protected String[] formShowFlag = null;
    protected String[] state = null;

    public boolean isSuperAdmin(){
    	return super.isSuperAdmin() && checkModulePath("I_DataTable");
    }
    public boolean isPowerSQLOut(){
    	return isSuperAdmin();
    }
    public boolean isPowerAddTable(){
    	return this.isSuperAdmin();// || this.getUserSession().isAdmin();
    }
    public boolean isPowerEditTable(){
    	return this.isSuperAdmin();// || this.getUserSession().isAdmin();
    }
    public boolean isPowerDelTable(){
    	return this.isSuperAdmin();// || this.getUserSession().isAdmin();
    }
    public List<LabelValueBean> getSctLevelList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_SCT_LEVEL, false, "");
    }
    
    public List<LabelValueBean> getDataTypeList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_DATA_TYPE, false, "");
    }
    
    public List<LabelValueBean> getC_tableTypeList() {
        return ComBeanI_DataTableType.getList(-1, isSuperAdmin(), "");
    }
    
    public List<LabelValueBean> getTableTypeList() {
        return ComBeanI_DataTableType.getList(-1, false, "");
    }

    public List<LabelValueBean> getStateList() {
        return ComBeanState.getList();
    }

    public I_DataTable getAo() {
		return ao;
	}

	public void setAo(I_DataTable ao) {
		this.ao = ao;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String[] getColid() {
		return colid;
	}

	public void setColid(String[] colid) {
		this.colid = colid;
	}

	public String[] getColName() {
		return colName;
	}

	public void setColName(String[] colName) {
		this.colName = colName;
	}

	public String[] getColMemo() {
		return colMemo;
	}

	public void setColMemo(String[] colMemo) {
		this.colMemo = colMemo;
	}

	public String[] getDataType() {
		return dataType;
	}

	public void setDataType(String[] dataType) {
		this.dataType = dataType;
	}

	public String[] getDataLen() {
		return dataLen;
	}

	public void setDataLen(String[] dataLen) {
		this.dataLen = dataLen;
	}

	public String[] getDataDotLen() {
		return dataDotLen;
	}

	public void setDataDotLen(String[] dataDotLen) {
		this.dataDotLen = dataDotLen;
	}

	public String[] getSctLevel() {
		return sctLevel;
	}

	public void setSctLevel(String[] sctLevel) {
		this.sctLevel = sctLevel;
	}
	public String[] getState() {
		return state;
	}

	public void setState(String[] state) {
		this.state = state;
	}

	public I_DataTableColumn getDc() {
		return dc;
	}

	public void setDc(I_DataTableColumn dc) {
		this.dc = dc;
	}

    public String getC_tableTypeID() {
        return c_tableTypeID;
    }

    public void setC_tableTypeID(String typeID) {
        c_tableTypeID = typeID;
    }

    public String getC_keyword() {
        return c_keyword;
    }

    public void setC_keyword(String c_keyword) {
        this.c_keyword = c_keyword;
    }
	public String getParentTableMemo() {
		return parentTableMemo;
	}
	public void setParentTableMemo(String parentTableMemo) {
		this.parentTableMemo = parentTableMemo;
	}
	public String getParentTableTypeName() {
		return parentTableTypeName;
	}
	public void setParentTableTypeName(String parentTableTypeName) {
		this.parentTableTypeName = parentTableTypeName;
	}
	public String[] getSeq() {
		return seq;
	}
	public void setSeq(String[] seq) {
		this.seq = seq;
	}
    public String[] getNullFlag() {
        return nullFlag;
    }
    public void setNullFlag(String[] nullFlag) {
        this.nullFlag = nullFlag;
    }

    public String[] getListShowFlag() {
        return listShowFlag;
    }

    public void setListShowFlag(String[] listShowFlag) {
        this.listShowFlag = listShowFlag;
    }

    public String[] getShowWidth() {
        return showWidth;
    }

    public void setShowWidth(String[] showWidth) {
        this.showWidth = showWidth;
    }

    public String[] getFormShowFlag() {
        return formShowFlag;
    }

    public void setFormShowFlag(String[] formShowFlag) {
        this.formShowFlag = formShowFlag;
    }

	public String[] getDef() {
		return def;
	}

	public void setDef(String[] def) {
		this.def = def;
	}
    
}
