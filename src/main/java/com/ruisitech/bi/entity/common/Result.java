//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Result {
    private Integer result;
    private String msg;
    @JsonInclude(Include.NON_NULL)
    private Object rows;
    private long total;

    public Result() {
    }

    public Result(Integer result, String msg, Object rows) {
        this.result = result;
        this.msg = msg;
        this.rows = rows;
    }

    public Result(Integer result, String msg, Object rows, long total) {
        this.result = result;
        this.msg = msg;
        this.rows = rows;
        this.total = total;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getRows() {
        return this.rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
