//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.imp;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisitech.bi.entity.common.PageParam;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.entity.imp.DataWriteColDto;
import com.ruisitech.bi.entity.imp.DataWriteDto;
import com.ruisitech.bi.mapper.etl.EtlTableMetaColMapper;
import com.ruisitech.bi.service.etl.DataLoaderService;
import com.ruisitech.bi.service.etl.DataSourceService;
import com.ruisitech.bi.service.etl.EtlBaseService;
import com.ruisitech.bi.service.etl.EtlTableMetaService;
import com.ruisitech.bi.util.RSBIUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;

@Service
public class DataWriteService extends EtlBaseService {
    @Autowired
    private DaoHelper daoHelper;
    @Autowired
    private EtlTableMetaService metaService;
    @Autowired
    private EtlTableMetaColMapper colMapper;
    @Autowired
    private DataSourceService dsService;
    @Autowired
    private DataLoaderService dlService;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public DataWriteService() {
    }

    public List<Map<String, Object>> loadData(EtlTableMeta table, PageParam pageParam) throws ExtConfigException {
        String cntsql = "select count(1) cnt from " + table.getTableName();
        int total = this.daoHelper.queryForInt(cntsql);
        pageParam.setTotal(total);
        List<EtlTableMetaCol> cols = this.colMapper.queryTableColumnsNotExpress(table.getTableId(), this.sysUser);
        StringBuffer tcols = new StringBuffer();

        for(int i = 0; i < cols.size(); ++i) {
            EtlTableMetaCol m = (EtlTableMetaCol)cols.get(i);
            String name = m.getColName();
            tcols.append(name);
            tcols.append(" as ");
            tcols.append(" \"" + name + "\" ");
            tcols.append(",");
        }

        String sql = "select " + tcols + "tmp_user_id" + " as \"" + "tmp_user_id" + "\" ," + "tmp_data_id" + " as \"" + "tmp_data_id" + "\" from " + table.getTableName() + " order by " + "tmp_data_id" + " desc";
        PageInfo page = new PageInfo();
        if (pageParam.getPage() != null) {
            page.setCurtpage((long)(pageParam.getPage() - 1));
        }

        if (pageParam.getRows() != null) {
            page.setPagesize(pageParam.getRows());
        }

        List<Map<String, Object>> ls = DaoUtils.calPage(sql, page, this.daoHelper);
        pageParam.setTotal((int)page.getAllsize());
        return ls;
    }

    public List<Map<String, Object>> comboData(Integer tableId, Integer colId) {
        EtlTableMetaCol col = new EtlTableMetaCol();
        col.setTableId(tableId);
        col.setColId(colId);
        EtlTableMetaCol m = this.colMapper.getComboCol(col);
        String matchCol = m.getMatchCol();
        String useCol = m.getUseCol();
        String tname = m.getMatchTable();
        String colText = m.getMatchColText();
        String sql = "select " + matchCol + " \"id\", " + (colText != null && colText.length() != 0 ? colText : matchCol) + " \"text\" ";
        if (useCol != null && useCol.length() > 0) {
            sql = sql + ", " + useCol + " as \"" + useCol + "\"";
        }

        sql = sql + " from " + tname;
        String condition = m.gettCondition();
        if (condition != null && condition.length() > 0) {
            sql = sql + " where " + condition;
        }

        List<Map<String, Object>> ls = this.daoHelper.queryForList(sql);
        return ls;
    }

    public Map<String, Object> getDataById(Integer tableId, Integer dataId, String tableName) {
        String sql = "select * from " + tableName + " where " + "tmp_data_id" + " = " + dataId;
        return this.daoHelper.queryForMap(sql);
    }

