//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.echarts;

import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColorVO;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import com.ruisi.ext.engine.view.emitter.chart.highcharts.LineChart;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScatterChart extends com.ruisi.ext.engine.view.emitter.chart.highcharts.ScatterChart {
    private List a = new ArrayList();

    public ScatterChart() {
    }

    @Override
    protected void initMargin() {
        this.a.clear();
        String var1 = this.config.getMarginRight();
        String var2 = this.config.getMarginLeft();
        String var3 = this.config.getMargin();
        String var4 = this.config.getLegendPosition();
        if (var3 == null || var3.equals("")) {
            if ("centerbottom".equals(var4)) {
                var3 = "42, " + (var1 != null ? var1 : "10") + ", 60, " + (var2 != null ? var2 : "65");
            } else {
                var3 = "42, " + (var1 != null ? var1 : "10") + ", 40, " + (var2 != null ? var2 : "65");
            }
        }

        this.config.setMargin(var3);
    }

    @Override
    public int createChartJS(boolean var1) {
        this.initConfig();
        this.initMargin();
        String var2 = this.chart.getXcol();
        String var3 = this.chart.getY2col();
        String var4 = this.chart.getYcol();
        String var5 = this.chart.getScol();
        String var6 = this.config.getUnitCol();
        String var7 = this.config.getFormatCol();
        String var8 = this.config.getYmin();
        this.AnalyseData(this.dataList, var2, var4, var5, var6, var7);
        String var9 = "";
        if (this.dataInfoList != null && this.dataInfoList.size() != 0) {
            var9 = this.a(this.dataList, this.dataInfoList, var2, var4, var3, var5);
        } else {
            var9 = this.a(this.dataList, var2, var4, var3);
        }

        if (this.chart.getWidth() != null && "auto".equalsIgnoreCase(this.chart.getWidth())) {
            int var10 = ChartUtils.autoWidth(this.xcolList);
            this.chart.setWidth(String.valueOf(var10));
        }

        String var17 = ChartUtils.crtChartDivStyle(this.chart.getWidth(), this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + this.chart.getId() + "\" style=\" " + var17 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("var myChart = echarts.init(document.getElementById('" + this.chart.getId() + "'));");
        this.out.println("var option = {");
        this.out.println(" title: {show:false},");
        String[] var11 = this.config.getMargin().split(",");
        this.out.println(" grid: {show:true, top:" + var11[0] + ", right:" + var11[1] + ", bottom : " + var11[2] + ", left:" + var11[3] + ", borderWidth:0 },");
        String var12 = super.getKpiForamtString();
        String var13 = super.getKpi2ForamtString();
        this.out.println(" tooltip: { formatter: function(params){ ");
        this.out.println("return " + (this.dataInfoList != null && this.dataInfoList.size() != 0 ? " params.seriesName + \"： \" +  " : "") + " params.value[2] + '<br/>" + this.config.getXdesc() + " ：' + formatNumber(params.data[0], '" + var13 + "')+'" + ChartUtils.writerUnit(this.chart.getRate2()) + "<br/>" + this.config.getXdesc() + "：' + formatNumber(params.data[1], '" + var12 + "')+'" + ChartUtils.writerUnit(this.chart.getRate()) + "<br/>'" + ";");
        this.out.println(" }},");
        if (this.config.getShowLegend()) {
            this.out.println(" legend: {");
            this.out.println("   data:[" + ChartUtils.list2string(this.a) + "],");
            this.out.print("textStyle:{color:'" + ChartUtils.getChartlegendColor(this.config.getStyle()) + "'},");
            if (this.config.getLegendLayout() != null && this.config.getLegendLayout().length() > 0) {
                this.out.println("orient:'" + this.config.getLegendLayout() + "',");
            }

            if ("righttop".equals(this.config.getLegendPosition())) {
                this.out.println("\tright:20,top:0");
            } else if ("centertop".equals(this.config.getLegendPosition())) {
                this.out.println("\tleft:'center',top:0");
            } else if ("centerbottom".equals(this.config.getLegendPosition())) {
                this.out.println("\tleft:'center',top:'bottom'");
            }

            this.out.println("  },");
        }

        this.out.println(" xAxis: {");
        this.out.println("\t splitLine:{lineStyle:{type:'dashed'}},");
        this.out.println("\tname:'" + this.config.getXdesc() + "',");
        this.out.println("\tnameLocation:'middle',");
        this.out.println("\taxisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }},");
        this.out.println("\tposition:'bottom',nameGap:25,scale:true,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'}");
        this.out.println(" },");
        this.out.println(" yAxis: {");
        this.out.println("\t splitLine:{lineStyle:{type:'dashed'}},scale:true,");
        this.out.println("\taxisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }},");
        this.out.println("\tname:'" + this.config.getYdesc() + "',nameGap:5,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'}");
        this.out.println("},");
        this.out.println(" series: [");
        this.out.println(var9);
        this.out.println("],");
        this.out.print(" color:[");
        AbstractChartEmitter$ColorVO[] var14 = AbstractChartEmitter$ColorVO.values();

        for(int var15 = 0; var15 < var14.length; ++var15) {
            String var16 = var14[var15].toString();
            this.out.print("'" + var16 + "'");
            if (var15 != var14.length - 1) {
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
        this.a.add(ChartUtils.replaeUnit(this.config.getYdesc()));
        StringBuffer var6 = new StringBuffer();
        int var7 = 0;

        for(int var8 = 0; var8 < var1.size(); ++var8) {
            Map var9 = (Map)var1.get(var8);
            Object var10 = var9.get(var3);
            Object var11 = var9.get(var4);
            Object var12 = var9.get(this.chart.getXcol());
            var6.append("[" + var11 + "," + var10 + ",\"" + (var12 == null ? "" : var12) + "\"],");
            ++var7;
            if (var7 >= this.config.getXcnt_Num()) {
                break;
            }
        }

        if (var6.lastIndexOf(",") >= 0) {
            var6.deleteCharAt(var6.lastIndexOf(","));
        }

        String var13 = this.config.getXdesc();
        var5 = var5 + "{    \tname:'" + var13 + "',\t\t\t\t\t \r\n ";
        var5 = var5 + "type:'scatter',";
        int var14 = this.chartColorIndex;
        if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var13) != null) {
            var14 = (Integer)this.chart.getSeriesColor().get(var13);
        }

        String var15 = AbstractChartEmitter$ColorVO.valueOf("c" + var14).toString();
        var5 = var5 + "itemStyle:{normal:{color:'" + var15 + "'}},";
        this.chartColorIndex = this.chartColorIndex + 1;
        if (this.config.getShowLabel() != null && this.config.getShowLabel()) {
            var5 = var5 + "label:{normal:{show:true, position:\"top\",formatter:function(params){ return params.value[2] }}},";
        }

        var5 = var5 + "\t data: [" + var6 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t     \r\n ";
        return var5;
    }

    private String a(List var1, List var2, String var3, String var4, String var5, String var6) {
        String var7 = "";

        for(int var8 = 0; var8 < var2.size(); ++var8) {
            Map var9 = (Map)var2.get(var8);
            String var10 = (String)var9.get("scolValue");
            this.a.add(var10);
            if (var8 >= LineChart.maxsercnt) {
                var7 = var7.substring(0, var7.lastIndexOf(","));
                break;
            }

            StringBuffer var11 = new StringBuffer();
            int var12 = 0;

            int var13;
            for(var13 = 0; var13 < var1.size(); ++var13) {
                Map var14 = (Map)var1.get(var13);
                String var15 = var14.get(var6).toString();
                if (var10.equals(var15)) {
                    Object var16 = var14.get(var4);
                    Object var17 = var14.get(var5);
                    Object var18 = var14.get(this.chart.getXcol());
                    var11.append("[" + var17 + "," + var16 + ",\"" + var18 + "\"],");
                    ++var12;
                    if (var12 == this.config.getXcnt_Num()) {
                        break;
                    }
                }
            }

            if (var11.lastIndexOf(",") >= 0) {
                var11.deleteCharAt(var11.lastIndexOf(","));
            }

            var7 = var7 + "{    \t\t\t\t\t\t \r\n  \tname: '" + var10 + "',     \r\n ";
            var7 = var7 + "type:'scatter',";
            var13 = this.chartColorIndex;
            if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var10) != null) {
                var13 = (Integer)this.chart.getSeriesColor().get(var10);
            }

            String var19 = AbstractChartEmitter$ColorVO.valueOf("c" + var13).toString();
            var7 = var7 + "itemStyle:{normal:{color:'" + var19 + "'}},";
            this.chartColorIndex = this.chartColorIndex + 1;
            if (this.config.getShowLabel() != null && this.config.getShowLabel()) {
                var7 = var7 + "label:{normal:{show:true, position:\"top\",formatter:function(params){ return params.value[2] }}},";
            }

            var7 = var7 + "\t data: [" + var11 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t     \r\n ";
            if (var8 != var2.size() - 1) {
                var7 = var7 + ",";
            }
        }

        return var7;
    }
}
