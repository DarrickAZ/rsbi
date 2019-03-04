//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.report;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;

public class MbReportType extends BaseEntity {
    private Integer id;
    private String name;
    private String text;
    private String note;
    private Integer crtUser;
    private Date crtDate;
    private Integer ord;
    private String iconCls;
    private String income;

    public MbReportType() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCrtUser() {
        return this.crtUser;
    }

    public void setCrtUser(Integer crtUser) {
        this.crtUser = crtUser;
    }

    public Date getCrtDate() {
        return this.crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public Integer getOrd() {
        return this.ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return this.iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getIncome() {
        return this.income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public void validate() {
        this.name = RSBIUtils.htmlEscape(this.name);
        this.text = RSBIUtils.htmlEscape(this.text);
        this.note = RSBIUtils.htmlEscape(this.note);
        this.iconCls = RSBIUtils.htmlEscape(this.iconCls);
        this.income = RSBIUtils.htmlEscape(this.income);
    }
}
