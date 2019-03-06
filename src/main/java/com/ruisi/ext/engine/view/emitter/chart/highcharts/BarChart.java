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

public class BarChart extends AbstractChartEmitter {
    public static final int barMaxcntDef = 31;

    public BarChart() {
    }

    protected void initConfg() {
        super.initConfg();
        if (this.config.getXcnt() == null) {
            this.config.setXcnt(String.valueOf(31));
        }

    }

    private void a() {
        String var1 = this.config.getMargin();
        String var2 = this.config.getLegendPosition();
        String var3 = this.config.getMarginRight();
        String var4 = this.config.getMarginLeft();
        if (var1 == null || var1.equals("")) {
            if ("centerbottom".equals(var2)) {
                var1 = "42, " + (var3 != null ? var3 : "10") + ", 60, " + (var4 != null ? var4 : "65");
            } else {
                var1 = "42, " + (var3 != null ? var3 : "10") + ", 40, " + (var4 != null ? var4 : "65");
            }
        }

        this.config.setMargin(var1);
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
        this.AnalyseData(this.dataList, var2, var3, var4, var5, var6);
        String var10 = this.chart.getWidth();
        if (var10 != null && "auto".equalsIgnoreCase(var10)) {
            int var11 = ChartUtils.autoWidth(this.xcolList);
            if (var11 == ChartUtils.chartMaxWidth) {
                int var12 = Integer.parseInt(this.chart.getHeight());
                var12 = (int)((double)var12 * 1.5D);
                this.chart.setHeight(String.valueOf(var12));
            }

            var10 = String.valueOf(var11);
            this.chart.setWidth(var10);
        }

        String var23;
        if (this.dataInfoList != null && this.dataInfoList.size() != 0) {
            var23 = this.a(this.dataList, this.dataInfoList, var2, var3, var4);
        } else {
            var23 = this.a(this.dataList, var2, var3);
        }

        String var24 = ChartUtils.crtChartDivStyle(var10, this.chart.getHeight(), this.chart.getAlign());
        StringBuffer var13 = new StringBuffer();
        if (!var1) {
            var13.append(" <div id=\"" + this.chart.getId() + "\" style=\" " + var24 + " \"></div> ");
            var13.append(" <script type=\"text/javascript\"> \t\r\n");
            var13.append(" var chart;  \t\r\n");
            var13.append(" jQuery(document).ready(function() { \t   \r\n");
            var13.append(" \tchart = new Highcharts.Chart(");
        }

        var13.append("\t\t{ \t   \r\n");
        var13.append(" \t\tchart: {\t\t   \r\n");
        var13.append(" \t\t\trenderTo: '" + this.chart.getId() + "',\t   \t  \r\n");
        var13.append("\t\t\t\tdefaultSeriesType: 'bar',  \r\n");
        if (var1) {
            var13.append(ChartUtils.crtPicSize(this.chart.getWidth(), this.chart.getHeight()));
        }

        var13.append(" \t\t\tmargin: [" + this.config.getMargin() + "]      \r\n");
        var13.append(" \t\t},\t   \r\n");
        var13.append(" \r\n ");
        var13.append(" \t\ttitle: {\t   \r\n");
        var13.append(" \t\t\ttext: '',\t   \r\n");
        var13.append(" \t\t\tstyle: {\t   \r\n");
        var13.append(" \t\t\t\tmargin: '5px 0 0 0' // center it\t   \r\n");
        var13.append(" \t\t\t}\t   \r\n");
        var13.append(" \t\t},\t   \r\n");
        var13.append(" \r\n ");
        var13.append("\t\t\txAxis: {\t   \r\n");
        var13.append("\t\t\t\tcategories: [ \t   \r\n");
        var13.append(this.setXcolDesc(this.xcolList) + " \r\n");
        var13.append("\t\t\t\t],\t   \r\n");
        if (!"none".equals(this.config.getTickInterval())) {
            var13.append("\t\t\t\ttickInterval:" + this.config.getTickInterval() + ",\r\n");
        }

        var13.append("      labels: {  \n");
        if (this.chart.getDateType() != null && this.chart.getDateType().length() > 0) {
            ChartUtils.formatDate(var13, this.chart.getDateType(), this.chart.getDateTypeFmt(), this.xcolList);
        }

        var13.append("enabled:true \n");
        var13.append("},\t\n");
        var13.append("\ttitle: {text: '" + this.config.getXdesc() + "'}\t\t\r\n");
        var13.append("\t\t\t},\t   \r\n");
        var13.append(" \r\n ");
        var13.append(" \r\n ");
        var13.append("\t\t\tyAxis: [{ \t   \r\n");
        if (var7 != null && var7.length() > 0) {
            var13.append("min:" + var7 + ",");
        }

        var13.append("\t\t\t\tlabels: {\t   \r\n");
        Object var14 = ((Map)this.dataList.get(0)).get(var6);
        String var15 = var14 == null ? null : var14.toString();
        if (var15 == null) {
            var15 = var6;
        }

        if (var15 != null && var15.length() >= 0 && !var1) {
            var13.append("\t\t\tformatter:function(){return formatNumber(this.value, '" + var15 + "');},  \r\n");
        }

        var13.append("\t\t\t\t\tstyle: {\t   \r\n");
        var13.append("\t\t\t\t\t}\t   \r\n");
        var13.append("\t\t\t\t},\t   \r\n");
        var13.append("         \ttitle: {text: '" + this.config.getYdesc() + "'}\t\t\r\n");
        var13.append("\t\t\t} \t   \r\n");
        var13.append("\t\t],\t   \r\n");
        var13.append(" \r\n ");
        var13.append("\t\t\t\ttitle: {\t   \r\n");
        var13.append("\t\t\t\t\ttext: ''\t   \r\n");
        var13.append("\t\t\t\t},\t   \r\n");
        String var16 = (String)((Map)this.dataList.get(0)).get(var6);
        if (var16 == null) {
            var16 = var6;
        }

        Object var17 = ((Map)this.dataList.get(0)).get(this.config.getUnitCol());
        String var18 = var17 == null ? null : var17.toString();
        if (var18 == null) {
            var18 = this.config.getUnitCol();
        }

        if ("%".equals(var18)) {
            var18 = "";
        }

        var13.append("\t\ttooltip: {\t   \r\n");
        var13.append("\t\t\tformatter: function() {\t   \r\n");
        var13.append("\t\t\t\treturn '<b>'+ this.series.name +'</b><br/>'+\t   \r\n");
        var13.append("\t\t\t\t\tthis.x +': '+ formatNumber(this.y, '" + (var16 == null ? "" : var16) + "') + '" + ChartUtils.writerUnit(this.chart.getRate()) + (var18 == null ? "" : var18) + "';\t   \r\n");
        var13.append("\t\t\t}\t   \r\n");
        var13.append("\t\t},\t   \r\n");
        var13.append("\t\tplotOptions: {\t\t\t\r\n");
        var13.append("\t\t\tbar : {\t\t\r\n");
        var13.append("\t\t\t\tcursor: 'pointer',\t\r\n");
        var13.append("\t\t\t\tpoint: {\t\t\t\r\n");
        var13.append("\t\t\t\t\tevents: {\t\t\r\n");
        var13.append("\t\t\t\t\t\tclick: function(e) {\t \t\r\n");
        var13.append("\t\t\t\t\t\t\tvar xvalue = this.series.xAxis.categories[this.x];\t\r\n");
        var13.append("\t\t\t\t\t\t\tvar yvalue = this.y;\t\t\t\r\n");
        var13.append("\t\t\t\t\t\t\tvar svalue = this.series.name;\t\r\n");
        var13.append("\t\t\t\t\t\t\tvar pos = {left:e.clientX, top:e.clientY};\t   \r\n");
        if (this.config.getAction() != null && !this.config.getAction().equals("")) {
            Map var19 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
            var13.append(" var ys = " + JSONObject.fromObject(var19) + "; \r\n");
            String var20 = (String)this.request.getAttribute("compId");
            Integer var21 = (Integer)this.request.getAttribute("xcolid");
            var13.append("\t\t\t\t\t\t\t" + this.config.getAction() + "(ys[xvalue], xvalue, yvalue, svalue, pos, " + var20 + ", " + var21 + ") ;  \r\n");
        }

        if (this.chart.getLink() != null) {
            String var25 = this.chart.getLink().getLinkUrl();
            if (var25 != null && var25.length() != 0) {
                var13.append("url = \"" + (var25.startsWith("http://") ? var25 : "http://" + var25) + "\"; \r\n");
                var13.append("window.open(url); \r\n");
            } else {
                Map var26 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
                String[] var27 = this.chart.getLink().getUrl();
                String[] var22 = this.chart.getLink().getTargetId();
                var13.append(" var ys = " + JSONObject.fromObject(var26) + "; \r\n");
                var13.append("\t\tchartComp_Link('" + this.chart.getXcolDesc() + "', ys[xvalue], " + ChartUtils.array2string(var27) + ", '" + this.chart.getLink().getParams() + "', " + ChartUtils.array2string(var22) + "," + ChartUtils.array2string(this.chart.getLink().getType()) + "); ");
            }
        }

        var13.append("\t\t\t\t\t\t}\t\r\n");
        var13.append("\t\t\t\t\t}\t\t\r\n");
        var13.append("\t\t\t\t},\t\t\t\r\n");
        var13.append("\t\t\t\tshadow: false,\t\t\t\r\n");
        var13.append("\t\t\t\tpointPadding: 0.05,\t\t\r\n");
        var13.append("\t\t\t\tborderWidth: 0\t\t\t\r\n");
        var13.append("\t\t\t}\t\t\r\n");
        var13.append("\t\t},\t\t\t\r\n");
        var13.append("\t\tlegend: {\t\t\t\r\n");
        var13.append("\t\t\tenabled: " + this.config.getShowLegend() + ",  \r\n");
        var13.append("\t\t\tlayout: '" + var9 + "',\t   \r\n");
        if (var8.equals("righttop")) {
            var13.append("\t\t\talign: 'right',verticalAlign: 'top',");
        } else if (var8.equals("centerbottom")) {
            var13.append("\talign:'center',verticalAlign: 'bottom',");
        }

        var13.append("\tborderWidth:0\r\n");
        var13.append("\t\t},\t\t\t\r\n");
        var13.append(" \r\n ");
        var13.append("\t\tseries: [\t\t   \r\n");
        var13.append(var23 + " \t\r\n ");
        var13.append("\t\t]\t   \r\n");
        var13.append("\t}");
        if (!var1) {
            var13.append(");\t   \t\t\r\n");
            var13.append(" \t\t\t\r\n ");
            var13.append("});\t   \t\t\r\n");
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
            String var11 = (String)var8.get(var4);
            if (!this.xcolList.contains(var10)) {
                this.xcolList.add(var10);
            }

            if (var4 != null && !var7.contains(var11)) {
                var7.add(var11);
                HashMap var12 = new HashMap();
                var12.put("scolValue", var11);
                if (this.dataInfoList.size() <= LineChart.maxsercnt) {
                    this.dataInfoList.add(var12);
                }
            }
        }

    }

