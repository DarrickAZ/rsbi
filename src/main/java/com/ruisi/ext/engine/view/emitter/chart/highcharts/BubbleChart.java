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

public class BubbleChart extends AbstractChartEmitter {
    public static String defcnt = "60";

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

    public int createChartJS(boolean var1) {
        this.initConfg();
        this.a();
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
        String var14 = this.config.getAction();
        this.AnalyseData(this.dataList, var2, var3, var4, var5, var6);
        if (var10 != null && "auto".equalsIgnoreCase(var10)) {
            int var15 = ChartUtils.autoWidth(this.xcolList);
            var10 = String.valueOf(var15);
            this.chart.setWidth(var10);
        }

        String var23 = ChartUtils.crtChartDivStyle(var10, this.chart.getHeight(), this.chart.getAlign());
        StringBuffer var16 = new StringBuffer();
        if (!var1) {
            var16.append(" <div id=\"" + var11 + "\" style=\" " + var23 + " \"></div>   \r\n");
            var16.append(" <script type=\"text/javascript\"> \t\r\n");
            var16.append(" var chart;  \t\r\n");
            var16.append("  \t   \r\n");
            var16.append(" \tchart = new Highcharts.Chart(");
        }

        var16.append("\t\t\t{ \t   \r\n");
        var16.append(" \t\tchart: {\t\t   \r\n");
        if (var1) {
            var16.append(ChartUtils.crtPicSize(this.chart.getWidth(), this.chart.getHeight()));
        }

        var16.append(" \t\t\trenderTo: '" + var11 + "',\t   \r\n");
        var16.append("\t\t\t\t type: 'bubble', plotBorderWidth:1\t\r\n");
        var16.append(" \t\t},\t   \r\n");
        var16.append(" \r\n ");
        var16.append(" \t\ttitle: {\t   \r\n");
        var16.append(" \t\t\ttext: '',\t   \r\n");
        var16.append(" \t\t\tstyle: {\t   \r\n");
        var16.append(" \t\t\t\tmargin: '10px 0 0 0' // center it\t   \r\n");
        var16.append(" \t\t\t}\t   \r\n");
        var16.append(" \t\t},\t   \r\n");
        var16.append(" \r\n ");
        var16.append("\t\t\txAxis: {\t   \r\n");
        if (var12 != null && var12.length() > 0) {
            var16.append("\t\t\t title: {enabled: true, text:'" + var12 + "'}, \r\n");
        } else {
            var16.append("\t\t\t title: {enabled: false}, \r\n");
        }

        var16.append("\t\t\t startOnTick: true, \r\n");
        var16.append("\t\t\t endOnTick: true, \r\n");
        var16.append("\t\t\t showLastLabel: true \r\n");
        var16.append("\t\t\t},\t   \r\n");
        var16.append("\t\t\tyAxis: {\t   \r\n");
        if (var13 != null && var13.length() > 0) {
            var16.append("\t\t\t title: {enabled: true, text:'" + var13 + "'} \r\n");
        } else {
            var16.append("\t\t\t title: {enabled: false} \r\n");
        }

        var16.append("\t\t\t},\t   \r\n");
        var16.append("\t\tplotOptions: {  \t   \r\n");
        var16.append("\t\t\tbubble: {\t   \r\n");
        String var18;
        if (var14 != null && !var14.equals("")) {
            var16.append("\t\t\t\tcursor: 'pointer',\t   \r\n");
            var16.append("\t\t\t\tpoint: {\t   \r\n");
            var16.append("\t\t\t\t\tevents: {\t   \r\n");
            var16.append("\t\t\t\t\t\tclick: function(e) {\t   \r\n");
            var16.append("\t\t\t\t\t\t\tvar yvalue = this.y;\t   \r\n");
            var16.append("\t\t\t\t\t\t\tvar svalue = this.series.name;\t   \r\n");
            var16.append("\t\t\t\t\t\t\tvar pos = {left:e.clientX, top:e.clientY};\t   \r\n");
            var16.append("\t\t\t\t\t\tvar xvalue = this.name;\t\r\n");
            Map var17 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
            var16.append(" var ys = " + JSONObject.fromObject(var17) + "; \r\n");
            var18 = (String)this.request.getAttribute("compId");
            Integer var19 = (Integer)this.request.getAttribute("xcolid");
            var16.append("\t\t\t\t\t\t\t" + var14 + "(ys[xvalue], xvalue, yvalue, svalue, pos, " + var18 + ", " + var19 + ") ;  \r\n");
            var16.append(" \r\n ");
            var16.append("\t\t\t\t\t\t}\t   \r\n");
            var16.append("\t\t\t\t\t}\t   \r\n");
            var16.append("\t\t\t\t},\t   \r\n");
        }

        var16.append("\t\t\tmarker: { \r\n");
        var16.append("\t\t\t\tradius: 5, states: {hover: { enabled: true, lineColor: 'rgb(100,100,100)'}} \r\n");
        var16.append("\t\t\t\t}, \r\n");
        var16.append("\t\t\tstates: { \r\n");
        var16.append("\t\t\t\thover: {marker: { enabled: false}} \r\n");
        var16.append("\t\t\t\t} \r\n");
        var16.append("\t\t\t}\t   \r\n");
        var16.append("\t\t},\t   \r\n");
        var16.append(" \r\n ");
        var16.append("\t\tlegend: {\t   \r\n");
        var16.append("\t\t\tenabled: " + this.config.getShowLabel() + ",\t   \r\n");
        var16.append("\t\t\tlayout: '" + var9 + "',\t   \r\n");
        if (var8.equals("righttop")) {
            var16.append("\t\t\talign: 'right',verticalAlign: 'top',");
        } else if (var8.equals("centerbottom")) {
            var16.append("\talign:'center',verticalAlign: 'bottom',");
        }

        var16.append("\tborderWidth:0\t   \r\n");
        var16.append("\t\t},\t   \r\n");
        String var24 = (String)((Map)this.dataList.get(0)).get(var6);
        var18 = (String)((Map)this.dataList.get(0)).get(this.config.getFormatCol2());
        String var25 = (String)((Map)this.dataList.get(0)).get(this.config.getFormatCol3());
        String var20 = (String)((Map)this.dataList.get(0)).get(this.config.getUnitCol());
        String var21 = (String)((Map)this.dataList.get(0)).get(this.config.getUnitCol2());
        String var22 = (String)((Map)this.dataList.get(0)).get(this.config.getUnitCol3());
        if (var20 == null) {
            var20 = var5 == null ? "" : var5;
        }

        if (var21 == null) {
            if (this.config.getUnitCol2() == null) {
                String var10000 = "";
            } else {
                this.config.getUnitCol2();
            }
        }

        if (var22 == null) {
            var22 = this.config.getUnitCol3() == null ? "" : this.config.getUnitCol3();
        }

        if (var24 == null) {
            var24 = var6 == null ? "" : var6;
        }

        if (var18 == null) {
            var18 = this.config.getFormatCol2() == null ? "" : this.config.getFormatCol2();
        }

        if (var25 == null) {
            var25 = this.config.getFormatCol3() == null ? "" : this.config.getFormatCol3();
        }

        var16.append("\ttooltip: { \n");
        var16.append("\tformatter:function(){ \n");
        var16.append("\treturn '<b>' + this.point.name + '</b><br/>'  + '" + var12 + "：' + formatNumber(this.x, '" + var18 + "')+'" + "'+'<br/>' " + " + '" + var13 + "：' + formatNumber(this.y, '" + var24 + "') + '" + "' + '<br/>' " + " + '气泡大小：' + formatNumber(this.point.z, '" + var25 + "') + '" + ChartUtils.writerUnit(this.chart.getRate3()) + var22 + "'; \n");
        var16.append("\t} \n");
        var16.append("\t},\t\n");
        var16.append(" \r\n ");
        var16.append("\t\tseries: [\t\t   \r\n");
        if (this.dataInfoList != null && this.dataInfoList.size() != 0) {
            var16.append(this.a(this.dataList, this.dataInfoList, var2, var3, this.chart.getY2col(), var4) + "\r\n");
        } else {
            var16.append(this.a(this.dataList, var2, var3, this.chart.getY2col()) + "\r\n");
        }

        var16.append("\t\t]\t   \r\n");
        var16.append("\t}");
        if (!var1) {
            var16.append(");\t   \r\n");
            var16.append(" \r\n ");
            var16.append("   \r\n");
            var16.append("</script>");
        }

        this.out.println(var16.toString());
        return 6;
    }

