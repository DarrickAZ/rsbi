//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.frame;

import com.ruisitech.bi.entity.report.ShareUrl;
import com.ruisitech.bi.service.report.ShareUrlService;
import java.util.Date;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;

public class ShareUrlSessionAuthcFilter extends AdviceFilter {
    @Autowired
    private ShareUrlService urlService;

    public ShareUrlSessionAuthcFilter() {
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject us = SecurityUtils.getSubject();
        String token = request.getParameter("token");
        ShareUrl shareUrl = this.urlService.getByToken(token);
        if (shareUrl == null) {
            request.setAttribute("ext.mv.err", "找不到报表文件。");
            request.getRequestDispatcher("/pages/control/Error.jsp").forward(request, response);
            return false;
        } else {
            Date crtdate = shareUrl.getCrtdate();
            Date now = new Date();
            long between = (now.getTime() - crtdate.getTime()) / 3600000L;
            if (shareUrl.getYxq() != -1 && between > (long)shareUrl.getYxq()) {
                request.setAttribute("ext.mv.err", "报表已过有效期。");
                request.getRequestDispatcher("/pages/control/Error.jsp").forward(request, response);
                return false;
            } else {
                request.setAttribute("surl", shareUrl);
                if (shareUrl.getIslogin() == 1 && !us.isAuthenticated() && !us.isRemembered()) {
                    HttpServletResponse res = (HttpServletResponse)response;
                    res.sendRedirect("../Login.action?backurl=report/shareView.action?token=" + token);
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
}
