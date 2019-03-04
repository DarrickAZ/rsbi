//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.frame;

import com.ruisitech.bi.entity.frame.Department;
import com.ruisitech.bi.service.frame.DepartmentService;
import com.ruisitech.bi.util.BaseController;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/frame"})
public class DepartmentController extends BaseController {
    @Autowired
    private DepartmentService serivce;

    public DepartmentController() {
    }

    @RequestMapping({"/Department.action"})
    public String index(ModelMap model, HttpServletRequest req) {
        return "frame/Department";
    }

    @RequestMapping({"/loadDepartment.action"})
    @ResponseBody
    public Object loadDepartment(Integer id) {
        return this.serivce.tree(id);
    }

    @RequestMapping({"/loadAllDepartment.action"})
    @ResponseBody
    public Object loadAllDepartment() {
        return this.serivce.list();
    }

    @RequestMapping({"/delDepartment.action"})
    @ResponseBody
    public Object delDepartment(Integer id) {
        this.serivce.deleteByPrimaryKey(id);
        return super.buildSucces();
    }

    @RequestMapping(
        value = {"/saveDepartment.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object saveDepartment(Department dept) {
        this.serivce.insertSelective(dept);
        return super.buildSucces(dept.getId());
    }

    @RequestMapping(
        value = {"/updateDepartment.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object updateDepartment(Department dept) {
        this.serivce.updateByPrimaryKeySelective(dept);
        return super.buildSucces();
    }

    @RequestMapping({"/getDepartment.action"})
    @ResponseBody
    public Object getDepartment(Integer id) {
        return this.serivce.selectByPrimaryKey(id);
    }
}
