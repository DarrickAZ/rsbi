//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context;

import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.context.html.SubmitCheckContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

import java.util.Map;

public interface MVContext extends Element {
    String getMvid();

    void setMvid(String var1);

    String getFormId();

    void setFormId(String var1);

    boolean isShowForm();

    void setShowForm(boolean var1);

    void setSubmitCheck(SubmitCheckContext var1);

    SubmitCheckContext getSubmitCheck();

    boolean isUpload();

    void setUpload(boolean var1);

    Map getSqls();

    void setSqls(Map var1);

    Map getCharts();

    void setCharts(Map var1);

    Map getDataGrids();

    void setDataGrids(Map var1);

    Map getCrossReports();

    void setCrossReports(Map var1);

    Map getGridReports();

    void setGridReports(Map var1);

    boolean isFromRef();

    void setFromRef(boolean var1);

    String getScripts();

    void setScripts(String var1);

    Map getCubeDataCenters();

    void setCubeDataCenters(Map var1);

    Map getGridDataCenters();

    void setGridDataCenters(Map var1);

    Map getDsources();

    void setDsources(Map var1);

    Boolean getHideMV();

    void setHideMV(Boolean var1);

    Map getMvParams();

    void setMvParam(String var1, InputField var2) throws ExtConfigException;
}
