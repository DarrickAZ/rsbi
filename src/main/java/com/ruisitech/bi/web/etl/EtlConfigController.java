//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.ruisitech.bi.entity.etl.Config;
import com.ruisitech.bi.service.etl.EtlConfigService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/etl"})
public class EtlConfigController extends BaseController {
    @Autowired
    private EtlConfigService configService;

    public EtlConfigController() {
    }

    @RequestMapping({"/EtlConfig.action"})
    public String index() {
        return "etl/EtlConfig";
    }

    @RequestMapping({"/loadCfg.action"})
    @ResponseBody
    public Object loadCfg() {
        return this.configService.listCfg();
    }

    @RequestMapping(
        value = {"/saveCfg.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object saveCfg(@RequestBody Config config) {
        config.setCrtUser(RSBIUtils.getLoginUserInfo().getUserId());
        this.configService.insertCfg(config);
        return super.buildSucces(config.getCfgid());
    }

    @RequestMapping(
        value = {"/updateCfg.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object updateCfg(@RequestBody Config config) {
        this.configService.updateCfgInfo(config);
        return super.buildSucces();
    }

    @RequestMapping({"/delCfg.action"})
    @ResponseBody
    public Object delCfg(Integer cfgid) {
        Config c = new Config();
        c.setCfgid(cfgid);
        this.configService.delCfg(c);
        return super.buildSucces();
    }
}
