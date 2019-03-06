//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.highcharts;

import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter;
import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColorVO;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import net.sf.json.JSONObject;

import java.util.*;

public class ColumnChart extends AbstractChartEmitter {
    public static final int columnMaxcntDef = 60;
    protected int colorIndex = 0;

    public ColumnChart() {
    }

    @Override
    protected void initConfg() {
        super.initConfg();
        if (this.config.getXcnt() == null) {
            this.config.setXcnt(String.valueOf(60));
        }

    }

    private void a() {
        String var1 = this.config.getMargin();
        String var2 = this.chart.getY2col();
        String var3 = this.config.getLegendPosition();
        String var4 = this.config.getMarginRight();
        String var5 = this.config.getMarginLeft();
        if (var1 == null || var1.equals("")) {
            if (var2 != null && var2.length() > 0) {
                if ("centerbottom".equals(var3)) {
                    var1 = "10, " + (var4 != null ? var4 : "90") + ", 90, " + (var5 != null ? var5 : "90");
                } else {
                    var1 = "10, " + (var4 != null ? var4 : "90") + ", 50, " + (var5 != null ? var5 : "90");
                }
            } else if ("centerbottom".equals(var3)) {
                var1 = "10, " + (var4 != null ? var4 : "20") + ", 90, " + (var5 != null ? var5 : "90");
            } else {
                var1 = "10, " + (var4 != null ? var4 : "20") + ", 50, " + (var5 != null ? var5 : "90");
            }
        }

        this.config.setMargin(var1);
    }

    public int createChartJS(boolean var1) {
        this.initConfg();
        this.a();
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
            if (var15 == ChartUtils.chartMaxWidth) {
                this.config.setRouteXaxisLable("-30");
            }

            var11 = String.valueOf(var15);
            this.chart.setWidth(var11);
        }

        String var32 = "";
        if (this.dataInfoList != null && this.dataInfoList.size() != 0 && this.dataInfoList.size() != 1) {
            var32 = var32 + this.a(this.dataList, this.dataInfoList, var2, var3, var5);
            if (var4 != null && var4.length() > 0) {
                var32 = var32 + "," + this.a(this.dataList, this.dataInfoList, var2, var4, var5);
            }
        } else {
            var32 = var32 + this.a(this.dataList, var2, var3);
            if (var4 != null && var4.length() > 0) {
                var32 = var32 + "," + this.a(this.dataList, var2, var4);
            }
        }

        String var16 = ChartUtils.crtChartDivStyle(var11, this.chart.getHeight(), this.chart.getAlign());
        StringBuffer var17 = new StringBuffer();
        if (!var1) {
            var17.append(" <div id=\"" + var12 + "\" style=\" " + var16 + " \"></div>   \r\n");
            var17.append(" <script type=\"text/javascript\"> \t\r\n");
            var17.append(" var chart;  \t\r\n");
            var17.append("\t   \r\n");
            var17.append(" \tchart = new Highcharts.Chart(");
        }

        var17.append("{ \t   \r\n");
        var17.append(" \t\tchart: {\t\t   \r\n");
        if (var1) {
            var17.append(ChartUtils.crtPicSize(this.chart.getWidth(), this.chart.getHeight()));
        }

        var17.append(" \t\t\trenderTo: '" + var12 + "',\t   \t  \r\n");
        var17.append("\t\t\t\tdefaultSeriesType: 'column',  \r\n");
        var17.append(" \t\t\tmargin: [" + this.config.getMargin() + "]      \r\n");
        var17.append(" \t\t},\t   \r\n");
        var17.append(" \r\n ");
        var17.append(" \t\ttitle: {\t   \r\n");
        var17.append(" \t\t\ttext: '',\t   \r\n");
        var17.append(" \t\t\tstyle: {\t   \r\n");
        var17.append(" \t\t\t\tmargin: '5px 0 0 0' // center it\t   \r\n");
        var17.append(" \t\t\t}\t   \r\n");
        var17.append(" \t\t},\t   \r\n");
        var17.append(" \r\n ");
        var17.append("\t\t\txAxis: [{\t   \r\n");
        var17.append("\t\t\t\ttitle: {text: '" + var13 + "'}\t\t\r\n");
        if (!"none".equals(this.config.getTickInterval())) {
            var17.append("\t,\n\t\t\ttickInterval:" + this.config.getTickInterval() + "\r\n");
        }

