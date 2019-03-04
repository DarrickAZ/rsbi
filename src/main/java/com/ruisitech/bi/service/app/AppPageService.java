//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.app;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.form.*;
import com.ruisi.ext.engine.view.context.html.DivContext;
import com.ruisi.ext.engine.view.context.html.DivContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisitech.bi.entity.bireport.*;
import com.ruisitech.bi.service.bireport.OlapChartService;
import com.ruisitech.bi.service.bireport.OlapTableService;
import com.ruisitech.bi.service.bireport.TableCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("prototype")
public class AppPageService {
    public static final String deftMvId = "mv.app.table";
    @Autowired
    private OlapTableService tableService;
    @Autowired
    private OlapChartService chartService;
    @Autowired
    private TableCacheService cahceService;
    @Autowired
    private DaoHelper daoHelper;

    public AppPageService() {
    }

    public MVContext viewTable(JSONObject pageInfo, String outType, int release) throws Exception {
        JSONObject table = pageInfo.getJSONObject("table");
        JSONObject tableJson = table.getJSONObject("tableJson");
        JSONArray kpiJson = (JSONArray)table.get("kpiJson");
        JSONArray cols = tableJson.getJSONArray("cols");
        JSONArray rows = tableJson.getJSONArray("rows");
        cols.remove(cols.size() - 1);
        JSONArray params = (JSONArray)pageInfo.get("params");
        JSONObject tableQueryJson = new JSONObject();
        tableQueryJson.put("kpiJson", kpiJson);
        tableQueryJson.put("cols", cols);
        tableQueryJson.put("rows", rows);
        tableQueryJson.put("params", params);
        TableQueryDto dto = (TableQueryDto)JSONObject.toJavaObject(tableQueryJson, TableQueryDto.class);
        dto.setTid(((KpiDto)dto.getKpiJson().get(0)).getTid());
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.app.table");
        TableInfoVO tinfo = this.cahceService.getTableInfo(dto.getTid());
        this.createParams(mv, tinfo, params, "table");
        DimDto kpiOther = new DimDto();
        kpiOther.setType("kpiOther");
        dto.getCols().add(kpiOther);
        CrossReportContext cr = this.tableService.json2Table(dto);
        dto.getCols().remove(dto.getCols().size() - 1);
        String id = "report" + IdCreater.create();
        cr.setId(id);
        cr.setOut(outType);
        cr.setConfCallBack("printConfig");
        mv.getChildren().add(cr);
        cr.setParent(mv);
        Map<String, CrossReportContext> crs = new HashMap();
        crs.put(cr.getId(), cr);
        mv.setCrossReports(crs);
        String sqls = this.tableService.createSql(dto, 0);
        GridDataCenterContext dc = this.tableService.createDataCenter(sqls, dto);
        cr.setRefDataCetner(dc.getId());
        if (mv.getGridDataCenters() == null) {
            mv.setGridDataCenters(new HashMap());
        }

        mv.getGridDataCenters().put(dc.getId(), dc);
        StringBuffer script = new StringBuffer();
        script.append("function printConfig(){out.println(\",fit:false,border:true,singleSelect:true\")}");
        mv.setScripts(script.toString());
        return mv;
    }

