package com.svv.dms.web.service.biz;

import java.util.List;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.Constants;
import com.svv.dms.web.common.ComBeanI_SystemParam;
import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ParamClass;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.IB_DataAttachFile;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.HIUtil;

public class IB_DataAttachFileBean extends AbstractBean {


	public String XMLDataAttachFile(){
		if(HIUtil.isEmpty(this.getParameter("cmd"))){
			this.afp = this.getParameter("afp", 0);
		}
        return this.exeByCmd("ForAttachFile");
	}
    @SuppressWarnings("unchecked")
	public String queryForAttachFile(){
        StringBuilder rtn = new StringBuilder("");
        List<IB_DataAttachFile> list = getList(IB_DataAttachFile.class, dbQuery(SQL.SP_B_DataAttachFileQueryByC(ao.getTableID()+"", ao.getDataid()+"")));
        if(list!=null && list.size()>0){
            boolean adminFlag = false;
            int i = 0;
            String path = getServlet().getServletContext().getContextPath() + "/" + Constants.UPLOAD_FILE_PATH;
            
            rtn.append("<div class=dt_header><img src=\"../doc/images/dotbx.png\" style=\"padding-right:10px;\">多媒体资料")
            .append("</div><table class=tbBorder cellspacing=1>");
            
            for(IB_DataAttachFile o: list){
                adminFlag = afp == 1;
                    
                if(i%4==0) rtn.append("<tr>");
                rtn.append("<td class=tdBodyM><a href=#><img src=\"").append(path).append(o.getFileName())
                .append("\" width=120 onclick=\"showPic(this.src)\"></a>");
                if(afp == 1) rtn.append(" <a href=\"javascript:void(0)\" onclick=\"del('").append(o.getId()).append("','").append(o.getFileName()).append("')\">删</a>");
                if(adminFlag){
                    rtn.append("<br>多媒体资料说明:<br><input class=ipt_text name=picDesc"+o.getId()+" value=\""+o.getFileMemo()+"\" size=15><input type=button class=buttonLine value=修 onclick=\"editFileDesc('"+o.getId()+"')\">");
                }else{
                    rtn.append("<br><b>").append(o.getFileMemo()).append("</b>");
                }
                rtn.append("</td>");
                i++;
            }
            rtn.append("</tr></table>");
        }else{
            rtn.append("<font color=red><b>没有多媒体资料！</b></font>");
        }
        this.setMessage(true, rtn.toString());
        return BConstants.MESSAGE;
    }
    public String addForAttachFile(){
        String tmp = afileFile.getFileName().toLowerCase();
        if(!tmp.endsWith(".jpg") && !tmp.endsWith(".gif")  && !tmp.endsWith(".png")  && !tmp.endsWith(".bmp")){
            this.setMessage(false, "请检查上传资料的后缀名，仅支持.jpg 、.gif 、.png 、.bmp格式！");
            return BConstants.SUCCESS;
        }
        ao.setFileName(this.uploadFormFile(afileFile, 2048000, "/Image/", ao.getFileType()+"", true, false, false, false));
        if(ao.getFileName()!=null && ao.getFileName().length()>0){
            Object[] params = new Object[]{ "add", -1, ao.getTableID(), ao.getDataid(), ao.getFileName(), ao.getFileMemo(), ao.getFileType(), ao.getFileFormat(), ao.getFileSize()};
            if(dbExe_p("SP_B_DataAttachFileManager", params)){
                loggerB(ComBeanLogType.TYPE_ADD, "上传多媒体资料", ao.getTableID()+"", ao.getDataid()+"", params);
                this.setMessage(true, "多媒体资料上传成功.");
            }
        }
        return BConstants.SUCCESS;
    }
    public String editForAttachFile(){
        Object[] params = new Object[]{ "edit", ao.getId(), ao.getTableID(), ao.getDataid(), ao.getFileName(), ao.getFileMemo(), ao.getFileType(), ao.getFileFormat(), ao.getFileSize()};
        if(dbExe_p("SP_B_DataAttachFileManager", params)){
            loggerB(ComBeanLogType.TYPE_EDIT, "修改多媒体资料", ao.getTableID()+"", ao.getDataid()+"", params);
            this.setMessage(true, "多媒体资料修改成功.");
        }
        return BConstants.MESSAGE;
    }
    public String delForAttachFile(){
        this.delFile(ao.getFileName());
        Object[] params = new Object[]{"del", ao.getId(), ao.getFileName()};
        if(dbExe_p("SP_B_DataAttachFileManager", params)){
            loggerB(ComBeanLogType.TYPE_DEL, "删除多媒体资料", ao.getTableID()+"", ao.getDataid()+"", params);
            this.setMessage(true, "多媒体资料删除成功.");
        }
        return BConstants.MESSAGE;
    }


	/**
	 * 
	 */
	private static final long serialVersionUID = 712490035813069364L;

    private FormFile afileFile;
    private IB_DataAttachFile ao = new IB_DataAttachFile();
    
    public List<LabelValueBean> getFileTypeList() {
        return ComBeanI_SystemParam.getList(ParamClass.CLASS_FILE_TYPE, false, "");
    }

    private int afp = 0;

	public int getAfp() {
		return afp;
	}

	public void setAfp(int afp) {
		this.afp = afp;
	}
    public FormFile getAfileFile() {
        return afileFile;
    }
    public void setAfileFile(FormFile afileFile) {
        this.afileFile = afileFile;
    }
    public IB_DataAttachFile getAo() {
        return ao;
    }
    public void setAo(IB_DataAttachFile ao) {
        this.ao = ao;
    }

}
