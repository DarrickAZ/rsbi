//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.common.RequestStatus;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.etl.DataSource;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.mapper.etl.EtlTableMetaColMapper;
import com.ruisitech.bi.mapper.etl.EtlTableMetaMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableRegService {
    @Autowired
    private EtlTableMetaMapper mapper;
    @Autowired
    private EtlTableMetaColMapper colMapper;
    @Autowired
    private MetaService metaService;
    private String sysUser = RSBIUtils.getConstant("sysUser");
    private String dbName = RSBIUtils.getConstant("dbName");

    public TableRegService() {
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public Result reg(String tableName) {
        Result ret = new Result();
        if (!"mysql".equals(this.dbName)) {
            tableName = tableName.toUpperCase();
        }

        EtlTableMeta meta = new EtlTableMeta();
        meta.setTableName(tableName);
        Integer cnt = this.mapper.tableExist(meta);
        if (cnt > 0) {
            ret.setResult(RequestStatus.FAIL_FIELD.getStatus());
            ret.setMsg("您选的表已经存在。");
        } else {
            try {
                this.saveOrUpdateTable(tableName, (Integer)null);
                ret.setResult(RequestStatus.SUCCESS.getStatus());
            } catch (Exception var6) {
                ret.setResult(RequestStatus.FAIL_FIELD.getStatus());
                ret.setMsg(var6.getMessage());
            }
        }

        return ret;
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void saveOrUpdateTable(String tableName, Integer tableId) throws Exception {
        EtlTableMeta meta = new EtlTableMeta();
        meta.setTableName(tableName);
        meta.setTableNote(tableName);
        meta.setTableId(tableId);
        meta.setTableDesc("");
        meta.setCrtUser(RSBIUtils.getLoginUserInfo().getUserId());
        meta.setIncome("custom");
        if (tableId == null && meta.getIdType() == 2) {
            meta.setTableId(this.mapper.maxTableId(this.sysUser));
        }

        if (tableId == null) {
            this.mapper.insertMetaTable(meta);
        } else {
            this.mapper.updateMetaTable(meta);
        }

        if (meta.getTableId() == null) {
            meta.setTableId(this.mapper.maxTableId(this.sysUser) - 1);
        }

        if (tableId != null) {
            this.colMapper.delTableColByTableId(meta);
        }

        Integer maxId = null;
        if (meta.getIdType() == 2) {
            maxId = this.colMapper.maxColId(this.sysUser);
        }

        List<DSColumn> cols = this.metaService.queryTableCols("select * from " + tableName, (DataSource)null, false);

        for(int i = 0; i < cols.size(); ++i) {
            DSColumn col = (DSColumn)cols.get(i);
            EtlTableMetaCol mcol = new EtlTableMetaCol();
            mcol.setColName(col.getName());
            mcol.setColType(col.getType());
            mcol.setColLength(col.getLength());
            mcol.setColNote(col.getDispName());
            mcol.setColOrd(col.getIdx());
            mcol.setTableId(meta.getTableId());
            mcol.setColDesc(col.getDispName());
            if (meta.getIdType() == 2) {
                mcol.setColId(maxId);
                maxId = maxId + 1;
            }

            this.colMapper.insertMetaTableCol(mcol);
        }

    }
}
