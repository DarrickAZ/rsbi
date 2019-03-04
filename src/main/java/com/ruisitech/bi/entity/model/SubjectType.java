//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;

public class SubjectType extends BaseEntity {
    private Integer dsId;
    private String name;
    private String note;
    private Integer pid;
    private String tp;
    private Integer ord;
    @JsonIgnore
    private Integer companyId;

    public SubjectType() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getTp() {
        return this.tp;
    }

    public void setTp(String tp) {
        this.tp = tp == null ? null : tp.trim();
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getDsId() {
        return this.dsId;
    }

    public void setDsId(Integer dsId) {
        this.dsId = dsId;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getOrd() {
        return this.ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public void validate() {
        this.note = RSBIUtils.htmlEscape(this.note);
        this.name = RSBIUtils.htmlEscape(this.name);
        this.tp = RSBIUtils.htmlEscape(this.tp);
    }
}
