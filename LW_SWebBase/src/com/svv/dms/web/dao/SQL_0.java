package com.svv.dms.web.dao;

import com.gs.db.util.DateUtil;
import com.svv.dms.web.util.HIUtil;

public class SQL_0{
    
    public static String SP_S_UserLogin = "select * from VS_User where state=1 and loginName='%s' and password='%s'";
    public static String SP_S_UserQueryByC(int myRoleID, String c_organID){
        return new StringBuilder(" select * from VS_User where userID>1 and roleID>").append(myRoleID)
        .append(getWhere_number("organID", c_organID))
        .append(" order by userID desc ").toString();
    }
    public static String SP_S_UserQuery = "select * from VS_User";
    public static String SP_S_UserQueryByUserID = "select * from VS_User where userID=%s";
    public static String SP_S_UserUpdateState = "update S_User set state=%s where userID=%s";
    public static String SP_S_UserUpdateCssStyle = "update S_User set cssStyle=%s where userID=%s";

    public static String SP_S_RoleQuery = "select * from S_Role where roleID>1 order by organType,roleSeq";
    public static String SP_S_RoleQueryByOrganType = "select * from S_Role where (organType=%s or organType=%s) and roleSeq>NVL((select roleSeq from S_Role b where b.roleID=%s),0) order by roleSeq";
    public static String SP_S_RoleQueryByRole = "select * from S_Role where roleSeq>NVL((select roleSeq from S_Role b where b.roleID=%s),0) order by organType,roleSeq";

    public static String SP_S_ModuleQuery = "select a.*, a.power rolePower from S_Module a order by moduleID";
    public static String SP_S_ModuleQueryByState = "select a.*, a.power rolePower from S_Module a where state=1 order by moduleID";
    public static String SP_S_ModuleQueryByRole = "select a.*,b.power rolePower from S_Module a, S_Role_Module b where a.moduleID=b.moduleID and a.state=1 and roleID=%d order by a.moduleID";
    public static String SP_S_ModuleQueryByBizType = "select a.*, a.power rolePower from S_Module where bizType=%s order by moduleID";

    public static String SP_S_ParamQuery = "select * from S_Param order by id";
    public static String SP_S_PlatFormQuery = "select * from S_PlatForm order by PlatFormID";
    public static String SP_S_QuickMenuQuery = "select a.*,b.img moduleImg from S_QuickMenu a, S_Module b where a.moduleID=b.moduleID and a.userID=%s order by a.seq";

    public static String SP_LogQueryByC(String logTable, String c_startTime, String c_endTime, String c_keyword){
        return new StringBuilder(" select a.* from "+logTable+" a where 1 = 1 ")
        .append(getWhere_seDate("istdate", c_startTime, c_endTime))
        .append(getWhere_like("userName||context", c_keyword))
        .append(" order by id desc ").toString();
    }
    public static String SP_LogQueryByC(String logTable, String c_startTime, String c_endTime, String c_keyword, String c_logType){
        return new StringBuilder(" select * from "+logTable+" where 1 = 1 ")
        .append(getWhere_number("logType", c_logType))
        .append(getWhere_seDate("istdate", c_startTime, c_endTime))
        .append(getWhere_like("userName||context||ipAddress", c_keyword))
        .append(" order by id desc ").toString();
    }
    public static String SP_LogDelByC(String logTable, String c_startTime, String c_endTime, String c_keyword){
        return new StringBuilder(" delete from "+logTable+" where 1 = 1 ")
        .append(getWhere_seDate("istdate", c_startTime, c_endTime))
        .append(getWhere_like("userName||context", c_keyword)).toString();
    }
    public static String SP_LogQueryByUserC(String logTable, String c_startTime, String c_endTime, String c_context, long userID){
        return new StringBuilder(" select * from "+logTable+" where 1 = 1 ")
        .append(getWhere_seDate("istdate", c_startTime, c_endTime))
        .append(getWhere_number("userID", userID+""))
        .append(getWhere_like("context", c_context))
        .append(" order by id desc ").toString();
    }
    public static String SP_LogQueryByOrganC(String logTable, String c_startTime, String c_endTime, String c_keyword, long organID){
        return new StringBuilder(" select a.* from "+logTable+" a where 1 = 1 ")
        .append(getWhere_seDate("istdate", c_startTime, c_endTime))
        .append(getWhere_like("userName||context", c_keyword))
        .append(" and exists (select b.organID from S_User b where b.userID=a.userID and b.organID="+organID+") ")
        .append(" order by id desc ").toString();
    }
    public static String SP_LogQueryByTableID = "select * from %s where tableID=%s order by istDate";
    
