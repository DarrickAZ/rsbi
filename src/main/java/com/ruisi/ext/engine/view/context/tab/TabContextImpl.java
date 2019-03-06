//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.tab;

import com.ruisi.ext.engine.control.ActionProcess;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.tab.TabBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

public class TabContextImpl extends AbstractContext implements TabContext {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private boolean f;
    private String g;

    public TabContextImpl() {
    }

    @Override
    public void check() {
        if (this.g != null && this.g.length() > 0) {
            MVContext var1 = null;
            try {
                var1 = ActionProcess.findMVContextById(this.g);
            } catch (ExtConfigException e1) {
                e1.printStackTrace();
            }
            var1.setFromRef(true);
        }

    }

    @Override
    public AbstractBuilder createBuilder() {
        return new TabBuilder(this);
    }

    @Override
    public boolean isActive() {
        return this.f;
    }

    @Override
    public void setActive(boolean var1) {
        this.f = var1;
    }

    @Override
    public String getTitle() {
        return this.a;
    }

    @Override
    public void setTitle(String var1) {
        this.a = var1;
    }

    @Override
    public String getRef() {
        return this.g;
    }

    @Override
    public void setRef(String var1) {
        this.g = var1;
    }

    @Override
    public String getContent() {
        return this.b;
    }

    @Override
    public void setContent(String var1) {
        this.b = var1;
    }

    @Override
    public String getValue() {
        return this.e;
    }

    @Override
    public void setValue(String var1) {
        this.e = var1;
    }

    @Override
    public String getAction() {
        return this.c;
    }

    @Override
    public void setAction(String var1) {
        this.c = var1;
    }

    @Override
    public String getMethod() {
        return this.d;
    }

    @Override
    public void setMethod(String var1) {
        this.d = var1;
    }
}
