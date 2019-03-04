//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.emitter.ContextEmitter;
import com.ruisi.ext.engine.view.emitter.json.JSONEmitter;
import com.ruisitech.bi.service.app.AppPageService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.CompPreviewService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/app"})
public class CompViewController extends BaseController {
    @Autowired
    private AppPageService pageService;

    public CompViewController() {
    }

    @RequestMapping(
        value = {"/CompView!viewTable.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object viewAppTable(String pageInfo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ExtContext.getInstance().removeMV("mv.app.table");
        JSONObject json = (JSONObject)JSON.parse(pageInfo);
        MVContext mv = this.pageService.viewTable(json, "json", 0);
        CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
        ser.setParams(this.pageService.getMvParams("table"));
        ser.initPreview();
        ContextEmitter emitter = new JSONEmitter();
        String ret = ser.buildMV(mv, emitter, req.getServletContext());
        return ret;
    }

    @RequestMapping(
        value = {"/CompView!viewChart.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object viewAppChart(String pageInfo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ExtContext.getInstance().removeMV("mv.app.table");
        JSONObject json = (JSONObject)JSON.parse(pageInfo);
        MVContext mv = this.pageService.viewChart(json, 0);
        CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
        ser.setParams(this.pageService.getMvParams("chart"));
        ser.initPreview();
        ContextEmitter emitter = new JSONEmitter();
        String ret = ser.buildMV(mv, emitter, req.getServletContext());
        return ret;
    }
}
