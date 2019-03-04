//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.portal;

import com.ruisitech.bi.entity.bireport.ChartQueryDto;
import com.ruisitech.bi.entity.bireport.KpiDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PortalChartQuery extends ChartQueryDto {
    private List<KpiDto> useKpiJson;
    private List<PortalParamDto> portalParams;
    private List<CompParamDto> compParams;
    private String id;
    private String name;
    private String type;
    private String dashboardStyle;
    private Map<String, Integer> colors;
    private String useIn;
    private Map<String, Object> style = new HashMap();
    private List<DashboardParamDto> dashboardParam;

    public PortalChartQuery() {
    }

    public List<DashboardParamDto> getDashboardParam() {
        return this.dashboardParam;
    }

    public void setDashboardParam(List<DashboardParamDto> dashboardParam) {
        this.dashboardParam = dashboardParam;
    }

    public List<PortalParamDto> getPortalParams() {
        return this.portalParams;
    }

    public void setPortalParams(List<PortalParamDto> portalParams) {
        this.portalParams = portalParams;
    }

    public List<KpiDto> getKpiJson() {
        if (this.useKpiJson == null) {
            this.useKpiJson = new ArrayList();
            Iterator var1 = super.getKpiJson().iterator();

            while(var1.hasNext()) {
                KpiDto kpi = (KpiDto)var1.next();
                if (kpi != null) {
                    this.useKpiJson.add(kpi);
                }
            }
        }

        return this.useKpiJson;
    }

    public List<CompParamDto> getCompParams() {
        return this.compParams;
    }

    public void setCompParams(List<CompParamDto> compParams) {
        this.compParams = compParams;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Map<String, Object> getStyle() {
        return this.style;
    }

    public void setStyle(Map<String, Object> style) {
        this.style = style;
    }

    public List<KpiDto> getUseKpiJson() {
        return this.useKpiJson;
    }

    public void setUseKpiJson(List<KpiDto> useKpiJson) {
        this.useKpiJson = useKpiJson;
    }

    public Map<String, Integer> getColors() {
        return this.colors;
    }

    public void setColors(Map<String, Integer> colors) {
        this.colors = colors;
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
