//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.dashboard;

import com.ruisitech.bi.entity.dashboard.CompShare;
import com.ruisitech.bi.service.dashboard.CompShareService;
import com.ruisitech.bi.util.BaseController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/dashboard/share"})
public class CompShareController extends BaseController {
    @Autowired
    private CompShareService service;

    public CompShareController() {
    }

    @RequestMapping(
        value = {"/save.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object save(CompShare record) {
        if (this.service.selectByPrimaryKey(record.getId()) != null) {
            return super.buildError("此分析图已经保存。");
        } else {
            this.service.insert(record);
            return super.buildSucces();
        }
    }

    @RequestMapping({"/delete.action"})
    @ResponseBody
    public Object delete(String id) {
        this.service.deleteByPrimaryKey(id);
        return super.buildSucces();
    }

    @RequestMapping({"/list.action"})
    @ResponseBody
    public Object list(String keyword) {
        List<CompShare> ls = this.service.list(keyword);
        return super.buildSucces(ls);
    }

    @RequestMapping({"/get.action"})
    @ResponseBody
    public Object get(String id) {
        CompShare vo = this.service.selectByPrimaryKey(id);
        return vo.getCfg();
    }

    @RequestMapping(
        value = {"/update.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object update(CompShare record) {
        this.service.updateByPrimaryKeySelective(record);
        return super.buildSucces();
    }
}
