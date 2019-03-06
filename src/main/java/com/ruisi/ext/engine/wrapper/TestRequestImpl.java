//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.wrapper;

import java.io.BufferedReader;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import org.apache.commons.fileupload.FileItem;

public class TestRequestImpl implements ExtRequest {
    private Map a = new HashMap();
    private Map b;
    private ExtSession c;

    public TestRequestImpl(Map var1, ExtSession var2) {
        if (var1 == null) {
            this.b = new HashMap();
        } else {
            this.b = var1;
        }

        this.c = var2;
    }

    public Object getAttribute(String var1) {
        return this.a.get(var1);
    }

    public Enumeration getAttributeNames() {
        return null;
    }

    public String getAuthType() {
        return null;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public int getContentLength() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public String getContextPath() {
        return null;
    }

    public Cookie[] getCookies() {
        return null;
    }

    public long getDateHeader(String var1) {
        return 0L;
    }

    public String getHeader(String var1) {
        return null;
    }

    public Enumeration getHeaderNames() {
        return null;
    }

    public Enumeration getHeaders(String var1) {
        return null;
    }

    public ServletInputStream getInputStream() {
        return null;
    }

    public int getIntHeader(String var1) {
        return 0;
    }

    public String getLocalAddr() {
        return null;
    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public String getLocalName() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }

    public String getMethod() {
        return "GET";
    }

    public String getParameter(String var1) {
        return (String)this.b.get(var1);
    }

    public Map getParameterMap() {
        return null;
    }

    public Enumeration getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String var1) {
        return null;
    }

    public String getPathInfo() {
        return null;
    }

    public String getPathTranslated() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public Object getProxy() {
        return null;
    }

    public String getQueryString() {
        return null;
    }

    public BufferedReader getReader() {
        return null;
    }

    public String getRealPath(String var1) {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public int getRemotePort() {
        return 0;
    }

    public String getRemoteUser() {
        return null;
    }

    public RequestDispatcher getRequestDispatcher(String var1) {
        return null;
    }

    public String getRequestedSessionId() {
        return null;
    }

    public String getRequestURI() {
        return null;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public String getServletPath() {
        return null;
    }

    public ExtSession getSession() {
        return this.c;
    }

    public ExtSession getSession(boolean var1) {
        return this.c;
    }

    public FileItem getUploadFile(String var1) {
        return null;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    public boolean isSecure() {
        return false;
    }

    public void removeAttribute(String var1) {
        this.a.remove(var1);
    }

    public void setAttribute(String var1, Object var2) {
        this.a.put(var1, var2);
    }

    public void setCharacterEncoding(String var1) {
    }
}
