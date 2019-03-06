//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.dsource;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.dao.DatabaseHelper;
import com.ruisi.ext.engine.util.P;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataSourceBuilder {
    private static Log a = LogFactory.getLog(DataSourceBuilder.class);

    public DataSourceBuilder() {
    }

    public DataSourceContext findDataSource(String var1, MVContext var2) throws ExtConfigException {
        Map var3 = var2.getDsources();
        if (var3 != null && var3.get(var1) != null) {
            return (DataSourceContext)var3.get(var1);
        } else {
            String var4 = ConstantsEngine.replace("配置的dataSource id = $0 不存在.", var1);
            throw new ExtConfigException(var4);
        }
    }

    protected int queryDataCount(String var1, DataSourceContext var2, Connection var3) throws SQLException {
        boolean var4 = false;
        String var5 = "select count(*) cnt from (" + var1 + ") ttt";
        PreparedStatement var6 = null;
        ResultSet var7 = null;

        int var18;
        try {
            try {
                var6 = var3.prepareStatement(var5);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            var7 = var6.executeQuery();
            var7.next();
            var18 = var7.getInt(1);
        } finally {
            if (var6 != null) {
                try {
                    var6.close();
                } catch (SQLException var16) {
                    var16.printStackTrace();
                }
            }

            if (var7 != null) {
                try {
                    var7.close();
                } catch (SQLException var15) {
                    var15.printStackTrace();
                }
            }

        }

        return var18;
    }

    public List queryForList(String var1, DataSourceContext var2, PageInfo var3, ExtRequest var4) {
        ArrayList var5 = new ArrayList();
        Connection var6 = null;
        PreparedStatement var7 = null;
        ResultSet var8 = null;

        try {
            var6 = this.crtConnection(var2);
            int var9 = this.queryDataCount(var1, var2, var6);
            var3.setAllsize((long)var9);
            var4.setAttribute("ext.view.pageInfo", var3);
            DatabaseHelper var10 = ExtContext.getInstance().getDatabaseHelper(var2.getLinktype());
            var1 = var10.getQueryPageSql(var1, var3);
            a.info(var1);
            var7 = var6.prepareStatement(var1);
            var8 = var7.executeQuery();
            int var11 = var8.getMetaData().getColumnCount();
            String[] var12 = new String[var11];

            for(int var13 = 1; var13 <= var11; ++var13) {
                var12[var13 - 1] = var8.getMetaData().getColumnLabel(var13);
            }

            while(var8.next()) {
                CaseInsensitiveMap var28 = new CaseInsensitiveMap();

                for(int var14 = 1; var14 <= var12.length; ++var14) {
                    String var15 = var12[var14 - 1];
                    var28.put(var15, getResultSetValue(var8, var14));
                }

                var5.add(var28);
            }

            return var5;
        } catch (Exception var26) {
            var26.printStackTrace();
            throw new ExtRuntimeException(var26.getMessage(), var26);
        } finally {
            if (var7 != null) {
                try {
                    var7.close();
                } catch (SQLException var25) {
                    var25.printStackTrace();
                }
            }

            if (var8 != null) {
                try {
                    var8.close();
                } catch (SQLException var24) {
                    var24.printStackTrace();
                }
            }

            this.closeConnection(var6);
        }
    }

    public List queryForList(String var1, DataSourceContext var2) {
        ArrayList var3 = new ArrayList();
        Connection var4 = null;
        PreparedStatement var5 = null;
        ResultSet var6 = null;

        try {
            var4 = this.crtConnection(var2);
            var5 = var4.prepareStatement(var1);
            a.info(var1);
            var6 = var5.executeQuery();
            int var7 = var6.getMetaData().getColumnCount();
            String[] var8 = new String[var7];

            for(int var9 = 1; var9 <= var7; ++var9) {
                var8[var9 - 1] = var6.getMetaData().getColumnLabel(var9);
            }

            while(var6.next()) {
                CaseInsensitiveMap var24 = new CaseInsensitiveMap();

                for(int var10 = 1; var10 <= var8.length; ++var10) {
                    String var11 = var8[var10 - 1];
                    var24.put(var11, getResultSetValue(var6, var10));
                }

                var3.add(var24);
            }

            return var3;
        } catch (Exception var22) {
            var22.printStackTrace();
            throw new ExtRuntimeException(var22);
        } finally {
            if (var5 != null) {
                try {
                    var5.close();
                } catch (SQLException var21) {
                    var21.printStackTrace();
                }
            }

            if (var6 != null) {
                try {
                    var6.close();
                } catch (SQLException var20) {
                    var20.printStackTrace();
                }
            }

            this.closeConnection(var4);
        }
    }

    public Connection crtConnection(DataSourceContext var1) {
        Connection var2 = null;

        try {
            String var4;
            if (var1.getUse() != null && !"jdbc".equalsIgnoreCase(var1.getUse())) {
                InitialContext var8 = new InitialContext();
                var4 = "java:comp/env/" + var1.getJndiname();
                DataSource var9 = (DataSource)var8.lookup(var4);
                var2 = var9.getConnection();
            } else {
                DatabaseHelper var3 = ExtContext.getInstance().getDatabaseHelper(var1.getLinktype());
                var4 = var3.getClazz();
                Class.forName(var4).newInstance();
                String var5 = P.decode(var1.getLinkpwd());
                var2 = DriverManager.getConnection(var1.getLinkurl(), var1.getLinkname(), var5);
            }

            return var2;
        } catch (SQLException var6) {
            var6.printStackTrace();
            throw new ExtRuntimeException("数据库连接出错。" + var6.getMessage(), var6);
        } catch (Exception var7) {
            throw new ExtRuntimeException(var7.getMessage(), var7);
        }
    }

    public void closeConnection(Connection var1) {
        if (var1 != null) {
            try {
                var1.close();
            } catch (SQLException var3) {
                var3.printStackTrace();
            }
        }

    }

    public static Object getResultSetValue(ResultSet var0, int var1) {
        Object var2 = null;
        try {
            var2 = var0.getObject(var1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (var2 instanceof Blob) {
            try {
                var2 = var0.getBytes(var1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (var2 instanceof Clob) {
            try {
                var2 = var0.getString(var1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (var2 != null && var2.getClass().getName().startsWith("oracle.sql.TIMESTAMP")) {
            try {
                var2 = var0.getTimestamp(var1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (var2 != null && var2.getClass().getName().startsWith("oracle.sql.DATE")) {
            String var3 = null;
            try {
                var3 = var0.getMetaData().getColumnClassName(var1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (!"java.sql.Timestamp".equals(var3) && !"oracle.sql.TIMESTAMP".equals(var3)) {
                try {
                    var2 = var0.getDate(var1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    var2 = var0.getTimestamp(var1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                if (var2 != null && var2 instanceof Date && "java.sql.Timestamp".equals(var0.getMetaData().getColumnClassName(var1))) {
                    try {
                        var2 = var0.getTimestamp(var1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return var2;
    }
}
