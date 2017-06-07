package com.svv.dms.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class FileDES {
    public static final String KEY = "_LSHJGV@";
    
    /** 
     * 文件file进行加密 
     * @param fileUrl 文件路径 
     * @param key 密码 
     * @throws Exception 
     */ 
    public static void encrypt(String fileUrl) throws Exception {
    	String PS = File.separator;
        File file = new File(fileUrl);  
        String path = file.getPath();  
        if(!file.exists()){  
            return;  
        }  
        int index = path.lastIndexOf(PS);  
        String destFile = path.substring(0, index)+PS+"abc";  
        File dest = new File(destFile);  
        InputStream in = new FileInputStream(fileUrl);  
        OutputStream out = new FileOutputStream(destFile);  
        byte[] buffer = new byte[1024];  
        int r;  
        byte[] buffer2=new byte[1024];  
        while (( r= in.read(buffer)) > 0) {  
            for(int i=0;i<r;i++)  
            {  
                byte b=buffer[i];  
                buffer2[i]=b==255?0:++b;  
            }  
            out.write(buffer2, 0, r);  
            out.flush();  
        }  
        in.close();  
        out.close();  
        file.delete();  
        dest.renameTo(new File(fileUrl));  
        appendMethodA(fileUrl, KEY);
        System.out.println("加密成功");  
    }  
   
    /** 
     *  
     * @param fileName 
     * @param content 密钥 
     */ 
    public static void appendMethodA(String fileName, String content) {  
        try {  
            // 打开一个随机访问文件流，按读写方式  
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");  
            // 文件长度，字节数  
            long fileLength = randomFile.length();  
            //将写文件指针移到文件尾。  
            randomFile.seek(fileLength);  
            randomFile.writeBytes(content);  
            randomFile.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * 解密 
     * @param fileUrl 源文件 
     * @param tempUrl 临时文件 
     * @param ketLength 密码长度 
     * @return 
     * @throws Exception 
     */ 
    public static String decrypt(String fileUrl, String tempUrl) throws Exception{  
        File file = new File(fileUrl);  
        if (!file.exists()) {  
            return null;  
        }  
        File dest = new File(tempUrl);  
        if (!dest.getParentFile().exists()) {  
            dest.getParentFile().mkdirs();  
        }  
   
        InputStream is = new FileInputStream(fileUrl);  
        OutputStream out = new FileOutputStream(tempUrl);  
   
        byte[] buffer = new byte[1024];  
        byte[] buffer2=new byte[1024];  
        byte bMax=(byte)255;  
        long size = file.length() - KEY.length();  
        int mod = (int) (size%1024);  
        int div = (int) (size>>10);  
        int count = mod==0?div:(div+1);  
        int k = 1, r;  
        while ((k <= count && ( r = is.read(buffer)) > 0)){  
            if(mod != 0 && k==count){  
                r =  mod;  
            }  
   
            for(int i = 0;i < r;i++){  
                byte b=buffer[i];  
                buffer2[i]=b==0?bMax:--b;  
            }  
            out.write(buffer2, 0, r);
            k++;  
        }  
        out.close();  
        is.close();
        System.out.println("解密成功"); 
        return tempUrl;  
    }  
   
    /** 
     * 判断文件是否加密 
     * @param fileName 
     * @return 
     */ 
    public static String readFileLastByte(String fileName) {  
        File file = new File(fileName);  
        if(!file.exists())return null;  
        StringBuffer str = new StringBuffer();  
        try {  
            // 打开一个随机访问文件流，按读写方式  
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "r");  
            // 文件长度，字节数  
            long fileLength = randomFile.length();  
            //将写文件指针移到文件尾。  
            for(int i = KEY.length() ; i>=1 ; i--){  
                randomFile.seek(fileLength-i);  
                str.append((char)randomFile.read());  
            }  
            randomFile.close();
            System.out.println("readFileLastByte yes");
            return str.toString();  
        } catch (IOException e) {  
            e.printStackTrace();    
        }
        System.out.println("readFileLastByte no");
        return null;  
    }
    
    public static void main(String[] args) throws Exception {  
        String path = "D:/app/";
        if(readFileLastByte(path+"aa.docx")!=null){
        	encrypt(path+"aa.docx");  //加密
        }else{
            decrypt(path+"aa.docx", path+"aa0.docx");//解密
        }
//        decrypt(path+"aa.docx", path+"aa0.docx");//解密
        
    }
}
