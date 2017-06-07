package com.svv.dms.web.service.admin;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.common.ComBeanI_DataTable;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanState;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.entity.I_DataTableColumnExtInfo;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.TColumn;

public class I_DataTableColumnExtInfoBean extends AbstractBean {

    public String I_DataTableColumnExtInfo(){
        return this.exeByCmd("");
    }

    public String query(){
    	this.setOptionList(BConstants.OPTION_ALL);
        setResult_List(getQueryList(QUERY_CMD_List)); 
        return BConstants.LIST;
    }

    @SuppressWarnings("rawtypes")
    protected List queryListByCmd(int cmd){
    	if(cmd==QUERY_CMD_List){
            logger(ComBeanLogType.TYPE_QUERY, "查询元数据");
            return getList(I_DataTableColumnExtInfo.class, dbQuery("SP_I_DataTableColumnExtInfoQuery", null));
    	}
    	return null;
    }

    public String add(){
        Object[] params = new Object[]{"add", ao.getId(), ao.getTableID(), ao.getColid(), ao.getExtCode(), ao.getExtNameZh(), ao.getExtNameZhs(), ao.getExtNameEn(), ao.getExtNameEns(), ao.getExtNameFr1(), ao.getExtNameFr1s(), ao.getExtNameFr2(), ao.getExtNameFr2s(), ao.getExtNameFr3(), ao.getExtNameFr3s(), ao.getExtDefine(), ao.getExtShow(), ao.getExtLength(), ao.getExtPrecision(), ao.getExtUnit(), ao.getExtValueScope(), ao.getExtValueScopeTypeID(), ao.getExtValueScopeTypeParam(), ao.getExtRelationSubCols(), ao.getExtKeyIDFlag(), ao.getExtKeyNameFlag(), ao.getExtKeywordFlag(), ao.getExtQueryByFlag(), ao.getExtSynName(), ao.getExtRelateEnvironment(), ao.getExtVersion(), ao.getExtMemo(), ao.getExtState()};
        if(dbExe_p("SP_I_DTColumnExtInfoManager", params)){
        	logger(ComBeanLogType.TYPE_ADD, "添加元数据", params);
        	this.setScript("parent.page_redirect();parent.closeBox();");
            ComBeanI_DataTable.load();
        }
        return BConstants.MESSAGE_PAGE;
    }
    
    public String edit(){
        Object[] params = new Object[]{"edit", ao.getId(), ao.getTableID(), ao.getColid(), ao.getExtCode(), ao.getExtNameZh(), ao.getExtNameZhs(), ao.getExtNameEn(), ao.getExtNameEns(), ao.getExtNameFr1(), ao.getExtNameFr1s(), ao.getExtNameFr2(), ao.getExtNameFr2s(), ao.getExtNameFr3(), ao.getExtNameFr3s(), ao.getExtDefine(), ao.getExtShow(), ao.getExtLength(), ao.getExtPrecision(), ao.getExtUnit(), ao.getExtValueScope(), ao.getExtValueScopeTypeID(), ao.getExtValueScopeTypeParam(), ao.getExtRelationSubCols(), ao.getExtKeyIDFlag(), ao.getExtKeyNameFlag(), ao.getExtKeywordFlag(), ao.getExtQueryByFlag(), ao.getExtSynName(), ao.getExtRelateEnvironment(), ao.getExtVersion(), ao.getExtMemo(), ao.getExtState()};
        if(dbExe_p("SP_I_DTColumnExtInfoManager", params)){
        	logger(ComBeanLogType.TYPE_EDIT, "修改元数据", params);
        	this.setScript("parent.page_redirect();parent.closeBox();");
            ComBeanI_DataTable.load();
        }
        return BConstants.MESSAGE_PAGE;
    }
    
    public String del(){
        Object[] params = new Object[]{"del", ao.getId(), ao.getTableID(), ao.getColid(), ao.getExtCode(), ao.getExtNameZh()};
        if(dbExe_p("SP_I_DTColumnExtInfoManager", params)){
            logger(ComBeanLogType.TYPE_DEL, "删除元数据", params);
            ComBeanI_DataTable.load();
        }
        return BConstants.MESSAGE;
    }
    @SuppressWarnings("unchecked")
	protected void loadByID(){
        List<I_DataTableColumnExtInfo> list = getList(I_DataTableColumnExtInfo.class, dbQuery(
        		SQL.SP_I_DataTableColumnExtInfoQueryByC(ao.getId()==-1?"":ao.getId()+"", ao.getColid()==-1?"":ao.getColid()+"", "")));
        if (list == null ){
            //this.setMessage(false, ComError.err_000001);
        	List<I_DataTableColumn> list1 = getList(I_DataTableColumn.class, dbQuery("SP_I_DataTableColumnQueryByID",
        			new Object[]{ao.getColid()+""}));
        	if (list1 != null ){
        		ao.setTableID(list1.get(0).getTableID());
        		ao.setExtNameZh(list1.get(0).getColMemo());
        	}
        	
        }else{
            ao = list.get(0);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
	private void setResult_List(List list) {
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn(I_DataTableColumnExtInfo.extCode_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_DataTableColumnExtInfo.extNameZh_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_DataTableColumnExtInfo.extDefine_desc, null, TColumn.ALIGN_LEFT),
                                     new TColumn(I_DataTableColumnExtInfo.istDate_desc, "istdate", TColumn.ALIGN_LEFT),
                                     new TColumn("操作", null, TColumn.ALIGN_LEFT)};
            for(I_DataTableColumnExtInfo o: (List<I_DataTableColumnExtInfo>)list){
                objs[i++] = new Object[]{"",
                                         o.getExtCode(),
                                         o.getExtNameZh(),
                                         o.getExtDefine(),
                                         o.getExtIstDate(),
                                         this.getOptionHtmlString(o.getId()+"", o.getExtNameZh(), "")};
            }
            
        }
        this.setResultList(objs);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -8422475366273959921L;

    private I_DataTableColumnExtInfo ao = new I_DataTableColumnExtInfo();
    
    private String extValueScopeType = "";

    public List<LabelValueBean> getStateList() {
        return ComBeanState.getList();
    }
    
    public List<LabelValueBean> getExtValueScopeTypeList() {
		return ComBeanI_SystemParam.getList(ParamClass.CLASS_VALUE_SCOPE_TYPE, false, "");
    }

	public I_DataTableColumnExtInfo getAo() {
		return ao;
	}

	public void setAo(I_DataTableColumnExtInfo ao) {
		this.ao = ao;
	}

    public String getExtValueScopeType() {
		return extValueScopeType;
	}

	public void setExtValueScopeType(String extValueScopeType) {
		this.extValueScopeType = extValueScopeType;
	}

}
