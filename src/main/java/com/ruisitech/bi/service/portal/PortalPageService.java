//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.portal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.chart.ChartKeyContext;
import com.ruisi.ext.engine.view.context.cross.BaseKpiField;
import com.ruisi.ext.engine.view.context.cross.CrossKpi;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.RowDimContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.face.OptionsLoader;
import com.ruisi.ext.engine.view.context.form.ButtonContext;
import com.ruisi.ext.engine.view.context.form.ButtonContextImpl;
import com.ruisi.ext.engine.view.context.form.DateSelectContextImpl;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.context.form.MultiSelectContext;
import com.ruisi.ext.engine.view.context.form.MultiSelectContextImpl;
import com.ruisi.ext.engine.view.context.form.SelectContextImpl;
import com.ruisi.ext.engine.view.context.form.TextFieldContext;
import com.ruisi.ext.engine.view.context.form.TextFieldContextImpl;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.context.html.DivContext;
import com.ruisi.ext.engine.view.context.html.DivContextImpl;
import com.ruisi.ext.engine.view.context.html.ImageContext;
import com.ruisi.ext.engine.view.context.html.ImageContextImpl;
import com.ruisi.ext.engine.view.context.html.TextContext;
import com.ruisi.ext.engine.view.context.html.TextContextImpl;
import com.ruisi.ext.engine.view.context.html.TextProperty;
import com.ruisi.ext.engine.view.context.html.table.TableContext;
import com.ruisi.ext.engine.view.context.html.table.TableContextImpl;
import com.ruisi.ext.engine.view.context.html.table.TdContext;
import com.ruisi.ext.engine.view.context.html.table.TdContextImpl;
import com.ruisi.ext.engine.view.context.html.table.TrContext;
import com.ruisi.ext.engine.view.context.html.table.TrContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisitech.bi.entity.bireport.DimDto;
import com.ruisitech.bi.entity.bireport.KpiDto;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.model.TableDimension;
import com.ruisitech.bi.entity.portal.BoxQuery;
import com.ruisitech.bi.entity.portal.GridColDto;
import com.ruisitech.bi.entity.portal.GridQuery;
import com.ruisitech.bi.entity.portal.LinkAcceptDto;
import com.ruisitech.bi.entity.portal.PortalChartQuery;
import com.ruisitech.bi.entity.portal.PortalTableQuery;
import com.ruisitech.bi.service.bireport.BaseCompService;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.service.model.TableDimensionService;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class PortalPageService extends BaseCompService {
    public static final String deftMvId = "mv.portal.tmp";
    private Map<String, InputField> mvParams = new HashMap();
    private StringBuffer css = new StringBuffer();
    private StringBuffer scripts = new StringBuffer();
    @Autowired
    private PortalChartService chartService;
    @Autowired
    private TableCacheService cacheService;
    @Autowired
    private TableDimensionService dimService;
    @Autowired
    private PortalTableService tableSerivce;
    @Autowired
    private BoxService boxSerivce;
    @Autowired
    private PortalGridService gridSerivce;

    public PortalPageService() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
        this.mvParams.clear();
    }

    public MVContext json2MV(JSONObject pageJson, boolean release, boolean export) throws Exception {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.portal.tmp");
        mv.setHideMV(false);
        Object param = pageJson.get("params");
        if (param != null && ((JSONArray)param).size() > 0) {
            DivContext outdiv = new DivContextImpl();
            outdiv.setStyleClass("ibox");
            outdiv.setStyle("margin:10px;");
            outdiv.setChildren(new ArrayList());
            outdiv.setParent(mv);
            mv.getChildren().add(outdiv);
            DivContext div = new DivContextImpl();
            div.setStyleClass("ibox-content");
            div.setStyle("padding:5px;");
            div.setParent(outdiv);
            div.setChildren(new ArrayList());
            outdiv.getChildren().add(div);
            JSONArray pp = (JSONArray)param;

            for(int i = 0; i < pp.size(); ++i) {
                this.parserParam(pp.getJSONObject(i), div, mv, !release);
            }

            ButtonContext btn = new ButtonContextImpl();
            btn.setDesc("查询");
            btn.setType("button");
            btn.setMvId(new String[]{mv.getMvid()});
            div.getChildren().add(btn);
            btn.setParent(div);
        }

        JSONObject body = pageJson.getJSONObject("body");
        super.setPageBody(body);
        this.chartService.setPageBody(body);
        this.tableSerivce.setPageBody(body);
        this.parserBody(body, mv, param, release);
        TextContext csstext = new TextContextImpl();
        csstext.setText("<style>" + this.css.toString() + "</style>");
        mv.getChildren().add(csstext);
        csstext.setParent(mv);
        return mv;
    }

    public void parserBody(JSONObject body, MVContext mv, Object param, boolean release) throws Exception {
        TableContext tab = new TableContextImpl();
        tab.setStyleClass("r_layout");
        tab.setChildren(new ArrayList());
        mv.getChildren().add(tab);
        tab.setParent(mv);
        int i = 1;

        while(true) {
            Object tmp = body.get("tr" + i);
            if (tmp == null) {
                if (this.scripts != null) {
                    mv.setScripts(this.scripts.toString());
                }

                return;
            }

            JSONArray trs = (JSONArray)tmp;
            TrContext tabTr = new TrContextImpl();
            tabTr.setChildren(new ArrayList());
            tab.getChildren().add(tabTr);
            tabTr.setParent(tab);

            for(int j = 0; j < trs.size(); ++j) {
                JSONObject td = trs.getJSONObject(j);
                TdContext tabTd = new TdContextImpl();
                tabTd.setStyleClass("layouttd");
                tabTd.setChildren(new ArrayList());
                tabTd.setParent(tabTr);
                tabTr.getChildren().add(tabTd);
                tabTd.setColspan(String.valueOf(td.getIntValue("colspan")));
                tabTd.setRowspan(String.valueOf(td.getIntValue("rowspan")));
                tabTd.setWidth(td.getIntValue("width") + "%");
                Object cldTmp = td.get("children");
                if (cldTmp != null) {
                    JSONArray children = (JSONArray)cldTmp;

                    for(int k = 0; k < children.size(); ++k) {
                        JSONObject comp = children.getJSONObject(k);
                        String tp = comp.getString("type");
                        String showtitle = (String)comp.get("showtitle");
                        DivContext div = new DivContextImpl();
                        div.setStyleClass("ibox");
                        div.setChildren(new ArrayList());
                        tabTd.getChildren().add(div);
                        div.setParent(tabTd);
                        if (k == children.size() - 1) {
                            div.setStyle("margin-bottom:0px;");
                        }

                        div.setStyle((div.getStyle() == null ? "" : div.getStyle()) + "border:none;");
                        DivContextImpl content;
                        if ((showtitle == null || !"false".equalsIgnoreCase(showtitle)) && !tp.equals("box")) {
                            content = new DivContextImpl();
                            content.setChildren(new ArrayList());
                            content.setStyleClass("ibox-title-view");
                            div.getChildren().add(content);
                            content.setParent(div);
                            TextContext text = new TextContextImpl();
                            text.setText(comp.getString("name"));
                            TextProperty ctp = new TextProperty();
                            ctp.setAlign("center");
                            ctp.setWeight("bold");
                            text.setTextProperty(ctp);
                            content.getChildren().add(text);
                            text.setParent(content);
                        }

                        content = new DivContextImpl();
                        if (!"false".equalsIgnoreCase(showtitle)) {
                            content.setStyleClass("ibox-content");
                            content.setStyle("border-top:none; padding:3px;");
                        }

                        String bgcolor = (String)comp.get("bgcolor");
                        if (bgcolor != null && bgcolor.length() > 0) {
                            content.setStyle((content.getStyle() == null ? "" : content.getStyle()) + "background-color:" + bgcolor + ";");
                        }

                        content.setChildren(new ArrayList());
                        div.getChildren().add(content);
                        content.setParent(div);
                        if (tp.equals("text")) {
                            this.createText(content, comp);
                        } else if (tp.equals("chart")) {
                            PortalChartQuery chart = (PortalChartQuery)JSONObject.toJavaObject(comp, PortalChartQuery.class);
                            this.createChart(mv, content, chart, release);
                        } else if (tp.equals("table")) {
                            PortalTableQuery table = (PortalTableQuery)JSONObject.toJavaObject(comp, PortalTableQuery.class);
                            this.crtTable(mv, content, table, release);
                        } else if (tp.equals("grid")) {
                            GridQuery grid = (GridQuery)JSONObject.toJavaObject(comp, GridQuery.class);
                            this.crtGrid(mv, content, grid, release);
                        } else if (tp.equals("box")) {
                            BoxQuery ncomp = (BoxQuery)JSONObject.toJavaObject(comp, BoxQuery.class);
                            this.createBox(mv, content, ncomp);
                        } else if (tp.equals("pic")) {
                            this.createImage(content, comp);
                        }
                    }
                }
            }

            ++i;
        }
    }

    public void createBox(MVContext mv, Element td, BoxQuery compJson) throws IOException {
        this.boxSerivce.json2Box(compJson, td, mv, true);
    }

    public void createImage(Element td, JSONObject compJson) {
        String picType = compJson.getString("picType");
        if (picType != null && picType.length() != 0) {
            ImageContext img = new ImageContextImpl();
            if ("net".equals(picType)) {
                img.setUrl(compJson.getString("picurl"));
            } else {
                img.setPath(compJson.getString("picurl"));
            }

            img.setType(picType);
            img.setAlign("center");
            img.setWidth(compJson.getInteger("width"));
            img.setHeight(compJson.getInteger("height"));
            td.getChildren().add(img);
            img.setParent(td);
        }
    }

    public void crtGrid(MVContext mv, Element tabTd, GridQuery grid, boolean release) throws IOException {
        List<GridColDto> cols = grid.getCols();
        if (cols != null && !cols.isEmpty() && cols.size() != 0) {
            GridReportContext cr = this.gridSerivce.json2Grid(grid);
            String id = "report" + IdCreater.create();
            cr.setId(id);
            String sql = this.gridSerivce.createSql(grid);
            GridDataCenterContext dc = this.gridSerivce.createDataCenter(grid, sql);
            cr.setRefDataCenter(dc.getId());
            if (mv.getGridDataCenters() == null) {
                mv.setGridDataCenters(new HashMap());
            }

            mv.getGridDataCenters().put(dc.getId(), dc);
            tabTd.getChildren().add(cr);
            cr.setParent(tabTd);
            if (mv.getGridReports() == null) {
                Map<String, GridReportContext> crs = new HashMap();
                mv.setGridReports(crs);
            }

            Map<String, GridReportContext> crs = mv.getGridReports();
            crs.put(cr.getId(), cr);
            mv.setGridReports(crs);
        }
    }

    public void crtTable(MVContext mv, Element tabTd, PortalTableQuery table, boolean release) throws Exception {
        if (table.getKpiJson() != null && table.getKpiJson().size() != 0) {
            TableInfoVO tinfo = this.cacheService.getTableInfo(table.getTid());
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
                mybaseKpi = baseKpi;
                cr = this.tableSerivce.getTableService().json2Table(table);
                cr.setBaseKpi(baseKpi);
            } else {
                DimDto kpiOther = new DimDto();
                kpiOther.setType("kpiOther");
                cols.add(kpiOther);
                cr = this.tableSerivce.getTableService().json2Table(table);
                cols.remove(cols.size() - 1);
            }

            String id = "report" + IdCreater.create();
            cr.setId(id);
            cr.setOut("lockUI");
            String height = table.getHeight();
            if (height != null && height.length() > 0) {
                cr.setHeight(height);
            }

            cr.setShowData(true);
            if (mybaseKpi != null) {
                cr.setBaseKpi(mybaseKpi);
            }

            String sqls = this.tableSerivce.createSql(table, 0, 0, 0);
            GridDataCenterContext dc = this.tableSerivce.createDataCenter(sqls, table);
            cr.setRefDataCetner(dc.getId());
            if (mv.getGridDataCenters() == null) {
                mv.setGridDataCenters(new HashMap());
            }

            mv.getGridDataCenters().put(dc.getId(), dc);
            List<RowDimContext> drillDims = cr.getDims();

            for(int i = 0; drillDims != null && i < drillDims.size(); ++i) {
                RowDimContext drillDim = (RowDimContext)drillDims.get(i);
                sqls = this.tableSerivce.createSql(table, 0, i + 1, 0);
                GridDataCenterContext drillDc = this.tableSerivce.createDataCenter(sqls, table);
                drillDim.setRefDataCenter(drillDc.getId());
                mv.getGridDataCenters().put(drillDc.getId(), drillDc);
            }

            tabTd.getChildren().add(cr);
            cr.setParent(tabTd);
            LinkAcceptDto linkAccept = table.getLinkAccept();
            if (linkAccept != null && linkAccept.getId() != null) {
                String alias = super.findEventParamName(table.getId());
                if (alias == null) {
                    alias = linkAccept.getAlias();
                }

                String tableColKey = linkAccept.getTableColKey();
                if (tableColKey != null && tableColKey.length() > 0) {
                    alias = tableColKey;
                }

                if (!this.mvParams.containsKey(alias)) {
                    TextFieldContext linkText = new TextFieldContextImpl();
                    linkText.setType("hidden");
                    linkText.setDefaultValue(linkAccept.getDftval());
                    linkText.setId(alias);
                    linkText.setShow(true);
                    mv.getChildren().add(0, linkText);
                    linkText.setParent(mv);
                    if (!release) {
                        this.mvParams.put(linkText.getId(), linkText);
                        mv.setMvParam(linkText.getId(), linkText);
                        mv.setShowForm(true);
                    }
                }
            }

            if (mv.getCrossReports() == null) {
                Map<String, CrossReportContext> crs = new HashMap();
                mv.setCrossReports(crs);
            }

            mv.getCrossReports().put(cr.getId(), cr);
            this.scripts.append(this.tableSerivce.getTableService().getScripts());
        }
    }

    public void createText(Element td, JSONObject compJson) {
        TextContext text = new TextContextImpl();
        TextProperty tp = new TextProperty();
        Integer height = compJson.getInteger("height");
        tp.setHeight(String.valueOf(height));
        JSONObject style = (JSONObject)compJson.get("style");
        String desc;
        if (style != null && !style.isEmpty()) {
            tp.setAlign((String)style.get("talign"));
            tp.setSize((String)style.get("tfontsize"));
            desc = (String)style.get("tfontweight");
            tp.setWeight("true".equals(desc) ? "bold" : "normal");
            tp.setColor((String)style.get("tfontcolor"));
            tp.setId("C" + IdCreater.create());
            this.css.append("#" + tp.getId() + "{");
            String italic = (String)style.get("titalic");
            String underscore = (String)style.get("tunderscore");
            String lineheight = (String)style.get("tlineheight");
            String tbgcolor = (String)style.get("tbgcolor");
            if ("true".equals(italic)) {
                this.css.append("font-style:italic;");
            }

            if ("true".equals(underscore)) {
                this.css.append("text-decoration: underline;");
            }

            if (lineheight != null && lineheight.length() > 0) {
                this.css.append("line-height:" + lineheight + "px;");
            }

            if (tbgcolor != null && tbgcolor.length() > 0) {
                this.css.append("background-color:" + tbgcolor + ";");
            }

            this.css.append("}");
        }

        text.setTextProperty(tp);
        desc = compJson.getString("desc");
        text.setText(desc);
        text.setParent(td);
        text.setFormatEnter(true);
        td.getChildren().add(text);
    }

    public void createChart(MVContext mv, Element tabTd, PortalChartQuery chart, boolean release) throws Exception {
        if (chart.getMkpi() != null && chart.getMkpi() || chart.getChartJson() != null && chart.getKpiJson() != null && chart.getKpiJson().size() != 0) {
            if (!chart.getMkpi() || chart.getMkpiJson() != null && chart.getMkpiJson().size() != 0) {
                TableInfoVO tinfo = this.cacheService.getTableInfo(chart.getTid());
                ChartContext cr = this.chartService.json2Chart(chart);

                for(int i = 0; i < cr.getProperties().size(); ++i) {
                    ChartKeyContext p = (ChartKeyContext)cr.getProperties().get(i);
                    if (p.getName().equals("action")) {
                        p.setValue((String)null);
                    }
                }

                cr.setId("C" + IdCreater.create());
                String sql = this.chartService.createSql(chart, 1);
                GridDataCenterContext dc = this.chartService.getChartService().createDataCenter(chart, sql);
                cr.setRefDataCenter(dc.getId());
                if (mv.getGridDataCenters() == null) {
                    mv.setGridDataCenters(new HashMap());
                }

                mv.getGridDataCenters().put(dc.getId(), dc);
                tabTd.getChildren().add(cr);
                cr.setParent(tabTd);
                if (mv.getCharts() == null) {
                    Map<String, ChartContext> crs = new HashMap();
                    mv.setCharts(crs);
                }

                mv.getCharts().put(cr.getId(), cr);
                LinkAcceptDto linkAccept = chart.getChartJson().getLinkAccept();
                if (linkAccept != null && linkAccept.getId() != null) {
                    String alais = super.findEventParamName(chart.getId());
                    if (alais == null || alais.length() == 0) {
                        alais = linkAccept.getTableColKey() != null && linkAccept.getTableColKey().length() > 0 ? linkAccept.getTableColKey() : linkAccept.getAlias();
                    }

                    if (!this.mvParams.containsKey(alais)) {
                        TextFieldContext linkText = new TextFieldContextImpl();
                        linkText.setType("hidden");
                        linkText.setDefaultValue(linkAccept.getDftval());
                        linkText.setId(alais);
                        linkText.setShow(true);
                        mv.getChildren().add(0, linkText);
                        linkText.setParent(mv);
                        if (!release) {
                            this.mvParams.put(linkText.getId(), linkText);
                            mv.setMvParam(linkText.getId(), linkText);
                            mv.setShowForm(true);
                        }
                    }
                }

            }
        }
    }

    public void parserParam(JSONObject param, DivContext div, MVContext mv, boolean isput) throws ExtConfigException, IOException {
        String type = param.getString("type");
        String id = param.getString("paramid");
        String desc = param.getString("name");
        String def = (String)param.get("defvalue");
        String vtp = (String)param.get("valtype");
        String dtformat = (String)param.get("dtformat");
        String hiddenprm = (String)param.get("hiddenprm");
        InputField input = null;
        TextFieldContextImpl target;
        if ("y".equals(hiddenprm)) {
            target = new TextFieldContextImpl();
            target.setType("hidden");
            target.setShow(true);
            input = target;
        } else {
            String sql;
            String min;
            if ("radio".equals(type)) {
                SelectContextImpl target = new SelectContextImpl();
                if ("static".equals(vtp)) {
                    this.paramOptions(param, target);
                } else if ("dynamic".equals(vtp)) {
                    sql = this.createDimSql(param);
                    min = TemplateManager.getInstance().createTemplate(sql);
                    target.setTemplateName(min);
                }

                target.setAddEmptyValue(true);
                input = target;
            } else if ("checkbox".equals(type)) {
                MultiSelectContext target = new MultiSelectContextImpl();
                if ("static".equals(vtp)) {
                    this.paramOptions(param, target);
                } else if ("dynamic".equals(vtp)) {
                    sql = this.createDimSql(param);
                    min = TemplateManager.getInstance().createTemplate(sql);
                    target.setTemplateName(min);
                }

                input = target;
            } else if (!"dateselect".equals(type) && !"monthselect".equals(type) && !"yearselect".equals(type)) {
                if ("text".equals(type)) {
                    target = new TextFieldContextImpl();
                    input = target;
                }
            } else {
                DateSelectContextImpl target = new DateSelectContextImpl();
                sql = (String)param.get("maxval");
                if (sql != null && sql.length() > 0) {
                    target.setMaxDate(sql);
                }

                min = (String)param.get("minval");
                if (min != null && min.length() > 0) {
                    target.setMinDate(min);
                }

                if (dtformat != null && dtformat.length() > 0) {
                    target.setDateFormat(dtformat);
                }

                if ("monthselect".equals(type)) {
                    target.setDateType("month");
                } else if ("yearselect".equals(type)) {
                    target.setDateType("year");
                }

                input = target;
            }
        }

        ((InputField)input).setId(id);
        ((InputField)input).setDesc(desc);
        String size = (String)param.get("size");
        if (size != null && size.length() > 0) {
            if (!"radio".equals(type) && !"checkbox".equals(type)) {
                ((InputField)input).setSize(size);
            } else {
                ((InputField)input).setSize(String.valueOf(Integer.parseInt(size) * 8));
            }
        }

        if (def != null && def.length() > 0) {
            if ("dateselect".equals(type) || "monthselect".equals(type) || "yearselect".equals(type)) {
                def = this.parserDefDate(type, dtformat, def);
            }

            ((InputField)input).setDefaultValue(def);
        }

        ((InputField)input).setOutBox(true);
        div.getChildren().add(input);
        ((InputField)input).setParent(div);
        if (isput) {
            this.mvParams.put(((InputField)input).getId(), input);
            mv.setMvParam(((InputField)input).getId(), (InputField)input);
        }

    }

    private void paramOptions(JSONObject param, OptionsLoader option) {
        List<Map<String, Object>> ls = option.loadOptions();
        if (ls == null) {
            ls = new ArrayList();
            option.setOptions((List)ls);
        }

        Object vals = param.get("values");
        if (vals != null) {
            JSONArray values = (JSONArray)vals;

            for(int i = 0; i < values.size(); ++i) {
                JSONObject opt = values.getJSONObject(i);
                Map<String, Object> nOption = new HashMap();
                nOption.put("text", opt.getString("text"));
                nOption.put("value", opt.getString("value"));
                ((List)ls).add(nOption);
            }
        }

    }

    public String createDimSql(JSONObject dim) {
        JSONObject opt = dim.getJSONObject("option");
        TableDimension d = this.dimService.queryDimCol(opt.getInteger("dimId"), opt.getInteger("tableId"));
        String col = d.getCol();
        String key = d.getColkey();
        String name = d.getColtext();
        String dimord = d.getDimord();
        String ordcol = d.getOrdcol();
        String tname = d.getTname();
        String sql = "select distinct " + (key != null && key.length() != 0 ? key : col) + " \"value\", " + (name != null && name.length() != 0 ? name : col) + " \"text\" from " + tname;
        if (ordcol != null && ordcol.length() > 0) {
            sql = sql + " order by " + ordcol;
        }

        if (ordcol != null && ordcol.length() > 0 && dimord != null && dimord.length() > 0) {
            sql = sql + " " + dimord;
        }

        return sql;
    }

    public String createMonthSql() {
        String sql = "select mid \"value\", mname \"text\" from code_month order by mid desc";
        return sql;
    }

    private String parserDefDate(String type, String dtformat, String defVal) {
        String regEx = "\\s*now\\s*([+|-]*)\\s*([0-9]*)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(defVal);
        if (m.find()) {
            String m1 = m.group(1).trim();
            String m2 = m.group(2).trim();
            Calendar cal = Calendar.getInstance();
            if (m1.length() > 0 && m2.length() > 0) {
                int tp = 5;
                if ("dateselect".equals(type)) {
                    tp = 5;
                } else if ("monthselect".equals(type)) {
                    tp = 2;
                } else if ("yearselect".equals(type)) {
                    tp = 1;
                }

                Integer step = new Integer(m2);
                if ("+".equals(m1)) {
                    cal.add(tp, step);
                } else {
                    cal.add(tp, -step);
                }

                return (new SimpleDateFormat(dtformat)).format(cal.getTime());
            } else {
                return defVal.trim().equals("now") ? (new SimpleDateFormat(dtformat)).format(cal.getTime()) : defVal;
            }
        } else {
            return defVal;
        }
    }

    public Map<String, InputField> getMvParams() {
        return this.mvParams;
    }
}
