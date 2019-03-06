//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.util.List;

public interface DatasetProvider {
    List queryData(String var1, boolean var2, String var3, ExtRequest var4, PageInfo var5);
}
