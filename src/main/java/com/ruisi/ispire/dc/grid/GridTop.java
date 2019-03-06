//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GridTop extends GridBaseProcessor {
    private Integer a;

    public GridTop(Integer var1) {
        this.a = var1;
    }

    public List process() {
        ArrayList var1 = new ArrayList();

        for(int var2 = 0; var2 < this.a && var2 < this.datas.size(); ++var2) {
            var1.add((Map)this.datas.get(var2));
        }

        return var1;
    }
}
