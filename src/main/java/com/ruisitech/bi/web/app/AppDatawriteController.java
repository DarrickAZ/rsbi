//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.service.etl.EtlTableMetaColService;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.service.imp.DataWriteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/app"})
public class AppDatawriteController {
    @Autowired
    private EtlTableMetaService service;
    @Autowired
    private DataWriteService dwService;
    @Autowired
    private EtlTableMetaColService colService;

    public AppDatawriteController() {
    }

    @RequestMapping({"/Datawrite.action"})
    public String index(ModelMap model) {
        List<EtlTableMeta> ls = this.service.selectTables("dw");
        model.addAttribute("ls", ls);
        return "app/Datawrite";
    }

    @RequestMapping({"/write.action"})
    public String write(Integer tableId, ModelMap model) {
        EtlTableMeta meta = this.service.getTable(tableId);
        List<EtlTableMetaCol> cols = this.colService.queryTableColumnsNotExpress(tableId);
        model.addAttribute("table", meta);
        model.addAttribute("cols", cols);
        return "app/Datawrite-write";
    }

    @RequestMapping({"/list.action"})
    public String list(Integer tableId, ModelMap model) {
        EtlTableMeta meta = this.service.getTable(tableId);
        List<EtlTableMetaCol> cols = this.colService.queryTableColumnsNotExpress(tableId);
        model.addAttribute("table", meta);
        model.addAttribute("cols", cols);
        return "app/Datawrite-list";
    }

    @RequestMapping({"/premod.action"})
    public String preMod(Integer tableId, Integer dataId, ModelMap model) {
        EtlTableMeta meta = this.service.getTable(tableId);
        List<EtlTableMetaCol> cols = this.colService.queryTableColumnsNotExpress(tableId);
        model.addAttribute("table", meta);
        model.addAttribute("cols", cols);
        model.addAttribute("data", this.dwService.getDataById(tableId, dataId, meta.getTableName()));
        return "app/Datawrite-mod";
    }
}
