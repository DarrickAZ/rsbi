//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.bireport;

import com.ruisitech.bi.entity.bireport.Calendar;
import com.ruisitech.bi.service.bireport.CalendarService;
import com.ruisitech.bi.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/bireport"})
public class CalendarController extends BaseController {
    @Autowired
    private CalendarService service;

    public CalendarController() {
    }

    @RequestMapping({"/Calendar.action"})
    public String index() {
        return "bireport/Calendar";
    }

    @RequestMapping({"/CalendarView.action"})
    public String view() {
        return "bireport/Calendar-view";
    }

    @RequestMapping(
        value = {"/removejr.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object remove(String day) {
        Calendar cal = new Calendar();
        cal.setDay(day);
        this.service.removeFestival(cal);
        return super.buildSucces();
    }

    @RequestMapping({"/addjr.action"})
    @ResponseBody
    public Object add(Calendar cal) {
        this.service.insertFestival(cal);
        return super.buildSucces();
    }
}
