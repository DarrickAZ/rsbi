//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart;

import com.opensymphony.xwork2.util.ValueStack;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import com.ruisi.ext.runtime.tag.CalendarTag;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import net.sf.json.JSONObject;
import org.apache.struts2.views.jsp.TagUtils;

public class ChartUtils {
    public static int chartMaxWidth = 750;
    public static int chartMinWidth = 500;

    public ChartUtils() {
    }

    public static void print(PageContext var0, Object var1) throws JspException {
        try {
            var0.getOut().print(var1);
        } catch (IOException var3) {
            throw new JspException(var3);
        }
    }

    public static void println(PageContext var0, Object var1) throws JspException {
        try {
            var0.getOut().println(var1);
        } catch (IOException var3) {
            throw new JspException(var3);
        }
    }

    public static ValueStack getStack(PageContext var0) {
        return TagUtils.getStack(var0);
    }

    public static Object findValue(PageContext var0, String var1) {
        return TagUtils.getStack(var0).findValue(var1);
    }

    public static double[] findMaxMin(List var0, String var1) {
        double var2 = 1.7976931348623157E308D;
        double var4 = 0.0D;

        for(int var6 = 0; var6 < var0.size(); ++var6) {
            Map var7 = (Map)var0.get(var6);
            double var8 = getKpiValue(var7, var1);
            if (var8 < var2) {
                var2 = var8;
            }

            if (var8 > var4) {
                var4 = var8;
            }
        }

        return new double[]{var2, var4};
    }

    public static String writerUnit(Integer var0) {
        if (var0 == null) {
            return "";
        } else {
            int var1 = var0;
            if (var1 == 1) {
                return "";
            } else if (var1 == 100) {
                return "百";
            } else if (var1 == 1000) {
                return "千";
            } else if (var1 == 10000) {
                return "万";
            } else if (var1 == 1000000) {
                return "百万";
            } else {
                return var1 == 100000000 ? "亿" : "*" + var1;
            }
        }
    }

    public static double getKpiValue(Map var0, String var1) {
        Object var2 = var0.get(var1);
        double var3 = 0.0D;
        if (var2 != null && var2 instanceof Integer) {
            var3 = ((Integer)var2).doubleValue();
        } else if (var2 != null && var2 instanceof Long) {
            var3 = ((Long)var2).doubleValue();
        } else if (var2 != null && var2 instanceof Double) {
            var3 = (Double)var2;
        } else if (var2 != null && var2 instanceof BigDecimal) {
            var3 = ((BigDecimal)var2).doubleValue();
        } else if (var2 != null && var2 instanceof Float) {
            var3 = ((Float)var2).doubleValue();
        }

        return var3;
    }

    public static String NumFormat(Object var0, String var1) {
        if (var0 == null) {
            return "";
        } else {
            if (var0 instanceof String) {
                try {
                    var0 = new Double(Double.parseDouble(var0.toString()));
                } catch (NumberFormatException var3) {
                    throw new RuntimeException(var0 + " is a String, but it can parse to double!");
                }
            }

            if (!(var0 instanceof Number)) {
                throw new RuntimeException(var0 + " is not a type of Number!");
            } else {
                DecimalFormat var2 = new DecimalFormat();
                var2.applyPattern(var1);
                return var2.format(var0);
            }
        }
    }

    public static double getDoubleObject(Object var0) {
        if (var0 == null) {
            return 0.0D;
        } else if (var0 instanceof Number) {
            return ((Number)var0).doubleValue();
        } else if (var0 instanceof String) {
            try {
                return Double.parseDouble((String)var0);
            } catch (NumberFormatException var2) {
                System.out.println("parse " + var0 + " to double error,then return 0");
                return 0.0D;
            }
        } else {
            return 0.0D;
        }
    }

