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

public class RadarChart extends com.ruisi.ext.engine.view.emitter.chart.highcharts.RadarChart {
    private List a = new ArrayList();

    public RadarChart() {
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
        this.AnalyseData(this.dataList, var2, var3, var5, var6, var7);
        if (var11 != null && "auto".equalsIgnoreCase(var11)) {
            int var15 = ChartUtils.autoWidth(this.xcolList);
            var11 = String.valueOf(var15);
            this.chart.setWidth(var11);
        }

        String var21 = "";
        if (this.dataInfoList != null && this.dataInfoList.size() != 0 && this.dataInfoList.size() != 1) {
            var21 = var21 + this.a(this.dataList, this.dataInfoList, var2, var3, var5);
        } else {
            var21 = var21 + this.a(this.dataList, var2, var3);
        }

        String var16 = ChartUtils.crtChartDivStyle(var11, this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + var12 + "\" style=\" " + var16 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("var myChart = echarts.init(document.getElementById('" + var12 + "'));");
        this.out.println("var pushObj = [];  //防止雷达图在多个坐标上出现数值");
        this.out.println("var existpushval = function(val){for(k=0; k<pushObj.length; k++){if(pushObj[k] == val){return true;}}return false;}");
        this.out.println("var option = {");
        this.out.println(" title: {show:false},");
        String var17 = super.getKpiForamtString();
        this.out.println("radar:{");
        this.out.println("shape:'circle',");
        this.out.println("radius:'85%',nameGap:5,axisLabel:{show: true,margin:2,formatter:function (value, index){if (!existpushval(value) && index != 0) {pushObj.push(value);return formatNumber(value, '" + var17 + "', true);" + "}else{" + "return '';" + "}" + "}},splitNumber:4,");
        this.out.println("indicator:[" + this.a(this.xcolList) + "]");
        this.out.println("},");
        this.out.println(" tooltip: { },");
        if (this.config.getShowLegend()) {
            this.out.println(" legend: {");
            this.out.println("   data:[" + ChartUtils.list2string(this.a) + "],");
            this.out.print("textStyle:{color:'" + ChartUtils.getChartlegendColor(this.config.getStyle()) + "'},");
            if (var10 != null && var10.length() > 0) {
                this.out.println("orient:'" + var10 + "',");
            }

            if ("righttop".equals(var9)) {
                this.out.println("\tright:20,top:0");
            } else if ("centertop".equals(var9)) {
                this.out.println("\tleft:'center',top:0");
            } else if ("centerbottom".equals(var9)) {
                this.out.println("\tleft:'center',top:'bottom'");
            }

            this.out.println("  },");
        }

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
            var6 = var6 + "type:'radar',";
            var12 = this.chartColorIndex;
            if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var9) != null) {
                var12 = (Integer)this.chart.getSeriesColor().get(var9);
            }

            String var15 = AbstractChartEmitter$ColorVO.valueOf("c" + var12).toString();
            var6 = var6 + "itemStyle:{normal:{color:'" + var15 + "'}},";
            this.chartColorIndex = this.chartColorIndex + 1;
            if (this.config.getMarkerEnabled()) {
                var6 = var6 + "showAllSymbol:true,";
            } else {
                var6 = var6 + "symbol:'none',";
            }

            var6 = var6 + "\t\tdata: [{value:[" + var10 + "],areaStyle:{normal:{opacity:0.3,color:'" + var15 + "'}}}] \t  " + "\t}  ";
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
        String var10 = ChartUtils.replaeUnit(this.config.getYdesc());
        var4 = var4 + "{    \t\t\t\t\t\t   \tname: '" + var10 + "',      ";
        var4 = var4 + "type:'radar', ";
        if (this.config.getMarkerEnabled()) {
            var4 = var4 + "showAllSymbol:true,";
        } else {
            var4 = var4 + "symbol:'none',";
        }

        int var11 = this.chartColorIndex;
        if (this.chart.getSeriesColor() != null && this.chart.getSeriesColor().get(var10) != null) {
            var11 = (Integer)this.chart.getSeriesColor().get(var10);
        }

        String var12 = AbstractChartEmitter$ColorVO.valueOf("c" + var11).toString();
        var4 = var4 + "itemStyle:{normal:{color:'" + var12 + "'}},";
        this.chartColorIndex = this.chartColorIndex + 1;
        var4 = var4 + "\t\tdata: [{name:'" + ChartUtils.replaeUnit(this.config.getYdesc()) + "',value:[" + var5 + "],areaStyle:{normal:{opacity:0.4,color:'" + var12 + "'}}}] \t  " + "\t} ";
        return var4;
    }

    private String a(List var1) {
        double var2 = 0.0D;

        for(int var4 = 0; var4 < this.dataList.size(); ++var4) {
            Map var5 = (Map)this.dataList.get(var4);
            double var6 = ChartUtils.getKpiValue(var5, this.chart.getYcol());
            if (var6 > var2) {
                var2 = var6;
            }
        }

        String var8 = "";
        int var9 = var1.size();

        for(int var10 = 0; var10 < var1.size() && var10 < this.config.getXcnt_Num(); ++var10) {
            var8 = var8 + "{";
            var8 = var8 + "name:\"" + var1.get(var10) + "\",max:" + var2;
            var8 = var8 + "}";
            if (var10 != var9 - 1 && var10 != this.config.getXcnt_Num() - 1) {
                var8 = var8 + ",";
            }
        }

        return var8;
    }
}
