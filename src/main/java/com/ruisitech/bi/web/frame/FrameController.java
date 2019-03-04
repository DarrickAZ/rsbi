//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.frame;

import com.ruisi.ext.engine.init.XmlParser;
import com.ruisitech.bi.entity.frame.User;
import com.ruisitech.bi.service.frame.MenuService;
import com.ruisitech.bi.service.frame.SystemService;
import com.ruisitech.bi.util.IsModel;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/frame"})
public class FrameController {
    @Autowired
    private MenuService service;
    @Autowired
    private SystemService sysService;

    public FrameController() {
    }

    @RequestMapping({"/Frame.action"})
    public String execute(ModelMap model, HttpServletRequest req) {
        User user = RSBIUtils.getLoginUserInfo();
        model.addAttribute("menus", this.service.listUserMenus(user.getUserId()));
        model.addAttribute("uinfo", user);
        return IsModel.check(req) ? "/frame/Frame-mobile" : "/frame/Frame";
    }

    @RequestMapping({"/Welcome.action"})
    public String welcome(ModelMap model, HttpServletRequest req) {
        Map<String, Object> sinfo = this.sysService.getSystemInfo();
        model.put("sinfo", sinfo);

        try {
            int syts = XmlParser.getEndDate(req.getServletContext());
            model.put("syts", syts);
        } catch (Exception var5) {
            model.put("syts", -10);
        }

        return "/frame/Frame-welcome";
    }
}
