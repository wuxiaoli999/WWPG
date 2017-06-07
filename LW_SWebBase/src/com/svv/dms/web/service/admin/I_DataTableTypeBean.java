package com.svv.dms.web.service.admin;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.common.ComBeanI_DataTable;
import com.svv.dms.web.common.ComBeanI_DataTableType;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanState;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.entity.I_DataTableType;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.TColumn;

public class I_DataTableTypeBean extends AbstractBean {

    public String I_DataTableType(){
        return this.exeByCmd("");
    }
	  
    @SuppressWarnings("unchecked")
	public String query(){
        this.addOptionList(BConstants.option_del_string);
        setResult_List(getList(I_DataTableType.class, dbQuery("SP_I_DataTableTypeQuery", null)));
        logger(ComBeanLogType.TYPE_QUERY, "查询数据表类别");
        return BConstants.LIST;
    }

    public String add(){
        Object[] params = new Object[]{"add", dtt.getTableTypeID(), dtt.getTableTypeName(), dtt.getTypeLevel(), dtt.getParentID(), dtt.getMemo(), dtt.getState()};
        if(dbExe_p("SP_I_DataTableTypeManager", params)){
        	logger(ComBeanLogType.TYPE_ADD, "添加数据表类别", params);
        	reflesh();
        }
        return BConstants.SUCCESS;
    }
    
    public String edit(){
        Object[] params = new Object[]{"edit", dtt.getTableTypeID(), dtt.getTableTypeName(), dtt.getTypeLevel(), dtt.getParentID(), dtt.getMemo(), dtt.getState()};
        if(dbExe_p("SP_I_DataTableTypeManager", params)){
        	logger(ComBeanLogType.TYPE_EDIT, "编辑数据表类别", params);
        	reflesh();
        }
        return BConstants.SUCCESS;
    }
    
    public String del(){
        Object[] params = new Object[]{"del", dtt.getTableTypeID(), dtt.getTableTypeName()};
        if(dbExe_p("SP_I_DataTableTypeManager", params)){
        	logger(ComBeanLogType.TYPE_DEL, "删除数据表类别", params);
        	reflesh();
        }
        return BConstants.MESSAGE;
    }

    public String reflesh(){
        Reflesh();
        return BConstants.MESSAGE;
    }
    public static void Reflesh(){
        ComBeanI_DataTableType.load();
        ComBeanI_DataTable.load();
    }

    private void setResult_List(List<I_DataTableType> list) {
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("ID", "tableTypeID", TColumn.ALIGN_LEFT),
                                     new TColumn("类别名称", null, TColumn.ALIGN_LEFT),
                                     new TColumn("级别", "typeLevel", TColumn.ALIGN_LEFT),
                                     new TColumn("状态", null, TColumn.ALIGN_LEFT),
                                     new TColumn("更新日期", null, TColumn.ALIGN_LEFT),
                                     new TColumn("操作", null, TColumn.ALIGN_LEFT)};
            for(I_DataTableType o: list){
                objs[i++] = new Object[]{"doFocus('"+o.getTableTypeID()+"','"+o.getTableTypeName()+"','"+o.getTypeLevel()+"','"+o.getParentID()+"','"+o.getMemo()+"','"+o.getState()+"');",
                                         o.getTableTypeID(),
                                         o.getTableTypeName(),
                                         ComBeanI_SystemParam.getText(ParamClass.CLASS_LEVEL, o.getTypeLevel()),
                                         ComBeanState.getText(o.getState()),
                                         o.getUptDate(),
                                         this.getOptionHtmlString(o.getTableTypeID(), o.getTableTypeName(), "")};
            }
            
        }
        this.setResultList(objs);
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = 6056554675524855323L;
	
	protected I_DataTableType dtt = new I_DataTableType();

	public I_DataTableType getDtt() {
		return dtt;
	}

	public void setDtt(I_DataTableType dtt) {
		this.dtt = dtt;
	}
	

	
    public List<LabelValueBean> getStateList() {
        return ComBeanState.getList();
    }
    
	public List<LabelValueBean> getLevelList(){
		return ComBeanI_SystemParam.getList(ParamClass.CLASS_LEVEL, false, "");
    }
}
