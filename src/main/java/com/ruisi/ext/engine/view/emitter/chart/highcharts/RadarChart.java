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

public class RadarChart extends AbstractChartEmitter {
    public static final int maxcntDef = 30;

    public RadarChart() {
    }

    protected void initMargin() {
        if (this.chart.getWidth() == null || this.chart.getWidth().equals("")) {
            this.chart.setWidth("800");
        }

        if (this.chart.getHeight() == null || this.chart.getHeight().equals("")) {
            this.chart.setHeight("400");
        }

        if (this.config.getLegendLayout() == null || this.config.getLegendLayout().equals("")) {
            this.config.setLegendLayout("vertical");
        }

        if (this.config.getLegendPosition() == null || this.config.getLegendPosition().equals("")) {
            this.config.setLegendPosition("righttop");
        }

        if (this.config.getXcnt() == null || this.config.getXcnt().length() == 0) {
            this.config.setXcnt(String.valueOf(30));
        }

    }

    public int createChartJS(boolean var1) {
        this.initMargin();
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
            var11 = String.valueOf(var15);
            this.chart.setWidth(var11);
        }

        String var25 = ChartUtils.crtChartDivStyle(var11, this.chart.getHeight(), this.chart.getAlign());
        StringBuffer var16 = new StringBuffer();
        if (!var1) {
            var16.append(" <div id=\"" + var12 + "\" style=\" " + var25 + " \"></div>   \r\n");
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

        var16.append(" \t\t\trenderTo: '" + var12 + "',\t   \r\n");
        var16.append("\t\t\t\t polar: true,  type:'line'\t\r\n");
        var16.append(" \t\t},\t   \r\n");
        var16.append(" \r\n ");
        var16.append(" \t\ttitle: {\t   \r\n");
        var16.append(" \t\t\ttext: '',\t   \r\n");
        var16.append(" \t\t\tstyle: {\t   \r\n");
        var16.append(" \t\t\t\tmargin: '10px 0 0 0' // center it\t   \r\n");
        var16.append(" \t\t\t}\t   \r\n");
        var16.append(" \t\t},\t   \r\n");
        var16.append(" \r\n ");
        var16.append("\t\t\txAxis: [{\t   \r\n");
        var16.append("\t\t\t\tcategories: [ \t   \r\n");
        var16.append(this.a(this.xcolList) + " \r\n");
        var16.append("\t\t\t\t],\t   \r\n");
        var16.append("      labels: {  \n");
        if (this.chart.getDateType() != null && this.chart.getDateType().length() > 0) {
            ChartUtils.formatDate(var16, this.chart.getDateType(), this.chart.getDateTypeFmt(), this.xcolList);
        }

        var16.append("enabled:true \n");
        var16.append("},\t\n");
        var16.append("\t\t\t tickmarkPlacement: 'on', \r\n");
        var16.append("\t\t\t lineWidth: 0 \r\n");
        var16.append("\t\t\t}],\t   \r\n");
        var16.append(" \r\n ");
        var16.append("\t\t\tyAxis: [{ \t   \r\n");
        if (var8 != null && var8.length() > 0) {
            var16.append("min:" + var8 + ",");
        }

        var16.append("\t\t\t\tlabels: {\t   \r\n");
        Object var17 = ((Map)this.dataList.get(0)).get(var7);
        String var18 = var17 == null ? null : var17.toString();
        if (var18 == null) {
            var18 = var7;
        }

        if (var18 != null && var18.length() >= 0 && !var1) {
            var16.append("\t\t\tformatter:function(){return formatNumber(this.value, '" + var18 + "');},  \r\n");
        }

        var16.append("\t\t\t\t\tstyle: {\t   \r\n");
        var16.append("\t\t\t\t\t}\t   \r\n");
        var16.append("\t\t\t\t}\t   \r\n");
        var16.append("\t\t\t} \t   \r\n");
        var16.append("\t\t],\t   \r\n");
        var16.append(" \r\n ");
        String var19 = (String)((Map)this.dataList.get(0)).get(var7);
        if (var19 == null) {
            var19 = var7;
        }

        String var20 = (String)((Map)this.dataList.get(0)).get(this.config.getUnitCol());
        if (var20 == null) {
            var20 = this.config.getUnitCol();
        }

        if ("%".equals(var20)) {
            var20 = "";
        }

        var16.append("\t\ttooltip: {\t   \r\n");
        var16.append("\t\t\tformatter: function() {\t   \r\n");
        var16.append("\t\t\t\treturn '<b>'+ this.series.name +'</b><br/>'+\t   \r\n");
        var16.append("\t\t\t\t\tthis.x +': '+ formatNumber(this.y, '" + (var19 == null ? "" : var19) + "') + '" + ChartUtils.writerUnit(this.chart.getRate()) + (var20 == null ? "" : var20) + "';\t   \r\n");
        var16.append("\t\t\t}\t   \r\n");
        var16.append("\t\t},\t   \r\n");
        var16.append(" \r\n ");
        var16.append("\t\tplotOptions: {  \t   \r\n");
        var16.append("\t\t\tline: {\t   \r\n");
        var16.append("\t\t\t\tcursor: 'pointer',\t   \r\n");
        var16.append("\t\t\t\tpoint: {\t   \r\n");
        var16.append("\t\t\t\t\tevents: {\t   \r\n");
        var16.append("\t\t\t\t\t\tclick: function(e) {\t   \r\n");
        var16.append("\t\t\t\t\t\t\tvar yvalue = this.y;\t   \r\n");
        var16.append("\t\t\t\t\t\t\tvar svalue = this.series.name;\t   \r\n");
        var16.append("\t\t\t\t\t\t\tvar pos = {left:e.clientX, top:e.clientY};\t   \r\n");
        var16.append("\t\t\t\t\t\tvar xvalue = this.series.xAxis.categories[this.x];\t\r\n");
        if (this.config.getAction() != null && !this.config.getAction().equals("")) {
            Map var21 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
            var16.append(" var ys = " + JSONObject.fromObject(var21) + "; \r\n");
            String var22 = (String)this.request.getAttribute("compId");
            Integer var23 = (Integer)this.request.getAttribute("xcolid");
            var16.append("\t\t\t\t\t\t\t" + this.config.getAction() + "(ys[xvalue], xvalue, yvalue, svalue, pos, " + var22 + ", " + var23 + ") ;  \r\n");
        }

        if (this.chart.getLink() != null) {
            String var26 = this.chart.getLink().getLinkUrl();
            if (var26 != null && var26.length() != 0) {
                var16.append("url = \"" + (var26.startsWith("http://") ? var26 : "http://" + var26) + "\"; \r\n");
                var16.append("window.open(url); \r\n");
            } else {
                Map var27 = ChartUtils.listXcolDatas(this.chart.loadOptions(), this.chart, this.config.getXcnt_Num());
                String[] var28 = this.chart.getLink().getUrl();
                String[] var24 = this.chart.getLink().getTargetId();
                var16.append(" var ys = " + JSONObject.fromObject(var27) + "; \r\n");
                var16.append("\t\tchartComp_Link('" + this.chart.getXcolDesc() + "', ys[xvalue], " + ChartUtils.array2string(var28) + ", '" + this.chart.getLink().getParams() + "', " + ChartUtils.array2string(var24) + "," + ChartUtils.array2string(this.chart.getLink().getType()) + "); ");
            }
        }

        var16.append(" \r\n ");
        var16.append("\t\t\t\t\t\t}\t   \r\n");
        var16.append("\t\t\t\t\t}\t   \r\n");
        var16.append("\t\t\t\t}\t   \r\n");
        var16.append("\t\t\t},\t   \r\n");
        var16.append("\t\t\t\tseries: {   \r\n");
        var16.append("\t\t\t\t\tmarker: {\t\t\t   \r\n");
        var16.append("\t\t\t\t\t\tenabled: " + this.config.getMarkerEnabled() + ",  \r\n");
        var16.append("\t\t\t\t\t\tlineWidth: 0,\t   \r\n");
        var16.append("\t\t\t\t\t\tradius: 4,\t   \r\n");
        var16.append("\t\t\t\t\t\tlineColor: '#FFFFFF'\t   \r\n");
        var16.append("\t\t\t\t\t},\t\t   \r\n");
        var16.append("\t\t\t\t\tstates: { \t\t   \r\n");
        var16.append("\t\t\t\t\t\thover: {\t\t   \r\n");
        var16.append("\t\t\t\t\t\t\tmarker: {\t   \r\n");
        var16.append("\t\t\t\t\t\t\t\tenabled: true,   \r\n");
        var16.append("\t\t\t\t\t\t\t\tsymbol: 'auto',   \r\n");
        var16.append("\t\t\t\t\t\t\t\tradius: " + (this.config.getMarkerEnabled() ? 5 : 4) + ",   \r\n");
        var16.append("\t\t\t\t\t\t\t\tlineWidth: 1   \r\n");
        var16.append("\t\t\t\t\t\t\t}   \r\n");
        var16.append("\t\t\t\t\t\t}\t   \r\n");
        var16.append("\t\t\t\t\t}\t\t   \r\n");
        var16.append("\t\t\t\t}\t\t\t   \r\n");
        var16.append("\t\t},\t   \r\n");
        var16.append(" \r\n ");
        var16.append("\t\tlegend: {\t   \r\n");
        var16.append("\t\t\tenabled: " + this.config.getShowLegend() + ",\t   \r\n");
        var16.append("\t\t\tlayout: '" + var10 + "',\t   \r\n");
        if (var9.equals("righttop")) {
            var16.append("\t\t\talign: 'right',verticalAlign: 'top',");
        } else if (var9.equals("centerbottom")) {
            var16.append("\talign:'center',verticalAlign: 'bottom',");
        }

        var16.append("\tborderWidth:0 \r\n");
        var16.append("\t\t},\t   \r\n");
        var16.append(" \r\n ");
        var16.append("\t\tseries: [\t\t   \r\n");
        if (this.dataInfoList != null && this.dataInfoList.size() != 0) {
            var16.append(this.a(this.dataList, this.dataInfoList, var2, var3, var5) + "\r\n");
        } else {
            var16.append(this.a(this.dataList, var2, var3) + "\r\n");
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
            var6 = var6 + "\tpointPlacement: 'on',\t\r\n";
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
        var4 = var4 + "\t\tpointPlacement: 'on',\t   \t\t\t \r\n";
        var4 = var4 + "\t data: [" + var6 + "] \t \r\n " + "\t}   \t\t\t\t\t\t\t     \r\n ";
        return var4;
    }

    private String a(List var1) {
        if (this.chart.getXcol() != null && this.chart.getXcol().length() != 0) {
            String var2 = "";
            int var3 = var1.size();

            for(int var4 = 0; var4 < var1.size(); ++var4) {
                String var5 = "" + var1.get(var4);
                var2 = var2 + "'" + var5 + "'";
                if (var4 != var3 - 1) {
                    var2 = var2 + ",";
                }

                int var6 = var4 + 1;
                if (var6 % 10 == 0) {
                    var2 = var2 + "\r\n";
                }
            }

            return var2;
        } else {
            return "'合计'";
        }
    }
}
