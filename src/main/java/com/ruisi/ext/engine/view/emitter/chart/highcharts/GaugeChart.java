//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.highcharts;

import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import java.util.Map;
import net.sf.json.JSONObject;

public class GaugeChart extends AbstractChartEmitter {
    public GaugeChart() {
    }

    protected void initMargin() {
        String var1 = this.config.getMargin();
        String var2 = this.config.getLegendPosition();
        String var3 = this.chart.getWidth();
        String var4 = this.chart.getHeight();
        if (var3 == null || var3.equals("")) {
            this.chart.setWidth("370");
        }

        if (var4 == null || var4.equals("")) {
            this.chart.setHeight("250");
        }

        if (var1 == null || var1.equals("")) {
            this.config.setMargin("10, 160, 20, 5");
        }

        if (this.config.getLegendLayout() == null || this.config.getLegendLayout().equals("")) {
            this.config.setLegendLayout("vertical");
        }

        if (var2 == null || var2.equals("")) {
            this.config.setLegendPosition("left: 'auto',bottom: '10px',right: '5px',top: 'auto' ");
        }

    }

    public int createChartJS(boolean var1) {
        this.initMargin();
        String var2 = this.chart.getXcol();
        String var3 = this.chart.getYcol();
        String var4 = this.chart.getScol();
        String var5 = this.config.getUnitCol();
        String var6 = this.config.getFormatCol();
        String var7 = this.config.getYmin();
        String var8 = this.config.getLegendPosition();
        String var9 = this.config.getLegendLayout();
        String var10 = this.chart.getWidth();
        String var11 = this.chart.getId();
        String var12 = this.config.getXdesc();
        String var13 = this.config.getYdesc();
        double[] var14 = ChartUtils.findMaxMin(this.dataList, var3);
        double var15 = var14[0];
        double var17 = var14[1];
        StringBuffer var19 = new StringBuffer();
        if (!var1) {
            var19.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            var19.append("<tr>");
        }

        int var20 = this.config.getGaugeCnt_Num();

        for(int var21 = 0; var21 < this.dataList.size() && var21 < var20; ++var21) {
            String var22;
            if (!var1) {
                var19.append("<td>");
                var22 = "width: " + var10 + "px; height: " + this.chart.getHeight() + "px; margin: 0 auto;  display:block;";
                var19.append(" <span id=\"" + var11 + (var20 == 1 ? "" : var21) + "\" style=\" " + var22 + " \"></span>   \r\n");
                var19.append(" <script type=\"text/javascript\"> \t\r\n");
                var19.append(" jQuery('#" + var11 + (var20 == 1 ? "" : var21) + "').highcharts(");
            }

            var19.append("\t\t{ \t   \r\n");
            var19.append(" \t\tchart: {\t\t   \r\n");
            if (var1) {
                var19.append(ChartUtils.crtPicSize(this.chart.getWidth(), this.chart.getHeight()));
            }

            var19.append("\t\t\t\ttype:'gauge',  plotBackgroundColor: null,plotBackgroundImage: null,plotBorderWidth: 0, plotShadow: false");
            var19.append(" \t\t},\t   \r\n");
            var19.append(" \r\n ");
            var19.append(" \t\ttitle: {\t   \r\n");
            var19.append(" \t\t\ttext: ''  \r\n");
            var19.append(" \t\t},\t   \r\n");
            var19.append("tooltip:{enabled:false},");
            var19.append("pane: {\t\r\n");
            var19.append("    startAngle: -150, \r\n");
            var19.append("    endAngle: 150, \r\n");
            var19.append("    background: [{ \r\n");
            var19.append("        backgroundColor: { \r\n");
            var19.append("            linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 }, \r\n");
            var19.append("            stops: [ \r\n");
            var19.append("                [0, '#FFF'], \r\n");
            var19.append("                [1, '#333']  \r\n");
            var19.append("            ]  \r\n");
            var19.append("        }, \r\n");
            var19.append("        borderWidth: 0, \r\n");
            var19.append("        outerRadius: '109%' \r\n");
            var19.append("    }, {  \r\n");
            var19.append("        backgroundColor: { \r\n");
            var19.append("            linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 }, \r\n ");
            var19.append("            stops: [ \r\n ");
            var19.append("                [0, '#333'], \r\n ");
            var19.append("                [1, '#FFF'] \r\n ");
            var19.append("            ]  \r\n");
            var19.append("       },  \r\n");
            var19.append("        borderWidth: 1, \r\n");
            var19.append("        outerRadius: '107%' \r\n");
            var19.append("     }, { \r\n");
            var19.append("    }, {  \r\n");
            var19.append("        backgroundColor: '#DDD', \r\n");
            var19.append("        borderWidth: 0, \r\n");
            var19.append("        outerRadius: '105%', \r\n");
            var19.append("        innerRadius: '103%'  \r\n");
            var19.append("    }] \r\n");
            var19.append("}, \r\n");
            var22 = (String)((Map)this.dataList.get(var21)).get(this.config.getUnitCol());
            if (var22 == null) {
                var22 = this.config.getUnitCol() == null ? "" : this.config.getUnitCol();
            }

            String var23 = (String)((Map)this.dataList.get(var21)).get(this.config.getFormatCol());
            if (var23 == null) {
                var23 = this.config.getFormatCol() == null ? "" : this.config.getFormatCol();
            }

            Object var24 = ((Map)this.dataList.get(var21)).get(this.chart.getXcol());
            double var25 = ChartUtils.getKpiValue((Map)this.dataList.get(var21), this.chart.getYcol());
            var19.append("yAxis: { \n");
            var19.append("    min: " + (this.config.getYmin() != null && var7.length() > 0 ? this.config.getYmin() : var15 * 1.1D) + ", \n");
            var19.append("    max: " + (this.config.getYmax() != null && this.config.getYmax().length() > 0 ? this.config.getYmax() : var17 * 1.1D) + ", \n");
            var19.append("    minorTickInterval: 'auto', \n");
            var19.append("    minorTickWidth: 1, \n");
            var19.append("    minorTickLength: 10, \n");
            var19.append("    minorTickPosition: 'inside', \n");
            var19.append("    minorTickColor: '#666', \n");
            var19.append("    tickPixelInterval: 30, \n");
            var19.append("    tickWidth: 2, \n");
            var19.append("    tickPosition: 'inside', \n");
            var19.append("    tickLength: 10, \n");
            var19.append("    tickColor: '#666', \n");
            var19.append("    labels: { \n");
            var19.append("        step: 3, \n");
            var19.append("        rotation: 'auto' \n");
            var19.append("    }, \n");
            var19.append("    title: { \n");
            var19.append("        text: '" + (var24 == null ? "" : var24) + "' \n");
            var19.append("    }, \n");
            double var27 = this.config.getYmin() != null && var7.length() > 0 ? Double.parseDouble(this.config.getYmin()) : var15 * 1.1D;
            double var29 = this.config.getYmax() != null && this.config.getYmax().length() > 0 ? Double.parseDouble(this.config.getYmax()) : var17 * 1.1D;
            double var31 = var27 + (var29 - var27) * 0.7D;
            double var33 = var31 + (var29 - var27) * 0.15D;
            var19.append("    plotBands: [{ \n");
            var19.append("        from: " + var27 + ", \n");
            var19.append("        to: " + var31 + ", \n");
            var19.append("        color: '#55BF3B' // green \n");
            var19.append("    }, { \n");
            var19.append("        from: " + var31 + ", \n");
            var19.append("        to: " + var33 + ", \n");
            var19.append("        color: '#DDDF0D' // yellow \n");
            var19.append("    }, { \n");
            var19.append("        from: " + var33 + ", \n");
            var19.append("        to: " + var29 + ", \n");
            var19.append("        color: '#DF5353' // red \n");
            var19.append("    }]        \n");
            var19.append("}, \n");
            if (this.config.getAction() != null && !this.config.getAction().equals("")) {
                var19.append("\t\tplotOptions: {  \t   \r\n");
                var19.append("\t\t\tgauge: {\t   \r\n");
                var19.append("\t\t\t\tcursor: 'pointer',\t   \r\n");
                var19.append("\t\t\t\tpoint: {\t   \r\n");
                var19.append("\t\t\t\t\tevents: {\t   \r\n");
                var19.append("\t\t\t\t\t\tclick: function(e) {\t   \r\n");
                var19.append("\t\t\t\t\t\t\tvar yvalue = this.y;\t   \r\n");
                var19.append("\t\t\t\t\t\t\tvar svalue = '';\t   \r\n");
                var19.append("\t\t\t\t\t\t\tvar pos = {left:e.clientX, top:e.clientY};\t   \r\n");
                var19.append("\t\t\t\t\t\t\tvar xvalue = this.series.name;\t\r\n");
                Map var35 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, 4);
                var19.append(" var ys = " + JSONObject.fromObject(var35) + "; \r\n");
                String var36 = (String)this.request.getAttribute("compId");
                Integer var37 = (Integer)this.request.getAttribute("xcolid");
                var19.append("\t\t\t\t\t\t\t" + this.config.getAction() + "(ys[xvalue], xvalue, yvalue, svalue, pos, " + var36 + ", " + var37 + ") ;  \r\n");
                var19.append("\t\t\t\t\t\t\t} \r\n");
                var19.append("\t\t\t\t\t\t}\t   \r\n");
                var19.append("\t\t\t\t\t}\t   \r\n");
                var19.append("\t\t\t\t}\t   \r\n");
                var19.append("\t\t\t},\t   \r\n");
            }

            var19.append("\t\tseries: [\t\t   \r\n");
            var19.append(this.a(var24, var25, var22, var23, var1) + " \t\r\n ");
            var19.append("\t\t]\t   \r\n");
            var19.append("\t}");
            if (!var1) {
                var19.append(");\t   \t\t\r\n");
                var19.append(" \t\t\t\r\n ");
                var19.append("  \t\t\r\n");
                var19.append("</script>");
                var19.append("</td>");
            }
        }

        if (!var1) {
            var19.append("</tr>");
            var19.append("</table>");
        }

        this.out.println(var19.toString());
        return 6;
    }

    private String a(Object var1, double var2, String var4, String var5, boolean var6) {
        String var7 = "";
        var7 = var7 + "{\t\t\t\r\nname: '" + (var1 == null ? "" : var1) + "',\t\r\n" + "data: [\t\t\t\r\n" + var2 + "\t\r\n" + "],\t\t\t\t\t\r\n" + (!var6 ? "dataLabels:{ formatter:function(){var kmh = this.y;return formatNumber(kmh, '" + var5 + "') + '" + ChartUtils.writerUnit(this.chart.getRate()) + ("%".equals(var4) ? "" : var4) + "'} },\t\t\t\t\t\r\n" : "") + "tooltip:{valueSuffix:' " + var4 + "'}" + "}\t\t\t\t\t\r\n";
        return var7;
    }
}
