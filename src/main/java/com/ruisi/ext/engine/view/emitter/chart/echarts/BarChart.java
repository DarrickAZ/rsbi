//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.echarts;

import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColorVO;
import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColumnColorVO;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import com.ruisi.ext.engine.view.emitter.chart.highcharts.LineChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BarChart extends com.ruisi.ext.engine.view.emitter.chart.highcharts.BarChart {
    private List a = new ArrayList();

    public BarChart() {
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

    @Override
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
        String var10 = this.chart.getWidth();
        this.AnalyseData(this.dataList, var2, var3, var4, var5, var6);
        if (var10 != null && "auto".equalsIgnoreCase(var10)) {
            int var11 = ChartUtils.autoWidth(this.xcolList);
            if (var11 == ChartUtils.chartMaxWidth) {
                int var12 = Integer.parseInt(this.chart.getHeight());
                var12 = (int)((double)var12 * 1.6D);
                this.chart.setHeight(String.valueOf(var12));
            }

            var10 = String.valueOf(var11);
            this.chart.setWidth(var10);
        }

        String var19 = "";
        if (this.dataInfoList != null && this.dataInfoList.size() != 0 && this.dataInfoList.size() != 1) {
            var19 = var19 + this.a(this.dataList, this.dataInfoList, var2, var3, var4);
        } else {
            var19 = var19 + this.a(this.dataList, var2, var3);
        }

        String var20 = ChartUtils.crtChartDivStyle(var10, this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + this.chart.getId() + "\" style=\" " + var20 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("var myChart = echarts.init(document.getElementById('" + this.chart.getId() + "'));");
        this.out.println("var option = {");
        this.out.println(" title: {show:false},");
        String[] var13 = this.config.getMargin().split(",");
        this.out.println(" grid: {show:true, top:" + var13[0] + ", right:" + var13[1] + ", bottom : " + var13[2] + ", left:" + var13[3] + " },");
        String var14 = super.getKpiForamtString();
        String var15 = super.getKpiUnitString();
        this.out.println(" tooltip: { trigger: 'axis'},");
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

        this.out.println(" yAxis: {");
        this.out.println("\t type:'category',");
        this.out.println("\t name:'" + this.config.getXdesc() + "',");
        this.out.println("\t nameGap:8,");
        this.out.println("\t axisTick:{interval:0},nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'},");
        this.out.println("\taxisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }},");
        this.out.println("\t axisLabel:{");
        this.out.println("show:true,color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'");
        if (this.config.getTickInterval() != null && this.config.getTickInterval().length() > 0 && !"none".equals(this.config.getTickInterval())) {
            this.out.println(",interval:" + this.config.getTickInterval());
        }

        this.out.println("},");
        this.out.println("    data: [" + this.setXcolDesc(this.xcolList) + "]");
        this.out.println(" },");
        this.out.println(" xAxis: {name:'" + this.config.getXdesc() + "',type:'value',nameLocation:'middle',nameGap:25,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'},axisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }},axisLabel:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "',formatter:function(value){ return formatNumber(value,'" + var14 + "', true); }}},");
        this.out.println(" series: [" + var19 + "],");
        this.out.print(" color:[");
        AbstractChartEmitter$ColorVO[] var16 = AbstractChartEmitter$ColorVO.values();

        for(int var17 = 0; var17 < var16.length; ++var17) {
            String var18 = var16[var17].toString();
            this.out.print("'" + var18 + "'");
            if (var17 != var16.length - 1) {
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
            var6 = var6 + "{    \t\t\t\t\t\t   \tname: '" + var9 + "',      ";
            var6 = var6 + "type:'bar',barMaxWidth:40,barMinHeight:10,";
            var12 = this.chartColorIndex;
            if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var9) != null) {
                var12 = (Integer)this.chart.getSeriesColor().get(var9);
            }

            String var18 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var12 + "1").toString();
            String var19 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var12 + "2").toString();
            String var15 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var12 + "3").toString();
            this.chartColorIndex = this.chartColorIndex + 1;
            var6 = var6 + "itemStyle:{normal:{color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset: 0, color: '" + var18 + "'},{offset: 0.5, color: '" + var19 + "'},{offset: 1, color: '" + var15 + "'}])}},";
            if (this.config.getShowLabel()) {
                String var16 = super.getKpiForamtString();
                String var17 = super.getKpiUnitString();
                var6 = var6 + " label : { normal: {show: true,position:\"insideRight\",formatter:function(p){return formatNumber(p.value,'" + var16 + "') + '" + var17 + "'}}},";
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
        String var14 = ChartUtils.replaeUnit(this.config.getYdesc());
        var4 = var4 + "{    \t\t\t\t\t\t   \tname: '" + var14 + "',      ";
        int var15 = this.chartColorIndex;
        if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var14) != null) {
            var15 = (Integer)this.chart.getSeriesColor().get(var14);
        }

        var4 = var4 + "type:'bar',barMaxWidth:40,barMinHeight:10,";
        String var16 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var15 + "1").toString();
        String var10 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var15 + "2").toString();
        String var11 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var15 + "3").toString();
        this.chartColorIndex = this.chartColorIndex + 1;
        var4 = var4 + "itemStyle:{normal:{color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset: 0, color: '" + var16 + "'},{offset: 0.5, color: '" + var10 + "'},{offset: 1, color: '" + var11 + "'}])}},";
        if (this.config.getShowLabel()) {
            String var12 = super.getKpiForamtString();
            String var13 = super.getKpiUnitString();
            var4 = var4 + " label : { normal: {show: true,position:\"insideRight\",formatter:function(p){return formatNumber(p.value,'" + var12 + "') + '" + var13 + "'}}},";
        }

        var4 = var4 + "\t\tdata: [" + var5 + "] \t  " + "\t} ";
        return var4;
    }
}
