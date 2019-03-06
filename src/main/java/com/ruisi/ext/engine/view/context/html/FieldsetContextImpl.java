//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.FieldsetBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class FieldsetContextImpl extends AbstractContext implements FieldsetContext {
    private String a;

    public FieldsetContextImpl() {
    }

    public String getTitle() {
        return this.a;
    }

    public void setTitle(String var1) {
        this.a = var1;
    }

    public AbstractBuilder createBuilder() {
        return new FieldsetBuilder(this);
    }
}
