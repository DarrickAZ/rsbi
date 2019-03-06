//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

public class ColumnInfo {
    private String a;
    private String b;

    public ColumnInfo() {
    }

    public String getName() {
        return this.a;
    }

    public void setName(String var1) {
        this.a = var1;
    }

    public String getType() {
        return this.b;
    }

    public void setType(String var1) {
        this.b = var1;
    }

    public boolean isNumber() {
        return "Number".equals(this.b);
    }
}
