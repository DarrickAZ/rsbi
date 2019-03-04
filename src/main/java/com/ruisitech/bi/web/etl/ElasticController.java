//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.ruisitech.bi.entity.etl.EtlTransform;
import com.ruisitech.bi.service.etl.ElasticService;
import com.ruisitech.bi.service.etl.TransformService;
import com.ruisitech.bi.util.BaseController;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/etl"})
public class ElasticController extends BaseController {
    @Autowired
    private TransformService tfService;
    @Autowired
    private ElasticService elastic;

    public ElasticController() {
    }

    @RequestMapping(
        value = {"/elastic/save.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object save(EtlTransform tf) {
        try {
            this.elastic.saveOrUpdate(tf);
            return this.buildSucces();
        } catch (Exception var3) {
            return this.buildError(var3.getMessage());
        }
    }

    @RequestMapping(
        value = {"/elastic/update.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object update(EtlTransform tf) {
        try {
            this.elastic.saveOrUpdate(tf);
            return this.buildSucces();
        } catch (Exception var3) {
            return this.buildError(var3.getMessage());
        }
    }

    @RequestMapping(
        value = {"/elastic/run.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object run(Integer id, HttpServletRequest req) throws SQLException {
        return this.elastic.run(id, req.getServletContext());
    }

    @RequestMapping({"/loadEsTable.action"})
    @ResponseBody
    public Object list() {
        return this.tfService.listEsTables();
    }

    @RequestMapping({"/deleteEsTable.action"})
    @ResponseBody
    public Object delete(Integer id) {
        this.elastic.deleteConfig(id);
        return super.buildSucces();
    }
}
