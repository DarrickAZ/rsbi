//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import java.util.List;

public class AggreVO {
    private String a;
    private String b;
    private String c;
    private Boolean d;
    private String e;
    private List f;
    private List g;

    public AggreVO() {
    }

    public AggreVO(String var1, String var2) {
        this.a = var1;
        this.b = var2;
    }

    public String getType() {
        return this.a;
    }

    public String getName() {
        return this.b;
    }

    public String getAlias() {
        return this.c;
    }

    public Boolean getExpression() {
        return this.d;
    }

    public void setType(String var1) {
        this.a = var1;
    }

    public void setName(String var1) {
        this.b = var1;
    }

    public void setAlias(String var1) {
        this.c = var1;
    }

    public void setExpression(Boolean var1) {
        this.d = var1;
    }

    public String getFuncName() {
        return this.e;
    }

    public List getParams() {
        return this.f;
    }

    public void setFuncName(String var1) {
        this.e = var1;
    }

    public void setParams(List var1) {
        this.f = var1;
    }

    public List getParamAggre() {
        return this.g;
    }

    public void setParamAggre(List var1) {
        this.g = var1;
    }
}
