//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.DimOtherContext;
import com.ruisi.ext.engine.view.context.cross.RowDimContext;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class RowDimOtherRule extends Rule {
    public RowDimOtherRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("code");
        String var5 = var3.getValue("type");
        DimOtherContext var6 = new DimOtherContext();
        var6.setCode(var4);
        var6.setType(var5);
        RowDimContext var7 = (RowDimContext)this.digester.peek();
        Object var8 = var7.getOthers();
        if (var8 == null) {
            var8 = new ArrayList();
            var7.setOthers((List)var8);
        }

        ((List)var8).add(var6);
    }

    public void end(String var1, String var2) {
    }
}
