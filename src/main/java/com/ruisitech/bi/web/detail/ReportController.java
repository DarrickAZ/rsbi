//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.detail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.emitter.ContextEmitter;
import com.ruisi.ext.engine.view.emitter.excel.ExcelEmitter;
import com.ruisi.ext.engine.view.emitter.pdf.PdfEmitter;
import com.ruisi.ext.engine.view.emitter.text.TextEmitter;
import com.ruisi.ext.engine.view.emitter.word.WordEmitter;
import com.ruisitech.bi.entity.detail.OlapDetail;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.service.detail.OlapDetailService;
import com.ruisitech.bi.service.detail.PageService;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.CompPreviewService;
import com.ruisitech.bi.util.RSBIUtils;
import java.io.InputStream;
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
@RequestMapping({"/detail"})
public class ReportController extends BaseController {
    @Autowired
    private EtlTableMetaService service;
    @Autowired
    private PageService pageService;
    @Autowired
    private OlapDetailService detailService;

    public ReportController() {
    }

    @RequestMapping({"/Report.action"})
    public String index(Integer pageId, Integer tableId, ModelMap model) {
        if (pageId != null) {
            OlapDetail detail = this.detailService.selectByPrimaryKey(pageId);
            model.addAttribute("pageInfo", detail.getPageinfo());
        } else if (tableId != null) {
            EtlTableMeta table = this.service.getTableOnly(tableId);
            String pageInfo = this.pageService.crtTableQuery(table);
            model.addAttribute("pageInfo", pageInfo);
        }

        return "detail/Report";
    }

    @RequestMapping({"/listReports.action"})
    @ResponseBody
    public Object listReports(String keyword) {
        return this.detailService.listOlapDetail(keyword);
    }

    @RequestMapping({"/saveReport.action"})
    @ResponseBody
    public Object saveReport(OlapDetail detail) {
        this.detailService.saveOrUpdate(detail);
        return super.buildSucces(detail.getPageId());
    }

    @RequestMapping({"/delReport.action"})
    @ResponseBody
    public Object delReport(Integer pageId) {
        this.detailService.deleteByPrimaryKey(pageId);
        return super.buildSucces();
    }

    @RequestMapping(
        value = {"/ReportExport.action"},
        method = {RequestMethod.POST}
    )
    public String export(String type, String json, String picinfo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        ExtContext.getInstance().removeMV("detail.report.tmp");
        req.setAttribute("picinfo", picinfo);
        JSONObject page = (JSONObject)JSON.parse(json);
        MVContext mv = this.pageService.json2MV(page);
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

        return null;
    }
}
