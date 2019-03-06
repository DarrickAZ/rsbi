//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.tree;

import com.ruisi.ext.engine.view.context.form.TreeContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeService {
    private TreeContext a;

    public TreeService(TreeContext var1) {
        this.a = var1;
    }

    public List createTreeData(List var1) {
        List var2 = this.a(var1, this.a.getDefRootId());
        this.a(var2, var1);
        return var2;
    }

    private void a(List var1, List var2) {
        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Map var4 = (Map)var1.get(var3);
            String var5 = null;
            Object var6 = var4.get(this.a.getValueId());
            if (var6 != null) {
                var5 = var6.toString();
            }

            if (var5 != null) {
                List var7 = this.a(var2, var5);
                if (var7.size() > 0) {
                    this.a(var7, var2);
                }

                if (var7.size() > 0) {
                    var4.put("state", "closed");
                }

                var4.put("children", var7);
            }
        }

    }

    private List a(List var1, String var2) {
        ArrayList var3 = new ArrayList();

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Map var5 = (Map)var1.get(var4);
            String var6 = null;
            Object var7 = var5.get(this.a.getValuePid());
            if (var7 != null) {
                var6 = var7.toString();
            }

            if (var6 != null && var6.equals(var2)) {
                var3.add(var5);
            }
        }

        return var3;
    }
}
