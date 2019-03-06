//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init;

import java.io.File;
import java.util.List;

public class XmlGetter {
    public XmlGetter() {
    }

    public void get(String var1, List var2, String var3) {
        File var4 = new File(var1);
        if (var4.isDirectory()) {
            var3 = var3 + var4.getName() + ".";
            File[] var5 = var4.listFiles(new a(this));
            File[] var9 = var5;
            int var8 = var5.length;

            for(int var7 = 0; var7 < var8; ++var7) {
                File var6 = var9[var7];
                this.get(var6.getAbsolutePath(), var2, var3);
            }
        } else {
            var2.add(new String[]{var1, var3 + var4.getPath()});
        }

    }
}
