//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.echarts;

import com.ruisi.ext.engine.view.context.dc.grid.AggreVO;
import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColorVO;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import com.ruisi.ispire.dc.grid.DataAggregation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LineChart extends com.ruisi.ext.engine.view.emitter.chart.highcharts.LineChart {
    private List a = new ArrayList();

    public LineChart() {
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

    @Override
    public int createChartJS(boolean var1) {
        this.a.clear();
        this.initConfg();
        this.a();
        String var2 = this.chart.getXcol();
        String var3 = this.chart.getY2col();
        String var4 = this.chart.getYcol();
        String var5 = this.chart.getScol();
        String var6 = this.config.getUnitCol();
        String var7 = this.config.getFormatCol();
        String var8 = this.config.getYmin();
        this.AnalyseData(this.dataList, var2, var4, var5, var6, var7);
        String var9 = this.chart.getWidth();
        if (var9 != null && "auto".equalsIgnoreCase(var9)) {
            int var10 = ChartUtils.autoWidth(this.xcolList);
            if (var10 == ChartUtils.chartMaxWidth) {
                this.config.setRouteXaxisLable("-30");
                if (this.xcolList.size() <= 31) {
                    this.config.setTickInterval("1");
                } else {
                    this.config.setTickInterval("2");
                }
            }

            var9 = String.valueOf(var10);
            this.chart.setWidth(var9);
        }

        String var18 = "";
        if (this.dataInfoList != null && this.dataInfoList.size() != 0 && this.dataInfoList.size() != 1) {
            var18 = var18 + this.a(this.dataList, this.dataInfoList, var2, var4, var5);
            if (var3 != null && var3.length() > 0) {
                if (this.chart.getMergeData() != null && this.chart.getMergeData()) {
                    DataAggregation var11 = new DataAggregation(new String[]{var2}, new AggreVO[]{new AggreVO(this.chart.getY2Aggre(), var3)}, this.dataList);
                    var18 = var18 + "," + this.a(var11.process(), var2, var3);
                } else {
                    var18 = var18 + "," + this.a(this.dataList, this.dataInfoList, var2, var3, var5);
                }
            }
        } else {
            var18 = var18 + this.a(this.dataList, var2, var4);
            if (var3 != null && var3.length() > 0) {
                var18 = var18 + "," + this.a(this.dataList, var2, var3);
            }
        }

        String var19 = ChartUtils.crtChartDivStyle(var9, this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + this.chart.getId() + "\" style=\" " + var19 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("var myChart = echarts.init(document.getElementById('" + this.chart.getId() + "'));");
        this.out.println("var option = {");
        this.out.println(" title: {show:false},");
        String[] var12 = this.config.getMargin().split(",");
        this.out.println(" grid: {show:true, borderWidth:0, top:" + var12[0] + ", right:" + var12[1] + ", bottom : " + var12[2] + ", left:" + var12[3] + " },");
        String var13 = super.getKpiForamtString();
        String var14 = super.getKpi2ForamtString();
        this.out.println(" tooltip: { trigger: 'axis'},");
        if (this.config.getShowLegend()) {
            this.out.println(" legend: {");
            this.out.println("   data:[" + ChartUtils.list2string(this.a) + "],");
            if (this.config.getLegendLayout() != null && this.config.getLegendLayout().length() > 0) {
                this.out.println("orient:'" + this.config.getLegendLayout() + "',");
            }

            this.out.print("textStyle:{color:'" + ChartUtils.getChartlegendColor(this.config.getStyle()) + "'},");
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
        this.out.println("\t name:'" + this.config.getXdesc() + "',nameLocation:'middle',nameGap:26,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'},");
        this.out.println("\t axisTick:{interval:0},");
        this.out.println("\taxisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }},");
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
        this.out.println(" yAxis: [{");
        this.out.println("name:'" + this.config.getYdesc() + "',nameGap:8,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'},axisLabel:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "', formatter:function(value){ return formatNumber(value,'" + var13 + "', true); }},");
        this.out.println("axisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }}");
        if (var8 != null && var8.length() > 0) {
            this.out.println(" ,min:" + var8);
        } else {
            this.out.println(" ,min:'dataMin'");
        }

        this.out.println("}");
        if (var3 != null && var3.length() > 0) {
            this.out.println(",{");
            this.out.println("name:'" + this.config.getY2desc() + "',nameGap:8,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'},position:'right'," + "axisLabel:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "', formatter:function(value){ return formatNumber(value,'" + var14 + "', true); }},");
            this.out.println("splitLine:{show: false},");
            this.out.println("axisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }}");
            if (var8 != null && var8.length() > 0) {
                this.out.println(" ,min:" + var8);
            } else {
                this.out.println(" ,min:'dataMin'");
            }

            this.out.println("}");
        }

        this.out.println("],");
        this.out.println(" series: [" + var18 + "],");
        this.out.print(" color:[");
        AbstractChartEmitter$ColorVO[] var15 = AbstractChartEmitter$ColorVO.values();

        for(int var16 = 0; var16 < var15.length; ++var16) {
            String var17 = var15[var16].toString();
            this.out.print("'" + var17 + "'");
            if (var16 != var15.length - 1) {
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
            if (var7 >= maxsercnt) {
                var6 = var6.substring(0, var6.lastIndexOf(","));
                break;
            }

            int var11 = 0;

            for(int var12 = 0; var12 < this.xcolList.size(); ++var12) {
                Object var13 = this.xcolList.get(var12);
                Object var14 = ChartUtils.findRow(var3, var4, var5, var13, var9, var1);
                var10 = var10 + var14 + ",";
                ++var11;
                if (var11 >= Integer.parseInt(this.config.getXcnt())) {
                    break;
                }
            }

            var10 = var10.substring(0, var10.length() - 1);
            String var17 = var9 == null ? "" : var9.toString();
            if (this.chart.getY2col() != null && this.chart.getY2col().length() > 0) {
                var17 = var17 + "-" + (var4.equals(this.chart.getYcol()) ? this.config.getYdesc() : this.config.getY2desc());
            }

            this.a.add(var17);
            var6 = var6 + "{    \t\t\t\t\t\t   \tname: '" + var17 + "',      ";
            var6 = var6 + "type:'line', smooth:" + (this.config.getSpline() ? "true" : "false") + ",";
            if (this.config.getMarkerEnabled()) {
                var6 = var6 + "showAllSymbol:true,symbolSize:6,";
            } else {
                var6 = var6 + "symbol:false,symbolSize:1,";
            }

            int var18 = this.chartColorIndex;
            if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var17) != null) {
                var18 = (Integer)this.chart.getSeriesColor().get(var17);
            }

            String var19 = AbstractChartEmitter$ColorVO.valueOf("c" + var18).toString();
            var6 = var6 + "itemStyle:{normal:{color:'" + var19 + "'}},";
            this.chartColorIndex = this.chartColorIndex + 1;
            if (this.chart.getY2col() != null && this.chart.getY2col().length() > 0 && var4.equals(this.chart.getY2col())) {
                var6 = var6 + "yAxisIndex: 1,";
            }

            if (this.config.getShowLabel()) {
                String var15 = super.getKpiForamtString();
                String var16 = super.getKpiUnitString();
                var6 = var6 + " label : { normal: {show: true,position:\"top\",formatter:function(p){return formatNumber(p.value,'" + var15 + "') + '" + var16 + "'}}},";
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
            if (var6 >= Integer.parseInt(this.config.getXcnt())) {
                break;
            }
        }

        var5 = var5.substring(0, var5.length() - 1);
        String var12 = var3.equals(this.chart.getYcol()) ? ChartUtils.replaeUnit(this.config.getYdesc()) : ChartUtils.replaeUnit(this.config.getY2desc());
        var4 = var4 + "{    \t\t\t\t\t\t   \tname: '" + var12 + "',      ";
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
        if (this.chart.getY2col() != null && this.chart.getY2col().length() > 0 && var3.equals(this.chart.getY2col())) {
            var4 = var4 + "yAxisIndex: 1,";
        }

        if (this.config.getShowLabel()) {
            String var10 = super.getKpiForamtString();
            String var11 = super.getKpiUnitString();
            var4 = var4 + " label : { normal: {show: true,position:\"top\",formatter:function(p){return formatNumber(p.value,'" + var10 + "') + '" + var11 + "'}}},";
        }

        var4 = var4 + "\t\tdata: [" + var5 + "] \t  " + "\t} ";
        return var4;
    }
}