        var17.append(",\n\t\t\t\tcategories: [ \t   \r\n");
        var17.append(this.setXcolDesc(this.xcolList) + "\t\r\n");
        var17.append(" \t\t\t]\t\t\r\n");
        var17.append(",      labels: {  \n");
        if (this.chart.getDateType() != null && this.chart.getDateType().length() > 0) {
            ChartUtils.formatDate(var17, this.chart.getDateType(), this.chart.getDateTypeFmt(), this.xcolList);
        }

        String var18 = this.config.getRouteXaxisLable();
        if (var18 != null && !"0".equals(var18)) {
            var17.append("\t\t\t\trotation: " + var18 + ",\t\n");
            var17.append("\t\t\t\talign: '" + (Integer.parseInt(var18) > 0 ? "left" : "right") + "',\n");
        }

        var17.append("enabled:true \n");
        var17.append("}\t\n");
        var17.append("\t\t\t}],\t   \r\n");
        var17.append(" \r\n ");
        var17.append("\t\t\tyAxis: [{ \t   \r\n");
        if (var8 != null && var8.length() > 0) {
            var17.append("min:" + var8 + ",");
        }

        var17.append("\t\t\t\tlabels: {\t   \r\n");
        Object var19 = ((Map)this.dataList.get(0)).get(var7);
        String var20 = var19 == null ? null : var19.toString();
        if (var20 == null) {
            var20 = var7;
        }

        if (var20 != null && var20.length() >= 0) {
            var17.append("\t\t\tformatter:function(){return formatNumber(this.value, '" + var20 + "');},  \r\n");
        }

        var17.append("\t\t    \t   style: {\t\t\t\t\r\n");
        var17.append("\t\t    \t   }\t\t\t\t\t\r\n");
        var17.append("\t\t\t\t},\t   \r\n");
        var17.append("         \ttitle: {text: '" + var14 + "'},\t\t\r\n");
        var17.append("\t\t  \t\tplotLines: [{\t   \r\n");
        var17.append("\t\t\t\t\tvalue: 0,\t\t\r\n");
        var17.append("\t\t\t\t\twidth: 1,\t\t\r\n");
        var17.append("\t\t\t\t\tcolor: '#808080'\t\r\n");
        var17.append("\t\t\t\t}]\t\t\t\t\t\r\n");
        var17.append("\t\t    }\t   \r\n");
        String var21 = this.config.getFormatCol2();
        String var23;
        if (var4 != null && var4.length() > 0) {
            var17.append(",{ \r\n");
            var17.append(" labels : {");
            Object var22 = ((Map)this.dataList.get(0)).get(var21);
            var23 = var22 == null ? null : var22.toString();
            if (var23 == null) {
                var23 = var21;
            }

            if (var23 != null && var23.length() >= 0 && !var1) {
                var17.append("\t\t\tformatter:function(){return formatNumber(this.value, '" + var23 + "');},  \r\n");
            }

            var17.append("\t\t    \t   style: {\t\t\t\t\r\n");
            var17.append("\t\t    \t   }\t\t\t\t\t\r\n");
            var17.append("\t\t\t\t},\t   \r\n");
            var17.append(" title: {text:'" + this.config.getY2desc() + "'}, \r\n");
            var17.append(" opposite: true");
            var17.append("} \r\n");
        }

        var17.append("\t\t  ],\t   \r\n");
        var17.append(" \r\n ");
        String var33 = (String)((Map)this.dataList.get(0)).get(var7);
        if (var33 == null) {
            var33 = var7;
        }

        var23 = (String)((Map)this.dataList.get(0)).get(var21);
        if (var23 == null) {
            var23 = var21;
        }

        Object var24 = ((Map)this.dataList.get(0)).get(this.config.getUnitCol());
        String var25 = var24 == null ? null : var24.toString();
        if (var25 == null) {
            var25 = this.config.getUnitCol();
        }

        if ("%".equals(var25)) {
            var25 = "";
        }

        var17.append("\t\ttooltip: {\t   \r\n");
        var17.append("\t\t\tformatter: function() {   \r\n");
        String var26 = "";
        if (this.dataInfoList != null && this.dataInfoList.size() > 0) {
            var26 = "'<b>'+ this.series.name +'</b><br/>' + \r\n";
        }

