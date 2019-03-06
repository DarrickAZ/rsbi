//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.KpiComputeContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class KpiComputeRule extends Rule {
    public KpiComputeRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        KpiComputeContext var4 = new KpiComputeContext();
        var4.setFormula(var3.getValue("formula"));
        var4.setName(var3.getValue("name"));
        var4.setSrc(var3.getValue("src"));
        DataCenterContext var5 = (DataCenterContext)this.digester.peek();
        var5.getProcess().add(var4);
        this.digester.push(var4);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
