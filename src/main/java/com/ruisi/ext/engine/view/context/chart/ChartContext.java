//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.chart;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.face.ContextSerial;
import com.ruisi.ext.engine.view.context.face.OptionsLoader;
import com.ruisi.ext.engine.view.context.face.RefChecker;
import com.ruisi.ext.engine.view.context.face.Template;
import java.util.List;
import java.util.Map;

public interface ChartContext extends Element, ContextSerial, OptionsLoader, RefChecker, Template {
    void setShape(String var1);

    String getShape();

    String getXcol();

    void setXcol(String var1);

    String getYcol();

    void setYcol(String var1);

    String getScol();

    void setScol(String var1);

    String getWidth();

    void setWidth(String var1);

    String getHeight();

    void setHeight(String var1);

    String getProperty();

    void setProperty(String var1);

    String getStyle();

    void setStyle(String var1);

    String getRef();

    void setRef(String var1);

    String getId();

    void setId(String var1);

    void setDtype(String var1);

    String getDtype();

    List getProperties();

    void setProperties(List var1);

    String[] getFormula();

    void setFormula(String[] var1);

    String[] getFormulaName();

    void setFormulaName(String[] var1);

    String getLabel();

    void setLabel(String var1);

    String getRefDataCenter();

    void setRefDataCenter(String var1);

    String getY2col();

    void setY2col(String var1);

    String getY3col();

    void setY3col(String var1);

    String getRefDsource();

    void setRefDsource(String var1);

    String getXcolDesc();

    void setXcolDesc(String var1);

    Integer getRate();

    void setRate(Integer var1);

    Integer getRate2();

    void setRate2(Integer var1);

    Integer getRate3();

    void setRate3(Integer var1);

    ChartLinkContext getLink();

    void setLink(ChartLinkContext var1);

    ChartTitleContext getTitle();

    void setTitle(ChartTitleContext var1);

    String getAlign();

    void setAlign(String var1);

    ChartDrillContext getDrill();

    void setDrill(ChartDrillContext var1);

    String getRightSer();

    void setRightSer(String var1);

    String getDateType();

    void setDateType(String var1);

    String getDateTypeFmt();

    void setDateTypeFmt(String var1);

    Boolean getXlsData();

    void setXlsData(Boolean var1);

    Boolean getMergeData();

    void setMergeData(Boolean var1);

    String getY2Aggre();

    void setY2Aggre(String var1);

    List getYcols();

    void setYcols(List var1);

    Map getSeriesColor();

    void setSeriesColor(Map var1);
}
