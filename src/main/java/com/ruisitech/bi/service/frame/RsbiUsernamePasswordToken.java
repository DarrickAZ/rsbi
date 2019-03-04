//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.frame;

import org.apache.shiro.authc.UsernamePasswordToken;

public class RsbiUsernamePasswordToken extends UsernamePasswordToken {
    private static final long serialVersionUID = -3245428649330999429L;
    private Boolean chkpsd;

    public RsbiUsernamePasswordToken(String username, String password, String host) {
        super(username, password, host);
    }

    public Boolean getChkpsd() {
        return this.chkpsd;
    }

    public void setChkpsd(Boolean chkpsd) {
        this.chkpsd = chkpsd;
    }
}
