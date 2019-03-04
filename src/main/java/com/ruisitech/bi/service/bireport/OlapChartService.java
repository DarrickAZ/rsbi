//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.bireport;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.chart.ChartContextImpl;
import com.ruisi.ext.engine.view.context.chart.ChartKeyContext;
import com.ruisi.ext.engine.view.context.chart.ChartYcolContext;
import com.ruisi.ext.engine.view.context.dc.grid.*;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.context.html.TextContext;
import com.ruisi.ext.engine.view.context.html.TextContextImpl;
import com.ruisitech.bi.entity.bireport.*;
import com.ruisitech.bi.mapper.bireport.AreaMapper;
import com.ruisitech.bi.service.etl.ElasticService;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.ext.service.DataControlInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.*;

@Service
@Scope("prototype")
public class OlapChartService extends BaseCompService {
    public static final String deftMvId = "mv.chart.tmp";
    @Autowired
    private TableCacheService cacheService;
    @Autowired
    private AreaMapper areaMapper;
    private Map<String, InputField> mvParams = new HashMap();
    @Autowired
    private TableCacheService cahceService;
    @Autowired
    private ElasticService elaService;
    @Autowired
    private DataControlInterface dataControl;
    private String sysUser = RSBIUtils.getConstant("sysUser");
    private String dbName = RSBIUtils.getConstant("dbName");

