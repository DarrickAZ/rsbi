//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.grid.DataGridContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.Iterator;

public class LabelUtils {
    public LabelUtils() {
    }

    public static CrossReportContext findCrossBylabel(MVContext var0, String var1) {
        if (var0.getCrossReports() == null) {
            return null;
        } else {
            Iterator var3 = var0.getCrossReports().values().iterator();

            while(var3.hasNext()) {
                CrossReportContext var2 = (CrossReportContext)var3.next();
                if (var1.equals(var2.getLabel())) {
                    return var2;
                }
            }

            return null;
        }
    }

    public static ChartContext findChartBylabel(MVContext var0, String var1) {
        if (var0.getCharts() == null) {
            return null;
        } else {
            Iterator var3 = var0.getCharts().values().iterator();

            while(var3.hasNext()) {
                ChartContext var2 = (ChartContext)var3.next();
                if (var1.equals(var2.getLabel())) {
                    return var2;
                }
            }

            return null;
        }
    }

    public static DataGridContext findDataGridBylabel(MVContext var0, String var1) {
        if (var0.getDataGrids() == null) {
            return null;
        } else {
            Iterator var3 = var0.getDataGrids().values().iterator();

            while(var3.hasNext()) {
                DataGridContext var2 = (DataGridContext)var3.next();
                if (var1.equals(var2.getLabel())) {
                    return var2;
                }
            }

            return null;
        }
    }

    public static GridReportContext findGridReportBylabel(MVContext var0, String var1) {
        if (var0.getGridReports() == null) {
            return null;
        } else {
            Iterator var3 = var0.getGridReports().values().iterator();

            while(var3.hasNext()) {
                GridReportContext var2 = (GridReportContext)var3.next();
                if (var1.equals(var2.getLabel())) {
                    return var2;
                }
            }

            return null;
        }
    }

    public static Object findObjectByLabel(MVContext var0, String var1, String var2) throws ExtConfigException {
        if ("chart".equalsIgnoreCase(var2)) {
            return findChartBylabel(var0, var1);
        } else if ("cross".equalsIgnoreCase(var2)) {
            return findCrossBylabel(var0, var1);
        } else if ("dataGrid".equalsIgnoreCase(var2)) {
            return findDataGridBylabel(var0, var1);
        } else if ("gridReport".equalsIgnoreCase(var2)) {
            return findGridReportBylabel(var0, var1);
        } else {
            throw new ExtConfigException("组件类型 " + var2 + "未找到。");
        }
    }

    public static String findServiceIdByType(String var0) throws ExtConfigException {
        if ("chart".equalsIgnoreCase(var0)) {
            return "ext.sys.chart.rebuild";
        } else if ("cross".equalsIgnoreCase(var0)) {
            return "ext.sys.cross.rebuild";
        } else if ("dataGrid".equalsIgnoreCase(var0)) {
            return "ext.sys.fenye.ajax";
        } else if ("gridReport".equalsIgnoreCase(var0)) {
            return "ext.sys.gridreport.rebuild";
        } else {
            throw new ExtConfigException("组件类型 " + var0 + "未找到。");
        }
    }
}
