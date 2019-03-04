//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.detail;

import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisitech.bi.entity.detail.DetailChartQueryDto;
import com.ruisitech.bi.service.detail.DetailChartSerice;
import com.ruisitech.bi.util.CompPreviewService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("detailChartViewController")
@RequestMapping({"/detail"})
public class ChartViewController {
    private static Logger log = Logger.getLogger(ChartViewController.class);
    @Autowired
    private DetailChartSerice service;

    public ChartViewController() {
    }

    @RequestMapping({"/ChartView.action"})
    @ResponseBody
    public Object view(@RequestBody DetailChartQueryDto chart, HttpServletRequest req, HttpServletResponse res) {
        try {
            ExtContext.getInstance().removeMV("detail.chart.tmp");
            MVContext mv = this.service.json2MV(chart);
            CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
            ser.initPreview();
            String ret = ser.buildMV(mv, req.getServletContext());
            return ret;
        } catch (Exception var7) {
            log.error("cross出错。", var7);
            return var7.getMessage();
        }
    }
}
