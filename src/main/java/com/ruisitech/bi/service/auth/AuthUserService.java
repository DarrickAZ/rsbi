//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.auth;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisitech.bi.entity.frame.User;
import com.ruisitech.bi.mapper.frame.UserMapper;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.bi.util.TreeInterface;
import com.ruisitech.bi.util.TreeService;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthUserService {
    @Autowired
    private UserMapper userMapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @Autowired
    private DaoHelper daoHelper;

    public AuthUserService() {
    }

    public List<User> listUsers(String keyword) {
        return this.userMapper.listUsers(keyword, this.sysUser);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void deleteUser(Integer userId) {
        this.daoHelper.execute("delete from " + this.sysUser + ".sc_login_user where user_id = " + userId);
        this.daoHelper.execute("delete from " + this.sysUser + ".user_menu_rela where user_id = " + userId);
        this.daoHelper.execute("delete from " + this.sysUser + ".role_user_rela where user_id = " + userId);
    }

    public String saveUser(User u) {
        int cnt = this.userMapper.userExist(this.sysUser, u.getStaffId());
        if (cnt > 0) {
            return "账号已经存在。";
        } else {
            u.setUserId(this.userMapper.maxUserId(this.sysUser));
            u.setPassword(RSBIUtils.getMD5(u.getPassword().getBytes()));
            this.userMapper.insertuser(u);
            return null;
        }
    }

    public void updateUser(User u) {
        this.userMapper.updateuser(u);
        if (u.getPassword() != null && u.getPassword().length() > 0) {
            u.setPassword(RSBIUtils.getMD5(u.getPassword().getBytes()));
            this.userMapper.modPsd(u);
        }

    }

    public User getUserById(Integer userId) {
        return this.userMapper.getUserById(userId, this.sysUser);
    }

    public Map<String, Object> listUserMenus(Integer userId) {
        List<Map<String, Object>> ls = this.userMapper.listUserMenus(userId, this.sysUser);
        TreeService ser = new TreeService();
        List<Map<String, Object>> ret = ser.createTreeData(ls, new TreeInterface() {
            public void processMap(Map<String, Object> m) {
                String chk2 = m.get("id2") == null ? "" : m.get("id2").toString();
                if (chk2 != null && chk2.length() != 0) {
                    m.put("disabled", true);
                    m.put("checked", true);
                } else {
                    String chk3 = m.get("id3") == null ? "" : m.get("id3").toString();
                    if (chk3 != null && chk3.length() != 0) {
                        m.put("checked", true);
                    } else {
                        m.put("checked", false);
                    }

                    m.put("disabled", false);
                }

                Map<String, Object> attributes = new HashMap();
                m.put("attributes", attributes);
                attributes.put("id2", m.get("id2"));
                attributes.put("id3", m.get("id3"));
                attributes.put("disabled", m.get("disabled"));
            }

            public void processEnd(Map<String, Object> m, boolean hasChild) {
                String chk3 = m.get("id3") == null ? "" : m.get("id3").toString();
                if (hasChild && chk3 != null && chk3.length() > 0) {
                    m.remove("checked");
                }

            }
        });
        Map<String, Object> m = new HashMap();
        m.put("id", "root");
        m.put("text", "系统菜单树");
        m.put("iconCls", "icon-earth");
        m.put("children", ret);
        return m;
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void saveUserMenu(final Integer userId, String menuIds) {
        String delSql = "delete from " + this.sysUser + ".user_menu_rela where user_id = " + userId;
        this.daoHelper.execute(delSql);
        String[] ids = menuIds.split(",");
        String sql = "insert into " + this.sysUser + ".user_menu_rela(user_id, menu_id) values(?,?)";
        String[] var6 = ids;
        int var7 = ids.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            final String tmp = var6[var8];
            if (tmp.length() > 0) {
                this.daoHelper.execute(sql, new PreparedStatementCallback<Object>() {
                    public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        ps.setInt(1, userId);
                        ps.setString(2, tmp);
                        ps.executeUpdate();
                        return null;
                    }
                });
            }
        }

    }
}
