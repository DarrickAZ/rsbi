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

public class AreaChart extends AbstractChartEmitter {
    public AreaChart() {
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
        String var2 = this.config.getLegendPosition();
        String var3 = this.config.getMarginRight();
        String var4 = this.config.getMarginLeft();
        if (var1 == null || var1.equals("")) {
            if ("centerbottom".equals(var2)) {
                var1 = "10, " + (var3 != null ? var3 : "10") + ", 80," + (var4 != null ? var4 : "90");
            } else {
                var1 = "10, " + (var3 != null ? var3 : "10") + ", 50," + (var4 != null ? var4 : "90");
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
        if (this.dataList != null && this.dataList.size() != 0) {
            this.AnalyseData(this.dataList, var2, var3, var4, var5, var6);
            if (this.chart.getWidth() != null && "auto".equalsIgnoreCase(this.chart.getWidth())) {
                int var8 = ChartUtils.autoWidth(this.xcolList);
                if (var8 == ChartUtils.chartMaxWidth) {
                    this.config.setRouteXaxisLable("-30");
                }

                if (this.xcolList.size() <= 31) {
                    this.config.setTickInterval("1");
                } else {
                    this.config.setTickInterval("2");
                }

                this.chart.setWidth(String.valueOf(var8));
            }

            String var19 = ChartUtils.crtChartDivStyle(this.chart.getWidth(), this.chart.getHeight(), this.chart.getAlign());
            StringBuffer var9 = new StringBuffer();
            if (!var1) {
                var9.append(" <div id=\"" + this.chart.getId() + "\" style=\" " + var19 + " \"></div>   \r\n");
                var9.append(" <script type=\"text/javascript\"> \t\r\n");
                var9.append(" var chart;  \t\r\n");
                var9.append(" jQuery(document).ready(function() { \t   \r\n");
                var9.append(" \tchart = new Highcharts.Chart(");
            }

            var9.append("\t\t\t{ \t   \r\n");
            var9.append(" \t\tchart: {\t\t   \r\n");
            var9.append(" \t\t\trenderTo: '" + this.chart.getId() + "',\t   \t  \r\n");
            var9.append("\t\t\t\tdefaultSeriesType: 'areaspline',  \r\n");
            if (var1) {
                var9.append(ChartUtils.crtPicSize(this.chart.getWidth(), this.chart.getHeight()));
            }

            var9.append(" \t\t\tmargin: [" + this.config.getMargin() + "]      \r\n");
            var9.append(" \t\t},\t   \r\n");
            var9.append(" \r\n ");
            var9.append(" \t\ttitle: {\t   \r\n");
            var9.append(" \t\t\ttext: '',\t   \r\n");
            var9.append(" \t\t\tstyle: {\t   \r\n");
            var9.append(" \t\t\t\tmargin: '5px 0 0 0' // center it\t   \r\n");
            var9.append(" \t\t\t}\t   \r\n");
            var9.append(" \t\t},\t   \r\n");
            var9.append(" \r\n ");
            var9.append("\t\t\txAxis: {\t   \r\n");
            var9.append("\t\t\t\tcategories: [ \t   \r\n");
            var9.append(this.setXcolDesc(this.xcolList) + " \r\n");
            var9.append("\t\t\t\t],\t   \r\n");
            if (!"none".equals(this.config.getTickInterval())) {
                var9.append("\t\t\t\ttickInterval:" + this.config.getTickInterval() + ",\r\n");
            }

            var9.append("      labels: {  \n");
            if (this.chart.getDateType() != null && this.chart.getDateType().length() > 0) {
                ChartUtils.formatDate(var9, this.chart.getDateType(), this.chart.getDateTypeFmt(), this.xcolList);
            }

            if (this.config.getRouteXaxisLable() != null && !"0".equals(this.config.getRouteXaxisLable())) {
                var9.append("\t\t\t\trotation: " + this.config.getRouteXaxisLable() + ",\t\n");
                var9.append("\t\t\t\talign: '" + (Integer.parseInt(this.config.getRouteXaxisLable()) > 0 ? "left" : "right") + "',\n");
            }

            var9.append("enabled:true \n");
            var9.append("},\t\n");
            var9.append("\ttitle: {text: '" + this.config.getXdesc() + "'}\t\t\r\n");
            var9.append("\t\t\t},\t   \r\n");
            var9.append(" \r\n ");
            var9.append(" \r\n ");
            var9.append("\t\t\tyAxis: [{ \t   \r\n");
            if (var7 != null && var7.length() > 0) {
                var9.append("min:" + var7 + ",");
            }

            var9.append("\t\t\t\tlabels: {\t   \r\n");
            Object var10 = ((Map)this.dataList.get(0)).get(var6);
            String var11 = var10 == null ? null : var10.toString();
            if (var11 == null) {
                var11 = var6;
            }

            if (var11 != null && var11.length() >= 0 && !var1) {
                var9.append("\t\t\tformatter:function(){return formatNumber(this.value, '" + var11 + "');},  \r\n");
            }

            var9.append("\t\t\t\t\tstyle: {\t   \r\n");
            var9.append("\t\t\t\t\t}\t   \r\n");
            var9.append("\t\t\t\t},\t   \r\n");
            var9.append("         \ttitle: {text: '" + this.config.getYdesc() + "'}\t\t\r\n");
            var9.append("\t\t\t} \t   \r\n");
            var9.append("\t\t],\t   \r\n");
            var9.append(" \r\n ");
            String var12 = (String)((Map)this.dataList.get(0)).get(var6);
            if (var12 == null) {
                var12 = var6;
            }

            Object var13 = ((Map)this.dataList.get(0)).get(this.config.getUnitCol());
            String var14 = var13 == null ? null : var13.toString();
            if (var14 == null) {
                var14 = this.config.getUnitCol();
            }

            if ("%".equals(var14)) {
                var14 = "";
            }

            var9.append("\t\ttooltip: {\t   \r\n");
            var9.append("\t\t\tformatter: function() {\t   \r\n");
            var9.append("\t\t\t\treturn '<b>'+ this.series.name +'</b><br/>'+\t   \r\n");
            var9.append("\t\t\t\t\tthis.x +': '+ formatNumber(this.y, '" + (var12 == null ? "" : var12) + "') + '" + ChartUtils.writerUnit(this.chart.getRate()) + (var14 == null ? "" : var14) + "';\t   \r\n");
            var9.append("\t\t\t}\t   \r\n");
            var9.append("\t\t},\t   \r\n");
            var9.append("\t\tplotOptions: {\t\t\t\r\n");
            var9.append("\t\t\tareaspline : {\t\t\r\n");
            var9.append("\t\t\t\tcursor: 'pointer',\t\r\n");
            var9.append("\t\t\t\tpoint: {\t\t\t\r\n");
            var9.append("\t\t\t\t\tevents: {\t\t\r\n");
            var9.append("\t\t\t\t\t\tclick: function() {\t \t\r\n");
            var9.append("\t\t\t\t\t\t\tvar yvalue = this.y;\t\t\t\r\n");
            var9.append("\t\t\t\t\t\t\tvar svalue = this.series.name;\t\r\n");
            if (this.config.getAction() != null) {
                this.config.getAction().equals("");
            }

            if (this.chart.getLink() != null) {
                String var15 = this.chart.getLink().getLinkUrl();
                if (var15 != null && var15.length() != 0) {
                    var9.append("url = \"" + (var15.startsWith("http://") ? var15 : "http://" + var15) + "\"; \r\n");
                    var9.append("window.open(url); \r\n");
                } else {
                    Map var16 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
                    String[] var17 = this.chart.getLink().getUrl();
                    String[] var18 = this.chart.getLink().getTargetId();
                    var9.append(" var ys = " + JSONObject.fromObject(var16) + "; \r\n");
                    var9.append("\t\tchartComp_Link('" + this.chart.getXcolDesc() + "', ys[xvalue], " + ChartUtils.array2string(var17) + ", '" + this.chart.getLink().getParams() + "', " + ChartUtils.array2string(var18) + "," + ChartUtils.array2string(this.chart.getLink().getType()) + "); ");
                }
            }

            var9.append("\t\t\t\t\t\t}\t\r\n");
            var9.append("\t\t\t\t\t}\t\t\r\n");
            var9.append("\t\t\t\t},\t\t\t\r\n");
            var9.append("\t\t\t\tlineColor: '#666666',\t\r\n");
            var9.append("\t\t\t\tlineWidth: 1,\t\t\t\r\n");
            var9.append("\t\t\t\tmarker: {\t\t\t    \r\n");
            var9.append("\t\t\t\t\tlineWidth: 1,\t\t\t\r\n");
            var9.append("\t\t\t\t\tlineColor: '#666666'\t\r\n");
            var9.append("\t\t\t\t},\t\t\t\r\n");
            var9.append("\t\t\t\tfillOpacity: 0.3\t\t\t\r\n");
            var9.append("\t\t\t},\t\t\r\n");
            var9.append("\t\t\t\tseries: {   \r\n");
            var9.append("\t\t\t\t\tmarker: {\t\t\t   \r\n");
            var9.append("\t\t\t\t\t\tenabled: " + this.config.getMarkerEnabled() + ",  \r\n");
            var9.append("\t\t\t\t\t\tlineWidth: 0,\t   \r\n");
            var9.append("\t\t\t\t\t\tradius: 4,\t   \r\n");
            var9.append("\t\t\t\t\t\tlineColor: '#FFFFFF',\t   \r\n");
            var9.append("\t\t\t\t\t\tfillColor: null\t   \r\n");
            var9.append("\t\t\t\t\t},\t\t   \r\n");
            var9.append("\t\t\t\t\tstates: { \t\t   \r\n");
            var9.append("\t\t\t\t\t\thover: {\t\t   \r\n");
            var9.append("\t\t\t\t\t\t\tmarker: {\t   \r\n");
            var9.append("\t\t\t\t\t\t\t\tenabled: true,   \r\n");
            var9.append("\t\t\t\t\t\t\t\tsymbol: 'auto',   \r\n");
            var9.append("\t\t\t\t\t\t\t\tradius: 4,   \r\n");
            var9.append("\t\t\t\t\t\t\t\tlineWidth: 1   \r\n");
            var9.append("\t\t\t\t\t\t\t}   \r\n");
            var9.append("\t\t\t\t\t\t}\t   \r\n");
            var9.append("\t\t\t\t\t}\t\t   \r\n");
            var9.append("\t\t\t\t}\t\t\t   \r\n");
            var9.append("\t\t},\t\t\t\r\n");
            var9.append("\t\tlegend: {\t\t\t\r\n");
            var9.append("\t\t\tenabled: " + this.config.getShowLegend() + ",\t   \r\n");
            var9.append("\t\t\tlayout: '" + this.config.getLegendLayout() + "',\t   \r\n");
            if (this.config.getLegendPosition().equals("righttop")) {
                var9.append("\t\t\talign: 'right',verticalAlign: 'top',");
            } else if (this.config.getLegendPosition().equals("centerbottom")) {
                var9.append("\talign:'center',verticalAlign: 'bottom',");
            }

            var9.append("\tborderWidth:0\t   \r\n");
            var9.append("\t\t},\t\t\t\r\n");
            var9.append(" \r\n ");
            var9.append("\t\tseries: [\t\t   \r\n");
            if (this.dataInfoList != null && this.dataInfoList.size() != 0) {
                var9.append(this.a(this.dataList, this.dataInfoList, var2, var3, var4) + "\r\n");
            } else {
                var9.append(this.a(this.dataList, var2, var3) + "\r\n");
            }

            var9.append("\t\t]\t   \r\n");
            var9.append("\t}");
            if (!var1) {
                var9.append(");\t   \t\t\r\n");
                var9.append(" \t\t\t\r\n ");
                var9.append("});\t   \t\t\r\n");
                var9.append("</script>");
            }

            this.out.println(var9.toString());
            return 6;
        } else {
            this.out.println("图形data属性所指向的List为null");
            return 6;
        }
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
                    if (this.dataInfoList.size() <= LineChart.maxsercnt) {
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

        var4 = var4 + "{    \tname:'" + this.config.getXdesc() + "',\t\t\t\t\t \r\n ";
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