    private String a(List var1, List var2, String var3, String var4, String var5) {
        String var6 = "";
        AbstractChartEmitter$ColorVO[] var7 = AbstractChartEmitter$ColorVO.values();

        for(int var8 = 0; var8 < var2.size(); ++var8) {
            Map var9 = (Map)var2.get(var8);
            Object var10 = var9.get("scolValue");
            if (var8 >= LineChart.maxsercnt) {
                var6 = var6.substring(0, var6.lastIndexOf(","));
                break;
            }

            StringBuffer var11 = new StringBuffer();
            int var12 = 0;

            for(int var13 = 0; var13 < this.xcolList.size(); ++var13) {
                Object var14 = this.xcolList.get(var13);
                Object var15 = ChartUtils.findRow(var3, var4, var5, var14, var10, var1);
                var11.append(var15 + ",");
                ++var12;
                if (var12 >= this.config.getXcnt_Num()) {
                    break;
                }
            }

            if (var11.lastIndexOf(",") >= 0) {
                var11.deleteCharAt(var11.lastIndexOf(","));
            }

            var6 = var6 + "{    \t\t\t\t\t\t \r\n  \tname: '" + var10 + "',     \r\n ";
            var6 = var6 + "\t\tcolor: '" + var7[var8] + "',    \r\n ";
            var6 = var6 + "\t data: [" + var11 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t     \r\n ";
            if (var8 != var2.size() - 1) {
                var6 = var6 + ",";
            }
        }

        return var6;
    }

