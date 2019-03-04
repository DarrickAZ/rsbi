//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.common;

public class DSColumn {
    private Integer idx;
    private String name;
    private String type;
    private String dispName;
    private Integer length;
    private Integer scale;
    private String tname;
    private Boolean isshow = true;
    private String expression;

    public DSColumn() {
    }

    public String getName() {
        return this.name != null ? this.name.trim() : this.name;
    }

    public String getType() {
        return this.type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDispName() {
        return this.dispName;
    }

    public void setDispName(String dispName) {
        this.dispName = dispName;
    }

    public String getTname() {
        return this.tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Integer getIdx() {
        return this.idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Boolean getIsshow() {
        return this.isshow;
    }

    public void setIsshow(Boolean isshow) {
        this.isshow = isshow;
    }

    public Integer getLength() {
        return this.length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getScale() {
        return this.scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }
}
