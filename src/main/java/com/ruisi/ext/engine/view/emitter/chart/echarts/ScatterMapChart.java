//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.echarts;

import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import com.ruisi.ext.engine.view.emitter.chart.highcharts.MapChart;
import java.util.List;
import java.util.Map;

public class ScatterMapChart extends MapChart {
    public ScatterMapChart() {
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
        double[] var11 = ChartUtils.findMaxMin(this.dataList, var2);
        double var12 = var11[0];
        double var14 = var11[1];
        if (this.config.getGisMap() != null && this.config.getGisMap()) {
            var10 = "china";
        }

        String var16 = ChartUtils.crtChartDivStyle(var8, this.chart.getHeight(), this.chart.getAlign());
        this.out.println(" <div id=\"" + var9 + "\" style=\" " + var16 + " \"></div>");
        this.out.println(" <script type=\"text/javascript\">");
        this.out.println("$(function(){");
        this.out.println("$.get('../ext-res/js/chart/" + var10 + ".json', function (json) {");
        this.out.println("echarts.registerMap('" + var10 + "', json);");
        if (this.config.getGisMap() != null && this.config.getGisMap()) {
            this.out.println("$.get('../ext-res/js/chart/cityjwd.json', function(geoCoordMap){");
            this.out.println(" var convertData = function (name, value) {");
            this.out.println("\tvar res;");
            this.out.println("     var geoCoord = geoCoordMap[name];");
            this.out.println("     if (geoCoord) {");
            this.out.println("\t\t   res = geoCoord.concat(value)");
            this.out.println("     }");
            this.out.println("return res;");
            this.out.println("};");
        } else {
            this.out.println("var convertData=function(name, val){for(j=0; j<json.features.length; j++){if(json.features[j].properties.name == name){return [json.features[j].properties.cp[0],json.features[j].properties.cp[1], val];}}}");
        }

        this.out.println("var myChart = echarts.init(document.getElementById('" + var9 + "'));");
        this.out.println("var option = {");
        this.out.println("tooltip: {");
        this.out.println("\tformatter:function(params){ \n");
        this.out.println("\treturn '<b>" + this.config.getYdesc() + "</b><br/>'+params.name+'：' + params.value[2] +'<br/>'; \n");
        this.out.println("\t} \n");
        this.out.println("},");
        this.out.println(" visualMap: {");
        this.out.println(" type:'continuous',");
        this.out.println(" min: " + var12 * 0.9D + ",");
        this.out.println(" max: " + var14 * 1.1D + ",");
        this.out.println(" left: 'left',");
        this.out.println(" top: 'bottom',");
        this.out.println(" color: ['orangered','yellow','lightskyblue'],");
        this.out.println("  text: ['高','低'],   ");
        if (this.config.getShowLegend()) {
            this.out.println("\tshow:true,");
        } else {
            this.out.println("\tshow:false,");
        }

        this.out.println("  calculable: true");
        this.out.println(" },");
        this.out.println("geo: {");
        this.out.println("\tmap: '" + var10 + "',");
        this.out.println(" label: {");
        this.out.println("\t  emphasis: {");
        this.out.println("\t\t show: false");
        this.out.println("\t}");
        this.out.println(" },");
        this.out.println("  roam: true,");
        this.out.println(" itemStyle: {");
        this.out.println("\tnormal: {");
        this.out.println("\t\tareaColor: '#e6e1e1',");
        this.out.println("\t\tborderColor: '#fdfdfd'");
        this.out.println("\t},");
        this.out.println("\t emphasis: {");
        this.out.println("\t\tareaColor: '#c19a9a'");
        this.out.println("\t}");
        this.out.println("  }");
        this.out.println("},");
        this.out.println("series: [");
        this.out.println(" {");
        this.out.println("  name: '" + this.config.getYdesc() + "',");
        this.out.println("  type: 'scatter',");
        this.out.println("  coordinateSystem: 'geo',");
        this.out.println("  roam:true, ");
        this.out.println("  symbolSize: function(data){if(data){return bubbleSize(" + var14 + "," + var12 + ",data[2], 40);}else{return 10}},");
        this.out.println("  selectedMode : 'single',");
        this.out.println("  label: {");
        this.out.println("\t\t normal: { show: " + this.config.getShowLabel() + ",position: 'right', formatter:'{b}', textStyle:{color:'#737373'} },");
        this.out.println("\temphasis: { show: false }");
        this.out.println(" },");
        this.out.println(" itemStyle:{normal:{color: '#c44f53'}},");
        this.out.println(" data:" + this.a(this.dataList, var2, var3));
        this.out.println("  }");
        this.out.println("]");
        this.out.println("};");
        this.out.println("myChart.setOption(option);");
        if (this.config.getGisMap() != null && this.config.getGisMap()) {
            this.out.println("});");
        }

        this.out.println("});");
        this.out.println("});");
        this.out.println(" </script>");
        return 6;
    }

    private String a(List var1, String var2, String var3) {
        StringBuffer var4 = new StringBuffer("[ ");

        for(int var5 = 0; var5 < var1.size(); ++var5) {
            Map var6 = (Map)var1.get(var5);
            Object var7 = var6.get(this.chart.getXcol());
            double var8 = ChartUtils.getKpiValue(var6, var2);
            var4.append("{ \"name\": \"" + var7 + "\", \"value\": convertData('" + var7 + "'," + var8 + ")}");
            if (var5 != var1.size() - 1) {
                var4.append(",");
            }
        }

        var4.append("]");
        return var4.toString();
    }
}
