//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.cross;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.face.OptionsLoader;
import com.ruisi.ext.engine.view.context.face.RefChecker;
import com.ruisi.ext.engine.view.context.face.Template;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import java.util.List;
import java.util.Map;

public interface CrossReportContext extends Element, OptionsLoader, RefChecker, Template {
    CrossCols getCrossCols();

    void setCrossCols(CrossCols var1);

    CrossRows getCrossRows();

    void setCrossRows(CrossRows var1);

    void setRef(String var1);

    String getRef();

    String getId();

    void setId(String var1);

    boolean isShowData();

    void setShowData(boolean var1);

    String getOut();

    void setOut(String var1);

    List getDims();

    void setDims(List var1);

    void setDataUrl(String var1);

    String getDataUrl();

    String getLabel();

    void setLabel(String var1);

    Boolean getExport();

    void setExport(Boolean var1);

    PageInfo getPageInfo();

    void setPageInfo(PageInfo var1);

    Boolean getPrint();

    void setPrint(Boolean var1);

    String getHeight();

    void setHeight(String var1);

    String getExportName();

    void setExportName(String var1);

    String getFirstSort();

    void setFirstSort(String var1);

    String getTotalTemplateName();

    void setTotalTemplateName(String var1);

    CrossKpi getBaseKpi();

    void setBaseKpi(CrossKpi var1);

    String getRefDataCetner();

    void setRefDataCetner(String var1);

    List getRowHeads();

    void setRowHeads(List var1);

    Boolean getOrderDrill();

    void setOrderDrill(Boolean var1);

    Boolean getBgAgg();

    void setBgAgg(Boolean var1);

    Map getExtSqlTemplates();

    void setExtSqlTemplates(Map var1);

    Map getExtDatas();

    void setExtDatas(Map var1);

    String getConfCallBack();

    void setConfCallBack(String var1);

    String getRefDsource();

    void setRefDsource(String var1);

    String getWidth();

    void setWidth(String var1);

    String getStyle();

    void setStyle(String var1);
}
