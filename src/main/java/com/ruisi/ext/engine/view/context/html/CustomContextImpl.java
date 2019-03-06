//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.CustomBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class CustomContextImpl extends AbstractContext implements CustomContext {
    private Object a;
    private Object b;

    public CustomContextImpl() {
    }

    public AbstractBuilder createBuilder() {
        return new CustomBuilder(this);
    }

    public Object getJson() {
        return this.a;
    }

    public void setJson(Object var1) {
        this.a = var1;
    }

    public Object getPageInfo() {
        return this.b;
    }

    public void setPageInfo(Object var1) {
        this.b = var1;
    }
}
