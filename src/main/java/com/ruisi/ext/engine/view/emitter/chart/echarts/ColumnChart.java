//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.echarts;

import com.ruisi.ext.engine.view.context.dc.grid.AggreVO;
import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColorVO;
import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColumnColorVO;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import com.ruisi.ext.engine.view.emitter.chart.highcharts.LineChart;
import com.ruisi.ispire.dc.grid.DataAggregation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ColumnChart extends com.ruisi.ext.engine.view.emitter.chart.highcharts.ColumnChart {
    private List a = new ArrayList();

    public ColumnChart() {
    }

    private void a() {
        String var1 = this.config.getMargin();
        String var2 = this.chart.getY2col();
        String var3 = this.config.getLegendPosition();
        String var4 = this.config.getMarginRight();
        String var5 = this.config.getMarginLeft();
        if (var1 == null || var1.equals("")) {
            if (var2 != null && var2.length() > 0) {
                if ("centerbottom".equals(var3)) {
                    var1 = "42, " + (var4 != null ? var4 : "70") + ", 60, " + (var5 != null ? var5 : "65");
                } else {
                    var1 = "42, " + (var4 != null ? var4 : "70") + ", 40, " + (var5 != null ? var5 : "65");
                }
            } else if ("centerbottom".equals(var3)) {
                var1 = "42, " + (var4 != null ? var4 : "10") + ", 60, " + (var5 != null ? var5 : "65");
            } else {
                var1 = "42, " + (var4 != null ? var4 : "10") + ", 40, " + (var5 != null ? var5 : "65");
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
        this.AnalyseData(this.dataList, var2, var3, var5, var6, var7);
        if (var11 != null && "auto".equalsIgnoreCase(var11)) {
            int var15 = ChartUtils.autoWidth(this.xcolList);
            if (var15 == ChartUtils.chartMaxWidth) {
                this.config.setRouteXaxisLable("-30");
                if (this.xcolList.size() <= 31) {
                    this.config.setTickInterval("1");
                } else {
                    this.config.setTickInterval("2");
                }
            }

            var11 = String.valueOf(var15);
            this.chart.setWidth(var11);
        }

        String var21 = "";
        if (this.dataInfoList != null && this.dataInfoList.size() != 0 && this.dataInfoList.size() != 1) {
            var21 = var21 + this.a(this.dataList, this.dataInfoList, var2, var3, var5);
            if (var4 != null && var4.length() > 0) {
                if (this.chart.getMergeData() != null && this.chart.getMergeData()) {
                    DataAggregation var16 = new DataAggregation(new String[]{var2}, new AggreVO[]{new AggreVO(this.chart.getY2Aggre(), var4)}, this.dataList);
                    var21 = var21 + "," + this.a(var16.process(), var2, var4);
                } else {
                    var21 = var21 + "," + this.a(this.dataList, this.dataInfoList, var2, var4, var5);
                }
            }
        } else {
            var21 = var21 + this.a(this.dataList, var2, var3);
            if (var4 != null && var4.length() > 0) {
                var21 = var21 + "," + this.a(this.dataList, var2, var4);
            }
        }

        String var22 = ChartUtils.crtChartDivStyle(var11, this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + var12 + "\" style=\" " + var22 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("var myChart = echarts.init(document.getElementById('" + var12 + "'));");
        this.out.println("var option = {");
        this.out.println(" title: {show:false},");
        String[] var17 = this.config.getMargin().split(",");
        this.out.println(" grid: {show:true, borderWidth:0,top:" + var17[0] + ", right:" + var17[1] + ", bottom : " + var17[2] + ", left:" + var17[3] + " },");
        this.out.println(" tooltip: { trigger: 'axis'},");
        if (this.config.getShowLegend()) {
            this.out.println(" legend: {");
            this.out.println("   data:[" + ChartUtils.list2string(this.a) + "],");
            if (var10 != null && var10.length() > 0) {
                this.out.println("orient:'" + var10 + "',");
            }

            this.out.print("textStyle:{color:'" + ChartUtils.getChartlegendColor(this.config.getStyle()) + "'},");
            if ("righttop".equals(var9)) {
                this.out.println("\tright:20,top:0");
            } else if ("centertop".equals(var9)) {
                this.out.println("\tleft:'center',top:0");
            } else if ("centerbottom".equals(var9)) {
                this.out.println("\tleft:'center',top:'bottom'");
            }

            this.out.println("  },");
        }

        this.out.println(" xAxis: {");
        this.out.println("\t name:'" + var13 + "',nameLocation:'middle',nameGap:26,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'},");
        this.out.println("\taxisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }},");
        this.out.println("\t axisTick:{interval:0},");
        this.out.println("\t axisLabel:{");
        this.out.println("show:true,color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'");
        if (this.config.getRouteXaxisLable() != null && this.config.getRouteXaxisLable().length() > 0) {
            this.out.println(",rotate:" + Math.abs(Integer.parseInt(this.config.getRouteXaxisLable())));
        }

        if (this.config.getTickInterval() != null && this.config.getTickInterval().length() > 0 && !"none".equals(this.config.getTickInterval())) {
            this.out.println(",interval:" + this.config.getTickInterval());
        }

        this.out.println("},");
        this.out.println("    data: [" + this.setXcolDesc(this.xcolList) + "]");
        this.out.println(" },");
        this.out.println(" yAxis: [{name:'" + var14 + "',nameGap:8,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'},axisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }},axisLabel:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "',formatter:function(value){ return formatNumber(value,'" + super.getKpiForamtString() + "', true); }}");
        this.out.print(",splitLine:{show: true, lineStyle:{color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "'}}");
        if (var8 != null && var8.length() > 0) {
            this.out.println(" ,min:" + var8);
        }

        this.out.println("}");
        if (var4 != null && var4.length() > 0) {
            this.out.println(",{");
            this.out.println("splitLine:{show: false},");
            this.out.println("name:'" + this.config.getY2desc() + "',nameGap:8,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'},position:'right',axisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }}," + "axisLabel:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "',formatter:function(value){ return formatNumber(value,'" + super.getKpi2ForamtString() + "', true); }}");
            this.out.println(",min:'dataMin'");
            this.out.println("}");
        }

        this.out.println("],");
        this.out.println(" series: [" + var21 + "],");
        this.out.print(" color:[");
        AbstractChartEmitter$ColorVO[] var18 = AbstractChartEmitter$ColorVO.values();

        for(int var19 = 0; var19 < var18.length; ++var19) {
            String var20 = var18[var19].toString();
            this.out.print("'" + var20 + "'");
            if (var19 != var18.length - 1) {
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
            String var10 = "";
            if (var7 >= LineChart.maxsercnt) {
                var6 = var6.substring(0, var6.lastIndexOf(","));
                break;
            }

            int var11 = 0;

            for(int var12 = 0; var12 < this.xcolList.size(); ++var12) {
                Object var13 = this.xcolList.get(var12);
                Object var14 = ChartUtils.findRow(var3, var4, var5, var13, var9, var1);
                var10 = var10 + var14 + ",";
                ++var11;
                if (var11 >= this.config.getXcnt_Num()) {
                    break;
                }
            }

            String var20 = this.chart.getY2col();
            var10 = var10.substring(0, var10.length() - 1);
            String var21 = var9 == null ? "" : var9.toString();
            if (var20 != null && var20.length() > 0 && var20.equals(var4)) {
                var21 = var21 + "-" + (var4.equals(var4) ? this.config.getY2desc() : this.config.getY2desc());
            }

            this.a.add(var21);
            var6 = var6 + "{    \t\t\t\t\t\t   \tname: '" + var21 + "',      ";
            var6 = var6 + "barMaxWidth:40,barMinHeight:5,";
            if (var20 != null && var20.length() > 0 && var4.equals(var20)) {
                var6 = var6 + "type:'line',";
                var6 = var6 + "yAxisIndex: 1,";
                var6 = var6 + "showAllSymbol:true,";
            } else {
                var6 = var6 + "type:'bar',";
                if (this.config.getStack() != null && this.config.getStack()) {
                    var6 = var6 + "stack:'stack',";
                }
            }

            int var22 = this.chartColorIndex;
            if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var21) != null) {
                var22 = (Integer)this.chart.getSeriesColor().get(var21);
            }

            String var15 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var22 + "1").toString();
            String var16 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var22 + "2").toString();
            String var17 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var22 + "3").toString();
            this.chartColorIndex = this.chartColorIndex + 1;
            var6 = var6 + "itemStyle:{normal:{color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset: 0, color: '" + var15 + "'},{offset: 0.5, color: '" + var16 + "'},{offset: 1, color: '" + var17 + "'}])}},";
            if (this.config.getShowLabel()) {
                String var18 = super.getKpiForamtString();
                String var19 = super.getKpiUnitString();
                var6 = var6 + " label : { normal: {show: true,position:\"top\",formatter:function(p){return formatNumber(p.value,'" + var18 + "') + '" + var19 + "'}}},";
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
        if (var3.equals(this.chart.getYcol())) {
            this.a.add(ChartUtils.replaeUnit(this.config.getYdesc()));
        } else if (var3.equals(this.chart.getY2col())) {
            this.a.add(ChartUtils.replaeUnit(this.config.getY2desc()));
        }

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
        String var14 = var3.equals(this.chart.getYcol()) ? ChartUtils.replaeUnit(this.config.getYdesc()) : ChartUtils.replaeUnit(this.config.getY2desc());
        var4 = var4 + "{    \t\t\t\t\t\t   \tname: '" + var14 + "',      ";
        var4 = var4 + "barMaxWidth:40,barMinHeight:10,";
        if (this.chart.getY2col() != null && this.chart.getY2col().length() > 0 && var3.equals(this.chart.getY2col())) {
            var4 = var4 + "type:'line',";
            var4 = var4 + "yAxisIndex: 1,";
            var4 = var4 + "showAllSymbol:true,";
        } else {
            var4 = var4 + "type:'bar',";
        }

        int var15 = this.chartColorIndex;
        if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var14) != null) {
            var15 = (Integer)this.chart.getSeriesColor().get(var14);
        }

        String var16 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var15 + "1").toString();
        String var10 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var15 + "2").toString();
        String var11 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var15 + "3").toString();
        this.chartColorIndex = this.chartColorIndex + 1;
        var4 = var4 + "itemStyle:{normal:{color:new echarts.graphic.LinearGradient(0,0,0,1,[{offset: 0, color: '" + var16 + "'},{offset: 0.5, color: '" + var10 + "'},{offset: 1, color: '" + var11 + "'}])}},";
        if (this.config.getShowLabel()) {
            String var12 = super.getKpiForamtString();
            String var13 = super.getKpiUnitString();
            var4 = var4 + " label : { normal: {show: true,color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "',position:\"top\",formatter:function(p){return formatNumber(p.value,'" + var12 + "') + '" + var13 + "'}}},";
        }

        var4 = var4 + "\t\tdata: [" + var5 + "] \t  " + "\t} ";
        return var4;
    }
}
