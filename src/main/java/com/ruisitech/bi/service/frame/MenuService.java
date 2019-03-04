//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.frame;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.frame.Menu;
import com.ruisitech.bi.mapper.frame.MenuMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
    @Autowired
    private MenuMapper mapper;
    @Autowired
    private DaoHelper daoHelper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public MenuService() {
    }

    public List<Menu> listUserMenus(Integer userId) {
        String sysUser = RSBIUtils.getConstant("sysUser");
        List<Menu> menuList = this.mapper.listUserMenus(userId, sysUser);
        List<Menu> roots = this.findMenuChildren(0, menuList);

        for(int i = 0; i < roots.size(); ++i) {
            Menu root = (Menu)roots.get(i);
            Integer id = root.getMenuId();
            List<Menu> subList = this.findMenuChildren(id, menuList);
            root.setChildren(subList);

            for(int j = 0; j < subList.size(); ++j) {
                Menu sub = (Menu)subList.get(j);
                Integer subId = sub.getMenuId();
                sub.setChildren(this.findMenuChildren(subId, menuList));
            }
        }

        return roots;
    }

    private List<Menu> findMenuChildren(Integer pid, List<Menu> menuList) {
        List<Menu> ret = new ArrayList();

        for(int i = 0; i < menuList.size(); ++i) {
            Menu m = (Menu)menuList.get(i);
            Integer value = m.getMenuPid();
            if (value != null && value.equals(pid)) {
                ret.add(m);
            }
        }

        return ret;
    }

    public Menu getById(Integer menuId) {
        return this.mapper.getById(menuId, this.sysUser);
    }

    public List<Map<String, Object>> listMenuByPid(Integer pid) {
        return this.mapper.listMenuByPid(pid, this.sysUser);
    }

    public void saveMenu(final Menu m) {
        String idSql = "select max(menu_id) from " + this.sysUser + ".sc_menu";
        Integer maxId = (Integer)this.daoHelper.queryForObject(idSql, Integer.class);
        final int mid = maxId + 1;
        final Integer pid = m.getMenuPid();
        String sql = "insert into " + this.sysUser + ".sc_menu(menu_id,menu_pid,menu_name,menu_desc,menu_date,menu_order,menu_url, urls, avatar) values(?,?,?,?,";
        sql = sql + (new BaseEntity()).getDateString();
        sql = sql + ",?,?,?,?)";
        this.daoHelper.execute(sql, new PreparedStatementCallback<Object>() {
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(1, mid);
                ps.setString(3, m.getMenuName());
                ps.setString(4, m.getMenuDesc());
                ps.setInt(2, pid);
                ps.setInt(5, m.getMenuOrder());
                ps.setString(6, m.getMenuUrl());
                ps.setString(8, m.getAvatar());
                ps.setString(7, m.getUrls());
                ps.executeUpdate();
                return null;
            }
        });
        m.setMenuId(mid);
    }

    public void updateMenu(final Menu menu) {
        String dt = menu.getDateString();
        String sql = "update " + this.sysUser + ".sc_menu set menu_name=?,menu_desc=?,menu_date=" + dt + ",menu_order=?,menu_url=?,avatar=? where menu_id=?";
        this.daoHelper.execute(sql, new PreparedStatementCallback<Object>() {
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(3, menu.getMenuOrder());
                ps.setString(2, menu.getMenuDesc());
                ps.setString(1, menu.getMenuName());
                ps.setString(4, menu.getMenuUrl());
                ps.setString(5, menu.getAvatar());
                ps.setInt(6, menu.getMenuId());
                ps.executeUpdate();
                return null;
            }
        });
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public Result deleteMenu(Integer id) {
        Result r = new Result();
        String chkSql = "select count(*) from " + this.sysUser + ".sc_menu where menu_pid = " + id;
        BigDecimal ct = (BigDecimal)this.daoHelper.queryForObject(chkSql, BigDecimal.class);
        if (ct.intValue() > 0) {
            r.setResult(0);
            r.setMsg("该菜单下可能含有子菜单，不能删除。");
            return r;
        } else {
            String sql = "delete from " + this.sysUser + ".sc_menu where menu_id = " + id;
            this.daoHelper.execute(sql);
            this.daoHelper.execute("delete from " + this.sysUser + ".role_menu_rela where menu_id = " + id);
            this.daoHelper.execute("delete from " + this.sysUser + ".user_menu_rela where menu_id = " + id);
            r.setResult(1);
            return r;
        }
    }
}
