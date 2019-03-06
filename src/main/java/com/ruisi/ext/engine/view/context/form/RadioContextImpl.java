//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.control.ActionProcess;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.form.RadioBuilder;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

import java.util.List;
import java.util.Map;

public class RadioContextImpl extends AbstractInputContext implements RadioContext {
    private List a;
    private String b;
    private String c;
    private Boolean d;
    private String e;
    private String f;
    private String[] g;

    public RadioContextImpl() {
    }

    @Override
    public void check() throws ExtConfigException {
        if (this.a != null) {
            for(int var1 = 0; var1 < this.a.size(); ++var1) {
                Map var2 = (Map)this.a.get(var1);
                String var3 = (String)var2.get("ref");
                if (var3 != null && var3.length() > 0) {
                    MVContext var4 = ActionProcess.findMVContextById(var3);
                    var4.setFromRef(true);
                }
            }

        }
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
    public AbstractBuilder createBuilder() {
        return new RadioBuilder(this);
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
    public String getTemplateName() {
        return this.b;
    }

    @Override
    public void setTemplateName(String var1) {
        this.b = var1;
    }

    @Override
    public String getOnClick() {
        return this.c;
    }

    @Override
    public void setOnClick(String var1) {
        this.c = var1;
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
        return "radio";
    }

    @Override
    public Boolean getShowSpan() {
        return this.d;
    }

    @Override
    public void setShowSpan(Boolean var1) {
        this.d = var1;
    }

    @Override
    public String getRadioStyle() {
        return this.f;
    }

    @Override
    public void setRadioStyle(String var1) {
        this.f = var1;
    }

    @Override
    public String[] getTarget() {
        return this.g;
    }

    @Override
    public void setTarget(String[] var1) {
        this.g = var1;
    }
}
