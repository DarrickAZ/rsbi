//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.report;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/report"})
public class MbIntroController {
    public MbIntroController() {
    }

    @RequestMapping({"/MbIntro.action"})
    public String index() {
        return "report/MbIntro";
    }
}
