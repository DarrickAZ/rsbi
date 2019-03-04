//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.transaction.annotation.Transactional;

public class EtlBaseService {
    public static final String tbbId = "tmp_data_id";
    public static final String tbbUser = "tmp_user_id";

    public EtlBaseService() {
    }

    public String createImpTableSql(EtlTableMeta table) {
        StringBuffer sb = new StringBuffer("");
        sb.append("create table " + table.getTableName() + " (\n");
        List<DSColumn> ls = table.getCols();

        for(int i = 0; i < ls.size(); ++i) {
            sb.append("\t");
            DSColumn col = (DSColumn)ls.get(i);
            sb.append(col.getName());
            sb.append(" ");
            Integer length = col.getLength();
            Integer scale = col.getScale();
            sb.append(this.javaType2db(col.getType(), table.getDbName(), length, scale));
            if (i != ls.size() - 1) {
                sb.append(",");
            }

            sb.append("\n");
        }

        sb.append(")");
        if ("mysql".equals(table.getDbName())) {
            sb.append(" ENGINE=MyISAM CHARSET=utf8 COMMENT='" + table.getTableDesc() + "'");
        }

        return sb.toString();
    }

    public String createDwTableSql(EtlTableMeta table) {
        StringBuffer sb = new StringBuffer("");
        sb.append("create table " + table.getTableName() + " (\n");
        List<EtlTableMetaCol> ls = table.getMetaCols();

        for(int i = 0; i < ls.size(); ++i) {
            sb.append("\t");
            EtlTableMetaCol col = (EtlTableMetaCol)ls.get(i);
            sb.append(col.getColName());
            sb.append(" ");
            Integer length = col.getColLength();
            Integer scale = col.getColScale();
            sb.append(this.javaType2db(col.getColType(), table.getDbName(), length, scale));
            if (i != ls.size() - 1) {
                sb.append(",");
            }

            sb.append("\n");
        }

        sb.append(",");
        sb.append("tmp_data_id " + this.javaType2db("Int", table.getDbName(), 11, 2));
        sb.append(" not null PRIMARY KEY,\n");
        sb.append("tmp_user_id " + this.javaType2db("Int", table.getDbName(), 11, 2));
        sb.append("\n");
        sb.append(")");
        if ("mysql".equals(table.getDbName())) {
            sb.append(" ENGINE=MyISAM CHARSET=utf8 COMMENT='" + table.getTableDesc() + "'");
        }

        return sb.toString();
    }

    public String columnType2java(String tp) {
        tp = tp.replaceAll(" UNSIGNED", "");
        String ret = null;
        if (!"varchar".equalsIgnoreCase(tp) && !"varchar2".equalsIgnoreCase(tp) && !"nvarchar2".equalsIgnoreCase(tp) && !"char".equalsIgnoreCase(tp) && !"string".equalsIgnoreCase(tp)) {
            if (!"int".equalsIgnoreCase(tp) && !"INTEGER".equalsIgnoreCase(tp) && !"MEDIUMINT".equalsIgnoreCase(tp) && !"BIGINT".equalsIgnoreCase(tp) && !"smallint".equalsIgnoreCase(tp) && !"TINYINT".equalsIgnoreCase(tp)) {
                if (!"number".equalsIgnoreCase(tp) && !"DECIMAL".equalsIgnoreCase(tp) && !"Float".equalsIgnoreCase(tp) && !"Double".equalsIgnoreCase(tp)) {
                    if (!"DATETIME".equalsIgnoreCase(tp) && !"Timestamp".equalsIgnoreCase(tp)) {
                        if ("DATE".equalsIgnoreCase(tp)) {
                            ret = "Date";
                        }
                    } else {
                        ret = "Datetime";
                    }
                } else {
                    ret = "Double";
                }
            } else {
                ret = "Int";
            }
        } else {
            ret = "String";
        }

        return ret;
    }

