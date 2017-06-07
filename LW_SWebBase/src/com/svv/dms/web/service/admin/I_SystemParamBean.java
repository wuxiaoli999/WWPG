package com.svv.dms.web.service.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanState;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.I_SystemParam;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.TColumn;

public class I_SystemParamBean extends AbstractBean {

    public String I_SystemParam(){
        return this.exeByCmd("");
    }    
    
    @SuppressWarnings("unchecked")
    public String query(){
        this.addOptionList(BConstants.option_edit_string);
        this.addOptionList(BConstants.option_copy_string);
        this.addOptionList(BConstants.option_del_string);
        
        this.c_class = this.getParameter("c_class");
        setResult_List(getList(I_SystemParam.class, dbQuery(SQL.SP_I_SystemParamQueryByC("", c_class, ""))));
        logger(ComBeanLogType.TYPE_QUERY, "查询类别参数");
        return BConstants.LIST;
    }

    /*
    public String query(){
        this.c_class = this.getParameter("c_class");
        logger(ComBeanLogType.TYPE_QUERY, "查询类别参数");
        return this.getListHead("类别参数", SQL.SP_I_SystemParamQueryByC("", c_class, getUserSession().getRoleID()==1?"":"1"));
    }
    protected List<I_DataTableColumn> getColumnList(String pageTab){
    	List<I_DataTableColumn> collist = new ArrayList<I_DataTableColumn>();
    	collist.add(new I_DataTableColumn("id", I_SystemParam.id_desc, I_DataTableColumn.LISTKB_SHOW, 0, true));
    	collist.add(new I_DataTableColumn("paramClass", I_SystemParam.paramClass_desc, I_DataTableColumn.LISTKB_SHOW, 1));
    	collist.add(new I_DataTableColumn("className", I_SystemParam.className_desc, I_DataTableColumn.LISTKB_SHOW, 1, 20));
    	collist.add(new I_DataTableColumn("value", I_SystemParam.value_desc, I_DataTableColumn.LISTKB_SHOW, 1, 6));
    	collist.add(new I_DataTableColumn("name", I_SystemParam.name_desc, I_DataTableColumn.LISTKB_SHOW, 1, 20));
    	collist.add(new I_DataTableColumn("parentClass", I_SystemParam.parentClass_desc, I_DataTableColumn.LISTKB_SHOW, 1));
    	collist.add(new I_DataTableColumn("state", I_SystemParam.state_desc, I_DataTableColumn.LISTKB_SHOW, 1));
    	return collist;
    }
    */
    
    public String add(){
        ao.setParentClass(this.getParameter("parentClass", "-1"));
        Object[] params = new Object[]{"add", -1, ao.getParamClass(), ao.getClassName(), ao.getValue(), ao.getName(), ao.getParentClass(), ao.getState()};
        if(dbExe_p("SP_I_SystemParamManager", params)){
        	logger(ComBeanLogType.TYPE_ADD, "添加类别参数", params);
        	reflesh();
        }
		return BConstants.SUCCESS;
    }
    
    public String edit(){
        ao.setParentClass(this.getParameter("parentClass", "-1"));
        Object[] params = new Object[]{"edit", ao.getId(), ao.getParamClass(), ao.getClassName(), ao.getValue(), ao.getName(), ao.getParentClass(), ao.getState()};
        if(dbExe_p("SP_I_SystemParamManager", params)){
        	logger(ComBeanLogType.TYPE_EDIT, "编辑类别参数", params);
        	reflesh();
        }
        return BConstants.SUCCESS;
    }
    
    public String del(){
        Object[] params = new Object[]{"del", ao.getId(), ao.getParamClass(), ao.getClassName(), ao.getValue(), ao.getName()};
        if(dbExe_p("SP_I_SystemParamManager", params)){
        	logger(ComBeanLogType.TYPE_DEL, "删除类别参数", params);
        	reflesh();
        }
        return BConstants.SUCCESS;
    }

    public String reflesh(){
        ComBeanI_SystemParam.load();
        return BConstants.MESSAGE;
    }

    private void setResult_List(List<I_SystemParam> list) {
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{//new TColumn("ID"),
                                     new TColumn("参数类别"),
                                     new TColumn("参数值"),
                                     new TColumn("值名称"),
                                     new TColumn("状态"),
                                     new TColumn("更新日期"),
                                     new TColumn("操作", TColumn.ALIGN_LEFT)};
            for(I_SystemParam o: list){
                objs[i++] = new Object[]{"doFocus('"+o.getId()+"','"+o.getParamClass()+"','"+o.getClassName()+"','"+o.getValue()+"','"+o.getName()+"','"+o.getParentClass()+"','"+o.getState()+"');",
                                         //o.getId(),
                                         o.getParamClass()+" : "+o.getClassName(),
                                         o.getValue(),
                                         o.getName(),
                                         o.getState(),
                                         o.getUptDate(),
                                         this.getOptionHtmlString(o.getId(), o.getName(), "")};
            }
            
        }
        this.setResultList(objs);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -8422475366273959921L;

	private String c_class = "";
	
	private I_SystemParam ao = new I_SystemParam();
    private String value_1 = "";
    private String value_2 = "";
	
    public List<LabelValueBean> getStateList() {
        return ComBeanState.getList();
    }
    
    public List<LabelValueBean> getClassList(){
        List<Object> result = dbQuery(this.getUserSession().getRoleID()==1 ? "SP_I_SystemParamClassQuery" : "SP_I_SystemParamClassQueryShow", null);
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();
        list.add(new LabelValueBean("", ""));
        if(result!=null && result.size()>0){
            for(Object row: result){
                list.add(new LabelValueBean(DBUtil.getDBString(row, "className"), DBUtil.getDBString(row, "paramClass")));
            }
        }
        return Collections.unmodifiableList(list);
    }
	
	public I_SystemParam getAo() {
        return ao;
    }

    public void setAo(I_SystemParam ao) {
        this.ao = ao;
    }

    public String getC_class() {
		return c_class;
	}

	public void setC_class(String c_class) {
		this.c_class = c_class;
	}

    public String getValue_1() {
        return value_1;
    }

    public void setValue_1(String value_1) {
        this.value_1 = value_1;
    }

    public String getValue_2() {
        return value_2;
    }

    public void setValue_2(String value_2) {
        this.value_2 = value_2;
    }

}
