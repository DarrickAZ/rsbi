//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.auth;

import com.alibaba.fastjson.JSON;
import com.ruisitech.bi.service.auth.AuthUserService;
import com.ruisitech.bi.util.BaseController;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/auth"})
public class UserMenuController extends BaseController {
    @Autowired
    private AuthUserService service;

    public UserMenuController() {
    }

    @RequestMapping({"/userMenu.action"})
    public String index(Integer userId, ModelMap model) {
        Map<String, Object> dts = this.service.listUserMenus(userId);
        model.addAttribute("datas", JSON.toJSONString(dts));
        return "auth/User-menu";
    }

    @RequestMapping(
        value = {"/userMenu/save.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object saveUserMenu(Integer userId, String menuIds) {
        this.service.saveUserMenu(userId, menuIds);
        return super.buildSucces();
    }
}
