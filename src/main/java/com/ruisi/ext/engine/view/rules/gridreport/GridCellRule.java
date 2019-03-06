//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.gridreport;

import com.ruisi.ext.engine.view.context.gridreport.GridCell;
import java.util.List;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class GridCellRule extends Rule {
    public GridCellRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("colSpan");
        String var5 = var3.getValue("rowSpan");
        String var6 = var3.getValue("desc");
        String var7 = var3.getValue("align");
        String var8 = var3.getValue("alias");
        String var9 = var3.getValue("width");
        String var10 = var3.getValue("financeFmt");
        String var11 = var3.getValue("formatPattern");
        String var12 = var3.getValue("order");
        String var13 = var3.getValue("class");
        String var14 = var3.getValue("jsFunc");
        String var15 = var3.getValue("dynamicText");
        GridCell var16 = new GridCell();
        var16.setAlias(var8);
        var16.setAlign(var7);
        var16.setDesc(var6);
        var16.setFormatPattern(var11);
        var16.setStyleClass(var13);
        var16.setJsFunc(var14);
        var16.setWidth(var9);
        var16.setOrder("true".equalsIgnoreCase(var12));
        var16.setFinanceFmt("true".equalsIgnoreCase(var10));
        if (var4 != null && var4.length() > 0) {
            var16.setColSpan(Integer.parseInt(var4));
        }

        if (var5 != null && var5.length() > 0) {
            var16.setRowSpan(Integer.parseInt(var5));
        }

        if (var15 != null && var15.length() > 0) {
            var16.setDynamicText("true".equalsIgnoreCase(var15));
        }

        List var17 = (List)this.digester.peek();
        var17.add(var16);
        this.digester.push(var16);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
