package com.svv.dms.web.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanI_DataModule;
import com.svv.dms.web.common.ComBeanI_DataTable;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanState;
import com.svv.dms.web.common.ComError;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.entity.I_DataModule;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.TColumn;

public class I_DataModuleBean extends AbstractBean {

    public String I_DataModule(){
        return this.exeByCmd("");
    }
    
    public String editPage(){
    	System.out.println("I_DataModuleBean.getUserSession()==null 1? " + (this.getUserSession()==null?true:false));
        List<Object> list = dbQuery("SP_I_DataModuleQueryByID", new Object[]{ ao.getModuleID() });
    	System.out.println("I_DataModuleBean.getUserSession()==null 2? " + (this.getUserSession()==null?true:false));
        if (list == null || !isSuperAdmin()){
            this.setMessage(false, ComError.err_000001);
            return BConstants.MESSAGE_PAGE;
        }else{
        	Object o = list.get(0);
            ao = new I_DataModule();
            ao.setModuleID(DBUtil.getDBString(o, "moduleID_"));
            ao.setModuleName(DBUtil.getDBString(o, "moduleName_"));
            ao.setViewName(DBUtil.getDBString(o, "viewName")); //视图名称
            if(HIUtil.isEmpty(ao.getViewName())) ao.setViewName(ao.getModuleName());
            ao.setViewSql(DBUtil.getDBString(o, "viewSql"));
            ao.setTableIDs(DBUtil.getDBString(o, "tableIDs"));
            if(ao.getTableIDs().length()>0){
            	String[] ss = ao.getTableIDs().split(",");
            	String s1;
                Pattern p;
                Matcher m;
            	for(String sss: ss){
            		if(sss.indexOf(".")<0){
            			s1 = sss;
            		}else{
	            		s1 = sss.substring(sss.lastIndexOf(".")+1);
            		}
            		p = Pattern.compile("(.+?)\\[(.+?)\\]");
    				m = p.matcher("a0"+s1);
    				while(m.find()){
                		tables_str += "," + sss + "|" + s1 + "|" + m.group(2) + "|"+ComBeanI_DataTable.getMemo(Integer.parseInt(m.group(2)));
    				}
            	}
            	tables_str = tables_str.substring(1);
            }
            ao.setColNum(DBUtil.getDBInt(o, "colNum_"));
            ao.setOtherCondition(DBUtil.getDBString(o, "otherCondition"));
            ao.setOtherButtons(DBUtil.getDBString(o, "otherButtons"));
            if(DBUtil.getDBString(o, "state").length()>0) ao.setState(DBUtil.getDBInt(o, "state"));
        }
    	
    	return "editPage";
    }
    
    public String edit(){
    	if(isSuperAdmin()){
	        Object[] params = getSqlParams("add");
	        if(params!=null) if(dbExe("SP_I_DataModuleManager", params)){
	        	logger(ComBeanLogType.TYPE_ADD, "配置数据模块", params);
	            ComBeanI_DataModule.load();
	        }
    	}
        return BConstants.EDITPAGE;
    }
    public String del(){
    	if(isSuperAdmin()){
	        Object[] params = new Object[]{"del", ao.getModuleID(), ao.getModuleName(), "", "", "", "", -1, -1, -1};
	        if(dbExe("SP_I_DataModuleManager", params)){
	        	logger(ComBeanLogType.TYPE_DEL, "删除数据模块", params);
	            ComBeanI_DataModule.load();
	        }
    	}
        return BConstants.MESSAGE;
    }
    public String delCol(){
        Object[] params = new Object[]{"delCol", dc.getId(), dc.getColMemo(), "", "", "", "", -1, -1, -1};
        if(dbExe("SP_I_DataModuleManager", params)){
            logger(ComBeanLogType.TYPE_DEL, "删除数据模块列", params);
            ComBeanI_DataModule.load();
        }
        return BConstants.MESSAGE;
    }
    
