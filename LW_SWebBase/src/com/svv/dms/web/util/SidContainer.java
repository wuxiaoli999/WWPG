package com.svv.dms.web.util;

import java.util.concurrent.ConcurrentHashMap;

public class SidContainer {

    private ConcurrentHashMap<String, String> map = null;

    public SidContainer() {
        map = new ConcurrentHashMap<String, String>();
    }

    public void set(String sid, String pos) {
        this.map.put(sid, pos);
    }

    public String get(String sid) {
        return this.map.get(sid);
    }

    public String remove(String sid) {
        return this.map.remove(sid);
    }
}
