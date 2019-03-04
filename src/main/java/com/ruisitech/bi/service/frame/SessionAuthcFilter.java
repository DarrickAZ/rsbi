//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.frame;

import com.ruisitech.bi.entity.frame.User;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;

public class SessionAuthcFilter extends AdviceFilter {
    @Autowired
    private UserService userService;
    private static final String rsbiTokenStr = "rsbiToken";

    public SessionAuthcFilter() {
    }

    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject us = SecurityUtils.getSubject();
        Session session = us.getSession();
        if (!us.isAuthenticated() && us.isRemembered() && session.getAttribute("session.user.key") == null) {
            Object staffId = us.getPrincipal();
            if (staffId == null) {
                request.getRequestDispatcher("/pages/control/NoLogin.jsp").forward(request, response);
                return false;
            }

            User u = this.userService.getUserByUserId((String)staffId);
            session.setAttribute("session.user.key", u);
        }

        if (!us.isAuthenticated() && !us.isRemembered()) {
            String rsbiToken = request.getParameter("rsbiToken");
            if (rsbiToken == null || rsbiToken.length() == 0) {
                HttpServletRequest req = (HttpServletRequest)request;
                Cookie[] cookies = req.getCookies();

                for(int i = 0; cookies != null && i < cookies.length; ++i) {
                    if ("rsbiToken".equals(cookies[i].getName())) {
                        rsbiToken = cookies[i].getValue();
                        break;
                    }
                }
            }

            if (rsbiToken != null && rsbiToken.length() > 0) {
                String ret = this.userService.shiroSSOLogin(rsbiToken);
                if ("SUC".equals(ret)) {
                    return true;
                }
            }

            request.getRequestDispatcher("/pages/control/NoLogin.jsp").forward(request, response);
            return false;
        } else {
            return true;
        }
    }
}
