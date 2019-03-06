//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.form.TreeBuilder;
import java.util.List;

public class TreeContextImpl extends AbstractInputContext implements TreeContext {
    private List a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;

    public TreeContextImpl() {
    }

    @Override
    public String getTemplateName() {
        return this.b;
    }

    @Override
    public void setTemplateName(String var1) {
        this.b = var1;
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
    public String getInputType() {
        return "tree";
    }

    @Override
    public AbstractBuilder createBuilder() {
        return new TreeBuilder(this);
    }

    @Override
    public List loadOptions() {
        return this.a;
    }

    @Override
    public void setOptions(List var1) {
        this.a = var1;
    }

    @Override
    public String getRefDsource() {
        return this.c;
    }

    @Override
    public void setRefDsource(String var1) {
        this.c = var1;
    }

    @Override
    public String getValueId() {
        return this.d;
    }

    @Override
    public String getValueText() {
        return this.e;
    }

    @Override
    public String getValuePid() {
        return this.f;
    }

    @Override
    public void setValueId(String var1) {
        this.d = var1;
    }

    @Override
    public void setValueText(String var1) {
        this.e = var1;
    }

    @Override
    public void setValuePid(String var1) {
        this.f = var1;
    }

    @Override
    public String getDefRootId() {
        return this.g;
    }

    @Override
    public void setDefRootId(String var1) {
        this.g = var1;
    }
}
