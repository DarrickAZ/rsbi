//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.ruisitech.bi.entity.etl.EtlTransform;
import com.ruisitech.bi.service.etl.SqlService;
import com.ruisitech.bi.service.etl.TransformService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/etl"})
public class SqlController extends BaseController {
    private static Logger log = Logger.getLogger(SqlController.class);
    @Autowired
    private TransformService tfService;
    @Autowired
    private SqlService sqlService;

    public SqlController() {
    }

    @RequestMapping({"/loadSqlTable.action"})
    @ResponseBody
    public Object list() {
        return this.tfService.listTf("sql");
    }

    @RequestMapping({"/delSql.action"})
    @ResponseBody
    public Object delSql(Integer id) {
        this.tfService.deleteByPrimaryKey(id);
        return super.buildSucces();
    }

    @RequestMapping({"/testSql.action"})
    @ResponseBody
    public Object testSql(String sql) {
        try {
            sql = RSBIUtils.unescape(sql);
            this.sqlService.testsql(sql);
        } catch (Exception var3) {
            return super.buildError(var3.getMessage());
        }

        return super.buildSucces();
    }

    @RequestMapping(
        value = {"/saveSql.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object saveSql(EtlTransform tf) {
        try {
            this.sqlService.save(tf);
        } catch (Exception var3) {
            log.error("保存出错。", var3);
            return super.buildError(var3.getMessage());
        }

        return super.buildSucces();
    }

    @RequestMapping(
        value = {"/updateSql.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object updateSql(EtlTransform tf) {
        try {
            this.sqlService.update(tf);
        } catch (Exception var3) {
            log.error("修改出错。", var3);
            return super.buildError(var3.getMessage());
        }

        return super.buildSucces();
    }

    @RequestMapping({"/runSql.action"})
    @ResponseBody
    public Object runSql(Integer id) {
        EtlTransform tf = this.tfService.selectByPrimaryKey(id);
        return this.sqlService.runSql(tf);
    }
}
