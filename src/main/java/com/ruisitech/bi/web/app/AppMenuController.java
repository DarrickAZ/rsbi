//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/app"})
public class AppMenuController {
    public AppMenuController() {
    }

    @RequestMapping({"/Menus!topMenu2.action"})
    @ResponseBody
    public Object topMenu(Integer userId, HttpServletRequest request) {
        List<Map<String, Object>> ls = new ArrayList();
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        Map<String, Object> m0 = new HashMap();
        m0.put("id", 0);
        m0.put("name", "数据填报");
        m0.put("note", "手动录入数据到系统中,表结构需先在PC端定义。");
        m0.put("pic", "resource/img/3g/a6.png");
        m0.put("url", basePath + "app/Datawrite.action");
        ls.add(m0);
        Map<String, Object> m1 = new HashMap();
        m1.put("id", 1);
        m1.put("name", "数据透视");
        m1.put("note", "通过移动端对已经建模的数据进行透视分析。");
        m1.put("pic", "resource/img/3g/a1.png");
        m1.put("url", "");
        ls.add(m1);
        Map<String, Object> m3 = new HashMap();
        m3.put("id", 2);
        m3.put("name", "查询已有");
        m3.put("note", "查询已保存的数据透视表,进行快速访问。");
        m3.put("pic", "resource/img/3g/a3.png");
        m3.put("url", "");
        ls.add(m3);
        Map<String, Object> m2 = new HashMap();
        m2.put("id", 3);
        m2.put("name", "手机报表");
        m2.put("note", "通过手机端查看报表数据,报表需在PC端先创建。");
        m2.put("pic", "resource/img/3g/a4.png");
        m2.put("url", "");
        ls.add(m2);
        Map<String, Object> m5 = new HashMap();
        m5.put("id", 4);
        m5.put("name", "信息推送");
        m5.put("note", "系统每天或每月向用户推送定制的指标数据。");
        m5.put("pic", "resource/img/3g/a5.png");
        m5.put("url", "");
        ls.add(m5);
        Map<String, Object> m6 = new HashMap();
        m6.put("id", 5);
        m6.put("name", "系统帮助");
        m6.put("note", "睿思BI系统介绍，指导您正确使用本系统。");
        m6.put("pic", "resource/img/3g/help.png");
        m6.put("url", basePath + "app/Helper.action");
        ls.add(m6);
        return ls;
    }

    @RequestMapping({"/Helper.action"})
    public String helper() {
        return "app/Helper";
    }
}
