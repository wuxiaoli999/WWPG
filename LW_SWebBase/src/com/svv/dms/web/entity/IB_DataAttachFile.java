package com.svv.dms.web.entity;

public class IB_DataAttachFile extends AbstractEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7332488505767684463L;
	
	protected long id    = -1;
    protected int tableID    = 0;
    protected long dataid    = -1;
    protected String fileName  = "";
    protected String fileMemo  = "";
    protected int fileType    = 0;
    protected String fileFormat    = "";
    protected int fileSize    = 0;
    protected String istDate    = "";
    protected String uptDate    = "";

    public static String id_desc    = "id";
    public static String tableID_desc    = "数据表ID";
    public static String dataid_desc    = "数据ID";
    public static String fileName_desc    = "附件文件名";
    public static String fileMemo_desc    = "附件说明";
    public static String fileType_desc    = "附件类别：图片 音频 视频 文本";
    public static String fileFormat_desc    = "附件格式";
    public static String fileSize_desc    = "附件大小(长度)";
    public static String istDate_desc    = "插入时间";
    public static String uptDate_desc    = "更新时间";

    public IB_DataAttachFile(){}
    public IB_DataAttachFile(Object rs){
    	loadFromRs(this, rs);
    }
    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public int getTableID(){
        return this.tableID;
    }

    public void setTableID(int tableID){
        this.tableID = tableID;
    }

    public long getDataid(){
        return this.dataid;
    }

    public void setDataid(long dataid){
        this.dataid = dataid;
    }

    public int getFileType(){
        return this.fileType;
    }

    public void setFileType(int fileType){
        this.fileType = fileType;
    }

    public String getFileFormat(){
        return this.fileFormat;
    }

    public void setFileFormat(String fileFormat){
        this.fileFormat = fileFormat;
    }

    public int getFileSize(){
        return this.fileSize;
    }

    public void setFileSize(int fileSize){
        this.fileSize = fileSize;
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

    public String getId_desc(){
        return id_desc;
    }

    public String getTableID_desc(){
        return tableID_desc;
    }

    public String getDataid_desc(){
        return dataid_desc;
    }

    public String getFileType_desc(){
        return fileType_desc;
    }

    public String getFileFormat_desc(){
        return fileFormat_desc;
    }

    public String getFileSize_desc(){
        return fileSize_desc;
    }

    public String getIstDate_desc(){
        return istDate_desc;
    }

    public String getUptDate_desc(){
        return uptDate_desc;
    }
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileMemo() {
		return fileMemo;
	}
	public void setFileMemo(String fileMemo) {
		this.fileMemo = fileMemo;
	}
	public String getFileName_desc() {
		return fileName_desc;
	}
	public String getFileMemo_desc() {
		return fileMemo_desc;
	}

}
