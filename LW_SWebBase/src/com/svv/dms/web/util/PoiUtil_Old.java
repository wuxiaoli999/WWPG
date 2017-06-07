package com.svv.dms.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;



public class PoiUtil_Old {
	
	private HSSFWorkbook wb;
	private HSSFDataFormat hf;	
	
	private HSSFCellStyle style;
	private HSSFCellStyle boldStyle;
	private HSSFCellStyle titleStyle;
	private HSSFCellStyle headStyle;
	private HSSFCellStyle dataStyle;
    private HSSFCellStyle numStyle;
    private HSSFCellStyle num2Style;
	private HSSFCellStyle doubleStyle;
	private HSSFCellStyle mergedStyle;
	
	public PoiUtil_Old(){
		init();
	}
	
	private void init(){
		
		wb = new HSSFWorkbook();
		hf = wb.createDataFormat();
		
		HSSFFont font = wb.createFont();
	    font.setFontName("宋体");
	    style = wb.createCellStyle();
	    style.setFont(font);
	    
	    HSSFFont boldFont = wb.createFont();
	    boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    boldStyle = wb.createCellStyle();
	    boldStyle.setFont(boldFont);
	    
	    HSSFFont titleFont = wb.createFont();
	    titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    titleFont.setFontHeight((short)220);
	    titleStyle = wb.createCellStyle();
	    titleStyle.setFont(titleFont);
	    titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    
	    HSSFFont datafont = wb.createFont();
	    datafont.setFontName("宋体");
	    dataStyle = wb.createCellStyle();
	    dataStyle.setFont(datafont);
	    dataStyle.setWrapText(true);     
	    dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
	    dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	    dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
	    dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
	    dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    
	    
	    HSSFFont datafont1 = wb.createFont();
	    datafont1.setFontName("宋体");
	    mergedStyle = wb.createCellStyle();
	    mergedStyle.setFont(datafont1);
	    mergedStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);                         //水平居中
	    mergedStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);              //垂直居中
	    //设置单无格的边框为粗体，边框颜色
	    mergedStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    mergedStyle.setBottomBorderColor(HSSFColor.BLACK.index);
	    mergedStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    mergedStyle.setLeftBorderColor(HSSFColor.BLACK.index);
	    mergedStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    mergedStyle.setRightBorderColor(HSSFColor.BLACK.index);
	    mergedStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    mergedStyle.setTopBorderColor(HSSFColor.BLACK.index);
	    mergedStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);         // 设置单元格背景色;
	    mergedStyle.setWrapText(true);//文本区域随内容多少自动调整
	    
        HSSFFont numfont = wb.createFont();
        numfont.setFontName("宋体");
        numStyle = wb.createCellStyle();
        numStyle.setFont(numfont);
        numStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        numStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        numStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        numStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        numStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        numStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        //HSSFFont num2font = wb.createFont();
        numfont.setFontName("宋体");
        num2Style = wb.createCellStyle();
        num2Style.setFont(numfont);
        num2Style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        num2Style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        num2Style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        num2Style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        num2Style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        num2Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        num2Style.setDataFormat(hf.getFormat("#,##0"));
        
	    doubleStyle = wb.createCellStyle();
	    doubleStyle.setFont(datafont);
	    doubleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
	    doubleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	    doubleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
	    doubleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
	    doubleStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
	    doubleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    doubleStyle.setDataFormat(hf.getFormat("#,##0.00"));
	    
	    HSSFFont headFont = wb.createFont();
	    headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    headStyle = wb.createCellStyle();
	    headStyle.setFont(headFont);
	    headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
	    headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	    headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
	    headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
	    headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);//设定单元个背景颜色
		
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
	
    public HSSFWorkbook createHssf(String title, Object[] objs) {
        return createHssf(title, objs, 2);
    }
    public HSSFWorkbook createHssf(String title, Object[] objs, int firstDataRowIndex) {
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
            
            HSSFSheet sheet = wb.createSheet("sheet");
            for (int i = 0; i < columns; i++)
                sheet.setColumnWidth((short) i, (short) (widths[i] * 256));

            short iRow = 0;
            HSSFRow row;
            if(!HIUtil.isEmpty(title)){
                row = sheet.createRow(iRow++);
                createCell(row, 0, titleStyle, title);
                row = sheet.createRow(iRow++);
                createCell(row, 0, boldStyle, "");
                
                sheet.addMergedRegion(new Region(0, (short)0, 0, (short)(columns-1)));
                sheet.addMergedRegion(new Region(1, (short)0, 1, (short)(columns-1)));
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
                        sheet.addMergedRegion(new Region(row.getRowNum(), (short)0, row.getRowNum(), (short)(c-1)));
                    }
                }
                /*
                if(1==1){
                    HSSFRow row0 = sheet.createRow(0);
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
	
	private static void createCell(HSSFRow row, int column, HSSFCellStyle hStyle, String value){
		HSSFCell cell = row.createCell((short)column);
		/////////////////////////cell.set.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(value);
		cell.setCellStyle(hStyle);
	}

	private static void createCell(HSSFRow row, int column, HSSFCellStyle hStyle, Double value){
		HSSFCell cell = row.createCell((short)column);
		/////////////////////////cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(value);
		cell.setCellStyle(hStyle);
    }
    
    private void addRow(HSSFSheet sheet, HSSFRow row, HSSFCellStyle hStyle, Object[] values) {
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
                sheet.addMergedRegion(new Region(row.getRowNum(), (short)tmp, row.getRowNum(), (short)k));
            }
            //System.out.println("[addRow] "+t.getColname()+" Cols="+t.getColspan()+" k="+k);
        }
    }
    
    private int addTColumnRow(HSSFSheet sheet, HSSFRow row, HSSFCellStyle hStyle, Object[] values, int columns) {
        int k = 0;
        int tmp = 0;
        HashMap<Integer, List<Integer>> next_k = new HashMap<Integer, List<Integer>>();
        int cur_rownum = 0;
        HSSFRow currow = row;
        HSSFRow[] nextrows = new HSSFRow[5]; //最多5行
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
            sheet.addMergedRegion(new Region(currow.getRowNum(), (short)tmp, currow.getRowNum()+t.getRowspan()-1, (short)k));
        }
        return currow.getRowNum();
    }
    private short getAlign(String align){
        if(align.equals(TColumn.ALIGN_LEFT)) return HSSFCellStyle.ALIGN_LEFT;
        if(align.equals(TColumn.ALIGN_CENTER)) return HSSFCellStyle.ALIGN_CENTER;
        if(align.equals(TColumn.ALIGN_RIGHT)) return HSSFCellStyle.ALIGN_RIGHT;
        return HSSFCellStyle.ALIGN_LEFT;
    }
}
