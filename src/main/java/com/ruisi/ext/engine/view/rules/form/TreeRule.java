//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.form;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.form.TreeContext;
import com.ruisi.ext.engine.view.context.form.TreeContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class TreeRule extends Rule {
    public TreeRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("desc");
        String var5 = var3.getValue("id");
        String var6 = var3.getValue("require");
        String var7 = var3.getValue("size");
        String var8 = var3.getValue("value");
        String var9 = var3.getValue("defaultValue");
        String var10 = var3.getValue("valueId");
        String var11 = var3.getValue("valueText");
        String var12 = var3.getValue("valuePid");
        String var13 = var3.getValue("defRootId");
        if (var10 != null && var10.length() != 0) {
            if (var11 != null && var11.length() != 0) {
                if (var12 != null && var12.length() != 0) {
                    if (var13 != null && var13.length() != 0) {
                        TreeContextImpl var14 = new TreeContextImpl();
                        var14.setDefaultValue(var9);
                        var14.setId(var5);
                        var14.setDesc(var4);
                        var14.setRequire("true".equalsIgnoreCase(var6));
                        var14.setSize(var7);
                        var14.setValue(var8);
                        var14.setValueId(var10);
                        var14.setValuePid(var12);
                        var14.setValueText(var11);
                        var14.setDefRootId(var13);
                        Element var15 = (Element)this.digester.peek();
                        if (var15.getChildren() == null) {
                            var15.setChildren(new ArrayList());
                        }

                        var15.getChildren().add(var14);
                        var14.setParent(var15);
                        this.digester.push(var14);
                        MVContext var16 = RuleUtils.findMVContext(var15);
                        var16.setMvParam(var5, var14);
                        var16.setShowForm(true);
                    } else {
                        throw new ExtConfigException("tree 标签未配置 defRootId");
                    }
                } else {
                    throw new ExtConfigException("tree 标签未配置 valuePid");
                }
            } else {
                throw new ExtConfigException("tree 标签未配置 valueText");
            }
        } else {
            throw new ExtConfigException("tree 标签未配置 valueId");
        }
    }

    @Override
    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            TreeContext var4 = (TreeContext)this.digester.peek();
            String var5 = TemplateManager.getInstance().createTemplate(var3);
            var4.setTemplateName(var5);
        }
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
