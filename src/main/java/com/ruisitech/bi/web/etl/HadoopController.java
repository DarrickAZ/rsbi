//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.ruisitech.bi.service.etl.EtlConfigService;
import com.ruisitech.bi.service.etl.HadoopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/etl"})
public class HadoopController {
    @Autowired
    private HadoopService service;
    @Autowired
    private EtlConfigService configService;

    public HadoopController() {
    }

    @RequestMapping({"/Hadoop.action"})
    public String index(String hdfsAddress, ModelMap model) {
        model.addAttribute("hdfsAddress", hdfsAddress);
        return "etl/Hadoop";
    }

    @RequestMapping({"/testHdfs.action"})
    @ResponseBody
    public Object testHdfs(String hdfsAddress) {
        return this.service.testHdfs(hdfsAddress);
    }

    @RequestMapping({"/HadoopImport.action"})
    public String hadoopImport(String hdfsAddress, Integer cfgid, String method, ModelMap model) {
        if ("resume".equals(method)) {
            String ctx = this.configService.getCfg(cfgid);
            model.addAttribute("ctx", ctx);
        }

        return "etl/Hadoop-import";
    }
}
