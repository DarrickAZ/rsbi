//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import org.mozilla.javascript.Function;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class ExtChartUtils {
    public ExtChartUtils() {
    }

    public static List createChartData(List var0, ChartContext var1, PageBuilder$JSObject var2) throws ExtConfigException {
        ArrayList var3 = new ArrayList();

        for(int var4 = 0; var4 < var0.size(); ++var4) {
            Map var5 = (Map)var0.get(var4);
            String var6 = (String)var5.get("isuse");
            String var7 = (String)var5.get("id");
            if (var6 == null || var7 == null) {
                throw new ExtConfigException("chart标签中如果配置了 formula 属性，则数据中必须包含 isuse 和 id字段. ");
            }

            if ("1".equals(var6)) {
                var3.add(var5);
            }
        }

        List var17 = getXcol(var1, var0);
        List var18 = getIDs(var1, var0);
        Collections.sort(var18);

        for(int var19 = 0; var19 < var1.getFormula().length; ++var19) {
            for(int var20 = 0; var20 < var17.size(); ++var20) {
                Object[] var8 = new Object[var18.size()];
                String var9 = (String)var17.get(var20);
                System.out.println("xVal=" + var9);
                List var10 = getOneXValDatas(var9, var1, var0);

                for(int var11 = 0; var11 < var10.size(); ++var11) {
                    var8[var11] = getYcolValByID((String)var18.get(var11), var10, var1);
                }

                String var21 = null;
                String var12 = var1.getFormula()[var19];
                Object var13 = var2.getScope().get(var12, var2.getScope());
                if (var13 == null || !(var13 instanceof Function)) {
                    String var22 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var12);
                    throw new ExtRuntimeException(var22);
                }

                Function var14 = (Function)var13;
                Object[] var15 = new Object[]{var8};
                var21 = var14.call(var2.getCt(), var2.getScope(), var2.getScope(), var15).toString();
                HashMap var16 = new HashMap();
                var16.put(var1.getXcol(), var9);
                var16.put(var1.getYcol(), new BigDecimal(var21));
                var16.put(var1.getScol(), var1.getFormulaName()[var19]);
                var3.add(var16);
            }
        }

        return var3;
    }

    public static void convertData2XML(StringBuffer var0, String var1, ChartContext var2, List var3) {
        String[] var4 = new String[]{"B4DAF8", "F6BF17", "92BE0F", "00CC67", "866DAF", "FE52D8", "B9BB00", "BA3473"};
        String[] var5 = new String[]{"DD0000", "F99E07", "393939", "059B37", "08089C", "0909FF", "9B069B"};
        String[] var6 = new String[]{"105d87", "2a789e", "4e98b5", "7dc4da", "9fe2f3"};
        int var7 = 0;
        int var8 = 0;
        int var9 = 0;
        String var10 = "";
        String var11 = "";
        String var12 = var2.getProperty();
        var10 = a(var12, "numberSuffix");
        var11 = a(var12, "sNumberSuffix");
        List var13 = getScol(var2, var3);
        String var16;
        String var22;
        String var40;
        if ("single".equals(var2.getDtype())) {
            Map var14 = (Map)var3.get(0);
            String var15 = (String)var14.get("unit");
            var16 = (String)var14.get("fmt");
            DecimalFormat var17;
            if (var15 != null && var16 != null) {
                var17 = new DecimalFormat(var16);

                for(int var34 = 0; var34 < var3.size(); ++var34) {
                    String var36 = (String)((Map)var3.get(var34)).get(var2.getXcol());
                    Object var39 = ((Map)var3.get(var34)).get(var2.getYcol());
                    var40 = "0";
                    if (var39 != null) {
                        var40 = var17.format(var39);
                    }

                    if (("%".equals(var10) || "%".equals(var15)) && var39 != null) {
                        var39 = Double.valueOf(var39.toString()) * 100.0D;
                        var15 = "";
                    }

                    var0.append("<set label='" + var36 + "' value='" + var39 + "' toolText ='" + var36 + "：" + var40 + var15 + "'  />");
                }
            } else {
                var16 = var16 == null ? "#,##0.##" : var16;
                var17 = new DecimalFormat(var16);
                Iterator var19 = var3.iterator();

                while(var19.hasNext()) {
                    Map var18 = (Map)var19.next();
                    String var20 = (String)var18.get(var2.getXcol());
                    Object var21 = var18.get(var2.getYcol());
                    var22 = "0";
                    if (var21 != null) {
                        var17.format(var21);
                    }

                    var0.append("<set label='" + var20 + "' value='" + var21 + "' ");
                    var0.append("/>");
                }
            }

            ++var8;
            if (var8 == var5.length) {
                boolean var29 = false;
            }
        } else {
            var0.append("<categories>");

            int var30;
            for(var30 = 0; ((String)var13.get(var30)).length() >= 3 && "avg".equalsIgnoreCase(((String)var13.get(var30)).substring(0, 3)); ++var30) {
            }

            List var31 = getOneScolDatas((String)var13.get(var30), var2, var3);
            Iterator var33 = var31.iterator();

            while(var33.hasNext()) {
                Map var32 = (Map)var33.next();
                String var35 = (String)var32.get(var2.getXcol());
                var0.append("<category label='" + var35 + "' />");
            }

            var0.append("</categories>");
            var33 = var13.iterator();

            while(true) {
                while(var33.hasNext()) {
                    var16 = (String)var33.next();
                    boolean var37 = false;
                    if (var2.getRightSer() != null && var2.getRightSer().equals(var16)) {
                        var37 = true;
                    }

                    List var38;
                    Map var41;
                    if (var16.length() >= 3 && "avg".equalsIgnoreCase(var16.substring(0, 3))) {
                        var38 = getOneScolDatas(var16, var2, var3);
                        var41 = (Map)var38.get(0);
                        var40 = (String)var41.get("unit") == null ? "" : (String)var41.get("unit");
                        var22 = ((Map)var38.get(0)).get("fmt") == null ? "#,##0.00" : (String)((Map)var38.get(0)).get("fmt");
                        BigDecimal var43 = (BigDecimal)var41.get(var2.getYcol());
                        BigDecimal var45 = var43;
                        if (var37) {
                            if (("%".equals(var11) || "%".equals(var40)) && var43 != null) {
                                var45 = var43.multiply(new BigDecimal(100));
                            }
                        } else if (("%".equals(var10) || "%".equals(var40)) && var43 != null) {
                            var45 = var43.multiply(new BigDecimal(100));
                        }

                        var0.append("<trendlines>");
                        var0.append("<line startValue='" + var45 + "'");
                        if (var37) {
                            var0.append(" parentYAxis ='s'");
                        }

                        var0.append(" color='" + var6[var9] + "'");
                        DecimalFormat var47 = new DecimalFormat(var22);
                        var0.append(" displayValue=' ' toolText='" + var41.get(var2.getXcol()) + ":\\n" + var47.format(var43));
                        var0.append("%".equals(var40) ? "" : var40);
                        var0.append("' showOnTop='1'/>");
                        var0.append("</trendlines>");
                        ++var9;
                        if (var9 == var6.length) {
                            var9 = 0;
                        }
                    } else {
                        var0.append("<dataset seriesName='" + var16 + "' showValues='0' ");
                        if (var37) {
                            var0.append(" parentYAxis='S'");
                            var0.append(" color='" + var5[var8] + "'");
                        } else {
                            var0.append(" color='" + var4[var7] + "'");
                        }

                        var0.append(">");
                        var38 = getOneScolDatas(var16, var2, var3);
                        var41 = (Map)var38.get(0);
                        var40 = (String)var41.get("unit");
                        var22 = (String)var41.get("fmt");
                        if (var40 != null && var22 != null) {
                            DecimalFormat var42 = new DecimalFormat(var22);
                            Iterator var46 = var38.iterator();

                            while(var46.hasNext()) {
                                Map var44 = (Map)var46.next();
                                Object var48 = var44.get(var2.getYcol());
                                String var27 = var44.get(var2.getXcol()).toString();
                                String var28 = "0";
                                if (var48 != null) {
                                    var42.format(var48);
                                }

                                var0.append("<set value='" + var48 + "' ");
                                if (var37) {
                                    var0.append(" color='" + var5[var8] + "' />");
                                } else {
                                    var0.append(" color='" + var4[var7] + "' />");
                                }
                            }
                        } else {
                            Iterator var24 = var38.iterator();

                            while(var24.hasNext()) {
                                Map var23 = (Map)var24.next();
                                Object var25 = var23.get(var2.getYcol());
                                if (var23.get(var2.getXcol()) != null) {
                                    String var26 = var23.get(var2.getXcol()).toString();
                                    var0.append("<set value='" + var25 + "'");
                                    var0.append("/>");
                                }
                            }
                        }

                        var0.append("</dataset>");
                        if (var37) {
                            ++var8;
                        } else {
                            ++var7;
                        }

                        if (var7 == var4.length) {
                            var7 = 0;
                        }

                        if (var8 == var5.length) {
                            var8 = 0;
                        }
                    }
                }

                return;
            }
        }

    }

    private static String a(String var0, String var1) {
        String var2 = "";
        var0 = var0.replaceAll("\"", "'");
        int var3 = var0.indexOf(var1);
        if (var3 != -1) {
            var2 = var0.substring(var3);
            var2 = var2.substring(var2.indexOf("'") + 1);
            var2 = var2.substring(0, var2.indexOf("'"));
        }

        return var2;
    }

    public static List getOneScolDatas(String var0, ChartContext var1, List var2) {
        ArrayList var3 = new ArrayList();

        for(int var4 = 0; var4 < var2.size(); ++var4) {
            Object var5 = ((Map)var2.get(var4)).get(var1.getScol());
            String var6 = var5 == null ? "" : var5.toString();
            if (var6.equals(var0)) {
                var3.add((Map)var2.get(var4));
            }
        }

        return var3;
    }

    public static List getOneXValDatas(String var0, ChartContext var1, List var2) {
        ArrayList var3 = new ArrayList();

        for(int var4 = 0; var4 < var2.size(); ++var4) {
            String var5 = ((Map)var2.get(var4)).get(var1.getXcol()).toString();
            if (var5 == null) {
                String var6 = ConstantsEngine.replace("图形中配置的x $0 在sql中 不存在.", var1.getXcol());
                throw new ExtRuntimeException(var6);
            }

            if (var0.equals(var5)) {
                var3.add((Map)var2.get(var4));
            }
        }

        return var3;
    }

    public static List getScol(ChartContext var0, List var1) {
        ArrayList var2 = new ArrayList();
        if (var0.getScol() != null && var0.getScol().length() != 0) {
            for(int var3 = 0; var3 < var1.size(); ++var3) {
                Object var4 = ((Map)var1.get(var3)).get(var0.getScol());
                String var5 = var4 == null ? "" : var4.toString();
                if (var5 == null) {
                    String var6 = ConstantsEngine.replace("图形中配置的ser $0  在sql中不存在.", var0.getScol());
                    throw new ExtRuntimeException(var6);
                }

                if (!var2.contains(var5)) {
                    var2.add(var5);
                }
            }

            return var2;
        } else {
            return var2;
        }
    }

    public static List getXcol(ChartContext var0, List var1) {
        ArrayList var2 = new ArrayList();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Object var4 = ((Map)var1.get(var3)).get(var0.getXcol());
            String var5 = var4 == null ? "" : var4.toString();
            if (!var2.contains(var5)) {
                var2.add(var5);
            }
        }

        return var2;
    }

    public static List getIDs(ChartContext var0, List var1) {
        ArrayList var2 = new ArrayList();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Object var4 = ((Map)var1.get(var3)).get("id");
            String var5 = var4 == null ? "" : var4.toString();
            if (!var2.contains(var5)) {
                var2.add(var5);
            }
        }

        return var2;
    }

    public static Object getYcolValByID(String var0, List var1, ChartContext var2) {
        Object var3 = null;
        Iterator var5 = var1.iterator();

        while(var5.hasNext()) {
            Map var4 = (Map)var5.next();
            Object var6 = var4.get("id");
            String var7 = var6 == null ? "" : var6.toString();
            if (var7.equals(var0)) {
                Object var8 = var4.get(var2.getYcol());
                var3 = var8;
                break;
            }
        }

        return var3;
    }

    public static Object findYvalByXval(String var0, String var1, String var2, List var3) {
        Object var4 = null;

        for(int var5 = 0; var5 < var3.size(); ++var5) {
            Map var6 = (Map)var3.get(var5);
            Object var7 = var6.get(var1);
            String var8 = var7 == null ? "" : var7.toString();
            if (var0.equals(var8)) {
                var4 = var6.get(var2);
                break;
            }
        }

        return var4;
    }

    public static Object findYvalByXval(Object var0, String var1, String var2, String var3, Object var4, List var5) {
        Object var6 = null;

        for(int var7 = 0; var7 < var5.size(); ++var7) {
            Map var8 = (Map)var5.get(var7);
            Object var9 = var8.get(var1);
            Object var10 = var8.get(var3);
            if (var0 != null && var0.equals(var9) && var4 != null && var4.equals(var10)) {
                var6 = var8.get(var2);
                break;
            }
        }

        return var6;
    }
}
