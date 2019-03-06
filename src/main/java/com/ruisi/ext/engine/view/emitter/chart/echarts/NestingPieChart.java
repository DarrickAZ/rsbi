//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.echarts;

import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColorVO;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import com.ruisi.ext.engine.view.emitter.chart.highcharts.PieChart;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NestingPieChart extends PieChart {
    private List a = new ArrayList();

    public NestingPieChart() {
    }

    @Override
    public int createChartJS(boolean var1) {
        this.a.clear();
        this.initMargin();
        String var2 = this.chart.getYcol();
        String var3 = this.chart.getScol();
        String var4 = this.config.getFormatCol();
        String var5 = this.config.getLegendPosition();
        String var6 = this.chart.getWidth();
        String var7 = this.chart.getId();
        String var8 = this.a(this.dataList, var2, var3, var4);
        String var9 = ChartUtils.crtChartDivStyle(var6, this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + var7 + "\" style=\" " + var9 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("var myChart = echarts.init(document.getElementById('" + var7 + "'));");
        this.out.println("var option = {");
        String var10 = (String)((Map)this.dataList.get(0)).get(var4);
        if (var10 == null) {
            var10 = var4;
        }

        Object var11 = ((Map)this.dataList.get(0)).get(this.config.getUnitCol());
        String var12 = var11 == null ? null : var11.toString();
        if (var12 == null) {
            var12 = this.config.getUnitCol();
        }

        if ("%".equals(var12)) {
            var12 = "";
        }

        this.out.println(" tooltip: { formatter: function(params){ ");
        this.out.println("  return  params.name + ': '+  formatNumber(params.value, '" + (var10 == null ? "" : var10) + "')+'" + ChartUtils.writerUnit(this.chart.getRate()) + (var12 == null ? "" : var12) + "(' + params.percent+'%)';");
        this.out.println(" }},");
        if (this.config.getShowLegend()) {
            this.out.println(" legend: {");
            this.out.println("   data:[" + ChartUtils.list2string(this.a) + "],");
            this.out.print("textStyle:{color:'" + ChartUtils.getChartlegendColor(this.config.getStyle()) + "'},");
            if ("righttop".equals(var5)) {
                this.out.println("\tright:20,top:0");
            } else if ("centertop".equals(var5)) {
                this.out.println("\tleft:'center',top:0");
            } else if ("centerbottom".equals(var5)) {
                this.out.println("\tleft:'center',top:'bottom'");
            }

            if (this.config.getLegendLayout() != null && this.config.getLegendLayout().length() > 0) {
                this.out.println(",orient:'" + this.config.getLegendLayout() + "'");
            }

            this.out.println("  },");
        }

        this.out.println(" series: [" + var8 + "],");
        this.out.print(" color:[");
        AbstractChartEmitter$ColorVO[] var13 = AbstractChartEmitter$ColorVO.values();

        for(int var14 = 0; var14 < var13.length; ++var14) {
            String var15 = var13[var14].toString();
            this.out.print("'" + var15 + "'");
            if (var14 != var13.length - 1) {
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
        String var5 = "";
        String var6 = "";
        String var7 = "";
        LinkedHashMap var8 = new LinkedHashMap();

        Object var11;
        for(int var9 = 0; var9 < var1.size(); ++var9) {
            Map var10 = (Map)var1.get(var9);
            var11 = var10.get(this.chart.getXcol());
            Object var12 = var10.get(var2);
            Object var13 = var10.get(this.chart.getScol());
            if (var9 >= this.config.getXcnt_Num()) {
                break;
            }

            if (!var8.containsKey(var11)) {
                var8.put(var11, new ArrayList());
            } else {
                List var14 = (List)var8.get(var11);
                var14.add(var12);
            }

            var7 = var7 + "{ name: \"" + var13 + "\",value: " + var12 + "},";
            this.a.add(var13);
        }

        double var22;
        for(Iterator var20 = var8.entrySet().iterator(); var20.hasNext(); var6 = var6 + "{ name: \"" + var11 + "\",value: " + var22 + "},") {
            Entry var18 = (Entry)var20.next();
            var11 = var18.getKey();
            List var21 = (List)var18.getValue();
            var22 = 0.0D;

            for(int var15 = 0; var15 < var21.size(); ++var15) {
                Object var16 = var21.get(var15);
                Double var17 = ChartUtils.getKpiValue(var16);
                if (var17 != null) {
                    var22 += var17;
                }
            }
        }

        var6 = var6.substring(0, var6.length() - 1);
        var7 = var7.substring(0, var7.length() - 1);
        String var19 = "";
        if ("np".equals(this.config.getLabelType())) {
            var19 = "{b}: {d}%";
        } else if ("nv".equals(this.config.getLabelType())) {
            var19 = "{b}: {c}";
        } else if ("n".equals(this.config.getLabelType())) {
            var19 = "{b}";
        }

        var5 = var5 + "{\t\t\t\r\ntype: 'pie',\t\r\nname: '" + this.config.getXdesc() + "',\t\r\n" + "label:{normal:{position: 'inner',show:" + this.config.getShowLabel() + ",formatter: '" + var19 + "'}},";
        var5 = var5 + "radius: [0, '45%'], \r\n";
        var5 = var5 + "data: [\t\t\t\r\n" + var6 + "\t\r\n" + "]\t\t\t\t\t\r\n" + "},{\t\t\t\t\r\n" + "type: 'pie',\t\r\n" + "name: '" + this.config.getXdesc() + "',\t\r\n" + "label:{normal:{show:" + this.config.getShowLabel() + ",formatter: '" + var19 + "'}}," + "radius: ['60%', '80%'], \r\n" + "data:[" + var7 + "] " + "}";
        return var5;
    }
}
