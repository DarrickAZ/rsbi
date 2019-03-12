//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.model;

import com.ruisitech.bi.entity.model.SubjectType;
import com.ruisitech.bi.mapper.model.SubjectTypeMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectTypeService {
    @Autowired
    private SubjectTypeMapper mapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");
    private String dubhe = RSBIUtils.getConstant("dubhe");

    public SubjectTypeService() {
    }

    public void deleteByPrimaryKey(Integer dsId) {
        this.mapper.deleteByPrimaryKey(this.sysUser, dsId);
    }

    public int cntTables(Integer typeId) {
        return this.mapper.cntTables(this.sysUser, typeId);
    }

    public void insert(SubjectType record) {
        if (record.getIdType() == 2) {
            record.setDsId(this.mapper.maxTypeid(this.sysUser));
        }

        this.mapper.insert(record);
    }

    public SubjectType selectByPrimaryKey(Integer dsId) {
        return this.mapper.selectByPrimaryKey(this.sysUser, dsId);
    }

    public void updateByPrimaryKey(SubjectType record) {
        this.mapper.updateByPrimaryKey(record);
    }

    public List<Map<String, Object>> selectByTree() {
        return this.mapper.selectByTree(this.sysUser);
    }

    public List<SubjectType> list() {
        return this.mapper.list(this.sysUser);
    }
}
