//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class KpiRule extends Rule {
    public KpiRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("order");
        DataCenterContext var5 = (DataCenterContext)this.digester.peek();
        if (var4 != null && var4.length() > 0) {
            var5.getDataSetConf().setKpiOrder("true".equalsIgnoreCase(var4));
        }

    }

    @Override
    public void body(String var1, String var2, String var3) throws ExtConfigException {
        if (var3 != null && var3.length() != 0) {
            DataCenterContext var4 = (DataCenterContext)this.digester.peek();
            String[] var5 = var3.split(",");
            ArrayList var6 = new ArrayList();
            String[] var10 = var5;
            int var9 = var5.length;

            for(int var8 = 0; var8 < var9; ++var8) {
                String var7 = var10[var8];
                var6.add(var7);
            }

            var4.getDataSetConf().setKpis(var6);
        } else {
            throw new ExtConfigException(" dataCenter 中未配置指标. ");
        }
    }

    @Override
    public void end(String var1, String var2) {
    }
}
