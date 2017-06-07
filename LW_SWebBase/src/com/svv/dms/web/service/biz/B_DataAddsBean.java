package com.svv.dms.web.service.biz;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gs.db.database.BizOBJResult;
import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.Pinyin4jUtil;
import com.svv.dms.web.util.PoiUtil;
import com.svv.dms.web.util.TColumn;

public class B_DataAddsBean extends B_DataBean {

	public String B_DataAdds(){
        return this.exeByCmd("");
    }
	public String XMLDataAdds(){
        return "XMLDataAdds";
    }
	
	public String getXls(){
    	Object[] objs = getResult_List_xls();
    	String title = ao.getTableMemo() + "_" + HIUtil.getCurrentDate();
    	this.setResultPoi(ao.getTableName() + "_data", new PoiUtil().createXssf(title, objs));
    	return BConstants.POI;
	}
	
	protected String saveQueryCondition(){
		return SQL.SP_B_DataQueryByC(ao.getTableID(), ao.getTableName(), ao.getParentDataid()+"", ParamClass.VALUE_DATA_STATUS_BEGIN+"", this.getUserSession().getSctLevel()+"", getKeywordColNames(), c_keyword);
	}
	
	public String commit(){
		String dataids = this.getParameter("dataids");
		String [] dataid_list = (dataids.indexOf(",") > 0) ? dataids.split(",") : new String[]{dataids};
		for(String dataid: dataid_list){
	        Object[] params = new Object[]{
	        		"updateStatus", 
	        		dataid,
	        		ao.getTableID(),
	        		HIUtil.toSPParamSplitString(new Object[]{ParamClass.VALUE_DATA_STATUS_WAITING_CHECK, actionMemo}),
	        		Constants.SPLITER,
	        		2
	        		};
	        if(dbExe("SP_B_DataManager", params)){
	        	loggerB(ComBeanLogType.TYPE_EDIT, "提交新增数据："+ao.getTableMemo(), ao.getTableID()+"", dataid, params);
	        	this.msg(ACTION_ID_COMMIT, dataid);
	        }
		}
	    return BConstants.MESSAGE;
	}
	
    public String adds(){
        try {
            if(dataFile.getFileName().length()==0){ // || !dataFile.getFileName().toLowerCase().endsWith(".xls")
                setMessage(false, "文件格式有误，请检查！");
                return BConstants.MESSAGE_PAGE;
            }
            
            XSSFWorkbook wb = new XSSFWorkbook(dataFile.getInputStream());

            XSSFSheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            
            BizOBJResult br = B_DataAdds.adds(wb, getColumnList(null));
            if(!br.getResult()){
                this.setMessage(false, br.getInfo());
                return BConstants.MESSAGE_PAGE;
            }
            
            String[][] line = (String[][])br.getObject();

            List<I_DataTableColumn> collist = getColumnList(null);
            int fixColNum = (HIUtil.isEmpty(ao.getParentDataid()) ? 2 : 3);
        	Object[] tmpParams = new Object[collist.size() + fixColNum];
        	String paramValue;
        	StringBuilder keyword;
            boolean result = false;
            for (int i = 0; i<rowNum; i++) {
            	int k = 0;
            	
            	keyword = new StringBuilder();
            	tmpParams = new Object[collist.size() + fixColNum];
            	tmpParams[k++] = ao.getDataSctLevel();
            	tmpParams[k++] = ParamClass.VALUE_DATA_STATUS_BEGIN;
            	for(int c=0;c<collist.size();c++){
            		I_DataTableColumn o = collist.get(c);
            		paramValue = line[i][c];

            		if(o.getDataType()==ParamClass.VALUE_DATA_TYPE_DATETIME && !HIUtil.isEmpty(paramValue)) paramValue = "to_date('"+HIUtil.toDBStr(paramValue)+"','yyyy-mm-dd hh24:mi:ss')";
            		else paramValue = HIUtil.toDBStr(paramValue);
            		if(o.getExtKeyNameFlag()==1) keyword.append(paramValue).append(" ").append(Pinyin4jUtil.getPinYin(paramValue)).append(" ").append(Pinyin4jUtil.getPinYinHeadChar(paramValue)).append(" ");

            		tmpParams[k++] = paramValue;
            	}
            	if(!HIUtil.isEmpty(ao.getParentDataid())) tmpParams[k++] = ao.getParentDataid();

                Object[] params = new Object[]{
                		"add",
                		-1,
                		ao.getTableID(),
                		HIUtil.toDBStr(keyword.toString()),
                		HIUtil.toSPParamSplitString(tmpParams),
                		Constants.SPLITER,
                		collist.size() + fixColNum,
                		this.getUserSession().getUserName()
                		};
                if(dbExe("SP_B_DataManager", params)){
                	result = true;
                }
            }
            if(result) logger(ComBeanLogType.TYPE_ADD, "导入数据文件"+dataFileName);

    	    return BConstants.MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
            this.setMessage(false, "文件导入失败！ "+e.getMessage());
            return BConstants.ERROR;
        }
    }

	private Object[] getResult_List_xls() {        
        Object[] objs = null;
        int rownum = 2;

        List<I_DataTableColumn> collist = this.getColumnList(null);
    	int i = 0;
        objs = new Object[rownum+2];
        objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
        
        int j = 0;
        Object[] tmp = new Object[collist.size()+1];
        tmp[j++] = new TColumn("序号", null, TColumn.ALIGN_CENTER);
        for(I_DataTableColumn o: collist){
            tmp[j++] = new TColumn(o.getColMemo(), null, TColumn.ALIGN_CENTER);
        }
        objs[i++] = tmp;
        
        for(int k=0; k<rownum; k++){
        	tmp = new Object[collist.size()+2];
        	j = 0;
        	tmp[j++] = "";
        	tmp[j++] = (k+1);
        	for(int c=0;c<collist.size();c++){
            	tmp[j++] = "";
            }
            objs[i++] = tmp;
        }
        return objs;
    }
    

    /**
	 * 
	 */
	private static final long serialVersionUID = -3689648063103841459L;
	
}
