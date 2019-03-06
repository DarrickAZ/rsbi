//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.DataSetDimContext;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DimRule extends Rule {
    public DimRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        DataSetDimContext var4 = new DataSetDimContext();
        var4.setId(var3.getValue("id"));
        var4.setValues(var3.getValue("values"));
        String var5 = var3.getValue("use");
        String var6 = var3.getValue("order");
        String var7 = var3.getValue("size");
        String var8 = var3.getValue("type");
        var4.setType(var8);
        if (var7 != null && var7.length() > 0) {
            var4.setSize(Integer.parseInt(var7));
        }

        if (var5 != null && var5.length() > 0) {
            var4.setUse("true".equalsIgnoreCase(var5));
        }

        if (var6 != null && var6.length() > 0) {
            var4.setOrder("true".equalsIgnoreCase(var6));
        }

        DataCenterContext var9 = (DataCenterContext)this.digester.peek();
        if (var9.getDataSetConf().getDims() == null) {
            var9.getDataSetConf().setDims(new ArrayList());
        }

        var9.getDataSetConf().getDims().add(var4);
    }

    public void body(String var1, String var2, String var3) {
    }

    public void end(String var1, String var2) {
    }
}
