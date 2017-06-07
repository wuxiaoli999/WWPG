package com.svv.dms.web.listener;

import java.util.TreeMap;

import javax.servlet.http.HttpSessionAttributeListener;

import com.svv.dms.web.Constants;
import com.svv.dms.web.entity.S_User;
import com.svv.dms.web.util.HIUtil;

public class SystemSessionListener implements javax.servlet.http.HttpSessionListener, HttpSessionAttributeListener {
    
	public static TreeMap<String, Object> map = new TreeMap<String, Object>();

    public void sessionCreated(javax.servlet.http.HttpSessionEvent event) {        
    }

    public void sessionDestroyed(javax.servlet.http.HttpSessionEvent event) {        
    }

    public void attributeReplaced(javax.servlet.http.HttpSessionBindingEvent event) {
        addUser(event);
    }

    public void attributeAdded(javax.servlet.http.HttpSessionBindingEvent event) {
        addUser(event);
    }

    public void attributeRemoved(javax.servlet.http.HttpSessionBindingEvent event) {
        delUser(event);
    }
    
    private void addUser(javax.servlet.http.HttpSessionBindingEvent event) {
        javax.servlet.http.HttpSession session = event.getSession();
        if (event.getName().equals(Constants.SESSION_ATTRIBUTE_SUSER)) {
        	S_User user = (S_User) event.getValue();
            if(map.containsKey(session.getId())) delUser(event);
            map.put(session.getId(), user);
            System.out.println(HIUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss") + " session login for " + user.getUserName() + " ("
                    + user.getLoginName() + ") " + session.getId());
        }
    }

    private boolean isTimedOut(javax.servlet.http.HttpSession session) {
        try {
            long idle = new java.util.Date().getTime() - session.getLastAccessedTime();
            return idle > (session.getMaxInactiveInterval() * 1000);
        } catch (IllegalStateException e) {           
            return true;
        }
    }

    private void delUser(javax.servlet.http.HttpSessionBindingEvent event) {
        javax.servlet.http.HttpSession session = event.getSession();
        if (event.getName().equals(Constants.SESSION_ATTRIBUTE_SUSER)) {
        	S_User user = (S_User) event.getValue();
            map.remove(session.getId());
            System.out.println(HIUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss") + " session "
                    + (isTimedOut(session) ? "timeout" : "logout") + " for " + user.getUserName() + " ("
                    + user.getLoginName() + ") " + session.getId());            
        }
    }
    
}

