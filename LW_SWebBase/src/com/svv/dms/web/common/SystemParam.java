package com.svv.dms.web.common;

import java.util.List;
import java.util.TreeMap;

import com.svv.dms.web.Constants;
import com.svv.dms.web.entity.S_Param;
import com.svv.dms.web.util.HIUtil;

public class SystemParam{
    
    private static TreeMap<String, String> map = null;

    public static String getValue(String paramName) {
        String rtn = "";
        if (map != null) rtn = map.get(paramName.toUpperCase());
        if(HIUtil.isNull(rtn)){
            HIUtil.print("错误，参数[" + paramName.toUpperCase() + "]为空!");
        }
        return HIUtil.toString(rtn);
    }

    public static int getIntValue(String paramName) {
        int rtn = 0;
        try{
            if (map != null) rtn = Integer.parseInt(HIUtil.toString(map.get(paramName.toUpperCase())));
        } catch (Exception e) {
            rtn = 0;
            HIUtil.print("错误，参数[" + paramName.toUpperCase() + "]不是数值!");
            e.printStackTrace();
        }
        return rtn;
    }

    @SuppressWarnings("unchecked")
	public static void load() {
        map = new TreeMap<String, String>();

        List<S_Param> result = HIUtil.getList(S_Param.class, HIUtil.dbQuery("SP_S_ParamQuery", null));
        if (result != null && result.size() > 0) {
            for (S_Param o : result) {
                map.put(o.getParamName().toUpperCase(), o.getParamValue());
            }
            System.out.println("==============启动日志101：数据库读取正常。");
        }else{
            System.err.println("==============启动警告401：数据库读取异常！");
        }
        Constants.loadSystemParam();
    }

}
