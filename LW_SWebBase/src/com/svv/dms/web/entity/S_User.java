package com.svv.dms.web.entity;

import java.util.List;

import com.svv.dms.web.common.ParamClass;

public class S_User extends AbstractEntity {

    private static final long serialVersionUID = 9175681533433383456L;

    protected long userID;
    protected String userName = "";
    protected String loginName = "";
    protected String password = "";
    protected String validatePassword = "";
    protected int roleID = 10000;
    protected int state = 0;
    protected int loginNum = 0;
    protected int loginFlag = 0;
    protected String lastLoginDate = "";
    protected String cssStyle = "";
    protected int areaID = -1;
    protected long empID = -1;
    protected long operator = -1;
    protected String istDate = "";
    protected String uptDate = "";

    /** ************************************************************** */

    protected int organID = -1;
    protected int departmentID = -1;
    protected int jobID = -1;
    protected String roleName = "";
    protected String empName = "";
    protected String organName = "";
    protected int organType;
    protected String departmentName = "";
    protected String jobName = "";
    protected String cardNO = "";
    protected int power;
    protected int sctLevel;

    private String userDescName = "";
    private List<S_Module> myMenuModules = null;
    
    public S_User(){}
    public S_User(Object rs){
    	loadFromRs(this, rs);
    	this.lastLoginDate = this.lastLoginDate.substring(0, lastLoginDate.length()-5);
    }
    public S_User(String userName){
    	this.userName = userName;
    }

	public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getRoleID() {
        return roleID;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public void setIstDate(String istDate) {
        this.istDate = istDate;
    }

    public String getIstDate() {
        return istDate;
    }

    public void setLoginFlag(int loginFlag) {
        this.loginFlag = loginFlag;
    }

    public int getLoginFlag() {
        return loginFlag;
    }

    public void setLastLoginDate(String lastloginDate) {
        this.lastLoginDate = lastloginDate;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isAdmin() {
        return this.power==1 ||this.power==ParamClass.VALUE_SUPER_ADMIN_POWER ? true : false;
    }

    public boolean isSuperAdmin() {
        return this.roleID==-999 && this.power==ParamClass.VALUE_SUPER_ADMIN_POWER? true : false;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    private boolean authenticated = false;

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    private List<String> myModules = null;

    public List<S_Module> getMyMenuModules() {
        return myMenuModules;
    }

    public void setMyMenuModules(List<S_Module> myMenuModules) {
        this.myMenuModules = myMenuModules;
    }

    public List<String> getMyModules() {
        return myModules;
    }

    public void setMyModules(List<String> myModules) {
        this.myModules = myModules;
    }

    public int getAreaID() {
        return areaID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public String getUptDate() {
        return uptDate;
    }

    public void setUptDate(String uptDate) {
        this.uptDate = uptDate;
    }

	public long getEmpID() {
		return empID;
	}

	public void setEmpID(long empID) {
		this.empID = empID;
	}

	public int getOrganID() {
		return organID;
	}

	public void setOrganID(int organID) {
		this.organID = organID;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public int getOrganType() {
		return organType;
	}

	public void setOrganType(int organType) {
		this.organType = organType;
	}

    public String getCardNO() {
        return cardNO;
    }

    public void setCardNO(String cardNO) {
        this.cardNO = cardNO;
    }

    public int getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(int loginNum) {
        this.loginNum = loginNum;
    }

    public String getValidatePassword() {
        return validatePassword;
    }

    public void setValidatePassword(String validatePassword) {
        this.validatePassword = validatePassword;
    }

    public long getOperator() {
        return operator;
    }

    public void setOperator(long operator) {
        this.operator = operator;
    }

	public int getSctLevel() {
		return sctLevel;
	}

	public void setSctLevel(int sctLevel) {
		this.sctLevel = sctLevel;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public int getDepartmentID() {
        return departmentID;
    }
    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
    public int getJobID() {
        return jobID;
    }
    public void setJobID(int jobID) {
        this.jobID = jobID;
    }
    public String getJobName() {
        return jobName;
    }
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
	public String getUserDescName() {
		return userDescName;
	}
	public void setUserDescName(String userDescName) {
		this.userDescName = userDescName;
	}
    
}