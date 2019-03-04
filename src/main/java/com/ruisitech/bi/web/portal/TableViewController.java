//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.portal;

import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisitech.bi.entity.portal.PortalTableQuery;
import com.ruisitech.bi.service.portal.PortalTableService;
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

@Controller("portalTableController")
@Scope("prototype")
@RequestMapping({"/portal"})
public class TableViewController {
    private static Logger log = Logger.getLogger(TableViewController.class);
    @Autowired
    private PortalTableService serivce;

    public TableViewController() {
    }

    @RequestMapping(
        value = {"/TableView.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object tableView(@RequestBody PortalTableQuery table, HttpServletRequest req, HttpServletResponse res) {
        try {
            ExtContext.getInstance().removeMV("mv.portal.table");
            MVContext mv = this.serivce.json2MV(table);
            CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
            ser.setParams(this.serivce.getMvParams());
            ser.initPreview();
            String ret = ser.buildMV(mv, req.getServletContext());
            return ret;
        } catch (Exception var7) {
            log.error("table出错了。", var7);
            return var7.getMessage();
        }
    }
}
