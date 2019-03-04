//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.dashboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;

public class CompShare extends BaseEntity {
    private String id;
    private Integer crtuser;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date crtdate;
    private String title;
    private String cfg;
    private String compIcon;

    public CompShare() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getCrtuser() {
        return this.crtuser;
    }

    public void setCrtuser(Integer crtuser) {
        this.crtuser = crtuser;
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
        this.title = title == null ? null : title.trim();
    }

    public String getCfg() {
        return this.cfg;
    }

    public void setCfg(String cfg) {
        this.cfg = cfg == null ? null : cfg.trim();
    }

    public String getCompIcon() {
        return this.compIcon;
    }

    public void setCompIcon(String compIcon) {
        this.compIcon = compIcon;
    }

    public void validate() {
        this.id = RSBIUtils.htmlEscape(this.id);
        this.title = RSBIUtils.htmlEscape(this.title);
        this.compIcon = RSBIUtils.htmlEscape(this.compIcon);
    }
}
