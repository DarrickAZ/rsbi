//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import com.ruisitech.bi.entity.frame.User;
import com.ruisitech.bi.service.frame.UserService;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/app"})
public class AppUserController {
    @Autowired
    private UserService service;

    public AppUserController() {
    }

    @RequestMapping({"/UInfo.action"})
    @ResponseBody
    public Object userInfo() {
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        Map<String, Object> ret = this.service.appUserinfo(userId);
        return JSONObject.fromObject(ret).toString();
    }

    @RequestMapping({"/UserReg.action"})
    @ResponseBody
    public Object userReg(String userName, String password, String yzm, String guid, HttpServletRequest req) {
        User u = new User();
        u.setStaffId(userName);
        u.setLoginName(userName);
        u.setPassword(password);
        return this.service.reg(u, yzm, guid, req);
    }
}