    public void updateData(DataWriteDto dto) {
        Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        StringBuffer sql = new StringBuffer("update ");
        sql.append(dto.getTableName() + " set ");
        final List<DataWriteColDto> cols = dto.getCols();

        for(int i = 0; i < cols.size(); ++i) {
            DataWriteColDto col = (DataWriteColDto)cols.get(i);
            sql.append(col.getColName() + " = ?");
            if (i != cols.size() - 1) {
                sql.append(",");
            }
        }

        sql.append(" where tmp_data_id = " + dto.getDataId());
        this.daoHelper.execute(sql.toString(), new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                for(int i = 0; i < cols.size(); ++i) {
                    DataWriteColDto col = (DataWriteColDto)cols.get(i);
                    String type = col.getColType();

                    try {
                        String val = col.getValue();
                        if (val != null && val.length() != 0) {
                            if ("Double".equals(type)) {
                                ps.setDouble(i + 1, new Double(val));
                            } else if ("Int".equals(type)) {
                                ps.setInt(i + 1, new Integer(val));
                            } else if ("String".equals(type)) {
                                ps.setString(i + 1, val);
                            } else {
                                SimpleDateFormat sdf;
                                if ("Date".equals(type)) {
                                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    ps.setDate(i + 1, new Date(sdf.parse(val).getTime()));
                                } else if ("Datetime".equals(type)) {
                                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    ps.setTimestamp(i + 1, new Timestamp(sdf.parse(val).getTime()));
                                }
                            }
                        } else {
                            ps.setNull(i + 1, 4);
                        }
                    } catch (Exception var7) {
                        var7.printStackTrace();
                        throw new SQLException("Sql 转换 出错。");
                    }
                }

                ps.executeUpdate();
                return null;
            }
        });
    }

    public void saveData(DataWriteDto dto) {
        final Integer userId = RSBIUtils.getLoginUserInfo().getUserId();
        StringBuffer sql = new StringBuffer("insert into ");
        sql.append(dto.getTableName());
        sql.append("(");
        sql.append("tmp_data_id,tmp_user_id,");
        final List<DataWriteColDto> cols = dto.getCols();

        int i;
        for(i = 0; i < cols.size(); ++i) {
            DataWriteColDto col = (DataWriteColDto)dto.getCols().get(i);
            sql.append(col.getColName());
            if (i != cols.size() - 1) {
                sql.append(",");
            }
        }

        sql.append(")");
        sql.append(" values(?,?,");

        for(i = 0; i < cols.size(); ++i) {
            sql.append("?");
            if (i != cols.size() - 1) {
                sql.append(",");
            }
        }

        sql.append(")");
        i = this.daoHelper.queryForInt("select case when max(tmp_data_id) is null then 1 else max(tmp_data_id) + 1 end from " + dto.getTableName());
        int finalI = i;
        this.daoHelper.execute(sql.toString(), new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(1, finalI);
                ps.setInt(2, userId);

                for(int ix = 0; ix < cols.size(); ++ix) {
                    DataWriteColDto col = (DataWriteColDto)cols.get(ix);
                    String type = col.getColType();

                    try {
                        String val = col.getValue();
                        if (val != null && val.length() != 0) {
                            if ("Double".equals(type)) {
                                ps.setDouble(ix + 3, new Double(val));
                            } else if ("Int".equals(type)) {
                                ps.setInt(ix + 3, new Integer(val));
                            } else if ("String".equals(type)) {
                                ps.setString(ix + 3, val);
                            } else {
                                SimpleDateFormat sdf;
                                if ("Date".equals(type)) {
                                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    ps.setDate(ix + 3, new Date(sdf.parse(val).getTime()));
                                } else if ("Datetime".equals(type)) {
                                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    ps.setTimestamp(ix + 3, new Timestamp(sdf.parse(val).getTime()));
                                }
                            }
                        } else {
                            ps.setNull(ix + 3, 4);
                        }
                    } catch (Exception var7) {
                        var7.printStackTrace();
                        throw new SQLException("Sql 转换 出错。");
                    }
                }

                ps.executeUpdate();
                return null;
            }
        });
    }

    public void delData(String tableName, Integer dataId) {
        String sql = "delete from " + tableName + " where " + "tmp_data_id" + " = " + dataId;
        this.daoHelper.execute(sql);
    }

    public Result impDatas(String tarTname, Integer tarTid, String tname, Integer tableId) {
        Result ret = new Result();
        List<EtlTableMetaCol> curTable = this.metaService.queryTableColumns(tableId, false);
        List<EtlTableMetaCol> target = this.metaService.queryTableColumns(tarTid, false);
        if (target.size() != curTable.size()) {
            ret.setResult(0);
            ret.setMsg("导入表字段和原表字段不对应。");
        } else {
            for(int i = 0; i < target.size(); ++i) {
                EtlTableMetaCol col = (EtlTableMetaCol)target.get(i);
                String colName = col.getColName();
                if (!this.colExist(curTable, colName)) {
                    ret.setResult(0);
                    ret.setMsg("导入表字段和原表字段不对应。字段：" + colName);
                    break;
                }
            }
        }

        if (ret.getResult() == null) {
            try {
                this.process(curTable, tname, target, tarTname);
                ret.setResult(1);
            } catch (Exception var11) {
                ret.setResult(0);
                ret.setMsg(var11.getMessage());
            }
        }

        return ret;
    }

    private boolean colExist(List<EtlTableMetaCol> cols, String colName) {
        boolean exist = false;

        for(int i = 0; i < cols.size(); ++i) {
            EtlTableMetaCol col = (EtlTableMetaCol)cols.get(i);
            String cname = col.getColName();
            if (colName.equals(cname)) {
                exist = true;
                break;
            }
        }

        return exist;
    }

    private void process(List<EtlTableMetaCol> curTable, String currTname, List<EtlTableMetaCol> target, String targetTname) {
        int maxId = this.daoHelper.queryForInt("select case when max(tmp_data_id) is null then 1 else max(tmp_data_id) + 1 end from " + currTname);
        int userId = RSBIUtils.getLoginUserInfo().getUserId();
        String querySql = this.querySql(target, targetTname);
        String insertSql = this.insertSql(curTable, currTname);
        Connection targetConn = null;
        Connection insertConn = null;
        PreparedStatement exeps = null;
        PreparedStatement insertps = null;

        try {
            targetConn = this.dsService.getConnection();
            insertConn = this.dsService.getConnection();
            insertConn.setAutoCommit(false);
            insertps = insertConn.prepareStatement(insertSql);
            exeps = targetConn.prepareStatement(querySql, 1003, 1007);
            exeps.setFetchSize(10000);

            ResultSet rs;
            for(rs = exeps.executeQuery(); rs.next(); ++maxId) {
                try {
                    int size = target.size();

                    DataLoaderService var10000;
                    for(int i = 0; i < size; ++i) {
                        Object val = DataSourceBuilder.getResultSetValue(rs, i + 1);
                        var10000 = this.dlService;
                        DataLoaderService.setPreparedStatementValue(insertps, i + 1, val);
                    }

                    var10000 = this.dlService;
                    DataLoaderService.setPreparedStatementValue(insertps, size + 1, userId);
                    var10000 = this.dlService;
                    DataLoaderService.setPreparedStatementValue(insertps, size + 2, maxId);
                    insertps.addBatch();
                } catch (Exception var42) {
                    var42.printStackTrace();
                }
            }

            rs.close();
            insertps.executeBatch();
            insertConn.commit();
        } catch (Exception var43) {
            var43.printStackTrace();
        } finally {
            try {
                insertConn.setAutoCommit(true);
            } catch (SQLException var41) {
                var41.printStackTrace();
            }

            if (exeps != null) {
                try {
                    exeps.close();
                } catch (SQLException var40) {
                    var40.printStackTrace();
                }
            }

            if (insertps != null) {
                try {
                    insertps.close();
                } catch (SQLException var39) {
                    var39.printStackTrace();
                }
            }

            if (insertConn != null) {
                try {
                    insertConn.close();
                } catch (SQLException var38) {
                    var38.printStackTrace();
                }
            }

            if (targetConn != null) {
                try {
                    targetConn.close();
                } catch (SQLException var37) {
                    var37.printStackTrace();
                }
            }

        }

    }

    private String querySql(List<EtlTableMetaCol> tarCols, String targetTname) {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");

        for(int i = 0; i < tarCols.size(); ++i) {
            String col_name = ((EtlTableMetaCol)tarCols.get(i)).getColName();
            sql.append(col_name);
            if (i != tarCols.size() - 1) {
                sql.append(",");
            }
        }

        sql.append(" from ");
        sql.append(targetTname);
        return sql.toString();
    }

    private String insertSql(List<EtlTableMetaCol> currCols, String currTname) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append(currTname);
        sql.append("(");

        int i;
        for(i = 0; i < currCols.size(); ++i) {
            String colName = ((EtlTableMetaCol)currCols.get(i)).getColName();
            sql.append(colName);
            sql.append(", ");
        }

        sql.append("TMP_USER_ID,");
        sql.append("TMP_DATA_ID");
        sql.append(")");
        sql.append(" values( ");

        for(i = 0; i < currCols.size(); ++i) {
            sql.append("?");
            sql.append(", ");
        }

        sql.append("?,?)");
        return sql.toString();
    }
}
