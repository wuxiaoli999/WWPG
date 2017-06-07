package com.svv.dms.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
* <p>Title: 周计算类，星期一为一周的开始，星期日为一周的结束</p> 
* <p>Description: 在两年的交接地带还有疑问。</p>
* <p>比如2006-12-29到2009-01-04，属于2008年的最后一周，</p>
* <p>2009-01-05位2009年第一周的开始。</p>
* <p>db2种的week_iso也是这样计算的</p>
* <p>Copyright: Copyright (c) 2006</p> 
* <p>DateTime: 2006-4-11 23:36:39</p>
*
* @author gumpgz
* @version 1.0
*/



public class WeekAndDayUtil {
	
public static String formatDate(Date date,String pattern){
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);		
	}
/**
* 取得当前日期是多少周
* 
* @param date
* @return
*/
public static int getWeekOfYear(Date date) {
Calendar c = new GregorianCalendar();
c.setFirstDayOfWeek(Calendar.MONDAY);
c.setMinimalDaysInFirstWeek(7);
c.setTime (date);

return c.get(Calendar.WEEK_OF_YEAR);
}

/**
* 得到某一年周的总数
* 
* @param year
* @return
*/
public static int getMaxWeekNumOfYear(int year) { 
Calendar c = new GregorianCalendar();
c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

return getWeekOfYear(c.getTime());
}

/**
* 得到某年某周的第一天
* 
* @param year 
* @param week
* @return
*/
public static String getFirstDayOfWeek(int year, int week) {
Calendar c = new GregorianCalendar();
c.set(Calendar.YEAR, year);
c.set (Calendar.MONTH, Calendar.JANUARY);
c.set(Calendar.DATE, 1);

Calendar cal = (GregorianCalendar) c.clone();
cal.add(Calendar.DATE, week * 7);

return getFirstDayOfWeek(cal.getTime ());
}

/**
* 得到某年某周的最后一天
* 
* @param year
* @param week
* @return
*/
public static String getLastDayOfWeek(int year, int week) {
Calendar c = new GregorianCalendar(); 
c.set(Calendar.YEAR, year);
c.set(Calendar.MONTH, Calendar.JANUARY);
c.set(Calendar.DATE, 1);

Calendar cal = (GregorianCalendar) c.clone();
cal.add(Calendar.DATE , week * 7);

return getLastDayOfWeek(cal.getTime());
}

/**
* 取得当前日期所在周的第一天
* 
* @param date
* @return
*/
public static String getFirstDayOfWeek(Date date) { 
Calendar c = new GregorianCalendar();
c.setFirstDayOfWeek(Calendar.MONDAY);
c.setTime(date);
c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
return getDateSub(formatDate(c.getTime(),"yyyy-MM-dd"),2+"");
}

/**
* 取得当前日期所在周的最后一天
* 
* @param date
* @return
*/
public static String getLastDayOfWeek(Date date) {
Calendar c = new GregorianCalendar();
c.setFirstDayOfWeek(Calendar.MONDAY);
c.setTime(date);
c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
return getDateSub(formatDate(c.getTime(),"yyyy-MM-dd"),2+"");
}

public static String getDate(){ 
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd"); 
	Date dd = new Date(); 
	return ft.format(dd); 
	} 
//取得日期相差的天数
public static long getQuot(String time1, String time2){ 
	long quot = 0; 
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd"); 
	try { 
	Date date1 = ft.parse( time1 ); 
	Date date2 = ft.parse( time2 ); 
	quot = date1.getTime() - date2.getTime(); 
	quot = quot/1000/60/60/24; 
	} catch (ParseException e) { 
	e.printStackTrace(); 
	} 
	return quot; 
	}
//取得日期加天数后的日期
public static String getDateSum(String time1,String days){
	long times = 0;
    String time2 = "";
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd"); 
	try { 
	Date date1 = ft.parse( time1 ); 	 
	times = date1.getTime(); 
	times = times+Long.parseLong(days)*1000*60*60*24; 
	Date ao = new Date();
	ao.setTime(times);
	time2 = ft.format(ao);
	} catch (ParseException e) { 
	e.printStackTrace(); 
	} 
	return time2; 
}
//取得日期减天数后的日期
public static String getDateSub(String time1,String days){
	long times = 0;
    String time2 = "";
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd"); 
	try { 
	Date date1 = ft.parse( time1 ); 	 
	times = date1.getTime(); 
	times = times-Long.parseLong(days)*1000*60*60*24; 
	Date ao = new Date();
	ao.setTime(times);
	time2 = ft.format(ao);
	} catch (ParseException e) { 
	e.printStackTrace(); 
	} 
	return time2; 
}
}
 