//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.detail;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.dc.grid.AggreVO;
import com.ruisi.ext.engine.view.context.dc.grid.GridAggregationContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContextImpl;
import com.ruisi.ext.engine.view.context.dc.grid.GridSetConfContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridSortContext;
import com.ruisi.ext.engine.view.context.dc.grid.MultiDsContext;
import com.ruisitech.bi.entity.bireport.DimDto;
import com.ruisitech.bi.entity.bireport.KpiDto;
import com.ruisitech.bi.entity.bireport.ParamDto;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.detail.CrossQueryDto;
import com.ruisitech.bi.service.bireport.BaseCompService;
import com.ruisitech.bi.service.bireport.OlapTableService;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.ext.service.DataControlInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrossService extends BaseCompService {
    public static final String deftMvId = "mv.detail.cross";
    @Autowired
    private DataControlInterface dataControl;
    @Autowired
    private OlapTableService tableService;
    @Autowired
    private TableCacheService cahceService;
    private String dbName = RSBIUtils.getConstant("dbName");

    public CrossService() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
    }

    public MVContext json2MV(CrossQueryDto cross) throws Exception {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.detail.cross");
        DimDto kpiOther = new DimDto();
        kpiOther.setType("kpiOther");
        cross.getCols().add(kpiOther);
        CrossReportContext cr = this.tableService.json2Table(cross);
        cross.getCols().remove(cross.getCols().size() - 1);
        String id = "report" + IdCreater.create();
        cr.setId(id);
        cr.setOut("olap");
        cr.setShowData(true);
        String[] sqls = this.createSql(cross);
        GridDataCenterContext dc = this.createDataCenter(sqls, cross);
        cr.setRefDataCetner(dc.getId());
        if (mv.getGridDataCenters() == null) {
            mv.setGridDataCenters(new HashMap());
        }

        mv.getGridDataCenters().put(dc.getId(), dc);
        mv.getChildren().add(cr);
        cr.setParent(mv);
        Map<String, CrossReportContext> crs = new HashMap();
        crs.put(cr.getId(), cr);
        mv.setCrossReports(crs);
        return mv;
    }

    public String[] createSql(CrossQueryDto cross) {
        return new String[]{this.createSql(cross, 0)};
    }

    public String createSql(CrossQueryDto cross, int index) {
        Map<TableInfoVO, String> tableAlias = new HashMap();
        JSONObject dset = cross.getDset();
        JSONArray joinTabs = dset.getJSONArray("joininfo");
        String dsetId = dset.getString("datasetid");
        Integer tid = dset.getInteger("tid");
        tableAlias.put(this.cahceService.getTableInfo(tid), "a0");

        for(int i = 0; i < joinTabs.size(); ++i) {
            JSONObject tab = joinTabs.getJSONObject(i);
            Integer subTid = tab.getInteger("tid");
            TableInfoVO ref = this.cahceService.getTableInfo(subTid);
            if (!tableAlias.containsKey(ref)) {
                tableAlias.put(ref, "a" + (i + 1));
            }
        }

        StringBuffer sb = new StringBuffer("select ");

        int idx;
        for(idx = 0; idx < cross.getDims().size(); ++idx) {
            DimDto d = (DimDto)cross.getDims().get(idx);
            if ("y".equals(d.getDyna())) {
                sb.append(d.getColname());
            } else {
                sb.append(this.getTableAliasByTname(tableAlias, d.getTname()) + "." + d.getColname());
            }

            sb.append(" " + d.getAlias() + ",");
        }

        for(idx = 0; idx < cross.getKpiJson().size(); ++idx) {
            KpiDto k = (KpiDto)cross.getKpiJson().get(idx);
            sb.append(k.getAggre() + "(");
            sb.append(k.getCol_name());
            sb.append(") " + k.getAlias());
            if (idx != cross.getKpiJson().size() - 1) {
                sb.append(",");
            }
        }

        if (cross.getKpiJson().size() == 0) {
            sb.append(" " + ("db2".equals(this.dbName) ? "0" : "null") + " kpi_value ");
        }

        idx = 0;
        sb.append(" from ");
        Set<Entry<TableInfoVO, String>> set = tableAlias.entrySet();

        for(Iterator var28 = set.iterator(); var28.hasNext(); sb.append(" ")) {
            Entry<TableInfoVO, String> m = (Entry)var28.next();
            if (((TableInfoVO)m.getKey()).getSql() != null && ((TableInfoVO)m.getKey()).getSql().length() > 0) {
                sb.append("(" + ((TableInfoVO)m.getKey()).getSql() + ") ");
            } else {
                sb.append(((TableInfoVO)m.getKey()).getTname());
            }

            sb.append(" " + (String)m.getValue());
            ++idx;
            if (idx != set.size()) {
                sb.append(",");
            }
        }

        sb.append("where 1=1 ");
        if (this.dataControl != null) {
            String ret = this.dataControl.process(RSBIUtils.getLoginUserInfo(), this.cahceService.getTableInfo(tid).getTname());
            if (ret != null) {
                sb.append(ret + " ");
            }
        }

        for(int i = 0; i < joinTabs.size(); ++i) {
            JSONObject tab = joinTabs.getJSONObject(i);
            String ref = tab.getString("ref");
            sb.append("and a0." + tab.getString("col") + "=" + this.getTableAliasByTname(tableAlias, ref) + "." + tab.getString("refKey"));
            sb.append(" ");
        }

        Object o = dset.getJSONArray("param");
        String type;
        String name;
        String tname;
        String expression;
        String val2;
        int i;
        if (o != null) {
            JSONArray params = (JSONArray)o;

            for(i = 0; i < params.size(); ++i) {
                JSONObject p = (JSONObject)params.get(i);
                type = p.getString("col");
                name = p.getString("tname");
                tname = p.getString("type");
                expression = p.getString("val");
                val2 = p.getString("val2");
                sb.append(" and " + this.getTableAliasByTname(tableAlias, name) + "." + type);
                sb.append(tname);
                if (!"String".equalsIgnoreCase(tname)) {
                    sb.append(expression);
                } else {
                    sb.append("'");
                    sb.append(expression);
                    sb.append("'");
                }

                if ("between".equals(tname)) {
                    sb.append(" and ");
                    if (!"String".equalsIgnoreCase(tname)) {
                        sb.append(val2);
                    } else {
                        sb.append("'");
                        sb.append(val2);
                        sb.append("'");
                    }
                }

                sb.append(" ");
            }
        }

        List<ParamDto> pageParams = cross.getParams();

        for(i = 0; pageParams != null && i < pageParams.size(); ++i) {
            ParamDto p = (ParamDto)pageParams.get(i);
            type = p.getType();
            name = p.getName();
            tname = p.getTname();
            expression = p.getExpression();
            val2 = p.getDatasetid();
            if (dsetId.equals(val2)) {
                JSONObject filter;
                String filterType;
                Object val1;
                if (!"Double".equals(type) && !"Int".equals(type)) {
                    if ("Date".equals(type)) {
                        filter = p.getFilter();
                        if (filter != null && !filter.isEmpty()) {
                            filterType = filter.getString("stdt");
                            String enddt = filter.getString("enddt");
                            sb.append(" and ");
                            if (expression != null && expression.length() != 0) {
                                sb.append(expression);
                            } else {
                                sb.append(this.getTableAliasByTname(tableAlias, tname) + "." + name);
                            }

                            sb.append(" between '" + filterType + " 00:00:00' and '" + enddt + " 23:59:59'");
                        }
                    } else {
                        filter = p.getFilter();
                        if (filter != null && !filter.isEmpty()) {
                            filterType = filter.getString("filterType");
                            val1 = filter.get("val1");
                            sb.append(" and ");
                            if (expression != null && expression.length() != 0) {
                                sb.append(expression);
                            } else {
                                sb.append(this.getTableAliasByTname(tableAlias, tname) + "." + name);
                            }

                            sb.append(" " + filterType);
                            if ("like".equals(filterType)) {
                                sb.append(" '%" + val1 + "%'");
                            } else {
                                sb.append(" '" + val1 + "'");
                            }
                        }
                    }
                } else {
                    filter = p.getFilter();
                    if (filter != null && !filter.isEmpty()) {
                        filterType = filter.getString("filterType");
                        val1 = filter.get("val1");
                        sb.append(" and ");
                        if (expression != null && expression.length() != 0) {
                            sb.append(expression);
                        } else {
                            sb.append(this.getTableAliasByTname(tableAlias, tname) + "." + name);
                        }

                        sb.append(" " + filterType + " " + val1);
                        if ("between".equals(filterType)) {
                            sb.append(" and " + filter.get("val2"));
                        }
                    }
                }
            }
        }

        List<DimDto> dims = cross.getDims();
        if (dims.size() > 0) {
            sb.append(" group by ");

            for(int j = 0; j < dims.size(); ++j) {
                DimDto dim = (DimDto)dims.get(j);
                if ("y".equals(dim.getDyna())) {
                    sb.append(dim.getColname());
                } else {
                    sb.append(this.getTableAliasByTname(tableAlias, dim.getTname()) + "." + dim.getColname());
                }

                if (j != dims.size() - 1) {
                    sb.append(",");
                }
            }
        }

        return sb.toString().replaceAll("@", "'");
    }

    public GridDataCenterContext createDataCenter(String[] sqls, CrossQueryDto cross) throws IOException {
        GridDataCenterContext ctx = new GridDataCenterContextImpl();
        GridSetConfContext conf = new GridSetConfContext();
        ctx.setConf(conf);
        ctx.setId("DC-" + IdCreater.create());
        ArrayList orderCols;
        String[] cols;
        int i;
        if (sqls.length == 1) {
            String name = TemplateManager.getInstance().createTemplate(sqls[0]);
            conf.setTemplateName(name);
        } else {
            orderCols = new ArrayList();
            String[] var6 = sqls;
            int var7 = sqls.length;

            //int i;
            for(i = 0; i < var7; ++i) {
                String s = var6[i];
                MultiDsContext mctx = new MultiDsContext();
                String name = TemplateManager.getInstance().createTemplate(s);
                mctx.setTemplateName(name);
                orderCols.add(mctx);
            }

            conf.setMultiDsContext(orderCols);
            GridAggregationContext agg = new GridAggregationContext();
            cols = new String[cross.getDims().size()];

            for(i = 0; i < cross.getDims().size(); ++i) {
                DimDto dim = (DimDto)cross.getDims().get(i);
                cols[i] = dim.getAlias();
            }

            agg.setColumn(cols);
            AggreVO[] aggs = new AggreVO[cross.getKpiJson().size()];

            for(i = 0; i < cross.getKpiJson().size(); ++i) {
                KpiDto kpi = (KpiDto)cross.getKpiJson().get(i);
                AggreVO avo = new AggreVO();
                avo.setAlias(kpi.getAlias());
                avo.setName(kpi.getAlias());
                String aggre = kpi.getAggre();
                if ("count".equals(aggre)) {
                    aggre = "sum";
                }

                avo.setType(aggre);
                aggs[i] = avo;
            }

            agg.setAggreVO(aggs);
            ctx.getProcess().add(agg);
        }

        orderCols = new ArrayList();

        //int i;
        DimDto dim;
        for(i = 0; i < cross.getCols().size(); ++i) {
            dim = (DimDto)cross.getCols().get(i);
            if (dim.getDimord() != null && dim.getDimord().length() > 0) {
                orderCols.add(dim.getAlias() + "," + dim.getDimord() + ",");
            }
        }

        for(i = 0; i < cross.getKpiJson().size(); ++i) {
            KpiDto kpi = (KpiDto)cross.getKpiJson().get(i);
            if (kpi.getSort() != null && kpi.getSort().length() > 0) {
                orderCols.add(kpi.getAlias() + "," + kpi.getSort());
            }
        }

        for(i = 0; i < cross.getRows().size(); ++i) {
            dim = (DimDto)cross.getRows().get(i);
            if (dim.getDimord() != null && dim.getDimord().length() > 0) {
                orderCols.add(dim.getAlias() + "," + dim.getDimord());
            }
        }

        if (orderCols.size() > 0) {
            GridSortContext sort = new GridSortContext();
            cols = new String[orderCols.size()];
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

    public OlapTableService getTableService() {
        return this.tableService;
    }
}
