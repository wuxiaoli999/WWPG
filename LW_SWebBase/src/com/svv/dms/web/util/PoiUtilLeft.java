package com.svv.dms.web.util;

import java.util.List;
import java.util.Vector;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class PoiUtilLeft {
	
	public static final int CELL_STYLE_DATA = 0;
	public static final int CELL_STYLE_NUM = 1;
	public static final int CELL_STYLE_DOUBLE = 2;
	public static final int CELL_STYLE_DATE = 3;
	
	private XSSFWorkbook wb;
	private XSSFDataFormat hf;	
	
	private XSSFCellStyle style;
	private XSSFCellStyle boldStyle;
	private XSSFCellStyle titleStyle;
	private XSSFCellStyle headStyle;
	private XSSFCellStyle dataStyle;
	private XSSFCellStyle numStyle;
	private XSSFCellStyle doubleStyle;
	
	public PoiUtilLeft(){
		init();
	}
	
	private void init(){
		
		wb = new XSSFWorkbook();
		hf = wb.createDataFormat();
		
		XSSFFont font = wb.createFont();
	    font.setFontName("宋体");
	    style = wb.createCellStyle();
	    style.setFont(font);
	    
	    XSSFFont boldFont = wb.createFont();
	    boldFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    boldStyle = wb.createCellStyle();
	    boldStyle.setFont(boldFont);
	    
	    XSSFFont titleFont = wb.createFont();
	    titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    titleFont.setFontHeight((short)220);
	    titleStyle = wb.createCellStyle();
	    titleStyle.setFont(titleFont);
	    titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	    
	    XSSFFont datafont = wb.createFont();
	    datafont.setFontName("宋体");
	    dataStyle = wb.createCellStyle();
	    dataStyle.setFont(datafont);
	    dataStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);//下边框
	    dataStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
	    dataStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
	    dataStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
	    dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	    
	    XSSFFont numfont = wb.createFont();
	    numfont.setFontName("宋体");
	    numStyle = wb.createCellStyle();
	    numStyle.setFont(numfont);
	    numStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);//下边框
	    numStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
	    numStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
	    numStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
	    numStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
	    numStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	    numStyle.setDataFormat(hf.getFormat("#,##0"));
	    
	    doubleStyle = wb.createCellStyle();
	    doubleStyle.setFont(datafont);
	    doubleStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);//下边框
	    doubleStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
	    doubleStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
	    doubleStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
	    doubleStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
	    doubleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	    doubleStyle.setDataFormat(hf.getFormat("#,##0.00"));
	    
	    XSSFFont headFont = wb.createFont();
	    headFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
	    headStyle = wb.createCellStyle();
	    headStyle.setFont(headFont);
	    headStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);//下边框
	    headStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
	    headStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
	    headStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
	    headStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	    headStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
	    headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//设定单元个背景颜色
		
	}
	
	/**
	 * @param title
	 * @param data
	 * -----------------------------------------------------
	 * 		|category1|category2|category3|...|categoryn|
	 * item1|data11	  |data12	|data13	  |...|data1n	|
	 * item2|data21	  |data22	|data23	  |...|data2n	|
	 * item3|data31	  |data32	|data33	  |...|data3n	|
	 * ...
	 * ...
	 * itemm|datam1	  |datam2	|datam3	  |...|datamn	|
	 * ------------------------------------------------------
	 * @param width
	 */
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public XSSFWorkbook createHssf(String title,List<List> data,int[] width,int[] style,int hgh) {	    
	    
		try{
		
		    XSSFSheet sheet = wb.createSheet("sheet");
		    for (int i = 0; i < width.length; i++) 
		        sheet.setColumnWidth((short) i, (short) (width[i] * 256));
		      
		    short iRow = 0;
		    XSSFRow row = sheet.createRow(iRow++);
		    createCell(row, 0, titleStyle, title);
		    
		    row = sheet.createRow(iRow++);
		    createCell(row, 0, boldStyle, "");
		    
		    sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 0, (short)(width.length-1)));
		    sheet.addMergedRegion(new CellRangeAddress(1, (short)0, 1, (short)(width.length-1)));
		    	    
		    row = sheet.createRow(iRow++);
		    Vector vtrCaption = new Vector(data.get(0));	   
		    addRow(row, headStyle, vtrCaption);	    
		    
		    for(int i=1;i<data.size();i++){
		    	row = sheet.createRow(iRow++);
		    	
		    	List list = data.get(i);
		    	for(int j=0;j<list.size();j++){
		    		
		    		int iStyle = (style==null||style.length<=j)?CELL_STYLE_DATA:style[j]; 
		    		if(iStyle==CELL_STYLE_DATA){
		    			createCell(row,j,dataStyle,sformat(list.get(j)));
		    		}
		    		else if(iStyle==CELL_STYLE_NUM){
		    			createCell(row,j,numStyle,dformat(list.get(j)));	    			
		    		}
		    		else if(iStyle==CELL_STYLE_DOUBLE){
		    			createCell(row,j,doubleStyle,dformat(list.get(j)));
		    		}
		    		else if(iStyle==CELL_STYLE_DATE){
		    			createCell(row,j,dataStyle,sformat(list.get(j)));
		    		}
		    		else{
		    			createCell(row,j,dataStyle,sformat(list.get(j)));
		    		}	    		
		    	}
		    	
		    }
		    for(int i=3;i<data.size()+3;i++){
		    	if((i-3)%hgh==0){
		        sheet.addMergedRegion(new CellRangeAddress(i, (short)0, i+hgh-1, (short)0));	
		    	}
		    }
			return wb;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
		
	}
	
	private String sformat(Object o) {
	    return o == null || String.valueOf(o).equals("null") ? "" : String.valueOf(o);
	}
	
	private double dformat(Object o) {
		try{
			return o == null ? 0D : Double.parseDouble(String.valueOf(o));
		}catch(Exception e){			
		}
		return 0D;		
	}
	
	private static void createCell(XSSFRow row, int column, XSSFCellStyle hStyle, String value){
		XSSFCell cell = row.createCell((short)column);
		cell.setCellValue(value);
		cell.setCellStyle(hStyle);
	}

	private static void createCell(XSSFRow row, int column, XSSFCellStyle hStyle, Double value){
		XSSFCell cell = row.createCell((short)column);
		cell.setCellValue(value);
		cell.setCellStyle(hStyle);
   }
	
    @SuppressWarnings("rawtypes")
    private void addRow(XSSFRow row, XSSFCellStyle hStyle, Vector vValue) {
	    for(int i=0;i<vValue.size();i++){
	      XSSFCell cell = row.createCell((short)i);
	      cell.setCellValue((String)vValue.get(i));
	      cell.setCellStyle(hStyle);
	    } 
	}
	
	/*
	public static void main(String[] argc) throws Exception{
		
		List data = new java.util.ArrayList();
		List caption = new java.util.ArrayList();
		caption.add("category1");
		caption.add("category2");
		caption.add("category3");
		caption.add("category4");
		caption.add("category5");	
		data.add(caption);
		for(int i=0;i<10;i++){
			List list = new java.util.ArrayList();
			list.add("dds");
			list.add(12334);
			list.add("3234SA4");
			list.add(0.45);
			list.add("2007-12-34");
			data.add(list);
		}
		
		PoiUtil poi = new PoiUtil();
		XSSFWorkbook hssf = poi.createHssf("test", data, 
				new int[]{12,10,20,10,50}, 
				new int[]{CELL_STYLE_DATA,CELL_STYLE_NUM,CELL_STYLE_DATA,CELL_STYLE_DOUBLE,CELL_STYLE_DATE});
		
		java.io.FileOutputStream out = new java.io.FileOutputStream("c:\\test.xls");
		hssf.write(out);
		out.close();
		
		System.out.println("over");
	}
	*/
}
