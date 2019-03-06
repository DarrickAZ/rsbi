//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.DsKpiFilterContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class KpiFilterRule extends Rule {
    public KpiFilterRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("kpi");
        String var5 = var3.getValue("toNewKpi");
        String var6 = var3.getValue("newKpiCode");
        DataCenterContext var7 = (DataCenterContext)this.digester.peek();
        if (!var7.getDataSetConf().isKpiExist(var4)) {
            String var10 = ConstantsEngine.replace("kpiFilter/filter 配置出错,引用的指标不存在，kpi=$0", var4);
            throw new ExtConfigException(var10);
        } else {
            DsKpiFilterContext var8 = new DsKpiFilterContext();
            var8.setKpi(var4);
            var8.setNewKpiCode(var6);
            if (var5 != null && var5.length() > 0) {
                var8.setToNewKpi("true".equalsIgnoreCase(var5));
            }

            Object var9 = var7.getDataSetConf().getKpiFilters();
            if (var9 == null) {
                var9 = new HashMap();
                var7.getDataSetConf().setKpiFilters((Map)var9);
                ((Map)var9).put(var4, new ArrayList());
            }

            ((List)((Map)var9).get(var4)).add(var8);
            this.digester.push(var8);
        }
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
