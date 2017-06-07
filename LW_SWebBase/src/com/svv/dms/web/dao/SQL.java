package com.svv.dms.web.dao;


public class SQL extends SQL_0{
	
	public static void main(String[] args){
		String s = "[3].R[3]";
		if(s.indexOf(".")<0){
			System.out.println("111");
		}else{
			System.out.println("0" + s.indexOf("."));
		}
	}
}