//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisitech.bi.entity.etl.Config;
import com.ruisitech.bi.mapper.etl.ConfigMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtlConfigService {
    @Autowired
    private ConfigMapper configMapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public EtlConfigService() {
    }

    public String getCfg(Integer cfgid) {
        return this.configMapper.getConfig(cfgid, this.sysUser);
    }

    public void insertCfg(Config config) {
        config.setCfgid(this.configMapper.maxCfgid(this.sysUser));
        this.configMapper.insertCfg(config);
    }

    public List<Config> listCfg() {
        return this.configMapper.listConfig(this.sysUser);
    }

    public void updateCfgInfo(Config config) {
        this.configMapper.updateCfgInfo(config);
    }

    public void delCfg(Config config) {
        this.configMapper.delCfg(config);
    }
}
