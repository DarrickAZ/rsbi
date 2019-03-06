//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.form.TextFieldBuilder;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

import java.util.Map;

public class TextFieldContextImpl extends AbstractInputContext implements TextFieldContext {
    private String a;
    private String b;
    private String c;

    public TextFieldContextImpl() {
    }

    @Override
    public AbstractBuilder createBuilder() {
        return new TextFieldBuilder(this);
    }

    @Override
    public String getType() {
        return this.c;
    }

    @Override
    public void setType(String var1) {
        this.c = var1;
    }

    @Override
    public String getReadOnly() {
        return this.a;
    }

    @Override
    public void setReadOnly(String var1) {
        this.a = var1;
    }

    public String getCascade() {
        return this.b;
    }

    @Override
    public void check() throws ExtConfigException {
        if (this.b != null && this.b.length() > 0) {
            MVContext var1 = RuleUtils.findMVContext(this);
            Map var2 = var1.getMvParams();
            if (!var2.containsKey(this.b)) {
                String var3 = ConstantsEngine.replace("配置的级联参数 $0 在文件 $1 (xml)中不存在.", this.b, var1.getMvid());
                throw new ExtConfigException(var3);
            }
        }

    }

    public void setCascade(String var1) {
        this.b = var1;
    }

    @Override
    public String getInputType() {
        return "text";
    }
}
