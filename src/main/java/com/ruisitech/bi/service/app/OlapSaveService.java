//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.app;

import com.ruisitech.bi.entity.app.OlapSave;
import com.ruisitech.bi.mapper.app.OlapSaveMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OlapSaveService {
    @Autowired
    private OlapSaveMapper mapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public OlapSaveService() {
    }

    public int save(OlapSave vo) {
        if (vo.getId() == null) {
            vo.setId(this.mapper.maxId(this.sysUser));
            JSONObject page = JSONObject.fromObject(vo.getPageInfo());
            String id = String.valueOf(vo.getId());
            page.put("id", id);
            vo.setPageInfo(page.toString());
        }

        return this.mapper.save(vo);
    }

    public int update(OlapSave vo) {
        return this.mapper.update(vo);
    }

    public List<OlapSave> list() {
        return this.mapper.list(this.sysUser);
    }

    public int delete(Integer id) {
        return this.mapper.delete(this.sysUser, id);
    }

    public String getById(Integer id) {
        return this.mapper.getById(this.sysUser, id);
    }
}
