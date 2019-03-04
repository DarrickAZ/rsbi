//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.model;

import com.ruisitech.bi.service.model.SubjectManagerService;
import com.ruisitech.bi.service.model.TableMetaServcice;
import com.ruisitech.bi.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/model"})
public class TableMetaController extends BaseController {
    @Autowired
    private TableMetaServcice service;
    @Autowired
    private SubjectManagerService managerSerivce;

    public TableMetaController() {
    }

    @RequestMapping({"/cubeTree.action"})
    @ResponseBody
    public Object cubeTree(String selectDsIds) {
        return this.service.getCubeTree(selectDsIds);
    }

    @RequestMapping({"/cubeInfo.action"})
    @ResponseBody
    public Object cubeInfo(Integer tableId) {
        return this.managerSerivce.getCube(tableId);
    }
}
