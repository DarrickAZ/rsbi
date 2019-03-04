//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.bireport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.emitter.ContextEmitter;
import com.ruisi.ext.engine.view.emitter.excel.ExcelEmitter;
import com.ruisi.ext.engine.view.emitter.pdf.PdfEmitter;
import com.ruisi.ext.engine.view.emitter.text.TextEmitter;
import com.ruisi.ext.engine.view.emitter.word.WordEmitter;
import com.ruisitech.bi.entity.bireport.OlapInfo;
import com.ruisitech.bi.service.bireport.OlapService;
import com.ruisitech.bi.service.bireport.ReportService;
import com.ruisitech.bi.service.model.TableMetaServcice;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.CompPreviewService;
import com.ruisitech.bi.util.RSBIUtils;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/bireport"})
public class ReportDesignController extends BaseController {
    @Autowired
    private OlapService service;
    @Autowired
    private TableMetaServcice metaService;
    @Autowired
    private ReportService rservice;

    public ReportDesignController() {
    }

    @RequestMapping({"/ReportDesign.action"})
    public String index(Integer pageId, Integer selectDs, ModelMap model) {
        if (pageId != null) {
            OlapInfo olap = this.service.getOlap(pageId);
            if (olap != null) {
                model.addAttribute("pageInfo", olap.getPageInfo());
                model.addAttribute("pageName", olap.getPageName());
            }
        } else if (selectDs == null) {
            model.addAttribute("selectDs", this.metaService.getDefTid());
        } else if (selectDs != null) {
            model.addAttribute("selectDs", selectDs);
        }

        return "bireport/ReportDesign";
    }

    @RequestMapping(
        value = {"/ReportExport.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object export(String type, String json, String picinfo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ExtContext.getInstance().removeMV("mv.export.tmp");
        JSONObject obj = (JSONObject)JSON.parse(json);
        MVContext mv = this.rservice.json2MV(obj, 0);
        req.setAttribute("picinfo", picinfo);
        CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
        ser.setParams((Map)null);
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
            ret = RSBIUtils.htmlPage(ret, RSBIUtils.getConstant("resPath"), "olap");
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

        return null;
    }

    @RequestMapping({"/kpidesc.action"})
    public String kpidesc(String selectDsIds, ModelMap model) {
        List<Map<String, Object>> ls = this.metaService.listKpiDesc(selectDsIds);
        model.addAttribute("ls", ls);
        return "bireport/DataSet-kpidesc";
    }

    @RequestMapping(
        value = {"/print.action"},
        method = {RequestMethod.POST}
    )
    public Object print(String pageInfo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ExtContext.getInstance().removeMV("mv.export.tmp");
        JSONObject obj = (JSONObject)JSON.parse(pageInfo);
        MVContext mv = this.rservice.json2MV(obj, 0);
        CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
        ser.setParams((Map)null);
        ser.initPreview();
        String ret = ser.buildMV(mv, req.getServletContext());
        req.setAttribute("data", ret);
        return "bireport/ReportDesign-print";
    }
}
