package com.svv.dms.web.util;


public class NextPage {
    private boolean auto = true;  //默认自动转向
    private int time = 1;  //当等于0时，不自动转向
    private String name = "";
    private String url = "";

    public NextPage(String name, String url) {
        super();
        this.name = name;
        this.url = url;
    }
  
    public NextPage(int time, String name, String url) {
        super();
        this.auto = time>0;
        this.time = time;
        this.name = name;
        this.url = url;
    }

    public boolean isAuto() {
        return auto;
    }
    public void setAuto(boolean auto) {
        this.auto = auto;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    
}