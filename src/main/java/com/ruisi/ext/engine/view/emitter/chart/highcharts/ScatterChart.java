//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.highcharts;

import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter;
import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColorVO;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public class ScatterChart extends AbstractChartEmitter {
    public static final int scatterMaxcntDef = 50;

    public ScatterChart() {
    }

    protected void initConfig() {
        String var1 = this.chart.getWidth();
        if (var1 == null || var1.equals("")) {
            this.chart.setWidth("800");
        }

        if (this.chart.getHeight() == null || this.chart.getHeight().equals("")) {
            this.chart.setHeight("400");
        }

        if (this.config.getLegendLayout() == null || this.config.getLegendLayout().length() == 0) {
            this.config.setLegendLayout("horizontal");
        }

        if (this.config.getLegendPosition() == null || this.config.getLegendPosition().equals("")) {
            this.config.setLegendPosition("righttop");
        }

        if (this.config.getXcnt() == null || this.config.getXcnt().length() == 0) {
            this.config.setXcnt(String.valueOf(50));
        }

    }

    protected void initMargin() {
        String var1 = this.config.getMarginRight();
        String var2 = this.config.getMarginLeft();
        if (this.config.getMargin() == null || this.config.getMargin().length() == 0) {
            String var3 = "10, " + (var1 != null ? var1 : "10") + ", 40, " + (var2 != null ? var2 : "70");
            this.config.setMargin(var3);
        }

    }

    public int createChartJS(boolean var1) {
        this.initConfig();
        this.initMargin();
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
        this.AnalyseData(this.dataList, var2, var3, var4, var6, var5);
        if (var10 != null && "auto".equalsIgnoreCase(var10)) {
            int var12 = ChartUtils.autoWidth(this.xcolList);
            var10 = String.valueOf(var12);
            this.chart.setWidth(var10);
        }

        String var18 = ChartUtils.crtChartDivStyle(var10, this.chart.getHeight(), this.chart.getAlign());
        StringBuffer var13 = new StringBuffer();
        if (!var1) {
            var13.append(" <div id=\"" + var11 + "\" style=\" " + var18 + " \"></div>   \r\n");
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
        var13.append("\t\t\t\t type: 'scatter',plotBorderWidth:1\t\r\n");
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

        var13.append("\t\t\t},\t   \r\n");
        var13.append("\t\tplotOptions: {  \t   \r\n");
        var13.append("\t\t\tscatter: {\t   \r\n");
        String var15;
        if (this.config.getAction() != null && !this.config.getAction().equals("")) {
            var13.append("\t\t\t\tcursor: 'pointer',\t   \r\n");
            var13.append("\t\t\t\tpoint: {\t   \r\n");
            var13.append("\t\t\t\t\tevents: {\t   \r\n");
            var13.append("\t\t\t\t\t\tclick: function(e) {\t   \r\n");
            var13.append("\t\t\t\t\t\t\tvar yvalue = this.y;\t   \r\n");
            var13.append("\t\t\t\t\t\t\tvar svalue = this.series.name;\t   \r\n");
            var13.append("\t\t\t\t\t\t\tvar pos = {left:e.clientX, top:e.clientY};\t   \r\n");
            var13.append("\t\t\t\t\t\tvar xvalue = this.name;\t\r\n");
            Map var14 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
            var13.append(" var ys = " + JSONObject.fromObject(var14) + "; \r\n");
            var15 = (String)this.request.getAttribute("compId");
            Integer var16 = (Integer)this.request.getAttribute("xcolid");
            var13.append("\t\t\t\t\t\t\t" + this.config.getAction() + "(ys[xvalue], xvalue, yvalue, svalue, pos, " + var15 + ", " + var16 + ") ;  \r\n");
            var13.append(" \r\n ");
            var13.append("\t\t\t\t\t\t}\t   \r\n");
            var13.append("\t\t\t\t\t}\t   \r\n");
            var13.append("\t\t\t\t},\t   \r\n");
        }

        var13.append("\tdataLabels:{enabled:false}, \n");
        var13.append("\t\t\tmarker: { \r\n");
        var13.append("\t\t\t\tradius: 5, states: {hover: { enabled: true, lineColor: 'rgb(100,100,100)'}} \r\n");
        var13.append("\t\t\t\t}, \r\n");
        var13.append("\t\t\tstates: { \r\n");
        var13.append("\t\t\t\thover: {marker: { enabled: false}} \r\n");
        var13.append("\t\t\t\t} \r\n");
        var13.append("\t\t\t}\t   \r\n");
        var13.append("\t\t},\t   \r\n");
        var13.append(" \r\n ");
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
        String var19 = (String)((Map)this.dataList.get(0)).get(var5);
        var15 = (String)((Map)this.dataList.get(0)).get(this.config.getFormatCol2());
        String var20 = (String)((Map)this.dataList.get(0)).get(var6);
        String var17 = (String)((Map)this.dataList.get(0)).get(this.config.getUnitCol2());
        if (var20 == null) {
            var20 = var6 == null ? "" : var6;
        }

        if (var17 == null) {
            if (this.config.getUnitCol2() == null) {
                String var10000 = "";
            } else {
                this.config.getUnitCol2();
            }
        }

        if (var19 == null) {
            var19 = var5 == null ? "" : var5;
        }

        if (var15 == null) {
            var15 = this.config.getFormatCol2() == null ? "" : this.config.getFormatCol2();
        }

        var13.append("\ttooltip: { \n");
        var13.append("\tformatter:function(){ \n");
        var13.append("\treturn '<b>' + this.point.name + '</b><br/>'  + '" + this.config.getXdesc() + "：' + formatNumber(this.x, '" + var15 + "')+'<br/>' " + " + '" + this.config.getYdesc() + "：' + formatNumber(this.y, '" + var19 + "'); \n");
        var13.append("\t} \n");
        var13.append("\t},\t\n");
        var13.append(" \r\n ");
        var13.append("\t\tseries: [\t\t   \r\n");
        if (this.dataInfoList != null && this.dataInfoList.size() != 0) {
            var13.append(this.a(this.dataList, this.dataInfoList, var2, var3, this.chart.getY2col(), var4) + "\r\n");
        } else {
            var13.append(this.a(this.dataList, var2, var3, this.chart.getY2col()) + "\r\n");
        }

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

    protected void AnalyseData(List var1, String var2, String var3, String var4, String var5, String var6) {
        LinkedList var7 = new LinkedList();
        Iterator var9 = var1.iterator();

        while(var9.hasNext()) {
            Map var8 = (Map)var9.next();
            Object var10 = var8.get(var2);
            if (!this.xcolList.contains(var10) && this.xcolList.size() <= this.config.getXcnt_Num()) {
                this.xcolList.add(var10);
            }

            if (var4 != null) {
                String var11 = var8.get(var4).toString();
                if (!var7.contains(var11)) {
                    var7.add(var11);
                    HashMap var12 = new HashMap();
                    var12.put("scolValue", var11);
                    if (this.dataInfoList.size() <= LineChart.maxsercnt) {
                        this.dataInfoList.add(var12);
                    }
                }
            }
        }

    }

    private String a(List var1, List var2, String var3, String var4, String var5, String var6) {
        String var7 = "";
        AbstractChartEmitter$ColorVO[] var8 = AbstractChartEmitter$ColorVO.values();

        for(int var9 = 0; var9 < var2.size(); ++var9) {
            Map var10 = (Map)var2.get(var9);
            String var11 = (String)var10.get("scolValue");
            if (var9 >= LineChart.maxsercnt) {
                var7 = var7.substring(0, var7.lastIndexOf(","));
                break;
            }

            StringBuffer var12 = new StringBuffer();
            int var13 = 0;

            for(int var14 = 0; var14 < var1.size(); ++var14) {
                Map var15 = (Map)var1.get(var14);
                String var16 = var15.get(var6).toString();
                if (var11.equals(var16)) {
                    Object var17 = var15.get(var4);
                    Object var18 = var15.get(var5);
                    Object var19 = var15.get(this.chart.getXcol());
                    var12.append("{x:" + var18 + ",y:" + var17 + ",name:'" + var19 + "'},");
                    ++var13;
                    if (var13 == this.config.getXcnt_Num()) {
                        break;
                    }
                }
            }

            if (var12.lastIndexOf(",") >= 0) {
                var12.deleteCharAt(var12.lastIndexOf(","));
            }

            var7 = var7 + "{    \t\t\t\t\t\t \r\n  \tname: '" + var11 + "',     \r\n ";
            var7 = var7 + "\t color: '" + var8[var9] + "',\t   \t\t\t \r\n";
            var7 = var7 + "\t data: [" + var12 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t     \r\n ";
            if (var9 != var2.size() - 1) {
                var7 = var7 + ",";
            }
        }

        return var7;
    }

    private String a(List var1, String var2, String var3, String var4) {
        String var5 = "";
        AbstractChartEmitter$ColorVO[] var6 = AbstractChartEmitter$ColorVO.values();
        StringBuffer var7 = new StringBuffer();
        int var8 = 0;

        for(int var9 = 0; var9 < var1.size(); ++var9) {
            Map var10 = (Map)var1.get(var9);
            Object var11 = var10.get(var3);
            Object var12 = var10.get(var4);
            Object var13 = var10.get(this.chart.getXcol());
            var7.append("{x:" + var12 + ",y:" + var11 + ", name:'" + (var13 == null ? "" : var13) + "'},");
            ++var8;
            if (var8 >= this.config.getXcnt_Num()) {
                break;
            }
        }

        if (var7.lastIndexOf(",") >= 0) {
            var7.deleteCharAt(var7.lastIndexOf(","));
        }

        var5 = var5 + "{    \tname:'" + this.config.getXdesc() + "',\t\t\t\t\t \r\n ";
        var5 = var5 + "\t\tcolor: '" + var6[0] + "',\t   \t\t\t \r\n";
        var5 = var5 + "\t data: [" + var7 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t     \r\n ";
        return var5;
    }
}
