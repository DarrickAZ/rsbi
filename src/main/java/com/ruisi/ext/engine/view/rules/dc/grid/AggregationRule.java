//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.AggreVO;
import com.ruisi.ext.engine.view.context.dc.grid.GridAggregationContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class AggregationRule extends Rule {
    private GridAggregationContext a;

    public AggregationRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("column");
        boolean var5 = "true".equalsIgnoreCase(var3.getValue("toExt"));
        this.a = new GridAggregationContext();
        if (var4 != null) {
            this.a.setColumn(var4.split(","));
        }

        this.a.setToExt(var5);
        GridDataCenterContext var6 = (GridDataCenterContext)this.digester.peek();
        var6.getProcess().add(this.a);
        ArrayList var7 = new ArrayList();
        this.digester.push(var7);
    }

    public void end(String var1, String var2) {
        List var3 = (List)this.digester.pop();
        AggreVO[] var4 = new AggreVO[var3.size()];

        for(int var5 = 0; var5 < var3.size(); ++var5) {
            var4[var5] = (AggreVO)var3.get(var5);
        }

        this.a.setAggreVO(var4);
    }
}
