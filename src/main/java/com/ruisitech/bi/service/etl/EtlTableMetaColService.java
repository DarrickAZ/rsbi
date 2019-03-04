//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.mapper.etl.EtlTableMetaColMapper;
import com.ruisitech.bi.mapper.etl.EtlTableMetaMapper;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EtlTableMetaColService extends EtlBaseService {
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @Autowired
    private EtlTableMetaColMapper colMapper;
    @Autowired
    private EtlTableMetaMapper mapper;
    @Autowired
    private DaoHelper daoHelper;
    @Autowired
    private TableCacheService cacheService;

    public EtlTableMetaColService() {
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void insertMetaTableCol(EtlTableMetaCol meta, boolean dyna) {
        if (!dyna) {
            EtlTableMeta table = this.mapper.getTable(meta.getTableId(), this.sysUser);
            meta.setTableName(table.getTableName());
            super.addTableColumn(meta, this.daoHelper);
        }

        if (meta.getIdType() == 2) {
            meta.setColId(this.colMapper.maxColId(this.sysUser));
        }

        this.colMapper.insertMetaTableCol(meta);
        this.cacheService.removeTableInfo(meta.getTableId());
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void updateTableCol(EtlTableMetaCol meta, boolean dyna) {
        if (!dyna) {
            EtlTableMeta table = this.mapper.getTable(meta.getTableId(), this.sysUser);
            meta.setTableName(table.getTableName());
            EtlTableMetaCol oldCol = this.getTableColumn(meta.getTableId(), meta.getColId());
            if (!oldCol.getColType().equals(meta.getColType()) || !oldCol.getColLength().equals(meta.getColLength())) {
                super.updateColumn(meta, this.daoHelper);
            }
        } else {
            this.colMapper.updDimExpressByFromCol(meta);
            this.colMapper.updKpiExpressByFromCol(meta);
        }

        this.colMapper.updateTableCol(meta);
        this.cacheService.removeTableInfo(meta.getTableId());
    }

    public void delTableColById(Integer colId) {
        this.colMapper.delTableColById(colId, this.sysUser);
    }

    public List<EtlTableMetaCol> queryTableColumns(Integer tableId, Integer colId) {
        return this.colMapper.queryTableColumns(tableId, colId, this.sysUser);
    }

    public List<EtlTableMetaCol> queryTableColumnsNotExpress(Integer tableId) {
        return this.colMapper.queryTableColumnsNotExpress(tableId, this.sysUser);
    }

    public EtlTableMetaCol getTableColumn(Integer tableId, Integer colId) {
        List<EtlTableMetaCol> ret = this.colMapper.queryTableColumns(tableId, colId, this.sysUser);
        return ret != null && ret.size() != 0 ? (EtlTableMetaCol)ret.get(0) : null;
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void delTableColumn(Integer tableId, Integer colId) {
        EtlTableMetaCol col = this.getTableColumn(tableId, colId);
        if (col.getExpression() == null || col.getExpression().length() == 0) {
            EtlTableMeta table = this.mapper.getTable(tableId, this.sysUser);
            col.setTableName(table.getTableName());
            super.removeTableColumn(col, this.daoHelper);
        }

        this.colMapper.delTableColById(colId, this.sysUser);
        this.cacheService.removeTableInfo(tableId);
    }
}
