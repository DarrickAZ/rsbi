//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.portal;

import com.ruisitech.bi.entity.bireport.KpiDto;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;

public class BoxQuery extends BaseEntity {
    private String id;
    private String type;
    private String name;
    private Integer tid;
    private String tname;
    private Integer height;
    private KpiDto kpiJson;
    private List<PortalParamDto> portalParams;
    private List<CompParamDto> compParams;
    private String bgcolor;
    private String useIn;
    private String dashboardStyle;
    private List<DashboardParamDto> dashboardParam;

    public BoxQuery() {
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

    public KpiDto getKpiJson() {
        return this.kpiJson;
    }

    public void setKpiJson(KpiDto kpiJson) {
        this.kpiJson = kpiJson;
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

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getTname() {
        return this.tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getBgcolor() {
        return this.bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
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
