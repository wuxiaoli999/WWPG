package com.svv.dms.web.util;

import java.security.MessageDigest;

public class MD5{
	
	public final static String encode(String s) { 
	  char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	  'a', 'b', 'c', 'd', 'e', 'f' }; 
	  try { 
	   byte[] strTemp = s.getBytes(); 
	   // 使用MD5创建MessageDigest对象
	   MessageDigest mdTemp = MessageDigest.getInstance("MD5"); 
	   mdTemp.update(strTemp); 
	   byte[] md = mdTemp.digest(); 
	   int j = md.length; 
	   char str[] = new char[j * 2]; 
	   int k = 0; 
	   for (int i = 0; i < j; i++) { 
	    byte b = md[i]; 
	    // System.out.println((int)b);
	    // 将没个数(int)b进行双字节加密
	    str[k++] = hexDigits[b >> 4 & 0xf]; 
	    str[k++] = hexDigits[b & 0xf]; 
	   } 
	   return new String(str); 
	  } catch (Exception e) {return null;} 
	} 
	
	// 测试
	public static void main(String[] args) {
      System.out.println("888的MD5加密后："+MD5.encode("888"));
      System.out.println("999："+MD5.encode("999"));
      System.out.println("JOJO999的MD5加密后："+MD5.encode("JOJO999"));
      System.out.println("JOJOO999的MD5加密后："+MD5.encode("JOJOO999"));
      
		String s = "AG_User_T01`AG会"
				+ "员（01）`超级管理员`400000`802001`1`0`0`0`0`0``-1`1`, 38^39^40^41^42^43^44^45^46^47^48^49^50^51^52^53^54^55^56^57^58^59`c1^c2^c3^c4^c5^c6^c7^c8^c9^c10^c11^c12^c"
				+ "13^c14^c15^c16^c17^c18^c19^c20^c21^c22`AGID^爱仕号^手机号^昵称^性别^年龄^城市^省^国家^地址^头像^语言^QQ^email^facebook^openid^首次登录日期^首次注册日期^上次登录"
				+ "日期^有效状态^个性签名^备注`VARCHAR2(30) DEFAULT ^VARCHAR2(30) DEFAULT ^VARCHAR2(30) DEFAULT ^VARCHAR2(30) DEFAULT ^VARCHAR2(10) DEFAULT ^NUMBER(3,0) DEFAULT ^V"
				+ "ARCHAR2(20) DEFAULT ^VARCHAR2(30) DEFAULT ^VARCHAR2(20) DEFAULT ^VARCHAR2(50) DEFAULT ^VARCHAR2(200) DEFAULT ^VARCHAR2(30) DEFAULT ^VARCHAR2(30) DEFAULT ^VARCHA"
				+ "R2(30) DEFAULT ^VARCHAR2(20) DEFAULT ^VARCHAR2(50) DEFAULT ^DATE DEFAULT sysdate^DATE DEFAULT sysdate^DATE^NUMBER(6,0) DEFAULT ^VARCHAR2(30) DEFAULT ^VARCHAR2(1"
				+ "00) DEFAULT `202002^202002^202002^202002^202002^202001^202002^202002^202002^202002^202008^202002^202002^202002^202002^202002^202006^202006^202006^202001^202002^"
				+ "202002`30^30^30^30^10^3^20^30^20^50^200^30^30^30^20^50^20^20^20^6^30^100`0^0^0^0^0^0^0^0^0^0^0^0^0^0^0^0^0^0^0^0^0^0`0^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^0^0^1^1^1^1"
				+ "`^^^^^^^^^^^^^^^^^^^^^`802001^802001^802001^802001^802001^802001^802001^802001^802001^802001^802001^802001^802001^802001^802001^802001^802001^802001^802001^8020"
				+ "01^802001^802001`1^2^3^4^5^6^7^8^9^10^11^12^13^14^15^16^17^18^19^20^21^22`1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1`30^30^30^30^10^3^20^50^200^30^200^30^30^30"
				+ "^20^50^20^20^20^6^30^100`1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1`1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1^1`";
		System.out.println(s.getBytes().length);
      
	} 
}
