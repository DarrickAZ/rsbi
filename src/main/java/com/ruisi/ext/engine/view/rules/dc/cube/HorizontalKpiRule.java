//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;

public class HorizontalKpiRule extends Rule {
    public HorizontalKpiRule() {
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() > 0) {
            DataCenterContext var4 = (DataCenterContext)this.digester.peek();
            String[] var5 = var3.split(",");
            ArrayList var6 = new ArrayList();
            String[] var10 = var5;
            int var9 = var5.length;

            for(int var8 = 0; var8 < var9; ++var8) {
                String var7 = var10[var8];
                var6.add(var7);
            }

            var4.getDataSetConf().setHorizontalKpis(var6);
        }

    }
}
