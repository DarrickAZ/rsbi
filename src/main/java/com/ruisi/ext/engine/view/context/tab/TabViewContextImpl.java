//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.tab;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.tab.TabViewBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class TabViewContextImpl extends AbstractContext implements TabViewContext {
    private boolean a;
    private String b;
    private String c;

    public TabViewContextImpl() {
    }

    public AbstractBuilder createBuilder() {
        return new TabViewBuilder(this);
    }

    public boolean isAjax() {
        return this.a;
    }

    public void setAjax(boolean var1) {
        this.a = var1;
    }

    public String getOutParameterName() {
        return this.b;
    }

    public void setOutParameterName(String var1) {
        this.b = var1;
    }

    public String getOut() {
        return this.c;
    }

    public void setOut(String var1) {
        this.c = var1;
    }
}
