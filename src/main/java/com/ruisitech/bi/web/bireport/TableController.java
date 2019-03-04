//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.bireport;

import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisitech.bi.entity.bireport.TableQueryDto;
import com.ruisitech.bi.service.bireport.OlapTableService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.CompPreviewService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("bireportTable")
@Scope("prototype")
@RequestMapping({"/bireport"})
public class TableController extends BaseController {
    private static Logger log = Logger.getLogger(ChartController.class);
    @Autowired
    private OlapTableService tableService;

    public TableController() {
    }

    @RequestMapping(
        value = {"/TableView.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object tableView(@RequestBody TableQueryDto tableJson, HttpServletRequest req, HttpServletResponse res) {
        try {
            req.setAttribute("table", tableJson);
            req.setAttribute("compId", String.valueOf(tableJson.getId()));
            ExtContext.getInstance().removeMV("mv.tmp.table");
            MVContext mv = this.tableService.json2MV(tableJson);
            CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
            ser.setParams(this.tableService.getMvParams());
            ser.initPreview();
            String ret = ser.buildMV(mv, req.getServletContext());
            return ret;
        } catch (Exception var7) {
            log.error("table出错了。", var7);
            return var7.getMessage();
        }
    }
}
