//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.bireport;

import com.ruisitech.bi.entity.common.BaseEntity;

public class Calendar extends BaseEntity {
    private Integer id;
    private String day;
    private String type;
    private String festival;

    public Calendar() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFestival() {
        return this.festival;
    }

    public void setFestival(String festival) {
        this.festival = festival;
    }

    public void validate() {
    }
}
