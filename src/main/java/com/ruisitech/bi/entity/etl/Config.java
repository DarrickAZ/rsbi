//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.etl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;

public class Config extends BaseEntity {
    private Integer cfgid;
    private String cfgName;
    private Integer crtUser;
    private String loginName;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date crtDate;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date updateDate;
    private String cfgContent;
    private String impType;
    private Integer fileId;

    public Config() {
    }

    public Integer getCfgid() {
        return this.cfgid;
    }

    public void setCfgid(Integer cfgid) {
        this.cfgid = cfgid;
    }

    public String getCfgName() {
        return this.cfgName;
    }

    public void setCfgName(String cfgName) {
        this.cfgName = cfgName;
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

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCfgContent() {
        return this.cfgContent;
    }

    public void setCfgContent(String cfgContent) {
        this.cfgContent = cfgContent;
    }

    public String getImpType() {
        return this.impType;
    }

    public void setImpType(String impType) {
        this.impType = impType;
    }

    public Integer getFileId() {
        return this.fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void validate() {
        this.cfgName = RSBIUtils.htmlEscape(this.cfgName);
        this.loginName = RSBIUtils.htmlEscape(this.loginName);
        this.impType = RSBIUtils.htmlEscape(this.impType);
    }
}
