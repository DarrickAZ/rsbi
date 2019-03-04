//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.etl.DataSource;
import com.ruisitech.bi.entity.etl.EtlTransform;
import com.ruisitech.bi.service.etl.DataSetService;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.service.etl.R2CService;
import com.ruisitech.bi.service.etl.TransformService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/etl"})
public class TransformController extends BaseController {
    @Autowired
    private EtlTableMetaService service;
    @Autowired
    private DataSetService dsetService;
    @Autowired
    private TransformService tfService;
    @Autowired
    private R2CService r2cService;

    public TransformController() {
    }

    @RequestMapping({"/Transform.action"})
    public String index() {
        return "etl/Transform";
    }

    @RequestMapping({"/loadTfTable.action"})
    @ResponseBody
    public Object list() {
        return this.tfService.listTf("aggre");
    }

    @RequestMapping({"/loadR2CTable.action"})
    @ResponseBody
    public Object listR2C() {
        return this.tfService.listTf("r2c");
    }

    @RequestMapping({"/listDatasetColumns.action"})
    @ResponseBody
    public Object listDatasetColumns(String transform) {
        JSONObject dataset = (JSONObject)JSON.parse(transform);

        try {
            List<DSColumn> ret = this.dsetService.queryMetaAndIncome(dataset, (DataSource)null);
            return super.buildSucces(ret);
        } catch (Exception var4) {
            var4.printStackTrace();
            return super.buildError(var4.getMessage());
        }
    }

    @RequestMapping(
        value = {"/updateTf.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object update(EtlTransform tf) {
        this.tfService.update(tf);
        return super.buildSucces();
    }

    @RequestMapping(
        value = {"/saveTf.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object save(EtlTransform tf) {
        tf.setIncome("aggre");
        tf.setCrtuser(RSBIUtils.getLoginUserInfo().getUserId());
        this.tfService.insert(tf);
        return super.buildSucces();
    }

    @RequestMapping(
        value = {"/saveR2C.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object saveR2C(EtlTransform tf) {
        tf.setIncome("r2c");
        tf.setCrtuser(RSBIUtils.getLoginUserInfo().getUserId());
        this.r2cService.insert(tf);
        return super.buildSucces();
    }

    @RequestMapping(
        value = {"/updateR2C.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object updateR2C(EtlTransform tf) {
        tf.setCrtuser(RSBIUtils.getLoginUserInfo().getUserId());
        this.r2cService.update(tf);
        return super.buildSucces();
    }

    @RequestMapping({"/delTf.action"})
    @ResponseBody
    public Object delTf(Integer id) {
        this.tfService.deleteByPrimaryKey(id);
        return super.buildSucces();
    }

    @RequestMapping({"/getTf.action"})
    @ResponseBody
    public Object getTf(Integer id) {
        return this.tfService.getTfConfig(id);
    }

    @RequestMapping({"/runTf.action"})
    @ResponseBody
    public Object runTf(Integer id, HttpServletRequest req) {
        EtlTransform tf = this.tfService.selectByPrimaryKey(id);
        return this.tfService.runTf(tf, req.getServletContext());
    }

    @RequestMapping({"/runR2C.action"})
    @ResponseBody
    public Object runR2C(Integer id, HttpServletRequest req) {
        EtlTransform tf = this.tfService.selectByPrimaryKey(id);
        return this.r2cService.runTf(tf, req.getServletContext());
    }
}
