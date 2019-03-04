//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import com.ruisitech.bi.entity.app.Collect;
import com.ruisitech.bi.service.app.CollectService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/app"})
public class CollectController extends BaseController {
    @Autowired
    private CollectService service;

    public CollectController() {
    }

    @RequestMapping({"/Collect!list.action"})
    @ResponseBody
    public Object list(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        return this.service.listCollect(userId, basePath);
    }

    @RequestMapping({"/Collect!add.action"})
    @ResponseBody
    public Object add(Collect collect) {
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        collect.setUserId(userId);
        Map<String, Object> ret = new HashMap();
        int cnt = this.service.collectExist(collect);
        if (cnt > 0) {
            ret.put("result", false);
        } else {
            ret.put("result", true);
            this.service.addCollect(collect);
        }

        return ret;
    }

    @RequestMapping({"/Collect!delete.action"})
    @ResponseBody
    public Object delete(Collect collect) {
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        collect.setUserId(userId);
        this.service.delCollect(collect);
        return super.buildSucces();
    }
}
