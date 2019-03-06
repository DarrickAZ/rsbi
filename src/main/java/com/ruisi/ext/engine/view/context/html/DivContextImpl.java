//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.DivBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class DivContextImpl extends AbstractContext implements DivContext {
    private String a;
    private String b;
    private String c;
    private String d;

    public DivContextImpl() {
    }

    public AbstractBuilder createBuilder() {
        return new DivBuilder(this);
    }

    public String getId() {
        return this.a;
    }

    public void setId(String var1) {
        this.a = var1;
    }

    public String getStyle() {
        return this.b;
    }

    public void setStyle(String var1) {
        this.b = var1;
    }

    public String getStyleClass() {
        return this.c;
    }

    public void setStyleClass(String var1) {
        this.c = var1;
    }

    public String getAlign() {
        return this.d;
    }

    public void setAlign(String var1) {
        this.d = var1;
    }
}
