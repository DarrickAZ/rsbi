//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.FilterContext;
import org.apache.commons.digester.Rule;

public class FilterRule extends Rule {
    public FilterRule() {
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            FilterContext var5 = new FilterContext();
            var5.setExpress(var3);
            DataCenterContext var6 = (DataCenterContext)this.digester.peek();
            var6.getProcess().add(var5);
        }
    }
}
