//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.imp;

import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.service.imp.TableService;
import com.ruisitech.bi.util.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/imp"})
public class TableController extends BaseController {
    private static Logger log = Logger.getLogger(TableController.class);
    @Autowired
    private TableService service;
    @Autowired
    private EtlTableMetaService tableMetaService;

    public TableController() {
    }

    @RequestMapping({"/TableCrt.action"})
    public String index() {
        return "imp/Table-crt";
    }

    @RequestMapping(
        value = {"/saveTable.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object saveTable(@RequestBody EtlTableMeta table) {
        try {
            this.service.saveTable(table);
            return super.buildSucces();
        } catch (Exception var3) {
            log.error("保存出错。", var3);
            return super.buildError(var3.getMessage());
        }
    }

    @RequestMapping({"/listTableCols.action"})
    @ResponseBody
    public Object listTableCols(Integer tableId) {
        return this.tableMetaService.queryTableColumns(tableId, false);
    }

    @RequestMapping({"/delTable.action"})
    @ResponseBody
    public Object delTable(Integer tableId) {
        try {
            this.tableMetaService.deleteTable(tableId, true);
            return super.buildSucces();
        } catch (Exception var3) {
            log.error("删除出错。", var3);
            return super.buildError(var3.getMessage());
        }
    }

    @RequestMapping({"/updateTable.action"})
    @ResponseBody
    public Object updateTable(EtlTableMeta table) {
        this.tableMetaService.updateTableInfo(table);
        return super.buildSucces();
    }
}
