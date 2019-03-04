//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.detail;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisitech.bi.entity.bireport.DimDto;
import com.ruisitech.bi.entity.bireport.KpiDto;
import com.ruisitech.bi.entity.bireport.ParamDto;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.detail.DetailChartQueryDto;
import com.ruisitech.bi.service.bireport.BaseCompService;
import com.ruisitech.bi.service.bireport.OlapChartService;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.ext.service.DataControlInterface;
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
public class DetailChartSerice extends BaseCompService {
    public static final String deftMvId = "detail.chart.tmp";
    @Autowired
    private DataControlInterface dataControl;
    @Autowired
    private OlapChartService chartService;
    @Autowired
    private TableCacheService cacheService;

    public DetailChartSerice() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
    }

    public MVContext json2MV(DetailChartQueryDto chart) throws Exception {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("detail.chart.tmp");
        ChartContext cr = this.chartService.json2Chart(chart, false);
        String id = "report" + IdCreater.create();
        cr.setId(id);
        String sql = this.createSql(chart, 0);
        GridDataCenterContext dc = this.chartService.createDataCenter(chart, sql);
        cr.setRefDataCenter(dc.getId());
        if (mv.getGridDataCenters() == null) {
            mv.setGridDataCenters(new HashMap());
        }

        mv.getGridDataCenters().put(dc.getId(), dc);
        mv.getChildren().add(cr);
        cr.setParent(mv);
        return mv;
    }

    public String createSql(DetailChartQueryDto chart, int index) {
        JSONObject dset = chart.getDset();
        String dsetId = dset.getString("datasetid");
        JSONArray joinTabs = dset.getJSONArray("joininfo");
        Map<TableInfoVO, String> tableAlias = new HashMap();
        String master = dset.getString("master");
        Integer masterId = dset.getInteger("tid");
        tableAlias.put(this.cacheService.getTableInfo(masterId), "a0");

        for(int i = 0; i < joinTabs.size(); ++i) {
            JSONObject tab = joinTabs.getJSONObject(i);
            Integer tid = tab.getInteger("tid");
            TableInfoVO subTab = this.cacheService.getTableInfo(tid);
            if (!tableAlias.containsKey(subTab)) {
                tableAlias.put(subTab, "a" + (i + 1));
            }
        }

        List<String> group = new ArrayList();
        StringBuffer sb = new StringBuffer("select ");
        List<DimDto> dims = chart.getChartJson().getDims();

        String ret;
        String name;
        String ref;
        for(int i = 0; i < dims.size(); ++i) {
            DimDto dim = (DimDto)dims.get(i);
            String alias = dim.getAlias();
            ret = dim.getColname();
            name = dim.getTname();
            ref = dim.getExpression();
            if (ref != null && ref.length() > 0) {
                sb.append(" " + ref + " as " + alias);
                group.add(ref);
            } else {
                sb.append(" " + this.getTableAliasByTname(tableAlias, name) + "." + ret + " as " + alias);
                group.add(this.getTableAliasByTname(tableAlias, name) + "." + ret);
            }

            sb.append(",");
        }

        List<KpiDto> kpis = chart.getKpiJson();

        String g;
        String type;
        int idx;
        for(idx = 0; idx < kpis.size(); ++idx) {
            KpiDto kpi = (KpiDto)kpis.get(idx);
            ret = kpi.getAlias();
            name = kpi.getCol_name();
            ref = kpi.getTname();
            g = kpi.getExpression();
            type = kpi.getAggre();
            if (g != null && g.length() > 0) {
                sb.append(" " + type + "(" + g + ") as " + ret);
            } else {
                sb.append(" " + type + "(" + this.getTableAliasByTname(tableAlias, ref) + "." + name + ") as " + ret);
            }

            if (idx != kpis.size() - 1) {
                sb.append(",");
            }
        }

        idx = 0;
        sb.append(" from ");
        Set<Entry<TableInfoVO, String>> set = tableAlias.entrySet();

        for(Iterator var35 = set.iterator(); var35.hasNext(); sb.append(" ")) {
            Entry<TableInfoVO, String> m = (Entry)var35.next();
            TableInfoVO tinfo = (TableInfoVO)m.getKey();
            if (tinfo.getSql() != null && tinfo.getSql().length() > 0) {
                sb.append("(" + tinfo.getSql() + ") ");
            } else {
                sb.append(tinfo.getTname());
            }

            sb.append(" " + (String)m.getValue());
            ++idx;
            if (idx != set.size()) {
                sb.append(",");
            }
        }

        sb.append("where 1=1 ");
        if (this.dataControl != null) {
            ret = this.dataControl.process(RSBIUtils.getLoginUserInfo(), master);
            if (ret != null) {
                sb.append(ret + " ");
            }
        }

        for(int i = 0; i < joinTabs.size(); ++i) {
            JSONObject tab = joinTabs.getJSONObject(i);
            ref = tab.getString("ref");
            sb.append("and a0." + tab.getString("col") + "=" + this.getTableAliasByTname(tableAlias, ref) + "." + tab.getString("refKey"));
            sb.append(" ");
        }

        Object o = dset.getJSONArray("param");
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

        List<ParamDto> pageParams = chart.getParams();

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

        if (group.size() > 0) {
            sb.append(" group by ");

            for(i = 0; i < group.size(); ++i) {
                g = (String)group.get(i);
                sb.append(g);
                if (i != group.size() - 1) {
                    sb.append(",");
                }
            }
        }

        return sb.toString().replaceAll("@", "'");
    }

    public OlapChartService getChartService() {
        return this.chartService;
    }
}