    public String updateColSeq(){
        Object[] params = new Object[]{"updateColSeq", dc.getId(), dc.getSeq(), dc.getColMemo(), "", "", "", -1, -1, -1};
        if(dbExe("SP_I_DataModuleManager", params)){
            logger(ComBeanLogType.TYPE_EDIT, "更改数据模块排序", params);
            ComBeanI_DataModule.load();
        }
        return BConstants.MESSAGE;
    }
    
    private Object[] getSqlParams(String type){
        if(colid==null || colid.length==0){
        	this.setMessage(false, "您没有输入参数，请检查！");
        	return null;
        }
    	
        ao.setViewSql(toViewSql());
    	Object[] tmpParams = new Object[]{ao.getModuleName(), ao.getViewName(), ao.getViewSql(), ao.getTableIDs(), ao.getOtherCondition(), ao.getOtherButtons(), ao.getState()};
    	Object[] tmpStrs = new Object[]{
    			StringUtils.join(this.id, Constants.SPLITER_SP),
    			StringUtils.join(this.tableid, Constants.SPLITER_SP),
    			StringUtils.join(this.colid, Constants.SPLITER_SP),
    			StringUtils.join(this.colstr, Constants.SPLITER_SP),
    			StringUtils.join(this.colMemo, Constants.SPLITER_SP),
    			StringUtils.join(this.colName, Constants.SPLITER_SP),
    			StringUtils.join(this.colName_as, Constants.SPLITER_SP),
    			StringUtils.join(this.sctLevel, Constants.SPLITER_SP),
                StringUtils.join(this.seq, Constants.SPLITER_SP),
                StringUtils.join(this.groupFunc, Constants.SPLITER_SP),
                StringUtils.join(this.listShowFlag, Constants.SPLITER_SP),
                StringUtils.join(this.showWidth, Constants.SPLITER_SP),
                StringUtils.join(this.formShowFlag, Constants.SPLITER_SP),
                StringUtils.join(this.extKeywordFlag, Constants.SPLITER_SP),
                StringUtils.join(this.extQueryByFlag, Constants.SPLITER_SP),
    			StringUtils.join(this.state, Constants.SPLITER_SP)
    			};
    	
        Object[] params = new Object[]{
        		type,
        		ao.getModuleID(),
        		HIUtil.toSPParamSplitString(tmpParams),
        		HIUtil.toSPParamSplitString(tmpStrs),
        		Constants.SPLITER,
        		Constants.SPLITER_SP,
        		tmpParams.length,
        		tmpStrs.length,
        		colid.length
        		};
        return params;
    }
    
