//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.report;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;

public class ShareUrl extends BaseEntity {
    private String token;
    private Integer islogin;
    private Integer yxq;
    private Date crtdate;
    private String reportId;
    private Integer crtUser;

    public ShareUrl() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Integer getIslogin() {
        return this.islogin;
    }

    public void setIslogin(Integer islogin) {
        this.islogin = islogin;
    }

    public Integer getYxq() {
        return this.yxq;
    }

    public void setYxq(Integer yxq) {
        this.yxq = yxq;
    }

    public Date getCrtdate() {
        return this.crtdate;
    }

    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }

    public String getReportId() {
        return this.reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Integer getCrtUser() {
        return this.crtUser;
    }

    public void setCrtUser(Integer crtUser) {
        this.crtUser = crtUser;
    }

    public void validate() {
        this.token = RSBIUtils.htmlEscape(this.token);
        this.reportId = RSBIUtils.htmlEscape(this.reportId);
    }
}
