//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruisitech.bi.entity.common.PageParam;
import com.ruisitech.bi.entity.etl.DataSource;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.service.etl.DataSetService;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/etl"})
public class TableManagerController extends BaseController {
    private static Logger log = Logger.getLogger(TableManagerController.class);
    @Autowired
    private EtlTableMetaService service;
    @Autowired
    private DataSetService dsService;

    public TableManagerController() {
    }

    @RequestMapping({"/TableManager.action"})
    public String index() {
        return "etl/TableManager";
    }

    @RequestMapping({"/loadTable.action"})
    @ResponseBody
    public Object loadTable(String income, PageParam page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<EtlTableMeta> ls = this.service.selectTables(income, page);
        PageInfo<EtlTableMeta> pageInfo = new PageInfo(ls);
        return super.buildSucces(pageInfo);
    }

    @RequestMapping({"/loadByIncomes.action"})
    @ResponseBody
    public Object loadTableByIncomes(String income, PageParam page) {
        if (page != null && page.getPage() != null && page.getRows() != null) {
            PageHelper.startPage(page.getPage(), page.getRows());
        }

        String[] ims = income.split(",");
        List<String> incomes = Arrays.asList(ims);
        List<EtlTableMeta> ls = this.service.selectByIncomes(incomes, page);
        if (page != null && page.getPage() != null && page.getRows() != null) {
            PageInfo<EtlTableMeta> pageInfo = new PageInfo(ls);
            return super.buildSucces(pageInfo);
        } else {
            return ls;
        }
    }

    @RequestMapping({"/tableExist.action"})
    @ResponseBody
    public Object tableExist(String tableName) {
        int tableCnt = this.service.tableExist(tableName);
        return super.buildSucces(tableCnt);
    }

    @RequestMapping(
        value = {"/saveTableInfo.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object saveTableInfo(@RequestBody EtlTableMeta table) {
        table.setIncome("etl");
        table.setCrtUser(RSBIUtils.getLoginUserInfo().getUserId());

        try {
            EtlTableMeta ret = this.service.saveTableInfo(table);
            return super.buildSucces(ret);
        } catch (Exception var3) {
            log.error("创建表失败", var3);
            return super.buildError(var3.getMessage());
        }
    }

    @RequestMapping(
        value = {"/updateTableInfo.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object updateTableInfo(@RequestBody EtlTableMeta table) {
        this.service.updateTableInfo(table);
        return super.buildSucces();
    }

    @RequestMapping({"/delTable.action"})
    @ResponseBody
    public Object deleteTable(Integer tableId) {
        this.service.deleteTable(tableId, true);
        return super.buildSucces();
    }

    @RequestMapping({"/getTableInfo.action"})
    @ResponseBody
    public Object getTableInfo(Integer tableId) {
        EtlTableMeta table = this.service.getTableAll(tableId);
        return table;
    }

    @RequestMapping({"/queryTableData.action"})
    public Object queryTableData(Integer tableId, ModelMap model) {
        EtlTableMeta t = this.service.getTableOnly(tableId);
        String sql = "select * from ";
        if (t.getTableSql() != null && t.getTableSql().length() > 0) {
            sql = sql + "(" + t.getTableSql() + ") cc ";
        } else {
            sql = sql + t.getTableName();
        }

        try {
            List<Object> ls = this.dsService.queryTopN(sql, (DataSource)null, 20);
            model.put("ls", ls);
            return "etl/TableManager-queryData";
        } catch (Exception var6) {
            model.addAttribute("msg", var6.getMessage());
            var6.printStackTrace();
            return "control/SpringmvcError";
        }
    }
}
