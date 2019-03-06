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

public class AreaChart extends com.ruisi.ext.engine.view.emitter.chart.highcharts.AreaChart {
    private List a = new ArrayList();

    public AreaChart() {
    }

    private void a() {
        String var1 = this.config.getMargin();
        String var2 = this.config.getLegendPosition();
        String var3 = this.config.getMarginRight();
        String var4 = this.config.getMarginLeft();
        if (var1 == null || var1.equals("")) {
            if ("centerbottom".equals(var2)) {
                var1 = "42, " + (var3 != null ? var3 : "10") + ", 60, " + (var4 != null ? var4 : "65");
            } else {
                var1 = "42, " + (var3 != null ? var3 : "10") + ", 40, " + (var4 != null ? var4 : "65");
            }
        }

        this.config.setMargin(var1);
    }

    public int createChartJS(boolean var1) {
        this.a.clear();
        this.initConfg();
        this.a();
        String var2 = this.chart.getXcol();
        String var3 = this.chart.getYcol();
        String var4 = this.chart.getScol();
        String var5 = this.config.getUnitCol();
        String var6 = this.config.getFormatCol();
        String var7 = this.config.getYmin();
        String var8 = this.config.getLegendPosition();
        String var9 = this.config.getLegendLayout();
        this.AnalyseData(this.dataList, var2, var3, var4, var5, var6);
        if (this.chart.getWidth() != null && "auto".equalsIgnoreCase(this.chart.getWidth())) {
            int var10 = ChartUtils.autoWidth(this.xcolList);
            if (var10 == ChartUtils.chartMaxWidth) {
                this.config.setRouteXaxisLable("-30");
                if (this.xcolList.size() <= 31) {
                    this.config.setTickInterval("1");
                } else {
                    this.config.setTickInterval("2");
                }
            }

            this.chart.setWidth(String.valueOf(var10));
        }

        String var20 = "";
        if (this.dataInfoList != null && this.dataInfoList.size() != 0 && this.dataInfoList.size() != 1) {
            var20 = var20 + this.a(this.dataList, this.dataInfoList, var2, var3, var4);
        } else {
            var20 = var20 + this.a(this.dataList, var2, var3);
        }

        String var11 = ChartUtils.crtChartDivStyle(this.chart.getWidth(), this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + this.chart.getId() + "\" style=\" " + var11 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("var myChart = echarts.init(document.getElementById('" + this.chart.getId() + "'));");
        this.out.println("var option = {");
        this.out.println(" title: {show:false},");
        String[] var12 = this.config.getMargin().split(",");
        this.out.println(" grid: {show:true, borderWidth:0, top:" + var12[0] + ", right:" + var12[1] + ", bottom : " + var12[2] + ", left:" + var12[3] + " },");
        String var13 = super.getKpiForamtString();
        String var14 = super.getKpiUnitString();
        this.out.println(" tooltip: {trigger: 'axis'},");
        if (this.config.getShowLegend()) {
            this.out.println(" legend: {");
            this.out.println("   data:[" + ChartUtils.list2string(this.a) + "],");
            if (var9 != null && var9.length() > 0) {
                this.out.println("orient:'" + var9 + "',");
            }

            this.out.print("textStyle:{color:'" + ChartUtils.getChartlegendColor(this.config.getStyle()) + "'},");
            if ("righttop".equals(var8)) {
                this.out.println("\tright:10,top:0");
            } else if ("centertop".equals(var8)) {
                this.out.println("\tleft:'center',top:0");
            } else if ("centerbottom".equals(var8)) {
                this.out.println("\tleft:'center',top:'bottom'");
            }

            this.out.println("  },");
        }

        this.out.println(" xAxis: {");
        this.out.println("\t name:'" + this.config.getXdesc() + "',nameLocation:'middle',nameGap:25,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'},");
        this.out.println("\taxisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }},");
        this.out.println("\t axisTick:{interval:0},");
        this.out.println("   boundaryGap : false,");
        this.out.println("\t axisLabel:{");
        this.out.println("show:true");
        String var15 = this.config.getRouteXaxisLable();
        if (var15 != null && var15.length() > 0) {
            this.out.println(",rotate:" + Math.abs(Integer.parseInt(var15)));
        }

        String var16 = this.config.getTickInterval();
        if (var16 != null && var16.length() > 0 && !"none".equals(var16)) {
            this.out.println(",interval:" + var16);
        }

        this.out.println("},");
        this.out.println("    data: [" + this.setXcolDesc(this.xcolList) + "]");
        this.out.println(" },");
        this.out.println(" yAxis: {");
        this.out.println("name:'" + this.config.getYdesc() + "',nameGap:8,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'},axisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }},axisLabel:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "',formatter:function(value){ return formatNumber(value,'" + var13 + "', true); }}");
        if (var7 != null && var7.length() > 0) {
            this.out.println(" ,min:" + var7);
        } else {
            this.out.println(" ,min:'dataMin'");
        }

        this.out.println("},");
        this.out.println(" series: [" + var20 + "],");
        this.out.print(" color:[");
        AbstractChartEmitter$ColorVO[] var17 = AbstractChartEmitter$ColorVO.values();

        for(int var18 = 0; var18 < var17.length; ++var18) {
            String var19 = var17[var18].toString();
            this.out.print("'" + var19 + "'");
            if (var18 != var17.length - 1) {
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

    private String a(List var1, List var2, String var3, String var4, String var5) {
        String var6 = "";

        for(int var7 = 0; var7 < var2.size(); ++var7) {
            Map var8 = (Map)var2.get(var7);
            Object var9 = var8.get("scolValue");
            this.a.add(var9);
            String var10 = "";
            if (var7 >= LineChart.maxsercnt) {
                var6 = var6.substring(0, var6.lastIndexOf(","));
                break;
            }

            int var11 = 0;

            int var12;
            for(var12 = 0; var12 < this.xcolList.size(); ++var12) {
                Object var13 = this.xcolList.get(var12);
                Object var14 = ChartUtils.findRow(var3, var4, var5, var13, var9, var1);
                var10 = var10 + var14 + ",";
                ++var11;
                if (var11 >= this.config.getXcnt_Num()) {
                    break;
                }
            }

            var10 = var10.substring(0, var10.length() - 1);
            var6 = var6 + "{    \t\t\t\t\t\t   \tname: '" + var9 + "',   areaStyle: {normal: {}},   ";
            var6 = var6 + "type:'line', smooth:" + (this.config.getSpline() ? "true" : "false") + ",";
            if (this.config.getMarkerEnabled()) {
                var6 = var6 + "showAllSymbol:true,symbolSize:6,";
            } else {
                var6 = var6 + "symbol:false,symbolSize:1,";
            }

            var12 = this.chartColorIndex;
            if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var9) != null) {
                var12 = (Integer)this.chart.getSeriesColor().get(var9);
            }

            String var16 = AbstractChartEmitter$ColorVO.valueOf("c" + var12).toString();
            var6 = var6 + "itemStyle:{normal:{color:'" + var16 + "'}},";
            this.chartColorIndex = this.chartColorIndex + 1;
            if (this.config.getShowLabel()) {
                String var17 = super.getKpiForamtString();
                String var15 = super.getKpiUnitString();
                var6 = var6 + " label : { normal: {show: true,position:\"top\",formatter:function(p){return formatNumber(p.value,'" + var17 + "') + '" + var15 + "'}}},";
            }

            var6 = var6 + "\t\tdata: [" + var10 + "] \t  " + "\t}   \t\t\t\t\t\t\t  ";
            if (var7 != var2.size() - 1) {
                var6 = var6 + ",";
            }
        }

        return var6;
    }

    private String a(List var1, String var2, String var3) {
        String var4 = "";
        this.a.add(ChartUtils.replaeUnit(this.config.getYdesc()));
        String var5 = "";
        int var6 = 0;

        for(int var7 = 0; var7 < var1.size(); ++var7) {
            Map var8 = (Map)var1.get(var7);
            Object var9 = null;
            var9 = var8.get(var3);
            var5 = var5 + var9 + ",";
            ++var6;
            if (var6 >= this.config.getXcnt_Num()) {
                break;
            }
        }

        var5 = var5.substring(0, var5.length() - 1);
        String var12 = ChartUtils.replaeUnit(this.config.getYdesc());
        var4 = var4 + "{    \t\t\t\t\t\t   \tname: '" + var12 + "',  areaStyle: {normal: {}},    ";
        var4 = var4 + "type:'line', smooth:" + (this.config.getSpline() ? "true" : "false") + ",";
        if (this.config.getMarkerEnabled()) {
            var4 = var4 + "showAllSymbol:true,symbolSize:6,";
        } else {
            var4 = var4 + "symbol:false,symbolSize:1,";
        }

        int var13 = this.chartColorIndex;
        if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var12) != null) {
            var13 = (Integer)this.chart.getSeriesColor().get(var12);
        }

        String var14 = AbstractChartEmitter$ColorVO.valueOf("c" + var13).toString();
        var4 = var4 + "itemStyle:{normal:{color:'" + var14 + "'}},";
        this.chartColorIndex = this.chartColorIndex + 1;
        if (this.config.getShowLabel()) {
            String var10 = super.getKpiForamtString();
            String var11 = super.getKpiUnitString();
            var4 = var4 + " label : { normal: {show: true,position:\"top\",formatter:function(p){return formatNumber(p.value,'" + var10 + "') + '" + var11 + "'}}},";
        }

        var4 = var4 + "\t\tdata: [" + var5 + "] \t  " + "\t} ";
        return var4;
    }
}
