<%@ page language="java" import="java.io.*" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String fullName = request.getQueryString();
  //String fullName = (String)request.getParameter("");
  if(fullName==null || fullName.length()==0){
    out.println("非法请求");
    return;
  }  
  
  String filedisplay = java.net.URLEncoder.encode(fullName.substring(fullName.lastIndexOf("/")+1),"UTF-8");
  String fileName = request.getRealPath("/") + "/exportfile/" + filedisplay;
  System.out.println(fileName);
    
  //设置响应头和下载保存的文件名
  response.setContentType("application/pdf");
  response.setHeader("Content-Disposition", "attachment; filename=\"" + filedisplay + "\"");


  OutputStream outer = null;   
  FileInputStream in = null;   
  try{
    in = new FileInputStream(fileName);
       
    byte[] b = new byte[1024];   
    int i = 0;
    
    outer = response.getOutputStream();
    while((i = in.read(b)) > 0){   
      outer.write(b, 0, i);   
    }
    outer.flush();   
    //要加以下两句话，否则会报错   
    //java.lang.IllegalStateException: getOutputStream() has already been called for //this response     
    out.clear();   
    out = pageContext.pushBody();   
  }catch(FileNotFoundException e){ 
    out.println("文件不存在！");
    System.out.println(e.getMessage());   
  }finally{   
    if(in != null){   
      in.close();   
      in = null;   
    }
  }   
%>