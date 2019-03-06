//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.control.ActionProcess;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.form.ButtonBuilder;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

public class ButtonContextImpl extends AbstractInputContext implements ButtonContext {
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
    private String k;
    private String[] l;
    private String m;
    private String n;

    public ButtonContextImpl() {
    }

    @Override
    public void check() throws ExtConfigException {
        if (this.l != null) {
            String[] var4;
            int var3 = (var4 = this.l).length;

            for(int var2 = 0; var2 < var3; ++var2) {
                String var1 = var4[var2];
                ActionProcess.findMVContextById(var1, false);
            }
        }

    }

    @Override
    public String[] getMvId() {
        return this.l;
    }

    @Override
    public void setMvId(String[] var1) {
        this.l = var1;
    }

    @Override
    public String getTarget() {
        return this.k;
    }

    @Override
    public void setTarget(String var1) {
        this.k = var1;
    }

    @Override
    public AbstractBuilder createBuilder() {
        return new ButtonBuilder(this);
    }

    @Override
    public String getType() {
        return this.a;
    }

    @Override
    public void setType(String var1) {
        this.a = var1;
    }

    @Override
    public String getAction() {
        return this.b;
    }

    @Override
    public void setAction(String var1) {
        this.b = var1;
    }

    @Override
    public String getMethod() {
        return this.c;
    }

    @Override
    public void setMethod(String var1) {
        this.c = var1;
    }

    @Override
    public String getFrom() {
        return this.d;
    }

    @Override
    public void setFrom(String var1) {
        this.d = var1;
    }

    @Override
    public String getSubmit() {
        return this.e;
    }

    @Override
    public void setSubmit(String var1) {
        this.e = var1;
    }

    @Override
    public String getCheckParam() {
        return this.f;
    }

    @Override
    public void setCheckParam(String var1) {
        this.f = var1;
    }

    @Override
    public String getOnClick() {
        return this.g;
    }

    @Override
    public void setOnClick(String var1) {
        this.g = var1;
    }

    @Override
    public boolean isConfirm() {
        return this.h;
    }

    @Override
    public void setConfirm(boolean var1) {
        this.h = var1;
    }

    @Override
    public String getSrc() {
        return this.i;
    }

    @Override
    public void setSrc(String var1) {
        this.i = var1;
    }

    @Override
    public String getExportDataGrid() {
        return this.j;
    }

    @Override
    public void setExportDataGrid(String var1) {
        this.j = var1;
    }

    @Override
    public String getInputType() {
        return "button";
    }

    @Override
    public String getIconCls() {
        return this.m;
    }

    @Override
    public void setIconCls(String var1) {
        this.m = var1;
    }

    @Override
    public String getStyleClass() {
        return this.n;
    }

    @Override
    public void setStyleClass(String var1) {
        this.n = var1;
    }
}
