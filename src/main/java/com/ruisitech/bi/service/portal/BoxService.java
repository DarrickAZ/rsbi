//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.portal;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContextImpl;
import com.ruisi.ext.engine.view.context.dc.grid.GridSetConfContext;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.context.html.DataContext;
import com.ruisi.ext.engine.view.context.html.DataContextImpl;
import com.ruisi.ext.engine.view.context.html.TextContext;
import com.ruisi.ext.engine.view.context.html.TextContextImpl;
import com.ruisi.ext.engine.view.context.html.TextProperty;
import com.ruisitech.bi.entity.bireport.KpiDto;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.portal.BoxQuery;
import com.ruisitech.bi.service.bireport.BaseCompService;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.service.etl.ElasticService;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.ext.service.DataControlInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class BoxService extends BaseCompService {
    public static final String deftMvId = "mv.portal.box";
    private Map<String, InputField> mvParams = new HashMap();
    @Autowired
    private DataControlInterface dataControl;
    @Autowired
    private TableCacheService cacheService;
    @Autowired
    private ElasticService elaService;

    public BoxService() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
        this.mvParams.clear();
    }

    public MVContext json2MV(BoxQuery box) throws Exception {
        MVContext mv = new MVContextImpl();
        mv.setChildren(new ArrayList());
        String formId = "f" + IdCreater.create();
        mv.setFormId(formId);
        mv.setMvid("mv.portal.box");
        mv.setHideMV(true);
        this.parserHiddenParam(box.getPortalParams(), mv, this.mvParams);
        this.json2Box(box, mv, mv, false);
        return mv;
    }

    public void json2Box(BoxQuery box, Element curEle, MVContext mv, boolean crtTitle) throws IOException {
        if (box.getKpiJson() != null && box.getKpiJson().getKpi_id() != null) {
            if (crtTitle) {
                TextContext text = new TextContextImpl();
                TextProperty tp = new TextProperty();
                tp.setAlign("center");
                tp.setColor(box.getKpiJson().getTfontcolor());
                tp.setStyleClass("ibox-title-view");
                text.setText(box.getName());
                text.setTextProperty(tp);
                curEle.getChildren().add(text);
                text.setParent(curEle);
            }

            Integer tid = box.getTid();
            TableInfoVO tinfo = this.cacheService.getTableInfo(tid);
            String sql = this.createSql(box, tinfo, 0);
            GridDataCenterContext dc = this.createDataCenter(sql, box.getKpiJson(), tinfo);
            if (mv.getGridDataCenters() == null) {
                mv.setGridDataCenters(new HashMap());
            }

            mv.getGridDataCenters().put(dc.getId(), dc);
            DataContext data = new DataContextImpl();
            data.setKey("k" + box.getKpiJson().getKpi_id());
            data.setRefDataCenter(dc.getId());
            curEle.getChildren().add(data);
            data.setParent(curEle);
            TextContext text = new TextContextImpl();
            String str = "#if($!k" + box.getKpiJson().getKpi_id() + "." + box.getKpiJson().getAlias() + ") $extUtils.numberFmt($!k" + box.getKpiJson().getKpi_id() + "." + box.getKpiJson().getAlias() + ", '" + box.getKpiJson().getFmt() + "') #if($outType=='html') <font style='font-size:12px;'> #end\n";
            Integer rate = box.getKpiJson().getRate();
            if (rate != null) {
                str = str + this.writerUnit(rate);
            }

            str = str + box.getKpiJson().getUnit() + " #if($outType=='html') </font> #end\n";
            str = str + "#else - #end\n";
            String word = TemplateManager.getInstance().createTemplate(str);
            text.setTemplateName(word);
            text.setFormatHtml(true);
            TextProperty tp = new TextProperty();
            tp.setAlign("center");
            tp.setWeight("normal");
            tp.setLineHeight(box.getHeight());
            tp.setHeight(String.valueOf(box.getHeight()));
            Integer tfontsize = box.getKpiJson().getTfontsize();
            if (tfontsize != null) {
                tp.setSize(String.valueOf(tfontsize));
            } else {
                tp.setSize("32");
            }

            String tfontcolor = box.getKpiJson().getTfontcolor();
            if (tfontcolor != null && tfontcolor.length() > 0) {
                tp.setColor(tfontcolor);
            } else {
                String style = box.getDashboardStyle();
                if ("bigscreen".equals(style)) {
                    tp.setColor("#eaeaea");
                } else {
                    tp.setColor("#000000");
                }
            }

            tp.setStyleClass("boxcls");
            text.setTextProperty(tp);
            curEle.getChildren().add(text);
            text.setParent(curEle);
        }
    }

    public String createSql(BoxQuery box, TableInfoVO tinfo, int index) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(box.getKpiJson().getCol_name());
        Integer rate = box.getKpiJson().getRate();
        if (rate != null) {
            sql.append("/" + rate);
        }

        sql.append(" as ");
        sql.append(box.getKpiJson().getAlias());
        sql.append(" from ");
        if (tinfo.getSql() != null && tinfo.getSql().length() > 0) {
            sql.append("(" + tinfo.getSql() + ") cc ");
        } else {
            sql.append(tinfo.getTname());
        }

        sql.append(" where 1=1 ");
        if (this.dataControl != null) {
            String ret = this.dataControl.process(RSBIUtils.getLoginUserInfo(), tinfo.getTname());
            if (ret != null) {
                sql.append(ret + " ");
            }
        }

        sql.append(" " + super.dealCubeParams(box.getCompParams(), "dim", tinfo));
        sql.append(super.dealDashboardParams(box.getDashboardParam(), tinfo));
        return sql.toString();
    }

    public GridDataCenterContext createDataCenter(String sql, KpiDto kpi, TableInfoVO tinfo) throws IOException {
        GridDataCenterContext ctx = new GridDataCenterContextImpl();
        GridSetConfContext conf = new GridSetConfContext();
        if ("y".equals(tinfo.getEsEnable()) && "y".equals(tinfo.getUseEs())) {
            conf.setMaster(tinfo.getTname());
            conf.setDatasetProvider(this.elaService);
        }

        ctx.setConf(conf);
        ctx.setId("DC-" + IdCreater.create());
        String name = TemplateManager.getInstance().createTemplate(sql);
        ctx.getConf().setTemplateName(name);
        return ctx;
    }

    public Map<String, InputField> getMvParams() {
        return this.mvParams;
    }
}
