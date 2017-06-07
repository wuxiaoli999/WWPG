package com.svv.dms.web;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;

import com.gs.db.dao.DaoUtil;
import com.gs.db.database.JDBCDataManager;
import com.svv.dms.web.common.LWParam;
import com.svv.dms.web.common.SystemParam;
import com.svv.dms.web.dao.DaoFactory;
import com.svv.dms.web.service.system.LoginBean;

public class WebClientServlet extends ActionServlet {

	private static final long serialVersionUID = -4771125474045960131L;
    final public static boolean TSVR_FLAG = false;
    
    //private Server server = new Server(); 
		
	public void init() throws ServletException{
	    
        super.init();
        DaoUtil.setDaoManager(DaoFactory.getDaoManager(JDBCDataManager.SERVER_DATABASE_MODE));
         
        // 载入系统参数
        //LoginBean.initParam("5B35B7ABB7CAA5FCA84B876E315078D4", "9F37B0C8BF8747E4A0C88906AB73D340F749DBD52812F4CE");//PD_XDGPhm: pdxdgphm xdg999
        LoginBean.initParam("7DEB6DE174E48F0A", "4C83329BEB8DB3CD7E95F241003F09DA");//PC_ACE: pcace lolo999
        //LoginBean.initParam("5292AA42453C3663", "1F4F97E09DB103C0FC9C610B5060E67F");//PB_MEDI: pbmedi lolo999
        //LoginBean.initParam("9A3EDB57F2513D82A84B876E315078D4", "EC952EE3D9F98AD4CC2DFE6A0C0890F2");//YD_PUBNET: ydpubnet lolo999
        //LoginBean.initParam("CAC0DD4B9E898F06", "63EA3416F0C506A2D5A211DAA61FDED9FC9C610B5060E67F");//YC_SKDL: ycskdl lolo999
        //LoginBean.initParam("09E1FB96A6DCE858A84B876E315078D4", "B5B9D523E4E9A6518EEC9FEB020F71A6FC9C610B5060E67F");//YB_CapinfoNet: ybcapnet lolo999
        //LoginBean.initParam("E2243F1F7B285E49", "812281C9D3CA77F1CC2DFE6A0C0890F2");//YA_Deco: yadeco lolo999
        //LoginBean.initParam("2F4DD211D6C699F4", "673C995C6859555CCB60C537EC89BEA8FC9C610B5060E67F");//PA_DM动漫: padmdms lolo999
        //LoginBean.initParam("305E162D2022EEC8", "E83984A6A8C2C255CC2DFE6A0C0890F2");//lweb
        //LoginBean.initParam("6A76D90C4C0EB64EA84B876E315078D4", "60D132285EFA893FCC2DFE6A0C0890F2");//ofood
        //LoginBean.initParam("00791F446668140D", "BC939366E921EDD0CC2DFE6A0C0890F2");//微信
        //LoginBean.initParam("938D9CB19F1292E9", "EC1A35D58465BB6344DC236C0494C778");//ACEGEAR
        //LoginBean.initParam("25A3565B3897E2B0", "E9B4DE4C8C4B69E4F672214F4EAEE63AD8B0E3711C05BFEE");//Huayinfo
        //-----LoginBean.initParam("4BC7BBBF879334A3", "F2C0F0546B914C7161FDFBF377600E7E");//兵棋bqdms

        SystemParam.load();
        LWParam.load();
        System.out.println("==============启动日志999：启动完毕。");
	}	
}
