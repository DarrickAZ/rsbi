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

public class BubbleChart extends com.ruisi.ext.engine.view.emitter.chart.highcharts.BubbleChart {
    private List a = new ArrayList();

    public BubbleChart() {
    }

    private void a() {
        String var1 = this.config.getMargin();
        String var2 = this.config.getMarginRight();
        String var3 = this.config.getMarginLeft();
        if (var1 == null || var1.equals("")) {
            var1 = "10, " + (var2 != null ? var2 : "10") + ", 40, " + (var3 != null ? var3 : "70");
        }

        this.config.setMargin(var1);
        if (this.config.getXcnt() == null || this.config.getXcnt().length() == 0) {
            this.config.setXcnt(defcnt);
        }

    }

    @Override
    public int createChartJS(boolean var1) {
        this.a.clear();
        this.initConfg();
        this.a();
        double var2 = 1.7976931348623157E308D;
        double var4 = 0.0D;

        for(int var6 = 0; var6 < this.dataList.size(); ++var6) {
            Map var7 = (Map)this.dataList.get(var6);
            double var8 = ChartUtils.getKpiValue(var7, this.chart.getY3col());
            if (var8 < var2) {
                var2 = var8;
            }

            if (var8 > var4) {
                var4 = var8;
            }
        }

        String var28 = this.chart.getXcol();
        String var29 = this.chart.getYcol();
        String var30 = this.chart.getScol();
        String var9 = this.config.getUnitCol();
        String var10 = this.config.getFormatCol();
        String var11 = this.config.getYmin();
        String var12 = this.config.getLegendPosition();
        String var13 = this.config.getLegendLayout();
        String var14 = this.chart.getWidth();
        String var15 = this.chart.getId();
        String var16 = this.config.getXdesc();
        String var17 = this.config.getYdesc();
        String var18 = this.config.getAction();
        this.AnalyseData(this.dataList, var28, var29, var30, var9, var10);
        String var19 = "";
        if (this.dataInfoList != null && this.dataInfoList.size() != 0) {
            var19 = this.a(this.dataList, this.dataInfoList, var4, var2);
        } else {
            var19 = this.a(this.dataList, var4, var2);
        }

        if (var14 != null && "auto".equalsIgnoreCase(var14)) {
            int var20 = ChartUtils.autoWidth(this.xcolList);
            var14 = String.valueOf(var20);
            this.chart.setWidth(var14);
        }

        String var31 = ChartUtils.crtChartDivStyle(var14, this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + var15 + "\" style=\" " + var31 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("var myChart = echarts.init(document.getElementById('" + var15 + "'));");
        this.out.println("var option = {");
        this.out.println(" title: {show:false},");
        String[] var21 = this.config.getMargin().split(",");
        this.out.println(" grid: {show:true, top:" + var21[0] + ", right:" + var21[1] + ", bottom : " + var21[2] + ", left:" + var21[3] + ", borderWidth:0 },");
        String var22 = (String)((Map)this.dataList.get(0)).get(this.config.getFormatCol3());
        if (var22 == null) {
            var22 = this.config.getFormatCol3();
        }

        String var23 = super.getKpiForamtString();
        String var24 = super.getKpi2ForamtString();
        this.out.println(" tooltip: { formatter: function(params){ ");
        this.out.println("  return  params.value[2] + '<br/> " + var16 + " ：' + formatNumber(params.data[0], '" + var24 + "')+'" + ChartUtils.writerUnit(this.chart.getRate2()) + "<br/>" + var17 + "：' + formatNumber(params.data[1], '" + var23 + "')+'" + ChartUtils.writerUnit(this.chart.getRate()) + "<br/>" + "气泡值：'+ formatNumber(params.data[3], '" + (var22 == null ? "" : var22) + "')+'" + ChartUtils.writerUnit(this.chart.getRate3()) + "' " + ";");
        this.out.println(" }},");
        if (this.config.getShowLegend()) {
            this.out.println(" legend: {");
            this.out.println("   data:[" + ChartUtils.list2string(this.a) + "],");
            this.out.print("textStyle:{color:'" + ChartUtils.getChartlegendColor(this.config.getStyle()) + "'},");
            if (var13 != null && var13.length() > 0) {
                this.out.println("orient:'" + var13 + "',");
            }

            if ("righttop".equals(var12)) {
                this.out.println("\tright:20,top:0");
            } else if ("centertop".equals(var12)) {
                this.out.println("\tleft:'center',top:0");
            } else if ("centerbottom".equals(var12)) {
                this.out.println("\tleft:'center',top:'bottom'");
            }

            this.out.println("  },");
        }

        this.out.println(" xAxis: {");
        this.out.println("\t splitLine:{lineStyle:{type:'dashed'}},");
        this.out.println("\tname:'" + var16 + "',");
        this.out.println("\tnameLocation:'middle',");
        this.out.println("\taxisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }},");
        this.out.println("\tposition:'bottom',nameGap:25,scale:true,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'}");
        this.out.println(" },");
        this.out.println(" yAxis: {");
        this.out.println("\t splitLine:{lineStyle:{type:'dashed'}},scale:true,nameTextStyle:{color:'" + ChartUtils.getChartXYColor(this.config.getStyle()) + "'},");
        this.out.println("\taxisLine:{ lineStyle:{ color:'" + ChartUtils.getChartXYLineColor(this.config.getStyle()) + "' }},");
        this.out.println("\tname:'" + var17 + "'");
        this.out.println("},");
        this.out.println(" series: [");
        this.out.println(var19);
        this.out.println("],");
        this.out.print(" color:[");
        AbstractChartEmitter$ColorVO[] var25 = AbstractChartEmitter$ColorVO.values();

        for(int var26 = 0; var26 < var25.length; ++var26) {
            String var27 = var25[var26].toString();
            this.out.print("'" + var27 + "'");
            if (var26 != var25.length - 1) {
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

    private String a(List var1, double var2, double var4) {
        String var6 = "";
        this.a.add(ChartUtils.replaeUnit(this.config.getYdesc()));
        StringBuffer var7 = new StringBuffer();
        int var8 = 0;

        for(int var9 = 0; var9 < var1.size(); ++var9) {
            Map var10 = (Map)var1.get(var9);
            Object var11 = var10.get(this.chart.getYcol());
            Object var12 = var10.get(this.chart.getY2col());
            Object var13 = var10.get(this.chart.getY3col());
            Object var14 = var10.get(this.chart.getXcol());
            var7.append("[" + var12 + "," + var11 + ",\"" + (var14 == null ? "" : var14) + "\"," + var13 + "],");
            ++var8;
            if (var8 >= this.config.getXcnt_Num()) {
                break;
            }
        }

        if (var7.lastIndexOf(",") >= 0) {
            var7.deleteCharAt(var7.lastIndexOf(","));
        }

        String var15 = this.config.getXdesc();
        var6 = var6 + "{    \t\t\t\t\t\t \r\n  \tname: '" + var15 + "',     \r\n ";
        var6 = var6 + "type:'scatter',";
        var6 = var6 + "symbolSize:function(data){ return bubbleSize(" + var2 + "," + var4 + ",data[3]);" + "},";
        if (this.config.getShowLabel() != null && this.config.getShowLabel()) {
            var6 = var6 + "label:{normal:{show:true, position:\"top\",formatter:function(params){ return params.value[2]?params.value[2]:'合计' }}},";
        }

        int var16 = this.chartColorIndex;
        if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var15) != null) {
            var16 = (Integer)this.chart.getSeriesColor().get(var15);
        }

        String var17 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var16 + "1").toString();
        String var18 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var16 + "2").toString();
        String var19 = AbstractChartEmitter$ColumnColorVO.valueOf("c" + var16 + "3").toString();
        this.chartColorIndex = this.chartColorIndex + 1;
        var6 = var6 + "itemStyle:{normal:{shadowBlur: 10,shadowColor: 'rgba(25, 100, 150, 0.5)',shadowOffsetY: 5,color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset: 0, color: '" + var17 + "'},{offset: 0.5, color: '" + var18 + "'},{offset: 1, color: '" + var19 + "'}])" + "}},";
        var6 = var6 + "\t data: [" + var7 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t     \r\n ";
        return var6;
    }

