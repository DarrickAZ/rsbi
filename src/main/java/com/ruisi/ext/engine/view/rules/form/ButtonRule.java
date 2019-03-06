//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.form;

import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.form.ButtonContextImpl;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ButtonRule extends Rule {
    public ButtonRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("desc");
        String var5 = var3.getValue("type");
        String var6 = var3.getValue("action");
        String var7 = var3.getValue("method");
        String var8 = var3.getValue("submit");
        String var9 = var3.getValue("checkParam");
        String var10 = var3.getValue("onClick");
        String var11 = var3.getValue("confirm");
        String var12 = var3.getValue("exportDataGrid");
        String var13 = var3.getValue("iconCls");
        String var14 = var3.getValue("styleClass");
        if (var9 == null || var9.length() == 0) {
            var9 = "true";
        }

        String var15 = var3.getValue("src");
        String var16 = var3.getValue("mvId");
        String var17 = var3.getValue("target");
        String var18 = var3.getValue("id");
        ButtonContextImpl var19 = new ButtonContextImpl();
        var19.setDesc(var4);
        var19.setType(var5);
        var19.setAction(var6);
        var19.setMethod(var7);
        var19.setSubmit(var8);
        var19.setCheckParam(var9);
        var19.setOnClick(var10);
        var19.setSrc(var15);
        var19.setId(var18);
        var19.setExportDataGrid(var12);
        var19.setConfirm("true".equalsIgnoreCase(var11));
        var19.setTarget(var17);
        var19.setIconCls(var13);
        var19.setStyleClass(var14);
        if (var16 != null && var16.length() > 0) {
            var19.setMvId(var16.split(","));
        }

        Element var20 = (Element)this.digester.peek();
        if (var20.getChildren() == null) {
            var20.setChildren(new ArrayList());
        }

        var20.getChildren().add(var19);
        var19.setParent(var20);
        MVContext var21 = RuleUtils.findMVContext(var20);
        var19.setFrom(var21.getMvid());
        var21.setShowForm(true);
    }

    public void end(String var1, String var2) {
    }
}
