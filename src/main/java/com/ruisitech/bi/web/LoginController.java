//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web;

import com.ruisitech.bi.service.frame.UserService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSAUtils;
import com.ruisitech.bi.util.RSBIUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/"})
public class LoginController extends BaseController {
    @Autowired
    private UserService userService;

    public LoginController() {
    }

    @RequestMapping({"/Login.action"})
    public String login(String backurl, ModelMap model) {
        if (backurl != null && backurl.length() > 0) {
            backurl = RSBIUtils.escape(backurl);
        }

        model.addAttribute("backurl", backurl);
        Subject us = SecurityUtils.getSubject();
        return !us.isAuthenticated() && !us.isRemembered() ? "Login" : "redirect:frame/Frame.action";
    }

    @RequestMapping(
        value = {"/dologin.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object dologin(String userName, String password, String backurl, ModelMap model) {
        userName = RSAUtils.decryptBase64(userName);
        password = RSAUtils.decryptBase64(password);
        model.addAttribute("backurl", backurl);
        String msg = this.userService.shiroLogin(userName, password);
        if ("SUC".equals(msg)) {
            Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
            this.userService.updateLogDateAndCnt(userId);
            if (backurl != null && backurl.length() > 0) {
                backurl = RSBIUtils.unescape(backurl);
            }

            return super.buildSucces(backurl);
        } else {
            return super.buildError(msg);
        }
    }

    @RequestMapping(
        value = {"/getKey.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object getKey() {
        String publicKey = RSAUtils.generateBase64PublicKey();
        return publicKey;
    }
}
