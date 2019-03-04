//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.bireport;

import com.ruisitech.bi.entity.common.BaseEntity;

public class KpiFilterDto extends BaseEntity {
    private Integer kpi;
    private String filterType;
    private Double val1;
    private Double val2;

    public KpiFilterDto() {
    }

    public String getFilterType() {
        return this.filterType;
    }

    public Integer getKpi() {
        return this.kpi;
    }

    public void setKpi(Integer kpi) {
        this.kpi = kpi;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public Double getVal1() {
        return this.val1;
    }

    public Double getVal2() {
        return this.val2;
    }

    public void setVal1(Double val1) {
        this.val1 = val1;
    }

    public void setVal2(Double val2) {
        this.val2 = val2;
    }

    public void validate() {
    }
}
