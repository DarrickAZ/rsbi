//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import com.ruisitech.bi.service.portal.PortalService;
import com.ruisitech.bi.service.report.MbReportTypeService;
import com.ruisitech.bi.util.RSBIUtils;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/app"})
public class AppReportController {
    @Autowired
    private MbReportTypeService service;
    @Autowired
    private PortalService portalService;

    public AppReportController() {
    }

    @RequestMapping({"/Report!listCata.action"})
    @ResponseBody
    public Object listCata() {
        return this.service.listcataTree("mobile");
    }

    @RequestMapping({"/Report!listReport.action"})
    @ResponseBody
    public Object listReport(Integer cataId, HttpServletRequest request) {
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        List<Map<String, Object>> ls = this.portalService.listAppReport(userId, cataId);

        for(int i = 0; i < ls.size(); ++i) {
            Map<String, Object> m = (Map)ls.get(i);
            Object o = m.get("dt");
            m.put("dt", sdf.format(o));
            String url = basePath + "app/Report!view.action?rid=" + m.get("rid");
            m.put("url", url);
        }

        return ls;
    }
}
