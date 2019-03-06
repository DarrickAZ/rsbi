//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.BenchmarkPercContext;
import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class BenchmarkPercRule extends Rule {
    public BenchmarkPercRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        BenchmarkPercContext var4 = new BenchmarkPercContext();
        DataCenterContext var5 = (DataCenterContext)this.digester.peek();
        var5.getProcess().add(var4);
    }
}
