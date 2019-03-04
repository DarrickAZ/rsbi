//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.bireport;

import com.ruisitech.bi.entity.common.BaseEntity;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class QueryDayDto extends BaseEntity {
    private String startDay;
    private String endDay;

    public QueryDayDto() {
    }

    public String getStartDay() {
        return this.startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return this.endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public int getBetweenDay() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        long l1 = sdf.parse(this.startDay).getTime();
        long l2 = sdf.parse(this.endDay).getTime();
        long result = Math.abs(l1 - l2) / 86400000L;
        return (int)result;
    }

    public void validate() {
    }
}
