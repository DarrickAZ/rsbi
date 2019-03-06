//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html.table;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.table.TdBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class TdContextImpl extends AbstractContext implements TdContext {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;

    public TdContextImpl() {
    }

    public String getStyleClass() {
        return this.f;
    }

    public void setStyleClass(String var1) {
        this.f = var1;
    }

    public AbstractBuilder createBuilder() {
        return new TdBuilder(this);
    }

    public String getWidth() {
        return this.a;
    }

    public void setWidth(String var1) {
        this.a = var1;
    }

    public String getHeight() {
        return this.b;
    }

    public void setHeight(String var1) {
        this.b = var1;
    }

    public String getStyle() {
        return this.c;
    }

    public void setStyle(String var1) {
        this.c = var1;
    }

    public String getColspan() {
        return this.d;
    }

    public void setColspan(String var1) {
        this.d = var1;
    }

    public String getRowspan() {
        return this.e;
    }

    public void setRowspan(String var1) {
        this.e = var1;
    }
}
