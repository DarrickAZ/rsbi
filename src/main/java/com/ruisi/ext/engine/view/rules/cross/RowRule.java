//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.context.cross.CrossRows;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class RowRule extends Rule {
    public RowRule() {
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
        String var18 = var3.getValue("note");
        String var19 = var3.getValue("casParent");
        String var20 = var3.getValue("uselink");
        String var21 = var3.getValue("aliasDesc");
        String var22 = var3.getValue("styleClass");
        String var23 = var3.getValue("styleToLine");
        String var24 = var3.getValue("alt");
        String var25 = var3.getValue("aliasFmt");
        String var26 = var3.getValue("aliasAggregation");
        String var27 = var3.getValue("spaceNum");
        String var28 = var3.getValue("testFunc");
        String var29 = var3.getValue("kpiRate");
        String var30 = var3.getValue("notCondition");
        String var31 = var3.getValue("extDs");
        String var32 = var3.getValue("showWeek");
        String var33 = var3.getValue("dateType");
        String var34 = var3.getValue("dateTypeFmt");
        String var35 = var3.getValue("dataClass");
        String var36 = var3.getValue("top");
        String var37 = var3.getValue("dimAggre");
        String var38 = var3.getValue("brstep");
        if (var5 != null && var5.length() != 0) {
            CrossField var39 = new CrossField();
            var39.setDesc(var4);
            var39.setType(var5);
            var39.setValue(var7);
            var39.setAlias(var6);
            var39.setWidth(var16);
            var39.setFormatPattern(var8);
            var39.setAggregation(var9);
            var39.setId(var12);
            var39.setFormula(var13);
            var39.setStart(var15);
            var39.setJsFunc(var17);
            var39.setAlt(var24);
            var39.setStyleClass(var22);
            var39.setAliasFmt(var25);
            var39.setAliasAggregation(var26);
            var39.setTestFunc(var28);
            var39.setExtDs(var31);
            var39.setDateType(var33);
            var39.setDateTypeFmt(var34);
            var39.setDataClass(var35);
            var39.setDimAggre(var37);
            var39.setFinanceFmt("true".equalsIgnoreCase(var14));
            if (var36 != null && var36.length() > 0) {
                var39.setTop(new Integer(var36));
            }

            if (var10 != null && var10.length() > 0) {
                var39.setSize(Integer.parseInt(var10));
            }

            if (var11 != null && var11.length() > 0) {
                var39.setMulti("true".equalsIgnoreCase(var11));
            }

            if (var30 != null && var30.length() > 0) {
                var39.setNotCondition("true".equalsIgnoreCase(var30));
            }

            if (var27 != null && var27.length() > 0) {
                var39.setSpaceNum(new Integer(var27));
            }

            if (var29 != null && var29.length() > 0) {
                var39.setKpiRate(new BigDecimal(var29));
            }

            if (var32 != null && var32.length() > 0) {
                var39.setShowWeek("true".equalsIgnoreCase(var32));
            }

            if (var23 != null && var23.length() > 0) {
                var39.setStyleToLine("true".equalsIgnoreCase(var23));
            }

            if ("true".equalsIgnoreCase(var18)) {
                var39.setNote(true);
            }

            if ("true".equalsIgnoreCase(var19)) {
                var39.setCasParent(true);
            }

            if ("true".equalsIgnoreCase(var20)) {
                var39.setUselink(true);
            }

            if (var38 != null && var38.length() > 0) {
                var39.setBrstep(new Integer(var38));
            }

            var39.setAliasDesc(var21);
            Object var40 = this.digester.peek();
            if (var40 instanceof CrossRows) {
                CrossRows var41 = (CrossRows)var40;
                var41.getRows().add(var39);
            } else {
                if (!(var40 instanceof CrossField)) {
                    throw new ExtConfigException("crossRow配置有错.");
                }

                CrossField var42 = (CrossField)var40;
                if (var42.getSubs() == null) {
                    var42.setSubs(new ArrayList());
                }

                var42.getSubs().add(var39);
                var39.setParent(var42);
            }

            this.digester.push(var39);
        } else {
            throw new ExtConfigException("crossRow 必须配置 type 属性");
        }
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
