//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.wrapper;

import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ExtRequestImpl implements ExtRequest {
    private HttpServletRequest a;
    private List b;

    public ExtRequestImpl(HttpServletRequest var1) {
        this.a = var1;
        if (ServletFileUpload.isMultipartContent(var1)) {
            DiskFileItemFactory var2 = new DiskFileItemFactory();

            try {
                ServletFileUpload var3 = new ServletFileUpload(var2);
                var3.setHeaderEncoding("UTF-8");
                this.b = var3.parseRequest(var1);
            } catch (Exception var5) {
                throw new ExtRuntimeException(var5);
            }
        }

        try {
            var1.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException var4) {
            throw new ExtRuntimeException(var4);
        }
    }

    public HttpServletRequest getRequest() {
        return this.a;
    }

    @Override
    public String getAuthType() {
        return this.a.getAuthType();
    }

    @Override
    public String getContextPath() {
        return this.a.getContextPath();
    }

    @Override
    public Cookie[] getCookies() {
        return this.a.getCookies();
    }

    @Override
    public long getDateHeader(String var1) {
        return this.a.getDateHeader(var1);
    }

    @Override
    public String getHeader(String var1) {
        return this.a.getHeader(var1);
    }

    @Override
    public Enumeration getHeaderNames() {
        return this.a.getHeaderNames();
    }

    @Override
    public Enumeration getHeaders(String var1) {
        return this.a.getHeaders(var1);
    }

    @Override
    public int getIntHeader(String var1) {
        return this.a.getIntHeader(var1);
    }

    @Override
    public String getMethod() {
        return this.a.getMethod();
    }

    @Override
    public String getPathInfo() {
        return this.a.getPathInfo();
    }

    @Override
    public String getPathTranslated() {
        return this.a.getPathTranslated();
    }

    @Override
    public String getQueryString() {
        return this.a.getQueryString();
    }

    @Override
    public String getRemoteUser() {
        return this.a.getRemoteHost();
    }

    @Override
    public String getRequestURI() {
        return this.a.getRequestURI();
    }

    @Override
    public StringBuffer getRequestURL() {
        return this.a.getRequestURL();
    }

    @Override
    public String getRequestedSessionId() {
        return this.a.getRequestedSessionId();
    }

    @Override
    public String getServletPath() {
        return this.a.getServletPath();
    }

    @Override
    public ExtSession getSession() {
        return new ExtSessionImpl(this.a.getSession());
    }

    @Override
    public ExtSession getSession(boolean var1) {
        return new ExtSessionImpl(this.a.getSession(var1));
    }

    @Override
    public Principal getUserPrincipal() {
        return this.a.getUserPrincipal();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return this.a.isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return this.a.isRequestedSessionIdFromURL();
    }

    public boolean isRequestedSessionIdFromUrl() {
        return this.a.isRequestedSessionIdFromUrl();
    }

    public boolean isRequestedSessionIdValid() {
        return this.a.isRequestedSessionIdValid();
    }

    public boolean isUserInRole(String var1) {
        return this.a.isUserInRole(var1);
    }

    @Override
    public Object getAttribute(String var1) {
        return this.a.getAttribute(var1);
    }

    @Override
    public Enumeration getAttributeNames() {
        return this.a.getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        return this.a.getCharacterEncoding();
    }

    @Override
    public int getContentLength() {
        return this.a.getContentLength();
    }

    @Override
    public String getContentType() {
        return this.a.getContentType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.a.getInputStream();
    }

    @Override
    public String getLocalAddr() {
        return this.a.getLocalAddr();
    }

    @Override
    public String getLocalName() {
        return this.a.getLocalName();
    }

    @Override
    public int getLocalPort() {
        return this.a.getLocalPort();
    }

    @Override
    public Locale getLocale() {
        return this.a.getLocale();
    }

    @Override
    public Object getProxy() {
        return this.a;
    }

    @Override
    public Enumeration getLocales() {
        return this.a.getLocales();
    }

    @Override
    public FileItem getUploadFile(String var1) {
        Iterator var3 = this.b.iterator();

        while(var3.hasNext()) {
            FileItem var2 = (FileItem)var3.next();
            if (var2.getFieldName().equalsIgnoreCase(var1)) {
                return var2;
            }
        }

        return null;
    }

    @Override
    public String getParameter(String var1) {
        if (var1 == null) {
            return null;
        } else if (!ServletFileUpload.isMultipartContent(this.a)) {
            return this.a.getParameter(var1);
        } else {
            Iterator var3 = this.b.iterator();

            while(var3.hasNext()) {
                FileItem var2 = (FileItem)var3.next();
                if (var2.getFieldName().equalsIgnoreCase(var1)) {
                    try {
                        return new String(var2.get(), "UTF-8");
                    } catch (Exception var5) {
                        throw new ExtRuntimeException(var5);
                    }
                }
            }

            return null;
        }
    }

    @Override
    public Map getParameterMap() {
        return this.a.getParameterMap();
    }

    @Override
    public Enumeration getParameterNames() {
        return this.a.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String var1) {
        if (!ServletFileUpload.isMultipartContent(this.a)) {
            return this.a.getParameterValues(var1);
        } else {
            ArrayList var2 = new ArrayList();
            Iterator var4 = this.b.iterator();

            while(var4.hasNext()) {
                FileItem var3 = (FileItem)var4.next();
                if (var3.getFieldName().equalsIgnoreCase(var1)) {
                    try {
                        String var5 = new String(var3.get(), "UTF-8");
                        var2.add(var5);
                    } catch (Exception var6) {
                        throw new ExtRuntimeException(var6);
                    }
                }
            }

            String[] var7 = new String[var2.size()];

            for(int var8 = 0; var8 < var2.size(); ++var8) {
                var7[var8] = (String)var2.get(var8);
            }

            return var7;
        }
    }

    @Override
    public String getProtocol() {
        return this.a.getProtocol();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return this.a.getReader();
    }

    @Override
    public String getRealPath(String var1) {
        return this.a.getRealPath(var1);
    }

    @Override
    public String getRemoteAddr() {
        return this.a.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return this.a.getRemoteHost();
    }

    @Override
    public int getRemotePort() {
        return this.a.getRemotePort();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String var1) {
        return this.a.getRequestDispatcher(var1);
    }

    @Override
    public String getScheme() {
        return this.a.getScheme();
    }

    @Override
    public String getServerName() {
        return this.a.getServerName();
    }

    @Override
    public int getServerPort() {
        return this.a.getServerPort();
    }

    @Override
    public boolean isSecure() {
        return this.a.isSecure();
    }

    @Override
    public void removeAttribute(String var1) {
        this.a.removeAttribute(var1);
    }

    @Override
    public void setAttribute(String var1, Object var2) {
        this.a.setAttribute(var1, var2);
    }

    @Override
    public void setCharacterEncoding(String var1) throws UnsupportedEncodingException {
        this.a.setCharacterEncoding(var1);
    }
}
