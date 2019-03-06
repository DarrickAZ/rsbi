//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart.highcharts;

import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter;
import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter$ColorVO;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public class PieChart extends AbstractChartEmitter {
    public static final int pieMaxcntDef = 20;

    public PieChart() {
    }

    protected void initMargin() {
        if (this.chart.getWidth() == null || this.chart.getWidth().length() == 0) {
            this.chart.setWidth("370");
        }

        if (this.chart.getHeight() == null || this.chart.getHeight().length() == 0) {
            this.chart.setHeight("250");
        }

        if (this.config.getMargin() == null || this.config.getMargin().length() == 0) {
            this.config.setMargin("10, 20, 30, 5");
        }

        if (this.config.getLegendLayout() == null || this.config.getLegendLayout().length() == 0) {
            this.config.setLegendLayout("vertical");
        }

        if (this.config.getLabelType() == null || this.config.getLabelType().length() == 0) {
            this.config.setLabelType("np");
        }

        if (this.config.getXcnt() == null || this.config.getXcnt().length() == 0) {
            this.config.setXcnt(String.valueOf(20));
        }

    }

    public int createChartJS(boolean var1) {
        this.initMargin();
        String var2 = this.chart.getYcol();
        String var3 = this.chart.getScol();
        String var4 = this.config.getFormatCol();
        String var5 = this.config.getLegendPosition();
        String var6 = this.chart.getWidth();
        String var7 = this.chart.getId();
        String var8 = this.a(this.dataList, var2, var3, var4);
        String var9 = ChartUtils.crtChartDivStyle(var6, this.chart.getHeight(), this.chart.getAlign());
        StringBuffer var10 = new StringBuffer();
        if (!var1) {
            var10.append(" <div id=\"" + var7 + "\" style=\" " + var9 + " \"></div>   \r\n");
            var10.append(" <script type=\"text/javascript\"> \t\r\n");
            var10.append(" var chart;  \t\r\n");
            var10.append(" \tchart = new Highcharts.Chart(");
        }

        var10.append("\t\t{ \t   \r\n");
        var10.append(" \t\tchart: {\t\t   \r\n");
        if (var1) {
            var10.append(ChartUtils.crtPicSize(this.chart.getWidth(), this.chart.getHeight()));
        }

        var10.append(" \t\t\trenderTo: '" + var7 + "',\t   \t  \r\n");
        var10.append(" \t\t\tmargin: [" + this.config.getMargin() + "]      \r\n");
        var10.append(" \t\t},\t   \r\n");
        var10.append(" \r\n ");
        var10.append(" \t\ttitle: {\t   \r\n");
        var10.append(" \t\t\ttext: '',\t   \r\n");
        var10.append(" \t\t\tstyle: {\t   \r\n");
        var10.append(" \t\t\t\tmargin: '5px 0 0 0' // center it\t   \r\n");
        var10.append(" \t\t\t}\t   \r\n");
        var10.append(" \t\t},\t   \r\n");
        String var11 = (String)((Map)this.dataList.get(0)).get(var4);
        if (var11 == null) {
            var11 = var4;
        }

        Object var12 = ((Map)this.dataList.get(0)).get(this.config.getUnitCol());
        String var13 = var12 == null ? null : var12.toString();
        if (var13 == null) {
            var13 = this.config.getUnitCol();
        }

        if ("%".equals(var13)) {
            var13 = "";
        }

        var10.append("\t\ttooltip: {\t   \r\n");
        var10.append("  formatter:function(){ return '<b>' + this.key + '</b>: ' + formatNumber(this.y,'" + var11 + "') + '" + ChartUtils.writerUnit(this.chart.getRate()) + var13 + "' + '<br/> 占比:  ' + formatNumber(this.percentage/100, '0.00%') }");
        var10.append("\t\t},\t   \t\t\r\n");
        var10.append("\tplotOptions: {");
        var10.append("series :{ cursor: 'pointer',");
        var10.append("point:{events:{click:function(e){");
        var10.append("\t\tvar xvalue = e.point.name;\t\r\n");
        var10.append("\t\tvar yvalue = this.y;\t   \r\n");
        var10.append("\t\tvar svalue = '';\t   \r\n");
        var10.append("\t\tvar pos = {left:e.clientX, top:e.clientY};\t   \r\n");
        if (this.config.getAction() != null && this.config.getAction().length() > 0) {
            Map var14 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
            var10.append(" var ys = " + JSONObject.fromObject(var14) + "; \r\n");
            String var15 = (String)this.request.getAttribute("compId");
            Integer var16 = (Integer)this.request.getAttribute("xcolid");
            var10.append("\t\t" + this.config.getAction() + "(ys[xvalue], xvalue, yvalue, svalue, pos, " + var15 + ", " + var16 + ") ;  \r\n");
        }

        if (this.chart.getLink() != null) {
            String var18 = this.chart.getLink().getLinkUrl();
            if (var18 != null && var18.length() != 0) {
                var10.append("url = \"" + (var18.startsWith("http://") ? var18 : "http://" + var18) + "\"; \r\n");
                var10.append("window.open(url); \r\n");
            } else {
                Map var19 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
                String[] var20 = this.chart.getLink().getUrl();
                String[] var17 = this.chart.getLink().getTargetId();
                var10.append(" var ys = " + JSONObject.fromObject(var19) + "; \r\n");
                var10.append("\t\tchartComp_Link('" + this.chart.getXcolDesc() + "', ys[xvalue], " + ChartUtils.array2string(var20) + ", '" + this.chart.getLink().getParams() + "', " + ChartUtils.array2string(var17) + "," + ChartUtils.array2string(this.chart.getLink().getType()) + "); ");
            }
        }

        var10.append("\t}}}}");
        var10.append(",");
        var10.append("pie: {allowPointSelect: true, dataLabels: {");
        if (this.config.getDistance() != null) {
            var10.append("distance:" + this.config.getDistance() + ",");
        }

        var10.append("enabled: " + this.config.getShowLabel() + ", ");
        if ("np".equals(this.config.getLabelType())) {
            var10.append("format: '<b>{point.name}</b>:{point.percentage:.1f}%'");
        } else if ("nv".equals(this.config.getLabelType())) {
            var10.append("format: '<b>{point.name}</b>:{point.y}'");
        } else if ("n".equals(this.config.getLabelType())) {
            var10.append("format: '<b>{point.name}</b>'");
        }

        var10.append("},showInLegend: " + (this.config.getShowLegend() ? "true" : "false"));
        var10.append("}");
        var10.append("}, \r\n");
        var10.append("legend: {");
        var10.append("\tenabled: " + this.config.getShowLegend() + ",");
        var10.append("\tlayout: '" + this.config.getLegendLayout() + "',");
        if (var5.equals("righttop")) {
            var10.append("\talign: 'right',verticalAlign: 'top',");
        } else if (var5.equals("centerbottom")) {
            var10.append("\talign:'center',verticalAlign: 'bottom',");
        }

        var10.append("borderWidth:0");
        var10.append("}, \r\n");
        var10.append("\t\tseries: [\t\t   \r\n");
        var10.append(var8 + " \t\r\n ");
        var10.append("\t\t]\t   \r\n");
        var10.append("\t}");
        if (!var1) {
            var10.append(");\t   \t\t\r\n");
            var10.append(" \t\t\t\r\n ");
            var10.append("  \t\t\r\n");
            var10.append("</script>");
        }

        this.out.println(var10.toString());
        return 6;
    }

    private String a(List var1, String var2, String var3, String var4) {
        String var5 = "";
        Object var6 = "";
        AbstractChartEmitter$ColorVO[] var7 = AbstractChartEmitter$ColorVO.values();
        String var8 = "";

        for(int var9 = 0; var9 < var1.size(); ++var9) {
            Map var10 = (Map)var1.get(var9);
            Object var11 = var10.get(this.chart.getXcol());
            Object var12 = var10.get(var2);
            var6 = var10.get(this.chart.getScol());
            if (var9 >= this.config.getXcnt_Num()) {
                break;
            }

            var8 = var8 + "{    \t\t\t\t\t\t \r\n  \tname: '" + (this.chart.getXcol() == null ? "合计" : var11) + "',     \r\n " + "\t\tcolor: '" + var7[var9] + "',    \r\n ";
            var8 = var8 + "\t\ty: " + var12 + " \t \r\n " + "\t}   \r\n ";
            if (var9 != var1.size() - 1) {
                var8 = var8 + ",";
            }
        }

        var5 = var5 + "{\t\t\t\r\ntype: 'pie',\t\r\nname: '" + var6 + "',\t\r\n" + "data: [\t\t\t\r\n" + var8 + "\t\r\n" + "]\t\t\t\t\t\r\n" + "}\t\t\t\t\t\r\n";
        return var5;
    }
}
