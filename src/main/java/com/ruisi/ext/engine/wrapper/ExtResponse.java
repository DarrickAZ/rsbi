//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.wrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;

public interface ExtResponse {
    void addCookie(Cookie var1);

    void addDateHeader(String var1, long var2);

    void addHeader(String var1, String var2);

    void addIntHeader(String var1, int var2);

    boolean containsHeader(String var1);

    String encodeRedirectUrl(String var1);

    String encodeRedirectURL(String var1);

    String encodeUrl(String var1);

    String encodeURL(String var1);

    void sendError(int var1, String var2) throws IOException;

    void sendError(int var1) throws IOException;

    void sendRedirect(String var1) throws IOException;

    void setDateHeader(String var1, long var2);

    void setHeader(String var1, String var2);

    void setIntHeader(String var1, int var2);

    void setStatus(int var1, String var2);

    void setStatus(int var1);

    void flushBuffer() throws IOException;

    int getBufferSize();

    String getCharacterEncoding();

    String getContentType();

    Locale getLocale();

    ServletOutputStream getOutputStream() throws IOException;

    PrintWriter getWriter() throws IOException;

    boolean isCommitted();

    void reset();

    void resetBuffer();

    void setBufferSize(int var1);

    void setCharacterEncoding(String var1);

    void setContentLength(int var1);

    void setContentType(String var1);

    void setLocale(Locale var1);

    Object getProxy();
}
