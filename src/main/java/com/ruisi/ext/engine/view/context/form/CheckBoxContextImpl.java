//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.form.CheckBoxBuilder;
import java.util.List;

public class CheckBoxContextImpl extends AbstractInputContext implements CheckBoxContext {
    private String a;
    private List b;
    private String c;
    private String[] d;
    private String e;
    private Boolean f;
    private String g;

    public CheckBoxContextImpl() {
    }

    @Override
    public String[] getTarget() {
        return this.d;
    }

    @Override
    public void setTarget(String[] var1) {
        this.d = var1;
    }

    @Override
    public List loadOptions() {
        return this.b;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
        return clone;
    }

    @Override
    public void setOptions(List var1) {
        this.b = var1;
    }

    @Override
    public AbstractBuilder createBuilder() {
        return new CheckBoxBuilder(this);
    }

    @Override
    public String getDataId() {
        return this.c;
    }

    @Override
    public void setDataId(String var1) {
        this.c = var1;
    }

    @Override
    public String getTemplateName() {
        return this.a;
    }

    @Override
    public void setTemplateName(String var1) {
        this.a = var1;
    }

    @Override
    public String getRefDsource() {
        return this.e;
    }

    @Override
    public void setRefDsource(String var1) {
        this.e = var1;
    }

    @Override
    public String getInputType() {
        return "checkBox";
    }

    @Override
    public Boolean getShowSpan() {
        return this.f;
    }

    @Override
    public String getCheckboxStyle() {
        return this.g;
    }

    @Override
    public void setShowSpan(Boolean var1) {
        this.f = var1;
    }

    @Override
    public void setCheckboxStyle(String var1) {
        this.g = var1;
    }
}