    public OlapChartService() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
        this.mvParams.clear();
    }

    public MVContext json2MV(ChartQueryDto chart, boolean xlsdata) throws Exception {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.chart.tmp");
        if (!xlsdata) {
            this.createChartDrill(mv, chart);
        }

        ChartContext cr = this.json2Chart(chart, false);
        cr.setXlsData(xlsdata);
        String sql = this.createSql(chart, 0, 0);
        GridDataCenterContext dc = this.createDataCenter(chart, sql);
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
    }

    public String createSql(ChartQueryDto chart, int release, int index) {
        TableInfoVO tinfo = this.cacheService.getTableInfo(chart.getTid());
        boolean qEs = false;
        if ("y".equals(tinfo.getUseEs()) && "y".equals(tinfo.getEsEnable())) {
            qEs = true;
        }

        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        List<DimDto> dims = chart.getChartJson().getDims();

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

        List<KpiDto> kpis = chart.getMkpi() ? chart.getMkpiJson() : chart.getKpiJson();
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
            sql.append(" from (" + tsql + ") ");
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

        //String ret;
        for(int i = 0; i < dims.size(); ++i) {
            DimDto dim = (DimDto)dims.get(i);
            if (dim.getType().equals("day")) {
                if (dim.getDay() != null) {
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " between '" + dim.getDay().getStartDay() + "' and '" + dim.getDay().getEndDay() + "'");
                } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                    ret = RSBIUtils.dealStringParam(dim.getVals());
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " in (" + ret + ")");
                }
            } else if (dim.getType().equals("month")) {
                if (dim.getMonth() != null) {
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " between '" + dim.getMonth().getStartMonth() + "' and '" + dim.getMonth().getEndMonth() + "'");
                } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                    ret = RSBIUtils.dealStringParam(dim.getVals());
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " in (" + ret + ")");
                }
            } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                ret = dim.getVals();
                if ("string".equalsIgnoreCase(dim.getValType())) {
                    ret = RSBIUtils.dealStringParam(dim.getVals());
                }

                sql.append(" and " + (dim.getTableColKey() != null && dim.getTableColKey().length() > 0 ? dim.getTableColKey() : (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname())) + " in (" + ret + ")");
            }
        }

        List<ParamDto> params = chart.getParams();

        int i;
        for(i = 0; params != null && i < params.size(); ++i) {
            ParamDto param = (ParamDto)params.get(i);
            int tid = param.getTid();
            int tid2 = new Integer(tinfo.getTid());
            String tp = param.getType();
            String colname = qEs ? (param.getColname().equals(param.getFromCol()) ? param.getFromCol() : param.getAlias()) : param.getColname();
            String alias = param.getAlias();
            if (tid == tid2 && (tp.equals("day") || tp.equals("month"))) {
                if (release == 0 && param.getSt() != null && param.getSt().length() > 0) {
                    sql.append(" and " + colname + " between '" + param.getSt() + "' and '" + param.getEnd() + "'");
                } else if (release == 1) {
                    sql.append(" and " + colname + " between '$s_" + alias + "' and '$e_" + alias + "'");
                } else if (release == 2) {
                    sql.append(" #if($" + alias + " != '') and " + colname + " = $" + alias + " #end");
                }
            } else if (tid == tid2) {
                if (release == 0 && param.getVals() != null && param.getVals().length() > 0) {
                    String vls = param.getVals();
                    if ("string".equalsIgnoreCase(param.getValType())) {
                        vls = RSBIUtils.dealStringParam(param.getVals());
                    }

                    sql.append(" and " + colname + " in (" + vls + ")");
                } else if (release == 1 || release == 2) {
                    sql.append(" #if($" + alias + " != '') and " + colname + " in ($extUtils.printVals($" + alias + ", '" + param.getValType() + "')) #end");
                }
            }
        }

        String tp;
        if (dims.size() > 0) {
            sql.append(" group by ");

            for(i = 0; i < dims.size(); ++i) {
                DimDto dim = (DimDto)dims.get(i);
                String key = dim.getTableColKey();
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
        Iterator var32 = chart.getKpiJson().iterator();

        while(var32.hasNext()) {
            KpiDto kpi = (KpiDto)var32.next();
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

        if (filter.length() > 0) {
            sql.append(" having 1=1 " + filter);
        }

        ret = sql.toString();
        ret = ret.replaceAll("##", "\\$extUtils.printJH()");
        return ret;
    }

    public ChartContext json2Chart(ChartQueryDto chart, boolean is3g) {
        ChartContext ctx = new ChartContextImpl();
        ctx.setLabel("T2");
        DimDto obj = chart.getChartJson().getXcol();
        String chartId;
        String tp2;
        String tableName;
        if (obj != null && obj.getId() != null) {
            String tp = obj.getType();
            chartId = obj.getAlias();
            tp2 = obj.getTableColKey();
            tableName = obj.getTableColName();
            if ("day".equals(tp)) {
                ctx.setDateType(tp);
                ctx.setDateTypeFmt(obj.getDateformat());
            }

            if (tp2 != null && tp2.length() > 0 && tableName != null && tableName.length() > 0) {
                ctx.setXcolDesc(chartId);
                ctx.setXcol(chartId + "2");
            } else {
                ctx.setXcolDesc(chartId);
                ctx.setXcol(chartId);
            }
        }

        KpiDto kpiInfo;
        if (chart.getMkpi()) {
            List<ChartYcolContext> ycols = new ArrayList();
            Iterator var16 = chart.getMkpiJson().iterator();

            while(var16.hasNext()) {
                KpiDto k = (KpiDto)var16.next();
                ChartYcolContext ycol = new ChartYcolContext();
                ycol.setName(k.getAlias());
                ycol.setDesc(k.getKpi_name());
                ycols.add(ycol);
            }

            kpiInfo = (KpiDto)chart.getMkpiJson().get(0);
            ctx.setYcols(ycols);
        } else {
            kpiInfo = (KpiDto)chart.getKpiJson().get(0);
            ctx.setYcol(kpiInfo.getAlias());
        }

        if (!chart.getMkpi() && chart.getKpiJson().size() > 1) {
            ctx.setY2col(((KpiDto)chart.getKpiJson().get(1)).getAlias());
        }

        if (!chart.getMkpi() && chart.getKpiJson().size() > 2) {
            ctx.setY3col(((KpiDto)chart.getKpiJson().get(2)).getAlias());
        }

        if (!chart.getMkpi()) {
            DimDto scol = chart.getChartJson().getScol();
            if (scol != null && scol.getId() != null) {
                tp2 = scol.getType();
                tableName = scol.getTableName();
                ctx.setScol(scol.getAlias());
                if (tableName != null && tableName.length() > 0 && ("frd".equals(tp2) || "year".equals(tp2) || "quarter".equals(tp2))) {
                    ctx.setScol(ctx.getScol() + "_desc");
                }
            }
        }

        ctx.setShape(chart.getChartJson().getType());
        if (is3g) {
            ctx.setWidth("100%");
        } else {
            ctx.setWidth("auto");
        }

        ctx.setHeight("280");
        chartId = "chart" + IdCreater.create();
        ctx.setId(chartId);
        List<ChartKeyContext> properties = new ArrayList();
        ChartKeyContext val1 = new ChartKeyContext("margin", is3g ? "30, 20, 50, 75" : "30, 20, 50, 90");
        properties.add(val1);
        if (!chart.getMkpi() && kpiInfo.getRate() != null) {
            ctx.setRate(kpiInfo.getRate());
        }

        if (!chart.getMkpi() && chart.getKpiJson().size() > 1) {
            ctx.setRate2(((KpiDto)chart.getKpiJson().get(1)).getRate());
        }

        if (!chart.getMkpi() && chart.getKpiJson().size() > 2) {
            ctx.setRate3(((KpiDto)chart.getKpiJson().get(2)).getRate());
        }

        String xdesc;
        if (chart.getMkpi()) {
            properties.add(new ChartKeyContext("ydesc", "度量"));
        } else {
            String ydesc = kpiInfo.getKpi_name() != null && kpiInfo.getKpi_name().length() != 0 ? kpiInfo.getKpi_name() : kpiInfo.getCol_name();
            xdesc = this.writerUnit(kpiInfo.getRate()) + (kpiInfo.getUnit() == null ? "" : kpiInfo.getUnit());
            if (xdesc != null && xdesc.length() > 0) {
                ydesc = "(" + xdesc + ")";
            }

            properties.add(new ChartKeyContext("ydesc", ydesc));
        }

        if (kpiInfo.getFmt() != null && kpiInfo.getFmt().length() > 0) {
            properties.add(new ChartKeyContext("formatCol", "kpi_fmt"));
        }

        if (kpiInfo.getUnit() != null && kpiInfo.getUnit().length() > 0) {
            properties.add(new ChartKeyContext("unitCol", "kpi_unit"));
        }

        properties.add(new ChartKeyContext("action", "drillChart"));
        if (!"map".equals(ctx.getShape())) {
            properties.add(new ChartKeyContext("showLabel", "false"));
        }

        if ("pie".equals(ctx.getShape())) {
            properties.add(new ChartKeyContext("showLegend", "true"));
            if (!is3g) {
                ctx.setWidth("600");
            }
        }

        if ("gauge".equals(ctx.getShape()) && !is3g) {
            ctx.setWidth("210");
        }

        if ("radar".equals(ctx.getShape()) && !is3g) {
            ctx.setHeight("340");
        }

        if ("map".equals(ctx.getShape()) && !is3g) {
            ctx.setWidth("600");
            ctx.setHeight("350");
        }

        if ("bubble".equals(ctx.getShape()) || "scatter".equals(ctx.getShape())) {
            KpiDto kpiInfo2 = (KpiDto)chart.getKpiJson().get(1);
            xdesc = kpiInfo2.getKpi_name() != null && kpiInfo2.getKpi_name().length() != 0 ? kpiInfo2.getKpi_name() : kpiInfo2.getCol_name();
            String xdesc2 = super.writerUnit(kpiInfo2.getRate()) + (kpiInfo2.getUnit() == null ? "" : kpiInfo2.getUnit());
            if (xdesc2 != null && xdesc2.length() > 0) {
                xdesc = "(" + xdesc2 + ")";
            }

            properties.add(new ChartKeyContext("xdesc", xdesc));
            properties.add(new ChartKeyContext("formatCol2", kpiInfo2.getFmt()));
            properties.add(new ChartKeyContext("unitCol2", kpiInfo2.getUnit()));
            if ("bubble".equals(ctx.getShape())) {
                KpiDto kpiInfo3 = (KpiDto)chart.getKpiJson().get(2);
                properties.add(new ChartKeyContext("formatCol3", kpiInfo3.getFmt()));
                properties.add(new ChartKeyContext("unitCol3", kpiInfo3.getUnit()));
            }
        }

        if ("line".equals(ctx.getShape()) || "column".equals(ctx.getShape())) {
            properties.add(new ChartKeyContext("legendLayout", "horizontal"));
        }

        if ("map".equals(ctx.getShape())) {
            properties.add(new ChartKeyContext("mapJson", chart.getChartJson().getMaparea()));
        }

        if (is3g) {
            properties.add(new ChartKeyContext("routeXaxisLable", "-45"));
        }

        ctx.setProperties(properties);
        return ctx;
    }

    public GridDataCenterContext createDataCenter(ChartQueryDto chart, String sql) throws IOException {
        GridDataCenterContext ctx = new GridDataCenterContextImpl();
        GridSetConfContext conf = new GridSetConfContext();
        TableInfoVO tinfo = this.cahceService.getTableInfo(chart.getTid());
        if ("y".equals(tinfo.getEsEnable()) && "y".equals(tinfo.getUseEs())) {
            //conf.setMaster(tinfo.getTname());
            //conf.setDatasetProvider(this.elaService);
        }

        ctx.setConf(conf);
        ctx.setId("DC-" + IdCreater.create());
        String name = TemplateManager.getInstance().createTemplate(sql);
        ctx.getConf().setTemplateName(name);
        String maparea = chart.getChartJson().getMaparea();
        String type = chart.getChartJson().getType();
        //int i;
        if ("map".equals(type) && maparea != null && maparea.length() > 0 && !"china".equals(maparea)) {
            for(int i = 0; i < chart.getChartJson().getDims().size(); ++i) {
                DimDto dim = (DimDto)chart.getChartJson().getDims().get(i);
                if (dim.getType().equals("city")) {
                    GridFilterContext filter = new GridFilterContext();
                    filter.setColumn(dim.getTableColName() != null && dim.getTableColName().length() > 0 ? dim.getTableColName() : dim.getAlias());
                    filter.setFilterType("in");
                    List<Area> ls = this.areaMapper.listCityByProvCode(this.sysUser, maparea);
                    StringBuffer sb = new StringBuffer();

                    for(i = 0; i < ls.size(); ++i) {
                        Area a = (Area)ls.get(i);
                        sb.append(a.getCityName());
                        if (i != ls.size() - 1) {
                            sb.append(",");
                        }
                    }

                    filter.setValue(sb.toString());
                    ctx.getProcess().add(filter);
                }
            }
        }

        List<String> orderCols = new ArrayList();
        List<KpiDto> kpis = chart.getMkpi() != null && chart.getMkpi() && chart.getMkpiJson() != null ? chart.getMkpiJson() : chart.getKpiJson();

        int i;
        for(i = 0; i < kpis.size(); ++i) {
            KpiDto kpi = (KpiDto)kpis.get(i);
            if (kpi.getSort() != null && kpi.getSort().length() > 0) {
                orderCols.add(kpi.getAlias() + "," + kpi.getSort());
            }
        }

        for(i = 0; i < chart.getChartJson().getDims().size(); ++i) {
            DimDto dim = (DimDto)chart.getChartJson().getDims().get(i);
            if (dim.getDimord() != null && dim.getDimord().length() > 0) {
                if (dim.getOrdcol() != null && dim.getOrdcol().length() > 0) {
                    orderCols.add(dim.getOrdcol() + "," + dim.getDimord());
                } else {
                    orderCols.add((dim.getTableColKey() != null && dim.getTableColKey().length() > 0 ? dim.getTableColKey() : dim.getAlias()) + "," + dim.getDimord());
                }
            }
        }

        if (orderCols.size() > 0) {
            GridSortContext sort = new GridSortContext();
            String[] cols = new String[orderCols.size()];
            String[] types = new String[orderCols.size()];

            for(i = 0; i < orderCols.size(); ++i) {
                String[] strs = ((String)orderCols.get(i)).split(",");
                cols[i] = strs[0];
                types[i] = strs[1];
            }

            sort.setColumn(cols);
            sort.setType(types);
            sort.setChangeOldOrder(true);
            ctx.getProcess().add(sort);
        }

        return ctx;
    }

    public void createChartDrill(MVContext mv, ChartQueryDto chart) {
        StringBuffer txt = new StringBuffer();
        txt.append("<div class=\"chartdrillmenu\">");
        int cnt = 0;

        for(Iterator var5 = chart.getChartJson().getParams().iterator(); var5.hasNext(); ++cnt) {
            DimDto dim = (DimDto)var5.next();
            if (cnt == 0) {
                txt.append("钻取维：");
            }

            txt.append("<span class=\"chartdrillDim\"><a href=\"javascript:;\" title=\"上卷\" onclick=\"chartGoupDim(" + chart.getId() + ", " + dim.getId() + ",'" + dim.getPos() + "',true)\" style=\"opacity:0.5\"></a>" + dim.getDimdesc() + "(" + dim.getValDesc() + ")</span>");
        }

        if (cnt == 0) {
            txt.append("<span class=\"charttip\">(点击图形节点进行钻取分析)</span>");
        }

        txt.append("</div>");
        TextContext text = new TextContextImpl();
        text.setText(txt.toString());
        text.setParent(mv);
        mv.getChildren().add(text);
    }

    public Map<String, InputField> getMvParams() {
        return this.mvParams;
    }
}
