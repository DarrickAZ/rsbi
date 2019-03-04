//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.bireport;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.cross.CrossCols;
import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContextImpl;
import com.ruisi.ext.engine.view.context.cross.CrossRows;
import com.ruisi.ext.engine.view.context.cross.RowDimContext;
import com.ruisi.ext.engine.view.context.cross.RowLinkContext;
import com.ruisi.ext.engine.view.context.dc.grid.ComputeMoveAvgContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridAccountContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContextImpl;
import com.ruisi.ext.engine.view.context.dc.grid.GridFilterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridSetConfContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridShiftContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridSortContext;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ispire.dc.grid.GridProcContext;
import com.ruisi.ispire.dc.grid.GridShift;
import com.ruisitech.bi.entity.bireport.DimDto;
import com.ruisitech.bi.entity.bireport.KpiDto;
import com.ruisitech.bi.entity.bireport.ParamDto;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.bireport.TableQueryDto;
import com.ruisitech.bi.entity.portal.PortalTableQuery;
import com.ruisitech.bi.service.etl.ElasticService;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.ext.service.DataControlInterface;
import com.ruisitech.ext.service.MyCrossFieldLoader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class OlapTableService extends BaseCompService {
    public static final String deftMvId = "mv.tmp.table";
    private Map<String, InputField> mvParams = new HashMap();
    private StringBuffer scripts = new StringBuffer();
    @Autowired
    private TableCacheService cahceService;
    @Autowired
    private ElasticService elaService;
    private List<GridFilterContext> filters = new ArrayList();
    @Autowired
    private DataControlInterface dataControl;
    private String dbName = RSBIUtils.getConstant("dbName");

    public OlapTableService() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
        this.mvParams.clear();
        this.filters.clear();
    }

    public MVContext json2MV(TableQueryDto table) throws ParseException, IOException {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.tmp.table");
        DimDto kpiOther = new DimDto();
        kpiOther.setType("kpiOther");
        table.getCols().add(kpiOther);
        CrossReportContext cr = this.json2Table(table);
        table.getCols().remove(table.getCols().size() - 1);
        String id = "report" + IdCreater.create();
        cr.setId(id);
        cr.setOut("olap");
        cr.setShowData(true);
        mv.getChildren().add(cr);
        cr.setParent(mv);
        Map<String, CrossReportContext> crs = new HashMap();
        crs.put(cr.getId(), cr);
        mv.setCrossReports(crs);
        String sql = this.createSql(table, 0);
        GridDataCenterContext dc = this.createDataCenter(sql, table);
        cr.setRefDataCetner(dc.getId());
        if (mv.getGridDataCenters() == null) {
            mv.setGridDataCenters(new HashMap());
        }

        mv.getGridDataCenters().put(dc.getId(), dc);
        String scripts = this.scripts.toString();
        mv.setScripts(scripts);
        return mv;
    }

    public GridDataCenterContext createDataCenter(String sql, TableQueryDto sqlVO) throws IOException {
        GridDataCenterContext ctx = new GridDataCenterContextImpl();
        GridSetConfContext conf = new GridSetConfContext();
        TableInfoVO tinfo = this.cahceService.getTableInfo(sqlVO.getTid());
        if ("y".equals(tinfo.getEsEnable()) && "y".equals(tinfo.getUseEs())) {
            conf.setMaster(tinfo.getTname());
            conf.setDatasetProvider(this.elaService);
        }

        ctx.setConf(conf);
        ctx.setId("DC-" + IdCreater.create());
        String name = TemplateManager.getInstance().createTemplate(sql);
        ctx.getConf().setTemplateName(name);
        List<String> orderCols = new ArrayList();

        int i;
        DimDto dim;
        for(i = 0; i < sqlVO.getCols().size(); ++i) {
            dim = (DimDto)sqlVO.getDims().get(i);
            if (dim.getDimord() != null && dim.getDimord().length() > 0) {
                if (dim.getOrdcol() != null && dim.getOrdcol().length() > 0) {
                    orderCols.add(dim.getOrdcol() + "," + dim.getDimord());
                } else {
                    orderCols.add(dim.getAlias() + "," + dim.getDimord());
                }
            }
        }

        KpiDto kpi;
        for(i = 0; i < sqlVO.getKpiJson().size(); ++i) {
            kpi = (KpiDto)sqlVO.getKpiJson().get(i);
            if (kpi.getSort() != null && kpi.getSort().length() > 0) {
                orderCols.add(kpi.getAlias() + "," + kpi.getSort());
            }
        }

        for(i = 0; i < sqlVO.getRows().size(); ++i) {
            dim = (DimDto)sqlVO.getDims().get(i);
            if (dim.getDimord() != null && dim.getDimord().length() > 0) {
                if (dim.getOrdcol() != null && dim.getOrdcol().length() > 0) {
                    orderCols.add(dim.getOrdcol() + "," + dim.getDimord());
                } else {
                    orderCols.add(dim.getAlias() + "," + dim.getDimord());
                }
            }
        }

        String[] jss;
        if (orderCols.size() > 0) {
            GridSortContext sort = new GridSortContext();
            String[] cols = new String[orderCols.size()];
            jss = new String[orderCols.size()];

            for(int i = 0; i < orderCols.size(); ++i) {
                String[] strs = ((String)orderCols.get(i)).split(",");
                cols[i] = strs[0];
                jss[i] = strs[1];
            }

            sort.setColumn(cols);
            sort.setType(jss);
            sort.setChangeOldOrder(true);
            ctx.getProcess().add(sort);
        }

        Iterator var19 = sqlVO.getKpiJson().iterator();

        while(true) {
            while(true) {
                do {
                    do {
                        if (!var19.hasNext()) {
                            var19 = this.filters.iterator();

                            while(var19.hasNext()) {
                                GridFilterContext filter = (GridFilterContext)var19.next();
                                ctx.getProcess().add(filter);
                            }

                            return ctx;
                        }

                        kpi = (KpiDto)var19.next();
                    } while(kpi.getCompute() == null);
                } while(kpi.getCompute().length() <= 0);

                GridProcContext proc;
                if ("zb".equals(kpi.getCompute())) {
                    proc = this.createAccount(sqlVO, kpi);
                    ctx.getProcess().add(proc);
                } else if (!"sxpm".equals(kpi.getCompute()) && !"jxpm".equals(kpi.getCompute())) {
                    if ("ydpj".equals(kpi.getCompute())) {
                        proc = this.createMoveAvg(sqlVO, kpi);
                        ctx.getProcess().add(proc);
                    } else {
                        jss = kpi.getCompute().split(",");
                        String[] var22 = jss;
                        int var23 = jss.length;

                        for(int var13 = 0; var13 < var23; ++var13) {
                            String js = var22[var13];
                            GridProcContext proc = this.createShift(sqlVO, kpi, js);
                            ctx.getProcess().add(proc);
                        }
                    }
                } else {
                    proc = this.createSort(sqlVO, kpi);
                    ctx.getProcess().add(proc);
                }
            }
        }
    }

    public CrossReportContext json2Table(TableQueryDto table) throws ParseException {
        CrossReportContext ctx = new CrossReportContextImpl();
        CrossCols cols = new CrossCols();
        cols.setCols(new ArrayList());
        ctx.setCrossCols(cols);
        CrossRows rows = new CrossRows();
        rows.setRows(new ArrayList());
        ctx.setCrossRows(rows);
        ctx.setLabel(table.getId());
        boolean uselink = false;
        Map<String, Object> link = table.getLink();
        if (table instanceof PortalTableQuery && "dashboard".equals(((PortalTableQuery)table).getUseIn())) {
            PortalTableQuery tq = (PortalTableQuery)table;
            if ("dashboard".equals(tq.getUseIn())) {
                RowLinkContext rlink = new RowLinkContext();
                rlink.setAction("window.dashboard.tableRowClick");
                ctx.getCrossRows().setLink(rlink);
                uselink = true;
            }
        } else if (link != null && !link.isEmpty()) {
            RowLinkContext rlink = new RowLinkContext();
            rlink.setParamName((String)link.get("paramName"));
            String url = (String)link.get("url");
            if (url != null && url.length() > 0) {
                rlink.setUrl(url);
            } else {
                String target = (String)link.get("target");
                String type = (String)link.get("type");
                rlink.setTarget(target.split(","));
                rlink.setType(type.split(","));
            }

            ctx.getCrossRows().setLink(rlink);
            uselink = true;
        }

        List<RowDimContext> drill = this.getDrillDim(table);
        if (drill != null && drill.size() > 0) {
            ctx.setDims(drill);
            uselink = true;
        }

        List<DimDto> colsStr = table.getCols();
        this.loopJsonField(colsStr, cols.getCols(), table.getKpiJson(), "col", uselink);
        List<DimDto> rowStr = table.getRows();
        this.loopJsonField(rowStr, rows.getRows(), table.getKpiJson(), "row", uselink);
        CrossField cf;
        if (cols.getCols().size() == 0) {
            cf = new CrossField();
            cf.setType("none");
            cf.setDesc("合计");
            cols.getCols().add(cf);
        }

        if (rows.getRows().size() == 0) {
            cf = new CrossField();
            cf.setType("none");
            cf.setDesc("合计");
            rows.getRows().add(cf);
        }

        return ctx;
    }

    private void loopJsonField(List<DimDto> arrays, List<CrossField> ls, List<KpiDto> kpis, String pos, boolean uselink) throws ParseException {
        List<CrossField> tmp = ls;

        for(int i = 0; i < arrays.size(); ++i) {
            DimDto obj = (DimDto)arrays.get(i);
            String type = obj.getType();
            String issum = obj.getIssum();
            ArrayList newCf;
            String alias;
            String code;
            String topType;
            String aggre;
            Iterator var28;
            CrossField tp;
            CrossField cf;
            Iterator var31;
            KpiDto kpi;
            CrossField cf;
            String tableColName;
            if (type.equals("kpiOther")) {
                newCf = new ArrayList();
                if (((List)tmp).size() == 0) {
                    var28 = kpis.iterator();

                    label289:
                    while(true) {
                        KpiDto kpi;
                        do {
                            do {
                                if (!var28.hasNext()) {
                                    break label289;
                                }

                                kpi = (KpiDto)var28.next();
                                cf = new CrossField();
                                cf.setType(type);
                                cf.setAggregation(kpi.getAggre());
                                cf.setAlias(kpi.getAlias());
                                cf.setFormatPattern(kpi.getFmt());
                                cf.setOrder("y".equals(kpi.getOrder()));
                                cf.setSubs(new ArrayList());
                                cf.setId(String.valueOf(kpi.getKpi_id()));
                                if (kpi.getRate() != null) {
                                    cf.setKpiRate(new BigDecimal(kpi.getRate()));
                                }

                                alias = this.writerUnit(cf.getKpiRate()) + kpi.getUnit();
                                if (alias != null && alias.length() > 0) {
                                    cf.setDesc(kpi.getKpi_name() + "(" + alias + ")");
                                } else {
                                    cf.setDesc(kpi.getKpi_name());
                                }

                                cf.setJsFunc(kpi.getFuncname());
                                code = kpi.getCode();
                                if (code != null && code.length() > 0) {
                                    try {
                                        code = URLDecoder.decode(code, "UTF-8");
                                    } catch (UnsupportedEncodingException var26) {
                                        var26.printStackTrace();
                                    }

                                    this.scripts.append("function " + cf.getJsFunc() + "(value,col,row,data){" + code + "}");
                                }

                                Map<String, Object> warn = kpi.getWarning();
                                if (warn != null && !warn.isEmpty()) {
                                    topType = this.createWarning(warn, kpi.getFmt(), this.scripts);
                                    cf.setJsFunc(topType);
                                }

                                ((List)tmp).add(cf);
                                newCf.add(cf);
                            } while(kpi.getCompute() == null);
                        } while(kpi.getCompute().length() <= 0);

                        String[] jss = kpi.getCompute().split(",");
                        String[] var38 = jss;
                        int var41 = jss.length;

                        for(int var21 = 0; var21 < var41; ++var21) {
                            String js = var38[var21];
                            CrossField compute = this.kpiCompute(js, kpi);
                            ((List)tmp).add(compute);
                            newCf.add(compute);
                        }
                    }
                } else {
                    var28 = ((List)tmp).iterator();

                    label260:
                    while(var28.hasNext()) {
                        tp = (CrossField)var28.next();
                        var31 = kpis.iterator();

                        while(true) {
                            do {
                                do {
                                    if (!var31.hasNext()) {
                                        continue label260;
                                    }

                                    kpi = (KpiDto)var31.next();
                                    cf = new CrossField();
                                    cf.setType(type);
                                    cf.setAggregation(kpi.getAggre());
                                    cf.setAlias(kpi.getAlias());
                                    cf.setFormatPattern(kpi.getFmt());
                                    cf.setOrder("y".equals(kpi.getOrder()));
                                    cf.setSubs(new ArrayList());
                                    cf.setId(kpi.getKpi_id().toString());
                                    if (kpi.getRate() != null) {
                                        cf.setKpiRate(new BigDecimal(kpi.getRate()));
                                    }

                                    tableColName = this.writerUnit(cf.getKpiRate()) + kpi.getUnit();
                                    if (tableColName != null && tableColName.length() > 0) {
                                        cf.setDesc(kpi.getKpi_name() + "(" + tableColName + ")");
                                    } else {
                                        cf.setDesc(kpi.getKpi_name());
                                    }

                                    cf.setJsFunc(kpi.getFuncname());
                                    topType = kpi.getCode();
                                    if (topType != null && topType.length() > 0) {
                                        try {
                                            topType = URLDecoder.decode(topType, "UTF-8");
                                        } catch (UnsupportedEncodingException var27) {
                                            var27.printStackTrace();
                                        }

                                        this.scripts.append("function " + cf.getJsFunc() + "(value,col,row,data){" + topType + "}");
                                    }

                                    Map<String, Object> warn = kpi.getWarning();
                                    if (warn != null && !warn.isEmpty()) {
                                        aggre = this.createWarning(warn, kpi.getFmt(), this.scripts);
                                        cf.setJsFunc(aggre);
                                    }

                                    tp.getSubs().add(cf);
                                    newCf.add(cf);
                                } while(kpi.getCompute() == null);
                            } while(kpi.getCompute().length() <= 0);

                            String[] jss = kpi.getCompute().split(",");
                            String[] var43 = jss;
                            int var44 = jss.length;

                            for(int var45 = 0; var45 < var44; ++var45) {
                                String js = var43[var45];
                                CrossField compute = this.kpiCompute(js, kpi);
                                tp.getSubs().add(compute);
                                newCf.add(compute);
                            }
                        }
                    }
                }

                tmp = newCf;
            } else {
                CrossField cf;
                String alias;
                String alias;
                CrossField sumcf;
                CrossField sumcf;
                if ("day".equals(type)) {
                    newCf = new ArrayList();
                    if (((List)tmp).size() == 0) {
                        cf = new CrossField();
                        cf.setCasParent(true);
                        cf.setTop(obj.getTop());
                        alias = obj.getTopType();
                        if (alias != null && alias.length() > 0) {
                            cf.setTopType(alias);
                        }

                        cf.setId(String.valueOf(obj.getId()));
                        cf.setType("frd");
                        cf.setDateType("day");
                        cf.setSort(obj.getDimord());
                        cf.setDateTypeFmt(obj.getDateformat());
                        cf.setUselink(uselink);
                        cf.setValue(obj.getVals());
                        cf.setMulti(true);
                        cf.setShowWeek(false);
                        cf.setDesc(obj.getDimdesc());
                        alias = obj.getAlias();
                        cf.setAlias(alias);
                        cf.setAliasDesc(alias);
                        cf.setSubs(new ArrayList());
                        ((List)tmp).add(cf);
                        newCf.add(cf);
                        if ("y".equals(issum)) {
                            sumcf = new CrossField();
                            sumcf.setType("none");
                            code = obj.getAggre();
                            if (code != null && code.length() > 0 && !"auto".equals(code)) {
                                sumcf.setDimAggre(code);
                            }

                            sumcf.setDesc(MyCrossFieldLoader.loadFieldName(sumcf.getDimAggre()));
                            sumcf.setSubs(new ArrayList());
                            ((List)tmp).add(sumcf);
                            newCf.add(sumcf);
                        }
                    } else {
                        var28 = ((List)tmp).iterator();

                        while(var28.hasNext()) {
                            tp = (CrossField)var28.next();
                            if (!tp.getType().equals("none")) {
                                cf = new CrossField();
                                cf.setCasParent(true);
                                cf.setTop(obj.getTop());
                                alias = obj.getTopType();
                                if (alias != null && alias.length() > 0) {
                                    cf.setTopType(alias);
                                }

                                cf.setId(String.valueOf(obj.getId()));
                                cf.setType("frd");
                                cf.setDateType("day");
                                cf.setSort(obj.getDimord());
                                cf.setDateTypeFmt(obj.getDateformat());
                                cf.setUselink(uselink);
                                cf.setValue(obj.getVals());
                                cf.setMulti(true);
                                cf.setShowWeek(false);
                                cf.setDesc(obj.getDimdesc());
                                code = obj.getAlias();
                                cf.setAlias(code);
                                cf.setAliasDesc(code);
                                cf.setSubs(new ArrayList());
                                cf.setParent(tp);
                                tp.getSubs().add(cf);
                                newCf.add(cf);
                                if ("y".equals(issum)) {
                                    sumcf = new CrossField();
                                    sumcf.setType("none");
                                    topType = obj.getAggre();
                                    if (topType != null && topType.length() > 0 && !"auto".equals(topType)) {
                                        sumcf.setDimAggre(topType);
                                    }

                                    sumcf.setDesc(MyCrossFieldLoader.loadFieldName(sumcf.getDimAggre()));
                                    sumcf.setSubs(new ArrayList());
                                    tp.getSubs().add(sumcf);
                                    newCf.add(sumcf);
                                }
                            }
                        }
                    }

                    tmp = newCf;
                } else if ("month".equals(type)) {
                    newCf = new ArrayList();
                    if (((List)tmp).size() == 0) {
                        cf = new CrossField();
                        cf.setCasParent(true);
                        cf.setTop(obj.getTop());
                        alias = obj.getTopType();
                        if (alias != null && alias.length() > 0) {
                            cf.setTopType(alias);
                        }

                        cf.setId(String.valueOf(obj.getId()));
                        cf.setType("frd");
                        cf.setDateType("month");
                        cf.setSort(obj.getDimord());
                        cf.setDateTypeFmt(obj.getDateformat());
                        cf.setUselink(uselink);
                        cf.setValue(obj.getVals());
                        cf.setMulti(true);
                        cf.setDesc(obj.getDimdesc());
                        alias = obj.getAlias();
                        cf.setAlias(alias);
                        cf.setAliasDesc(alias);
                        cf.setSubs(new ArrayList());
                        ((List)tmp).add(cf);
                        newCf.add(cf);
                        if ("y".equals(issum)) {
                            sumcf = new CrossField();
                            sumcf.setType("none");
                            code = obj.getAggre();
                            if (code != null && code.length() > 0 && !"auto".equals(code)) {
                                sumcf.setDimAggre(code);
                            }

                            sumcf.setDesc(MyCrossFieldLoader.loadFieldName(sumcf.getDimAggre()));
                            sumcf.setSubs(new ArrayList());
                            ((List)tmp).add(sumcf);
                            newCf.add(sumcf);
                        }
                    } else {
                        var28 = ((List)tmp).iterator();

                        while(var28.hasNext()) {
                            tp = (CrossField)var28.next();
                            if (!tp.getType().equals("none")) {
                                cf = new CrossField();
                                cf.setCasParent(true);
                                cf.setTop(obj.getTop());
                                alias = obj.getTopType();
                                if (alias != null && alias.length() > 0) {
                                    cf.setTopType(alias);
                                }

                                cf.setId(String.valueOf(obj.getId()));
                                cf.setType("frd");
                                cf.setDateType("month");
                                cf.setDateTypeFmt(obj.getDateformat());
                                cf.setSort(obj.getDimord());
                                cf.setUselink(uselink);
                                cf.setValue(obj.getVals());
                                cf.setMulti(true);
                                cf.setDesc(obj.getDimdesc());
                                code = obj.getAlias();
                                cf.setAlias(code);
                                cf.setAliasDesc(code);
                                cf.setSubs(new ArrayList());
                                cf.setParent(tp);
                                tp.getSubs().add(cf);
                                newCf.add(cf);
                                if ("y".equals(issum)) {
                                    sumcf = new CrossField();
                                    sumcf.setType("none");
                                    topType = obj.getAggre();
                                    if (topType != null && topType.length() > 0 && !"auto".equals(topType)) {
                                        sumcf.setDimAggre(topType);
                                    }

                                    sumcf.setDesc(MyCrossFieldLoader.loadFieldName(sumcf.getDimAggre()));
                                    sumcf.setSubs(new ArrayList());
                                    tp.getSubs().add(sumcf);
                                    newCf.add(sumcf);
                                }
                            }
                        }
                    }

                    tmp = newCf;
                } else {
                    newCf = new ArrayList();
                    if (((List)tmp).size() == 0) {
                        cf = new CrossField();
                        cf.setType("frd");
                        cf.setId(String.valueOf(obj.getId()));
                        cf.setDesc(obj.getDimdesc());
                        cf.setSort(obj.getDimord());
                        alias = obj.getAlias();
                        alias = obj.getTableColKey();
                        alias = obj.getTableColName();
                        if (alias != null && alias.length() != 0 && alias != null && alias.length() != 0) {
                            cf.setAlias(alias);
                            cf.setAliasDesc(alias + "2");
                        } else {
                            cf.setAlias(alias);
                            cf.setAliasDesc(alias);
                        }

                        cf.setCasParent(true);
                        cf.setTop(obj.getTop());
                        code = obj.getTopType();
                        if (code != null && code.length() > 0) {
                            cf.setTopType(code);
                        }

                        cf.setUselink(uselink);
                        cf.setValue(obj.getVals());
                        cf.setMulti(true);
                        cf.setSubs(new ArrayList());
                        ((List)tmp).add(cf);
                        newCf.add(cf);
                        if ("y".equals(issum)) {
                            sumcf = new CrossField();
                            sumcf.setType("none");
                            topType = obj.getAggre();
                            if (topType != null && topType.length() > 0 && !"auto".equals(topType)) {
                                sumcf.setDimAggre(topType);
                            }

                            sumcf.setDesc(MyCrossFieldLoader.loadFieldName(sumcf.getDimAggre()));
                            sumcf.setSubs(new ArrayList());
                            ((List)tmp).add(sumcf);
                            newCf.add(sumcf);
                        }
                    } else {
                        var28 = ((List)tmp).iterator();

                        label344:
                        while(true) {
                            do {
                                while(true) {
                                    if (!var28.hasNext()) {
                                        break label344;
                                    }

                                    tp = (CrossField)var28.next();
                                    if (tp.getType().equals("none")) {
                                        break;
                                    }

                                    cf = new CrossField();
                                    cf.setType("frd");
                                    cf.setId(String.valueOf(obj.getId()));
                                    cf.setDesc(obj.getDimdesc());
                                    cf.setSort(obj.getDimord());
                                    alias = obj.getAlias();
                                    code = obj.getTableColKey();
                                    tableColName = obj.getTableColName();
                                    if (code != null && code.length() != 0 && tableColName != null && tableColName.length() != 0) {
                                        cf.setAlias(alias);
                                        cf.setAliasDesc(alias + "2");
                                    } else {
                                        cf.setAlias(alias);
                                        cf.setAliasDesc(alias);
                                    }

                                    cf.setCasParent(true);
                                    cf.setTop(obj.getTop());
                                    topType = obj.getTopType();
                                    if (topType != null && topType.length() > 0) {
                                        cf.setTopType(topType);
                                    }

                                    cf.setUselink(uselink);
                                    cf.setValue(obj.getVals());
                                    cf.setMulti(true);
                                    cf.setSubs(new ArrayList());
                                    cf.setParent(tp);
                                    tp.getSubs().add(cf);
                                    newCf.add(cf);
                                    if ("y".equals(issum)) {
                                        CrossField sumcf = new CrossField();
                                        sumcf.setType("none");
                                        aggre = obj.getAggre();
                                        if (aggre != null && aggre.length() > 0 && !"auto".equals(aggre)) {
                                            sumcf.setDimAggre(aggre);
                                        }

                                        sumcf.setDesc(MyCrossFieldLoader.loadFieldName(sumcf.getDimAggre()));
                                        sumcf.setSubs(new ArrayList());
                                        tp.getSubs().add(sumcf);
                                        newCf.add(sumcf);
                                    }
                                }
                            } while(!pos.equals("col"));

                            var31 = kpis.iterator();

                            while(var31.hasNext()) {
                                kpi = (KpiDto)var31.next();
                                cf = new CrossField();
                                cf.setType("kpiOther");
                                cf.setDesc(kpi.getKpi_name());
                                cf.setAggregation(kpi.getAggre());
                                cf.setAlias(kpi.getAlias());
                                cf.setFormatPattern(kpi.getFmt());
                                cf.setSubs(new ArrayList());
                                cf.setId(String.valueOf(kpi.getKpi_id()));
                                if (kpi.getRate() != null) {
                                    cf.setKpiRate(new BigDecimal(kpi.getRate()));
                                }

                                tp.getSubs().add(cf);
                                cf.setParent(tp);
                            }
                        }
                    }

                    tmp = newCf;
                }
            }
        }

    }

    public String createSql(TableQueryDto table, int release) throws ParseException {
        TableInfoVO tinfo = this.cahceService.getTableInfo(table.getTid());
        boolean qEs = false;
        if ("y".equals(tinfo.getUseEs()) && "y".equals(tinfo.getEsEnable())) {
            qEs = true;
        }

        int jstype = table.getKpiComputeType();
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        List<DimDto> dims = table.getDims();

        String tsql;
        for(int i = 0; i < dims.size(); ++i) {
            DimDto dim = (DimDto)dims.get(i);
            tsql = dim.getTableColKey();
            String txt = dim.getTableColName();
            if (tsql != null && txt != null && tsql.length() > 0 && txt.length() > 0) {
                sql.append(tsql + " as " + dim.getAlias() + ", " + txt + " as " + dim.getAlias() + "2,");
            } else {
                sql.append(qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname());
                sql.append(" " + dim.getAlias() + ", ");
            }
        }

        List<KpiDto> kpis = table.getKpiJson();
        if (kpis.size() == 0) {
            sql.append(" max(0) kpi_value ");
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
        if (tsql != null && tsql.length() != 0) {
            sql.append(" from (" + tsql + ") ");
        } else {
            sql.append(" from " + tname + " ");
        }

        sql.append(" where 1=1 ");
        if (this.dataControl != null) {
            sql.append(this.dataControl.process(RSBIUtils.getLoginUserInfo(), tname));
        }

        String key;
        for(int i = 0; i < dims.size(); ++i) {
            DimDto dim = (DimDto)dims.get(i);
            String vls;
            GridFilterContext filter;
            String[] q;
            GridFilterContext filter;
            if (dim.getType().equals("day")) {
                if (dim.getDay() != null) {
                    vls = dim.getDay().getStartDay();
                    key = dim.getDay().getEndDay();
                    if (jstype != 0) {
                        q = this.resetBetween(vls, key, dim.getType(), dim.getDateformat(), jstype);
                        vls = q[0];
                        key = q[1];
                        filter = new GridFilterContext();
                        filter.setColumn(dim.getAlias());
                        filter.setFilterType("between");
                        filter.setValue(dim.getDay().getStartDay());
                        filter.setValue2(dim.getDay().getEndDay());
                        filter.setDateFormat(dim.getDateformat());
                        this.filters.add(filter);
                    }

                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " between '" + vls + "' and '" + key + "'");
                } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                    vls = dim.getVals();
                    if (jstype != 0) {
                        vls = this.resetVals(vls, dim.getType(), dim.getDateformat(), jstype);
                        filter = new GridFilterContext();
                        filter.setColumn(dim.getAlias());
                        filter.setFilterType("in");
                        filter.setValue(dim.getVals());
                        this.filters.add(filter);
                    }

                    vls = RSBIUtils.dealStringParam(vls);
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " in (" + vls + ")");
                }
            } else if (dim.getType().equals("month")) {
                if (dim.getMonth() != null) {
                    vls = dim.getMonth().getStartMonth();
                    key = dim.getMonth().getEndMonth();
                    if (jstype != 0) {
                        q = this.resetBetween(vls, key, dim.getType(), dim.getDateformat(), jstype);
                        vls = q[0];
                        key = q[1];
                        filter = new GridFilterContext();
                        filter.setColumn(dim.getAlias());
                        filter.setFilterType("between");
                        filter.setValue(dim.getMonth().getStartMonth());
                        filter.setValue2(dim.getMonth().getEndMonth());
                        filter.setDateFormat(dim.getDateformat());
                        this.filters.add(filter);
                    }

                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " between '" + vls + "' and '" + key + "'");
                } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                    vls = dim.getVals();
                    if (jstype != 0) {
                        vls = this.resetVals(vls, dim.getType(), dim.getDateformat(), jstype);
                        filter = new GridFilterContext();
                        filter.setColumn(dim.getAlias());
                        filter.setFilterType("in");
                        filter.setValue(dim.getVals());
                        this.filters.add(filter);
                    }

                    vls = RSBIUtils.dealStringParam(vls);
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " in (" + vls + ")");
                }
            } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                vls = null;
                if (("year".equals(dim.getType()) || "quarter".equals(dim.getType())) && jstype != 0) {
                    vls = this.resetVals(dim.getVals(), dim.getType(), dim.getDateformat(), jstype);
                    filter = new GridFilterContext();
                    filter.setColumn(dim.getAlias());
                    filter.setFilterType("in");
                    filter.setValue(dim.getVals());
                    this.filters.add(filter);
                } else {
                    vls = dim.getVals();
                }

                if ("string".equalsIgnoreCase(dim.getValType())) {
                    vls = RSBIUtils.dealStringParam(vls);
                }

                sql.append(" and " + (dim.getTableColKey() != null && dim.getTableColKey().length() > 0 ? dim.getTableColKey() : (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname())) + " in (" + vls + ")");
            }
        }

        List<ParamDto> params = table.getParams();

        int i;
        for(i = 0; params != null && i < params.size(); ++i) {
            ParamDto param = (ParamDto)params.get(i);
            int tid = param.getTid();
            int tid2 = tinfo.getTid();
            String tp = param.getType();
            String colname = qEs ? (param.getColname().equals(param.getFromCol()) ? param.getFromCol() : param.getAlias()) : param.getColname();
            String alias = param.getAlias();
            String valType = param.getValType();
            String dateformat = param.getDateformat();
            String vls;
            GridFilterContext filter;
            if (tid != tid2 || !tp.equals("day") && !tp.equals("month")) {
                if (tid == tid2) {
                    if (release == 0 && param.getVals() != null && param.getVals().length() > 0) {
                        vls = param.getVals();
                        if (jstype != 0 && ("year".equals(tp) || "quarter".equals(tp))) {
                            vls = this.resetVals(vls, tp, param.getDateformat(), jstype);
                        }

                        if ("string".equalsIgnoreCase(valType)) {
                            vls = RSBIUtils.dealStringParam(vls);
                        }

                        sql.append(" and " + colname + " in (" + vls + ")");
                    } else if (release == 1 || release == 2) {
                        sql.append(" #if($" + alias + " != '') and " + colname + " in ($extUtils.printVals($myUtils.resetVals($" + alias + ",'" + tp + "','" + dateformat + "', " + jstype + "), '" + valType + "')) #end");
                    }

                    if (jstype != 0 && ("year".equals(tp) || "quarter".equals(tp))) {
                        filter = new GridFilterContext();
                        filter.setColumn(param.getAlias());
                        filter.setFilterType("in");
                        if (release == 0 && param.getVals() != null && param.getVals().length() > 0) {
                            filter.setValue(param.getVals());
                        } else if (release == 1 || release == 2) {
                            filter.setValue("${" + colname + "}");
                        }

                        this.filters.add(filter);
                    }
                }
            } else {
                String ostart;
                String oend;
                if (release == 0 && param.getSt() != null && param.getSt().length() > 0) {
                    vls = param.getSt();
                    ostart = param.getEnd();
                    oend = vls;
                    String end = ostart;
                    if (jstype != 0) {
                        String[] q = this.resetBetween(vls, ostart, tp, param.getDateformat(), jstype);
                        oend = q[0];
                        end = q[1];
                    }

                    sql.append(" and " + colname + " between '" + oend + "' and '" + end + "'");
                } else if (release == 1) {
                    sql.append(" #if($s_" + alias + " != '' && $e_" + alias + " != '') and " + colname + " between $myUtils.resetBetween($s_" + alias + ", $e_" + alias + ", '" + tp + "', '" + dateformat + "', " + jstype + ") #end");
                } else if (release == 2) {
                    sql.append(" #if($" + alias + " != '') and " + colname + " = $" + alias + " #end");
                }

                if (jstype != 0) {
                    filter = new GridFilterContext();
                    filter.setColumn(param.getAlias());
                    filter.setFilterType("between");
                    filter.setDateFormat(param.getDateformat());
                    if (release == 0 && param.getSt() != null && param.getSt().length() > 0) {
                        ostart = param.getSt();
                        oend = param.getEnd();
                        filter.setValue(ostart);
                        filter.setValue2(oend);
                    } else if (release == 1) {
                        filter.setValue("${s_" + alias + "}");
                        filter.setValue2("${e_" + alias + "}");
                    } else if (release == 2) {
                        filter.setValue("${" + alias + "}");
                    }

                    this.filters.add(filter);
                }
            }
        }

        String tp;
        if (dims.size() > 0) {
            sql.append(" group by ");

            for(i = 0; i < dims.size(); ++i) {
                DimDto dim = (DimDto)dims.get(i);
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
        Iterator var44 = table.getKpiJson().iterator();

        while(var44.hasNext()) {
            KpiDto kpi = (KpiDto)var44.next();
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

        return sql.toString();
    }

    public List<RowDimContext> getDrillDim(TableQueryDto table) {
        List<Map<String, Object>> drillDim = table.getDrillDim();
        if (drillDim != null && !drillDim.isEmpty()) {
            List<RowDimContext> ret = new ArrayList();

            for(int i = 0; i < drillDim.size(); ++i) {
                Map<String, Object> obj = (Map)drillDim.get(i);
                RowDimContext dim = new RowDimContext();
                String tableColKey = (String)obj.get("tableColKey");
                String tableColName = (String)obj.get("tableColName");
                String code = (String)obj.get("code");
                String name = (String)obj.get("name");
                if (tableColKey != null && tableColKey.length() != 0) {
                    dim.setCode(tableColKey);
                    dim.setName(tableColName);
                    dim.setCodeDesc(tableColName);
                } else {
                    dim.setCode(code);
                    dim.setName(name);
                    dim.setCodeDesc(code);
                }

                dim.setType("frd");
                ret.add(dim);
            }

            return ret;
        } else {
            return null;
        }
    }

    public String[] resetBetween(String start, String end, String type, String dateFormat, int jstype) throws ParseException {
        if (jstype == 0) {
            return new String[]{start, end};
        } else {
            String nval;
            if ("day".equals(type)) {
                if (jstype == 1 || jstype == 3) {
                    nval = GridShift.getDateShiftValue(start, type, dateFormat, "sq");
                    start = nval;
                }

                if (jstype == 2 || jstype == 3) {
                    nval = GridShift.getDateShiftValue(start, type, dateFormat, "tq");
                    start = nval;
                }

                return new String[]{start, end};
            } else if (!"month".equals(type)) {
                return null;
            } else {
                if (jstype == 1 || jstype == 3) {
                    nval = GridShift.getDateShiftValue(start, type, dateFormat, "sq");
                    start = nval;
                }

                if (jstype == 2 || jstype == 3) {
                    nval = GridShift.getDateShiftValue(start, type, dateFormat, "tq");
                    start = nval;
                }

                return new String[]{start, end};
            }
        }
    }

    private GridProcContext createSort(TableQueryDto table, KpiDto kpi) {
        GridSortContext proc = new GridSortContext();
        proc.setAppendOrder(true);
        proc.setChangeOldOrder(false);
        StringBuffer sb = new StringBuffer("");
        StringBuffer orderSb = new StringBuffer("");

        for(int i = 0; i < table.getCols().size(); ++i) {
            DimDto dim = (DimDto)table.getDims().get(i);
            sb.append(dim.getAlias());
            sb.append(",");
            orderSb.append(dim.getDimord());
            orderSb.append(",");
        }

        sb.append(kpi.getAlias());
        orderSb.append("sxpm".equals(kpi.getCompute()) ? "asc" : "desc");
        proc.setColumn(sb.toString().split(","));
        proc.setType(orderSb.toString().split(","));
        return proc;
    }

    private GridProcContext createShift(TableQueryDto table, KpiDto kpi, String compute) {
        int minDate = 4;
        DimDto minDim = null;
        Iterator var6 = table.getDims().iterator();

        String str;
        while(var6.hasNext()) {
            DimDto dim = (DimDto)var6.next();
            str = dim.getType();
            if (!"frd".equalsIgnoreCase(str)) {
                int curDate = this.type2value(str);
                if (curDate <= minDate) {
                    minDate = curDate;
                    minDim = dim;
                }
            }
        }

        GridShiftContext proc = new GridShiftContext();
        proc.setDateType(minDim.getType());
        proc.setDateFormat(minDim.getDateformat());
        proc.setDateColumn(minDim.getAlias());
        proc.setComputeType(compute);
        proc.setKpiColumn(new String[]{kpi.getAlias()});
        StringBuffer sb = new StringBuffer("");
        Iterator var13 = table.getDims().iterator();

        while(var13.hasNext()) {
            DimDto dim = (DimDto)var13.next();
            String tp = dim.getType();
            if (!"year".equals(tp) && !"quarter".equals(tp) && !"month".equals(tp) && !"day".equals(tp)) {
                sb.append(dim.getAlias());
                sb.append(",");
            }
        }

        if (sb.length() > 0) {
            str = sb.substring(0, sb.length() - 1);
            proc.setKeyColumns(str.split(","));
        }

        return proc;
    }

    private GridProcContext createMoveAvg(TableQueryDto table, KpiDto kpi) {
        ComputeMoveAvgContext ctx = new ComputeMoveAvgContext();
        ctx.setAlias(kpi.getAlias() + "_ydpj");
        ctx.setColumn(kpi.getAlias());
        ctx.setStep(3);
        return ctx;
    }

    private GridProcContext createAccount(TableQueryDto table, KpiDto kpi) {
        GridAccountContext proc = new GridAccountContext();
        proc.setColumn(kpi.getAlias());
        StringBuffer sb = new StringBuffer("");

        for(int i = 0; i < table.getCols().size(); ++i) {
            DimDto dim = (DimDto)table.getCols().get(i);
            sb.append(dim.getColname());
            sb.append(",");
        }

        if (sb.length() > 0) {
            String str = sb.substring(0, sb.length() - 1);
            proc.setGroupDim(str.split(","));
        }

        return proc;
    }

    private CrossField kpiCompute(String compute, KpiDto kpi) {
        CrossField cf = new CrossField();
        if ("zb".equals(compute)) {
            cf.setDesc("占比");
            cf.setAggregation("avg");
            cf.setAlias(kpi.getAlias() + "_zb");
            cf.setFormatPattern("0.00%");
        } else if (!"sxpm".equals(compute) && !"jxpm".equals(compute)) {
            if ("ydpj".equals(compute)) {
                cf.setDesc("移动平均");
                cf.setAggregation(kpi.getAggre());
                cf.setAlias(kpi.getAlias() + "_ydpj");
                cf.setFormatPattern(kpi.getFmt());
                if (kpi.getRate() != null) {
                    cf.setKpiRate(new BigDecimal(kpi.getRate()));
                }
            } else if ("sq".equals(compute)) {
                cf.setDesc("上期值");
                cf.setAggregation(kpi.getAggre());
                cf.setAlias(kpi.getAlias() + "_sq");
                cf.setFormatPattern(kpi.getFmt());
                if (kpi.getRate() != null) {
                    cf.setKpiRate(new BigDecimal(kpi.getRate()));
                }
            } else if ("tq".equals(compute)) {
                cf.setDesc("同期值");
                cf.setAggregation(kpi.getAggre());
                cf.setAlias(kpi.getAlias() + "_tq");
                cf.setFormatPattern(kpi.getFmt());
                if (kpi.getRate() != null) {
                    cf.setKpiRate(new BigDecimal(kpi.getRate()));
                }
            } else if ("zje".equals(compute)) {
                cf.setDesc("增减额");
                cf.setAggregation(kpi.getAggre());
                cf.setAlias(kpi.getAlias() + "_zje");
                cf.setFormatPattern(kpi.getFmt());
                if (kpi.getRate() != null) {
                    cf.setKpiRate(new BigDecimal(kpi.getRate()));
                }

                cf.setFinanceFmt(true);
            } else if ("hb".equals(compute)) {
                cf.setDesc("环比");
                cf.setAggregation("avg");
                cf.setAlias(kpi.getAlias() + "_hb");
                cf.setFormatPattern("0.00%");
                cf.setFinanceFmt(true);
            } else if ("tb".equals(compute)) {
                cf.setDesc("同比");
                cf.setAggregation("avg");
                cf.setAlias(kpi.getAlias() + "_tb");
                cf.setFormatPattern("0.00%");
                cf.setFinanceFmt(true);
            }
        } else {
            cf.setDesc(("sxpm".equals(compute) ? "升序" : "降序") + "排名");
            cf.setAggregation("avg");
            cf.setAlias(kpi.getAlias() + "_order");
            cf.setFormatPattern("#,###");
            cf.setStyleClass("pms");
            cf.setStyleToLine(true);
        }

        cf.setType("kpiOther");
        cf.setId("ext_" + kpi.getKpi_id() + "_" + compute);
        return cf;
    }

    public String resetVals(String inputval, String type, String dateFormat, int jstype) throws ParseException {
        if (jstype == 0) {
            return inputval;
        } else {
            String[] vals = inputval.split(",");
            List<String> rets = new ArrayList();
            String[] var7 = vals;
            int var8 = vals.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String val = var7[var9];
                if (!rets.contains(val)) {
                    rets.add(val);
                }

                String nval;
                if (jstype == 1 || jstype == 3) {
                    nval = GridShift.getDateShiftValue(val, type, dateFormat, "sq");
                    if (!rets.contains(nval)) {
                        rets.add(nval);
                    }
                }

                if (jstype == 2 || jstype == 3) {
                    nval = GridShift.getDateShiftValue(val, type, dateFormat, "tq");
                    if (!rets.contains(nval)) {
                        rets.add(nval);
                    }
                }
            }

            return this.list2String(rets);
        }
    }

    private String list2String(List<String> rets) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < rets.size(); ++i) {
            String ret = (String)rets.get(i);
            sb.append(ret);
            if (i != rets.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    public Map<String, InputField> getMvParams() {
        return this.mvParams;
    }

    public StringBuffer getScripts() {
        return this.scripts;
    }
}
