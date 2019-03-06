//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

public class CrossOutData$Data {
    Object a;
    int b;
    int c = 1;
    Object d;
    String e;
    int f = 1;
    int g;

    public String getFmt() {
        return this.e;
    }

    public void setFmt(String var1) {
        this.e = var1;
    }

    public Object getTrueValue() {
        return this.d;
    }

    public void setTrueValue(Object var1) {
        this.d = var1;
    }

    public int getColSpan() {
        return this.c;
    }

    public void setColSpan(int var1) {
        this.c = var1;
    }

    public int getRowSpan() {
        return this.f;
    }

    public void setRowSpan(int var1) {
        this.f = var1;
    }

    public int getType() {
        return this.b;
    }

    public void setType(int var1) {
        this.b = var1;
    }

    public Object getValue() {
        return this.a;
    }

    public CrossOutData$Data(Object var1) {
        this.a = var1;
    }

    public int getLevel() {
        return this.g;
    }

    public void setLevel(int var1) {
        this.g = var1;
    }

    public String toString() {
        return "value=" + this.a + ",colspan=" + this.c + ",rowspan=" + this.f;
    }
}
