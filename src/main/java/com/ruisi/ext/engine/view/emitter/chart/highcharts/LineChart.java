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

public class LineChart extends AbstractChartEmitter {
    public static final int maxcntDef = 60;
    public static final int maxsercntDef = 6;
    public static int maxsercnt = 6;
    protected int colorIndex = 0;

    public LineChart() {
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

    @Override
    public int createChartJS(boolean var1) {
        this.initConfg();
        this.a();
        this.AnalyseData(this.dataList, this.chart.getXcol(), this.chart.getYcol(), this.chart.getScol(), this.config.getUnitCol(), this.config.getFormatCol());
        if (this.chart.getWidth() != null && "auto".equalsIgnoreCase(this.chart.getWidth())) {
            int var2 = ChartUtils.autoWidth(this.xcolList);
            if (var2 == ChartUtils.chartMaxWidth) {
                this.config.setRouteXaxisLable("-30");
            }

            this.chart.setWidth(String.valueOf(var2));
        }

        String var15 = ChartUtils.crtChartDivStyle(this.chart.getWidth(), this.chart.getHeight(), this.chart.getAlign());
        StringBuffer var3 = new StringBuffer();
        if (!var1) {
            var3.append(" <div id=\"" + this.chart.getId() + "\" style=\" " + var15 + " \"></div> \r\n");
            var3.append(" <script type=\"text/javascript\"> \t\r\n");
            var3.append(" var chart;  \t\r\n");
            var3.append("  \t   \r\n");
            var3.append(" \tchart = new Highcharts.Chart(");
        }

        var3.append("\t\t\t{ \t   \r\n");
        var3.append(" \t\tchart: {\t\t   \r\n");
        if (var1) {
            var3.append(ChartUtils.crtPicSize(this.chart.getWidth(), this.chart.getHeight()));
        }

        var3.append(" \t\t\trenderTo: '" + this.chart.getId() + "',\t   \r\n");
        var3.append(" \t\t\tmargin: [" + this.config.getMargin() + "],    \r\n");
        var3.append(" \t\t\tzoomType: 'None'\t   \r\n");
        var3.append(" \t\t},\t   \r\n");
        var3.append(" \r\n ");
        var3.append(" \t\ttitle: {\t   \r\n");
        var3.append(" \t\t\ttext: '',\t   \r\n");
        var3.append(" \t\t\tstyle: {\t   \r\n");
        var3.append(" \t\t\t\tmargin: '10px 0 0 0' // center it\t   \r\n");
        var3.append(" \t\t\t}\t   \r\n");
        var3.append(" \t\t},\t   \r\n");
        var3.append(" \r\n ");
        var3.append("\t\t\txAxis: [{\t   \r\n");
        var3.append("\t\t\t\tcategories: [ \t   \r\n");
        var3.append(this.setXcolDesc(this.xcolList) + " \r\n");
        var3.append("\t\t\t\t],\t   \r\n");
        if (!"none".equals(this.config.getTickInterval())) {
            var3.append("\t\t\t\ttickInterval:" + this.config.getTickInterval() + ",\r\n");
        }

        var3.append("      labels: {  \n");
        if (this.chart.getDateType() != null && this.chart.getDateType().length() > 0) {
            ChartUtils.formatDate(var3, this.chart.getDateType(), this.chart.getDateTypeFmt(), this.xcolList);
        }

        if (this.config.getRouteXaxisLable() != null && !"0".equals(this.config.getRouteXaxisLable())) {
            var3.append("\t\t\t\trotation: " + this.config.getRouteXaxisLable() + ",\t\n");
            var3.append("\t\t\t\talign: '" + (Integer.parseInt(this.config.getRouteXaxisLable()) > 0 ? "left" : "right") + "',\n");
        }

        var3.append("enabled:true \n");
        var3.append("},\t\n");
        var3.append("\ttitle: {text: '" + this.config.getXdesc() + "'}\t\t\r\n");
        var3.append("\t\t\t}],\t   \r\n");
        var3.append(" \r\n ");
        var3.append("\t\t\tyAxis: [{ \t   \r\n");
        if (this.config.getYmin() != null && this.config.getYmin().length() > 0) {
            var3.append("min:" + this.config.getYmin() + ",");
        }

        var3.append("\t\t\t\tlabels: {\t   \r\n");
        Object var4 = ((Map)this.dataList.get(0)).get(this.config.getFormatCol());
        String var5 = var4 == null ? null : var4.toString();
        if (var5 == null) {
            var5 = this.config.getFormatCol();
        }

        if (var5 != null && var5.length() >= 0 && !var1) {
            var3.append("\tformatter:function(){return formatNumber(this.value, '" + var5 + "');},  \r\n");
        }

        var3.append("\t\t\t\t\tstyle: {\t   \r\n");
        var3.append("\t\t\t\t\t}\t   \r\n");
        var3.append("\t\t\t\t},\t   \r\n");
        var3.append("         \ttitle: {text: '" + this.config.getYdesc() + "'}\t\t\r\n");
        var3.append("\t\t\t} \t   \r\n");
        String var7;
        if (this.chart.getY2col() != null && this.chart.getY2col().length() > 0) {
            var3.append(",{ \r\n");
            var3.append(" labels : {");
            Object var6 = ((Map)this.dataList.get(0)).get(this.config.getFormatCol2());
            var7 = var6 == null ? null : var6.toString();
            if (var7 == null) {
                var7 = this.config.getFormatCol2();
            }

            if (var7 != null && var7.length() >= 0 && !var1) {
                var3.append("\tformatter:function(){return formatNumber(this.value, '" + var7 + "'); },  \r\n");
            }

            var3.append("\t\t\t\t\tstyle: {\t   \r\n");
            var3.append("\t\t\t\t\t}\t   \r\n");
            var3.append("\t\t\t\t},\t   \r\n");
            var3.append(" title: {text:'" + this.config.getY2desc() + "'}, \r\n");
            var3.append(" opposite: true");
            var3.append("} \r\n");
        }

        var3.append("\t\t],\t   \r\n");
        var3.append(" \r\n ");
        String var16 = (String)((Map)this.dataList.get(0)).get(this.config.getFormatCol());
        if (var16 == null) {
            var16 = this.config.getFormatCol();
        }

        var7 = (String)((Map)this.dataList.get(0)).get(this.config.getFormatCol2());
        if (var7 == null) {
            var7 = this.config.getFormatCol2();
        }

        Object var8 = ((Map)this.dataList.get(0)).get(this.config.getUnitCol());
        String var9 = var8 == null ? null : var8.toString();
        if (var9 == null) {
            var9 = this.config.getUnitCol();
        }

        if ("%".equals(var9)) {
            var9 = "";
        }

        var3.append("\t\ttooltip: {\t   \r\n");
        var3.append("\t\t\tformatter: function() {\t   \r\n");
        String var10 = "";
        if (this.chart.getY2col() != null && this.chart.getY2col().length() != 0) {
            var10 = "'<b>'+ this.series.name +'</b><br/>' + \r\n";
        } else if (this.dataInfoList != null && this.dataInfoList.size() > 0) {
            var10 = "'<b>'+ this.series.name +'</b><br/>' + \r\n";
        }

        var3.append("\t\t\t\treturn " + var10);
        String var12;
        if (this.chart.getY2col() != null && this.chart.getY2col().length() != 0) {
            Object var11 = ((Map)this.dataList.get(0)).get(this.config.getUnitCol2());
            var12 = var11 == null ? null : var11.toString();
            if (var12 == null) {
                var12 = this.config.getUnitCol2();
            }

            if ("%".equals(var12)) {
                var12 = "";
            }

            var3.append("(this.series.index == 0 ? ");
            var3.append("this.x +': '+ formatNumber(this.y, '" + (var16 == null ? "" : var16) + "') + '" + ChartUtils.writerUnit(this.chart.getRate()) + (var9 == null ? "" : var9) + "'");
            var3.append(":");
            var3.append("this.x +': '+ formatNumber(this.y, '" + (var7 == null ? "" : var7) + "') + '" + ChartUtils.writerUnit(this.chart.getRate2()) + (var12 == null ? "" : var12) + "'");
            var3.append(");");
        } else {
            var3.append("\t\t\t\t\tthis.x +': '+ formatNumber(this.y, '" + (var16 == null ? "" : var16) + "') + '" + ChartUtils.writerUnit(this.chart.getRate()) + (var9 == null ? "" : var9) + "';\t   \r\n");
        }

        var3.append("\t\t\t}\t   \r\n");
        var3.append("\t\t},\t   \r\n");
        var3.append(" \r\n ");
        var3.append("\t\tplotOptions: {  \t   \r\n");
        var3.append("\t\t\tspline: {\t   \r\n");
        var3.append("\t\t\t\tcursor: 'pointer',\t   \r\n");
        var3.append("\t\t\t\tpoint: {\t   \r\n");
        var3.append("\t\t\t\t\tevents: {\t   \r\n");
        var3.append("\t\t\t\t\t\tclick: function(e) {\t   \r\n");
        var3.append("\t\t\t\t\t\t\tvar yvalue = this.y;\t   \r\n");
        var3.append("\t\t\t\t\t\t\tvar svalue = this.series.name;\t   \r\n");
        var3.append("\t\t\t\t\t\t\tvar pos = {left:e.clientX, top:e.clientY};\t   \r\n");
        var3.append("\t\t\t\t\t\tvar xvalue = this.series.xAxis.categories[this.x];\t\r\n");
        if (this.config.getAction() != null && !this.config.getAction().equals("")) {
            Map var17 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
            var3.append(" var ys = " + JSONObject.fromObject(var17) + "; \r\n");
            var12 = (String)this.request.getAttribute("compId");
            Integer var13 = (Integer)this.request.getAttribute("xcolid");
            var3.append("\t\t\t\t\t\t\t" + this.config.getAction() + "(ys[xvalue], xvalue, yvalue, svalue, pos, " + var12 + ", " + var13 + ") ;  \r\n");
            var3.append(" \r\n ");
        }

        if (this.chart.getLink() != null) {
            String var18 = this.chart.getLink().getLinkUrl();
            if (var18 != null && var18.length() != 0) {
                var3.append("url = \"" + (var18.startsWith("http://") ? var18 : "http://" + var18) + "\"; \r\n");
                var3.append("window.open(url); \r\n");
            } else {
                Map var20 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
                String[] var19 = this.chart.getLink().getUrl();
                String[] var14 = this.chart.getLink().getTargetId();
                var3.append(" var ys = " + JSONObject.fromObject(var20) + "; \r\n");
                var3.append("\t\tchartComp_Link('" + this.chart.getXcolDesc() + "', ys[xvalue], " + ChartUtils.array2string(var19) + ", '" + this.chart.getLink().getParams() + "', " + ChartUtils.array2string(var14) + "," + ChartUtils.array2string(this.chart.getLink().getType()) + "); ");
            }
        }

        var3.append("\t\t\t\t\t\t}\t   \r\n");
        var3.append("\t\t\t\t\t}\t   \r\n");
        var3.append("\t\t\t\t}\t   \r\n");
        var3.append("\t\t\t},\t   \r\n");
        var3.append("\t\t\t\tseries: {   \r\n");
        var3.append("\t\t\t\t\tmarker: {\t\t\t   \r\n");
        var3.append("\t\t\t\t\t\tenabled: " + this.config.getMarkerEnabled() + ",  \r\n");
        var3.append("\t\t\t\t\t\tlineWidth: 0,\t   \r\n");
        var3.append("\t\t\t\t\t\tradius: 4,\t   \r\n");
        var3.append("\t\t\t\t\t\tlineColor: '#FFFFFF'\t   \r\n");
        var3.append("\t\t\t\t\t},\t\t   \r\n");
        var3.append("\t\t\t\t\tstates: { \t\t   \r\n");
        var3.append("\t\t\t\t\t\thover: {\t\t   \r\n");
        var3.append("\t\t\t\t\t\t\tmarker: {\t   \r\n");
        var3.append("\t\t\t\t\t\t\t\tenabled: true,   \r\n");
        var3.append("\t\t\t\t\t\t\t\tsymbol: 'auto',   \r\n");
        var3.append("\t\t\t\t\t\t\t\tradius: " + (this.config.getMarkerEnabled() ? 5 : 4) + ",   \r\n");
        var3.append("\t\t\t\t\t\t\t\tlineWidth: 1   \r\n");
        var3.append("\t\t\t\t\t\t\t}   \r\n");
        var3.append("\t\t\t\t\t\t}\t   \r\n");
        var3.append("\t\t\t\t\t}\t\t   \r\n");
        var3.append("\t\t\t\t}\t\t\t   \r\n");
        var3.append("\t\t},\t   \r\n");
        var3.append(" \r\n ");
        var3.append("\t\tlegend: {\t   \r\n");
        var3.append("\t\t\tenabled: " + this.config.getShowLegend() + ",\t   \r\n");
        var3.append("\t\t\tlayout: '" + this.config.getLegendLayout() + "',\t   \r\n");
        if (this.config.getLegendLayout().equals("righttop")) {
            var3.append("\t\t\talign: 'right',verticalAlign: 'top',");
        } else if (this.config.getLegendLayout().equals("centerbottom")) {
            var3.append("\talign:'center',verticalAlign: 'bottom',");
        }

        var3.append("\tborderWidth:0\r\n");
        var3.append("\t\t},\t   \r\n");
        var3.append(" \r\n ");
        var3.append("\t\tseries: [\t\t   \r\n");
        if (this.dataInfoList != null && this.dataInfoList.size() != 0 && this.dataInfoList.size() != 1) {
            var3.append(this.a(this.dataList, this.dataInfoList, this.chart.getXcol(), this.chart.getYcol(), this.chart.getScol()) + "\r\n");
        } else {
            var3.append(this.a(this.dataList, this.chart.getXcol(), this.chart.getYcol()) + "\r\n");
            if (this.chart.getY2col() != null && this.chart.getY2col().length() > 0) {
                var3.append(",");
                var3.append(this.a(this.dataList, this.chart.getXcol(), this.chart.getY2col()));
            }
        }

        var3.append("\t\t]\t   \r\n");
        var3.append("\t}");
        if (!var1) {
            var3.append(");\t   \r\n");
            var3.append(" \r\n ");
            var3.append("   \r\n");
            var3.append("</script>");
        }

        this.out.println(var3.toString());
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
                Object var11 = var8.get(var4);
                if (!var7.contains(var11)) {
                    var7.add(var11);
                    HashMap var12 = new HashMap();
                    var12.put("scolValue", var11);
                    if (this.dataInfoList.size() <= maxsercnt) {
                        this.dataInfoList.add(var12);
                    }
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
            if (var8 >= maxsercnt) {
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

            var6 = var6 + "{    \t\t\t\t\t\t \r\n  \tname: '" + var10 + "',     \r\n " + "\t\tcolor: '" + var7[this.colorIndex] + "',    \r\n ";
            ++this.colorIndex;
            if (this.chart.getY2col() != null && this.chart.getY2col().length() > 0 && var4.equals(this.chart.getY2col())) {
                var6 = var6 + "type:'" + (this.config.getSpline() ? "spline" : "line") + "',";
                var6 = var6 + "yAxis: 1,";
            } else {
                var6 = var6 + "type:'" + (this.config.getSpline() ? "spline" : "line") + "',";
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
        StringBuffer var6 = new StringBuffer();
        int var7 = 0;

        for(int var8 = 0; var8 < var1.size(); ++var8) {
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

        var4 = var4 + "{    \tname:'" + (var3.equals(this.chart.getYcol()) ? ChartUtils.replaeUnit(this.config.getYdesc()) : ChartUtils.replaeUnit(this.config.getY2desc())) + "',\t\t\t\t\t \r\n ";
        var4 = var4 + "\t\tcolor: '" + var5[this.colorIndex] + "',    \r\n ";
        ++this.colorIndex;
        if (this.chart.getY2col() != null && this.chart.getY2col().length() > 0 && var3.equals(this.chart.getY2col())) {
            var4 = var4 + "type:'" + (this.config.getSpline() ? "spline" : "line") + "',";
            var4 = var4 + "yAxis: 1,";
        } else {
            var4 = var4 + "type:'" + (this.config.getSpline() ? "spline" : "line") + "',";
            if (this.chart.getY2col() != null && this.chart.getY2col().length() > 0) {
                var4 = var4 + "yAxis: 0,";
            }
        }

        var4 = var4 + "\t data: [" + var6 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t     \r\n ";
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
