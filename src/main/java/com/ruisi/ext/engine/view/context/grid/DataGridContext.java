//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.grid;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.face.OptionsLoader;
import com.ruisi.ext.engine.view.context.face.RefChecker;
import com.ruisi.ext.engine.view.context.face.Template;

public interface DataGridContext extends Element, OptionsLoader, RefChecker, Template {
    ColConfigContext getColConfigContext();

    void setColConfigContext(ColConfigContext var1);

    PageInfo getPageInfo();

    void setPageInfo(PageInfo var1);

    void setDataId(String var1);

    String getDataId();

    String getRef();

    void setRef(String var1);

    boolean isAjax();

    void setAjax(boolean var1);

    String getPageInputName();

    void setPageInputName(String var1);

    String getId();

    void setId(String var1);

    String getDataRef();

    void setDataRef(String var1);

    boolean isInit();

    void setInit(boolean var1);

    String getLabel();

    void setLabel(String var1);

    Boolean getExport();

    void setExport(Boolean var1);

    String getOut();

    void setOut(String var1);

    String getRightMenuFunc();

    void setRightMenuFunc(String var1);

    String getTargetDiv();

    void setTargetDiv(String var1);

    String getRefDsource();

    void setRefDsource(String var1);
}
