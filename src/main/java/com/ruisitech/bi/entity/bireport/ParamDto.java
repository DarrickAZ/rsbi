//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.bireport;

import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.common.BaseEntity;

public class ParamDto extends BaseEntity {
    private String type;
    private String colname;
    private String alias;
    private String valType;
    private String dateformat;
    private String tname;
    private String st;
    private String end;
    private String vals;
    private String valStrs;
    private String valDesc;
    private Integer id;
    private Integer tid;
    private String colDesc;
    private String tableName;
    private String dimord;
    private String tableColKey;
    private String tableColName;
    private String grouptype;
    private String name;
    private Integer filtertype;
    private String fromCol;
    private String expression;
    private String datasetid;
    private JSONObject filter;
    private String dispName;

    public ParamDto() {
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

    public String getValType() {
        return this.valType;
    }

    public void setValType(String valType) {
        this.valType = valType;
    }

    public String getDateformat() {
        return this.dateformat;
    }

    public void setDateformat(String dateformat) {
        this.dateformat = dateformat;
    }

    public String getTname() {
        return this.tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getSt() {
        return this.st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getVals() {
        return this.vals;
    }

    public void setVals(String vals) {
        this.vals = vals;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValDesc() {
        return this.valDesc;
    }

    public void setValDesc(String valDesc) {
        this.valDesc = valDesc;
    }

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getColDesc() {
        return this.colDesc;
    }

    public void setColDesc(String colDesc) {
        this.colDesc = colDesc;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDimord() {
        return this.dimord;
    }

    public void setDimord(String dimord) {
        this.dimord = dimord;
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

    public String getValStrs() {
        return this.valStrs;
    }

    public void setValStrs(String valStrs) {
        this.valStrs = valStrs;
    }

    public String getGrouptype() {
        return this.grouptype;
    }

    public void setGrouptype(String grouptype) {
        this.grouptype = grouptype;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFiltertype() {
        return this.filtertype;
    }

    public void setFiltertype(Integer filtertype) {
        this.filtertype = filtertype;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getDatasetid() {
        return this.datasetid;
    }

    public void setDatasetid(String datasetid) {
        this.datasetid = datasetid;
    }

    public JSONObject getFilter() {
        return this.filter;
    }

    public void setFilter(JSONObject filter) {
        this.filter = filter;
    }

    public String getDispName() {
        return this.dispName;
    }

    public void setDispName(String dispName) {
        this.dispName = dispName;
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
