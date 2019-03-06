//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

public class CrossOutData {
    private CrossOutData$Header[][] a;
    private int b;
    private int c;
    private CrossOutData$Data[][] d;

    public CrossOutData$Header[][] getHeaders() {
        return this.a;
    }

    public CrossOutData$Data[][] getDatas() {
        return this.d;
    }

    public int getRowLevel() {
        return this.b;
    }

    public void setRowLevel(int var1) {
        this.b = var1;
    }

    public int getColLevel() {
        return this.c;
    }

    public void setColLevel(int var1) {
        this.c = var1;
    }

    CrossOutData(CrossOutData$Header[][] var1, CrossOutData$Data[][] var2) {
        this.a = var1;
        this.d = var2;
    }
}
