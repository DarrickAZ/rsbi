//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init.rule;

import com.ruisi.ext.engine.dao.DatabaseHelper;
import com.ruisi.ext.engine.view.context.ExtContext;
import org.apache.commons.digester.Rule;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

public class DatabaseRule extends Rule {
    private Logger a = Logger.getLogger(DatabaseRule.class);

    public DatabaseRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("type");
        String var5 = var3.getValue("class");

        try {
            DatabaseHelper var6 = (DatabaseHelper)Class.forName(var5).newInstance();
            ExtContext.getInstance().putDatabaseHelper(var4, var6);
        } catch (InstantiationException var8) {
            var8.printStackTrace();
        } catch (IllegalAccessException var9) {
            var9.printStackTrace();
        } catch (ClassNotFoundException var10) {
            this.a.error("类找不到 " + var5, var10);
        }

    }
}
