//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.detail;

import com.ruisitech.bi.service.detail.CrossDetailService;
import com.ruisitech.bi.util.BaseController;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("detailDetailController")
@RequestMapping({"/detail"})
public class DetailController extends BaseController {
    @Autowired
    private CrossDetailService detailService;

    public DetailController() {
    }

    @RequestMapping(
        value = {"/detail.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object detail(String json, HttpServletRequest req) {
        try {
            return this.detailService.detailDatas(json);
        } catch (Exception var4) {
            return super.buildError("出错了," + var4.getMessage());
        }
    }
}
