//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.form;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.form.DateSelectContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DateSelectRule extends Rule {
    public DateSelectRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("id");
        String var5 = var3.getValue("desc");
        String var6 = var3.getValue("require");
        String var7 = var3.getValue("size");
        String var8 = var3.getValue("value");
        String var9 = var3.getValue("defaultValue");
        String var10 = var3.getValue("dateFormat");
        String var11 = var3.getValue("maxDate");
        String var12 = var3.getValue("minDate");
        String var13 = var3.getValue("showCalendar");
        String var14 = var3.getValue("target");
        String var15 = var3.getValue("outBox");
        String var16 = var3.getValue("dateType");
        DateSelectContextImpl var17 = new DateSelectContextImpl();
        var17.setId(var4);
        var17.setDesc(var5);
        var17.setDefaultValue(var9);
        var17.setRequire("true".equalsIgnoreCase(var6));
        var17.setSize(var7);
        var17.setValue(var8);
        var17.setDateFormat(var10);
        var17.setMaxDate(var11);
        var17.setMinDate(var12);
        var17.setShowCalendar("true".equalsIgnoreCase(var13));
        var17.setDateType(var16);
        if (var14 != null && var14.length() > 0) {
            var17.setTarget(var14.split(","));
        }

        if (var15 != null && var15.length() > 0) {
            var17.setOutBox("true".equalsIgnoreCase(var15));
        }

        if (var4 != null && var4.length() != 0) {
            Element var20 = (Element)this.digester.peek();
            if (var20.getChildren() == null) {
                var20.setChildren(new ArrayList());
            }

            var20.getChildren().add(var17);
            var17.setParent(var20);
            MVContext var19 = RuleUtils.findMVContext(var20);
            var19.setMvParam(var4, var17);
            var19.setShowForm(true);
        } else {
            String var18 = ConstantsEngine.replace(" $0 输入框未配置id.", "dateSelect");
            throw new ExtConfigException(var18);
        }
    }

    @Override
    public void end(String var1, String var2) {
    }
}
