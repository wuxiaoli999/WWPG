package com.svv.dms.web.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.gs.db.util.DBUtil;


public abstract class AbstractEntity implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = 8148181385182825666L;

    public AbstractEntity(){}
	
	@SuppressWarnings("rawtypes")
    protected void loadFromRs(AbstractEntity o, Object rs){
		try {
			
			Field[] fields = null;
			Class superC = o.getClass().getSuperclass();
			if(!superC.getName().equals("AbstractEntity")){
				Field[] myFields = o.getClass().getDeclaredFields();
				Field[] superFields = superC.getDeclaredFields();
				fields = new Field[myFields.length + superFields.length];
				System.arraycopy(myFields, 0, fields, 0, myFields.length);
				System.arraycopy(superFields, 0, fields, myFields.length, superFields.length);
			}else{
				fields = o.getClass().getDeclaredFields();
			}
			
			if(fields!=null && fields.length>0){
				for(Field f: fields){
					// private-2, protected-4, public static-9, final public static-25, private static final-26
					//if(o.getClass().getName().endsWith("olumn")) System.err.println("[AbstractEntity] filed=====" +f.getName()+ "    " + f.getModifiers() + "    superC=" + superC.getName());
					if( f.getModifiers() == 4 ){
						String name = f.getName();
						
						try {
							Method method = o.getClass().getMethod("set" + name.substring(0,1).toUpperCase() + name.substring(1), new Class[]{f.getType()});
							//System.err.println("[AbstractEntity] filed=====" +f.getName()+ "    " + f.getType().getName() + " rs=" + DBUtil.getDBString(rs, name));
							
							if(name.toLowerCase().endsWith("tdate")){
								//System.err.println("[AbstractEntity] is date");
						        method.invoke(o, DBUtil.getDBDateStr(rs, name));
							}else if(f.getType().getName().equals("java.lang.String")){
								//System.err.println("[AbstractEntity] is string");
						        method.invoke(o, DBUtil.getDBString(rs, name));
							}else if(f.getType().getName().equals("long")){
								//System.err.println("[AbstractEntity] is long");
						        method.invoke(o, DBUtil.getDBLong(rs, name));
							}else if(f.getType().getName().equals("int")){
								//System.err.println("[AbstractEntity] is int");
						        method.invoke(o, DBUtil.getDBInt(rs, name));
							}else if(f.getType().getName().equals("double")){
								//System.err.println("[AbstractEntity] is double");
						        method.invoke(o, DBUtil.getDBDouble(rs, name));
							}else{
								System.err.println("[AbstractEntity] is others");
						        method.invoke(o, DBUtil.getDBString(rs, name));
							}
						} catch (Exception e) {
							/////////System.err.println("[AbstractEntity Exception] no colname: "+this.getClass().getName()+" filed=====" +f.getName()+ "    " + "set" + name.substring(0,1).toUpperCase() + name.substring(1));
							//e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
