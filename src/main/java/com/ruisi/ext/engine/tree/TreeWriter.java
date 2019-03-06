//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.tree;

import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.util.Iterator;
import java.util.List;

public class TreeWriter {
    private boolean a = false;
    private ExtRequest b;
    private ExtWriter c;
    private TreeNode d;
    private boolean e = true;

    public TreeWriter(ExtRequest var1, ExtWriter var2, TreeNode var3) {
        this.b = var1;
        this.c = var2;
        this.d = var3;
    }

    public void write() {
        this.c.println("<div class=\"hqtree\">");
        if (this.a) {
            this.loopTree(this.d, this.c);
        } else {
            this.loopTree(this.d.getChilden(), this.c);
        }

        this.c.println("</div>");
    }

    private void a(TreeNode var1, ExtWriter var2) {
        var2.print("<div class=\"tree\" ");
        var1.isLeaf();
        var2.print(">");
        int var3 = var1.getLevel() - 1;
        int var4 = var3 * 10;
        var2.print("<span style=\"width:" + var4 + "px;\"></span>");
        if (!var1.isLeaf()) {
            if (var1.isIsopen()) {
                var2.print("<span class=\"treeNode-icon-open\" onClick=\"clicknode(this)\" isopen=\"true\"></span>");
            } else {
                var2.print("<span class=\"treeNode-icon-close\" onClick=\"clicknode(this)\" isopen=\"false\"></span>");
            }
        } else {
            var2.print("<span class=\"treeNode-icon-leaf\"></span>");
        }

        if (this.e) {
            var2.print("<input type='checkbox' name='c_svc' value='" + var1.getId() + "' ");
            if (var1.isIsopen()) {
                var2.print(" checked");
            }

            var2.print(">");
        }

        if (var1.isLeaf()) {
            var2.print(var1.getName());
        } else {
            var2.println(var1.getName());
        }

        var2.println("</div>");
    }

    public void loopTree(List var1, ExtWriter var2) {
        Iterator var4 = var1.iterator();

        while(var4.hasNext()) {
            TreeNode var3 = (TreeNode)var4.next();
            this.a(var3, var2);
            if (!var3.isLeaf()) {
                var2.println("<div " + (var3.isIsopen() ? "" : "style=\"display:none\"") + ">");
            }

            this.loopTree(var3.getChilden(), var2);
            if (!var3.isLeaf()) {
                var2.println("</div>");
            }
        }

    }

    public void loopTree(TreeNode var1, ExtWriter var2) {
        this.a(var1, var2);
        if (!var1.isLeaf()) {
            var2.println("<div " + (var1.isIsopen() ? "" : "style=\"display:none\"") + ">");
        }

        List var3 = var1.getChilden();
        Iterator var5 = var3.iterator();

        while(var5.hasNext()) {
            TreeNode var4 = (TreeNode)var5.next();
            this.loopTree(var4, var2);
        }

        if (!var1.isLeaf()) {
            var2.println("</div>");
        }

    }

    public boolean isShowRoot() {
        return this.a;
    }

    public void setShowRoot(boolean var1) {
        this.a = var1;
    }

    public boolean isShowCheckbox() {
        return this.e;
    }

    public void setShowCheckbox(boolean var1) {
        this.e = var1;
    }
}
