//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.dashboard;

import com.ruisitech.bi.entity.dashboard.Dashboard;
import com.ruisitech.bi.mapper.dashboard.DashboardMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired
    private DashboardMapper mapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public DashboardService() {
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.mapper.deleteByPrimaryKey(id, this.sysUser);
    }

    public int insert(Dashboard record) {
        return this.mapper.insert(record);
    }

    public Dashboard selectByPrimaryKey(Integer id) {
        return this.mapper.selectByPrimaryKey(id, this.sysUser);
    }

    public int updateByPrimaryKeySelective(Dashboard record) {
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    public Integer maxDashboardId() {
        return this.mapper.maxDashboardId(this.sysUser);
    }

    public List<Dashboard> list(Dashboard record) {
        return this.mapper.list(record);
    }

    public void setDefDashboard(Integer id, Integer userId) {
        this.mapper.setDefDashboard(id, userId, this.sysUser);
    }

    public Integer getDefDashboard(Integer userId) {
        return this.mapper.getDefDashboard(userId, this.sysUser);
    }
}
