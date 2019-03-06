//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.context.AbstractContext;

public abstract class AbstractInputContext extends AbstractContext implements InputField {
    private String a;
    private String b;
    private String c;
    private boolean d = false;
    private String e;
    private String f;
    private String g;
    private boolean h = false;
    private Boolean i;
    private Object j;

    public AbstractInputContext() {
    }

    public boolean isShow() {
        return this.h;
    }

    public void setShow(boolean var1) {
        this.h = var1;
    }

    public String getDesc() {
        return this.b;
    }

    public void setDesc(String var1) {
        this.b = var1;
    }

    public String getSize() {
        return this.a;
    }

    public void setSize(String var1) {
        this.a = var1;
    }

    public String getId() {
        return this.c;
    }

    public void setId(String var1) {
        this.c = var1;
    }

    public boolean isRequire() {
        return this.d;
    }

    public void setRequire(boolean var1) {
        this.d = var1;
    }

    public String getValue() {
        return this.e;
    }

    public void setValue(String var1) {
        this.e = var1;
    }

    public String getOutValue() {
        return this.f;
    }

    public void setOutValue(String var1) {
        this.f = var1;
    }

    public String getDefaultValue() {
        return this.g;
    }

    public void setDefaultValue(String var1) {
        this.g = var1;
    }

    public Boolean getOutBox() {
        return this.i;
    }

    public void setOutBox(Boolean var1) {
        this.i = var1;
    }

    public Object getTmpval() {
        return this.j;
    }

    public void setTmpval(Object var1) {
        this.j = var1;
    }
}
