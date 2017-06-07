package com.svv.dms.web.service.system;

import java.util.List;

import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.SystemParam;
import com.svv.dms.web.entity.S_Param;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.DES;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.TColumn;

public class S_ParamBean extends AbstractBean {

    public String S_Param(){
        return this.exeByCmd("");
    }
    
    public String S_ParamSet(){
        return this.exeByCmd("");
    }

    @SuppressWarnings("unchecked")
    public String query(){
        this.addOptionList(BConstants.option_edit_string);
        this.addOptionList(BConstants.option_copy_string);
        this.addOptionList(BConstants.option_del_string);
        
        setResult_List(getList(S_Param.class, dbQuery("SP_S_ParamQuery", null)));
        logger(ComBeanLogType.TYPE_QUERY, "查询系统参数");
        return BConstants.LIST;
    }
    
    public String add(){
        Object[] params = new Object[] {
                "add",
                this.id,
                des.encrypt(this.paramName),
                des.encrypt(this.paramValue),
                des.encrypt(this.remark)
                };
        
        if(dbExe("SP_S_ParamManager", params)){
            logger(ComBeanLogType.TYPE_ADD, "添加系统参数", params);
            reload();
        }
        
        return BConstants.SUCCESS;
    }
    
    public String edit(){
        Object[] params = new Object[] {
                "edit",
                this.id,
                des.encrypt(this.paramName),
                des.encrypt(this.paramValue),
                des.encrypt(this.remark)
                };
        
        if(dbExe("SP_S_ParamManager", params)){
            logger(ComBeanLogType.TYPE_EDIT, "编辑系统参数", params);
            reload();
        }
        
        return BConstants.SUCCESS;
    }

    public String copy(){
        Object[] params = new Object[] {
                "copy",
                this.id,
                "",
                "",
                ""
                };
        
        if(dbExe("SP_S_ParamManager", params)){
            logger(ComBeanLogType.TYPE_ADD, "复制添加系统参数", params);
            reload();
        }
        
        return BConstants.SUCCESS;
    }
    public String del(){
        Object[] params = new Object[] {
                "del",
                this.id,
                "",
                "",
                ""
                };
        
        if(dbExe("SP_S_ParamManager", params)){
            logger(ComBeanLogType.TYPE_DEL, "删除系统参数", params);
            reload();
        }
        
        return BConstants.SUCCESS;
    }
    
    public void setResult_List(List<S_Param> list) {
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("ID"),
                                     new TColumn("参数名称"),
                                     new TColumn("参数值"),
                                     new TColumn("备注"),
                                     new TColumn("操作")};
            for(S_Param o: list){
                objs[i++] = new Object[]{"doFocus('"+o.getId()+"','"+o.getParamName()+"','"+o.getParamValue()+"','"+HIUtil.toHtmlStr(o.getRemark())+"');",
                                         o.getId(),
                                         o.getParamName(),                                         
                                         o.getParamValue(),                                         
                                         o.getRemark(),
                                         this.getOptionHtmlString(o.getId(), o.getParamName(), "")};
            }
            
        }
        this.setResultList(objs);
    }
    
    private void reload() {
        SystemParam.load();
    }

    /**
     * 
     */
    private static final long serialVersionUID = 5584988069920659556L;

    private String id = "";
    private String paramName = "";
    private String paramValue = "";
    private String remark = "";
    
    private static DES des = new DES();
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