    protected void AnalyseData(List var1, String var2, String var3, String var4, String var5, String var6) {
        LinkedList var7 = new LinkedList();
        Iterator var9 = var1.iterator();

        while(var9.hasNext()) {
            Map var8 = (Map)var9.next();
            Object var10 = var8.get(var2);
            String var11 = (String)var8.get(var5);
            String var12 = (String)var8.get(var6);
            if (!this.xcolList.contains(var10) && this.xcolList.size() <= this.config.getXcnt_Num()) {
                this.xcolList.add(var10);
            }

            if (var4 != null) {
                String var13 = var8.get(var4).toString();
                if (!var7.contains(var13)) {
                    var7.add(var13);
                    HashMap var14 = new HashMap();
                    var14.put("scolValue", var13);
                    var14.put("unitValue", var11);
                    var14.put("formatValue", var12);
                    if (this.dataInfoList.size() <= LineChart.maxsercnt) {
                        this.dataInfoList.add(var14);
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
                    Object var19 = var15.get(this.chart.getY3col());
                    Object var20 = var15.get(this.chart.getXcol());
                    var12.append("{x:" + var18 + ",y:" + var17 + ",z:" + var19 + ",name:'" + var20 + "'},");
                    ++var13;
                    if (var13 >= this.config.getXcnt_Num()) {
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
            Object var13 = var10.get(this.chart.getY3col());
            Object var14 = var10.get(this.chart.getXcol());
            var7.append("{x:" + var12 + ",y:" + var11 + ",z:" + var13 + ", name:'" + (var14 == null ? "" : var14) + "'},");
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
