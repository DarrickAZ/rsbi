//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dsource;

import java.util.HashMap;
import java.util.Map;

public class DataSourceContext {
    private Map a = new HashMap();

    public DataSourceContext() {
    }

    public String getId() {
        return (String)this.a.get("id");
    }

    public String getLinktype() {
        return (String)this.a.get("linktype");
    }

    public String getLinkname() {
        return (String)this.a.get("linkname");
    }

    public String getLinkpwd() {
        return (String)this.a.get("linkpwd");
    }

    public String getLinkurl() {
        return (String)this.a.get("linkurl");
    }

    public String getJndiname() {
        return (String)this.a.get("jndiname");
    }

    public String getUse() {
        return (String)this.a.get("use");
    }

    public void putProperty(String var1, String var2) {
        this.a.put(var1, var2);
    }
}
