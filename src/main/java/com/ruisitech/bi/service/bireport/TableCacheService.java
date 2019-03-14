//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.bireport;

import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.mapper.bireport.OlapMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableCacheService {
    @Autowired
    private OlapMapper mapper;
    private String sysUser = RSBIUtils.getConstant("sysUser");
    private String dubhe = RSBIUtils.getConstant("dubhe");
    private Map<Integer, TableInfoVO> tableInfos = Collections.synchronizedMap(new HashMap());

    public TableCacheService() {
    }

    public synchronized TableInfoVO getTableInfo(Integer tid) {
        TableInfoVO ret = (TableInfoVO)this.tableInfos.get(tid);
        if (ret == null) {
            TableInfoVO tinfo = this.mapper.getQueryTable(tid, this.sysUser,this.dubhe);
            if (tinfo == null) {
                throw new ExtRuntimeException("无法找到数据表，表ID：" + tid + "。");
            }

            tinfo.setCreateTime(new Date());
            List<EtlTableMetaCol> cols = this.mapper.getQueryTableCols(tid, this.sysUser);
            tinfo.setCols(cols);
            ret = tinfo;
            this.tableInfos.put(tid, tinfo);
        }

        return ret;
    }

    public synchronized TableInfoVO getTableInfoByTname(String tname) {
        TableInfoVO ret = null;
        Iterator var3 = this.tableInfos.entrySet().iterator();

        while(var3.hasNext()) {
            Entry<Integer, TableInfoVO> tabs = (Entry)var3.next();
            if (((TableInfoVO)tabs.getValue()).getTname().equals(tname)) {
                ret = (TableInfoVO)tabs.getValue();
            }
        }

        return ret;
    }

    public synchronized void removeTableInfo(Integer tid) {
        this.tableInfos.remove(tid);
    }

    public synchronized void removeOutTimeCache() {
        Date now = new Date();
        List<Integer> removeKeys = new ArrayList();
        Iterator var3 = this.tableInfos.entrySet().iterator();

        while(var3.hasNext()) {
            Entry<Integer, TableInfoVO> tabs = (Entry)var3.next();
            TableInfoVO tvo = (TableInfoVO)tabs.getValue();
            if (Math.abs(now.getTime() - tvo.getCreateTime().getTime()) >= 86400000L) {
                removeKeys.add(tabs.getKey());
            }
        }

        var3 = removeKeys.iterator();

        while(var3.hasNext()) {
            Integer key = (Integer)var3.next();
            this.tableInfos.remove(key);
        }

    }
}
