//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.report;

import com.ruisitech.bi.entity.report.ShareUrl;
import com.ruisitech.bi.mapper.report.ShareUrlMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareUrlService {
    @Autowired
    private ShareUrlMapper mapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public ShareUrlService() {
    }

    public void saveShareUrl(ShareUrl vo) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        vo.setCrtUser(userId);
        vo.setToken(token);
        this.mapper.insert(vo);
    }

    public ShareUrl getByToken(String token) {
        return this.mapper.selectByPrimaryKey(this.sysUser, token);
    }
}
