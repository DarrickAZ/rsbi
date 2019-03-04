//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.model;

import com.ruisitech.bi.entity.etl.DataSource;
import com.ruisitech.bi.entity.model.TableDimension;
import com.ruisitech.bi.mapper.model.TableDimensionMapper;
import com.ruisitech.bi.service.etl.DataSetService;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableDimensionService {
    @Autowired
    private TableDimensionMapper dimMapper;
    @Autowired
    private DataSetService dsService;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public TableDimensionService() {
    }

    public TableDimension findDimensionByAlias(String alias, Integer tid) {
        return this.dimMapper.findDimensionByAlias(this.sysUser, alias, tid);
    }

    public List<TableDimension> getTableDims(Integer tid) {
        return this.dimMapper.getTableDims(this.sysUser, tid);
    }

    public TableDimension queryDimCol(Integer dimId, Integer tid) {
        return this.dimMapper.queryDimCol(this.sysUser, dimId, tid);
    }

    public List<Object> paramFilter(TableDimension d, String keyword) throws Exception {
        String col = d.getCol();
        String key = d.getColkey();
        String name = d.getColtext();
        String dimord = d.getDimord();
        String tname = d.getTname();
        String tsql = d.getTsql();
        String sql = "select distinct " + (key != null && key.length() != 0 ? key : col) + " \"id\", " + (name != null && name.length() != 0 ? name : col) + " \"name\" from ";
        if (tsql != null && tsql.length() > 0) {
            sql = sql + "(" + tsql + ") xxx ";
        } else {
            sql = sql + tname;
        }

        if (keyword != null && keyword.length() > 0) {
            sql = sql + " where " + (name != null && name.length() != 0 ? name : col) + " like '%" + keyword + "%'";
        }

        if (dimord != null && dimord.length() > 0) {
            sql = sql + " order by " + (key != null && key.length() != 0 ? key : col) + " " + dimord;
        }

        List<Object> ret = this.dsService.queryTopN(sql, (DataSource)null, 50);
        ret.remove(0);
        return ret;
    }
}
