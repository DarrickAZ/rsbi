//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.LinkBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class LinkContextImpl extends AbstractContext implements LinkContext {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private boolean h;
    private String i;
    private String j;

    public LinkContextImpl() {
    }

    public String getText() {
        return this.b;
    }

    public void setText(String var1) {
        this.b = var1;
    }

    public AbstractBuilder createBuilder() {
        return new LinkBuilder(this);
    }

    public String getAction() {
        return this.a;
    }

    public void setAction(String var1) {
        this.a = var1;
    }

    public String getMethod() {
        return this.c;
    }

    public void setMethod(String var1) {
        this.c = var1;
    }

    public String getOnClick() {
        return this.d;
    }

    public void setOnClick(String var1) {
        this.d = var1;
    }

    public String getFrom() {
        return this.e;
    }

    public void setFrom(String var1) {
        this.e = var1;
    }

    public String getStyleClass() {
        return this.f;
    }

    public void setStyleClass(String var1) {
        this.f = var1;
    }

    public String getSrc() {
        return this.g;
    }

    public void setSrc(String var1) {
        this.g = var1;
    }

    public boolean isAllowParam() {
        return this.h;
    }

    public void setAllowParam(boolean var1) {
        this.h = var1;
    }

    public String getWindowState() {
        return this.i;
    }

    public void setWindowState(String var1) {
        this.i = var1;
    }

    public String getOtherParam() {
        return this.j;
    }

    public void setOtherParam(String var1) {
        this.j = var1;
    }
}
