//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.etl;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;

public class UpLoadFile extends BaseEntity {
    private Integer id;
    private Integer userId;
    private String fileType;
    private String fileName;
    private String filePath;
    private Integer cfgid;

    public UpLoadFile() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getCfgid() {
        return this.cfgid;
    }

    public void setCfgid(Integer cfgid) {
        this.cfgid = cfgid;
    }

    public void validate() {
        this.fileType = RSBIUtils.htmlEscape(this.fileType);
        this.fileName = RSBIUtils.htmlEscape(this.fileName);
        this.filePath = RSBIUtils.htmlEscape(this.filePath);
    }
}
