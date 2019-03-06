//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.context.cross.CrossFieldOther;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class CrossOtherRule extends Rule {
    public CrossOtherRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("id");
        String var5 = var3.getValue("type");
        String var6 = var3.getValue("alias");
        String var7 = var3.getValue("value");
        String var8 = var3.getValue("desc");
        String var9 = var3.getValue("formatPattern");
        String var10 = var3.getValue("aggregation");
        String var11 = var3.getValue("use");
        String var12 = var3.getValue("removeKey");
        CrossFieldOther var13 = new CrossFieldOther();
        var13.setId(var4);
        var13.setType(var5);
        var13.setAlias(var6);
        var13.setValue(var7);
        var13.setDesc(var8);
        var13.setFormatPattern(var9);
        var13.setAggregation(var10);
        var13.setRemoveKey(var12);
        if (var11 != null && var11.length() > 0) {
            var13.setUse("true".equalsIgnoreCase(var11));
        }

        Object var14 = this.digester.peek();
        if (!(var14 instanceof CrossField)) {
            throw new ExtConfigException("配置的crossOther必须在crossCol/crossRow下方.");
        } else {
            CrossField var15 = (CrossField)var14;
            if (var15.getOther() == null) {
                var15.setOther(new ArrayList());
            }

            var15.getOther().add(var13);
            var13.setParent(var15.getParent());
        }
    }

    @Override
    public void end(String var1, String var2) {
    }
}
