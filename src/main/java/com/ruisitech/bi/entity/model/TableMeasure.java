//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.model;

public class TableMeasure extends TableMetaCol {
    private Integer kpiId;
    private String name;
    private String kpinote;
    private String unit;
    private String fmt;
    private String aggre;
    private Integer calcKpi;
    private Integer tid;
    private String kpiGroup;
    private String kpiGroupName;

    public TableMeasure() {
    }

    public Integer getKpiId() {
        return this.kpiId;
    }

    public void setKpiId(Integer kpiId) {
        this.kpiId = kpiId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKpinote() {
        return this.kpinote;
    }

    public void setKpinote(String kpinote) {
        this.kpinote = kpinote;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFmt() {
        return this.fmt;
    }

    public void setFmt(String fmt) {
        this.fmt = fmt;
    }

    public String getAggre() {
        return this.aggre;
    }

    public String getAggreCol() {
        return "count(distinct)".equals(this.aggre) ? "count(distinct " + super.getCol() + ")" : this.aggre + "(" + super.getCol() + ")";
    }

    public void setAggre(String aggre) {
        this.aggre = aggre;
    }

    public Integer getCalcKpi() {
        return this.calcKpi;
    }

    public void setCalcKpi(Integer calcKpi) {
        this.calcKpi = calcKpi;
    }

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getKpiGroup() {
        return this.kpiGroup;
    }

    public void setKpiGroup(String kpiGroup) {
        this.kpiGroup = kpiGroup;
    }

    public String getKpiGroupName() {
        return this.kpiGroupName;
    }

    public void setKpiGroupName(String kpiGroupName) {
        this.kpiGroupName = kpiGroupName;
    }
}
