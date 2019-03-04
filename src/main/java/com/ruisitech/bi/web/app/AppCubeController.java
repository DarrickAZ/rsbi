//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import com.ruisitech.bi.service.app.AppCubeService;
import com.ruisitech.bi.util.BaseController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/app"})
public class AppCubeController extends BaseController {
    @Autowired
    private AppCubeService service;
    private static Logger log = Logger.getLogger(AppCubeController.class);

    public AppCubeController() {
    }

    @RequestMapping({"/Cube!getKpi.action"})
    @ResponseBody
    public Object getKpi(Integer tableid) {
        return this.service.getKpi(tableid);
    }

    @RequestMapping({"/Cube!getDim.action"})
    @ResponseBody
    public Object getDim(Integer tableid, String filterDateDim) {
        return this.service.getDim(tableid, filterDateDim);
    }

    @RequestMapping({"/DimFilter.action"})
    @ResponseBody
    public Object dimFilter(Integer dimId, Integer tid) {
        try {
            return this.service.dimFilter(dimId, tid);
        } catch (Exception var4) {
            log.error("维度筛选出错。", var4);
            return super.buildError(var4.getMessage());
        }
    }
}
