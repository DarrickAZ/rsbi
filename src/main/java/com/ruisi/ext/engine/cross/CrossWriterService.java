//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.cross.CrossCols;
import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.context.cross.CrossFieldOther;
import com.ruisi.ext.engine.view.context.cross.CrossKpi;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.RowDimContext;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import com.ruisi.ispire.dc.cube.operation.DataSetAggregation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.javascript.Function;

public class CrossWriterService {
    private static Log a = LogFactory.getLog(CrossWriterService.class);
    private ExtRequest b;
    protected CrossKpi currKpiNode;
    protected CrossKpi currFormula;
    protected CrossKpi baseKpiNode;
    protected CrossKpi backKpiNode;
    protected int whereIn = 0;
    protected int backKpiIn = 0;
    protected int formulaPos = 0;
    protected int kpiPos = 0;
    protected String kpiValueColumn;
    protected CrossReportContext crossReport;

    public static String getKpiValueColumn() {
        String var0 = ExtContext.getInstance().getConstant("kpiValueColumn");
        if (var0 == null || var0.length() == 0) {
            var0 = "kpi_value";
        }

        return var0;
    }

    public CrossWriterService(CrossField var1, ExtRequest var2, CrossReportContext var3) {
        if (var3.getBaseKpi() == null) {
            this.baseKpiNode = var1;
        } else {
            this.baseKpiNode = var3.getBaseKpi();
        }

        this.b = var2;
        this.crossReport = var3;
        this.kpiValueColumn = getKpiValueColumn();
    }

    public static void findNodeByLevel(int var0, List var1, List var2) {
        if (var1 != null) {
            CrossField var3;
            for(Iterator var4 = var1.iterator(); var4.hasNext(); findNodeByLevel(var0, var3.getSubs(), var2)) {
                var3 = (CrossField)var4.next();
                if (var3.getLevel() == var0) {
                    var2.add(var3);
                }
            }

        }
    }

    public static void findNodeByLastLevel(int var0, List var1, List var2) {
        if (var1 != null) {
            CrossField var3;
            for(Iterator var4 = var1.iterator(); var4.hasNext(); findNodeByLastLevel(var0, var3.getSubs(), var2)) {
                var3 = (CrossField)var4.next();
                if (var3.getLastLevel() == var0) {
                    var2.add(var3);
                }
            }

        }
    }

    public static void loopRow(List var0, List var1) {
        if (var0 != null) {
            Iterator var3 = var0.iterator();

            while(var3.hasNext()) {
                CrossField var2 = (CrossField)var3.next();
                var1.add(var2);
                loopRow(var2.getSubs(), var1);
            }

        }
    }

    public static void loopColGetParams(StringBuffer var0, CrossField var1) {
        if (var1 != null) {
            var0.append("&");
            var0.append(var1.getAlias() + "=" + var1.getValue());
            loopColGetParams(var0, var1.getParent());
        }
    }

    public List querySubData(CrossField var1, List var2, int var3, String var4) {
        ArrayList var5 = new ArrayList();
        HashMap var6 = new HashMap();
        String var7 = this.loopFieldUp(var1, var6, var3, var4);
        if (var7 != null && var7.length() > 0) {
            var6.remove(var7);
        }

        Iterator var9 = var2.iterator();

        while(var9.hasNext()) {
            Map var8 = (Map)var9.next();
            if (this.isCheckData(var8, var6)) {
                var5.add(var8);
            }
        }

        return var5;
    }

    public void setCrossFiledLastLevel(List var1, CrossCols var2) {
        if (var1 != null) {
            for(int var3 = 0; var3 < var1.size(); ++var3) {
                CrossField var4 = (CrossField)var1.get(var3);
                int var5 = var4.getSubs() == null ? 0 : var4.getSubs().size();
                if (var5 == 0) {
                    var4.setLastLevel(var4.getLevel());
                }

                if (var5 == 0 && var4.getLevel() != var2.getMaxLevel() - 1) {
                    var4.setLastLevel(var2.getMaxLevel() - 1);
                }

                this.setCrossFiledLastLevel(var4.getSubs(), var2);
            }

        }
    }

