//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.control.ActionProcess;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.IncludeBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

public class IncludeContextImpl extends AbstractContext implements IncludeContext {
    private String a;
    private String b;
    private String c;

    public IncludeContextImpl() {
    }

    @Override
    public AbstractBuilder createBuilder() {
        return new IncludeBuilder(this);
    }

    @Override
    public void check() throws ExtConfigException {
        if (this.b != null && this.b.length() > 0) {
            MVContext var1 = ActionProcess.findMVContextById(this.b, false);
            var1.setFromRef(true);
        }

    }

    @Override
    public String getPage() {
        return this.a;
    }

    @Override
    public void setPage(String var1) {
        this.a = var1;
    }

    @Override
    public String getMvid() {
        return this.b;
    }

    @Override
    public void setMvid(String var1) {
        this.b = var1;
    }

    @Override
    public String getContent() {
        return this.c;
    }

    @Override
    public void setContent(String var1) {
        this.c = var1;
    }
}
