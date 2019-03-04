//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruisitech.bi.entity.app.AppPush;
import com.ruisitech.bi.entity.common.PageParam;
import com.ruisitech.bi.service.app.AppPushMsgService;
import com.ruisitech.bi.service.app.AppPushService;
import com.ruisitech.bi.service.frame.UserService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/app"})
public class AppPushContorller extends BaseController {
    @Autowired
    private AppPushService service;
    @Autowired
    private UserService uService;
    @Autowired
    private AppPushMsgService msgService;

    public AppPushContorller() {
    }

    @RequestMapping({"/Push!listMsg2.action"})
    @ResponseBody
    public Object listMsg2(Integer page) {
        if (page == null) {
            page = 0;
        }

        PageParam pageParam = new PageParam();
        pageParam.setPage(page + 1);
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Map<String, Object>> ls = this.msgService.listMsg(userId);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo(ls);
        Map<String, Object> ret = new HashMap();
        ret.put("rows", pageInfo.getList());
        ret.put("hasNext", pageInfo.isHasNextPage());
        return JSONObject.fromObject(ret).toString();
    }

    @RequestMapping({"/Push!list.action"})
    @ResponseBody
    public Object list() {
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        return this.service.pushlist(userId);
    }

    @RequestMapping({"/Push!listMsg.action"})
    @ResponseBody
    public Object listMsg() throws IOException {
        List<Map<String, Object>> ls = new ArrayList();
        return ls;
    }

    @RequestMapping({"/Push!updateChennel.action"})
    @ResponseBody
    public Object updateChennel(String channel) {
        this.uService.updateChannel(channel);
        return super.buildSucces();
    }

    @RequestMapping({"/Push!get.action"})
    @ResponseBody
    public Object get(Integer id) {
        return this.service.getPushCfg(id);
    }

    @RequestMapping({"/Push!listPushSubject.action"})
    @ResponseBody
    public Object listPushSubject(String subjectType) {
        return this.service.pushSubject(subjectType);
    }

    @RequestMapping({"/Push!save.action"})
    @ResponseBody
    public Object save(AppPush vo) {
        vo.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        this.service.addPushCfg(vo);
        return vo.getId();
    }

    @RequestMapping({"/Push!update.action"})
    @ResponseBody
    public Object update(AppPush vo) {
        vo.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        this.service.updatePushCfg(vo);
        return super.buildSucces();
    }

    @RequestMapping({"/Push!del.action"})
    @ResponseBody
    public Object delete(Integer id) {
        this.service.delPush(id);
        return super.buildSucces();
    }

    @RequestMapping({"/Push!stopPush.action"})
    @ResponseBody
    public Object stopPush(Integer id) {
        this.service.stopPush(id);
        return super.buildSucces();
    }

    @RequestMapping({"/Push!startPush.action"})
    @ResponseBody
    public Object startPush(Integer id) {
        this.service.startPush(id);
        return super.buildSucces();
    }

    @RequestMapping({"/Push!getMsg.action"})
    @ResponseBody
    public Object getMsg(Integer id) {
        return this.msgService.getMsg(id);
    }

    @RequestMapping({"/Push!msg2Read.action"})
    @ResponseBody
    public Object msg2Read(Integer id) {
        this.msgService.msg2Read(id);
        return super.buildSucces();
    }

    @RequestMapping({"/Push!delMsg.action"})
    @ResponseBody
    public Object delMsg(Integer id) {
        this.msgService.deletePush(id);
        return super.buildSucces();
    }

    @RequestMapping({"/Push!delAll.action"})
    @ResponseBody
    public Object delAll() {
        this.msgService.deleteAllPush();
        return super.buildSucces();
    }
}
