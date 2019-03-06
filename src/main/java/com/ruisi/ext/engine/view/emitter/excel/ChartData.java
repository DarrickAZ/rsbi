//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.excel;

import javax.servlet.ServletContext;

public class ChartData {
    private String a;
    private int b;
    private int c;
    private String d;
    private String e;
    private ServletContext f;

    public ChartData() {
    }

    public String getJson() {
        return this.a;
    }

    public void setJson(String var1) {
        this.a = var1;
    }

    public int getWidth() {
        return this.b;
    }

    public int getHeight() {
        return this.c;
    }

    public void setWidth(int var1) {
        this.b = var1;
    }

    public void setHeight(int var1) {
        this.c = var1;
    }

    public String getTitle() {
        return this.d;
    }

    public void setTitle(String var1) {
        this.d = var1;
    }

    public ServletContext getContext() {
        return this.f;
    }

    public void setContext(ServletContext var1) {
        this.f = var1;
    }

    public String getPicinfo() {
        return this.e;
    }

    public void setPicinfo(String var1) {
        this.e = var1;
    }
}
