package com.svv.dms.web.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.svv.dms.web.Constants;

public class FileUploadUtil {

    private String m_FileStorePath = Constants.UPLOAD_FILE_PATH;

    private int m_maxPostSize = 2 * 1024 * 1024; // 2 M

    private List<String> m_strFileName;

    public String[] getTextFile(HttpServletRequest request, String path) {
        try {
            uploadFile(request, path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // reset file name
        List<String> rtn = m_strFileName;
        
        // zipFile must not null and must ends with the specified suffix ".txt" or ".dat"
        if (m_strFileName != null && m_strFileName.size() > 0){
            for(String s: m_strFileName){
                if(s.toLowerCase().trim().endsWith(".txt") 
                  || s.toLowerCase().trim().endsWith(".xls")) {
                    ;
                }else{
                    rtn.remove(s);
                    new File(s).delete();
                }
            }
        }
        return rtn==null ? null : (String[])rtn.toArray();
    }

	@SuppressWarnings("rawtypes")
    private void uploadFile(HttpServletRequest request, String path) throws IOException {
        m_FileStorePath = path + m_FileStorePath + "\\";
        MultipartRequest multi = new MultipartRequest(request, m_FileStorePath, m_maxPostSize);
        Enumeration files = multi.getFileNames();
        this.m_strFileName = new ArrayList<String>();
        while (files.hasMoreElements()) {
            String name = (String) files.nextElement();
            String filename = multi.getFilesystemName(name);
            // String type = multi.getContentType(name);
            File f = multi.getFile(name);
            if (f != null && f.length() > 0) {
                // System.out.println("length:"+f.length());
                this.m_strFileName.add(m_FileStorePath + filename);
            }
        }
    }

}