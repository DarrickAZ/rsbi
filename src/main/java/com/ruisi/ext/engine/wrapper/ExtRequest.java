//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import org.apache.commons.fileupload.FileItem;

public interface ExtRequest {
    String getAuthType();

    String getContextPath();

    Cookie[] getCookies();

    long getDateHeader(String var1);

    String getHeader(String var1);

    Enumeration getHeaderNames();

    Enumeration getHeaders(String var1);

    int getIntHeader(String var1);

    String getMethod();

    String getPathInfo();

    String getPathTranslated();

    String getQueryString();

    String getRemoteUser();

    String getRequestURI();

    StringBuffer getRequestURL();

    String getRequestedSessionId();

    String getServletPath();

    ExtSession getSession();

    ExtSession getSession(boolean var1);

    Principal getUserPrincipal();

    boolean isRequestedSessionIdFromCookie();

    boolean isRequestedSessionIdFromURL();

    Object getAttribute(String var1);

    Enumeration getAttributeNames();

    String getCharacterEncoding();

    int getContentLength();

    String getContentType();

    ServletInputStream getInputStream() throws IOException;

    FileItem getUploadFile(String var1);

    String getParameter(String var1);

    Map getParameterMap();

    Enumeration getParameterNames();

    String[] getParameterValues(String var1);

    String getProtocol();

    BufferedReader getReader() throws IOException;

    String getRealPath(String var1);

    String getRemoteAddr();

    String getRemoteHost();

    int getRemotePort();

    RequestDispatcher getRequestDispatcher(String var1);

    String getScheme();

    String getServerName();

    int getServerPort();

    boolean isSecure();

    void removeAttribute(String var1);

    void setAttribute(String var1, Object var2);

    void setCharacterEncoding(String var1) throws UnsupportedEncodingException;

    String getLocalAddr();

    String getLocalName();

    int getLocalPort();

    Locale getLocale();

    Enumeration getLocales();

    Object getProxy();
}
