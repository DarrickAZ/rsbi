//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.ext.service;

import com.ruisi.ext.engine.cross.CrossFieldLoader;
import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.util.List;
import java.util.Map;

public class MyCrossFieldLoader implements CrossFieldLoader {
    private ExtRequest request;

    public MyCrossFieldLoader() {
    }

    public void setRequest(ExtRequest request) {
        this.request = request;
    }

    public CrossField loadFieldByKpiCode(String kpiCode) {
        return null;
    }

    public String loadFieldName(String type, String value) {
        return "合计";
    }

    public List<String> loadUserCustomize(String userId, String mvId) {
        return null;
    }

    public static String loadFieldName(String aggre) {
        if ("sum".equalsIgnoreCase(aggre)) {
            return "合计值";
        } else if ("avg".equalsIgnoreCase(aggre)) {
            return "均值";
        } else if ("max".equalsIgnoreCase(aggre)) {
            return "最大值";
        } else if ("min".equalsIgnoreCase(aggre)) {
            return "最小值";
        } else if ("count".equalsIgnoreCase(aggre)) {
            return "计数";
        } else if ("var".equalsIgnoreCase(aggre)) {
            return "方差";
        } else if ("sd".equalsIgnoreCase(aggre)) {
            return "标准差";
        } else {
            return "middle".equalsIgnoreCase(aggre) ? "中位数" : "合计";
        }
    }

    public List loadField(String type, String values, Map keys) {
        return null;
    }
}
