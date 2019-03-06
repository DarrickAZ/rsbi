//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import java.util.List;
import java.util.Map;

public abstract class DataCenter {
    public static final String dimValuesSplitSymbol = "@";
    public static final String paramsStartSymbol = "$";
    public static final String dynamicKpiSymbol = "@DKPI@";
    public static final String dynamicKpiKeySymbol = "CKP_CODE";
    protected Map inputParams;
    protected Map refDataSets;
    protected Map extKpiAndAlias;
    protected List extKpiFormula;
    protected List baseExtKpi;
    protected List jsExtKpiFuncAndAlias;
    protected Map sysKpiComputeFNameAndParams;

    public DataCenter() {
    }

    public abstract DataSet getDataSet(DataCenterContext var1);

    public DataSet getDataSetById(String var1) throws ScriptEnginerException {
        DataSet var2 = (DataSet)this.refDataSets.get(var1);
        if (var2 == null) {
            String var3 = ConstantsEngine.replace(" _getDataSetById 出错，引用的数据集不存在。(id=$0)", var1);
            throw new ScriptEnginerException(var3);
        } else {
            return var2;
        }
    }

    public void setInputParams(Map var1) {
        this.inputParams = var1;
    }

    public Map getInputParams() {
        return this.inputParams;
    }

    public Map getRefDataSets() {
        return this.refDataSets;
    }

    public void setRefDataSets(Map var1) {
        this.refDataSets = var1;
    }

    public Map getExtKpiAndAlias() {
        return this.extKpiAndAlias;
    }

    public List getExtKpiFormula() {
        return this.extKpiFormula;
    }

    public List getBaseExtKpi() {
        return this.baseExtKpi;
    }

    public List getJsExtKpiFuncAndAlias() {
        return this.jsExtKpiFuncAndAlias;
    }

    public void setJsExtKpiFuncAndAlias(List var1) {
        this.jsExtKpiFuncAndAlias = var1;
    }

    public Map getSysKpiComputeFNameAndParams() {
        return this.sysKpiComputeFNameAndParams;
    }

    public void setSysKpiComputeFNameAndParams(Map var1) {
        this.sysKpiComputeFNameAndParams = var1;
    }
}
