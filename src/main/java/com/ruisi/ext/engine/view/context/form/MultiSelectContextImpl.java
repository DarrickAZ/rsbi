//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.form.SelectBuilder;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.List;
import java.util.Map;

public class MultiSelectContextImpl extends AbstractInputContext implements MultiSelectContext {
    private List a;
    private String b;
    private String c;
    private String d;
    private String e;
    private Boolean f;

    public MultiSelectContextImpl() {
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
        return new SelectBuilder(this, true);
    }

    @Override
    public String getDataId() {
        return this.b;
    }

    @Override
    public void setDataId(String var1) {
        this.b = var1;
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
        return this.c;
    }

    @Override
    public void setTemplateName(String var1) {
        this.c = var1;
    }

    @Override
    public String getCascade() {
        return this.d;
    }

    @Override
    public void setCascade(String var1) {
        this.d = var1;
    }

    @Override
    public void check() throws ExtConfigException {
        if (this.d != null && this.d.length() > 0) {
            MVContext var1 = RuleUtils.findMVContext(this);
            Map var2 = var1.getMvParams();
            if (!var2.containsKey(this.d)) {
                String var3 = ConstantsEngine.replace("配置的级联参数 $0 在文件 $1 (xml)中不存在.", this.d, var1.getMvid());
                throw new ExtConfigException(var3);
            }
        }

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
        return "mselect";
    }

    @Override
    public Boolean getAddEmptyValue() {
        return this.f;
    }

    @Override
    public void setAddEmptyValue(Boolean var1) {
        this.f = var1;
    }
}
