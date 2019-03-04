//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.model;

import com.ruisitech.bi.entity.model.TableMeta;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.service.model.SubjectManagerService;
import com.ruisitech.bi.service.model.SubjectTypeService;
import com.ruisitech.bi.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/model"})
public class NewCubeController extends BaseController {
    @Autowired
    private SubjectTypeService serivce;
    @Autowired
    private SubjectManagerService managerSerivce;
    @Autowired
    private EtlTableMetaService tableService;
    @Autowired
    private SubjectManagerService subjectService;

    public NewCubeController() {
    }

    @RequestMapping({"/newCubeStep1.action"})
    public String newCubeStep1(ModelMap model) {
        model.addAttribute("types", this.serivce.list());
        model.addAttribute("ls", this.tableService.selectTables((String)null));
        return "model/NewCube-step1";
    }

    @RequestMapping({"/newCubeStep2.action"})
    public String newCubeStep2(Integer tableId, ModelMap model) {
        model.addAttribute("ls", this.tableService.queryTableColumns(tableId, true));
        return "model/NewCube-step2";
    }

    @RequestMapping({"/tableExist.action"})
    @ResponseBody
    public Object tableExist(Integer tableId) {
        Integer ret = this.subjectService.tableExist(tableId);
        return super.buildSucces(ret);
    }

    @RequestMapping(
        value = {"/saveCube.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object saveCube(@RequestBody TableMeta table) {
        this.managerSerivce.saveCube(table);
        return super.buildSucces();
    }

    @RequestMapping(
        value = {"/updateCube.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object updateCube(@RequestBody TableMeta table) {
        this.managerSerivce.updateCube(table);
        return super.buildSucces();
    }
}
