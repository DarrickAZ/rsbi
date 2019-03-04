//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.frame;

import com.ruisitech.bi.entity.frame.User;
import com.ruisitech.bi.service.frame.UserService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/frame"})
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    public UserController() {
    }

    @RequestMapping({"/User.action"})
    @ResponseBody
    public Object getUserInfo() {
        String staffId = RSBIUtils.getLoginUserInfo().getStaffId();
        User u = this.userService.getUserByUserId(staffId);
        return u;
    }

    @RequestMapping(
        value = {"/chgPsd.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object chgPsd(String password1, String password2, String password3) {
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        String userPassword = this.userService.checkPsd(userId);
        if (!userPassword.equals(RSBIUtils.getEncodedStr(password1))) {
            return this.buildError("原始密码错误");
        } else {
            User u = new User();
            u.setUserId(userId);
            u.setPassword(RSBIUtils.getEncodedStr(password2));
            this.userService.modPsd(u);
            return this.buildSucces();
        }
    }
}
