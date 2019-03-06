//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

public interface DateSelectContext extends InputField {
    String getDateFormat();

    String getMaxDate();

    String getMinDate();

    void setDateFormat(String var1);

    void setMaxDate(String var1);

    void setMinDate(String var1);

    Boolean getShowCalendar();

    void setShowCalendar(Boolean var1);

    String[] getTarget();

    void setTarget(String[] var1);

    String getDateType();

    void setDateType(String var1);
}
