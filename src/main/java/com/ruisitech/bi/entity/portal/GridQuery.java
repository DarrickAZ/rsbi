//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.portal;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;

public class GridQuery extends BaseEntity {
    private String id;
    private String type;
    private String name;
    private Integer tid;
    private String tname;
    private List<GridColDto> cols;
    private List<PortalParamDto> portalParams;
    private List<CompParamDto> compParams;
    private String lockhead;
    private Integer height;
    private Integer pageSize;
    private String isnotfy;
    private Boolean showtitle;
    private String useIn;
    private List<DashboardParamDto> dashboardParam;
    private Integer currPage;
    private String dashboardStyle;

    public GridQuery() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GridColDto> getCols() {
        return this.cols;
    }

    public void setCols(List<GridColDto> cols) {
        this.cols = cols;
    }

    public List<PortalParamDto> getPortalParams() {
        return this.portalParams;
    }

    public void setPortalParams(List<PortalParamDto> portalParams) {
        this.portalParams = portalParams;
    }

    public List<CompParamDto> getCompParams() {
        return this.compParams;
    }

    public void setCompParams(List<CompParamDto> compParams) {
        this.compParams = compParams;
    }

    public String getLockhead() {
        return this.lockhead;
    }

    public void setLockhead(String lockhead) {
        this.lockhead = lockhead;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getIsnotfy() {
        return this.isnotfy;
    }

    public void setIsnotfy(String isnotfy) {
        this.isnotfy = isnotfy;
    }

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getCurrPage() {
        return this.currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public String getTname() {
        return this.tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Boolean getShowtitle() {
        return this.showtitle;
    }

    public void setShowtitle(Boolean showtitle) {
        this.showtitle = showtitle;
    }

    public String getUseIn() {
        return this.useIn;
    }

    public void setUseIn(String useIn) {
        this.useIn = useIn;
    }

    public List<DashboardParamDto> getDashboardParam() {
        return this.dashboardParam;
    }

    public void setDashboardParam(List<DashboardParamDto> dashboardParam) {
        this.dashboardParam = dashboardParam;
    }

    public void validate() {
        this.name = RSBIUtils.htmlEscape(this.name);
    }

    public String getDashboardStyle() {
        return this.dashboardStyle;
    }

    public void setDashboardStyle(String dashboardStyle) {
        this.dashboardStyle = dashboardStyle;
    }
}
