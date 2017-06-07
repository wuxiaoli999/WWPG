package com.svv.dms.web.service.base;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class PageManager {
    
    private static final String DEFAULT_CACHE_NAME = "gs.grid.page";
    private static final int DEFAULT_CACHE_ELEMENTS = 10000;
    private static final boolean DEFAULT_CACHE_TODISK = true;
    private static final boolean DEFAULT_CACHE_ETERNAL = false;
    private static final long DEFAULT_CACHE_LIVETIME = 300;//900
    private static final long DEFAULT_CACHE_IDLETIME = 300;//600
    
    private static final String DEFAULT_ELEMENT_KEY = "gs.page.list.key";
    
    private static CacheManager manager = null;
    
    static{
        manager = CacheManager.create();
        manager.addCache(new Cache(DEFAULT_CACHE_NAME,DEFAULT_CACHE_ELEMENTS,
                                    DEFAULT_CACHE_TODISK,
                                    DEFAULT_CACHE_ETERNAL,
                                    DEFAULT_CACHE_LIVETIME,
                                    DEFAULT_CACHE_IDLETIME));
    }
    
    public PageManager(String elementKeyName, PageContext origin){        
        setOrigin(elementKeyName, origin);  
    }
    
    private void setElement(Object key,Object obj){
        Element element = new Element(key,obj);
        manager.getCache(DEFAULT_CACHE_NAME).put(element);
    }
    
    private Object getElement(Object key){
        Element element = manager.getCache(DEFAULT_CACHE_NAME).get(key);
        return element==null?null:element.getObjectValue();
    }
    
    public void setOrigin(String elementKeyName, PageContext origin){
        //save to cache
        //setElement(DEFAULT_ELEMENT_KEY,origin);
        setElement(DEFAULT_ELEMENT_KEY + elementKeyName, origin);
    }
    
    public PageContext getOrigin(String elementKeyName){
        //get from cache
        return (PageContext)getElement(DEFAULT_ELEMENT_KEY + elementKeyName);
    }
    
    public boolean isElementInMemory(String elementKeyName){
        return manager.getCache(DEFAULT_CACHE_NAME).isElementInMemory(DEFAULT_ELEMENT_KEY + elementKeyName);
    }
    
}
