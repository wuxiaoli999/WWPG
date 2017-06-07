package com.svv.dms.web.service.admin;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.svv.dms.web.common.ComBeanLogType;
import com.svv.dms.web.common.ComBeanState;
import com.svv.dms.web.entity.T_Message;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;
import com.svv.dms.web.util.TColumn;

public class I_MessageBean extends AbstractBean {

    public String I_Message(){
        return this.exeByCmd("");
    }
    public String setRead(){
        if(this.dbExeBySQL("SP_T_MessageUpdateReadFlag", new Object[]{1, id})){
            this.setMessage(true, "1");
        }
        return BConstants.MESSAGE;
    }
    
    public String sendPage(){
    	return "sendPage";
    }
    
    @SuppressWarnings("unchecked")
	public String queryRecv(){
        //this.addOptionList(BConstants.option_del_string);
        setResult_List_recv(getList(T_Message.class, dbQuery("SP_T_MessageQueryByRecver", new Object[]{this.getUserSession().getUserID()})));
        logger(ComBeanLogType.TYPE_QUERY, "查询接收短消息");
        return BConstants.LIST;
    }

    @SuppressWarnings("unchecked")
	public String querySend(){
        setResult_List_send(getList(T_Message.class, dbQuery("SP_T_MessageQueryBySender", new Object[]{this.getUserSession().getUserID()})));
        logger(ComBeanLogType.TYPE_QUERY, "查询已发送短消息");
        return BConstants.LIST;
    }
    public String add(){
        this.senderID = this.getUserSession().getUserID()+"";
        this.senderName = this.getUserSession().getUserName();
        Object[] params = new Object[]{"add", -1, content, senderID, senderName, recverID, recverName, 0};
        if(dbExe_p("SP_T_MessageManager", params)) logger(ComBeanLogType.TYPE_ADD, "发送短消息", params);
        return BConstants.SUCCESS;
    }
    public String delFromSend(){
        if(this.dbExeBySQL("SP_T_MessageUpdateSendShowFlag", new Object[]{0, id})){
            this.setMessage(true, "1");
        }
        return BConstants.MESSAGE;
    }
    public String del(){
        if(this.dbExeBySQL("SP_T_MessageDel", new Object[]{StringUtils.join(ids, ",")})){
            logger(ComBeanLogType.TYPE_DEL, "删除短消息", null);  
            this.setMessage(true, "删除成功。");
        }
        return BConstants.SUCCESS;   
    }
    private void setResult_List_recv(List<T_Message> list) {
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("选择", null, TColumn.ALIGN_LEFT),
                                     new TColumn("内容"),
                                     new TColumn("发送人"),
                                     new TColumn("状态", null, TColumn.ALIGN_LEFT),
                                     new TColumn("发送日期", null, TColumn.ALIGN_LEFT),
                                     //new TColumn("操作", null, TColumn.ALIGN_LEFT)
            };
            for(T_Message o: list){
                objs[i++] = new Object[]{"",
                                         "<input type=checkbox class=checkbox name=ids value='"+o.getId()+"'>",
                                         "<a href=# onclick=\"detail("+o.getId()+",'"+o.getContent()+"')\">"+this.subString(o.getContent(), 30)+"</a>",
                                         o.getSenderName(),
                                         getReadFlagText(o.getReadFlag(), o.getId()),
                                         o.getIstDate(),
                                         //this.getOptionHtmlString(o.getId()+"", "","")
                                         };
            }
            
        }
        this.setResultList(objs);
    }

    private void setResult_List_send(List<T_Message> list) {
        
        Object[] objs = null;
        if(list!=null && list.size()>0){
            int i = 0;
            objs = new Object[list.size()+2];
            objs[i++] = this.getParameter("tableHeight", BConstants.DEF_TABLE_HEIGHT);
            objs[i++] = new Object[]{new TColumn("选择", null, TColumn.ALIGN_LEFT),
                                     new TColumn("内容"),
                                     new TColumn("接收人"),
                                     new TColumn("状态", null, TColumn.ALIGN_LEFT),
                                     new TColumn("发送日期", null, TColumn.ALIGN_LEFT),
                                     new TColumn("删除", null, TColumn.ALIGN_LEFT)};
            for(T_Message o: list){
                objs[i++] = new Object[]{"show('查看信息','"+o.getContent()+"',600,260)",
                                         "<input type=checkbox class=checkbox name=ids value='"+o.getId()+"'>",
                                         "<a href=# onclick=\"show('查看信息','"+o.getContent()+"',600,260)\">"+this.subString(o.getContent(), 30)+"</a>",
                                         o.getRecverName(),
                                         getReadFlagText(o.getReadFlag()),
                                         o.getIstDate(),
                                         "<a href=\"#\" onclick=\"del('"+o.getId()+"')\">删除</a>"};
            }
            
        }
        this.setResultList(objs);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -8422475366273959921L;

    private String[] ids = new String[]{};
    private String id = "";
	private String content         = "";
	private String senderID        = "";
	private String senderName      = "";
	private String recverID        = "";
	private String recverName      = "";
	private String readFlag        = "";
	
    public List<LabelValueBean> getStateList() {
        return ComBeanState.getList();
    }
    public String getReadFlagText(int key){
        if(key==0) return "<font color=red>未读</font>";
        if(key==1) return "已读";
        return "";
    }
    public String getReadFlagText(int key, long id){
        if(key==0) return "<a href=# onclick=setRead("+id+")><font color=red>未读</font></a>";
        if(key==1) return "已读";
        return "";
    }
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getRecverID() {
		return recverID;
	}

	public void setRecverID(String recverID) {
		this.recverID = recverID;
	}

	public String getRecverName() {
		return recverName;
	}

	public void setRecverName(String recverName) {
		this.recverName = recverName;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
