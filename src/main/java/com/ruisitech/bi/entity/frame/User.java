//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.frame;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.io.Serializable;
import java.util.Date;

public final class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6096757156465671644L;
    private Integer userId;
    private String staffId;
    private Date loginDate;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date regDate;
    private Integer logCnt;
    private String loginName;
    private String password;
    private String gender;
    private String mobilePhone;
    private String email;
    private String officeTel;
    private Integer state;
    private String channel;
    private Integer deptId;
    private String deptCode;
    private String deptName;
    private Integer errCnt;
    private Date errDate;

    public User() {
    }

    public String getStaffId() {
        return this.staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOfficeTel() {
        return this.officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String toString() {
        return "id = " + this.userId + ", name = " + this.loginName;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLogCnt() {
        return this.logCnt;
    }

    public void setLogCnt(Integer logCnt) {
        this.logCnt = logCnt;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getDeptId() {
        return this.deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptCode() {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void validate() {
        this.loginName = RSBIUtils.htmlEscape(this.loginName);
        this.staffId = RSBIUtils.htmlEscape(this.staffId);
    }

    public Date getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getRegDate() {
        return this.regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Integer getErrCnt() {
        return this.errCnt;
    }

    public void setErrCnt(Integer errCnt) {
        this.errCnt = errCnt;
    }

    public Date getErrDate() {
        return this.errDate;
    }

    public void setErrDate(Date errDate) {
        this.errDate = errDate;
    }
}
