//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.portal;

import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisitech.bi.entity.portal.GridQuery;
import com.ruisitech.bi.service.portal.PortalGridService;
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

@Controller("portalGridController")
@Scope("prototype")
@RequestMapping({"/portal"})
public class GridViewController extends BaseController {
    private static Logger log = Logger.getLogger(GridViewController.class);
    @Autowired
    private PortalGridService serivce;

    public GridViewController() {
    }

    @RequestMapping(
        value = {"/GridView.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object gridView(@RequestBody GridQuery grid, HttpServletRequest req, HttpServletResponse res) {
        try {
            ExtContext.getInstance().removeMV("mv.portal.gridReport");
            MVContext mv = this.serivce.json2MV(grid);
            CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
            ser.setParams(this.serivce.getMvParams());
            ser.initPreview();
            String ret = ser.buildMV(mv, req.getServletContext());
            return ret;
        } catch (Exception var7) {
            log.error("grid出错了。", var7);
            return var7.getMessage();
        }
    }
}
