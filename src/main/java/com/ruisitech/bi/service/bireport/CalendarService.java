//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.bireport;

import com.ruisi.ext.runtime.tag.CalendarTag;
import com.ruisitech.bi.entity.bireport.Calendar;
import com.ruisitech.bi.mapper.bireport.CalendarMapper;
import com.ruisitech.bi.util.RSBIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendarService {
    @Autowired
    private CalendarMapper mapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public CalendarService() {
    }

    public void insertFestival(Calendar cal) {
        if (cal.getIdType() == 2) {
            cal.setId(this.mapper.maxFestivalId(this.sysUser));
        }

        if (cal.getType().equals("more")) {
            cal.setDay(cal.getDay().substring(4, 8));
        }

        this.mapper.insertFestival(cal);
        CalendarTag.pushFestival(new String[]{cal.getDay(), cal.getFestival()});
    }

    public void removeFestival(Calendar cal) {
        String day = cal.getDay();
        CalendarTag.removeFestival(day);
        this.mapper.removeFestival(cal);
        day = day.substring(4, 8);
        CalendarTag.removeFestival(day);
        cal.setDay(day);
        this.mapper.removeFestival(cal);
    }

    public Integer maxFestivalId() {
        return this.mapper.maxFestivalId(this.sysUser);
    }
}
