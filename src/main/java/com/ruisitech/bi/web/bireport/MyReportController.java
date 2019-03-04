//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.bireport;

import com.ruisitech.bi.entity.bireport.OlapInfo;
import com.ruisitech.bi.service.bireport.OlapService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/bireport"})
public class MyReportController extends BaseController {
    @Autowired
    private OlapService service;

    public MyReportController() {
    }

    @RequestMapping({"/listReport.action"})
    @ResponseBody
    public Object list(String keyword, ModelMap model) {
        List<OlapInfo> ret = this.service.listreport(keyword);
        return ret;
    }

    @RequestMapping(
        value = {"/saveReport.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object save(OlapInfo info) {
        if (info.getPageId() == null) {
            if (this.service.olapExist(info.getPageName()) > 0) {
                return super.buildError("报表名存在重复。");
            }

            info.setPageId(this.service.maxOlapId());
            info.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
            JSONObject page = JSONObject.fromObject(info.getPageInfo());
            page.put("id", info.getPageId());
            info.setPageInfo(page.toString());
            this.service.insertOlap(info);
        } else {
            this.service.updateOlap(info);
        }

        return super.buildSucces(info.getPageId());
    }

    @RequestMapping({"/deleteReport.action"})
    @ResponseBody
    public Object deleteReport(Integer id) {
        this.service.deleteOlap(id);
        return this.buildSucces();
    }

    @RequestMapping({"/renameReport.action"})
    @ResponseBody
    public Object rename(OlapInfo info) {
        this.service.renameOlap(info);
        return this.buildSucces();
    }
}
