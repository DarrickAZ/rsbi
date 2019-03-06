//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.DataBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class DataContextImpl extends AbstractContext implements DataContext {
    private String a;
    private String b;
    private boolean c = false;
    private String d;
    private String e;
    private String[] f;
    private String[] g;

    public DataContextImpl() {
    }

    public AbstractBuilder createBuilder() {
        return new DataBuilder(this);
    }

    public String getTemplateName() {
        return this.a;
    }

    public void setTemplateName(String var1) {
        this.a = var1;
    }

    public String getKey() {
        return this.b;
    }

    public void setKey(String var1) {
        this.b = var1;
    }

    public boolean isMulti() {
        return this.c;
    }

    public void setMulti(boolean var1) {
        this.c = var1;
    }

    public String getRefDsource() {
        return this.d;
    }

    public void setRefDsource(String var1) {
        this.d = var1;
    }

    public String[] getOutKey() {
        return this.f;
    }

    public String[] getOutVal() {
        return this.g;
    }

    public void setOutKey(String[] var1) {
        this.f = var1;
    }

    public void setOutVal(String[] var1) {
        this.g = var1;
    }

    public String getRefDataCenter() {
        return this.e;
    }

    public void setRefDataCenter(String var1) {
        this.e = var1;
    }
}
