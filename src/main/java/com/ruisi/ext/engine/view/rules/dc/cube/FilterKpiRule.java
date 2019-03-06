//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.FilterKpiContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class FilterKpiRule extends Rule {
    public FilterKpiRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        FilterKpiContext var4 = new FilterKpiContext();
        var4.setIn(var3.getValue("in"));
        var4.setNotIn(var3.getValue("notIn"));
        var4.setSrc(var3.getValue("src"));
        DataCenterContext var5 = (DataCenterContext)this.digester.peek();
        var5.getProcess().add(var4);
    }
}
