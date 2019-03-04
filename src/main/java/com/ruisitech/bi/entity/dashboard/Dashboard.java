//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.dashboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;

public class Dashboard extends BaseEntity {
    private Integer id;
    private Integer userId;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date createdate;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date updatedate;
    private String pageName;
    private String pageInfo;
    private String keyword;
    private String editorEnable;
    private Boolean defDashboard;

    public Dashboard() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return this.updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getPageName() {
        return this.pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName == null ? null : pageName.trim();
    }

    public String getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(String pageInfo) {
        this.pageInfo = pageInfo == null ? null : pageInfo.trim();
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getEditorEnable() {
        return this.editorEnable;
    }

    public void setEditorEnable(String editorEnable) {
        this.editorEnable = editorEnable;
    }

    public void validate() {
        this.pageName = RSBIUtils.htmlEscape(this.pageName);
        this.keyword = RSBIUtils.htmlEscape(this.keyword);
    }

    public Boolean getDefDashboard() {
        return this.defDashboard;
    }

    public void setDefDashboard(Boolean defDashboard) {
        this.defDashboard = defDashboard;
    }
}