    public MVContext viewChart(JSONObject pageInfo, int release) throws Exception {
        ChartQueryDto dto = (ChartQueryDto)JSONObject.toJavaObject(pageInfo, ChartQueryDto.class);
        dto.setTid(((KpiDto)dto.getKpiJson().get(0)).getTid());
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.app.table");
        TableInfoVO tinfo = this.cahceService.getTableInfo(dto.getTid());
        JSONObject chartJson = pageInfo.getJSONObject("chartJson");
        JSONArray params = (JSONArray)pageInfo.get("params");
        if (params == null) {
            params = chartJson.getJSONArray("params");
        }

        this.createParams(mv, tinfo, params, "chart");
        ChartContext cr = this.chartService.json2Chart(dto, true);
        String sqls = this.chartService.createSql(dto, 0, 0);
        GridDataCenterContext dc = this.chartService.createDataCenter(dto, sqls);
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

    private void createParams(MVContext mv, TableInfoVO tinfo, JSONArray params, String tp) throws IOException, ExtConfigException {
        DivContext div = new DivContextImpl();
        div.setStyleClass("rpeortParam");
        div.setChildren(new ArrayList());
        mv.getChildren().add(div);
        div.setParent(mv);
        if (params != null && params.size() != 0) {
            for(int i = 0; params != null && i < params.size(); ++i) {
                JSONObject param = params.getJSONObject(i);
                String name = param.getString("name");
                String type = param.getString("type");
                String colname = param.getString("alias");
                InputField input = null;
                InputField input2 = null;
                SelectContextImpl target;
                String sql;
                String template;
                String start;
                if (!"frd".equalsIgnoreCase(type) && !"year".equalsIgnoreCase(type) && !"quarter".equalsIgnoreCase(type)) {
                    String max;
                    String dateformat;
                    if ("day".equalsIgnoreCase(type)) {
                        sql = this.createDimSql(param, tinfo);
                        List<Map<String, Object>> datas = this.daoHelper.queryForList(sql);
                        Map<String, Object> first = (Map)datas.get(0);
                        Map<String, Object> end = (Map)datas.get(datas.size() - 1);
                        String min = (String)first.get("value");
                        max = (String)end.get("value");
                        dateformat = (String)param.get("dateformat");
                        if (dateformat != null) {
                            dateformat = dateformat.replaceAll("mm", "MM");
                        }

                        DateSelectContextImpl target01 = new DateSelectContextImpl();
                        target01.setDesc("时间区间");
                        target01.setId("s_" + colname);
                        target01.setDateFormat(dateformat);
                        target01.setSize("9");
                        target01.setMaxDate(max);
                        target01.setMinDate(min);
                        target01.setTmpval(param);
                        input = target01;
                        start = (String)param.get("st");
                        if (start == null || start.length() == 0) {
                            start = min;
                        }
                        target01.setValue(start);
                        DateSelectContextImpl target2 = new DateSelectContextImpl();
                        target2.setId("e_" + colname);
                        target2.setSize("9");
                        target2.setDateFormat(dateformat);
                        target2.setMaxDate(max);
                        target2.setMinDate(min);
                        target2.setDefaultValue(max);
                        target2.setTmpval(param);
                        input2 = target2;
                        String endd = (String)param.get("end");
                        if (endd == null || endd.length() == 0) {
                            endd = max;
                        }

                        target2.setValue(endd);
                    } else if ("month".equalsIgnoreCase(type)) {
                        target = new SelectContextImpl();
                        sql = this.createDimSql(param, tinfo);
                        template = TemplateManager.getInstance().createTemplate(sql);
                        target.setTemplateName(template);
                        input = target;
                        target.setTmpval(param);
                        target.setDesc(name);
                        target.setId("s_" + colname);
                        start = (String)param.get("st");
                        target.setAddEmptyValue(true);
                        target.setValue(start);
                        SelectContextImpl target2 = new SelectContextImpl();
                        max = TemplateManager.getInstance().createTemplate(sql);
                        target2.setTemplateName(max);
                        target2.setDesc((String)null);
                        target2.setTmpval(param);
                        target2.setId("e_" + colname);
                        input2 = target2;
                        dateformat = (String)param.get("end");
                        target2.setAddEmptyValue(true);
                        target2.setValue(dateformat);
                    }
                } else {
                    target = new SelectContextImpl();
                    sql = this.createDimSql(param, tinfo);
                    template = TemplateManager.getInstance().createTemplate(sql);
                    target.setTemplateName(template);
                    input = target;
                    target.setTmpval(param);
                    target.setAddEmptyValue(true);
                    start = (String)param.get("vals");
                    target.setValue(start);
                    target.setDesc(name);
                    target.setId(colname);
                }

                div.getChildren().add(input);
                ((InputField)input).setParent(div);
                if (input2 != null) {
                    div.getChildren().add(input2);
                    ((InputField)input2).setParent(div);
                }

                if ("table".equalsIgnoreCase(tp)) {
                    this.tableService.getMvParams().put(((InputField)input).getId(), input);
                    if (input2 != null) {
                        this.tableService.getMvParams().put(((InputField)input2).getId(), input2);
                    }
                } else {
                    this.chartService.getMvParams().put(((InputField)input).getId(), input);
                    if (input2 != null) {
                        this.chartService.getMvParams().put(((InputField)input2).getId(), input2);
                    }
                }

                mv.setMvParam(((InputField)input).getId(), (InputField)input);
                if (input2 != null) {
                    mv.setMvParam(((InputField)input2).getId(), (InputField)input2);
                }

                mv.setShowForm(true);
            }

            ButtonContext btn = new ButtonContextImpl();
            btn.setDesc("查询");
            btn.setType("button");
            btn.setFrom(mv.getMvid());
            btn.setMvId(new String[]{mv.getMvid()});
            div.getChildren().add(btn);
            btn.setParent(div);
        }
    }

    private String createDimSql(JSONObject dim, TableInfoVO tinfo) {
        String tableColName = (String)dim.get("tableColName");
        String tableColKey = (String)dim.get("tableColKey");
        String colname = (String)dim.get("colname");
        String sql = "select distinct " + (tableColKey != null && tableColKey.length() > 0 ? tableColKey : colname) + " \"value\", " + (tableColName != null && tableColName.length() > 0 ? tableColName : colname) + " \"text\" from ";
        if (tinfo.getSql() != null && tinfo.getSql().length() > 0) {
            sql = sql + "(" + tinfo.getSql() + ")";
        } else {
            sql = sql + tinfo.getTname();
        }

        sql = sql + " order by " + dim.get("colname") + " " + dim.get("dimord");
        return sql;
    }

    public Map<String, InputField> getMvParams(String tp) {
        return "table".equals(tp) ? this.tableService.getMvParams() : this.chartService.getMvParams();
    }
}
