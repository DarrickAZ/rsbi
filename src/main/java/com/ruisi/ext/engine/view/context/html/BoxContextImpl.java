//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.BoxBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class BoxContextImpl extends AbstractContext implements BoxContext {
    private String a;
    private String b;
    private String c;
    private String d;

    public BoxContextImpl() {
    }

    public AbstractBuilder createBuilder() {
        return new BoxBuilder(this);
    }

    public String getTitle() {
        return this.a;
    }

    public void setTitle(String var1) {
        this.a = var1;
    }

    public String getCloseIcon() {
        return this.b;
    }

    public void setCloseIcon(String var1) {
        this.b = var1;
    }

    public String getId() {
        return this.c;
    }

    public void setId(String var1) {
        this.c = var1;
    }

    public String getState() {
        return this.d;
    }

    public void setState(String var1) {
        this.d = var1;
    }
}
