//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.highcharts;

import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HeatmapChart extends AbstractChartEmitter {
    private List a = new LinkedList();

    public HeatmapChart() {
    }

    private void a() {
        if (this.chart.getWidth() == null || this.chart.getWidth().equals("")) {
            this.chart.setWidth("800");
        }

        if (this.chart.getHeight() == null || this.chart.getHeight().equals("")) {
            this.chart.setHeight("400");
        }

        if (this.config.getMargin() == null || this.config.getMargin().equals("")) {
            this.config.setMargin("10, 70, 50, 70");
        }

        if (this.config.getLegendLayout() == null || this.config.getLegendLayout().equals("")) {
            this.config.setLegendLayout("vertical");
        }

        if (this.config.getLegendPosition() == null || this.config.getLegendPosition().equals("")) {
            this.config.setLegendPosition("left: 'auto',bottom: 'auto',right: '10px',top: '10px' ");
        }

        if (this.config.getXcnt() == null || this.config.getXcnt().length() == 0) {
            this.config.setXcnt(String.valueOf(60));
        }

    }

    public int createChartJS(boolean var1) {
        this.a();
        String var2 = this.chart.getXcol();
        String var3 = this.chart.getYcol();
        String var4 = this.chart.getScol();
        String var5 = this.config.getFormatCol();
        String var6 = this.config.getUnitCol();
        String var7 = this.config.getYmin();
        String var8 = this.config.getYmax();
        String var9 = this.config.getLegendLayout();
        String var10 = this.chart.getWidth();
        String var11 = this.chart.getId();
        this.a(this.dataList, var2, var3, var4, var6, var5);
        if (var10 != null && "auto".equalsIgnoreCase(var10)) {
            int var12 = ChartUtils.autoWidth(this.xcolList);
            var10 = String.valueOf(var12);
            this.chart.setWidth(var10);
        }

        String var17 = ChartUtils.crtChartDivStyle(var10, this.chart.getHeight(), this.chart.getAlign());
        StringBuffer var13 = new StringBuffer();
        if (!var1) {
            var13.append(" <div id=\"" + var11 + "\" style=\" " + var17 + " \"></div>   \r\n");
            var13.append(" <script type=\"text/javascript\"> \t\r\n");
            var13.append(" var chart;  \t\r\n");
            var13.append("  \t   \r\n");
            var13.append(" \tchart = new Highcharts.Chart(");
        }

        var13.append("\t\t\t{ \t   \r\n");
        var13.append(" \t\tchart: {\t\t   \r\n");
        if (var1) {
            var13.append(ChartUtils.crtPicSize(this.chart.getWidth(), this.chart.getHeight()));
        }

        var13.append(" \t\t\trenderTo: '" + var11 + "',\t   \r\n");
        var13.append("\t\t\t\t type: 'heatmap',\t\r\n");
        var13.append(" \t\t\tmargin: [" + this.config.getMargin() + "]    \r\n");
        var13.append(" \t\t},\t   \r\n");
        var13.append(" \r\n ");
        var13.append(" \t\ttitle: {\t   \r\n");
        var13.append(" \t\t\ttext: '',\t   \r\n");
        var13.append(" \t\t\tstyle: {\t   \r\n");
        var13.append(" \t\t\t\tmargin: '10px 0 0 0' // center it\t   \r\n");
        var13.append(" \t\t\t}\t   \r\n");
        var13.append(" \t\t},\t   \r\n");
        var13.append(" \r\n ");
        var13.append("\t\t\txAxis: {\t   \r\n");
        if (this.config.getXdesc() != null && this.config.getXdesc().length() > 0) {
            var13.append("\t\t\t title: {enabled: true, text:'" + this.config.getXdesc() + "'}, \r\n");
        } else {
            var13.append("\t\t\t title: {enabled: false}, \r\n");
        }

        var13.append("\t\t\t\tcategories:[" + this.a(this.xcolList) + "],");
        if (!"none".equals(this.config.getTickInterval())) {
            var13.append("\t\t\t\ttickInterval:" + this.config.getTickInterval() + ",\r\n");
        }

        if (this.config.getRouteXaxisLable() != null && !"0".equals(this.config.getRouteXaxisLable())) {
            var13.append("      labels: {  \n");
            var13.append("\t\t\t\trotation: " + this.config.getRouteXaxisLable() + ",\t\n");
            var13.append("\t\t\t\talign: '" + (Integer.parseInt(this.config.getRouteXaxisLable()) > 0 ? "left" : "right") + "'\n");
            var13.append("},\t\n");
        }

        var13.append("\t\t\t startOnTick: true, \r\n");
        var13.append("\t\t\t endOnTick: true, \r\n");
        var13.append("\t\t\t showLastLabel: true \r\n");
        var13.append("\t\t\t},\t   \r\n");
        var13.append("\t\t\tyAxis: {\t   \r\n");
        if (this.config.getYdesc() != null && this.config.getYdesc().length() > 0) {
            var13.append("\t\t\t title: {enabled: true, text:'" + this.config.getYdesc() + "'} \r\n");
        } else {
            var13.append("\t\t\t title: {enabled: false} \r\n");
        }

        var13.append("\t\t\t,categories:[" + this.a(this.a) + "]");
        var13.append("\t\t\t},\t   \r\n");
        var13.append(" colorAxis: {");
        var13.append(" min: " + (var7 != null && var7.length() != 0 ? var7 : "0") + ",");
        var13.append(" minColor: '#FFFFFF',");
        var13.append(" maxColor: Highcharts.getOptions().colors[0]");
        var13.append(" },");
        var13.append("\t\tlegend: {\t   \r\n");
        var13.append("\t\t\tenabled: " + this.config.getShowLegend() + ",\t   \r\n");
        var13.append("\t\t\tlayout: '" + var9 + "',\t   \r\n");
        if (this.config.getLegendPosition().equals("righttop")) {
            var13.append("\t\t\talign: 'right',verticalAlign: 'top',");
        } else if (this.config.getLegendPosition().equals("centerbottom")) {
            var13.append("\talign:'center',verticalAlign: 'bottom',");
        }

        var13.append("borderWidth:0 \r\n");
        var13.append("\t\t},\t   \r\n");
        String var10000 = (String)((Map)this.dataList.get(0)).get(var5);
        Object var15 = ((Map)this.dataList.get(0)).get(var6);
        String var16 = var15 == null ? null : var15.toString();
        if (var16 == null) {
            var16 = var6 == null ? "" : var6;
        }

        if ("%".equals(var16)) {
            var16 = "";
        }

        var13.append("\ttooltip: { \n");
        var13.append("\tformatter:function(){ \n");
        var13.append("\treturn '<b>' + this.series.xAxis.categories[this.point.x] + '</b> <br><b>' + this.series.yAxis.categories[this.point.y] + '</b> <br><b>' +  this.point.value + '</b>'; \n");
        var13.append("\t} \n");
        var13.append("\t},\t\n");
        var13.append(" \r\n ");
        var13.append("\t\tseries: [\t\t   \r\n");
        var13.append(this.a(this.dataList, var2, var3, var4) + "\r\n");
        var13.append("\t\t]\t   \r\n");
        var13.append("\t}");
        if (!var1) {
            var13.append(");\t   \r\n");
            var13.append(" \r\n ");
            var13.append("   \r\n");
            var13.append("</script>");
        }

        this.out.println(var13.toString());
        return 6;
    }

    private void a(List var1, String var2, String var3, String var4, String var5, String var6) {
        Iterator var8 = var1.iterator();

        while(var8.hasNext()) {
            Map var7 = (Map)var8.next();
            Object var9 = var7.get(var2);
            if (!this.xcolList.contains(var9) && this.xcolList.size() <= this.config.getXcnt_Num()) {
                this.xcolList.add(var9);
            }

            if (var4 != null) {
                Object var10 = var7.get(var4);
                if (!this.a.contains(var10)) {
                    this.a.add(var10);
                }
            }
        }

    }

    private String a(List var1, String var2, String var3, String var4) {
        StringBuffer var5 = new StringBuffer("{");
        var5.append("name:'',");
        var5.append("borderWidth:1,");
        var5.append("data:[");

        for(int var6 = 0; var6 < this.xcolList.size(); ++var6) {
            Object var7 = this.xcolList.get(var6);

            for(int var8 = 0; var8 < this.a.size(); ++var8) {
                Object var9 = this.a.get(var8);
                Object var10 = this.a(var7, var9);
                var5.append("[" + var6 + ", " + var8 + "," + var10 + "]");
                if (var6 != this.xcolList.size() - 1 || var8 != this.a.size() - 1) {
                    var5.append(",");
                }
            }
        }

        var5.append("],");
        var5.append("dataLabels:{");
        var5.append("enabled: true,");
        var5.append("color: '#000000'");
        var5.append("}");
        var5.append("}");
        return var5.toString();
    }

    private Object a(Object var1, Object var2) {
        Object var3 = null;

        for(int var4 = 0; var4 < this.dataList.size(); ++var4) {
            Map var5 = (Map)this.dataList.get(var4);
            Object var6 = var5.get(this.chart.getXcol());
            Object var7 = var5.get(this.chart.getScol());
            if (var6.equals(var1) && var7.equals(var2)) {
                var3 = var5.get(this.chart.getYcol());
            }
        }

        return var3;
    }

    private StringBuffer a(List var1) {
        StringBuffer var2 = new StringBuffer("");

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Object var4 = var1.get(var3);
            if (var4 instanceof String) {
                var2.append("'");
            }

            var2.append(var4);
            if (var4 instanceof String) {
                var2.append("'");
            }

            if (var3 != var1.size() - 1) {
                var2.append(",");
            }
        }

        return var2;
    }
}
