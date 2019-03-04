//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.bireport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.form.ButtonContext;
import com.ruisi.ext.engine.view.context.form.ButtonContextImpl;
import com.ruisi.ext.engine.view.context.form.DateSelectContext;
import com.ruisi.ext.engine.view.context.form.DateSelectContextImpl;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.context.form.MultiSelectContextImpl;
import com.ruisi.ext.engine.view.context.form.SelectContextImpl;
import com.ruisi.ext.engine.view.context.html.DivContext;
import com.ruisi.ext.engine.view.context.html.DivContextImpl;
import com.ruisi.ext.engine.view.context.html.TextContext;
import com.ruisi.ext.engine.view.context.html.TextContextImpl;
import com.ruisitech.bi.entity.bireport.ChartQueryDto;
import com.ruisitech.bi.entity.bireport.DimDto;
import com.ruisitech.bi.entity.bireport.ParamDto;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.bireport.TableQueryDto;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ReportService extends BaseCompService {
    public static final String deftMvId = "mv.export.tmp";
    @Autowired
    private TableCacheService cacheService;
    @Autowired
    private OlapChartService chartService;
    @Autowired
    private OlapTableService tableService;

    public ReportService() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
    }

    public MVContext json2MV(JSONObject json, int release) throws Exception {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.export.tmp");
        JSONArray ps = json.getJSONArray("params");
        ParamDto[] ls = (ParamDto[])JSONArray.toJavaObject(ps, ParamDto[].class);
        List<ParamDto> params = Arrays.asList(ls);
        int i;
        String name;
        if (!ps.isEmpty()) {
            String type;
            String colname;
            if (release == 0) {
                StringBuffer sb = new StringBuffer("参数： ");
                TextContext parStr = new TextContextImpl();

                for(int i = 0; i < params.size(); ++i) {
                    ParamDto param = (ParamDto)params.get(i);
                    type = param.getName();
                    colname = param.getType();
                    if (!"frd".equals(colname) && !"year".equals(colname) && !"quarter".equals(colname)) {
                        if ("month".equals(colname) || "day".equals(colname)) {
                            sb.append(type + "(" + (param.getSt() == null ? "无" : param.getSt()) + " 至 " + (param.getEnd() == null ? "无" : param.getEnd()) + ")");
                        }
                    } else {
                        sb.append(type + "(" + (param.getValStrs() == null ? "无" : param.getValStrs()) + ")");
                    }

                    sb.append("  ");
                }

                parStr.setText(sb.toString());
                mv.getChildren().add(parStr);
                parStr.setParent(mv);
            } else {
                DivContext div = new DivContextImpl();
                div.setStyleClass("rpeortParam");
                div.setChildren(new ArrayList());
                mv.getChildren().add(div);
                div.setParent(mv);

                for(i = 0; i < params.size(); ++i) {
                    ParamDto param = (ParamDto)params.get(i);
                    name = param.getName();
                    type = param.getType();
                    colname = param.getColname();
                    String values = param.getVals();
                    InputField input = null;
                    InputField input2 = null;
                    String sql;
                    String template;
                    if (!"frd".equalsIgnoreCase(type) && !"year".equalsIgnoreCase(type) && !"quarter".equalsIgnoreCase(type)) {
                        if ("day".equalsIgnoreCase(type)) {
                            DateSelectContext target = new DateSelectContextImpl();
                            sql = param.getSt();
                            target.setDefaultValue(sql == null ? "" : sql.replaceAll("-", ""));
                            target.setDesc("开始" + name);
                            target.setId("s_" + colname);
                            input = target;
                            DateSelectContext target2 = new DateSelectContextImpl();
                            String val2 = param.getEnd();
                            target2.setDefaultValue(val2 == null ? "" : val2.replaceAll("-", ""));
                            target2.setDesc("结束" + name);
                            target2.setId("e_" + colname);
                            input2 = target2;
                        } else if ("month".equalsIgnoreCase(type)) {
                            SelectContextImpl target = new SelectContextImpl();
                            sql = this.createMonthSql();
                            template = TemplateManager.getInstance().createTemplate(sql);
                            target.setTemplateName(template);
                            input = target;
                            target.setDefaultValue(param.getSt());
                            target.setDesc("开始" + name);
                            target.setId("s_" + colname);
                            SelectContextImpl target2 = new SelectContextImpl();
                            String template2 = TemplateManager.getInstance().createTemplate(sql);
                            target2.setTemplateName(template2);
                            target2.setDefaultValue(param.getEnd());
                            target2.setDesc("结束" + name);
                            target2.setId("e_" + colname);
                            input2 = target2;
                        }
                    } else {
                        MultiSelectContextImpl target = new MultiSelectContextImpl();
                        sql = this.createDimSql(param);
                        template = TemplateManager.getInstance().createTemplate(sql);
                        target.setTemplateName(template);
                        input = target;
                        target.setDefaultValue(values == null ? "" : values);
                        target.setDesc(name);
                        target.setId(colname);
                    }

                    div.getChildren().add(input);
                    ((InputField)input).setParent(div);
                    if (input2 != null) {
                        div.getChildren().add(input2);
                        ((InputField)input2).setParent(div);
                    }
                }

                ButtonContext btn = new ButtonContextImpl();
                btn.setDesc("查询");
                btn.setType("button");
                btn.setMvId(new String[]{"mv.export.tmp"});
                div.getChildren().add(btn);
                btn.setParent(div);
            }
        }

        JSONArray comps = json.getJSONArray("comps");

        for(i = 0; i < comps.size(); ++i) {
            JSONObject obj = comps.getJSONObject(i);
            name = obj.getString("type");
            if ("table".equals(name)) {
                TableQueryDto dto = (TableQueryDto)JSONObject.toJavaObject(obj, TableQueryDto.class);
                dto.setParams(params);
                this.createTable(mv, dto, release);
            }

            if ("chart".equals(name)) {
                ChartQueryDto dto = (ChartQueryDto)JSONObject.toJavaObject(obj, ChartQueryDto.class);
                dto.setParams(params);
                this.createChart(mv, dto, release);
            }
        }

        return mv;
    }

    public CrossReportContext createTable(MVContext mv, TableQueryDto dto, int release) throws IOException, ParseException {
        TableInfoVO tinfo = this.cacheService.getTableInfo(dto.getTid());
        DimDto kpiOther = new DimDto();
        kpiOther.setType("kpiOther");
        dto.getCols().add(kpiOther);
        CrossReportContext cr = this.tableService.json2Table(dto);
        dto.getCols().remove(dto.getCols().size() - 1);
        String id = "report" + IdCreater.create();
        cr.setId(id);
        cr.setOut("html");
        cr.setShowData(true);
        String sqls = this.tableService.createSql(dto, 0);
        GridDataCenterContext dc = this.tableService.createDataCenter(sqls, dto);
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
        return cr;
    }

    public void createChart(MVContext mv, ChartQueryDto chart, int release) throws IOException, ParseException {
        TableInfoVO tinfo = this.cacheService.getTableInfo(chart.getTid());
        StringBuffer sb = new StringBuffer("");
        int cnt = false;

        DimDto dim;
        for(Iterator var7 = chart.getChartJson().getParams().iterator(); var7.hasNext(); sb.append(dim.getDimdesc() + "(" + dim.getValDesc() + ") - ")) {
            dim = (DimDto)var7.next();
            if (!cnt) {
                sb.append("钻取维：");
            }
        }

        String drillText = sb.toString();
        if (drillText.length() > 0) {
            TextContext txt = new TextContextImpl();
            txt.setText(drillText);
            mv.getChildren().add(txt);
            txt.setParent(mv);
        }

        ChartContext cr = this.chartService.json2Chart(chart, false);
        String sqls = this.chartService.createSql(chart, 0, 0);
        GridDataCenterContext dc = this.chartService.createDataCenter(chart, sqls);
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
    }

    public String createDimSql(ParamDto param) {
        String tname = param.getTname();
        String sql;
        if (tname != null && tname.length() != 0) {
            sql = "select " + param.getTableColKey() + " \"value\", " + param.getTableColName() + " \"text\" from " + tname;
            return sql;
        } else {
            sql = "select distinct " + param.getColname() + " \"value\", " + param.getColname() + " \"text\" from " + tname;
            return sql;
        }
    }

    public String createMonthSql() {
        String sql = "select mid \"value\", mname \"text\" from code_month order by mid desc";
        return sql;
    }
}
