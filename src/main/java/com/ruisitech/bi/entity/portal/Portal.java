//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.portal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;

public class Portal extends BaseEntity {
    private String pageId;
    private Integer userId;
    private String userName;
    private String pageInfo;
    private String pageName;
    private String is3g;
    private Integer cataId;
    private String cataName;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date crtDate;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date updateDate;

    public Portal() {
    }

    public String getPageId() {
        return this.pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(String pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getPageName() {
        return this.pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getIs3g() {
        return this.is3g;
    }

    public void setIs3g(String is3g) {
        this.is3g = is3g;
    }

    public Integer getCataId() {
        return this.cataId;
    }

    public void setCataId(Integer cataId) {
        this.cataId = cataId;
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

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCataName() {
        return this.cataName;
    }

    public void setCataName(String cataName) {
        this.cataName = cataName;
    }

    public void validate() {
        this.pageName = RSBIUtils.htmlEscape(this.pageName);
        this.pageId = RSBIUtils.htmlEscape(this.pageId);
    }
}
