//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.portal;

import com.ruisi.ext.engine.dao.DaoRsbiHelperImpl;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisitech.bi.annotation.TidCheck;
import com.ruisitech.bi.entity.portal.PortalChartQuery;
import com.ruisitech.bi.service.portal.PortalChartService;
import com.ruisitech.bi.util.CompPreviewService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("portalChartController")
@Scope("prototype")
@RequestMapping({"/portal"})
public class ChartViewController {
    private static Logger log = Logger.getLogger(ChartViewController.class);
    @Autowired
    private PortalChartService chartService;

    public ChartViewController() {
    }

    @RequestMapping(
        value = {"/ChartView.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    @TidCheck
    public Object chartView(@RequestBody PortalChartQuery chartJson, HttpServletRequest req, HttpServletResponse res) {
        try {
            //绑定tid到线程
            if(chartJson!=null){
                DaoRsbiHelperImpl.getDaoRsbiThreadLocal().set(chartJson.getTid()+"");
            }
            ExtContext.getInstance().removeMV("mv.portal.chart");
            MVContext mv = this.chartService.json2MV(chartJson);
            req.setAttribute("compId", chartJson.getId());
            CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
            ser.setParams(this.chartService.getMvParams());
            ser.initPreview();
            String ret = ser.buildMV(mv, req.getServletContext());
            return ret;
        } catch (Exception var7) {
            log.error("chart出错了。", var7);
            return var7.getMessage();
        }
    }

    @RequestMapping({"/ChartType.action"})
    public String chartType() {
        return "portal/ChartView-chartType";
    }

    @RequestMapping({"/chartColors.action"})
    @ResponseBody
    public Object chartColors() {
        return this.chartService.queryChartColors();
    }
}
