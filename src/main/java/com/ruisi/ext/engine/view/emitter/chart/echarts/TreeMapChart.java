//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.echarts;

import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import java.util.List;
import java.util.Map;

public class TreeMapChart extends AbstractChartEmitter {
    public static final int wordCnttDef = 20;

    public TreeMapChart() {
    }

    public int createChartJS(boolean var1) {
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
        String var15 = this.a(this.dataList, var3, var5, var7);
        String var16 = ChartUtils.crtChartDivStyle(var11, this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + var12 + "\" style=\" " + var16 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("var myChart = echarts.init(document.getElementById('" + var12 + "'));");
        this.out.println("var option = {");
        this.out.println("legend:{show:false},");
        this.out.println(" tooltip: { },");
        this.out.println(" series: [" + var15 + "]");
        this.out.println("};");
        this.out.println("myChart.setOption(option);");
        ChartUtils.echartsClick(this.out, this.chart, this.request, this.config);
        this.out.println("});");
        this.out.println(" </script>");
        return 6;
    }

    protected void initMargin() {
        if (this.chart.getWidth() == null || this.chart.getWidth().length() == 0) {
            this.chart.setWidth("370");
        }

        if (this.chart.getHeight() == null || this.chart.getHeight().length() == 0) {
            this.chart.setHeight("250");
        }

        if (this.config.getXcnt() == null || this.config.getXcnt().length() == 0) {
            this.config.setXcnt(String.valueOf(20));
        }

    }

    private String a(List var1, String var2, String var3, String var4) {
        String var5 = "";
        String var6 = "";

        for(int var7 = 0; var7 < var1.size(); ++var7) {
            Map var8 = (Map)var1.get(var7);
            Object var9 = var8.get(this.chart.getXcol());
            Object var10 = var8.get(var2);
            if (var7 >= this.config.getXcnt_Num()) {
                break;
            }

            var6 = var6 + "{ name: \"" + (this.chart.getXcol() == null ? "合计" : var9) + "\",";
            var6 = var6 + "\tvalue: " + var10 + "}";
            if (var7 != var1.size() - 1 && var7 != this.config.getXcnt_Num() - 1) {
                var6 = var6 + ",";
            }
        }

        var5 = var5 + "{\t\t\t\r\ntype: 'treemap', roam:false,  nodeClick:false, width: '99%', height:'99%', breadcrumb:{show:false},  \r\n";
        var5 = var5 + "data: [\t\t\t\r\n" + var6 + "\t\r\n" + "]\t\t\t\t\t\r\n" + "}\t\t\t\t\t\r\n";
        return var5;
    }
}
