package com.svv.dms.web.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hpsf.Property;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.struts.util.LabelValueBean;

import com.gs.db.dao.DaoUtil;
import com.gs.db.database.BizDBResult;
import com.gs.db.database.JDBCDataManager;
import com.gs.db.util.DBConstants;
import com.gs.db.util.DBUtil;
import com.gs.db.util.DateUtil;
import com.jeesoon.struts.beanaction.ActionContext;
import com.svv.dms.web.Constants;
import com.svv.dms.web.dao.SQL;
import com.svv.dms.web.entity.S_User;

public class HIUtil {

	public static S_User getUserSession() {
		return (S_User) ActionContext.getActionContext().getSessionMap().get(Constants.SESSION_ATTRIBUTE_SUSER);
	}
	
	public static String[] split(String str, String split){
		String p = split;
		if("$".equals(p)) p = "\\" + p;
		if("|".equals(p)) p = "\\" + p;
		String s = str.replaceAll(p, p+"=R_Z_R=");
		String[] ss = s.split(p);
		int len = ss.length;
		String[] rtn = new String[len];
		for(int i=0; i<len; i++){
			rtn[i] = ss[i].replaceAll("=R_Z_R=", "");
		}
		return rtn;
	}
	
	/**
	* Reallocates an array with a new size, and copies the contents
	* of the old array to the new array.
	* @param oldArray  the old array, to be reallocated.
	* @param newSize   the new array size.
	* @return          A new array with the same contents.
	*/
	@SuppressWarnings("rawtypes")
	public static Object resizeArray(Object[] oldArray, int newSize) {
	   int oldSize = java.lang.reflect.Array.getLength(oldArray);
	   Class elementType = oldArray.getClass().getComponentType();
	   Object newArray = java.lang.reflect.Array.newInstance(
	         elementType,newSize);
	   int preserveLength = Math.min(oldSize,newSize);
	   if (preserveLength > 0)
	      System.arraycopy (oldArray,0,newArray,0,preserveLength);
	   return newArray; 
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getList(Class c, List<Object> rowlist) {
		List<Object> rtn = null;
		try {
			if (rowlist != null && rowlist.size() > 0) {
				/*
				 * String c = new Object(){ public String getClassName(){ String
				 * className = this.getClass().getName(); return
				 * className.substring(0, className.lastIndexOf('$')); }
				 * }.getClassName();
				 * System.out.println("[AbstractEntity.getList] className" + c);
				 */

				rtn = new ArrayList<Object>();
				for (Object row : rowlist) {
					rtn.add(c.getConstructor(new Class[] { Object.class }).newInstance(row));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	public static boolean isOracle() {
		return JDBCDataManager.SERVER_JDBC_POOL_TYPE.equals(DBConstants.JDBC_POOL_TYPE_ORACLE);
	}

	public static boolean isMysql() {
		return JDBCDataManager.SERVER_JDBC_POOL_TYPE.equals(DBConstants.JDBC_POOL_TYPE_MSQL);
	}

	public static String getCssStyle(HttpSession session) {
		try {
			S_User me = (S_User) session.getAttribute(Constants.SESSION_ATTRIBUTE_SUSER);
			if (me == null)
				return "blue";
			return me == null ? "blue" : me.getCssStyle();
		} catch (Exception e) {
			return "blue";
		}
	}

	public static String getRandomString(int length) {
		// String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String min(String a, String min) {
		double a1 = 0;
		double b1 = 0;
		try {
			a1 = Double.parseDouble(a);
		} catch (Exception e) {
			a1 = 0;
		}
		try {
			b1 = Double.parseDouble(min);
		} catch (Exception e) {
			b1 = 0;
		}
		return (a1 < b1) ? a : min;
	}

	public static String doubleAdd(String a, String b, int dot) {
		double a1 = 0;
		double b1 = 0;
		try {
			a1 = Double.parseDouble(a);
		} catch (Exception e) {
			a1 = 0;
		}
		try {
			b1 = Double.parseDouble(b);
		} catch (Exception e) {
			b1 = 0;
		}
		return HIUtil.NumFormat(a1 + b1, dot);
	}

	public static String doubleAdd(String a, String b, String c, int dot) {
		double a1 = 0;
		double b1 = 0;
		double c1 = 0;
		try {
			a1 = Double.parseDouble(a);
		} catch (Exception e) {
			a1 = 0;
		}
		try {
			b1 = Double.parseDouble(b);
		} catch (Exception e) {
			b1 = 0;
		}
		try {
			c1 = Double.parseDouble(c);
		} catch (Exception e) {
			c1 = 0;
		}
		return HIUtil.NumFormat(a1 + b1 + c1, dot);
	}

	public static String doubleMultiply(String a, String b, int dot) {
		double a1 = 0;
		double b1 = 0;
		try {
			a1 = Double.parseDouble(a);
		} catch (Exception e) {
			a1 = 0;
		}
		try {
			b1 = Double.parseDouble(b);
		} catch (Exception e) {
			b1 = 0;
		}
		return HIUtil.NumFormat(a1 * b1, dot);
	}

	public static String doubleMultiply(String a, String b, String c, int dot) {
		double a1 = 0;
		double b1 = 0;
		double c1 = 0;
		try {
			a1 = Double.parseDouble(a);
		} catch (Exception e) {
			a1 = 0;
		}
		try {
			b1 = Double.parseDouble(b);
		} catch (Exception e) {
			b1 = 0;
		}
		try {
			c1 = Double.parseDouble(c);
		} catch (Exception e) {
			c1 = 0;
		}
		return HIUtil.NumFormat(a1 * b1 * c1, dot);
	}

	public static String toSPParamSplitString(Object[] o) {
		return StringUtils.join(o, Constants.SPLITER) + Constants.SPLITER;
	}

	public static List<Object> dbQuery(String sql) {
		try {
			System.out.println("[HIUtil.dbQuery] " + sql);
			BizDBResult br = DaoUtil.dbQuery("sql", sql);
			return br.getRecordset();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean dbExe(String sql) {
		try {
			System.out.println("[HIUtil.dbExe] " + sql);
			BizDBResult br = DaoUtil.dbExe("sql", sql);
			if(!br.getResult()) System.out.println(br.getInfo());
			return br.getResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int getSqlIntValue(String sql, String colName) {
		System.out.println("[HIUtil.getSqlIntValue] " + sql);
		try{
			return Integer.parseInt(HIUtil.getSqlValue(sql, colName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public static String getSqlValue(String sql, String colName) {
		System.out.println("[HIUtil.getSqlValue] " + sql);
		BizDBResult br = DaoUtil.dbQuery("sql", sql);
		if (br.getResult() && br.getRecordset() != null && br.getRecordset().size() > 0) {
			return DBUtil.getDBString(br.getRecordset().get(0), colName);
		}
		return "";
	}

	public static List<Object> dbQuery(String sp, Object[] params) {
		String sql = "no sql";
		// System.out.println("HIUtil.dbQuery " +
		// HIUtil.getCurrentDate("hh:mm:ss") + " 000000000000 "+sp);
		try {
			sql = (String) SQL.class.getField(sp).get(new SQL());
			BizDBResult br = DaoUtil.dbQuery(sp, String.format(sql, params));
			// System.out.println("HIUtil.dbQuery " +
			// HIUtil.getCurrentDate("hh:mm:ss") +
			// " 999999999999999999999999999 "+sp);
			return br.getRecordset();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getCellString(XSSFCell cell) {
		return getCellString(cell, 0);
	}

	public static String getCellString(XSSFCell cell, int dot) {
		String rtn = "";
		if (cell != null) {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				rtn = HIUtil.NumFormat(cell.getNumericCellValue(), dot);
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				rtn = cell.getStringCellValue().trim();
			}
		}
		if (rtn.length() == 0)
			rtn = HIUtil.NumFormat(0, dot);
		return rtn;
	}

	public static String parseSplitString(String inputStr, boolean hasEnd) {
		String rtn = "";
		if (!HIUtil.isEmpty(inputStr))
			rtn = inputStr.substring(1, hasEnd ? inputStr.length() : inputStr.length() - 1).replaceAll(Constants.SPLITER2S, Constants.SPLITER);
		return rtn;
	}

	public static String getAjaxParamter(HttpServletRequest request, String name, String def) {
		try {
			if (request.getParameter(name) == null)
				return def;
			String param = new String(request.getParameter(name).getBytes("ISO8859_1"), "gb2312");
			return isEmpty(param) ? def : param;
		} catch (Exception e) {
			return def;
		}
	}

	public static String getAttribute(HttpServletRequest request, String name, String def) {
		try {
			if (request.getAttribute(name) == null)
				return def;
			String param = (String) request.getAttribute(name);
			return isEmpty(param) ? def : param;
		} catch (Exception e) {
			return def;
		}
	}

	public static String getParameter(HttpServletRequest request, String name, String def) {
		try {
			if (request.getParameter(name) == null)
				return def;
			String param = (String) request.getParameter(name);
			return isEmpty(param) ? def : param;
		} catch (Exception e) {
			return def;
		}
	}

	public static int getParameter(HttpServletRequest request, String name, int def) {
		try {
			if (request.getParameter(name) == null)
				return def;
			String param = (String) request.getParameter(name);
			return isEmpty(param) ? def : Integer.parseInt(param);
		} catch (Exception e) {
			return def;
		}
	}

	@SuppressWarnings("rawtypes")
	public static List<LabelValueBean> getLabelList(Map map, boolean hasAll) {
		List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
		if (hasAll)
			rtn.add(new LabelValueBean(Constants.DEF_SELECT_TEXT, ""));
		rtn.addAll(HIUtil.getLabelList(map));
		return Collections.unmodifiableList(rtn);
	}

	public static List<LabelValueBean> getLabelList(ArrayList<Property> alist, boolean hasAll) {
		List<LabelValueBean> rtn = new ArrayList<LabelValueBean>();
		if (hasAll)
			rtn.add(new LabelValueBean(Constants.DEF_SELECT_TEXT, ""));
		rtn.addAll(HIUtil.getLabelList(alist));
		return Collections.unmodifiableList(rtn);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<LabelValueBean> getLabelList(Map map) {
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		if (map != null && map.size() > 0) {
			Set<Object> keySet = map.keySet();
			for (Object key : keySet) {
				list.add(new LabelValueBean((String) map.get(key), key.toString()));
			}
		}
		return Collections.unmodifiableList(list);
	}

	public static List<LabelValueBean> getLabelList(List<String[]> map) {
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		if (map != null && map.size() > 0) {
			for (String[] ss : map) {
				list.add(new LabelValueBean(ss[0], ss[1]));
			}
		}
		return Collections.unmodifiableList(list);
	}

	public static List<LabelValueBean> getLabelList(ArrayList<Property> alist) {
		List<LabelValueBean> list = new ArrayList<LabelValueBean>();
		if (alist != null && alist.size() > 0) {
			for (Property o : alist) {
				list.add(new LabelValueBean((String) o.getValue(), o.getID() + ""));
			}
		}
		return Collections.unmodifiableList(list);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String genHtmlSelect(boolean showID, String selectName, Map map, String def, boolean nullFlag, String onchange) {
		StringBuffer rtn = new StringBuffer("<select class=sel_text name=").append(selectName).append(map != null && map.size()==1 ? " onclick=\"" : " onchange=\"").append(onchange).append("\">");
		if (map != null && map.size() > 0) {
			Set<Object> keySet = map.keySet();
			if (nullFlag)
				rtn = rtn.append("<option value=\"\"></option>");
			for (Object key : keySet) {
				String selected = String.valueOf(key).equals(def) ? " selected" : "";
				rtn = rtn.append("<option value=\"").append(key).append("\"").append(selected).append(">").append(showID?key+" - " : "").append(map.get(key)).append("</option>");
			}
		}
		rtn.append("</select>");
		return rtn.toString();
	}

	public static String genHtmlSelect(boolean showID, String selectName, List<HIProperty> list, String def, boolean nullFlag, String onchange) {
		StringBuffer rtn = new StringBuffer("<select class=sel_text name=").append(selectName).append(list != null && list.size()==1 ? " onclick=\"" : " onchange=\"").append(onchange).append("\">");
		if (list != null && list.size() > 0) {
			if (nullFlag)
				rtn = rtn.append("<option value=\"\"></option>");
			for (HIProperty p: list) {
				String selected = String.valueOf(p.getKey()).equals(def) ? " selected" : "";
				rtn = rtn.append("<option value=\"").append(p.getKey()).append("\"").append(selected).append(">").append(showID?p.getKey()+" - " : "").append(p.getValue()).append("</option>");
			}
		}
		rtn.append("</select>");
		return rtn.toString();
	}

	public static String genHtmlOption(List<String[]> map, Object def) {
		StringBuffer rtn = new StringBuffer("");
		if (map != null && map.size() > 0) {
			for (String[] o : map) {
				String selected = String.valueOf(o[0]).equals(String.valueOf(def)) ? " selected" : "";
				rtn = rtn.append("<option value=\"").append(o[0]).append("\"").append(selected).append(">").append(o[1]).append("</option>");
			}
		}
		return rtn.toString();
	}

	public static String genHtmlCheckbox(String[][] map, String name, int lineNum) {
		StringBuffer rtn = new StringBuffer("");
		if (map != null && map.length > 0) {
			int maxlineNum = map.length;
			if (lineNum > 0)
				maxlineNum = lineNum;
			rtn.append("<table class=tbBorder cellspacing=1><tr>");
			int i = 0;
			for (i = 0; i < map.length; i++) {
				rtn = rtn.append("<td class=tdBody><input type=checkbox class=checkbox name=\"").append(name).append("\" value=\"").append(map[i][0])
						.append("\" onclick=\"onSelect(this)\"><label>").append(map[i][1]).append("</label></td>")
						.append(((i + 1) % maxlineNum == 0) ? "</tr><tr>" : "");
			}
			if (i % maxlineNum > 0)
				rtn.append("<td class=tdBody colspan=" + (maxlineNum - i % maxlineNum) + "><br></td>");
			rtn.append("</tr></table>");
		}
		return rtn.toString();
	}

	public static boolean isNull(Object o) {
		return o == null ? true : false;
	}

	public static Object isNull(Object o, Object def) {
		return o == null ? def : o;
	}

	public static boolean isEmpty(String o) {
		return o == null || o.length() == 0 ? true : false;
	}

	public static String isEmpty(String o, String def) {
		return o == null || o.length() == 0 ? def : o;
	}

	public static String getCurrentDate() {
		return getCurrentDate("yyyy-MM-dd");
	}

	public static String getCurrentStartDate() {
		return getCurrentDate("yyyy-MM-dd") + " 00:00";
	}

	public static String getCurrentEndDate() {
		return getCurrentDate("yyyy-MM-dd") + " 23:59";
	}

	public static String getCurrentDate(String format) {
		return (new SimpleDateFormat(format)).format(new Date());
	}

	public static Date addDate(int addDay) { 
		Calendar c = new GregorianCalendar();
		//System.out.println(DateUtil.formatDate(c.getTime(), "yyyyMMdd HHmmss"));
		c.add(Calendar.DAY_OF_MONTH, addDay); 
		//System.out.println(DateUtil.formatDate(c.getTime(), "yyyyMMdd HHmmss"));
		return c.getTime();
	}
	public static Date addDate(int add, int CalendarField) { 
		Calendar c = new GregorianCalendar();
		//System.out.println(DateUtil.formatDate(c.getTime(), "yyyyMMdd HHmmss"));
		c.add(CalendarField, add); 
		//System.out.println(DateUtil.formatDate(c.getTime(), "yyyyMMdd HHmmss"));
		return c.getTime();
	}
	
	public static String addDate(int addDay, String format) { 
		Calendar c = new GregorianCalendar();
		//System.out.println(DateUtil.formatDate(c.getTime(), "yyyyMMdd HHmmss"));
		c.add(Calendar.DAY_OF_MONTH, addDay); 
		//System.out.println(DateUtil.formatDate(c.getTime(), "yyyyMMdd HHmmss"));
		return DateUtil.formatDate(c.getTime(), format);
	}
	
	public static String addDate(int add, int CalendarField,  String format) { 
		Calendar c = new GregorianCalendar();
		//System.out.println(DateUtil.formatDate(c.getTime(), "yyyyMMdd HHmmss"));
		c.add(CalendarField, add); 
		//System.out.println(DateUtil.formatDate(c.getTime(), "yyyyMMdd HHmmss"));
		return DateUtil.formatDate(c.getTime(), format);
	}

	public static Date addMinute(int addMinute) {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.MINUTE, addMinute);
		return c.getTime();
	}

	public static String dSingleQuote(String str){
		if(str==null) return null;
		return str.replaceAll("&#39;", "'");
	}
	public static String eSingleQuote(String str){
		if(str==null) return null;
		return str.replaceAll("'", "&#39;");
	}
	public static String htmlDecode(String str) {
		if(str==null) return null;
		String s = str;
		if (toString(str) == "")
			return "";
		s = s.replaceAll("&lt;", "<");
		s = s.replaceAll("&gt;", ">");
		s = s.replaceAll("&amp;", "&");
		s = s.replaceAll("&nbsp;", " ");
		s = s.replaceAll("&#39;", "'");
		s = s.replaceAll("&quot;", "\"");
		s = s.replace("\n", "<br>");
		return s;
	}
	public static String htmlEncode(String str) {
		if(str==null) return null;
		String s = str;
		if (toString(str) == "")
			return "";
		s = s.replaceAll("<", "&lt;");
		s = s.replaceAll(">", "&gt;");
		s = s.replaceAll("&", "&amp;");
		s = s.replaceAll(" ", "&nbsp;");
		s = s.replaceAll("'", "&#39;");
		s = s.replaceAll("\"", "&quot;");
		s = s.replace("<br>", "\n");
		return s;
	}

	public static String toHtmlStr(String str) {
		if (toString(str) == "") return "";
		str = str.replaceAll(System.getProperty("line.separator"), "\\\\n");
		str = str.replaceAll("\\\\n", "<br>");
		str = str.replaceAll(" ", "&nbsp;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}

	public static String toDBStr(String str) {
		if (toString(str) == "") return "";
		str = str.replaceAll(System.getProperty("line.separator"), "\n");
		str = str.replaceAll("'", "''");
		return str;
	}

	public static String rTrim(String str, String badS) {
		if (toString(str) == "")
			return "";
		while (str.endsWith(badS)) {
			str = str.substring(0, str.length() - badS.length());
		}
		return str;
	}

	public static void print(String o) {
		System.out.println(o == null ? "null" : o);
	}

	public static String toString(Object o) {
		return o == null || String.valueOf(o).equalsIgnoreCase("null") ? "" : String.valueOf(o);
	}

	public static String toString(Long o) {
		return o == null || o == -1 ? "" : String.valueOf(o);
	}

	public static String lPad(String s, String f0, int len) {
		String str = "";
		for (int i = 0; i < len; i++) {
			str += f0;
		}
		str = str.concat(s);
		return str.substring(str.length() - len);
	}

	public static String lPad(String s, String f) {
		String str = f.concat(s);
		return str.substring(str.length() - f.length());
	}

	public static String NumFormat(String dbl, int numFig) {
		if (HIUtil.isEmpty(dbl))
			return "";
		String rtn = "";
		try {
			rtn = NumFormat(Double.parseDouble(dbl), numFig);
		} catch (Exception e) {
			e.printStackTrace();
			rtn = "";
		}
		return rtn;
	}

	public static String NumFormat(double dbl, int numFig) {
		String rtn = "";
		try {
			String format = "###0";
			if (numFig > 0) {
				format = format.concat(".");
				for (int i = 0; i < numFig; i++) {
					format = format.concat("0");
				}
			}
			DecimalFormat f = new DecimalFormat(format);
			rtn = f.format(dbl);
		} catch (Exception e) {
			e.printStackTrace();
			rtn = "";
		}
		return rtn;
	}

	public static String bSubstring(String s, int length) {
		try{
			byte[] bytes = s.getBytes("Unicode");
			int n = 0; // 表示当前的字节数
			int i = 2; // 前两个字节是标志位，bytes[0] = -2，bytes[1] = -1。所以从第3位开始截取。
			for (; i < bytes.length && n < length; i++) {
				// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
				if (i % 2 == 1) {
					n++; // 在UCS2第二个字节时n加1
				} else {
					// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
					if (bytes[i] != 0) {
						n++;
					}
				}
			}
			// 如果i为奇数时，处理成偶数
			if (i % 2 == 1){
				// 该UCS2字符是汉字时，去掉这个截一半的汉字
				if (bytes[i - 1] != 0)
					i = i - 1;
				// 该UCS2字符是字母或数字，则保留该字符
				else
					i = i + 1;
			}
			return new String(bytes, 0, i, "Unicode");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] gbk2utf8(String chenese) {

		char c[] = chenese.toCharArray();
		byte[] fullByte = new byte[3 * c.length];
		for (int i = 0; i < c.length; i++) {
			int m = (int) c[i];
			String word = Integer.toBinaryString(m);

			StringBuffer sb = new StringBuffer();
			int len = 16 - word.length();
			// 补零
			for (int j = 0; j < len; j++) {
				sb.append("0");
			}
			sb.append(word);
			sb.insert(0, "1110");
			sb.insert(8, "10");
			sb.insert(16, "10");

			String s1 = sb.substring(0, 8);
			String s2 = sb.substring(8, 16);
			String s3 = sb.substring(16);

			byte b0 = Integer.valueOf(s1, 2).byteValue();
			byte b1 = Integer.valueOf(s2, 2).byteValue();
			byte b2 = Integer.valueOf(s3, 2).byteValue();
			byte[] bf = new byte[3];
			bf[0] = b0;
			fullByte[i * 3] = bf[0];
			bf[1] = b1;
			fullByte[i * 3 + 1] = bf[1];
			bf[2] = b2;
			fullByte[i * 3 + 2] = bf[2];

		}
		return fullByte;
	}

    public static boolean resizeImageFile(String realSrcPath, String realDestPath, int destImgW, int destImgH, boolean proportion) {
        try {  
            File f2 = new File(realSrcPath);
            BufferedImage bi2 = ImageIO.read(f2);  
            int newWidth;
            int newHeight;
            // 判断是否是等比缩放
            if (proportion == true) {
            	// 为等比缩放计算输出的图片宽度及高度
            	double rate1 = ((double) bi2.getWidth(null)) / (double) destImgW + 0.1;
            	double rate2 = ((double) bi2.getHeight(null)) / (double) destImgH + 0.1;
            	// 根据缩放比率大的进行缩放控制
            	double rate = rate1 < rate2 ? rate1 : rate2;
            	newWidth = (int) (((double) bi2.getWidth(null)) / rate);
            	newHeight = (int) (((double) bi2.getHeight(null)) / rate);
            } else {
            	newWidth = destImgW; // 输出的图片宽度
            	newHeight = destImgH; // 输出的图片高度
            }
            BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);  
            Graphics2D g2d = to.createGraphics();
            to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth,newHeight, Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = to.createGraphics();
            
            Image from = bi2.getScaledInstance(newWidth, newHeight, bi2.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();
            ImageIO.write(to, "PNG", new File(realDestPath));
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false;
    }
    public static boolean resizeImageFile(String realSrcPath, String realDestPath, int destImgW, int destImgH) {
		//原图片等比例缩小或放大之后的图片
	    int narrowImgW ;
	    int narrowImgH ;
	    //原图片大小
	    int srcImgW;
	    int srcImgH;
	    try {
	    	File file = new File(realSrcPath);
	    	if(!file.exists()){
	    		System.out.println("[resizeImageFile] 文件不存在");
	    		return false;
	    	}
	        BufferedImage bi = ImageIO. read(file);
	    	if(bi==null){
	    		System.out.println("[resizeImageFile] 文件为空");
	    		return false;
	    	}
	        srcImgW = bi.getWidth();
	        srcImgH = bi.getHeight();
	        // 转换图片尺寸与目标尺寸比较 ， 如果转换图片较小，说明转换图片相对于目标图片来说高较小，需要以高为基准进行缩放。
	        if((float)srcImgW /srcImgH < (float)destImgW / destImgH){
	            narrowImgW = ( int)(((float )destImgH / (float)srcImgH)*srcImgW);
	            narrowImgH = destImgH;
	            //按照原图以高为基准等比例缩放、或放大。这一步高为所需图片的高度，宽度肯定会比目标宽度宽。
	            int cutNarrowImgSize = (narrowImgW - destImgW)/2;
	            BufferedImage narrowImg = new BufferedImage(narrowImgW, narrowImgH, BufferedImage.TYPE_INT_RGB);

	            Graphics2D g2d = narrowImg.createGraphics();
	            narrowImg = g2d.getDeviceConfiguration().createCompatibleImage(narrowImgW, narrowImgH, Transparency.TRANSLUCENT);
	            
	            narrowImg.getGraphics().drawImage(bi.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_SMOOTH ), 0, 0, null);
	            //等比例缩放完成后宽度与目标尺寸宽度相比较 ， 将多余宽的部分分为两份 ，左边删除一部分
	            Image image = narrowImg.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_DEFAULT );
	            CropImageFilter cropFilter = new CropImageFilter(cutNarrowImgSize, 0, narrowImgW-cutNarrowImgSize, narrowImgH);
	            Image img = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(image.getSource(), cropFilter));
	            BufferedImage cutLiftNarrowImg = new BufferedImage( narrowImgW-cutNarrowImgSize, narrowImgH, BufferedImage.TYPE_INT_RGB );
	            cutLiftNarrowImg.getGraphics().drawImage(img, 0, 0, null);
	            //右边删除一部分
	            image = cutLiftNarrowImg.getScaledInstance(narrowImgW-cutNarrowImgSize, narrowImgH, Image.SCALE_DEFAULT );
	            cropFilter = new CropImageFilter(0, 0, narrowImgW-cutNarrowImgSize*2, narrowImgH);
	            img = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(image.getSource(), cropFilter));
	            BufferedImage cutRightNarrowImg = new BufferedImage( narrowImgW-cutNarrowImgSize*2, narrowImgH, BufferedImage.TYPE_INT_RGB );
	            Graphics g = cutRightNarrowImg.getGraphics();
	            g.drawImage(img, 0, 0, null); // 绘制截取后的图
	            g.dispose();
	            //输出为文件 最终为所需要的格式
	            ImageIO.write(cutRightNarrowImg, "PNG", new File(realDestPath));
	            
	        } else { //以宽度为基准
	            narrowImgW = destImgW;
	            narrowImgH = ( int) (((float )destImgW / (float)srcImgW)*srcImgH);
	            int cutNarrowImgSize = (narrowImgH - destImgH)/2;
	 
	            BufferedImage narrowImg = new BufferedImage(narrowImgW, narrowImgH,BufferedImage.TYPE_INT_RGB);

	            Graphics2D g2d = narrowImg.createGraphics();
	            narrowImg = g2d.getDeviceConfiguration().createCompatibleImage(narrowImgW, narrowImgH, Transparency.TRANSLUCENT);
	            
	            narrowImg.getGraphics().drawImage(bi.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_SMOOTH ), 0, 0, null);
	 
	            Image image = narrowImg.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_DEFAULT );
	            CropImageFilter cropFilter = new CropImageFilter(0, cutNarrowImgSize, narrowImgW, narrowImgH-cutNarrowImgSize);
	            Image img = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(image.getSource(), cropFilter));
	            BufferedImage cutTopNarrowImg = new BufferedImage( narrowImgW, narrowImgH-cutNarrowImgSize,BufferedImage. TYPE_INT_RGB);
	            cutTopNarrowImg.getGraphics().drawImage(img, 0, 0, null);
	 
	            image = cutTopNarrowImg.getScaledInstance(narrowImgW, narrowImgH-cutNarrowImgSize, Image.SCALE_DEFAULT);
	            cropFilter = new CropImageFilter(0, 0, narrowImgW, narrowImgH-cutNarrowImgSize*2);
	            img = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(image.getSource(), cropFilter));
	            BufferedImage cutBottomNarrowImg = new BufferedImage( narrowImgW, narrowImgH-cutNarrowImgSize*2,BufferedImage. TYPE_INT_RGB);
	            Graphics g = cutBottomNarrowImg.getGraphics();
	            g.drawImage(img, 0, 0, null);
	            g.dispose();
	            ImageIO. write(cutBottomNarrowImg, "PNG", new File(realDestPath));
	        }
	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false;
    }

}
