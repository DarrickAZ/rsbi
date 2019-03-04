//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.model;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import java.util.Map;

public class TableMeta extends BaseEntity {
    private Integer tid;
    private String tName;
    private String tDesc;
    private String tNote;
    private Integer typeId;
    private String typeName;
    private List<TableDimension> dims;
    private List<TableMeasure> kpis;
    private String key;
    private List<Map<String, Object>> delObj;
    private Integer userId;

    public TableMeta() {
    }

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String gettName() {
        return this.tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettDesc() {
        return this.tDesc;
    }

    public void settDesc(String tDesc) {
        this.tDesc = tDesc;
    }

    public Integer getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public List<TableDimension> getDims() {
        return this.dims;
    }

    public void setDims(List<TableDimension> dims) {
        this.dims = dims;
    }

    public List<TableMeasure> getKpis() {
        return this.kpis;
    }

    public void setKpis(List<TableMeasure> kpis) {
        this.kpis = kpis;
    }

    public String gettNote() {
        return this.tNote;
    }

    public void settNote(String tNote) {
        this.tNote = tNote;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Map<String, Object>> getDelObj() {
        return this.delObj;
    }

    public void setDelObj(List<Map<String, Object>> delObj) {
        this.delObj = delObj;
    }

    public void validate() {
        this.tName = RSBIUtils.htmlEscape(this.tName);
        this.tDesc = RSBIUtils.htmlEscape(this.tDesc);
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
