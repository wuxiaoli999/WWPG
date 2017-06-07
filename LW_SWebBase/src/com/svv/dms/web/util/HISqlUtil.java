package com.svv.dms.web.util;

import org.apache.commons.lang.StringUtils;


public class HISqlUtil {
    public static String getPageSQLFORMAT_MYSQL(){
        String sql = " select * from ( select * from ( select %s LIMIT %d,%d) as f1 %s LIMIT %d,%d) as f2 %s ";
        return sql;
    }
    public static String getPageSQLFORMAT_SQLSERVER(){
        String sql = " select * from ( select top %d * from ( select top %d %s ) as f1 %s ) as f2 %s ";
        return sql;
    }
	public static String getPageSQLFORMAT_ORACLE(){
		String sql = " select * from ( select rownum rownum_, f1.* from (%s ) f1 where 1=1) f2 where rownum_ between %d and %d %s ";  
    	return sql;
    }
    public static String upDownOrderByStr(String str){
    	String[] ss = str.split(",");
    	for(int i=0; i<ss.length; i++){
    		String s = ss[i].trim();
    		if(s.indexOf(" asc")>0){
    			s = s.replaceAll(" asc", " desc");
    		}else if(s.indexOf(" desc")>0){
    			s = s.replaceAll(" desc", "");
    		}else{
    			s += " desc";
    		}
    		ss[i] = s;
    	}
    	return StringUtils.join(ss, ",");
    }
    public static String getOrderByStr(String sql){
    	return StringUtils.getChomp(sql, "order by");
    }
	

}
