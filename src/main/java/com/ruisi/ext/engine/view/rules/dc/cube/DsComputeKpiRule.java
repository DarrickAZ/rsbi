//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.DsComputeKpiContext;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DsComputeKpiRule extends Rule {
    public DsComputeKpiRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("formula");
        String var5 = var3.getValue("name");
        DsComputeKpiContext var6 = new DsComputeKpiContext();
        var6.setFormula(var4);
        var6.setName(var5);
        DataCenterContext var7 = (DataCenterContext)this.digester.peek();
        if (var7.getDataSetConf().getComputeKpis() == null) {
            var7.getDataSetConf().setComputeKpis(new ArrayList());
        }

        var7.getDataSetConf().getComputeKpis().add(var6);
    }
}