    private String toViewSql(){
    	StringBuilder s = new StringBuilder();
        
        String mainTableName = null;
        StringBuilder select = new StringBuilder();
        StringBuilder select1 = new StringBuilder();
        StringBuilder from = new StringBuilder();
        StringBuilder where = new StringBuilder();
        StringBuilder group = new StringBuilder();
        String relationID, mark, mytableID, myas;
        String colname;
        Pattern p;
        Matcher m;
        HashMap<String, String> map_tableas = new HashMap<String, String>();
        HashMap<String, String> map_tableid = new HashMap<String, String>();

    	//[1],[1].R[3],[1].R[3].R[2]
        String[] ss = ao.getTableIDs().split(",");
        for(String sss:ss){
        	relationID = ""; mark = ""; mytableID = ""; myas = "";
        	
        	if(sss.indexOf(".")<0){
        		p = Pattern.compile("\\[(.+?)\\]");
				m = p.matcher(sss);
				while(m.find()){
	        		mytableID = m.group(1);
	        		myas = " t_".concat(mytableID);
					mainTableName = myas;
	        		from.append(" from ").append(ComBeanI_DataTable.get(Integer.parseInt(mytableID)).getTableName()).append(myas);
	        		where.append(" where 1=1").append(ao.getOtherCondition());
				}
        		
        	}else if(sss.split("\\.").length>=2){
        		String s0 = sss.substring(0, sss.lastIndexOf("."));
        		String s1 = sss.substring(sss.lastIndexOf(".")+1);
        		p = Pattern.compile("(.+?)\\[(.+?)\\]");
				m = p.matcher(s1);
				while(m.find()){
					relationID = map_tableid.get(s0);
					mark = m.group(1);
					mytableID = m.group(2);
	        		myas = " t".concat(relationID).concat("_").concat(mytableID);
	        		from.append(", ").append(ComBeanI_DataTable.get(Integer.parseInt(mytableID)).getTableName()).append(myas);
	        		switch(mark){
	        		case "P": //父表
		        		where.append(" and ").append(map_tableas.get(s0)).append(".parentDataid=").append(myas).append(".dataid");
		        		break;
	        		case "C": //子表
		        		where.append(" and ").append(map_tableas.get(s0)).append(".dataid=").append(myas).append(".parentDataid");
		        		break;
	        		case "B": //相同父表
		        		where.append(" and ").append(map_tableas.get(s0)).append(".parentDataid=").append(myas).append(".parentDataid");
		        		break;
	        		case "R": //关联表
	        			colname = this.getSqlValue(String.format("select colname cc from I_DataTableColumn a, I_DataTableColumnExtInfo b where a.colid=b.colid and a.tableid=%s and b.extValueScopeTypeID=%s and b.extValueScopeTypeParam=%s", new Object[]{relationID, ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE, mytableID}), "cc");
		        		where.append(" and ").append(map_tableas.get(s0)).append(".").append(colname).append("=").append(myas).append(".dataid");
		        		break;
	        		case "T": //被关联表
	        			colname = this.getSqlValue(String.format("select colname cc from I_DataTableColumn a, I_DataTableColumnExtInfo b where a.colid=b.colid and a.tableid=%s and b.extValueScopeTypeID=%s and b.extValueScopeTypeParam=%s", new Object[]{mytableID, ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE, relationID}), "cc");
		        		where.append(" and ").append(map_tableas.get(s0)).append(".dataid=").append(myas).append(".").append(colname);
		        		break;
	        		case "F": //间接关联表
	        			List<Object> list = dbQuery(String.format("select a.colname c1,c.colname c2 from I_DataTableColumn a, I_DataTableColumnExtInfo b, I_DataTableColumn c, I_DataTableColumnExtInfo d where a.colid=b.colid c.colid=d.colid and a.tableid=%s and c.tableid=%s and b.extValueScopeTypeID=%s and b.extValueScopeTypeID=d.extValueScopeTypeID and b.extValueScopeTypeParam=d.extValueScopeTypeParam", new Object[]{mytableID, relationID, ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE}));
		        		where.append(" and ").append(map_tableas.get(s0)).append(".").append(DBUtil.getDBString(list.get(0), "c2")).append("=").append(myas).append(".").append(DBUtil.getDBString(list.get(0), "c1"));
		        		break;
		        	}
				}
        	}
        	map_tableas.put(sss, myas);
        	map_tableid.put(sss, mytableID);
        }
        
        int i_groupFunc;
        boolean hasGroupFunc = false;
        for(int i=0; i<colName.length; i++){
        	i_groupFunc = Integer.parseInt(groupFunc[i]);
        	if(i_groupFunc==ParamClass.VALUE_GROUPFUNC_GROUPBY){
        		select1.append(",").append(colName_as[i]).append(" ").append(colName[i]);
        	    group.append(",").append(colName_as[i]);
        	}else{
        		hasGroupFunc = true;
        		select1.append(",").append(ComBeanI_SystemParam.getText(ParamClass.CLASS_GROUPFUNC, i_groupFunc)).append("(").append(colName_as[i]).append(") ").append(colName[i]);
        	}
        }
        select1.append(", 0 attachFileNum");
        if(hasGroupFunc){
        	select.append("select max(").append(mainTableName).append(".dataid) dataid, max(").append(mainTableName).append(".dataStatus) dataStatus, max(").append(mainTableName).append(".dataSctLevel) dataSctLevel, max(").append(mainTableName).append(".istdate) istdate, max(").append(mainTableName).append(".uptdate) uptdate");
        }else{
        	select.append("select ").append(mainTableName).append(".dataid, ").append(mainTableName).append(".dataStatus,").append(mainTableName).append(".dataSctLevel,").append(mainTableName).append(".istdate,").append(mainTableName).append(".uptdate");
        }
        
        s.append(select).append(select1).append(from).append(where);
        if(hasGroupFunc && group.length()>0) s.append("@@@").append(group.substring(1));
        System.out.println(s.toString());
    	return s.toString();
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
                                 new TColumn(I_DataTableColumn.colstr_desc, TColumn.ALIGN_LEFT, 280),
                                 new TColumn(I_DataTableColumn.colMemo_desc, TColumn.ALIGN_LEFT, 280),
                                 new TColumn(I_DataTableColumn.colName_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.groupFunc_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.listShowFlag_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.showWidth_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.formShowFlag_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.extKeywordFlag_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.extQueryByFlag_desc, null, TColumn.ALIGN_LEFT),
                                 new TColumn(I_DataTableColumn.sctLevel_desc, null, TColumn.ALIGN_LEFT)};
        for(int k=0; k<rowNum; k++){
        	I_DataTableColumn o = collist!=null && k<collist.size() ? collist.get(k) : new I_DataTableColumn();
        	if(o.getSeq()<k+1) o.setSeq(k+1);
            objs[i++] = new Object[]{"doFocus("+(k+1)+")",
            		                 "<b>B</b><input type=text name=\"seq\" id=\"seq_"+(k+1)+"\" value=\""+o.getSeq()+"\" class=\"ipt_read\" maxlength=3 size=2 onblur=\"intInput(this);\" onafterpaste=\"intInput(this)\">" + "<input type=hidden name=\"id\" id=\"id_"+(k+1)+"\" value=\""+o.getId()+"\"><input type=hidden name=\"tableid\" id=\"tableid_"+(k+1)+"\" value=\""+o.getTableID()+"\"><input type=hidden name=\"colid\" id=\"colid_"+(k+1)+"\" value=\""+o.getColid()+"\"><input type=hidden name=\"state\" id=\"state_"+(k+1)+"\" value=\""+o.getState()+"\">",
                                     "<input type=text name=\"colstr\" id=\"colstr_"+(k+1)+"\" value=\""+o.getColstr()+"\" class=\"ReadOnly\" readOnly=true maxlength=100 size=40>",
                                     "<input type=text name=\"colMemo\" id=\"colMemo_"+(k+1)+"\" value=\""+o.getColMemo()+"\" class=\"tableInput\" maxlength=100 size=40>",
                                     "<input type=text name=\"colName_as\" id=\"colName_as_"+(k+1)+"\" value=\""+o.getColName_as()+"\" class=\"ReadOnly\" readOnly=true maxlength=50><input type=hidden name=\"colName\" id=\"colName_"+(k+1)+"\">",
                                     "<span id=\"groupFunc_"+(k+1)+"\"><select class=sel_text name=\"groupFunc\" value=\""+o.getGroupFunc()+"\"></select><input type=hidden name=\"groupFunc_\" value=\""+o.getGroupFunc()+"\"></span>",
                                     "<input type=hidden name=listShowFlag id=\"listShowFlag_"+(k+1)+"\" value=\""+HIUtil.isEmpty(o.getListShowFlag(),"1")+"\"><input type=checkbox class=checkbox "+(HIUtil.isEmpty(o.getListShowFlag(),"1").equals("1") ? "checked" : "")+" onblur=\"d('listShowFlag_"+(k+1)+"').value=this.checked?1:0\">",
                                     "<input type=text name=\"showWidth\" id=\"showWidth_"+(k+1)+"\" value=\""+o.getShowWidth()+"\" class=\"tableInput\" maxlength=6 size=6 onblur=\"intInput(this);\" onafterpaste=\"intInput(this)\">",
                                     "<input type=hidden name=formShowFlag id=\"formShowFlag_"+(k+1)+"\" value=\""+HIUtil.isEmpty(o.getFormShowFlag(),"1")+"\"><input type=checkbox class=checkbox "+(HIUtil.isEmpty(o.getFormShowFlag(),"1").equals("1") ? "checked" : "")+" onblur=\"d('formShowFlag_"+(k+1)+"').value=this.checked?1:0\">",
                                     "<input type=hidden name=extKeywordFlag id=\"extKeywordFlag_"+(k+1)+"\" value=\""+o.getExtKeywordFlag()+"\"><input type=checkbox class=checkbox "+(o.getExtKeywordFlag()==1 ? "checked" : "")+" onblur=\"d('extKeywordFlag_"+(k+1)+"').value=this.checked?1:0\">",
                                     "<input type=hidden name=extQueryByFlag id=\"extQueryByFlag_"+(k+1)+"\" value=\""+o.getExtQueryByFlag()+"\"><input type=checkbox class=checkbox "+(o.getExtQueryByFlag()==1 ? "checked" : "")+" onblur=\"d('extQueryByFlag_"+(k+1)+"').value=this.checked?1:0\">",
                                     "<span id=\"sctLevel_"+(k+1)+"\"><select class=sel_text name=\"sctLevel\" value=\""+o.getSctLevel()+"\"></select><input type=hidden name=\"sctLevel_\" value=\""+o.getSctLevel()+"\"></span>",};
        }
        
