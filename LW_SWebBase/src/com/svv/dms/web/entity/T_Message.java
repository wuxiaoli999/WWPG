package com.svv.dms.web.entity;


public class T_Message extends AbstractEntity {

    private static final long serialVersionUID = -7073179232937445514L;

    protected long id;
    protected String content         = "";
    protected long tableID;
    protected long dataid;
    protected int actionID;
    protected long senderID;
    protected String senderName      = "";
    protected long recverID;
    protected String recverName      = "";
    protected int readFlag;

    protected String istDate = "";
    protected String uptDate = "";

    public T_Message(){}
    public T_Message(Object rs){
    	loadFromRs(this, rs);
    }
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getSenderID() {
		return senderID;
	}

	public void setSenderID(long senderID) {
		this.senderID = senderID;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public long getRecverID() {
		return recverID;
	}

	public void setRecverID(long recverID) {
		this.recverID = recverID;
	}

	public String getRecverName() {
		return recverName;
	}

	public void setRecverName(String recverName) {
		this.recverName = recverName;
	}

	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
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
	public long getTableID() {
		return tableID;
	}
	public void setTableID(long tableID) {
		this.tableID = tableID;
	}
	public long getDataid() {
		return dataid;
	}
	public void setDataid(long dataid) {
		this.dataid = dataid;
	}
	public int getActionID() {
		return actionID;
	}
	public void setActionID(int actionID) {
		this.actionID = actionID;
	}
}
