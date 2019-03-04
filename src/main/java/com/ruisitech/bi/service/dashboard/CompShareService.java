//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.dashboard;

import com.ruisitech.bi.entity.dashboard.CompShare;
import com.ruisitech.bi.mapper.dashboard.CompShareMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompShareService {
    @Autowired
    private CompShareMapper mapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public CompShareService() {
    }

    public int deleteByPrimaryKey(String id) {
        return this.mapper.deleteByPrimaryKey(id, this.sysUser);
    }

    public int insert(CompShare record) {
        record.setCrtuser(RSBIUtils.getLoginUserInfo().getUserId());
        return this.mapper.insert(record);
    }

    public CompShare selectByPrimaryKey(String id) {
        return this.mapper.selectByPrimaryKey(id, this.sysUser);
    }

    public int updateByPrimaryKeySelective(CompShare record) {
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    public List<CompShare> list(String keyword) {
        return this.mapper.list(keyword, this.sysUser);
    }
}