        var17.append("\t\t\t\treturn " + var26);
        if (var4 != null && var4.length() != 0) {
            var17.append("(this.series.type == 'column' ? ");
            var17.append("this.x +': '+ formatNumber(this.y, '" + (var33 == null ? "" : var33) + "') + '" + ChartUtils.writerUnit(this.chart.getRate()) + (var25 == null ? "" : var25) + "'");
            var17.append(":");
            var17.append("this.x +': '+ formatNumber(this.y, '" + (var23 == null ? "" : var23) + "') + '" + ChartUtils.writerUnit(this.chart.getRate2()) + (this.config.getUnitCol2() == null ? "" : this.config.getUnitCol2()) + "'");
            var17.append(");");
        } else {
            var17.append("\t\t\t\t\tthis.x +': '+ formatNumber(this.y, '" + (var33 == null ? "" : var33) + "') + '" + ChartUtils.writerUnit(this.chart.getRate()) + (var25 == null ? "" : var25) + "';\t   \r\n");
        }

        var17.append("\t\t\t}\t   \r\n");
        var17.append("\t\t},\t   \r\n");
        var17.append("\t\tplotOptions: {\t\t\t\r\n");
        var17.append("\t\t\tcolumn : {\t\t\r\n");
        var17.append("\t\t\t\tcursor: 'pointer',\t\r\n");
        var17.append("\t\t\t\tpoint: {\t\t\t\r\n");
        var17.append("\t\t\t\t\tevents: {\t\t\r\n");
        var17.append("\t\t\t\t\t\tclick: function(e) {\t \t\r\n");
        var17.append("\t\t\t\t\t\t\tvar xvalue = this.series.xAxis.categories[this.x];\t\r\n");
        var17.append("\t\t\t\t\t\t\tvar yvalue = this.y;\t\t\t\r\n");
        var17.append("\t\t\t\t\t\t\tvar svalue = this.series.name;\t\r\n");
        var17.append("\t\t\t\t\t\t\tvar pos = {left:e.clientX, top:e.clientY};\t   \r\n");
        String var27 = this.config.getAction();
        if (var27 != null && !var27.equals("")) {
            Map var28 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
            var17.append(" var ys = " + JSONObject.fromObject(var28) + "; \r\n");
            String var29 = (String)this.request.getAttribute("compId");
            Integer var30 = (Integer)this.request.getAttribute("xcolid");
            var17.append("\t\t\t\t\t\t\t" + var27 + "(ys[xvalue], xvalue, yvalue, svalue, pos, " + var29 + ", " + var30 + ") ;  \r\n");
        }

        if (this.chart.getLink() != null) {
            String var34 = this.chart.getLink().getLinkUrl();
            if (var34 != null && var34.length() != 0) {
                var17.append("url = \"" + (var34.startsWith("http://") ? var34 : "http://" + var34) + "\"; \r\n");
                var17.append("window.open(url); \r\n");
            } else {
                Map var35 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
                String[] var36 = this.chart.getLink().getUrl();
                String[] var31 = this.chart.getLink().getTargetId();
                var17.append(" var ys = " + JSONObject.fromObject(var35) + "; \r\n");
                var17.append("\t\tchartComp_Link('" + this.chart.getXcolDesc() + "', ys[xvalue], " + ChartUtils.array2string(var36) + ", '" + this.chart.getLink().getParams() + "', " + ChartUtils.array2string(var31) + "," + ChartUtils.array2string(this.chart.getLink().getType()) + "); ");
            }
        }

        var17.append("\t\t\t\t\t\t}\t\r\n");
        var17.append("\t\t\t\t\t}\t\t\r\n");
        var17.append("\t\t\t\t},\t\t\t\r\n");
        var17.append("\t\t\t\tshadow: false,\t\t\t\r\n");
        var17.append("\t\t\t\tpointPadding: 0.05,\t\t\r\n");
        var17.append("\t\t\t\tborderWidth: 0\t\t\t\r\n");
        var17.append("\t\t\t}\t\t\r\n");
        var17.append("\t\t},\t\t\t\r\n");
        var17.append("\t\tlegend: {\t\t\t\r\n");
        var17.append("\t\t\tenabled: " + this.config.getShowLegend() + ",  \r\n");
        var17.append("\t\t\tlayout: '" + var10 + "',\t   \r\n");
        if (this.config.getLegendPosition().equals("righttop")) {
            var17.append("\t\t\talign: 'right',verticalAlign: 'top',");
        } else if (this.config.getLegendPosition().equals("centerbottom")) {
            var17.append("\talign:'center',verticalAlign: 'bottom',");
        }