    public String loopFieldUp(CrossField var1, Map var2, int var3, String var4) {
        String var5 = null;
        String var6 = var1.getType();
        Boolean var7 = var1.getNotCondition();
        if (!"none".equals(var6) && (var7 == null || !var7)) {
            if (var6.equals("kpiOther")) {
                if (var1.getValue() != null && var1.getValue().length() > 0) {
                    var2.put(this.kpiValueColumn, var1.getValue());
                }
            } else if (var4 == null || var4.length() == 0) {
                var2.put(var1.getAlias(), var1.getValue());
            }

            CrossFieldOther var8;
            Iterator var9;
            if (var4 != null && var4.length() != 0) {
                if (var1 == this.currFormula) {
                    if (var1.getOther() != null) {
                        var9 = var1.getOther().iterator();

                        label105:
                        while(true) {
                            do {
                                do {
                                    do {
                                        if (!var9.hasNext()) {
                                            break label105;
                                        }

                                        var8 = (CrossFieldOther)var9.next();
                                    } while(var8.getId() == null);
                                } while(var8.getId().length() <= 0);
                            } while(!var8.getId().equals(var4));

                            if (!var8.getType().equals("kpiOther") || var8.getValue() != null && var8.getValue().length() != 0) {
                                var2.put(var8.getAlias(), var8.getRetValue());
                            }

                            var5 = var8.getRemoveKey();
                        }
                    }
                } else {
                    var2.put(var1.getAlias(), var1.getValue());
                }
            } else if (var1.getOther() != null) {
                var9 = var1.getOther().iterator();

                label118:
                while(true) {
                    do {
                        if (!var9.hasNext()) {
                            break label118;
                        }

                        var8 = (CrossFieldOther)var9.next();
                    } while(var8.getId() != null && var8.getId().length() != 0);

                    if (var8.getType().equals("kpiOther")) {
                        if (var8.getValue() != null && var8.getValue().length() > 0) {
                            var2.put(this.kpiValueColumn, var8.getRetValue());
                        }
                    } else {
                        var2.put(var8.getAlias(), var8.getRetValue());
                    }
                }
            }
        }

        if ("kpi".equals(var6) || "kpiOther".equals(var6)) {
            if (this.currKpiNode != null) {
                if (!"kpiOther".equals(this.currKpiNode.getType())) {
                    if ("kpiOther".equals(var6)) {
                        this.whereIn = var3;
                        this.backKpiNode = this.currKpiNode;
                        this.currKpiNode = var1;
                    }
                } else {
                    this.backKpiNode = var1;
                    this.backKpiIn = var3;
                }
            } else {
                this.whereIn = var3;
                this.currKpiNode = var1;
                if (var1.getType().equalsIgnoreCase("kpiOther") && var1.getOther() != null && var1.getOther().size() > 0) {
                    this.backKpiIn = var3;
                    this.backKpiNode = (CrossKpi)var1.getOther().get(0);
                }
            }
        }

        if (var1.getFormula() != null && var1.getFormula().length() > 0) {
            this.formulaPos = var3;
            this.currFormula = var1;
        }

        if (var1.getParent() != null) {
            String var10 = this.loopFieldUp(var1.getParent(), var2, var3, var4);
            if (var10 != null && var10.length() > 0) {
                var5 = var10;
            }
        }

        return var5;
    }

    public boolean isCheckData(Map var1, Map var2) {
        boolean var3 = true;
        Iterator var5 = var2.entrySet().iterator();

        while(var5.hasNext()) {
            Entry var4 = (Entry)var5.next();
            Object var6 = var1.get(var4.getKey());
            if (var6 == null) {
                var6 = "NULLVAL";
            }

            Object var7 = var4.getValue();
            if (var7 instanceof List) {
                List var8 = (List)var7;
                if (!this.fieldValuesExistInData(var8, var6)) {
                    var3 = false;
                    break;
                }
            } else if (!var6.toString().equals(var4.getValue())) {
                var3 = false;
                break;
            }
        }

        return var3;
    }

