//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.common.PageParam;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.mapper.etl.EtlTableMetaColMapper;
import com.ruisitech.bi.mapper.etl.EtlTableMetaMapper;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.bi.util.SortService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EtlTableMetaService extends EtlBaseService {
    @Autowired
    private EtlTableMetaMapper mapper;
    @Autowired
    private EtlTableMetaColMapper colMapper;
    @Autowired
    private DaoHelper daoHelper;
    @Autowired
    private TableCacheService cacheService;
    private String sysUser = RSBIUtils.getConstant("sysUser");
    private String dubhe = RSBIUtils.getConstant("dubhe");

    public EtlTableMetaService() {
    }

    public Integer tableExist(String tableName) {
        EtlTableMeta meta = new EtlTableMeta();
        meta.setTableName(tableName.toUpperCase());
        return this.mapper.tableExist(meta);
    }

    public List<EtlTableMeta> dwselectTables() {
        return this.mapper.dwselectTables(this.sysUser);
    }

    public EtlTableMeta getTableByTname(String tableName) {
        return this.mapper.getTableByTname(tableName, this.sysUser);
    }

    public EtlTableMeta getTableAll(Integer tableId) {
        EtlTableMeta table = this.mapper.getTable(tableId, this.sysUser);
        List<EtlTableMetaCol> metaCols = this.colMapper.queryTableColumns(tableId, (Integer)null, this.sysUser);
        table.setMetaCols(metaCols);
        return table;
    }

    public EtlTableMeta getTable(Integer tableId) {
        EtlTableMeta table = this.mapper.getTable(tableId, this.sysUser);
        return table;
    }

    public EtlTableMeta getTableOnly(Integer tableId) {
        EtlTableMeta table = this.mapper.getTable(tableId, this.sysUser);
        return table;
    }

    public List<EtlTableMeta> selectTables(String income, PageParam p) {
        List<EtlTableMeta> ret = this.mapper.selectTables(income, this.sysUser);
        if (p.getOrder() != null && p.getSort() != null) {
            SortService sort = new SortService(p.getSort(), p.getOrder());
            Collections.sort(ret, sort);
        }

        return ret;
    }

    public List<EtlTableMeta> selectTables(String income) {
        List<EtlTableMeta> list = null;
        try {
            list = this.mapper.selectTablesFromDubhe(this.dubhe);

            for(EtlTableMeta meta: list){
                if(meta.getTableId()==null && meta.getTbId()!=null){
                    // 查询tableId-tbId 映射表 ，获取table id
                    Integer tableId = mapper.selectTableIdByTabId(meta.getTbId(),this.sysUser);
                    if(tableId==null){
                        mapper.insertTableIdMapping(meta.getTbId(),this.sysUser);
                        tableId = mapper.selectTableIdByTabId(meta.getTbId(),this.sysUser);
                    }
                    meta.setTableId(tableId);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }


    public List<EtlTableMeta> selectByIncomes(List<String> incomes, PageParam p) {
        List<EtlTableMeta> ret = this.mapper.selectByIncomes(incomes, this.sysUser);
        if (p.getOrder() != null && p.getSort() != null) {
            SortService sort = new SortService(p.getSort(), p.getOrder());
            Collections.sort(ret, sort);
        }

        return ret;
    }

    public List<EtlTableMetaCol> queryTableColumns(Integer tableId, boolean hasExpress) {
        try {
            return this.colMapper.queryTableColumnsFromDubhe(tableId, this.dubhe, this.sysUser);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

       // return hasExpress ? this.colMapper.queryTableColumns(tableId, (Integer)null, this.sysUser) : this.colMapper.queryTableColumnsNotExpress(tableId, this.sysUser);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public EtlTableMeta saveTableInfo(EtlTableMeta table) {
        String sql = this.createImpTableSql(table);
        this.daoHelper.execute(sql);
        Integer maxId;
        if (table.getIdType() == 2) {
            maxId = this.mapper.maxTableId(this.sysUser);
            table.setTableId(maxId);
        }

        this.mapper.insertMetaTable(table);
        if (table.getTableId() == null) {
            table.setTableId(this.mapper.maxTableId(this.sysUser) - 1);
        }

        maxId = null;
        if (table.getIdType() == 2) {
            maxId = this.colMapper.maxColId(this.sysUser);
        }

        for(int i = 0; i < table.getCols().size(); ++i) {
            DSColumn col = (DSColumn)table.getCols().get(i);
            EtlTableMetaCol mcol = new EtlTableMetaCol();
            mcol.setColName(col.getName());
            mcol.setColType(col.getType());
            mcol.setColLength(col.getLength());
            mcol.setColScale(col.getScale());
            mcol.setColNote(col.getDispName());
            mcol.setColOrd(col.getIdx());
            mcol.setTableId(table.getTableId());
            mcol.setColDesc(col.getDispName());
            if (table.getIdType() == 2) {
                mcol.setColId(maxId);
                maxId = maxId + 1;
            }

            this.colMapper.insertMetaTableCol(mcol);
        }

        return table;
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void updateTableInfo(EtlTableMeta table) {
        this.mapper.updateMetaTable(table);
        this.cacheService.removeTableInfo(table.getTableId());
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void deleteTable(Integer tableId, boolean dropTable) {
        EtlTableMeta table = this.mapper.getTable(tableId, this.sysUser);
        if (dropTable) {
            super.droptable(table.getTableName(), this.daoHelper, table.getDbName());
        }

        this.mapper.deleteTable(table);
        this.colMapper.delTableColByTableId(table);
    }

    public String getTableSql(Integer tableId) {
        return this.mapper.getTableSql(tableId, this.sysUser);
    }

    public Integer maxTableId(String sysUser) {
        return this.mapper.maxTableId(sysUser);
    }

    public int updateEsEnable(EtlTableMeta table) {
        return this.mapper.updateEsEnable(table);
    }

    public int updateUseEs(EtlTableMeta table) {
        return this.mapper.updateUseEs(table);
    }
}