    public static Map listXcolDatas(List var0, ChartContext var1, int var2) {
        HashMap var3 = new HashMap();

        for(int var4 = 0; var4 < var0.size(); ++var4) {
            Map var5 = (Map)var0.get(var4);
            Object var6 = var5.get(var1.getXcol());
            Object var7 = var5.get(var1.getXcolDesc());
            if (var7 == null) {
                var7 = "NULLVAL";
            }

            if (!var3.containsKey(var6)) {
                var3.put(var6 == null ? "未知" : var6.toString(), var7);
            }

            if (var2 != -1 && var3.size() > var2) {
                break;
            }
        }

        return var3;
    }

    public static int autoWidth(List var0) {
        int var1 = 0;
        int var2 = 0;

        for(Iterator var4 = var0.iterator(); var4.hasNext(); ++var2) {
            Object var3 = var4.next();
            String var5 = var3 == null ? "" : var3.toString();
            var1 += var5.length();
        }

        int var6 = 40 + var1 * 11 + var2 * 26 + 40;
        if (var6 < chartMinWidth) {
            var6 = chartMinWidth;
        }

        if (var6 > chartMaxWidth) {
            var6 = chartMaxWidth;
        }

        return var6;
    }

    public static Object findRow(String var0, String var1, String var2, Object var3, Object var4, List var5) {
        Object var6 = null;

        for(int var7 = 0; var7 < var5.size(); ++var7) {
            Map var8 = (Map)var5.get(var7);
            Object var9 = var8.get(var0);
            Object var10 = var8.get(var2);
            if (var0 != null && var0.length() != 0) {
                if (var9 != null && var9.equals(var3) && var10 != null && var10.equals(var4)) {
                    var6 = var8.get(var1);
                    break;
                }
            } else if (var10 != null && var10.equals(var4)) {
                var6 = var8.get(var1);
                break;
            }
        }

        return var6;
    }

    public static String array2string(String[] var0) {
        if (var0 != null && var0.length != 0) {
            StringBuffer var1 = new StringBuffer("[");

            for(int var2 = 0; var2 < var0.length; ++var2) {
                var1.append("'");
                var1.append(var0[var2]);
                var1.append("'");
                if (var0.length - 1 != var2) {
                    var1.append(",");
                }
            }

            var1.append("]");
            return var1.toString();
        } else {
            return null;
        }
    }

    public static String list2string(List var0) {
        if (var0 != null && var0.size() != 0) {
            StringBuffer var1 = new StringBuffer("");

            for(int var2 = 0; var2 < var0.size(); ++var2) {
                var1.append("\"");
                var1.append(var0.get(var2));
                var1.append("\"");
                if (var0.size() - 1 != var2) {
                    var1.append(",");
                }
            }

            return var1.toString();
        } else {
            return "";
        }
    }

    public static String crtChartDivStyle(String var0, String var1, String var2) {
        String var3 = "width: " + var0 + (var0.endsWith("%") ? "" : "px") + "; height: " + var1 + (var1.endsWith("%") ? "" : "px") + "; ";
        if ("center".equals(var2)) {
            var3 = var3 + "margin: 0 auto;";
        }

        return var3;
    }

    public static String crtPicSize(String var0, String var1) {
        String var2 = var0;
        if (var0.endsWith("%")) {
            String var3 = var0.replace("%", "");
            int var4 = Integer.parseInt(var3);
            var2 = String.valueOf(var4 / 100 * 800);
        }

        return "width:" + var2 + ", height:" + var1 + ",";
    }

    public static String replaeUnit(String var0) {
        return var0 == null ? var0 : var0.replaceAll("\\(.+\\)", "");
    }

    public static void formatDate(StringBuffer var0, String var1, String var2, List var3) {
        var0.append("\tformatter:function(){ \n ");
        if ("month".equals(var1)) {
            var0.append("\tif(this.value.length != 6) return this.value; else\treturn this.value.substring(0, 4) + '-' + this.value.substring(4, 6);");
        } else if ("day".equals(var1)) {
            HashMap var4 = new HashMap();
            Iterator var6 = var3.iterator();

            while(var6.hasNext()) {
                Object var5 = var6.next();
                String var7 = CalendarTag.getFestival((String)var5, var2);
                if (var7 != null && var7.length() > 0) {
                    var4.put(var5, var7);
                }
            }

            var0.append("var jrs = " + JSONObject.fromObject(var4).toString() + "; \n");
            var0.append(" var ret = jrs[this.value] ?  jrs[this.value]  : this.value; \n");
            var0.append("\t\treturn ret; ");
        }

        var0.append(" \n}, \n");
    }