    public boolean fieldValuesExistInData(List var1, Object var2) {
        boolean var3 = false;
        Iterator var5 = var1.iterator();

        while(var5.hasNext()) {
            Map var4 = (Map)var5.next();
            if (var4.get("fid").equals(var2)) {
                var3 = true;
                break;
            }
        }

        return var3;
    }

    public Double findKipValue(List var1, CrossKpi var2, CrossField var3, CrossField var4, String var5) {
        if (var2 == null) {
            return null;
        } else {
            if (var2.getExtDs() != null && var2.getExtDs().length() > 0) {
                List var6 = (List)this.crossReport.getExtDatas().get(var2.getExtDs());
                var6 = this.querySubData(var4, var6, 1, (String)null);
                var1 = this.querySubData(var3, var6, 2, (String)null);
            }

            String var15 = null;
            if ("kpi".equalsIgnoreCase(var2.getType())) {
                var15 = getKpiValueColumn();
            } else {
                if (this.currFormula != null) {
                    Iterator var8 = this.currFormula.getOther().iterator();

                    while(var8.hasNext()) {
                        CrossFieldOther var7 = (CrossFieldOther)var8.next();
                        if (var7.getId() != null && var7.getId().length() > 0 && var5 != null && var7.getId().equals(var5)) {
                            var15 = var7.getAlias();
                        }
                    }
                }

                if (var15 == null) {
                    var15 = var2.getAlias();
                }
            }

            Double var16 = null;
            int var17 = var1.size();
            if (var17 == 0) {
                var16 = null;
            } else {
                Object var10;
                String var11;
                if (var17 == 1) {
                    Map var9 = (Map)var1.get(0);
                    var10 = var9.get(var15);
                    if (var10 == null) {
                        if (!var9.containsKey(var15)) {
                            var11 = ConstantsEngine.replace("crossReport 中的数据集缺少 $0 字段.", var15);
                            a.info(var11);
                        }
                    } else if (var10 instanceof BigDecimal) {
                        var16 = ((BigDecimal)var10).doubleValue();
                    } else if (var10 instanceof Integer) {
                        var16 = ((Integer)var10).doubleValue();
                    } else if (var10 instanceof Long) {
                        var16 = ((Long)var10).doubleValue();
                    } else if (var10 instanceof Float) {
                        var16 = ((Float)var10).doubleValue();
                    } else if (var10 instanceof Double) {
                        var16 = (Double)var10;
                    } else {
                        var16 = null;
                    }
                } else {
                    ArrayList var18 = new ArrayList();
                    Iterator var22 = var1.iterator();

                    while(var22.hasNext()) {
                        Map var20 = (Map)var22.next();
                        Object var12 = var20.get(var15);
                        if (var12 != null) {
                            if (var12 instanceof BigDecimal) {
                                var18.add(((BigDecimal)var12).doubleValue());
                            } else if (var12 instanceof Long) {
                                var18.add(((Long)var12).doubleValue());
                            } else if (var12 instanceof Integer) {
                                var18.add(((Integer)var12).doubleValue());
                            } else if (var12 instanceof Float) {
                                var18.add(((Float)var12).doubleValue());
                            } else if (var12 instanceof Double) {
                                var18.add((Double)var12);
                            } else {
                                var18.add((Object)null);
                            }
                        } else if (!var20.containsKey(var15)) {
                            String var13 = ConstantsEngine.replace("crossReport 中的数据集缺少 $0 字段.", var15);
                            a.info(var13);
                        }
                    }

                    if (var18.size() == 0) {
                        var16 = null;
                    } else {
                        var10 = null;
                        if ("kpiOther".equalsIgnoreCase(var3.getType())) {
                            var3 = var3.getParent();
                        }

                        if (var3 != null && var3.getDimAggre() != null && var4.getDimAggre() != null) {
                            return null;
                        }

                        String var21;
                        if ((var3 == null || var3.getDimAggre() == null) && var4.getDimAggre() == null) {
                            var21 = this.getCurrKpiAgg(var2);
                        } else {
                            var21 = var3 != null && var3.getDimAggre() != null ? var3.getDimAggre() : var4.getDimAggre();
                        }

                        if (var21 == null) {
                            var11 = ConstantsEngine.replace("指标数据需要聚合，但未设置聚合方式. aggregation = null, kpi/kpiOther = $0", var2.getDesc());
                            throw new ExtRuntimeException(var11);
                        }

                        if ("count".equalsIgnoreCase(var21)) {
                            var16 = new Double((double)var18.size());
                            return var16;
                        }

                        Iterator var14;
                        double var23;
                        Double var26;
                        if ("sum".equalsIgnoreCase(var21)) {
                            var23 = 0.0D;

                            for(var14 = var18.iterator(); var14.hasNext(); var23 += var26) {
                                var26 = (Double)var14.next();
                            }

                            var16 = var23;
                        } else if ("avg".equalsIgnoreCase(var21)) {
                            var23 = 0.0D;

                            for(var14 = var18.iterator(); var14.hasNext(); var23 += var26) {
                                var26 = (Double)var14.next();
                            }

                            var16 = var23 / (double)var18.size();
                        } else if ("max".equalsIgnoreCase(var21)) {
                            var16 = (Double)Collections.max(var18);
                        } else if ("min".equalsIgnoreCase(var21)) {
                            var16 = (Double)Collections.min(var18);
                        } else if ("var".equalsIgnoreCase(var21)) {
                            var16 = DataSetAggregation.getVariance(var18);
                        } else if ("sd".equalsIgnoreCase(var21)) {
                            var16 = DataSetAggregation.getStandardDiviation(var18);
                        } else if ("middle".equalsIgnoreCase(var21)) {
                            int var25 = var18.size();
                            int var24 = Math.round((float)(var25 / 2));
                            var16 = (Double)var18.get(var24);
                        }
                    }
                }
            }

            BigDecimal var19 = this.getCurrKpiRate(var2);
            if (var19 != null && var16 != null && var19.intValue() != 0) {
                var16 = var16 / var19.doubleValue();
            }

            return var16;
        }
    }

