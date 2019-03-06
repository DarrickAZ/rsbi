//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

public class CrossOutData$Header {
    String a;
    int b = 1;
    int c = 1;
    int d = 0;

    public int getColSpan() {
        return this.b;
    }

    public void setColSpan(int var1) {
        this.b = var1;
    }

    public int getRowSpan() {
        return this.c;
    }

    public void setRowSpan(int var1) {
        this.c = var1;
    }

    public String getName() {
        return this.a;
    }

    public CrossOutData$Header(String var1) {
        this.a = var1;
    }

    public String toString() {
        return "name=" + this.a + ",colspan=" + this.b + ",rowspan=" + this.c;
    }

    public int getLevel() {
        return this.d;
    }

    public void setLevel(int var1) {
        this.d = var1;
    }
}
