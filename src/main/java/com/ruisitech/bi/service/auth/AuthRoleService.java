//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.auth;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisitech.bi.entity.frame.Role;
import com.ruisitech.bi.mapper.frame.RoleMapper;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.bi.util.TreeInterface;
import com.ruisitech.bi.util.TreeService;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthRoleService {
    @Autowired
    private RoleMapper mapper;
    @Autowired
    private DaoHelper daoHelper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public AuthRoleService() {
    }

    public Map<String, Object> listRoleMenus(Integer roleId) {
        List<Map<String, Object>> menus = this.mapper.listRoleMenus(roleId, this.sysUser);
        TreeService tree = new TreeService();
        List<Map<String, Object>> ret = tree.createTreeData(menus, new TreeInterface() {
            public void processMap(Map<String, Object> m) {
                Object id2 = m.get("id2");
                if (id2 != null) {
                    m.put("checked", true);
                }

            }

            public void processEnd(Map<String, Object> m, boolean hasChild) {
                if (hasChild) {
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

    public List<Map<String, Object>> roledata(Integer roleId) {
        List<Map<String, Object>> datas = this.mapper.roledata(roleId, this.sysUser);
        TreeService tree = new TreeService();
        List<Map<String, Object>> ret = tree.createTreeData(datas, new TreeInterface() {
            public void processMap(Map<String, Object> m) {
                Object seldata = m.get("seldata");
                if (seldata != null) {
                    m.put("checked", true);
                }

            }

            public void processEnd(Map<String, Object> m, boolean hasChild) {
            }
        });
        return ret;
    }

    public List<Role> list(String keyword) {
        return this.mapper.list(this.sysUser, keyword);
    }

    public List<Role> listUserRole(Integer userId) {
        return this.mapper.listUserRole(this.sysUser, userId);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void addUserRole(String[] roleIds, Integer userId) {
        this.daoHelper.execute("delete from " + this.sysUser + ".role_user_rela where user_id = " + userId);

        for(int i = 0; roleIds != null && i < roleIds.length; ++i) {
            String roleId = roleIds[i];
            this.daoHelper.execute("insert into " + this.sysUser + ".role_user_rela(user_id,role_id) values(" + userId + "," + roleId + ")");
        }

    }

    public void saveRole(final Role role) {
        final int idType = role.getIdType();
        String dateKey = role.getDateString();
        String sql = "insert into " + this.sysUser + ".sc_role(role_name,role_desc,create_date,create_user, ord" + (idType == 2 ? ",role_id" : "") + ") values(?,?," + dateKey + ",?,?" + (idType == 2 ? ",?" : "") + ")";
        this.daoHelper.execute(sql, new PreparedStatementCallback<Object>() {
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, role.getRoleName());
                ps.setString(2, role.getRoleDesc());
                ps.setString(3, RSBIUtils.getLoginUserInfo().getLoginName());
                ps.setInt(4, role.getOrd());
                if (idType == 2) {
                    int maxid = AuthRoleService.this.daoHelper.queryForInt("select case WHEN max(role_id) is null then 1 else  max(role_id) + 1 end id from " + AuthRoleService.this.sysUser + ".sc_role");
                    ps.setInt(5, maxid);
                }

                ps.executeUpdate();
                return null;
            }
        });
    }

    public void updateRole(final Role role) {
        String sql = "update " + this.sysUser + ".sc_role set role_name = ?,role_desc = ?, ord=? where role_id = ?";
        this.daoHelper.execute(sql, new PreparedStatementCallback<Object>() {
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(4, role.getRoleId());
                ps.setString(1, role.getRoleName());
                ps.setString(2, role.getRoleDesc());
                ps.setInt(3, role.getOrd());
                ps.executeUpdate();
                return null;
            }
        });
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void deleteRole(Integer id) {
        String sql = "delete from " + this.sysUser + ".sc_role where role_id = " + id;
        this.daoHelper.execute(sql);
        this.daoHelper.execute("delete from " + this.sysUser + ".role_menu_rela where role_id = " + id);
        this.daoHelper.execute("delete from " + this.sysUser + ".role_user_rela where role_id = " + id);
    }

    public Role getRole(Integer id) {
        return this.mapper.getById(this.sysUser, id);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void roleMenu(String menuIds, final Integer roleId) {
        String delSql = "delete from " + this.sysUser + ".role_menu_rela where role_id = " + roleId;
        this.daoHelper.execute(delSql);
        String[] ids = menuIds.split(",");
        String sql = "insert into " + this.sysUser + ".role_menu_rela(role_id, menu_id) values(?,?)";
        String[] var6 = ids;
        int var7 = ids.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            final String tmp = var6[var8];
            if (tmp.length() > 0) {
                this.daoHelper.execute(sql, new PreparedStatementCallback<Object>() {
                    public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        ps.setInt(1, roleId);
                        ps.setString(2, tmp);
                        ps.executeUpdate();
                        return null;
                    }
                });
            }
        }

    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void roleDataSave(final Integer roleId, HttpServletRequest req) {
        String delsql = "delete from " + this.sysUser + ".role_data_rela where role_id = " + roleId;
        this.daoHelper.execute(delsql);
        List<String> datas = new ArrayList();
        Enumeration enumer = req.getParameterNames();

        while(enumer.hasMoreElements()) {
            String key = (String)enumer.nextElement();
            if (key.startsWith("view")) {
                String[] ks = key.split("@");
                datas.add(ks[1]);
            }
        }

        Iterator var9 = datas.iterator();

        while(var9.hasNext()) {
            final String data = (String)var9.next();
            String sql = "insert into " + this.sysUser + ".role_data_rela(role_id, data_id) values(?,?)";
            this.daoHelper.execute(sql, new PreparedStatementCallback<Object>() {
                public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                    ps.setInt(1, roleId);
                    ps.setInt(2, Integer.parseInt(data));
                    ps.executeUpdate();
                    return null;
                }
            });
        }

    }
}
