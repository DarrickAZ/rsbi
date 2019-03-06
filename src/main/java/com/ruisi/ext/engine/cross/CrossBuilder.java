//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.context.cross.CrossFieldOther;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.DimOtherContext;
import com.ruisi.ext.engine.view.context.cross.RowDimContext;
import com.ruisi.ext.engine.view.context.cross.RowHeadContext;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.view.test.TestUtils;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CrossBuilder {
    private CrossReportContext a;
    private ExtRequest b;
    private CrossFieldLoader c;
    private ExtEnvirContext d;
    private boolean e = false;

    public ExtEnvirContext getCtx() {
        return this.d;
    }

    public boolean isExistOtherInCrossList() {
        return this.e;
    }

    public CrossBuilder(CrossReportContext var1, ExtRequest var2, ExtEnvirContext var3) {
        this.a = var1;
        this.b = var2;
        this.d = var3;
        this.c = (CrossFieldLoader)var2.getAttribute("ext.view.fieldLoader");
    }

    public static boolean isExistDrill(CrossReportContext var0) {
        return var0.getDims() != null && var0.getDims().size() > 0;
    }

    public static String getDrillDim(ExtRequest var0) {
        return var0.getParameter("drillDim");
    }

    public static void createDeftDim(CrossReportContext var0) {
        CrossField var1 = null;
        Iterator var3 = var0.getCrossRows().getRows().iterator();

        while(var3.hasNext()) {
            CrossField var2 = (CrossField)var3.next();
            if (!"none".equalsIgnoreCase(var2.getType())) {
                var1 = var2;
            }
        }

        RowDimContext var7 = new RowDimContext();
        String var8 = "";
        if (var0.getRowHeads() != null && var0.getRowHeads().size() > 0) {
            var8 = ((RowHeadContext)var0.getRowHeads().get(0)).getDesc();
        } else if ("kpi".equalsIgnoreCase(var1.getType())) {
            var8 = "指标";
        } else if ("kpiOther".equalsIgnoreCase(var1.getType())) {
            var8 = "指标";
        } else {
            var8 = var1.getDesc();
        }

        var7.setName(var8);
        var7.setCode(var1.getAlias());
        var7.setType(var1.getType());
        var7.setCodeDesc(var1.getAliasDesc());
        if (var1.getUselink() != null) {
            var7.setUseLink(var1.getUselink());
        }

        var7.setSize(var1.getSize());
        var7.setStart(var1.getStart());
        DimOtherContext var6;
        if (var1.getOther() != null && var1.getOther().size() > 0) {
            for(Iterator var5 = var1.getOther().iterator(); var5.hasNext(); var7.getOthers().add(var6)) {
                CrossFieldOther var4 = (CrossFieldOther)var5.next();
                var6 = new DimOtherContext();
                var6.setCode(var4.getAlias());
                var6.setType(var4.getType());
                if (var7.getOthers() == null) {
                    var7.setOthers(new ArrayList());
                }
            }
        }

        var0.getDims().add(0, var7);
    }

    public void init() {
        ArrayList var1 = new ArrayList();
        List var2 = this.a.getCrossCols().getCols();
        this.initField(var2, var1, (CrossField)null, "col");
        ArrayList var3 = new ArrayList();
        List var4 = this.a.getCrossRows().getRows();
        if (isExistDrill(this.a)) {
            String var5 = getDrillDim(this.b);
            RowDimContext var6 = getUseDim(this.a, var5);
            if (var6 == null) {
                throw new ExtRuntimeException("需要钻取的纬度未设置 deftUse 属性.");
            }

            String var7 = (String)this.b.getAttribute("ext_fromAjax");
            if ("true".equals(var7) && var5 != null) {
                var4 = this.createRowByDim(var6, var4);
            }
        }

        this.initField(var4, var3, (CrossField)null, "row");
        this.a.getCrossCols().setTmpCols(var2);
        this.a.getCrossRows().setTmpRows(var4);
        this.a.getCrossCols().setCols(var1);
        this.a.getCrossRows().setRows(var3);
        int var8 = this.a(var1, 0) + 1;
        this.a.getCrossCols().setMaxLevel(var8);
        int var9 = this.a(var3, 0) + 1;
        this.a.getCrossRows().setMaxLevel(var9);
    }

    public List createRowByDim(RowDimContext var1, List var2) {
        ArrayList var3 = new ArrayList();
        CrossField var4 = new CrossField();
        var4.setType(var1.getType());
        var4.setAlias(var1.getCode());
        var4.setDesc(var1.getName());
        var4.setMulti(true);
        var4.setTestFunc(var1.getTestFunc());
        var4.setUselink(var1.isUseLink());
        var4.setAliasDesc(var1.getCodeDesc());
        var4.setValue(var1.getValue());
        var4.setSize(var1.getSize());
        var4.setStart(var1.getStart());
        CrossField var5 = null;
        Iterator var7 = var2.iterator();

        while(var7.hasNext()) {
            CrossField var6 = (CrossField)var7.next();
            if (!"none".equalsIgnoreCase(var6.getType())) {
                var5 = var6;
                break;
            }
        }

        var4.setAliasFmt(var5.getAliasFmt());
        if (var1.getCascade() != null && var1.getCascade().length() > 0) {
            var4.setCasParent(true);
            RowDimContext var8 = this.getDimByAlias(var1.getCascade());
            CrossField var9 = new CrossField();
            var9.setType(var8.getType());
            var9.setAlias(var8.getCode());
            var9.setDesc(var8.getName());
            var9.setValue(this.b.getParameter(var8.getCode()));
            var9.setMulti(true);
            var4.setParent(var9);
        }

        var3.add(var4);
        return var3;
    }

    public static RowDimContext getUseDim(CrossReportContext var0, String var1) {
        if (var1 != null && var1.length() != 0) {
            Iterator var3 = var0.getDims().iterator();

            while(var3.hasNext()) {
                RowDimContext var2 = (RowDimContext)var3.next();
                if (var2.getCode().equals(var1)) {
                    return var2;
                }
            }

            throw new ExtRuntimeException(var1 + "未找到.");
        } else {
            return (RowDimContext)var0.getDims().get(0);
        }
    }

    public RowDimContext getDimByAlias(String var1) {
        Iterator var3 = this.a.getDims().iterator();

        while(var3.hasNext()) {
            RowDimContext var2 = (RowDimContext)var3.next();
            if (var2.getCode().equals(var1)) {
                return var2;
            }
        }

        throw new ExtRuntimeException(var1 + "未找到.");
    }

    private void a(CrossField var1, List var2, CrossField var3, CrossField var4, String var5) {
        if (var1.getOther() != null && var1.getOther().size() > 0) {
            ArrayList var6 = new ArrayList();

            for(int var7 = 0; var7 < var1.getOther().size(); ++var7) {
                CrossFieldOther var8 = (CrossFieldOther)var1.getOther().get(var7);
                if (var8.getUse() != null && var8.getUse()) {
                    CrossField var9 = new CrossField(var8);
                    ArrayList var10 = new ArrayList();
                    var8.setAlias(var1.getAlias());
                    var8.setType(var1.getType());
                    var8.setAggregation(var1.getAggregation());
                    var8.setFormatPattern(var1.getFormatPattern());
                    var8.setValue(var1.getValue());
                    var8.setUse(false);
                    var10.add(var8);
                    var9.setOther(var10);
                    var9.setParent(var3);
                    var9.setJsonId(var1.getValue());
                    if (var4.getSubs() != null && var4.getSubs().size() > 0) {
                        ArrayList var11 = new ArrayList();
                        this.initField(var4.getSubs(), var11, var9, var5);
                        var9.setSubs(var11);
                    }

                    var2.add(var9);
                    var6.add(var9);
                    if (var5.equals("col")) {
                        this.e = true;
                    }
                }
            }

            var1.setCalcCols(var6);
        }

    }

    public void initField(List var1, List var2, CrossField var3, String var4) {
        if (var1 != null) {
            for(int var5 = 0; var5 < var1.size(); ++var5) {
                CrossField var6 = (CrossField)var1.get(var5);
                int var7 = var6.getSize();
                Boolean var8 = var6.getMulti();
                String var10;
                if (var7 != 0) {
                    List var9 = null;
                    var10 = var6.getStart();
                    if (var10 != null && var10.length() > 0) {
                        var10 = TestUtils.findValue(var10, this.b, this.d);
                    }

                    if (var6.getType().equalsIgnoreCase("day")) {
                        var9 = CrossUtils.getDayBySize(var10, var7, var6.getShowWeek());
                    } else {
                        if (!var6.getType().equalsIgnoreCase("month")) {
                            throw new ExtRuntimeException("crossCol 标签中 size 属性只能配置在 type为 day或month中.");
                        }

                        var9 = CrossUtils.getMonthBySize(var10, var7);
                    }

                    for(int var11 = 0; var11 < var7; ++var11) {
                        CrossField var12 = var6.clone();
                        CrossUtils$DayOrMonth var13 = (CrossUtils$DayOrMonth)var9.get(var11);
                        var12.setDesc(var13.getDesc());
                        var12.setValue(var13.getName());
                        if (var6.getSubs() != null) {
                            var12.setSubs(new ArrayList());
                        }

                        var2.add(var12);
                        if (var3 != null) {
                            var12.setParent(var3);
                        }

                        this.a(var12, var2, var3, var6, var4);
                        this.initField(var6.getSubs(), var12.getSubs(), var12, var4);
                    }
                } else if (var8 != null && var8) {
                    String var19 = var6.getType();
                    var10 = var6.getValue();
                    HashMap var22 = null;
                    if (var6.getCasParent() != null && var6.getCasParent()) {
                        var22 = new HashMap();
                        this.a((CrossField)var3, (Map)var22);
                    }

                    List var23;
                    if ("FRD".equalsIgnoreCase(var19)) {
                        var23 = this.a(var6, var10, var22);

                        CrossField var24;
                        for(Iterator var14 = var23.iterator(); var14.hasNext(); this.initField(var6.getSubs(), var24.getSubs(), var24, var4)) {
                            var24 = (CrossField)var14.next();
                            var2.add(var24);
                            if (var3 != null) {
                                var24.setParent(var3);
                            }

                            if (var24.getOther() != null && var24.getOther().size() > 0) {
                                Iterator var16 = var24.getOther().iterator();

                                while(var16.hasNext()) {
                                    CrossFieldOther var15 = (CrossFieldOther)var16.next();
                                    var15.setParent(var3);
                                }
                            }

                            this.a(var24, var2, var3, var6, var4);
                            if (var6.getSubs() != null) {
                                var24.setSubs(new ArrayList());
                            }
                        }
                    } else {
                        var23 = this.c.loadField(var19, var10, var22);
                        if (var23 == null || var23.size() == 0) {
                            String var26 = ConstantsEngine.replace("需要加载类型为 $0 的纬度数据不存在.", var19);
                            throw new ExtRuntimeException(var26);
                        }

                        for(int var25 = 0; var25 < var23.size(); ++var25) {
                            Map var27 = (Map)var23.get(var25);
                            CrossField var28 = var6.clone();
                            var28.setDesc((String)var27.get("code_desc"));
                            var28.setValue((String)var27.get("code_id"));
                            var2.add(var28);
                            if (var3 != null) {
                                var28.setParent(var3);
                            }

                            if (var28.getOther() != null && var28.getOther().size() > 0) {
                                Iterator var17 = var28.getOther().iterator();

                                while(var17.hasNext()) {
                                    CrossFieldOther var29 = (CrossFieldOther)var17.next();
                                    var29.setParent(var3);
                                }
                            }

                            if (var6.getSubs() != null) {
                                var28.setSubs(new ArrayList());
                            }

                            this.a(var28, var2, var3, var6, var4);
                            this.initField(var6.getSubs(), var28.getSubs(), var28, var4);
                        }
                    }
                } else {
                    CrossField var18 = var6.clone();
                    var2.add(var18);
                    if (var3 != null) {
                        var18.setParent(var3);
                    }

                    if (var18.getOther() != null && var18.getOther().size() > 0) {
                        Iterator var21 = var18.getOther().iterator();

                        while(var21.hasNext()) {
                            CrossFieldOther var20 = (CrossFieldOther)var21.next();
                            var20.setParent(var3);
                        }
                    }

                    if (var18.getDesc() == null || var18.getDesc().length() == 0) {
                        var10 = this.c.loadFieldName(var18.getType(), var18.getValue());
                        var18.setDesc(var10);
                    }

                    if (var6.getSubs() != null) {
                        var18.setSubs(new ArrayList());
                    }

                    this.a(var18, var2, var3, var6, var4);
                    this.initField(var6.getSubs(), var18.getSubs(), var18, var4);
                }
            }

        }
    }

    private int a(List var1, int var2) {
        if (var1 == null) {
            return var2;
        } else {
            CrossField var3;
            for(Iterator var4 = var1.iterator(); var4.hasNext(); var2 = this.a(var3.getSubs(), var2)) {
                var3 = (CrossField)var4.next();
                if (var3.getParent() != null) {
                    var3.setLevel(var3.getParent().getLevel() + 1);
                }

                if (var3.getLevel() > var2) {
                    var2 = var3.getLevel();
                }

                if (var3.getOther() != null && var3.getOther().size() > 0) {
                    for(int var5 = 0; var5 < var3.getOther().size(); ++var5) {
                        CrossFieldOther var6 = (CrossFieldOther)var3.getOther().get(var5);
                        var6.setRetValue(TestUtils.findValue(var6.getValue(), this.b, this.d));
                    }
                }

                if (var3.getSubs() == null || var3.getSubs().size() == 0) {
                    var3.setSpan(1);
                    String var8 = "";
                    if (var3.getJsonId() != null) {
                        var8 = var8 + var3.getJsonId();
                    }

                    var8 = var8 + (var3.getValue() == null ? "N" : var3.getValue());
                    if (var3.getType().equals("kpiOther")) {
                        var8 = var8 + "_" + var3.getAlias();
                    }

                    if (var3.getOther() != null && var3.getOther().size() > 0) {
                        var8 = var8 + "_";

                        for(int var9 = 0; var9 < var3.getOther().size(); ++var9) {
                            CrossFieldOther var7 = (CrossFieldOther)var3.getOther().get(var9);
                            var8 = var8 + (var7.getRetValue() == null ? "N" : var7.getRetValue());
                            if (var9 != var3.getOther().size() - 1) {
                                var8 = var8 + "_";
                            }
                        }
                    }

                    var3.setJsonId(var8);
                    this.a(var3, var3);
                }
            }

            return var2;
        }
    }

    private void a(CrossField var1, CrossField var2) {
        CrossField var3 = var1.getParent();
        if (var3 != null) {
            var3.setSpan(var3.getSpan() + 1);
            String var4 = var3.getValue();
            if (var3.getType().equalsIgnoreCase("kpiOther")) {
                var4 = var3.getAlias();
            }

            var2.setJsonId(var2.getJsonId() + "_" + (var4 == null ? "N" : var4));
            this.a(var3, var2);
        }

    }

    private void a(CrossField var1, Map var2) {
        if (var1 != null) {
            var2.put(var1.getAlias(), var1.getValue());
            this.a(var1.getParent(), var2);
        }

    }

    private boolean a(String[] var1, Object var2) {
        boolean var3 = false;
        String[] var7 = var1;
        int var6 = var1.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            String var4 = var7[var5];
            if (var4.equals(var2)) {
                var3 = true;
                break;
            }
        }

        return var3;
    }

    private List a(CrossField var1, String var2, Map var3) {
        Object var4 = new ArrayList();
        List var5 = this.a.loadOptions();
        String[] var6 = null;
        if (var2 != null && var2.length() > 0) {
            var6 = var2.split(",");
        }

        byte var7 = 1;

        int var8;
        for(var8 = 0; var8 < var5.size(); ++var8) {
            Map var9 = (Map)var5.get(var8);
            Object var10 = var9.get(var1.getAliasDesc());
            Object var11 = var9.get(var1.getAlias());
            if (var11 instanceof BigDecimal || var11 instanceof Double || var11 instanceof Long || var11 instanceof Integer || var11 instanceof Float) {
                var7 = 2;
            }

            String var12 = var11 == null ? "NULLVAL" : var11.toString();
            String var13 = var10 == null ? "未知" : var10.toString();
            if ((var6 == null || this.a((String[])var6, (Object)var12)) && (var3 == null || var3.size() <= 0 || this.isCheckData(var9, var3))) {
                CrossField var14 = var1.clone();
                var14.setHeader(var1.getDesc());
                String var15 = var13.toString();
                var14.setDesc(var15);
                var14.setValue(var12);
                if (var1.getAliasFmt() != null && var1.getAliasFmt().length() > 0) {
                    String var16 = (String)var9.get(var1.getAliasFmt());
                    String var17;
                    if (var16 == null) {
                        var17 = ConstantsEngine.replace("crossReport 中配置的  aliasFmt ($0) 无法从sql中获取数据.", var1.getAliasFmt());
                        throw new ExtRuntimeException(var17);
                    }

                    var17 = var1.getAliasAggregation();
                    String var18 = "sum";
                    if (var17 != null && var17.length() > 0) {
                        var18 = (String)var9.get(var17);
                        if (var18 == null) {
                            String var19 = ConstantsEngine.replace("crossReport 中配置的  aliasAggregation ($0) 无法从sql中获取数据.", var17);
                            throw new ExtRuntimeException(var19);
                        }
                    }

                    var14.setAggregation(var18);
                    var14.setFormatPattern(var16);
                    var14.setType("kpi");
                    var14.setTypeChg2Kpi(true);
                }

                if (!this.a((CrossField)var14, (List)var4)) {
                    ((List)var4).add(var14);
                }

                if (var1.getTopType() == null || "number".equalsIgnoreCase(var1.getTopType())) {
                    int var21 = var1.getTop() == null ? 50 : var1.getTop();
                    if (((List)var4).size() >= var21) {
                        break;
                    }
                }
            }
        }

        if (var1.getTop() != null && "percent".equals(var1.getTopType())) {
            var8 = (int)Math.round((double)var1.getTop() / 100.0D * (double)((List)var4).size());
            var4 = ((List)var4).subList(0, var8);
        }

        if (var1.getSort() != null && var1.getSort().length() > 0) {
            System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
            CrossFieldSort var20 = new CrossFieldSort(var1.getSort(), var7);
            Collections.sort((List)var4, var20);
        }

        return (List)var4;
    }

    public boolean isCheckData(Map var1, Map var2) {
        boolean var3 = true;
        Iterator var5 = var2.entrySet().iterator();

        while(var5.hasNext()) {
            Entry var4 = (Entry)var5.next();
            String var6 = var1.get(var4.getKey()) == null ? "NULLVAL" : var1.get(var4.getKey()).toString();
            if (!var6.equals(var4.getValue())) {
                var3 = false;
                break;
            }
        }

        return var3;
    }

    private boolean a(CrossField var1, List var2) {
        Iterator var4 = var2.iterator();

        while(var4.hasNext()) {
            CrossField var3 = (CrossField)var4.next();
            if (var3.getValue().equals(var1.getValue())) {
                return true;
            }
        }

        return false;
    }

    public CrossReportContext getCrossReport() {
        return this.a;
    }

    public ExtRequest getRequest() {
        return this.b;
    }

    public CrossFieldLoader getLoader() {
        return this.c;
    }
}
