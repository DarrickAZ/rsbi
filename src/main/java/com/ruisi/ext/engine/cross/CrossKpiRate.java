//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import java.math.BigDecimal;

public class CrossKpiRate {
    private String a;
    private BigDecimal b;
    private String c;

    public CrossKpiRate() {
    }

    public String getRateName() {
        return this.c;
    }

    public void setRateName(String var1) {
        this.c = var1;
    }

    public BigDecimal getRate() {
        return this.b;
    }

    public void setRate(BigDecimal var1) {
        this.b = var1;
    }

    public String getKpiCode() {
        return this.a;
    }

    public void setKpiCode(String var1) {
        this.a = var1;
    }
}