        var17.append("\tborderWidth:0\r\n");
        var17.append("\t\t},\t\t\t\r\n");
        var17.append(" \r\n ");
        var17.append("\t\tseries: [\t\t   \r\n");
        var17.append(var32 + " \t\r\n ");
        var17.append("\t\t]\t   \r\n");
        var17.append("\t}");
        if (!var1) {
            var17.append(");\t   \t\t\r\n");
            var17.append(" \t\t\t\r\n ");
            var17.append("  \t\t\r\n");
            var17.append("</script>");
        }

        this.out.println(var17.toString());
        return 6;
    }

    protected void AnalyseData(List var1, String var2, String var3, String var4, String var5, String var6) {
        LinkedList var7 = new LinkedList();
        Iterator var9 = var1.iterator();

        while(var9.hasNext()) {
            Map var8 = (Map)var9.next();
            Object var10 = var8.get(var2);
            Object var11 = var8.get(var4);
            if (!this.xcolList.contains(var10) && this.xcolList.size() <= this.config.getXcnt_Num()) {
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
            String var11 = "";
            if (var8 >= LineChart.maxsercnt) {
                var6 = var6.substring(0, var6.lastIndexOf(","));
                break;
            }

            int var12 = 0;

            for(int var13 = 0; var13 < this.xcolList.size(); ++var13) {
                Object var14 = this.xcolList.get(var13);
                Object var15 = ChartUtils.findRow(var3, var4, var5, var14, var10, var1);
                var11 = var11 + var15 + ",";
                ++var12;
                if (var12 >= this.config.getXcnt_Num()) {
                    break;
                }
            }

            var11 = var11.substring(0, var11.length() - 1);
            var6 = var6 + "{    \t\t\t\t\t\t \r\n  \tname: '" + var10 + "',     \r\n " + "\t\tcolor: '" + var7[this.colorIndex] + "',    \r\n ";
            ++this.colorIndex;
            if (this.chart.getY2col() != null && this.chart.getY2col().length() > 0 && var4.equals(this.chart.getY2col())) {
                var6 = var6 + "type:'spline',";
                var6 = var6 + "yAxis: 1,";
            } else {
                var6 = var6 + "type:'column',";
                if (this.chart.getY2col() != null && this.chart.getY2col().length() > 0) {
                    var6 = var6 + "yAxis: 0,";
                }
            }

            var6 = var6 + "\t\tdata: [" + var11 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t \r\n ";
            if (var8 != var2.size() - 1) {
                var6 = var6 + ",";
            }
        }

        return var6;
    }

    private String a(List var1, String var2, String var3) {
        String var4 = "";
        AbstractChartEmitter$ColorVO[] var5 = AbstractChartEmitter$ColorVO.values();
        String var6 = "";
        int var7 = 0;

        for(int var8 = 0; var8 < var1.size(); ++var8) {
            Map var9 = (Map)var1.get(var8);
            Object var10 = null;
            var10 = var9.get(var3);
            var6 = var6 + var10 + ",";
            ++var7;
            if (var7 >= this.config.getXcnt_Num()) {
                break;
            }
        }

        var6 = var6.substring(0, var6.length() - 1);
        var4 = var4 + "{    \t\t\t\t\t\t \r\n  \tname: '" + (var3.equals(this.chart.getYcol()) ? ChartUtils.replaeUnit(this.config.getYdesc()) : ChartUtils.replaeUnit(this.config.getY2desc())) + "',     \r\n " + "\t\tcolor: '" + var5[this.colorIndex] + "',    \r\n ";
        ++this.colorIndex;
        String var11 = this.chart.getY2col();
        if (var11 != null && var11.length() > 0 && var3.equals(var11)) {
            var4 = var4 + "type:'spline',";
            var4 = var4 + "yAxis: 1,";
        } else {
            var4 = var4 + "type:'column',";
            if (var11 != null && var11.length() > 0) {
                var4 = var4 + "yAxis: 0,";
            }
        }

        var4 = var4 + "\t\tdata: [" + var6 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t \r\n ";
        return var4;
    }

    protected String setXcolDesc(List var1) {
        if (this.chart.getXcol() != null && this.chart.getXcol().length() != 0) {
            String var2 = "";
            int var3 = var1.size();

            for(int var4 = 0; var4 < var1.size() && var4 < this.config.getXcnt_Num(); ++var4) {
                String var5 = "" + var1.get(var4);
                var2 = var2 + "\"" + var5 + "\"";
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
