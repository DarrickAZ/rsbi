//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.wrapper;

import java.util.Enumeration;
import javax.servlet.ServletContext;

public interface ExtSession {
    Object getAttribute(String var1);

    Enumeration getAttributeNames();

    long getCreationTime();

    String getId();

    long getLastAccessedTime();

    int getMaxInactiveInterval();

    ServletContext getServletContext();

    void invalidate();

    boolean isNew();

    void removeAttribute(String var1);

    void setAttribute(String var1, Object var2);

    void setMaxInactiveInterval(int var1);
}
