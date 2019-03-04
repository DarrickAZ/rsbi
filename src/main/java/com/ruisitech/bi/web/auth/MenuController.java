//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.auth;

import com.ruisitech.bi.entity.frame.Menu;
import com.ruisitech.bi.service.frame.MenuService;
import com.ruisitech.bi.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/auth"})
public class MenuController extends BaseController {
    @Autowired
    private MenuService service;

    public MenuController() {
    }

    @RequestMapping({"/menu.action"})
    public String index() {
        return "auth/Menu";
    }

    @RequestMapping({"/menu/loadData.action"})
    @ResponseBody
    public Object loadData(Integer id) {
        return this.service.listMenuByPid(id);
    }

    @RequestMapping(
        value = {"/menu/save.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object save(Menu menu) {
        this.service.saveMenu(menu);
        return super.buildSucces(menu.getMenuId());
    }

    @RequestMapping(
        value = {"/menu/update.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object update(Menu menu) {
        this.service.updateMenu(menu);
        return super.buildSucces();
    }

    @RequestMapping({"/menu/get.action"})
    @ResponseBody
    public Object getMenu(Integer menuId) {
        return this.service.getById(menuId);
    }

    @RequestMapping({"/menu/delete.action"})
    @ResponseBody
    public Object delete(Integer menuId) {
        return this.service.deleteMenu(menuId);
    }
}
