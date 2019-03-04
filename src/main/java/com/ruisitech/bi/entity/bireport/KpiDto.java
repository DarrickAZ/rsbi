//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.bireport;

import com.ruisitech.bi.entity.common.BaseEntity;
import java.util.Map;

public class KpiDto extends BaseEntity {
    private String aggre;
    private String col_name;
    private String fmt;
    private String alias;
    private String kpi_name;
    private String ydispName;
    private String tname;
    private String descKey;
    private Integer rate;
    private String unit;
    private Integer kpi_id;
    private String sort;
    private String order;
    private Integer min;
    private Integer max;
    private Integer calc;
    private KpiFilterDto filter;
    private Map<String, Object> style;
    private Map<String, Object> warning;
    private String compute;
    private String funcname;
    private String code;
    private Boolean mergeData;
    private Integer tid;
    private Integer tfontsize;
    private String tfontcolor;
    private String expression;
    private String valType;
    private String dyna;
    private String fromCol;

    public KpiDto() {
    }

    public String getAggre() {
        return this.aggre;
    }

    public void setAggre(String aggre) {
        this.aggre = aggre;
    }

    public String getFmt() {
        return this.fmt;
    }

    public void setFmt(String fmt) {
        this.fmt = fmt;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getYdispName() {
        return this.ydispName;
    }

    public void setYdispName(String ydispName) {
        this.ydispName = ydispName;
    }

    public String getTname() {
        return this.tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getDescKey() {
        return this.descKey;
    }

    public void setDescKey(String descKey) {
        this.descKey = descKey;
    }

    public Integer getRate() {
        return this.rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getCalc() {
        return this.calc;
    }

    public void setCalc(Integer calc) {
        this.calc = calc;
    }

    public KpiFilterDto getFilter() {
        return this.filter;
    }

    public void setFilter(KpiFilterDto filter) {
        this.filter = filter;
    }

    public Map<String, Object> getStyle() {
        return this.style;
    }

    public void setStyle(Map<String, Object> style) {
        this.style = style;
    }

    public Map<String, Object> getWarning() {
        return this.warning;
    }

    public void setWarning(Map<String, Object> warning) {
        this.warning = warning;
    }

    public String getCompute() {
        return this.compute;
    }

    public void setCompute(String compute) {
        this.compute = compute;
    }

    public String getFuncname() {
        return this.funcname;
    }

    public void setFuncname(String funcname) {
        this.funcname = funcname;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getMergeData() {
        return this.mergeData;
    }

    public void setMergeData(Boolean mergeData) {
        this.mergeData = mergeData;
    }

    public Integer getMax() {
        return this.max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return this.min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public String getKpi_name() {
        return this.kpi_name;
    }

    public void setKpi_name(String kpi_name) {
        this.kpi_name = kpi_name;
    }

    public Integer getKpi_id() {
        return this.kpi_id;
    }

    public void setKpi_id(Integer kpi_id) {
        this.kpi_id = kpi_id;
    }

    public String getCol_name() {
        return this.col_name;
    }

    public void setCol_name(String col_name) {
        this.col_name = col_name;
    }

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getTfontsize() {
        return this.tfontsize;
    }

    public void setTfontsize(Integer tfontsize) {
        this.tfontsize = tfontsize;
    }

    public String getTfontcolor() {
        return this.tfontcolor;
    }

    public void setTfontcolor(String tfontcolor) {
        this.tfontcolor = tfontcolor;
    }

    public String getDyna() {
        return this.dyna;
    }

    public void setDyna(String dyna) {
        this.dyna = dyna;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getValType() {
        return this.valType;
    }

    public void setValType(String valType) {
        this.valType = valType;
    }

    public String getFromCol() {
        return this.fromCol;
    }

    public void setFromCol(String fromCol) {
        this.fromCol = fromCol;
    }

    public void validate() {
    }
}