    public List findExistKpi(CrossReportContext var1) {
        int var2 = this.kpiPos;
        if (var2 == 0) {
            throw new ExtRuntimeException("指标需要定制，但未在rows/cols 上设置 rebuild 属性");
        } else {
            ArrayList var3 = new ArrayList();
            if (var2 == 1) {
                this.a(var1.getCrossRows().getRows(), var3);
            }

            if (var2 == 2) {
                this.a(var1.getCrossCols().getCols(), var3);
            }

            return var3;
        }
    }

    private void a(List var1, List var2) {
        if (var1 != null) {
            CrossField var3;
            for(Iterator var4 = var1.iterator(); var4.hasNext(); this.a(var3.getSubs(), var2)) {
                var3 = (CrossField)var4.next();
                if (var3.getType().equals("kpi")) {
                    var2.add(var3);
                }
            }

        }
    }

    public int loopFieldParent(CrossField var1) {
        int var2 = 0;
        if (var1.getParent() != null) {
            ++var2;
            var2 += this.loopFieldParent(var1.getParent());
        }

        return var2;
    }

    public boolean paramIsInDirllDim(String var1) {
        List var2 = this.crossReport.getDims();
        if (var2 != null && var2.size() != 0) {
            boolean var3 = false;
            Iterator var5 = var2.iterator();

            while(var5.hasNext()) {
                RowDimContext var4 = (RowDimContext)var5.next();
                if (var4.getCode().equals(var1)) {
                    var3 = true;
                    break;
                }
            }

            return var3;
        } else {
            return false;
        }
    }