    public static String SP_I_AreaQuery = "select * from I_Area order by areaID";
    
    public static String SP_I_SystemParamClassQuery = "select distinct paramClass, className from I_SystemParam order by className";
    public static String SP_I_SystemParamClassQueryShow = "select distinct paramClass, className from I_SystemParam where isShow=1 order by className";
    public static String SP_I_SystemParamQueryByParent = "select * from I_SystemParam where parentClass=%s order by paramClass, value";
    public static String SP_I_SystemParamQueryByState = "select * from I_SystemParam where state=%s order by paramClass, value";
    public static String SP_I_SystemParamQueryByC(String parentClass, String c_paramClass, String isShow){
        return new StringBuilder(" select * from I_SystemParam where 1 = 1 ")
        .append(getWhere_number("parentClass", parentClass))
        .append(getWhere_number("paramClass", c_paramClass))
        .append(getWhere_number("isShow", isShow))
        .append(" order by paramClass, value ").toString();
    }
    
    public static String SP_T_MessageQueryByRecver = "select * from T_Message where recverID=%s order by istdate desc";
    public static String SP_T_MessageQueryBySender = "select * from T_Message where senderID=%s and sendShowFlag=1 order by istdate desc";
    public static String SP_T_MessageUpdateReadFlag = "update T_Message set readFlag=%s where id=%s";
    public static String SP_T_MessageUpdateSendShowFlag = "update T_Message set sendShowFlag=%s where id=%s";
    public static String SP_T_MessageDel = "delete from T_Message where id in (%s)";
    public static String SP_T_MessageQueryByActionId = "select * from T_Message where ACTIONID=%s order by istdate desc";

    public static String SP_I_DataParamClassQuery = "select paramClassID, className from I_DataParamType where state=1 and typeLevel=201001 order by paramClassID";
    public static String SP_I_DataParamTypeQueryByParentOrderByName = "select * from I_DataParamType where state=1 and parentID=%s order by className";
    public static String SP_I_DataParamTypeQueryByParentOrderByID = "select * from I_DataParamType where state=1 and parentID=%s order by paramClassID";
    public static String SP_I_DataParamTypeQueryChildsByParent = "select * from I_DataParamType where state=1 and F_CheckDataTableType(parentID,%s)=1 order by paramClassID";
    public static String SP_I_DataParamTypeQueryByParent = "select * from I_DataParamType where state=%s and parentID=%s order by paramClassID";
    public static String SP_I_DataParamTypeQueryByC(String c_typeLevel, String c_parentID){
        return new StringBuilder(" select * from I_DataParamType where 1=1")
        .append(getWhere_number("typeLevel", c_typeLevel))
        .append(getWhere_number("parentID", c_parentID))
        .append(" order by paramClassID").toString();
    }
    
    public static String SP_I_DataTableTypeQuery = "select * from I_DataTableType order by tableTypeID";

    public static String SP_I_DataModuleQueryByID = "select a.*, NVL(a.colNum,0) colNum_,b.moduleID moduleID_,b.moduleName moduleName_ from I_DataModule a,S_Module b where a.moduleID(+)=b.moduleID and b.moduleID='%s'";
    public static String SP_I_DataModuleColumnQueryByC(String moduleID, String sctLevel, String extKeywordFlag, boolean sortBySeq){
        return new StringBuilder(" select * from VI_DataModuleColumn where 1=1")
        .append(getWhere_string("moduleID", moduleID))
        .append(getWhere_number("extKeywordFlag", extKeywordFlag))
        .append(getWhere_endNumber("sctLevel", sctLevel, true))
        .append(sortBySeq ? " order by seq, colid " : "order by colid ")
        .toString();
    }

