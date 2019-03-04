//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.bireport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruisitech.bi.entity.common.BaseEntity;

public class DimDto extends BaseEntity {
    private Integer id;
    private String type;
    private String colname;
    private String alias;
    private String vals;
    private String valDesc;
    private String issum;
    private Integer tid;
    private String tname;
    private Integer calc;
    private String tableName;
    private String tableColKey;
    private String tableColName;
    private String dimord;
    private String ordcol;
    private String dimdesc;
    private String valType;
    private String dimpos;
    private String pos;
    private String dateformat;
    private String grouptype;
    private String groupname;
    private String iscas;
    private Integer top;
    private String topType;
    private String aggre;
    private Integer filtertype;
    private String startmt;
    private String endmt;
    private String startdt;
    private String enddt;
    private String xdispName;
    private String tickInterval;
    private String routeXaxisLable;
    private Boolean endlvl;
    private String dyna;
    private String expression;
    private String fromCol;
    @JsonIgnore
    private QueryDayDto day;
    @JsonIgnore
    private QueryMonthDto month;

    public DimDto() {
    }

    public String getOrdcol() {
        return this.ordcol;
    }

    public void setOrdcol(String ordcol) {
        this.ordcol = ordcol;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColname() {
        return this.colname;
    }

    public void setColname(String colname) {
        this.colname = colname;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getVals() {
        return this.vals;
    }

    public void setVals(String vals) {
        this.vals = vals;
    }

    public String getValDesc() {
        return this.valDesc;
    }

    public void setValDesc(String valDesc) {
        this.valDesc = valDesc;
    }

    public String getIssum() {
        return this.issum;
    }

    public void setIssum(String issum) {
        this.issum = issum;
    }

    public String getTname() {
        return this.tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Integer getCalc() {
        return this.calc;
    }

    public void setCalc(Integer calc) {
        this.calc = calc;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableColKey() {
        return this.tableColKey;
    }

    public void setTableColKey(String tableColKey) {
        this.tableColKey = tableColKey;
    }

    public String getTableColName() {
        return this.tableColName;
    }

    public void setTableColName(String tableColName) {
        this.tableColName = tableColName;
    }

    public String getDimord() {
        return this.dimord;
    }

    public void setDimord(String dimord) {
        this.dimord = dimord;
    }

    public String getDimdesc() {
        return this.dimdesc;
    }

    public void setDimdesc(String dimdesc) {
        this.dimdesc = dimdesc;
    }

    public String getValType() {
        return this.valType;
    }

    public void setValType(String valType) {
        this.valType = valType;
    }

    public String getDimpos() {
        return this.dimpos;
    }

    public void setDimpos(String dimpos) {
        this.dimpos = dimpos;
    }

    public String getPos() {
        return this.pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getDateformat() {
        return this.dateformat;
    }

    public void setDateformat(String dateformat) {
        this.dateformat = dateformat;
    }

    public String getGrouptype() {
        return this.grouptype;
    }

    public void setGrouptype(String grouptype) {
        this.grouptype = grouptype;
    }

    public String getIscas() {
        return this.iscas;
    }

    public void setIscas(String iscas) {
        this.iscas = iscas;
    }

    public QueryDayDto getDay() {
        if (this.day == null && this.startdt != null && this.startdt.length() > 0 && this.enddt != null && this.enddt.length() > 0) {
            this.day = new QueryDayDto();
            this.day.setStartDay(this.startdt);
            this.day.setEndDay(this.enddt);
        }

        return this.day;
    }

    public void setDay(QueryDayDto day) {
        this.day = day;
    }

    public QueryMonthDto getMonth() {
        if (this.month == null && this.startmt != null && this.startmt.length() > 0 && this.endmt != null && this.endmt.length() > 0) {
            this.month = new QueryMonthDto();
            this.month.setStartMonth(this.startmt);
            this.month.setEndMonth(this.endmt);
        }

        return this.month;
    }

    public void setMonth(QueryMonthDto month) {
        this.month = month;
    }

    public Integer getTop() {
        return this.top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public String getTopType() {
        return this.topType;
    }

    public void setTopType(String topType) {
        this.topType = topType;
    }

    public String getAggre() {
        return this.aggre;
    }

    public void setAggre(String aggre) {
        this.aggre = aggre;
    }

    public Integer getFiltertype() {
        return this.filtertype;
    }

    public void setFiltertype(Integer filtertype) {
        this.filtertype = filtertype;
    }

    public String getStartmt() {
        return this.startmt;
    }

    public void setStartmt(String startmt) {
        this.startmt = startmt;
    }

    public String getEndmt() {
        return this.endmt;
    }

    public void setEndmt(String endmt) {
        this.endmt = endmt;
    }

    public String getStartdt() {
        return this.startdt;
    }

    public void setStartdt(String startdt) {
        this.startdt = startdt;
    }

    public String getEnddt() {
        return this.enddt;
    }

    public void setEnddt(String enddt) {
        this.enddt = enddt;
    }

    public String getXdispName() {
        return this.xdispName;
    }

    public void setXdispName(String xdispName) {
        this.xdispName = xdispName;
    }

    public String getTickInterval() {
        return this.tickInterval;
    }

    public void setTickInterval(String tickInterval) {
        this.tickInterval = tickInterval;
    }

    public String getRouteXaxisLable() {
        return this.routeXaxisLable;
    }

    public void setRouteXaxisLable(String routeXaxisLable) {
        this.routeXaxisLable = routeXaxisLable;
    }

    public Boolean getEndlvl() {
        return this.endlvl;
    }

    public void setEndlvl(Boolean endlvl) {
        this.endlvl = endlvl;
    }

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
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

    public String getFromCol() {
        return this.fromCol;
    }

    public void setFromCol(String fromCol) {
        this.fromCol = fromCol;
    }

    public void validate() {
    }
}
