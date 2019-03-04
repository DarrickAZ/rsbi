//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.etl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;

public class JobConfig extends BaseEntity {
    private Integer id;
    private String name;
    private Integer crtUser;
    private String crtUserName;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date crtDate;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date updatedate;
    private String cfg;
    private Integer state;

    public JobConfig() {
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

    public Integer getCrtUser() {
        return this.crtUser;
    }

    public void setCrtUser(Integer crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtUserName() {
        return this.crtUserName;
    }

    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    public Date getCrtDate() {
        return this.crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public Date getUpdatedate() {
        return this.updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getCfg() {
        return this.cfg;
    }

    public void setCfg(String cfg) {
        this.cfg = cfg;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void validate() {
        this.name = RSBIUtils.htmlEscape(this.name);
    }
}
