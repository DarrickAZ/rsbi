//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.portal;

import com.ruisitech.bi.entity.bireport.TableQueryDto;
import java.util.List;
import java.util.Map;

public class PortalTableQuery extends TableQueryDto {
    private String id;
    private String name;
    private String type;
    private Map<String, Object> link;
    private List<PortalParamDto> portalParams;
    private List<CompParamDto> compParams;
    private String lockhead;
    private String height;
    private String showtitle;
    private Map<String, Object> style;
    private List<Map<String, Object>> drillDim;
    private String useIn;
    private List<DashboardParamDto> dashboardParam;
    private String dashboardStyle;

    public PortalTableQuery() {
    }

    public List<DashboardParamDto> getDashboardParam() {
        return this.dashboardParam;
    }

    public void setDashboardParam(List<DashboardParamDto> dashboardParam) {
        this.dashboardParam = dashboardParam;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getLink() {
        return this.link;
    }

    public void setLink(Map<String, Object> link) {
        this.link = link;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Map<String, Object>> getDrillDim() {
        return this.drillDim;
    }

    public void setDrillDim(List<Map<String, Object>> drillDim) {
        this.drillDim = drillDim;
    }

    public String getLockhead() {
        return this.lockhead;
    }

    public void setLockhead(String lockhead) {
        this.lockhead = lockhead;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Map<String, Object> getStyle() {
        return this.style;
    }

    public void setStyle(Map<String, Object> style) {
        this.style = style;
    }

    public String getShowtitle() {
        return this.showtitle;
    }

    public void setShowtitle(String showtitle) {
        this.showtitle = showtitle;
    }

    public String getUseIn() {
        return this.useIn;
    }

    public void setUseIn(String useIn) {
        this.useIn = useIn;
    }

    public String getDashboardStyle() {
        return this.dashboardStyle;
    }

    public void setDashboardStyle(String dashboardStyle) {
        this.dashboardStyle = dashboardStyle;
    }
}
