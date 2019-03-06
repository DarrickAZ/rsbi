//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.util.List;
import java.util.Map;

public class FieldLoaderImpl implements CrossFieldLoader {
    private ExtRequest a;

    public FieldLoaderImpl() {
    }

    public void setRequest(ExtRequest var1) {
        this.a = var1;
    }

    public String loadFieldName(String var1, String var2) {
        return "测试标题";
    }

    public List loadField(String var1, String var2, Map var3) {
        return null;
    }

    public List loadUserCustomize(String var1, String var2) {
        return null;
    }

    public List loadKpiBySvc(String var1) {
        return null;
    }

    public CrossField loadFieldByKpiCode(String var1) {
        CrossField var2 = new CrossField();
        var2.setAggregation("sum");
        var2.setFormatPattern("###,##0");
        var2.setType("kpiOther");
        String var3 = CrossWriterService.getKpiValueColumn();
        var2.setAlias(var3);
        return var2;
    }
}
