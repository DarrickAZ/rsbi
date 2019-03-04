//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.detail;

import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisitech.bi.entity.detail.CrossQueryDto;
import com.ruisitech.bi.service.detail.CrossService;
import com.ruisitech.bi.util.CompPreviewService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("detailCrossViewController")
@RequestMapping({"/detail"})
public class CrossViewController {
    private static Logger log = Logger.getLogger(CrossViewController.class);
    @Autowired
    private CrossService service;

    public CrossViewController() {
    }

    @RequestMapping({"/CrossView.action"})
    @ResponseBody
    public Object view(@RequestBody CrossQueryDto cross, HttpServletRequest req, HttpServletResponse res) {
        try {
            req.setAttribute("table", cross);
            req.setAttribute("compId", "T2");
            ExtContext.getInstance().removeMV("mv.detail.cross");
            MVContext mv = this.service.json2MV(cross);
            CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
            ser.initPreview();
            String ret = ser.buildMV(mv, req.getServletContext());
            return ret;
        } catch (Exception var7) {
            log.error("cross出错。", var7);
            return var7.getMessage();
        }
    }
}
