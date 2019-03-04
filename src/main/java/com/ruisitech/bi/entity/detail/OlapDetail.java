//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;

public class OlapDetail extends BaseEntity {
    private Integer pageId;
    private Integer userid;
    private String userName;
    private String pagename;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date crtdate;
    private Integer companyId;
    private String pageinfo;

    public OlapDetail() {
    }

    public Integer getPageId() {
        return this.pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getPagename() {
        return this.pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename == null ? null : pagename.trim();
    }

    public Date getCrtdate() {
        return this.crtdate;
    }

    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getPageinfo() {
        return this.pageinfo;
    }

    public void setPageinfo(String pageinfo) {
        this.pageinfo = pageinfo == null ? null : pageinfo.trim();
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void validate() {
        this.userName = RSBIUtils.htmlEscape(this.userName);
        this.pagename = RSBIUtils.htmlEscape(this.pagename);
    }
}
