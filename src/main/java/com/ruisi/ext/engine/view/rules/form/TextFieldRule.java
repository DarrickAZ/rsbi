//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.form;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.form.TextFieldContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class TextFieldRule extends Rule {
    public TextFieldRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        TextFieldContextImpl var4 = new TextFieldContextImpl();
        String var5 = var3.getValue("desc");
        String var6 = var3.getValue("size");
        String var7 = var3.getValue("type");
        String var8 = var3.getValue("id");
        String var9 = var3.getValue("require");
        String var10 = var3.getValue("value");
        String var11 = var3.getValue("readOnly");
        String var12 = var3.getValue("defaultValue");
        String var13 = var3.getValue("show");
        String var14 = var3.getValue("outBox");
        if (var8 != null && var8.length() != 0) {
            var4.setDesc(var5);
            var4.setSize(var6);
            var4.setType(var7);
            var4.setValue(var10);
            var4.setId(var8);
            var4.setRequire("true".equalsIgnoreCase(var9));
            var4.setReadOnly(var11);
            var4.setDefaultValue(var12);
            if ("true".equalsIgnoreCase(var13)) {
                var4.setShow(true);
            }

            if (var14 != null && var14.length() > 0) {
                var4.setOutBox("true".equalsIgnoreCase(var14));
            }

            Element var17 = (Element)this.digester.peek();
            if (var17.getChildren() == null) {
                var17.setChildren(new ArrayList());
            }

            var17.getChildren().add(var4);
            var4.setParent(var17);
            MVContext var16 = RuleUtils.findMVContext(var17);
            var16.setMvParam(var8, var4);
            var16.setShowForm(true);
        } else {
            String var15 = ConstantsEngine.replace(" $0 输入框未配置id.", "textField");
            throw new ExtConfigException(var15);
        }
    }

    @Override
    public void end(String var1, String var2) {
    }
}
