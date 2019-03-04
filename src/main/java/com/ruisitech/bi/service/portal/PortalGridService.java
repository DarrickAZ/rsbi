//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.portal;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContextImpl;
import com.ruisi.ext.engine.view.context.dc.grid.GridSetConfContext;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.context.gridreport.GridCell;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContextImpl;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.portal.CompParamDto;
import com.ruisitech.bi.entity.portal.GridColDto;
import com.ruisitech.bi.entity.portal.GridQuery;
import com.ruisitech.bi.service.bireport.BaseCompService;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.service.etl.ElasticService;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.ext.service.DataControlInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class PortalGridService extends BaseCompService {
    public static final String deftMvId = "mv.portal.gridReport";
    private Map<String, InputField> mvParams = new HashMap();
    @Autowired
    private DataControlInterface dataControl;
    @Autowired
    private TableCacheService cacheService;
    @Autowired
    private ElasticService elaService;

    public PortalGridService() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
        this.mvParams.clear();
    }

    public MVContext json2MV(GridQuery grid) throws Exception {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.portal.gridReport");
        mv.setHideMV(true);
        super.parserHiddenParam(grid.getPortalParams(), mv, this.mvParams);
        GridReportContext cr = this.json2Grid(grid);
        String id = "report" + IdCreater.create();
        cr.setId(id);
        String style = grid.getDashboardStyle();
        if (style != null && style.length() > 0) {
            cr.setStyle(style);
        }

        String sql = this.createSql(grid);
        GridDataCenterContext dc = this.createDataCenter(grid, sql);
        cr.setRefDataCenter(dc.getId());
        if (mv.getGridDataCenters() == null) {
            mv.setGridDataCenters(new HashMap());
        }

        mv.getGridDataCenters().put(dc.getId(), dc);
        mv.getChildren().add(cr);
        cr.setParent(mv);
        Map<String, GridReportContext> crs = new HashMap();
        crs.put(cr.getId(), cr);
        mv.setGridReports(crs);
        return mv;
    }

    public GridDataCenterContext createDataCenter(GridQuery grid, String sql) throws IOException {
        GridDataCenterContext ctx = new GridDataCenterContextImpl();
        GridSetConfContext conf = new GridSetConfContext();
        TableInfoVO tinfo = this.cacheService.getTableInfo(grid.getTid());
        if ("y".equals(tinfo.getEsEnable()) && "y".equals(tinfo.getUseEs())) {
            conf.setMaster(tinfo.getTname());
            conf.setDatasetProvider(this.elaService);
            conf.setAgg(false);
        }

        ctx.setConf(conf);
        ctx.setId("DC-" + IdCreater.create());
        String name = TemplateManager.getInstance().createTemplate(sql);
        ctx.getConf().setTemplateName(name);
        return ctx;
    }

    public GridReportContext json2Grid(GridQuery gridJson) {
        GridReportContext grid = new GridReportContextImpl();
        Integer height = gridJson.getHeight();
        grid.setOut("lockUI");
        if (height != null) {
            grid.setHeight(String.valueOf(height));
        }

        List<GridColDto> cols = gridJson.getCols();
        GridCell[][] headers = new GridCell[1][cols.size()];

        String isnotfy;
        String id;
        String fmt;
        for(int i = 0; i < cols.size(); ++i) {
            GridColDto col = (GridColDto)cols.get(i);
            GridCell cell = new GridCell();
            cell.setColSpan(1);
            cell.setRowSpan(1);
            isnotfy = col.getName();
            id = col.getId();
            fmt = col.getDispName();
            cell.setDesc(fmt != null && fmt.length() != 0 ? fmt : isnotfy);
            cell.setAlias(id);
            headers[0][i] = cell;
        }

        grid.setHeaders(headers);
        GridCell[][] detail = new GridCell[1][cols.size()];

        for(int i = 0; i < cols.size(); ++i) {
            GridColDto col = (GridColDto)cols.get(i);
            GridCell cell = new GridCell();
            id = col.getId();
            cell.setAlias(id);
            fmt = col.getFmt();
            String align = col.getAlign();
            if (fmt != null && fmt.length() > 0) {
                cell.setFormatPattern(fmt);
            }

            if (align != null && align.length() > 0) {
                cell.setAlign(align);
            }

            detail[0][i] = cell;
        }

        grid.setDetails(detail);
        Integer pageSize = gridJson.getPageSize();
        if (pageSize == null) {
            pageSize = 10;
        }

        PageInfo page = new PageInfo();
        page.setPagesize(pageSize);
        isnotfy = gridJson.getIsnotfy();
        if (!"true".equals(isnotfy)) {
            grid.setPageInfo(page);
        }

        return grid;
    }

    public String createSql(GridQuery grid) {
        TableInfoVO tvo = this.cacheService.getTableInfo(grid.getTid());
        boolean qEs = false;
        if ("y".equals(tvo.getUseEs()) && "y".equals(tvo.getEsEnable())) {
            qEs = true;
        }

        StringBuffer sb = new StringBuffer("select ");
        List<GridColDto> cols = grid.getCols();

        String id;
        for(int i = 0; i < cols.size(); ++i) {
            GridColDto col = (GridColDto)cols.get(i);
            String name = col.getName();
            id = col.getExpression();
            if (qEs) {
                sb.append(" " + name + " as " + name);
            } else if (id != null && id.length() > 0) {
                sb.append(" " + id + " as " + name);
            } else {
                sb.append(" " + name + " as " + name);
            }

            if (i != cols.size() - 1) {
                sb.append(",");
            }
        }

        sb.append(" from ");
        if (tvo.getSql() != null && tvo.getSql().length() > 0) {
            sb.append("(" + tvo.getSql() + ") a ");
        } else {
            sb.append(tvo.getTname());
        }

        sb.append(" where 1=1 ");
        if (this.dataControl != null) {
            String ret = this.dataControl.process(RSBIUtils.getLoginUserInfo(), tvo.getTname());
            if (ret != null) {
                sb.append(ret + " ");
            }
        }

        List<CompParamDto> pageParams = grid.getCompParams();
        sb.append(super.dealCubeParams(pageParams, "dim", tvo));
        sb.append(super.dealDashboardParams(grid.getDashboardParam(), tvo));

        for(int i = 0; i < cols.size(); ++i) {
            GridColDto col = (GridColDto)cols.get(i);
            id = col.getId();
            String sort = col.getSort();
            if (sort != null && sort.length() > 0) {
                if (qEs) {
                    sb.append(" order by sort_func(" + col.getName() + ",'" + (!col.getType().equals("Datetime") && !col.getType().equals("Date") ? (col.getType().equalsIgnoreCase("String") ? "string" : "number") : "string") + "') ");
                    sb.append(sort);
                } else {
                    sb.append(" order by " + id + " ");
                    sb.append(sort);
                }
                break;
            }
        }

        return sb.toString().replaceAll("@", "'").replaceAll("\\[x\\]", "\\$");
    }

    public Map<String, InputField> getMvParams() {
        return this.mvParams;
    }
}