    public static Double getKpiValue(Object var0) {
        if (var0 == null) {
            return null;
        } else {
            Double var1;
            if (var0 instanceof BigDecimal) {
                var1 = ((BigDecimal)var0).doubleValue();
            } else if (var0 instanceof Integer) {
                var1 = ((Integer)var0).doubleValue();
            } else if (var0 instanceof Long) {
                var1 = ((Long)var0).doubleValue();
            } else if (var0 instanceof Float) {
                var1 = ((Float)var0).doubleValue();
            } else {
                var1 = (Double)var0;
            }

            return var1;
        }
    }

    public static void echartsClick(ExtWriter var0, ChartContext var1, ExtRequest var2, ChartConfigVO var3) {
        String var4 = var1.getShape();
        var0.println("myChart.on('click', function(params){");
        if (!var4.equals("scatter") && !var4.equals("bubble")) {
            var0.println("var xvalue = params.name;");
            var0.println("var yvalue = params.value;");
        } else {
            var0.println("var xvalue = params.value[2];");
            var0.println("var yvalue = params.value[0];");
        }

        var0.println("var svalue = params.seriesName;");
        var0.println("var pos = {left:params.event.event.clientX, top:params.event.event.clientY};");
        Map var5 = listXcolDatas(var1.loadOptions(), var1, var3.getXcnt_Num());
        var0.println("var ys = " + JSONObject.fromObject(var5) + ";");
        String var6;
        if (var3.getAction() != null && var3.getAction().length() > 0) {
            var6 = (String)var2.getAttribute("compId");
            var0.println(var3.getAction() + "(ys[xvalue], xvalue, yvalue, svalue, pos, '" + var6 + "', '" + var1.getXcol() + "','" + var1.getXcolDesc() + "','" + var3.getXdesc() + "'); ");
        }

        if (var1.getLink() != null) {
            var6 = var1.getLink().getLinkUrl();
            if (var6 != null && var6.length() != 0) {
                var0.println("url = \"" + (var6.startsWith("http://") ? var6 : "http://" + var6) + "\"; ");
                var0.println("window.open(url); ");
            } else {
                String[] var7 = var1.getLink().getUrl();
                String[] var8 = var1.getLink().getTargetId();
                String var9 = var1.getLink().getParamName();
                var0.println("chartComp_Link('" + (var9 != null && var9.length() != 0 ? var9 : var1.getXcolDesc()) + "', ys[xvalue], " + array2string(var7) + ", '" + var1.getLink().getParams() + "', " + array2string(var8) + "," + array2string(var1.getLink().getType()) + ",'" + var1.getId() + "'); ");
            }
        }

        var0.println("});");
    }

    public static String getChartXYColor(String var0) {
        if ("bigscreen".equals(var0)) {
            return "#e4e4e4";
        } else if (var0 != null && var0.length() != 0 && !"def".equals(var0)) {
            throw new RuntimeException("chart style = " + var0 + " 未定义。");
        } else {
            return "#616161";
        }
    }

    public static String getChartXYLineColor(String var0) {
        if ("bigscreen".equals(var0)) {
            return "#e4e4e4";
        } else if (var0 != null && var0.length() != 0 && !"def".equals(var0)) {
            throw new RuntimeException("chart style = " + var0 + " 未定义。");
        } else {
            return "#939393";
        }
    }

    public static String getChartlegendColor(String var0) {
        if ("bigscreen".equals(var0)) {
            return "#f3eded";
        } else if (var0 != null && var0.length() != 0 && !"def".equals(var0)) {
            throw new RuntimeException("chart style = " + var0 + " 未定义。");
        } else {
            return "#333";
        }
    }
}