    private String a(List var1, List var2, double var3, double var5) {
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

            for(int var13 = 0; var13 < var1.size(); ++var13) {
                Map var14 = (Map)var1.get(var13);
                String var15 = var14.get(this.chart.getScol()).toString();
                if (var10.equals(var15)) {
                    Object var16 = var14.get(this.chart.getYcol());
                    Object var17 = var14.get(this.chart.getY2col());
                    Object var18 = var14.get(this.chart.getY3col());
                    Object var19 = var14.get(this.chart.getXcol());
                    var11.append("[" + var17 + "," + var16 + ",\"" + var19 + "\"," + var18 + "],");
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
            var7 = var7 + "symbolSize:function(data){ return bubbleSize(" + var3 + "," + var5 + ",data[3]);" + "},";
            if (this.config.getShowLabel() != null && this.config.getShowLabel()) {
                var7 = var7 + "label:{normal:{show:true, position:\"top\",formatter:function(params){ return params.value[2] }}},";
            }

            var7 = var7 + "itemStyle:{normal:{shadowBlur: 10,shadowColor: 'rgba(25, 100, 150, 0.5)',shadowOffsetY: 5,color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{offset: 0,color: 'rgb(129, 227, 238)'}, {offset: 1, color: 'rgb(25, 183, 207)'}])}},";
            var7 = var7 + "\t data: [" + var11 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t     \r\n ";
            if (var8 != var2.size() - 1) {
                var7 = var7 + ",";
            }
        }

        return var7;
    }
}
