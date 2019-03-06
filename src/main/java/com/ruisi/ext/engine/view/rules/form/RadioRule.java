//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.form;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.form.RadioContext;
import com.ruisi.ext.engine.view.context.form.RadioContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class RadioRule extends Rule {
    public RadioRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        RadioContextImpl var4 = new RadioContextImpl();
        String var5 = var3.getValue("desc");
        String var6 = var3.getValue("id");
        String var7 = var3.getValue("require");
        String var8 = var3.getValue("size");
        String var9 = var3.getValue("value");
        String var10 = var3.getValue("defaultValue");
        String var11 = var3.getValue("onClick");
        String var12 = var3.getValue("showSpan");
        String var13 = var3.getValue("radioStyle");
        String var14 = var3.getValue("target");
        String var15 = var3.getValue("outBox");
        if (var6 != null && var6.length() != 0) {
            var4.setId(var6);
            var4.setDefaultValue(var10);
            var4.setDesc(var5);
            var4.setOnClick(var11);
            var4.setRequire("true".equalsIgnoreCase(var7));
            var4.setShowSpan("true".equalsIgnoreCase(var12));
            var4.setSize(var8);
            var4.setValue(var9);
            var4.setRadioStyle(var13);
            if (var14 != null && var14.length() > 0) {
                var4.setTarget(var14.split(","));
            }

            if (var15 != null && var15.length() > 0) {
                var4.setOutBox("true".equalsIgnoreCase(var15));
            }

            Element var18 = (Element)this.digester.peek();
            if (var18.getChildren() == null) {
                var18.setChildren(new ArrayList());
            }

            var18.getChildren().add(var4);
            var4.setParent(var18);
            this.digester.push(var4);
            MVContext var17 = RuleUtils.findMVContext(var18);
            try {
                var17.setMvParam(var6, var4);
            } catch (ExtConfigException e) {
                e.printStackTrace();
            }
            var17.setShowForm(true);
        } else {
            String var16 = ConstantsEngine.replace(" $0 输入框未配置id.", "radio");
            throw new ExtConfigException(var16);
        }
    }

    @Override
    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            RadioContext var4 = (RadioContext)this.digester.peek();
            String var5 = TemplateManager.getInstance().createTemplate(var3);
            var4.setTemplateName(var5);
        }
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
