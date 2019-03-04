//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.bireport;

import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisitech.bi.entity.bireport.Area;
import com.ruisitech.bi.entity.bireport.ChartQueryDto;
import com.ruisitech.bi.service.bireport.AreaService;
import com.ruisitech.bi.service.bireport.OlapChartService;
import com.ruisitech.bi.util.CompPreviewService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("bireportChart")
@Scope("prototype")
@RequestMapping({"/bireport"})
public class ChartController {
    private static Logger log = Logger.getLogger(ChartController.class);
    @Autowired
    private AreaService areaService;
    @Autowired
    private OlapChartService chartService;

    public ChartController() {
    }

    @RequestMapping(
        value = {"/ChartView.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object chartView(@RequestBody ChartQueryDto chartJson, HttpServletRequest req, HttpServletResponse res) {
        try {
            req.setAttribute("compId", String.valueOf(chartJson.getId()));
            ExtContext.getInstance().removeMV("mv.chart.tmp");
            MVContext mv = this.chartService.json2MV(chartJson, false);
            CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
            ser.initPreview();
            String ret = ser.buildMV(mv, req.getServletContext());
            return ret;
        } catch (Exception var7) {
            log.error("chart出错了。", var7);
            return var7.getMessage();
        }
    }

    @RequestMapping({"/insertChart.action"})
    public String insertChart(String tp, ModelMap model) {
        model.addAttribute("tp", tp);
        List<Area> areas = this.areaService.listProvs();
        model.addAttribute("areas", areas);
        return "bireport/Panel-insertChart";
    }

    @RequestMapping({"/getProvByName.action"})
    @ResponseBody
    public Object getProvByName(String name) {
        return this.areaService.getProvByName(name);
    }
}
