//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.frame;

import com.ruisitech.bi.entity.frame.User;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;

public class AppSessionAuthcFilter extends AdviceFilter {
    @Autowired
    private UserService userService;

    public AppSessionAuthcFilter() {
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject us = SecurityUtils.getSubject();
        Session session = us.getSession();
        if (!us.isAuthenticated() && us.isRemembered() && session.getAttribute("session.user.key") == null) {
            Object staffId = us.getPrincipal();
            if (staffId == null) {
                request.getRequestDispatcher("/pages/control/NoLogin_app.jsp").forward(request, response);
                return false;
            }

            User u = this.userService.getUserByStaffId(staffId.toString());
            session.setAttribute("session.user.key", u);
        }

        if (!us.isAuthenticated() && !us.isRemembered()) {
            request.getRequestDispatcher("/pages/control/NoLogin_app.jsp").forward(request, response);
            return false;
        } else {
            return true;
        }
    }
}
