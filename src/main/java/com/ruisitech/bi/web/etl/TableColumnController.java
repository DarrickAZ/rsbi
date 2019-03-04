//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.service.etl.DynaColCheckService;
import com.ruisitech.bi.service.etl.EtlTableMetaColService;
import com.ruisitech.bi.util.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/etl"})
public class TableColumnController extends BaseController {
    private static Logger log = Logger.getLogger(TableColumnController.class);
    @Autowired
    private EtlTableMetaColService service;
    @Autowired
    private DynaColCheckService checkService;

    public TableColumnController() {
    }

    @RequestMapping({"/createTableDyna.action"})
    @ResponseBody
    public Object createTableDyna(EtlTableMetaCol col) {
        col.setColOrd(99);
        this.service.insertMetaTableCol(col, true);
        return super.buildSucces();
    }

    @RequestMapping({"/addTableColumn.action"})
    @ResponseBody
    public Object addTableColumn(EtlTableMetaCol col) {
        col.setColOrd(66);

        try {
            this.service.insertMetaTableCol(col, false);
            return super.buildSucces();
        } catch (Exception var3) {
            var3.printStackTrace();
            return super.buildError(var3.getMessage());
        }
    }

    @RequestMapping({"/updateTableDyna.action"})
    @ResponseBody
    public Object updateTableDyna(EtlTableMetaCol col) {
        this.service.updateTableCol(col, true);
        return super.buildSucces();
    }

    @RequestMapping({"/updateTableColumn.action"})
    @ResponseBody
    public Object updateTableCol(EtlTableMetaCol col) {
        try {
            this.service.updateTableCol(col, false);
            return super.buildSucces();
        } catch (Exception var3) {
            log.error("出错了。", var3);
            return super.buildError(var3.getMessage());
        }
    }

    @RequestMapping({"/getTableColumn.action"})
    @ResponseBody
    public Object getTableColumn(Integer tableId, Integer colId) {
        EtlTableMetaCol ret = this.service.getTableColumn(tableId, colId);
        return super.buildSucces(ret);
    }

    @RequestMapping({"/delTableColumn.action"})
    @ResponseBody
    public Object delTableColumn(Integer tableId, Integer colId) {
        try {
            this.service.delTableColumn(tableId, colId);
            return super.buildSucces();
        } catch (Exception var4) {
            log.error("出错了。", var4);
            return super.buildError(var4.getMessage());
        }
    }

    @RequestMapping({"/testDynaColumn.action"})
    @ResponseBody
    public Object testDynaColumn(String expression, Integer tid, Boolean mustAgg) {
        try {
            String msg = this.checkService.checkExpression(expression, tid, mustAgg);
            return msg == null ? super.buildSucces() : super.buildError(msg);
        } catch (Exception var5) {
            var5.printStackTrace();
            return super.buildError(var5.getMessage());
        }
    }
}
