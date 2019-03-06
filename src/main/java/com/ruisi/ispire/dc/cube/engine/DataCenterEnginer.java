//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.engine;

import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import com.ruisi.ispire.dc.cube.DataCenter;
import com.ruisi.ispire.dc.cube.DataFormat;
import java.util.Map;
import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class DataCenterEnginer {
    private static Logger a = Logger.getLogger(DataCenterEnginer.class);
    private String b;
    private ExtWriter c;
    private DataCenter d;
    private SystemFuncLoader e;

    public DataCenterEnginer(String var1, ExtWriter var2, Map var3) throws ClassNotFoundException, ScriptEnginerException, IllegalAccessException, InstantiationException {
        this.b = var1;
        String var4 = ExtContext.getInstance().getConstant("dataCenter");
        if (var4 != null && var4.length() != 0) {
            this.d = (DataCenter)Class.forName(var4).newInstance();
            this.d.setInputParams(var3);
            this.c = var2;
            this.e = new SystemFuncLoader();
            this.b();
            this.a();
        } else {
            throw new ScriptEnginerException("未配置数据中心.");
        }
    }

    public Object startEnginer() throws ScriptEnginerException {
        Object var6;
        try {
            Context var1 = Context.enter();
            ScriptableObject var2 = var1.initStandardObjects();
            this.a(var2);
            var1.evaluateString(var2, this.b, (String)null, 1, (Object)null);
            Function var3 = (Function)var2.get("main", var2);
            Object var4 = var3.call(var1, var2, var2, new Object[0]);
            var6 = var4;
        } catch (Exception var9) {
            a.error("查询执行出错.", var9);
            throw new ScriptEnginerException("查询执行出错.", var9);
        } finally {
            Context.exit();
        }

        return var6;
    }

    private void a(Scriptable var1) {
        Object var2 = Context.javaToJS(this.c, var1);
        ScriptableObject.putProperty(var1, "out", var2);
        Object var3 = Context.javaToJS(this.d, var1);
        ScriptableObject.putProperty(var1, "dc", var3);
        Object var4 = Context.javaToJS(new DataFormat(), var1);
        ScriptableObject.putProperty(var1, "df", var4);
    }

    private void a() {
        StringBuffer var1 = new StringBuffer();
        var1.append("function main(){");
        var1.append(this.b);
        var1.append("}");
        this.b = var1.toString();
    }

    private void b() throws ScriptEnginerException {
        this.b = this.e.load() + "\n" + this.b;
    }
}
