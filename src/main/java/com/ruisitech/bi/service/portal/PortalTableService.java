//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.portal;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.cross.BaseKpiField;
import com.ruisi.ext.engine.view.context.cross.CrossKpi;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.RowDimContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContextImpl;
import com.ruisi.ext.engine.view.context.dc.grid.GridSetConfContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridSortContext;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisitech.bi.entity.bireport.DimDto;
import com.ruisitech.bi.entity.bireport.KpiDto;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.portal.LinkAcceptDto;
import com.ruisitech.bi.entity.portal.PortalTableQuery;
import com.ruisitech.bi.service.bireport.BaseCompService;
import com.ruisitech.bi.service.bireport.OlapTableService;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.service.etl.ElasticService;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.ext.service.DataControlInterface;
import java.io.IOException;
import java.math.BigDecimal;
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
public class PortalTableService extends BaseCompService {
    public static final String deftMvId = "mv.portal.table";
    private Map<String, InputField> mvParams = new HashMap();
    @Autowired
    private DataControlInterface dataControl;
    @Autowired
    private TableCacheService cacheService;
    @Autowired
    private ElasticService elaService;
    @Autowired
    private OlapTableService tableService;

    public PortalTableService() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
        this.mvParams.clear();
    }

    public MVContext json2MV(PortalTableQuery table) throws Exception {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.portal.table");
        mv.setHideMV(true);
        super.parserHiddenParam(table.getPortalParams(), mv, this.mvParams);
        CrossReportContext cr = null;
        CrossKpi mybaseKpi = null;
        List<DimDto> cols = table.getCols();
        if (cols.size() > 0 && table.getKpiJson().size() == 1) {
            KpiDto kpi = (KpiDto)table.getKpiJson().get(0);
            CrossKpi baseKpi = new BaseKpiField();
            baseKpi.setAggregation(kpi.getAggre());
            baseKpi.setAlias(kpi.getAlias());
            baseKpi.setFormatPattern(kpi.getFmt());
            baseKpi.setKpiRate(kpi.getRate() == null ? null : new BigDecimal(kpi.getRate()));
            cr = this.tableService.json2Table(table);
            cr.setBaseKpi(baseKpi);
        } else {
            DimDto kpiOther = new DimDto();
            kpiOther.setType("kpiOther");
            cols.add(kpiOther);
            cr = this.tableService.json2Table(table);
            cols.remove(cols.size() - 1);
        }

        String id = "report" + IdCreater.create();
        cr.setId(id);
        cr.setOut("lockUI");
        if (table.getDashboardStyle() != null && table.getDashboardStyle().length() > 0) {
            cr.setStyle(table.getDashboardStyle());
        }

        String height = table.getHeight();
        if (height != null && height.length() > 0) {
            cr.setHeight(height);
        }

        cr.setShowData(true);
        TableInfoVO tinfo = this.cacheService.getTableInfo(table.getTid());
        String sqls = this.createSql(table, 0, 0, 0);
        GridDataCenterContext dc = this.createDataCenter(sqls, table);
        cr.setRefDataCetner(dc.getId());
        if (mv.getGridDataCenters() == null) {
            mv.setGridDataCenters(new HashMap());
        }

        mv.getGridDataCenters().put(dc.getId(), dc);
        List<RowDimContext> drillDims = cr.getDims();

        for(int i = 0; drillDims != null && i < drillDims.size(); ++i) {
            RowDimContext drillDim = (RowDimContext)drillDims.get(i);
            sqls = this.createSql(table, 0, i + 1, 0);
            GridDataCenterContext drillDc = this.createDataCenter(sqls, table);
            drillDim.setRefDataCenter(drillDc.getId());
            mv.getGridDataCenters().put(drillDc.getId(), drillDc);
        }

        mv.getChildren().add(cr);
        cr.setParent(mv);
        Map<String, CrossReportContext> crs = new HashMap();
        crs.put(cr.getId(), cr);
        mv.setCrossReports(crs);
        String scripts = this.tableService.getScripts().toString();
        if (scripts.length() > 0) {
            mv.setScripts(scripts);
        }

        return mv;
    }

    public String createSql(PortalTableQuery table, int release, int drillLevel, int index) {
        TableInfoVO tinfo = this.cacheService.getTableInfo(table.getTid());
        boolean qEs = false;
        if ("y".equals(tinfo.getUseEs()) && "y".equals(tinfo.getEsEnable())) {
            qEs = true;
        }

        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        List<DimDto> dims = table.getDims();

        String tname;
        String tsql;
        for(int i = 0; i < dims.size(); ++i) {
            DimDto dim = (DimDto)dims.get(i);
            tname = dim.getTableColKey();
            tsql = dim.getTableColName();
            if (tname != null && tsql != null && tname.length() > 0 && tsql.length() > 0) {
                sql.append(tname + " as " + dim.getAlias() + ", " + tsql + " as " + dim.getAlias() + "2,");
            } else {
                sql.append((qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " " + dim.getAlias() + ", ");
            }
        }

        List<Map<String, Object>> drillDim = table.getDrillDim();
        String ret;
        String col;
        String alias;
        String key;
        if (drillDim != null && drillDim.size() >= drillLevel) {
            for(int i = 0; i < drillLevel; ++i) {
                Map<String, Object> dim = (Map)drillDim.get(i);
                tsql = (String)dim.get("tableColKey");
                ret = (String)dim.get("tableColName");
                col = (String)dim.get("fromCol");
                if (tsql != null && ret != null && tsql.length() > 0 && ret.length() > 0) {
                    sql.append(tsql + ", " + ret + ",");
                } else {
                    alias = (String)dim.get("colname");
                    key = (String)dim.get("code");
                    sql.append((qEs ? (alias.equals(col) ? col : key) : alias) + " " + key + ", ");
                }
            }
        }

        List<KpiDto> kpis = table.getKpiJson();
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

        tname = tinfo.getTname();
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

        for(int i = 0; i < dims.size(); ++i) {
            DimDto dim = (DimDto)dims.get(i);
            if (dim.getType().equals("day")) {
                if (dim.getDay() != null) {
                    alias = dim.getDay().getStartDay();
                    key = dim.getDay().getEndDay();
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " between '" + alias + "' and '" + key + "'");
                } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                    alias = dim.getVals();
                    alias = RSBIUtils.dealStringParam(alias);
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " in (" + alias + ")");
                }
            } else if (dim.getType().equals("month")) {
                if (dim.getMonth() != null) {
                    alias = dim.getMonth().getStartMonth();
                    key = dim.getMonth().getEndMonth();
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " between '" + alias + "' and '" + key + "'");
                } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                    alias = dim.getVals();
                    alias = RSBIUtils.dealStringParam(alias);
                    sql.append(" and " + (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname()) + " in (" + alias + ")");
                }
            } else if (dim.getVals() != null && dim.getVals().length() > 0) {
                alias = dim.getVals();
                if ("string".equalsIgnoreCase(dim.getValType())) {
                    alias = RSBIUtils.dealStringParam(alias);
                }

                sql.append(" and " + (dim.getTableColKey() != null && dim.getTableColKey().length() > 0 ? dim.getTableColKey() : (qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname())) + " in (" + alias + ")");
            }
        }

        sql.append(super.dealCubeParams(table.getCompParams(), "dim", tinfo));
        sql.append(super.dealDashboardParams(table.getDashboardParam(), tinfo));
        if (drillLevel == 1 && table.getRows().size() == 1) {
            DimDto row = (DimDto)table.getRows().get(0);
            col = row.getValType();
            if ("String".equalsIgnoreCase(col)) {
                sql.append(" and " + (qEs ? (row.getColname().equals(row.getFromCol()) ? row.getFromCol() : row.getAlias()) : row.getColname()) + " = '$" + row.getAlias() + "'");
            } else {
                sql.append(" and " + (qEs ? (row.getColname().equals(row.getFromCol()) ? row.getFromCol() : row.getAlias()) : row.getColname()) + " = $" + row.getAlias());
            }
        }

        LinkAcceptDto linkAccept = table.getLinkAccept();
        String txt;
        if (linkAccept != null && linkAccept.getId() != null) {
            col = qEs ? linkAccept.getFromCol() : linkAccept.getCol();
            alias = super.findEventParamName(table.getId());
            if (alias == null) {
                alias = linkAccept.getAlias();
            }

            key = linkAccept.getValType();
            txt = linkAccept.getTableColKey();
            if (txt != null && txt.length() > 0) {
                alias = txt;
            }

            String ncol = "$" + alias;
            if ("string".equalsIgnoreCase(key)) {
                ncol = "'" + ncol + "'";
            }

            sql.append("#if($" + alias + " && $" + alias + " != '') and  " + col + " = " + ncol + " #end");
        }

        if (dims.size() > 0) {
            sql.append(" group by ");

            int i;
            for(i = 0; i < dims.size(); ++i) {
                DimDto dim = (DimDto)dims.get(i);
                key = dim.getTableColKey();
                txt = dim.getTableColName();
                if (key != null && txt != null && key.length() > 0 && txt.length() > 0) {
                    sql.append(key + ", " + txt);
                } else {
                    sql.append(qEs ? (dim.getColname().equals(dim.getFromCol()) ? dim.getFromCol() : dim.getAlias()) : dim.getColname());
                }

                if (i != dims.size() - 1) {
                    sql.append(",");
                }
            }

            if (drillDim != null && drillDim.size() >= drillLevel) {
                for(i = 0; i < drillLevel; ++i) {
                    Map<String, Object> dim = (Map)drillDim.get(i);
                    key = (String)dim.get("tableColKey");
                    txt = (String)dim.get("tableColName");
                    if (key != null && txt != null && key.length() > 0 && txt.length() > 0) {
                        sql.append("," + key);
                    } else {
                        sql.append("," + dim.get("code"));
                    }
                }
            }
        }

        StringBuffer filter = new StringBuffer("");
        Iterator var38 = table.getKpiJson().iterator();

        while(var38.hasNext()) {
            KpiDto kpi = (KpiDto)var38.next();
            if (kpi.getFilter() != null) {
                filter.append(" and " + kpi.getCol_name() + " ");
                txt = kpi.getFilter().getFilterType();
                filter.append(txt);
                filter.append(" ");
                double val1 = kpi.getFilter().getVal1();
                if (kpi.getFmt() != null && kpi.getFmt().endsWith("%")) {
                    val1 /= 100.0D;
                }

                filter.append(val1 * (double)(kpi.getRate() == null ? 1 : kpi.getRate()));
                if ("between".equals(txt)) {
                    double val2 = kpi.getFilter().getVal2();
                    if (kpi.getFmt() != null && kpi.getFmt().endsWith("%")) {
                        val2 /= 100.0D;
                    }

                    filter.append(" and " + val2 * (double)(kpi.getRate() == null ? 1 : kpi.getRate()));
                }
            }
        }

        filter.append(this.dealCubeParams(table.getCompParams(), "kpi", tinfo));
        if (filter.length() > 0) {
            sql.append(" having 1=1 " + filter);
        }

        return sql.toString();
    }

    public GridDataCenterContext createDataCenter(String sqls, PortalTableQuery table) throws IOException {
        GridDataCenterContext ctx = new GridDataCenterContextImpl();
        GridSetConfContext conf = new GridSetConfContext();
        TableInfoVO tinfo = this.cacheService.getTableInfo(table.getTid());
        if ("y".equals(tinfo.getEsEnable()) && "y".equals(tinfo.getUseEs())) {
            conf.setMaster(tinfo.getTname());
            conf.setDatasetProvider(this.elaService);
        }

        ctx.setConf(conf);
        ctx.setId("DC-" + IdCreater.create());
        String name = TemplateManager.getInstance().createTemplate(sqls);
        ctx.getConf().setTemplateName(name);
        List<String> orderCols = new ArrayList();

        int i;
        DimDto dim;
        for(i = 0; i < table.getCols().size(); ++i) {
            dim = (DimDto)table.getCols().get(i);
            if (dim.getDimord() != null && dim.getDimord().length() > 0) {
                if (dim.getOrdcol() != null && dim.getOrdcol().length() > 0) {
                    orderCols.add(dim.getOrdcol() + "," + dim.getDimord());
                } else {
                    orderCols.add(dim.getAlias() + "," + dim.getDimord());
                }
            }
        }

        for(i = 0; i < table.getKpiJson().size(); ++i) {
            KpiDto kpi = (KpiDto)table.getKpiJson().get(i);
            if (kpi.getSort() != null && kpi.getSort().length() > 0) {
                orderCols.add(kpi.getAlias() + "," + kpi.getSort());
            }
        }

        for(i = 0; i < table.getRows().size(); ++i) {
            dim = (DimDto)table.getRows().get(i);
            if (dim.getDimord() != null && dim.getDimord().length() > 0) {
                if (dim.getOrdcol() != null && dim.getOrdcol().length() > 0) {
                    orderCols.add(dim.getOrdcol() + "," + dim.getDimord());
                } else {
                    orderCols.add(dim.getAlias() + "," + dim.getDimord());
                }
            }
        }

        if (orderCols.size() > 0) {
            GridSortContext sort = new GridSortContext();
            String[] cols = new String[orderCols.size()];
            String[] types = new String[orderCols.size()];

            for(int i = 0; i < orderCols.size(); ++i) {
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

    public Map<String, InputField> getMvParams() {
        return this.mvParams;
    }

    public OlapTableService getTableService() {
        return this.tableService;
    }
}
