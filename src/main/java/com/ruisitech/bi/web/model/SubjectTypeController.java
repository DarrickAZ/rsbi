//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.model;

import com.ruisitech.bi.entity.model.SubjectType;
import com.ruisitech.bi.service.model.SubjectTypeService;
import com.ruisitech.bi.util.BaseController;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/model"})
public class SubjectTypeController extends BaseController {
    @Autowired
    private SubjectTypeService serivce;

    public SubjectTypeController() {
    }

    @RequestMapping({"/SubjectType.action"})
    public String index(ModelMap model) {
        List<Map<String, Object>> datas = this.serivce.selectByTree();
        model.addAttribute("str", JSONArray.fromObject(datas));
        return "model/SubjectType";
    }

    @RequestMapping({"/listSubjectType.action"})
    @ResponseBody
    public Object listSubjectType() {
        return this.serivce.selectByTree();
    }

    @RequestMapping({"/saveSubjectType.action"})
    @ResponseBody
    public Object saveSubjectType(SubjectType type) {
        type.setPid(0);
        type.setTp("type");
        this.serivce.insert(type);
        return super.buildSucces();
    }

    @RequestMapping({"/updateSubjectType.action"})
    @ResponseBody
    public Object upateSubjectType(SubjectType type) {
        this.serivce.updateByPrimaryKey(type);
        return super.buildSucces();
    }

    @RequestMapping({"/delSubjectType.action"})
    @ResponseBody
    public Object delSubjectType(Integer id) {
        int cnt = this.serivce.cntTables(id);
        if (cnt > 0) {
            return super.buildError("分类下含有报表，不能删除。");
        } else {
            this.serivce.deleteByPrimaryKey(id);
            return super.buildSucces();
        }
    }

    @RequestMapping({"/getSubjectType.action"})
    @ResponseBody
    public Object getSubjectType(Integer id) {
        return this.serivce.selectByPrimaryKey(id);
    }
}
