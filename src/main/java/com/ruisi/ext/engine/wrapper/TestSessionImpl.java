//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.wrapper;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;

public class TestSessionImpl implements ExtSession {
    private Map a = new HashMap();

    public TestSessionImpl() {
    }

    public Object getAttribute(String var1) {
        return this.a.get(var1);
    }

    public Enumeration getAttributeNames() {
        return null;
    }

    public long getCreationTime() {
        return 0L;
    }

    public String getId() {
        return null;
    }

    public long getLastAccessedTime() {
        return 0L;
    }

    public int getMaxInactiveInterval() {
        return 0;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public void invalidate() {
    }

    public boolean isNew() {
        return false;
    }

    public void removeAttribute(String var1) {
        this.a.remove(var1);
    }

    public void setAttribute(String var1, Object var2) {
        this.a.put(var1, var2);
    }

    public void setMaxInactiveInterval(int var1) {
    }
}
