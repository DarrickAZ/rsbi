//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.grid;

import com.ruisi.ext.engine.view.context.grid.ColContext;
import com.ruisi.ext.engine.view.context.grid.ColLinkContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.test.TestUtils;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ColLinkRule extends Rule {
    public ColLinkRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("byAlias");
        String var5 = var3.getValue("action");
        String var6 = var3.getValue("method");
        String var7 = var3.getValue("desc");
        String var8 = var3.getValue("target");
        String var9 = var3.getValue("ajax");
        String var10 = var3.getValue("styleClass");
        String var11 = var3.getValue("test");
        String var12 = var3.getValue("htmlTarget");
        String var13 = var3.getValue("params");
        String var14 = var3.getValue("otherParams");
        ColLinkContext var15 = new ColLinkContext();
        if (var4 != null && var4.length() > 0) {
            var15.setByAlias(var4.split(","));
        }

        var15.setAction(var5);
        var15.setMethod(var6);
        var15.setDesc(var7);
        var15.setTarget(var8);
        var15.setHtmlTarget(var12);
        var15.setStyleClass(var10);
        var15.setOtherParams(var14);
        var15.setAjax("true".equalsIgnoreCase(var9));
        ArrayList var16 = null;
        if (var13 != null && var13.length() > 0) {
            var16 = new ArrayList();
            String[] var17 = var13.split(",");
            if (var17 != null) {
                String[] var21 = var17;
                int var20 = var17.length;

                for(int var19 = 0; var19 < var20; ++var19) {
                    String var18 = var21[var19];
                    var16.add(var18);
                }
            }
        }

        var15.setParams(var16);
        if (var11 != null && var11.length() > 0) {
            var15.setTestAdapter(TestUtils.createTest(var11));
        }

        ColContext var22 = (ColContext)this.digester.peek();
        var22.getLinkCtxs().add(var15);
    }

    @Override
    public void end(String var1, String var2) {
    }
}
