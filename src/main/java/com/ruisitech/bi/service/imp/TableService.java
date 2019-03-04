//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.imp;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.mapper.etl.EtlTableMetaColMapper;
import com.ruisitech.bi.mapper.etl.EtlTableMetaMapper;
import com.ruisitech.bi.service.etl.EtlBaseService;
import com.ruisitech.bi.util.RSBIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableService extends EtlBaseService {
    @Autowired
    private EtlTableMetaMapper mapper;
    @Autowired
    private EtlTableMetaColMapper colMapper;
    @Autowired
    private DaoHelper daoHelper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public TableService() {
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void saveTable(EtlTableMeta table) {
        String sql = super.createDwTableSql(table);
        this.daoHelper.execute(sql);
        table.setCrtUser(RSBIUtils.getLoginUserInfo().getUserId());
        table.setIncome("dw");
        if (table.getIdType() == 2) {
            table.setTableId(this.mapper.maxTableId(this.sysUser));
        }

        this.mapper.insertMetaTable(table);
        if (table.getTableId() == null) {
            table.setTableId(this.mapper.maxTableId(this.sysUser) - 1);
        }

        for(int i = 0; i < table.getMetaCols().size(); ++i) {
            EtlTableMetaCol col = (EtlTableMetaCol)table.getMetaCols().get(i);
            col.setTableId(table.getTableId());
            col.setColOrd(i);
            col.setColName(col.getColName().trim());
            if (col.getIdType() == 2) {
                col.setColId(this.colMapper.maxColId(this.sysUser));
            }

            this.colMapper.insertMetaTableCol(col);
        }

    }
}