    private String a(List var1, String var2, String var3) {
        String var4 = "";
        AbstractChartEmitter$ColorVO[] var5 = AbstractChartEmitter$ColorVO.values();
        StringBuffer var6 = new StringBuffer();
        int var7 = 0;

        for(int var8 = 0; var8 < this.xcolList.size(); ++var8) {
            Map var9 = (Map)var1.get(var8);
            Object var10 = null;
            var10 = var9.get(var3);
            var6.append(var10 + ",");
            ++var7;
            if (var7 >= this.config.getXcnt_Num()) {
                break;
            }
        }

        if (var6.lastIndexOf(",") >= 0) {
            var6.deleteCharAt(var6.lastIndexOf(","));
        }

        var4 = var4 + "{    \t\t\t\t\t\t \r\n  \tname: '" + this.config.getXdesc() + "',     \r\n ";
        var4 = var4 + "\t\tcolor: '" + var5[0] + "',    \r\n ";
        var4 = var4 + "\t data: [" + var6 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t     \r\n ";
        return var4;
    }

    protected String setXcolDesc(List var1) {
        if (this.chart.getXcol() != null && this.chart.getXcol().length() != 0) {
            String var2 = "";
            int var3 = var1.size();

            for(int var4 = 0; var4 < var1.size() && var4 < this.config.getXcnt_Num(); ++var4) {
                var2 = var2 + "\"" + var1.get(var4) + "\"";
                if (var4 != var3 - 1 && var4 != this.config.getXcnt_Num() - 1) {
                    var2 = var2 + ",";
                }
            }

            return var2;
        } else {
            return "'合计'";
        }
    }
}
