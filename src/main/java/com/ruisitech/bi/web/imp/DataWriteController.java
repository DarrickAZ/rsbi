//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.imp;

import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.service.imp.DataWriteService;
import com.ruisitech.bi.util.BaseController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/imp"})
public class DataWriteController extends BaseController {
    @Autowired
    private EtlTableMetaService service;
    @Autowired
    private DataWriteService dwService;

    public DataWriteController() {
    }

    @RequestMapping({"/DataWrite.action"})
    public String index() {
        return "imp/DataWrite";
    }

    @RequestMapping({"/DataWriteList.action"})
    @ResponseBody
    public Object list() {
        List<EtlTableMeta> ls = this.service.selectTables("dw");
        return ls;
    }

    @RequestMapping({"/comboData.action"})
    @ResponseBody
    public Object comboData(Integer tableId, Integer colId) {
        return this.dwService.comboData(tableId, colId);
    }
}
