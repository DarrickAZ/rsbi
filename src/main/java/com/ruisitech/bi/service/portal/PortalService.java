//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.portal;

import com.ruisitech.bi.entity.portal.Portal;
import com.ruisitech.bi.mapper.portal.PortalMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortalService {
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @Autowired
    private PortalMapper mapper;

    public PortalService() {
    }

    public List<Portal> listPortal(Integer cataId) {
        return this.mapper.listPortal(cataId, this.sysUser);
    }

    public String getPortalCfg(String pageId) {
        return this.mapper.getPortalCfg(pageId, this.sysUser);
    }

    public List<Portal> list3g(Integer cataId) {
        return this.mapper.list3g(cataId, this.sysUser);
    }

    public void insertPortal(Portal portal) {
        this.mapper.insertPortal(portal);
    }

    public void deletePortal(String pageId) {
        this.mapper.deletePortal(pageId, this.sysUser);
    }

    public void updatePortal(Portal portal) {
        this.mapper.updatePortal(portal);
    }

    public Portal getPortal(String pageId) {
        return this.mapper.getPortal(pageId, this.sysUser);
    }

    public void renamePortal(Portal portal) {
        this.mapper.renamePortal(portal);
    }

    public List<Map<String, Object>> listAppReport(Integer userId, Integer cataId) {
        return this.mapper.listAppReport(userId, cataId, this.sysUser);
    }
}
