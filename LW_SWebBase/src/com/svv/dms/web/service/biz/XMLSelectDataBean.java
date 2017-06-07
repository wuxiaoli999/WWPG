package com.svv.dms.web.service.biz;

import java.io.File;
import java.util.TreeMap;

import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.FileDES;
import com.svv.dms.web.util.HIUtil;

public class XMLSelectDataBean extends B_DataBean {

	public String XMLSelectData(){
        return this.exeByCmd("ForXML");
    }
	public String showDataFileForXML(){
	    String fileName = this.getParameter("f");
	    String exd = fileName.substring(fileName.indexOf("."));
	    String fullName = getRealPath(Constants.UPLOAD_FILE_PATH + "\\" + B_DataBaseBean.PATH_DATAFILE + "\\" + dES.encrypt(fileName));
	    String tempName = getRealPath("temp\\" + HIUtil.getCurrentDate("yyyyMMddHHmmss")+this.getUserSession().getUserID() + exd);
	    
	    try {
            tempName = FileDES.decrypt(fullName, tempName);
            logger(ComBeanLogType.TYPE_QUERY, "查阅文件："+fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
	    this.setMessage(true, tempName);
	    return BConstants.MESSAGE;
	}
	public String delDataFileForXML(){
	    try{
    	    File file = new File(this.getParameter("f"));
    	    file.delete();
	    }catch(Exception e){
	        e.printStackTrace();
	    }
        return BConstants.MESSAGE;
	}
	public String getCommonInputHtmlForXML(){
        StringBuilder s = new StringBuilder("");
        long colid = this.getParameter("colid", -1);
        String value = this.getParameter("value");
        boolean editFlag = this.getParameter("editFlag", 0)==1;
        I_DataTableColumn o = null;
        try {
            o = ao.getTable().getColumnsMap().get(colid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(o!=null){
            s.append(initInputHTML(ao.getTable(), o, value, editFlag, null)[0]);
        }

        this.setMessage(true, s.toString());
        return BConstants.MESSAGE;
	}
	
	public String getSelectOptionDataForXML(){
        String parentName = this.getParameter("parentName");
        String parentValue = this.getParameter("parentValue");
        String name = this.getParameter("name");
        String value = this.getParameter("value");
        int synFlag = this.getParameter("synFlag", 0);
        boolean hasAll = this.getParameter("hasAll", 0)==1;
        String onchange = this.getParameter("onchange");
        TreeMap<Long, String> dataMap = null;
        if(synFlag == 0){
            System.out.println("parentName="+parentName+"   parentValue="+parentValue+"   name="+name+"   value="+value+"   onchange="+onchange);
            dataMap = ao.getTable().getDataKeyNameMap(parentName, parentValue, null, null, null, null);
        }else{
            dataMap = ao.getTable().getDataKeyNameMap(null, null, null, null, parentValue, null);
        }

        this.setMessage(true, HIUtil.genHtmlSelect(true, name, dataMap, value, hasAll, onchange));
        return BConstants.MESSAGE;
	}
	
    public String getTreeDataForXML(){
		String s = getTreeData(true);
    	this.setMessage(true, s.toString());
    	//System.out.println(s.toString()); //####################################################
    	return BConstants.MESSAGE;
    }    
    private String getTreeData(boolean firstLevel){
        StringBuilder s = new StringBuilder("");
    	try{
        	StringBuilder tmp = new StringBuilder("");
            String parentName = this.getParameter("parentName");
            String parentValue = this.getParameter("parentValue");
            String c_keyword = this.getParameter("c_keyword");
            if(ao.getTable().getDataNum()>2000){
            	return "[{\"rowid\":\"-1\",\"text\":\"数据太多，无法加载\",\"iconCls\":\"icon-no\"}]";
            }
            TreeMap<Long, String> dataMap = ao.getTable().getDataKeyNameMap(parentName, parentValue, null, null, null, c_keyword);
            //System.out.println("[getTreeData getTreeData getTreeData] parentID="+parentID+"] "+(list==null?0:list.size())); //####################################################
            if(dataMap!=null && dataMap.size()>0){
                for(Long key: dataMap.keySet()){
                	tmp.append("{\"rowid\":\"").append(key).append("\",\"text\":\"").append(dataMap.get(key)).append("\"");
                	tmp.append(",\"iconCls\":\"icon-ok\"");
                    //////if(o.getChildNum()>0) tmp.append(getTreeData(o.getTableTypeID(), level+1, false));
                    tmp.append("},");
                }
            }
            if(tmp.length() > 0){
	            s.append("[");
	            s.append(tmp);
	            s.setLength(s.length()-1);
	            s.append("]");
            }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return s.toString();
    }

    /**
	 * 
	 */
	private static final long serialVersionUID = -8636561795411579182L;

}
