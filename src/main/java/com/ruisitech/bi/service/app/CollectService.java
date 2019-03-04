//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.app;

import com.ruisitech.bi.entity.app.Collect;
import com.ruisitech.bi.mapper.app.CollectMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectService {
    @Autowired
    private CollectMapper mapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public CollectService() {
    }

    public List<Collect> listCollect(Integer userId, String basePath) {
        List<Collect> ls = this.mapper.listCollect(this.sysUser, userId);

        for(int i = 0; i < ls.size(); ++i) {
            Collect m = (Collect)ls.get(i);
            String url = basePath + "app/Report!view.action?rid=" + m.getRid();
            m.setUrl(url);
        }

        return ls;
    }

    public Integer collectExist(Collect collect) {
        return this.mapper.collectExist(collect);
    }

    public void addCollect(Collect collect) {
        this.mapper.addCollect(collect);
    }

    public void delCollect(Collect collect) {
        this.mapper.delCollect(collect);
    }
}
