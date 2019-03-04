//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.report;

import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.report.MbReportType;
import com.ruisitech.bi.service.report.MbReportTypeService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/report"})
public class MbReportTypeController extends BaseController {
    @Autowired
    private MbReportTypeService service;

    public MbReportTypeController() {
    }

    @RequestMapping({"/MobReportType.action"})
    public String index(ModelMap model) {
        model.addAttribute("str", JSONObject.toJSONString(this.service.listcataTree("mobile")));
        return "report/MobReportType";
    }

    @RequestMapping({"/typeTree.action"})
    @ResponseBody
    public Object tree() {
        return this.service.listcataTree("mobile");
    }

    @RequestMapping(
        value = {"/addType.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object addType(MbReportType type) {
        type.setCrtUser(RSBIUtils.getLoginUserInfo().getUserId());
        Integer maxId;
        if (type.getIdType() == 2) {
            maxId = this.service.maxTypeId();
            type.setId(maxId == null ? 1 : maxId + 1);
        }

        this.service.insertType(type);
        maxId = this.service.maxTypeId();
        return super.buildSucces(maxId);
    }

    @RequestMapping(
        value = {"/updateType.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object updateType(MbReportType type) {
        this.service.updateType(type);
        return super.buildSucces();
    }

    @RequestMapping({"/deleteType.action"})
    @ResponseBody
    public Object deleteType(Integer id) {
        if (this.service.cntMobileReport(id) > 0) {
            return super.buildError("分类下存在报表,不能删除。");
        } else {
            this.service.deleleType(id);
            return super.buildSucces();
        }
    }

    @RequestMapping({"/getType.action"})
    @ResponseBody
    public Object getType(Integer id) {
        return this.service.getType(id);
    }
}
