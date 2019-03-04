//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.app;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;

public class Collect extends BaseEntity {
    private String rid;
    private Integer userId;
    private Date crtdate;
    private String title;
    private String url;

    public Collect() {
    }

    public String getRid() {
        return this.rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCrtdate() {
        return this.crtdate;
    }

    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void validate() {
        this.title = RSBIUtils.htmlEscape(this.title);
        this.rid = RSBIUtils.htmlEscape(this.rid);
        this.url = RSBIUtils.htmlEscape(this.url);
    }
}
