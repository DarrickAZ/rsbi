//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisitech.bi.entity.common.RequestStatus;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.etl.Config;
import com.ruisitech.bi.entity.etl.UpLoadFile;
import com.ruisitech.bi.mapper.etl.ConfigMapper;
import com.ruisitech.bi.mapper.etl.UpLoadFileMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.io.File;
import java.util.Iterator;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class UpLoadFileService {
    @Autowired
    private UpLoadFileMapper mapper;
    @Autowired
    private ConfigMapper configMapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public UpLoadFileService() {
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public Result upLoadFile(MultipartHttpServletRequest multiRequest, String type, Integer cfgid) {
        Result ret = new Result();
        Iterator iter = multiRequest.getFileNames();

        while(iter.hasNext()) {
            MultipartFile file = multiRequest.getFile(((String)iter.next()).toString());
            if (file != null) {
                String filename = file.getOriginalFilename();
                String extName = filename.substring(filename.lastIndexOf("."));
                String id = UUID.randomUUID().toString().replace("-", "");
                filename = id + extName;
                String path = RSBIUtils.getUploadFilePath() + filename;

                try {
                    file.transferTo(new File(path));
                    UpLoadFile f = new UpLoadFile();
                    f.setUserId(RSBIUtils.getLoginUserInfo().getUserId());
                    f.setFileName(file.getOriginalFilename());
                    f.setFilePath(filename);
                    f.setFileType(type);
                    if (f.getIdType() == 2) {
                        f.setId(this.mapper.maxfileId(this.sysUser));
                    }

                    this.mapper.insertupload(f);
                    if (f.getIdType() == 1) {
                        f.setId(this.mapper.maxfileId(this.sysUser) - 1);
                    }

                    if (cfgid != null) {
                        Config cfg = new Config();
                        cfg.setCfgid(cfgid);
                        cfg.setFileId(f.getId());
                        this.configMapper.updateConfig(cfg);
                    }
                } catch (Exception var13) {
                    ret.setResult(RequestStatus.FAIL_FIELD.getStatus());
                    ret.setMsg(var13.getMessage());
                    var13.printStackTrace();
                }
            }
        }

        ret.setResult(RequestStatus.SUCCESS.getStatus());
        ret.setMsg("文件上传成功。");
        return ret;
    }

    public UpLoadFile queryupload(UpLoadFile file) {
        return this.mapper.queryupload(file);
    }

    public UpLoadFile curUpload(UpLoadFile file) {
        return this.mapper.curUpload(file);
    }
}
