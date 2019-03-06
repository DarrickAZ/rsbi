//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.gridreport;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContextImpl;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class GridReportRule extends Rule {
    public GridReportRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("id");
        String var5 = var3.getValue("label");
        String var6 = var3.getValue("out");
        String var7 = var3.getValue("width");
        String var8 = var3.getValue("height");
        String var9 = var3.getValue("trMenu");
        GridReportContextImpl var10 = new GridReportContextImpl();
        if (var4 != null && var4.length() != 0) {
            var10.setId(var4);
        } else {
            String var11 = "grid" + IdCreater.create();
            var10.setId(var11);
        }

        if (var9 != null && "true".equalsIgnoreCase(var9)) {
            var10.setTrMenu(true);
        }

        var10.setLabel(var5);
        var10.setOut(var6);
        var10.setWidth(var7);
        var10.setHeight(var8);
        Element var14 = (Element)this.digester.peek();
        if (var14.getChildren() == null) {
            var14.setChildren(new ArrayList());
        }

        var14.getChildren().add(var10);
        var10.setParent(var14);
        this.digester.push(var10);
        MVContext var12 = RuleUtils.findMVContext(var14);
        if (var12.getGridReports() == null || var12.getGridReports().size() == 0) {
            HashMap var13 = new HashMap();
            var12.setGridReports(var13);
        }

        var12.getGridReports().put(var10.getId(), var10);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
