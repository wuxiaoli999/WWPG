package com.svv.dms.web.util;

public class HIProperty {
    private String key;
    private String value;
    
    public HIProperty(String key, String value){
    	this.key = key;
    	this.value = value;
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
    
    
}
