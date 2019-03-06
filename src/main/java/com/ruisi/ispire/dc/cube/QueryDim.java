//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube;

import java.util.List;

public class QueryDim {
    private String a;
    private String b;
    private String c;
    private String d;
    private List e;
    private List f;

    public QueryDim() {
    }

    public List getInputValues() {
        return this.f;
    }

    public void setInputValues(List var1) {
        this.f = var1;
    }

    public List getValues() {
        return this.e;
    }

    public void setValues(List var1) {
        this.e = var1;
    }

    public String getType() {
        return this.b;
    }

    public void setType(String var1) {
        this.b = var1;
    }

    public String getDimCol() {
        return this.a;
    }

    public void setDimCol(String var1) {
        this.a = var1;
    }

    public String getDimDescCol() {
        return this.c;
    }

    public void setDimDescCol(String var1) {
        this.c = var1;
    }

    public String getOrderCol() {
        return this.d;
    }

    public void setOrderCol(String var1) {
        this.d = var1;
    }

    public String toString() {
        return " type = " + this.b + ", id = " + this.a;
    }
}
