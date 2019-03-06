//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.KpiDescBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;
import java.util.ArrayList;
import java.util.List;

public class KpiDescContextImpl extends AbstractContext implements KpiDescContext {
    private String a;
    private List b;
    private List c = new ArrayList();

    public KpiDescContextImpl() {
    }

    @Override
    public String getImpl() {
        return this.a;
    }

    @Override
    public void setImpl(String var1) {
        this.a = var1;
    }

    @Override
    public AbstractBuilder createBuilder() {
        return new KpiDescBuilder(this);
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }

    @Override
    public List getKpis() {
        return this.c;
    }

    @Override
    public void setKpis(List var1) {
        this.c = var1;
    }

    @Override
    public List loadOptions() {
        return this.b;
    }

    @Override
    public void setOptions(List var1) {
        this.b = var1;
    }
}
