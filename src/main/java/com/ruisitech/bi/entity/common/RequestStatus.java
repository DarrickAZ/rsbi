//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.common;

public enum RequestStatus {
    SUCCESS(1),
    FAIL_FIELD(0);

    private Integer status;

    private RequestStatus(int status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }
}
