package com.svv.dms.web.entity;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import com.gs.db.util.DBUtil;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.util.HIUtil;
import com.svv.dms.web.util.Pinyin4jUtil;

public class I_DataTable extends I_DataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175409144853160630L;
	
    protected int tableID;
    protected String tableName = "B_";
    protected String tableMemo = "";
    protected String serverPath = "";
    protected int tableTypeID;
	protected int colNum = 0;
	protected long dataNum = 0;
    protected int sctLevel   = -1;
    protected int sequenceFlag  = 0;
    protected int pkFlag  = 0;
    protected int sidFlag  = 0;
    protected int scopeFlag  = 0;
    protected int attachFileFlag  = 0;
    protected int dataStatusFlag  = 0;
    protected int sctLevelFlag  = 0;
    protected String parentTableID  = "";
	protected int childTableNum = 0;
    protected String childTableIDs  = "";

    public static String tableID_desc         = "数据表ID";
    public static String tableName_desc       = "英文名称";
    public static String tableMemo_desc       = "数据表中文名称";
    public static String serverPath_desc      = "服务器路径";
    public static String tableTypeID_desc     = "数据表类别";
    public static String colNum_desc          = "属性数";
    public static String dataNum_desc         = "数据数";
    public static String sctLevel_desc        = "密级";
    public static String sequenceFlag_desc    = "启用序列";
    public static String pkFlag_desc          = "启用主键";
	public static String sidFlag_desc         = "系统标识表";
    public static String scopeFlag_desc       = "值域数据表";
    public static String attachFileFlag_desc  = "启用附件";
    public static String dataStatusFlag_desc  = "启用数据状态";
    public static String sctLevelFlag_desc    = "启用密级";
    public static String otherButtons_desc    = "其他功能菜单";
    public static String parentTableID_desc   = "所属父表";
    public static String childTableNum_desc   = "子表数";
    public static String state_desc           = "有效状态";
    public static String istDate_desc         = "创建日期";

    private HashMap<Long, Object> dataMap = null;

    public I_DataTable(){}
    public I_DataTable(Object rs){
    	loadFromRs(this, rs);
    }
    
    public int getTableID() {
		return tableID;
	}

	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableMemo() {
		return tableMemo;
	}

	public void setTableMemo(String tableMemo) {
		this.tableMemo = tableMemo;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public int getSctLevel() {
		return sctLevel;
	}

	public void setSctLevel(int sctLevel) {
		this.sctLevel = sctLevel;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getIstDate() {
		return istDate;
	}

	public void setIstDate(String istDate) {
		this.istDate = istDate;
	}

	public String getUptDate() {
		return uptDate;
	}

	public void setUptDate(String uptDate) {
		this.uptDate = uptDate;
	}

	public String getTableID_desc() {
		return tableID_desc;
	}

	public String getTableName_desc() {
		return tableName_desc;
	}

	public String getTableMemo_desc() {
		return tableMemo_desc;
	}

	public String getServerPath_desc() {
		return serverPath_desc;
	}

	public String getSctLevel_desc() {
		return sctLevel_desc;
	}

	public String getState_desc() {
		return state_desc;
	}

	public String getIstDate_desc() {
		return istDate_desc;
	}

    public int getTableTypeID() {
        return tableTypeID;
    }

    public void setTableTypeID(int tableTypeID) {
        this.tableTypeID = tableTypeID;
    }

    public String getTableTypeID_desc() {
        return tableTypeID_desc;
    }

	public long getDataNum() {
		return dataNum;
	}

	public void setDataNum(long dataNum) {
		this.dataNum = dataNum;
	}

	public String getDataNum_desc() {
		return dataNum_desc;
	}
	public int getColNum() {
		return colNum;
	}
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
	public String getColNum_desc() {
		return colNum_desc;
	}
    public int getSidFlag() {
        return sidFlag;
    }
    public void setSidFlag(int sidFlag) {
        this.sidFlag = sidFlag;
    }
    public String getSidFlag_desc() {
        return sidFlag_desc;
    }
    public int getScopeFlag() {
        return scopeFlag;
    }
    public void setScopeFlag(int scopeFlag) {
        this.scopeFlag = scopeFlag;
    }
    public String getScopeFlag_desc() {
        return scopeFlag_desc;
    }
	public String getParentTableID() {
		return parentTableID;
	}
	public void setParentTableID(String parentTableID) {
		this.parentTableID = parentTableID;
	}
	public String getParentTableID_desc() {
		return parentTableID_desc;
	}
	public int getChildTableNum() {
		return childTableNum;
	}
	public void setChildTableNum(int childTableNum) {
		this.childTableNum = childTableNum;
	}
	public String getChildTableNum_desc() {
		return childTableNum_desc;
	}
	public String getChildTableIDs() {
		return childTableIDs;
	}
	public void setChildTableIDs(String childTableIDs) {
		this.childTableIDs = childTableIDs;
	}
    public String getAttachFileFlag_desc() {
        return attachFileFlag_desc;
    }
    public int getAttachFileFlag() {
        return attachFileFlag;
    }
    public void setAttachFileFlag(int attachFileFlag) {
        this.attachFileFlag = attachFileFlag;
    }
    public int getDataStatusFlag() {
        return dataStatusFlag;
    }
    public void setDataStatusFlag(int dataStatusFlag) {
        this.dataStatusFlag = dataStatusFlag;
    }
    public String getDataStatusFlag_desc() {
        return dataStatusFlag_desc;
    }
    public int getSctLevelFlag() {
        return sctLevelFlag;
    }
    public void setSctLevelFlag(int sctLevelFlag) {
        this.sctLevelFlag = sctLevelFlag;
    }
    public String getSctLevelFlag_desc() {
        return sctLevelFlag_desc;
    }
    public String getOtherButtons() {
        return otherButtons;
    }
    public void setOtherButtons(String otherButtons) {
        this.otherButtons = otherButtons;
    }
    public String getOtherButtons_desc() {
        return otherButtons_desc;
    }
    public int getSequenceFlag() {
		return sequenceFlag;
	}
	public void setSequenceFlag(int sequenceFlag) {
		this.sequenceFlag = sequenceFlag;
	}
	public String getSequenceFlag_desc() {
		return sequenceFlag_desc;
	}    
    public int getPkFlag() {
		return pkFlag;
	}
	public void setPkFlag(int pkFlag) {
		this.pkFlag = pkFlag;
	}
	public String getPkFlag_desc() {
		return pkFlag_desc;
	}

	@SuppressWarnings("unchecked")
	public List<I_DataTableColumn> queryColumnList(){
        return HIUtil.getList(I_DataTableColumn.class, HIUtil.dbQuery(SQL.SP_I_DataTableColumnQueryByC(this.getTableID()+"", "", "", true)));
    }

    /**********************************************************************/
    public TreeMap<Long, String> getDataKeyNameMap(String parentName1, String parentValue1, String parentName2, String parentValue2, String dataid, String keyword) {
        if(dataMap==null) setDataMap();
        TreeMap<Long, String> dataKeyNameMap = new TreeMap<Long, String>();
        if(dataMap!=null){
            List<I_DataTableColumn> collist = this.getColumns_keyname();
            if(collist!=null && collist.size()>0){
                String colname = collist.get(0).getColName();    
                String keyname;
                for(Object o: dataMap.values()){
                	keyname = DBUtil.getDBString(o, colname);
                    if( (HIUtil.isEmpty(parentValue1) || DBUtil.getDBString(o, parentName1).equals(parentValue1))
                            && (HIUtil.isEmpty(parentValue2) || DBUtil.getDBString(o, parentName2).equals(parentValue2))
                            && (HIUtil.isEmpty(dataid) || DBUtil.getDBString(o, "dataid").equals(dataid))
                            && (HIUtil.isEmpty(keyword) || keyname.indexOf(keyword)>=0 || Pinyin4jUtil.getPinYin(keyname).indexOf(keyword)>=0 || Pinyin4jUtil.getPinYinHeadChar(keyname).indexOf(keyword)>=0)
                    		)
                        dataKeyNameMap.put(DBUtil.getDBLong(o, "dataid"), keyname);
                }
            }
        }
        return dataKeyNameMap;
    }
    public HashMap<Long, Object> getDataMap(){
        if(dataMap==null) setDataMap();
        return dataMap;
    }
    public void reloadDataMap(){
        if(dataMap!=null) setDataMap();
    }
    @SuppressWarnings("rawtypes")
    private void setDataMap() {
        dataMap = new HashMap<Long, Object>();
        List result = HIUtil.dbQuery(SQL.SP_B_DataQueryByC(this.getTableID(), this.getTableName(), "", ""));
        if (result != null && result.size() > 0) {
            for (Object o : result) {
                dataMap.put(DBUtil.getDBLong(o, "dataid"), o);
            }
        }
    }
    /**********************************************************************/

}
