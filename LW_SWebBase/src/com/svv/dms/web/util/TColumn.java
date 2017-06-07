package com.svv.dms.web.util;

import java.io.Serializable;

public class TColumn implements Serializable {

    private static final long serialVersionUID = 7645804658627912125L;

    public final static int TYPE_STRING = 0;
    public final static int TYPE_DATE = 1;
    public final static int TYPE_INT = 2;
    public final static int TYPE_NUMBER = 3;
    public final static int TYPE_DOUBLE = 4;
    public final static int TYPE_RADIO = 5;
    public final static int TYPE_CHECKBOX = 6;
    public final static int TYPE_LINK = 7;
    public final static int TYPE_NUMBER2 = 8;
    
    public final static String ALIGN_LEFT = "left";
    public final static String ALIGN_CENTER = "center";
    public final static String ALIGN_RIGHT = "right";

    private String colname = "";
    private String sortname = "";
    private int type = 0;
    private String align = "left";
    private String action = "";
    private int width = 0;
    private int colspan = 1;
    private int rowspan = 1;
    private boolean hasButton = false;

    public TColumn(){}
    
    public TColumn(String colname){
        this.colname = colname;
    }
    
    public TColumn(String colname, String sortname){
        this.colname = colname;
        this.sortname = sortname;
    }
    
    public TColumn(String colname, int width){
        this.colname = colname;
        this.width = width;
    }
    
    public TColumn(String colname, String sortname, String align){
        this.colname = colname;
        this.sortname = sortname;
        this.align = align;
    }
    
    public TColumn(String colname, String align, int type, String action){
        this.colname = colname;
        this.type = type;
        this.align = align;
        this.action = action;
    }

    public TColumn(String colname, int type, String align, int width){
        this.colname = colname;
        this.type = type;
        this.align = align;
        this.width = width;
    }

    public TColumn(String colname, String align, int width){
        this.colname = colname;
        this.align = align;
        this.width = width;
    }

    public TColumn(String colname, String sortname, String align, int width){
        this.colname = colname;
        this.sortname = sortname;
        this.align = align;
        this.width = width;
    }

	public TColumn(String colname, String align, boolean hasButton){
        this.colname = colname;
        this.align = align;
        this.hasButton = hasButton;
    }

    public TColumn(String colname, String align, int width, int rowspan, int colspan){
        this.colname = colname;
        this.align = align;
        this.width = width;
        this.rowspan = rowspan;
        this.colspan = colspan;
    }
    public TColumn(String colname, String align, int rowspan, int colspan){
        this.colname = colname;
        this.align = align;
        this.rowspan = rowspan;
        this.colspan = colspan;
    }
    public TColumn(String colname, String sortname, String align, int rowspan, int colspan){
        this.colname = colname;
        this.sortname = sortname;
        this.align = align;
        this.rowspan = rowspan;
        this.colspan = colspan;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getColname() {
        return colname;
    }

    public void setColname(String colname) {
        this.colname = colname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public boolean getHasButton() {
        return hasButton;
    }

    public void setHasButton(boolean hasButton) {
        this.hasButton = hasButton;
    }

}
