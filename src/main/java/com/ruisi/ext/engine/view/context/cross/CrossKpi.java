//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.cross;

import java.math.BigDecimal;
import java.util.List;

public interface CrossKpi extends Cloneable {
    String getDesc();

    void setDesc(String var1);

    String getFormatPattern();

    void setFormatPattern(String var1);

    String getAggregation();

    void setAggregation(String var1);

    String getType();

    void setType(String var1);

    String getAlias();

    void setAlias(String var1);

    String getJsFunc();

    void setJsFunc(String var1);

    Boolean getTypeChg2Kpi();

    void setTypeChg2Kpi(Boolean var1);

    CrossKpi clone();

    String getFormula();

    void setFormula(String var1);

    String getValue();

    void setValue(String var1);

    BigDecimal getKpiRate();

    void setKpiRate(BigDecimal var1);

    boolean isFinanceFmt();

    void setFinanceFmt(boolean var1);

    String getExtDs();

    void setExtDs(String var1);

    void setOther(List var1);

    List getOther();

    String getDataClass();

    void setDataClass(String var1);
}
