//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.auth;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruisitech.bi.entity.common.PageParam;
import com.ruisitech.bi.entity.frame.Role;
import com.ruisitech.bi.service.auth.AuthRoleService;
import com.ruisitech.bi.util.BaseController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/auth"})
public class RoleController extends BaseController {
    @Autowired
    private AuthRoleService service;

    public RoleController() {
    }

    @RequestMapping({"/role/list.action"})
    @ResponseBody
    public Object userList(@RequestBody PageParam page) {
        if (page != null && page.getPage() != null && page.getRows() != null) {
            PageHelper.startPage(page.getPage(), page.getRows());
        }

        List<Role> ls = this.service.list(page.getSearch());
        PageInfo<Role> pageInfo = new PageInfo(ls);
        return super.buildSucces(pageInfo);
    }

    @RequestMapping({"/role/userRolelist.action"})
    @ResponseBody
    public Object userRolelist(Integer userId) {
        return this.service.listUserRole(userId);
    }

    @RequestMapping({"/role/userRoleSave.action"})
    @ResponseBody
    public Object userRoleSave(String[] roleId, Integer userId) {
        this.service.addUserRole(roleId, userId);
        return super.buildSucces();
    }

    @RequestMapping({"/roleList.action"})
    public String index() {
        return "auth/Role-list";
    }

    @RequestMapping(
        value = {"/role/save.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object save(Role role) {
        this.service.saveRole(role);
        return super.buildSucces();
    }

    @RequestMapping(
        value = {"/role/update.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object update(Role role) {
        this.service.updateRole(role);
        return super.buildSucces();
    }

    @RequestMapping({"/role/delete.action"})
    @ResponseBody
    public Object delete(Integer roleId) {
        this.service.deleteRole(roleId);
        return super.buildSucces();
    }

    @RequestMapping({"/role/get.action"})
    @ResponseBody
    public Object getRole(Integer roleId) {
        return this.service.getRole(roleId);
    }
}
