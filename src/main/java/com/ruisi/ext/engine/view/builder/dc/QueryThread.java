//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.dc;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import java.util.List;
import org.apache.log4j.Logger;

public class QueryThread extends DataSourceBuilder implements Runnable {
    private static Logger a = Logger.getLogger(QueryThread.class);
    private String b;
    private List c;
    private String d;
    private DaoHelper e;
    private ExtEnvirContext f;

    public QueryThread(String var1, String var2, List var3, DaoHelper var4, ExtEnvirContext var5) {
        this.b = var1;
        this.d = var2;
        this.e = var4;
        this.f = var5;
        this.c = var3;
    }

    public void run() {
        try {
            List var1 = null;
            if (this.d != null && this.d.length() != 0) {
                MVContext var2 = RuleUtils.findCurMV(this.f);
                DataSourceContext var3 = this.findDataSource(this.d, var2);
                var1 = this.queryForList(this.b, var3);
            } else {
                var1 = this.e.queryForList(this.b);
            }

            if (var1 != null && var1.size() > 0) {
                this.c.addAll(var1);
            }
        } catch (Exception var4) {
            a.error("SQL 查询出错。", var4);
        }

    }

    public List getRetDatas() {
        return this.c;
    }
}
