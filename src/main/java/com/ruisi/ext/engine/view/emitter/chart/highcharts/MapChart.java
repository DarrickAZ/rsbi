//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.highcharts;

import com.ruisi.ext.engine.view.builder.chart.ChartBuilder;
import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public class MapChart extends AbstractChartEmitter {
    protected double maxValue = 4.9E-324D;
    protected double minValue = 1.7976931348623157E308D;

    public MapChart() {
    }

    protected void initMargin() {
        if (this.chart.getWidth() == null || this.chart.getWidth().length() == 0) {
            this.chart.setWidth("370");
        }

        if (this.chart.getHeight() == null || this.chart.getHeight().length() == 0) {
            this.chart.setHeight("250");
        }

        if (this.config.getMargin() == null || this.config.getMargin().length() == 0) {
            this.config.setMargin("10, 20, 20, 5");
        }

        if (this.config.getLegendLayout() == null || this.config.getLegendLayout().length() == 0) {
            this.config.setLegendLayout("vertical");
        }

        if (this.config.getLegendPosition() == null || this.config.getLegendPosition().length() == 0) {
            this.config.setLegendLayout("righttop");
        }

    }

    public int createChartJS(boolean var1) {
        this.initMargin();
        String var2 = this.chart.getYcol();
        String var3 = this.chart.getScol();
        String var4 = this.config.getFormatCol();
        String var5 = this.config.getYmin();
        String var6 = this.config.getYmax();
        String var7 = this.config.getLegendLayout();
        String var8 = this.chart.getWidth();
        String var9 = this.chart.getId();
        String var10 = this.config.getMapJson();
        boolean var11 = ChartBuilder.isDrill(this.request);
        if (var10 != null && var10.length() != 0) {
            String var12 = this.a(this.dataList, var2, var3, var4);
            String var13 = ChartUtils.crtChartDivStyle(var8, this.chart.getHeight(), this.chart.getAlign());
            StringBuffer var14 = new StringBuffer();
            if (var11) {
                this.out.print("<a href=\"#\" onclick=\"chart_drillUp('" + this.chart.getDrill().getUrl() + "','" + this.chart.getDrill().getParams() + "','" + this.chart.getId() + "')\">返回</a>");
            }

            var14.append(" <div id=\"" + var9 + "\" style=\" " + var13 + " \"></div>   \r\n");
            var14.append(" <script type=\"text/javascript\"> jQuery(function(){ \t\r\n");
            var14.append(var12);
            if (var11) {
                var10 = ((Map)this.dataList.get(0)).get(this.chart.getDrill().getMapCol()).toString();
            }

            var14.append("\r\n jQuery.getJSON('../ext-res/highcharts/map/" + var10 + "?t='+Math.random(), function (data) { \r\n");
            var14.append(" \t$('#" + var9 + "').highcharts('Map', { \r\n");
            var14.append("            title : { \r\n");
            var14.append("                text : '' \r\n");
            var14.append("            }, \r\n");
            var14.append("            mapNavigation: { \r\n");
            var14.append("                enabled: true, \r\n");
            var14.append("                enableButtons: true \r\n");
            var14.append("            }, \r\n");
            var14.append("\t\t\texporting: { \r\n");
            var14.append("\t\t\t\tenabled:false \r\n");
            var14.append("\t\t\t}, \r\n");
            var14.append("            colorAxis: {  \r\n");
            var14.append("                min: " + (var5 != null && var5.length() > 0 ? var5 : null) + ", \r\n");
            var14.append("                max: " + (var6 != null && var6.length() > 0 ? var6 : null) + ",  \r\n");
            var14.append("                type: 'logarithmic' \r\n");
            var14.append("            }, \r\n");
            var14.append("\t\t\tlegend:{ \r\n");
            var14.append("\t\t\t\tenabled: " + this.config.getShowLegend() + ", \r\n");
            if ("horizontal".equals(var7)) {
                var14.append("\t\t\t\tverticalAlign:'bottom', layout:'" + var7 + "', align:'center' \r\n");
            } else {
                var14.append("\t\t\t\tverticalAlign:'center', layout:'" + var7 + "', align:'right' \r\n");
            }

            var14.append("\t\t\t}, \r\n");
            Object var15 = ((Map)this.dataList.get(0)).get(this.config.getUnitCol());
            String var16 = var15 == null ? null : var15.toString();
            if (var16 == null) {
                var16 = this.config.getUnitCol();
            }

            if ("%".equals(var16)) {
                var16 = "";
            }

            String var17 = (String)((Map)this.dataList.get(0)).get(var4);
            if (var17 == null) {
                var17 = var4;
            }

            var14.append("                tooltip: { ");
            var14.append("\t\t\tformatter: function() { ");
            var14.append("\t\t\t\treturn '<b>'+ this.series.name +'</b><br/>'+ ");
            var14.append("\t\t\t\t\tthis.point.name +': '+ formatNumber(this.point.value, '" + (var17 == null ? "" : var17) + "') + '" + ChartUtils.writerUnit(this.chart.getRate()) + (var16 == null ? "" : var16) + "'; ");
            var14.append("\t\t\t}\t   \r\n");
            var14.append("                }, \r\n");
            String var18 = (String)((Map)this.dataList.get(0)).get(this.chart.getScol());
            var14.append("            series : [{  \r\n");
            var14.append("                data : dt, \r\n");
            var14.append("                mapData: data, \r\n");
            var14.append("                joinBy: ['hasc', 'code'], \r\n");
            var14.append("                name: '" + var18 + "',  \r\n");
            var14.append("                states: { \r\n");
            var14.append("                    hover: { ");
            var14.append("                        color: '#BADA55' \r\n");
            var14.append("                    }  \r\n");
            var14.append("                }, \r\n");
            Map var19;
            if (this.chart.getDrill() != null && !var11) {
                var14.append("\t\t\t\tpoint: { events : { click : function(e){ \r\n");
                var14.append("\t\t\t\t\tvar xvalue = e.point.name; \r\n");
                var19 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, 100);
                String var22 = this.chart.getDrill().getUrl();
                var14.append(" var ys = " + JSONObject.fromObject(var19) + "; \r\n");
                var14.append("\t\tchart_Drill('" + this.chart.getXcolDesc() + "', ys[xvalue], \r\n" + "'" + var22 + "'" + ", '" + this.chart.getDrill().getParams() + "','" + this.chart.getId() + "'); \r\n");
                var14.append("\t\t\t\t}} }, \r\n");
            } else if (this.chart.getLink() != null) {
                var14.append("\t\t\t\tpoint: { events : { click : function(e){ \r\n");
                var14.append("\t\t\t\t\tvar xvalue = e.point.name; \r\n");
                var19 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, 100);
                String[] var20 = this.chart.getLink().getUrl();
                String[] var21 = this.chart.getLink().getTargetId();
                var14.append(" var ys = " + JSONObject.fromObject(var19) + "; \r\n");
                var14.append("\t\tchartComp_Link('" + this.chart.getXcolDesc() + "', ys[xvalue], \r\n" + ChartUtils.array2string(var20) + ", '" + this.chart.getLink().getParams() + "', " + ChartUtils.array2string(var21) + "," + ChartUtils.array2string(this.chart.getLink().getType()) + "); \r\n");
                var14.append("\t\t\t\t}} }, \r\n");
            }

            var14.append("\t\t\t\tdataLabels: { \r\n");
            var14.append("                    enabled: true,  \r\n");
            var14.append("                    format: '" + ("china.json".equals(this.config.getMapJson()) ? "{point.properties.name}" : "{point.name}") + "' \r\n");
            var14.append("                }  \r\n");
            var14.append("            }]  \r\n");
            var14.append("        });  \r\n");
            var14.append("    });  \r\n");
            var14.append("});");
            var14.append("</script> \r\n");
            this.out.println(var14.toString());
            return 6;
        } else {
            throw new ExtRuntimeException("您还未定义地图的 mapJson ...");
        }
    }

    private String a(List var1, String var2, String var3, String var4) {
        StringBuffer var5 = new StringBuffer("var dt = [ ");

        for(int var6 = 0; var6 < var1.size(); ++var6) {
            Map var7 = (Map)var1.get(var6);
            double var8 = ChartUtils.getKpiValue(var7, var2);
            if (var8 < this.minValue) {
                this.minValue = var8;
            }

            if (var8 > this.maxValue) {
                this.maxValue = var8;
            }

            String var10 = (String)var7.get("cmap_id");
            if (var10 == null || var10.length() == 0) {
                throw new ExtRuntimeException("未发现地域维。");
            }

            var5.append("{ \"code\": \"" + var10 + "\", \"value\": " + var8 + "}");
            if (var6 != var1.size() - 1) {
                var5.append(",");
            }
        }

        var5.append("];");
        return var5.toString();
    }
}
