//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.AggregationContext;
import java.util.ArrayList;

import com.ruisi.ispire.dc.cube.operation.DataSetAggregation$KpiAggreType;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class TypeRule extends Rule {
    public TypeRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("kpi");
        String var5 = var3.getValue("type");
        DataSetAggregation$KpiAggreType var6 = new DataSetAggregation$KpiAggreType();
        var6.setKpi(var4);
        var6.setType(var5);
        AggregationContext var7 = (AggregationContext)this.digester.peek();
        if (var7.getAggreTypes() == null) {
            var7.setAggreTypes(new ArrayList());
        }

        var7.getAggreTypes().add(var6);
    }

    @Override
    public void end(String var1, String var2) {
    }
}
