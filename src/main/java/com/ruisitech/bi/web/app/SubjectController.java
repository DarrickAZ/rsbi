//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import com.ruisitech.bi.service.model.TableMetaServcice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("appSubjectController")
@RequestMapping({"/app"})
public class SubjectController {
    @Autowired
    private TableMetaServcice service;

    public SubjectController() {
    }

    @RequestMapping({"/Subject!list.action"})
    @ResponseBody
    public Object list() {
        return this.service.applistSubject();
    }
}
