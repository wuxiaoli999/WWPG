package com.svv.dms.web.service.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;
import java.util.UUID;

import org.apache.struts.upload.FormFile;

import com.jeesoon.struts.beanaction.ActionContext;
import com.svv.dms.web.Constants;
import com.svv.dms.web.service.base.AbstractBean;
import com.svv.dms.web.service.base.BConstants;

public class UploadifyBean extends AbstractBean {
    
    public String upload() {
        try{
            System.out.println("上传文件 开始 。。。。。");
            String savePath = getServlet().getServletConfig().getServletContext().getRealPath("") + "/" + Constants.UPLOAD_FILE_PATH + this.getParameter("path") + "/";
            System.out.println(savePath);/////////////////////////////////////////////////////
            
            StringTokenizer st = new StringTokenizer(savePath, "/");
            String path1 = st.nextToken()+"/";
            String path2 = path1;
            while(st.hasMoreTokens()){
                path1 = st.nextToken()+"/";
                path2 += path1;
                File inbox = new File(path2);
                if(!inbox.exists())
                    inbox.mkdir();
            }
                        
            filename = this.getParameter("Filename");
            String extName = filename.substring(filename.lastIndexOf("."));
            name = UUID.randomUUID().toString();
            File file = new File(savePath + name + extName);
            try {
                System.out.println(savePath + name + extName);/////////////////////////////////////////////////////
                InputStream stream = uploadfile.getInputStream();// 把文件读入
                OutputStream bos = new FileOutputStream(file);// 建立一个上传文件的输出流
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                    bos.write(buffer, 0, bytesRead);// 将文件写入服务器
                }
                bos.close();
                stream.close();

                System.out.println("上传成功。");/////////////////////////////////////////////////////
            } catch (IOException e) {
                e.printStackTrace();
            }
            ActionContext.getActionContext().getResponse().getWriter().print(name + extName);
            this.setMessage(true, "文件上传成功.");
        }catch(Exception e){
            e.printStackTrace();
        }
        return BConstants.MESSAGE_PAGE;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -2340408785555467846L;

    private FormFile uploadfile;
    private String filename;
    private String name;
    
    public FormFile getUploadfile() {
        return uploadfile;
    }
    public void setUploadfile(FormFile uploadfile) {
        this.uploadfile = uploadfile;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
