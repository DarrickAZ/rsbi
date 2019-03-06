//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.form.DateSelectBuilder;

public class DateSelectContextImpl extends AbstractInputContext implements DateSelectContext {
    private String a;
    private String b;
    private String c;
    private Boolean d;
    private String[] e;
    private String f = "day";

    public DateSelectContextImpl() {
    }

    public AbstractBuilder createBuilder() {
        return new DateSelectBuilder(this);
    }

    public String getInputType() {
        return "dateSelect";
    }

    public String getDateFormat() {
        return this.a;
    }

    public String getMaxDate() {
        return this.b;
    }

    public String getMinDate() {
        return this.c;
    }

    public void setDateFormat(String var1) {
        this.a = var1;
    }

    public void setMaxDate(String var1) {
        this.b = var1;
    }

    public void setMinDate(String var1) {
        this.c = var1;
    }

    public Boolean getShowCalendar() {
        return this.d;
    }

    public void setShowCalendar(Boolean var1) {
        this.d = var1;
    }

    public String[] getTarget() {
        return this.e;
    }

    public void setTarget(String[] var1) {
        this.e = var1;
    }

    public String getDateType() {
        return this.f;
    }

    public void setDateType(String var1) {
        this.f = var1;
    }
}
