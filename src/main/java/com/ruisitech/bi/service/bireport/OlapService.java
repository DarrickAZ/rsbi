//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.bireport;

import com.ruisitech.bi.entity.bireport.OlapInfo;
import com.ruisitech.bi.mapper.bireport.OlapMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OlapService {
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @Autowired
    private OlapMapper mapper;

    public OlapService() {
    }

    public OlapInfo getOlap(Integer pageId) {
        return this.mapper.getOlap(pageId, this.sysUser);
    }

    public List<OlapInfo> listreport(String keyword) {
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        return this.mapper.listreport(keyword, userId, this.sysUser);
    }

    public void insertOlap(OlapInfo olap) {
        this.mapper.insertOlap(olap);
    }

    public void updateOlap(OlapInfo olap) {
        this.mapper.updateOlap(olap);
    }

    public void deleteOlap(Integer pageId) {
        this.mapper.deleteOlap(pageId, this.sysUser);
    }

    public void renameOlap(OlapInfo olap) {
        this.mapper.renameOlap(olap);
    }

    public Integer maxOlapId() {
        return this.mapper.maxOlapId(this.sysUser);
    }

    public Integer olapExist(String pageName) {
        return this.mapper.olapExist(pageName, this.sysUser);
    }

    public List<Map<String, Object>> listKpiDesc(Integer cubeId) {
        return this.mapper.listKpiDesc(cubeId, this.sysUser);
    }
}