    public static String SP_I_DataTableQuery = "select * from VI_DataTable order by tableID";
    public static String SP_I_DataModuleQuery = "select * from I_DataModule order by moduleID";
    public static String SP_I_DataTableQueryByID = "select * from VI_DataTable where tableID=%s";
    public static String SP_I_DataTableQueryByParentTableID = "select * from VI_DataTable where parentTableID=%s order by tableID";
    public static String SP_I_DataTableQueryByC(String c_tableTypeID, String c_keyword, String parentTableID){
        return new StringBuilder(" select t.*, F_GetParentSort('I_DataTable','tableID','parentTableID',tableID,parentTableID) sort from VI_DataTable t where 1=1 ")
        .append(HIUtil.isEmpty(c_tableTypeID)?"" : " and F_CheckDataTableType(tableTypeID, "+c_tableTypeID+")=1 ")
        .append(getWhere_number("parentTableID", parentTableID))
        .append(getWhere_like("tableName||tableMemo", c_keyword))
        .append(" order by tableTypeID, sort, tableName").toString();
    }
    public static String SP_I_DataTableColumnQueryByID = "select * from I_DataTableColumn where colid=%s order by seq";
    public static String SP_I_DataTableColumnQueryByC(String tableID){
        return SP_I_DataTableColumnQueryByC(tableID, "", "", true);
    }
    public static String SP_I_DataTableColumnQueryByC(String tableID, String sctLevel){
        return SP_I_DataTableColumnQueryByC(tableID, sctLevel, "", true);
    }
    public static String SP_I_DataTableColumnQueryByC(String tableID, String sctLevel, String extKeywordFlag, boolean sortBySeq){
        return new StringBuilder(" select * from VI_DataTableColumn where 1=1")
        .append(getWhere_number("tableID", tableID))
        .append(getWhere_number("extKeywordFlag", extKeywordFlag))
        .append(getWhere_endNumber("sctLevel", sctLevel, true))
        .append(sortBySeq ? " order by seq, colid " : "order by colid ")
        .toString();
    }

    public static String SP_I_DataTableColumnExtInfoQueryByC(String id, String colid, String tableID){
        return new StringBuilder(" select * from I_DataTableColumnExtInfo where 1=1 ")
        .append(getWhere_number("id", id))
        .append(getWhere_number("colid", colid))
        .append(getWhere_number("tableID", tableID))
        .append(" order by colid").toString();
    }

    public static String SP_I_ProcessQueryByID = "select * from I_Process where psid=%s";
    public static String SP_I_ProcessQueryByC(String organID, String processType){
        return new StringBuilder(" select * from I_Process where 1=1 ")
        .append(getWhere_number("organID", organID))
        .append(getWhere_number("processType", processType))
        .append(" order by psid")
        .toString();
    }
    public static String SP_I_ProcessDescQuery = "select * from I_ProcessDesc where psid=%s order by seq";
    public static String SP_I_ProcessDescQueryBySeq = "select * from I_ProcessDesc where psid=%s and seq=%s";
    
    public static String SP_IB_ProcessQuery = "select * from IB_Process where organID=%s order by processID desc";
    public static String SP_IB_ProcessQueryByID = "select * from IB_Process where processID=%s";
    public static String SP_IB_ProcessQueryByC(String organID, String curActor, String creator, String dealFlag){
        return new StringBuilder(" select * from IB_Process where 1=1 ")
        .append(getWhere_number("organID", organID))
        .append(getWhere_number("creator", creator))
        .append(getWhere_number("dealFlag", dealFlag))
        .append(getWhere_like("','||curActor||','", HIUtil.isEmpty(curActor)?"":","+curActor+","))
        .append(" order by uptdate desc")
        .toString();
    }
    public static String SP_IB_ProcessDetailQuery = "select * from IB_ProcessDetail where processID=%s order by id";
    
