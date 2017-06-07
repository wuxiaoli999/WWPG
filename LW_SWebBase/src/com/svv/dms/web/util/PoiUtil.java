package com.svv.dms.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class PoiUtil {
	
	private XSSFWorkbook wb;
	private XSSFDataFormat hf;	
	
	private XSSFCellStyle style;
	private XSSFCellStyle boldStyle;
	private XSSFCellStyle titleStyle;
	private XSSFCellStyle headStyle;
	private XSSFCellStyle dataStyle;
    private XSSFCellStyle numStyle;
    private XSSFCellStyle num2Style;
	private XSSFCellStyle doubleStyle;
	private XSSFCellStyle mergedStyle;
	
	public PoiUtil(){
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
	    dataStyle.setWrapText(true);     
	    dataStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);//下边框
	    dataStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
	    dataStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
	    dataStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
	    dataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	    
	    
	    XSSFFont datafont1 = wb.createFont();
	    datafont1.setFontName("宋体");
	    mergedStyle = wb.createCellStyle();
	    mergedStyle.setFont(datafont1);
	    mergedStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);                         //水平居中
	    mergedStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);              //垂直居中
	    //设置单无格的边框为粗体，边框颜色
	    mergedStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
	    XSSFColor XSSFColor = new XSSFColor();
	    mergedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    mergedStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
	    mergedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    mergedStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
	    mergedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    mergedStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
	    mergedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	    mergedStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());         // 设置单元格背景色;
	    mergedStyle.setWrapText(true);//文本区域随内容多少自动调整
	    
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
        
        //XSSFFont num2font = wb.createFont();
        numfont.setFontName("宋体");
        num2Style = wb.createCellStyle();
        num2Style.setFont(numfont);
        num2Style.setBorderBottom(XSSFCellStyle.BORDER_THIN);//下边框
        num2Style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
        num2Style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
        num2Style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
        num2Style.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        num2Style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        num2Style.setDataFormat(hf.getFormat("#,##0"));
        
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
	 */
	
    public XSSFWorkbook createXssf(String title, Object[] objs) {
        return createXssf(title, objs, 2);
    }
    public XSSFWorkbook createXssf(String title, Object[] objs, int firstDataRowIndex) {
        if(objs==null || objs.length==0) return null;
        try{
            Object[] headRow = (Object[])objs[1];//objs[0] is tableHeight
            int columns = ((Object[])objs[firstDataRowIndex]).length-1;
            int[] widths = new int[columns];
            int[] styles = new int[columns];
            short[] align = new short[columns];
            String[] heads = new String[headRow.length];
            int k = 0;
            boolean twoRow = false;
            for(int i=0; i<headRow.length; i++){
                TColumn t = (TColumn)headRow[i];
                heads[i] = t.getColname();
                //System.out.println("[createHssf] head i="+i+"   columns="+columns+"   "+t.getColname());
                if(t.getRowspan()>1) twoRow = true;
                if(k>=columns) continue;
                for(int m=0; m<t.getColspan(); m++){
                    widths[k] = t.getWidth()==0 ? 20 : t.getWidth() / 5;
                    styles[k] = t.getType();
                    align[k] = getAlign(t.getAlign());
                    k++;
                }
            }
            
            XSSFSheet sheet = wb.createSheet("sheet");
            for (int i = 0; i < columns; i++)
                sheet.setColumnWidth((short) i, (short) (widths[i] * 256));

            short iRow = 0;
            XSSFRow row;
            if(!HIUtil.isEmpty(title)){
                row = sheet.createRow(iRow++);
                createCell(row, 0, titleStyle, title);
                row = sheet.createRow(iRow++);
                createCell(row, 0, boldStyle, "");
                
                sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 0, (short)(columns-1)));
                sheet.addMergedRegion(new CellRangeAddress(1, (short)0, 1, (short)(columns-1)));
            }

            row = sheet.createRow(iRow++);
            if(twoRow) iRow = (short) (addTColumnRow(sheet, row, headStyle, headRow, columns) + 1);
            else this.addRow(sheet, row, headStyle, headRow);
            
            for(int i=2;i<objs.length;i++){
                row = sheet.createRow(iRow++);                
                Object[] items = (Object[])objs[i];
                int span = columns - (items.length-1);
                //System.out.println("[createHssf] body i="+i+" items.length="+items.length+"  span="+span);
                int c = 0;
                int iStyle;
                for(int j=1;j<items.length;j++,c++){//j=0 is doFocus()
                    //System.out.println("[createHssf] body j="+j+"   item="+items[j]);
                    iStyle = styles[c];
                    if (iStyle == TColumn.TYPE_STRING) {
                        dataStyle.setAlignment(align[c]);
                        createCell(row, c, dataStyle, sformat(items[j]));
                    } else if (iStyle == TColumn.TYPE_NUMBER) {
                        createCell(row, c, numStyle, dformat(items[j]));
                    } else if (iStyle == TColumn.TYPE_NUMBER2) {
                        createCell(row, c, num2Style, dformat(items[j]));
                    } else if (iStyle == TColumn.TYPE_DOUBLE) {
                        createCell(row, c, doubleStyle, dformat(items[j]));
                    } else if (iStyle == TColumn.TYPE_DATE) {
                        createCell(row, c, dataStyle, sformat(items[j]));
                    } else {
                        createCell(row, c, dataStyle, sformat(items[j]));
                    }
                    if(span>0 && j==1){
                        for(int d=0; d<span; d++){
                            iStyle = styles[c];
                            createCell(row, c++, dataStyle, "");
                        }
                        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), (short)0, row.getRowNum(), (short)(c-1)));
                    }
                }
                /*
                if(1==1){
                    XSSFRow row0 = sheet.createRow(0);
                    createCell(row0, 0, titleStyle, "nihaonihao");
                    return wb;
                }*/
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
    
    private void addRow(XSSFSheet sheet, XSSFRow row, XSSFCellStyle hStyle, Object[] values) {
        int k = 0;
        int tmp = 0;
        for (int i = 0; i < values.length; i++,k++) {
            TColumn t = (TColumn)values[i];
            createCell(row, k, hStyle, (String) t.getColname());
            tmp = k;
            if(t.getColspan()>1){
                for(int j=0; j<t.getColspan()-1; j++){
                    createCell(row, ++k, hStyle, "");
                }
                sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), (short)tmp, row.getRowNum(), (short)k));
            }
            //System.out.println("[addRow] "+t.getColname()+" Cols="+t.getColspan()+" k="+k);
        }
    }
    
    private int addTColumnRow(XSSFSheet sheet, XSSFRow row, XSSFCellStyle hStyle, Object[] values, int columns) {
        int k = 0;
        int tmp = 0;
        HashMap<Integer, List<Integer>> next_k = new HashMap<Integer, List<Integer>>();
        int cur_rownum = 0;
        XSSFRow currow = row;
        XSSFRow[] nextrows = new XSSFRow[5]; //最多5行
        System.out.println("[addTColumnRow] columns="+columns);
        for (int i = 0; i < values.length; i++,k++) {
            TColumn t = (TColumn)values[i];
            while(next_k.get(cur_rownum)!=null && next_k.get(cur_rownum).contains(k)) k++;
            if(k>=columns){
                cur_rownum++;
                if(nextrows[cur_rownum] == null) nextrows[cur_rownum] = sheet.createRow(currow.getRowNum()+1);
                currow = nextrows[cur_rownum];
                k = 0;
                while(next_k.get(cur_rownum)!=null && next_k.get(cur_rownum).contains(k)) k++;
                System.out.println("[addTColumnRow] cur_rownum=="+(cur_rownum)+" sheet.createRow=="+(currow.getRowNum()+1));
            }
            
            createCell(currow, k, hStyle, (String) t.getColname());
            if(t.getRowspan()>1){
                for(int j=1; j<t.getRowspan(); j++){
                    if(nextrows[cur_rownum+j] == null) nextrows[cur_rownum+j] = sheet.createRow(cur_rownum+j);
                    createCell(nextrows[cur_rownum+j], k, hStyle, "");
                    
                    List<Integer> k_list = next_k.get(cur_rownum+j);
                    if(k_list==null) k_list = new ArrayList<Integer>();
                    k_list.add(k);
                    next_k.put(cur_rownum+j, k_list);
                    System.out.println("[addTColumnRow] cur_rownum+j=="+(cur_rownum+j)+" k_list.add=="+k);
                }
            }
            tmp = k;
            if(t.getColspan()>1){
                for(int j=0; j<t.getColspan()-1; j++){
                    createCell(currow, ++k, hStyle, "");
                }
            }
            sheet.addMergedRegion(new CellRangeAddress(currow.getRowNum(), (short)tmp, currow.getRowNum()+t.getRowspan()-1, (short)k));
        }
        return currow.getRowNum();
    }
    private short getAlign(String align){
        if(align.equals(TColumn.ALIGN_LEFT)) return XSSFCellStyle.ALIGN_LEFT;
        if(align.equals(TColumn.ALIGN_CENTER)) return XSSFCellStyle.ALIGN_CENTER;
        if(align.equals(TColumn.ALIGN_RIGHT)) return XSSFCellStyle.ALIGN_RIGHT;
        return XSSFCellStyle.ALIGN_LEFT;
    }
}
