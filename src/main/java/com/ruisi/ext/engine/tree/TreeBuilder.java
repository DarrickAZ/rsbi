//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeBuilder {
    private String[] a;
    private String b;

    public TreeBuilder() {
    }

    public TreeNode buildTree(List var1) {
        TreeNode var2 = new TreeNode();
        var2.setId(this.b);
        this.a(var2, var1);
        return var2;
    }

    public TreeNode findFirstLeaf(TreeNode var1) {
        if (var1.getChilden().size() == 0) {
            return var1;
        } else {
            Iterator var3 = var1.getChilden().iterator();
            if (var3.hasNext()) {
                TreeNode var2 = (TreeNode)var3.next();
                return this.findFirstLeaf(var2);
            } else {
                return null;
            }
        }
    }

    private void a(TreeNode var1, List var2) {
        List var3 = this.a(var2, var1);
        Iterator var5 = var3.iterator();

        while(var5.hasNext()) {
            Map var4 = (Map)var5.next();
            TreeNode var6 = new TreeNode();
            var1.putChilden(var6);
            this.a(var6, var4);
            var6.setLevel(var1.getLevel() + 1);
            this.a(var6, var2);
        }

    }

    private void a(TreeNode var1, Map var2) {
        var1.setId((String)var2.get("value"));
        var1.setName((String)var2.get("text"));
        var1.setPid((String)var2.get("pid"));
        if (this.a != null && this.a(this.a, var1.getId())) {
            var1.setIsopen(true);
        }

    }

    private boolean a(String[] var1, String var2) {
        String[] var6 = var1;
        int var5 = var1.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String var3 = var6[var4];
            if (var3.equals(var2)) {
                return true;
            }
        }

        return false;
    }

    private List a(List var1, TreeNode var2) {
        ArrayList var3 = new ArrayList();
        Iterator var5 = var1.iterator();

        while(var5.hasNext()) {
            Map var4 = (Map)var5.next();
            if (var4.get("pid").equals(var2.getId())) {
                var3.add(var4);
            }
        }

        if (var3.size() == 0) {
            var2.setLeaf(true);
        }

        return var3;
    }

    public String getRootId() {
        return this.b;
    }

    public void setRootId(String var1) {
        this.b = var1;
    }

    public String[] getSelectNodes() {
        return this.a;
    }

    public void setSelectNodes(String[] var1) {
        this.a = var1;
    }
}
