//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.ruisitech.bi.entity.etl.UpLoadFile;
import com.ruisitech.bi.service.etl.EtlConfigService;
import com.ruisitech.bi.service.etl.UpLoadFileService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/etl"})
public class ImportController extends BaseController {
    @Autowired
    private UpLoadFileService service;
    @Autowired
    private EtlConfigService configService;

    public ImportController() {
    }

    @RequestMapping({"/ImportCsv.action"})
    public String importCsv() {
        return "etl/Import-csv";
    }

    @RequestMapping({"/ImportXls.action"})
    public String importXls() {
        return "etl/Import-xls";
    }

    @RequestMapping({"/ImportDb.action"})
    public String importDb(String method, Integer cfgid, String dsource, ModelMap model) {
        model.addAttribute("dsource", dsource);
        return "etl/Import-db";
    }

    @RequestMapping({"/ImportCsv2.action"})
    public String importCsv2(Integer cfgid, String method, ModelMap model) {
        UpLoadFile file;
        if ("resume".equals(method)) {
            file = new UpLoadFile();
            file.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
            file.setFileType("csv");
            file.setCfgid(cfgid);
            model.addAttribute("file", this.service.curUpload(file));
            String ctx = this.configService.getCfg(cfgid);
            model.addAttribute("ctx", ctx);
        } else {
            file = new UpLoadFile();
            file.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
            file.setFileType("csv");
            file = this.service.queryupload(file);
            model.addAttribute("file", file);
        }

        return "etl/Import-csv2";
    }

    @RequestMapping({"/ImportXls2.action"})
    public String importXls2(Integer cfgid, String method, ModelMap model) {
        UpLoadFile file;
        if ("resume".equals(method)) {
            file = new UpLoadFile();
            file.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
            file.setFileType("xls");
            file.setCfgid(cfgid);
            model.addAttribute("file", this.service.curUpload(file));
            String ctx = this.configService.getCfg(cfgid);
            model.addAttribute("ctx", ctx);
        } else {
            file = new UpLoadFile();
            file.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
            file.setFileType("xls");
            file = this.service.queryupload(file);
            model.addAttribute("file", file);
        }

        return "etl/Import-xls2";
    }

    @RequestMapping({"/ImportDb2.action"})
    public String importDb2(Integer cfgid, String method, ModelMap model) {
        if ("resume".equals(method)) {
            String ctx = this.configService.getCfg(cfgid);
            model.addAttribute("ctx", ctx);
        }

        return "etl/Import-db2";
    }
}
