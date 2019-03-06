//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContextImpl;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class CrossReportRule extends Rule {
    public CrossReportRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("id");
        String var5 = var3.getValue("out");
        String var6 = var3.getValue("label");
        String var7 = var3.getValue("export");
        String var8 = var3.getValue("print");
        String var9 = var3.getValue("width");
        String var10 = var3.getValue("height");
        String var11 = var3.getValue("exportName");
        String var12 = var3.getValue("firstNotSort");
        String var13 = var3.getValue("confCallBack");
        String var14 = var3.getValue("bgAgg");
        CrossReportContextImpl var15 = new CrossReportContextImpl();
        var15.setOut(var5);
        var15.setHeight(var10);
        var15.setExportName(var11);
        var15.setExport("true".equalsIgnoreCase(var7));
        var15.setPrint("true".equalsIgnoreCase(var8));
        var15.setFirstSort(var12);
        var15.setBgAgg("true".equalsIgnoreCase(var14));
        var15.setConfCallBack(var13);
        var15.setWidth(var9);
        var15.setLabel(var6);
        if (var4 != null && var4.length() != 0) {
            var15.setId(var4);
        } else {
            String var16 = "report" + IdCreater.create();
            var15.setId(var16);
        }

        Element var19 = (Element)this.digester.peek();
        if (var19.getChildren() == null) {
            var19.setChildren(new ArrayList());
        }

        var19.getChildren().add(var15);
        var15.setParent(var19);
        this.digester.push(var15);
        MVContext var17 = RuleUtils.findMVContext(var19);
        if (var17.getCrossReports() == null) {
            HashMap var18 = new HashMap();
            var17.setCrossReports(var18);
        }

        var17.getCrossReports().put(var15.getId(), var15);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
