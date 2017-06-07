package com.svv.dms.web.service.base;


public class PageContext {
	
    private int definePage = 10;
    private long totalPage = 0;
    private int curPage = 0;
    private long totalRow = 0;
    private String key = "";
    
    public PageContext(int definePage){     
        setDefinePage(definePage);
    }
    
    public PageContext(String elementKeyName, long totalRow, int pageRow){
    	this.key = elementKeyName;
    	this.definePage = pageRow;
        this.totalRow = totalRow;
        totalPage = (totalRow-1)/definePage+1;
        curPage = 0;
    }
    
    public void setDefinePage(int definePage){
        this.definePage=definePage;
    }
    
    public int getDefinePage(){
        return definePage;
    }   
    
    public long getTotalPage(){
        return totalPage;
    }
    
    public int getCurPage(){
        return curPage;
    }
        
    public long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
}
