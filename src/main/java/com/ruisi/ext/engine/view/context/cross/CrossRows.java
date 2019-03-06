//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.cross;

import java.util.List;

public class CrossRows implements Cloneable {
    private List a;
    private List b;
    private RowLinkContext c;
    private Boolean d;
    private Boolean e;
    private int f;

    public CrossRows() {
    }

    @Override
    public CrossRows clone() throws CloneNotSupportedException {
        this.b = null;
        return (CrossRows)super.clone();
    }

    public List getRows() {
        return this.a;
    }

    public void setRows(List var1) {
        this.a = var1;
    }

    public int getMaxLevel() {
        return this.f;
    }

    public void setMaxLevel(int var1) {
        this.f = var1;
    }

    public List getTmpRows() {
        return this.b;
    }

    public void setTmpRows(List var1) {
        if (this.b == null) {
            this.b = var1;
        }

    }

    public RowLinkContext getLink() {
        return this.c;
    }

    public void setLink(RowLinkContext var1) {
        this.c = var1;
    }

    public Boolean getOverlapped() {
        return this.d;
    }

    public void setOverlapped(Boolean var1) {
        this.d = var1;
    }

    public Boolean getUnmerge() {
        return this.e;
    }

    public void setUnmerge(Boolean var1) {
        this.e = var1;
    }
}
