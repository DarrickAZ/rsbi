//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.portal;

import com.ruisitech.bi.entity.dashboard.CompShare;
import com.ruisitech.bi.service.dashboard.CompShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/portal"})
public class CompEditerController {
    @Autowired
    private CompShareService service;

    public CompEditerController() {
    }

    @RequestMapping({"/CompEditer.action"})
    public String index(String id, String from, ModelMap model) {
        if ("comp".equals(from)) {
            CompShare comp = this.service.selectByPrimaryKey(id);
            if (comp != null) {
                String json = comp.getCfg();
                model.put("pageInfo", "{\"layout\":1,\"body\":{\"tr1\":[{\"colspan\":1,\"rowspan\":1,\"width\":100,\"height\":100,\"id\":1,\"children\":[" + json + "]}]},\"id\":\"xxxxxx\"}");
            }
        } else if ("dashboard".equals(from)) {
            model.put("id", id);
        }

        return "portal/CompEditer";
    }
}
