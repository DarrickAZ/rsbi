//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.portal;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.chart.*;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.emitter.chart.AbstractChartEmitter.ColorVO;
import com.ruisitech.bi.entity.bireport.DimDto;
import com.ruisitech.bi.entity.bireport.KpiDto;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.portal.LinkAcceptDto;
import com.ruisitech.bi.entity.portal.PortalChartQuery;
import com.ruisitech.bi.service.bireport.BaseCompService;
import com.ruisitech.bi.service.bireport.OlapChartService;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.ext.service.DataControlInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Service
@Scope("prototype")
public class PortalChartService extends BaseCompService {
    public static final String deftMvId = "mv.portal.chart";
    @Autowired
    private TableCacheService cacheService;
    @Autowired
    private OlapChartService chartService;
    private Map<String, InputField> mvParams = new HashMap();
    @Autowired
    private DataControlInterface dataControl;

    public PortalChartService() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
        this.mvParams.clear();
    }

    public MVContext json2MV(PortalChartQuery chartJson) throws Exception {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.portal.chart");
        mv.setHideMV(true);
        super.parserHiddenParam(chartJson.getPortalParams(), mv, this.mvParams);
        if (chartJson.getKpiJson().size() != 0 && chartJson.getKpiJson().get(0) != null) {
            ChartContext cr = this.json2Chart(chartJson);
            cr.setId("C" + chartJson.getId());
            String sql = this.createSql(chartJson, 0, 0);
            GridDataCenterContext dc = this.chartService.createDataCenter(chartJson, sql);
            cr.setRefDataCenter(dc.getId());
            if (mv.getGridDataCenters() == null) {
                mv.setGridDataCenters(new HashMap());
            }

            mv.getGridDataCenters().put(dc.getId(), dc);
            mv.getChildren().add(cr);
            cr.setParent(mv);
            Map<String, ChartContext> crs = new HashMap();
            crs.put(cr.getId(), cr);
            mv.setCharts(crs);
            return mv;
        } else {
            return mv;
        }
    }

    public ChartContext json2Chart(PortalChartQuery chartJson) {
        ChartContext ctx = new ChartContextImpl();
        DimDto obj = chartJson.getChartJson().getXcol();
        String chartType;
        if (obj != null && obj.getId() != null) {
            String alias = obj.getAlias();
            String key = obj.getTableColKey();
            chartType = obj.getTableColName();
            if (key != null && key.length() > 0 && chartType != null && chartType.length() > 0) {
                ctx.setXcolDesc(alias);
                ctx.setXcol(alias + "2");
            } else {
                ctx.setXcol(alias);
                ctx.setXcolDesc(alias);
            }
        }

        KpiDto kpiInfo;
        if (chartJson.getMkpi() && chartJson.getMkpiJson() != null) {
            List<ChartYcolContext> ycols = new ArrayList();
            Iterator var29 = chartJson.getMkpiJson().iterator();

            while(var29.hasNext()) {
                KpiDto k = (KpiDto)var29.next();
                ChartYcolContext ycol = new ChartYcolContext();
                ycol.setName(k.getAlias());
                ycol.setDesc(k.getKpi_name());
                ycols.add(ycol);
            }

            kpiInfo = (KpiDto)chartJson.getMkpiJson().get(0);
            ctx.setYcols(ycols);
        } else {
            kpiInfo = (KpiDto)chartJson.getKpiJson().get(0);
            ctx.setYcol(kpiInfo.getAlias());
        }

        List<KpiDto> kpis = chartJson.getKpiJson();
        chartType = chartJson.getChartJson().getType();
        if (chartType.equals("scatter")) {
            ctx.setY2col(((KpiDto)kpis.get(1)).getAlias());
        }

        if (chartType.equals("bubble")) {
            ctx.setY2col(((KpiDto)kpis.get(1)).getAlias());
            ctx.setY3col(((KpiDto)kpis.get(2)).getAlias());
        }

        if (!chartJson.getMkpi() && kpiInfo.getRate() != null) {
            ctx.setRate(kpiInfo.getRate());
        }

        if (!chartJson.getMkpi() && kpis.size() > 1) {
            ctx.setRate2(((KpiDto)kpis.get(1)).getRate());
        }

        if (!chartJson.getMkpi() && kpis.size() > 2) {
            ctx.setRate3(((KpiDto)kpis.get(2)).getRate());
        }

        if (!chartJson.getMkpi()) {
            DimDto scol = chartJson.getChartJson().getScol();
            if (scol != null && scol.getId() != null) {
                ctx.setScol(scol.getAlias());
            }
        }

        ctx.setShape(chartJson.getChartJson().getType());
        ctx.setWidth("99%");
        ctx.setHeight(chartJson.getChartJson().getHeight() == null ? "250" : String.valueOf(chartJson.getChartJson().getHeight()));
        ctx.setAlign("center");
        List<ChartKeyContext> properties = new ArrayList();
        String unitStr = super.writerUnit(kpiInfo.getRate()) + (kpiInfo.getUnit() == null ? "" : kpiInfo.getUnit());
        String ydesc = kpiInfo.getYdispName() + (unitStr.length() == 0 ? "" : "(" + unitStr + ")");
        properties.add(new ChartKeyContext("ydesc", chartJson.getMkpi() ? "度量" : ydesc));
        String legendLayout;
        if (!"bubble".equals(ctx.getShape()) && !"scatter".equals(ctx.getShape())) {
            if (chartJson.getChartJson().getXcol() != null && chartJson.getChartJson().getXcol().getId() != null) {
                properties.add(new ChartKeyContext("xdesc", chartJson.getChartJson().getXcol().getXdispName()));
            }
        } else {
            KpiDto kpiInfo2 = (KpiDto)kpis.get(1);
            legendLayout = super.writerUnit(kpiInfo2.getRate()) + (kpiInfo2.getUnit() == null ? "" : kpiInfo2.getUnit());
            properties.add(new ChartKeyContext("xdesc", kpiInfo2.getKpi_name() + (legendLayout.length() == 0 ? "" : "(" + legendLayout + ")")));
            properties.add(new ChartKeyContext("formatCol2", kpiInfo2.getFmt()));
        }

        if (kpiInfo.getFmt() != null && kpiInfo.getFmt().length() > 0) {
            properties.add(new ChartKeyContext("formatCol", "kpi_fmt"));
        }

        if (kpiInfo.getUnit() != null && kpiInfo.getUnit().length() > 0) {
            properties.add(new ChartKeyContext("unitCol", "kpi_unit"));
        }

        if (kpiInfo.getMin() != null) {
            properties.add(new ChartKeyContext("ymin", String.valueOf(kpiInfo.getMin())));
        }

        if (kpiInfo.getMax() != null) {
            properties.add(new ChartKeyContext("ymax", String.valueOf(kpiInfo.getMax())));
        }

        String lengend = chartJson.getChartJson().getShowLegend();
        ChartKeyContext val1;
        if (lengend != null && !"true".equals(lengend)) {
            val1 = new ChartKeyContext("showLegend", "false");
            properties.add(val1);
        } else {
            val1 = new ChartKeyContext("showLegend", "true");
            properties.add(val1);
        }

        legendLayout = chartJson.getChartJson().getLegendLayout();
        if (legendLayout != null) {
            ChartKeyContext val1 = new ChartKeyContext("legendLayout", legendLayout);
            properties.add(val1);
        }

        String legendpos = chartJson.getChartJson().getLegendpos();
        if (legendpos != null) {
            ChartKeyContext val1 = new ChartKeyContext("legendPosition", legendpos);
            properties.add(val1);
        }

        ChartKeyContext val1;
        if (obj != null && obj.getId() != null) {
            Integer top = obj.getTop();
            if (top != null) {
                val1 = new ChartKeyContext("xcnt", String.valueOf(top));
                properties.add(val1);
            }

            if (obj.getTickInterval() != null) {
                val1 = new ChartKeyContext("tickInterval", obj.getTickInterval());
                properties.add(val1);
            }

            if (obj.getRouteXaxisLable() != null) {
                val1 = new ChartKeyContext("routeXaxisLable", obj.getRouteXaxisLable());
                properties.add(val1);
            }
        }

        String dataLabel = chartJson.getChartJson().getDataLabel();
        if (dataLabel != null && !"false".equals(dataLabel)) {
            val1 = new ChartKeyContext("showLabel", "true");
            properties.add(val1);
        } else {
            val1 = new ChartKeyContext("showLabel", "false");
            properties.add(val1);
        }

        String labelType = chartJson.getChartJson().getLabelType();
        ChartKeyContext val4 = new ChartKeyContext("labelType", labelType);
        properties.add(val4);
        ChartKeyContext val1 = new ChartKeyContext("gaugeCnt", "1");
        properties.add(val1);
        String marginLeft = chartJson.getChartJson().getMarginLeft();
        if (marginLeft != null && marginLeft.length() > 0) {
            ChartKeyContext tmp = new ChartKeyContext("marginLeft", marginLeft);
            properties.add(tmp);
        }

        String marginRight = chartJson.getChartJson().getMarginRight();
        if (marginRight != null && marginRight.length() > 0) {
            ChartKeyContext tmp = new ChartKeyContext("marginRight", marginRight);
            properties.add(tmp);
        }

        String markerEnabled = chartJson.getChartJson().getMarkerEnabled();
        if (markerEnabled != null && "true".equals(markerEnabled)) {
            ChartKeyContext md = new ChartKeyContext("markerEnabled", "false");
            properties.add(md);
        }

        if ("map".equals(ctx.getShape())) {
            properties.add(new ChartKeyContext("mapJson", chartJson.getChartJson().getMaparea()));
        }

        if ("dashboard".equals(chartJson.getUseIn())) {
            properties.add(new ChartKeyContext("action", "window.dashboard.seriesClick"));
        } else {
            properties.add(new ChartKeyContext("action", "setSeriesColor"));
        }

        ctx.setProperties(properties);
        String style = chartJson.getDashboardStyle();
        if (style != null && style.length() > 0) {
            properties.add(new ChartKeyContext("style", style));
        }

        Map<String, Object> link = chartJson.getChartJson().getLink();
        if (link != null && !link.isEmpty()) {
            ctx.setLink(this.createChartLink(link));
        }

        ctx.setLabel(chartJson.getId());
        Integer typeIndex = chartJson.getChartJson().getTypeIndex();
        if ((chartType.equals("column") || chartType.equals("line")) && typeIndex != null && (2 == typeIndex || 4 == typeIndex) && kpis.size() > 1 && kpis.get(1) != null) {
            ctx.setY2col(((KpiDto)kpis.get(1)).getAlias());
            ctx.setMergeData(((KpiDto)kpis.get(1)).getMergeData());
            ctx.setY2Aggre(((KpiDto)kpis.get(1)).getAggre());
            String y2unit = super.writerUnit(((KpiDto)kpis.get(1)).getRate()) + (((KpiDto)kpis.get(1)).getUnit() == null ? "" : ((KpiDto)kpis.get(1)).getUnit());
            ChartKeyContext y2desc = new ChartKeyContext("y2desc", ((KpiDto)kpis.get(1)).getYdispName() + (y2unit.length() == 0 ? "" : "(" + y2unit + ")"));
            properties.add(y2desc);
            ChartKeyContext y2fmtcol = new ChartKeyContext("formatCol2", ((KpiDto)kpis.get(1)).getFmt());
            properties.add(y2fmtcol);
        }

        ChartKeyContext gisMap;
        if ("column".equals(chartType) && typeIndex != null && (3 == typeIndex || 4 == typeIndex)) {
            gisMap = new ChartKeyContext("stack", "true");
            properties.add(gisMap);
        }

        if ("pie".equals(chartType) && typeIndex != null && 2 == typeIndex) {
            gisMap = new ChartKeyContext("ring", "true");
            properties.add(gisMap);
        }

        if ("pie".equals(chartType) && typeIndex != null && 3 == typeIndex) {
            ctx.setShape("nestingPie");
        }

        if ("map".equals(chartType) && typeIndex != null && (2 == typeIndex || 3 == typeIndex)) {
            ctx.setShape("scatterMap");
        }

        if ("map".equals(chartType) && typeIndex != null && 3 == typeIndex) {
            gisMap = new ChartKeyContext("gisMap", "true");
            properties.add(gisMap);
        }

        ctx.setSeriesColor(chartJson.getColors());
        return ctx;
    }

    public ChartLinkContext createChartLink(Map<String, Object> link) {
        String type = (String)link.get("type");
        String target = (String)link.get("target");
        String url = (String)link.get("url");
        String paramName = (String)link.get("paramName");
        ChartLinkContext clink = new ChartLinkContext();
        if (url != null && url.length() > 0) {
            clink.setLinkUrl(url);
        } else {
            clink.setTarget(target.split(","));
            clink.setType(type.split(","));
        }

        clink.setParamName(paramName);
        return clink;
    }

    public String createSql(PortalChartQuery chartJson, int release) {
        return this.createSql(chartJson, release, 0);
    }

    public String createSql(PortalChartQuery chartJson, int release, int index) {
        TableInfoVO tinfo = this.cacheService.getTableInfo(chartJson.getTid());
        boolean qEs = false;
        if ("y".equals(tinfo.getUseEs()) && "y".equals(tinfo.getEsEnable())) {
            qEs = true;
        }

        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        List<DimDto> dims = chartJson.getChartJson().getDims();

        String tsql;
        String ret;
        for(int i = 0; i < dims.size(); ++i) {
            DimDto dim = (DimDto)dims.get(i);
            tsql = dim.getTableColKey();
            ret = dim.getTableColName();
            if (tsql != null && ret != null && tsql.length() > 0 && ret.length() > 0) {
                sql.append(tsql + " " + dim.getAlias() + ", " + ret + " " + dim.getAlias() + "2,");
            } else {
                sql.append(" " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " " + dim.getAlias() + ", ");
            }
        }

        List<KpiDto> kpis = chartJson.getMkpi() && chartJson.getMkpiJson() != null ? chartJson.getMkpiJson() : chartJson.getKpiJson();
        if (kpis.size() == 0) {
            sql.append(" 0 kpi_value ");
        } else {
            for(int i = 0; i < kpis.size(); ++i) {
                KpiDto kpi = (KpiDto)kpis.get(i);
                sql.append(kpi.getCol_name() + " ");
                sql.append(kpi.getAlias());
                if (i != kpis.size() - 1) {
                    sql.append(",");
                }
            }
        }

        String tname = tinfo.getTname();
        tsql = tinfo.getSql();
        if (tsql != null && tsql.length() > 0) {
            sql.append(" from (" + tsql + ") t ");
        } else {
            sql.append(" from " + tname + " ");
        }

        sql.append(" where 1=1 ");
        if (this.dataControl != null) {
            ret = this.dataControl.process(RSBIUtils.getLoginUserInfo(), tname);
            if (ret != null) {
                sql.append(ret + " ");
            }
        }

        DimDto dim;
        String key;
        int i;
        for(i = 0; i < dims.size(); ++i) {
            dim = (DimDto)dims.get(i);
            if (dim.getType().equals("day")) {
                if (dim.getDay() != null) {
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " between '" + dim.getDay().getStartDay() + "' and '" + dim.getDay().getEndDay() + "'");
                } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                    key = RSBIUtils.dealStringParam(dim.getVals());
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " in (" + key + ")");
                }
            } else if (dim.getType().equals("month")) {
                if (dim.getMonth() != null) {
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " between '" + dim.getMonth().getStartMonth() + "' and '" + dim.getMonth().getEndMonth() + "'");
                } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                    key = RSBIUtils.dealStringParam(dim.getVals());
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " in (" + key + ")");
                }
            } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                key = dim.getVals();
                if ("string".equalsIgnoreCase(dim.getValType())) {
                    key = RSBIUtils.dealStringParam(dim.getVals());
                }

                sql.append(" and " + (dim.getTableColKey() != null && dim.getTableColKey().length() > 0 ? dim.getTableColKey() : (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname())) + " in (" + key + ")");
            }
        }

        sql.append(super.dealCubeParams(chartJson.getCompParams(), "dim", tinfo));
        sql.append(super.dealDashboardParams(chartJson.getDashboardParam(), tinfo));
        String tp;
        String col;
        if (release == 1) {
            LinkAcceptDto linkAccept = chartJson.getChartJson().getLinkAccept();
            if (linkAccept != null && linkAccept.getId() != null) {
                col = qEs ? (linkAccept.getCol().equals(linkAccept.getFromCol()) ? linkAccept.getFromCol() : linkAccept.getAlias()) : linkAccept.getCol();
                key = chartJson.getId();
                tp = super.findEventParamName(key);
                if (tp == null) {
                    tp = linkAccept.getAlias();
                }

                String valtype = linkAccept.getValType();
                String tableColKey = linkAccept.getTableColKey();
                if (tableColKey != null && tableColKey.length() > 0) {
                    tp = tableColKey;
                }

                String ncol = "$" + tp;
                if ("string".equalsIgnoreCase(valtype)) {
                    ncol = "'" + ncol + "'";
                }

                sql.append("#if($" + tp + " && $" + tp + " != '') and  " + col + " = " + ncol + " #end");
            }
        }

        if (dims.size() > 0) {
            sql.append(" group by ");

            for(i = 0; i < dims.size(); ++i) {
                dim = (DimDto)dims.get(i);
                key = dim.getTableColKey();
                tp = dim.getTableColName();
                if (key != null && tp != null && key.length() > 0 && tp.length() > 0) {
                    sql.append(key + ", " + tp);
                } else {
                    sql.append(qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname());
                }

                if (i != dims.size() - 1) {
                    sql.append(",");
                }
            }
        }

        StringBuffer filter = new StringBuffer("");
        Iterator var26 = kpis.iterator();

        while(var26.hasNext()) {
            KpiDto kpi = (KpiDto)var26.next();
            if (kpi.getFilter() != null) {
                filter.append(" and " + (qEs ? kpi.getAlias() : kpi.getCol_name()) + " ");
                tp = kpi.getFilter().getFilterType();
                filter.append(tp);
                filter.append(" ");
                double val1 = kpi.getFilter().getVal1();
                if (kpi.getFmt() != null && kpi.getFmt().endsWith("%")) {
                    val1 /= 100.0D;
                }

                filter.append(val1 * (double)(kpi.getRate() == null ? 1 : kpi.getRate()));
                if ("between".equals(tp)) {
                    double val2 = kpi.getFilter().getVal2();
                    if (kpi.getFmt() != null && kpi.getFmt().endsWith("%")) {
                        val2 /= 100.0D;
                    }

                    filter.append(" and " + val2 * (double)(kpi.getRate() == null ? 1 : kpi.getRate()));
                }
            }
        }

        filter.append(super.dealCubeParams(chartJson.getCompParams(), "kpi", tinfo));
        if (filter.length() > 0) {
            sql.append(" having 1=1 " + filter);
        }

        col = sql.toString();
        col = col.replaceAll("##", "\\$extUtils.printJH()");
        return col;
    }

    public Object queryChartColors() {
        ColorVO[] vls = ColorVO.values();
        String[] v = new String[vls.length];

        for(int i = 0; i < vls.length; ++i) {
            ColorVO c = vls[i];
            v[i] = c.toString();
        }

        return v;
    }

    public Map<String, InputField> getMvParams() {
        return this.mvParams;
    }

    public OlapChartService getChartService() {
        return this.chartService;
    }
}
