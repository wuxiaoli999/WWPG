package com.svv.dms.web.service.biz;

import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;


public class B_DataPublicCmtBean extends B_DataPublicBean {

	public String B_DataPublicCmt(){
        if("".equals(cmd) && ao.getTableID() > 0){
            if(!this.checkUserPower("B_DataPubCmt_"+ao.getTableID())){
                this.setMessage(false, "您没有访问权限，请联系系统管理员！");
                return BConstants.MESSAGE;
            }
        }
        return this.exeByCmd("");
    }
	
	protected String saveQueryCondition(){
        return SQL.SP_B_DataQueryByC(ao.getTableID(), ao.getTableName(), ao.getParentDataid()+"", ParamClass.VALUE_DATA_STATUS_WAITING_CMT+"", this.getUserSession().getSctLevel()+"", getKeywordColNames(), c_keyword);
	}
	
	public String cmt(){
		String dataids = this.getParameter("dataids");
		String [] dataid_list = (dataids.indexOf(",") > 0) ? dataids.split(",") : new String[]{dataids};
		for(String dataid: dataid_list){
	        Object[] params = new Object[]{
	        		"updateStatus", 
	        		dataid,
	        		ao.getTableID(),
	        		HIUtil.toSPParamSplitString(new Object[]{ParamClass.VALUE_DATA_STATUS_WAITING_PUBLISH, actionMemo}),
	        		Constants.SPLITER,
	        		2
	        		};
	        if(dbExe("SP_B_DataManager", params)){
	        	loggerB(ComBeanLogType.TYPE_EDIT, "审核数据："+ao.getTableMemo(), ao.getTableID()+"", dataid, params);
	        	this.msg(ACTION_ID_CMT, dataid);
	        }
		}
	    return BConstants.MESSAGE;
	}
	
	public String recheck(){
		String dataids = this.getParameter("dataids");
		String [] dataid_list = (dataids.indexOf(",") > 0) ? dataids.split(",") : new String[]{dataids};
		for(String dataid: dataid_list){
	        Object[] params = new Object[]{
	        		"updateStatus", 
	        		dataid,
	        		ao.getTableID(),
	        		HIUtil.toSPParamSplitString(new Object[]{ParamClass.VALUE_DATA_STATUS_BEGIN, actionMemo}),
	        		Constants.SPLITER,
	        		2
	        		};
	        if(dbExe("SP_B_DataManager", params)){
	        	loggerB(ComBeanLogType.TYPE_ADD, "退回数据："+ao.getTableMemo(), ao.getTableID()+"", dataid, params);
	        	this.msg(ACTION_ID_REBACK, dataid);
	        }
		}
	    return BConstants.MESSAGE;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3689648063103841459L;

}
