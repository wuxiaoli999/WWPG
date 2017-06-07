package com.svv.dms.web.listener;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gs.db.database.BizAResult;
import com.svv.dms.web.Constants;
import com.svv.dms.web.entity.S_User;


public class SystemPermissionFilter implements Filter {
    
    protected String encoding = null;    
    protected FilterConfig filterConfig = null;
    protected boolean ignore = true;

    public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        
        if (ignore || (request.getCharacterEncoding() == null)) {
            request.setCharacterEncoding(selectEncoding(request));
        }

        HttpServletRequest req = (HttpServletRequest) request;
        //System.out.println("=====req session ID========="+req.getSession().getId());
        S_User user = (S_User) SystemSessionListener.map.get(req.getSession().getId());//req.getSession().getAttribute(Constants.SESSION_ATTRIBUTE_SUSER);
        if (user == null) {
            HttpServletResponse rep = (HttpServletResponse) response;
            rep.sendRedirect("../adminindex.y");
        } else {
            String url = req.getRequestURI();
            if (url.length() > 1) {
            	int slash1 = url.lastIndexOf("/") + 1;
                int slash2 = url.lastIndexOf(".");
            	int slash3 = url.lastIndexOf("/", slash1-2) + 1;
                String methodName = url.substring(slash1, slash2);
                String localaddr = req.getLocalAddr();
                Constants.WEB_URL = "http://"+localaddr+":"+req.getLocalPort()+url.substring(0, slash3);

                String path = url.substring(slash3, slash2);
                //System.out.println("SystemPermissionFilter::::::path="+url+" methodName="+Arrays.toString(user.getMyModules().toArray()));
               
                if (methodName.length() > 0) {
                    if (user.getMyModules()!=null && user.getMyModules().contains(methodName)) {
                        request.setAttribute(Constants.REQUEST_ATTRIBUTE_METHODNAME, methodName);
                        request.setAttribute(Constants.REQUEST_ATTRIBUTE_METHODPATH, path);
                        chain.doFilter(request, response);
                    } else {
                    	System.out.println("您无权访问此页面！methodName="+methodName);
                        request.setAttribute(Constants.REQUEST_ATTRIBUTE_MESSAGE, new BizAResult(false, "您无权访问此页面！"));
                        RequestDispatcher dispatcher = request.getRequestDispatcher("../Error.y");
                        dispatcher.forward(request, response);
                    }
                }
            }
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("ignore");
        if (value == null)
            this.ignore = true;
        else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes"))
            this.ignore = true;
        else
            this.ignore = false;
    }

    protected String selectEncoding(ServletRequest request) {
        return (this.encoding);
    }
    
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
    
}