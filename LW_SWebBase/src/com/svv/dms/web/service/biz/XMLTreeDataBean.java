package com.svv.dms.web.service.biz;

import java.util.List;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.common.ComBeanIS_Department;
import com.svv.dms.web.common.ComBeanIS_Emp;
import com.svv.dms.web.common.ComBeanIS_Organ;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;

public class XMLTreeDataBean extends AbstractBean {

    public String XMLTreeData(){
        return this.exeByCmd("");
    }
    
    public String getTree(){
        String s = getTreeData(this.getParameter("parentID", 0)); 
        this.setMessage(true, s.toString());
        //System.out.println(s.toString()); //####################################################
        return BConstants.MESSAGE;
    }

    private String getTreeData(int parentID){
        StringBuilder s = new StringBuilder("");
        StringBuilder tmp = new StringBuilder("");
        int organID = Integer.parseInt(HIUtil.isNull(this.getSession("SSID_"+ComBeanIS_Organ.TABLEID), -1)+"");
        
        List<Object> list = null;
        if(treeType.equals("Emp")) list = ComBeanIS_Emp.getList(organID, parentID);
        else if(treeType.equals("Department")) list = ComBeanIS_Department.getList(organID, parentID);
        //System.out.println("[getTreeData getTreeData getTreeData parentID="+parentID+"] "+list.size());
        
        if(list!=null && list.size()>0){
            for(Object o: list){
                List<Object> l = treeType.equals("Emp") ? ComBeanIS_Emp.getList(organID, DBUtil.getDBInt(o, "dataid")) : ComBeanIS_Department.getList(organID, DBUtil.getDBInt(o, "dataid"));
                int childNum = l!=null ? l.size() : 0;
                boolean isParent = childNum>0;
                tmp.append("{\"rowid\":\"").append(isParent?0:1).append("_").append(DBUtil.getDBLong(o, "dataid")).append("\",\"code\":\"").append(DBUtil.getDBLong(o, "dataid")).append("\",\"text\":\"").append(DBUtil.getDBString(o, "c1")).append("\"");
                tmp.append(",\"childNum\":").append(childNum).append("");

                if(childNum > 0 && DBUtil.getDBInt(o, "dataid") >= 1000) tmp.append(",\"treeState\":\"closed\"");
                else if(childNum == 0 && DBUtil.getDBInt(o, "dataid") >= 1000) tmp.append(",\"iconCls\":\"icon-file\"");
                else tmp.append(",\"iconCls\":\"icon-dot\"");
                /////////////////////if(o.getChildNum()>0) tmp.append(getTreeData(o.getParamClassID(), o.getTypeLevel()+1, false));
                tmp.append("},");
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
    
    /**
     * 
     */
    private static final long serialVersionUID = -1126554688244511271L;
    
    private String treeType = "";
    private boolean checkbox = false;

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }
    
}
