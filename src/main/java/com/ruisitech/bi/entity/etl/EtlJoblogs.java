//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.etl;

import com.ruisitech.bi.entity.common.BaseEntity;
import java.util.Date;

public class EtlJoblogs extends BaseEntity {
    private Integer jobId;
    private String period;
    private String jobResult;
    private Date exedate;
    private Integer costtime;
    private Integer rowcnt;
    private Integer companyId;
    private Integer jobState;

    public EtlJoblogs() {
    }

    public Integer getJobId() {
        return this.jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getJobResult() {
        return this.jobResult;
    }

    public void setJobResult(String jobResult) {
        this.jobResult = jobResult == null ? null : jobResult.trim();
    }

    public Date getExedate() {
        return this.exedate;
    }

    public void setExedate(Date exedate) {
        this.exedate = exedate;
    }

    public Integer getCosttime() {
        return this.costtime;
    }

    public void setCosttime(Integer costtime) {
        this.costtime = costtime;
    }

    public Integer getRowcnt() {
        return this.rowcnt;
    }

    public void setRowcnt(Integer rowcnt) {
        this.rowcnt = rowcnt;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getJobState() {
        return this.jobState;
    }

    public void setJobState(Integer jobState) {
        this.jobState = jobState;
    }

    public void validate() {
    }
}
