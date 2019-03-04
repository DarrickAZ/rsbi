//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import com.ruisitech.bi.entity.frame.User;
import com.ruisitech.bi.service.frame.UserService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/app"})
public class AppLoginController extends BaseController {
    @Autowired
    private UserService userService;

    public AppLoginController() {
    }

    @RequestMapping({"/Login!login.action"})
    @ResponseBody
    public Object login(String userName, String password, String channel_id) {
        String ret = this.userService.shiroLogin(userName, password);
        Map<String, Object> obj = new HashMap();
        if ("SUC".equals(ret)) {
            obj.put("result", true);
            User user = RSBIUtils.getLoginUserInfo();
            String token = RSBIUtils.getMD5((user.getStaffId() + (new Date()).getTime()).getBytes());
            obj.put("token", token);
            Map<String, Object> u = new HashMap();
            u.put("userId", "1");
            obj.put("user", u);
            this.userService.updateLogDateAndCnt(user.getUserId());
        } else {
            obj.put("result", false);
            obj.put("msg", ret);
        }

        return obj;
    }

    @RequestMapping({"/Login!logout.action"})
    @ResponseBody
    public Object logout(Integer userId) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            try {
                subject.logout();
            } catch (Exception var4) {
            }
        }

        return super.buildSucces();
    }
}
