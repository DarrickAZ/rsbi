//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.tab;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.face.RefChecker;

public interface TabContext extends Element, RefChecker {
    boolean isActive();

    void setActive(boolean var1);

    String getTitle();

    void setTitle(String var1);

    String getRef();

    void setRef(String var1);

    void setContent(String var1);

    String getContent();

    void setValue(String var1);

    String getValue();

    String getAction();

    void setAction(String var1);

    String getMethod();

    void setMethod(String var1);
}
