//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.model;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruisitech.bi.entity.common.PageParam;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.entity.model.TableMeta;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.service.model.SubjectManagerService;
import com.ruisitech.bi.service.model.SubjectTypeService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.SortService;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/model"})
public class SubjectManagerController extends BaseController {
    @Autowired
    private SubjectTypeService serivce;
    @Autowired
    private SubjectManagerService managerSerivce;
    @Autowired
    private EtlTableMetaService tableMetaService;

    public SubjectManagerController() {
    }

    @RequestMapping({"/SubjectManager.action"})
    public String index(ModelMap model) {
        List<Map<String, Object>> datas = this.serivce.selectByTree();
        model.addAttribute("str", JSONArray.fromObject(datas));
        return "model/SubjectManager";
    }

    @RequestMapping({"/listSubject.action"})
    @ResponseBody
    public Object listSubject(TableMeta table, PageParam page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<TableMeta> ls = this.managerSerivce.listAllSubject(table);
        if (page.getOrder() != null && page.getSort() != null) {
            SortService s = new SortService(page.getSort(), page.getOrder());
            Collections.sort(ls, s);
        }

        PageInfo<TableMeta> pageInfo = new PageInfo(ls);
        return super.buildSucces(pageInfo);
    }

    @RequestMapping({"/listSubjectNoPage.action"})
    @ResponseBody
    public Object listSubjectNoPage(TableMeta table) {
        List<TableMeta> ls = this.managerSerivce.listAllSubject(table);
        return ls;
    }

    @RequestMapping({"/listAuthSubject.action"})
    @ResponseBody
    public Object listAuthSubject(TableMeta table, PageParam page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<TableMeta> ls = this.managerSerivce.listAuthSubject(table);
        PageInfo<TableMeta> pageInfo = new PageInfo(ls);
        return super.buildSucces(pageInfo);
    }

    @RequestMapping({"/delSubject.action"})
    @ResponseBody
    public Object delSubject(Integer tid) {
        this.managerSerivce.delCube(tid);
        return super.buildSucces();
    }

    @RequestMapping({"/getSubject.action"})
    @ResponseBody
    public Object getSubject(Integer tableId) {
        TableMeta table = this.managerSerivce.getCube(tableId);
        Map<String, Object> ret = new HashMap();
        ret.put("table", table);
        ret.put("types", this.serivce.list());
        List<EtlTableMetaCol> cols = this.tableMetaService.queryTableColumns(tableId, true);
        ret.put("cols", cols);
        return ret;
    }
}
