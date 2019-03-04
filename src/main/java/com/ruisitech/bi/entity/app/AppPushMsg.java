//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.app;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;

public class AppPushMsg extends BaseEntity {
    private Integer pushId;
    private Integer userId;
    private String dataDate;
    private String title;
    private String msg;
    private String channel;
    private Date crtdate;
    private Integer state;
    private String pushType;

    public AppPushMsg() {
    }

    public Integer getPushId() {
        return this.pushId;
    }

    public void setPushId(Integer pushId) {
        this.pushId = pushId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDataDate() {
        return this.dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getCrtdate() {
        return this.crtdate;
    }

    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPushType() {
        return this.pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public void validate() {
        this.channel = RSBIUtils.htmlEscape(this.channel);
        this.dataDate = RSBIUtils.htmlEscape(this.dataDate);
        this.title = RSBIUtils.htmlEscape(this.title);
        this.msg = RSBIUtils.htmlEscape(this.msg);
        this.pushType = RSBIUtils.htmlEscape(this.pushType);
    }
}
