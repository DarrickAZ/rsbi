//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.gridreport;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.face.OptionsLoader;
import com.ruisi.ext.engine.view.context.face.Template;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import java.util.Map;

public interface GridReportContext extends Element, OptionsLoader, Template {
    GridCell[][] getHeaders();

    void setHeaders(GridCell[][] var1);

    GridCell[][] getDetails();

    void setDetails(GridCell[][] var1);

    GridCell[][] getFooters();

    void setFooters(GridCell[][] var1);

    String getId();

    void setId(String var1);

    String getLabel();

    void setLabel(String var1);

    PageInfo getPageInfo();

    void setPageInfo(PageInfo var1);

    String getRefDataCenter();

    void setRefDataCenter(String var1);

    Map getExtDatas();

    void setExtDatas(Map var1);

    String getRefDsource();

    void setRefDsource(String var1);

    String getWidth();

    void setWidth(String var1);

    String getHeight();

    void setHeight(String var1);

    Boolean getTrMenu();

    void setTrMenu(Boolean var1);

    String getOut();

    void setOut(String var1);

    String getStyle();

    void setStyle(String var1);
}
