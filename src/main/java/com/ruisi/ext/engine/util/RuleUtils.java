//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.grid.DataGridContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.util.Iterator;
import java.util.List;

public class RuleUtils {
    public RuleUtils() {
    }

    public static MVContext findMVContext(Element var0) {
        if (var0 == null) {
            return null;
        } else {
            return var0 instanceof MVContext ? (MVContext)var0 : findMVContext(var0.getParent());
        }
    }

    public static boolean isEmpty(List var0) {
        if (var0.isEmpty()) {
            return true;
        } else {
            boolean var1 = true;
            Iterator var3 = var0.iterator();

            while(var3.hasNext()) {
                String var2 = (String)var3.next();
                if (var2 != null) {
                    var1 = false;
                    break;
                }
            }

            return var1;
        }
    }

    public static int theDataGridPos(Element var0) {
        int var1 = 0;
        if (var0.getChildren() == null) {
            return var1;
        } else {
            Element var2;
            for(Iterator var3 = var0.getChildren().iterator(); var3.hasNext(); var1 += theDataGridPos(var2)) {
                var2 = (Element)var3.next();
                if (var2 instanceof DataGridContext) {
                    ++var1;
                }
            }

            return var1;
        }
    }

    public static MVContext findCurMV(ExtEnvirContext var0) {
        MVContext var1 = (MVContext)var0.get("mvInfo");
        return var1;
    }

    public static String getResPath(ExtRequest var0) {
        String var1 = (String)var0.getAttribute("ext.respath");
        return var1 != null && var1.length() != 0 ? var1 : "../";
    }
}
