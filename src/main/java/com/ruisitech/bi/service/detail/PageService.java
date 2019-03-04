//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.detail;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisitech.bi.entity.bireport.DimDto;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.detail.CrossQueryDto;
import com.ruisitech.bi.entity.detail.DetailChartQueryDto;
import com.ruisitech.bi.entity.detail.GridQueryDto;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.mapper.etl.EtlTableMetaColMapper;
import com.ruisitech.bi.service.bireport.BaseCompService;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageService extends BaseCompService {
    public static final String deftMvId = "detail.report.tmp";
    @Autowired
    private EtlTableMetaColMapper colMapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @Autowired
    private TableCacheService cacheService;
    @Autowired
    private CrossService crossService;
    @Autowired
    private GridService gridService;
    @Autowired
    private DetailChartSerice chartService;

    public PageService() {
    }

    public String crtTableQuery(EtlTableMeta tinfo) {
        String tname = tinfo.getTableName();
        String tnote = tinfo.getTableNote();
        JSONObject json = new JSONObject();
        JSONArray ds = new JSONArray();
        JSONObject d = new JSONObject();
        d.put("name", tname + "(" + tnote + ")");
        d.put("note", tnote);
        d.put("master", tname);
        d.put("tid", tinfo.getTableId());
        d.put("joininfo", new JSONArray());
        d.put("datasetid", "d1");
        List<DSColumn> cols = this.colMapper.queryColumnsRetDsColumn(tinfo.getTableId(), this.sysUser);
        d.put("cols", cols);
        ds.add(d);
        json.put("dataset", ds);
        json.put("selectDs", "d1");
        return JSONObject.toJSONString(json, new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty});
    }

    public String createDatasetSql(JSONObject obj) {
        Map<TableInfoVO, String> tableAlias = new HashMap();
        JSONArray joinTabs = obj.getJSONArray("joininfo");
        Integer masterId = obj.getInteger("tid");
        tableAlias.put(this.cacheService.getTableInfo(masterId), "a0");

        for(int i = 0; i < joinTabs.size(); ++i) {
            JSONObject tab = joinTabs.getJSONObject(i);
            Integer subTid = tab.getInteger("tid");
            TableInfoVO subTable = this.cacheService.getTableInfo(subTid);
            if (!tableAlias.containsKey(subTable)) {
                tableAlias.put(subTable, "a" + (i + 1));
            }
        }

        StringBuffer sb = new StringBuffer("select ");
        Set<Entry<TableInfoVO, String>> set = tableAlias.entrySet();
        int idx = 0;

        Entry m;
        Iterator var20;
        for(var20 = set.iterator(); var20.hasNext(); sb.append(" ")) {
            m = (Entry)var20.next();
            sb.append((String)m.getValue() + ".*");
            ++idx;
            if (idx != set.size()) {
                sb.append(",");
            }
        }

        idx = 0;
        sb.append("from ");

        for(var20 = set.iterator(); var20.hasNext(); sb.append(" ")) {
            m = (Entry)var20.next();
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

        for(int i = 0; i < joinTabs.size(); ++i) {
            JSONObject tab = joinTabs.getJSONObject(i);
            String ref = tab.getString("ref");
            sb.append("and a0." + tab.getString("col") + "=" + this.getTableAliasByTname(tableAlias, ref) + "." + tab.getString("refKey"));
            sb.append(" ");
        }

        Object o = obj.get("param");
        if (o != null) {
            JSONArray params = (JSONArray)o;

            for(int i = 0; i < params.size(); ++i) {
                JSONObject p = (JSONObject)params.get(i);
                String col = p.getString("col");
                String tname = p.getString("tname");
                String type = p.getString("type");
                String val = p.getString("val");
                String val2 = p.getString("val2");
                sb.append(" and " + this.getTableAliasByTname(tableAlias, tname) + "." + col);
                sb.append(type);
                if ("string".equals(type)) {
                    sb.append(val);
                } else {
                    sb.append("'");
                    sb.append(val);
                    sb.append("'");
                }

                if ("between".equals(type)) {
                    sb.append(" and ");
                    if ("string".equals(type)) {
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

        return sb.toString();
    }

    public MVContext json2MV(JSONObject page) throws Exception {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("detail.report.tmp");
        JSONObject cross = (JSONObject)page.get("cross");
        JSONObject grid;
        if (cross != null) {
            grid = this.findDset(page, cross.getString("datasetid"));
            CrossQueryDto dto = (CrossQueryDto)JSONObject.toJavaObject(cross, CrossQueryDto.class);
            dto.setDset(grid);
            CrossReportContext cr = this.json2Cross(dto, mv);
            mv.getChildren().add(cr);
            cr.setParent(mv);
        }

        grid = this.findGrid(page);
        JSONObject cdset;
        if (grid != null) {
            JSONArray cols = grid.getJSONArray("cols");
            if (cols != null && !cols.isEmpty()) {
                GridQueryDto dto = (GridQueryDto)JSONObject.toJavaObject(grid, GridQueryDto.class);
                cdset = this.findDset(page, grid.getString("datasetid"));
                dto.setDset(cdset);
                GridReportContext gridC = this.json2Grid(dto, true);
                mv.getChildren().add(gridC);
                gridC.setParent(mv);
                Map<String, GridReportContext> crs = new HashMap();
                crs.put(gridC.getId(), gridC);
                mv.setGridReports(crs);
            }
        }

        JSONObject chart = this.findChart(page);
        if (chart != null) {
            DetailChartQueryDto dto = (DetailChartQueryDto)JSONObject.toJavaObject(chart, DetailChartQueryDto.class);
            if (dto.getKpiJson() != null && dto.getKpiJson().size() > 0) {
                cdset = this.findDset(page, chart.getString("datasetid"));
                dto.setDset(cdset);
                ChartContext chartC = this.json2Chart(dto, mv);
                mv.getChildren().add(chartC);
                chartC.setParent(mv);
            }
        }

        return mv;
    }

    public GridReportContext json2Grid(GridQueryDto dto, boolean export) throws Exception {
        GridReportContext cr = this.gridService.json2Grid(dto, export);
        String id = "report" + IdCreater.create();
        cr.setId(id);
        String sql = this.gridService.createSql(dto);
        String name = TemplateManager.getInstance().createTemplate(sql);
        cr.setTemplateName(name);
        return cr;
    }

    public ChartContext json2Chart(DetailChartQueryDto chart, MVContext mv) throws Exception {
        ChartContext cr = this.chartService.getChartService().json2Chart(chart, false);
        String id = "report" + IdCreater.create();
        cr.setId(id);
        String sql = this.chartService.createSql(chart, 0);
        GridDataCenterContext dc = this.chartService.getChartService().createDataCenter(chart, sql);
        cr.setRefDataCenter(dc.getId());
        if (mv.getGridDataCenters() == null) {
            mv.setGridDataCenters(new HashMap());
        }

        mv.getGridDataCenters().put(dc.getId(), dc);
        return cr;
    }

    public CrossReportContext json2Cross(CrossQueryDto dto, MVContext mv) throws Exception {
        DimDto kpiOther = new DimDto();
        kpiOther.setType("kpiOther");
        dto.getCols().add(kpiOther);
        CrossReportContext cr = this.crossService.getTableService().json2Table(dto);
        dto.getCols().remove(dto.getCols().size() - 1);
        String id = "report" + IdCreater.create();
        cr.setId(id);
        cr.setOut("html");
        cr.setShowData(true);
        String[] sqls = this.crossService.createSql(dto);
        GridDataCenterContext dc = this.crossService.createDataCenter(sqls, dto);
        cr.setRefDataCetner(dc.getId());
        if (mv.getGridDataCenters() == null) {
            mv.setGridDataCenters(new HashMap());
        }

        mv.getGridDataCenters().put(dc.getId(), dc);
        return cr;
    }

    public JSONObject findDset(JSONObject page, String dsetId) {
        JSONObject ret = null;
        JSONArray dss = page.getJSONArray("dataset");

        for(int i = 0; i < dss.size(); ++i) {
            JSONObject ds = dss.getJSONObject(i);
            String datasetid = ds.getString("datasetid");
            if (datasetid.equals(dsetId)) {
                ret = ds;
                break;
            }
        }

        return ret;
    }

    public JSONObject findGrid(JSONObject page) {
        return (JSONObject)page.get("grid");
    }

    public JSONObject findChart(JSONObject page) {
        return (JSONObject)page.get("chart");
    }

    public JSONArray findParams(JSONObject page) {
        return (JSONArray)page.get("params");
    }
}
