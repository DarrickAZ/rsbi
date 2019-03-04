//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.bireport;

import com.ruisitech.bi.entity.common.BaseEntity;
import java.util.List;

public class ChartQueryDto extends BaseEntity {
    private String id;
    private Integer tid;
    private String tname;
    private Boolean mkpi;
    private List<KpiDto> kpiJson;
    private List<KpiDto> mkpiJson;
    private ChartJSONDto chartJson;
    private List<ParamDto> params;

    public ChartQueryDto() {
    }

    public List<KpiDto> getKpiJson() {
        return this.kpiJson;
    }

    public void setKpiJson(List<KpiDto> kpiJson) {
        this.kpiJson = kpiJson;
    }

    public ChartJSONDto getChartJson() {
        return this.chartJson;
    }

    public void setChartJson(ChartJSONDto chartJson) {
        this.chartJson = chartJson;
    }

    public List<ParamDto> getParams() {
        return this.params;
    }

    public void setParams(List<ParamDto> params) {
        this.params = params;
    }

    public Boolean getMkpi() {
        return this.mkpi == null ? false : this.mkpi;
    }

    public void setMkpi(Boolean mkpi) {
        this.mkpi = mkpi;
    }

    public List<KpiDto> getMkpiJson() {
        return this.mkpiJson;
    }

    public void setMkpiJson(List<KpiDto> mkpiJson) {
        this.mkpiJson = mkpiJson;
    }

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTname() {
        return this.tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void validate() {
    }
}
