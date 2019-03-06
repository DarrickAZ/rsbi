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
import com.ruisi.ext.engine.view.context.form.CheckBoxContext;
import com.ruisi.ext.engine.view.context.form.CheckBoxContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class CheckBoxRule extends Rule {
    public CheckBoxRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        CheckBoxContextImpl var4 = new CheckBoxContextImpl();
        String var5 = var3.getValue("desc");
        String var6 = var3.getValue("id");
        String var7 = var3.getValue("data");
        String var8 = var3.getValue("require");
        String var9 = var3.getValue("value");
        String var10 = var3.getValue("defaultValue");
        String var11 = var3.getValue("target");
        String var12 = var3.getValue("showSpan");
        String var13 = var3.getValue("checkboxStyle");
        String var14 = var3.getValue("outBox");
        if (var6 != null && var6.length() != 0) {
            var4.setDataId(var7);
            var4.setId(var6);
            var4.setDesc(var5);
            var4.setDefaultValue(var10);
            var4.setRequire("true".equalsIgnoreCase(var8));
            var4.setShowSpan("true".equalsIgnoreCase(var12));
            var4.setValue(var9);
            if (var11 != null && var11.length() > 0) {
                var4.setTarget(var11.split(","));
            }

            var4.setCheckboxStyle(var13);
            if (var14 != null && var14.length() > 0) {
                var4.setOutBox("true".equalsIgnoreCase(var14));
            }

            Element var17 = (Element)this.digester.peek();
            if (var17.getChildren() == null) {
                var17.setChildren(new ArrayList());
            }

            var17.getChildren().add(var4);
            var4.setParent(var17);
            this.digester.push(var4);
            MVContext var16 = RuleUtils.findMVContext(var17);
            var16.setMvParam(var6, var4);
            var16.setShowForm(true);
        } else {
            String var15 = ConstantsEngine.replace(" $0 输入框未配置id.", "checkBox");
            throw new ExtConfigException(var15);
        }
    }

    @Override
    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            CheckBoxContext var4 = (CheckBoxContext)this.digester.peek();
            String var5 = TemplateManager.getInstance().createTemplate(var3);
            var4.setTemplateName(var5);
        }
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
