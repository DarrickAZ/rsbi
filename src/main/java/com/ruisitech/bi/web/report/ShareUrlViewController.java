//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisitech.bi.entity.report.ShareUrl;
import com.ruisitech.bi.service.portal.PortalPageService;
import com.ruisitech.bi.service.portal.PortalService;
import com.ruisitech.bi.util.CompPreviewService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
@RequestMapping({"/report"})
public class ShareUrlViewController {
    @Autowired
    private PortalService portalService;
    @Autowired
    private PortalPageService pageService;

    public ShareUrlViewController() {
    }

    @RequestMapping({"/shareView.action"})
    public String view(HttpServletRequest req, HttpServletResponse res, ModelMap model) throws Exception {
        ShareUrl surl = (ShareUrl)req.getAttribute("surl");
        String cfg = this.portalService.getPortalCfg(surl.getReportId());
        if (cfg == null) {
            model.addAttribute("ext.mv.err", "找不到报表文件。");
            return "control/Error";
        } else {
            ExtContext.getInstance().removeMV("mv.portal.tmp");
            JSONObject json = (JSONObject)JSON.parse(cfg);
            MVContext mv = this.pageService.json2MV(json, false, false);
            CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
            ser.setParams(this.pageService.getMvParams());
            ser.initPreview();
            String ret = ser.buildMV(mv, req.getServletContext());
            req.setAttribute("str", ret);
            return "app/Report-view";
        }
    }
}