    public static String SP_B_DataQueryByFormationID(int tableID, String tableName, String dataStatus, String dataSctLevel, String formationColName, String formationIDStr){
        return new StringBuilder(" select a.*,F_GetDataAttachFileNum("+tableID+",dataid) attachFileNum")
        .append(" from ").append(tableName).append(" a where 1=1 ")
        .append(getWhere_number("dataStatus", dataStatus))
        .append(getWhere_endNumber("dataSctLevel", dataSctLevel, true))
        .append(getWhere_string(formationColName, formationIDStr))
        .toString();
    }

    public static String SP_B_DataQueryByID(int tableID, String tableName, long dataid){
        return new StringBuilder(" select a.*,F_GetDataAttachFileNum("+tableID+",dataid) attachFileNum")
        .append(" from ").append(tableName).append(" a where 1=1 ")
        .append(getWhere_number("dataid", dataid+""))
        .toString();
    }
    public static String SP_B_DataQueryByC(int tableID, String tableName, String dataStatus, String dataSctLevel){
        return SP_B_DataQueryByC(tableID, tableName, "", dataStatus, dataSctLevel, "", "", "");
    }
    public static String SP_B_DataQueryByC(int tableID, String tableName, String dataStatus, String dataSctLevel, String otherCondition){
        return SP_B_DataQueryByC(tableID, tableName, "", dataStatus, dataSctLevel, "", "", otherCondition);
    }
    public static String SP_B_DataQueryByC(int tableID, String tableName, String parentDataid, String dataStatus, String dataSctLevel, String keywordColNames, String c_keyword){
        return SP_B_DataQueryByC(tableID, tableName, parentDataid, dataStatus, dataSctLevel, keywordColNames, c_keyword, "");
    }
    public static String SP_B_DataQueryByC(int tableID, String tableName, String parentDataid, String dataStatus, String dataSctLevel, String keywordColNames, String c_keyword, String otherCondition){
    	StringBuilder s = new StringBuilder(" select a.*,F_GetDataAttachFileNum("+tableID+",dataid) attachFileNum")
        .append(" from ").append(tableName).append(" a where 1=1 ")
        .append(getWhere_number("parentDataid", parentDataid))
        .append(getWhere_number("dataStatus", dataStatus))
        .append(getWhere_endNumber("dataSctLevel", dataSctLevel, true))
        .append(otherCondition);
    	//s.append(getWhere_like(keywordColNames, c_keyword))
    	if(c_keyword.length()>0){
        	s.append(" and ( 1=1 ");
    		String[] kk = c_keyword.split(" ");
    		String[] ww;
    		int i = 0;
    		for(String k: kk){
    			if(k.length()>0){
    				if(i==0) s.append(" ");
    				else s.append(" or 1=1 ");
    			    ww = k.split(",");
    			    for(String w: ww){
    			    	if(w.length()>0){
    			    		s.append(getWhere_like(keywordColNames, w));
    			    	}
    			    }
    			    i++;
    			}
    			
    		}
    		s.append(")");
    	}
        s.append(" order by dataid");
        return s.toString();
    }
    public static String SP_B_DataQueryByC(String moduleSql, String dataStatus, String dataSctLevel, String keywordColNames, String c_keyword, String otherCondition){    	
    	String[] ss = moduleSql.split("@@@");
    	StringBuilder s = new StringBuilder(ss[0])
        //.append(getWhere_number("dataStatus", dataStatus))
        //.append(getWhere_endNumber("dataSctLevel", dataSctLevel, true))
        .append(otherCondition);
    	//s.append(getWhere_like(keywordColNames, c_keyword))
    	if(c_keyword.length()>0){
        	s.append(" and ( 1=1 ");
    		String[] kk = c_keyword.split(" ");
    		String[] ww;
    		int i = 0;
    		for(String k: kk){
    			if(k.length()>0){
    				if(i==0) s.append(" ");
    				else s.append(" or 1=1 ");
    			    ww = k.split(",");
    			    for(String w: ww){
    			    	if(w.length()>0){
    			    		s.append(getWhere_like(keywordColNames, w));
    			    	}
    			    }
    			    i++;
    			}
    			
    		}
    		s.append(")");
    	}
    	if(ss.length>1) s.append(" group by ").append(ss[1]);
        s.append(" order by dataid");
        return s.toString();
    }
    public static String SP_B_DataHisQueryByC(int tableID, String tableName, String dataid, String dataStatus, String dataSctLevel){
        return new StringBuilder(" select a.*,0 attachFileNum")
        .append("  from Z").append(tableName).append(" a where 1=1 ")
        .append(getWhere_number("dataid", dataid))
        .append(getWhere_number("dataStatus", dataStatus))
        .append(getWhere_endNumber("dataSctLevel", dataSctLevel, true))
        .append(" order by iid desc").toString();
    }
    public static String SP_B_DataAttachFileQueryByC(String tableID, String dataid){
        return new StringBuilder(" select * from B_DataAttachFile where 1=1 ")
        .append(getWhere_number("tableID", tableID))
        .append(getWhere_number("dataid", dataid))
        .append(" order by id").toString();
    }

