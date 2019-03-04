//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.portal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.emitter.ContextEmitter;
import com.ruisi.ext.engine.view.emitter.excel.ExcelEmitter;
import com.ruisi.ext.engine.view.emitter.pdf.PdfEmitter;
import com.ruisi.ext.engine.view.emitter.text.TextEmitter;
import com.ruisi.ext.engine.view.emitter.word.WordEmitter;
import com.ruisitech.bi.service.portal.PortalPageService;
import com.ruisitech.bi.service.portal.PortalService;
import com.ruisitech.bi.util.CompPreviewService;
import com.ruisitech.bi.util.RSBIUtils;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("prototype")
@RequestMapping({"/portal"})
public class PortalViewController {
    private static Logger log = Logger.getLogger(PortalViewController.class);
    @Autowired
    private PortalService portalService;
    @Autowired
    private PortalPageService pageService;

    public PortalViewController() {
    }

    @RequestMapping({"/view.action"})
    @ResponseBody
    public Object view(String pageId, HttpServletRequest req, HttpServletResponse res) {
        ExtContext.getInstance().removeMV("mv.portal.tmp");
        String cfg = this.portalService.getPortalCfg(pageId);
        if (cfg == null) {
            return "找不到报表文件。";
        } else {
            try {
                JSONObject json = (JSONObject)JSON.parse(cfg);
                MVContext mv = this.pageService.json2MV(json, false, false);
                CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
                ser.setParams(this.pageService.getMvParams());
                ser.initPreview();
                String ret = ser.buildMV(mv, req.getServletContext());
                return ret;
            } catch (Exception var9) {
                log.error("portal出错了。", var9);
                return var9.getMessage();
            }
        }
    }

    @RequestMapping({"/export.action"})
    public void export(String type, String pageId, String json, String picinfo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ExtContext.getInstance().removeMV("mv.portal.tmp");
        if (json == null || json.length() == 0) {
            json = this.portalService.getPortalCfg(pageId);
        }

        req.setAttribute("picinfo", picinfo);
        JSONObject obj = (JSONObject)JSON.parse(json);
        MVContext mv = this.pageService.json2MV(obj, false, true);
        CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
        ser.setParams(this.pageService.getMvParams());
        ser.initPreview();
        String fileName = "file.";
        if ("html".equals(type)) {
            fileName = fileName + "html";
        } else if ("excel".equals(type)) {
            fileName = fileName + "xls";
        } else if ("csv".equals(type)) {
            fileName = fileName + "csv";
        } else if ("pdf".equals(type)) {
            fileName = fileName + "pdf";
        } else if ("word".equals(type)) {
            fileName = fileName + "docx";
        }

        res.setContentType("application/x-msdownload");
        String contentDisposition = "attachment; filename=\"" + fileName + "\"";
        res.setHeader("Content-Disposition", contentDisposition);
        String ret;
        InputStream is;
        if ("html".equals(type)) {
            ret = ser.buildMV(mv, req.getServletContext());
            ret = RSBIUtils.htmlPage(ret, RSBIUtils.getConstant("resPath"), "report");
            is = IOUtils.toInputStream(ret, "utf-8");
            IOUtils.copy(is, res.getOutputStream());
            is.close();
        } else if ("excel".equals(type)) {
            ContextEmitter emitter = new ExcelEmitter();
            ser.buildMV(mv, emitter, req.getServletContext());
        } else if ("csv".equals(type)) {
            ContextEmitter emitter = new TextEmitter();
            ret = ser.buildMV(mv, emitter, req.getServletContext());
            is = IOUtils.toInputStream(ret, "gb2312");
            IOUtils.copy(is, res.getOutputStream());
            is.close();
        } else if ("pdf".equals(type)) {
            ContextEmitter emitter = new PdfEmitter();
            ser.buildMV(mv, emitter, req.getServletContext());
        } else if ("word".equals(type)) {
            ContextEmitter emitter = new WordEmitter();
            ser.buildMV(mv, emitter, req.getServletContext());
        }

    }

    @RequestMapping({"/print.action"})
    public String print(String pageId, String pageInfo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ExtContext.getInstance().removeMV("mv.portal.tmp");
        if (pageInfo == null || pageInfo.length() == 0) {
            pageInfo = this.portalService.getPortalCfg(pageId);
        }

        if (pageInfo == null) {
            return null;
        } else {
            JSONObject obj = (JSONObject)JSON.parse(pageInfo);
            MVContext mv = this.pageService.json2MV(obj, false, false);
            CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
            ser.setParams(this.pageService.getMvParams());
            ser.initPreview();
            String ret = ser.buildMV(mv, req.getServletContext());
            req.setAttribute("str", ret);
            return "portal/PortalIndex-print";
        }
    }

    @RequestMapping({"/getReportJson.action"})
    @ResponseBody
    public Object getReportJson(String reportId) {
        return this.portalService.getPortalCfg(reportId);
    }
}
