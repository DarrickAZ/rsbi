//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.detail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.etl.DataSource;
import com.ruisitech.bi.service.detail.PageService;
import com.ruisitech.bi.service.etl.DataSetService;
import com.ruisitech.bi.util.BaseController;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/detail"})
public class DataSetController extends BaseController {
    private static Logger log = Logger.getLogger(DataSetController.class);
    @Autowired
    private DataSetService dsService;
    @Autowired
    private PageService pgService;

    public DataSetController() {
    }

    @RequestMapping({"/datasetMeta.action"})
    @ResponseBody
    public Object datasetMeta(String dataset) {
        JSONObject json = (JSONObject)JSON.parse(dataset);

        try {
            List<DSColumn> ret = this.dsService.queryMetaAndIncome(json, (DataSource)null);
            return ret;
        } catch (Exception var4) {
            log.error("元数据查询出错。", var4);
            return super.buildError(var4.getMessage());
        }
    }

    @RequestMapping({"/queryData.action"})
    public String queryData(String dataset, ModelMap model) {
        try {
            JSONObject json = (JSONObject)JSON.parse(dataset);
            String sql = this.pgService.createDatasetSql(json);
            List ls = this.dsService.queryTopN(sql, (DataSource)null, 50);
            model.addAttribute("ls", ls);
            return "detail/DataSet-queryData";
        } catch (Exception var6) {
            log.error("数据查询错误。", var6);
            model.addAttribute("msg", var6.getMessage());
            return "control/SpringmvcError";
        }
    }
}
