//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.wrapper;

import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class ExtSessionImpl implements ExtSession {
    private HttpSession a;

    public ExtSessionImpl(HttpSession var1) {
        this.a = var1;
    }

    public Object getAttribute(String var1) {
        return this.a.getAttribute(var1);
    }

    public Enumeration getAttributeNames() {
        return this.a.getAttributeNames();
    }

    public long getCreationTime() {
        return this.a.getCreationTime();
    }

    public String getId() {
        return this.a.getId();
    }

    public long getLastAccessedTime() {
        return this.a.getLastAccessedTime();
    }

    public int getMaxInactiveInterval() {
        return this.a.getMaxInactiveInterval();
    }

    public ServletContext getServletContext() {
        return this.a.getServletContext();
    }

    public void invalidate() {
        this.a.invalidate();
    }

    public boolean isNew() {
        return this.a.isNew();
    }

    public void removeAttribute(String var1) {
        this.a.removeAttribute(var1);
    }

    public void setAttribute(String var1, Object var2) {
        this.a.setAttribute(var1, var2);
    }

    public void setMaxInactiveInterval(int var1) {
        this.a.setMaxInactiveInterval(var1);
    }
}
