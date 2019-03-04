//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.portal;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruisitech.bi.entity.common.PageParam;
import com.ruisitech.bi.entity.portal.Portal;
import com.ruisitech.bi.entity.report.MbReportType;
import com.ruisitech.bi.service.portal.PortalService;
import com.ruisitech.bi.service.report.MbReportTypeService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.bi.util.SortService;
import java.util.Collections;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/portal"})
public class PortalIndexController extends BaseController {
    @Autowired
    private PortalService portalService;
    @Autowired
    private MbReportTypeService mbService;

    public PortalIndexController() {
    }

    @RequestMapping({"/customization.action"})
    public String customization(String pageId, String menus, String is3g, ModelMap model) {
        if (menus != null && menus.length() > 0) {
            JSONObject obj = JSONObject.fromObject(menus);
            model.addAttribute("menuDisp", obj);
        }

        String pageInfo = pageId == null ? null : this.portalService.getPortalCfg(pageId);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("is3g", is3g);
        return "portal/PortalIndex-customiz";
    }

    @RequestMapping({"/PortalIndex.action"})
    public String index(Integer cataId, ModelMap model) {
        List<MbReportType> typeLs = this.mbService.listcataTree("pc");
        model.addAttribute("typels", typeLs);
        model.addAttribute("cataId", cataId);
        return "portal/PortalIndex";
    }

    @RequestMapping({"/listReports.action"})
    @ResponseBody
    public Object listReports(Integer cataId, PageParam page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<Portal> ls = this.portalService.listPortal(cataId);
        if (page.getOrder() != null && page.getSort() != null) {
            SortService sort = new SortService(page.getSort(), page.getOrder());
            Collections.sort(ls, sort);
        }

        PageInfo<Portal> pageInfo = new PageInfo(ls);
        return super.buildSucces(pageInfo);
    }

    @RequestMapping({"/delete.action"})
    @ResponseBody
    public Object delete(String pageId) {
        this.portalService.deletePortal(pageId);
        return this.buildSucces();
    }

    @RequestMapping({"/deleteType.action"})
    @ResponseBody
    public Object deleteType(Integer id) {
        int cnt = this.mbService.cntReport(id);
        if (cnt > 0) {
            return this.buildError("分类下存在报表,不能删除。");
        } else {
            this.mbService.deleleType(id);
            return this.buildSucces();
        }
    }

    @RequestMapping(
        value = {"/rename.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object rename(Portal portal) {
        this.portalService.renamePortal(portal);
        return this.buildSucces();
    }

    @RequestMapping({"/show.action"})
    public String show(String pageId, String income, ModelMap model) {
        model.addAttribute("pageId", pageId);
        model.addAttribute("income", income);
        return "portal/PortalIndex-show";
    }

    @RequestMapping(
        value = {"/save.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object save(Portal portal) {
        String pageId = portal.getPageId();
        if (pageId != null && pageId.length() != 0) {
            this.portalService.updatePortal(portal);
        } else {
            JSONObject obj = JSONObject.fromObject(portal.getPageInfo());
            String id = RSBIUtils.getUUIDStr();
            obj.put("id", id);
            portal.setPageId(id);
            portal.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
            portal.setPageInfo(obj.toString());
            portal.setIs3g("y".equals(portal.getIs3g()) ? "y" : "n");
            this.portalService.insertPortal(portal);
        }

        return super.buildSucces(portal.getPageId());
    }

    @RequestMapping({"/listTypes.action"})
    @ResponseBody
    public Object listTypes() {
        return this.mbService.listcataTree("pc");
    }

    @RequestMapping({"/listMobileTypes.action"})
    @ResponseBody
    public Object listAppTypes() {
        return this.mbService.listcataTree("mobile");
    }
}
