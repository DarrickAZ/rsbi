//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.frame;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.common.PageParam;
import com.ruisitech.bi.entity.frame.User;
import com.ruisitech.bi.mapper.frame.UserMapper;
import com.ruisitech.bi.service.app.VerifyCodeService;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @Autowired
    private UserMapper mapper;
    @Autowired
    private VerifyCodeService vcService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${sso.url.userInfo.bytoken}")
    private String userInfoByTokenUrl;
    @Value("${sso.url.userInfo.bystaff}")
    private String userInfoByStaffUrl;
    @Value("${sso.url.userList}")
    private String userListUrl;
    private static Logger log = Logger.getLogger(UserService.class);

    public UserService() {
    }

    public User getUserByStaffId(String staffId) {
        return this.mapper.getUserByStaffId(staffId, this.sysUser);
    }

    public User getUserBySSO(String staffId) {
        Map<String, Object> urlVariables = new HashMap();
        urlVariables.put("staffId", staffId);
        JSONObject user = (JSONObject)this.restTemplate.getForEntity(this.userInfoByStaffUrl, JSONObject.class, urlVariables).getBody();
        if (user != null && !user.isEmpty()) {
            User u = (User)JSON.toJavaObject(user, User.class);
            return u;
        } else {
            return null;
        }
    }

    public User getUserByUserId(String staffId) {
        return this.userInfoByStaffUrl != null && this.userInfoByStaffUrl.length() > 0 ? this.getUserBySSO(staffId) : this.mapper.getUserByStaffId(staffId, this.sysUser);
    }

    public void updateLogDateAndCnt(Integer userId) {
        User u = new User();
        u.setUserId(userId);
        this.mapper.updateLogDateAndCnt(u);
    }

    public void modPsd(User u) {
        this.mapper.modPsd(u);
    }

    public String checkPsd(Integer userId) {
        return this.mapper.checkPsd(userId, this.sysUser);
    }

    public Map<String, Object> appUserinfo(Integer userId) {
        return this.mapper.appUserinfo(userId, this.sysUser);
    }

    public String shiroSSOLogin(String rsbiToken) {
        RsbiUsernamePasswordToken token = new RsbiUsernamePasswordToken(rsbiToken, "XXXXXX", (String)null);
        token.setRememberMe(false);
        token.setChkpsd(false);

        try {
            SecurityUtils.getSubject().login(token);
            return "SUC";
        } catch (Exception var4) {
            log.error("登录出错。", var4);
            return "内部错误，请重试！";
        }
    }

    public String shiroLogin(String userName, String password) {
        RsbiUsernamePasswordToken token = new RsbiUsernamePasswordToken(userName, password, (String)null);
        token.setRememberMe(true);

        try {
            SecurityUtils.getSubject().login(token);
            return "SUC";
        } catch (UnknownAccountException var7) {
            return "账号不存在！";
        } catch (IncorrectCredentialsException var8) {
            return "密码错误！";
        } catch (AuthenticationException var9) {
            String ret = null;
            Object t = var9;

            while(true) {
                if (((Throwable)t).getCause() == null) {
                    ret = ((Throwable)t).getMessage();
                    break;
                }

                t = ((Throwable)t).getCause();
                if (((Throwable)t).getCause() == null) {
                    ret = ((Throwable)t).getMessage();
                    break;
                }
            }

            return ret;
        } catch (Exception var10) {
            log.error("登录出错。", var10);
            return "内部错误，请重试！";
        }
    }

    public int updateChannel(String channel) {
        User u = new User();
        u.setChannel(channel);
        u.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
        return this.mapper.updateChannel(u);
    }

    public Map<String, Object> reg(User user, String yzm, String guid, HttpServletRequest req) {
        Map<String, Object> ret = new HashMap();
        int cnt = this.mapper.userExist(this.sysUser, user.getStaffId());
        if (cnt > 0) {
            ret.put("state", false);
            ret.put("msg", "您注册的账号已经存在。");
            return ret;
        } else {
            String validateCode = (String)this.vcService.getYzmMap().get(guid);
            if (validateCode != null && validateCode.equalsIgnoreCase(yzm)) {
                req.getSession().removeAttribute("validateCode");
                this.vcService.getYzmMap().remove(guid);
                user.setUserId(this.mapper.maxUserId(this.sysUser));
                user.setState(1);
                this.mapper.insertuser(user);
                ret.put("state", true);
                return ret;
            } else {
                ret.put("state", false);
                ret.put("msg", "验证码错误。");
                return ret;
            }
        }
    }

    public User getUserInfoByToken(String token) {
        try {
            String url = this.userInfoByTokenUrl + "?token=" + token;
            ResponseEntity<String> resp = this.restTemplate.getForEntity(url, String.class, new Object[0]);
            String info = (String)resp.getBody();
            JSONObject json = (JSONObject)JSON.parse(info);
            User user = (User)JSONObject.toJavaObject(json, User.class);
            return user;
        } catch (Exception var7) {
            log.error("SSO获取用户信息失败", var7);
            return null;
        }
    }

    public List<User> listSSOUsers(PageParam page, String keyword) {
        try {
            String url = this.userListUrl;
            Map<String, Object> request = new HashMap();
            request.put("page", page.getPage());
            request.put("rows", page.getRows());
            request.put("keyword", keyword);
            ResponseEntity<JSONArray> users = this.restTemplate.getForEntity(url, JSONArray.class, request);
            JSONArray usersEntity = (JSONArray)users.getBody();
            List<User> ret = new ArrayList();

            for(int i = 0; i < usersEntity.size(); ++i) {
                User u = (User)JSON.toJavaObject(usersEntity.getJSONObject(i), User.class);
                ret.add(u);
            }

            return ret;
        } catch (Exception var10) {
            log.error("SSO获取用户信息失败", var10);
            return null;
        }
    }

    public boolean isSSOUserList() {
        return this.userListUrl != null && this.userListUrl.length() != 0;
    }

    public void updateErrCnt(User user) {
        this.mapper.updateErrCnt(user);
    }

    public void dealLockUsers() {
        List<User> users = this.mapper.selectLockUsers(this.sysUser);
        Iterator var2 = users.iterator();

        while(var2.hasNext()) {
            User u = (User)var2.next();
            if (u.getErrDate() != null) {
                Date now = new Date();
                if (now.getTime() - u.getErrDate().getTime() > 600000L) {
                    this.mapper.clearErrCnt(u);
                }
            }
        }

    }

    public void clearErrCnt(User u) {
        this.mapper.clearErrCnt(u);
    }
}
