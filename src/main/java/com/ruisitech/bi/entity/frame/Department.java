//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.frame;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;

public class Department extends BaseEntity {
    private Integer id;
    private String text;
    private String deptCode;
    private String deptName;
    private String deptNote;
    private Integer pid;
    private String state;

    public Department() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getDeptNote() {
        return this.deptNote;
    }

    public void setDeptNote(String deptNote) {
        this.deptNote = deptNote == null ? null : deptNote.trim();
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void validate() {
        this.deptCode = RSBIUtils.htmlEscape(this.deptCode);
        this.deptName = RSBIUtils.htmlEscape(this.deptName);
        this.deptNote = RSBIUtils.htmlEscape(this.deptNote);
    }
}
