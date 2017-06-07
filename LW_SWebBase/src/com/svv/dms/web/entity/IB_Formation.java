package com.svv.dms.web.entity;

public class IB_Formation extends AbstractEntity {
	final public static int NODEKB_TABLETYPE = 1;
	final public static int NODEKB_TABLE = 2;
	final public static int NODEKB_DATA = 3;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5989882831664028704L;
	
	protected int formationID    = -1;
    protected String formationName    = "";
    protected int formationType = -1;
    protected int nodeType = -1;
    protected int parentID   = -1;
    protected long childNum  = 0;
    protected String memo    = "";
    protected String ext1    = "";
    protected String ext2    = "";
    protected String ext3    = "";
    protected int state      = 1;
    protected String istDate    = "";
    protected String uptDate    = "";
    
    protected String sort    = "";
    private int nodeKB = -1;

    public static String formationID_desc    = "ID";
    public static String formationName_desc    = "名称";
    public static String parentID_desc    = "上级";
    public static String childNum_desc    = "下级数目";
    public static String memo_desc    = "备注";
    public static String state_desc    = "状态";
    public static String ext1_desc    = "";
    public static String ext2_desc    = "";
    public static String ext3_desc    = "";

    public IB_Formation(){}
    public IB_Formation(Object rs){
    	loadFromRs(this, rs);
    }

    public int getFormationID() {
		return formationID;
	}
	public void setFormationID(int formationID) {
		this.formationID = formationID;
	}
	public String getFormationName() {
		return formationName;
	}
	public void setFormationName(String formationName) {
		this.formationName = formationName;
	}
	public int getParentID(){
        return this.parentID;
    }

    public void setParentID(int parentID){
        this.parentID = parentID;
    }

    public String getMemo(){
        return this.memo;
    }

    public void setMemo(String memo){
        this.memo = memo;
    }

    public String getExt1(){
        return this.ext1;
    }

    public void setExt1(String ext1){
        this.ext1 = ext1;
    }

    public String getExt2(){
        return this.ext2;
    }

    public void setExt2(String ext2){
        this.ext2 = ext2;
    }

    public String getExt3(){
        return this.ext3;
    }

    public void setExt3(String ext3){
        this.ext3 = ext3;
    }

    public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getIstDate(){
        return this.istDate;
    }

    public void setIstDate(String istDate){
        this.istDate = istDate;
    }

    public String getUptDate(){
        return this.uptDate;
    }

    public void setUptDate(String uptDate){
        this.uptDate = uptDate;
    }

    public String getFormationID_desc(){
        return formationID_desc;
    }

    public String getFormationName_desc(){
        return formationName_desc;
    }

    public String getParentID_desc(){
        return parentID_desc;
    }

    public String getMemo_desc(){
        return memo_desc;
    }

    public String getState_desc(){
        return state_desc;
    }
	public String getExt1_desc() {
		return ext1_desc;
	}
	public String getExt2_desc() {
		return ext2_desc;
	}
	public String getExt3_desc() {
		return ext3_desc;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public long getChildNum() {
		return childNum;
	}
	public void setChildNum(long childNum) {
		this.childNum = childNum;
	}
	public String getChildNum_desc() {
		return childNum_desc;
	}
	public int getFormationType() {
		return formationType;
	}
	public void setFormationType(int formationType) {
		this.formationType = formationType;
	}
	public int getNodeType() {
		return nodeType;
	}
	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}
	public int getNodeKB() {
		return nodeKB;
	}
	public void setNodeKB(int nodeKB) {
		this.nodeKB = nodeKB;
	}

}
