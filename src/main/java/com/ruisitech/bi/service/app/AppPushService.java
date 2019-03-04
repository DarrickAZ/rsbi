//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.app.AppPush;
import com.ruisitech.bi.entity.frame.User;
import com.ruisitech.bi.mapper.app.AppPushMapper;
import com.ruisitech.bi.service.etl.JobManagerService;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class AppPushService {
    @Autowired
    private AppPushMapper mapper;
    @Autowired
    private JobManagerService service;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public AppPushService() {
    }

    public List<Map<String, Object>> pushlist(Integer userId) {
        return this.mapper.pushlist(this.sysUser, userId);
    }

    public String getPushCfg(Integer id) {
        AppPush vo = new AppPush();
        vo.setId(id);
        vo.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        return this.mapper.getPushCfg(vo);
    }

    public List<Map<String, Object>> pushSubject(String subjectType) {
        return this.mapper.pushSubject(this.sysUser, subjectType);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public int addPushCfg(AppPush vo) {
        if (vo.getId() == null) {
            vo.setId(this.mapper.maxPushId(this.sysUser));
        }

        int ret = this.mapper.addPushCfg(vo);
        User u = RSBIUtils.getLoginUserInfo();
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        JSONObject json = (JSONObject)JSON.parse(this.mapper.getPushCfg(vo));
        this.service.runPushJob(vo.getId(), u.getChannel(), json, request.getServletContext(), u.getUserId());
        return ret;
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public int updatePushCfg(AppPush vo) {
        this.service.stopPushJob(vo.getId());
        User u = RSBIUtils.getLoginUserInfo();
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        JSONObject json = (JSONObject)JSON.parse(vo.getPageInfo());
        this.service.runPushJob(vo.getId(), u.getChannel(), json, request.getServletContext(), u.getUserId());
        return this.mapper.updatePushCfg(vo);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public int delPush(Integer id) {
        AppPush vo = new AppPush();
        vo.setId(id);
        vo.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        this.service.stopPushJob(id);
        return this.mapper.delPush(vo);
    }

    public int maxPushId() {
        return this.mapper.maxPushId(this.sysUser);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public int stopPush(Integer id) {
        AppPush vo = new AppPush();
        vo.setId(id);
        vo.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        this.service.stopPushJob(id);
        return this.mapper.stopPush(vo);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public int startPush(Integer id) {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        String channel = RSBIUtils.getLoginUserInfo().getChannel();
        AppPush vo = new AppPush();
        vo.setUserId(userId);
        vo.setId(id);
        JSONObject json = (JSONObject)JSON.parse(this.mapper.getPushCfg(vo));
        this.service.runPushJob(id, channel, json, request.getServletContext(), userId);
        return this.mapper.startPush(vo);
    }
}
