package com.svv.dms.web.entity;


public class S_Module extends AbstractEntity {

    final public static int POWER_NONE = 0; //无
    final public static int POWER_OWN = 1; //本人
    final public static int POWER_AREA = 2; //本地区
    final public static int POWER_ALL = 9; //所有

    private static final long serialVersionUID = 63479965134972964L;

    protected String s_moduleID = "";
    protected String moduleID = "";
    protected String moduleName = "";
    protected int state;
    protected int isMenu;
    protected String parent = "";
    protected int hasChild;
    protected String url = "";
    protected String img = "";
    protected String power = "";
    protected String rolePower = "";
    protected int bizType = -1;
    protected String remark = "";
    protected String istDate = "";
    protected String uptDate = "";

    protected String myPower = "00000000"; //第1位:查看  第2位:添加  第3位:复制  第4位:编辑  第5位:删除  第6位:历史  第7位:导入  第8位:导出（0-无 1-本人 2-本地区 9-所有）

    public static String s_moduleID_desc   = "原模块编号";
    public static String moduleID_desc     = "模块编号";
    public static String moduleName_desc   = "模块名称";
    public static String state_desc        = "有效状态";
    public static String isMenu_desc       = "是否菜单";
    public static String parent_desc       = "上级菜单";
    public static String hasChild_desc     = "是否有子菜单";
    public static String url_desc          = "链接";
    public static String img_desc          = "图标文件名";
    public static String power_desc        = "可分配权限";
    public static String remark_desc       = "备注";

    public static final String[] POWER_ZH = {"详情", "新增", "复制", "编辑", "删除", "历史", "导入", "导出"};
    public static final String[] POWER_FUN = {"detail", "add", "copy", "edit", "del", "his", "import", "export"};

    public S_Module(){}
    public S_Module(Object rs){
    	loadFromRs(this, rs);
    }
    public String getModuleID() {
		return moduleID;
	}

	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(int isMenu) {
		this.isMenu = isMenu;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getHasChild() {
		return hasChild;
	}

	public void setHasChild(int hasChild) {
		this.hasChild = hasChild;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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

	public String getPower() {
        return power;
    }

    public boolean hasPower() {
        return !(rolePower.length()==0);
    }
    public void setPower(String power) {
        this.power = power;
    }

    public String getRolePower() {
        return rolePower;
    }

    public void setRolePower(String rolePower) {
        this.rolePower = rolePower;
    }
    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getModuleID_desc() {
		return moduleID_desc;
	}

	public String getModuleName_desc() {
		return moduleName_desc;
	}

	public String getState_desc() {
		return state_desc;
	}

	public String getIsMenu_desc() {
		return isMenu_desc;
	}

	public String getParent_desc() {
		return parent_desc;
	}

	public String getHasChild_desc() {
		return hasChild_desc;
	}

	public String getUrl_desc() {
		return url_desc;
	}

	public String getImg_desc() {
		return img_desc;
	}

	public String getPower_desc() {
		return power_desc;
	}

	public String getRemark_desc() {
		return remark_desc;
	}

	public String getS_moduleID() {
		return s_moduleID;
	}

	public void setS_moduleID(String s_moduleid) {
		s_moduleID = s_moduleid;
	}

	public String getS_moduleID_desc() {
		return s_moduleID_desc;
	}

}