//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.auth;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruisitech.bi.entity.common.PageParam;
import com.ruisitech.bi.entity.frame.User;
import com.ruisitech.bi.service.auth.AuthUserService;
import com.ruisitech.bi.service.frame.UserService;
import com.ruisitech.bi.util.BaseController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("authUser")
@RequestMapping({"/auth"})
public class UserController extends BaseController {
    @Autowired
    private AuthUserService service;
    @Autowired
    private UserService userService;

    public UserController() {
    }

    @RequestMapping({"/userList.action"})
    public String userIndex(ModelMap model) {
        if (this.userService.isSSOUserList()) {
            model.addAttribute("sso", "y");
        }

        return "auth/User-list";
    }

    @RequestMapping(
        value = {"/userList/list.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object userList(@RequestBody PageParam page) {
        List ls;
        PageInfo pageInfo;
        if (this.userService.isSSOUserList()) {
            ls = this.userService.listSSOUsers(page, page.getSearch());
            pageInfo = new PageInfo(ls);
            return super.buildSucces(pageInfo);
        } else {
            if (page != null && page.getPage() != null && page.getRows() != null) {
                PageHelper.startPage(page.getPage(), page.getRows());
            }

            ls = this.service.listUsers(page.getSearch());
            pageInfo = new PageInfo(ls);
            return super.buildSucces(pageInfo);
        }
    }

    @RequestMapping(
        value = {"/userList/delete.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object userDelete(Integer userId) {
        this.service.deleteUser(userId);
        return super.buildSucces();
    }

    @RequestMapping(
        value = {"/userList/save.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object userSave(User u) {
        String msg = this.service.saveUser(u);
        return msg == null ? super.buildSucces() : super.buildError(msg);
    }

    @RequestMapping(
        value = {"/userList/update.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object userUpdate(User u) {
        this.service.updateUser(u);
        return super.buildSucces();
    }

    @RequestMapping({"/userList/get.action"})
    @ResponseBody
    public Object getUser(Integer userId) {
        User u = this.service.getUserById(userId);
        return super.buildSucces(u);
    }
}
