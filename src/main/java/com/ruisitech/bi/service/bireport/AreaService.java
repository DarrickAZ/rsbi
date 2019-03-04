//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.bireport;

import com.ruisitech.bi.entity.bireport.Area;
import com.ruisitech.bi.mapper.bireport.AreaMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaService {
    @Autowired
    private AreaMapper mapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public AreaService() {
    }

    public List<Area> listProvs() {
        return this.mapper.listProvs(this.sysUser);
    }

    public Area getProvByName(String name) {
        return this.mapper.getProvByName(this.sysUser, name);
    }
}
