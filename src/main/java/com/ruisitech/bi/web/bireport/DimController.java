//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.bireport;

import com.ruisitech.bi.entity.bireport.ParamDto;
import com.ruisitech.bi.entity.model.TableDimension;
import com.ruisitech.bi.service.bireport.OlapService;
import com.ruisitech.bi.service.model.TableDimensionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/bireport"})
public class DimController {
    @Autowired
    private OlapService service;
    @Autowired
    private TableDimensionService dimService;

    public DimController() {
    }

    @RequestMapping({"/queryDims.action"})
    @ResponseBody
    public Object queryDims(Integer tableId) {
        return this.dimService.getTableDims(tableId);
    }

    @RequestMapping({"/paramFilter.action"})
    public String paramFilter(ParamDto param, ModelMap model) throws Exception {
        TableDimension d = this.dimService.queryDimCol(param.getId(), param.getTid());
        List<Object> ls = this.dimService.paramFilter(d, (String)null);
        model.addAttribute("datas", ls);
        model.addAttribute("dimType", d.getType());
        model.addAttribute("vals", param.getVals());
        model.addAttribute("dimId", param.getId());
        if (d.getType().equals("month") || d.getType().equals("day")) {
            model.addAttribute("st", param.getSt());
            model.addAttribute("end", param.getEnd());
        }

        return "bireport/DimFilter-pfilter";
    }

    @RequestMapping({"/paramSearch.action"})
    public String paramSearch(ParamDto param, String keyword, ModelMap model) throws Exception {
        TableDimension d = this.dimService.queryDimCol(param.getId(), param.getTid());
        List<Object> ls = this.dimService.paramFilter(d, keyword);
        model.addAttribute("datas", ls);
        model.addAttribute("dimType", d.getType());
        model.addAttribute("vals", param.getVals());
        model.addAttribute("dimId", param.getId());
        return "bireport/DimFilter-search";
    }

    @RequestMapping({"/DimFilter.action"})
    public String dimFilter(ParamDto param, ModelMap model) throws Exception {
        TableDimension d = this.dimService.queryDimCol(param.getId(), param.getTid());
        List<Object> ls = this.dimService.paramFilter(d, (String)null);
        model.addAttribute("datas", ls);
        model.addAttribute("dimType", d.getType());
        model.addAttribute("vals", param.getVals());
        model.addAttribute("dimId", param.getId());
        if (d.getType().equals("month") || d.getType().equals("day")) {
            model.addAttribute("st", param.getSt());
            model.addAttribute("end", param.getEnd());
        }

        model.addAttribute("filtertype", param.getFiltertype());
        model.addAttribute("dateformat", param.getDateformat());
        return "bireport/DimFilter";
    }
}
