//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.ext.service;

import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.frame.User;
import com.ruisitech.bi.service.bireport.TableCacheService;
import org.springframework.beans.factory.annotation.Autowired;

public class DataControlImpl implements DataControlInterface {
    @Autowired
    private TableCacheService cacheService;

    public DataControlImpl() {
    }

    public String process(User u, String tname) {
        if (u == null) {
            return "";
        } else {
            String deptCode = u.getDeptCode();
            TableInfoVO tvo = this.cacheService.getTableInfoByTname(tname);
            return tvo.getDataControlCol() != null && tvo.getDataControlCol().length() > 0 ? " and " + tvo.getDataControlCol() + " = '" + deptCode + "'" : "";
        }
    }
}