    public static String SP_IB_FormationQueryByC(String c_keyword){
        return new StringBuilder(" select t.*, F_GetParentSort('IB_Formation','formationID','parentID',formationID,parentID) sort ")
        .append(" from IB_Formation t where 1=1 ")
        .append(getWhere_like("formationID||formationName", c_keyword))
        .append(" order by sort,formationID").toString();
    }    
    public static String SP_B_EmpFormationQueryChildsByParent = 
        "select * from VIS_EmployeeFormation where F_CheckArmyFormationChildID(parentID,%s)=1 order by formationID";
    /******************************************************************************************/
    public String get(String sp){
        try {
            return (String)this.getClass().getField(sp).get(new SQL_0());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWhere_number(String colName, String value){
        if(!HIUtil.isEmpty(value)){
        	if(value.startsWith("!")) return getWhere_not(colName, value.substring(1));
            return new StringBuilder(" and ").append(colName).append("=").append(value).toString();
        }
        return "";
    }
    public static String getWhere_number(String colName, String value, String cal){
        if(!HIUtil.isEmpty(value)){
            return new StringBuilder(" and ").append(colName).append(cal).append(value).toString();
        }
        return "";
    }
    public static String getWhere_not(String colName, String value){
        if(!HIUtil.isEmpty(value)){
            return new StringBuilder(" and ").append(colName).append("<>'").append(value.replaceAll("'", "''")).append("'").toString();
        }
        return "";
    }
    public static String getWhere_notlike(String colName, String value){
        if(!HIUtil.isEmpty(value)){
			return new StringBuilder(" and (").append(colName).append(" is null or ").append(colName).append(" not like '%").append(value.replaceAll("'", "''")).append("%')").toString();
        }
        return "";
    }
    public static String getWhere_in(String colName, String value){
        if(!HIUtil.isEmpty(value)){
            return new StringBuilder(" and ").append(colName).append(" in (").append(value).append(")").toString();
        }
        return "";
    }
	public static String getWhere_string(String colName, String value){
		if(!HIUtil.isEmpty(value)){
			return new StringBuilder(" and ").append(colName).append("='").append(value.replaceAll("'", "''")).append("'").toString();
		}
		return "";
	}
    public static String getWhere_seDate(String colName, String s, String e){
        if(!HIUtil.isEmpty(s) && !HIUtil.isEmpty(e)){
            String tmp1 = s;
            if(s.indexOf("年")>0) tmp1 = DateUtil.formatDate(DateUtil.parseDate(s+" 000000", "yyyy年MM月dd日 HHmmss"), "yyyy-MM-dd HH:mm:ss");
            String tmp2 = e;
            if(e.indexOf("年")>0) tmp2 = DateUtil.formatDate(DateUtil.parseDate(e+" 235959", "yyyy年MM月dd日 HHmmss"), "yyyy-MM-dd HH:mm:ss");

            if(HIUtil.isOracle()){
                return new StringBuilder(" and ").append(colName).append(" between to_date('").append(tmp1).append("','yyyy-mm-dd hh24:mi:ss') and to_date('").append(tmp2).append("','yyyy-mm-dd hh24:mi:ss')").toString();
            }else{
                return new StringBuilder(" and ").append(colName).append(" between '").append(tmp1).append("' and '").append(tmp2).append("'").toString();
            }
        }
        if(!HIUtil.isEmpty(s)) return getWhere_startDate(colName, s, true);
        if(!HIUtil.isEmpty(e)) return getWhere_endDate(colName, e, true);
        return "";
    }
	
    public static String getWhere_startDate(String colName, String value, boolean equalFlag){
        if(!HIUtil.isEmpty(value)){
            String tmp = value;
            if(value.indexOf("年")>0) tmp = DateUtil.formatDate(DateUtil.parseDate(value+" 000000", "yyyy年MM月dd日 HHmmss"), "yyyy-MM-dd HH:mm:ss");

            if(HIUtil.isOracle()){
                return new StringBuilder(" and ").append(colName).append(equalFlag?">=":">").append("to_date('").append(tmp).append("','yyyy-mm-dd hh24:mi:ss')").toString();
            }else{
                return new StringBuilder(" and ").append(colName).append(equalFlag?">=":">").append("'").append(tmp).append("'").toString();
            }
        }
        return "";
    }
    public static String getWhere_endDate(String colName, String value, boolean equalFlag){
        if(!HIUtil.isEmpty(value)){
            String tmp = value;
            if(value.indexOf("年")>0) tmp = DateUtil.formatDate(DateUtil.parseDate(value+" 235959", "yyyy年MM月dd日 HHmmss"), "yyyy-MM-dd HH:mm:ss");

            if(HIUtil.isOracle()){
                return new StringBuilder(" and ").append(colName).append(equalFlag?"<=":"<").append("to_date('").append(tmp).append("','yyyy-mm-dd hh24:mi:ss')").toString();
            }else{
                return new StringBuilder(" and ").append(colName).append(equalFlag?"<=":"<").append("'").append(tmp).append("'").toString();
            }
        }
        return "";
    }
    public static String getWhere_seNumber(String colName, String s, String e){
        if(!HIUtil.isEmpty(s) && !HIUtil.isEmpty(e)){
            return new StringBuilder(" and ").append(colName).append(" between ").append(s.replaceAll("'", "''")).append(" and ").append(e.replaceAll("'", "''")).toString();
        }
        if(!HIUtil.isEmpty(s)) return getWhere_startNumber(colName, s, true);
        if(!HIUtil.isEmpty(e)) return getWhere_endNumber(colName, e, true);
        return "";
    }
    public static String getWhere_startNumber(String colName, String value, boolean equalFlag){
        if(!HIUtil.isEmpty(value)){
            return new StringBuilder(" and ").append(colName).append(equalFlag?">=":">").append(value.replaceAll("'", "''")).toString();
        }
        return "";
    }
    public static String getWhere_endNumber(String colName, String value, boolean equalFlag){
        if(!HIUtil.isEmpty(value)){
            return new StringBuilder(" and ").append(colName).append(equalFlag?"<=":"<").append(value.replaceAll("'", "''")).toString();
        }
        return "";
    }
	public static String getWhere_like(String colName, String value){
		if(!HIUtil.isEmpty(value)){
			return new StringBuilder(" and UPPER(").append(colName).append(") like '%").append(value.toUpperCase().replaceAll("'", "''")).append("%'").toString();
		}
		return "";
	}
	
}