//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.context.face.RefChecker;

public interface ButtonContext extends RefChecker, InputField {
    void setType(String var1);

    String getType();

    void setAction(String var1);

    String getAction();

    void setMethod(String var1);

    String getMethod();

    String getOnClick();

    void setOnClick(String var1);

    String getFrom();

    void setFrom(String var1);

    String getSubmit();

    void setSubmit(String var1);

    String getCheckParam();

    void setCheckParam(String var1);

    boolean isConfirm();

    void setConfirm(boolean var1);

    void setSrc(String var1);

    String getSrc();

    String getExportDataGrid();

    void setExportDataGrid(String var1);

    String getTarget();

    void setTarget(String var1);

    String[] getMvId();

    void setMvId(String[] var1);

    String getIconCls();

    void setIconCls(String var1);

    String getStyleClass();

    void setStyleClass(String var1);
}
