//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

public class SystemFuncLoader {
    private static Logger a = Logger.getLogger(SystemFuncLoader.class);

    public SystemFuncLoader() {
    }

    public String load() throws ScriptEnginerException {
        InputStream var1 = this.getClass().getResourceAsStream("/com/ruisi/ispire/dc/cube/engine/sysFunc.js");
        InputStreamReader var2 = new InputStreamReader(var1);
        BufferedReader var3 = new BufferedReader(var2);
        String var4 = null;
        StringBuffer var5 = new StringBuffer();

        try {
            while((var4 = var3.readLine()) != null) {
                var5.append(var4);
                var5.append("\n");
            }
        } catch (IOException var7) {
            a.error("读取资源文件出错.", var7);
            throw new ScriptEnginerException("读取资源文件出错.", var7);
        }

        return var5.toString();
    }
}
