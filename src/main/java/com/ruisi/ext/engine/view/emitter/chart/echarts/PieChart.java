//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.echarts;

import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColorVO;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PieChart extends com.ruisi.ext.engine.view.emitter.chart.highcharts.PieChart {
    private List a = new ArrayList();

    public PieChart() {
    }

    @Override
    public int createChartJS(boolean var1) {
        this.a.clear();
        this.initMargin();
        String var2 = this.chart.getXcol();
        String var3 = this.chart.getYcol();
        String var4 = this.chart.getY2col();
        String var5 = this.chart.getScol();
        String var6 = this.config.getUnitCol();
        String var7 = this.config.getFormatCol();
        String var8 = this.config.getYmin();
        String var9 = this.config.getLegendPosition();
        String var10 = this.config.getLegendLayout();
        String var11 = this.chart.getWidth();
        String var12 = this.chart.getId();
        String var13 = this.config.getXdesc();
        String var14 = this.config.getYdesc();
        String var15 = this.a(this.dataList, var3, var5, var7);
        String var16 = ChartUtils.crtChartDivStyle(var11, this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + var12 + "\" style=\" " + var16 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("var myChart = echarts.init(document.getElementById('" + var12 + "'));");
        this.out.println("var option = {");
        String var17 = (String)((Map)this.dataList.get(0)).get(var7);
        if (var17 == null) {
            var17 = var7;
        }

        Object var18 = ((Map)this.dataList.get(0)).get(this.config.getUnitCol());
        String var19 = var18 == null ? null : var18.toString();
        if (var19 == null) {
            var19 = this.config.getUnitCol();
        }

        if ("%".equals(var19)) {
            var19 = "";
        }

        this.out.println(" tooltip: { formatter: function(params){ ");
        this.out.println("  return  params.name + ': '+  formatNumber(params.value, '" + (var17 == null ? "" : var17) + "')+'" + ChartUtils.writerUnit(this.chart.getRate()) + (var19 == null ? "" : var19) + "(' + params.percent+'%)';");
        this.out.println(" }},");
        if (this.config.getShowLegend()) {
            this.out.println(" legend: {");
            this.out.println("   data:[" + ChartUtils.list2string(this.a) + "],");
            this.out.print("textStyle:{color:'" + ChartUtils.getChartlegendColor(this.config.getStyle()) + "'},");
            if ("righttop".equals(var9)) {
                this.out.println("\tright:20,top:0");
            } else if ("centertop".equals(var9)) {
                this.out.println("\tleft:'center',top:0");
            } else if ("centerbottom".equals(var9)) {
                this.out.println("\tleft:'center',top:'bottom'");
            }

            if (var10 != null && var10.length() > 0) {
                this.out.println(",orient:'" + var10 + "'");
            }

            this.out.println("  },");
        }

        this.out.println(" series: [" + var15 + "],");
        this.out.print(" color:[");
        AbstractChartEmitter$ColorVO[] var20 = AbstractChartEmitter$ColorVO.values();

        for(int var21 = 0; var21 < var20.length; ++var21) {
            String var22 = var20[var21].toString();
            this.out.print("'" + var22 + "'");
            if (var21 != var20.length - 1) {
                this.out.print(",");
            }
        }

        this.out.println("]");
        this.out.println("};");
        this.out.println("myChart.setOption(option);");
        ChartUtils.echartsClick(this.out, this.chart, this.request, this.config);
        this.out.println("});");
        this.out.println(" </script>");
        return 6;
    }

    private String a(List var1, String var2, String var3, String var4) {
        this.a.clear();
        String var5 = "";
        Object var6 = "";
        String var7 = "";

        for(int var8 = 0; var8 < var1.size(); ++var8) {
            Map var9 = (Map)var1.get(var8);
            Object var10 = var9.get(this.chart.getXcol());
            Object var11 = var9.get(var2);
            var6 = var9.get(var3);
            if (var8 >= this.config.getXcnt_Num()) {
                break;
            }

            var7 = var7 + "{ name: \"" + (this.chart.getXcol() == null ? "合计" : var10) + "\",";
            var7 = var7 + "\tvalue: " + var11 + "}";
            this.a.add(var10);
            if (var8 != var1.size() - 1 && var8 != this.config.getXcnt_Num() - 1) {
                var7 = var7 + ",";
            }
        }

        String var12 = "";
        if ("np".equals(this.config.getLabelType())) {
            var12 = "{b}: {d}%";
        } else if ("nv".equals(this.config.getLabelType())) {
            var12 = "{b}: {c}";
        } else if ("n".equals(this.config.getLabelType())) {
            var12 = "{b}";
        }

        var5 = var5 + "{\t\t\t\r\ntype: 'pie',\t\r\nname: '" + var6 + "',\t\r\n" + "label:{normal:{show:" + this.config.getShowLabel() + ",formatter: '" + var12 + "'}},";
        if (this.config.getRing() != null && this.config.getRing()) {
            var5 = var5 + "radius: ['55%', '75%'], \r\n";
        }

        var5 = var5 + "data: [\t\t\t\r\n" + var7 + "\t\r\n" + "]\t\t\t\t\t\r\n" + "}\t\t\t\t\t\r\n";
        return var5;
    }
}
