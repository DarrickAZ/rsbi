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
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.context.form.MultiSelectContextImpl;
import com.ruisi.ext.engine.view.context.form.SelectContext;
import com.ruisi.ext.engine.view.context.form.SelectContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class SelectRule extends Rule {
    public SelectRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("desc");
        String var5 = var3.getValue("id");
        String var6 = var3.getValue("data");
        String var7 = var3.getValue("require");
        String var8 = var3.getValue("size");
        String var9 = var3.getValue("value");
        String var10 = var3.getValue("cascade");
        String var11 = var3.getValue("multiple");
        String var12 = var3.getValue("defaultValue");
        String var13 = var3.getValue("addEmptyValue");
        String var14 = var3.getValue("outBox");
        String var15;
        if (var5 != null && var5.length() != 0) {
            var15 = null;
            Object var18;
            if (var11 != null && "true".equalsIgnoreCase(var11)) {
                var18 = new MultiSelectContextImpl();
            } else {
                var18 = new SelectContextImpl();
            }

            ((SelectContext)var18).setDefaultValue(var12);
            ((SelectContext)var18).setDataId(var6);
            ((SelectContext)var18).setId(var5);
            ((SelectContext)var18).setDesc(var4);
            ((SelectContext)var18).setRequire("true".equalsIgnoreCase(var7));
            ((SelectContext)var18).setSize(var8);
            ((SelectContext)var18).setValue(var9);
            ((SelectContext)var18).setCascade(var10);
            if ("true".equals(var13)) {
                ((SelectContext)var18).setAddEmptyValue(true);
            }

            if (var14 != null && var14.length() > 0) {
                ((SelectContext)var18).setOutBox("true".equalsIgnoreCase(var14));
            }

            Element var16 = (Element)this.digester.peek();
            if (var16.getChildren() == null) {
                var16.setChildren(new ArrayList());
            }

            var16.getChildren().add(var18);
            ((SelectContext)var18).setParent(var16);
            this.digester.push(var18);
            MVContext var17 = RuleUtils.findMVContext(var16);
            var17.setMvParam(var5, (InputField)var18);
            var17.setShowForm(true);
        } else {
            var15 = ConstantsEngine.replace(" $0 输入框未配置id.", "select");
            throw new ExtConfigException(var15);
        }
    }

    @Override
    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            SelectContext var4 = (SelectContext)this.digester.peek();
            String var5 = TemplateManager.getInstance().createTemplate(var3);
            var4.setTemplateName(var5);
        }
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
