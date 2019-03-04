//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.frame;

import com.ruisitech.bi.entity.frame.User;
import com.ruisitech.bi.util.RSBIUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ShiroDbRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    public static final String SESSION_USER_KEY = "session.user.key";
    @Value("${sso.url.userInfo.bystaff}")
    private String userInfoByStaffUrl;

    public ShiroDbRealm() {
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        RsbiUsernamePasswordToken tk = (RsbiUsernamePasswordToken)authcToken;
        User userLogin = this.tokenToUser(tk);
        User ui = null;
        if (tk.getChkpsd() != null && !tk.getChkpsd()) {
            ui = this.userService.getUserInfoByToken(tk.getUsername());
            if (ui == null || ui.getUserId() == null) {
                throw new UnknownAccountException();
            }

            if (ui.getState() == 0) {
                throw new AuthenticationException("账号已经被停用!");
            }
        } else {
            if (this.userInfoByStaffUrl != null && this.userInfoByStaffUrl.length() > 0) {
                ui = this.userService.getUserBySSO(userLogin.getStaffId());
                if (ui == null || ui.getUserId() == null) {
                    throw new UnknownAccountException();
                }

                ui.setPassword(RSBIUtils.getMD5(ui.getPassword().getBytes()));
            } else {
                ui = this.userService.getUserByStaffId(userLogin.getStaffId());
            }

            if (ui == null) {
                throw new UnknownAccountException();
            }

            if (ui.getPassword() == null) {
                throw new AuthenticationException("账号未获取到密码信息，不能进行登录验证!");
            }

            if (ui.getErrCnt() != null && ui.getErrCnt() >= 5) {
                throw new AuthenticationException("密码错误次数超过5次，账号被锁定10分钟!");
            }

            if (!ui.getPassword().equals(RSBIUtils.getMD5(userLogin.getPassword().getBytes()))) {
                this.userService.updateErrCnt(ui);
                throw new IncorrectCredentialsException();
            }

            if (ui.getState() == 0) {
                throw new AuthenticationException("账号已经被停用!");
            }
        }

        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("session.user.key", ui);
        this.userService.clearErrCnt(ui);
        String realmName = this.getName();
        Object principal = authcToken.getPrincipal();
        return new SimpleAuthenticationInfo(principal, userLogin.getPassword(), realmName);
    }

    private User tokenToUser(UsernamePasswordToken authcToken) {
        User user = new User();
        user.setStaffId(authcToken.getUsername());
        user.setPassword(authcToken.getPassword() == null ? null : String.valueOf(authcToken.getPassword()));
        return user;
    }

    public UserService getUserService() {
        return this.userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
