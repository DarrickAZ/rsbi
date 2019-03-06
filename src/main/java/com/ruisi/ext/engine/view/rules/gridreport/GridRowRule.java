//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.gridreport;

import com.ruisi.ext.engine.view.context.gridreport.GridCell;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class GridRowRule extends Rule {
    public GridRowRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        ArrayList var4 = new ArrayList();
        this.digester.push(var4);
    }

    public void end(String var1, String var2) {
        List var3 = (List)this.digester.pop();
        GridCell[] var4 = new GridCell[var3.size()];

        for(int var5 = 0; var5 < var3.size(); ++var5) {
            var4[var5] = (GridCell)var3.get(var5);
        }

        List var6 = (List)this.digester.peek();
        var6.add(var4);
    }
}
