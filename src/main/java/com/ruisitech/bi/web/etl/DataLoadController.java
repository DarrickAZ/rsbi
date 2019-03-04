//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.ruisitech.bi.entity.etl.DataImpRestDto;
import com.ruisitech.bi.entity.etl.ImpConfigDto;
import com.ruisitech.bi.service.etl.DataLoaderService;
import com.ruisitech.bi.util.BaseController;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope("prototype")
@RequestMapping({"/etl"})
public class DataLoadController extends BaseController {
    @Autowired
    private DataLoaderService service;

    public DataLoadController() {
    }

    @RequestMapping(
        value = {"/dataImpRest.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object dataImpRest(@RequestBody DataImpRestDto dto, HttpServletRequest req) {
        String chkStr = this.service.check(dto);
        if (chkStr == null) {
            try {
                this.service.restRun(dto, req.getServletContext());
                return super.buildSucces("成功导入" + this.service.getCurCount() + "条数据。");
            } catch (Exception var5) {
                return super.buildError(var5.getMessage());
            }
        } else {
            return super.buildError(chkStr);
        }
    }

    @RequestMapping(
        value = {"/runDataLoad.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object runDataLoad(@RequestBody ImpConfigDto dto, HttpServletRequest req) {
        req.getSession().setAttribute(dto.getImpid(), this.service);
        int ret = this.service.run(dto, req.getServletContext());
        req.getSession().removeAttribute(dto.getImpid());
        return ret == 1 ? this.buildError(this.service.getErrInfo()) : this.buildSucces(this.service.getCurCount());
    }

    @RequestMapping({"/getLoadState.action"})
    @ResponseBody
    public Object getState(String impid, HttpServletRequest req) {
        DataLoaderService dlService = (DataLoaderService)req.getSession().getAttribute(impid);
        if (dlService == null) {
            return this.buildError("导入已完成。");
        } else {
            int cnt = dlService.getRowCount();
            int curCnt = dlService.getCurCount();
            Map<String, Object> m = new HashMap();
            m.put("cnt", cnt);
            m.put("curCnt", curCnt);
            return this.buildSucces(m);
        }
    }

    @RequestMapping({"/stopLoad.action"})
    @ResponseBody
    public Object stopLoad(String impid, HttpServletRequest req) {
        DataLoaderService dlService = (DataLoaderService)req.getSession().getAttribute(impid);
        if (dlService == null) {
            return this.buildError("导入已完成。");
        } else {
            dlService.stopImport();
            req.getSession().removeAttribute(impid);
            return this.buildSucces();
        }
    }
}
