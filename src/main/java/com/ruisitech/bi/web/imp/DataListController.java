//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.imp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/imp"})
public class DataListController {
    public DataListController() {
    }

    @RequestMapping({"/DataList.action"})
    public String index() {
        return "imp/DataList";
    }
}
