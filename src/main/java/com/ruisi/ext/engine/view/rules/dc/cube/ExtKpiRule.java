//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.DataSetExtKpiContext;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ExtKpiRule extends Rule {
    public ExtKpiRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("alias");
        DataSetExtKpiContext var5 = new DataSetExtKpiContext();
        var5.setAlias(var4);
        DataCenterContext var6 = (DataCenterContext)this.digester.peek();
        if (var6.getDataSetConf().getExtKpis() == null) {
            var6.getDataSetConf().setExtKpis(new ArrayList());
        }

        var6.getDataSetConf().getExtKpis().add(var5);
        this.digester.push(var5);
    }

    public void body(String var1, String var2, String var3) {
        DataSetExtKpiContext var4 = (DataSetExtKpiContext)this.digester.peek();
        var4.setExpress(var3);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
