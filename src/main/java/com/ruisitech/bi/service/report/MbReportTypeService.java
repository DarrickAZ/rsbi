//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.report;

import com.ruisitech.bi.entity.report.MbReportType;
import com.ruisitech.bi.mapper.report.MbReportTypeMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MbReportTypeService {
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @Autowired
    private MbReportTypeMapper mapper;

    public MbReportTypeService() {
    }

    public List<MbReportType> listcataTree(String income) {
        return this.mapper.listcataTree(this.sysUser, income);
    }

    public void insertType(MbReportType type) {
        this.mapper.insertType(type);
    }

    public void updateType(MbReportType type) {
        this.mapper.updateType(type);
    }

    public void deleleType(Integer id) {
        this.mapper.deleleType(id, this.sysUser);
    }

    public MbReportType getType(Integer id) {
        return this.mapper.getType(id, this.sysUser);
    }

    public Integer cntReport(Integer id) {
        return this.mapper.cntReport(id, this.sysUser);
    }

    public Integer cntMobileReport(Integer id) {
        return this.mapper.cntMobileReport(id, this.sysUser);
    }

    public Integer maxTypeId() {
        return this.mapper.maxTypeId(this.sysUser);
    }
}
