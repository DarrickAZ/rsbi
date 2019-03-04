//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.etl.DataSource;
import com.ruisitech.bi.entity.etl.ImpConfigDto;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.service.etl.MetaService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/etl"})
public class MetaController extends BaseController {
    private Logger log = Logger.getLogger(MetaController.class);
    @Autowired
    private EtlTableMetaService tableMetaService;
    @Autowired
    private MetaService service;

    public MetaController() {
    }

    @RequestMapping({"/listTables.action"})
    @ResponseBody
    public Object listTables(String income) {
        return this.tableMetaService.selectTables(income);
    }

    @RequestMapping({"/dwselectTables.action"})
    @ResponseBody
    public Object dwselectTables() {
        return this.tableMetaService.dwselectTables();
    }

    @RequestMapping({"/listdbTables.action"})
    @ResponseBody
    public Object listdbTables(DataSource dsource) {
        try {
            List<Map<String, Object>> ls = this.service.queryTables((String)null, dsource);
            return this.buildSucces(ls);
        } catch (Exception var3) {
            this.log.error("出错了。", var3);
            return this.buildError(var3.getMessage());
        }
    }

    @RequestMapping({"/listTablesBySchema.action"})
    @ResponseBody
    public Object listTablesBySchema(String id) {
        try {
            List<Map<String, Object>> ls = this.service.queryTables(id, (DataSource)null);
            return ls;
        } catch (Exception var3) {
            this.log.error("出错了。", var3);
            return this.buildError(var3.getMessage());
        }
    }

    @RequestMapping(
        value = {"/listTableColumns.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object listTableColumns(String tname, String dsource) {
        try {
            DataSource ds = null;
            String sql = "select * from " + tname;
            if (dsource != null) {
                JSONObject json = (JSONObject)JSON.parse(dsource);
                ds = (DataSource)JSONObject.toJavaObject(json, DataSource.class);
            }

            List<DSColumn> ls = this.service.queryTableCols(sql, ds, false);
            List<Map<String, Object>> ret = new ArrayList();

            for(int i = 0; i < ls.size(); ++i) {
                DSColumn t = (DSColumn)ls.get(i);
                String name = t.getName();
                Map<String, Object> m = new HashMap();
                m.put("id", name);
                m.put("text", name);
                m.put("name", name);
                m.put("iconCls", "icon-dscol");
                m.put("attributes", new HashMap());
                ret.add(m);
            }

            return ret;
        } catch (Exception var11) {
            this.log.error("出错了。", var11);
            return this.buildError(var11.getMessage());
        }
    }

    @RequestMapping({"/getTableColumns.action"})
    @ResponseBody
    public Object getTableColumns(Integer tableId) {
        return this.tableMetaService.queryTableColumns(tableId, true);
    }

    @RequestMapping({"/getTableColumnsNotExpress.action"})
    @ResponseBody
    public Object getTableColumnsNotExpress(Integer tableId) {
        return this.tableMetaService.queryTableColumns(tableId, false);
    }

    @RequestMapping({"/showImpDatas.action"})
    public String showImpDatas(@RequestBody ImpConfigDto dto, ModelMap model) {
        try {
            List<Object> ls = this.service.showImportDatas(dto);
            model.addAttribute("ls", ls);
            return dto.getImpType().equals("db") ? "etl/Meta-showDBDatas" : "etl/Meta-showDatas";
        } catch (Exception var4) {
            this.log.error("出错了。", var4);
            model.addAttribute("msg", var4.getMessage());
            return "/control/SpringmvcError";
        }
    }

    @RequestMapping({"/showImpOneData.action"})
    public String showImpOneData(@RequestBody ImpConfigDto dto, ModelMap model) {
        try {
            List<Object> ls = this.service.showImportOneData(dto);
            model.addAttribute("ls", ls);
            return dto.getImpType().equals("db") ? "etl/Meta-showDBOneData" : "etl/Meta-showOneData";
        } catch (Exception var4) {
            this.log.error("出错了。", var4);
            model.addAttribute("msg", var4.getMessage());
            return "/control/SpringmvcError";
        }
    }

    @RequestMapping({"/showImpColumns.action"})
    @ResponseBody
    public Object showImpColumns(@RequestBody ImpConfigDto dto) {
        try {
            List<DSColumn> ls = this.service.showImportColumns(dto, false);
            return this.buildSucces(ls);
        } catch (Exception var3) {
            this.log.error("出错了。", var3);
            return this.buildError(var3.getMessage());
        }
    }

    @RequestMapping({"/createTableColumns.action"})
    @ResponseBody
    public Object createTableColumns(@RequestBody ImpConfigDto dto) {
        try {
            List<DSColumn> ls = this.service.showImportColumns(dto, true);
            return this.buildSucces(ls);
        } catch (Exception var3) {
            this.log.error("出错了。", var3);
            return this.buildError(var3.getMessage());
        }
    }

    @RequestMapping({"/getDBName.action"})
    @ResponseBody
    public Object getDBName() {
        String dbName = RSBIUtils.getConstant("dbName");
        return super.buildSucces(dbName);
    }
}
