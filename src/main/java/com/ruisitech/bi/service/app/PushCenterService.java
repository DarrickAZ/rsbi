//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.app.AppPush;
import com.ruisitech.bi.mapper.app.AppPushMapper;
import com.ruisitech.bi.service.etl.JobManagerService;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushCenterService {
    @Autowired
    private AppPushMapper mapper;
    @Autowired
    private JobManagerService jobService;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public PushCenterService() {
    }

    public void initPushConfig(ServletContext sct) {
        List<AppPush> ls = this.mapper.listRuningPush(this.sysUser);

        for(int i = 0; i < ls.size(); ++i) {
            AppPush push = (AppPush)ls.get(i);
            String pctx = push.getPageInfo();
            String channel_id = push.getChannel();
            if (channel_id != null && channel_id.length() != 0) {
                Integer push_id = push.getId();
                Integer userId = push.getUserId();
                JSONObject json = (JSONObject)JSON.parse(pctx);
                this.jobService.runPushJob(push_id, channel_id, json, sct, userId);
            }
        }

    }
}