    public void writerFieldParent(CrossField var1, ExtWriter var2, int var3) {
        if (var1.getParent() != null) {
            CrossField var4 = var1.getParent();
            --var3;
            String var6 = var4.getDesc();
            var2.print(" 'h" + var3 + "': '" + var6 + "',");
            var2.print(" 'h_" + var3 + "': '" + var4.getValue() + "',");
            this.writerFieldParent(var4, var2, var3);
        }

    }

    public boolean isKpiExist(String var1, String var2, List var3) {
        boolean var4 = false;
        if (var3 == null) {
            return var4;
        } else {
            Iterator var6 = var3.iterator();

            while(var6.hasNext()) {
                CrossField var5 = (CrossField)var6.next();
                if (var2.equals(var5.getValue()) && var5.getOther() != null) {
                    String var7 = ((CrossFieldOther)var5.getOther().get(0)).getValue();
                    if (var1.equals(var7)) {
                        var4 = true;
                        break;
                    }
                }
            }

            return var4;
        }
    }

    public CrossField getRow(List var1, int var2) {
        CrossField var3 = (CrossField)var1.get(0);

        for(int var4 = 0; var4 < var2; ++var4) {
            if (var3.getSubs().size() > 0) {
                var3 = (CrossField)var3.getSubs().get(0);
            }
        }

        return var3;
    }

    public Double findFormulaValue(Double var1, CrossField var2, CrossField var3) {
        CrossKpi var4 = this.currFormula;
        String var5 = var4.getFormula();
        CrossField var6 = (CrossField)var4;
        List var7 = var6.getOther();
        if (var7 != null && var7.size() > 0) {
            Double[] var8 = new Double[var7.size() + 1];
            if (var1 != null) {
                var8[0] = var1;
            }

            int var9 = 1;
            int var10 = 0;

            while(true) {
                if (var10 >= var7.size()) {
                    if (!this.a(var8)) {
                        String var14 = null;
                        PageBuilder$JSObject var15 = (PageBuilder$JSObject)this.b.getAttribute("ext.script.engine");
                        Object var16 = var15.getScope().get(var5, var15.getScope());
                        if (var16 == null || !(var16 instanceof Function)) {
                            String var18 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var5);
                            throw new ExtRuntimeException(var18);
                        }

                        Function var17 = (Function)var16;
                        var14 = var17.call(var15.getCt(), var15.getScope(), var15.getScope(), var8).toString();
                        var1 = new Double(var14);
                    } else {
                        var1 = null;
                    }
                    break;
                }

                CrossFieldOther var11 = (CrossFieldOther)var7.get(var10);
                if (var11.getId() != null) {
                    List var12 = this.querySubData(var3, this.crossReport.loadOptions(), 1, var11.getId());
                    var12 = this.querySubData(var2, var12, 2, var11.getId());
                    Double var13 = this.findKipValue(var12, this.currKpiNode, var2, var3, var11.getId());
                    if (var13 != null) {
                        var8[var9] = var13;
                    }

                    ++var9;
                }

                ++var10;
            }
        }

