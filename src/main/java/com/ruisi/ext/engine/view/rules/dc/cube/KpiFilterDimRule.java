//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataSetDimContext;
import com.ruisi.ext.engine.view.context.dc.cube.DsKpiFilterContext;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class KpiFilterDimRule extends Rule {
    public KpiFilterDimRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("id");
        String var5 = var3.getValue("value");
        DataSetDimContext var6 = new DataSetDimContext();
        var6.setId(var4);
        var6.setValues(var5);
        DsKpiFilterContext var7 = (DsKpiFilterContext)this.digester.peek();
        if (var7.getFilterDims() == null) {
            var7.setFilterDims(new ArrayList());
        }

        var7.getFilterDims().add(var6);
    }

    public void end(String var1, String var2) {
    }
}
