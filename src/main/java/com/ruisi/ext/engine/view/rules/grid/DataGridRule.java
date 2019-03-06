//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.grid;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.grid.DataGridContextImpl;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DataGridRule extends Rule {
    public DataGridRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("label");
        String var5 = var3.getValue("export");
        String var6 = var3.getValue("out");
        String var7 = var3.getValue("rightMenuFunc");
        String var8 = var3.getValue("targetDiv");
        DataGridContextImpl var9 = new DataGridContextImpl();
        var9.setLabel(var4);
        var9.setOut(var6);
        var9.setExport("true".equalsIgnoreCase(var5));
        var9.setTargetDiv(var8);
        var9.setRightMenuFunc(var7);
        Element var10 = (Element)this.digester.peek();
        if (var10.getChildren() == null) {
            var10.setChildren(new ArrayList());
        }

        var10.getChildren().add(var9);
        this.digester.push(var9);
        var9.setParent(var10);
        String var11 = "dg" + IdCreater.create();
        var9.setId(var11);
        MVContext var12 = RuleUtils.findMVContext(var10);
        if (var12.getDataGrids() == null || var12.getDataGrids().size() == 0) {
            HashMap var13 = new HashMap();
            var12.setDataGrids(var13);
        }

        var12.getDataGrids().put(var9.getId(), var9);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
