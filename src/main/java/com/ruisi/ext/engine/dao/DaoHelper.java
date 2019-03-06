//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.PreparedStatementCallback;

public interface DaoHelper {
    List queryForList(String var1);

    Map queryForMap(String var1);

    Object queryForObject(String var1, Class var2);

    Object execute(String var1, PreparedStatementCallback var2);

    Long queryForLong(String var1);

    void execute(String var1);

    int queryForInt(String var1);

    List queryForList(String var1, Object[] var2);

    Object execute(ConnectionCallback var1);
}
