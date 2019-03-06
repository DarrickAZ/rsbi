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
import javax.servlet.http.HttpServletResponse;

public class ExtResponseImpl implements ExtResponse {
    private HttpServletResponse a;

    public ExtResponseImpl(HttpServletResponse var1) {
        this.a = var1;
    }

    @Override
    public void addCookie(Cookie var1) {
        this.a.addCookie(var1);
    }

    @Override
    public void addDateHeader(String var1, long var2) {
        this.a.addDateHeader(var1, var2);
    }

    @Override
    public void addHeader(String var1, String var2) {
        this.a.addHeader(var1, var2);
    }

    @Override
    public void addIntHeader(String var1, int var2) {
        this.a.addIntHeader(var1, var2);
    }

    @Override
    public boolean containsHeader(String var1) {
        return this.a.containsHeader(var1);
    }

    @Override
    public String encodeRedirectUrl(String var1) {
        return this.a.encodeRedirectUrl(var1);
    }

    @Override
    public String encodeRedirectURL(String var1) {
        return this.a.encodeRedirectURL(var1);
    }

    @Override
    public String encodeUrl(String var1) {
        return this.a.encodeUrl(var1);
    }

    @Override
    public String encodeURL(String var1) {
        return this.a.encodeURL(var1);
    }

    @Override
    public void flushBuffer() throws IOException {
        this.a.flushBuffer();
    }

    @Override
    public int getBufferSize() {
        return this.a.getBufferSize();
    }

    @Override
    public String getCharacterEncoding() {
        return this.a.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        return this.a.getContentType();
    }

    @Override
    public Locale getLocale() {
        return this.a.getLocale();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return this.a.getOutputStream();
    }

    @Override
    public Object getProxy() {
        return this.a;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return this.a.getWriter();
    }

    @Override
    public boolean isCommitted() {
        return this.a.isCommitted();
    }

    @Override
    public void reset() {
        this.a.reset();
    }

    @Override
    public void resetBuffer() {
        this.a.resetBuffer();
    }

    @Override
    public void sendError(int var1, String var2) throws IOException {
        this.a.sendError(var1, var2);
    }

    @Override
    public void sendError(int var1) throws IOException {
        this.a.sendError(var1);
    }

    @Override
    public void sendRedirect(String var1) throws IOException {
        this.a.sendRedirect(var1);
    }

    @Override
    public void setBufferSize(int var1) {
        this.a.setBufferSize(var1);
    }

    @Override
    public void setCharacterEncoding(String var1) {
        this.a.setCharacterEncoding(var1);
    }

    @Override
    public void setContentLength(int var1) {
        this.a.setContentLength(var1);
    }

    @Override
    public void setContentType(String var1) {
        this.a.setContentType(var1);
    }

    @Override
    public void setDateHeader(String var1, long var2) {
        this.a.setDateHeader(var1, var2);
    }

    @Override
    public void setHeader(String var1, String var2) {
        this.a.setHeader(var1, var2);
    }

    @Override
    public void setIntHeader(String var1, int var2) {
        this.a.setIntHeader(var1, var2);
    }

    @Override
    public void setLocale(Locale var1) {
        this.a.setLocale(var1);
    }

    @Override
    public void setStatus(int var1, String var2) {
        this.a.setStatus(var1, var2);
    }

    @Override
    public void setStatus(int var1) {
        this.a.setStatus(var1);
    }
}
