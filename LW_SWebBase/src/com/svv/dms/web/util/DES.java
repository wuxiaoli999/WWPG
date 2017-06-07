package com.svv.dms.web.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DES {
	private static final String KEY = "_LSHJGV@";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;
	
	/**
	 * 将byte数组转换为表示16进制值的字符串，如：byte[]{8,18}转换为：0813，和public static hexStr2ByteArr()
	 */
    public static String byte2hex(byte[] b) { // 一个字节的数，
       int blen = b.length;
       StringBuffer sb = new StringBuffer(blen * 2);
        String tmp = "";
        for (int n = 0; n < blen; n++) {
            // 整数转成十六进制表示
            tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
            	sb.append("0");
            }
            sb.append(tmp);
        }
        tmp = null;
        return sb.toString().toUpperCase(); // 转成大写
    }

    /**
     * 字符串转java字节码
     * @param b
     * @return
     */
    public static byte[] hex2byte(String s) {
    	byte[] b;
		try {
			b = s.getBytes("UTF-8");
	    	int blen = b.length;
	        if ((blen % 2) != 0) {
	            throw new IllegalArgumentException("长度不是偶数");
	        }
	        byte[] b2 = new byte[blen / 2];
	        for (int n = 0; n < blen; n += 2) {
	            String item = new String(b, n, 2);
	            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节

	            b2[n / 2] = (byte) Integer.parseInt(item, 16);
	        }
	        b = null;
	        return b2;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    /**
     * 指定密钥构造方法
     * @param s
     * @return
     */
    public DES(String newKey) {
    	Security.addProvider(new com.sun.crypto.provider.SunJCE());
    	Key key;
		try {
			key = getKey(newKey.getBytes("UTF-8"));
	    	this.encryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
	    	this.encryptCipher.init(Cipher.ENCRYPT_MODE, key);
	    	
	    	this.decryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
	    	this.decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public DES(){
    	Security.addProvider(new com.sun.crypto.provider.SunJCE());
    	Key key;
		try {
			key = getKey(KEY.getBytes("UTF-8"));
	    	this.encryptCipher = Cipher.getInstance("DES");
	    	this.encryptCipher.init(Cipher.ENCRYPT_MODE, key);
	    	
	    	this.decryptCipher = Cipher.getInstance("DES");
	    	this.decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8为，不足8位时后面补0，超出8位只取前8位
     * @param s
     * @return
     */
    private Key getKey(byte[] arr) throws Exception {
    	byte[] arrB = new byte[8];
    	
    	for(int i=0;i<arr.length && i<arrB.length; i++){
    		arrB[i] = arr[i];
    	}
    	Key key = new SecretKeySpec(arrB, "DES");
    	return key;
    }
    
    /**
     * 加密字节数组
     * @param s
     * @return
     */
    public byte[] encrypt(byte[] src) throws Exception {
    	return this.encryptCipher.doFinal(src);
    }

    /**
     * 加密字符串
     * @param s
     * @return
     */
    public String encrypt(String src) {
    	try {
			return byte2hex(encrypt(src.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    /**
     * 解密字节数组
     * @param s
     * @return
     */
    public byte[] decrypt(byte[] src) throws Exception {
    	return this.decryptCipher.doFinal(src);
    }
    /**
     * 解密字符串
     * @param s
     * @return
     */
    public String decrypt(String src) {
    	try {
        	return new String(decrypt(hex2byte(src)), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
	public static String md5(String s){		
		MessageDigest messageDigest;		
		String newContent = "";		
		try{
			byte[] bytes = s.getBytes("UTF-8");
			messageDigest= MessageDigest.getInstance("md5");
			messageDigest.reset();							
			messageDigest.update(bytes);					
			byte[] hash=messageDigest.digest();				
			
			for(int i=0;i<hash.length;i++){
				int v = hash[i] & 0xFF;						
				if (v < 16) newContent += "0";
				newContent += Integer.toString(v, 16).toLowerCase();
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return newContent;
	}

	// 测试
	public static void main(String[] args) throws Exception {
		DES des = new DES();
		System.out.println("xdg999的md5加密后："+DES.md5("xdg999"));
		System.out.println("LOLO999的md5加密后："+MD5.encode("LOLO999"));
		System.out.println("LOLOO999的md5加密后："+MD5.encode("LOLOO999"));
		System.out.println("DAISUKI9I9的md5加密后："+MD5.encode("DAISUKI9I9"));
		System.out.println("DAISUKI9I99的md5加密后："+MD5.encode("DAISUKI9I99"));
		System.out.println("yuanshi的md5加密后："+DES.md5("yuanshi"));
		System.out.println("YUANSHI的md5加密后："+DES.md5("YUANSHI"));
		System.out.println("8888的md5加密后："+DES.md5("8888"));
//        System.out.println("c1的DES加密后："+des.encrypt("SYSTEM_NAME"));
//        System.out.println("c2的DES加密后："+des.encrypt("RTP_TICK_INTERVAL"));
//        System.out.println("c3的DES加密后："+des.encrypt("DEF_AREA_ID"));
//        System.out.println("c4的DES加密后："+des.encrypt("DEF_NEXT_PAGE_AUTO_TIME"));
//        System.out.println("c5的DES加密后："+des.encrypt("DEF_CLOSE_AUTO_TIME"));
//        System.out.println("c6的DES加密后："+des.encrypt("DEF_USER_PASSWORD"));
//        System.out.println("c7的DES加密后："+des.encrypt("DBUSER"));
        System.out.println("v1的DES加密后："+des.encrypt("兵棋在线"));
        System.out.println("v1的DES加密后："+des.encrypt("原食在线"));
        System.out.println("v1的DES加密后："+des.encrypt("中国动漫在线"));
        System.out.println("v1的DES加密后："+des.encrypt("MEDI在线"));
        System.out.println("v1的DES加密后："+des.encrypt("装修在线"));
        System.out.println("v1的DES加密后："+des.encrypt("首信网优在线"));
        System.out.println("v1的DES加密后："+des.encrypt("石开电力在线"));
        System.out.println("v1的DES加密后："+des.encrypt("网联在线"));
        System.out.println("v1的DES加密后："+des.encrypt("ACE Elite"));
        System.out.println("v1的DES加密后："+des.encrypt("新道格旋转机械"));
//        System.out.println("v1的DES加密后："+des.encrypt("微联在线"));
//        System.out.println("v1的DES加密后："+des.encrypt("ACEGEAR ONLINE"));
//        System.out.println("v1的DES加密后："+des.encrypt("浙江华亿信息管理平台"));
//        System.out.println("v1的DES加密后："+des.encrypt("浙江华亿S-EDI管理平台"));
//        System.out.println("v1的DES加密后："+des.encrypt("中国兵棋部"));
//        System.out.println("v2 v4 v5的DES加密后："+des.encrypt("0"));
//        System.out.println("v3的DES加密后："+des.encrypt("2000"));
//        System.out.println("v6的DES加密后："+des.encrypt("123456"));
        System.out.println("v7的DES加密后："+des.encrypt("lwdms"));
        System.out.println("v7的DES加密后："+des.encrypt("ofooddms"));
        System.out.println("v7的DES加密后："+des.encrypt("padmdms"));
        System.out.println("v7的DES加密后："+des.encrypt("pbmedi"));
        System.out.println("v7的DES加密后："+des.encrypt("yadeco"));
        System.out.println("v7的DES加密后："+des.encrypt("ybcapnet"));
        System.out.println("v7的DES加密后："+des.encrypt("ycskdl"));
        System.out.println("v7的DES加密后："+des.encrypt("ydpubnet"));
        System.out.println("v7的DES加密后："+des.encrypt("pcace"));
        System.out.println("v7的DES加密后："+des.encrypt("pdxdgphm"));
//        System.out.println("v7的DES加密后："+des.encrypt("wxdms"));
//        System.out.println("v7的DES加密后："+des.encrypt("acedms"));
//        System.out.println("v7的DES加密后："+des.encrypt("hidms"));
//        System.out.println("v7的DES加密后："+des.encrypt("jxcdms"));
//        System.out.println("v7的DES加密后："+des.encrypt("bqdms"));
//        System.out.println("m1的DES加密后："+des.encrypt("系统名称"));
//        System.out.println("m2的DES加密后："+des.encrypt("RTP连接间隔，单位：毫秒"));
//        System.out.println("m3的DES加密后："+des.encrypt("系统默认属地"));
//        System.out.println("m4的DES加密后："+des.encrypt("页面自动转向时间(秒)"));
//        System.out.println("m5的DES加密后："+des.encrypt("消息自动关闭时间(秒)"));
//        System.out.println("m6的DES加密后："+des.encrypt("帐户默认密码"));
//        System.out.println("m7的DES加密后："+des.encrypt("数据库用户"));
//        System.out.println("[C7450C63D3E1D83AA84B876E315078D4]的DES解密后："+des.decrypt("DBA1E522E08C23F76A023AA06B09B43BDF5423D6BEC4C8A343D277676C3BC278CFDD68D57906D7838A25B2A25CB0C881E549CDE7025A5A13DA525AD3D0991E953CABF9B0009647E3FBA5793991E48D6B9E32BD8615D64B5BB1D3D6251BCC7D076DCCC4B78E0A5497")); 
	} 
}
