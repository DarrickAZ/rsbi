//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.dashboard;

import com.ruisitech.bi.entity.dashboard.Dashboard;
import com.ruisitech.bi.service.dashboard.DashboardService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/dashboard"})
public class IndexController extends BaseController {
    @Autowired
    private DashboardService service;

    public IndexController() {
    }

    @RequestMapping({"/Index.action"})
    public String index(Integer id, ModelMap model) {
        if (id != null) {
            Dashboard vo = this.service.selectByPrimaryKey(id);
            if (vo != null && vo.getPageInfo() != null) {
                model.addAttribute("pageInfo", vo.getPageInfo());
                model.addAttribute("title", vo.getPageName());
                model.addAttribute("editor", vo.getEditorEnable());
            }
        } else {
            Integer did = this.service.getDefDashboard(RSBIUtils.getLoginUserInfo().getUserId());
            if (did != null) {
                Dashboard vo = this.service.selectByPrimaryKey(did);
                if (vo != null && vo.getPageInfo() != null) {
                    model.addAttribute("pageInfo", vo.getPageInfo());
                    model.addAttribute("title", vo.getPageName());
                    model.addAttribute("editor", vo.getEditorEnable());
                }
            }
        }

        return "dashboard/Index";
    }

    @RequestMapping({"/get.action"})
    @ResponseBody
    public Object get(Integer id) {
        Dashboard vo = this.service.selectByPrimaryKey(id);
        return vo;
    }

    @RequestMapping({"/View.action"})
    public String view(Integer id, ModelMap model) {
        if (id == null) {
            model.addAttribute("msg", "id为空.");
            return "control/SpringmvcError";
        } else {
            Dashboard vo = this.service.selectByPrimaryKey(id);
            if (vo == null) {
                model.addAttribute("msg", "id为" + id + "的报表不存在.");
                return "control/SpringmvcError";
            } else {
                model.addAttribute("pageInfo", vo.getPageInfo());
                model.addAttribute("title", vo.getPageName());
                model.addAttribute("editor", vo.getEditorEnable());
                return "dashboard/View";
            }
        }
    }

    @RequestMapping({"/copyUrl.action"})
    @ResponseBody
    public Object copyUrl(Integer id, HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/dashboard/View.action?id=" + id;
        return super.buildSucces(basePath);
    }

    @RequestMapping({"/newDashboard.action"})
    public String newDashboard(Integer id, ModelMap model) {
        return "dashboard/Index";
    }

    @RequestMapping(
        value = {"/save.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object save(Dashboard dashboard) {
        Integer pageId = dashboard.getId();
        if (pageId == null) {
            JSONObject obj = JSONObject.fromObject(dashboard.getPageInfo());
            Integer id = this.service.maxDashboardId();
            obj.put("id", id);
            dashboard.setId(id);
            dashboard.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
            dashboard.setPageInfo(obj.toString());
            this.service.insert(dashboard);
        } else {
            this.service.updateByPrimaryKeySelective(dashboard);
        }

        return super.buildSucces(dashboard.getId());
    }

    @RequestMapping({"/list.action"})
    @ResponseBody
    public Object list() {
        Dashboard dashboard = new Dashboard();
        dashboard.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        return super.buildSucces(this.service.list(dashboard));
    }

    @RequestMapping({"/search.action"})
    @ResponseBody
    public Object search(String keyword) {
        Dashboard dashboard = new Dashboard();
        dashboard.setKeyword(keyword);
        dashboard.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        return super.buildSucces(this.service.list(dashboard));
    }

    @RequestMapping(
        value = {"/setDashboard.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object setDashboard(Dashboard dashboard) {
        Boolean def = dashboard.getDefDashboard();
        if (def != null && def) {
            this.service.setDefDashboard(dashboard.getId(), RSBIUtils.getLoginUserInfo().getUserId());
        }

        this.service.updateByPrimaryKeySelective(dashboard);
        return super.buildSucces();
    }

    @RequestMapping({"/delete.action"})
    @ResponseBody
    public Object delete(Integer id) {
        this.service.deleteByPrimaryKey(id);
        return super.buildSucces();
    }
}
