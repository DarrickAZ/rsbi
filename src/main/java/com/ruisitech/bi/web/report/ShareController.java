//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.report;

import com.ruisitech.bi.entity.report.MailSenderInfo;
import com.ruisitech.bi.entity.report.ShareUrl;
import com.ruisitech.bi.service.report.MailService;
import com.ruisitech.bi.service.report.ShareUrlService;
import com.ruisitech.bi.util.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/report"})
public class ShareController extends BaseController {
    private static Logger log = Logger.getLogger(ShareController.class);
    @Autowired
    private MailService service;
    @Autowired
    private ShareUrlService urlService;

    public ShareController() {
    }

    @RequestMapping(
        value = {"/sendMail.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object sendMail(MailSenderInfo info, HttpServletRequest req, HttpServletResponse res) {
        try {
            this.service.shareByMail(info, req, res);
            return super.buildSucces();
        } catch (Exception var5) {
            log.error("发送邮件出错了。", var5);
            return super.buildError(var5.getMessage());
        }
    }

    @RequestMapping(
        value = {"/copyUrl.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object copyUrl(ShareUrl dto, HttpServletRequest request) {
        this.urlService.saveShareUrl(dto);
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/report/shareView.action?token=" + dto.getToken();
        return super.buildSucces(basePath);
    }
}
