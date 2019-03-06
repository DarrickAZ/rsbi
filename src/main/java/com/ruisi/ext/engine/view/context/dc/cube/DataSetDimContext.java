//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

public class DataSetDimContext implements Cloneable {
    private String a;
    private String b;
    private boolean c = true;
    private boolean d = false;
    private String e;
    private int f;

    public DataSetDimContext() {
    }

    public String getType() {
        return this.e;
    }

    public void setType(String var1) {
        this.e = var1;
    }

    public int getSize() {
        return this.f;
    }

    public void setSize(int var1) {
        this.f = var1;
    }

    public boolean isOrder() {
        return this.d;
    }

    public void setOrder(boolean var1) {
        this.d = var1;
    }

    public boolean isUse() {
        return this.c;
    }

    public void setUse(boolean var1) {
        this.c = var1;
    }

    public String getId() {
        return this.a;
    }

    public void setId(String var1) {
        this.a = var1;
    }

    public String getValues() {
        return this.b;
    }

    public void setValues(String var1) {
        this.b = var1;
    }

    @Override
    public DataSetDimContext clone() throws CloneNotSupportedException {
        return (DataSetDimContext)super.clone();
    }
}
