//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruisitech.bi.entity.common.PageParam;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.service.etl.MetaService;
import com.ruisitech.bi.service.etl.TableRegService;
import com.ruisitech.bi.util.BaseController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/etl"})
public class TableRegController extends BaseController {
    @Autowired
    private EtlTableMetaService service;
    @Autowired
    private TableRegService regService;
    @Autowired
    private MetaService metaService;

    public TableRegController() {
    }

    @RequestMapping({"/TableReg.action"})
    public String index() {
        return "etl/TableReg";
    }

    @RequestMapping({"/loadRegTable.action"})
    @ResponseBody
    public Object loadRegTable(PageParam page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<EtlTableMeta> ls = this.service.selectTables("custom", page);
        PageInfo<EtlTableMeta> pageInfo = new PageInfo(ls);
        return super.buildSucces(pageInfo);
    }

    @RequestMapping({"/delRegTable.action"})
    @ResponseBody
    public Object deleteRegTable(Integer tableId) {
        this.service.deleteTable(tableId, false);
        return super.buildSucces();
    }

    @RequestMapping({"/flashRegTable.action"})
    @ResponseBody
    public Object flashRegTable(Integer tableId, String tableName) {
        try {
            this.regService.saveOrUpdateTable(tableName, tableId);
            return super.buildSucces();
        } catch (Exception var4) {
            return super.buildSucces(var4.getMessage());
        }
    }

    @RequestMapping({"/listSchema.action"})
    @ResponseBody
    public Object listSchema() {
        return this.metaService.queryDbSchemas();
    }

    @RequestMapping({"/currSchema.action"})
    @ResponseBody
    public Object currSchema() {
        String schema = this.metaService.getCurrentSchema();
        return super.buildSucces(schema);
    }

    @RequestMapping({"/regSchemaTable.action"})
    @ResponseBody
    public Object regSchemaTable(String tableName) {
        return this.regService.reg(tableName);
    }
}
