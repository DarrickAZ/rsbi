//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.imp;

import com.ruisitech.bi.entity.common.PageParam;
import com.ruisitech.bi.entity.common.RequestStatus;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.entity.imp.DataWriteDto;
import com.ruisitech.bi.service.etl.EtlTableMetaColService;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.service.imp.DataWriteService;
import com.ruisitech.bi.util.BaseController;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/imp"})
public class SubmitController extends BaseController {
    @Autowired
    private EtlTableMetaService service;
    @Autowired
    private EtlTableMetaColService colService;
    @Autowired
    private DataWriteService dwService;
    private static Logger log = Logger.getLogger(SubmitController.class);

    public SubmitController() {
    }

    @RequestMapping({"/Submit.action"})
    public String index(ModelMap model) {
        List<EtlTableMeta> ls = this.service.selectTables("dw");
        model.addAttribute("tabs", ls);
        return "imp/Submit";
    }

    @RequestMapping({"/write.action"})
    public String write(Integer tableId, ModelMap model) {
        EtlTableMeta meta = this.service.getTable(tableId);
        List<EtlTableMetaCol> cols = this.colService.queryTableColumnsNotExpress(tableId);
        meta.setMetaCols(cols);
        model.addAttribute("table", meta);
        model.addAttribute("cols", meta.getMetaCols());
        return "imp/Submit-write";
    }

    @RequestMapping({"/listSubmit.action"})
    public String listSubmit(String income, Integer tableId, ModelMap model) {
        EtlTableMeta meta = this.service.getTable(tableId);
        model.addAttribute("table", meta);
        model.addAttribute("cols", this.colService.queryTableColumnsNotExpress(tableId));
        model.addAttribute("income", income);
        return "imp/Submit-list";
    }

    @RequestMapping({"/loaddata.action"})
    @ResponseBody
    public Object loadData(EtlTableMeta table, PageParam pageParam) {
        try {
            List<Map<String, Object>> ls = this.dwService.loadData(table, pageParam);
            return new Result(RequestStatus.SUCCESS.getStatus(), "操作成功", ls, (long)pageParam.getTotal());
        } catch (Exception var4) {
            var4.printStackTrace();
            return super.buildError("出错啦。" + var4.getMessage());
        }
    }

    @RequestMapping(
        value = {"/saveSubmit.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object save(@RequestBody DataWriteDto dto) {
        try {
            this.dwService.saveData(dto);
            return super.buildSucces();
        } catch (Exception var3) {
            log.error("填报出错。", var3);
            return super.buildError(var3.getMessage());
        }
    }

    @RequestMapping(
        value = {"/updateSubmit.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object update(@RequestBody DataWriteDto dto) {
        try {
            this.dwService.updateData(dto);
            return super.buildSucces();
        } catch (Exception var3) {
            log.error("填报出错。", var3);
            return super.buildError(var3.getMessage());
        }
    }

    @RequestMapping({"/delData.action"})
    @ResponseBody
    public Object delData(String tableName, Integer dataId) {
        this.dwService.delData(tableName, dataId);
        return super.buildSucces();
    }

    @RequestMapping({"/premod.action"})
    public String preMod(Integer tableId, Integer dataId, ModelMap model) {
        EtlTableMeta meta = this.service.getTable(tableId);
        List<EtlTableMetaCol> cols = this.colService.queryTableColumnsNotExpress(tableId);
        model.addAttribute("table", meta);
        model.addAttribute("cols", cols);
        model.addAttribute("data", this.dwService.getDataById(tableId, dataId, meta.getTableName()));
        return "imp/Submit-mod";
    }

    @RequestMapping(
        value = {"/impDatas.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object impDatas(String tarTname, Integer tarTid, String tname, Integer tableId) {
        return this.dwService.impDatas(tarTname, tarTid, tname, tableId);
    }
}
