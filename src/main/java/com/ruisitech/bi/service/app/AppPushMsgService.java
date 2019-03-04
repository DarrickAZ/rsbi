//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.app;

import com.ruisitech.bi.entity.app.AppPushMsg;
import com.ruisitech.bi.mapper.app.AppPushMsgMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppPushMsgService {
    @Autowired
    private AppPushMsgMapper mapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public AppPushMsgService() {
    }

    public int addPushMsg(AppPushMsg vo) {
        return this.mapper.addPushMsg(vo);
    }

    public Map<String, Object> getPushDateDim(Map<String, Object> p) {
        return this.mapper.getPushDateDim(p);
    }

    public List<Map<String, Object>> listMsg(Integer userId) {
        return this.mapper.listMsg(this.sysUser, userId);
    }

    public String getMsg(Integer id) {
        AppPushMsg vo = new AppPushMsg();
        vo.setPushId(id);
        vo.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        return this.mapper.getMsg(vo);
    }

    public int msg2Read(Integer id) {
        AppPushMsg vo = new AppPushMsg();
        vo.setPushId(id);
        vo.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        return this.mapper.msg2Read(vo);
    }

    public int deletePush(Integer id) {
        AppPushMsg vo = new AppPushMsg();
        vo.setPushId(id);
        vo.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        return this.mapper.deletePush(vo);
    }

    public int deleteAllPush() {
        AppPushMsg vo = new AppPushMsg();
        vo.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        return this.mapper.deletePush(vo);
    }
}
