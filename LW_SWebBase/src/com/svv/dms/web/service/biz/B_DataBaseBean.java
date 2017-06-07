package com.svv.dms.web.service.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.common.ComBeanI_DataModule;
import com.svv.dms.web.common.ComBeanI_DataTable;
import com.svv.dms.web.common.ComBeanI_DataTableType;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.entity.B_Data;
import com.svv.dms.web.entity.I_DataModule;
import com.svv.dms.web.entity.I_DataTable;
import com.svv.dms.web.entity.I_DataTableType;
import com.svv.dms.web.service.base.AbstractBean;

public class B_DataBaseBean extends AbstractBean {
	final public static int ACTION_ID_COMMIT = 0;
	final public static int ACTION_ID_CHECK = 1;
	final public static int ACTION_ID_CMT = 2;
	final public static int ACTION_ID_REBACK = 3;
	final public static int ACTION_ID_PUBLISH = 4;
	final public static int ACTION_ID_DONGJIE = 5;

	protected String exeByCmd(String BeanName){
		if(ao.getTableID() > 0){
			I_DataTable table = ComBeanI_DataTable.get(ao.getTableID());
			ao.setTable(table);
			ao.setTableName(table.getTableName());
			ao.setTableMemo(table.getTableMemo());
		}else if(ao.getModuleID().length() > 0){
			I_DataModule module = ComBeanI_DataModule.get(ao.getModuleID());
			ao.setModule(module);
			ao.setModuleName(module.getModuleName());
			ao.setViewName(module.getViewName());
		}
        return super.exeByCmd(BeanName);
	}
		
	protected void msg(int actionID, String dataid){
		String tip = "";
		if(actionID==ACTION_ID_COMMIT){
			tip = "提交新增";
		}else if(actionID==ACTION_ID_CHECK){
			tip = "校验";
		}else if(actionID==ACTION_ID_CMT){
			tip = "审核";
		}else if(actionID==ACTION_ID_REBACK){
			tip = "退回";
		}else if(actionID==ACTION_ID_PUBLISH){
			tip = "发布";
		}
    	this.msg(tip + "数据："+ao.getTableMemo()+" 编号="+dataid, ao.getTableID(), dataid, actionID);
	}
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 7558246240700607904L;
    
    protected B_Data ao = new B_Data();
	protected String actionMemo = "";  //操作说明
	
    protected int sys_data_type = -1;
	
	public int getSys_data_type() {
        return sys_data_type;
    }

    public void setSys_data_type(int sys_data_type) {
        this.sys_data_type = sys_data_type;
    }

    public String getActionMemo() {
		return actionMemo;
	}

	public void setActionMemo(String actionMemo) {
		this.actionMemo = actionMemo;
	}

	public List<LabelValueBean> getTopDataTableTypeList() {
		List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
		
		List<I_DataTableType> list = ComBeanI_DataTableType.getList(-1, ParamClass.VALUE_LEVEL_ONE, ParamClass.VALUE_LEVEL_ONE);
    	if(list!=null && list.size()>0){    		
    		for(I_DataTableType o: list){
    			rtn.add(new LabelValueBean(ComBeanI_DataTableType.getSimpText(o.getTableTypeID()), o.getTableTypeID()+""));
    		}
    	}
        return Collections.unmodifiableList(rtn);
    }
	public List<LabelValueBean> getSctLevelList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_SCT_LEVEL, false, "");
    }
    
	public B_Data getAo() {
		return ao;
	}

	public void setAo(B_Data ao) {
		this.ao = ao;
	}

}
