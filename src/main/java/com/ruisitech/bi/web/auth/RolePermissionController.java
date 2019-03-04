//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.auth;

import com.alibaba.fastjson.JSON;
import com.ruisitech.bi.service.auth.AuthRoleService;
import com.ruisitech.bi.util.BaseController;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/auth"})
public class RolePermissionController extends BaseController {
    @Autowired
    private AuthRoleService service;

    public RolePermissionController() {
    }

    @RequestMapping({"/roleMenu.action"})
    public String roleMenu(Integer roleId, ModelMap model) {
        Map<String, Object> menus = this.service.listRoleMenus(roleId);
        model.addAttribute("datas", JSON.toJSONString(menus));
        return "auth/role-menu";
    }

    @RequestMapping(
        value = {"/role/menuSave.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object roleMenuSave(String menuIds, Integer roleId) {
        this.service.roleMenu(menuIds, roleId);
        return super.buildSucces();
    }

    @RequestMapping({"/roleData.action"})
    public String roleData(Integer roleId, ModelMap model) {
        model.addAttribute("datas", JSON.toJSONString(this.service.roledata(roleId)));
        return "auth/role-data";
    }

    @RequestMapping(
        value = {"/role/dataSave.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object roleDataSave(Integer roleId, HttpServletRequest req) {
        this.service.roleDataSave(roleId, req);
        return super.buildSucces();
    }
}
