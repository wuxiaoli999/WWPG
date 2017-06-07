package com.svv.dms.web.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class AAGenCode {
	public static void main(String[] args){
        AAGenCode a = new AAGenCode();
	    a.read("B_Process", "processTitle");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	}
	
	private void read(String table, String nameColName){
        try {
            FileInputStream fis = new FileInputStream("E:/Huainfo/PG/Hi_Web/db/db_setup.sql");
            InputStreamReader isr = new InputStreamReader(fis, "GB2312");
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            int flag = 0;
            List<String> cols = new ArrayList<String>();
            String tableMemo = "";
            while((str=br.readLine().trim())!=null){
                if(str.indexOf("Creating table hidms."+table+" ")>0){
                    flag = 1;
                    tableMemo = str.split(" ")[4].trim();
                    continue;
                }
                if(flag>0 && str.indexOf("CONSTRAINT")>=0) break;
                if(flag==2){
                    str = str.replaceAll("\\s{1,}", " ");
                    //System.out.println(str);
                    cols.add(str);
                }
                if(flag==1 && str.indexOf("(")>=0) flag = 2;
            }
            
            aa(table, tableMemo, cols);
            bb(table, tableMemo, nameColName, cols);
            cc(table, tableMemo, nameColName, cols);
            dd(table, tableMemo, nameColName, cols);
            
            br.close();
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        
	}

    private void aa(String table, String tableMemo, List<String> cols){
        try {
            File file = new File("D:\\temp\\"+table+".java");
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fis = new FileOutputStream("D:\\temp\\"+table+".java");
            OutputStreamWriter isr = new OutputStreamWriter(fis, "UTF-8");
            BufferedWriter br = new BufferedWriter(isr);
            
            br.write("package com.svv.dms.web.entity;\r\n");
            br.write("\r\n");
            br.write("public class "+table+" extends AbstractEntity {\r\n");
            //br.write("\r\n");
            
            for(String s1: cols){
                String[] s22 = s1.split(" ");
                if(s22[1].trim().startsWith("NUM")){
                    if(Integer.parseInt(s22[1].trim().substring(7, s22[1].trim().indexOf(",")))>6){
                        br.write("    protected long " + s22[0].trim() + "    = -1;\r\n");
                    }else{
                        br.write("    protected int " + s22[0].trim() + "    = "+(s22[1].trim().equals("state")?"1":"0")+";\r\n");
                    }
                }else if(s22[1].trim().startsWith("VAR") || s22[1].trim().startsWith("DATE")){
                    br.write("    protected String " + s22[0].trim() + "    = \"\";\r\n");
                }
            }
            br.write("\r\n");
            
            for(String s1: cols){
                String[] s22 = s1.split(" ");
                if(s22.length>2 && s22[2].trim().length()>0){
                    br.write("    public static String " + s22[0].trim() + "_desc    = \""+s22[2].replace("--", "").trim()+"\";\r\n");
                }
            }
            br.write("\r\n");

            br.write("    public "+table+"(){}\r\n");
            br.write("    public "+table+"(Object rs){\r\n");
            br.write("        loadFromRs(this, rs);\r\n");
            br.write("    }\r\n");
            
            
            for(String s1: cols){
                String[] s22 = s1.split(" ");
                if(s22[1].trim().startsWith("NUM")){
                    if(Integer.parseInt(s22[1].trim().substring(7, s22[1].trim().indexOf(",")))>6){
                        br.write("    public long get" + s22[0].trim().substring(0,1).toUpperCase()+ s22[0].trim().substring(1) + "(){\r\n");
                    }else{
                        br.write("    public int get" + s22[0].trim().substring(0,1).toUpperCase()+ s22[0].trim().substring(1) + "(){\r\n");
                    }
                }else if(s22[1].trim().startsWith("VAR") || s22[1].trim().startsWith("DATE")){
                    br.write("    public String get" + s22[0].trim().substring(0,1).toUpperCase()+ s22[0].trim().substring(1) + "(){\r\n");
                }
                br.write("        return this."+s22[0].trim()+";\r\n");
                br.write("    }\r\n");
                br.write("\r\n");
                
                if(s22[1].trim().startsWith("NUM")){
                    if(Integer.parseInt(s22[1].trim().substring(7, s22[1].trim().indexOf(",")))>6){
                        br.write("    public void set" + s22[0].trim().substring(0,1).toUpperCase()+ s22[0].trim().substring(1) + "(long "+s22[0].trim()+"){\r\n");
                    }else{
                        br.write("    public void set" + s22[0].trim().substring(0,1).toUpperCase()+ s22[0].trim().substring(1) + "(int "+s22[0].trim()+"){\r\n");
                    }
                }else if(s22[1].trim().startsWith("VAR") || s22[1].trim().startsWith("DATE")){
                    br.write("    public void set" + s22[0].trim().substring(0,1).toUpperCase()+ s22[0].trim().substring(1) + "(String "+s22[0].trim()+"){\r\n");
                }
                br.write("        this."+s22[0].trim()+" = "+s22[0].trim()+";\r\n");
                br.write("    }\r\n");
                br.write("\r\n");
            }

            for(String s1: cols){
                String[] s22 = s1.split(" ");
                if(s22.length>2  && s22[2].trim().length()>0){
                    br.write("    public String get" + s22[0].trim().substring(0,1).toUpperCase()+ s22[0].trim().substring(1) + "_desc(){\r\n");
                    br.write("        return "+s22[0].trim()+"_desc;\r\n");
                    br.write("    }\r\n");
                    br.write("\r\n");
                }
            }
            br.write("}\r\n");
            br.close();
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private void bb(String table, String tableMemo, String nameColName, List<String> cols){
        try {
            File file = new File("D:\\temp\\"+table+"Bean.java");
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fis = new FileOutputStream("D:\\temp\\"+table+"Bean.java");
            OutputStreamWriter isr = new OutputStreamWriter(fis, "UTF-8");
            BufferedWriter br = new BufferedWriter(isr);

            if(table.startsWith("B")) br.write("package com.svv.dms.web.service.biz;\r\n");
            if(table.startsWith("I")) br.write("package com.svv.dms.web.service.admin;\r\n");
            if(table.startsWith("S")) br.write("package com.svv.dms.web.service.system;\r\n");
            br.write("\r\n");
            br.write("public class "+table+"Bean extends AbstractBean {\r\n");
            br.write("\r\n");
            br.write("    public String "+table+"(){\r\n");
            br.write("        return this.exeByCmd(\"\");\r\n");
            br.write("    }\r\n");
            br.write("    \r\n");
            br.write("    public String query(){\r\n");
            br.write("        this.addOptionList(BConstants.option_edit_string);\r\n");
            br.write("        this.addOptionList(BConstants.option_copy_string);\r\n");
            br.write("        this.addOptionList(BConstants.option_del_string);\r\n");
            br.write("        setResult_List(getList("+table+".class, dbQuery(\"SP_"+table+"Query\", null)));\r\n");
            br.write("        logger(ComBeanLogType.TYPE_QUERY, \"查询"+tableMemo+"\");\r\n");
            br.write("        return BConstants.LIST;\r\n");
            br.write("    }\r\n");
            br.write("    \r\n");
            br.write("    public String add(){\r\n");
            br.write("        Object[] params = new Object[]{\"add\"");
            for(String s1: cols){
                String[] s22 = s1.split(" ");
                if(!s22[0].trim().equals("uptDate") && !s22[0].trim().equals("istDate")){
                  br.write(", ao.get" + s22[0].trim().substring(0,1).toUpperCase()+s22[0].trim().substring(1) + "()");
                }
            }
            br.write("};\r\n");
            br.write("        if(dbExe_p(\"SP_"+table+"Manager\", params)){\r\n");
            br.write("            logger(ComBeanLogType.TYPE_ADD, \"添加"+tableMemo+"\", params);\r\n");
            br.write("        }\r\n");
            br.write("        return BConstants.SUCCESS;\r\n");
            br.write("    }\r\n");
            br.write("    \r\n");
            br.write("    public String edit(){\r\n");
            br.write("        Object[] params = new Object[]{\"edit\"");
            for(String s1: cols){
                String[] s22 = s1.split(" ");
                if(!s22[0].trim().equals("uptDate") && !s22[0].trim().equals("istDate")){
                  br.write(", ao.get" + s22[0].trim().substring(0,1).toUpperCase()+s22[0].trim().substring(1) + "()");
                }
            }
            br.write("};\r\n");
            br.write("        if(dbExe_p(\"SP_"+table+"Manager\", params)){\r\n");
            br.write("            logger(ComBeanLogType.TYPE_EDIT, \"编辑"+tableMemo+"\", params);\r\n");
            br.write("        }\r\n");
            br.write("        return BConstants.SUCCESS;\r\n");
            br.write("    }\r\n");
            br.write("    \r\n");
            br.write("    public String copy(){\r\n");
            br.write("        Object[] params = new Object[]{\"copy\"");
            for(int i=0;i<cols.size();i++){
                if(i==0 || cols.get(i).startsWith(nameColName)){
                    String[] s22 = cols.get(i).split(" ");
                    br.write(", ao.get" + s22[0].trim().substring(0,1).toUpperCase()+s22[0].trim().substring(1) + "()");
                }
            }
            br.write("};\r\n");
            br.write("        if(dbExe_p(\"SP_"+table+"Manager\", params)){\r\n");
            br.write("            logger(ComBeanLogType.TYPE_ADD, \"复制"+tableMemo+"\", params);\r\n");
            br.write("        }\r\n");
            br.write("        return BConstants.MESSAGE;\r\n");
            br.write("    }\r\n");
            br.write("    \r\n");
            br.write("    public String del(){\r\n");
            br.write("        Object[] params = new Object[]{\"del\"");
            for(int i=0;i<cols.size();i++){
                if(i==0 || cols.get(i).startsWith(nameColName)){
                    String[] s22 = cols.get(i).split(" ");
                    br.write(", ao.get" + s22[0].trim().substring(0,1).toUpperCase()+s22[0].trim().substring(1) + "()");
                }
            }
            br.write("};\r\n");
            br.write("        if(dbExe_p(\"SP_"+table+"Manager\", params)){\r\n");
            br.write("            logger(ComBeanLogType.TYPE_DEL, \"删除"+tableMemo+"\", params);\r\n");
            br.write("        }\r\n");
            br.write("        return BConstants.MESSAGE;\r\n");
            br.write("    }\r\n");
            br.write("    \r\n");
            br.write("    private void setResult_List(List<"+table+"> list) {\r\n");
            br.write("        Object[] objs = null;\r\n");
            br.write("        if(list!=null && list.size()>0){\r\n");
            br.write("            int i = 0;\r\n");
            br.write("            objs = new Object[list.size()+2];\r\n");
            br.write("            objs[i++] = this.getParameter(\"tableHeight\", BConstants.DEF_TABLE_HEIGHT);\r\n");
            br.write("            objs[i++] = new Object[]{");
            for(int i=0;i<cols.size();i++){
                String[] s22 = cols.get(i).split(" ");
                if(i>0) br.write("                                     ");
                br.write("new TColumn("+table+"."+s22[0].trim()+"_desc, null, TColumn.ALIGN_LEFT),\r\n");
            }
            br.write("                                     new TColumn(\"操作\", null, TColumn.ALIGN_LEFT)};\r\n");
            br.write("            for("+table+" o: list){\r\n");
            br.write("                objs[i++] = new Object[]{\"doFocus(");
            for(int i=0;i<cols.size();i++){
                String[] s22 = cols.get(i).split(" ");
                if(!s22[0].trim().equals("uptDate") && !s22[0].trim().equals("istDate")){
                    if(i>0) br.write(",");
                    br.write("'\"+o.get"+s22[0].trim().substring(0,1).toUpperCase()+s22[0].trim().substring(1)+"()+\"'");
                }
            }
            br.write(");\",\r\n");
            for(String s1: cols){
                String[] s22 = s1.split(" ");
                if(s22[0].trim().equals("state")){
                    br.write("                                         ComBeanState.getText(o.get" + s22[0].trim().substring(0,1).toUpperCase()+s22[0].trim().substring(1) + "()),\r\n");
                }else{
                    br.write("                                         o.get" + s22[0].trim().substring(0,1).toUpperCase()+s22[0].trim().substring(1) + "(),\r\n");
                }
            }
            br.write("                                         this.getOptionHtmlString(");
            for(int i=0;i<cols.size();i++){
                if(i==0 || cols.get(i).startsWith(nameColName)){
                    String[] s22 = cols.get(i).split(" ");
                    br.write("o.get" + s22[0].trim().substring(0,1).toUpperCase()+s22[0].trim().substring(1) + "(),");
                }
            }
            br.write("\"\")};\r\n");
            br.write("            }\r\n");
            br.write("        }\r\n");
            br.write("        this.setResultList(objs);\r\n");
            br.write("    }\r\n");
            br.write("    \r\n");
            br.write("    private "+table+" ao = new "+table+"();\r\n");
            br.write("    public "+table+" getAo() {\r\n");
            br.write("        return ao;\r\n");
            br.write("    }\r\n");
            br.write("    public void setAo("+table+" ao) {\r\n");
            br.write("        this.ao = ao;\r\n");
            br.write("    }\r\n");


            br.write("}\r\n");
            br.close();
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
    

    private void cc(String table, String tableMemo, String nameColName, List<String> cols){
        try {
            File file = new File("D:\\temp\\"+table+".jsp");
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fis = new FileOutputStream("D:\\temp\\"+table+".jsp");
            OutputStreamWriter isr = new OutputStreamWriter(fis, "UTF-8");
            BufferedWriter br = new BufferedWriter(isr);
            
            br.write("<%@ page language=\"java\" pageEncoding=\"UTF-8\"%>\r\n");
            br.write("<%@ include file=\"/common/IncludeTop.jsp\" %>\r\n");
            br.write("\r\n");
            br.write("    <!--script language=javascript>setTitle(\"超级管理 - "+tableMemo+"管理\");</script-->\r\n");
            br.write("    <form name=\"MainForm\" action=\"/\" method=\"POST\">\r\n");
            br.write("\r\n");
            br.write("    <div id=\"db_main\">\r\n");
            br.write("    <div class=\"dt_header\">【"+tableMemo+"列表】\r\n");
            br.write("    &nbsp;&nbsp;&nbsp;&nbsp;<input type=\"button\" class=\"button\" value=\" 新建 \" onclick=\"add()\">\r\n");
            br.write("    </div>\r\n");
            br.write("    <div id=\"list_div\" align=\"center\"></div>\r\n");
            br.write("    </div>\r\n");
            br.write("\r\n");
            br.write("    </form>\r\n");
            br.write("\r\n");
            br.write("    <script language=\"javascript\">\r\n");
            br.write("    <!--\r\n");
            br.write("      function doFocus(){\r\n");
            br.write("        var items = doFocus.arguments;\r\n");
            br.write("        var i = 0;\r\n");
            for(String s1: cols){
                String[] s22 = s1.split(" ");
                if(!s22[0].trim().equals("uptDate") && !s22[0].trim().equals("istDate")){
                    br.write("        d(\"ao."+s22[0].trim()+"\").value = items[i++];\r\n");
                }
            }
            br.write("      }\r\n");
            br.write("      function add(node){\r\n");
            for(String s1: cols){
                String[] s22 = s1.split(" ");
                if(!s22[0].trim().equals("uptDate") && !s22[0].trim().equals("istDate")){
                    br.write("        d(\"ao."+s22[0].trim()+"\").value = '';\r\n");
                }
            }
            br.write("        setDisplay(d('div_btn'), false);\r\n");
            br.write("        setDisplay(d('btn_add'), false);\r\n");
            br.write("        setDisplay(d('btn_edit'), true);\r\n");
            br.write("        showFormBox('【"+tableMemo+" - 添加】','div_temp_win');\r\n");
            br.write("      }\r\n");
            br.write("      function edit(){\r\n");
            br.write("        setDisplay(d('div_btn'), false);\r\n");
            br.write("        setDisplay(d('btn_add'), true);\r\n");
            br.write("        setDisplay(d('btn_edit'), false);\r\n");
            br.write("        showFormBox('【"+tableMemo+" - 编辑】','div_temp_win');\r\n");
            br.write("      }\r\n");
            br.write("      function exec(tip,a){\r\n");
            br.write("        var f = document.DataForm;\r\n");
            String[] stemp = cols.get(0).split(" ");
            br.write("        if( (a=='add' || ((a=='edit'||a=='del') && checkSelect(f(\"ao."+stemp[0].trim()+"\"), \""+tableMemo+"\")))\r\n");
            br.write("         && confirmAction('确定要'+tip+'【'+f(\"ao."+nameColName+"\").value+'】吗？', function(){\r\n");
            br.write("             f.action = \""+table+".y?cmd=\"+a;\r\n");
            br.write("             f.submit();\r\n");
            br.write("         }))\r\n");
            br.write("           return true;\r\n");
            br.write("      }\r\n");
            br.write("      function del(obj,id,name){\r\n");
            br.write("        checkAdminPass('确定要删除[' + id + ':' + name + ']吗？', function(){\r\n");
            br.write("            ajax('"+table+".y?cmd=del', {'ao."+stemp[0].trim()+"':id,'ao."+nameColName+"':name}, function(html){\r\n");
            br.write("                setMessage(html);\r\n");
            br.write("                page_redirect();\r\n");
            br.write("            });\r\n");
            br.write("        });\r\n");
            br.write("      }\r\n");
            br.write("      function copy(obj,id,name){\r\n");
            br.write("        checkAdminPass('确定要复制[' + id + ':' + name + ']吗？', function(){\r\n");
            br.write("            ajax('"+table+".y?cmd=copy', {'ao."+stemp[0].trim()+"':id,'ao."+nameColName+"':name}, function(html){\r\n");
            br.write("                setMessage(html);\r\n");
            br.write("                page_redirect();\r\n");
            br.write("            });\r\n");
            br.write("        });\r\n");
            br.write("      }\r\n");
            br.write("      function page_redirect(page,pageKey){\r\n");
            br.write("        var f = document.MainForm;\r\n");
            br.write("        var dx = new DynaXmlHttp();\r\n");
            br.write("        dx.setAction(\""+table+".y?cmd=query\");\r\n");
            br.write("        load(dx,true);\r\n");
            br.write("      }\r\n");
            br.write("      function init(){\r\n");
            br.write("        page_redirect();\r\n");
            br.write("      }\r\n");
            br.write("    -->\r\n");
            br.write("    </script>\r\n");
            br.write("\r\n");
            br.write("<%@ include file=\"/common/IncludeBottom.jsp\" %>\r\n");
            br.write("\r\n");
            br.write("    <div id=\"div_temp_win\" class=\"easyui-dialog\">\r\n");
            br.write("    <form name=\"DataForm\" action=\"/\" method=\"POST\">\r\n");
            br.write("    <div id=\"db_main\">\r\n");
            br.write("    <table class=tbBorder cellSpacing=1>\r\n");
            for(String s1: cols){
                String[] s22 = s1.split(" ");
                if(!s22[0].trim().equals("uptDate") && !s22[0].trim().equals("istDate") && !s22[0].trim().equals("state")){
                    br.write("      <tr>\r\n");
                    br.write("        <td class=\"tdHeader\"><bean:write name=\""+table+"Bean\" property=\"ao."+s22[0].trim()+"_desc\" /></td>\r\n");
                    br.write("        <td class=\"tdBody\"><html:text styleClass=\"ipt_text\" name=\""+table+"Bean\" property=\"ao."+s22[0].trim()+"\" size=\"20\" /></td>\r\n");
                    br.write("      </tr>\r\n");
                }else if(s22[0].trim().equals("state")){
                    br.write("      <tr>\r\n");
                    br.write("        <td class=\"tdHeader\"><bean:write name=\""+table+"Bean\" property=\"ao."+s22[0].trim()+"_desc\" /></td>\r\n");
                    br.write("        <td class=\"tdBody\"><html:select styleClass=\"sel_text\" name=\""+table+"Bean\" property=\"ao."+s22[0].trim()+"\">\r\n");
                    br.write("                           <html:optionsCollection name=\""+table+"Bean\" property=\""+s22[0].trim()+"List\" /></html:select></td>\r\n");
                    br.write("      </tr>\r\n");
                }
            }
            br.write("      <tr id=\"div_btn\">\r\n");
            br.write("        <td class=\"tdHeaderP\" colspan=\"2\">\r\n");
            br.write("          <input type=\"button\" class=\"button\" name=\"btn_add\" value=\" 添加 \" onclick=\"exec('添加','add');\">&nbsp;&nbsp;\r\n");
            br.write("          <input type=\"button\" class=\"button\" name=\"btn_edit\" value=\" 修改 \" onclick=\"exec('修改','edit');\">&nbsp;&nbsp;\r\n");
            br.write("        </td>\r\n");
            br.write("      </tr>\r\n");
            br.write("    </table>\r\n");
            br.write("    </div>\r\n");
            br.write("    </form>\r\n");
            br.write("    </div>\r\n");
            br.write("\r\n");
            br.close();
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private void dd(String table, String tableMemo, String nameColName, List<String> cols){
        try {
            File file = new File("D:\\temp\\"+table+".sql");
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fis = new FileOutputStream("D:\\temp\\"+table+".sql");
            OutputStreamWriter isr = new OutputStreamWriter(fis, "UTF-8");
            BufferedWriter br = new BufferedWriter(isr);
            
            br.write("  -- "+table+" : "+tableMemo+"管理\r\n");
            br.write("  procedure SP_"+table+"Manager(\r\n");
            br.write("    a_type          in varchar2,\r\n");
            br.write("    a_id            in number,\r\n");
            br.write("    a_paramStr      in varchar2,\r\n");
            br.write("    a_spliter       in varchar2,\r\n");
            br.write("    a_preCount      in number,\r\n");
            br.write("    Message         out varchar2,\r\n");
            br.write("    ResultCursor    out rc_class)\r\n");
            br.write("  is\r\n");
            br.write("    v_paramStr      VARCHAR2(4000);\r\n");
            br.write("    tmp             VARCHAR2(500);\r\n");
            br.write("    splitlen        integer;\r\n");
            br.write("    N               integer;\r\n");
            for(int i=1;i<cols.size();i++){
                if(!cols.get(i).startsWith("uptDate") && !cols.get(i).startsWith("istDate")){
                    String[] s22 = cols.get(i).split(" ");
                    br.write("    a_"+s22[0].trim()+"       "+table+"."+s22[0].trim()+"%type;\r\n");
                }
            }
            br.write("  begin\r\n");
            br.write("    if a_type = 'add' or a_type = 'edit' then\r\n");
            br.write("\r\n");
            br.write("      splitlen := length(a_spliter||'a')-1;\r\n");
            br.write("      v_paramStr := a_paramStr;\r\n");
            br.write("      for N in 1..a_preCount loop\r\n");
            br.write("          tmp := substr(v_paramStr, 1, instr(v_paramStr, a_spliter)-1);\r\n");
            for(int i=1;i<cols.size();i++){
                if(!cols.get(i).startsWith("uptDate") && !cols.get(i).startsWith("istDate")){
                    String[] s22 = cols.get(i).split(" ");
                    String tmp = s22[1].trim().startsWith("NUM")||s22[1].trim().startsWith("CHAR") ?"to_number(tmp)":"tmp";
                    if(i==1){
                        br.write("             if N="+i+" then a_"+s22[0].trim()+" := "+tmp+";\r\n");
                    }else{
                        br.write("          elsif N="+i+" then a_"+s22[0].trim()+" := "+tmp+";\r\n");
                    }
                }
            }
            br.write("          end if;\r\n");
            br.write("          v_paramStr := substr(v_paramStr, instr(v_paramStr, a_spliter)+splitlen, length(v_paramStr));\r\n");
            br.write("      end loop;\r\n");
            br.write("\r\n");
            br.write("      if a_type = 'add' then\r\n");
            br.write("        Message := '添加';\r\n");
            br.write("        insert into "+table+"(");
            for(int i=0;i<cols.size();i++){
                String[] s22 = cols.get(i).split(" ");
                br.write(s22[0].trim()+(i==cols.size()-1?")\r\n":","));
            }
            br.write("             values(");
            for(int i=0;i<cols.size();i++){
                String[] s22 = cols.get(i).split(" ");
                if(i==0){
                    br.write("SF_GetMaxID('"+table+"','"+s22[0].trim()+"'),");
                }else if(!cols.get(i).startsWith("uptDate") && !cols.get(i).startsWith("istDate")){
                    br.write("a_"+s22[0].trim()+(i==cols.size()-1?")\r\n":","));
                }else if(cols.get(i).startsWith("uptDate") || cols.get(i).startsWith("istDate")){
                    br.write("sysdate"+(i==cols.size()-1?");\r\n":","));
                }
            }
            br.write("\r\n");
            br.write("      elsif a_type = 'edit' then\r\n");
            br.write("        Message := '修改';\r\n");
            br.write("        update "+table+" set\r\n");
            for(int i=1;i<cols.size();i++){
                String[] s22 = cols.get(i).split(" ");
                if(!cols.get(i).startsWith("uptDate") && !cols.get(i).startsWith("istDate")){
                    br.write("               "+s22[0].trim()+" = a_"+s22[0].trim()+(i==cols.size()-1?"\r\n":",\r\n"));
                }else if(cols.get(i).startsWith("uptDate") || cols.get(i).startsWith("istDate")){
                    br.write("               "+s22[0].trim()+" = sysdate"+(i==cols.size()-1?"\r\n":",\r\n"));
                }
            }
            String[] stemp = cols.get(0).split(" ");
            br.write("        where "+stemp[0].trim()+" = a_id;\r\n");
            br.write("      end if;\r\n");
            br.write("\r\n");
            br.write("    elsif a_type = 'copy' then\r\n");
            br.write("      Message := '复制';\r\n");
            br.write("        insert into "+table+"(");
            for(int i=0;i<cols.size();i++){
                String[] s22 = cols.get(i).split(" ");
                br.write(s22[0].trim()+(i==cols.size()-1?")\r\n":","));
            }
            br.write("             select ");
            for(int i=0;i<cols.size();i++){
                String[] s22 = cols.get(i).split(" ");
                if(i==0){
                    br.write("SF_GetMaxID('"+table+"','"+s22[0].trim()+"'),");
                }else if(s22[0].trim().equals(nameColName)){
                    br.write(s22[0].trim()+"||'<副>',");
                }else if(!cols.get(i).startsWith("uptDate") && !cols.get(i).startsWith("istDate")){
                    br.write(s22[0].trim()+(i==cols.size()-1?")\r\n":","));
                }else if(cols.get(i).startsWith("uptDate") || cols.get(i).startsWith("istDate")){
                    br.write("sysdate"+(i==cols.size()-1?"\r\n":","));
                }
            }
            br.write("        from "+table+" where "+stemp[0].trim()+" = a_id;\r\n");
            br.write("\r\n");
            br.write("    elsif a_type = 'del' then\r\n");
            br.write("\r\n");
            br.write("      Message := '删除';\r\n");
            br.write("      delete from "+table+" where "+stemp[0].trim()+" = a_id;\r\n");
            br.write("\r\n");
            br.write("    end if;\r\n");
            br.write("    commit;\r\n");
            br.write("    Message := Message || '"+tableMemo+"成功!';\r\n");
            br.write("    open ResultCursor for select '' sqlmsg from dual;\r\n");
            br.write("  exception when others then\r\n");
            br.write("    rollback;\r\n");
            br.write("    Message := Message || '"+tableMemo+"失败! '||substr(sqlerrm,1,100);\r\n");
            br.write("    open ResultCursor for select Message sqlmsg from dual;\r\n");
            br.write("  end;\r\n");
            br.write("\r\n");

            br.close();
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


}