        return var1;
    }

    private boolean a(Double[] var1) {
        boolean var2 = false;
        Double[] var6 = var1;
        int var5 = var1.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            Double var3 = var6[var4];
            if (var3 == null) {
                var2 = true;
                break;
            }
        }

        return var2;
    }

    public String getCurrKpiFmt(CrossKpi var1) {
        String var2 = var1.getFormatPattern();
        if (var2 == null || var2.length() == 0) {
            var2 = this.backKpiNode != null ? this.backKpiNode.getFormatPattern() : null;
        }

        return var2;
    }

    public String getCurrKpiAgg(CrossKpi var1) {
        String var2 = var1.getAggregation();
        if (var2 == null || var2.length() == 0) {
            var2 = this.backKpiNode != null ? this.backKpiNode.getAggregation() : null;
        }

        return var2;
    }

    public BigDecimal getCurrKpiRate(CrossKpi var1) {
        BigDecimal var2 = var1.getKpiRate();
        if (var2 == null) {
            var2 = this.backKpiNode != null ? this.backKpiNode.getKpiRate() : null;
        }

        return var2;
    }

    public String writerHeader(CrossField var1, String var2) {
        String var3 = var1.getDesc();
        if (var1.getType().equals("frd") && "month".equalsIgnoreCase(var1.getDateType()) && var3 != null && var3.length() == 6) {
            var3 = var3.substring(0, 4) + "-" + var3.substring(4, 6);
        }

        if (var1.getType().equals("frd") && "day".equalsIgnoreCase(var1.getDateType()) && var3 != null && var3.length() == 8) {
            var3 = var3.substring(0, 4) + "-" + var3.substring(4, 6) + "-" + var3.substring(6, 8);
        }

        if (var1.getBrstep() != null) {
            var3 = substringHeader(var3.trim(), var1.getBrstep(), var2);
        }

        return var3;
    }

    public String writerUnit(BigDecimal var1) {
        if (var1 == null) {
            return "";
        } else {
            int var2 = var1.intValue();
            if (var2 == 1) {
                return "";
            } else if (var2 == 100) {
                return "百";
            } else if (var2 == 1000) {
                return "千";
            } else if (var2 == 10000) {
                return "万";
            } else if (var2 == 1000000) {
                return "百万";
            } else {
                return var2 == 100000000 ? "亿" : "*" + var2;
            }
        }
    }

    public static boolean isChinese(char var0) {
        if (var0 >= 19968 && var0 <= 171941) {
            return true;
        } else if (var0 == 12289) {
            return true;
        } else {
            return var0 == 12290;
        }
    }

    public static int substringCount(String var0) {
        char[] var1 = var0.toCharArray();
        int var2 = 0;
        int[] var3 = new int[4];

        int var4;
        for(var4 = 0; var4 < var1.length; ++var4) {
            if (isChinese(var1[var4])) {
                var2 += 2;
            } else {
                ++var2;
            }

            if (var2 > 14 && var3[0] == 0) {
                var3[0] = var4;
            }

            if (var2 > 28 && var3[1] == 0) {
                var3[1] = var4;
            }

            if (var2 > 42 && var3[2] == 0) {
                var3[2] = var4;
            }

            if (var2 > 56) {
                var3[3] = var4;
                break;
            }
        }

        var4 = 0;
        int[] var8 = var3;
        int var7 = var3.length;

        for(int var6 = 0; var6 < var7; ++var6) {
            int var5 = var8[var6];
            if (var5 != 0) {
                ++var4;
            }
        }

        return var4;
    }

    public static String substringHeader(String var0, int var1, String var2) {
        char[] var3 = var0.toCharArray();
        int var4 = 0;
        int[] var5 = new int[4];

        for(int var6 = 0; var6 < var3.length; ++var6) {
            if (isChinese(var3[var6])) {
                var4 += 2;
            } else {
                ++var4;
            }

            if (var4 > var1 * 2 && var5[0] == 0) {
                var5[0] = var6;
            }

            if (var4 > var1 * 4 && var5[1] == 0) {
                var5[1] = var6;
            }

            if (var4 > var1 * 6 && var5[2] == 0) {
                var5[2] = var6;
            }

            if (var4 > var1 * 8) {
                var5[3] = var6;
                break;
            }
        }

        String var10 = "<br/>";
        if ("xls".equals(var2)) {
            var10 = "\r\n";
        }

        int var7 = 0;
        String var8 = "";

        for(int var9 = 0; var9 < var5.length; ++var9) {
            if (var5[var9] > 0) {
                var8 = var8 + var0.substring(var7, var5[var9]) + var10;
                var7 = var5[var9];
            }
        }

        if (var8.length() == 0) {
            return var0;
        } else {
            return var8 + var0.substring(var7, var0.length());
        }
    }
}
