//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import com.ruisitech.bi.entity.app.OlapSave;
import com.ruisitech.bi.service.app.OlapSaveService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/app"})
public class UsaveController extends BaseController {
    @Autowired
    private OlapSaveService service;

    public UsaveController() {
    }

    @RequestMapping({"/Usave!save.action"})
    @ResponseBody
    public Object save(OlapSave save) {
        save.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        this.service.save(save);
        return save.getId();
    }

    @RequestMapping({"/Usave!delete.action"})
    @ResponseBody
    public Object delete(Integer id) {
        this.service.delete(id);
        return super.buildSucces();
    }

    @RequestMapping({"/Usave!get.action"})
    @ResponseBody
    public Object get(Integer id) {
        String json = this.service.getById(id);
        return json;
    }

    @RequestMapping({"/Usave!list.action"})
    @ResponseBody
    public Object list() {
        return this.service.list();
    }

    @RequestMapping({"/Usave!update.action"})
    @ResponseBody
    public Object update(OlapSave save) {
        this.service.update(save);
        return super.buildSucces();
    }
}
