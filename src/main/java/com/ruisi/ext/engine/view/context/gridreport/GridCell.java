//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.gridreport;

public class GridCell {
    private int a = 1;
    private int b = 1;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private boolean h = false;
    private String i;
    private boolean j = false;
    private String k;
    private Boolean l;
    private GridCellLink m;

    public GridCell() {
    }

    public String toString() {
        return " desc=" + this.c + ", " + "alias=" + this.e + ", colSpan=" + this.a + ", rowSpan=" + this.b;
    }

    public int getColSpan() {
        return this.a;
    }

    public void setColSpan(int var1) {
        this.a = var1;
    }

    public int getRowSpan() {
        return this.b;
    }

    public void setRowSpan(int var1) {
        this.b = var1;
    }

    public String getDesc() {
        return this.c;
    }

    public void setDesc(String var1) {
        this.c = var1;
    }

    public String getAlign() {
        return this.d;
    }

    public void setAlign(String var1) {
        this.d = var1;
    }

    public String getAlias() {
        return this.e;
    }

    public void setAlias(String var1) {
        this.e = var1;
    }

    public String getFormatPattern() {
        return this.g;
    }

    public void setFormatPattern(String var1) {
        this.g = var1;
    }

    public boolean isOrder() {
        return this.h;
    }

    public void setOrder(boolean var1) {
        this.h = var1;
    }

    public String getStyleClass() {
        return this.i;
    }

    public void setStyleClass(String var1) {
        this.i = var1;
    }

    public boolean isFinanceFmt() {
        return this.j;
    }

    public void setFinanceFmt(boolean var1) {
        this.j = var1;
    }

    public String getJsFunc() {
        return this.k;
    }

    public void setJsFunc(String var1) {
        this.k = var1;
    }

    public Boolean getDynamicText() {
        return this.l;
    }

    public void setDynamicText(Boolean var1) {
        this.l = var1;
    }

    public String getWidth() {
        return this.f;
    }

    public void setWidth(String var1) {
        this.f = var1;
    }

    public GridCellLink getLink() {
        return this.m;
    }

    public void setLink(GridCellLink var1) {
        this.m = var1;
    }
}
