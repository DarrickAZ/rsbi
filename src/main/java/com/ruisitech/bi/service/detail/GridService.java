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
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.context.gridreport.GridCell;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContextImpl;
import com.ruisitech.bi.entity.bireport.ParamDto;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.detail.GridQueryDto;
import com.ruisitech.bi.entity.portal.GridColDto;
import com.ruisitech.bi.service.bireport.BaseCompService;
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
public class GridService extends BaseCompService {
    public static final String deftMvId = "mv.grid.gridReport";
    @Autowired
    private DataControlInterface dataControl;
    @Autowired
    private TableCacheService cacheService;

    public GridService() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
    }

    public MVContext json2MV(GridQueryDto grid, boolean export) throws Exception {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.grid.gridReport");
        GridReportContext cr = this.json2Grid(grid, export);
        String id = "report" + IdCreater.create();
        cr.setId(id);
        String sql = this.createSql(grid);
        String name = TemplateManager.getInstance().createTemplate(sql);
        cr.setTemplateName(name);
        mv.getChildren().add(cr);
        cr.setParent(mv);
        Map<String, GridReportContext> crs = new HashMap();
        crs.put(cr.getId(), cr);
        mv.setGridReports(crs);
        return mv;
    }

    public GridReportContext json2Grid(GridQueryDto dto, boolean export) {
        GridReportContext grid = new GridReportContextImpl();
        if (export) {
            grid.setOut("html");
        } else {
            grid.setOut("query");
        }

        List<GridColDto> cols = dto.getCols();
        GridCell[][] headers = new GridCell[1][cols.size()];

        String type;
        for(int i = 0; i < cols.size(); ++i) {
            GridColDto col = (GridColDto)cols.get(i);
            GridCell cell = new GridCell();
            cell.setColSpan(1);
            cell.setRowSpan(1);
            String name = col.getName();
            type = col.getDispName();
            cell.setDesc(type != null && type.length() != 0 ? type : name);
            cell.setAlias(col.getAlias());
            headers[0][i] = cell;
        }

        grid.setHeaders(headers);
        GridCell[][] detail = new GridCell[1][cols.size()];

        for(int i = 0; i < cols.size(); ++i) {
            GridColDto col = (GridColDto)cols.get(i);
            GridCell cell = new GridCell();
            type = col.getType();
            cell.setAlias(col.getAlias());
            if ("Double".equalsIgnoreCase(type) || "Int".equalsIgnoreCase(type)) {
                cell.setAlign("right");
            }

            String fmt = col.getFmt();
            if (fmt != null && fmt.length() > 0) {
                cell.setFormatPattern(fmt);
            }

            detail[0][i] = cell;
        }

        grid.setDetails(detail);
        Object pageSize = dto.getPageSize();
        if (pageSize == null) {
            pageSize = 10;
        }

        Integer currPage = dto.getCurrPage();
        if (currPage == null) {
            currPage = 0;
        }

        PageInfo page = new PageInfo();
        page.setPagesize((Integer)pageSize);
        page.setCurtpage((long)currPage);
        grid.setPageInfo(page);
        return grid;
    }

    public String createSql(GridQueryDto grid) {
        Map<TableInfoVO, String> tableAlias = new HashMap();
        JSONObject dset = grid.getDset();
        JSONArray joinTabs = dset.getJSONArray("joininfo");
        String dsetId = dset.getString("datasetid");
        Integer masterId = dset.getInteger("tid");
        String master = dset.getString("master");
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
        List<GridColDto> cols = grid.getCols();

        String ret;
        String ref;
        int idx;
        for(idx = 0; idx < cols.size(); ++idx) {
            GridColDto col = (GridColDto)cols.get(idx);
            ret = col.getName();
            String tname = col.getTname();
            ref = col.getExpression();
            if (ref != null && ref.length() > 0) {
                sb.append(" " + ref + " as " + col.getAlias());
            } else {
                sb.append(" " + this.getTableAliasByTname(tableAlias, tname) + "." + ret + " as " + col.getAlias());
            }

            if (idx != cols.size() - 1) {
                sb.append(",");
            }
        }

        idx = 0;
        sb.append(" from ");
        Set<Entry<TableInfoVO, String>> set = tableAlias.entrySet();

        for(Iterator var29 = set.iterator(); var29.hasNext(); sb.append(" ")) {
            Entry<TableInfoVO, String> m = (Entry)var29.next();
            TableInfoVO tab = (TableInfoVO)m.getKey();
            if (tab.getSql() != null && tab.getSql().length() > 0) {
                sb.append("(" + tab.getSql() + ") ");
            } else {
                sb.append(tab.getTname());
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

        List<ParamDto> pageParams = grid.getParams();

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
                    if (!"Date".equals(type) && !"Datetime".equals(type)) {
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
                    } else {
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

        for(i = 0; i < cols.size(); ++i) {
            GridColDto col = (GridColDto)cols.get(i);
            type = col.getSort();
            if (type != null && type.length() > 0) {
                sb.append(" order by " + col.getAlias() + " ");
                sb.append(type);
                break;
            }
        }

        return sb.toString().replaceAll("@", "'");
    }
}
