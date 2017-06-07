package com.svv.dms.web.common;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import com.gs.db.util.Configure;
import com.svv.dms.web.util.HIUtil;

public class LWParam{
    
    private static HashMap<String, String> map = null;

    public static String getValue(String paramName) {
        String rtn = "";
        if(map==null) load();
        if (map != null) rtn = map.get(paramName);
        if(HIUtil.isNull(rtn)){
            HIUtil.print("错误，参数[" + paramName + "]为空!");
        }
        return HIUtil.toString(rtn);
    }

    public static int getIntValue(String paramName) {
        int rtn = 0;
        if(map==null) load();
        try{
            if (map != null) rtn = Integer.parseInt(HIUtil.toString(map.get(paramName.toUpperCase())));
        } catch (Exception e) {
            rtn = 0;
            HIUtil.print("错误，参数[" + paramName.toUpperCase() + "]不是数值!");
            e.printStackTrace();
        }
        return rtn;
    }

	public static void load() {
        map = new HashMap<String, String>();        
        try{
            Configure con = new Configure("/lw.properties");  
            Properties pro = con.getProperties();
            System.out.println("pro.size()="+pro.size());
            Set<Object> keys = pro.keySet();
            for(Object key: keys){
                System.out.println("key=["+String.valueOf(key).trim()+"]   value=["+con.getProperty(String.valueOf(key)).trim()+"]");
            	map.put(String.valueOf(key).trim(), String.valueOf(con.getProperty(String.valueOf(key)).trim()));
            }
            System.out.println("==============启动日志102：读取属性文件[/lw.properties]完毕");
        }catch(Exception e){
            System.err.println("==============启动警告402：不能读取属性文件[/lw.properties]");
        }
    }

}
