//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.etl;

import com.ruisitech.bi.entity.etl.UpLoadFile;
import org.apache.ibatis.annotations.Param;

public interface UpLoadFileMapper {
    void insertupload(UpLoadFile var1);

    Integer maxfileId(@Param("sysUser") String var1);

    UpLoadFile queryupload(UpLoadFile var1);

    UpLoadFile curUpload(UpLoadFile var1);
}
