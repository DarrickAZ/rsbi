//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.build;

import com.ruisi.ext.engine.init.ExtXMLLoader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.ServletContext;

public class DataSetXMLLoader implements ExtXMLLoader {
    private Map a;

    public InputStream load(String var1, String var2, String var3, ServletContext var4) {
        String var5 = (String)this.a.get(var1);
        ByteArrayInputStream var6 = new ByteArrayInputStream(var5.getBytes());
        return var6;
    }

    public DataSetXMLLoader(Map var1) {
        this.a = var1;
    }
}
