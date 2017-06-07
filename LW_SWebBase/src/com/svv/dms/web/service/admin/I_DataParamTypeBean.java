package com.svv.dms.web.service.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.common.ComBeanI_DataParamType;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanState;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.I_DataParamType;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.TColumn;

public class I_DataParamTypeBean extends AbstractBean {

    public String I_DataParamType(){
        return this.exeByCmd("");
    }
    public String XMLSelectDataParamType(){
        return this.exeByCmd("ForXMLSelect");
    }
    public String XMLDataParamTypeTree(){
        return this.exeByCmd("ForXMLTree");
    }
    public String getTreeDataForXMLTree(){
    	int allFlag = this.getParameter("allFlag", 0);
		String s = getTreeData(getParameter("parentID", -1), getParameter("sortByName", 0)==1, ParamClass.VALUE_LEVEL_ZERO, allFlag==1);		
    	this.setMessage(true, s.toString());
    	//System.out.println(s.toString());
    	return BConstants.MESSAGE;
    }
    private String getTreeData(int parentID, boolean sortByName, int level, boolean allFlag){
        StringBuilder s = new StringBuilder("");
        StringBuilder tmp = new StringBuilder("");
        if(allFlag || level <= ParamClass.VALUE_LEVEL_ONE){
	    	List<I_DataParamType> list = ComBeanI_DataParamType.getList(parentID, sortByName);
	        //System.out.println("[getTreeData getTreeData getTreeData parentID="+parentID+"] "+list.size());
	        if(list!=null && list.size()>0){
	            for(I_DataParamType o: list){
	            	boolean isParent = o.getChildNum()>0 && (allFlag || o.getTypeLevel()==ParamClass.VALUE_LEVEL_ZERO);
	            	tmp.append("{\"rowid\":\"").append(isParent?0:1).append("_").append(o.getParamClassID()).append("_").append(o.getParamValue()).append("\",\"code\":\"").append(o.getParamClassID()).append("\",\"text\":\"").append(o.getClassName()).append("\"");
	            	tmp.append(",\"className\":\"").append(o.getClassName()).append("\"");
	            	tmp.append(",\"classNameEn\":\"").append(o.getClassNameEn()).append("\"");
	            	tmp.append(",\"classNameEn2\":\"").append(o.getClassNameEn2()).append("\"");
	            	tmp.append(",\"classNameEn3\":\"").append(o.getClassNameEn3()).append("\"");
	            	tmp.append(",\"classNameEn4\":\"").append(o.getClassNameEn4()).append("\"");
	            	tmp.append(",\"classNameEn5\":\"").append(o.getClassNameEn5()).append("\"");
	            	tmp.append(",\"typeLevel\":\"").append(ComBeanI_SystemParam.getText(ParamClass.CLASS_LEVEL, o.getTypeLevel())).append("\"");
	            	tmp.append(",\"childNum\":").append(o.getChildNum()).append("");
	            	tmp.append(",\"memo\":\"").append(o.getMemo()).append("\"");
	            	
	                if(isParent) tmp.append(",\"treeState\":\"closed\"");
	                else tmp.append(",\"iconCls\":\"icon-ok\"");
	                /////////////////////if(o.getChildNum()>0) tmp.append(getTreeData(o.getParamClassID(), o.getTypeLevel()+1, false));
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
        return s.toString();
    }
  
    @SuppressWarnings("unchecked")
	public String query(){
        this.addOptionList(BConstants.option_edit_string);
        this.addOptionList(BConstants.option_del_string);
        setResult_List(getList(I_DataParamType.class, dbQuery(SQL.SP_I_DataParamTypeQueryByC("", c_class))));
        logger(ComBeanLogType.TYPE_QUERY, "查询数据应用字典");
        return BConstants.LIST;
    }

    public String add(){
        Object[] params = new Object[]{"add", dtt.getParamClassID(), dtt.getClassName(), dtt.getClassNameEn(), dtt.getClassNameEn2(), dtt.getClassNameEn3(), dtt.getClassNameEn4(), dtt.getClassNameEn5(), dtt.getTypeLevel(), dtt.getParentID(), dtt.getMemo(), dtt.getState()};
        if(dbExe_p("SP_I_DataParamTypeManager", params)){
        	logger(ComBeanLogType.TYPE_ADD, "添加数据应用字典", params);
        	reflesh();
        }
        return BConstants.SUCCESS;
    }
    
    public String edit(){
        Object[] params = new Object[]{"edit", dtt.getParamClassID(), dtt.getClassName(), dtt.getClassNameEn(), dtt.getClassNameEn2(), dtt.getClassNameEn3(), dtt.getClassNameEn4(), dtt.getClassNameEn5(), dtt.getTypeLevel(), dtt.getParentID(), dtt.getMemo(), dtt.getState()};
        if(dbExe_p("SP_I_DataParamTypeManager", params)){
        	logger(ComBeanLogType.TYPE_EDIT, "编辑数据应用字典", params);
            reflesh();
        }
        return BConstants.SUCCESS;
    }
    
    public String del(){
        Object[] params = new Object[]{"del", dtt.getParamClassID(), dtt.getClassName()};
        if(dbExe_p("SP_I_DataParamTypeManager", params)){
        	logger(ComBeanLogType.TYPE_DEL, "删除数据应用字典", params);
            reflesh();
        }
        return BConstants.MESSAGE;
    }

    public String reflesh(){
        ComBeanI_DataParamType.load();
        return BConstants.MESSAGE;
    }

    private void setResult_List(List<I_DataParamType> list) {
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("ID", "tableTypeID", TColumn.ALIGN_LEFT),
                                     new TColumn(I_DataParamType.className_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_DataParamType.classNameEn_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_DataParamType.classNameEn2_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_DataParamType.memo_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn("级别", "typeLevel", TColumn.ALIGN_LEFT),
                                     new TColumn("状态", null, TColumn.ALIGN_LEFT),
                                     new TColumn("操作", null, TColumn.ALIGN_LEFT)};
            for(I_DataParamType o: list){
                objs[i++] = new Object[]{"doFocus('"+o.getParamClassID()+"','"+o.getClassName()+"','"+o.getClassNameEn()+"','"+o.getTypeLevel()+"','"+o.getParentID()+"','"+o.getMemo()+"','"+o.getState()+"');",
                                         o.getParamClassID(),
                                         o.getClassName(),
                                         o.getClassNameEn(),
                                         o.getClassNameEn2(),
                                         o.getMemo(),
                                         ComBeanI_SystemParam.getText(ParamClass.CLASS_LEVEL, o.getTypeLevel()),
                                         ComBeanState.getText(o.getState()),
                                         this.getOptionHtmlString(o.getParamClassID(), o.getClassName(), "")};
            }
            
        }
        this.setResultList(objs);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -8422475366273959921L;

    private String c_class = "";
    
	private I_DataParamType dtt = new I_DataParamType();
    
    public List<LabelValueBean> getClassList(){
        List<Object> result = dbQuery("SP_I_DataParamClassQuery", null);
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(new LabelValueBean("", ""));
        if(result!=null && result.size()>0){
            for(Object row: result){
                list.add(new LabelValueBean(DBUtil.getDBString(row, "className"), DBUtil.getDBString(row, "paramClassID")));
            }
        }
        return Collections.unmodifiableList(list);
    }
	
    public List<LabelValueBean> getStateList() {
        return ComBeanState.getList();
    }
    
	public List<LabelValueBean> getLevelList(){
		return ComBeanI_SystemParam.getList(ParamClass.CLASS_LEVEL, false, "");
    }

	public I_DataParamType getDtt() {
		return dtt;
	}

	public void setDtt(I_DataParamType dtt) {
		this.dtt = dtt;
	}
    public String getC_class() {
        return c_class;
    }
    public void setC_class(String c_class) {
        this.c_class = c_class;
    }
	
}
