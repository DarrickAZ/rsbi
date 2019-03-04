//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.report;

import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.portal.Portal;
import com.ruisitech.bi.service.portal.PortalService;
import com.ruisitech.bi.service.report.MbReportTypeService;
import com.ruisitech.bi.util.BaseController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/report"})
public class PushManagerController extends BaseController {
    @Autowired
    private MbReportTypeService service;
    @Autowired
    private PortalService portalService;

    public PushManagerController() {
    }

    @RequestMapping({"/PushManager.action"})
    public String index(ModelMap model) {
        model.addAttribute("str", JSONObject.toJSONString(this.service.listcataTree("mobile")));
        return "report/PushManager";
    }

    @RequestMapping({"/pushList.action"})
    @ResponseBody
    public Object list(Integer cataId) {
        List<Portal> ls = this.portalService.list3g(cataId);
        return ls;
    }

    @RequestMapping({"/pushDel.action"})
    @ResponseBody
    public Object delete(String id) {
        this.portalService.deletePortal(id);
        return super.buildSucces();
    }
}
