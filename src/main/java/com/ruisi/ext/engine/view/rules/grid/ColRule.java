//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.grid;

import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.form.CheckBoxContextImpl;
import com.ruisi.ext.engine.view.context.form.RadioContextImpl;
import com.ruisi.ext.engine.view.context.grid.ColConfigContext;
import com.ruisi.ext.engine.view.context.grid.ColContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.test.TestUtils;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ColRule extends Rule {
    public ColRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("alias");
        String var5 = var3.getValue("desc");
        String var6 = var3.getValue("formatPattern");
        String var7 = var3.getValue("width");
        String var8 = var3.getValue("financeFmt");
        String var9 = var3.getValue("type");
        String var10 = var3.getValue("id");
        String var11 = var3.getValue("require");
        String var12 = var3.getValue("test");
        String var13 = var3.getValue("order");
        String var14 = var3.getValue("length");
        String var15 = var3.getValue("align");
        String var16 = var3.getValue("class");
        String var17 = var3.getValue("alt");
        String var18 = var3.getValue("jsFunc");
        ColConfigContext var19 = (ColConfigContext)this.digester.peek();
        ColContext var20 = new ColContext(var19.getDataGridContext());
        var20.setAlias(var4);
        var20.setDesc(var5);
        var20.setFinanceFmt("true".equalsIgnoreCase(var8));
        var20.setFormatPattern(var6);
        var20.setType(var9);
        var20.setId(var10);
        var20.setWidth(var7);
        var20.setAlign(var15);
        var20.setAlt(var17);
        var20.setJsFunc(var18);
        var20.setStyleClass(var16);
        if (var14 != null && var14.length() > 0) {
            var20.setLength(Integer.parseInt(var14));
        }

        var20.setOrder("true".equalsIgnoreCase(var13));
        if (var12 != null && var12.length() > 0) {
            var20.setTestAdapter(TestUtils.createTest(var12));
        }

        var19.putColContext(var20);
        MVContext var22;
        if ("checkbox".equalsIgnoreCase(var9)) {
            CheckBoxContextImpl var21 = new CheckBoxContextImpl();
            var21.setId(var10);
            var21.setDesc(var5);
            var21.setRequire("true".equalsIgnoreCase(var11));
            var22 = RuleUtils.findMVContext(var20.getDataGridContext());
            if (var22 != null) {
                var22.setMvParam(var10, var21);
                var22.setShowForm(true);
            }
        }

        if ("radio".equalsIgnoreCase(var9)) {
            RadioContextImpl var23 = new RadioContextImpl();
            var23.setId(var10);
            var23.setDesc(var5);
            var23.setRequire("true".equalsIgnoreCase(var11));
            var22 = RuleUtils.findMVContext(var20.getDataGridContext());
            if (var22 != null) {
                var22.setMvParam(var10, var23);
                var22.setShowForm(true);
            }
        }

        this.digester.push(var20);
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
