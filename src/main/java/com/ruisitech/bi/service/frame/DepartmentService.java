//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.frame;

import com.ruisitech.bi.entity.frame.Department;
import com.ruisitech.bi.mapper.frame.DepartmentMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @Autowired
    private DepartmentMapper mapper;

    public DepartmentService() {
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.mapper.deleteByPrimaryKey(this.sysUser, id);
    }

    public int insertSelective(Department record) {
        record.setId(this.mapper.maxDeptId(this.sysUser));
        return this.mapper.insertSelective(record);
    }

    public Department selectByPrimaryKey(Integer id) {
        return this.mapper.selectByPrimaryKey(this.sysUser, id);
    }

    public int updateByPrimaryKeySelective(Department record) {
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    public List<Department> list() {
        return this.mapper.list(this.sysUser);
    }

    public List<Department> tree(Integer pid) {
        return this.mapper.tree(this.sysUser, pid);
    }
}
