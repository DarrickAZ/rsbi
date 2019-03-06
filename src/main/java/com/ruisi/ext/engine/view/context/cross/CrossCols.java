//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.cross;

import java.util.List;

public class CrossCols implements Cloneable {
    private List a;
    private int b;
    private List c;
    private ColLinkContext d;

    public CrossCols() {
    }

    @Override
    public CrossCols clone() throws CloneNotSupportedException {
        this.c = null;
        return (CrossCols)super.clone();
    }

    public List getCols() {
        return this.a;
    }

    public void setCols(List var1) {
        this.a = var1;
    }

    public int getMaxLevel() {
        return this.b;
    }

    public void setMaxLevel(int var1) {
        this.b = var1;
    }

    public List getTmpCols() {
        return this.c;
    }

    public void setTmpCols(List var1) {
        if (this.c == null) {
            this.c = var1;
        }

    }

    public ColLinkContext getColLink() {
        return this.d;
    }

    public void setColLink(ColLinkContext var1) {
        this.d = var1;
    }
}
