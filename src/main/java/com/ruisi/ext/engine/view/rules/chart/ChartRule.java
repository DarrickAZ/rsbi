//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.chart;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.chart.ChartContextImpl;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ChartRule extends Rule {
    public ChartRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("shape");
        String var5 = var3.getValue("id");
        String var6 = var3.getValue("xcol");
        String var7 = var3.getValue("xcolDesc");
        String var8 = var3.getValue("ycol");
        String var9 = var3.getValue("scol");
        String var10 = var3.getValue("width");
        String var11 = var3.getValue("height");
        String var12 = var3.getValue("label");
        String var13 = var3.getValue("y2col");
        String var14 = var3.getValue("y3col");
        String var15 = var3.getValue("rate");
        String var16 = var3.getValue("rate2");
        String var17 = var3.getValue("rate3");
        String var18 = var3.getValue("align");
        String var19 = var3.getValue("rightSer");
        String var20 = var3.getValue("dateType");
        String var21 = var3.getValue("dateTypeFmt");
        String var22 = var3.getValue("mergeData");
        String var23 = var3.getValue("y2Aggre");
        String var24 = var3.getValue("formula");
        String var25 = var3.getValue("formulaName");
        ChartContextImpl var26 = new ChartContextImpl();
        var26.setShape(var4);
        var26.setXcol(var6);
        var26.setYcol(var8);
        var26.setScol(var9);
        var26.setWidth(var10);
        var26.setHeight(var11);
        var26.setLabel(var12);
        var26.setY2col(var13);
        var26.setY3col(var14);
        var26.setXcolDesc(var7);
        var26.setAlign(var18);
        var26.setRightSer(var19);
        var26.setDateType(var20);
        var26.setDateTypeFmt(var21);
        var26.setY2Aggre(var23);
        if (var15 != null && var15.length() > 0) {
            var26.setRate(Integer.parseInt(var15));
        }

        if (var16 != null && var16.length() > 0) {
            var26.setRate2(Integer.parseInt(var16));
        }

        if (var17 != null && var17.length() > 0) {
            var26.setRate3(Integer.parseInt(var17));
        }

        if (var24 != null && var24.trim().length() > 0) {
            var26.setFormula(var24.split(","));
        }

        if (var25 != null && var25.trim().length() > 0) {
            var26.setFormulaName(var25.split(","));
        }

        if (var22 != null && var22.length() > 0) {
            var26.setMergeData("true".equalsIgnoreCase(var22));
        }

        if (var5 != null && var5.length() != 0) {
            var26.setId(var5);
        } else {
            String var27 = "chart" + IdCreater.create();
            var26.setId(var27);
        }

        Element var30 = (Element)this.digester.peek();
        if (var30.getChildren() == null) {
            var30.setChildren(new ArrayList());
        }

        var30.getChildren().add(var26);
        var26.setParent(var30);
        MVContext var28 = RuleUtils.findMVContext(var30);
        if (var28.getCharts() == null) {
            HashMap var29 = new HashMap();
            var28.setCharts(var29);
            var29.put(var26.getId(), var26);
        } else {
            var28.getCharts().put(var26.getId(), var26);
        }

        this.digester.push(var26);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
