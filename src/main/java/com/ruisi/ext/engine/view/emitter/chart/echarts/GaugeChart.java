//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.echarts;

import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColorVO;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import java.util.Map;

public class GaugeChart extends com.ruisi.ext.engine.view.emitter.chart.highcharts.GaugeChart {
    public GaugeChart() {
    }

    @Override
    public int createChartJS(boolean var1) {
        this.initMargin();
        String var2 = this.config.getFormatCol();
        String var3 = this.config.getYmin();
        double var4 = 1.7976931348623157E308D;
        double var6 = 0.0D;

        int var8;
        for(var8 = 0; var8 < this.dataList.size(); ++var8) {
            Map var9 = (Map)this.dataList.get(var8);
            double var10 = ChartUtils.getKpiValue(var9, this.chart.getYcol());
            if (var10 < var4) {
                var4 = var10;
            }

            if (var10 > var6) {
                var6 = var10;
            }
        }

        this.out.println("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        this.out.println("<tr>");
        var8 = this.config.getGaugeCnt_Num();

        for(int var19 = 0; var19 < this.dataList.size() && var19 < var8; ++var19) {
            this.out.println("<td>");
            String var20 = ChartUtils.crtChartDivStyle(this.chart.getWidth(), this.chart.getHeight(), this.chart.getAlign());
            this.out.println(" <div id=\"" + this.chart.getId() + (var8 == 1 ? "" : var19) + "\" style=\" " + var20 + " \"></div>");
            this.out.println(" <script type=\"text/javascript\">");
            this.out.println("$(function(){");
            this.out.println("var myChart = echarts.init(document.getElementById('" + this.chart.getId() + (var8 == 1 ? "" : var19) + "'));");
            this.out.println("var option = {");
            String var11 = (String)((Map)this.dataList.get(0)).get(var2);
            if (var11 == null) {
                var11 = var2;
            }

            Object var12 = ((Map)this.dataList.get(0)).get(this.config.getUnitCol());
            String var13 = var12 == null ? null : var12.toString();
            if (var13 == null) {
                var13 = this.config.getUnitCol();
            }

            if ("%".equals(var13)) {
                var13 = "";
            }

            this.out.println(" tooltip: { formatter: function(params){ ");
            this.out.println("  return  params.name + ': '+  formatNumber(params.value, '" + (var11 == null ? "" : var11) + "')+'" + ChartUtils.writerUnit(this.chart.getRate()) + (var13 == null ? "" : var13) + "';");
            this.out.println(" }},");
            Object var14;
            if (this.dataList.size() <= 1) {
                var14 = this.config.getYdesc();
            } else {
                var14 = ((Map)this.dataList.get(var19)).get(this.chart.getXcol());
            }

            double var15 = ChartUtils.getKpiValue((Map)this.dataList.get(var19), this.chart.getYcol());
            int var17 = this.chartColorIndex;
            if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var14) != null) {
                var17 = (Integer)this.chart.getSeriesColor().get(var14);
            }

            String var18 = AbstractChartEmitter$ColorVO.valueOf("c" + var17).toString();
            this.chartColorIndex = this.chartColorIndex + 1;
            this.out.println(" series: [{ name: '" + var14 + "'," + "type: 'gauge'," + "splitNumber: 5," + "radius:'95%'," + "axisLabel:{show:true,formatter: function(val){ " + "\treturn formatNumber(val, '" + var11 + "', true)" + "}}," + "axisLine:{lineStyle:{width:15}}," + "title:{textStyle:{fontSize:14}}," + "detail:{textStyle:{fontSize:13},formatter:function(val){" + "\treturn formatNumber(val, '" + (var11 == null ? "" : var11) + "') +  '" + ChartUtils.writerUnit(this.chart.getRate()) + (var13 == null ? "" : var13) + "';" + "}}," + "splitLine:{length:15}," + "itemStyle:{normal:{color:'" + var18 + "'}}," + "min:" + (this.config.getYmin() != null && var3.length() > 0 ? var3 : var4 / 1.1D) + ",max:" + (this.config.getYmax() != null && this.config.getYmax().length() > 0 ? this.config.getYmax() : var6 * 1.1D) + "," + "data: [{value: " + var15 + ", name: '" + var14 + "'}]" + "}]");
            this.out.println("};");
            this.out.println("myChart.setOption(option);");
            this.config.setXcnt("4");
            ChartUtils.echartsClick(this.out, this.chart, this.request, this.config);
            this.out.println("});");
            this.out.println(" </script>");
            this.out.println("</td>");
        }

        this.out.println("</tr>");
        this.out.println("</table>");
        return 6;
    }
}