        this.setResultList(objs);        
    	return BConstants.LIST;
    }
    
  
    /**
     * 
     */
    private static final long serialVersionUID = -8422475366273959921L;

    protected String tables_str = "";

    protected I_DataModule ao = new I_DataModule();
    protected I_DataTableColumn dc = new I_DataTableColumn();

    protected String parentTableMemo = "";
    protected String parentTableTypeName = "";

    protected int rowNum = 15;
    protected String[] id = null;
    protected String[] tableid = null;
    protected String[] colid = null;
    protected String[] colName = null;
    protected String[] colName_as = null;
    protected String[] colMemo = null;
    protected String[] colstr = null;
    protected String[] sctLevel = null;
    protected String[] seq = null;
    protected String[] groupFunc = null;
    protected String[] listShowFlag = null;
    protected String[] showWidth = null;
    protected String[] formShowFlag = null;
    protected String[] extKeywordFlag = null;
    protected String[] extQueryByFlag = null;
    protected String[] state = null;

    public List<LabelValueBean> getSctLevelList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_SCT_LEVEL, false, "");
    }
    
    public List<LabelValueBean> getDataTypeList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_DATA_TYPE, false, "");
    }
    public List<LabelValueBean> getStateList() {
        return ComBeanState.getList();
    }

    public I_DataModule getAo() {
		return ao;
	}

	public void setAo(I_DataModule ao) {
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

	public String[] getColMemo() {
		return colMemo;
	}

	public void setColMemo(String[] colMemo) {
		this.colMemo = colMemo;
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

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public String[] getTableid() {
		return tableid;
	}

	public void setTableid(String[] tableid) {
		this.tableid = tableid;
	}

	public String[] getColstr() {
		return colstr;
	}

	public void setColstr(String[] colstr) {
		this.colstr = colstr;
	}

	public String[] getExtKeywordFlag() {
		return extKeywordFlag;
	}

	public void setExtKeywordFlag(String[] extKeywordFlag) {
		this.extKeywordFlag = extKeywordFlag;
	}

	public String[] getExtQueryByFlag() {
		return extQueryByFlag;
	}

	public void setExtQueryByFlag(String[] extQueryByFlag) {
		this.extQueryByFlag = extQueryByFlag;
	}

	public I_DataTableColumn getDc() {
		return dc;
	}

	public void setDc(I_DataTableColumn dc) {
		this.dc = dc;
	}

	public String getTables_str() {
		return tables_str;
	}

	public void setTables_str(String tables_str) {
		this.tables_str = tables_str;
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

    public String[] getColName() {
		return colName;
	}

	public void setColName(String[] colName) {
		this.colName = colName;
	}

	public String[] getColName_as() {
		return colName_as;
	}

	public void setColName_as(String[] colName_as) {
		this.colName_as = colName_as;
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

	public String[] getGroupFunc() {
		return groupFunc;
	}

	public void setGroupFunc(String[] groupFunc) {
		this.groupFunc = groupFunc;
	}
    
}
