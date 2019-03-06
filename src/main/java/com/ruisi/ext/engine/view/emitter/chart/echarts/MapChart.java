//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.echarts;

import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import java.util.List;
import java.util.Map;

public class MapChart extends com.ruisi.ext.engine.view.emitter.chart.highcharts.MapChart {
    public MapChart() {
    }

    public int createChartJS(boolean var1) {
        this.initMargin();
        String var2 = this.chart.getYcol();
        String var3 = this.chart.getScol();
        String var4 = this.config.getFormatCol();
        String var5 = this.config.getYmin();
        String var6 = this.config.getYmax();
        String var7 = this.config.getLegendLayout();
        String var8 = this.chart.getWidth();
        String var9 = this.chart.getId();
        String var10 = this.config.getMapJson();
        double[] var11 = ChartUtils.findMaxMin(this.dataList, var2);
        double var12 = var11[0];
        double var14 = var11[1];
        String var16 = ChartUtils.crtChartDivStyle(var8, this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + var9 + "\" style=\" " + var16 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("$.get('../ext-res/js/chart/" + var10 + ".json', function (json) {");
        this.out.println("echarts.registerMap('" + var10 + "', json);");
        this.out.println("var myChart = echarts.init(document.getElementById('" + var9 + "'));");
        this.out.println("var option = {");
        this.out.println("tooltip: {");
        this.out.println("},");
        this.out.println(" visualMap: {");
        this.out.println(" type:'continuous',");
        this.out.println(" min: " + var12 * 0.9D + ",");
        this.out.println(" max: " + var14 * 1.1D + ",");
        this.out.println(" left: 'left',");
        this.out.println(" top: 'bottom',");
        if ("bigscreen".equals(this.config.getStyle())) {
            this.out.println(" color: ['#0050B3','#56B1F5','#99D0F9'],");
        } else {
            this.out.println(" color: ['orangered','yellow','lightskyblue'],");
        }

        this.out.print("textStyle:{color:'" + ChartUtils.getChartlegendColor(this.config.getStyle()) + "'},");
        this.out.println("  text: ['高','低'],   ");
        if (this.config.getShowLegend()) {
            this.out.println("\tshow:true,");
        } else {
            this.out.println("\tshow:false,");
        }

        this.out.println("  calculable: true");
        this.out.println(" },");
        this.out.println("series: [");
        this.out.println(" {");
        this.out.println("  name: '" + var10 + "',");
        this.out.println("  type: 'map',");
        this.out.println("  roam:true, ");
        this.out.println("  mapType: '" + var10 + "',");
        this.out.println("  selectedMode : 'single',");
        this.out.println("  label: {");
        this.out.println("\t\t normal: { show: " + this.config.getShowLabel() + " },");
        this.out.println("\temphasis: { show: true }");
        this.out.println(" },");
        this.out.println(" data:" + this.a(this.dataList, var2, var3));
        this.out.println("  }");
        this.out.println("]");
        this.out.println("};");
        this.out.println("myChart.setOption(option);");
        this.config.setXcnt("-1");
        ChartUtils.echartsClick(this.out, this.chart, this.request, this.config);
        this.out.println("});");
        this.out.println("});");
        this.out.println(" </script>");
        return 6;
    }

    private String a(List var1, String var2, String var3) {
        StringBuffer var4 = new StringBuffer("[ ");

        for(int var5 = 0; var5 < var1.size(); ++var5) {
            Map var6 = (Map)var1.get(var5);
            Object var7 = var6.get(this.chart.getXcol());
            double var8 = ChartUtils.getKpiValue(var6, var2);
            var4.append("{ \"name\": \"" + var7 + "\", \"value\": " + var8 + "}");
            if (var5 != var1.size() - 1) {
                var4.append(",");
            }
        }

        var4.append("]");
        return var4.toString();
    }
}
