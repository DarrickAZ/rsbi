//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.detail;

import com.ruisitech.bi.entity.detail.OlapDetail;
import com.ruisitech.bi.mapper.detail.OlapDetailMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OlapDetailService {
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @Autowired
    private OlapDetailMapper mapper;

    public OlapDetailService() {
    }

    public int deleteByPrimaryKey(Integer pageId) {
        return this.mapper.deleteByPrimaryKey(this.sysUser, pageId);
    }

    public void saveOrUpdate(OlapDetail record) {
        if (record.getPageId() == null) {
            record.setUserid(RSBIUtils.getLoginUserInfo().getUserId());
            record.setPageId(this.mapper.queryMaxId(this.sysUser));
            JSONObject p = JSONObject.fromObject(record.getPageinfo());
            p.put("id", record.getPageId());
            record.setPageinfo(p.toString());
            this.mapper.insert(record);
        } else {
            this.mapper.updateByPrimaryKey(record);
        }

    }

    public OlapDetail selectByPrimaryKey(Integer pageId) {
        return this.mapper.selectByPrimaryKey(this.sysUser, pageId);
    }

    public List<OlapDetail> listOlapDetail(String keyword) {
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        return this.mapper.listOlapDetail(this.sysUser, userId, keyword);
    }

    public int updateByPrimaryKey(OlapDetail record) {
        return this.mapper.updateByPrimaryKey(record);
    }

    public Integer queryMaxId() {
        return this.mapper.queryMaxId(this.sysUser);
    }
}
