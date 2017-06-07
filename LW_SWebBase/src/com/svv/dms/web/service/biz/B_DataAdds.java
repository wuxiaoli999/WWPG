package com.svv.dms.web.service.biz;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gs.db.database.BizOBJResult;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.entity.IB_Formation;
import com.svv.dms.web.entity.I_DataParamType;
import com.svv.dms.web.entity.I_DataTableColumn;
import com.svv.dms.web.util.HIUtil;

public class B_DataAdds {
    private static int ADDS_FILE_COLUMN_NUM = 0;
    private static HashMap<Integer, HashMap<String, String>> dataParamTypeMap = new HashMap<Integer, HashMap<String, String>>();
    private static HashMap<Integer, HashMap<String, String>> empFormationMap = new HashMap<Integer, HashMap<String, String>>();

    public static BizOBJResult adds(XSSFWorkbook wb, List<I_DataTableColumn> collist){
        int i = 0;
        try {
            XSSFSheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum() + 1;
            ADDS_FILE_COLUMN_NUM = collist.size();
            String[][] line = new String[rowNum-1][ADDS_FILE_COLUMN_NUM];
            
            
            for (i = 0; i < rowNum-1; i++){
                line[i] = readLine(sheet.getRow(i+1), collist);
                if(line[i]==null){
                    return new BizOBJResult(false, "文件共"+rowNum+"行（第1行为标题），第"+ (i+2) +"行数据有误，请检查！", null);
                }
                int c = 0;
                for(I_DataTableColumn col: collist){
                	if(col.getDataType() == ParamClass.VALUE_DATA_TYPE_NUMBER){
                        try{ Double.parseDouble(line[i][c]);}catch(Exception e){ return new BizOBJResult(false, "文件共"+rowNum+"行（第1行为标题），第"+ (i+2) +"行"+col.getColMemo()+"有误，请检查！",null);}
                	}
                	c++;
                }
            }

            return new BizOBJResult(true, "", line);
        } catch (Exception e) {
            e.printStackTrace();
            return new BizOBJResult(false, "文件数据格式有误，请检查！", null);
        }
    }


    /**
     * 导入文件格式:
     * column0  column1 column2 column3 column4 column5 ...
     *  序号
     */
    private static String[] readLine(XSSFRow row, List<I_DataTableColumn> collist){
        String[] rtn = new String[ADDS_FILE_COLUMN_NUM];
        try{
            String col0 = HIUtil.getCellString(row.getCell((short) 0)).trim();
            String col1 = HIUtil.getCellString(row.getCell((short) 1)).trim();
            if(HIUtil.isEmpty(col0) || HIUtil.isEmpty(col1)) return null;

            int c = 0;
            for(I_DataTableColumn col: collist){
            	if(col.getExtValueScopeTypeID()>0){
	        		int paramTypeID = col.getExtValueScopeTypeID();
	        		//数据应用字典
	        		if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_SYS_DATAPARAM){
	        		    int param = Integer.parseInt(col.getExtValueScopeTypeParam());
	        			HashMap<String, String> tmp = dataParamTypeMap.get(param);
	        			if(tmp==null){
	        				tmp = getI_DataParamTypeMap(param);
	        				dataParamTypeMap.put(param, tmp);
	        			}
	            	    rtn[c++] = HIUtil.isEmpty(tmp.get(HIUtil.getCellString(row.getCell((short) c)).trim()), "-1");
	        			
	        		//业务数据
	        		}else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_DATATABLE){
	            	    rtn[c++] = HIUtil.getCellString(row.getCell((short) c)).trim();
	        		/*	
	        		//人员体系
	        		}else if(paramTypeID==ParamClass.VALUE_VALUE_SCOPE_TYPE_BIZ_EMPFORMATION){
                        int param = Integer.parseInt(col.getExtValueScopeTypeParam());
	        			HashMap<String, String> tmp = empFormationMap.get(param);
	        			if(tmp==null){
	        				tmp = getB_EmpFormationMap(param);
	        				empFormationMap.put(param, tmp);
	        			}
	            	    rtn[c++] = HIUtil.getCellString(row.getCell((short) c)).trim();
	            	    rtn[c-1] = HIUtil.isEmpty(tmp.get(rtn[c-1]), "") + Constants.SPLITER_DATA + rtn[c-1];
	            	    */
	        		}
            	}else{
            	    rtn[c++] = HIUtil.getCellString(row.getCell((short) c)).trim();
            	}
            }
           
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("导入文件数据格式有误！ error=" + e.getMessage());
            return null;
        }
        return rtn;
    }
    
    @SuppressWarnings("unchecked")
	private static HashMap<String, String> getI_DataParamTypeMap(int parentID){
        HashMap<String, String> map = new HashMap<String, String>();
        List<I_DataParamType> list = HIUtil.getList(I_DataParamType.class, HIUtil.dbQuery("SP_I_DataParamTypeQueryChildsByParent", new Object[]{parentID}));
        if (list != null && list.size()>0){
            for (I_DataParamType o : list) {
                map.put(o.getClassName(), o.getParamClassID()+"");
            }
        }       
        return map;
    }
    
    @SuppressWarnings("unchecked")
	private static HashMap<String, String> getB_EmpFormationMap(int parentID){
        HashMap<String, String> map = new HashMap<String, String>();
        List<IB_Formation> list = HIUtil.getList(IB_Formation.class, HIUtil.dbQuery("SP_B_EmpFormationQueryChildsByParent", new Object[]{parentID}));
        if (list != null && list.size()>0){
            for (IB_Formation o : list) {
                map.put(o.getFormationName(), o.getFormationID()+"");
            }
        }       
        return map;
    }

}
