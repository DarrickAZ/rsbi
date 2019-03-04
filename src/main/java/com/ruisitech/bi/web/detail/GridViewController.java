//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.detail;

import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisitech.bi.entity.detail.GridQueryDto;
import com.ruisitech.bi.service.detail.GridService;
import com.ruisitech.bi.util.CompPreviewService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/detail"})
public class GridViewController {
    private static Logger log = Logger.getLogger(GridViewController.class);
    @Autowired
    private GridService service;

    public GridViewController() {
    }

    @RequestMapping({"/GridView.action"})
    @ResponseBody
    public Object view(@RequestBody GridQueryDto grid, HttpServletRequest req, HttpServletResponse res) {
        ExtContext.getInstance().removeMV("mv.grid.gridReport");

        try {
            MVContext mv = this.service.json2MV(grid, false);
            CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
            ser.initPreview();
            String ret = ser.buildMV(mv, req.getServletContext());
            return ret;
        } catch (Exception var7) {
            log.error("出错了。", var7);
            return var7.getMessage();
        }
    }
}
