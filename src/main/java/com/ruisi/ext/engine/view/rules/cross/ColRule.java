//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.CrossCols;
import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ColRule extends Rule {
    public ColRule() {
    }

    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("desc");
        String var5 = var3.getValue("type");
        String var6 = var3.getValue("alias");
        String var7 = var3.getValue("value");
        String var8 = var3.getValue("formatPattern");
        String var9 = var3.getValue("aggregation");
        String var10 = var3.getValue("size");
        String var11 = var3.getValue("multi");
        String var12 = var3.getValue("id");
        String var13 = var3.getValue("formula");
        String var14 = var3.getValue("financeFmt");
        String var15 = var3.getValue("start");
        String var16 = var3.getValue("width");
        String var17 = var3.getValue("jsFunc");
        String var18 = var3.getValue("uselink");
        String var19 = var3.getValue("aliasDesc");
        String var20 = var3.getValue("styleClass");
        String var21 = var3.getValue("styleToLine");
        String var22 = var3.getValue("alt");
        String var23 = var3.getValue("order");
        String var24 = var3.getValue("aliasAggregation");
        String var25 = var3.getValue("aliasFmt");
        String var26 = var3.getValue("kpiRate");
        String var27 = var3.getValue("notCondition");
        String var28 = var3.getValue("casParent");
        String var29 = var3.getValue("extDs");
        String var30 = var3.getValue("showWeek");
        String var31 = var3.getValue("dateType");
        String var32 = var3.getValue("dateTypeFmt");
        String var33 = var3.getValue("dataClass");
        String var34 = var3.getValue("top");
        String var35 = var3.getValue("dimAggre");
        String var36 = var3.getValue("brstep");
        if (var5 != null && var5.length() != 0) {
            CrossField var37 = new CrossField();
            var37.setDesc(var4);
            var37.setType(var5);
            var37.setValue(var7);
            var37.setAlias(var6);
            var37.setWidth(var16);
            var37.setAlt(var22);
            var37.setFormatPattern(var8);
            var37.setAggregation(var9);
            var37.setId(var12);
            var37.setFinanceFmt("true".equalsIgnoreCase(var14));
            var37.setOrder("true".equalsIgnoreCase(var23));
            var37.setFormula(var13);
            var37.setAliasDesc(var19);
            var37.setStart(var15);
            var37.setJsFunc(var17);
            var37.setStyleClass(var20);
            var37.setAliasFmt(var25);
            var37.setAliasAggregation(var24);
            var37.setExtDs(var29);
            var37.setDateType(var31);
            var37.setDateTypeFmt(var32);
            var37.setDataClass(var33);
            var37.setDimAggre(var35);
            if (var34 != null && var34.length() > 0) {
                var37.setTop(new Integer(var34));
            }

            if (var10 != null && var10.length() > 0) {
                var37.setSize(Integer.parseInt(var10));
            }

            if (var36 != null && var36.length() > 0) {
                var37.setBrstep(new Integer(var36));
            }

            if (var27 != null && var27.length() > 0) {
                var37.setNotCondition("true".equalsIgnoreCase(var27));
            }

            if (var30 != null && var30.length() > 0) {
                var37.setShowWeek("true".equalsIgnoreCase(var30));
            }

            if (var11 != null && var11.length() > 0) {
                var37.setMulti("true".equalsIgnoreCase(var11));
            }

            if ("true".equalsIgnoreCase(var28)) {
                var37.setCasParent(true);
            }

            if (var21 != null && var21.length() > 0) {
                var37.setStyleToLine("true".equalsIgnoreCase(var21));
            }

            if (var26 != null && var26.length() > 0) {
                var37.setKpiRate(new BigDecimal(var26));
            }

            var37.setUselink("true".equalsIgnoreCase(var18));
            Object var38 = this.digester.peek();
            if (var38 instanceof CrossCols) {
                CrossCols var39 = (CrossCols)var38;
                var39.getCols().add(var37);
            } else {
                if (!(var38 instanceof CrossField)) {
                    throw new ExtConfigException("crossCol配置有错.");
                }

                CrossField var40 = (CrossField)var38;
                if (var40.getSubs() == null) {
                    var40.setSubs(new ArrayList());
                }

                var40.getSubs().add(var37);
                var37.setParent(var40);
            }

            this.digester.push(var37);
        } else {
            throw new ExtConfigException("crossCol 必须配置 type 属性");
        }
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