    private String javaType2db(String type, String dbName, Integer length, Integer scale) {
        if (scale == null) {
            scale = 2;
        }

        if ("mysql".equals(dbName)) {
            if ("String".equals(type)) {
                return length > 6000 ? "text" : "varchar(" + length + ")";
            } else if ("Int".equals(type)) {
                return "int(" + length + ")";
            } else if ("Double".equals(type)) {
                return "DECIMAL(" + length + "," + scale + ")";
            } else if ("Date".equals(type)) {
                return "DATE";
            } else if ("Datetime".equals(type)) {
                return "DATETIME";
            } else {
                throw new RuntimeException("类型 " + type + " 未定义。");
            }
        } else if (!"sqlser".equals(dbName) && !"sqlserver".equals(dbName)) {
            if ("oracle".equals(dbName)) {
                if ("String".equals(type)) {
                    return length > 6000 ? "clob" : "varchar2(" + length + ")";
                } else if ("Int".equals(type)) {
                    return "number(" + length + ")";
                } else if ("Double".equals(type)) {
                    return "number(" + length + ", " + scale + ")";
                } else if ("Date".equals(type)) {
                    return "date";
                } else if ("Datetime".equals(type)) {
                    return "date";
                } else {
                    throw new RuntimeException("类型 " + type + " 未定义。");
                }
            } else if ("db2".equals(dbName)) {
                if ("String".equals(type)) {
                    return length > 6000 ? "CLOB" : "VARCHAR(" + length + ")";
                } else if ("Int".equals(type)) {
                    return "INTEGER";
                } else if ("Double".equals(type)) {
                    return "DECIMAL(" + length + ", " + scale + ")";
                } else if ("Date".equals(type)) {
                    return "DATE";
                } else if ("Datetime".equals(type)) {
                    return "Timestamp";
                } else {
                    throw new RuntimeException("类型 " + type + " 未定义。");
                }
            } else {
                return null;
            }
        } else if ("String".equals(type)) {
            return length > 6000 ? "nvarchar(MAX)" : "nvarchar(" + length + ")";
        } else if ("Int".equals(type)) {
            return "int";
        } else if ("Double".equals(type)) {
            return "float";
        } else if ("Datetime".equals(type)) {
            return "datetime";
        } else if ("Date".equals(type)) {
            return "date";
        } else {
            throw new RuntimeException("类型 " + type + " 未定义。");
        }
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void droptable(String tname, DaoHelper daoHelper, final String dbName) {
        String sql;
        if ("sqlser".equals(dbName)) {
            sql = "if OBJECT_ID('" + tname + "') is not null drop table " + tname;
            daoHelper.execute(sql);
        } else if ("mysql".equals(dbName)) {
            sql = "drop table if exists " + tname;
            daoHelper.execute(sql);
        } else if ("oracle".equals(dbName)) {
            sql = (String)daoHelper.execute(new ConnectionCallback<Object>() {
                public Object doInConnection(Connection arg0) throws SQLException, DataAccessException {
                    return EtlBaseService.this.getCurrentSchema(arg0, dbName);
                }
            });
            String sql = "select count(1) cnt from all_tables where owner='" + sql + "'  and TABLE_NAME = upper('" + tname + "')";
            int cnt = daoHelper.queryForInt(sql);
            if (cnt >= 1) {
                daoHelper.execute("drop table " + tname);
            }
        } else if ("db2".equals(dbName)) {
            sql = "select count(1) cnt from syscat.tables where type='T' and tabname = upper('" + tname + "')";
            int cnt = daoHelper.queryForInt(sql);
            if (cnt >= 1) {
                daoHelper.execute("drop table " + tname);
            }
        }

    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void dropview(String tname, DaoHelper daoHelper, String dbName) {
        String sql;
        if ("sqlser".equals(dbName)) {
            sql = "if OBJECT_ID('" + tname + "') is not null drop view " + tname;
            daoHelper.execute(sql);
        } else if ("mysql".equals(dbName)) {
            sql = "drop view if exists " + tname;
            daoHelper.execute(sql);
        } else {
            int cnt;
            if ("oracle".equals(dbName)) {
                sql = "select count(1) cnt from all_views where VIEW_NAME = upper('" + tname + "')";
                cnt = daoHelper.queryForInt(sql);
                if (cnt >= 1) {
                    daoHelper.execute("drop view " + tname);
                }
            } else if ("db2".equals(dbName)) {
                sql = "select count(1) cnt from syscat.views where viewname = upper('" + tname + "')";
                cnt = daoHelper.queryForInt(sql);
                if (cnt >= 1) {
                    daoHelper.execute("drop view " + tname);
                }
            }
        }

    }

    public Date getServiceDatetime(String dbName, Connection conn) throws SQLException {
        Date dt = null;
        String sql = null;
        if (!"sqlser".equals(dbName) && !"sqlserver".equals(dbName)) {
            if ("mysql".equals(dbName)) {
                sql = "select now() from dual";
            } else if ("oracle".equals(dbName)) {
                sql = "select sysdate from dual";
            } else if ("db2".equals(dbName)) {
                sql = "select current timestamp from dual";
            } else {
                if (!"hive".equals(dbName)) {
                    throw new RuntimeException("类型 " + dbName + " 未定义。");
                }

                sql = "select current_timestamp()";
            }
        } else {
            sql = "SELECT GETDATE() AS CurrentDateTime ";
        }

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs;
        for(rs = ps.executeQuery(); rs.next(); dt = (Date)DataSourceBuilder.getResultSetValue(rs, 1)) {
        }

        rs.close();
        ps.close();
        return dt;
    }

    public void addTableColumn(EtlTableMetaCol vo, DaoHelper dao) {
        String tp = this.javaType2db(vo.getColType(), vo.getDbName(), vo.getColLength(), vo.getColScale());
        String sql = " ALTER TABLE " + vo.getTableName() + " ADD " + vo.getColName() + " " + tp;
        dao.execute(sql);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void updateColumn(EtlTableMetaCol vo, DaoHelper dao) {
        String dbName = vo.getDbName();
        String tp = this.javaType2db(vo.getColType(), dbName, vo.getColLength(), vo.getColScale());
        String sql = null;
        if ("db2".equals(dbName)) {
            sql = " ALTER TABLE " + vo.getTableName() + " alter column " + vo.getColName() + "  set data type " + tp;
        } else {
            sql = " ALTER TABLE " + vo.getTableName() + " modify " + vo.getColName() + " " + tp;
        }

        dao.execute(sql);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void removeTableColumn(EtlTableMetaCol vo, DaoHelper dao) {
        String sql = "ALTER TABLE " + vo.getTableName() + " DROP  column " + vo.getColName();
        dao.execute(sql);
    }

    public String getCurrentSchema(Connection conn, String dbName) throws SQLException {
        String ret = null;
        String sql;
        Statement ps;
        ResultSet rs;
        if ("db2".equalsIgnoreCase(dbName)) {
            sql = "select current schema from sysibm.dual";

            try {
                ps = conn.createStatement();
                rs = ps.executeQuery(sql);
                if (rs.next()) {
                    ret = rs.getString(1);
                }

                rs.close();
                ps.close();
            } catch (SQLException var9) {
                var9.printStackTrace();
            }
        } else if ("mysql".equalsIgnoreCase(dbName)) {
            ret = conn.getCatalog();
        } else if ("oracle".equals(dbName)) {
            sql = "select username from user_users";

            try {
                ps = conn.createStatement();
                rs = ps.executeQuery(sql);
                if (rs.next()) {
                    ret = rs.getString(1);
                }

                rs.close();
                ps.close();
            } catch (SQLException var8) {
                var8.printStackTrace();
            }
        } else if ("psql".equals(dbName)) {
            sql = "select * from current_user";

            try {
                ps = conn.createStatement();
                rs = ps.executeQuery(sql);
                if (rs.next()) {
                    ret = rs.getString(1);
                }

                rs.close();
                ps.close();
            } catch (SQLException var7) {
                var7.printStackTrace();
            }
        }

        return ret;
    }
}
