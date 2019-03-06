//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.tab;

import com.ruisi.ext.engine.view.context.Element;

public interface TabViewContext extends Element {
    boolean isAjax();

    void setAjax(boolean var1);

    void setOutParameterName(String var1);

    String getOutParameterName();

    String getOut();

    void setOut(String var1);
}
